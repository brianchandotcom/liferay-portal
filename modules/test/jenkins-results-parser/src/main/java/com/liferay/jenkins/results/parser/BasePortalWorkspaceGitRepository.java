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

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public void setPortalJobProperties(Job job) {
		setPortalAppServerProperties(
			_getPortalJobProperties("portal.app.server.properties", job));

		setPortalBuildProperties(
			_getPortalJobProperties("portal.build.properties", job));

		setPortalReleaseProperties(
			_getPortalJobProperties("portal.release.properties", job));

		setPortalSQLProperties(
			_getPortalJobProperties("portal.sql.properties", job));

		setPortalTestProperties(
			_getPortalJobProperties("portal.test.properties", job));
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

		_setBasePortalAppServerProperties();
		_setBasePortalBuildProperties();
	}

	protected BasePortalWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(pullRequest, upstreamBranchName);

		_setBasePortalAppServerProperties();
		_setBasePortalBuildProperties();
	}

	protected BasePortalWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef, upstreamBranchName);

		_setBasePortalAppServerProperties();
		_setBasePortalBuildProperties();
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

	private Properties _getPortalJobProperties(String propertyType, Job job) {
		Properties jobProperties = job.getJobProperties();

		Pattern pattern = Pattern.compile(
			propertyType + "\\[(?<propertyName>[^\\]]+)\\]");

		Set<String> propertyNames = new HashSet<>();

		for (String jobPropertyName : jobProperties.stringPropertyNames()) {
			Matcher matcher = pattern.matcher(jobPropertyName);

			if (!matcher.find()) {
				continue;
			}

			propertyNames.add(matcher.group("propertyName"));
		}

		Properties properties = new Properties();

		for (String propertyName : propertyNames) {
			String propertyValue = JenkinsResultsParserUtil.getProperty(
				jobProperties, propertyType, propertyName,
				getUpstreamBranchName());

			if ((propertyValue == null) && (job instanceof TestSuiteJob)) {
				TestSuiteJob testSuiteJob = (TestSuiteJob)job;

				propertyValue = JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyType, propertyName,
					testSuiteJob.getTestSuiteName());
			}

			if ((propertyValue == null) &&
				JenkinsResultsParserUtil.isWindows()) {

				propertyValue = JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyType, propertyName, "windows");
			}

			if (propertyValue != null) {
				properties.put(propertyName, propertyValue);
			}
		}

		return properties;
	}

	private void _setBasePortalAppServerProperties() {
		Properties properties = new Properties();

		properties.put("app.server.parent.dir", getDirectory() + "/bundles");

		setPortalAppServerProperties(properties);
	}

	private void _setBasePortalBuildProperties() {
		Properties properties = new Properties();

		properties.put("jsp.precompile", "off");
		properties.put("jsp.precompile.parallel", "off");
		properties.put("liferay.home", getDirectory() + "/bundles");

		setPortalBuildProperties(properties);
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