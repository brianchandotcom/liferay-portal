/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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