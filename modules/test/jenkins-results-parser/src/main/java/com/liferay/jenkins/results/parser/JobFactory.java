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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;

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
			topLevelBuild.getProjectNames(), null, null);
	}

	public static Job newJob(BuildData buildData) {
		String upstreamBranchName = null;

		if (buildData instanceof PortalBuildData) {
			PortalBuildData portalBuildData = (PortalBuildData)buildData;

			upstreamBranchName = portalBuildData.getPortalUpstreamBranchName();
		}

		return _newJob(
			buildData.getJobName(), null, upstreamBranchName, null, null, null,
			null, null, null);
	}

	public static Job newJob(JSONObject jsonObject) {
		return _newJob(
			null, null, null, null, null, null, null, null, jsonObject);
	}

	public static Job newJob(String jobName) {
		return _newJob(jobName, null, null, null, null, null, null, null, null);
	}

	public static Job newJob(String jobName, String testSuiteName) {
		return _newJob(
			jobName, testSuiteName, null, null, null, null, null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName) {

		return _newJob(
			jobName, testSuiteName, branchName, null, null, null, null, null,
			null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, null, null,
			null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName, Job.BuildProfile buildProfile) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, buildProfile,
			null, null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName, Job.BuildProfile buildProfile,
		PortalGitWorkingDirectory portalGitWorkingDirectory) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, buildProfile,
			null, null, portalGitWorkingDirectory, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName, Job.BuildProfile buildProfile,
		String portalUpstreamBranchName, List<String> projectNames,
		PortalGitWorkingDirectory portalGitWorkingDirectory) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, buildProfile,
			portalUpstreamBranchName, projectNames, portalGitWorkingDirectory,
			null);
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
		PortalGitWorkingDirectory portalGitWorkingDirectory,
		JSONObject jsonObject) {

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			portalUpstreamBranchName = branchName;
		}

		if (jsonObject != null) {
			String buildProfileString = jsonObject.getString("build_profile");

			buildProfile = Job.BuildProfile.valueOf(
				buildProfileString.toUpperCase());

			jobName = jsonObject.getString("job_name");
			testSuiteName = jsonObject.getString("test_suite_name");
		}

		StringBuilder sb = new StringBuilder();

		sb.append(jobName);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(branchName)) {
			sb.append("_");
			sb.append(branchName);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(repositoryName)) {
			sb.append("_");
			sb.append(repositoryName);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			sb.append("_liferay-portal");

			if (!portalUpstreamBranchName.equals("master")) {
				sb.append("-ee");
			}

			sb.append("_");
			sb.append(portalUpstreamBranchName);
		}

		if (buildProfile == null) {
			buildProfile = Job.BuildProfile.PORTAL;
		}

		sb.append("_");
		sb.append(buildProfile);

		if (JenkinsResultsParserUtil.isNullOrEmpty(testSuiteName)) {
			testSuiteName = "default";
		}

		sb.append("_");
		sb.append(testSuiteName);

		if (projectNames != null) {
			Collections.sort(projectNames);

			sb.append("_");
			sb.append(JenkinsResultsParserUtil.join("_", projectNames));
		}

		String key = sb.toString();

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		if ((jsonObject == null) && buildDatabase.hasJob(key)) {
			return buildDatabase.getJob(key);
		}

		Job job = _jobs.get(key);

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

			if (jsonObject != null) {
				job = new PortalAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new PortalAcceptancePullRequestJob(
					jobName, buildProfile, testSuiteName, branchName,
					portalGitWorkingDirectory);
			}
		}

		if (jobName.equals("root-cause-analysis-tool")) {
			if (jsonObject != null) {
				job = new RootCauseAnalysisToolJob(jsonObject);
			}
			else {
				job = new RootCauseAnalysisToolJob(
					jobName, buildProfile, branchName);
			}
		}

		if (jobName.equals("root-cause-analysis-tool-batch")) {
			if (jsonObject != null) {
				job = new RootCauseAnalysisToolBatchJob(jsonObject);
			}
			else {
				job = new RootCauseAnalysisToolBatchJob(
					jobName, buildProfile, branchName);
			}
		}

		if (jobName.equals("test-fixpack-builder-pullrequest")) {
			if (jsonObject != null) {
				job = new FixPackBuilderGitRepositoryJob(jsonObject);
			}
			else {
				job = new FixPackBuilderGitRepositoryJob(
					jobName, buildProfile, testSuiteName, branchName);
			}
		}

		if (jobName.startsWith("test-plugins-acceptance-pullrequest(")) {
			if (jsonObject != null) {
				job = new PluginsAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new PluginsAcceptancePullRequestJob(
					jobName, buildProfile, branchName);
			}
		}

		if (jobName.equals("test-plugins-extraapps")) {
			if (jsonObject != null) {
				job = new PluginsExtraAppsJob(jsonObject);
			}
			else {
				job = new PluginsExtraAppsJob(
					jobName, buildProfile, branchName);
			}
		}

		if (jobName.equals("test-plugins-marketplaceapp")) {
			if (jsonObject != null) {
				job = new PluginsMarketplaceAppJob(jsonObject);
			}
			else {
				job = new PluginsMarketplaceAppJob(
					jobName, testSuiteName, buildProfile, branchName);
			}
		}

		if (jobName.equals("test-plugins-release")) {
			if (jsonObject != null) {
				job = new PluginsReleaseJob(jsonObject);
			}
			else {
				job = new PluginsReleaseJob(
					jobName, testSuiteName, buildProfile, branchName);
			}
		}

		if (jobName.equals("test-plugins-upstream")) {
			if (jsonObject != null) {
				job = new PluginsUpstreamJob(jsonObject);
			}
			else {
				job = new PluginsUpstreamJob(
					jobName, testSuiteName, buildProfile, branchName);
			}
		}

		if (jobName.startsWith("test-portal-acceptance-pullrequest(")) {
			if (jsonObject != null) {
				job = new PortalAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new PortalAcceptancePullRequestJob(
					jobName, buildProfile, testSuiteName, branchName,
					portalGitWorkingDirectory);
			}
		}

		if (jobName.startsWith("test-portal-acceptance-upstream")) {
			if (jsonObject != null) {
				job = new PortalAcceptanceUpstreamJob(jsonObject);
			}
			else {
				job = new PortalAcceptanceUpstreamJob(
					jobName, buildProfile, testSuiteName, branchName,
					portalGitWorkingDirectory);
			}
		}

		if (jobName.equals("test-portal-app-release")) {
			if (jsonObject != null) {
				job = new PortalAppReleaseJob(jsonObject);
			}
			else {
				job = new PortalAppReleaseJob(
					jobName, buildProfile, branchName);
			}
		}

		if (jobName.startsWith("test-portal-aws(")) {
			if (jsonObject != null) {
				job = new PortalAWSJob(jsonObject);
			}
			else {
				job = new PortalAWSJob(jobName, buildProfile, branchName);
			}
		}

		if (jobName.startsWith("test-portal-environment(")) {
			if (jsonObject != null) {
				job = new PortalEnvironmentJob(jsonObject);
			}
			else {
				job = new PortalEnvironmentJob(
					jobName, buildProfile, branchName);
			}
		}

		if (jobName.startsWith("test-portal-environment-release(")) {
			if (jsonObject != null) {
				job = new PortalReleaseEnvironmentJob(jsonObject);
			}
			else {
				job = new PortalReleaseEnvironmentJob(
					jobName, buildProfile, branchName);
			}
		}

		if (jobName.startsWith("test-portal-fixpack-environment(")) {
			if (jsonObject != null) {
				job = new PortalFixpackEnvironmentJob(jsonObject);
			}
			else {
				job = new PortalFixpackEnvironmentJob(
					jobName, buildProfile, branchName);
			}
		}

		if (jobName.equals("test-portal-fixpack-release")) {
			if (jsonObject != null) {
				job = new PortalFixpackReleaseJob(jsonObject);
			}
			else {
				job = new PortalFixpackReleaseJob(
					jobName, buildProfile, branchName, testSuiteName,
					portalGitWorkingDirectory);
			}
		}

		if (jobName.equals("test-portal-hotfix-release")) {
			if (jsonObject != null) {
				job = new PortalHotfixReleaseJob(jsonObject);
			}
			else {
				job = new PortalHotfixReleaseJob(
					jobName, buildProfile, branchName, testSuiteName,
					portalGitWorkingDirectory);
			}
		}

		if (jobName.equals("test-portal-release")) {
			if (jsonObject != null) {
				job = new PortalReleaseJob(jsonObject);
			}
			else {
				job = new PortalReleaseJob(
					jobName, buildProfile, branchName, testSuiteName,
					portalGitWorkingDirectory);
			}
		}

		if (jobName.equals("test-portal-source-format")) {
			if (jsonObject != null) {
				job = new PortalAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new PortalAcceptancePullRequestJob(
					jobName, buildProfile, "sf", branchName,
					portalGitWorkingDirectory);
			}
		}

		if (jobName.startsWith("test-portal-testsuite-upstream(")) {
			if (jsonObject != null) {
				job = new PortalTestSuiteUpstreamJob(jsonObject);
			}
			else {
				job = new PortalTestSuiteUpstreamJob(
					jobName, buildProfile, testSuiteName, branchName,
					portalGitWorkingDirectory);
			}
		}

		if (jobName.startsWith("test-portal-testsuite-upstream-controller(") ||
			jobName.startsWith(
				"test-qa-websites-functional-daily-controller(")) {

			if (jsonObject != null) {
				job = new SimpleJob(jsonObject);
			}
			else {
				job = new SimpleJob(jobName, buildProfile);
			}
		}

		if (jobName.equals("test-qa-websites-functional-daily") ||
			jobName.equals("test-qa-websites-functional-environment") ||
			jobName.equals("test-qa-websites-functional-weekly")) {

			if (jsonObject != null) {
				job = new QAWebsitesGitRepositoryJob(jsonObject);
			}
			else {
				job = new QAWebsitesGitRepositoryJob(
					jobName, buildProfile, testSuiteName, branchName,
					projectNames);
			}
		}

		if (jobName.equals("test-results-consistency-report-controller")) {
			if (jsonObject != null) {
				job = new SimpleJob(jsonObject);
			}
			else {
				job = new SimpleJob(jobName, buildProfile);
			}
		}

		if (jobName.startsWith("test-subrepository-acceptance-pullrequest(")) {
			if (jsonObject != null) {
				job = new SubrepositoryAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new SubrepositoryAcceptancePullRequestJob(
					jobName, buildProfile, testSuiteName, branchName,
					repositoryName, portalUpstreamBranchName);
			}
		}

		if (job == null) {
			if (jsonObject != null) {
				job = new DefaultPortalJob(jsonObject);
			}
			else {
				job = new DefaultPortalJob(
					jobName, buildProfile, testSuiteName);
			}
		}

		_jobs.put(key, job);

		buildDatabase.putJob(key, job);

		return _jobs.get(key);
	}

	private static final Map<String, Job> _jobs = new HashMap<>();

}