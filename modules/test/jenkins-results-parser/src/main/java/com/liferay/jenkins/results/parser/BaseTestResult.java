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

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 * @author Yi-Chen Tsai
 */
public class BaseTestResult implements TestResult {

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public String getDisplayName() {
		String testName = getTestName();

		if (testName.startsWith("test[")) {
			return testName.substring(5, testName.length() - 1);
		}

		return getSimpleClassName() + "." + testName;
	}

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public String getErrorDetails() {
		return _errorDetails;
	}

	@Override
	public String getErrorStackTrace() {
		return _errorStackTrace;
	}

	@Override
	public Element getGitHubElement() {
		String testReportURL = getTestReportURL();

		Element downstreamBuildListItemElement = Dom4JUtil.getNewElement(
			"div", null);

		downstreamBuildListItemElement.add(
			Dom4JUtil.getNewAnchorElement(testReportURL, getDisplayName()));

		if (getErrorStackTrace() != null) {
			String trimmedStackTrace = StringUtils.abbreviate(
				getErrorStackTrace(), _MAX_ERROR_STACK_DISPLAY_LENGTH);

			downstreamBuildListItemElement.add(
				Dom4JUtil.toCodeSnippetElement(trimmedStackTrace));
		}

		return downstreamBuildListItemElement;
	}

	@Override
	public String getPackageName() {
		String className = getClassName();

		if (className.contains(".")) {
			int x = className.lastIndexOf(".");

			return className.substring(0, x);
		}

		Build build = getBuild();

		System.out.println(
			"Invalid test class name \"" + getClassName() + "\" in build " +
				build.getBuildURL());

		return className;
	}

	@Override
	public String getSimpleClassName() {
		String className = getClassName();

		if (className.contains(".")) {
			int x = className.lastIndexOf(".");

			return className.substring(x + 1);
		}

		Build build = getBuild();

		System.out.println(
			"Invalid test class name \"" + getClassName() + "\" in build " +
				build.getBuildURL());

		return className;
	}

	@Override
	public String getStatus() {
		return _status;
	}

	@Override
	public String getTestName() {
		return _testName;
	}

	public String getTestrayLogsURL() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		String logBaseURL = null;

		if (buildProperties.containsKey("log.base.url")) {
			logBaseURL = buildProperties.getProperty("log.base.url");
		}

		if (logBaseURL == null) {
			logBaseURL = _DEFAULT_LOG_BASE_URL;
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

	@Override
	public String getTestReportURL() {
		StringBuilder sb = new StringBuilder();

		Build build = getBuild();

		sb.append(build.getBuildURL());

		sb.append("/testReport/");

		String packageName = getPackageName();

		sb.append(packageName);

		sb.append("/");
		sb.append(getSimpleClassName());
		sb.append("/");

		String testName = getTestName();

		String encodedTestName = testName;

		encodedTestName = encodedTestName.replace(" ", "_");
		encodedTestName = encodedTestName.replace("#", "_");
		encodedTestName = encodedTestName.replace("(", "_");
		encodedTestName = encodedTestName.replace(")", "_");
		encodedTestName = encodedTestName.replace(".", "_");
		encodedTestName = encodedTestName.replace("<", "_");
		encodedTestName = encodedTestName.replace(">", "_");
		encodedTestName = encodedTestName.replace("[", "_");
		encodedTestName = encodedTestName.replace("\"", "_");
		encodedTestName = encodedTestName.replace("]", "_");

		if (packageName.equals("junit.framework")) {
			encodedTestName = encodedTestName.replace(".", "_");
		}

		sb.append(encodedTestName);

		return sb.toString();
	}

	protected BaseTestResult(Build build, JSONObject caseJSONObject) {
		if (build == null) {
			throw new IllegalArgumentException("Build may not be null");
		}

		this._build = build;

		_className = caseJSONObject.getString("className");

		_duration = (long)(caseJSONObject.getDouble("duration") * 1000D);

		_testName = caseJSONObject.getString("name");

		_status = caseJSONObject.getString("status");

		if (_status.equals("FAILED") && caseJSONObject.has("errorDetails") &&
			caseJSONObject.has("errorStackTrace")) {

			_errorDetails = caseJSONObject.optString("errorDetails");
			_errorStackTrace = caseJSONObject.optString("errorStackTrace");
		}
		else {
			_errorDetails = null;
			_errorStackTrace = null;
		}
	}

	protected String getAxisNumber() {
		AxisBuild axisBuild = null;

		Build build = getBuild();

		if (build instanceof AxisBuild) {
			axisBuild = (AxisBuild)build;

			return axisBuild.getAxisNumber();
		}

		return "INVALID_AXIS_NUMBER";
	}

	private static final String _DEFAULT_LOG_BASE_URL =
		"https://testray.liferay.com/reports/production/logs";

	private static final int _MAX_ERROR_STACK_DISPLAY_LENGTH = 1500;

	private final Build _build;
	private final String _className;
	private final long _duration;
	private final String _errorDetails;
	private final String _errorStackTrace;
	private final String _status;
	private final String _testName;

}