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

import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class PluginsTestSuiteJob
	extends PluginsGitRepositoryJob implements TestSuiteJob {

	public String getPluginName() {
		return _pluginName;
	}

	@Override
	public List<File> getPluginsTestBaseDirs() {
		return Arrays.asList(_getPluginTestBaseDir());
	}

	@Override
	public String getTestSuiteName() {
		return getPluginName();
	}

	protected PluginsTestSuiteJob(
		String jobName, String pluginName, BuildProfile buildProfile,
		String branchName) {

		super(jobName, buildProfile, branchName);

		_pluginName = pluginName;

		jobPropertiesFiles.add(
			new File(_getPluginTestBaseDir(), "test.properties"));
	}

	private File _getPluginTestBaseDir() {
		GitWorkingDirectory pluginsGitWorkingDirectory =
			getGitWorkingDirectory();

		return new File(
			pluginsGitWorkingDirectory.getWorkingDirectory(),
			JenkinsResultsParserUtil.combine(
				"portlets/", getPluginName(), "/test/functional"));
	}

	private final String _pluginName;

}