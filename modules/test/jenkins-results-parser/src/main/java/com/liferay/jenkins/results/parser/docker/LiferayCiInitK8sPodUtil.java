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
public class LiferayCiInitK8sPodUtil {

	public static void init() {
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

	private static void _createGitArtifacts(
		File gitArtifactsClusterDir, File gitArtifactsLocalDir,
		List<String> gitRepositoryNames) {

		if (!gitArtifactsClusterDir.exists()) {
			gitArtifactsClusterDir.mkdir();
		}

		if (!gitArtifactsLocalDir.exists()) {
			gitArtifactsLocalDir.mkdir();
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

			if (!gitRepositoryClusterArtifact.exists()) {
				throw new RuntimeException(
					"Could not find " + gitRepositoryClusterArtifact);
			}

			try {
				JenkinsResultsParserUtil.copy(
					gitRepositoryClusterArtifact, gitRepositoryLocalArtifact);

				TGZUtil.unarchive(
					gitRepositoryLocalArtifact, gitArtifactsLocalDir);

				JenkinsResultsParserUtil.delete(gitRepositoryLocalArtifact);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}

}