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

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PortalWorkspace extends BaseWorkspace {

	@Override
	public String getUpstreamRepositoryName() {
		String repositoryType = getRepositoryType();
		String upstreamBranchName = getUpstreamBranchName();

		if (upstreamBranchName.equals("master")) {
			return repositoryType;
		}

		return repositoryType + "-ee";
	}

	public void setAppServerProperties(Properties properties) {
		_appServerProperties.putAll(properties);
	}

	public void setBuildProperties(Properties properties) {
		_buildProperties.putAll(properties);
	}

	public void setSQLProperties(Properties properties) {
		_sqlProperties.putAll(properties);
	}

	public void setTestProperties(Properties properties) {
		_testProperties.putAll(properties);
	}

	@Override
	public void setupWorkspace() {
		super.setupWorkspace();

		JenkinsResultsParserUtil.writePropertiesFile(
			_appServerPropertiesFile, _appServerProperties, true);
		JenkinsResultsParserUtil.writePropertiesFile(
			_buildPropertiesFile, _buildProperties, true);
		JenkinsResultsParserUtil.writePropertiesFile(
			_sqlPropertiesFile, _sqlProperties, true);
		JenkinsResultsParserUtil.writePropertiesFile(
			_testPropertiesFile, _testProperties, true);
	}

	protected PortalWorkspace(
		String repositoryType, String upstreamBranchName) {

		super(repositoryType, upstreamBranchName);

		String hostname = System.getenv("HOSTNAME");

		_appServerPropertiesFile = new File(
			getRepositoryDir(),
			JenkinsResultsParserUtil.combine(
				"app.server.", hostname, ".properties"));

		_buildPropertiesFile = new File(
			getRepositoryDir(),
			JenkinsResultsParserUtil.combine(
				"build.", hostname, ".properties"));

		_sqlPropertiesFile = new File(
			getRepositoryDir(),
			JenkinsResultsParserUtil.combine(
				"sql/sql.", hostname, ".properties"));

		_testPropertiesFile = new File(
			getRepositoryDir(),
			JenkinsResultsParserUtil.combine("test.", hostname, ".properties"));
	}

	@Override
	protected File getDefaultRepositoryDir() {
		String repositoryType = getRepositoryType();
		String upstreamBranchName = getUpstreamBranchName();

		if (upstreamBranchName.equals("master")) {
			return new File(getBaseRepositoryDir(), repositoryType);
		}

		return new File(
			getBaseRepositoryDir(), repositoryType + "-" + upstreamBranchName);
	}

	private final Properties _appServerProperties = new Properties();
	private final File _appServerPropertiesFile;
	private final Properties _buildProperties = new Properties();
	private final File _buildPropertiesFile;
	private final Properties _sqlProperties = new Properties();
	private final File _sqlPropertiesFile;
	private final Properties _testProperties = new Properties();
	private final File _testPropertiesFile;

}