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

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class LiferayCiInitK8sClusterUtil {

	public static void init() {
		JenkinsResultsParserUtil.regenerateSshIdRsa(
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"SECRETS_VOLUME_DIR")));

		JenkinsResultsParserUtil.regenerateSshKnownHosts(
			JenkinsResultsParserUtil.getEnvironmentVariable("KNOWN_HOSTS"));

		String gitRepositoryNamesString =
			JenkinsResultsParserUtil.getEnvironmentVariable(
				"GIT_REPOSITORY_NAMES");

		_createGitArtifacts(
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"GIT_ARTIFACTS_CLUSTER_DIR")),
			new File(
				JenkinsResultsParserUtil.getEnvironmentVariable(
					"GIT_ARTIFACTS_LOCAL_DIR")),
			Arrays.asList(gitRepositoryNamesString.split(",")));
	}

	private static void _configureLocalCoreSettings(
		GitWorkingDirectory gitWorkingDirectory) {

		Map<String, String> configMap = new HashMap<>();

		configMap.put("core.autocrlf", "false");
		configMap.put("core.bare", "false");
		configMap.put("core.hideDotFiles", "dotGitOnly");
		configMap.put("core.logallrefupdates", "true");
		configMap.put("core.repositoryformatversion", "0");

		gitWorkingDirectory.configure(configMap, "--local");
	}

	private static void _configureLocalRemotes(
		GitWorkingDirectory gitWorkingDirectory) {

		String gitRemoteNames = "origin,upstream";
		String gitRemoteURL = JenkinsResultsParserUtil.combine(
			"git@github.com:liferay/",
			gitWorkingDirectory.getGitRepositoryName(), ".git");

		for (String remoteName : gitRemoteNames.split(",")) {
			gitWorkingDirectory.addGitRemote(
				true, remoteName, gitRemoteURL, true);
		}
	}

	private static void _createGitArtifacts(
		File gitArtifactsClusterDir, File gitArtifactsLocalDir,
		List<String> gitRepositoryNames) {

		String mirrorsHostName = "mirrors.lax.liferay.com";

		Boolean mirrorsAvailable =
			JenkinsResultsParserUtil.isServerPortReachable(mirrorsHostName, 80);

		if (!gitArtifactsClusterDir.exists()) {
			gitArtifactsClusterDir.mkdirs();
		}

		if (!gitArtifactsLocalDir.exists()) {
			gitArtifactsLocalDir.mkdirs();
		}

		for (String gitRepositoryName : gitRepositoryNames) {
			System.out.println();
			System.out.println("##");
			System.out.println("## " + gitRepositoryName);
			System.out.println("##");
			System.out.println();

			File gitRepositoryClusterArtifact = new File(
				gitArtifactsClusterDir, gitRepositoryName + ".tar.gz");
			File gitRepositoryLocalArtifact = new File(
				gitArtifactsLocalDir, gitRepositoryName + ".tar.gz");

			File gitRepositoryLocalDir = new File(
				gitArtifactsLocalDir, gitRepositoryName);

			if (gitRepositoryLocalDir.exists()) {
				gitRepositoryLocalDir.delete();
			}

			if (gitRepositoryLocalArtifact.exists()) {
				gitRepositoryLocalArtifact.delete();
			}

			if (gitRepositoryClusterArtifact.exists()) {
				try {
					JenkinsResultsParserUtil.copy(
						gitRepositoryClusterArtifact,
						gitRepositoryLocalArtifact);

					TGZUtil.unarchive(
						gitRepositoryLocalArtifact, gitArtifactsLocalDir);
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
			}
			else if (mirrorsAvailable) {
				try {
					URL gitArtifactURL = new URL(
						JenkinsResultsParserUtil.combine(
							"http://", mirrorsHostName, "/github.com/liferay/",
							gitRepositoryName, ".tar.gz"));

					JenkinsResultsParserUtil.toFile(
						gitArtifactURL, gitRepositoryLocalArtifact);

					TGZUtil.unarchive(
						gitRepositoryLocalArtifact, gitArtifactsLocalDir);
				}
				catch (MalformedURLException murle) {
					throw new RuntimeException(murle);
				}
			}
			else {
				String gitRemoteURL = JenkinsResultsParserUtil.combine(
					"git@github.com:liferay/", gitRepositoryName, ".git");

				GitUtil.clone(gitRemoteURL, gitRepositoryLocalDir);
			}

			GitWorkingDirectory gitWorkingDirectory =
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					GitUtil.getDefaultBranchName(gitRepositoryLocalDir),
					gitRepositoryLocalDir, gitRepositoryName);

			_configureLocalCoreSettings(gitWorkingDirectory);

			_configureLocalRemotes(gitWorkingDirectory);

			gitWorkingDirectory.cleanTempBranches();

			gitWorkingDirectory.fetch(
				gitWorkingDirectory.getUpstreamGitRemote(), false);

			gitWorkingDirectory.clean();

			gitWorkingDirectory.gc();

			File gitRepositoryLocalArtifactNew = new File(
				gitArtifactsLocalDir, gitRepositoryName + ".new.tar.gz");

			TGZUtil.archive(
				gitRepositoryLocalDir, gitRepositoryLocalArtifactNew);

			try {
				JenkinsResultsParserUtil.move(
					gitRepositoryLocalArtifactNew,
					gitRepositoryClusterArtifact);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}

}