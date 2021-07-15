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

/**
 * @author Michael Hashimoto
 */
public class CucumberAxisBuild extends AxisBuild {

	public List<CucumberFeatureResult> getCucumberFeatureResults() {
		if (_cucumberFeatureResults != null) {
			return _cucumberFeatureResults;
		}

		Document document = _getDocument();

		if (document == null) {
			return new ArrayList<>();
		}

		_cucumberFeatureResults = new ArrayList<>();

		List<Node> nodes = Dom4JUtil.getNodesByXPath(
			document, "//tbody/tr/td[@class='tagname']");

		for (Node node : nodes) {
			CucumberFeatureResult cucumberFeatureResult =
				new CucumberFeatureResult(this, node);

			cucumberFeatureResult.getCucumberScenarioResults();

			_cucumberFeatureResults.add(cucumberFeatureResult);
		}

		return _cucumberFeatureResults;
	}

	public CucumberTestClassResult getCucumberTestClassResult(
		String cucumberTestName) {

		for (TestClassResult testClassResult : getTestClassResults()) {
			if (!(testClassResult instanceof CucumberTestClassResult)) {
				continue;
			}

			CucumberTestClassResult cucumberTestClassResult =
				(CucumberTestClassResult)testClassResult;

			if (cucumberTestName.equals(
					cucumberTestClassResult.getCucumberTestName())) {

				return cucumberTestClassResult;
			}
		}

		return null;
	}

	public List<CucumberTestResult> getCucumberTestResults(String testStatus) {
		List<CucumberTestResult> cucumberTestResults = new ArrayList<>();

		for (TestResult testResult : getTestResults()) {
			if (!(testResult instanceof CucumberTestResult) ||
				JenkinsResultsParserUtil.isNullOrEmpty(testStatus) ||
				!testStatus.equals(testResult.getStatus())) {

				continue;
			}

			cucumberTestResults.add((CucumberTestResult)testResult);
		}

		return cucumberTestResults;
	}

	@Override
	public List<TestClassResult> getTestClassResults() {
		List<TestClassResult> testClassResults = new ArrayList<>();

		for (CucumberScenarioResult cucumberScenarioResult :
				_getCucumberScenarioResults()) {

			testClassResults.add(
				TestClassResultFactory.newCucumberTestClassResultTestResult(
					this, cucumberScenarioResult));
		}

		return testClassResults;
	}

	@Override
	public List<TestResult> getTestResults() {
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

			if (content.contains("None report file was added!")) {
				return null;
			}

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