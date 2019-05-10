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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class LiferayCiInitK8sNodeUtil {

	public static void init() {
		String secretsVolumeDir = _getEnvironmentVariable("SECRETS_VOLUME_DIR");

		if ((secretsVolumeDir != null) && !secretsVolumeDir.isEmpty()) {
			JenkinsResultsParserUtil.regenerateSshIdRsa(
				new File(secretsVolumeDir));
		}

		String knownHosts = _getEnvironmentVariable("KNOWN_HOSTS");

		if (knownHosts != null) {
			JenkinsResultsParserUtil.regenerateSshKnownHosts(knownHosts);
		}

		String gitRepositoryNamesString = _getEnvironmentVariable(
			"GIT_REPOSITORY_NAMES");

		List<String> gitRepositoryNames = null;

		if (gitRepositoryNamesString != null) {
			gitRepositoryNames = new ArrayList<>();

			Collections.addAll(
				gitRepositoryNames, gitRepositoryNamesString.split(","));
		}

		String gitArtifactsDirPath = _getEnvironmentVariable(
			"GIT_ARTIFACTS_DIR");

		if (gitArtifactsDirPath != null) {
			_createGitArtifacts(
				new File(gitArtifactsDirPath), gitRepositoryNames);
		}
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
		File gitArtifactsDir, List<String> gitRepositoryNames) {

		String mirrorsHostName = "mirrors.lax.liferay.com";

		Boolean mirrorsAvailable =
			JenkinsResultsParserUtil.isServerPortReachable(mirrorsHostName, 80);

		if (!gitArtifactsDir.exists()) {
			gitArtifactsDir.mkdir();
		}

		for (String gitRepositoryName : gitRepositoryNames) {
			System.out.println();
			System.out.println("##");
			System.out.println("## " + gitRepositoryName);
			System.out.println("##");
			System.out.println();

			File gitRepositoryArtifact = new File(
				gitArtifactsDir, gitRepositoryName + ".tar.gz");
			File gitRepositoryDir = new File(
				gitArtifactsDir, gitRepositoryName);

			if (!gitRepositoryDir.exists()) {
				if (gitRepositoryArtifact.exists()) {
					TGZUtil.unarchive(gitRepositoryArtifact, gitArtifactsDir);
				}
				else if (mirrorsAvailable) {
					try {
						URL gitArtifactURL = new URL(
							JenkinsResultsParserUtil.combine(
								"http://", mirrorsHostName,
								"/github.com/liferay/", gitRepositoryName,
								".tar.gz"));

						JenkinsResultsParserUtil.toFile(
							gitArtifactURL, gitRepositoryArtifact);

						TGZUtil.unarchive(
							gitRepositoryArtifact, gitArtifactsDir);
					}
					catch (MalformedURLException murle) {
						throw new RuntimeException(murle);
					}
				}
				else {
					String gitRemoteURL = JenkinsResultsParserUtil.combine(
						"git@github.com:liferay/", gitRepositoryName, ".git");

					GitUtil.clone(gitRemoteURL, gitRepositoryDir);
				}
			}

			GitWorkingDirectory gitWorkingDirectory =
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					GitUtil.getDefaultBranchName(gitRepositoryDir),
					gitRepositoryDir, gitRepositoryName);

			_configureLocalCoreSettings(gitWorkingDirectory);

			_configureLocalRemotes(gitWorkingDirectory);

			gitWorkingDirectory.cleanTempBranches();

			gitWorkingDirectory.fetch(
				gitWorkingDirectory.getUpstreamGitRemote(), false);

			gitWorkingDirectory.clean();

			gitWorkingDirectory.gc();

			File gitRepositoryArtifactNew = new File(
				gitArtifactsDir, gitRepositoryName + ".new.tar.gz");

			TGZUtil.archive(gitRepositoryDir, gitRepositoryArtifactNew);

			try {
				JenkinsResultsParserUtil.move(
					gitRepositoryArtifactNew, gitRepositoryArtifact);
			}
			catch (IOException ioe) {
			}
		}
	}

	private static String _getEnvironmentVariable(
		String environmentVariableName) {

		String environmentVariableValue = System.getenv(
			environmentVariableName);

		if ((environmentVariableValue == null) ||
			environmentVariableValue.isEmpty()) {

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Please set \'", environmentVariableName, "\'"));

			return null;
		}

		return environmentVariableValue;
	}

}