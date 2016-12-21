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

import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class TestResult {

	public TestResult(JSONObject caseJSONObject) {
		className = caseJSONObject.getString("className");

		int x = className.lastIndexOf(".");

		simpleClassName = className.substring(x + 1);

		packageName = className.substring(0, x);

		testName = caseJSONObject.getString("name");

		testName = testName.replace("[", "_");
		testName = testName.replace("]", "_");
		testName = testName.replace("#", "_");

		if (packageName.equals("junit.framework")) {
			testName = testName.replace(".", "_");
		}

		status = caseJSONObject.getString("status");
	}

	public String getClassName() {
		return className;
	}

	public String getDuration() {
		return duration;
	}

	public String getStatus() {
		return status;
	}

	public String getTestName() {
		return testName;
	}

	protected String className;
	protected String duration;
	protected String packageName;
	protected String status;
	protected String simpleClassName;
	protected String testName;

}