/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class InvalidGitCommitSHAFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_WAS_NOT_FOUND_IN_BRANCH)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_WAS_NOT_FOUND_IN_BRANCH);

		start = consoleText.lastIndexOf(_TOKEN_SHA, start);

		int end = consoleText.indexOf("\n", start);

		return getConsoleTextSnippet(consoleText, false, start, end);
	}

	@Override
	public Element getMessageElement(Build build) {
		String errorMessage = getMessage(build);

		if (errorMessage != null) {
			return Dom4JUtil.toCodeSnippetElement(getMessage(build));
		}

		return null;
	}

	private static final String _TOKEN_SHA = "SHA";

	private static final String _TOKEN_WAS_NOT_FOUND_IN_BRANCH =
		" was not found in branch '";

}