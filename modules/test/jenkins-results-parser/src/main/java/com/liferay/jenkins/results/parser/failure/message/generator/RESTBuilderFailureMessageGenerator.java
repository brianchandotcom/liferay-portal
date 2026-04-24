/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

/**
 * @author Brittney Nguyen
 */
public class RESTBuilderFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		int start = consoleText.lastIndexOf(
			_TOKEN_ERROR_PROCESSING_REST_CONFIG);

		if (start != -1) {
			start = consoleText.lastIndexOf("\n", start);

			int end = start + CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX;

			end = consoleText.lastIndexOf("\n", end);

			return getConsoleTextSnippet(consoleText, false, start, end);
		}

		if (consoleText.contains(_TOKEN_DETECTED_REST_BUILDER_CHANGES)) {
			start = consoleText.indexOf(_TOKEN_GIT_DIFF_VERSION);

			start = consoleText.lastIndexOf("\n", start);

			int end = start + CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX;

			end = consoleText.lastIndexOf("\n", end);

			return getConsoleTextSnippet(consoleText, false, start, end);
		}

		return null;
	}

	private static final String _TOKEN_DETECTED_REST_BUILDER_CHANGES =
		"Detected REST builder changes";

	private static final String _TOKEN_ERROR_PROCESSING_REST_CONFIG =
		"Error processing REST config files";

	private static final String _TOKEN_GIT_DIFF_VERSION = "--- a";

}