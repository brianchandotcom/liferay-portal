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

import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class RootCauseAnalysisToolTopLevelBuildRunner
	extends PortalTopLevelBuildRunner
		<PortalTopLevelBuildData, PortalWorkspace> {

	protected RootCauseAnalysisToolTopLevelBuildRunner(
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

		RootCauseAnalysisToolBuild rootCauseAnalysisToolBuild =
			(RootCauseAnalysisToolBuild)getTopLevelBuild();

		rootCauseAnalysisToolBuild.setDownstreamBuildDataList(
			portalTopLevelBuildData.getDownstreamBuildDataList());
		rootCauseAnalysisToolBuild.setWorkspaceGitRepository(
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

		try {
			workspaceGitRepository.storeCommitHistory(_getPortalBranchSHAs());
		}
		catch (Exception e) {
			String portalGitHubURL = getBuildParameter(
				_NAME_BUILD_PARAMETER_PORTAL_GITHUB_URL);

			failBuildRunner(
				JenkinsResultsParserUtil.combine(
					_NAME_BUILD_PARAMETER_PORTAL_BRANCH_SHAS,
					" has SHAs that are not be found within the latest ",
					String.valueOf(
						WorkspaceGitRepository.COMMITS_HISTORY_SIZE_MAX),
					" commits of <a href=\"", portalGitHubURL, "\">",
					portalGitHubURL, "</a>"),
				e);
		}
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

	private Integer _getAllowedPortalBranchSHAs() {
		String allowedPortalBranchSHAs = getJobProperty(
			"allowed.portal.branch.shas");

		if ((allowedPortalBranchSHAs == null) ||
			allowedPortalBranchSHAs.isEmpty()) {

			return -1;
		}

		return Integer.valueOf(allowedPortalBranchSHAs);
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

	private int _getMaxCommitGroupCount() {
		int maxCommitGroupCount = _getAllowedPortalBranchSHAs();

		if (maxCommitGroupCount != -1) {
			return maxCommitGroupCount;
		}

		return _COMMITS_GROUP_SIZE_MAX_DEFAULT;
	}

	private List<String> _getPortalBranchSHAs() {
		String portalBranchSHAs = getBuildParameter(
			_NAME_BUILD_PARAMETER_PORTAL_BRANCH_SHAS);

		if ((portalBranchSHAs == null) || portalBranchSHAs.isEmpty()) {
			WorkspaceGitRepository workspaceGitRepository =
				_getWorkspaceGitRepository();

			GitWorkingDirectory gitWorkingDirectory =
				workspaceGitRepository.getGitWorkingDirectory();

			List<LocalGitCommit> localGitCommits = gitWorkingDirectory.log(1);

			LocalGitCommit localGitCommit = localGitCommits.get(0);

			return ListUtil.toList(localGitCommit.getSHA());
		}

		Matcher matcher = _compareURLPattern.matcher(portalBranchSHAs);

		if (matcher.find()) {
			WorkspaceGitRepository workspaceGitRepository =
				_getWorkspaceGitRepository();

			List<LocalGitCommit> rangeLocalGitCommits =
				workspaceGitRepository.getRangeLocalGitCommits(
					matcher.group("earliestSHA"), matcher.group("latestSHA"));

			List<List<LocalGitCommit>> localGitCommitsLists =
				workspaceGitRepository.partitionLocalGitCommits(
					rangeLocalGitCommits, _getMaxCommitGroupCount());

			List<String> portalBranchSHAList = new ArrayList<>();

			for (List<LocalGitCommit> localGitCommits : localGitCommitsLists) {
				LocalGitCommit localGitCommit = localGitCommits.get(0);

				portalBranchSHAList.add(localGitCommit.getSHA());
			}

			return portalBranchSHAList;
		}

		List<String> portalBranchSHAList = new ArrayList<>();

		for (String portalBranchSHA : portalBranchSHAs.split(",")) {
			portalBranchSHAList.add(portalBranchSHA.trim());
		}

		return portalBranchSHAList;
	}

	private List<String> _getTestList() {
		String portalBatchTestSelector = getBuildParameter(
			_NAME_BUILD_PARAMETER_PORTAL_BATCH_TEST_SELECTOR);

		List<String> list = new ArrayList<>();

		for (String portalBatchTest : portalBatchTestSelector.split(",")) {
			list.add(portalBatchTest.trim());
		}

		return list;
	}

	private WorkspaceGitRepository _getWorkspaceGitRepository() {
		PortalWorkspace portalWorkspace = getWorkspace();

		return portalWorkspace.getPrimaryPortalWorkspaceGitRepository();
	}

	private void _validateBuildParameterJenkinsGitHubURL() {
		String jenkinsGitHubURL = getBuildParameter(
			_NAME_BUILD_PARAMETER_JENKINS_GITHUB_URL);

		if ((jenkinsGitHubURL == null) || jenkinsGitHubURL.isEmpty()) {
			return;
		}

		String failureMessage = JenkinsResultsParserUtil.combine(
			_NAME_BUILD_PARAMETER_JENKINS_GITHUB_URL,
			" has an invalid Jenkins GitHub URL <a href=\"", jenkinsGitHubURL,
			"\">", jenkinsGitHubURL, "</a>");

		Matcher matcher = _pattern.matcher(jenkinsGitHubURL);

		if (!matcher.find()) {
			failBuildRunner(failureMessage);
		}

		String repositoryName = matcher.group("repositoryName");

		if (!repositoryName.equals("liferay-jenkins-ee")) {
			failBuildRunner(failureMessage);
		}
	}

	private void _validateBuildParameterPortalBatchName() {
		String portalBatchName = getBuildParameter(
			_NAME_BUILD_PARAMETER_PORTAL_BATCH);

		if ((portalBatchName == null) || portalBatchName.isEmpty()) {
			failBuildRunner(_NAME_BUILD_PARAMETER_PORTAL_BATCH + " is null");
		}

		String allowedPortalBatchNames = getJobProperty(
			JenkinsResultsParserUtil.combine(
				"allowed.portal.batch.names[",
				getBuildParameter(
					_NAME_BUILD_PARAMETER_PORTAL_UPSTREAM_BRANCH_NAME),
				"]"));

		if ((allowedPortalBatchNames == null) ||
			allowedPortalBatchNames.isEmpty()) {

			return;
		}

		List<String> allowedPortalBatchNamesList = Arrays.asList(
			allowedPortalBatchNames.split(","));

		if (!allowedPortalBatchNamesList.contains(portalBatchName)) {
			StringBuilder sb = new StringBuilder();

			sb.append(_NAME_BUILD_PARAMETER_PORTAL_BATCH);
			sb.append(" must match one of the following: ");

			sb.append("<ul>");

			for (String allowedPortalBatchName : allowedPortalBatchNamesList) {
				sb.append("<li>");
				sb.append(allowedPortalBatchName);
				sb.append("</li>");
			}

			sb.append("</ul>");

			failBuildRunner(sb.toString());
		}
	}

	private void _validateBuildParameterPortalBatchTestSelector() {
		String portalBatchTestSelector = getBuildParameter(
			_NAME_BUILD_PARAMETER_PORTAL_BATCH_TEST_SELECTOR);

		if ((portalBatchTestSelector == null) ||
			portalBatchTestSelector.isEmpty()) {

			failBuildRunner(
				_NAME_BUILD_PARAMETER_PORTAL_BATCH_TEST_SELECTOR + " is null");
		}
	}

	private void _validateBuildParameterPortalBranchSHAs() {
		String portalBranchSHAs = getBuildParameter(
			_NAME_BUILD_PARAMETER_PORTAL_BRANCH_SHAS);

		if ((portalBranchSHAs == null) || portalBranchSHAs.isEmpty()) {
			return;
		}

		Integer allowedPortalBranchSHAs = _getAllowedPortalBranchSHAs();

		if (allowedPortalBranchSHAs == -1) {
			return;
		}

		Integer portalBranchSHACount =
			StringUtils.countMatches(portalBranchSHAs, ",") + 1;

		if (portalBranchSHACount > allowedPortalBranchSHAs) {
			failBuildRunner(
				JenkinsResultsParserUtil.combine(
					_NAME_BUILD_PARAMETER_PORTAL_BRANCH_SHAS,
					" can only reference ",
					String.valueOf(allowedPortalBranchSHAs),
					" portal branch SHAs"));
		}
	}

	private void _validateBuildParameterPortalGitHubURL() {
		String portalGitHubURL = getBuildParameter(
			_NAME_BUILD_PARAMETER_PORTAL_GITHUB_URL);

		if ((portalGitHubURL == null) || portalGitHubURL.isEmpty()) {
			failBuildRunner(
				_NAME_BUILD_PARAMETER_PORTAL_GITHUB_URL + " is null");
		}

		String failureMessage = JenkinsResultsParserUtil.combine(
			_NAME_BUILD_PARAMETER_PORTAL_GITHUB_URL,
			" has an invalid Portal GitHub URL <a href=\"", portalGitHubURL,
			"\">", portalGitHubURL, "</a>");

		Matcher matcher = _pattern.matcher(portalGitHubURL);

		if (!matcher.find()) {
			failBuildRunner(failureMessage);
		}

		String repositoryName = matcher.group("repositoryName");

		if (!repositoryName.equals("liferay-portal") &&
			!repositoryName.equals("liferay-portal-ee")) {

			failBuildRunner(failureMessage);
		}
	}

	private void _validateBuildParameterPortalUpstreamBranchName() {
		String portalUpstreamBranchName = getBuildParameter(
			_NAME_BUILD_PARAMETER_PORTAL_UPSTREAM_BRANCH_NAME);

		if ((portalUpstreamBranchName == null) ||
			portalUpstreamBranchName.isEmpty()) {

			failBuildRunner(
				_NAME_BUILD_PARAMETER_PORTAL_UPSTREAM_BRANCH_NAME + " is null");
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

			StringBuilder sb = new StringBuilder();

			sb.append(_NAME_BUILD_PARAMETER_PORTAL_UPSTREAM_BRANCH_NAME);
			sb.append(" must match one of the following: ");

			sb.append("<ul>");

			for (String allowedPortalUpstreamBranchName :
					allowedPortalUpstreamBranchNamesList) {

				sb.append("<li>");
				sb.append(allowedPortalUpstreamBranchName);
				sb.append("</li>");
			}

			sb.append("</ul>");

			failBuildRunner(sb.toString());
		}
	}

	private static final Integer _COMMITS_GROUP_SIZE_MAX_DEFAULT = 5;

	private static final String _NAME_BUILD_PARAMETER_JENKINS_GITHUB_URL =
		"JENKINS_GITHUB_URL";

	private static final String _NAME_BUILD_PARAMETER_PORTAL_BATCH =
		"PORTAL_BATCH_NAME";

	private static final String
		_NAME_BUILD_PARAMETER_PORTAL_BATCH_TEST_SELECTOR =
			"PORTAL_BATCH_TEST_SELECTOR";

	private static final String _NAME_BUILD_PARAMETER_PORTAL_BRANCH_SHAS =
		"PORTAL_BRANCH_SHAS";

	private static final String _NAME_BUILD_PARAMETER_PORTAL_GITHUB_URL =
		"PORTAL_GITHUB_URL";

	private static final String
		_NAME_BUILD_PARAMETER_PORTAL_UPSTREAM_BRANCH_NAME =
			"PORTAL_UPSTREAM_BRANCH_NAME";

	private static final Pattern _compareURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://github.com/(?<username>[^/]+)/(?<repositoryName>[^/]+)",
			"/compare/(?<earliestSHA>[0-9a-f]{5,40})\\.{3}",
			"(?<latestSHA>[0-9a-f]{5,40})"));
	private static final Pattern _pattern = Pattern.compile(
		"https://github.com/[^/]+/(?<repositoryName>[^/]+)/tree/.+");

}