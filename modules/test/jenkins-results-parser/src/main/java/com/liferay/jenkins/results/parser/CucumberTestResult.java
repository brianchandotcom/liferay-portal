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

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class CucumberTestResult extends BaseTestResult {

	@Override
	public String getClassName() {
		return _cucumberFeatureResult.getName();
	}

	public CucumberFeatureResult getCucumberFeatureResult() {
		return _cucumberFeatureResult;
	}

	public CucumberScenarioResult getCucumberScenarioResult() {
		return _cucumberScenarioResult;
	}

	@Override
	public String getDisplayName() {
		return _cucumberScenarioResult.getScenarioName();
	}

	@Override
	public long getDuration() {
		return _cucumberScenarioResult.getDuration();
	}

	public String getErrorDetails() {
		return _cucumberScenarioResult.getErrorDetails();
	}

	public String getErrorStackTrace() {
		return _cucumberScenarioResult.getErrorStacktrace();
	}

	@Override
	public Element getGitHubElement() {
		String testReportURL = getTestReportURL();

		Element downstreamBuildListItemElement = Dom4JUtil.getNewElement(
			"div", null);

		downstreamBuildListItemElement.add(
			Dom4JUtil.getNewAnchorElement(testReportURL, getDisplayName()));

		Dom4JUtil.addToElement(
			downstreamBuildListItemElement, " - ",
			Dom4JUtil.getNewAnchorElement(
				getConsoleOutputURL(), "Console Output"));

		String errorDetails = getErrorDetails();

		if ((errorDetails != null) && !errorDetails.isEmpty()) {
			Dom4JUtil.addToElement(
				downstreamBuildListItemElement,
				Dom4JUtil.toCodeSnippetElement(errorDetails));
		}

		if (hasLiferayLog()) {
			Dom4JUtil.addToElement(
				downstreamBuildListItemElement, " - ",
				Dom4JUtil.getNewAnchorElement(
					getLiferayLogURL(), "Liferay Log"));
		}

		return downstreamBuildListItemElement;
	}

	public String getPackageName() {
		return null;
	}

	public String getSimpleClassName() {
		return _cucumberFeatureResult.getName();
	}

	@Override
	public String getStatus() {
		return _cucumberScenarioResult.getStatus();
	}

	@Override
	public String getTestName() {
		return _cucumberScenarioResult.getScenarioName();
	}

	@Override
	public String getTestReportURL() {
		return _cucumberFeatureResult.getURL();
	}

	protected CucumberTestResult(
		Build build, CucumberScenarioResult cucumberScenarioResult) {

		super(build);

		if (cucumberScenarioResult == null) {
			throw new IllegalArgumentException("Scenario result is null");
		}

		_cucumberScenarioResult = cucumberScenarioResult;

		_cucumberFeatureResult =
			cucumberScenarioResult.getCucumberFeatureResult();
	}

	private final CucumberFeatureResult _cucumberFeatureResult;
	private final CucumberScenarioResult _cucumberScenarioResult;

}