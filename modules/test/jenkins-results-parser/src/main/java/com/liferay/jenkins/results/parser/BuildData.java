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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface BuildData {

	public static final String DEFAULT_JENKINS_GITHUB_URL =
		"https://github.com/liferay/liferay-jenkins-ee/tree/master";

	public static final File DEFAULT_WORKSPACE_DIR = new File(".");

	public static final String DIST_ROOT_PATH = "/tmp/dist";

	public String getBuildDescription();

	public Integer getBuildNumber();

	public String getBuildURL();

	public String getCohortName();

	public String getHostname();

	public String getJenkinsGitHubURL();

	public String getJobName();

	public JSONObject getJSONObject();

	public String getMasterHostname();

	public String getRunID();

	public Integer getTopLevelBuildNumber();

	public String getTopLevelJobName();

	public String getTopLevelMasterHostname();

	public String getTopLevelRunID();

	public String getUserContentRelativePath();

	public File getWorkspaceDir();

	public void setBuildDescription(String buildDescription);

	public void setBuildURL(String buildURL);

	public void setJenkinsGitHubURL(String jenkinsGitHubURL);

	public void setWorkspaceDir(File workspaceDir);

}