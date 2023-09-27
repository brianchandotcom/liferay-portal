/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class GitLPushFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		int index = consoleText.indexOf(_TOKEN_GIT_LPUSH_VALIDATION_FAILURE);

		if (index == -1) {
			return null;
		}

		int end = consoleText.lastIndexOf(_TOKEN_ERROR, index);

		end = consoleText.indexOf("\n", end);

		return getConsoleTextSnippetElementByEnd(consoleText, false, end);
	}

	private static final String _TOKEN_ERROR = "error:";

	private static final String _TOKEN_GIT_LPUSH_VALIDATION_FAILURE =
		"A git-lpush validation failure has occurred.";

}