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