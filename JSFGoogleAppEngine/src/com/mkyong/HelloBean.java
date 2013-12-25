package com.mkyong;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;
import org.bitpipeline.lib.owm.WeatherData.WeatherCondition;
import org.json.JSONException;

@ManagedBean(name = "weatherBean", eager = true)
@SessionScoped
public class HelloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String city;
	private String weatherInfo;

	private Map<String,String> cities = new HashMap<String, String>();
	
	
	public HelloBean() {
		cities.put("Koluszki", "Koluszki");
		cities.put("Lodz", "Lodz");
		cities.put("Wroclaw", "Wroclaw");
	}
	
	public void handleCityChange() {
		OwmClient owm = new OwmClient ();
		WeatherStatusResponse currentWeather;
		try {
			currentWeather = owm.currentWeatherAtCity (city, "PL");
			if (currentWeather.hasWeatherStatus ()) {
			    WeatherData weather = currentWeather.getWeatherStatus ().get (0);
			    if (weather.getPrecipitation () == Integer.MIN_VALUE) {
			        WeatherCondition weatherCondition = weather.getWeatherConditions ().get (0);
			        String description = weatherCondition.getDescription ();
			        if (description.contains ("rain") || description.contains ("shower"))
			            weatherInfo = "No rain measures in "+city+" but reports of " + description;
			        else
			        	weatherInfo = "No rain measures in "+city+": " + description;
			    } else
			    	weatherInfo = "It's raining in "+city+": " + weather.getPrecipitation () + " mm/h";
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Map<String, String> getCities() {
		
		return cities;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getWeatherInfo() {
		return weatherInfo;
	}

	public void setWeatherInfo(String weatherInfo) {
		this.weatherInfo = weatherInfo;
	}
}