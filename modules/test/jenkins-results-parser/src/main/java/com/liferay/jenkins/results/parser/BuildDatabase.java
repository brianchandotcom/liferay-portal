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

import java.net.URL;

import java.util.Properties;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 * @author Michael Hashimoto
 */
public interface BuildDatabase {

	public static final String FILE_NAME_BUILD_DATABASE = "build-database.json";

	public File getBuildDatabaseFile();

	public JSONObject getBuildDataJSONObject(String key);

	public JSONObject getBuildDataJSONObject(URL buildURL);

	public Properties getProperties(String key);

	public Properties getProperties(String key, Pattern pattern);

	public PullRequest getPullRequest(String key);

	public Workspace getWorkspace(String key);

	public WorkspaceGitRepository getWorkspaceGitRepository(String key);

	public boolean hasBuildData(String key);

	public boolean hasProperties(String key);

	public boolean hasPullRequest(String key);

	public boolean hasWorkspace(String key);

	public boolean hasWorkspaceGitRepository(String key);

	public void putBuildData(String key, BuildData buildData);

	public void putProperties(String key, File propertiesFile);

	public void putProperties(String key, Properties properties);

	public void putPullRequest(String key, PullRequest pullRequest);

	public void putWorkspace(String key, Workspace workspace);

	public void putWorkspaceGitRepository(
		String key, WorkspaceGitRepository workspaceGitRepository);

	public void writeFilteredPropertiesToFile(
		String destFilePath, Pattern pattern, String key);

	public void writePropertiesToFile(String destFilePath, String key);

}