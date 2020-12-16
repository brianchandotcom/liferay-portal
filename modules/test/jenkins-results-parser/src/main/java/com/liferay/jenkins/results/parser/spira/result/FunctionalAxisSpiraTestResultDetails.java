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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class FunctionalAxisSpiraTestResultDetails
	extends BaseAxisSpiraTestResultDetails {

	protected FunctionalAxisSpiraTestResultDetails(
		FunctionalAxisSpiraTestResult functionalAxisSpiraTestResult) {

		super(functionalAxisSpiraTestResult);

		_functionalAxisSpiraTestResult = functionalAxisSpiraTestResult;
	}

	@Override
	protected List<String> getTestrayListItems() {
		List<String> testrayListItems = super.getTestrayListItems();

		String artifactBaseURLContent = getArtifactBaseURLContent();

		String testClassMethodName =
			_functionalAxisSpiraTestResult.getTestClassMethodName();

		testClassMethodName = testClassMethodName.replace("#", "_");

		if (!artifactBaseURLContent.contains(testClassMethodName)) {
			return testrayListItems;
		}

		testrayListItems.add(
			JenkinsResultsParserUtil.combine(
				"<li>Poshi Report: <a href=\"", getArtifactBaseURL(), "/",
				testClassMethodName,
				"/index.html.gz\" target=\"_blank\">index.html</a></li>"));

		testrayListItems.add(
			JenkinsResultsParserUtil.combine(
				"<li>Poshi Summary: <a href=\"", getArtifactBaseURL(), "/",
				testClassMethodName,
				"/summary.html.gz\" target=\"_blank\">summary.html</a></li>"));

		return testrayListItems;
	}

	private final FunctionalAxisSpiraTestResult _functionalAxisSpiraTestResult;

}