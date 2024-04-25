/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Kenji Heigel
 */
public class PlaywrightBatchBuildTestrayCaseResult
	extends BatchBuildTestrayCaseResult {

	public PlaywrightBatchBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		_testClass = testClass;
	}

	@Override
	public String getComponentName() {
		PlaywrightTestClass playwrightTestClass = _getPlaywrightTestClass();

		if (playwrightTestClass == null) {
			return super.getComponentName();
		}

		String testrayMainComponentName =
			playwrightTestClass.getTestrayMainComponentName();

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayMainComponentName)) {
			return super.getComponentName();
		}

		return testrayMainComponentName;
	}

	@Override
	public String getErrors() {
		TestResult testResult = _getTestResult();

		if (testResult == null) {
			return super.getErrors();
		}

		if (!testResult.isFailing()) {
			return null;
		}

		return testResult.getErrorStackTrace();
	}

	@Override
	public String getName() {
		PlaywrightTestClass playwrightTestClass = _getPlaywrightTestClass();

		if (playwrightTestClass == null) {
			return super.getName();
		}

		return playwrightTestClass.getSpecFilePath();
	}

	@Override
	public Status getStatus() {
		TestResult testResult = _getTestResult();

		if (testResult == null) {
			return Status.UNTESTED;
		}

		if (testResult.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments =
			super.getTestrayAttachments();

		testrayAttachments.addAll(getLiferayLogTestrayAttachments());
		testrayAttachments.addAll(getLiferayOSGiLogTestrayAttachments());

		testrayAttachments.add(getPlaywrightReportTestrayAttachment());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
	}

	protected TestrayAttachment getPlaywrightReportTestrayAttachment() {
		return getTestrayAttachment(
			getBuild(), "Playwright Report",
			getAxisBuildURLPath() + "/playwright-report/index.html");
	}

	private PlaywrightTestClass _getPlaywrightTestClass() {
		if (!(_testClass instanceof PlaywrightTestClass)) {
			return null;
		}

		return (PlaywrightTestClass)_testClass;
	}

	private TestResult _getTestResult() {
		Build build = getBuild();

		if (build == null) {
			return null;
		}

		PlaywrightTestClass playwrightTestClass = _getPlaywrightTestClass();

		if (playwrightTestClass == null) {
			return null;
		}

		String specFilePath = playwrightTestClass.getSpecFilePath();
		String specTitle = playwrightTestClass.getSpecTitle();

		for (TestResult testResult : build.getTestResults()) {
			if (Objects.equals(specFilePath, testResult.getClassName()) &&
				Objects.equals(specTitle, testResult.getTestName())) {

				return testResult;
			}
		}

		return null;
	}

	private final TestClass _testClass;

}