import java.sql.*;
import java.lang.*;

public class myMain {

    // константы для mySQL server: путь, логин и пароль
    private static final String url = "jdbc:mysql://localhost:3306/myrailway";
    private static final String user = "root";
    private static final String password = "1111";
    private static final String driverName = "com.mysql.jdbc.Driver";

    // переменные для открытия и запросов
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {

        try {

            // подключаем драйвер
            Class.forName(driverName);

            // открываем соединение
            conn = DriverManager.getConnection(url,user,password);
            if (!conn.isClosed()){
                System.out.println("соединено с mySQL");
            }

            // запрос через Statement
            System.out.println("запрос через Statement:");
            stmt = conn.createStatement();
            String query = "SELECT * FROM ticket WHERE passenger = 'Petrov'";
            rs = stmt.executeQuery(query);

            // выводим результат запроса
            while (rs.next()) {
                System.out.println( rs.getInt("id") + "\t" + rs.getString("date") + "\t" + rs.getString("passenger") + "\t" + rs.getString("train"));
            }

            // запрос через Prepared Statement
            System.out.println("запрос через Prepared Statement:");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ticket WHERE passenger = ?");
            pstmt.setString(1,"Petrov");
            ResultSet pRs = pstmt.executeQuery();

            // выводим результат запроса
            while (pRs.next()) {
                System.out.println( pRs.getInt("id") + "\t" + pRs.getString("date") + "\t" + pRs.getString("passenger") + "\t" + pRs.getString("train"));
            }

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                // закрываем соединение и
                conn.close();
                stmt.close();
                rs.close();
                // pRs.close(); - не работает(
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
