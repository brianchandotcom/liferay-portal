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
import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class JUnitBatchBuildTestrayCaseResult
	extends BatchBuildTestrayCaseResult {

	public JUnitBatchBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		_jUnitTestClass = (JUnitTestClass)testClass;
	}

	@Override
	public String getComponentName() {
		String componentName = _jUnitTestClass.getTestrayMainComponentName();

		if (JenkinsResultsParserUtil.isNullOrEmpty(componentName)) {
			return super.getComponentName();
		}

		return componentName;
	}

	@Override
	public long getDuration() {
		List<TestClassResult> testClassResults = getTestClassResults();

		if (testClassResults == null) {
			return 0;
		}

		long duration = 0;

		for (TestClassResult testClassResult : testClassResults) {
			duration += testClassResult.getDuration();
		}

		return duration;
	}

	@Override
	public String getErrors() {
		Build build = getBuild();

		List<TestClassResult> testClassResults = getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
			if (build == null) {
				return "Unable to run build on CI";
			}

			String result = build.getResult();

			if (result == null) {
				return "Unable to finish build on CI";
			}

			if (result.equals("ABORTED")) {
				return build.getJobName() + " timed out after 2 hours";
			}

			if (result.equals("SUCCESS") || result.equals("UNSTABLE")) {
				return "Unable to run test on CI";
			}

			return "Failed prior to running test";
		}

		if (_isTestClassResultsSkipped()) {
			return "Failed prior to running test";
		}

		if (!_isTestClassResultsFailing()) {
			return null;
		}

		Map<String, String> errorMessages = new HashMap<>();

		for (TestResult testResult : getTestResults()) {
			if ((testResult == null) || !testResult.isFailing()) {
				continue;
			}

			String errorMessage = testResult.getErrorDetails();

			if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
				errorMessage = build.getFailureMessage();
			}

			if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
				errorMessage = "Failed for unknown reason";
			}

			if (errorMessage.contains("\n")) {
				errorMessage = errorMessage.substring(
					0, errorMessage.indexOf("\n"));
			}

			errorMessage = errorMessage.trim();

			if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
				errorMessage = "Failed for unknown reason";
			}

			String testName = testResult.getTestName();

			errorMessages.put(
				testName,
				JenkinsResultsParserUtil.combine(testName, ": ", errorMessage));
		}

		if (errorMessages.size() > 1) {
			return JenkinsResultsParserUtil.combine(
				String.valueOf(errorMessages.size()), " Failed tests: ",
				JenkinsResultsParserUtil.join(
					", ", new ArrayList<>(errorMessages.keySet())));
		}
		else if (errorMessages.size() == 1) {
			List<String> values = new ArrayList<>(errorMessages.values());

			return values.get(0);
		}

		return "Failed for unknown reason";
	}

	@Override
	public String getName() {
		String testClassName = JenkinsResultsParserUtil.getCanonicalPath(
			_jUnitTestClass.getTestClassFile());

		testClassName = testClassName.replaceAll(".*/(com/.*)\\.java", "$1");

		return testClassName.replace("/", ".");
	}

	@Override
	public Status getStatus() {
		Build build = getBuild();

		if (build == null) {
			return Status.UNTESTED;
		}

		List<TestClassResult> testClassResults = getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
			String result = build.getResult();

			if ((result == null) || result.equals("ABORTED") ||
				result.equals("FAILURE") || result.equals("SUCCESS") ||
				result.equals("UNSTABLE")) {

				return Status.UNTESTED;
			}

			return Status.FAILED;
		}

		if (_isTestClassResultsSkipped()) {
			return Status.UNTESTED;
		}

		if (_isTestClassResultsFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments =
			super.getTestrayAttachments();

		testrayAttachments.add(getFailureMessagesTestrayAttachment());
		testrayAttachments.addAll(getLiferayLogTestrayAttachments());
		testrayAttachments.addAll(getLiferayOSGiLogTestrayAttachments());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
	}

	protected TestrayAttachment getFailureMessagesTestrayAttachment() {
		List<TestClassResult> testClassResults = getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
			return null;
		}

		TestrayAttachment testrayAttachment = getTestrayAttachment(
			getBuild(), "Failure Messages",
			getAxisBuildURLPath() + "/" + getName() + ".txt.gz");

		if (testrayAttachment == null) {
			return null;
		}

		return testrayAttachment;
	}

	@Override
	protected List<TestrayAttachment> getLiferayLogTestrayAttachments() {
		List<TestClassResult> testClassResults = getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
			return new ArrayList<>();
		}

		return super.getLiferayLogTestrayAttachments();
	}

	@Override
	protected List<TestrayAttachment> getLiferayOSGiLogTestrayAttachments() {
		List<TestClassResult> testClassResults = getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
			return new ArrayList<>();
		}

		return super.getLiferayOSGiLogTestrayAttachments();
	}

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

			if (testClassName.equals(getName()) ||
				testClassName.startsWith(getName() + "$")) {

				_testClassResults.add(testClassResult);

				continue;
			}

			if (testClassName.equals("junit.framework.TestSuite")) {
				for (TestResult testResult : testClassResult.getTestResults()) {
					String testName = testResult.getTestName();

					if (testName.equals(getName())) {
						_testClassResults.add(testClassResult);

						break;
					}
				}
			}
		}

		return _testClassResults;
	}

	protected List<TestResult> getTestResults() {
		List<TestResult> testResults = new ArrayList<>();

		for (TestClassResult testClassResult : getTestClassResults()) {
			String testClassName = testClassResult.getClassName();

			if (!testClassName.equals("junit.framework.TestSuite")) {
				testResults.addAll(testClassResult.getTestResults());

				continue;
			}

			for (TestResult testResult : testClassResult.getTestResults()) {
				String testName = testResult.getTestName();

				if (testName.equals(getName())) {
					testResults.add(testResult);
				}
			}
		}

		return testResults;
	}

	private boolean _isTestClassResultsFailing() {
		for (TestClassResult testClassResult : getTestClassResults()) {
			if (testClassResult.isFailing()) {
				return true;
			}
		}

		return false;
	}

	private boolean _isTestClassResultsSkipped() {
		for (TestClassResult testClassResult : getTestClassResults()) {
			if (testClassResult.isSkipped()) {
				return true;
			}
		}

		return false;
	}

	private final JUnitTestClass _jUnitTestClass;
	private List<TestClassResult> _testClassResults;

}