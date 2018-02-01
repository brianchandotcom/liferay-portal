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

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public interface TestResult {

	public Build getBuild();

	public String getClassName();

	public String getConsoleOutputURL(String testrayLogsURL);

	public String getDisplayName();

	public long getDuration();

	public Element getGitHubElement();

	public Element getGitHubElement(String testrayLogsURL);

	public String getLiferayLogURL(String testrayLogsURL);

	public String getPackageName();

	public String getPoshiReportURL(String testrayLogsURL);

	public String getPoshiSummaryURL(String testrayLogsURL);

	public String getStatus();

	public String getTestName();

	public String getTestReportURL();

	public boolean hasLiferayLog(String testrayLogsURL);

}