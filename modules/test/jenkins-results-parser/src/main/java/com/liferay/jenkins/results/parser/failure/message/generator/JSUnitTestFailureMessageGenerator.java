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

import com.liferay.jenkins.results.parser.Dom4JUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Brittney Nguyen
 */
public class JSUnitTestFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessageElement(String consoleText) {
		Matcher buildFailuresMatcher = _buildFailuresPattern.matcher(
			consoleText);

		Matcher packageFailureMatcher = _packageFailurePattern.matcher(
			consoleText);

		if (!buildFailuresMatcher.find() && !packageFailureMatcher.find()) {
			return null;
		}

		List<Element> elementList = new ArrayList<>();

		List<String> packageFailureList = new ArrayList<>();

		while (packageFailureMatcher.find()) {
			String packageFailure = packageFailureMatcher.group(0);

			Element element = new DefaultElement("element");

			element.addText(packageFailure);

			if ((element != null) &&
				!packageFailureList.contains(packageFailure)) {

				elementList.add(element);
				packageFailureList.add(packageFailure);
			}
		}

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null,
				Dom4JUtil.toCodeSnippetElement(buildFailuresMatcher.group(0)),
				Dom4JUtil.getOrderedListElement(elementList, 7)));
	}

	private static final Pattern _buildFailuresPattern = Pattern.compile(
		"FAILURE: Build completed with \\d+ failure[s]?");
	private static final Pattern _packageFailurePattern = Pattern.compile(
		"Execution failed for task '[\\D]+:packageRunTest'");

}