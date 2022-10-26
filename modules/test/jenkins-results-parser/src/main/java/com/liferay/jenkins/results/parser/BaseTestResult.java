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

import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

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

		if (status.equals("FIXED") || status.equals("PASSED") ||
			status.equals("SKIPPED")) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isUniqueFailure() {
		return !UpstreamFailureUtil.isTestFailingInUpstreamJob(this);
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