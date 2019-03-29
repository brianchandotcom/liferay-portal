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

package com.liferay.jenkins.results.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalTestSuiteUpstreamControllerBuildData
	extends PortalTopLevelBuildData {

	protected PortalTestSuiteUpstreamControllerBuildData(
		String runID, String jobName, String buildURL) {

		super(runID, jobName, buildURL);

		setPortalBranchSHA(_getPortalBranchSHA());
		setPortalGitHubURL(_getPortalGitHubURL());
		setPortalUpstreamBranchName(_getPortalUpstreamBranchName());
	}

	private String _getPortalBranchSHA() {
		RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
			_getPortalGitHubURL());

		return remoteGitRef.getSHA();
	}

	private String _getPortalGitHubURL() {
		return JenkinsResultsParserUtil.combine(
			"https://github.com/liferay/", _getPortalRepositoryName(), "/tree/",
			_getPortalUpstreamBranchName());
	}

	private String _getPortalRepositoryName() {
		String upstreamBranchName = _getPortalUpstreamBranchName();

		if (upstreamBranchName.equals("master")) {
			return "liferay-portal";
		}

		return "liferay-portal-ee";
	}

	private String _getPortalUpstreamBranchName() {
		String jobName = getJobName();

		Matcher matcher = _pattern.matcher(jobName);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid job name " + jobName);
		}

		return matcher.group("branchName");
	}

	private static final Pattern _pattern = Pattern.compile(
		"[^\\(]+\\((?<branchName>[^_]+)_(?<suiteName>[^\\)]+)\\)");

}