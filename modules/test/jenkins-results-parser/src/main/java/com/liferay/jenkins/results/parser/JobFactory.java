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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class JobFactory {

	public static Job newJob(String jobName) {
		return newJob(jobName, "default");
	}

	public static Job newJob(String jobName, String testSuiteName) {
		Job job = _jobs.get(jobName);

		if (job != null) {
			return job;
		}

		if (jobName.contains("test-portal-acceptance-pullrequest(")) {
			PortalAcceptancePullRequestJob portalAcceptancePullRequestJob =
				new PortalAcceptancePullRequestJob(jobName, testSuiteName);

			GitWorkingDirectory gitWorkingDirectory =
				portalAcceptancePullRequestJob.getGitWorkingDirectory();

			String subrepositoryModuleName = _getSubrepositoryModuleName(
				gitWorkingDirectory);

			RepositoryJob repositoryJob = null;

			if (subrepositoryModuleName == null) {
				repositoryJob = portalAcceptancePullRequestJob;
			}
			else {
				repositoryJob = new SubrepositoryAcceptancePullRequestJob(
					jobName);

				Properties buildProperties = null;

				try {
					buildProperties =
						JenkinsResultsParserUtil.getBuildProperties();
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to get build properties", ioe);
				}

				repositoryJob.setRepositoryDir(
					new File(
						JenkinsResultsParserUtil.combine(
							buildProperties.getProperty("base.repository.dir"),
							"/", subrepositoryModuleName)));
			}

			_jobs.put(jobName, repositoryJob);

			return repositoryJob;
		}

		if (jobName.contains("test-portal-acceptance-upstream(")) {
			_jobs.put(jobName, new PortalAcceptanceUpstreamJob(jobName));

			return _jobs.get(jobName);
		}

		if (jobName.contains("test-subrepository-acceptance-pullrequest(")) {
			_jobs.put(
				jobName, new SubrepositoryAcceptancePullRequestJob(jobName));

			return _jobs.get(jobName);
		}

		throw new IllegalArgumentException("Invalid job name " + jobName);
	}

	private static String _getSubrepositoryModuleName(
		GitWorkingDirectory gitWorkingDirectory) {

		List<File> currentBranchModifiedFiles =
			gitWorkingDirectory.getModifiedFilesList();

		if (currentBranchModifiedFiles.size() == 1) {
			File modifiedFile = currentBranchModifiedFiles.get(0);

			String modifiedFileName = modifiedFile.getName();

			if (modifiedFileName.equals("ci-merge")) {
				File moduleDir = modifiedFile.getParentFile();

				List<File> lfrBuildPortalFiles =
					JenkinsResultsParserUtil.findFiles(
						moduleDir, "\\.lfrbuild-portal");

				if (lfrBuildPortalFiles.isEmpty()) {
					File gitrepoFile = new File(moduleDir, ".gitrepo");

					Properties properties =
						JenkinsResultsParserUtil.getProperties(gitrepoFile);

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