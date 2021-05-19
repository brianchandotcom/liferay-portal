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

import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class BatchTestrayCaseResult extends TestrayCaseResult {

	public BatchTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup) {

		super(testrayBuild, topLevelBuild);

		_axisTestClassGroup = axisTestClassGroup;
	}

	public AxisBuild getAxisBuild() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return topLevelBuild.getDownstreamAxisBuild(
			_axisTestClassGroup.getAxisName());
	}

	public String getAxisName() {
		return _axisTestClassGroup.getAxisName();
	}

	public String getBatchName() {
		return _axisTestClassGroup.getBatchName();
	}

	public Build getBuild() {
		return getAxisBuild();
	}

	@Override
	public String getComponentName() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.component", getBatchName());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getErrors() {
		Build build = getBuild();

		if (build == null) {
			return "Failed to run on CI";
		}

		if (!build.isFailing()) {
			return null;
		}

		String result = build.getResult();

		if (result == null) {
			return "Failed to finish build on CI";
		}

		if (result.equals("ABORTED")) {
			return "Aborted prior to running test";
		}

		String errorMessage = build.getFailureMessage();

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
		return getAxisName();
	}

	@Override
	public int getPriority() {
		try {
			String testrayCasePriority = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.priority", getBatchName());

			if ((testrayCasePriority != null) &&
				testrayCasePriority.matches("\\d+")) {

				return Integer.parseInt(testrayCasePriority);
			}

			return 5;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public Status getStatus() {
		Build build = getBuild();

		if (build == null) {
			return Status.UNTESTED;
		}

		if (build.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	@Override
	public String getTeamName() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.team", getBatchName());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments = new ArrayList<>();

		testrayAttachments.add(_getBuildResultTopLevelTestrayAttachment());
		testrayAttachments.add(_getJenkinsConsoleTestrayAttachment());
		testrayAttachments.add(_getJenkinsConsoleTopLevelTestrayAttachment());
		testrayAttachments.add(_getJenkinsReportTestrayAttachment());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
	}

	@Override
	public String getType() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.type", getBatchName());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String[] getWarnings() {
		return null;
	}

	protected String getAxisBuildURLPath() {
		AxisBuild axisBuild = getAxisBuild();

		if (axisBuild == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(_getTopLevelBuildURLPath());
		sb.append("/");
		sb.append(axisBuild.getAxisName());

		return sb.toString();
	}

	protected AxisTestClassGroup getAxisTestClassGroup() {
		return _axisTestClassGroup;
	}

	private TestrayAttachment _getBuildResultTopLevelTestrayAttachment() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild == null) {
			return null;
		}

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Build Result (Top Level)",
				JenkinsResultsParserUtil.combine(
					_getTopLevelBuildURLPath(), "/build-result.json.gz"));

		if (!testrayAttachment.exists()) {
			return null;
		}

		return testrayAttachment;
	}

	private TestrayAttachment _getJenkinsConsoleTestrayAttachment() {
		AxisBuild axisBuild = getAxisBuild();

		if (axisBuild == null) {
			return null;
		}

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Jenkins Console",
				JenkinsResultsParserUtil.combine(
					getAxisBuildURLPath(), "/jenkins-console.txt.gz"));

		if (!testrayAttachment.exists()) {
			return null;
		}

		return testrayAttachment;
	}

	private TestrayAttachment _getJenkinsConsoleTopLevelTestrayAttachment() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild == null) {
			return null;
		}

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Jenkins Console (Top Level)",
				JenkinsResultsParserUtil.combine(
					_getTopLevelBuildURLPath(), "/jenkins-console.txt.gz"));

		if (!testrayAttachment.exists()) {
			return null;
		}

		return testrayAttachment;
	}

	private TestrayAttachment _getJenkinsReportTestrayAttachment() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild == null) {
			return null;
		}

		TestrayAttachment testrayAttachment =
			TestrayFactory.newTestrayAttachment(
				this, "Jenkins Report (Top Level)",
				JenkinsResultsParserUtil.combine(
					_getTopLevelBuildURLPath(), "/jenkins-report.html.gz"));

		if (!testrayAttachment.exists()) {
			return null;
		}

		return testrayAttachment;
	}

	private String _getTopLevelBuildURLPath() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		Date date = new Date(topLevelBuild.getStartTime());

		sb.append(
			JenkinsResultsParserUtil.toDateString(
				date, "yyyy-MM", "America/Los_Angeles"));

		sb.append("/");

		JenkinsMaster jenkinsMaster = topLevelBuild.getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("/");
		sb.append(topLevelBuild.getJobName());
		sb.append("/");
		sb.append(topLevelBuild.getBuildNumber());

		return sb.toString();
	}

	private final AxisTestClassGroup _axisTestClassGroup;

}