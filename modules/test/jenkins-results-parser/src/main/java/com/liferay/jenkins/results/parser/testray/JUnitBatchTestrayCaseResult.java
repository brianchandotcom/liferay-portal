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
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class JUnitBatchTestrayCaseResult extends BatchTestrayCaseResult {

	public JUnitBatchTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup,
		TestClassGroup.TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		_testClass = testClass;
	}

	@Override
	public String getErrors() {
		TestClassResult testClassResult = getTestClassResult();

		if (testClassResult == null) {
			return "Failed to run on CI";
		}

		if (!testClassResult.isFailing()) {
			return null;
		}

		List<String> errorMessages = new ArrayList<>();

		for (TestResult testResult : testClassResult.getTestResults()) {
			if ((testResult == null) || !testResult.isFailing()) {
				continue;
			}

			StringBuilder sb = new StringBuilder();

			sb.append(testResult.getTestName());
			sb.append(": ");

			String errorDetails = testResult.getErrorDetails();

			if (!JenkinsResultsParserUtil.isNullOrEmpty(errorDetails)) {
				errorDetails = errorDetails.substring(
					0, errorDetails.indexOf("\n"));

				sb.append(errorDetails.trim());
			}
			else {
				sb.append("Failed for unknown reason");
			}

			errorMessages.add(sb.toString());
		}

		if (errorMessages.size() > 1) {
			return errorMessages.size() + " tests failed.";
		}
		else if (errorMessages.size() == 1) {
			return errorMessages.get(0);
		}

		return "Failed for unknown reason";
	}

	@Override
	public String getName() {
		String testClassName = JenkinsResultsParserUtil.getCanonicalPath(
			_testClass.getTestClassFile());

		testClassName = testClassName.replaceAll(
			".*/(com/liferay.*)\\.java", "$1");

		return testClassName.replace("/", ".");
	}

	@Override
	public Status getStatus() {
		TestClassResult testClassResult = getTestClassResult();

		if ((testClassResult == null) || testClassResult.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	public TestClassResult getTestClassResult() {
		Build build = getBuild();

		String result = build.getResult();

		if (!result.equals("SUCCESS") && !result.equals("UNSTABLE")) {
			return null;
		}

		return build.getTestClassResult(getName());
	}

	private final TestClassGroup.TestClass _testClass;

}