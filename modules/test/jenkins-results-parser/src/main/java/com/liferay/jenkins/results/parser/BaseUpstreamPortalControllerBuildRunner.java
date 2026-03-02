/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseUpstreamPortalControllerBuildRunner
	<S extends PortalTopLevelBuildData>
		extends BasePortalControllerBuildRunner<S> {

	protected BaseUpstreamPortalControllerBuildRunner(S buildData) {
		super(buildData);
	}

	protected abstract String getInvocationJobName();

	protected String getInvocationJobURL(String testSuite) {
		String invocationJobName = getInvocationJobName();

		String masterURL = null;

		try {
			masterURL = JenkinsResultsParserUtil.getBuildProperty(
				"jenkins.osb.jenkins.web.master.url", invocationJobName,
				testSuite);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		if (masterURL == null) {
			masterURL = JenkinsResultsParserUtil.getMostAvailableMasterURL(
				"http://" + getInvocationCohortName() + ".liferay.com", null, 1,
				invocationJobName, getSlaveLabel(testSuite), 24, 2);
		}

		return JenkinsResultsParserUtil.combine(
			masterURL, "/job/", invocationJobName);
	}

	protected String getSlaveLabel(String testSuite) {
		String slaveLabel = null;

		String invocationJobName = getInvocationJobName();

		try {
			slaveLabel = JenkinsResultsParserUtil.getBuildProperty(
				"jenkins.osb.jenkins.web.slave.label", invocationJobName,
				testSuite);

			if (JenkinsResultsParserUtil.isNullOrEmpty(slaveLabel)) {
				slaveLabel = JenkinsResultsParserUtil.getBuildProperty(
					"cloud.fleet.primary.label");
			}

			if (JenkinsResultsParserUtil.isNullOrEmpty(slaveLabel)) {
				slaveLabel = JenkinsResultsParserUtil.getBuildProperty(
					"master.auto.scaling.group.name");
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(slaveLabel)) {
			slaveLabel = SLAVE_LABEL_DEFAULT;
		}

		return slaveLabel;
	}

	protected String getTestPortalBuildProfile(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.test.portal.build.profile", testSuite);
	}

	protected String getTestrayProjectName(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.project.name", testSuite);
	}

	protected String getTestrayRoutineName(String testSuite) {
		String testrayRoutineName = _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.routine.name", testSuite);

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {
			testrayRoutineName = _getTestSuiteBuildProperty(
				"portal.testsuite.upstream.testray.build.type", testSuite);
		}

		return testrayRoutineName;
	}

	protected String getTestraySlackChannels(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.slack.channels", testSuite);
	}

	protected String getTestraySlackIconEmoji(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.slack.icon.emoji", testSuite);
	}

	protected String getTestraySlackUsername(String testSuite) {
		return _getTestSuiteBuildProperty(
			"portal.testsuite.upstream.testray.slack.username", testSuite);
	}

	protected static final String SLAVE_LABEL_DEFAULT = "slave";

	private String _getTestSuiteBuildProperty(
		String propertyName, String testSuite) {

		S buildData = getBuildData();

		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(), propertyName,
				buildData.getPortalUpstreamBranchName(), testSuite);
		}
		catch (IOException ioException) {
			return null;
		}
	}

}