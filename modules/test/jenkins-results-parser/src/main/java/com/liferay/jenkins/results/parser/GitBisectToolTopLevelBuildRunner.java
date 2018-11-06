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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class GitBisectToolTopLevelBuildRunner
	extends PortalTopLevelBuildRunner
		<PortalTopLevelBuildData, PortalWorkspace> {

	protected GitBisectToolTopLevelBuildRunner(
		PortalTopLevelBuildData portalTopLevelBuildData) {

		super(portalTopLevelBuildData);
	}

	@Override
	protected Element getJenkinsReportElement() {
		PortalTopLevelBuildData portalTopLevelBuildData = getBuildData();
		PortalWorkspace portalWorkspace = getWorkspace();

		if (portalWorkspace == null) {
			return Dom4JUtil.getNewElement(
				"html", null,
				Dom4JUtil.getNewElement(
					"h1", null, "Report building in progress for ",
					Dom4JUtil.getNewAnchorElement(
						portalTopLevelBuildData.getBuildURL(),
						portalTopLevelBuildData.getBuildURL())));
		}

		GitBisectToolBuild gitBisectToolBuild =
			(GitBisectToolBuild)getTopLevelBuild();

		gitBisectToolBuild.setDownstreamBuildDataList(
			portalTopLevelBuildData.getDownstreamBuildDataList());
		gitBisectToolBuild.setWorkspaceGitRepository(
			portalWorkspace.getPrimaryPortalWorkspaceGitRepository());

		return super.getJenkinsReportElement();
	}

	@Override
	protected void prepareInvocationBuildDataList() {
		PortalTopLevelBuildData portalTopLevelBuildData = getBuildData();

		String downstreamJobName =
			portalTopLevelBuildData.getJobName() + "-batch";

		for (String portalBranchSHA : _getPortalBranchSHAs()) {
			BatchBuildData batchBuildData = BuildDataFactory.newBatchBuildData(
				null, downstreamJobName, null);

			if (!(batchBuildData instanceof PortalBatchBuildData)) {
				throw new RuntimeException("Invalid build data");
			}

			PortalBatchBuildData portalBatchBuildData =
				(PortalBatchBuildData)batchBuildData;

			portalBatchBuildData.setBuildDescription(
				_getDownstreamBuildDescription(portalBranchSHA));

			portalBatchBuildData.setBatchName(_getBatchName());
			portalBatchBuildData.setPortalBranchSHA(portalBranchSHA);
			portalBatchBuildData.setTestList(_getTestList());

			addInvocationBuildData(portalBatchBuildData);
		}
	}

	@Override
	protected void setUpWorkspace() {
		super.setUpWorkspace();

		PortalWorkspace portalWorkspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			portalWorkspace.getPrimaryPortalWorkspaceGitRepository();

		workspaceGitRepository.storeCommitHistory(_getPortalBranchSHAs());
	}

	@Override
	protected void validateBuildParameters() {
		_validateBuildParameterJenkinsGitHubURL();
		_validateBuildParameterPortalBatchName();
		_validateBuildParameterPortalBatchTestSelector();
		_validateBuildParameterPortalBranchSHAs();
		_validateBuildParameterPortalGitHubURL();
		_validateBuildParameterPortalUpstreamBranchName();
	}

	private String _getBatchName() {
		BuildData buildData = getBuildData();

		return JenkinsResultsParserUtil.getBuildParameter(
			buildData.getBuildURL(), "PORTAL_BATCH_NAME");
	}

	private String _getDownstreamBuildDescription(String portalBranchSHA) {
		PortalTopLevelBuildData portalTopLevelBuildData = getBuildData();

		StringBuilder sb = new StringBuilder();

		sb.append(portalBranchSHA);
		sb.append(" - ");
		sb.append(_getBatchName());
		sb.append(" - ");
		sb.append("<a href=\"https://");
		sb.append(portalTopLevelBuildData.getTopLevelMasterHostname());
		sb.append(".liferay.com/userContent/");
		sb.append(portalTopLevelBuildData.getUserContentRelativePath());
		sb.append("jenkins-report.html\">Jenkins Report</a>");

		sb.append("<ul>");

		for (String test : _getTestList()) {
			sb.append("<li>");
			sb.append(test);
			sb.append("</li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private List<String> _getPortalBranchSHAs() {
		String portalGitCommits = getBuildParameter(_PORTAL_BRANCH_SHAS);

		return Arrays.asList(portalGitCommits.split(","));
	}

	private List<String> _getTestList() {
		String portalBatchTestSelector = getBuildParameter(
			_PORTAL_BATCH_TEST_SELECTOR);

		return Arrays.asList(portalBatchTestSelector.split(","));
	}

	private void _validateBuildParameterJenkinsGitHubURL() {
		String jenkinsGitHubURL = getBuildParameter(_JENKINS_GITHUB_URL);

		if ((jenkinsGitHubURL == null) || jenkinsGitHubURL.isEmpty()) {
			return;
		}

		String failureMessage = JenkinsResultsParserUtil.combine(
			_JENKINS_GITHUB_URL,
			" has an invalid Jenkins GitHub URL <a href=\"", jenkinsGitHubURL,
			"\">", jenkinsGitHubURL, "</a>");

		Matcher matcher = _pattern.matcher(jenkinsGitHubURL);

		if (!matcher.find()) {
			reportFailureMessageToBuildDescription(failureMessage);
		}

		String repositoryName = matcher.group("repositoryName");

		if (!repositoryName.equals("liferay-jenkins-ee")) {
			reportFailureMessageToBuildDescription(failureMessage);
		}
	}

	private void _validateBuildParameterPortalBatchName() {
		String portalBatchName = getBuildParameter(_PORTAL_BATCH_NAME);

		if ((portalBatchName == null) || portalBatchName.isEmpty()) {
			reportFailureMessageToBuildDescription(
				_PORTAL_BATCH_NAME + " is null");
		}

		String allowedPortalBatchNames = getJobProperty(
			JenkinsResultsParserUtil.combine(
				"allowed.portal.batch.names[",
				getBuildParameter(_PORTAL_UPSTREAM_BRANCH_NAME), "]"));

		if ((allowedPortalBatchNames == null) ||
			allowedPortalBatchNames.isEmpty()) {

			return;
		}

		List<String> allowedPortalBatchNamesList = Arrays.asList(
			allowedPortalBatchNames.split(","));

		if (!allowedPortalBatchNamesList.contains(portalBatchName)) {
			reportFailureMessageToBuildDescription(
				JenkinsResultsParserUtil.combine(
					_PORTAL_BATCH_NAME, " must match one of the following: ",
					allowedPortalBatchNames));
		}
	}

	private void _validateBuildParameterPortalBatchTestSelector() {
		String portalBatchTestSelector = getBuildParameter(
			_PORTAL_BATCH_TEST_SELECTOR);

		if ((portalBatchTestSelector == null) ||
			portalBatchTestSelector.isEmpty()) {

			reportFailureMessageToBuildDescription(
				_PORTAL_BATCH_TEST_SELECTOR + " is null");
		}
	}

	private void _validateBuildParameterPortalBranchSHAs() {
		String portalBranchSHAs = getBuildParameter(_PORTAL_BRANCH_SHAS);

		if ((portalBranchSHAs == null) || portalBranchSHAs.isEmpty()) {
			reportFailureMessageToBuildDescription(
				_PORTAL_BRANCH_SHAS + " is null");
		}

		String allowedPortalBranchSHAs = getJobProperty(
			"allowed.portal.branch.shas");

		if ((allowedPortalBranchSHAs == null) ||
			allowedPortalBranchSHAs.isEmpty()) {

			return;
		}

		Integer portalBranchSHACount = StringUtils.countMatches(
			portalBranchSHAs, ",") + 1;

		if (portalBranchSHACount >
				Integer.valueOf(allowedPortalBranchSHAs)) {

			reportFailureMessageToBuildDescription(
				JenkinsResultsParserUtil.combine(
					_PORTAL_BRANCH_SHAS, " can only reference ",
					allowedPortalBranchSHAs, " portal branch SHAs"));
		}
	}

	private void _validateBuildParameterPortalGitHubURL() {
		String portalGitHubURL = getBuildParameter(_PORTAL_GITHUB_URL);

		if ((portalGitHubURL == null) || portalGitHubURL.isEmpty()) {
			reportFailureMessageToBuildDescription(
				_PORTAL_GITHUB_URL + " is null");
		}

		String failureMessage = JenkinsResultsParserUtil.combine(
			_PORTAL_GITHUB_URL, " has an invalid Portal GitHub URL <a href=\"",
			portalGitHubURL, "\">", portalGitHubURL, "</a>");

		Matcher matcher = _pattern.matcher(portalGitHubURL);

		if (!matcher.find()) {
			reportFailureMessageToBuildDescription(failureMessage);
		}

		String repositoryName = matcher.group("repositoryName");

		if (!repositoryName.equals("liferay-portal") &&
			!repositoryName.equals("liferay-portal-ee")) {

			reportFailureMessageToBuildDescription(failureMessage);
		}
	}

	private void _validateBuildParameterPortalUpstreamBranchName() {
		String portalUpstreamBranchName = getBuildParameter(
			_PORTAL_UPSTREAM_BRANCH_NAME);

		if ((portalUpstreamBranchName == null) ||
			portalUpstreamBranchName.isEmpty()) {

			reportFailureMessageToBuildDescription(
				_PORTAL_UPSTREAM_BRANCH_NAME + " is null");
		}

		String allowedPortalUpstreamBranchNames = getJobProperty(
			"allowed.portal.upstream.branch.names");

		if ((allowedPortalUpstreamBranchNames == null) ||
			allowedPortalUpstreamBranchNames.isEmpty()) {

			return;
		}

		List<String> allowedPortalUpstreamBranchNamesList = Arrays.asList(
			allowedPortalUpstreamBranchNames.split(","));

		if (!allowedPortalUpstreamBranchNamesList.contains(
				portalUpstreamBranchName)) {

			reportFailureMessageToBuildDescription(
				JenkinsResultsParserUtil.combine(
					_PORTAL_UPSTREAM_BRANCH_NAME,
					" must match one of the following: ",
					allowedPortalUpstreamBranchNames));
		}
	}

	private static final String _JENKINS_GITHUB_URL = "JENKINS_GITHUB_URL";

	private static final String _PORTAL_BATCH_NAME = "PORTAL_BATCH_NAME";

	private static final String _PORTAL_BATCH_TEST_SELECTOR =
		"PORTAL_BATCH_TEST_SELECTOR";

	private static final String _PORTAL_BRANCH_SHAS = "PORTAL_BRANCH_SHAS";

	private static final String _PORTAL_GITHUB_URL = "PORTAL_GITHUB_URL";

	private static final String _PORTAL_UPSTREAM_BRANCH_NAME =
		"PORTAL_UPSTREAM_BRANCH_NAME";

	private static final Pattern _pattern = Pattern.compile(
		"https://github.com/[^/]+/(?<repositoryName>[^/]+)/tree/.+");

}