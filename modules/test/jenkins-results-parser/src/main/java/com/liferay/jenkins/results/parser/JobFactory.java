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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JobFactory {

	public static String getKey(Job job) {
		PortalGitWorkingDirectory portalGitWorkingDirectory = null;

		if (job instanceof PortalTestClassJob) {
			PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

			portalGitWorkingDirectory =
				portalTestClassJob.getPortalGitWorkingDirectory();
		}

		List<String> projectNames = null;

		if (job instanceof QAWebsitesGitRepositoryJob) {
			QAWebsitesGitRepositoryJob qaWebsitesGitRepositoryJob =
				(QAWebsitesGitRepositoryJob)job;

			projectNames = qaWebsitesGitRepositoryJob.getProjectNames();
		}

		String repositoryName = null;

		if (job instanceof GitRepositoryJob) {
			GitRepositoryJob gitRepositoryJob = (GitRepositoryJob)job;

			GitWorkingDirectory gitWorkingDirectory =
				gitRepositoryJob.getGitWorkingDirectory();

			repositoryName = gitWorkingDirectory.getGitRepositoryName();
		}

		String testSuiteName = "default";

		if (job instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)job;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}

		String upstreamBranchName = null;

		if (job instanceof GitRepositoryJob) {
			GitRepositoryJob gitRepositoryJob = (GitRepositoryJob)job;

			upstreamBranchName = gitRepositoryJob.getUpstreamBranchName();
		}

		return getKey(
			job.getBuildProfile(), job.getJobName(), portalGitWorkingDirectory,
			null, projectNames, repositoryName, testSuiteName,
			upstreamBranchName);
	}

	public static String getKey(
		Job.BuildProfile buildProfile, String jobName,
		PortalGitWorkingDirectory portalGitWorkingDirectory,
		String portalUpstreamBranchName, List<String> projectNames,
		String repositoryName, String testSuiteName,
		String upstreamBranchName) {

		StringBuilder sb = new StringBuilder();

		if (buildProfile == null) {
			buildProfile = Job.BuildProfile.DXP;
		}

		sb.append(buildProfile);
		sb.append("_");

		sb.append(jobName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName) &&
			(portalGitWorkingDirectory != null)) {

			portalUpstreamBranchName =
				portalGitWorkingDirectory.getUpstreamBranchName();
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			sb.append("_");
			sb.append(portalUpstreamBranchName);
		}

		if ((projectNames != null) && !projectNames.isEmpty()) {
			Collections.sort(projectNames);

			sb.append("_");
			sb.append(JenkinsResultsParserUtil.join("_", projectNames));
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(repositoryName)) {
			sb.append("_");
			sb.append(repositoryName);
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(testSuiteName)) {
			testSuiteName = "default";
		}

		sb.append("_");
		sb.append(testSuiteName);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(upstreamBranchName)) {
			sb.append("_");
			sb.append(upstreamBranchName);
		}

		return sb.toString();
	}

	public static String getKey(JSONObject jsonObject) {
		Job.BuildProfile buildProfile = Job.BuildProfile.DXP;

		if (jsonObject.has("build_profile")) {
			buildProfile = Job.BuildProfile.getByString(
				jsonObject.getString("build_profile"));
		}

		List<String> projectNames = null;

		JSONArray projectNamesJSONArray = jsonObject.optJSONArray(
			"project_names");

		if ((projectNamesJSONArray != null) &&
			!projectNamesJSONArray.isEmpty()) {

			projectNames = new ArrayList<>();

			for (int i = 0; i < projectNamesJSONArray.length(); i++) {
				projectNames.add(projectNamesJSONArray.getString(i));
			}
		}

		return getKey(
			buildProfile, jsonObject.getString("job_name"), null,
			jsonObject.optString("portal_upstream_branch_name"), projectNames,
			jsonObject.optString("repository_name"),
			jsonObject.optString("test_suite_name"),
			jsonObject.optString("upstream_branch_name"));
	}

	public static Job newJob(Build build) {
		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		String portalUpstreamBranchName = topLevelBuild.getParameterValue(
			"PORTAL_UPSTREAM_BRANCH_NAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalUpstreamBranchName)) {
			portalUpstreamBranchName = topLevelBuild.getBranchName();
		}

		return _newJob(
			topLevelBuild.getBuildProfile(), topLevelBuild.getJobName(), null,
			null, portalUpstreamBranchName, topLevelBuild.getProjectNames(),
			topLevelBuild.getBaseGitRepositoryName(),
			topLevelBuild.getTestSuiteName(), topLevelBuild.getBranchName());
	}

	public static Job newJob(BuildData buildData) {
		Job.BuildProfile buildProfile = null;
		String portalUpstreamBranchName = null;
		String repositoryName = null;
		String upstreamBranchName = null;

		if (buildData instanceof PortalBuildData) {
			PortalBuildData portalBuildData = (PortalBuildData)buildData;

			buildProfile = portalBuildData.getBuildProfile();
			portalUpstreamBranchName =
				portalBuildData.getPortalUpstreamBranchName();
			repositoryName = portalBuildData.getPortalGitHubRepositoryName();
			upstreamBranchName = portalBuildData.getPortalUpstreamBranchName();
		}

		return _newJob(
			buildProfile, buildData.getJobName(), null, null,
			portalUpstreamBranchName, null, repositoryName, null,
			upstreamBranchName);
	}

	public static Job newJob(
		Job.BuildProfile buildProfile, String jobName, JSONObject jsonObject,
		PortalGitWorkingDirectory portalGitWorkingDirectory,
		String portalUpstreamBranchName, List<String> projectNames,
		String repositoryName, String testSuiteName,
		String upstreamBranchName) {

		return _newJob(
			buildProfile, jobName, jsonObject, portalGitWorkingDirectory,
			portalUpstreamBranchName, projectNames, repositoryName,
			testSuiteName, upstreamBranchName);
	}

	public static Job newJob(JSONObject jsonObject) {
		return _newJob(
			null, null, jsonObject, null, null, null, null, null, null);
	}

	public static Job newJob(String jobName) {
		return _newJob(null, jobName, null, null, null, null, null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String upstreamBranchName) {

		return _newJob(
			null, jobName, null, null, null, null, null, testSuiteName,
			upstreamBranchName);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String upstreamBranchName,
		String repositoryName, Job.BuildProfile buildProfile) {

		return _newJob(
			buildProfile, jobName, null, null, null, null, repositoryName,
			testSuiteName, upstreamBranchName);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String upstreamBranchName,
		String repositoryName, Job.BuildProfile buildProfile,
		PortalGitWorkingDirectory portalGitWorkingDirectory) {

		return _newJob(
			buildProfile, jobName, null, portalGitWorkingDirectory, null, null,
			repositoryName, testSuiteName, upstreamBranchName);
	}

	private static Job _newJob(
		Job.BuildProfile buildProfile, String jobName, JSONObject jsonObject,
		PortalGitWorkingDirectory portalGitWorkingDirectory,
		String portalUpstreamBranchName, List<String> projectNames,
		String repositoryName, String testSuiteName,
		String upstreamBranchName) {

		String key;

		if (jsonObject != null) {
			jobName = jsonObject.getString("job_name");

			key = getKey(jsonObject);
		}
		else {
			key = getKey(
				buildProfile, jobName, portalGitWorkingDirectory,
				portalUpstreamBranchName, projectNames, repositoryName,
				testSuiteName, upstreamBranchName);
		}

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

				String gitWorkingBranchName =
					JenkinsResultsParserUtil.getProperty(
						buildProperties, "git.working.branch.name");

				String gitRepositoryName = "liferay-portal";

				if (!gitWorkingBranchName.equals("master")) {
					gitRepositoryName += "-ee";
				}

				GitWorkingDirectory gitWorkingDirectory =
					GitWorkingDirectoryFactory.newGitWorkingDirectory(
						gitWorkingBranchName, gitWorkingDir, gitRepositoryName);

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
					buildProfile, jobName, portalGitWorkingDirectory,
					testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.equals("root-cause-analysis-tool")) {
			if (jsonObject != null) {
				job = new RootCauseAnalysisToolJob(jsonObject);
			}
			else {
				job = new RootCauseAnalysisToolJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.equals("root-cause-analysis-tool-batch")) {
			if (jsonObject != null) {
				job = new RootCauseAnalysisToolBatchJob(jsonObject);
			}
			else {
				job = new RootCauseAnalysisToolBatchJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-fixpack-builder-pullrequest")) {
			if (jsonObject != null) {
				job = new FixPackBuilderGitRepositoryJob(jsonObject);
			}
			else {
				job = new FixPackBuilderGitRepositoryJob(
					buildProfile, jobName, testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-plugins-acceptance-pullrequest(")) {
			if (jsonObject != null) {
				job = new PluginsAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new PluginsAcceptancePullRequestJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-plugins-extraapps")) {
			if (jsonObject != null) {
				job = new PluginsExtraAppsJob(jsonObject);
			}
			else {
				job = new PluginsExtraAppsJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-plugins-marketplaceapp")) {
			if (jsonObject != null) {
				job = new PluginsMarketplaceAppJob(jsonObject);
			}
			else {
				job = new PluginsMarketplaceAppJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-plugins-release")) {
			if (jsonObject != null) {
				job = new PluginsReleaseJob(jsonObject);
			}
			else {
				job = new PluginsReleaseJob(
					buildProfile, jobName, testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-plugins-upstream")) {
			if (jsonObject != null) {
				job = new PluginsUpstreamJob(jsonObject);
			}
			else {
				job = new PluginsUpstreamJob(
					buildProfile, jobName, testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-portal-acceptance-pullrequest(")) {
			if (jsonObject != null) {
				job = new PortalAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new PortalAcceptancePullRequestJob(
					buildProfile, jobName, portalGitWorkingDirectory,
					testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-portal-acceptance-upstream")) {
			if (jsonObject != null) {
				job = new PortalAcceptanceUpstreamJob(jsonObject);
			}
			else {
				job = new PortalAcceptanceUpstreamJob(
					buildProfile, jobName, portalGitWorkingDirectory,
					testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-portal-app-release")) {
			if (jsonObject != null) {
				job = new PortalAppReleaseJob(jsonObject);
			}
			else {
				job = new PortalAppReleaseJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-portal-aws(")) {
			if (jsonObject != null) {
				job = new PortalAWSJob(jsonObject);
			}
			else {
				job = new PortalAWSJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-portal-environment(")) {
			if (jsonObject != null) {
				job = new PortalEnvironmentJob(jsonObject);
			}
			else {
				job = new PortalEnvironmentJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-portal-environment-release(")) {
			if (jsonObject != null) {
				job = new PortalReleaseEnvironmentJob(jsonObject);
			}
			else {
				job = new PortalReleaseEnvironmentJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-portal-fixpack-environment(")) {
			if (jsonObject != null) {
				job = new PortalFixpackEnvironmentJob(jsonObject);
			}
			else {
				job = new PortalFixpackEnvironmentJob(
					buildProfile, jobName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-portal-fixpack-release")) {
			if (jsonObject != null) {
				job = new PortalFixpackReleaseJob(jsonObject);
			}
			else {
				job = new PortalFixpackReleaseJob(
					buildProfile, jobName, portalGitWorkingDirectory,
					testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-portal-hotfix-release")) {
			if (jsonObject != null) {
				job = new PortalHotfixReleaseJob(jsonObject);
			}
			else {
				job = new PortalHotfixReleaseJob(
					buildProfile, jobName, portalGitWorkingDirectory,
					testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-portal-release")) {
			if (jsonObject != null) {
				job = new PortalReleaseJob(jsonObject);
			}
			else {
				job = new PortalReleaseJob(
					buildProfile, jobName, portalGitWorkingDirectory,
					testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.equals("test-portal-source-format")) {
			if (jsonObject != null) {
				job = new PortalAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new PortalAcceptancePullRequestJob(
					buildProfile, jobName, portalGitWorkingDirectory, "sf",
					upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-portal-testsuite-upstream(")) {
			if (jsonObject != null) {
				job = new PortalTestSuiteUpstreamJob(jsonObject);
			}
			else {
				job = new PortalTestSuiteUpstreamJob(
					buildProfile, jobName, portalGitWorkingDirectory,
					testSuiteName, upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-portal-testsuite-upstream-controller(") ||
			jobName.equals("test-results-consistency-report-controller") ||
			jobName.startsWith(
				"test-qa-websites-functional-daily-controller(")) {

			if (jsonObject != null) {
				job = new SimpleJob(jsonObject);
			}
			else {
				job = new SimpleJob(buildProfile, jobName);
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
					buildProfile, jobName, projectNames, testSuiteName,
					upstreamBranchName);
			}
		}

		if (jobName.startsWith("test-subrepository-acceptance-pullrequest(")) {
			if (jsonObject != null) {
				job = new SubrepositoryAcceptancePullRequestJob(jsonObject);
			}
			else {
				job = new SubrepositoryAcceptancePullRequestJob(
					buildProfile, jobName, portalUpstreamBranchName,
					repositoryName, testSuiteName, upstreamBranchName);
			}
		}

		if (job == null) {
			if (jsonObject != null) {
				job = new DefaultPortalJob(jsonObject);
			}
			else {
				job = new DefaultPortalJob(
					buildProfile, jobName, testSuiteName);
			}
		}

		_jobs.put(key, job);

		if (jsonObject == null) {
			buildDatabase.putJob(key, job);
		}

		return _jobs.get(key);
	}

	private static final Map<String, Job> _jobs = new HashMap<>();

}