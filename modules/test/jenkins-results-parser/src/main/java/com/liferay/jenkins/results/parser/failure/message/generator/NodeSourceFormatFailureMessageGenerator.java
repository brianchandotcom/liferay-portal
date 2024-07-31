/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

/**
 * @author Peter Yoo
 * @author Yi-Chen Tsai
 */
public class NodeSourceFormatFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		if (!consoleText.contains(":packageRunCheckFormat FAILED")) {
			return null;
		}

		int start = consoleText.indexOf("Task :packageRunCheckFormat");

		start = consoleText.lastIndexOf("\n", start);

		int end = start + CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX;

		end = consoleText.lastIndexOf("\n", end);

		return getConsoleTextSnippet(consoleText, false, start, end);
	}

}