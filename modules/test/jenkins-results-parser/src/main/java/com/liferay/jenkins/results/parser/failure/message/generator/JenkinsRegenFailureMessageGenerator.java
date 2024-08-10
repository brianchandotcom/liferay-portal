/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

/**
 * @author Yi-Chen Tsai
 */
public class JenkinsRegenFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		if (!consoleText.contains(_TOKEN_PLEASE_REGENERATE_AND_RESUBMIT)) {
			return null;
		}

		int start = consoleText.indexOf(_TOKEN_PLEASE_REGENERATE_AND_RESUBMIT);

		start = consoleText.lastIndexOf("\n", start);

		int end = consoleText.indexOf(_TOKEN_BUILD_FAILED, start);

		end = consoleText.lastIndexOf("\n", end);

		return getConsoleTextSnippet(consoleText, false, start, end);
	}

	private static final String _TOKEN_BUILD_FAILED = "BUILD FAILED";

	private static final String _TOKEN_PLEASE_REGENERATE_AND_RESUBMIT =
		"Please regenerate and resubmit after committing these files";

}