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

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public class PullRequestPortalTopLevelBuild
	extends PortalTopLevelBuild implements PullRequestBuild, WorkspaceBuild {

	public PullRequestPortalTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);

		setCompareToUpstream(true);

		try {
			String testSuiteName = getTestSuiteName();

			if (testSuiteName.equals("relevant")) {
				_stableJob = JobFactory.newJob(jobName, "stable", branchName);
			}
		}
		catch (Exception exception) {
			System.out.println("Unable to create stable job for " + jobName);

			exception.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");
		sb.append(getParameterValue("GITHUB_RECEIVER_USERNAME"));
		sb.append("/liferay-portal");

		String branchName = getBranchName();

		if (!branchName.equals("master")) {
			sb.append("-ee");
		}

		sb.append("/pull/");
		sb.append(getParameterValue("GITHUB_PULL_REQUEST_NUMBER"));

		_pullRequest = PullRequestFactory.newPullRequest(sb.toString());
	}

	public boolean bypassCITestRelevant() {
		String testSuiteName = getTestSuiteName();

		if ((testSuiteName == null) || !testSuiteName.equals("relevant")) {
			return false;
		}

		Workspace workspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		workspaceGitRepository.setUp();

		String ciTestRelevantBypassFilePathPatterns =
			JenkinsResultsParserUtil.getCIProperty(
				workspaceGitRepository.getUpstreamBranchName(),
				"ci.test.relevant.bypass.file.path.patterns",
				workspaceGitRepository.getName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(
				ciTestRelevantBypassFilePathPatterns)) {

			return false;
		}

		MultiPattern multiPattern = new MultiPattern(
			ciTestRelevantBypassFilePathPatterns.split("\\s*,\\s*"));

		List<String> modifiedFilePaths = new ArrayList<>();

		GitWorkingDirectory gitWorkingDirectory =
			workspaceGitRepository.getGitWorkingDirectory();

		for (File modifiedFile : gitWorkingDirectory.getModifiedFilesList()) {
			modifiedFilePaths.add(
				JenkinsResultsParserUtil.getCanonicalPath(modifiedFile));
		}

		if (!multiPattern.matchesAll(
				modifiedFilePaths.toArray(new String[0]))) {

			return false;
		}

		return true;
	}

	@Override
	public BranchInformation getOSBAsahBranchInformation() {
		Workspace workspace = getWorkspace();

		return new WorkspaceBranchInformation(
			workspace.getWorkspaceGitRepository(
				"com-liferay-osb-asah-private"));
	}

	@Override
	public BranchInformation getOSBFaroBranchInformation() {
		Workspace workspace = getWorkspace();

		return new WorkspaceBranchInformation(
			workspace.getWorkspaceGitRepository(
				"com-liferay-osb-faro-private"));
	}

	@Override
	public BranchInformation getPluginsBranchInformation() {
		Workspace workspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		String pluginsUpstreamBranchName =
			workspaceGitRepository.getUpstreamBranchName();

		if (!pluginsUpstreamBranchName.startsWith("ee-")) {
			pluginsUpstreamBranchName = "7.0.x";
		}

		return new WorkspaceBranchInformation(
			workspace.getWorkspaceGitRepository(
				JenkinsResultsParserUtil.getGitDirectoryName(
					"liferay-plugins-ee", pluginsUpstreamBranchName)));
	}

	@Override
	public BranchInformation getPortalBranchInformation() {
		Workspace workspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		return new WorkspaceBranchInformation(workspaceGitRepository);
	}

	@Override
	public PullRequest getPullRequest() {
		return _pullRequest;
	}

	@Override
	public String getResult() {
		String result = super.getResult();

		List<Build> downstreamBuildFailures = getFailedDownstreamBuilds();

		if ((result == null) || downstreamBuildFailures.isEmpty()) {
			return result;
		}

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		boolean pullRequestForwardUpstreamFailureComparisonEnabled =
			Boolean.parseBoolean(
				buildProperties.getProperty(
					"pull.request.forward.upstream.failure.comparison." +
						"enabled"));

		if (!pullRequestForwardUpstreamFailureComparisonEnabled ||
			!isCompareToUpstream()) {

			return result;
		}

		String testSuiteName = getTestSuiteName();

		if (!testSuiteName.matches("relevant|stable")) {
			return result;
		}

		String batchWhitelist = buildProperties.getProperty(
			"pull.request.forward.upstream.failure.comparison.batch.whitelist");

		List<String> whitelistedBatchRegexes = Arrays.asList(
			batchWhitelist.split("\\s*,\\s*"));

		for (Build downstreamBuild : downstreamBuildFailures) {
			if (downstreamBuild.isUniqueFailure()) {
				return result;
			}

			boolean approved = false;

			String jobVariant = downstreamBuild.getJobVariant();

			jobVariant = jobVariant.replaceAll("(.*)/.*", "$1");

			for (String whiteListedBatchRegex : whitelistedBatchRegexes) {
				if (jobVariant.matches(".*" + whiteListedBatchRegex + ".*")) {
					approved = true;

					break;
				}
			}

			if (!approved) {
				return result;
			}
		}

		return "APPROVED";
	}

	public String getStableJobResult() {
		if (_stableJob == null) {
			return null;
		}

		if (_stableJobResult != null) {
			return _stableJobResult;
		}

		List<Build> stableJobDownstreamBuilds = getStableJobDownstreamBuilds();

		int stableJobDownstreamBuildsSize = stableJobDownstreamBuilds.size();

		if (stableJobDownstreamBuildsSize == 0) {
			return null;
		}

		List<String> stableJobBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		int stableJobDownstreamBuildsCompletedCount =
			getJobVariantsDownstreamBuildCount(
				stableJobBatchNames, null, "completed");

		if (stableJobDownstreamBuildsCompletedCount !=
				stableJobDownstreamBuildsSize) {

			return null;
		}

		String result = getResult();
		int stableJobDownstreamBuildsSuccessCount =
			getJobVariantsDownstreamBuildCount(
				stableJobBatchNames, "SUCCESS", null);

		if (((result != null) && result.matches("(APPROVED|SUCCESS)")) ||
			(stableJobDownstreamBuildsSuccessCount ==
				stableJobDownstreamBuildsSize)) {

			_stableJobResult = "SUCCESS";
		}
		else {
			_stableJobResult = "FAILURE";
		}

		return _stableJobResult;
	}

	@Override
	public Workspace getWorkspace() {
		PullRequest pullRequest = getPullRequest();

		Workspace workspace = WorkspaceFactory.newWorkspace(
			pullRequest.getGitRepositoryName(),
			pullRequest.getUpstreamRemoteGitBranchName());

		if (workspace instanceof PortalWorkspace) {
			PortalWorkspace portalWorkspace = (PortalWorkspace)workspace;

			portalWorkspace.setOSBAsahGitHubURL(_getOSBAsahGitHubURL());
			portalWorkspace.setOSBFaroGitHubURL(_getOSBFaroGitHubURL());
			portalWorkspace.setPortalBuildProfile(_getPortalBuildProfile());
		}

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		workspaceGitRepository.setGitHubURL(pullRequest.getHtmlURL());

		String senderBranchSHA = _getSenderBranchSHA();

		if (JenkinsResultsParserUtil.isSHA(senderBranchSHA)) {
			workspaceGitRepository.setSenderBranchSHA(senderBranchSHA);
		}

		String upstreamBranchSHA = _getUpstreamBranchSHA();

		if (JenkinsResultsParserUtil.isSHA(upstreamBranchSHA)) {
			workspaceGitRepository.setBaseBranchSHA(upstreamBranchSHA);
		}

		return workspace;
	}

	@Override
	public boolean isUniqueFailure() {
		List<Build> failedDownstreamBuilds = getFailedDownstreamBuilds();

		for (Build downstreamBuild : failedDownstreamBuilds) {
			if (downstreamBuild.isUniqueFailure()) {
				return true;
			}
		}

		if (failedDownstreamBuilds.isEmpty()) {
			return true;
		}

		return false;
	}

	public static class WorkspaceBranchInformation
		implements BranchInformation {

		@Override
		public String getCachedRemoteGitRefName() {
			return _workspaceGitRepository.getGitHubDevBranchName();
		}

		@Override
		public String getOriginName() {
			return _workspaceGitRepository.getSenderBranchUsername();
		}

		@Override
		public Integer getPullRequestNumber() {
			Matcher matcher = _pattern.matcher(
				_workspaceGitRepository.getGitHubURL());

			if (!matcher.find()) {
				return 0;
			}

			return Integer.parseInt(matcher.group("pullNumber"));
		}

		@Override
		public String getReceiverUsername() {
			Matcher matcher = _pattern.matcher(
				_workspaceGitRepository.getGitHubURL());

			if (!matcher.find()) {
				return "liferay";
			}

			return matcher.group("username");
		}

		@Override
		public String getRepositoryName() {
			return _workspaceGitRepository.getName();
		}

		@Override
		public String getSenderBranchName() {
			return _workspaceGitRepository.getSenderBranchName();
		}

		@Override
		public String getSenderBranchSHA() {
			return _workspaceGitRepository.getSenderBranchSHA();
		}

		@Override
		public RemoteGitRef getSenderRemoteGitRef() {
			String remoteURL = JenkinsResultsParserUtil.combine(
				"git@github.com:", getSenderUsername(), "/",
				getRepositoryName(), ".git");

			return GitUtil.getRemoteGitRef(
				getSenderBranchName(), new File("."), remoteURL);
		}

		@Override
		public String getSenderUsername() {
			return _workspaceGitRepository.getSenderBranchUsername();
		}

		@Override
		public String getUpstreamBranchName() {
			return _workspaceGitRepository.getUpstreamBranchName();
		}

		@Override
		public String getUpstreamBranchSHA() {
			return _workspaceGitRepository.getBaseBranchSHA();
		}

		protected WorkspaceBranchInformation(
			WorkspaceGitRepository workspaceGitRepository) {

			_workspaceGitRepository = workspaceGitRepository;
		}

		private static final Pattern _pattern = Pattern.compile(
			"https://github.com/(?<username>[^/]+)/[^/]/pull/" +
				"(?<pullNumber>\\d+)");

		private final WorkspaceGitRepository _workspaceGitRepository;

	}

	protected Element getFailedStableJobSummaryElement() {
		List<String> stableJobBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		Element jobSummaryListElement = getJobSummaryListElement(
			false, stableJobBatchNames);

		int stableJobDownstreamBuildsSuccessCount =
			getJobVariantsDownstreamBuildCount(
				stableJobBatchNames, "SUCCESS", null);

		int stableJobDownstreamBuildsCount = getJobVariantsDownstreamBuildCount(
			stableJobBatchNames, null, null);

		int stableJobDownstreamBuildsFailureCount =
			stableJobDownstreamBuildsCount -
				stableJobDownstreamBuildsSuccessCount;

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"h4", null,
				String.valueOf(stableJobDownstreamBuildsFailureCount),
				" Failed Jobs:"),
			jobSummaryListElement);
	}

	protected List<Build> getStableJobDownstreamBuilds() {
		if (_stableJob != null) {
			return getJobVariantsDownstreamBuilds(
				_stableJob.getBatchNames(), null, null);
		}

		return Collections.emptyList();
	}

	protected Element getStableJobResultElement() {
		if (_stableJob == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		String stableJobResult = getStableJobResult();

		if (stableJobResult.equals("SUCCESS")) {
			sb.append(":heavy_check_mark: ");
		}
		else {
			sb.append(":x: ");
		}

		sb.append("ci:test:stable - ");

		sb.append(
			getJobVariantsDownstreamBuildCount(
				new ArrayList<>(_stableJob.getBatchNames()), "SUCCESS", null));

		sb.append(" out of ");

		List<Build> stableJobDownstreamBuilds = getStableJobDownstreamBuilds();

		sb.append(stableJobDownstreamBuilds.size());

		sb.append(" jobs passed");

		return Dom4JUtil.getNewElement("h3", null, sb.toString());
	}

	protected Element getStableJobSuccessSummaryElement() {
		List<String> stableJobBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		Element stableJobSummaryListElement = getJobSummaryListElement(
			true, stableJobBatchNames);

		int stableJobDownstreamBuildsSuccessCount =
			getJobVariantsDownstreamBuildCount(
				stableJobBatchNames, "SUCCESS", null);

		return Dom4JUtil.getNewElement(
			"details", null,
			Dom4JUtil.getNewElement(
				"summary", null,
				Dom4JUtil.getNewElement(
					"strong", null,
					String.valueOf(stableJobDownstreamBuildsSuccessCount),
					" Successful Jobs:")),
			stableJobSummaryListElement);
	}

	protected Element getStableJobSummaryElement() {
		List<String> stableJobBatchNames = new ArrayList<>(
			_stableJob.getBatchNames());

		int stableJobDownstreamBuildSuccessCount =
			getJobVariantsDownstreamBuildCount(
				stableJobBatchNames, "SUCCESS", null);

		List<Build> stableJobDownstreamBuilds = getStableJobDownstreamBuilds();

		Element detailsElement = Dom4JUtil.getNewElement(
			"details", null,
			Dom4JUtil.getNewElement(
				"summary", null,
				Dom4JUtil.getNewElement(
					"strong", null, "ci:test:stable - ",
					String.valueOf(stableJobDownstreamBuildSuccessCount),
					" out of ",
					String.valueOf(stableJobDownstreamBuilds.size()),
					" jobs PASSED")));

		int stableJobDownstreamBuildCount = getJobVariantsDownstreamBuildCount(
			stableJobBatchNames, null, null);

		if (stableJobDownstreamBuildSuccessCount <
				stableJobDownstreamBuildCount) {

			Dom4JUtil.addToElement(
				detailsElement, getFailedStableJobSummaryElement());
		}

		if (stableJobDownstreamBuildSuccessCount > 0) {
			Dom4JUtil.addToElement(
				detailsElement, getStableJobSuccessSummaryElement());
		}

		return detailsElement;
	}

	@Override
	protected Element getTopGitHubMessageElement() {
		Element rootElement = super.getTopGitHubMessageElement();

		List<Build> stableJobDownstreamBuilds = new ArrayList<>();

		if (_stableJob != null) {
			stableJobDownstreamBuilds.addAll(getStableJobDownstreamBuilds());
		}

		if (!stableJobDownstreamBuilds.isEmpty()) {
			Dom4JUtil.insertElementAfter(
				rootElement, null, getStableJobResultElement());
		}

		Element detailsElement = rootElement.element("details");

		if (!stableJobDownstreamBuilds.isEmpty()) {
			Element jobSummaryElement = detailsElement.element("details");

			Dom4JUtil.insertElementBefore(
				detailsElement, jobSummaryElement,
				getStableJobSummaryElement());
		}

		return rootElement;
	}

	private String _getOSBAsahGitHubURL() {
		String osbAsahGitHubURL = getParameterValue("OSB_ASAH_GITHUB_URL");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(osbAsahGitHubURL)) {
			return osbAsahGitHubURL;
		}

		Build controllerBuild = getControllerBuild();

		if (controllerBuild != null) {
			return controllerBuild.getParameterValue("OSB_ASAH_GITHUB_URL");
		}

		return null;
	}

	private String _getOSBFaroGitHubURL() {
		String osbFaroGitHubURL = getParameterValue("OSB_FARO_GITHUB_URL");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(osbFaroGitHubURL)) {
			return osbFaroGitHubURL;
		}

		Build controllerBuild = getControllerBuild();

		if (controllerBuild != null) {
			return controllerBuild.getParameterValue("OSB_FARO_GITHUB_URL");
		}

		return null;
	}

	private String _getPortalBuildProfile() {
		String portalBuildProfile = getParameterValue(
			"TEST_PORTAL_BUILD_PROFILE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBuildProfile)) {
			return "dxp";
		}

		return portalBuildProfile;
	}

	private String _getSenderBranchSHA() {
		String senderBranchSHA = getParameterValue("GITHUB_SENDER_BRANCH_SHA");

		if (JenkinsResultsParserUtil.isSHA(senderBranchSHA)) {
			return senderBranchSHA;
		}

		return null;
	}

	private String _getUpstreamBranchSHA() {
		String upstreamBranchSHA = getParameterValue(
			"GITHUB_UPSTREAM_BRANCH_SHA");

		if (JenkinsResultsParserUtil.isSHA(upstreamBranchSHA)) {
			return upstreamBranchSHA;
		}

		String portalBundlesDistURL = getParameterValue(
			"PORTAL_BUNDLES_DIST_URL");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBundlesDistURL)) {
			return null;
		}

		try {
			URL portalBundlesGitHashURL = new URL(
				JenkinsResultsParserUtil.getLocalURL(portalBundlesDistURL) +
					"/git-hash");

			if (!JenkinsResultsParserUtil.exists(portalBundlesGitHashURL)) {
				return null;
			}

			String portalBundlesGitHash = JenkinsResultsParserUtil.toString(
				portalBundlesGitHashURL.toString());

			portalBundlesGitHash = portalBundlesGitHash.trim();

			if (JenkinsResultsParserUtil.isSHA(portalBundlesGitHash)) {
				return portalBundlesGitHash;
			}

			return null;
		}
		catch (IOException ioException) {
			return null;
		}
	}

	private final PullRequest _pullRequest;
	private Job _stableJob;
	private String _stableJobResult;

}