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

/**
 * @author Michael Hashimoto
 */
public class JobFactory {

	public static Job newJob(BuildData buildData) {
		String portalUpstreamBranchName = null;

		if (buildData instanceof PortalBuildData) {
			PortalBuildData portalBuildData = (PortalBuildData)buildData;

			portalUpstreamBranchName =
				portalBuildData.getPortalUpstreamBranchName();
		}

		return newJob(buildData.getJobName(), null, portalUpstreamBranchName);
	}

	public static Job newJob(String jobName) {
		return newJob(jobName, null, null, null);
	}

	public static Job newJob(String jobName, String testSuiteName) {
		return newJob(jobName, testSuiteName, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String portalBranchName) {

		return newJob(jobName, testSuiteName, portalBranchName, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String portalBranchName,
		String repositoryName) {

		return _newJob(
			jobName, testSuiteName, portalBranchName, repositoryName);
	}

	private static boolean _isCentralMergePullRequest(
		GitWorkingDirectory gitWorkingDirectory) {

		List<File> currentBranchModifiedFiles =
			gitWorkingDirectory.getModifiedFilesList();

		if (currentBranchModifiedFiles.size() == 1) {
			File modifiedFile = currentBranchModifiedFiles.get(0);

			String modifiedFileName = modifiedFile.getName();

			if (modifiedFileName.equals("ci-merge")) {
				return true;
			}
		}

		return false;
	}

	private static Job _newJob(
		String jobName, String testSuiteName, String portalBranchName,
		String repositoryName) {

		Job job = _jobs.get(jobName);

		if (job != null) {
			return job;
		}

		if (jobName.equals("js-test-csv-report") ||
			jobName.equals("junit-test-csv-report")) {

			PortalGitRepositoryJob portalGitRepositoryJob =
				new PortalGitRepositoryJob(jobName) {

					@Override
					protected GitWorkingDirectory getNewGitWorkingDirectory() {
						return GitWorkingDirectoryFactory.
							newGitWorkingDirectory(
								getBranchName(),
								System.getProperty("user.dir"));
					}

				};

			_jobs.put(jobName, portalGitRepositoryJob);

			return _jobs.get(jobName);
		}

		if (jobName.equals("root-cause-analysis-tool")) {
			_jobs.put(
				jobName,
				new RootCauseAnalysisToolJob(jobName, portalBranchName));

			return _jobs.get(jobName);
		}

		if (jobName.equals("root-cause-analysis-tool-batch")) {
			_jobs.put(
				jobName,
				new RootCauseAnalysisToolBatchJob(jobName, portalBranchName));

			return _jobs.get(jobName);
		}

		if (jobName.startsWith("test-plugins-acceptance-pullrequest(")) {
			PluginsGitRepositoryJob pluginsGitRepositoryJob =
				new PluginsGitRepositoryJob(jobName);

			_jobs.put(jobName, pluginsGitRepositoryJob);

			return pluginsGitRepositoryJob;
		}

		if (jobName.startsWith("test-portal-acceptance-pullrequest(")) {
			PortalAcceptancePullRequestJob portalAcceptancePullRequestJob =
				new PortalAcceptancePullRequestJob(jobName, testSuiteName);

			if (_isCentralMergePullRequest(
					portalAcceptancePullRequestJob.getGitWorkingDirectory())) {

				portalAcceptancePullRequestJob = new CentralMergePullRequestJob(
					jobName);
			}

			_jobs.put(jobName, portalAcceptancePullRequestJob);

			return portalAcceptancePullRequestJob;
		}

		if (jobName.startsWith("test-portal-acceptance-upstream(")) {
			_jobs.put(
				jobName,
				new PortalAcceptanceUpstreamJob(jobName, testSuiteName));

			return _jobs.get(jobName);
		}

		if (jobName.equals("test-portal-fixpack-release")) {
			_jobs.put(
				jobName,
				new PortalFixpackReleaseJob(jobName, portalBranchName));

			return _jobs.get(jobName);
		}

		if (jobName.equals("test-portal-hotfix-release")) {
			_jobs.put(
				jobName, new PortalHotfixReleaseJob(jobName, portalBranchName));

			return _jobs.get(jobName);
		}

		if (jobName.equals("test-portal-release")) {
			_jobs.put(jobName, new PortalReleaseJob(jobName, portalBranchName));

			return _jobs.get(jobName);
		}

		if (jobName.startsWith("test-portal-testsuite-upstream(")) {
			_jobs.put(
				jobName,
				new PortalTestSuiteUpstreamJob(jobName, testSuiteName));

			return _jobs.get(jobName);
		}

		if (jobName.startsWith("test-portal-testsuite-upstream-controller(")) {
			_jobs.put(jobName, new SimpleJob(jobName));

			return _jobs.get(jobName);
		}

		if (jobName.startsWith("test-portal-upstream(")) {
			_jobs.put(jobName, new PortalUpstreamJob(jobName));

			return _jobs.get(jobName);
		}

		if (jobName.startsWith("test-subrepository-acceptance-pullrequest(")) {
			_jobs.put(
				jobName,
				new SubrepositoryAcceptancePullRequestJob(
					jobName, testSuiteName, repositoryName));

			return _jobs.get(jobName);
		}

		_jobs.put(jobName, new DefaultPortalJob(jobName));

		return _jobs.get(jobName);
	}

	private static final Map<String, Job> _jobs = new HashMap<>();

}