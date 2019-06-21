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

import com.liferay.jenkins.results.parser.GitHubDevSyncUtil;
import com.liferay.jenkins.results.parser.GitRepositoryArchivesDirResourceMonitor;
import com.liferay.jenkins.results.parser.GitUtil;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.ReadWriteResourceMonitor;
import com.liferay.jenkins.results.parser.TGZUtil;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class LiferayCIGitRepositoriesArchiverUtil {

	public static void init() {
		String gitRepositoryNamesString =
			JenkinsResultsParserUtil.getEnvironmentVariable(
				"GIT_REPOSITORY_NAMES");

		_createGitRepositoryArchives(
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"GIT_REPOSITORY_ARCHIVES_DIR")),
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"GIT_REPOSITORIES_BASE_DIR")),
			Arrays.asList(gitRepositoryNamesString.split(",")),
			new GitRepositoryArchivesDirResourceMonitor(
				JenkinsResultsParserUtil.getEnvironmentVariable("ETCD_URL")));
	}

	private static boolean _cloneGitRepositoryFromGitHub(
		File gitRepositoryDir) {

		try {
			GitUtil.clone(
				JenkinsResultsParserUtil.combine(
					"git@github.com:liferay/", gitRepositoryDir.getName()),
				gitRepositoryDir);

			return true;
		}
		catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	private static boolean _cloneGitRepositoryFromGitHubDev(
		File gitRepositoryDir) {

		try {
			GitHubDevSyncUtil.clone(
				gitRepositoryDir.getName(), gitRepositoryDir);

			return true;
		}
		catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	private static void _configureGitRepositoryCoreSettings(
		GitWorkingDirectory gitWorkingDirectory) {

		Map<String, String> configMap = new HashMap<>();

		configMap.put("core.autocrlf", "false");
		configMap.put("core.bare", "false");
		configMap.put("core.hideDotFiles", "dotGitOnly");
		configMap.put("core.logallrefupdates", "true");
		configMap.put("core.repositoryformatversion", "0");

		gitWorkingDirectory.configure(configMap, "--local");
	}

	private static void _configureGitRepositoryRemotes(
		GitWorkingDirectory gitWorkingDirectory) {

		String gitRemoteNames = "origin,upstream";
		String gitRemoteURL = JenkinsResultsParserUtil.combine(
			"git@github.com:liferay/",
			gitWorkingDirectory.getGitRepositoryName(), ".git");

		for (String remoteName : gitRemoteNames.split(",")) {
			gitWorkingDirectory.addGitRemote(
				true, remoteName, gitRemoteURL, true);
		}

		gitWorkingDirectory.addGitRemote(true, "upstream-temp", gitRemoteURL);
	}

	private static boolean _copyGitRepositoryFromMirrors(
		File gitRepositoryBaseDir, String gitRepositoryName) {

		String mirrorsHostName = "mirrors.lax.liferay.com";

		File gitRepositoryArchive = new File(
			gitRepositoryBaseDir, gitRepositoryName + ".tar.gz");

		JenkinsResultsParserUtil.delete(gitRepositoryArchive);

		if (!JenkinsResultsParserUtil.isServerPortReachable(
				mirrorsHostName, 80)) {

			return false;
		}

		try {
			URL gitRepositoryArchiveURL = new URL(
				JenkinsResultsParserUtil.combine(
					"http://", mirrorsHostName, "/github.com/liferay/",
					gitRepositoryName, ".tar.gz"));

			JenkinsResultsParserUtil.toFile(
				gitRepositoryArchiveURL, gitRepositoryArchive);

			TGZUtil.unarchive(gitRepositoryArchive, gitRepositoryBaseDir);

			return true;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();

			return false;
		}
		finally {
			JenkinsResultsParserUtil.delete(gitRepositoryArchive);
		}
	}

	private static boolean _copyGitRepositoryFromNFSDir(
		File gitRepositoryArchivesNFSDir, File gitRepositoryBaseDir,
		String gitRepositoryName) {

		File gitRepositoryArchiveNFS = new File(
			gitRepositoryArchivesNFSDir, gitRepositoryName + ".tar.gz");

		if (!gitRepositoryArchiveNFS.exists()) {
			return false;
		}

		File gitRepositoryArchive = new File(
			gitRepositoryBaseDir, gitRepositoryName + ".tar.gz");

		JenkinsResultsParserUtil.delete(gitRepositoryArchive);

		try {
			JenkinsResultsParserUtil.copy(
				gitRepositoryArchiveNFS, gitRepositoryArchive);

			TGZUtil.unarchive(gitRepositoryArchive, gitRepositoryBaseDir);

			return true;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();

			return false;
		}
		finally {
			JenkinsResultsParserUtil.delete(gitRepositoryArchive);
		}
	}

	private static void _createGitRepositoryArchives(
		File gitRepositoryArchivesNFSDir, File gitRepositoryBaseDir,
		List<String> gitRepositoryNames,
		ReadWriteResourceMonitor readWriteResourceMonitor) {

		if (!gitRepositoryArchivesNFSDir.exists()) {
			gitRepositoryArchivesNFSDir.mkdirs();
		}

		if (!gitRepositoryBaseDir.exists()) {
			gitRepositoryBaseDir.mkdirs();
		}

		for (String gitRepositoryName : gitRepositoryNames) {
			System.out.println();
			System.out.println("##");
			System.out.println("## " + gitRepositoryName);
			System.out.println("##");
			System.out.println();

			File gitRepositoryDir = new File(
				gitRepositoryBaseDir, gitRepositoryName);

			JenkinsResultsParserUtil.delete(gitRepositoryDir);

			if (!(_copyGitRepositoryFromNFSDir(
					gitRepositoryArchivesNFSDir, gitRepositoryBaseDir,
					gitRepositoryName) ||
				  _copyGitRepositoryFromMirrors(
					  gitRepositoryBaseDir, gitRepositoryName) ||
				  _cloneGitRepositoryFromGitHubDev(gitRepositoryDir) ||
				  _cloneGitRepositoryFromGitHub(gitRepositoryDir))) {

				throw new RuntimeException(
					"Unable to copy or clone the git repository");
			}

			GitWorkingDirectory gitWorkingDirectory =
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					GitUtil.getDefaultBranchName(gitRepositoryDir),
					gitRepositoryDir, gitRepositoryName);

			_configureGitRepositoryCoreSettings(gitWorkingDirectory);

			_configureGitRepositoryRemotes(gitWorkingDirectory);

			gitWorkingDirectory.cleanTempBranches();

			gitWorkingDirectory.fetch(
				gitWorkingDirectory.getUpstreamGitRemote(), false);

			gitWorkingDirectory.clean();

			gitWorkingDirectory.gc();

			File gitRepositoryArchive = new File(
				gitRepositoryBaseDir, gitRepositoryName + ".tar.gz");
			File gitRepositoryArchiveNFS = new File(
				gitRepositoryArchivesNFSDir, gitRepositoryName + ".tar.gz");

			String connectionKey =
				readWriteResourceMonitor.getNewConnectionName();

			try {
				TGZUtil.archive(gitRepositoryDir, gitRepositoryArchive);

				readWriteResourceMonitor.waitWrite(connectionKey);

				JenkinsResultsParserUtil.move(
					gitRepositoryArchive, gitRepositoryArchiveNFS);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			finally {
				readWriteResourceMonitor.signalWrite(connectionKey);

				JenkinsResultsParserUtil.delete(gitRepositoryArchive);
			}
		}
	}

}