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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class BuildDataJSONObject extends JSONObject {

	public BuildDataJSONObject() {
	}

	public BuildDataJSONObject(String jsonString) {
		super(jsonString);
	}

	public void addPropertiesToBuildData(String key, Properties properties) {
		JSONArray jsonArray = _toJSONArray(properties);

		put(key, jsonArray);
	}

	public void addPropertiesToBuildData(
		String key, String pathToPropertiesFile) {

		addPropertiesToBuildData(
			key,
			JenkinsResultsParserUtil.getProperties(
				new File(pathToPropertiesFile)));
	}

	public Map<String, String> getBuildDataMap(String key) {
		return getBuildDataMapWithFilter(key, null);
	}

	public Map<String, String> getBuildDataMapWithFilter(
		String key, String filter) {

		if (!has(key)) {
			throw new RuntimeException(
				"Unable to find build data for '" + key + "'");
		}

		Map<String, String> buildDataMap = new HashMap<>();

		JSONArray jsonArray = getJSONArray(key);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String propertyName = jsonObject.getString("name");

			if ((filter == null) ||
				((filter != null) && propertyName.contains(filter))) {

				buildDataMap.put(propertyName, jsonObject.getString("value"));
			}
		}

		return buildDataMap;
	}

	public void writeFilteredPropertiesToFile(
			String destFilePath, String filter, String key)
		throws IOException {

		Map<String, String> buildDataMap = getBuildDataMapWithFilter(
			key, filter);

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : buildDataMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
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