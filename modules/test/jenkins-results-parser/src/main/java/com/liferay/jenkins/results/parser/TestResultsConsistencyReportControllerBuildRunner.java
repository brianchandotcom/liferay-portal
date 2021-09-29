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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Yi-Chen Tsai
 */
public class TestResultsConsistencyReportControllerBuildRunner
	<S extends BaseBuildData>
		extends BaseBuildRunner<S> {

	@Override
	public Workspace getWorkspace() {
		if (_workspace != null) {
			return _workspace;
		}

		_workspace = WorkspaceFactory.newWorkspace();

		return _workspace;
	}

	@Override
	public void run() {
		keepJenkinsBuild(true);

		invokeTestSuiteBuilds();
	}

	@Override
	public void tearDown() {
	}

	protected TestResultsConsistencyReportControllerBuildRunner(S buildData) {
		super(buildData);
	}

	protected String getInvocationCohortName() {
		String invocationCohortName = System.getenv("INVOCATION_COHORT_NAME");

		if ((invocationCohortName != null) && !invocationCohortName.isEmpty()) {
			return invocationCohortName;
		}

		BuildData buildData = getBuildData();

		return buildData.getCohortName();
	}

	protected String getJobURL() {
		String mostAvailableMasterURL =
			JenkinsResultsParserUtil.getMostAvailableMasterURL(
				JenkinsResultsParserUtil.combine(
					"http://" + getInvocationCohortName() + ".liferay.com"),
				1);

		return JenkinsResultsParserUtil.combine(
			mostAvailableMasterURL, "/job/test-results-consistency-report");
	}

	protected void invokeTestSuiteBuilds() {
		List<Pair<String, String>> selectedTestSuiteBranchNamePairs =
			_getSelectedTestSuiteBranchNamePairs();

		if (selectedTestSuiteBranchNamePairs.isEmpty()) {
			System.out.println("There are no test suites to run at this time.");

			return;
		}

		String jenkinsAuthenticationToken;

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			jenkinsAuthenticationToken = buildProperties.getProperty(
				"jenkins.authentication.token");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		S buildData = getBuildData();

		List<String> invokedTestSuiteBranchNamePairDescriptions =
			new ArrayList<>();

		for (Pair<String, String> testSuiteBranchNamePair :
				selectedTestSuiteBranchNamePairs) {

			String jobURL = getJobURL();

			StringBuilder sb = new StringBuilder();

			sb.append(jobURL);
			sb.append("/buildWithParameters?");
			sb.append("token=");
			sb.append(jenkinsAuthenticationToken);

			Map<String, String> invocationParameters = new HashMap<>();

			String testSuite = testSuiteBranchNamePair.getKey();

			String branchName = testSuiteBranchNamePair.getValue();

			invocationParameters.put("CI_TEST_SUITE", testSuite);
			invocationParameters.put(
				"JENKINS_GITHUB_BRANCH_NAME",
				buildData.getJenkinsGitHubBranchName());
			invocationParameters.put(
				"JENKINS_GITHUB_BRANCH_USERNAME",
				buildData.getJenkinsGitHubUsername());
			invocationParameters.put("PORTAL_UPSTREAM_BRANCH_NAME", branchName);

			invocationParameters.putAll(buildData.getBuildParameters());

			for (Map.Entry<String, String> invocationParameter :
					invocationParameters.entrySet()) {

				if (invocationParameter.getValue() == null) {
					continue;
				}

				sb.append("&");
				sb.append(invocationParameter.getKey());
				sb.append("=");
				sb.append(invocationParameter.getValue());
			}

			String testSuiteBranchNamePairDescription =
				JenkinsResultsParserUtil.combine(
					testSuite, "(", branchName, ")");

			try {
				JenkinsResultsParserUtil.toString(sb.toString());

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Job for '", testSuiteBranchNamePairDescription,
						"' was invoked at ", jobURL));

				invokedTestSuiteBranchNamePairDescriptions.add(
					testSuiteBranchNamePairDescription);
			}
			catch (IOException ioException) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Unable to invoke a new build for test suite, '",
						testSuiteBranchNamePairDescription, "'"));

				ioException.printStackTrace();
			}
		}

		buildData.setBuildDescription(
			JenkinsResultsParserUtil.join(
				", ", invokedTestSuiteBranchNamePairDescriptions));

		updateBuildDescription();
	}

	private List<Build> _getBuildHistory() {
		S buildData = getBuildData();

		Build build = BuildFactory.newBuild(buildData.getBuildURL(), null);

		Job job = JobFactory.newJob(buildData.getJobName());

		return job.getBuildHistory(build.getJenkinsMaster());
	}

	private List<Pair<String, String>> _getBuildTestSuiteBranchNamePairs(
		Build build) {

		String buildDescription = build.getBuildDescription();

		if ((buildDescription == null) || buildDescription.isEmpty()) {
			return Collections.emptyList();
		}

		List<Pair<String, String>> buildTestSuiteBranchNamePairs =
			new ArrayList<>();

		for (String buildTestSuiteBranchNamePairDescription :
				buildDescription.split("\\s*,\\s*")) {

			Matcher matcher = _testSuiteBranchNameDescriptionPattern.matcher(
				buildTestSuiteBranchNamePairDescription);

			if (matcher.matches()) {
				buildTestSuiteBranchNamePairs.add(
					new ImmutablePair<>(
						matcher.group("testSuite"),
						matcher.group("branchName")));
			}
		}

		return buildTestSuiteBranchNamePairs;
	}

	private Map<Pair<String, String>, Long>
		_getCandidateTestSuiteStaleDurations() {

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		Map<Pair<String, String>, Long> candidateTestSuiteStaleDurations =
			new LinkedHashMap<>();

		for (Pair<String, String> testSuiteBranchNamePair :
				_getTestSuiteBranchNamePairs()) {

			String suiteStaleDuration = buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.stale.duration[",
					testSuiteBranchNamePair.getValue(), "][",
					testSuiteBranchNamePair.getKey(), "]"));

			if (suiteStaleDuration == null) {
				continue;
			}

			candidateTestSuiteStaleDurations.put(
				testSuiteBranchNamePair,
				Long.parseLong(suiteStaleDuration) * 60 * 1000);
		}

		return candidateTestSuiteStaleDurations;
	}

	private Map<Pair<String, String>, Long> _getLatestTestSuiteStartTimes() {
		List<Build> builds = _getBuildHistory();

		BuildData buildData = getBuildData();

		Build currentBuild = BuildFactory.newBuild(
			buildData.getBuildURL(), null);

		builds.remove(currentBuild);

		Map<Pair<String, String>, Long> latestTestSuiteStartTimes =
			new LinkedHashMap<>();

		for (Pair<String, String> testSuiteBranchNamePair :
				_getTestSuiteBranchNamePairs()) {

			for (Build build : builds) {
				List<Pair<String, String>> buildTestSuiteBranchNamePairs =
					_getBuildTestSuiteBranchNamePairs(build);

				if (buildTestSuiteBranchNamePairs.contains(
						testSuiteBranchNamePair)) {

					latestTestSuiteStartTimes.put(
						testSuiteBranchNamePair, build.getStartTime());

					break;
				}
			}
		}

		return latestTestSuiteStartTimes;
	}

	private List<Pair<String, String>> _getSelectedTestSuiteBranchNamePairs() {
		if (_selectedTestSuiteBranchNamePairs != null) {
			return _selectedTestSuiteBranchNamePairs;
		}

		_selectedTestSuiteBranchNamePairs = new ArrayList<>();

		Map<Pair<String, String>, Long> candidateTestSuiteStaleDurations =
			_getCandidateTestSuiteStaleDurations();

		Map<Pair<String, String>, Long> latestTestSuiteStartTimes =
			_getLatestTestSuiteStartTimes();

		S buildData = getBuildData();

		Long startTime = buildData.getStartTime();

		for (Map.Entry<Pair<String, String>, Long> entry :
				candidateTestSuiteStaleDurations.entrySet()) {

			Pair<String, String> testSuiteBranchNamePair = entry.getKey();

			if (!latestTestSuiteStartTimes.containsKey(
					testSuiteBranchNamePair)) {

				_selectedTestSuiteBranchNamePairs.add(testSuiteBranchNamePair);

				continue;
			}

			Long testSuiteIdleDuration =
				startTime -
					latestTestSuiteStartTimes.get(testSuiteBranchNamePair);

			if (testSuiteIdleDuration > entry.getValue()) {
				_selectedTestSuiteBranchNamePairs.add(testSuiteBranchNamePair);
			}
		}

		return _selectedTestSuiteBranchNamePairs;
	}

	private List<Pair<String, String>> _getTestSuiteBranchNamePairs() {
		List<Pair<String, String>> testSuiteBranchNamePairs = new ArrayList<>();

		try {
			List<String> testSuiteNames =
				JenkinsResultsParserUtil.getBuildPropertyAsList(
					true, "test.results.consistency.report.suites");

			for (String testSuiteName : testSuiteNames) {
				List<String> branchNames =
					JenkinsResultsParserUtil.getBuildPropertyAsList(
						true,
						"test.results.consistency.report.branches[" +
							testSuiteName + "]");

				for (String branchName : branchNames) {
					testSuiteBranchNamePairs.add(
						new ImmutablePair<>(testSuiteName, branchName));
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return testSuiteBranchNamePairs;
	}

	private static final Pattern _testSuiteBranchNameDescriptionPattern =
		Pattern.compile("(?<testSuite>[^\\(]*)\\((?<branchName>[^\\)]*)\\)");

	private List<Pair<String, String>> _selectedTestSuiteBranchNamePairs;
	private Workspace _workspace;

}