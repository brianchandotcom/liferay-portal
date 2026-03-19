/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Calum Ragan
 */
public class GitForcePushFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		Matcher matcher = _unableToCreateLocalBranchPattern.matcher(
			consoleText);

		if (!matcher.find()) {
			return null;
		}

		int start = matcher.start();

		start = consoleText.lastIndexOf("\n", start);

		int end = consoleText.indexOf("\n", matcher.end());

		return getConsoleTextSnippet(consoleText, false, start, end);
	}

	@Override
	public Element getMessageElement(Build build) {
		Element messageElement = super.getMessageElement(build);

		if (messageElement == null) {
			return null;
		}

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Please check if ",
				Dom4JUtil.getNewElement(
					"strong", null,
					getBaseBranchAnchorElement(build.getTopLevelBuild())),
				Dom4JUtil.getNewElement("strong", null, " was force pushed.")),
			messageElement);
	}

	private static final Pattern _unableToCreateLocalBranchPattern =
		Pattern.compile(
			"Unable to create local branch [\\w.-]+ at [a-zA-Z0-9]+");

}