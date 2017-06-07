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

import com.liferay.jenkins.results.parser.Build;

import java.util.Hashtable;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class CompileFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		return null;
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleOutput = build.getConsoleText();

		int end = consoleOutput.indexOf("Compile failed;");

		if (end == -1) {
			end = consoleOutput.indexOf("compileJava FAILED");
		}

		if (end == -1) {
			return null;
		}

		end = consoleOutput.lastIndexOf("\n", end);

		return getConsoleOutputSnippetElement(consoleOutput, true, end);
	}

}