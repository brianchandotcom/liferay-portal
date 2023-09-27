/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import org.dom4j.Element;

/**
 * @author Brittney Nguyen
 */
public class FormatFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		if (!consoleText.contains(_TOKEN_UNABLE_TO_FORMAT)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_UNABLE_TO_FORMAT);

		start = consoleText.lastIndexOf("\n", start);

		int end = start + CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX;

		end = consoleText.lastIndexOf("\n", end);

		return getConsoleTextSnippetElement(consoleText, false, start, end);
	}

	private static final String _TOKEN_UNABLE_TO_FORMAT = "Unable to format";

}