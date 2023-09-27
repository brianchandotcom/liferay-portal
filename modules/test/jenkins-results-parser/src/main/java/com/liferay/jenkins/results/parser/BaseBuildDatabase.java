/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.net.URL;

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
	public File getBuildDatabaseFile() {
		return _buildDatabaseFile;
	}

	@Override
	public JSONObject getBuildDataJSONObject(String key) {
		JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

		if (!buildsJSONObject.has(key)) {
			return new JSONObject();
		}

		return buildsJSONObject.getJSONObject(key);
	}

	@Override
	public JSONObject getBuildDataJSONObject(URL buildURL) {
		String buildURLString = buildURL.toString();

		JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

		for (Object key : buildsJSONObject.keySet()) {
			JSONObject buildJSONObject = buildsJSONObject.getJSONObject(
				key.toString());

			if (!buildJSONObject.has("build_url")) {
				continue;
			}

			if (buildURLString.equals(buildJSONObject.getString("build_url"))) {
				return buildJSONObject;
			}
		}

		return new JSONObject();
	}

	@Override
	public Job getJob(String key) {
		if (!hasJob(key)) {
			return null;
		}

		JSONObject jobsJSONObject = _jsonObject.getJSONObject("jobs");

		JSONObject jobJSONObject = jobsJSONObject.getJSONObject(key);

		return JobFactory.newJob(jobJSONObject);
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
	public PullRequest getPullRequest(String key) {
		if (!hasPullRequest(key)) {
			throw new RuntimeException(
				"Unable to find pull request for " + key);
		}

		JSONObject pullRequestsJSONObject = _jsonObject.getJSONObject(
			"pull_requests");

		JSONObject pullRequestJSONObject = pullRequestsJSONObject.getJSONObject(
			key);

		return PullRequestFactory.newPullRequest(pullRequestJSONObject);
	}

	@Override
	public Workspace getWorkspace(String key) {
		if (!hasWorkspace(key)) {
			throw new RuntimeException("Unable to find workspace");
		}

		JSONObject workspacesJSONObject = _jsonObject.getJSONObject(
			"workspaces");

		return WorkspaceFactory.newWorkspace(
			workspacesJSONObject.getJSONObject(key));
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
	public boolean hasJob(String key) {
		JSONObject jobsJSONObject = _jsonObject.getJSONObject("jobs");

		if (jobsJSONObject.has(key)) {
			JSONObject jobJSONObject = jobsJSONObject.getJSONObject(key);

			if ((jobJSONObject != null) && !jobJSONObject.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasProperties(String key) {
		JSONObject buildsJSONObject = _jsonObject.getJSONObject("properties");

		return buildsJSONObject.has(key);
	}

	@Override
	public boolean hasPullRequest(String key) {
		JSONObject pullRequestsJSONObject = _jsonObject.getJSONObject(
			"pull_requests");

		return pullRequestsJSONObject.has(key);
	}

	@Override
	public boolean hasWorkspace(String key) {
		JSONObject workspacesJSONObject = _jsonObject.getJSONObject(
			"workspaces");

		return workspacesJSONObject.has(key);
	}

	@Override
	public boolean hasWorkspaceGitRepository(String key) {
		JSONObject workspaceGitRepositoriesJSONObject =
			_jsonObject.getJSONObject("workspace_git_repositories");

		return workspaceGitRepositoriesJSONObject.has(key);
	}

	@Override
	public void putBuildData(String key, BuildData buildData) {
		synchronized (_buildDatabaseFile) {
			JSONObject buildsJSONObject = _jsonObject.getJSONObject("builds");

			buildsJSONObject.put(key, buildData.getJSONObject());

			_writeJSONObjectFile();
		}
	}

	@Override
	public void putJob(String key, Job job) {
		JSONObject jobJSONObject = job.getJSONObject();

		synchronized (_buildDatabaseFile) {
			JSONObject jobsJSONObject = _jsonObject.getJSONObject("jobs");

			jobsJSONObject.put(key, jobJSONObject);

			_writeJSONObjectFile();
		}
	}

	@Override
	public void putProperties(String key, File propertiesFile) {
		putProperties(
			key, JenkinsResultsParserUtil.getProperties(propertiesFile));
	}

	@Override
	public void putProperties(String key, Properties properties) {
		synchronized (_buildDatabaseFile) {
			JSONObject propertiesJSONObject = _jsonObject.getJSONObject(
				"properties");

			propertiesJSONObject.put(key, _toJSONArray(properties));

			_writeJSONObjectFile();
		}
	}

	@Override
	public void putPullRequest(String key, PullRequest pullRequest) {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		synchronized (_buildDatabaseFile) {
			JSONObject pullRequestsJSONObject = _jsonObject.getJSONObject(
				"pull_requests");

			pullRequestsJSONObject.put(key, pullRequest.getJSONObject());

			_writeJSONObjectFile();
		}
	}

	@Override
	public void putWorkspace(String key, Workspace workspace) {
		synchronized (_buildDatabaseFile) {
			JSONObject workspacesJSONObject = _jsonObject.getJSONObject(
				"workspaces");

			workspacesJSONObject.put(key, workspace.getJSONObject());

			_writeJSONObjectFile();
		}
	}

	@Override
	public void putWorkspaceGitRepository(
		String key, WorkspaceGitRepository workspaceGitRepository) {

		synchronized (_buildDatabaseFile) {
			JSONObject workspaceGitRepositoriesJSONObject =
				_jsonObject.getJSONObject("workspace_git_repositories");

			workspaceGitRepositoriesJSONObject.put(
				key, workspaceGitRepository.getJSONObject());

			_writeJSONObjectFile();
		}
	}

	@Override
	public void readBuildDatabaseFile() {
		synchronized (_buildDatabaseFile) {
			if (_buildDatabaseFile.exists()) {
				try {
					_jsonObject = new JSONObject(
						JenkinsResultsParserUtil.read(_buildDatabaseFile));
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
			else {
				_jsonObject = new JSONObject();
			}

			if (!_jsonObject.has("builds")) {
				_jsonObject.put("builds", new JSONObject());
			}

			if (!_jsonObject.has("jobs")) {
				_jsonObject.put("jobs", new JSONObject());
			}

			if (!_jsonObject.has("properties")) {
				_jsonObject.put("properties", new JSONObject());
			}

			if (!_jsonObject.has("pull_requests")) {
				_jsonObject.put("pull_requests", new JSONObject());
			}

			if (!_jsonObject.has("workspace_git_repositories")) {
				_jsonObject.put("workspace_git_repositories", new JSONObject());
			}

			if (!_jsonObject.has("workspaces")) {
				_jsonObject.put("workspaces", new JSONObject());
			}

			_writeJSONObjectFile();
		}
	}

	@Override
	public void writeFilteredPropertiesToFile(
		String destFilePath, Pattern pattern, String key) {

		Properties properties = getProperties(key, pattern);

		StringBuilder sb = new StringBuilder();

		sb.append("## Autogenerated\n");

		for (String propertyName : properties.stringPropertyNames()) {
			String propertyValue = JenkinsResultsParserUtil.getProperty(
				properties, propertyName);

			if (JenkinsResultsParserUtil.isNullOrEmpty(propertyValue)) {
				continue;
			}

			sb.append(propertyName);
			sb.append("=");

			propertyValue = properties.getProperty(propertyName);

			if (JenkinsResultsParserUtil.isWindows() &&
				propertyValue.startsWith("/") &&
				!propertyValue.startsWith("/c/")) {

				sb.append("C:");
			}

			sb.append(propertyValue);
			sb.append("\n");
		}

		try {
			JenkinsResultsParserUtil.write(destFilePath, sb.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		sb = new StringBuilder();

		if (JenkinsResultsParserUtil.isWindows()) {
			sb.append("#!C:/Program Files/Git/bin/sh.exe\n");
		}
		else {
			sb.append("#!/bin/bash\n");
		}

		sb.append("## Autogenerated\n");

		for (String propertyName : properties.stringPropertyNames()) {
			String propertyValue = JenkinsResultsParserUtil.getProperty(
				properties, propertyName);

			if (JenkinsResultsParserUtil.isNullOrEmpty(propertyValue)) {
				continue;
			}

			sb.append("export ");
			sb.append(propertyName);
			sb.append("=");

			if (JenkinsResultsParserUtil.isWindows() &&
				propertyValue.startsWith("/") &&
				!propertyValue.startsWith("/c/")) {

				sb.append("C:");
			}

			sb.append(JenkinsResultsParserUtil.escapeForBash(propertyValue));
			sb.append("\n");
		}

		try {
			JenkinsResultsParserUtil.write(destFilePath + ".sh", sb.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public void writePropertiesToFile(String destFilePath, String key) {
		writeFilteredPropertiesToFile(destFilePath, null, key);
	}

	protected BaseBuildDatabase(File baseDir) {
		_buildDatabaseFile = new File(
			baseDir, BuildDatabase.FILE_NAME_BUILD_DATABASE);

		readBuildDatabaseFile();
	}

	private JSONArray _toJSONArray(Properties properties) {
		JSONArray jsonArray = new JSONArray();

		int i = 0;

		for (String propertyName : properties.stringPropertyNames()) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"name", propertyName
			).put(
				"value", properties.getProperty(propertyName)
			);

			jsonArray.put(i, jsonObject);

			i++;
		}

		return jsonArray;
	}

	private synchronized void _writeJSONObjectFile() {
		synchronized (_buildDatabaseFile) {
			try {
				JenkinsResultsParserUtil.write(
					_buildDatabaseFile, _jsonObject.toString());
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

	private final File _buildDatabaseFile;
	private JSONObject _jsonObject;

}