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
import java.io.IOException;

import java.util.List;
import java.util.Properties;

/**
 * @author Leslie Wong
 */
public class CentralMergePullRequestJob
	extends PortalAcceptancePullRequestJob
	implements GitSubrepositoryDependentJob {

	public CentralMergePullRequestJob(String url) {
		super(url, "relevant");

		_gitSubrepositoryName = _getGitSubrepositoryName(gitWorkingDirectory);

		jobPropertiesFiles.add(
			new File(getGitSubrepositoryWorkingDirectory(), "test.properties"));
	}

	@Override
	public File getGitSubrepositoryWorkingDirectory() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		String gitSubrepositoryDir = JenkinsResultsParserUtil.combine(
			buildProperties.getProperty("base.repository.dir"), "/",
			_gitSubrepositoryName);

		return new File(gitSubrepositoryDir);
	}

	private static String _getGitSubrepositoryName(
		GitWorkingDirectory gitWorkingDirectory) {

		List<File> currentBranchModifiedFiles =
			gitWorkingDirectory.getModifiedFilesList();

		File modifiedFile = currentBranchModifiedFiles.get(0);

		File moduleDir = modifiedFile.getParentFile();

		File gitrepoFile = new File(moduleDir, ".gitrepo");

		Properties properties = JenkinsResultsParserUtil.getProperties(
			gitrepoFile);

		String gitSubrepositoryRemote = properties.getProperty("remote");

		String gitSubrepositoryName = gitSubrepositoryRemote.replaceAll(
			".*(com-liferay-[^\\.]+)\\.git", "$1");

		if (!gitSubrepositoryName.contains("-private")) {
			gitSubrepositoryName = gitSubrepositoryName + "-private";
		}

		return gitSubrepositoryName;
	}

	private final String _gitSubrepositoryName;

}