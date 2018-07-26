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

/**
 * @author Michael Hashimoto
 */
public class PortalRepositoryPropertiesFiles
	extends BaseRepositoryPropertiesFiles {

	public PortalRepositoryPropertiesFiles(LocalRepository localRepository) {
		super(localRepository);

		if (!(localRepository instanceof PortalLocalRepository)) {
			throw new RuntimeException("Invalid local repository");
		}

		_setBaseAppServerProperties();
		_setBaseBuildProperties();
	}

	public void setAppServerProperties(Properties properties) {
		setProperties(_APP_SERVER_PROPERTIES, properties);
	}

	public void setBuildProperties(Properties properties) {
		setProperties(_BUILD_PROPERTIES, properties);
	}

	public void setSQLProperties(Properties properties) {
		setProperties(_SQL_PROPERTIES, properties);
	}

	public void setTestProperties(Properties properties) {
		setProperties(_TEST_PROPERTIES, properties);
	}

	private void _setBaseAppServerProperties() {
		Properties properties = new Properties();

		LocalRepository localRepository = getLocalRepository();

		properties.put(
			"app.server.parent.dir",
			localRepository.getDirectory() + "/bundles");

		setAppServerProperties(properties);
	}

	private void _setBaseBuildProperties() {
		Properties properties = new Properties();

		properties.put("jsp.precompile", "off");
		properties.put("jsp.precompile.parallel", "off");

		LocalRepository localRepository = getLocalRepository();

		properties.put(
			"liferay.home", localRepository.getDirectory() + "/bundles");

		setBuildProperties(properties);
	}

	private static final String _APP_SERVER_PROPERTIES;

	private static final String _BUILD_PROPERTIES;

	private static final String _SQL_PROPERTIES;

	private static final String _TEST_PROPERTIES;

	static {
		String hostname = System.getenv("HOSTNAME");

		_APP_SERVER_PROPERTIES = JenkinsResultsParserUtil.combine(
			"app.server.", hostname, ".properties");
		_BUILD_PROPERTIES = JenkinsResultsParserUtil.combine(
			"build.", hostname, ".properties");
		_TEST_PROPERTIES = JenkinsResultsParserUtil.combine(
			"test.", hostname, ".properties");
		_SQL_PROPERTIES = JenkinsResultsParserUtil.combine(
			"sql/sql.", hostname, ".properties");
	}

}