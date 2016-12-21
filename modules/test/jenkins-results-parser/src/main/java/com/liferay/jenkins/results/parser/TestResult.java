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
			throw new IllegalArgumentException("axisBuild may not be null");
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

	public long getDuration() {
		return duration;
	}

	public String getStatus() {
		return status;
	}

	public String getTestName() {
		return testName;
	}

	protected AxisBuild axisBuild;
	protected String className;
	protected long duration;
	protected String packageName;
	protected String simpleClassName;
	protected String status;
	protected String testName;

}