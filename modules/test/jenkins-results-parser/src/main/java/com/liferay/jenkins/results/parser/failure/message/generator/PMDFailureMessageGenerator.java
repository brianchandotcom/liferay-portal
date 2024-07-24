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
public class PMDFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_TOKEN_PMD_VIOLATIONS_WERE_FOUND)) {
			return null;
		}

		int start = consoleText.indexOf(_TOKEN_PMD_VIOLATIONS_WERE_FOUND);

		start = consoleText.lastIndexOf("\n", start);

		return getConsoleTextSnippetByStart(consoleText, start);
	}

	@Override
	public Element getMessageElement(Build build) {
		String errorMessage = getMessage(build);

		if (errorMessage != null) {
			return Dom4JUtil.toCodeSnippetElement(errorMessage);
		}

		return null;
	}

	private static final String _TOKEN_PMD_VIOLATIONS_WERE_FOUND =
		"PMD violation(s) were found";

}