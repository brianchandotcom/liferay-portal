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
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.job.property.JobPropertyFactory;
import com.liferay.jenkins.results.parser.test.clazz.FunctionalTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.WordUtils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class FunctionalBatchTestrayCaseResult extends BatchTestrayCaseResult {

	public FunctionalBatchTestrayCaseResult(
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
	public String getErrors() {
		TestResult testResult = getTestResult();

		if (testResult == null) {
			Build build = getBuild();

			if (build == null) {
				return "Failed to run build on CI";
			}

			String result = build.getResult();

			if (result == null) {
				return "Failed to finish build on CI";
			}

			if (result.equals("ABORTED")) {
				return "Aborted prior to running test";
			}

			if (result.equals("SUCCESS") || result.equals("UNSTABLE")) {
				return "Failed to run test on CI";
			}

			return "Failed prior to running test";
		}

		if (!testResult.isFailing()) {
			return null;
		}

		String errorMessage = testResult.getErrorDetails();

		if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
			return "Failed for unknown reason";
		}

		if (errorMessage.contains("\n")) {
			errorMessage = errorMessage.substring(
				0, errorMessage.indexOf("\n"));
		}

		errorMessage = errorMessage.trim();

		if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
			return "Failed for unknown reason";
		}

		return errorMessage;
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
		Build build = getBuild();

		if (build == null) {
			return Status.UNTESTED;
		}

		TestResult testResult = getTestResult();

		if (testResult == null) {
			String result = build.getResult();

			if ((result == null) || result.equals("SUCCESS") ||
				result.equals("UNSTABLE")) {

				return Status.UNTESTED;
			}

			return Status.FAILED;
		}

		if (testResult.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	@Override
	public String getSubcomponentNames() {
		return JenkinsResultsParserUtil.getProperty(
			_functionalTestClass.getPoshiProperties(),
			"testray.component.names");
	}

	@Override
	public String getTeamName() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Job job = topLevelBuild.getJob();

		JobProperty teamNamesJobProperty = JobPropertyFactory.newJobProperty(
			job, "testray.team.names");

		String teamNames = teamNamesJobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(teamNames)) {
			return super.getTeamName();
		}

		String componentName = getComponentName();

		for (String teamName : teamNames.split(",")) {
			JobProperty teamComponentNamesJobProperty =
				JobPropertyFactory.newJobProperty(
					job, "testray.team." + teamName + ".component.names");

			String teamComponentNames =
				teamComponentNamesJobProperty.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(teamComponentNames)) {
				continue;
			}

			for (String teamComponentName : teamComponentNames.split(",")) {
				if (teamComponentName.equals(componentName)) {
					teamName = teamName.replace("-", " ");

					return WordUtils.capitalize(teamName);
				}
			}
		}

		return super.getTeamName();
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments =
			super.getTestrayAttachments();

		List<TestrayAttachment> liferayLogTestrayAttachments =
			_getLiferayLogTestrayAttachments();

		if (liferayLogTestrayAttachments != null) {
			testrayAttachments.addAll(liferayLogTestrayAttachments);
		}

		List<TestrayAttachment> liferayOSGiLogAttachments =
			_getLiferayOSGiLogTestrayAttachments();

		if (liferayOSGiLogAttachments != null) {
			testrayAttachments.addAll(liferayOSGiLogAttachments);
		}

		testrayAttachments.add(_getPoshiReportTestrayAttachment());
		testrayAttachments.add(_getPoshiSummaryTestrayAttachment());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
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

	@Override
	public String[] getWarnings() {
		StringBuilder sb = new StringBuilder();

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Date topLevelStartDate = new Date(topLevelBuild.getStartTime());

		sb.append(
			JenkinsResultsParserUtil.toDateString(
				topLevelStartDate, "yyyy-MM", "America/Los_Angeles"));

		sb.append("/");

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("/");
		sb.append(topLevelBuild.getJobName());
		sb.append("/");
		sb.append(topLevelBuild.getBuildNumber());
		sb.append("/");

		AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup();

		sb.append(axisTestClassGroup.getAxisName());

		sb.append("/poshi-warnings.xml");

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Poshi Warnings", sb.toString());

		if (!testrayAttachment.exists()) {
			return null;
		}

		String testrayAttachmentValue = testrayAttachment.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayAttachmentValue)) {
			return null;
		}

		try {
			Document document = Dom4JUtil.parse(testrayAttachmentValue);

			Element rootElement = document.getRootElement();

			List<String> warnings = new ArrayList<>();

			for (Element valueElement : rootElement.elements()) {
				String warning = valueElement.getText();

				warning = warning.trim();

				if (JenkinsResultsParserUtil.isNullOrEmpty(warning)) {
					continue;
				}

				warnings.add(warning);
			}

			if (!warnings.isEmpty()) {
				return warnings.toArray(new String[0]);
			}
		}
		catch (DocumentException documentException) {
		}

		return null;
	}

	private List<TestrayAttachment> _getLiferayLogTestrayAttachments() {
		if (getTestResult() == null) {
			return null;
		}

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Liferay Log",
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/liferay-log.txt.gz"));

		if (!testrayAttachment.exists()) {
			return null;
		}

		List<TestrayAttachment> testrayAttachments = new ArrayList<>();

		testrayAttachments.add(testrayAttachment);

		for (int i = 1; i <= 5; i++) {
			TestrayAttachment liferayLogTestrayAttachment =
				TestrayFactory.newTestrayAttachment(
					this,
					JenkinsResultsParserUtil.combine(
						"Liferay Log (", String.valueOf(i), ")"),
					JenkinsResultsParserUtil.combine(
						getAxisBuildURLPath(), "/liferay-log-",
						String.valueOf(i), ".txt.gz"));

			if (!liferayLogTestrayAttachment.exists()) {
				break;
			}

			testrayAttachments.add(liferayLogTestrayAttachment);
		}

		return testrayAttachments;
	}

	private List<TestrayAttachment> _getLiferayOSGiLogTestrayAttachments() {
		if (getTestResult() == null) {
			return null;
		}

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Liferay OSGi Log",
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/liferay-osgi-log.txt.gz"));

		if (!testrayAttachment.exists()) {
			return null;
		}

		List<TestrayAttachment> testrayAttachments = new ArrayList<>();

		testrayAttachments.add(testrayAttachment);

		for (int i = 1; i <= 5; i++) {
			TestrayAttachment liferayOSGiLogTestrayAttachment =
				TestrayFactory.newTestrayAttachment(
					this,
					JenkinsResultsParserUtil.combine(
						"Liferay OSGi Log (", String.valueOf(i), ")"),
					JenkinsResultsParserUtil.combine(
						getAxisBuildURLPath(), "/liferay-log-osgi-",
						String.valueOf(i), ".txt.gz"));

			if (!liferayOSGiLogTestrayAttachment.exists()) {
				break;
			}

			testrayAttachments.add(liferayOSGiLogTestrayAttachment);
		}

		return testrayAttachments;
	}

	private TestrayAttachment _getPoshiReportTestrayAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		name = name.replace("#", "_");

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Poshi Report",
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/",
					JenkinsResultsParserUtil.fixURL(name), "/index.html.gz"));

		if (!testrayAttachment.exists()) {
			return null;
		}

		return testrayAttachment;
	}

	private TestrayAttachment _getPoshiSummaryTestrayAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		name = name.replace("#", "_");

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Poshi Summary",
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/",
					JenkinsResultsParserUtil.fixURL(name), "/summary.html.gz"));

		if (!testrayAttachment.exists()) {
			return null;
		}

		return testrayAttachment;
	}

	private final FunctionalTestClass _functionalTestClass;

}