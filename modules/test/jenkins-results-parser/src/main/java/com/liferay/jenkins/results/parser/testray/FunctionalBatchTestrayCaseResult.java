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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class FunctionalBatchTestrayCaseResult extends BatchTestrayCaseResult {

	public FunctionalBatchTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup,
		TestClassGroup.TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		if (!(testClass instanceof
				FunctionalBatchTestClassGroup.FunctionalTestClass)) {

			throw new RuntimeException(
				"TestClass needs to be an instance of FunctionalTestClass");
		}

		_functionalTestClass =
			(FunctionalBatchTestClassGroup.FunctionalTestClass)testClass;
	}

	@Override
	public String getName() {
		return _functionalTestClass.getTestClassMethodName();
	}

	@Override
	public Status getStatus() {
		TestResult testResult = getTestResult();

		if ((testResult == null) || testResult.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	public TestResult getTestResult() {
		Build build = getBuild();

		if (build == null) {
			return null;
		}

		TestClassResult testClassResult = build.getTestClassResult(
			"com.liferay.poshi.runner.PoshiRunner");

		if (testClassResult == null) {
			return null;
		}

		return testClassResult.getTestResult("test[" + getName() + "]");
	}

	private final FunctionalBatchTestClassGroup.FunctionalTestClass
		_functionalTestClass;

}