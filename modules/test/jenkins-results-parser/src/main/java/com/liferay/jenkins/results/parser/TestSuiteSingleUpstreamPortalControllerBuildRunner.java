/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Yi-Chen Tsai
 */
public class TestSuiteSingleUpstreamPortalControllerBuildRunner
	<S extends ControllerPortalTopLevelBuildData>
		extends SingleUpstreamPortalControllerBuildRunner<S> {

	protected TestSuiteSingleUpstreamPortalControllerBuildRunner(S buildData) {
		super(buildData);
	}

	@Override
	protected String getInvocationJobName() {
		S buildData = getBuildData();

		return JenkinsResultsParserUtil.combine(
			"test-portal-testsuite-upstream(",
			buildData.getPortalUpstreamBranchName(), ")");
	}

}