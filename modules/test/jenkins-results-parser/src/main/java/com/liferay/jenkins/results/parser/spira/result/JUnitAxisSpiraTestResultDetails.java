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
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.BaseSpiraArtifact;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Michael Hashimoto
 */
public class JUnitAxisSpiraTestResultDetails
	extends BaseAxisSpiraTestResultDetails {

	protected JUnitAxisSpiraTestResultDetails(
		JUnitAxisSpiraTestResult jUnitAxisSpiraTestResult) {

		super(jUnitAxisSpiraTestResult);

		_jUnitAxisSpiraTestResult = jUnitAxisSpiraTestResult;
	}

	@Override
	protected String getTestFailuresSummary() {
		Map<String, String> failedTestResultMap = new TreeMap<>();

		List<TestResult> commonFailedTestResults =
			_jUnitAxisSpiraTestResult.getCommonFailedTestResults();

		for (TestResult testResult : commonFailedTestResults) {
			String testResultMethodName = JenkinsResultsParserUtil.combine(
				testResult.getSimpleClassName(), ".", testResult.getTestName());

			String testResultFailure = JenkinsResultsParserUtil.combine(
				BaseSpiraArtifact.fixStringForJSON(
					testResult.getErrorDetails()),
				"\n", testResult.getErrorStackTrace());

			failedTestResultMap.put(testResultMethodName, testResultFailure);
		}

		Map<String, TestResult> testResultMap = new HashMap<>();

		TestClassResult testClassResult =
			_jUnitAxisSpiraTestResult.getTestClassResult();

		if (testClassResult != null) {
			for (TestResult testResult : testClassResult.getTestResults()) {
				if (testResult == null) {
					continue;
				}

				testResultMap.put(testResult.getTestName(), testResult);
			}
		}

		TestClassGroup.TestClass testClass =
			_jUnitAxisSpiraTestResult.getTestClass();

		File testClassFile = testClass.getTestClassFile();

		String testClassName = testClassFile.getName();

		testClassName = testClassName.replace(".class", "");

		for (TestClassGroup.TestClass.TestClassMethod testClassMethod :
				testClass.getTestClassMethods()) {

			String testMethodName = testClassMethod.getName();

			TestResult testResult = testResultMap.get(testMethodName);

			if ((testResult != null) && !testResult.isFailing()) {
				continue;
			}

			String testMethodFailure = "Failed to run.";

			if (testResult != null) {
				testMethodFailure = JenkinsResultsParserUtil.combine(
					BaseSpiraArtifact.fixStringForJSON(
						testResult.getErrorDetails()),
					"\n", testResult.getErrorStackTrace());
			}

			failedTestResultMap.put(
				testClassName + "." + testMethodName, testMethodFailure);
		}

		if (failedTestResultMap.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<h4>Test Failures</h4><ul>");

		for (Map.Entry<String, String> failedTestResultEntry :
				failedTestResultMap.entrySet()) {

			sb.append("<li><details><summary>");
			sb.append(failedTestResultEntry.getKey());
			sb.append("</summary><pre>");
			sb.append(failedTestResultEntry.getValue());
			sb.append("</pre></details></li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private final JUnitAxisSpiraTestResult _jUnitAxisSpiraTestResult;

}