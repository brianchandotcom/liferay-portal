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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildData implements BuildData {

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
	public String getBuildDescription() {
		return JenkinsResultsParserUtil.combine(
			"<a href=\"https://", getTopLevelMasterHostname(),
			".liferay.com/userContent/", getUserContentRelativePath(),
			"jenkins-report.html\">Jenkins Report</a>");
	}

	@Override
	public Integer getBuildNumber() {
		return getInt("build_number");
	}

	@Override
	public String getBuildURL() {
		return getString("build_url");
	}

	@Override
	public String getCohortName() {
		return getString("cohort_name");
	}

	@Override
	public List<String> getDistNodes() {
		String distNodes = getString("dist_nodes");

		return Arrays.asList(distNodes.split(","));
	}

	@Override
	public String getDistPath() {
		return getString("dist_path");
	}

	@Override
	public String getHostname() {
		return getString("hostname");
	}

	@Override
	public String getJenkinsGitHubURL() {
		return getString("jenkins_github_url");
	}

	@Override
	public String getJobName() {
		return getString("job_name");
	}

	@Override
	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	@Override
	public String getMasterHostname() {
		return getString("master_hostname");
	}

	@Override
	public String getRunID() {
		return _runID;
	}

	@Override
	public String getUserContentRelativePath() {
		return JenkinsResultsParserUtil.combine(
			"jobs/", getTopLevelJobName(), "/builds/",
			String.valueOf(getTopLevelBuildNumber()), "/");
	}

	@Override
	public File getWorkspaceDir() {
		return new File(getString("workspace_dir"));
	}

	protected static boolean isValidJSONObject(
		JSONObject jsonObject, String type) {

		if (type == null) {
			return false;
		}

		if (jsonObject.has("type")) {
			if (type.equals(jsonObject.getString("type"))) {
				return true;
			}
		}

		return false;
	}

	protected BaseBuildData(JSONObject jsonObject) {
		_jsonObject = jsonObject;

		_runID = getString("run_id");

		validateKeys(_REQUIRED_KEYS);
	}

	protected BaseBuildData(Map<String, String> buildParameters) {
		_jsonObject = new JSONObject();
		_runID = _getRunID(buildParameters);

		String buildURL = _getBuildURL(buildParameters);

		Matcher matcher = _buildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL " + buildURL);
		}

		put("build_number", Integer.valueOf(matcher.group("buildNumber")));
		put("build_url", buildURL);
		put("cohort_name", matcher.group("cohortName"));
		put("hostname", JenkinsResultsParserUtil.getHostName("default"));
		put("jenkins_github_url", _getJenkinsGitHubURL(buildParameters));
		put("job_name", matcher.group("jobName"));
		put("master_hostname", matcher.group("masterHostname"));
		put("run_id", _runID);
		put("type", getType());
		put("workspace_dir", _getWorkspaceDir(buildParameters));

		validateKeys(_REQUIRED_KEYS);
	}

	protected Integer getInt(String key) {
		return _jsonObject.getInt(key);
	}

	protected String getString(String key) {
		return _jsonObject.getString(key);
	}

	protected abstract String getType();

	protected boolean has(String key) {
		return _jsonObject.has(key);
	}

	protected String optString(String key, String defaultValue) {
		return _jsonObject.optString(key, defaultValue);
	}

	protected void put(String key, Object value) {
		_jsonObject.put(key, value);

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		buildDatabase.putBuildData(getRunID(), this);
	}

	protected void validateKeys(String[] requiredKeys) {
		for (String requiredKey : requiredKeys) {
			if (!has(requiredKey)) {
				throw new RuntimeException("Missing " + requiredKey);
			}
		}
	}

	private String _getBuildURL(Map<String, String> buildParameters) {
		if (!buildParameters.containsKey("BUILD_URL")) {
			throw new RuntimeException("Please set BUILD_URL");
		}

		return buildParameters.get("BUILD_URL");
	}

	private String _getJenkinsGitHubURL(Map<String, String> buildParameters) {
		String jenkinsGitHubURL = buildParameters.get("JENKINS_GITHUB_URL");

		if ((jenkinsGitHubURL == null) || jenkinsGitHubURL.equals("")) {
			return _DEFAULT_JENKINS_GITHUB_URL;
		}

		return jenkinsGitHubURL;
	}

	private String _getRunID(Map<String, String> buildParameters) {
		String runID = buildParameters.get("RUN_ID");

		if ((runID == null) || runID.equals("")) {
			return _DEFAULT_RUN_ID;
		}

		return runID;
	}

	private String _getWorkspaceDir(Map<String, String> buildParameters) {
		File workspaceDir = new File(
			buildParameters.getOrDefault("WORKSPACE", _DEFAULT_WORKSPACE));

		try {
			return workspaceDir.getCanonicalPath();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static final String _DEFAULT_JENKINS_GITHUB_URL =
		"https://github.com/liferay/liferay-jenkins-ee/master";

	private static final String _DEFAULT_RUN_ID = "default";

	private static final String _DEFAULT_WORKSPACE = ".";

	private static final String[] _REQUIRED_KEYS = {
		"build_url", "build_number", "cohort_name", "hostname",
		"jenkins_github_url", "job_name", "master_hostname", "run_id",
		"workspace_dir"
	};

	private static final Pattern _buildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https?://(?<masterHostname>(?<cohortName>test-\\d+)-\\d+)",
			"(\\.liferay\\.com)?/job/(?<jobName>[^/]+)/(.*/)?",
			"(?<buildNumber>\\d+)/?"));

	private final JSONObject _jsonObject;
	private final String _runID;

}