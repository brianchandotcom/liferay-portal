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
public abstract class PortalWorkspace extends BaseWorkspace {

	public static boolean isPortalGitHubURL(String gitHubURL) {
		Matcher matcher = _portalGitHubURLPattern.matcher(gitHubURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	protected PortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName) {

		WorkspaceGitRepository workspaceGitRepository =
			WorkspaceUtil.getWorkspaceGitRepository(
				"portal", portalGitHubURL, portalUpstreamBranchName);

		if (!(workspaceGitRepository instanceof
				PortalWorkspaceGitRepository)) {

			throw new RuntimeException("Invalid workspace Git repository");
		}

		_primaryPortalWorkspaceGitRepository =
			(PortalWorkspaceGitRepository)workspaceGitRepository;

		_primaryPortalWorkspaceGitRepository.setUp();

		_companionPortalWorkspaceGitRepository =
			WorkspaceUtil.getDependencyWorkspaceGitRepository(
				CompanionPortalWorkspaceGitRepository.TYPE,
				_primaryPortalWorkspaceGitRepository);

		_companionPortalWorkspaceGitRepository.setUp();

		_otherPortalWorkspaceGitRepository =
			WorkspaceUtil.getDependencyWorkspaceGitRepository(
				OtherPortalWorkspaceGitRepository.TYPE,
				_primaryPortalWorkspaceGitRepository);

		_pluginsWorkspaceGitRepository =
			WorkspaceUtil.getDependencyWorkspaceGitRepository(
				PortalPluginsWorkspaceGitRepository.TYPE,
				_primaryPortalWorkspaceGitRepository);
	}

	protected WorkspaceGitRepository getOtherPortalWorkspaceGitRepository() {
		return _otherPortalWorkspaceGitRepository;
	}

	protected PortalWorkspaceGitRepository
		getPrimaryPortalWorkspaceGitRepository() {

		return _primaryPortalWorkspaceGitRepository;
	}

	@Override
	protected void setUpWorkspaceGitRepositories() {
		setUpJenkinsWorkspaceGitRepository();

		_primaryPortalWorkspaceGitRepository.setUp();

		if (_companionPortalWorkspaceGitRepository != null) {
			_companionPortalWorkspaceGitRepository.setUp();
		}

		if (_otherPortalWorkspaceGitRepository != null) {
			_otherPortalWorkspaceGitRepository.setUp();
		}

		_pluginsWorkspaceGitRepository.setUp();
	}

	@Override
	protected void setWorkspaceGitRepositoryJobProperties(Job job) {
		_primaryPortalWorkspaceGitRepository.setPortalJobProperties(job);
	}

	@Override
	protected void tearDownWorkspaceGitRepositories() {
		tearDownJenkinsWorkspaceGitRepository();

		_primaryPortalWorkspaceGitRepository.tearDown();

		if (_companionPortalWorkspaceGitRepository != null) {
			_companionPortalWorkspaceGitRepository.tearDown();
		}

		if (_otherPortalWorkspaceGitRepository != null) {
			_otherPortalWorkspaceGitRepository.tearDown();
		}

		_pluginsWorkspaceGitRepository.tearDown();
	}

	@Override
	protected void writeWorkspaceGitRepositoryPropertiesFiles() {
		_primaryPortalWorkspaceGitRepository.writePropertiesFiles();

		_pluginsWorkspaceGitRepository.writePropertiesFiles();
	}

	private static final Pattern _portalGitHubURLPattern = Pattern.compile(
		"https://github.com/[^/]+/(?<gitRepositoryName>" +
			"liferay-portal(-ee)?)/.*");

	private final WorkspaceGitRepository _companionPortalWorkspaceGitRepository;
	private final WorkspaceGitRepository _otherPortalWorkspaceGitRepository;
	private final WorkspaceGitRepository _pluginsWorkspaceGitRepository;
	private final PortalWorkspaceGitRepository
		_primaryPortalWorkspaceGitRepository;

}