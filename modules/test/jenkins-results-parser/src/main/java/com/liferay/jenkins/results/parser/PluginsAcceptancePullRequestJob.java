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

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class PluginsAcceptancePullRequestJob extends PluginsGitRepositoryJob {

	@Override
	public File getPluginTestBaseDir() {
		return _testBaseDir;
	}

	protected PluginsAcceptancePullRequestJob(
		String jobName, BuildProfile buildProfile, String branchName) {

		super(jobName, buildProfile, branchName);

		_testBaseDir = _getPluginTestBaseDir();

		if (_testBaseDir != null) {
			File testBaseDirPropertiesFile = new File(
				_testBaseDir, "test.properties");

			if (testBaseDirPropertiesFile.exists()) {
				jobPropertiesFiles.add(testBaseDirPropertiesFile);
			}
		}

		readJobProperties();
	}

	private File _getPluginTestBaseDir() {
		PluginsGitWorkingDirectory pluginsGitWorkingDirectory =
			portalGitWorkingDirectory.getPluginsGitWorkingDirectory();

		List<File> modifiedFilesList =
			pluginsGitWorkingDirectory.getModifiedFilesList();

		for (File modifiedFile : modifiedFilesList) {
			File parentDir = new File(modifiedFile.getPath());

			while (parentDir != null) {
				File testBaseDir = new File(parentDir, "test/functional");

				if (testBaseDir.exists()) {
					return testBaseDir;
				}

				parentDir = parentDir.getParentFile();
			}
		}

		return null;
	}

	private final File _testBaseDir;

}