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

import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.CucumberAxisBuild;
import com.liferay.jenkins.results.parser.CucumberTestResult;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.BaseSpiraArtifact;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseFolder;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberBatchTestClassGroup;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class CucumberAxisSpiraTestResult extends BaseAxisSpiraTestResult {

	public CucumberAxisBuild getCucumberAxisBuild() {
		AxisBuild axisBuild = getAxisBuild();

		if (!(axisBuild instanceof CucumberAxisBuild)) {
			return null;
		}

		return (CucumberAxisBuild)axisBuild;
	}

	public CucumberTestResult getCucumberTestResult() {
		CucumberAxisBuild cucumberAxisBuild = getCucumberAxisBuild();

		return cucumberAxisBuild.getCucumberTestResult(
			_cucumberTestClass.getScenarioName());
	}

	@Override
	public List<TestResult> getFailedTestResults() {
		List<TestResult> failedTestResults = getCommonFailedTestResults();

		CucumberTestResult cucumberTestResult = getCucumberTestResult();

		if (cucumberTestResult.isFailing()) {
			failedTestResults.add(cucumberTestResult);
		}

		return failedTestResults;
	}

	@Override
	public String getTestName() {
		return JenkinsResultsParserUtil.combine(
			_cucumberTestClass.getFeatureName(), " > ",
			_cucumberTestClass.getScenarioName());
	}

	protected CucumberAxisSpiraTestResult(
		SpiraBuildResult spiraBuildResult,
		CucumberAxisTestClassGroup axisTestClassGroup,
		CucumberBatchTestClassGroup.CucumberTestClass cucumberTestClass) {

		super(spiraBuildResult, axisTestClassGroup);

		_cucumberTestClass = cucumberTestClass;
	}

	@Override
	protected String getSpiraTestCasePath() {
		StringBuilder sb = new StringBuilder();

		SpiraTestCaseFolder spiraTestCaseFolder =
			spiraBuildResult.getSpiraTestCaseFolder();

		if (spiraTestCaseFolder != null) {
			sb.append(spiraTestCaseFolder.getPath());
		}

		for (String categoryName : _cucumberTestClass.getCategoryNames()) {
			sb.append("/");
			sb.append(BaseSpiraArtifact.fixStringForJSON(categoryName));
		}

		sb.append("/");
		sb.append(BaseSpiraArtifact.fixStringForJSON(getTestName()));

		return sb.toString();
	}

	private final CucumberBatchTestClassGroup.CucumberTestClass
		_cucumberTestClass;

}