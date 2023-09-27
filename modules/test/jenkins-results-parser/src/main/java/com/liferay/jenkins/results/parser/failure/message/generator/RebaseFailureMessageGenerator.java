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
 */
public class RebaseFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_COULD_NOT_APPLY) ||
			!consoleText.contains(_TOKEN_UNABLE_TO_REBASE)) {

			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_UNABLE_TO_REBASE);

		start = consoleText.lastIndexOf("\n", start);

		int end = consoleText.indexOf(_TOKEN_COULD_NOT_APPLY, start);

		end = consoleText.indexOf("\n", end);

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Please fix ",
				Dom4JUtil.getNewElement("strong", null, "rebase errors"),
				" on ",
				Dom4JUtil.getNewElement(
					"strong", null,
					getBaseBranchAnchorElement(build.getTopLevelBuild())),
				getConsoleTextSnippetElement(consoleText, false, start, end)));
	}

	private static final String _TOKEN_COULD_NOT_APPLY = "Could not apply";

	private static final String _TOKEN_UNABLE_TO_REBASE = "Unable to rebase";

}