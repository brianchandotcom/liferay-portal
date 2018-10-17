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

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Leslie Wong
 * @author Michael Hashimoto
 */
public abstract class BaseBuildDatabase implements BuildDatabase {

	@Override
	public File getBuildDatabaseJSFile() {
		File buildDatabaseJSFile = new File(
			_jsonObjectFile.getParent(), "build-database.js");

		try {
			JenkinsResultsParserUtil.write(
				buildDatabaseJSFile,
				JenkinsResultsParserUtil.combine(
					"build_database=", _jsonObject.toString()));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return buildDatabaseJSFile;
	}

	@Override
	public JSONObject getBuildDataJSONObject(String key) {
		JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

		if (!buildsJSONObject.has(key)) {
			buildsJSONObject.put(key, new JSONObject());
		}

		return buildsJSONObject.getJSONObject(key);
	}

	@Override
	public Properties getProperties(String key) {
		return getProperties(key, null);
	}

	@Override
	public Properties getProperties(String key, Pattern pattern) {
		if (!hasProperties(key)) {
			throw new RuntimeException("Unable to find properties for " + key);
		}

		JSONObject propertiesJSONObject = _jsonObject.getJSONObject(
			"properties");

		Properties properties = new Properties();

		JSONArray propertyJSONArray = propertiesJSONObject.getJSONArray(key);

		for (int i = 0; i < propertyJSONArray.length(); i++) {
			JSONObject propertyJSONObject = propertyJSONArray.getJSONObject(i);

			String propertyName = propertyJSONObject.getString("name");
			String propertyValue = propertyJSONObject.getString("value");

			if (pattern == null) {
				properties.setProperty(propertyName, propertyValue);

				continue;
			}

			Matcher matcher = pattern.matcher(propertyName);

			if (matcher.matches()) {
				properties.setProperty(propertyName, propertyValue);
			}
		}

		return properties;
	}

	@Override
	public WorkspaceGitRepository getWorkspaceGitRepository(String key) {
		if (!hasWorkspaceGitRepository(key)) {
			throw new RuntimeException(
				"Unable to find workspace repository for " + key);
		}

		JSONObject workspaceGitRepositoriesJSONObject =
			_jsonObject.getJSONObject("workspace_git_repositories");

		JSONObject workspaceGitRepositoryJSONObject =
			workspaceGitRepositoriesJSONObject.getJSONObject(key);

		return GitRepositoryFactory.getWorkspaceGitRepository(
			workspaceGitRepositoryJSONObject);
	}

	@Override
	public boolean hasBuildData(String key) {
		JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

		return buildsJSONObject.has(key);
	}

	@Override
	public boolean hasProperties(String key) {
		JSONObject buildsJSONObject = _jsonObject.getJSONObject("properties");

		return buildsJSONObject.has(key);
	}

	@Override
	public boolean hasWorkspaceGitRepository(String key) {
		JSONObject workspaceGitRepositoriesJSONObject =
			_jsonObject.getJSONObject("workspace_git_repositories");

		return workspaceGitRepositoriesJSONObject.has(key);
	}

	@Override
	public void putBuildData(String key, BuildData buildData) {
		JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

		buildsJSONObject.put(key, buildData.getJSONObject());

		_jsonObject.put("builds", buildsJSONObject);

		_writeJSONObjectFile();
	}

	@Override
	public void putProperties(String key, File propertiesFile) {
		putProperties(
			key, JenkinsResultsParserUtil.getProperties(propertiesFile));
	}

	@Override
	public void putProperties(String key, Properties properties) {
		JSONObject propertiesJSONObject = _jsonObject.getJSONObject(
			"properties");

		propertiesJSONObject.put(key, _toJSONArray(properties));

		_jsonObject.put("properties", propertiesJSONObject);

		_writeJSONObjectFile();
	}

	@Override
	public void putWorkspaceGitRepository(
		String key, WorkspaceGitRepository workspaceGitRepository) {

		JSONObject workspaceGitRepositoriesJSONObject =
			_jsonObject.getJSONObject("workspace_git_repositories");

		workspaceGitRepositoriesJSONObject.put(
			key, workspaceGitRepository.getJSONObject());

		_jsonObject.put(
			"workspace_git_repositories", workspaceGitRepositoriesJSONObject);

		_writeJSONObjectFile();
	}

	@Override
	public void writeFilteredPropertiesToFile(
		String destFilePath, Pattern pattern, String key) {

		Properties properties = getProperties(key, pattern);

		StringBuilder sb = new StringBuilder();

		for (String propertyName : properties.stringPropertyNames()) {
			sb.append(propertyName);
			sb.append("=");
			sb.append(properties.getProperty(propertyName));
			sb.append("\n");
		}

		try {
			JenkinsResultsParserUtil.write(destFilePath, sb.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Override
	public void writePropertiesToFile(String destFilePath, String key) {
		writeFilteredPropertiesToFile(destFilePath, null, key);
	}

	protected BaseBuildDatabase(File baseDir) {
		_jsonObjectFile = new File(
			baseDir, BuildDatabase.BUILD_DATABASE_FILE_NAME);

		_jsonObject = _getJSONObject();

		if (!_jsonObject.has("builds")) {
			_jsonObject.put("builds", new JSONObject());
		}

		if (!_jsonObject.has("properties")) {
			_jsonObject.put("properties", new JSONObject());
		}

		if (!_jsonObject.has("workspace_git_repositories")) {
			_jsonObject.put("workspace_git_repositories", new JSONObject());
		}

		_writeJSONObjectFile();
	}

	private JSONObject _getJSONObject() {
		if (_jsonObjectFile.exists()) {
			try {
				return new JSONObject(
					JenkinsResultsParserUtil.read(_jsonObjectFile));
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		return new JSONObject();
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

	private void _writeJSONObjectFile() {
		try {
			JenkinsResultsParserUtil.write(
				_jsonObjectFile, _jsonObject.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private final JSONObject _jsonObject;
	private final File _jsonObjectFile;

}