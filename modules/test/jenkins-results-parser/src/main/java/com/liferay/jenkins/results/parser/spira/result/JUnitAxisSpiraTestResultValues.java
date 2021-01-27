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
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.BaseSpiraArtifact;
import com.liferay.jenkins.results.parser.spira.SpiraCustomProperty;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

/**
 * @author Michael Hashimoto
 */
public class JUnitAxisSpiraTestResultValues
	extends BaseAxisSpiraTestResultValues {

	protected JUnitAxisSpiraTestResultValues(
		JUnitAxisSpiraTestResult jUnitAxisSpiraTestResult) {

		super(jUnitAxisSpiraTestResult);

		_jUnitAxisSpiraTestResult = jUnitAxisSpiraTestResult;
	}

	@Override
	protected List<Callable<List<SpiraCustomPropertyValue>>> getCallables() {
		List<Callable<List<SpiraCustomPropertyValue>>> callables =
			super.getCallables();

		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(_getMethodsValue());
				}

			});

		return callables;
	}

	@Override
	protected SpiraCustomPropertyValue getErrorMessageValue() {
		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Error Message", SpiraCustomProperty.Type.TEXT, true);

		TestClassResult testClassResult =
			_jUnitAxisSpiraTestResult.getTestClassResult();

		if (testClassResult == null) {
			AxisBuild axisBuild = _jUnitAxisSpiraTestResult.getAxisBuild();

			if (axisBuild == null) {
				return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty, "The test class failed to run.");
			}

			String status = axisBuild.getResult();

			if (!status.equals("SUCCESS")) {
				return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty,
					"The build failed prior to running the test.");
			}

			return null;
		}

		if (!testClassResult.isFailing()) {
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

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, sb.toString());
	}

	private SpiraCustomPropertyValue _getMethodsValue() {
		TestClassGroup.TestClass testClass =
			_jUnitAxisSpiraTestResult.getTestClass();

		List<TestClassGroup.TestClass.TestClassMethod> testClassMethods =
			testClass.getTestClassMethods();

		StringBuilder sb = new StringBuilder();

		sb.append("<details><summary>methods (");
		sb.append(testClassMethods.size());
		sb.append(")</summary><hr /><ol>");

		List<String> testClassMethodNames = new ArrayList<>();

		for (TestClassGroup.TestClass.TestClassMethod testClassMethod :
				testClassMethods) {

			testClassMethodNames.add(testClassMethod.getName());
		}

		Collections.sort(testClassMethodNames);

		for (String testClassMethodName : testClassMethodNames) {
			sb.append("<li>");
			sb.append(BaseSpiraArtifact.fixStringForJSON(testClassMethodName));
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