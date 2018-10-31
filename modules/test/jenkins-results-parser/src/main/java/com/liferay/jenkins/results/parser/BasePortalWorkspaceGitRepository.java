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

import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalWorkspaceGitRepository
	extends BaseWorkspaceGitRepository implements PortalWorkspaceGitRepository {

	@Override
	public void setPortalAppServerProperties(Properties properties) {
		setProperties(_FILE_PATH_APP_SERVER_PROPERTIES, properties);
	}

	@Override
	public void setPortalBuildProperties(Properties properties) {
		setProperties(_FILE_PATH_BUILD_PROPERTIES, properties);
	}

	@Override
	public void setPortalReleaseProperties(Properties properties) {
		setProperties(_FILE_PATH_RELEASE_PROPERTIES, properties);
	}

	@Override
	public void setPortalSQLProperties(Properties properties) {
		setProperties(_FILE_PATH_SQL_PROPERTIES, properties);
	}

	@Override
	public void setPortalTestProperties(Properties properties) {
		setProperties(_FILE_PATH_TEST_PROPERTIES, properties);
	}

	protected BasePortalWorkspaceGitRepository(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected BasePortalWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(pullRequest, upstreamBranchName);
	}

	protected BasePortalWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef, upstreamBranchName);
	}

	@Override
	protected String getDefaultRelativeGitRepositoryDirPath(
		String upstreamBranchName) {

		String name = getName();

		if (upstreamBranchName.equals("master")) {
			return name.replace("-ee", "");
		}

		return JenkinsResultsParserUtil.combine(
			name.replace("-ee", ""), "-", upstreamBranchName);
	}

	private static final String _FILE_PATH_APP_SERVER_PROPERTIES =
		JenkinsResultsParserUtil.combine(
			"app.server.", System.getenv("HOSTNAME"), ".properties");

	private static final String _FILE_PATH_BUILD_PROPERTIES =
		JenkinsResultsParserUtil.combine(
			"build.", System.getenv("HOSTNAME"), ".properties");

	private static final String _FILE_PATH_RELEASE_PROPERTIES =
		JenkinsResultsParserUtil.combine(
			"release.", System.getenv("HOSTNAME"), ".properties");

	private static final String _FILE_PATH_SQL_PROPERTIES =
		JenkinsResultsParserUtil.combine(
			"sql/sql.", System.getenv("HOSTNAME"), ".properties");

	private static final String _FILE_PATH_TEST_PROPERTIES =
		JenkinsResultsParserUtil.combine(
			"test.", System.getenv("HOSTNAME"), ".properties");

}