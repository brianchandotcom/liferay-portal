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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesGitWorkingDirectory extends GitWorkingDirectory {

	public List<File> getModifiedProjectDirs() {
		List<File> projectDirs = new ArrayList<>();

		File workingDirectory = getWorkingDirectory();

		for (File modifiedFile : getModifiedFilesList()) {
			File projectDir = _getProjectDir(modifiedFile);

			if ((projectDir == null) || projectDir.equals(workingDirectory) ||
				projectDirs.contains(projectDir)) {

				continue;
			}

			projectDirs.add(projectDir);
		}

		return projectDirs;
	}

	public List<String> getModifiedProjectNames() {
		List<String> projectNames = new ArrayList<>();

		File workingDirectory = getWorkingDirectory();

		for (File projectDir : getModifiedProjectDirs()) {
			if (projectDir.equals(workingDirectory)) {
				continue;
			}

			projectNames.add(
				JenkinsResultsParserUtil.getPathRelativeTo(
					projectDir, workingDirectory));
		}

		return projectNames;
	}

	protected QAWebsitesGitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath)
		throws IOException {

		super(upstreamBranchName, workingDirectoryPath);
	}

	protected QAWebsitesGitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath,
			String gitRepositoryName)
		throws IOException {

		super(upstreamBranchName, workingDirectoryPath, gitRepositoryName);
	}

	private File _getProjectDir(File file) {
		if (file == null) {
			return null;
		}

		File canonicalFile = JenkinsResultsParserUtil.getCanonicalFile(file);

		if (canonicalFile.equals(getWorkingDirectory())) {
			return null;
		}

		File parentFile = canonicalFile.getParentFile();

		if ((parentFile == null) || !parentFile.exists()) {
			return file;
		}

		if (!canonicalFile.isDirectory()) {
			return _getProjectDir(parentFile);
		}

		File testPropertiesFile = new File(canonicalFile, "build.gradle");

		if (!testPropertiesFile.exists()) {
			return _getProjectDir(parentFile);
		}

		return canonicalFile;
	}

}