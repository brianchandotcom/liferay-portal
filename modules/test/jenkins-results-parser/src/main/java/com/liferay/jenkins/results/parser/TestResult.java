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

/**
 * @author Leslie Wong
 */
public class TestResult {

	public TestResult(
		String className, String duration, String testName, String status) {

		this.className = className;
		this.duration = duration;
		this.testName = testName;
		this.status = status;
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
	protected String status;
	protected String testName;

}