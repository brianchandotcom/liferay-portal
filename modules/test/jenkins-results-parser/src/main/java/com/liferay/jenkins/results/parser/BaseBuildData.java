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
public abstract class BaseBuildData extends JSONObject implements BuildData {

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
	public String getMasterHostname() {
		return getString("master_hostname");
	}

	@Override
	public String getRunID() {
		return _runID;
	}

	@Override
	public File getWorkspaceDir() {
		return new File(getString("workspace_dir"));
	}

	@Override
	public JSONObject toJSONObject() {
		return this;
	}

	protected BaseBuildData(
		Map<String, String> buildParameters,
		JenkinsJSONObject jenkinsJSONObject) {

		this(buildParameters, jenkinsJSONObject, _DEFAULT_RUN_ID);
	}

	protected BaseBuildData(
		Map<String, String> buildParameters,
		JenkinsJSONObject jenkinsJSONObject, String runID) {

		super(_getBuildDataSource(jenkinsJSONObject, runID));

		if (runID == null) {
			runID = _DEFAULT_RUN_ID;
		}

		_runID = runID;

		if (!jenkinsJSONObject.has(runID)) {
			jenkinsJSONObject.put(runID, this);
		}

		String buildURL;

		if (has("build_url")) {
			buildURL = getString("build_url");
		}
		else {
			if (!buildParameters.containsKey("BUILD_URL")) {
				throw new RuntimeException("Please set BUILD_URL");
			}

			buildURL = buildParameters.get("BUILD_URL");
		}

		Matcher matcher = _buildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL " + buildURL);
		}

		if (!has("build_number")) {
			put("build_number", Integer.valueOf(matcher.group("buildNumber")));
		}

		if (!has("build_url")) {
			put("build_url", buildURL);
		}

		if (!has("cohort_name")) {
			put("cohort_name", matcher.group("cohortName"));
		}

		if (!has("hostname")) {
			put("hostname", JenkinsResultsParserUtil.getHostName("default"));
		}

		if (!has("jenkins_github_url")) {
			String jenkinsGithubURL = buildParameters.getOrDefault(
				"JENKINS_GITHUB_URL", _DEFAULT_JENKINS_GITHUB_URL);

			put("jenkins_github_url", jenkinsGithubURL);
		}

		if (!has("job_name")) {
			put("job_name", matcher.group("jobName"));
		}

		if (!has("master_hostname")) {
			put("master_hostname", matcher.group("masterHostname"));
		}

		if (!has("workspace_dir")) {
			put("workspace_dir", _getWorkspaceDir(buildParameters));
		}
	}

	private static String _getBuildDataSource(
		JenkinsJSONObject jenkinsJSONObject, String runID) {

		if (runID == null) {
			return "{}";
		}

		JSONObject jsonObject = jenkinsJSONObject.optJSONObject(runID);

		if (jsonObject != null) {
			return jsonObject.toString();
		}

		return "{}";
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

	private static final Pattern _buildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https?://(?<masterHostname>(?<cohortName>test-\\d+)-\\d+)",
			"(\\.liferay\\.com)?/job/(?<jobName>[^/]+)/(.*/)?",
			"(?<buildNumber>\\d+)/?"));

	private final String _runID;

}