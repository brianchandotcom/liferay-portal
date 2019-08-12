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

import com.liferay.jenkins.results.parser.GitRepositoryArchivesDirResourceMonitor;
import com.liferay.jenkins.results.parser.GitUtil;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.K8sNodeReadWriteResourceMonitor;
import com.liferay.jenkins.results.parser.TGZUtil;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class LiferayCIGitRepositoriesInitializerUtil {

	public static void init() {
		String gitRepositoryNamesString =
			JenkinsResultsParserUtil.getEnvironmentVariable(
				"GIT_REPOSITORY_NAMES");

		_createGitRepositoryDirs(
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"GIT_REPOSITORY_ARCHIVES_DIR")),
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"GIT_REPOSITORIES_BASE_DIR")),
			Arrays.asList(gitRepositoryNamesString.split(",")),
			new GitRepositoryArchivesDirResourceMonitor(
				JenkinsResultsParserUtil.getEnvironmentVariable("ETCD_URL")),
			new K8sNodeReadWriteResourceMonitor(
				JenkinsResultsParserUtil.getEnvironmentVariable("ETCD_URL"),
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"K8S_NODE_NAME")));
	}

	private static void _createGitRepositoryDirs(
		File gitRepositoryArchivesDir, File gitRepositoriesBaseDir,
		List<String> gitRepositoryNames,
		GitRepositoryArchivesDirResourceMonitor
			gitRepositoryArchivesDirResourceMonitor,
		K8sNodeReadWriteResourceMonitor k8sNodeReadWriteResourceMonitor) {

		if (!gitRepositoryArchivesDir.exists()) {
			gitRepositoryArchivesDir.mkdir();
		}

		if (!gitRepositoriesBaseDir.exists()) {
			gitRepositoriesBaseDir.mkdir();
		}

		for (String gitRepositoryName : gitRepositoryNames) {
			System.out.println();
			System.out.println("##");
			System.out.println("## " + gitRepositoryName);
			System.out.println("##");
			System.out.println();

			File gitRepositoryArchiveFile = new File(
				gitRepositoryArchivesDir, gitRepositoryName + ".tar.gz");

			if (!gitRepositoryArchiveFile.exists()) {
				throw new RuntimeException(
					"Could not find " + gitRepositoryArchiveFile);
			}

			File gitRepositoryDir = new File(
				gitRepositoriesBaseDir, gitRepositoryName);

			if (gitRepositoryDir.exists()) {
				if (gitRepositoryDir.isDirectory()) {
					try {
						GitWorkingDirectory gitWorkingDirectory =
							GitWorkingDirectoryFactory.newGitWorkingDirectory(
								GitUtil.getDefaultBranchName(gitRepositoryDir),
								gitRepositoryDir, gitRepositoryName);

						gitWorkingDirectory.reset("--hard");

						gitWorkingDirectory.status();

						continue;
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}

				JenkinsResultsParserUtil.delete(gitRepositoryDir);
			}

			File localGitRepositoryArchiveFile = new File(
				gitRepositoriesBaseDir, gitRepositoryName + ".tar.gz");

			String gitRepositoryConnectionKey =
				gitRepositoryArchivesDirResourceMonitor.getNewConnectionName();
			String k8sNodeConnectionName =
				k8sNodeReadWriteResourceMonitor.getNewConnectionName();

			try {
				k8sNodeReadWriteResourceMonitor.waitWrite(
					k8sNodeConnectionName);

				gitRepositoryArchivesDirResourceMonitor.wait(
					gitRepositoryConnectionKey);

				JenkinsResultsParserUtil.copy(
					gitRepositoryArchiveFile, localGitRepositoryArchiveFile);
			}
			catch (IOException ioe) {
				JenkinsResultsParserUtil.delete(localGitRepositoryArchiveFile);

				throw new RuntimeException(
					"Unable to copy repository archive file", ioe);
			}
			finally {
				gitRepositoryArchivesDirResourceMonitor.signal(
					gitRepositoryConnectionKey);

				k8sNodeReadWriteResourceMonitor.signalWrite(
					k8sNodeConnectionName);
			}

			try {
				k8sNodeReadWriteResourceMonitor.waitWrite(
					k8sNodeConnectionName);

				TGZUtil.unarchive(
					localGitRepositoryArchiveFile, gitRepositoriesBaseDir);
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to unarchive the repository archive file", ioe);
			}
			finally {
				k8sNodeReadWriteResourceMonitor.signalWrite(
					k8sNodeConnectionName);

				JenkinsResultsParserUtil.delete(localGitRepositoryArchiveFile);
			}
		}
	}

}