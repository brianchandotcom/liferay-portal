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
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

		attachments.add(_getPoshiReportAttachment());
		attachments.add(_getPoshiSummaryAttachment());

		attachments.removeAll(Collections.singleton(null));

		return attachments;
	}

	@Override
	public String getComponent() {
		return JenkinsResultsParserUtil.getProperty(
			_functionalTestClass.getPoshiProperties(),
			"testray.main.component.name");
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
					"/report.html.gz"));

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