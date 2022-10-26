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

import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import org.dom4j.Element;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestClassResult implements TestClassResult {

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public String getClassName() {
		List<TestResult> testResults = getTestResults();

		if (testResults.isEmpty()) {
			return _suiteJSONObject.getString("name");
		}

		TestResult testResult = testResults.get(0);

		return testResult.getClassName();
	}

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public String getPackageName() {
		String className = getClassName();

		int x = className.lastIndexOf(".");

		if (x < 0) {
			return "(root)";
		}

		return className.substring(0, x);
	}

	@Override
	public String getSimpleClassName() {
		String className = getClassName();

		int x = className.lastIndexOf(".");

		return className.substring(x + 1);
	}

	@Override
	public String getStatus() {
		if (_status != null) {
			return _status.toString();
		}

		_status = Status.PASSED;

		for (TestResult testResult : getTestResults()) {
			Status status = Status.valueOf(testResult.getStatus());

			if (_status.getPriority() <= status.getPriority()) {
				continue;
			}

			_status = status;
		}

		return _status.toString();
	}

	@Override
	public TestClass getTestClass() {
		if (_testClass != null) {
			return _testClass;
		}

		Build build = getBuild();

		if (!(build instanceof DownstreamBuild)) {
			return null;
		}

		DownstreamBuild downstreamBuild = (DownstreamBuild)build;

		AxisTestClassGroup axisTestClassGroup =
			downstreamBuild.getAxisTestClassGroup();

		if (axisTestClassGroup == null) {
			return null;
		}

		String className = getClassName();

		for (TestClass testClass : axisTestClassGroup.getTestClasses()) {
			if (!(testClass instanceof JUnitTestClass)) {
				continue;
			}

			JUnitTestClass jUnitTestClass = (JUnitTestClass)testClass;

			if (Objects.equals(className, jUnitTestClass.getTestClassName())) {
				_testClass = testClass;

				return _testClass;
			}
		}

		return null;
	}

	@Override
	public TestHistory getTestHistory() {
		TestClass testClass = getTestClass();

		if (testClass == null) {
			return null;
		}

		return testClass.getTestHistory();
	}

	@Override
	public TestResult getTestResult(String testName) {
		return _testResults.get(testName);
	}

	@Override
	public List<TestResult> getTestResults() {
		return new ArrayList<>(_testResults.values());
	}

	@Override
	public boolean isFailing() {
		Status status = Status.valueOf(getStatus());

		if ((status == Status.FIXED) || (status == Status.PASSED) ||
			(status == Status.SKIPPED)) {

			return false;
		}

		return true;
	}

	protected BaseTestClassResult(Build build, JSONObject suiteJSONObject) {
		if (suiteJSONObject == null) {
			throw new RuntimeException("Please set suiteJSONObject");
		}

		_build = build;
		_suiteJSONObject = suiteJSONObject;

		_duration = (long)(suiteJSONObject.getDouble("duration") * 1000);

		if (!suiteJSONObject.has("cases")) {
			return;
		}

		JSONArray casesJSONArray = suiteJSONObject.getJSONArray("cases");

		for (int i = 0; i < casesJSONArray.length(); i++) {
			JSONObject caseJSONObject = casesJSONArray.getJSONObject(i);

			TestResult testResult = TestResultFactory.newTestResult(
				build, caseJSONObject);

			_testResults.put(testResult.getTestName(), testResult);
		}
	}

	private final Build _build;
	private final long _duration;
	private Status _status;
	private final JSONObject _suiteJSONObject;
	private TestClass _testClass;
	private final Map<String, TestResult> _testResults = new TreeMap<>();

	private static enum Status {

		ABORTED(1), FAILED(2), FIXED(6), PASSED(7), REGRESSION(3), SKIPPED(5),
		UNSTABLE(4);

		public Integer getPriority() {
			return _priority;
		}

		private Status(Integer priority) {
			_priority = priority;
		}

		private final Integer _priority;

	}

}