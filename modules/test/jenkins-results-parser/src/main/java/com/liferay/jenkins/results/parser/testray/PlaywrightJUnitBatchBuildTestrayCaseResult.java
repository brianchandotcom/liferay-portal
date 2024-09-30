/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightTestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class PlaywrightJUnitBatchBuildTestrayCaseResult
	extends JUnitBatchBuildTestrayCaseResult {

	public PlaywrightJUnitBatchBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass,
		TestClassMethod testClassMethod) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup, testClass);

		_playwrightJUnitTestClass = (PlaywrightJUnitTestClass)testClass;

		_playwrightTestClassMethod = (PlaywrightTestClassMethod)testClassMethod;
	}

	@Override
	public String getName() {
		if (_playwrightJUnitTestClass == null) {
			return super.getName();
		}

		return _playwrightTestClassMethod.getName();
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments =
			super.getTestrayAttachments();

		testrayAttachments.add(getPlaywrightReportTestrayAttachment());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
	}

	protected TestrayAttachment getPlaywrightReportTestrayAttachment() {
		return getTestrayAttachment(
			getBuild(), "Playwright Report",
			getAxisBuildURLPath() + "/playwright-report/index.html");
	}

	@Override
	protected List<TestClassResult> getTestClassResults() {
		if (_testClassResults != null) {
			return _testClassResults;
		}

		Build build = getBuild();

		if (build == null) {
			return null;
		}

		_testClassResults = new ArrayList<>();

		for (TestClassResult testClassResult : build.getTestClassResults()) {
			String testClassName = testClassResult.getClassName();

			for (TestResult testResult : testClassResult.getTestResults()) {
				String fullTestName = JenkinsResultsParserUtil.combine(
					testClassName, " > ", testResult.getTestName());

				if (fullTestName.equals(getName())) {
					_testClassResults.add(testClassResult);

					break;
				}
			}
		}

		return _testClassResults;
	}

	@Override
	protected List<TestResult> getTestResults() {
		List<TestResult> testResults = new ArrayList<>();

		for (TestClassResult testClassResult : getTestClassResults()) {
			String testClassName = testClassResult.getClassName();

			List<TestResult> results = testClassResult.getTestResults();

			for (TestResult testResult : results) {
				String testName = JenkinsResultsParserUtil.combine(
					testClassName, " > ", testResult.getTestName());

				if (testName.equals(getName())) {
					testResults.add(testResult);
				}
			}
		}

		return testResults;
	}

	private final PlaywrightJUnitTestClass _playwrightJUnitTestClass;
	private final PlaywrightTestClassMethod _playwrightTestClassMethod;
	private List<TestClassResult> _testClassResults;

}