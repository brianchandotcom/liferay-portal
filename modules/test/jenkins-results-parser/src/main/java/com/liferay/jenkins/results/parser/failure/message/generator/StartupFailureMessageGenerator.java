/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class StartupFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		if (!consoleText.contains(_TOKEN_UNRESOLVED_REQUIREMENT)) {
			return null;
		}

		int start = consoleText.indexOf(_TOKEN_UNRESOLVED_REQUIREMENT);

		start = consoleText.lastIndexOf(_TOKEN_COULD_NOT_RESOLVE_MODULE, start);

		int newLineIndex = consoleText.lastIndexOf("\n", start);

		if (newLineIndex != -1) {
			start = newLineIndex;
		}

		int end = consoleText.indexOf(_TOKEN_DELETING, start);

		if (end == -1) {
			end = consoleText.length() - 1;
		}

		newLineIndex = consoleText.lastIndexOf("\n", end);

		if (newLineIndex != -1) {
			end = newLineIndex;
		}

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Startup error: ",
				Dom4JUtil.getNewElement(
					"strong", null, "Unresolved Requirement(s)")),
			getConsoleTextSnippetElement(consoleText, true, start, end));
	}

	private static final String _TOKEN_COULD_NOT_RESOLVE_MODULE =
		"Could not resolve module:";

	private static final String _TOKEN_DELETING = "Deleting:";

	private static final String _TOKEN_UNRESOLVED_REQUIREMENT =
		"Unresolved requirement:";

}