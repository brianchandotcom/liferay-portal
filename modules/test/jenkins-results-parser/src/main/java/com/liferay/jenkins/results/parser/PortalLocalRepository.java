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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalLocalRepository extends LocalRepository {

	public void setAppServerProperties(Properties properties) {
		setProperties(_FILE_PATH_APP_SERVER_PROPERTIES, properties);
	}

	public void setBuildProperties(Properties properties) {
		setProperties(_FILE_PATH_BUILD_PROPERTIES, properties);
	}

	public void setJobProperties(Job job) {
		Properties properties = new Properties();

		Properties jobProperties = job.getJobProperties();

		for (String jobPropertyName : jobProperties.stringPropertyNames()) {
			Matcher matcher = _pattern.matcher(jobPropertyName);

			if (matcher.find()) {
				String portalBuildPropertyName = matcher.group(
					"portalBuildPropertyName");

				properties.put(
					portalBuildPropertyName,
					JenkinsResultsParserUtil.getProperty(
						jobProperties, jobPropertyName));
			}
		}

		setBuildProperties(properties);
	}

	public void setSQLProperties(Properties properties) {
		setProperties(_FILE_PATH_SQL_PROPERTIES, properties);
	}

	public void setTestProperties(Properties properties) {
		setProperties(_FILE_PATH_TEST_PROPERTIES, properties);
	}

	protected PortalLocalRepository(String name, String upstreamBranchName) {
		super(name, upstreamBranchName);

		if (upstreamBranchName.startsWith("ee-") ||
			upstreamBranchName.endsWith("-private")) {

			if (!name.endsWith("-ee")) {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"The local repository, ", name,
						" should not be used with upstream branch ",
						upstreamBranchName, ". Use ", name, "-ee."));
			}
		}

		_setBaseAppServerProperties();
		_setBaseBuildProperties();
	}

	@Override
	protected String getDefaultRelativeRepositoryDirPath() {
		String upstreamBranchName = getUpstreamBranchName();

		if (upstreamBranchName.equals("master")) {
			return name.replace("-ee", "");
		}

		return JenkinsResultsParserUtil.combine(
			name.replace("-ee", ""), "-", upstreamBranchName);
	}

	private void _setBaseAppServerProperties() {
		Properties properties = new Properties();

		properties.put("app.server.parent.dir", getDirectory() + "/bundles");

		setAppServerProperties(properties);
	}

	private void _setBaseBuildProperties() {
		Properties properties = new Properties();

		properties.put("jsp.precompile", "off");
		properties.put("jsp.precompile.parallel", "off");
		properties.put("liferay.home", getDirectory() + "/bundles");

		setBuildProperties(properties);
	}

	private static final String _FILE_PATH_APP_SERVER_PROPERTIES;

	private static final String _FILE_PATH_BUILD_PROPERTIES;

	private static final String _FILE_PATH_SQL_PROPERTIES;

	private static final String _FILE_PATH_TEST_PROPERTIES;

	private static final Pattern _pattern = Pattern.compile(
		"portal.build.properties\\[(?<portalBuildPropertyName>[^\\]]+)\\]");

	static {
		String hostname = System.getenv("HOSTNAME");

		_FILE_PATH_APP_SERVER_PROPERTIES = JenkinsResultsParserUtil.combine(
			"app.server.", hostname, ".properties");
		_FILE_PATH_BUILD_PROPERTIES = JenkinsResultsParserUtil.combine(
			"build.", hostname, ".properties");
		_FILE_PATH_TEST_PROPERTIES = JenkinsResultsParserUtil.combine(
			"test.", hostname, ".properties");
		_FILE_PATH_SQL_PROPERTIES = JenkinsResultsParserUtil.combine(
			"sql/sql.", hostname, ".properties");
	}

}