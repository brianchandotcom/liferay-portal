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

import java.util.Collections;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class PlaywrightBatchBuildTestrayCaseResult
	extends BatchBuildTestrayCaseResult {

	public PlaywrightBatchBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass,
		TestClassMethod testClassMethod) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		_playwrightJUnitTestClass = (PlaywrightJUnitTestClass)testClass;
		_playwrightTestClassMethod = (PlaywrightTestClassMethod)testClassMethod;
	}

	@Override
	public String getComponentName() {
		String componentName =
			_playwrightJUnitTestClass.getTestrayMainComponentName();

		if (JenkinsResultsParserUtil.isNullOrEmpty(componentName)) {
			return super.getComponentName();
		}

		return componentName;
	}

	@Override
	public long getDuration() {
		return getTestResultDuration();
	}

	@Override
	public String getErrors() {
		return getTestResultErrors();
	}

	@Override
	public String getName() {
		if (_playwrightJUnitTestClass == null) {
			return super.getName();
		}

		return _playwrightTestClassMethod.getName();
	}

	@Override
	public Status getStatus() {
		return getTestResultStatus();
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments =
			super.getTestrayAttachments();

		testrayAttachments.add(getPlaywrightReportTestrayAttachment());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
	}

	@Override
	public TestResult getTestResult() {
		Build build = getBuild();

		if (build == null) {
			return null;
		}

		TestClassResult testClassResult = build.getTestClassResult(
			_playwrightJUnitTestClass.getSpecFilePath());

		if (testClassResult == null) {
			return null;
		}

		for (TestResult testResult : testClassResult.getTestResults()) {
			String fullTestName = JenkinsResultsParserUtil.combine(
				testClassResult.getClassName(), " > ",
				testResult.getTestName());

			if (fullTestName.equals(getName())) {
				return testResult;
			}
		}

		System.out.println("Unable to find test result for: " + getName());

		return null;
	}

	protected TestrayAttachment getPlaywrightReportTestrayAttachment() {
		return getTestrayAttachment(
			getBuild(), "Playwright Report",
			getAxisBuildURLPath() + "/playwright-report/index.html");
	}

	private final PlaywrightJUnitTestClass _playwrightJUnitTestClass;
	private final PlaywrightTestClassMethod _playwrightTestClassMethod;

}