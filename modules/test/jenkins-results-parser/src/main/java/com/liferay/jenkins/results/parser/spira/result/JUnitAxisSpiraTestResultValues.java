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

import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.BaseSpiraArtifact;
import com.liferay.jenkins.results.parser.spira.SpiraCustomProperty;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class JUnitAxisSpiraTestResultValues
	extends BaseAxisSpiraTestResultValues {

	@Override
	public List<SpiraCustomPropertyValue> getSpiraCustomPropertyValues() {
		List<SpiraCustomPropertyValue> spiraCustomPropertyValues =
			super.getSpiraCustomPropertyValues();

		spiraCustomPropertyValues.add(_getErrorMessageValue());
		spiraCustomPropertyValues.add(_getMethodsValue());

		spiraCustomPropertyValues.removeAll(Collections.singleton(null));

		return spiraCustomPropertyValues;
	}

	protected JUnitAxisSpiraTestResultValues(
		JUnitAxisSpiraTestResult jUnitAxisSpiraTestResult) {

		super(jUnitAxisSpiraTestResult);

		_jUnitAxisSpiraTestResult = jUnitAxisSpiraTestResult;
	}

	private SpiraCustomPropertyValue _getErrorMessageValue() {
		TestClassResult testClassResult =
			_jUnitAxisSpiraTestResult.getTestClassResult();

		if ((testClassResult == null) || !testClassResult.isFailing()) {
			return null;
		}

		int errorCount = 0;

		Map<String, List<TestResult>> errorTestResultsMap = new HashMap<>();

		for (TestResult testClassTestResult :
				testClassResult.getTestResults()) {

			if ((testClassTestResult == null) ||
				!testClassTestResult.isFailing()) {

				continue;
			}

			errorCount++;

			String errorMessage = testClassTestResult.getErrorDetails();

			if (errorMessage == null) {
				errorMessage = "No error message.";
			}

			errorMessage = errorMessage.trim();

			if (!errorMessage.isEmpty() && errorMessage.contains("\n")) {
				errorMessage = errorMessage.substring(
					0, errorMessage.indexOf("\n"));
			}

			List<TestResult> errorTestResults = errorTestResultsMap.get(
				errorMessage);

			if (errorTestResults == null) {
				errorTestResults = new ArrayList<>();
			}

			errorTestResults.add(testClassTestResult);

			errorTestResultsMap.put(errorMessage, errorTestResults);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<details><summary>errors (");
		sb.append(errorTestResultsMap.size());
		sb.append(") | tests (");
		sb.append(errorCount);
		sb.append(")</summary><hr /><ol>");

		for (Map.Entry<String, List<TestResult>> errorTestResultsEntry :
				errorTestResultsMap.entrySet()) {

			sb.append("<li>");
			sb.append("<pre>");

			sb.append(
				BaseSpiraArtifact.fixStringForJSON(
					errorTestResultsEntry.getKey()));

			sb.append("</pre><ol>");

			Set<String> errorTestNames = new TreeSet<>();

			for (TestResult errorTestResult :
					errorTestResultsEntry.getValue()) {

				errorTestNames.add(errorTestResult.getTestName());
			}

			for (String errorTestName : errorTestNames) {
				sb.append("<li>");
				sb.append(BaseSpiraArtifact.fixStringForJSON(errorTestName));
				sb.append("</li>");
			}

			sb.append("</ol></li>");
		}

		sb.append("</ol></details>");

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Error Message", SpiraCustomProperty.Type.TEXT, true),
			sb.toString());
	}

	private SpiraCustomPropertyValue _getMethodsValue() {
		TestClassResult testClassResult =
			_jUnitAxisSpiraTestResult.getTestClassResult();

		if (testClassResult == null) {
			return null;
		}

		List<TestResult> testResults = testClassResult.getTestResults();

		if ((testResults == null) || testResults.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<details><summary>methods (");
		sb.append(testResults.size());
		sb.append(")</summary><hr /><ol>");

		List<String> testResultNames = new ArrayList<>();

		for (TestResult testResult : testResults) {
			testResultNames.add(testResult.getTestName());
		}

		Collections.sort(testResultNames);

		for (String testResultName : testResultNames) {
			sb.append("<li>");
			sb.append(BaseSpiraArtifact.fixStringForJSON(testResultName));
			sb.append("</li>");
		}

		sb.append("</ol></details>");

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Methods", SpiraCustomProperty.Type.TEXT, true),
			sb.toString());
	}

	private final JUnitAxisSpiraTestResult _jUnitAxisSpiraTestResult;

}