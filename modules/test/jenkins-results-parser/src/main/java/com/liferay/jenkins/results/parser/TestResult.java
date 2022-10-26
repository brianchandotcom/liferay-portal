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

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public interface TestResult {

	public Build getBuild();

	public String getClassName();

	public String getDisplayName();

	public long getDuration();

	public String getErrorDetails();

	public String getErrorStackTrace();

	public Element getGitHubElement();

	public String getPackageName();

	public String getSimpleClassName();

	public String getStatus();

	public TestClass getTestClass();

	public TestClassResult getTestClassResult();

	public TestHistory getTestHistory();

	public String getTestName();

	public String getTestReportURL();

	public boolean isFailing();

	public boolean isUniqueFailure();

}