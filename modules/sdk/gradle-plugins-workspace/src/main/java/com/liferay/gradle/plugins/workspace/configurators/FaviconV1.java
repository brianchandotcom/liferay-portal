package com.liferay.gradle.plugins.workspace.configurators;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FaviconV1 {
	@JsonProperty(required = true) 
	public String name;
	public String description;
	@JsonProperty(required = true) 
	public String url;
	
	protected static final Pattern urlPrefixPattern = Pattern.compile("^https?:\\/\\/");
	public String toJSON() {
		Map<String, Object> config = new HashMap<>();
		
		config.put("description", description);
		
		String urlPrefix = "$[conf:host.service.address]/";
		Matcher urlMatcher = urlPrefixPattern.matcher(url);
		config.put("url", urlMatcher.matches() ? "" : urlPrefix + url);
		
		Map<String, Object> json = new HashMap<>();

		json.put("com.liferay.client.extensions.factory.configuration.v1.FaviconConfiguration~" + name, config);

		ObjectMapper objectMapper = new ObjectMapper();
		
		StringWriter sw = new StringWriter();
		try {
			objectMapper.writeValue(sw, json);
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sw.toString();
	}
}