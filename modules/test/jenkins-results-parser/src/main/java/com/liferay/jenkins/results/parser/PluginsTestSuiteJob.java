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

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public abstract class PluginsTestSuiteJob extends PluginsGitRepositoryJob {

	public String getPluginName() {
		return _pluginName;
	}

	public File getPluginTestBaseDir() {
		GitWorkingDirectory pluginsGitWorkingDirectory =
			getGitWorkingDirectory();

		return new File(
			pluginsGitWorkingDirectory.getWorkingDirectory(),
			JenkinsResultsParserUtil.combine(
				"portlets/", getPluginName(), "/test/functional"));
	}

	protected PluginsTestSuiteJob(
		String jobName, String pluginName, BuildProfile buildProfile,
		String branchName) {

		super(jobName, buildProfile, branchName);

		_pluginName = pluginName;

		jobPropertiesFiles.add(
			new File(getPluginTestBaseDir(), "test.properties"));
	}

	@Override
	protected Set<String> getRawBatchNames() {
		Set<String> rawBatchNames = new HashSet<>();

		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names", getJobName());

		if ((testBatchNames == null) || testBatchNames.isEmpty()) {
			return rawBatchNames;
		}

		for (String testBatchName : testBatchNames.split(",")) {
			if (!testBatchName.startsWith("plugins-functional")) {
				continue;
			}

			rawBatchNames.add(testBatchName);
		}

		return rawBatchNames;
	}

	private final String _pluginName;

}