/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class ModulesCompilationFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		String jobName = build.getJobName();

		if (!jobName.contains("modules-compile")) {
			return null;
		}

		return getMessageElement(build.getConsoleText());
	}

	@Override
	public Element getMessageElement(String consoleText) {
		if (!consoleText.contains(_TOKEN_COULD_NOT_RESOLVE_CONFIG)) {
			return null;
		}

		int end = consoleText.indexOf(_TOKEN_MERGE_TEST_RESULTS);

		end = consoleText.lastIndexOf(_TOKEN_TRY, end);

		end = consoleText.lastIndexOf("\n", end);

		int start = consoleText.lastIndexOf(_TOKEN_WHAT_WENT_WRONG, end);

		start = consoleText.lastIndexOf("\n", start);

		return getConsoleTextSnippetElement(consoleText, true, start, end);
	}

	private static final String _TOKEN_COULD_NOT_RESOLVE_CONFIG =
		"Could not resolve all files for configuration";

	private static final String _TOKEN_MERGE_TEST_RESULTS =
		"merge-test-results:";

	private static final String _TOKEN_TRY = "Try:";

	private static final String _TOKEN_WHAT_WENT_WRONG = "What went wrong:";

}