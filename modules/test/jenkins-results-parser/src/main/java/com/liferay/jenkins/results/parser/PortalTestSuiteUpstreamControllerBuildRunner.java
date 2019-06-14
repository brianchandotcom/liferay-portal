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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PortalTestSuiteUpstreamControllerBuildRunner
	<S extends PortalTestSuiteUpstreamControllerBuildData>
		extends BaseBuildRunner<S, Workspace> {

	@Override
	public void run() {
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

		buildData.setBuildDescription(String.join(",", _getTestSuites()));

		super.updateBuildDescription();
	}

	private List<Build> _getBuildHistory() {
		S buildData = getBuildData();

		Build build = BuildFactory.newBuild(buildData.getBuildURL(), null);

		Job job = JobFactory.newJob(buildData.getJobName());

		return job.getBuildHistory(build.getJenkinsMaster());
	}

	private Map<String, Long> _getCandidateTestSuiteStaleDurations() {
		S buildData = getBuildData();

		String upstreamBranchName = buildData.getPortalUpstreamBranchName();

		Properties buildProperties;
		List<String> testSuites;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();

			testSuites = JenkinsResultsParserUtil.getBuildPropertyAsList(
				true,
				"portal.testsuite.upstream.suites[" + upstreamBranchName + "]");
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		Map<String, Long> candidateTestSuiteStaleDurations =
			new LinkedHashMap();

		for (String testSuite : testSuites) {
			String suiteStaleDuration = buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.stale.duration[",
					upstreamBranchName, "][", testSuite, "]"));

			if (suiteStaleDuration == null) {
				continue;
			}

			candidateTestSuiteStaleDurations.put(
				testSuite, Long.parseLong(suiteStaleDuration) * 60 * 1000);
		}

		return candidateTestSuiteStaleDurations;
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

	private Map<String, Long> _getLatestTestSuiteStartTimes() {
		List<Build> builds = _getBuildHistory();

		BuildData buildData = getBuildData();

		Build currentBuild = BuildFactory.newBuild(
			buildData.getBuildURL(), null);

		Map<String, Long> latestTestSuiteStartTimes = new LinkedHashMap();

		for (Build build : builds) {
			if (build == currentBuild) {
				continue;
			}

			String buildDescription = build.getBuildDescription();

			if (buildDescription == null) {
				continue;
			}

			Long buildStartTime = build.getStartTime();

			for (String testSuite : buildDescription.split(",")) {
				testSuite = testSuite.trim();

				if (!latestTestSuiteStartTimes.containsKey(testSuite)) {
					latestTestSuiteStartTimes.put(testSuite, buildStartTime);
				}
			}
		}

		return latestTestSuiteStartTimes;
	}

	private String _getTestrayProjectName(String testSuite) {
		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			S buildData = getBuildData();

			return buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.testray.project.name[",
					buildData.getPortalUpstreamBranchName(), "][", testSuite,
					"]"));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private List<String> _getTestSuites() {
		if (_testSuites != null) {
			return _testSuites;
		}

		_testSuites = new ArrayList();

		S buildData = getBuildData();

		Map<String, Long> candidateTestSuiteStaleDurations =
			_getCandidateTestSuiteStaleDurations();

		Map<String, Long> latestTestSuiteStartTimes =
			_getLatestTestSuiteStartTimes();

		Long startTime = buildData.getStartTime();

		for (Map.Entry<String, Long> entry :
				candidateTestSuiteStaleDurations.entrySet()) {

			String testSuite = entry.getKey();

			if (!latestTestSuiteStartTimes.containsKey(testSuite)) {
				_testSuites.add(testSuite);

				continue;
			}

			Long testSuiteStaleDuration = entry.getValue();

			Long testSuiteduration =
				startTime - latestTestSuiteStartTimes.get(testSuite);

			if (testSuiteduration > testSuiteStaleDuration) {
				_testSuites.add(testSuite);
			}
		}

		return _testSuites;
	}

	private void _invokeJob() {
		List<String> testSuites = _getTestSuites();

		if ((testSuites == null) || testSuites.isEmpty()) {
			System.out.println(
				"There are no test suites to be run at this time.");

			return;
		}

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
	private List<String> _invokedTestSuites = new ArrayList();
	private List<String> _testSuites;

}