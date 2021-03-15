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
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
		AxisTestClassGroup axisTestClassGroup,
		TestClassGroup.TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		if (!(testClass instanceof
				FunctionalBatchTestClassGroup.FunctionalTestClass)) {

			throw new RuntimeException(
				"Test class is not a functional test class");
		}

		_functionalTestClass =
			(FunctionalBatchTestClassGroup.FunctionalTestClass)testClass;
	}

	@Override
	public List<Attachment> getAttachments() {
		List<Attachment> attachments = super.getAttachments();

		List<Attachment> liferayLogAttachments = _getLiferayLogAttachments();

		if (liferayLogAttachments != null) {
			attachments.addAll(liferayLogAttachments);
		}

		List<Attachment> liferayOSGiLogAttachments =
			_getLiferayOSGiLogAttachments();

		if (liferayOSGiLogAttachments != null) {
			attachments.addAll(liferayOSGiLogAttachments);
		}

		attachments.add(_getPoshiReportAttachment());
		attachments.add(_getPoshiSummaryAttachment());

		attachments.removeAll(Collections.singleton(null));

		return attachments;
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
			return "Failed to run on CI";
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
		TestResult testResult = getTestResult();

		if ((testResult == null) || testResult.isFailing()) {
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

		Properties jobProperties = job.getJobProperties();

		String testrayTeamNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "testray.team.names");

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayTeamNames)) {
			return super.getTeamName();
		}

		String componentName = getComponentName();

		for (String testrayTeamName : testrayTeamNames.split(",")) {
			String testrayTeamComponentNames =
				JenkinsResultsParserUtil.getProperty(
					jobProperties,
					"testray.team." + testrayTeamName + ".component.names");

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					testrayTeamComponentNames)) {

				continue;
			}

			for (String testrayTeamComponentName :
					testrayTeamComponentNames.split(",")) {

				if (testrayTeamComponentName.equals(componentName)) {
					testrayTeamName = testrayTeamName.replace("-", " ");

					return WordUtils.capitalize(testrayTeamName);
				}
			}
		}

		return super.getTeamName();
	}

	public TestResult getTestResult() {
		Build build = getBuild();

		if (build == null) {
			return null;
		}

		String result = build.getResult();

		if (!result.equals("SUCCESS") && !result.equals("UNSTABLE")) {
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

		TestrayS3Object testrayS3Object =
			TestrayS3ObjectFactory.newTestrayS3Object(
				TestrayS3Bucket.getInstance(), sb.toString());

		if (!testrayS3Object.exists()) {
			return null;
		}

		String testrayS3ObjectValue = testrayS3Object.getValue();

		testrayS3ObjectValue = testrayS3ObjectValue.trim();

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayS3ObjectValue)) {
			return null;
		}

		try {
			Document document = Dom4JUtil.parse(testrayS3ObjectValue);

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

	private List<Attachment> _getLiferayLogAttachments() {
		if (getTestResult() == null) {
			return null;
		}

		TestrayS3Object testrayS3Object =
			TestrayS3ObjectFactory.newTestrayS3Object(
				TestrayS3Bucket.getInstance(),
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/liferay-log.txt.gz"));

		List<Attachment> liferayLogAttachments = new ArrayList<>();

		liferayLogAttachments.add(
			new Attachment(this, testrayS3Object, "Liferay Log"));

		for (int i = 1; i <= 5; i++) {
			TestrayS3Object liferayLogTestrayS3Object =
				TestrayS3ObjectFactory.newTestrayS3Object(
					TestrayS3Bucket.getInstance(),
					JenkinsResultsParserUtil.combine(
						getAxisBuildURLPath(), "/liferay-log-",
						String.valueOf(i), ".txt.gz"));

			if (!liferayLogTestrayS3Object.exists()) {
				break;
			}

			liferayLogAttachments.add(
				new Attachment(
					this, liferayLogTestrayS3Object,
					"Liferay Log (" + i + ")"));
		}

		return liferayLogAttachments;
	}

	private List<Attachment> _getLiferayOSGiLogAttachments() {
		if (getTestResult() == null) {
			return null;
		}

		TestrayS3Object testrayS3Object =
			TestrayS3ObjectFactory.newTestrayS3Object(
				TestrayS3Bucket.getInstance(),
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/liferay-osgi-log.txt.gz"));

		if (!testrayS3Object.exists()) {
			return null;
		}

		List<Attachment> liferayOSGiLogAttachments = new ArrayList<>();

		liferayOSGiLogAttachments.add(
			new Attachment(this, testrayS3Object, "Liferay OSGi Log"));

		for (int i = 1; i <= 5; i++) {
			TestrayS3Object liferayOSGiLogTestrayS3Object =
				TestrayS3ObjectFactory.newTestrayS3Object(
					TestrayS3Bucket.getInstance(),
					JenkinsResultsParserUtil.combine(
						getAxisBuildURLPath(), "/liferay-log-",
						String.valueOf(i), ".txt.gz"));

			if (!liferayOSGiLogTestrayS3Object.exists()) {
				break;
			}

			liferayOSGiLogAttachments.add(
				new Attachment(
					this, liferayOSGiLogTestrayS3Object,
					"Liferay OSGi Log (" + i + ")"));
		}

		return liferayOSGiLogAttachments;
	}

	private Attachment _getPoshiReportAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		TestrayS3Object testrayS3Object =
			TestrayS3ObjectFactory.newTestrayS3Object(
				TestrayS3Bucket.getInstance(),
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/", name.replace("#", "_"),
					"/index.html.gz"));

		return new Attachment(this, testrayS3Object, "Poshi Report");
	}

	private Attachment _getPoshiSummaryAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		TestrayS3Object testrayS3Object =
			TestrayS3ObjectFactory.newTestrayS3Object(
				TestrayS3Bucket.getInstance(),
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/", name.replace("#", "_"),
					"/summary.html.gz"));

		return new Attachment(this, testrayS3Object, "Poshi Summary");
	}

	private final FunctionalBatchTestClassGroup.FunctionalTestClass
		_functionalTestClass;

}