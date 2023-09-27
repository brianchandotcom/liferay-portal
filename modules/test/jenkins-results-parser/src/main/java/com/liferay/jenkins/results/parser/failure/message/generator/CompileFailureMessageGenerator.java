/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class CompileFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		int end = consoleText.indexOf("Compile failed;");

		if (end != -1) {
			end = consoleText.indexOf("\n", end);

			return getConsoleTextSnippetElementByEnd(consoleText, false, end);
		}

		int start = consoleText.indexOf("compileJava FAILED");

		if (start != -1) {
			start = consoleText.lastIndexOf("\n", start);

			return getConsoleTextSnippetElementByStart(consoleText, start);
		}

		return null;
	}

}