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

/**
 * @author Michael Hashimoto
 */
public class PortalTestSuiteUpstreamControllerBuildRunner
	<S extends PortalTestSuiteUpstreamControllerBuildData>
		extends BaseBuildRunner<S, Workspace> {

	@Override
	public void run() {
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

	protected String getInvocationURL() {
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

	@Override
	protected void initWorkspace() {
		setWorkspace(WorkspaceFactory.newSimpleWorkspace());
	}

	@Override
	protected void updateBuildDescription() {
		S buildData = getBuildData();

		StringBuilder sb = new StringBuilder();

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

	private void _invokeJob() {
		StringBuilder sb = new StringBuilder();

		sb.append(getInvocationURL());
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
		sb.append("&JENKINS_GITHUB_BRANCH_NAME=");
		sb.append(buildData.getJenkinsGitHubBranchName());
		sb.append("&JENKINS_GITHUB_BRANCH_USERNAME=");
		sb.append(buildData.getJenkinsGitHubUsername());
		sb.append("&PORTAL_GIT_COMMIT=");
		sb.append(buildData.getPortalBranchSHA());
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

	private String _invocationURL;

}