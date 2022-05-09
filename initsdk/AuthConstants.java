package us.zoom.sdksample.initsdk;

import android.util.JsonReader;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public  class AuthConstants {

	// TODO Change it to your web domain
	public String WEB_DOMAIN = "zoom.us";

	/**
	 * We recommend that, you can generate jwttoken on your own server instead of hardcore in the code.
	 * We hardcore it here, just to run the demo.
	 *
	 * You can generate a jwttoken on the https://jwt.io/
	 * with this payload:
	 * {
	 *
	 *     "appKey": "string", // app key
	 *     "iat": long, // access token issue timestamp
	 *     "exp": long, // access token expire time
	 *     "tokenExp": long // token expire time
	 * }
	 */




	  public	 String SDK_JWTTOKEN(){
		// Create URL
		try{
			String url = "https://yoururl.here/meeting/";
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Accept","application/json");

			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);

			conn.connect();


			JSONObject jsonParam = new JSONObject();
			jsonParam.put("meetingNumber", 123456789);
			jsonParam.put("role", 1);



			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			//os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
			wr.writeBytes(jsonParam.toString());

			wr.flush();
			wr.close();

			try {
				InputStream in = new BufferedInputStream(conn.getInputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder result = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}

				JSONObject obj = new JSONObject(result.toString());
				return obj.getString("signature");

			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}



	}

}


