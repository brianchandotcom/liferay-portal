/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public class PortalUpstreamControllerSingleSuiteBuildRunner
	<S extends PortalTestSuiteUpstreamControllerBuildData>
		extends PortalTestSuiteUpstreamControllerSingleSuiteBuildRunner<S> {

	protected PortalUpstreamControllerSingleSuiteBuildRunner(S buildData) {
		super(buildData);
	}

	@Override
	protected String getJobURL(String testSuite) {
		String jobName = "test-portal-upstream";

		String masterURL = null;

		try {
			masterURL = JenkinsResultsParserUtil.getBuildProperty(
				jobName, testSuite);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		if (masterURL == null) {
			masterURL = JenkinsResultsParserUtil.getMostAvailableMasterURL(
				JenkinsResultsParserUtil.combine(
					"http://" + getInvocationCohortName() + ".liferay.com"),
				null, 1, 24, 2);
		}

		return JenkinsResultsParserUtil.combine(masterURL, "/job/", jobName);
	}

}