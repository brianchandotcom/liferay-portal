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
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PoshiTestResult extends BaseTestResult {

	public PoshiTestResult(Build build, JSONObject caseJSONObject) {
		super(build, caseJSONObject);
	}

	@Override
	public Element getGitHubElement(String testrayLogsURL) {
		String testReportURL = getTestReportURL();

		Element downstreamBuildListItemElement = Dom4JUtil.getNewElement(
			"div", null);

		downstreamBuildListItemElement.add(
			Dom4JUtil.getNewAnchorElement(testReportURL, getDisplayName()));

		Dom4JUtil.addToElement(
			downstreamBuildListItemElement, " - ",
			Dom4JUtil.getNewAnchorElement(
				getPoshiReportURL(testrayLogsURL), "Poshi Report"),
			" - ",
			Dom4JUtil.getNewAnchorElement(
				getPoshiSummaryURL(testrayLogsURL), "Poshi Summary"),
			" - ",
			Dom4JUtil.getNewAnchorElement(
				getConsoleOutputURL(testrayLogsURL), "Console Output"));

		if (errorDetails != null) {
			Dom4JUtil.addToElement(
				Dom4JUtil.toCodeSnippetElement(errorDetails));
		}

		if (hasLiferayLog(testrayLogsURL)) {
			Dom4JUtil.addToElement(
				downstreamBuildListItemElement, " - ",
				Dom4JUtil.getNewAnchorElement(
					getLiferayLogURL(testrayLogsURL), "Liferay Log"));
		}

		return downstreamBuildListItemElement;
	}

}