/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class PlaywrightCompilationFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		int end = consoleText.indexOf(_TOKEN_END_SNIPPET);

		end = consoleText.lastIndexOf("\n", end);

		if (end == -1) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_START_0_SNIPPET, end);

		if (start == -1) {
			start = consoleText.lastIndexOf(_TOKEN_START_1_SNIPPET, end);
		}

		if (start == -1) {
			start = consoleText.lastIndexOf(_TOKEN_START_2_SNIPPET, end);
		}

		start = consoleText.lastIndexOf("\n", start);

		return getConsoleTextSnippet(consoleText, true, start, end);
	}

	@Override
	public Element getMessageElement(Build build) {
		String jobVariant = build.getJobVariant();

		if (!jobVariant.contains("playwright-compile")) {
			return null;
		}

		return getMessageElement(build.getConsoleText());
	}

	private static final String _TOKEN_END_SNIPPET =
		"The following error occurred while executing this line:";

	private static final String _TOKEN_START_0_SNIPPET = "Error:";

	private static final String _TOKEN_START_1_SNIPPET = "/tmp/script.sh";

	private static final String _TOKEN_START_2_SNIPPET = "Executing commands:";

}