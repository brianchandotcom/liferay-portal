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

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalTestSuiteUpstreamControllerBuildData
	extends PortalTopLevelBuildData {

	public String getJenkinsBranchName() {
		String jenkinsGitHubURL = getBuildParameter("JENKINS_GITHUB_URL");

		if ((jenkinsGitHubURL == null) || jenkinsGitHubURL.isEmpty()) {
			return "master";
		}

		Matcher matcher = _jenkinsGitHubURLPattern.matcher(jenkinsGitHubURL);

		if (!matcher.find()) {
			return "master";
		}

		return matcher.group("branchName");
	}

	public String getJenkinsBranchUsername() {
		String jenkinsGitHubURL = getBuildParameter("JENKINS_GITHUB_URL");

		if ((jenkinsGitHubURL == null) || jenkinsGitHubURL.isEmpty()) {
			return "liferay";
		}

		Matcher matcher = _jenkinsGitHubURLPattern.matcher(jenkinsGitHubURL);

		if (!matcher.find()) {
			return "liferay";
		}

		return matcher.group("username");
	}

	public String getTestrayBuildName() {
		return JenkinsResultsParserUtil.combine(
			String.valueOf(getBuildNumber()), " - ",
			JenkinsResultsParserUtil.toDateString(
				new Date(getStartTime()), "yyyy-MM-dd[HH:mm:ss]",
				"America/Los_Angeles"));
	}

	public String getTestrayBuildType() {
		return JenkinsResultsParserUtil.combine(
			"[", getPortalUpstreamBranchName(), "] ci:test:",
			getTestSuiteName());
	}

	public String getTestrayProjectName() {
		String testrayProjectName = System.getenv("TESTRAY_PROJECT_NAME");

		if ((testrayProjectName != null) && !testrayProjectName.isEmpty()) {
			return testrayProjectName;
		}

		throw new RuntimeException("Please set 'TESTRAY_PROJECT_NAME'");
	}

	public String getTestSuiteName() {
		String jobName = getJobName();

		Matcher matcher = _jobNamePattern.matcher(jobName);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid job name " + jobName);
		}

		return matcher.group("testSuiteName");
	}

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
		String portalGitHubURL = System.getenv("PORTAL_GITHUB_URL");

		if ((portalGitHubURL != null) && !portalGitHubURL.isEmpty()) {
			return portalGitHubURL;
		}

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

		Matcher matcher = _jobNamePattern.matcher(jobName);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid job name " + jobName);
		}

		return matcher.group("upstreamBranchName");
	}

	private static final Pattern _jenkinsGitHubURLPattern = Pattern.compile(
		"https://github.com/(?<username>[^/]+)/liferay-jenkins-ee/tree/" +
			"(?<branchName>.+)");
	private static final Pattern _jobNamePattern = Pattern.compile(
		"[^\\(]+\\((?<upstreamBranchName>[^_]+)_(?<testSuiteName>[^\\)]+)\\)");

}