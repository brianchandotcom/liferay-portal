/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class ArchiveBinariesCacheControllerBuildRunner
	<S extends PortalTopLevelBuildData>
		extends BasePortalControllerBuildRunner<S> {

	@Override
	public void tearDown() {
	}

	protected ArchiveBinariesCacheControllerBuildRunner(S buildData) {
		super(buildData);
	}

	@Override
	protected void invokeBuilds() {
		StringBuilder sb = new StringBuilder();

		String invocationJobURL = _getInvocationJobURL();

		sb.append(invocationJobURL);

		sb.append("/buildWithParameters?");

		String jenkinsAuthenticationToken;

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			jenkinsAuthenticationToken = buildProperties.getProperty(
				"jenkins.authentication.token");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		S buildData = getBuildData();

		sb.append("token=");
		sb.append(jenkinsAuthenticationToken);

		Map<String, String> invocationParameters = new HashMap<>();

		invocationParameters.putAll(buildData.getBuildParameters());

		invocationParameters.put(
			"CONTROLLER_BUILD_URL", buildData.getBuildURL());
		invocationParameters.put(
			"JENKINS_GITHUB_BRANCH_NAME",
			buildData.getJenkinsGitHubBranchName());
		invocationParameters.put(
			"JENKINS_GITHUB_BRANCH_USERNAME",
			buildData.getJenkinsGitHubUsername());
		invocationParameters.put(
			"PORTAL_GIT_COMMIT", buildData.getPortalBranchSHA());

		String portalGitHubCompareURL = getPortalGitHubCompareURL();

		if (portalGitHubCompareURL != null) {
			invocationParameters.put(
				"PORTAL_GITHUB_COMPARE_URL", portalGitHubCompareURL);
		}

		invocationParameters.put(
			"PORTAL_UPSTREAM_BRANCH_NAME",
			buildData.getPortalUpstreamBranchName());
		invocationParameters.put("SLAVE_LABEL", _SLAVE_LABEL);
		invocationParameters.put(
			"TEST_PORTAL_BUILD_PROFILE", _TEST_PORTAL_BUILD_PROFILE);

		for (Map.Entry<String, String> invocationParameter :
				invocationParameters.entrySet()) {

			String invocationParameterValue = invocationParameter.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					invocationParameterValue)) {

				continue;
			}

			sb.append("&");
			sb.append(invocationParameter.getKey());
			sb.append("=");
			sb.append(invocationParameterValue);
		}

		try {
			JenkinsResultsParserUtil.toString(sb.toString());

			keepJenkinsBuild(true);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		sb = new StringBuilder();

		sb.append("<a href=\"");
		sb.append(invocationJobURL);
		sb.append("\"><strong>IN QUEUE</strong></a>");
		sb.append("<ul><li><strong>Git ID:</strong> ");
		sb.append("<a href=\"https://github.com/");
		sb.append(buildData.getPortalGitHubUsername());
		sb.append("/");
		sb.append(buildData.getPortalGitHubRepositoryName());
		sb.append("/commit/");
		sb.append(buildData.getPortalBranchSHA());
		sb.append("\">");
		sb.append(getPortalBranchAbbreviatedSHA());
		sb.append("</a></li>");

		if (portalGitHubCompareURL != null) {
			sb.append("<li><strong>Git Compare:</strong> <a href=\"");
			sb.append(portalGitHubCompareURL);
			sb.append("\">??? commits</a></li>");
		}

		sb.append("</ul>");

		buildData.setBuildDescription(sb.toString());

		updateBuildDescription();
	}

	private String _getInvocationJobURL() {
		String invocationJobName = "archive-binaries-cache";

		String masterURL = JenkinsResultsParserUtil.getMostAvailableMasterURL(
			"http://" + getInvocationCohortName() + ".liferay.com", null, 1,
			invocationJobName, _SLAVE_LABEL, 24, 2);

		return JenkinsResultsParserUtil.getRemoteURL(
			JenkinsResultsParserUtil.combine(
				masterURL, "/job/", invocationJobName));
	}

	private static final String _SLAVE_LABEL = "slave-bundle-builder";

	private static final String _TEST_PORTAL_BUILD_PROFILE = "dxp";

}