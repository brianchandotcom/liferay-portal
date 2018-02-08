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

import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PoshiTestResult extends BaseTestResult {

	@Override
	public Element getGitHubElement() {
		String testReportURL = getTestReportURL();

		Element downstreamBuildListItemElement = Dom4JUtil.getNewElement(
			"div", null);

		downstreamBuildListItemElement.add(
			Dom4JUtil.getNewAnchorElement(testReportURL, getDisplayName()));

		Dom4JUtil.addToElement(
			downstreamBuildListItemElement, " - ",
			Dom4JUtil.getNewAnchorElement(getPoshiReportURL(), "Poshi Report"),
			" - ",
			Dom4JUtil.getNewAnchorElement(
				getPoshiSummaryURL(), "Poshi Summary"),
			" - ",
			Dom4JUtil.getNewAnchorElement(
				getConsoleOutputURL(), "Console Output"));

		String errorDetails = getErrorDetails();

		if ((errorDetails != null) && !errorDetails.isEmpty()) {
			Dom4JUtil.addToElement(
				Dom4JUtil.toCodeSnippetElement(errorDetails));
		}

		if (hasLiferayLog()) {
			Dom4JUtil.addToElement(
				downstreamBuildListItemElement, " - ",
				Dom4JUtil.getNewAnchorElement(
					getLiferayLogURL(), "Liferay Log"));
		}

		return downstreamBuildListItemElement;
	}

	protected PoshiTestResult(Build build, JSONObject caseJSONObject) {
		super(build, caseJSONObject);
	}

	protected String getConsoleOutputURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(getTestrayLogsURL());
		sb.append("/jenkins-console.txt.gz");

		return sb.toString();
	}

	protected String getLiferayLogURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(getTestrayLogsURL());
		sb.append("/");
		sb.append(name.replace("#", "_"));
		sb.append("/liferay-log.txt.gz");

		return sb.toString();
	}

	protected String getPoshiReportURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(getTestrayLogsURL());
		sb.append("/");
		sb.append(name.replace("#", "_"));
		sb.append("/index.html.gz");

		return sb.toString();
	}

	protected String getPoshiSummaryURL() {
		StringBuilder sb = new StringBuilder();

		String name = getDisplayName();

		sb.append(getTestrayLogsURL());
		sb.append("/");
		sb.append(name.replace("#", "_"));
		sb.append("/summary.html.gz");

		return sb.toString();
	}

	protected boolean hasLiferayLog() {
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

}