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
public class CIFailureMessageGenerator extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		int index = consoleText.indexOf(_TOKEN_CI_ERROR);

		if (index == -1) {
			return null;
		}

		String snippet = consoleText.substring(
			index, consoleText.indexOf("\n", index));

		return Dom4JUtil.toCodeSnippetElement(snippet);
	}

	@Override
	public boolean isGenericCIFailure() {
		return true;
	}

	private static final String _TOKEN_CI_ERROR = "A CI failure has occurred.";

}