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

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;

import org.json.JSONArray;

/**
 * @author Michael Hashimoto
 */
public class CucumberAxisBuild extends AxisBuild {

	public List<CucumberFeatureResult> getCucumberFeatureResults() {
		if (_cucumberFeatureResults != null) {
			return _cucumberFeatureResults;
		}

		_cucumberFeatureResults = new ArrayList<>();

		List<Node> nodes = Dom4JUtil.getNodesByXPath(
			_getDocument(), "//tbody/tr/td[@class='tagname']");

		for (Node node : nodes) {
			CucumberFeatureResult cucumberFeatureResult =
				new CucumberFeatureResult(this, node);

			cucumberFeatureResult.getCucumberScenarioResults();

			_cucumberFeatureResults.add(cucumberFeatureResult);
		}

		return _cucumberFeatureResults;
	}

	public CucumberTestResult getCucumberTestResult(String testName) {
		TestResult testResult = getTestResult(testName);

		if (!(testResult instanceof CucumberTestResult)) {
			return null;
		}

		return (CucumberTestResult)testResult;
	}

	public List<CucumberTestResult> getCucumberTestResults(String testStatus) {
		List<CucumberTestResult> cucumberTestResults = new ArrayList<>();

		for (TestResult testResult : getTestResults(testStatus)) {
			if (!(testResult instanceof CucumberTestResult)) {
				continue;
			}

			cucumberTestResults.add((CucumberTestResult)testResult);
		}

		return cucumberTestResults;
	}

	@Override
	public List<TestResult> getTestResults(
		Build build, JSONArray suitesJSONArray) {

		List<TestResult> testResults = new ArrayList<>();

		for (CucumberScenarioResult cucumberScenarioResult :
				_getCucumberScenarioResults()) {

			testResults.add(
				TestResultFactory.newCucumberTestResultTestResult(
					this, cucumberScenarioResult));
		}

		return testResults;
	}

	protected CucumberAxisBuild(String url, BatchBuild parentBatchBuild) {
		super(JenkinsResultsParserUtil.getLocalURL(url), parentBatchBuild);
	}

	private List<CucumberScenarioResult> _getCucumberScenarioResults() {
		List<CucumberScenarioResult> cucumberScenarioResults =
			new ArrayList<>();

		for (CucumberFeatureResult cucumberFeatureResult :
				getCucumberFeatureResults()) {

			cucumberScenarioResults.addAll(
				cucumberFeatureResult.getCucumberScenarioResults());
		}

		return cucumberScenarioResults;
	}

	private Document _getDocument() {
		if (_document != null) {
			return _document;
		}

		try {
			String content = JenkinsResultsParserUtil.toString(
				getBuildURL() +
					"/cucumber-html-reports/overview-features.html");

			content = content.replaceAll("&nbsp;", " ");
			content = content.replaceAll("<br>", "<br/>");

			int x = content.indexOf("<table id=\"tablesorter\"");

			String end = "</table>";

			int y = content.indexOf(end, x) + end.length();

			_document = Dom4JUtil.parse(content.substring(x, y));

			return _document;
		}
		catch (DocumentException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private List<CucumberFeatureResult> _cucumberFeatureResults;
	private Document _document;

}