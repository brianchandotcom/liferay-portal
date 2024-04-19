/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Dom4JUtil;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public class JenkinsSourceFormatFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		if (!consoleText.contains(_TOKEN_PLEASE_RUN_FORMAT_SOURCE)) {
			return null;
		}

		int start = consoleText.lastIndexOf(_TOKEN_PLEASE_RUN_FORMAT_SOURCE);

		start = consoleText.lastIndexOf("\n", start);

		return getConsoleTextSnippetByStart(consoleText, start);
	}

	@Override
	public Element getMessageElement(String consoleText) {
		return Dom4JUtil.toCodeSnippetElement(getMessage(consoleText));
	}

	private static final String _TOKEN_PLEASE_RUN_FORMAT_SOURCE =
		"Please run format source and resubmit after fixing these files:";

}