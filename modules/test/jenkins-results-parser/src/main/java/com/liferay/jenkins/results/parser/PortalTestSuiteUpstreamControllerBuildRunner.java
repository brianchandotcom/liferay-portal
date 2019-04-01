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

import java.io.IOException;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalTestSuiteUpstreamControllerBuildRunner
	<S extends PortalTopLevelBuildData>
		extends BaseBuildRunner<S, Workspace> {

	@Override
	public void run() {
		if (_previousBuildHasExistingInvocation()) {
			return;
		}

		if (_previousBuildHasCurrentSHA()) {
			return;
		}

		_invokeJob();

		updateBuildDescription();

		keepLogs(true);
	}

	@Override
	public void tearDown() {
	}

	protected PortalTestSuiteUpstreamControllerBuildRunner(S buildData) {
		super(buildData);
	}

	@Override
	protected void initWorkspace() {
		setWorkspace(WorkspaceFactory.newSimpleWorkspace());
	}

	@Override
	protected void updateBuildDescription() {
		BuildData buildData = getBuildData();

		buildData.setBuildDescription(
			JenkinsResultsParserUtil.combine(
				"<strong>GIT ID</strong> - ", _getPortalBranchAbbreviatedSHA(),
				" - <a href=\"", _getInvocationURL(), "\">Invocation URL</a>"));

		super.updateBuildDescription();
	}

	private String _getInvocationURL() {
		if (_invocationURL != null) {
			return _invocationURL;
		}

		String baseInvocationURL =
			JenkinsResultsParserUtil.getMostAvailableMasterURL(
				JenkinsResultsParserUtil.combine(
					"http://" + _INVOCATION_COHORT_NAME + ".liferay.com"),
				1);

		S buildData = getBuildData();

		_invocationURL = JenkinsResultsParserUtil.combine(
			baseInvocationURL, "/job/test-portal-testsuite-upstream(",
			buildData.getPortalUpstreamBranchName(), ")");

		return _invocationURL;
	}

	private String _getPortalBranchAbbreviatedSHA() {
		S buildData = getBuildData();

		String portalBranchSHA = buildData.getPortalBranchSHA();

		return portalBranchSHA.substring(0, 7);
	}

	private String _getTestSuiteName() {
		BuildData buildData = getBuildData();

		String jobName = buildData.getJobName();

		Matcher matcher = _jobNamePattern.matcher(jobName);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid job name " + jobName);
		}

		return matcher.group("testSuiteName");
	}

	private void _invokeJob() {
		StringBuilder sb = new StringBuilder();

		sb.append(_getInvocationURL());
		sb.append("/buildWithParameters?");

		String jenkinsAuthenticationToken;

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			jenkinsAuthenticationToken = buildProperties.getProperty(
				"jenkins.authentication.token");
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		sb.append("token=");
		sb.append(jenkinsAuthenticationToken);

		sb.append("&CI_TEST_SUITE=");
		sb.append(_getTestSuiteName());

		S buildData = getBuildData();

		sb.append("&CONTROLLER_BUILD_URL=");
		sb.append(buildData.getBuildURL());

		String jenkinsGitHubURL = buildData.getBuildParameter(
			"JENKINS_GITHUB_URL");

		if (jenkinsGitHubURL != null) {
			Matcher matcher = _jenkinsGitHubURLPattern.matcher(
				jenkinsGitHubURL);

			if (matcher.find()) {
				sb.append("&JENKINS_GITHUB_BRANCH_NAME=");
				sb.append(matcher.group("branchName"));
				sb.append("&JENKINS_GITHUB_BRANCH_USERNAME=");
				sb.append(matcher.group("username"));
			}
		}

		sb.append("&PORTAL_GIT_COMMIT=");
		sb.append(buildData.getPortalBranchSHA());

		try {
			JenkinsResultsParserUtil.toString(sb.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private boolean _previousBuildHasCurrentSHA() {
		String portalBranchSHA = _getPortalBranchAbbreviatedSHA();

		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			if (description.contains(portalBranchSHA)) {
				return true;
			}
		}

		return false;
	}

	private boolean _previousBuildHasExistingInvocation() {
		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			if (description.contains("Invocation URL")) {
				return true;
			}
		}

		return false;
	}

	private static final String _INVOCATION_COHORT_NAME = "test-5";

	private static final Pattern _jenkinsGitHubURLPattern = Pattern.compile(
		"https://github.com/(?<username>[^/]+)/liferay-jenkins-ee/tree/" +
			"(?<branchName>.+)");
	private static final Pattern _jobNamePattern = Pattern.compile(
		"[^\\(]+\\((?<branchName>[^_]+)_(?<testSuiteName>[^\\)]+)\\)");

	private String _invocationURL;

}