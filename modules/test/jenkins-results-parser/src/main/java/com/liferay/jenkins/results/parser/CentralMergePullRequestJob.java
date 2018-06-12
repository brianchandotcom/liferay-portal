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
	implements SubrepositoryDependentJob {

	public CentralMergePullRequestJob(String url) {
		super(url, "relevant");

		_subrepositoryName = _getSubrepositoryName(gitWorkingDirectory);

		jobProperties.putAll(
			JenkinsResultsParserUtil.getProperties(
				new File(
					getSubrepositoryWorkingDirectory(), "test.properties")));
	}

	@Override
	public File getSubrepositoryWorkingDirectory() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		String subrepositoryDir = JenkinsResultsParserUtil.combine(
			buildProperties.getProperty("base.repository.dir"), "/",
			_subrepositoryName);

		return new File(subrepositoryDir);
	}

	private static String _getSubrepositoryName(
		GitWorkingDirectory gitWorkingDirectory) {

		List<File> currentBranchModifiedFiles =
			gitWorkingDirectory.getModifiedFilesList();

		File modifiedFile = currentBranchModifiedFiles.get(0);

		File moduleDir = modifiedFile.getParentFile();

		File gitrepoFile = new File(moduleDir, ".gitrepo");

		Properties properties = JenkinsResultsParserUtil.getProperties(
			gitrepoFile);

		String subrepositoryRemote = properties.getProperty("remote");

		String subrepositoryName = subrepositoryRemote.replaceAll(
			".*(com-liferay-[^\\.]+)\\.git", "$1");

		if (!subrepositoryName.contains("-private")) {
			subrepositoryName = subrepositoryName + "-private";
		}

		return subrepositoryName;
	}

	private final String _subrepositoryName;

}