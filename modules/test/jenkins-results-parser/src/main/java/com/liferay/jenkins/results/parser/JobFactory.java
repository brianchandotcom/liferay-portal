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

	public static Job newJob(Build build) {
		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		return _newJob(
			topLevelBuild.getJobName(), topLevelBuild.getTestSuiteName(),
			topLevelBuild.getBranchName(),
			topLevelBuild.getBaseGitRepositoryName(),
			topLevelBuild.getBuildProfile(),
			_getPortalUpstreamBranchName(topLevelBuild),
			topLevelBuild.getProjectNames(), null);
	}

	public static Job newJob(BuildData buildData) {
		String upstreamBranchName = null;

		if (buildData instanceof PortalBuildData) {
			PortalBuildData portalBuildData = (PortalBuildData)buildData;

			upstreamBranchName = portalBuildData.getPortalUpstreamBranchName();
		}

		return _newJob(
			buildData.getJobName(), null, upstreamBranchName, null, null, null,
			null, null);
	}

	public static Job newJob(String jobName) {
		return _newJob(jobName, null, null, null, null, null, null, null);
	}

	public static Job newJob(String jobName, String testSuiteName) {
		return _newJob(
			jobName, testSuiteName, null, null, null, null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName) {

		return _newJob(
			jobName, testSuiteName, branchName, null, null, null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, null, null,
			null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName, Job.BuildProfile buildProfile) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, buildProfile,
			null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName, Job.BuildProfile buildProfile,
		PortalGitWorkingDirectory portalGitWorkingDirectory) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, buildProfile,
			null, null, portalGitWorkingDirectory);
	}

	private static String _getPortalUpstreamBranchName(
		TopLevelBuild topLevelBuild) {

		String portalUpstreamBranchName = topLevelBuild.getParameterValue(
			"PORTAL_UPSTREAM_BRANCH_NAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			portalUpstreamBranchName = topLevelBuild.getBranchName();
		}

		return portalUpstreamBranchName;
	}

	private static Job _newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName, Job.BuildProfile buildProfile,
		String portalUpstreamBranchName, List<String> projectNames,
		PortalGitWorkingDirectory portalGitWorkingDirectory) {

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			portalUpstreamBranchName = branchName;
		}

		if (buildProfile == null) {
			buildProfile = Job.BuildProfile.PORTAL;
		}

		String jobKey = JenkinsResultsParserUtil.combine(
			jobName, "-", buildProfile.toString());

		if ((testSuiteName != null) && !testSuiteName.isEmpty()) {
			jobKey = JenkinsResultsParserUtil.combine(
				jobName, "-", testSuiteName);
		}

		Job job = _jobs.get(jobKey);

		if (job != null) {
			return job;
		}

		if (jobName.equals("js-test-csv-report") ||
			jobName.equals("junit-test-csv-report")) {

			if (portalGitWorkingDirectory == null) {
				File gitWorkingDir = JenkinsResultsParserUtil.getGitWorkingDir(
					new File(System.getProperty("user.dir")));

				Properties buildProperties =
					JenkinsResultsParserUtil.getProperties(
						new File(gitWorkingDir, "build.properties"));

				String upstreamBranchName =
					JenkinsResultsParserUtil.getProperty(
						buildProperties, "git.working.branch.name");

				String gitRepositoryName = "liferay-portal";

				if (!upstreamBranchName.equals("master")) {
					gitRepositoryName += "-ee";
				}

				GitWorkingDirectory gitWorkingDirectory =
					GitWorkingDirectoryFactory.newGitWorkingDirectory(
						upstreamBranchName, gitWorkingDir, gitRepositoryName);

				if (gitWorkingDirectory instanceof PortalGitWorkingDirectory) {
					portalGitWorkingDirectory =
						(PortalGitWorkingDirectory)gitWorkingDirectory;
				}
			}

			_jobs.put(
				jobKey,
				new PortalAcceptancePullRequestJob(
					jobName, buildProfile, testSuiteName, branchName,
					portalGitWorkingDirectory));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("root-cause-analysis-tool")) {
			_jobs.put(
				jobKey,
				new RootCauseAnalysisToolJob(
					jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("root-cause-analysis-tool-batch")) {
			_jobs.put(
				jobKey,
				new RootCauseAnalysisToolBatchJob(
					jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-fixpack-builder-pullrequest")) {
			FixPackBuilderGitRepositoryJob fixPackBuilderGitRepositoryJob =
				new FixPackBuilderGitRepositoryJob(
					jobName, buildProfile, testSuiteName, branchName);

			_jobs.put(jobKey, fixPackBuilderGitRepositoryJob);

			return fixPackBuilderGitRepositoryJob;
		}

		if (jobName.startsWith("test-plugins-acceptance-pullrequest(")) {
			PluginsAcceptancePullRequestJob pluginsAcceptancePullRequestJob =
				new PluginsAcceptancePullRequestJob(
					jobName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsAcceptancePullRequestJob);

			return pluginsAcceptancePullRequestJob;
		}

		if (jobName.equals("test-plugins-extraapps")) {
			PluginsExtraAppsJob pluginsExtraAppsJob = new PluginsExtraAppsJob(
				jobName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsExtraAppsJob);

			return pluginsExtraAppsJob;
		}

		if (jobName.equals("test-plugins-marketplaceapp")) {
			PluginsMarketplaceAppJob pluginsMarketplaceAppJob =
				new PluginsMarketplaceAppJob(
					jobName, testSuiteName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsMarketplaceAppJob);

			return pluginsMarketplaceAppJob;
		}

		if (jobName.equals("test-plugins-release")) {
			PluginsReleaseJob pluginsReleaseJob = new PluginsReleaseJob(
				jobName, testSuiteName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsReleaseJob);

			return pluginsReleaseJob;
		}

		if (jobName.equals("test-plugins-upstream")) {
			PluginsUpstreamJob pluginsUpstreamJob = new PluginsUpstreamJob(
				jobName, testSuiteName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsUpstreamJob);

			return pluginsUpstreamJob;
		}

		if (jobName.startsWith("test-portal-acceptance-pullrequest(")) {
			_jobs.put(
				jobKey,
				new PortalAcceptancePullRequestJob(
					jobName, buildProfile, testSuiteName, branchName,
					portalGitWorkingDirectory));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-acceptance-upstream")) {
			_jobs.put(
				jobKey,
				new PortalAcceptanceUpstreamJob(
					jobName, buildProfile, testSuiteName, branchName,
					portalGitWorkingDirectory));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-app-release")) {
			_jobs.put(
				jobKey,
				new PortalAppReleaseJob(jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-aws(")) {
			_jobs.put(
				jobKey, new PortalAWSJob(jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-environment(")) {
			_jobs.put(
				jobKey,
				new PortalEnvironmentJob(jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-environment-release(")) {
			_jobs.put(
				jobKey,
				new PortalReleaseEnvironmentJob(
					jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-fixpack-environment(")) {
			_jobs.put(
				jobKey,
				new PortalFixpackEnvironmentJob(
					jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-fixpack-release")) {
			_jobs.put(
				jobKey,
				new PortalFixpackReleaseJob(
					jobName, buildProfile, branchName, testSuiteName,
					portalGitWorkingDirectory));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-hotfix-release")) {
			_jobs.put(
				jobKey,
				new PortalHotfixReleaseJob(
					jobName, buildProfile, branchName, testSuiteName,
					portalGitWorkingDirectory));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-release")) {
			_jobs.put(
				jobKey,
				new PortalReleaseJob(
					jobName, buildProfile, branchName, testSuiteName,
					portalGitWorkingDirectory));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-source-format")) {
			_jobs.put(
				jobKey,
				new PortalAcceptancePullRequestJob(
					jobName, buildProfile, "sf", branchName,
					portalGitWorkingDirectory));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-testsuite-upstream(")) {
			_jobs.put(
				jobKey,
				new PortalTestSuiteUpstreamJob(
					jobName, buildProfile, testSuiteName, branchName,
					portalGitWorkingDirectory));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-testsuite-upstream-controller(") ||
			jobName.startsWith(
				"test-qa-websites-functional-daily-controller(")) {

			_jobs.put(jobKey, new SimpleJob(jobName, buildProfile));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-qa-websites-functional-daily") ||
			jobName.equals("test-qa-websites-functional-environment") ||
			jobName.equals("test-qa-websites-functional-weekly")) {

			_jobs.put(
				jobKey,
				new QAWebsitesGitRepositoryJob(
					jobName, buildProfile, testSuiteName, branchName,
					projectNames));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-results-consistency-report-controller")) {
			_jobs.put(jobKey, new SimpleJob(jobName, buildProfile));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-subrepository-acceptance-pullrequest(")) {
			_jobs.put(
				jobKey,
				new SubrepositoryAcceptancePullRequestJob(
					jobName, buildProfile, testSuiteName, repositoryName,
					portalUpstreamBranchName));

			return _jobs.get(jobKey);
		}

		_jobs.put(
			jobKey, new DefaultPortalJob(jobName, buildProfile, testSuiteName));

		return _jobs.get(jobKey);
	}

	private static final Map<String, Job> _jobs = new HashMap<>();

}