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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PluginsTopLevelBuild;
import com.liferay.jenkins.results.parser.PortalAppReleaseTopLevelBuild;
import com.liferay.jenkins.results.parser.PortalBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalFixpackRelease;
import com.liferay.jenkins.results.parser.PortalFixpackReleaseBuild;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalRelease;
import com.liferay.jenkins.results.parser.PortalReleaseBuild;
import com.liferay.jenkins.results.parser.PullRequest;
import com.liferay.jenkins.results.parser.PullRequestBuild;
import com.liferay.jenkins.results.parser.QAWebsitesTopLevelBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraRelease;
import com.liferay.jenkins.results.parser.spira.SpiraReleaseBuild;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseFolder;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseProductVersion;
import com.liferay.jenkins.results.parser.spira.SpiraTestSet;

import java.util.Date;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class BaseSpiraBuildResult implements SpiraBuildResult {

	@Override
	public PortalFixpackRelease getPortalFixpackRelease() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (!(topLevelBuild instanceof PortalFixpackReleaseBuild)) {
			return null;
		}

		PortalFixpackReleaseBuild portalFixpackReleaseBuild =
			(PortalFixpackReleaseBuild)topLevelBuild;

		return portalFixpackReleaseBuild.getPortalFixpackRelease();
	}

	@Override
	public PortalRelease getPortalRelease() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (!(topLevelBuild instanceof PortalReleaseBuild)) {
			return null;
		}

		PortalReleaseBuild portalReleaseBuild =
			(PortalReleaseBuild)topLevelBuild;

		return portalReleaseBuild.getPortalRelease();
	}

	@Override
	public Properties getPortalTestProperties() {
		return _portalGitWorkingDirectory.getTestProperties();
	}

	@Override
	public PullRequest getPullRequest() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (!(topLevelBuild instanceof PullRequestBuild)) {
			return null;
		}

		PullRequestBuild pullRequestBuild = (PullRequestBuild)topLevelBuild;

		return pullRequestBuild.getPullRequest();
	}

	@Override
	public SpiraProject getSpiraProject() {
		return _spiraProject;
	}

	@Override
	public SpiraRelease getSpiraRelease() {
		return _spiraRelease;
	}

	@Override
	public SpiraReleaseBuild getSpiraReleaseBuild() {
		return _spiraReleaseBuild;
	}

	@Override
	public SpiraTestCaseFolder getSpiraTestCaseFolder() {
		return _spiraTestCaseFolder;
	}

	@Override
	public SpiraTestCaseProductVersion getSpiraTestCaseProductVersion() {
		return _spiraTestCaseProductVersion;
	}

	@Override
	public SpiraTestSet getSpiraTestSet() {
		return _spiraTestSet;
	}

	@Override
	public TopLevelBuild getTopLevelBuild() {
		return _topLevelBuild;
	}

	protected BaseSpiraBuildResult(TopLevelBuild topLevelBuild) {
		_topLevelBuild = topLevelBuild;

		_portalGitWorkingDirectory = _getPortalGitWorkingDirectory();

		_spiraProject = _getSpiraProject();

		_spiraRelease = _getSpiraRelease(_spiraProject);
		_spiraTestCaseFolder = _getSpiraTestCaseFolder(_spiraProject);
		_spiraTestCaseProductVersion = _getSpiraTestCaseProductVersion(
			_spiraProject);
		_spiraTestSet = _getSpiraTestSet(_spiraProject);

		_spiraReleaseBuild = _getSpiraReleaseBuild(
			_spiraProject, _spiraRelease);
	}

	protected String getPortalTestSuiteProperty(String propertyName) {
		return JenkinsResultsParserUtil.getProperty(
			_portalGitWorkingDirectory.getTestProperties(), propertyName,
			_topLevelBuild.getJobName(), _topLevelBuild.getTestSuiteName());
	}

	protected String replaceEnvVars(String string) {
		string = _replaceEnvVarsControllerBuild(string);
		string = _replaceEnvVarsPluginsTopLevelBuild(string);
		string = _replaceEnvVarsPortalAppReleaseTopLevelBuild(string);
		string = _replaceEnvVarsPortalBranchInformationBuild(string);
		string = _replaceEnvVarsPullRequestBuild(string);
		string = _replaceEnvVarsQAWebsitesTopLevelBuild(string);
		string = _replaceEnvVarsTopLevelBuild(string);

		return string;
	}

	private PortalGitWorkingDirectory _getPortalGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			_topLevelBuild.getBranchName());
	}

	private SpiraProject _getSpiraProject() {
		long start = System.currentTimeMillis();

		String spiraProjectID = System.getenv("TEST_SPIRA_PROJECT_ID");

		if ((spiraProjectID == null) || !spiraProjectID.matches("\\d+")) {
			spiraProjectID = getPortalTestSuiteProperty(
				"test.batch.spira.project.id");
		}

		SpiraProject spiraProject = null;

		if ((spiraProjectID != null) && spiraProjectID.matches("\\d+")) {
			spiraProject = SpiraProject.getSpiraProjectByID(
				Integer.valueOf(spiraProjectID));
		}

		if (spiraProject != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Spira Project ", spiraProject.getURL(), " created in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));

			return spiraProject;
		}

		return null;
	}

	private SpiraRelease _getSpiraRelease(SpiraProject spiraProject) {
		long start = System.currentTimeMillis();

		String spiraReleaseID = System.getProperty("TEST_SPIRA_RELEASE_ID");

		SpiraRelease spiraRelease = null;

		if ((spiraReleaseID != null) && spiraReleaseID.matches("\\d+")) {
			spiraRelease = spiraProject.getSpiraReleaseByID(
				Integer.valueOf(spiraReleaseID));
		}

		String spiraReleasePath = System.getProperty("TEST_SPIRA_RELEASE_PATH");

		if ((spiraRelease == null) && (spiraReleasePath != null) &&
			spiraReleasePath.matches("\\/.+")) {

			spiraRelease = SpiraRelease.createSpiraReleaseByPath(
				spiraProject, replaceEnvVars(spiraReleasePath));
		}

		spiraReleaseID = getPortalTestSuiteProperty(
			"test.batch.spira.release.id");

		if ((spiraRelease == null) && (spiraReleaseID != null) &&
			spiraReleaseID.matches("\\d+")) {

			spiraRelease = spiraProject.getSpiraReleaseByID(
				Integer.valueOf(spiraReleaseID));
		}

		spiraReleasePath = getPortalTestSuiteProperty(
			"test.batch.spira.release.path");

		if ((spiraRelease == null) && (spiraReleasePath != null) &&
			spiraReleasePath.matches("\\/.+")) {

			spiraRelease = SpiraRelease.createSpiraReleaseByPath(
				spiraProject, replaceEnvVars(spiraReleasePath));
		}

		if (spiraRelease != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Spira Release ", spiraRelease.getURL(), " created in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));

			return spiraRelease;
		}

		return null;
	}

	private SpiraReleaseBuild _getSpiraReleaseBuild(
		SpiraProject spiraProject, SpiraRelease spiraRelease) {

		long start = System.currentTimeMillis();

		String spiraReleaseBuildID = System.getenv("TEST_SPIRA_BUILD_ID");

		SpiraReleaseBuild spiraReleaseBuild = null;

		if ((spiraReleaseBuildID != null) &&
			spiraReleaseBuildID.matches("\\d+")) {

			spiraReleaseBuild = spiraRelease.getSpiraReleaseBuildByID(
				Integer.valueOf(spiraReleaseBuildID));
		}

		String spiraReleaseBuildName = System.getenv("TEST_SPIRA_BUILD_NAME");

		if ((spiraReleaseBuild == null) && (spiraReleaseBuildName != null) &&
			!spiraReleaseBuildName.isEmpty()) {

			spiraReleaseBuild = SpiraReleaseBuild.createSpiraReleaseBuild(
				spiraProject, spiraRelease,
				replaceEnvVars(spiraReleaseBuildName),
				_getSpiraReleaseBuildDescription(),
				_getSpiraReleaseBuildStatus(), _topLevelBuild.getStartTime());
		}

		spiraReleaseBuildID = getPortalTestSuiteProperty(
			"test.batch.spira.build.id");

		if ((spiraReleaseBuild == null) && (spiraReleaseBuildID != null) &&
			spiraReleaseBuildID.matches("\\d+")) {

			spiraReleaseBuild = spiraRelease.getSpiraReleaseBuildByID(
				Integer.valueOf(spiraReleaseBuildID));
		}

		spiraReleaseBuildName = getPortalTestSuiteProperty(
			"test.batch.spira.build.name");

		if ((spiraReleaseBuild == null) && (spiraReleaseBuildName != null) &&
			!spiraReleaseBuildName.isEmpty()) {

			spiraReleaseBuild = SpiraReleaseBuild.createSpiraReleaseBuild(
				spiraProject, spiraRelease,
				replaceEnvVars(spiraReleaseBuildName),
				_getSpiraReleaseBuildDescription(),
				_getSpiraReleaseBuildStatus(), _topLevelBuild.getStartTime());
		}

		if (spiraReleaseBuild != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Spira Release Build ", spiraReleaseBuild.getURL(),
					" created in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));

			return spiraReleaseBuild;
		}

		return null;
	}

	private String _getSpiraReleaseBuildDescription() {
		String spiraReleaseBuildDescription = System.getenv(
			"TEST_SPIRA_BUILD_DESCRIPTION");

		if ((spiraReleaseBuildDescription == null) ||
			spiraReleaseBuildDescription.isEmpty()) {

			spiraReleaseBuildDescription = getPortalTestSuiteProperty(
				"test.batch.spira.build.description");
		}

		if (spiraReleaseBuildDescription == null) {
			spiraReleaseBuildDescription = "";
		}

		return replaceEnvVars(spiraReleaseBuildDescription);
	}

	private SpiraReleaseBuild.Status _getSpiraReleaseBuildStatus() {
		String result = _topLevelBuild.getResult();
		String status = _topLevelBuild.getStatus();

		if (result.equals("ABORTED")) {
			return SpiraReleaseBuild.Status.ABORTED;
		}
		else if (result.equals("APPROVED") && status.equals("completed")) {
			return SpiraReleaseBuild.Status.UNSTABLE;
		}
		else if (result.equals("SUCCESS") && status.equals("completed")) {
			return SpiraReleaseBuild.Status.SUCCEEDED;
		}

		return SpiraReleaseBuild.Status.FAILED;
	}

	private SpiraTestCaseFolder _getSpiraTestCaseFolder(
		SpiraProject spiraProject) {

		long start = System.currentTimeMillis();

		String spiraTestCaseFolderID = System.getenv(
			"TEST_SPIRA_BASE_TEST_CASE_FOLDER_ID");

		SpiraTestCaseFolder spiraTestCaseFolder = null;

		if ((spiraTestCaseFolderID != null) &&
			spiraTestCaseFolderID.matches("\\d+")) {

			spiraTestCaseFolder = spiraProject.getSpiraTestCaseFolderByID(
				Integer.valueOf(spiraTestCaseFolderID));
		}

		String spiraTestCaseFolderPath = System.getenv(
			"TEST_SPIRA_BASE_TEST_CASE_FOLDER_PATH");

		if ((spiraTestCaseFolder == null) &&
			(spiraTestCaseFolderPath != null) &&
			spiraTestCaseFolderPath.matches("\\/.+")) {

			spiraTestCaseFolder =
				SpiraTestCaseFolder.createSpiraTestCaseFolderByPath(
					spiraProject, replaceEnvVars(spiraTestCaseFolderPath));
		}

		spiraTestCaseFolderID = getPortalTestSuiteProperty(
			"test.batch.spira.base.test.case.folder.id");

		if ((spiraTestCaseFolder == null) && (spiraTestCaseFolderID != null) &&
			spiraTestCaseFolderID.matches("\\d+")) {

			spiraTestCaseFolder = spiraProject.getSpiraTestCaseFolderByID(
				Integer.valueOf(spiraTestCaseFolderID));
		}

		spiraTestCaseFolderPath = getPortalTestSuiteProperty(
			"test.batch.spira.base.test.case.folder.path");

		if ((spiraTestCaseFolder == null) &&
			(spiraTestCaseFolderPath != null) &&
			spiraTestCaseFolderPath.matches("\\/.+")) {

			spiraTestCaseFolder =
				SpiraTestCaseFolder.createSpiraTestCaseFolderByPath(
					spiraProject, replaceEnvVars(spiraTestCaseFolderPath));
		}

		if (spiraTestCaseFolder != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Base Spira Test Case Folder ",
					spiraTestCaseFolder.getURL(), " created in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));

			return spiraTestCaseFolder;
		}

		return null;
	}

	private SpiraTestCaseProductVersion _getSpiraTestCaseProductVersion(
		SpiraProject spiraProject) {

		return SpiraTestCaseProductVersion.createSpiraTestCaseProductVersion(
			spiraProject, SpiraTestCaseObject.class,
			_portalGitWorkingDirectory.getMajorPortalVersion());
	}

	private SpiraTestSet _getSpiraTestSet(SpiraProject spiraProject) {
		long start = System.currentTimeMillis();

		SpiraTestSet spiraTestSet = null;

		String spiraTestSetID = System.getenv("TEST_SPIRA_TEST_SET_ID");

		if ((spiraTestSetID != null) && spiraTestSetID.matches("\\d+")) {
			spiraTestSet = spiraProject.getSpiraTestSetByID(
				Integer.valueOf(spiraTestSetID));
		}

		String spiraTestSetPath = System.getenv("TEST_SPIRA_TEST_SET_PATH");

		if ((spiraTestSet == null) && (spiraTestSetPath != null) &&
			spiraTestSetPath.matches("\\/.+")) {

			spiraTestSet = SpiraTestSet.createSpiraTestSetByPath(
				spiraProject, replaceEnvVars(spiraTestSetPath),
				_getSpiraTestSetDescription());
		}

		spiraTestSetID = getPortalTestSuiteProperty(
			"test.batch.spira.test.set.id");

		if ((spiraTestSet == null) && (spiraTestSetID != null) &&
			spiraTestSetID.matches("\\d+")) {

			spiraTestSet = spiraProject.getSpiraTestSetByID(
				Integer.valueOf(spiraTestSetID));
		}

		spiraTestSetPath = getPortalTestSuiteProperty(
			"test.batch.spira.test.set.path");

		if ((spiraTestSet == null) && (spiraTestSetPath != null) &&
			spiraTestSetPath.matches("\\/.+")) {

			spiraTestSet = SpiraTestSet.createSpiraTestSetByPath(
				spiraProject, replaceEnvVars(spiraTestSetPath),
				_getSpiraTestSetDescription());
		}

		if (spiraTestSet != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Spira Test Set created in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));

			return spiraTestSet;
		}

		return null;
	}

	private String _getSpiraTestSetDescription() {
		String spiraTestSetDescription = System.getenv(
			"TEST_SPIRA_TEST_SET_DESCRIPTION");

		if ((spiraTestSetDescription == null) ||
			spiraTestSetDescription.isEmpty()) {

			spiraTestSetDescription = getPortalTestSuiteProperty(
				"test.batch.spira.test.set.description");
		}

		if (spiraTestSetDescription == null) {
			spiraTestSetDescription = "";
		}

		return replaceEnvVars(spiraTestSetDescription);
	}

	private String _replaceEnvVarsControllerBuild(String string) {
		Build controllerBuild = _topLevelBuild.getControllerBuild();

		if (controllerBuild == null) {
			return string;
		}

		string = string.replace(
			"$(jenkins.controller.build.url)", controllerBuild.getBuildURL());

		string = string.replace(
			"$(jenkins.controller.build.number)",
			String.valueOf(controllerBuild.getBuildNumber()));

		string = string.replace(
			"$(jenkins.controller.build.start)",
			JenkinsResultsParserUtil.toDateString(
				new Date(controllerBuild.getStartTime()),
				"yyyy-MM-dd[HH:mm:ss]", "America/Los_Angeles"));

		string = string.replace(
			"$(jenkins.controller.job.name)", controllerBuild.getJobName());

		JenkinsMaster jenkinsMaster = controllerBuild.getJenkinsMaster();

		return string.replace(
			"$(jenkins.controller.master.hostname)", jenkinsMaster.getName());
	}

	private String _replaceEnvVarsPluginsTopLevelBuild(String string) {
		if (!(_topLevelBuild instanceof PluginsTopLevelBuild)) {
			return string;
		}

		PluginsTopLevelBuild pluginsTopLevelBuild =
			(PluginsTopLevelBuild)_topLevelBuild;

		String pluginName = pluginsTopLevelBuild.getPluginName();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(pluginName)) {
			string = string.replace("$(plugin.name)", pluginName);
		}

		return string;
	}

	private String _replaceEnvVarsPortalAppReleaseTopLevelBuild(String string) {
		if (!(_topLevelBuild instanceof PortalAppReleaseTopLevelBuild)) {
			return string;
		}

		PortalAppReleaseTopLevelBuild portalAppReleaseTopLevelBuild =
			(PortalAppReleaseTopLevelBuild)_topLevelBuild;

		return string.replace(
			"$(portal.app.name)",
			portalAppReleaseTopLevelBuild.getPortalAppName());
	}

	private String _replaceEnvVarsPortalBranchInformationBuild(String string) {
		Job.BuildProfile buildProfile = _topLevelBuild.getBuildProfile();

		string = string.replace(
			"$(portal.profile)", buildProfile.toDisplayString());

		string = string.replace(
			"$(portal.version)",
			_portalGitWorkingDirectory.getMajorPortalVersion());

		if (!(_topLevelBuild instanceof PortalBranchInformationBuild)) {
			return string;
		}

		PortalBranchInformationBuild portalBranchInformationBuild =
			(PortalBranchInformationBuild)_topLevelBuild;

		Build.BranchInformation portalBranchInformation =
			portalBranchInformationBuild.getPortalBranchInformation();

		if (portalBranchInformation == null) {
			return string;
		}

		string = string.replace(
			"$(portal.branch.name)",
			portalBranchInformation.getUpstreamBranchName());

		string = string.replace(
			"$(portal.repository)",
			portalBranchInformation.getRepositoryName());

		return string.replace(
			"$(portal.sha)", portalBranchInformation.getSenderBranchSHA());
	}

	private String _replaceEnvVarsPullRequestBuild(String string) {
		PullRequest pullRequest = getPullRequest();

		if (pullRequest == null) {
			return string;
		}

		string = string.replace(
			"$(pull.request.number)", pullRequest.getNumber());

		string = string.replace(
			"$(pull.request.receiver.username)",
			pullRequest.getReceiverUsername());

		return string.replace(
			"$(pull.request.sender.username)", pullRequest.getSenderUsername());
	}

	private String _replaceEnvVarsQAWebsitesTopLevelBuild(String string) {
		if (!(_topLevelBuild instanceof QAWebsitesTopLevelBuild)) {
			return string;
		}

		QAWebsitesTopLevelBuild qaWebsitesTopLevelBuild =
			(QAWebsitesTopLevelBuild)_topLevelBuild;

		return string.replace(
			"$(qa.websites.project.name)",
			JenkinsResultsParserUtil.join(
				",", qaWebsitesTopLevelBuild.getProjectNames()));
	}

	private String _replaceEnvVarsTopLevelBuild(String string) {
		string = string.replace(
			"$(ci.test.suite)", _topLevelBuild.getTestSuiteName());

		string = string.replace(
			"$(jenkins.build.number)",
			String.valueOf(_topLevelBuild.getBuildNumber()));

		string = string.replace(
			"$(jenkins.build.start)",
			JenkinsResultsParserUtil.toDateString(
				new Date(_topLevelBuild.getStartTime()), "yyyy-MM-dd[HH:mm:ss]",
				"America/Los_Angeles"));

		string = string.replace(
			"$(jenkins.build.url)", _topLevelBuild.getBuildURL());

		string = string.replace(
			"$(jenkins.job.name)", _topLevelBuild.getJobName());

		JenkinsMaster jenkinsMaster = _topLevelBuild.getJenkinsMaster();

		string = string.replace(
			"$(jenkins.master.hostname)", jenkinsMaster.getName());

		return string.replace(
			"$(jenkins.report.url)", _topLevelBuild.getJenkinsReportURL());
	}

	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final SpiraProject _spiraProject;
	private final SpiraRelease _spiraRelease;
	private final SpiraReleaseBuild _spiraReleaseBuild;
	private final SpiraTestCaseFolder _spiraTestCaseFolder;
	private final SpiraTestCaseProductVersion _spiraTestCaseProductVersion;
	private final SpiraTestSet _spiraTestSet;
	private final TopLevelBuild _topLevelBuild;

}