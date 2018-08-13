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
public class PortalWorkspace extends BaseWorkspace {

	public static boolean isPortalGitHubURL(String gitHubURL) {
		Matcher matcher = _portalGitHubURLPattern.matcher(gitHubURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public void setPortalJobProperties(Job job) {
		portalLocalRepository.setJobProperties(job);
	}

	protected PortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName) {

		this(portalGitHubURL, portalUpstreamBranchName, false);
	}

	protected PortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName,
		boolean synchronizeBranches) {

		String portalRepositoryName = _getPortalRepositoryName(portalGitHubURL);

		portalLocalRepository = _getPortalLocalRepository(
			portalRepositoryName, portalUpstreamBranchName);

		portalLocalGitBranch = _getCachedPortalLocalGitBranch(
			portalLocalRepository, portalGitHubURL, synchronizeBranches);

		addLocalGitBranch(portalLocalGitBranch);
	}

	protected final PortalLocalGitBranch portalLocalGitBranch;
	protected final PortalLocalRepository portalLocalRepository;

	private PortalLocalGitBranch _getCachedPortalLocalGitBranch(
		PortalLocalRepository portalLocalRepository, String portalGitHubURL,
		boolean synchronizeBranches) {

		LocalGitBranch localGitBranch;

		if (PullRequest.isValidGitHubPullRequestURL(portalGitHubURL)) {
			PullRequest pullRequest = new PullRequest(portalGitHubURL);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				portalLocalRepository, pullRequest, synchronizeBranches);
		}
		else if (GitUtil.isValidGitHubRefURL(portalGitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
				portalGitHubURL);

			localGitBranch = LocalGitSyncUtil.createCachedLocalGitBranch(
				portalLocalRepository, remoteGitRef, synchronizeBranches);
		}
		else {
			throw new RuntimeException(
				"Invalid portal github url " + portalGitHubURL);
		}

		if (!(localGitBranch instanceof PortalLocalGitBranch)) {
			throw new RuntimeException(
				"Invalid local git branch " + localGitBranch);
		}

		return (PortalLocalGitBranch)localGitBranch;
	}

	private PortalLocalRepository _getPortalLocalRepository(
		String portalRepositoryName, String portalUpstreamBranchName) {

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			portalRepositoryName, portalUpstreamBranchName);

		if (!(localRepository instanceof PortalLocalRepository)) {
			throw new RuntimeException(
				"Invalid local repository " + localRepository);
		}

		return (PortalLocalRepository)localRepository;
	}

	private String _getPortalRepositoryName(String portalGitHubURL) {
		Matcher matcher = _portalGitHubURLPattern.matcher(portalGitHubURL);

		if (!matcher.find()) {
			throw new RuntimeException(
				"Invalid portal github url " + portalGitHubURL);
		}

		return matcher.group("repositoryName");
	}

	private static final Pattern _portalGitHubURLPattern = Pattern.compile(
		"https://github.com/[^/]+/(?<repositoryName>liferay-portal(-ee)?)/.*");

}