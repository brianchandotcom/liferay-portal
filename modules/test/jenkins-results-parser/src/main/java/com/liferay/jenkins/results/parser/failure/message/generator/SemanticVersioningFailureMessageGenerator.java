/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		int start = consoleText.lastIndexOf(_TOKEN_SEMVER_PACKAGE, end);

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

	private static final String _TOKEN_SEMVER_INCORRECT =
		"Semantic versioning is incorrect";

	private static final String _TOKEN_SEMVER_PACKAGE = "PACKAGE_NAME";

}