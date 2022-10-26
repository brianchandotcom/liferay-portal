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

import java.util.List;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public interface TestClassResult {

	public Build getBuild();

	public String getClassName();

	public long getDuration();

	public Element getGitHubElement();

	public Element getGitHubElement(Boolean uniqueFailures);

	public String getPackageName();

	public String getSimpleClassName();

	public String getStatus();

	public TestClass getTestClass();

	public String getTestClassReportURL();

	public TestHistory getTestHistory();

	public TestResult getTestResult(String testName);

	public List<TestResult> getTestResults();

	public boolean isFailing();

}