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

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalWorkspace
	extends BaseWorkspace implements PortalWorkspace {

	public static boolean isPortalGitHubURL(String gitHubURL) {
		Matcher matcher = _portalGitHubURLPattern.matcher(gitHubURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	@Override
	public WorkspaceGitRepository getCompanionPortalWorkspaceGitRepository() {
		return _companionPortalWorkspaceGitRepository;
	}

	@Override
	public LegacyWorkspaceGitRepository getLegacyWorkspaceGitRepository() {
		return legacyWorkspaceGitRepository;
	}

	@Override
	public WorkspaceGitRepository getOtherPortalWorkspaceGitRepository() {
		return _otherPortalWorkspaceGitRepository;
	}

	@Override
	public WorkspaceGitRepository getPluginsWorkspaceGitRepository() {
		return _pluginsWorkspaceGitRepository;
	}

	@Override
	public PortalWorkspaceGitRepository
		getPrimaryPortalWorkspaceGitRepository() {

		return _primaryPortalWorkspaceGitRepository;
	}

	protected BasePortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName) {

		this(portalGitHubURL, portalUpstreamBranchName, null);
	}

	protected BasePortalWorkspace(
		String portalGitHubURL, String portalUpstreamBranchName,
		String portalBranchSHA) {

		WorkspaceGitRepository workspaceGitRepository =
			WorkspaceUtil.getWorkspaceGitRepository(
				"portal", portalGitHubURL, portalUpstreamBranchName);

		if (!(workspaceGitRepository instanceof PortalWorkspaceGitRepository)) {
			throw new RuntimeException("Invalid workspace Git repository");
		}

		_primaryPortalWorkspaceGitRepository =
			(PortalWorkspaceGitRepository)workspaceGitRepository;

		if (portalBranchSHA != null) {
			_primaryPortalWorkspaceGitRepository.setBranchSHA(portalBranchSHA);
		}

		_primaryPortalWorkspaceGitRepository.setUp();

		_companionPortalWorkspaceGitRepository =
			WorkspaceUtil.getDependencyWorkspaceGitRepository(
				CompanionPortalWorkspaceGitRepository.TYPE,
				_primaryPortalWorkspaceGitRepository);

		if (_companionPortalWorkspaceGitRepository != null) {
			_companionPortalWorkspaceGitRepository.setUp();
		}

		workspaceGitRepository = WorkspaceUtil.getWorkspaceGitRepository(
			LegacyWorkspaceGitRepository.TYPE,
			_URL_GITHUB_PORTAL_LEGACY_EE_REPOSITORY, "master");

		if (!(workspaceGitRepository instanceof LegacyWorkspaceGitRepository)) {
			throw new RuntimeException("Invalid workspace Git repository");
		}

		legacyWorkspaceGitRepository =
			(LegacyWorkspaceGitRepository)workspaceGitRepository;

		_otherPortalWorkspaceGitRepository =
			WorkspaceUtil.getDependencyWorkspaceGitRepository(
				OtherPortalWorkspaceGitRepository.TYPE,
				_primaryPortalWorkspaceGitRepository);

		_pluginsWorkspaceGitRepository =
			WorkspaceUtil.getDependencyWorkspaceGitRepository(
				PortalPluginsWorkspaceGitRepository.TYPE,
				_primaryPortalWorkspaceGitRepository);
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
	protected void setWorkspaceBuildDataProperties(BuildData buildData) {
		Map<String, String> topLevelBuildParameters =
			buildData.getTopLevelBuildParameters();

		Properties portalTestProperties = new Properties();

		if (topLevelBuildParameters.containsKey("PORTAL_BATCH_NAME") &&
			topLevelBuildParameters.containsKey("PORTAL_BATCH_TEST_SELECTOR")) {

			String portalBatchName = topLevelBuildParameters.get(
				"PORTAL_BATCH_NAME");

			if (portalBatchName.contains("integration") ||
				portalBatchName.contains("unit")) {

				portalTestProperties.setProperty(
					JenkinsResultsParserUtil.combine(
						"test.batch.axis.count[", portalBatchName, "]"),
					"1");

				portalTestProperties.setProperty(
					JenkinsResultsParserUtil.combine(
						"test.batch.class.names.includes[", portalBatchName,
						"]"),
					topLevelBuildParameters.get("PORTAL_BATCH_TEST_SELECTOR"));
			}
		}

		_primaryPortalWorkspaceGitRepository.setPortalTestProperties(
			portalTestProperties);
	}

	@Override
	protected void setWorkspaceDefaultProperties() {
		Properties portalAppServerProperties = new Properties();

		portalAppServerProperties.put(
			"app.server.parent.dir",
			_primaryPortalWorkspaceGitRepository.getDirectory() + "/bundles");

		_primaryPortalWorkspaceGitRepository.setPortalAppServerProperties(
			portalAppServerProperties);

		Properties portalBuildProperties = new Properties();

		portalBuildProperties.put("jsp.precompile", "off");
		portalBuildProperties.put(
			"liferay.home",
			_primaryPortalWorkspaceGitRepository.getDirectory() + "/bundles");

		_primaryPortalWorkspaceGitRepository.setPortalBuildProperties(
			portalBuildProperties);

		Properties portalTestProperties = new Properties();

		portalTestProperties.put(
			"test.url", "http://" + System.getenv("HOSTNAME") + ":8080");

		_primaryPortalWorkspaceGitRepository.setPortalTestProperties(
			portalTestProperties);
	}

	@Override
	protected void setWorkspaceJobProperties(Job job) {
		_primaryPortalWorkspaceGitRepository.setPortalAppServerProperties(
			_primaryPortalWorkspaceGitRepository.getWorkspaceJobProperties(
				"portal.app.server.properties", job));

		_primaryPortalWorkspaceGitRepository.setPortalBuildProperties(
			_primaryPortalWorkspaceGitRepository.getWorkspaceJobProperties(
				"portal.build.properties", job));

		_primaryPortalWorkspaceGitRepository.setPortalReleaseProperties(
			_primaryPortalWorkspaceGitRepository.getWorkspaceJobProperties(
				"portal.release.properties", job));

		_primaryPortalWorkspaceGitRepository.setPortalSQLProperties(
			_primaryPortalWorkspaceGitRepository.getWorkspaceJobProperties(
				"portal.sql.properties", job));

		_primaryPortalWorkspaceGitRepository.setPortalTestProperties(
			_primaryPortalWorkspaceGitRepository.getWorkspaceJobProperties(
				"portal.test.properties", job));
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

	protected final LegacyWorkspaceGitRepository legacyWorkspaceGitRepository;

	private static final String _URL_GITHUB_PORTAL_LEGACY_EE_REPOSITORY =
		"https://github.com/liferay/liferay-qa-portal-legacy-ee/tree/master";

	private static final Pattern _portalGitHubURLPattern = Pattern.compile(
		"https://github.com/[^/]+/(?<gitRepositoryName>" +
			"liferay-portal(-ee)?)/.*");

	private final WorkspaceGitRepository _companionPortalWorkspaceGitRepository;
	private final WorkspaceGitRepository _otherPortalWorkspaceGitRepository;
	private final WorkspaceGitRepository _pluginsWorkspaceGitRepository;
	private final PortalWorkspaceGitRepository
		_primaryPortalWorkspaceGitRepository;

}