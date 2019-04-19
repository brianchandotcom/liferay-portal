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
	<S extends PortalTestSuiteUpstreamControllerBuildData>
		extends BaseBuildRunner<S, Workspace> {

	@Override
	public void run() {
		retirePreviousBuilds();

		if (_previousBuildHasCurrentSHA()) {
			S buildData = getBuildData();

			buildData.setBuildDescription(
				JenkinsResultsParserUtil.combine(
					"<strong>SKIPPED</strong> - <a href=\"https://github.com/",
					"liferay/liferay-portal/commit/",
					buildData.getPortalBranchSHA(), "\">",
					_getPortalBranchAbbreviatedSHA(), "</a> was already ran"));

			super.updateBuildDescription();

			return;
		}

		if (_previousBuildHasExistingInvocation()) {
			BuildData buildData = getBuildData();

			buildData.setBuildDescription(
				"<strong>SKIPPED</strong> - Job was already invoked");

			super.updateBuildDescription();

			return;
		}

		if (_previousBuildHasRunningInvocation()) {
			BuildData buildData = getBuildData();

			buildData.setBuildDescription(
				"<strong>SKIPPED</strong> - Job is already running");

			super.updateBuildDescription();

			return;
		}

		updateBuildDescription();

		keepJenkinsBuild(true);

		_invokeJob();
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
		S buildData = getBuildData();

		StringBuilder sb = new StringBuilder();

		sb.append("<strong>IN QUEUE</strong> - <a href=\"");
		sb.append(JenkinsResultsParserUtil.getRemoteURL(_getInvocationURL()));
		sb.append("\">Invocation URL</a>");

		sb.append("<ul><li><strong>Git ID:</strong> ");
		sb.append("<a href=\"https://github.com/");
		sb.append(buildData.getPortalGitHubUsername());
		sb.append("/");
		sb.append(buildData.getPortalGitHubRepositoryName());
		sb.append("/commit/");
		sb.append(buildData.getPortalBranchSHA());
		sb.append("\">");
		sb.append(_getPortalBranchAbbreviatedSHA());
		sb.append("</a></li>");

		String portalGitHubCompareURL = _getPortalGitHubCompareURL();

		if (portalGitHubCompareURL != null) {
			sb.append("<li><strong>Git Compare:</strong> <a href=\"");
			sb.append(_getPortalGitHubCompareURL());
			sb.append("\">??? commits</a></li>");
		}

		sb.append("</ul>");

		buildData.setBuildDescription(sb.toString());

		super.updateBuildDescription();
	}

	private String _getInvocationCohortName() {
		String invocationCorhortName = System.getenv("INVOCATION_COHORT_NAME");

		if ((invocationCorhortName != null) &&
			!invocationCorhortName.isEmpty()) {

			return invocationCorhortName;
		}

		BuildData buildData = getBuildData();

		return buildData.getCohortName();
	}

	private String _getInvocationURL() {
		if (_invocationURL != null) {
			return _invocationURL;
		}

		String baseInvocationURL =
			JenkinsResultsParserUtil.getMostAvailableMasterURL(
				JenkinsResultsParserUtil.combine(
					"http://" + _getInvocationCohortName() + ".liferay.com"),
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

	private String _getPortalGitHubCompareURL() {
		S buildData = getBuildData();

		return buildData.getPortalGitHubCompareURL(
			_getPreviousPortalBranchSHA());
	}

	private String _getPreviousPortalBranchSHA() {
		S buildData = getBuildData();

		String currentPortalBranchSHA = buildData.getPortalBranchSHA();

		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			Matcher matcher = _portalBranchSHAPattern.matcher(description);

			if (!matcher.find()) {
				continue;
			}

			String previousPortalBranchSHA = matcher.group("branchSHA");

			if (currentPortalBranchSHA.equals(previousPortalBranchSHA)) {
				continue;
			}

			return previousPortalBranchSHA;
		}

		return null;
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

		S buildData = getBuildData();

		sb.append("&CI_TEST_SUITE=");
		sb.append(buildData.getTestSuiteName());
		sb.append("&CONTROLLER_BUILD_URL=");
		sb.append(buildData.getBuildURL());
		sb.append("&JENKINS_GITHUB_BRANCH_NAME=");
		sb.append(buildData.getJenkinsGitHubBranchName());
		sb.append("&JENKINS_GITHUB_BRANCH_USERNAME=");
		sb.append(buildData.getJenkinsGitHubUsername());
		sb.append("&PORTAL_GIT_COMMIT=");
		sb.append(buildData.getPortalBranchSHA());

		String portalGitHubCompareURL = _getPortalGitHubCompareURL();

		if (portalGitHubCompareURL != null) {
			sb.append("&PORTAL_GITHUB_COMPARE_URL=");
			sb.append(portalGitHubCompareURL);
		}

		sb.append("&PORTAL_GITHUB_URL=");
		sb.append(buildData.getPortalGitHubURL());

		String testrayProjectName = buildData.getTestrayProjectName();

		if (testrayProjectName != null) {
			sb.append("&TESTRAY_BUILD_NAME=");
			sb.append(buildData.getTestrayBuildName());
			sb.append("&TESTRAY_BUILD_TYPE=");
			sb.append(buildData.getTestrayBuildType());
			sb.append("&TESTRAY_PROJECT_NAME=");
			sb.append(testrayProjectName);
		}

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

	private boolean _previousBuildHasRunningInvocation() {
		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			if (!description.contains("IN PROGRESS")) {
				continue;
			}

			Matcher buildURLMatcher = _buildURLPattern.matcher(description);

			if (!buildURLMatcher.find()) {
				continue;
			}

			String buildURL = buildURLMatcher.group("buildURL");

			try {
				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						buildURL + "/api/json?tree=result"));

				Object result = jsonObject.get("result");

				if (result.equals(JSONObject.NULL)) {
					return true;
				}

				JSONObject injectedEnvVarsJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							previousBuildJSONObject.getString("url") +
								"/injectedEnvVars/api/json"));

				JSONObject envMapJSONObject =
					injectedEnvVarsJSONObject.getJSONObject("envMap");

				StringBuilder sb = new StringBuilder();

				sb.append("<strong style=\"color: red\">FAILURE</strong> - ");
				sb.append(buildURLMatcher.group());

				Matcher portalBranchSHAMatcher =
					_portalBranchSHAPattern.matcher(description);
				Matcher portalGitHubCompareURLMatcher =
					_portalGitHubCompareURLPattern.matcher(description);

				if (portalBranchSHAMatcher.find() ||
					portalGitHubCompareURLMatcher.find()) {

					sb.append("<ul>");

					if (portalBranchSHAMatcher.find()) {
						sb.append("<li>");
						sb.append(portalBranchSHAMatcher.group());
						sb.append("</li>");
					}

					if (portalGitHubCompareURLMatcher.find()) {
						sb.append("<li>");
						sb.append(portalGitHubCompareURLMatcher.group());
						sb.append("</li>");
					}

					sb.append("</ul>");
				}

				JenkinsResultsParserUtil.updateBuildDescription(
					sb.toString(),
					Integer.valueOf(envMapJSONObject.getString("BUILD_NUMBER")),
					envMapJSONObject.getString("JOB_NAME"),
					envMapJSONObject.getString("HOSTNAME"));
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		return false;
	}

	private static final Pattern _buildURLPattern = Pattern.compile(
		"<a href=\"(?<buildURL>[^\"]+)\">Build URL</a>");
	private static final Pattern _portalBranchSHAPattern = Pattern.compile(
		"<strong>Git ID:</strong> <a href=\"https://github.com/[^/]+/[^/]+/" +
			"commit/(?<branchSHA>[0-9a-f]{40})\">[0-9a-f]{7}</a>");
	private static final Pattern _portalGitHubCompareURLPattern =
		Pattern.compile(
			"<strong>Git Compare:</strong> <a href=\"[^\"]+\">[^<]+</a>");

	private String _invocationURL;

}