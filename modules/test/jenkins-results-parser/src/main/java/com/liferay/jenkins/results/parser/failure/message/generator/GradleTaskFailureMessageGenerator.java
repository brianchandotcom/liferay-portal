/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class GradleTaskFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		Element errorMessageElement = getMessageElement(consoleText);

		if (errorMessageElement == null) {
			return null;
		}

		try {
			return Dom4JUtil.format(errorMessageElement);
		}
		catch (IOException ioException) {
			return ioException.getMessage();
		}
	}

	@Override
	public Element getMessageElement(String consoleText) {
		StringBuilder sb = new StringBuilder();

		Matcher matcher = _pattern.matcher(consoleText);

		if (matcher.find()) {
			String snippet = matcher.group(1);

			int start = consoleText.lastIndexOf(snippet);

			sb.append(getConsoleTextSnippetByStart(consoleText, start));

			sb.append("\n\n");
		}

		if (consoleText.contains(_TOKEN_WHAT_WENT_WRONG)) {
			int start = consoleText.lastIndexOf(_TOKEN_WHAT_WENT_WRONG);

			int whereIndex = consoleText.lastIndexOf(_TOKEN_WHERE, start);

			if (whereIndex != -1) {
				start = whereIndex;
			}

			start = consoleText.lastIndexOf("\n", start);

			sb.append(getConsoleTextSnippetByStart(consoleText, start));
		}

		if (sb.length() == 0) {
			return null;
		}

		return Dom4JUtil.toCodeSnippetElement(sb.toString());
	}

	private static final String _TOKEN_WHAT_WENT_WRONG = "* What went wrong:";

	private static final String _TOKEN_WHERE = "* Where:";

	private static final Pattern _pattern = Pattern.compile(
		"\\n(\\s+\\[exec\\] > Task :[^ ]+ FAILED)");

}