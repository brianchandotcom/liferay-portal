/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 * @author Yi-Chen Tsai
 */
public class SourceFormatFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		if (!consoleText.contains(_TOKEN_SOURCE_FORMAT)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_SOURCE_FORMAT);

		if (consoleText.contains(_TOKEN_FORMATTING_ISSUES)) {
			start = consoleText.lastIndexOf(_TOKEN_FORMATTING_ISSUES, start);
		}

		start = consoleText.lastIndexOf("\n", start);

		int end = start + CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX;

		end = consoleText.lastIndexOf("\n", end);

		return getConsoleTextSnippetElement(consoleText, false, start, end);
	}

	private static final String _TOKEN_FORMATTING_ISSUES = "formatting issues:";

	private static final String _TOKEN_SOURCE_FORMAT =
		"at com.liferay.source.formatter";

}