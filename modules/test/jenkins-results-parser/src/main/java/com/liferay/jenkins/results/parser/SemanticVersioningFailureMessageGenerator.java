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

import java.util.Hashtable;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 * @author Yi-Chen Tsai
 */
public class SemanticVersioningFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains(_SEMVER_INCORRECT_STRING) ||
			!consoleOutput.contains(_SEMVER_PACKAGE_STRING)) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p>Please fix <strong>semantic versioning</strong> on ");
		sb.append("<strong><a href=\"https://github.com/");
		sb.append(properties.get("github.origin.name"));
		sb.append("/");
		sb.append(properties.get("repository"));
		sb.append("/tree/");
		sb.append(properties.get("github.sender.branch.name"));
		sb.append("\">");
		sb.append(properties.get("github.origin.name"));
		sb.append("/");
		sb.append(properties.get("github.sender.branch.name"));
		sb.append("</a></strong>.</p>");

		int end = consoleOutput.indexOf(_SEMVER_INCORRECT_STRING);

		end = consoleOutput.indexOf("\n", end);

		int start = consoleOutput.lastIndexOf(_BASELINE_CHECK_STRING, end);

		start = consoleOutput.indexOf(_SEMVER_PACKAGE_STRING, start);

		start = consoleOutput.lastIndexOf("\n", start);

		sb.append(getConsoleOutputSnippet(consoleOutput, true, start, end));

		return sb.toString();
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_SEMVER_INCORRECT_STRING) ||
			!consoleText.contains(_SEMVER_PACKAGE_STRING)) {

			return null;
		}

		int end = consoleText.indexOf(_SEMVER_INCORRECT_STRING);

		end = consoleText.indexOf("\n", end);

		int start = consoleText.lastIndexOf(_BASELINE_CHECK_STRING, end);

		start = consoleText.indexOf(_SEMVER_PACKAGE_STRING, start);

		start = consoleText.lastIndexOf("\n", start);

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Please fix ",
				Dom4JUtil.getNewElement("strong", null, "semantic versioning"),
				" on ",
				Dom4JUtil.getNewElement(
					"strong", null,
					getBaseBranchAnchorElement(build.getTopLevelBuild()),
					getConsoleOutputSnippetElement(
						consoleText, true, start, end))));
	}

	private static final String _BASELINE_CHECK_STRING =
		"Checking for baseline log files";

	private static final String _SEMVER_INCORRECT_STRING =
		"Semantic versioning is incorrect";

	private static final String _SEMVER_PACKAGE_STRING = "PACKAGE_NAME";

}