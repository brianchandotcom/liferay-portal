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

package com.liferay.jenkins.results.parser;

import java.util.Hashtable;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 */
public class LocalGitMirrorFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (!consoleOutput.contains(_LOCAL_GIT_FAILURE_END_STRING) ||
			!consoleOutput.contains(_LOCAL_GIT_FAILURE_START_STRING)) {

			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<p>Unable to synchronize with <strong>local Git mirror");
		sb.append("</strong>.</p>");

		int end = consoleOutput.indexOf(_LOCAL_GIT_FAILURE_END_STRING);

		int start = consoleOutput.lastIndexOf(
			_LOCAL_GIT_FAILURE_START_STRING, end);

		consoleOutput = consoleOutput.substring(start, end);

		int minIndex = consoleOutput.length();

		for (String string : new String[] {"error: ", "fatal: "}) {
			int index = consoleOutput.indexOf(string);

			if (index != -1) {
				if (index < minIndex) {
					minIndex = index;
				}
			}
		}

		int gitCommandIndex = consoleOutput.lastIndexOf("+ git", minIndex);

		if (gitCommandIndex != -1) {
			start = gitCommandIndex;
		}

		start = consoleOutput.lastIndexOf("\n", start);

		end = consoleOutput.lastIndexOf("\n");

		sb.append(getConsoleOutputSnippet(consoleOutput, false, start, end));

		return sb.toString();
	}

	@Override
	public Element getMessageElement(Build build) {
		String consoleText = build.getConsoleText();

		if (!consoleText.contains(_LOCAL_GIT_FAILURE_END_STRING) ||
			!consoleText.contains(_LOCAL_GIT_FAILURE_START_STRING)) {

			return null;
		}

		Element messageElement = new DefaultElement("div");

		Dom4JUtil.addToElement(
			Dom4JUtil.getNewElement("p", messageElement),
			"Unable to synchronize with ",
			Dom4JUtil.wrapWithNewElement("local Git mirror", "strong"), ".");

		int end = consoleText.indexOf(_LOCAL_GIT_FAILURE_END_STRING);

		int start = consoleText.lastIndexOf(
			_LOCAL_GIT_FAILURE_START_STRING, end);

		consoleText = consoleText.substring(start, end);

		int minIndex = consoleText.length();

		for (String string : new String[] {"error: ", "fatal: "}) {
			int index = consoleText.indexOf(string);

			if (index != -1) {
				if (index < minIndex) {
					minIndex = index;
				}
			}
		}

		int gitCommandIndex = consoleText.lastIndexOf("+ git", minIndex);

		if (gitCommandIndex != -1) {
			start = gitCommandIndex;
		}

		start = consoleText.lastIndexOf("\n", start);

		end = consoleText.lastIndexOf("\n");

		messageElement.add(
			getConsoleOutputSnippetElement(consoleText, false, start, end));

		return messageElement;
	}

	private static final String _LOCAL_GIT_FAILURE_END_STRING = "BUILD FAILED";

	private static final String _LOCAL_GIT_FAILURE_START_STRING =
		"Too many retries while synchronizing GitHub pull request.";

}