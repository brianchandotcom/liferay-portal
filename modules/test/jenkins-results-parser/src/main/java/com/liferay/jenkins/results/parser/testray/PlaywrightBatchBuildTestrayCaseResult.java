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

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String errors = null;

		Build build = getBuild();

		TestResult testResult = getTestResult();

		if (testResult == null) {
			if (build == null) {
				return "Unable to run build on CI";
			}

			errors = "Failed prior to running test";

			String result = build.getResult();

			if (result == null) {
				errors = "Unable to finish build on CI";
			}

			if (result.equals("ABORTED")) {
				errors = build.getJobName() + " timed out after 2 hours";
			}

			if (result.equals("SUCCESS") || result.equals("UNSTABLE")) {
				errors = "Unable to run test on CI";
			}

			String failureMessage = build.getFailureMessage();

			if (JenkinsResultsParserUtil.isNullOrEmpty(failureMessage)) {
				return errors;
			}

			return errors + ": " + failureMessage;
		}

		if (testResult.isSkipped()) {
			return "Failed to run test on CI";
		}

		if (!testResult.isFailing()) {
			return null;
		}

		errors = testResult.getErrorDetails();

		if (JenkinsResultsParserUtil.isNullOrEmpty(errors)) {
			errors = build.getFailureMessage();
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(errors)) {
			return "Failed for unknown reason";
		}

		String stackTrace = testResult.getErrorStackTrace();

		if (stackTrace.length() > 500) {
			int index = stackTrace.indexOf("›");

			return stackTrace.substring(index, 500);
		}

		if (errors.contains("\n")) {
			errors = errors.substring(0, errors.indexOf("\n"));
		}

		errors = errors.trim();

		if (JenkinsResultsParserUtil.isNullOrEmpty(errors)) {
			return "Failed for unknown reason";
		}

		return errors;
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

		if (getPlaywrightTraceZip() != null) {
			testrayAttachments.add(getPlaywrightTraceZip());
		}

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

	protected TestrayAttachment getPlaywrightTraceZip() {
		Matcher matcher = _traceZipDirPattern.matcher(
			_playwrightJUnitTestClass.getSpecFilePath());
		StringBuilder sb = new StringBuilder();

		if (matcher.matches()) {
			String fullTestName = getName();

			String testName = fullTestName.substring(
				fullTestName.indexOf(">") + 1);

			testName = testName.trim();
			testName = testName.replace(" ", "-");

			sb.append(getAxisBuildURLPath());
			sb.append("/test-results/");
			sb.append(matcher.group("fileName"));
			sb.append("-");

			sb.append(testName);
			sb.append("-");

			String projectDir = matcher.group("projectDir");

			sb.append(projectDir.replace("/", "-"));

			sb.append("/trace.zip");
		}

		try {
			URL url = new URL(
				"https://playwright.liferay.com/?trace=" +
					"https://playwright.liferay.com/testray-results/" +
						sb.toString());

			return new DefaultTestrayAttachment(
				this, "Trace Viewer", sb.toString(), url);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private static final Pattern _traceZipDirPattern = Pattern.compile(
		"(?<projectDir>\\S*/\\S*)/(?<fileName>\\S*)\\.spec\\.ts");

	private final PlaywrightJUnitTestClass _playwrightJUnitTestClass;
	private final PlaywrightTestClassMethod _playwrightTestClassMethod;

}