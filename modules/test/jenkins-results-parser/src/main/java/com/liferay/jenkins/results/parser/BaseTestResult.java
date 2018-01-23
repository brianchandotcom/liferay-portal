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

	public BaseTestResult(Build build, JSONObject caseJSONObject) {
		if (build == null) {
			throw new IllegalArgumentException("Build may not be null");
		}

		this.build = build;

		className = caseJSONObject.getString("className");

		duration = (long)(caseJSONObject.getDouble("duration") * 1000D);

		int x = className.lastIndexOf(".");

		try {
			simpleClassName = className.substring(x + 1);

			packageName = className.substring(0, x);
		}
		catch (StringIndexOutOfBoundsException sioobe) {
			packageName = className;
			simpleClassName = className;

			System.out.println(
				"Invalid test class name \"" + className + "\" in build " +
					build.getBuildURL());
		}

		testName = caseJSONObject.getString("name");

		status = caseJSONObject.getString("status");

		if (status.equals("FAILED") && caseJSONObject.has("errorDetails") &&
			caseJSONObject.has("errorStackTrace")) {

			errorDetails = caseJSONObject.optString("errorDetails");
			errorStackTrace = caseJSONObject.optString("errorStackTrace");
		}
	}

	@Override
	public Build getBuild() {
		return build;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public String getDisplayName() {
		if (testName.startsWith("test[")) {
			return testName.substring(5, testName.length() - 1);
		}

		return simpleClassName + "." + testName;
	}

	@Override
	public long getDuration() {
		return duration;
	}

	@Override
	public Element getGitHubElement() {
		return getGitHubElement(null);
	}

	@Override
	public Element getGitHubElement(String testrayLogsURL) {
		String testReportURL = getTestReportURL();

		Element downstreamBuildListItemElement = Dom4JUtil.getNewElement(
			"div", null);

		downstreamBuildListItemElement.add(
			Dom4JUtil.getNewAnchorElement(testReportURL, getDisplayName()));

		if (errorStackTrace != null) {
			String trimmedStackTrace = StringUtils.abbreviate(
				errorStackTrace, _MAX_ERROR_STACK_DISPLAY_LENGTH);

			downstreamBuildListItemElement.add(
				Dom4JUtil.toCodeSnippetElement(trimmedStackTrace));
		}

		return downstreamBuildListItemElement;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public String getTestName() {
		return testName;
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

		Map<String, String> startPropertiesTempMap =
			build.getStartPropertiesTempMap();

		return JenkinsResultsParserUtil.combine(
			logBaseURL, "/",
			startPropertiesTempMap.get("TOP_LEVEL_MASTER_HOSTNAME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_START_TIME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_JOB_NAME"), "/",
			startPropertiesTempMap.get("TOP_LEVEL_BUILD_NUMBER"), "/",
			build.getJobVariant(), "/", getAxisBuildNumber());
	}

	@Override
	public String getTestReportURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(build.getBuildURL());
		sb.append("/testReport/");
		sb.append(packageName);
		sb.append("/");
		sb.append(simpleClassName);
		sb.append("/");

		String encodedTestName = testName;

		encodedTestName = encodedTestName.replace("[", "_");
		encodedTestName = encodedTestName.replace("]", "_");
		encodedTestName = encodedTestName.replace("#", "_");

		if (packageName.equals("junit.framework")) {
			encodedTestName = encodedTestName.replace(".", "_");
		}

		sb.append(encodedTestName);

		return sb.toString();
	}

	protected String getAxisBuildNumber() {
		AxisBuild axisBuild = null;

		if (build instanceof AxisBuild) {
			axisBuild = (AxisBuild)build;

			return axisBuild.getAxisNumber();
		}

		return "INVALID_AXIS_NUMBER";
	}

	protected Build build;
	protected String className;
	protected long duration;
	protected String errorDetails;
	protected String errorStackTrace;
	protected String packageName;
	protected String simpleClassName;
	protected String status;
	protected String testName;

	private static final String _DEFAULT_LOG_BASE_URL =
		"https://testray.liferay.com/reports/production/logs";

	private static final int _MAX_ERROR_STACK_DISPLAY_LENGTH = 1500;

}