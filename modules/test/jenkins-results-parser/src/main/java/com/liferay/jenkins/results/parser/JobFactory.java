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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class JobFactory {

	public static RepositoryJob newRepositoryJob(String jobName) {
		return newRepositoryJob(jobName, "default");
	}

	public static RepositoryJob newRepositoryJob(
		String jobName, String testSuiteName) {

		if (_jobs.containsKey(jobName)) {
			Job job = _jobs.get(jobName);

			if (job instanceof PortalRepositoryJob) {
				return (PortalRepositoryJob)_jobs.get(jobName);
			}

			throw new RuntimeException(
				jobName + " is not a portal repository job");
		}

		RepositoryJob repositoryJob = null;

		if (jobName.contains("test-portal-acceptance-pullrequest(")) {
			repositoryJob = new PortalAcceptancePullRequestJob(
				jobName, testSuiteName);

			GitWorkingDirectory gitWorkingDirectory =
				repositoryJob.getGitWorkingDirectory();

			String subrepositoryModuleName = _getSubrepositoryModuleName(
				gitWorkingDirectory);

			if (subrepositoryModuleName != null) {
				repositoryJob = new SubrepositoryAcceptancePullRequestJob(
					jobName, subrepositoryModuleName);
			}
		}
		else if (jobName.contains("test-portal-acceptance-upstream(")) {
			repositoryJob = new PortalAcceptanceUpstreamJob(jobName);
		}
		else if (jobName.contains(
					"test-subrepository-acceptance-pullrequest(")) {

			repositoryJob = new SubrepositoryAcceptancePullRequestJob(
				jobName, System.getenv("REPOSITORY_NAME"));
		}
		else {
			throw new RuntimeException("Invalid job name " + jobName);
		}

		if (repositoryJob != null) {
			_jobs.put(jobName, repositoryJob);
		}

		return repositoryJob;
	}

	private static String _getSubrepositoryModuleName(
		GitWorkingDirectory gitWorkingDirectory) {

		List<File> currentBranchFiles =
			gitWorkingDirectory.getModifiedFilesList();

		if (currentBranchFiles.size() == 1) {
			File diffFile = currentBranchFiles.get(0);

			String diffFilePath = diffFile.toString();

			if (diffFilePath.endsWith("ci-merge")) {
				File moduleDir = diffFile.getParentFile();

				List<File> lfrBuildPortalFiles =
					JenkinsResultsParserUtil.findFiles(
						moduleDir, "\\.lfrbuild-portal");

				if (lfrBuildPortalFiles.isEmpty()) {
					File gitRepoFile = new File(moduleDir, ".gitrepo");

					Properties properties =
						JenkinsResultsParserUtil.getProperties(gitRepoFile);

					String subrepositoryRemote = properties.getProperty(
						"remote");

					return subrepositoryRemote.replaceAll(
						".*(com-liferay-[^\\.]+)\\.git", "$1");
				}
			}
		}

		return null;
	}

	private static final Map<String, Job> _jobs = new HashMap<>();

}