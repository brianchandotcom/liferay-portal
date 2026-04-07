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
 * @author Michael Hashimoto
 */
public class PlaywrightTimeoutFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public String getMessage(String consoleText) {
		Matcher matcher = _pattern.matcher(consoleText);

		if (!matcher.find()) {
			return null;
		}

		return "Playwright test timed out: " + matcher.group(0);
	}

	@Override
	public Element getMessageElement(Build build) {
		String jobVariant = build.getJobVariant();

		if (!jobVariant.contains("playwright")) {
			return null;
		}

		return getMessageElement(build.getConsoleText());
	}

	@Override
	public Element getMessageElement(String consoleText) {
		Matcher matcher = _pattern.matcher(consoleText);

		String errorMessage = getMessage(consoleText);

		if (!matcher.find() || (errorMessage == null)) {
			return null;
		}

		Element messageElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null,
				Dom4JUtil.getNewElement("strong", null, errorMessage)));

		String snippet = matcher.group(0);

		messageElement.add(
			getConsoleTextSnippetElement(snippet, false, 0, snippet.length()));

		return messageElement;
	}

	private static final Pattern _pattern = Pattern.compile(
		"Test timeout of \\d+ms exceeded\\.");

}