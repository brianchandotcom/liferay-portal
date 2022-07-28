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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class CucumberTestClassResult implements TestClassResult {

	@Override
	public Build getBuild() {
		return _build;
	}

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

	public String getCucumberTestName() {
		return JenkinsResultsParserUtil.combine(
			_cucumberFeatureResult.getName(), " > ",
			_cucumberScenarioResult.getScenarioName());
	}

	public CucumberTestResult getCucumberTestResult(String testName) {
		for (TestResult testResult : getTestResults()) {
			if (testName.equals(testResult.getTestName())) {
				return (CucumberTestResult)testResult;
			}
		}

		return null;
	}

	@Override
	public long getDuration() {
		return _cucumberScenarioResult.getDuration();
	}

	@Override
	public long getOverheadDuration() {
		return 0L;
	}

	@Override
	public String getPackageName() {
		return null;
	}

	@Override
	public String getSimpleClassName() {
		return _cucumberFeatureResult.getName();
	}

	@Override
	public String getStatus() {
		return _cucumberScenarioResult.getStatus();
	}

	@Override
	public TestResult getTestResult(String testName) {
		for (TestResult testResult : getTestResults()) {
			if (testName.equals(testResult.getTestName())) {
				return testResult;
			}
		}

		return null;
	}

	@Override
	public List<TestResult> getTestResults() {
		return _testResults;
	}

	@Override
	public boolean isFailing() {
		for (TestResult testResult : getTestResults()) {
			if (testResult.isFailing()) {
				return true;
			}
		}

		return false;
	}

	protected CucumberTestClassResult(
		Build build, CucumberScenarioResult cucumberScenarioResult) {

		_build = build;

		if (cucumberScenarioResult == null) {
			throw new IllegalArgumentException("Scenario result is null");
		}

		_cucumberScenarioResult = cucumberScenarioResult;

		_cucumberFeatureResult =
			cucumberScenarioResult.getCucumberFeatureResult();

		_testResults.add(
			TestResultFactory.newCucumberTestResultTestResult(
				build, cucumberScenarioResult));
	}

	private final Build _build;
	private final CucumberFeatureResult _cucumberFeatureResult;
	private final CucumberScenarioResult _cucumberScenarioResult;
	private final List<TestResult> _testResults = new ArrayList<>();

}