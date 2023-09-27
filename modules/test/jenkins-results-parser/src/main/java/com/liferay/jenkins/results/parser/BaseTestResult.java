/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 * @author Yi-Chen Tsai
 */
public abstract class BaseTestResult implements TestResult {

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public TestClass getTestClass() {
		TestClassResult testClassResult = getTestClassResult();

		if (testClassResult == null) {
			return null;
		}

		return testClassResult.getTestClass();
	}

	@Override
	public TestClassResult getTestClassResult() {
		List<TestClassResult> testClassResults = _build.getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
			return null;
		}

		String testClassName = getClassName();

		for (TestClassResult testClassResult : _build.getTestClassResults()) {
			if (!testClassName.equals(testClassResult.getClassName())) {
				continue;
			}

			_testClassResult = testClassResult;

			break;
		}

		return _testClassResult;
	}

	@Override
	public TestHistory getTestHistory() {
		TestClass testClass = getTestClass();

		if (testClass == null) {
			return null;
		}

		return testClass.getTestHistory();
	}

	@Override
	public boolean isFailing() {
		String status = getStatus();

		Build build = getBuild();

		if (status.equals("PASSED") && build.isFailing()) {
			JSONObject testReportJSONObject = build.getTestReportJSONObject(
				false);

			int failCount = testReportJSONObject.getInt("failCount");
			int passCount = testReportJSONObject.getInt("passCount");

			if ((failCount == 0) && (passCount == 1)) {
				return true;
			}
		}

		if (status.equals("FIXED") || status.equals("PASSED") ||
			status.equals("SKIPPED")) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isUniqueFailure() {
		if (!isFailing()) {
			return false;
		}

		Build build = getBuild();

		if (!build.isCompareToUpstream()) {
			return true;
		}

		String batchName = build.getBatchName(build.getJobVariant());

		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		for (String upstreamFailure :
				UpstreamFailureUtil.getUpstreamJobFailures(
					"test", topLevelBuild)) {

			String testFailure = JenkinsResultsParserUtil.combine(
				getDisplayName(), ",", batchName);

			if (upstreamFailure.equals(testFailure)) {
				return false;
			}
		}

		return true;
	}

	protected BaseTestResult(Build build) {
		if (build == null) {
			throw new IllegalArgumentException("Build is null");
		}

		_build = build;
	}

	protected String getAxisNumber() {
		Build build = getBuild();

		if (build instanceof AxisBuild) {
			AxisBuild axisBuild = (AxisBuild)build;

			return axisBuild.getAxisNumber();
		}
		else if (build instanceof DownstreamBuild) {
			DownstreamBuild downstreamBuild = (DownstreamBuild)build;

			return downstreamBuild.getAxisVariable();
		}

		return "INVALID_AXIS_NUMBER";
	}

	protected String getConsoleOutputURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(getTestrayLogsURL());
		sb.append("/jenkins-console.txt.gz");

		return sb.toString();
	}

	protected String getLiferayLogURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(getTestrayLogsURL());
		sb.append("/");
		sb.append(name.replace('#', '_'));
		sb.append("/liferay-log.txt.gz");

		return sb.toString();
	}

	protected String getTestrayLogsURL() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		String logBaseURL = null;

		if (buildProperties.containsKey("log.base.url")) {
			logBaseURL = buildProperties.getProperty("log.base.url");
		}

		if (logBaseURL == null) {
			logBaseURL = _URL_BASE_LOGS_DEFAULT;
		}

		Build build = getBuild();

		Map<String, String> startPropertiesTempMap =
			build.getStartPropertiesTempMap();

		return JenkinsResultsParserUtil.combine(
			logBaseURL, "/",
			startPropertiesTempMap.get("TOP_LEVEL_MASTER_HOSTNAME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_START_TIME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_JOB_NAME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_BUILD_NUMBER"), "/",
			build.getJobVariant(), "/", getAxisNumber());
	}

	protected boolean hasLiferayLog() {
		String liferayLog = null;

		try {
			liferayLog = JenkinsResultsParserUtil.toString(
				getLiferayLogURL(), false, 0, 0, 0);
		}
		catch (IOException ioException) {
			return false;
		}

		return !liferayLog.isEmpty();
	}

	private static final String _URL_BASE_LOGS_DEFAULT =
		"https://testray.liferay.com/reports/production/logs";

	private final Build _build;
	private TestClassResult _testClassResult;

}