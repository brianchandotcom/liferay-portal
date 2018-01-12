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

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 * @author Yi-Chen Tsai
 */
public class SemanticVersioningFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_SEMVER_INCORRECT) ||
			!consoleText.contains(_TOKEN_SEMVER_PACKAGE)) {

			return null;
		}

		int end = consoleText.indexOf(_TOKEN_SEMVER_INCORRECT);

		end = consoleText.indexOf("\n", end);

		int start = consoleText.lastIndexOf(_TOKEN_BASELINE_CHECK, end);

		start = consoleText.indexOf(_TOKEN_SEMVER_PACKAGE, start);

		start = consoleText.lastIndexOf("\n", start);

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Please fix ",
				Dom4JUtil.getNewElement("strong", null, "semantic versioning"),
				" on ",
				Dom4JUtil.getNewElement(
					"strong", null,
					getBaseBranchAnchorElement(build.getTopLevelBuild())),
				getConsoleTextSnippetElement(consoleText, true, start, end)));
	}

	private static final String _TOKEN_BASELINE_CHECK =
		"Checking for baseline log files";

	private static final String _TOKEN_SEMVER_INCORRECT =
		"Semantic versioning is incorrect";

	private static final String _TOKEN_SEMVER_PACKAGE = "PACKAGE_NAME";

}