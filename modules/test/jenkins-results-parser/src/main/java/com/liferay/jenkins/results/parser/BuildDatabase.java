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

import java.util.Properties;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 * @author Michael Hashimoto
 */
public interface BuildDatabase {

	public static final String BUILD_DATABASE_FILE_NAME = "build-database.json";

	public File getBuildDatabaseJSFile();

	public JSONObject getBuildDataJSONObject(String key);

	public Properties getProperties(String key);

	public Properties getProperties(String key, Pattern pattern);

	public WorkspaceGitRepository getWorkspaceGitRepository(String key);

	public boolean hasBuildData(String key);

	public boolean hasProperties(String key);

	public boolean hasWorkspaceGitRepository(String key);

	public void putBuildData(String key, BuildData buildData);

	public void putProperties(String key, File propertiesFile);

	public void putProperties(String key, Properties properties);

	public void putWorkspaceGitRepository(
		String key, WorkspaceGitRepository workspaceGitRepository);

	public void writeFilteredPropertiesToFile(
		String destFilePath, Pattern pattern, String key);

	public void writePropertiesToFile(String destFilePath, String key);

}