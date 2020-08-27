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

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestClassResult implements TestClassResult {

	public Build getBuild() {
		TestResult firstTestResult = _getFirstTestResult();

		return firstTestResult.getBuild();
	}

	@Override
	public String getClassName() {
		TestResult firstTestResult = _getFirstTestResult();

		return firstTestResult.getClassName();
	}

	@Override
	public long getDuration() {
		long duration = 0;

		for (TestResult testResult : _testResults) {
			duration += testResult.getDuration();
		}

		return duration;
	}

	@Override
	public String getPackageName() {
		TestResult firstTestResult = _getFirstTestResult();

		return firstTestResult.getPackageName();
	}

	@Override
	public String getSimpleClassName() {
		TestResult firstTestResult = _getFirstTestResult();

		return firstTestResult.getSimpleClassName();
	}

	@Override
	public String getStatus() {
		if (_status != null) {
			return _status.toString();
		}

		TestResult firstTestResult = _getFirstTestResult();

		_status = Status.valueOf(firstTestResult.getStatus());

		for (TestResult testResult : _testResults) {
			Status status = Status.valueOf(testResult.getStatus());

			if (_status.getPriority() <= status.getPriority()) {
				continue;
			}

			_status = status;
		}

		return _status.toString();
	}

	@Override
	public List<TestResult> getTestResults() {
		return _testResults;
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

	protected BaseTestClassResult(List<TestResult> testResults) {
		if ((testResults == null) || testResults.isEmpty()) {
			throw new RuntimeException("Please set a test results");
		}

		_testResults = testResults;

		String testClassName = getClassName();

		for (TestResult testResult : _testResults) {
			if (!testClassName.equals(testResult.getClassName())) {
				throw new RuntimeException(
					"Mismatched test class name " + testClassName);
			}
		}
	}

	private TestResult _getFirstTestResult() {
		return _testResults.get(0);
	}

	private Status _status;
	private final List<TestResult> _testResults;

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