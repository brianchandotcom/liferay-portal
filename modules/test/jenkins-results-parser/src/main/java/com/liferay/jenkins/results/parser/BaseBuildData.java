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

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildData
	extends BuildDataJSONObject implements BuildData {

	public static final String TOP_LEVEL_RUN_ID = "top_level";

	public static String getJobName(String buildURL) {
		if (buildURL == null) {
			return null;
		}

		Matcher matcher = _buildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("jobName");
	}

	@Override
	public Integer getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public String getBuildURL() {
		return _buildURL;
	}

	@Override
	public String getCohortName() {
		return _cohortName;
	}

	@Override
	public String getHostname() {
		return _hostname;
	}

	@Override
	public String getJenkinsGitHubURL() {
		return _jenkinsGitHubURL;
	}

	@Override
	public String getJobName() {
		return _jobName;
	}

	@Override
	public String getMasterHostname() {
		return _masterHostname;
	}

	@Override
	public String getRunID() {
		return _runID;
	}

	@Override
	public File getWorkspaceDir() {
		return _workspaceDir;
	}

	@Override
	public JSONObject toJSONObject() {
		return this;
	}

	protected BaseBuildData(Map<String, String> buildParameters, String runID) {
		_init(buildParameters, runID);
	}

	protected BaseBuildData(
		String jsonString, Map<String, String> buildParameters, String runID) {

		super(jsonString);

		_init(buildParameters, runID);
	}

	protected void updateBuildData() {
		JSONObject jsonObject = optJSONObject(_runID);

		if (jsonObject == null) {
			jsonObject = new JSONObject();

			put(_runID, jsonObject);
		}

		jsonObject.put("build_number", getBuildNumber());
		jsonObject.put("build_url", getBuildURL());
		jsonObject.put("cohort_name", getCohortName());
		jsonObject.put("hostname", getHostname());
		jsonObject.put("jenkins_github_url", getJenkinsGitHubURL());
		jsonObject.put("job_name", getJobName());
		jsonObject.put("master_hostname", getMasterHostname());
		jsonObject.put("run_id", getRunID());
		jsonObject.put("workspace_dir", String.valueOf(getWorkspaceDir()));
	}

	private void _init(Map<String, String> buildParameters, String runID) {
		_buildParameters = buildParameters;
		_runID = runID;

		if (has(runID)) {
			JSONObject jsonObject = getJSONObject(runID);

			_buildNumber = jsonObject.getInt("build_number");
			_buildURL = jsonObject.getString("build_url");
			_cohortName = jsonObject.getString("cohort_name");
			_hostname = jsonObject.getString("hostname");
			_jobName = jsonObject.getString("job_name");
			_masterHostname = jsonObject.getString("master_hostname");
			_jenkinsGitHubURL = jsonObject.getString("jenkins_github_url");
			_workspaceDir = new File(jsonObject.getString("workspace_dir"));

			return;
		}

		_hostname = JenkinsResultsParserUtil.getHostName("");

		if (!_buildParameters.containsKey("BUILD_URL")) {
			throw new RuntimeException("Please set BUILD_URL");
		}

		_buildURL = _buildParameters.get("BUILD_URL");

		Matcher matcher = _buildURLPattern.matcher(_buildURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL " + _buildURL);
		}

		_buildNumber = Integer.valueOf(matcher.group("buildNumber"));
		_cohortName = matcher.group("cohortName");
		_jobName = matcher.group("jobName");
		_masterHostname = matcher.group("masterHostname");

		if (_buildParameters.containsKey("JENKINS_GITHUB_URL")) {
			_jenkinsGitHubURL = _buildParameters.get("JENKINS_GITHUB_URL");
		}
		else {
			_jenkinsGitHubURL = _DEFAULT_JENKINS_GITHUB_URL;
		}

		File workspaceDir;

		if (_buildParameters.containsKey("WORKSPACE")) {
			workspaceDir = new File(_buildParameters.get("WORKSPACE"));
		}
		else {
			workspaceDir = new File(".");
		}

		try {
			_workspaceDir = new File(workspaceDir.getCanonicalPath());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		updateBuildData();
	}

	private static final String _DEFAULT_JENKINS_GITHUB_URL =
		"https://github.com/liferay/liferay-jenkins-ee/master";

	private static final Pattern _buildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https?://(?<masterHostname>(?<cohortName>test-\\d+)-\\d+)",
			"(\\.liferay\\.com)?/job/(?<jobName>[^/]+)/(.*/)?",
			"(?<buildNumber>\\d+)/?"));

	private Integer _buildNumber;
	private Map<String, String> _buildParameters;
	private String _buildURL;
	private String _cohortName;
	private String _hostname;
	private String _jenkinsGitHubURL;
	private String _jobName;
	private String _masterHostname;
	private String _runID;
	private File _workspaceDir;

}