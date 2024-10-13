import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.util.ArrayList;
import java.util.List;


public class App extends JFrame {

	// Импорт переменных
	private static App mainWindow;
	private static Image snowFlake;
	private static Image logo;
	private static Image background;	
	private static long lastFrameTime;
	private static int dropV = 1;
	private static List<Integer[]> snowFlakes = new ArrayList<Integer[]>();

	

	public static void main (String[] args) throws IOException {
		
		// Импорт изображений
		snowFlake = ImageIO.read(App.class.getResourceAsStream("resources/snowflake.png"));
		logo = ImageIO.read(App.class.getResourceAsStream("resources/logo.png"));
		background = ImageIO.read(App.class.getResourceAsStream("resources/background.jpg"));


		// Иннициализация окна приложения
		mainWindow = new App();
		mainWindow.setTitle("App");
		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainWindow.setLocation(200, 50);
		mainWindow.setSize(900, 600);
		mainWindow.setResizable(false);
		AppField appField = new AppField();
		mainWindow.setVisible(true);
		mainWindow.add(appField);
		mainWindow.setVisible(true);


		lastFrameTime = System.nanoTime();
		

	}
	
	private static void onRepaint (Graphics g) {	
		Random random = new Random();
		long currentTime = System.nanoTime();
		float deltTime = (currentTime - lastFrameTime);	
		g.drawImage(background, 0, 0, null);
		g.drawImage(logo, 10, 20, null);

		// Добавление новых снежинок		
		if (deltTime > 1e+9) {		
			lastFrameTime = currentTime;			
			
			boolean isValidCrdnts = false;
			while (!isValidCrdnts){
				if (snowFlakes.size() >= 20) {
					break;
				}

				int crdntX = random.nextInt(900);
				boolean inList = false;
				for (int i = 0; i < snowFlakes.size(); i++){
					if (snowFlakes.get(i)[0] == crdntX){
						inList = true;
					}
				}

				if (!inList) {
					Integer[] newEl = {crdntX, 0};
					snowFlakes.add(newEl);
					isValidCrdnts = true;
					break;
				}
			}	
		}

		// Отрисовка всех снежинок
		for (int i = 0; i < snowFlakes.size(); i++){
			g.drawImage(snowFlake,  snowFlakes.get(i)[0],  snowFlakes.get(i)[1] , null);
		}

		// Анимация снежинок
		for (int i = 0; i < snowFlakes.size(); i++){
			if (snowFlakes.get(i)[1] > 600) {
				snowFlakes.remove(i);
			}
			else {
				snowFlakes.get(i)[1] += dropV;
			}
			
		}
	
	};
	
	private static class AppField extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			onRepaint(g);
			repaint();
		};
	};

	
}
