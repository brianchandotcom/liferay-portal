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

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface BuildData {

	public static final File DIR_WORKSPACE_DEFAULT = new File(".");

	public static final String FILE_PATH_DIST_ROOT = "/tmp/dist";

	public static final String URL_JENKINS_GITHUB_DEFAULT =
		"https://github.com/liferay/liferay-jenkins-ee/tree/master";

	public File getArtifactDir();

	public String getBuildDescription();

	public Long getBuildDuration();

	public String getBuildDurationString();

	public Integer getBuildNumber();

	public String getBuildParameter(String key);

	public Map<String, String> getBuildParameters();

	public String getBuildResult();

	public String getBuildStatus();

	public String getBuildURL();

	public String getCohortName();

	public Host getHost();

	public String getHostname();

	public String getJenkinsGitHubBranchName();

	public String getJenkinsGitHubRepositoryName();

	public String getJenkinsGitHubURL();

	public String getJenkinsGitHubUsername();

	public String getJobName();

	public String getJobURL();

	public JSONObject getJSONObject();

	public String getMasterHostname();

	public String getRunID();

	public Long getStartTime();

	public String getStartTimeString();

	public TopLevelBuildData getTopLevelBuildData();

	public Integer getTopLevelBuildNumber();

	public Map<String, String> getTopLevelBuildParameters();

	public String getTopLevelJobName();

	public String getTopLevelMasterHostname();

	public String getTopLevelRunID();

	public String getUserContentRelativePath();

	public File getWorkspaceDir();

	public void setBuildDescription(String buildDescription);

	public void setBuildDuration(Long buildDuration);

	public void setBuildResult(String buildResult);

	public void setBuildStatus(String buildStatus);

	public void setBuildURL(String buildURL);

	public void setInvocationTime(Long invocationTime);

	public void setJenkinsGitHubURL(String jenkinsGitHubURL);

	public void setWorkspaceDir(File workspaceDir);

}