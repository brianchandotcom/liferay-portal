/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalTestSuiteUpstreamControllerBuildRunner
	<S extends PortalTestSuiteUpstreamControllerBuildData>
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
		invokeTestSuiteBuilds();
	}

	@Override
	public void tearDown() {
	}

	protected PortalTestSuiteUpstreamControllerBuildRunner(S buildData) {
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
				null, 1, 24, 2);

		S buildData = getBuildData();

		return JenkinsResultsParserUtil.combine(
			mostAvailableMasterURL, "/job/test-portal-testsuite-upstream(",
			buildData.getPortalUpstreamBranchName(), ")");
	}

	protected String getTestPortalBuildProfile(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.test.portal.build.profile", testSuite);
	}

	protected String getTestraySlackChannels(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.slack.channels", testSuite);
	}

	protected String getTestraySlackIconEmoji(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.slack.icon.emoji", testSuite);
	}

	protected String getTestraySlackUsername(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.slack.username", testSuite);
	}

	protected void invokeTestSuiteBuilds() {
		List<String> testSuiteNames = _getSelectedTestSuiteNames();

		if (testSuiteNames.isEmpty()) {
			System.out.println("There are no test suites to run at this time.");

			keepJenkinsBuild(false);

			return;
		}

		String jenkinsAuthenticationToken = null;

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

		String portalBranchSHA = buildData.getPortalBranchSHA();

		for (String testSuiteName : testSuiteNames) {
			JSONObject previousBuildJSONObject =
				_getPreviousTestSuiteBuildJSONObject(testSuiteName);

			if ((previousBuildJSONObject != null) &&
				_previousBuildHasCurrentSHA(
					previousBuildJSONObject, portalBranchSHA)) {

				System.out.println(
					testSuiteName + " was invoked on this SHA already: " +
						portalBranchSHA);

				continue;
			}

			String jobURL = getJobURL();

			StringBuilder sb = new StringBuilder();

			sb.append(jobURL);
			sb.append("/buildWithParameters?");
			sb.append("token=");
			sb.append(jenkinsAuthenticationToken);

			Map<String, String> invocationParameters = new HashMap<>();

			invocationParameters.put("CI_TEST_SUITE", testSuiteName);
			invocationParameters.put(
				"JENKINS_GITHUB_BRANCH_NAME",
				buildData.getJenkinsGitHubBranchName());
			invocationParameters.put(
				"JENKINS_GITHUB_BRANCH_USERNAME",
				buildData.getJenkinsGitHubUsername());
			invocationParameters.put("PORTAL_GIT_COMMIT", portalBranchSHA);
			invocationParameters.put(
				"PORTAL_GITHUB_URL", buildData.getPortalGitHubURL());
			invocationParameters.put(
				"TEST_PORTAL_BUILD_PROFILE",
				getTestPortalBuildProfile(testSuiteName));

			String testrayProjectName = _getTestrayProjectName(testSuiteName);

			if (testrayProjectName != null) {
				String testrayRoutineName = JenkinsResultsParserUtil.combine(
					"[", buildData.getPortalUpstreamBranchName(), "] ci:test:",
					testSuiteName);

				String testrayBuildName = JenkinsResultsParserUtil.combine(
					testrayRoutineName, " - ",
					String.valueOf(buildData.getBuildNumber()), " - ",
					JenkinsResultsParserUtil.toDateString(
						new Date(buildData.getStartTime()),
						"yyyy-MM-dd[HH:mm:ss]", "America/Los_Angeles"));

				if (_getTestrayRoutineName(testSuiteName) != null) {
					testrayRoutineName = _getTestrayRoutineName(testSuiteName);
				}

				invocationParameters.put(
					"TESTRAY_BUILD_NAME", testrayBuildName);
				invocationParameters.put(
					"TESTRAY_PROJECT_NAME", testrayProjectName);
				invocationParameters.put(
					"TESTRAY_ROUTINE_NAME", testrayRoutineName);
			}

			invocationParameters.put(
				"TESTRAY_SLACK_CHANNELS",
				getTestraySlackChannels(testSuiteName));
			invocationParameters.put(
				"TESTRAY_SLACK_ICON_EMOJI",
				getTestraySlackIconEmoji(testSuiteName));
			invocationParameters.put(
				"TESTRAY_SLACK_USERNAME",
				getTestraySlackUsername(testSuiteName));

			invocationParameters.putAll(buildData.getBuildParameters());

			for (Map.Entry<String, String> invocationParameter :
					invocationParameters.entrySet()) {

				String invocationParameterValue =
					invocationParameter.getValue();

				if (JenkinsResultsParserUtil.isNullOrEmpty(
						invocationParameterValue)) {

					continue;
				}

				sb.append("&");
				sb.append(invocationParameter.getKey());
				sb.append("=");
				sb.append(invocationParameterValue);
			}

			try {
				JenkinsResultsParserUtil.toString(sb.toString());

				System.out.println(
					"Job for '" + testSuiteName + "' was invoked at " + jobURL);

				_invokedTestSuiteNames.add(testSuiteName);
			}
			catch (IOException ioException) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Unable to invoke a new build for test suite, '",
						testSuiteName, "'"));

				ioException.printStackTrace();
			}
		}

		boolean keepLogs = true;

		if (_invokedTestSuiteNames.isEmpty()) {
			keepLogs = false;
		}

		keepJenkinsBuild(keepLogs);

		StringBuilder sb = new StringBuilder();

		sb.append(JenkinsResultsParserUtil.join(", ", _invokedTestSuiteNames));
		sb.append(",");
		sb.append(" <strong>GIT ID</strong> - ");
		sb.append("<a href=\"https://github.com/");
		sb.append(buildData.getPortalGitHubUsername());
		sb.append("/");
		sb.append(buildData.getPortalGitHubRepositoryName());
		sb.append("/commit/");

		sb.append(portalBranchSHA);

		sb.append("\">");

		sb.append(_getPortalBranchAbbreviatedSHA());

		sb.append("</a>");

		buildData.setBuildDescription(sb.toString());

		updateBuildDescription();
	}

	private List<Build> _getBuildHistory() {
		S buildData = getBuildData();

		Build build = BuildFactory.newBuild(buildData.getBuildURL(), null);

		Job job = JobFactory.newJob(buildData.getJobName());

		return job.getBuildHistory(build.getJenkinsMaster());
	}

	private List<String> _getBuildTestSuiteNames(Build build) {
		String buildDescription = build.getBuildDescription();

		if ((buildDescription == null) || buildDescription.isEmpty()) {
			return Collections.emptyList();
		}

		return Arrays.asList(buildDescription.split("\\s*,\\s*"));
	}

	private Map<String, Long> _getCandidateTestSuiteStaleDurations() {
		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		S buildData = getBuildData();

		String upstreamBranchName = buildData.getPortalUpstreamBranchName();

		Map<String, Long> candidateTestSuiteStaleDurations =
			new LinkedHashMap<>();

		for (String testSuiteName : _getTestSuiteNames()) {
			String suiteStaleDuration = buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.stale.duration[",
					upstreamBranchName, "][", testSuiteName, "]"));

			if (suiteStaleDuration == null) {
				continue;
			}

			candidateTestSuiteStaleDurations.put(
				testSuiteName, Long.parseLong(suiteStaleDuration) * 60 * 1000);
		}

		return candidateTestSuiteStaleDurations;
	}

	private Map<String, Long> _getLatestTestSuiteStartTimes() {
		List<Build> builds = _getBuildHistory();

		BuildData buildData = getBuildData();

		Build currentBuild = BuildFactory.newBuild(
			buildData.getBuildURL(), null);

		builds.remove(currentBuild);

		Map<String, Long> latestTestSuiteStartTimes = new LinkedHashMap<>();

		for (String testSuiteName : _getTestSuiteNames()) {
			for (Build build : builds) {
				List<String> buildTestSuiteNames = _getBuildTestSuiteNames(
					build);

				if (buildTestSuiteNames.contains(testSuiteName)) {
					latestTestSuiteStartTimes.put(
						testSuiteName, build.getStartTime());

					break;
				}
			}
		}

		return latestTestSuiteStartTimes;
	}

	private String _getPortalBranchAbbreviatedSHA() {
		S buildData = getBuildData();

		String portalBranchSHA = buildData.getPortalBranchSHA();

		return portalBranchSHA.substring(0, 7);
	}

	private JSONObject _getPreviousTestSuiteBuildJSONObject(
		String testSuiteName) {

		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			if (description.contains("EXPIRE") ||
				description.contains("SKIPPED")) {

				continue;
			}

			if (description.contains(testSuiteName)) {
				return previousBuildJSONObject;
			}
		}

		return null;
	}

	private List<String> _getSelectedTestSuiteNames() {
		if (_selectedTestSuiteNames != null) {
			return _selectedTestSuiteNames;
		}

		_selectedTestSuiteNames = new ArrayList<>();

		Map<String, Long> candidateTestSuiteStaleDurations =
			_getCandidateTestSuiteStaleDurations();

		Map<String, Long> latestTestSuiteStartTimes =
			_getLatestTestSuiteStartTimes();

		S buildData = getBuildData();

		Long startTime = buildData.getStartTime();

		for (Map.Entry<String, Long> entry :
				candidateTestSuiteStaleDurations.entrySet()) {

			String testSuiteName = entry.getKey();

			if (!latestTestSuiteStartTimes.containsKey(testSuiteName)) {
				_selectedTestSuiteNames.add(testSuiteName);

				continue;
			}

			Long testSuiteIdleDuration =
				startTime - latestTestSuiteStartTimes.get(testSuiteName);

			if (testSuiteIdleDuration > entry.getValue()) {
				_selectedTestSuiteNames.add(testSuiteName);
			}
		}

		return _selectedTestSuiteNames;
	}

	private String _getTestrayProjectName(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.project.name", testSuite);
	}

	private String _getTestrayRoutineName(String testSuite) {
		String testrayRoutineName = _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.routine.name", testSuite);

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {
			testrayRoutineName = _getTestSuiteBuildProperty(
				"portal.testsuite.upstream.testray.build.type", testSuite);
		}

		return testrayRoutineName;
	}

	private String _getTestSuiteBuildProperty(
		String propertyName, String testSuite) {

		S buildData = getBuildData();

		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(), propertyName,
				buildData.getPortalUpstreamBranchName(), testSuite);
		}
		catch (IOException ioException) {
			return null;
		}
	}

	private List<String> _getTestSuiteNames() {
		S buildData = getBuildData();

		try {
			return JenkinsResultsParserUtil.getBuildPropertyAsList(
				true,
				JenkinsResultsParserUtil.combine(
					"portal.testsuite.upstream.suites[",
					buildData.getPortalUpstreamBranchName(), "]"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private boolean _previousBuildHasCurrentSHA(
		JSONObject previousBuildJSONObject, String portalBranchSHA) {

		if (previousBuildJSONObject == null) {
			return false;
		}

		String description = previousBuildJSONObject.optString(
			"description", "");

		Matcher matcher = _portalBranchSHAPattern.matcher(description);

		if (!matcher.find()) {
			return false;
		}

		String previousPortalBranchSHA = matcher.group("branchSHA");

		if (portalBranchSHA.equals(previousPortalBranchSHA)) {
			return true;
		}

		return false;
	}

	private static final Pattern _portalBranchSHAPattern = Pattern.compile(
		"<strong>GIT ID</strong> - <a href=\"https://github.com/[^/]+/[^/]+/" +
			"commit/(?<branchSHA>[0-9a-f]{40})\">[0-9a-f]{7}</a>");

	private final List<String> _invokedTestSuiteNames = new ArrayList<>();
	private List<String> _selectedTestSuiteNames;
	private Workspace _workspace;

}