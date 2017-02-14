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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class TestResult {

	public static List<TestResult> getTestResults(
		AxisBuild axisBuild, JSONArray suitesJSONArray, String testStatus) {

		List<TestResult> testResults = new ArrayList<>();

		for (int i = 0; i < suitesJSONArray.length(); i++) {
			JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(i);

			JSONArray casesJSONArray = suiteJSONObject.getJSONArray("cases");

			for (int j = 0; j < casesJSONArray.length(); j++) {
				TestResult testResult = new TestResult(
					axisBuild, casesJSONArray.getJSONObject(j));

				if ((testStatus == null) ||
					testStatus.equals(testResult.getStatus())) {

					testResults.add(testResult);
				}
			}
		}

		return testResults;
	}

	public TestResult(AxisBuild axisBuild, JSONObject caseJSONObject) {
		if (axisBuild == null) {
			throw new IllegalArgumentException("Axis build may not be null");
		}

		this.axisBuild = axisBuild;

		className = caseJSONObject.getString("className");

		duration = (long)(caseJSONObject.getDouble("duration") * 1000d);

		int x = className.lastIndexOf(".");

		simpleClassName = className.substring(x + 1);

		packageName = className.substring(0, x);

		testName = caseJSONObject.getString("name");

		status = caseJSONObject.getString("status");
	}

	public AxisBuild getAxisBuild() {
		return axisBuild;
	}

	public String getClassName() {
		return className;
	}

	public String getConsoleOutputURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(axisBuild.getTestRayLogsURL());
		sb.append("/jenkins-console.txt.gz");

		return sb.toString();
	}

	public String getDisplayName() {
		if (testName.startsWith("test[")) {
			return testName.substring(5, testName.length() - 1);
		}

		return simpleClassName + "." + testName;
	}

	public long getDuration() {
		return duration;
	}

	public String getLiferayLogURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(axisBuild.getTestRayLogsURL());
		sb.append("/");
		sb.append(name.replace("#", "_"));
		sb.append("/liferay-log.txt.gz");

		return sb.toString();
	}

	public String getPoshiReportURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(axisBuild.getTestRayLogsURL());
		sb.append("/");
		sb.append(name.replace("#", "_"));
		sb.append("/index.html.gz");

		return sb.toString();
	}

	public String getPoshiSummaryURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(axisBuild.getTestRayLogsURL());
		sb.append("/");
		sb.append(name.replace("#", "_"));
		sb.append("/summary.html.gz");

		return sb.toString();
	}

	public String getStatus() {
		return status;
	}

	public String getTestName() {
		return testName;
	}

	public String getTestReportURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(axisBuild.getBuildURL());
		sb.append("/testReport/");
		sb.append(packageName);
		sb.append("/");
		sb.append(simpleClassName);
		sb.append("/");

		String encodedTestName = testName;

		encodedTestName = encodedTestName.replace("[", "_");
		encodedTestName = encodedTestName.replace("]", "_");
		encodedTestName = encodedTestName.replace("#", "_");

		if (simpleClassName.equals("junit.framework")) {
			encodedTestName = encodedTestName.replace(".", "_");
		}

		sb.append(encodedTestName);

		return sb.toString();
	}

	public boolean hasLiferayLog() {
		String liferayLog = null;

		try {
			liferayLog = JenkinsResultsParserUtil.toString(
				getLiferayLogURL(), false, 0, 0, 0);
		}
		catch (IOException ioe) {
			return false;
		}

		return !liferayLog.isEmpty();
	}

	protected AxisBuild axisBuild;
	protected String className;
	protected long duration;
	protected String packageName;
	protected String simpleClassName;
	protected String status;
	protected String testName;

}