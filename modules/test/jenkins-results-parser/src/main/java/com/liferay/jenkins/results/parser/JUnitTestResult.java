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

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 * @author Yi-Chen Tsai
 */
public class JUnitTestResult extends BaseTestResult {

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public String getDisplayName() {
		return JenkinsResultsParserUtil.combine(
			getClassName(), ".", getTestName());
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
		Element downstreamBuildListItemElement = Dom4JUtil.getNewElement(
			"div", null);

		downstreamBuildListItemElement.add(
			Dom4JUtil.getNewAnchorElement(
				getTestReportURL(), getDisplayName()));

		TestHistory testHistory = getTestHistory();

		if (testHistory != null) {
			downstreamBuildListItemElement.addText(" - ");

			downstreamBuildListItemElement.add(
				Dom4JUtil.getNewAnchorElement(
					testHistory.getTestrayCaseResultURL(),
					JenkinsResultsParserUtil.combine(
						"Failed ",
						String.valueOf(testHistory.getFailureCount()),
						" of last ",
						String.valueOf(testHistory.getTestCount()))));
		}

		String errorStackTrace = getErrorStackTrace();

		if ((errorStackTrace != null) && !errorStackTrace.isEmpty()) {
			String trimmedStackTrace = StringUtils.abbreviate(
				errorStackTrace, _LINES_ERROR_STACK_DISPLAY_SIZE_MAX);

			downstreamBuildListItemElement.add(
				Dom4JUtil.toCodeSnippetElement(trimmedStackTrace));
		}

		return downstreamBuildListItemElement;
	}

	@Override
	public String getPackageName() {
		String className = getClassName();

		int x = className.lastIndexOf(".");

		if (x < 0) {
			return "(root)";
		}

		return className.substring(0, x);
	}

	@Override
	public String getSimpleClassName() {
		String className = getClassName();

		int x = className.lastIndexOf(".");

		return className.substring(x + 1);
	}

	@Override
	public String getStatus() {
		return _status;
	}

	@Override
	public String getTestName() {
		return _testName;
	}

	@Override
	public String getTestrayLogsURL() {
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

		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		if (topLevelBuild != null) {
			String topLevelStartDateString =
				JenkinsResultsParserUtil.toDateString(
					new Date(topLevelBuild.getStartTime()), "yyyy-MM",
					"America/Los_Angeles");

			JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

			return JenkinsResultsParserUtil.combine(
				logBaseURL, "/", topLevelStartDateString, "/",
				jenkinsMaster.getName(), "/", topLevelBuild.getJobName(), "/",
				String.valueOf(topLevelBuild.getBuildNumber()), "/",
				build.getJobVariant(), "/", getAxisNumber());
		}

		return build.getBuildURL();
	}

	@Override
	public String getTestReportURL() {
		StringBuilder sb = new StringBuilder();

		Build build = getBuild();

		sb.append(build.getBuildURL());

		sb.append("/testReport/");
		sb.append(getPackageName());
		sb.append("/");
		sb.append(getSimpleClassName());
		sb.append("/");
		sb.append(getEncodedTestName());

		String testReportURL = sb.toString();

		if (testReportURL.startsWith("http")) {
			try {
				return JenkinsResultsParserUtil.encode(testReportURL);
			}
			catch (MalformedURLException | URISyntaxException exception) {
				System.out.println(
					"Unable to encode the test report " + testReportURL);
			}
		}

		return testReportURL;
	}

	protected JUnitTestResult(Build build, JSONObject caseJSONObject) {
		super(build);

		_className = caseJSONObject.getString("className");
		_duration = (long)(caseJSONObject.getDouble("duration") * 1000);
		_status = caseJSONObject.getString("status");
		_testName = caseJSONObject.getString("name");

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

	protected String getEncodedTestName() {
		StringBuilder sb = new StringBuilder(getTestName());

		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);

			if (!Character.isJavaIdentifierPart(c)) {
				sb.setCharAt(i, '_');
			}
		}

		return sb.toString();
	}

	private static final int _LINES_ERROR_STACK_DISPLAY_SIZE_MAX = 1500;

	private static final String _URL_BASE_LOGS_DEFAULT =
		"https://testray.liferay.com/reports/production/logs";

	private final String _className;
	private final long _duration;
	private final String _errorDetails;
	private final String _errorStackTrace;
	private final String _status;
	private final String _testName;

}