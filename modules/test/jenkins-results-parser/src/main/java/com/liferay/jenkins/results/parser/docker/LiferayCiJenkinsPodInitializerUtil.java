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

package com.liferay.jenkins.results.parser.docker;

import com.liferay.jenkins.results.parser.GitUtil;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TGZUtil;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class LiferayCiJenkinsPodInitializerUtil {

	public static void init() {
		String gitRepositoryNamesString =
			JenkinsResultsParserUtil.getEnvironmentVariable(
				"GIT_REPOSITORY_NAMES");

		_createGitRepositoryDirs(
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"GIT_REPOSITORY_ARCHIVES_NFS_DIR")),
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"GIT_REPOSITORY_BASE_DIR")),
			Arrays.asList(gitRepositoryNamesString.split(",")));
	}

	private static void _createGitRepositoryDirs(
		File gitRepositoryArchivesNFSDir, File gitRepositoryBaseDir,
		List<String> gitRepositoryNames) {

		if (!gitRepositoryArchivesNFSDir.exists()) {
			gitRepositoryArchivesNFSDir.mkdir();
		}

		if (!gitRepositoryBaseDir.exists()) {
			gitRepositoryBaseDir.mkdir();
		}

		for (String gitRepositoryName : gitRepositoryNames) {
			System.out.println();
			System.out.println("##");
			System.out.println("## " + gitRepositoryName);
			System.out.println("##");
			System.out.println();

			File gitRepositoryArchiveNFS = new File(
				gitRepositoryArchivesNFSDir, gitRepositoryName + ".tar.gz");

			if (!gitRepositoryArchiveNFS.exists()) {
				throw new RuntimeException(
					"Could not find " + gitRepositoryArchiveNFS);
			}

			File gitRepositoryDir = new File(
				gitRepositoryBaseDir, gitRepositoryName);

			if (gitRepositoryDir.exists()) {
				if (gitRepositoryDir.isDirectory()) {
					try {
						GitWorkingDirectory gitWorkingDirectory =
							GitWorkingDirectoryFactory.newGitWorkingDirectory(
								GitUtil.getDefaultBranchName(gitRepositoryDir),
								gitRepositoryDir, gitRepositoryName);

						gitWorkingDirectory.reset("--hard");

						gitWorkingDirectory.status();

						return;
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}

				JenkinsResultsParserUtil.delete(gitRepositoryDir);
			}

			File gitRepositoryArchive = new File(
				gitRepositoryBaseDir, gitRepositoryName + ".tar.gz");

			try {
				JenkinsResultsParserUtil.copy(
					gitRepositoryArchiveNFS, gitRepositoryArchive);

				TGZUtil.unarchive(gitRepositoryArchive, gitRepositoryBaseDir);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			finally {
				JenkinsResultsParserUtil.delete(gitRepositoryArchive);
			}
		}
	}

}