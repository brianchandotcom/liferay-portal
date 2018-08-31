/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class JenkinsJSONObject extends JSONObject {

	public JenkinsJSONObject() {
	}

	public JenkinsJSONObject(String json) {
		super(json);
	}

	public void addBuildData(BuildData buildData) {
		put(buildData.getRunID(), buildData.toJSONObject());
	}

	public void addProperties(String key, Properties properties) {
		JSONArray jsonArray = _toJSONArray(properties);

		put(key, jsonArray);
	}

	public void addProperties(String key, String propertiesFilePath) {
		addProperties(
			key,
			JenkinsResultsParserUtil.getProperties(
				new File(propertiesFilePath)));
	}

	public Map<String, String> getMap(String key) {
		return getMap(key, null);
	}

	public Map<String, String> getMap(String key, Pattern pattern) {
		if (!has(key)) {
			throw new RuntimeException("Unable to find map for '" + key + "'");
		}

		Map<String, String> map = new HashMap<>();

		JSONArray jsonArray = getJSONArray(key);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String propertyName = jsonObject.getString("name");

			if (pattern == null) {
				map.put(propertyName, jsonObject.getString("value"));

				continue;
			}

			Matcher matcher = pattern.matcher(propertyName);

			if (matcher.matches()) {
				map.put(propertyName, jsonObject.getString("value"));
			}
		}

		return map;
	}

	public void writeFilteredPropertiesToFile(
			String destFilePath, Pattern pattern, String key)
		throws IOException {

		Map<String, String> map = getMap(key, pattern);

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("\n");
		}

		JenkinsResultsParserUtil.write(destFilePath, sb.toString());
	}

	public void writePropertiesToFile(String destFilePath, String key)
		throws IOException {

		writeFilteredPropertiesToFile(destFilePath, null, key);
	}

	private JSONArray _toJSONArray(Properties properties) {
		JSONArray jsonArray = new JSONArray();

		int i = 0;

		for (String propertyName : properties.stringPropertyNames()) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("name", propertyName);
			jsonObject.put("value", properties.getProperty(propertyName));

			jsonArray.put(i, jsonObject);

			i++;
		}

		return jsonArray;
	}

}