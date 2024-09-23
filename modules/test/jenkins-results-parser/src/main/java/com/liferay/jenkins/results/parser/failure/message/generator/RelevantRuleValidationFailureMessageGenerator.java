/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

/**
 * @author Brittney Nguyen
 */
public class RelevantRuleValidationFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		if (!consoleText.contains(_TOKEN_UNABLE_TO_CREATE_BATCH)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_ISSUES_WERE_FOUND);

		if (consoleText.contains(_TOKEN_UNABLE_TO_CREATE_BATCH) &&
			(start == -1)) {

			start = consoleText.lastIndexOf(
				_TOKEN_UNABLE_TO_CREATE_BATCH, start);
		}

		start = consoleText.lastIndexOf("\n", start);

		int end = consoleText.indexOf("Total time:", start);

		return getConsoleTextSnippet(consoleText, false, start, end);
	}

	private static final String _TOKEN_ISSUES_WERE_FOUND =
		"java.lang.RuntimeException: The following issues were found:";

	private static final String _TOKEN_UNABLE_TO_CREATE_BATCH =
		"Unable to create batch";

}