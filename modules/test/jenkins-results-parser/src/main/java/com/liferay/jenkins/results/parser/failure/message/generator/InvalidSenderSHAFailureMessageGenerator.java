/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class InvalidSenderSHAFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_FATAL_NOT_A_VALID_BRANCH_POINT)) {
			return null;
		}

		int start = consoleText.lastIndexOf(
			_TOKEN_FATAL_NOT_A_VALID_BRANCH_POINT);

		int end = consoleText.indexOf("\n", start);

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "The sender branch SHA could not be found on ",
				Dom4JUtil.getNewElement(
					"strong", null,
					getBaseBranchAnchorElement(build.getTopLevelBuild())),
				". The sender branch may have been force pushed or deleted ",
				"after the pull request test was initiated."),
			getConsoleTextSnippetElement(consoleText, false, start, end));
	}

	private static final String _TOKEN_FATAL_NOT_A_VALID_BRANCH_POINT =
		"fatal: Not a valid branch point";

}