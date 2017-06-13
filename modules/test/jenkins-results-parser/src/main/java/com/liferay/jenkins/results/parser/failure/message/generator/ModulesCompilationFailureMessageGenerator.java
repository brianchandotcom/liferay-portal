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

import java.util.Hashtable;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class ModulesCompilationFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains(_TOKEN_COULD_NOT_RESOLVE_CONFIG)) {
			return null;
		}

		int end = consoleOutput.indexOf(_TOKEN_MERGE_TEST_RESULTS);

		end = consoleOutput.lastIndexOf(_TOKEN_TRY, end);

		end = consoleOutput.lastIndexOf("\n", end);

		int start = consoleOutput.lastIndexOf(_TOKEN_WHAT_WENT_WRONG, end);

		start = consoleOutput.lastIndexOf("\n", start);

		return getConsoleOutputSnippet(consoleOutput, true, start, end);
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_COULD_NOT_RESOLVE_CONFIG)) {
			return null;
		}

		int end = consoleText.indexOf(_TOKEN_MERGE_TEST_RESULTS);

		end = consoleText.lastIndexOf(_TOKEN_TRY, end);

		end = consoleText.lastIndexOf("\n", end);

		int start = consoleText.lastIndexOf(_TOKEN_WHAT_WENT_WRONG, end);

		start = consoleText.lastIndexOf("\n", start);

		return getConsoleOutputSnippetElement(consoleText, true, start, end);
	}

	private static final String _TOKEN_COULD_NOT_RESOLVE_CONFIG =
		"Could not resolve all files for configuration";

	private static final String _TOKEN_MERGE_TEST_RESULTS =
		"merge-test-results:";

	private static final String _TOKEN_TRY = "Try:";

	private static final String _TOKEN_WHAT_WENT_WRONG = "What went wrong:";

}