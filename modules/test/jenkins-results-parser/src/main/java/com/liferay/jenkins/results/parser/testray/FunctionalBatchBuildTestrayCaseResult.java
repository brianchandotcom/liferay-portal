/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.FunctionalTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class FunctionalBatchBuildTestrayCaseResult
	extends BatchBuildTestrayCaseResult {

	public FunctionalBatchBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		if (!(testClass instanceof FunctionalTestClass)) {
			throw new RuntimeException(
				"Test class is not a functional test class");
		}

		_functionalTestClass = (FunctionalTestClass)testClass;
	}

	@Override
	public String getComponentName() {
		return JenkinsResultsParserUtil.getProperty(
			_functionalTestClass.getPoshiProperties(),
			"testray.main.component.name");
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
		return _functionalTestClass.getTestClassMethodName();
	}

	@Override
	public int getPriority() {
		String priority = JenkinsResultsParserUtil.getProperty(
			_functionalTestClass.getPoshiProperties(), "priority");

		if ((priority != null) && priority.matches("\\d+")) {
			return Integer.parseInt(priority);
		}

		return 5;
	}

	@Override
	public Status getStatus() {
		TestResult testResult = getTestResult();

		if (testResult != null) {
			String errorDetails = testResult.getErrorDetails();

			if (!JenkinsResultsParserUtil.isNullOrEmpty(errorDetails) &&
				errorDetails.contains("TEST_SETUP_ERROR:")) {

				return Status.BLOCKED;
			}
		}

		return getTestResultStatus();
	}

	@Override
	public String getSubcomponentNames() {
		return JenkinsResultsParserUtil.getProperty(
			_functionalTestClass.getPoshiProperties(),
			"testray.component.names");
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments =
			super.getTestrayAttachments();

		testrayAttachments.addAll(getLiferayLogTestrayAttachments());
		testrayAttachments.addAll(getLiferayOSGiLogTestrayAttachments());

		testrayAttachments.add(_getPoshiConsoleTestrayAttachment());
		testrayAttachments.add(_getPoshiReportTestrayAttachment());
		testrayAttachments.add(_getPoshiSummaryTestrayAttachment());

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
			"com.liferay.poshi.runner.PoshiRunner");

		if (testClassResult == null) {
			testClassResult = build.getTestClassResult(
				"com.liferay.poshi.runner.ParallelPoshiRunner");
		}

		if (testClassResult == null) {
			return null;
		}

		return testClassResult.getTestResult("test[" + getName() + "]");
	}

	@Override
	protected List<TestrayAttachment> getLiferayLogTestrayAttachments() {
		if (getTestResult() == null) {
			return new ArrayList<>();
		}

		return super.getLiferayLogTestrayAttachments();
	}

	@Override
	protected List<TestrayAttachment> getLiferayOSGiLogTestrayAttachments() {
		if (getTestResult() == null) {
			return new ArrayList<>();
		}

		return super.getLiferayOSGiLogTestrayAttachments();
	}

	private TestrayAttachment _getPoshiConsoleTestrayAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		name = name.replace("#", "_");

		return getTestrayAttachment(
			getBuild(), "Poshi Console",
			JenkinsResultsParserUtil.combine(
				getAxisBuildURLPath(), "/",
				JenkinsResultsParserUtil.fixURL(name), "/console.txt.gz"));
	}

	private TestrayAttachment _getPoshiReportTestrayAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		name = name.replace("#", "_");

		return getTestrayAttachment(
			getBuild(), "Poshi Report",
			JenkinsResultsParserUtil.combine(
				getAxisBuildURLPath(), "/",
				JenkinsResultsParserUtil.fixURL(name), "/index.html.gz"));
	}

	private TestrayAttachment _getPoshiSummaryTestrayAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		name = name.replace("#", "_");

		return getTestrayAttachment(
			getBuild(), "Poshi Summary",
			JenkinsResultsParserUtil.combine(
				getAxisBuildURLPath(), "/",
				JenkinsResultsParserUtil.fixURL(name), "/summary.html.gz"));
	}

	private final FunctionalTestClass _functionalTestClass;

}