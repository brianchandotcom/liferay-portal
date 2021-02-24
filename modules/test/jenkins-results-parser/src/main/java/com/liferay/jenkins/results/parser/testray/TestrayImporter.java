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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.BatchDependentJob;
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
import com.liferay.jenkins.results.parser.PortalHotfixRelease;
import com.liferay.jenkins.results.parser.PortalHotfixReleaseBuild;
import com.liferay.jenkins.results.parser.PortalRelease;
import com.liferay.jenkins.results.parser.PortalReleaseBuild;
import com.liferay.jenkins.results.parser.PullRequest;
import com.liferay.jenkins.results.parser.PullRequestBuild;
import com.liferay.jenkins.results.parser.QAWebsitesTopLevelBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class TestrayImporter {

	public TestrayImporter(Build build) {
		if (build == null) {
			throw new RuntimeException("Please provide a valid build");
		}

		_topLevelBuild = build.getTopLevelBuild();

		if (_topLevelBuild == null) {
			throw new RuntimeException(
				"Please provide a build with a top level build");
		}
	}

	public Job getJob() {
		if (_job != null) {
			return _job;
		}

		_job = _topLevelBuild.getJob();

		return _job;
	}

	public PortalFixpackRelease getPortalFixpackRelease() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (!(topLevelBuild instanceof PortalFixpackReleaseBuild)) {
			return null;
		}

		PortalFixpackReleaseBuild portalFixpackReleaseBuild =
			(PortalFixpackReleaseBuild)topLevelBuild;

		return portalFixpackReleaseBuild.getPortalFixpackRelease();
	}

	public PortalHotfixRelease getPortalHotfixRelease() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (!(topLevelBuild instanceof PortalHotfixReleaseBuild)) {
			return null;
		}

		PortalHotfixReleaseBuild portalHotfixReleaseBuild =
			(PortalHotfixReleaseBuild)topLevelBuild;

		return portalHotfixReleaseBuild.getPortalHotfixRelease();
	}

	public PortalRelease getPortalRelease() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (!(topLevelBuild instanceof PortalReleaseBuild)) {
			return null;
		}

		PortalReleaseBuild portalReleaseBuild =
			(PortalReleaseBuild)topLevelBuild;

		return portalReleaseBuild.getPortalRelease();
	}

	public PullRequest getPullRequest() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (!(topLevelBuild instanceof PullRequestBuild)) {
			return null;
		}

		PullRequestBuild pullRequestBuild = (PullRequestBuild)topLevelBuild;

		return pullRequestBuild.getPullRequest();
	}

	public TestrayBuild getTestrayBuild() {
		if (_testrayBuild != null) {
			return _testrayBuild;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();
		TestrayBuild testrayBuild = null;

		try {
			String testrayBuildID = System.getProperty("TESTRAY_BUILD_ID");

			TestrayRoutine testrayRoutine = getTestrayRoutine();

			if ((testrayBuildID != null) && testrayBuildID.matches("\\d+")) {
				testrayBuild = testrayRoutine.getTestrayBuildByID(
					Integer.parseInt(testrayBuildID));
			}

			String testrayBuildName = System.getProperty("TESTRAY_BUILD_NAME");

			if ((testrayBuild == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildName)) {

				testrayBuild = testrayRoutine.createTestrayBuild(
					getTestrayProductVersion(),
					_replaceEnvVars(testrayBuildName));
			}

			testrayBuildID = _getBuildParameter("TESTRAY_BUILD_ID");

			if ((testrayBuild == null) && (testrayBuildID != null) &&
				testrayBuildID.matches("\\d+")) {

				testrayBuild = testrayRoutine.getTestrayBuildByID(
					Integer.parseInt(testrayBuildID));
			}

			testrayBuildName = _getBuildParameter("TESTRAY_BUILD_NAME");

			if ((testrayBuild == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildName)) {

				testrayBuild = testrayRoutine.createTestrayBuild(
					getTestrayProductVersion(),
					_replaceEnvVars(testrayBuildName));
			}

			Job job = getJob();

			testrayBuildID = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.build.id", job.getJobName(),
				_topLevelBuild.getTestSuiteName());

			if ((testrayBuild == null) && (testrayBuildID != null) &&
				testrayBuildID.matches("\\d+")) {

				testrayBuild = testrayRoutine.getTestrayBuildByID(
					Integer.parseInt(testrayBuildID));
			}

			testrayBuildName = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.build.name", job.getJobName(),
				_topLevelBuild.getTestSuiteName());

			if ((testrayBuild == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildName)) {

				testrayBuild = testrayRoutine.createTestrayBuild(
					getTestrayProductVersion(),
					_replaceEnvVars(testrayBuildName));
			}
		}
		finally {
			if (testrayBuild != null) {
				_testrayBuild = testrayBuild;

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Build ",
						String.valueOf(_testrayBuild.getURL()), " created in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));

				return _testrayBuild;
			}
		}

		return null;
	}

	public TestrayProductVersion getTestrayProductVersion() {
		if (_testrayProductVersion != null) {
			return _testrayProductVersion;
		}

		long start = System.currentTimeMillis();
		TestrayProductVersion testrayProductVersion = null;

		try {
			TestrayProject testrayProject = getTestrayProject();

			String testrayProductVersionID = System.getProperty(
				"TESTRAY_PRODUCT_VERSION_ID");

			if ((testrayProductVersionID != null) &&
				testrayProductVersionID.matches("\\d+")) {

				testrayProductVersion =
					testrayProject.getTestrayProductVersionByID(
						Integer.parseInt(testrayProductVersionID));
			}

			String testrayProductVersionName = System.getProperty(
				"TESTRAY_PRODUCT_VERSION_NAME");

			if ((testrayProductVersion == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(
					testrayProductVersionName)) {

				testrayProductVersion =
					testrayProject.createTestrayProductVersion(
						_replaceEnvVars(testrayProductVersionName));
			}

			testrayProductVersionID = _getBuildParameter(
				"TESTRAY_PRODUCT_VERSION_ID");

			if ((testrayProductVersion == null) &&
				(testrayProductVersionID != null) &&
				testrayProductVersionID.matches("\\d+")) {

				testrayProductVersion =
					testrayProject.getTestrayProductVersionByID(
						Integer.parseInt(testrayProductVersionID));
			}

			testrayProductVersionName = _getBuildParameter(
				"TESTRAY_PRODUCT_VERSION_NAME");

			if ((testrayProductVersion == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(
					testrayProductVersionName)) {

				testrayProductVersion =
					testrayProject.createTestrayProductVersion(
						_replaceEnvVars(testrayProductVersionName));
			}

			Job job = getJob();

			testrayProductVersionID = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.product.version.id",
				job.getJobName(), _topLevelBuild.getTestSuiteName());

			if ((testrayProductVersion == null) &&
				(testrayProductVersionID != null) &&
				testrayProductVersionID.matches("\\d+")) {

				testrayProductVersion =
					testrayProject.getTestrayProductVersionByID(
						Integer.parseInt(testrayProductVersionID));
			}

			testrayProductVersionName = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.product.version.name",
				job.getJobName(), _topLevelBuild.getTestSuiteName());

			if ((testrayProductVersion == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(
					testrayProductVersionName)) {

				testrayProductVersion =
					testrayProject.createTestrayProductVersion(
						_replaceEnvVars(testrayProductVersionName));
			}
		}
		finally {
			if (testrayProductVersion != null) {
				_testrayProductVersion = testrayProductVersion;

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Product Version ",
						String.valueOf(_testrayProductVersion.getURL()),
						" created in ",
						JenkinsResultsParserUtil.toDurationString(
							System.currentTimeMillis() - start)));

				return _testrayProductVersion;
			}
		}

		return null;
	}

	public TestrayProject getTestrayProject() {
		if (_testrayProject != null) {
			return _testrayProject;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();
		TestrayProject testrayProject = null;

		try {
			String testrayProjectID = System.getProperty("TESTRAY_PROJECT_ID");

			TestrayServer testrayServer = getTestrayServer();

			if ((testrayProjectID != null) &&
				testrayProjectID.matches("\\d+")) {

				testrayProject = testrayServer.getTestrayProjectByID(
					Integer.parseInt(testrayProjectID));
			}

			String testrayProjectName = System.getProperty(
				"TESTRAY_PROJECT_NAME");

			if ((testrayProject == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayProjectName)) {

				testrayProject = testrayServer.getTestrayProjectByName(
					_replaceEnvVars(testrayProjectName));
			}

			testrayProjectID = _getBuildParameter("TESTRAY_PROJECT_ID");

			if ((testrayProject == null) && (testrayProjectID != null) &&
				testrayProjectID.matches("\\d+")) {

				testrayProject = testrayServer.getTestrayProjectByID(
					Integer.parseInt(testrayProjectID));
			}

			testrayProjectName = _getBuildParameter("TESTRAY_PROJECT_NAME");

			if ((testrayProject == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayProjectName)) {

				testrayProject = testrayServer.getTestrayProjectByName(
					_replaceEnvVars(testrayProjectName));
			}

			Job job = getJob();

			testrayProjectID = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.project.id", job.getJobName(),
				_topLevelBuild.getTestSuiteName());

			if ((testrayProject == null) && (testrayProjectID != null) &&
				testrayProjectID.matches("\\d+")) {

				testrayProject = testrayServer.getTestrayProjectByID(
					Integer.parseInt(testrayProjectID));
			}

			testrayProjectName = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.project.name",
				job.getJobName(), _topLevelBuild.getTestSuiteName());

			if ((testrayProject == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayProjectName)) {

				testrayProject = testrayServer.getTestrayProjectByName(
					_replaceEnvVars(testrayProjectName));
			}
		}
		finally {
			if (testrayProject != null) {
				_testrayProject = testrayProject;

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Project ",
						String.valueOf(_testrayProject.getURL()),
						" created in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));

				return _testrayProject;
			}
		}

		return null;
	}

	public TestrayRoutine getTestrayRoutine() {
		if (_testrayRoutine != null) {
			return _testrayRoutine;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();
		TestrayRoutine testrayRoutine = null;

		try {
			String testrayRoutineID = System.getProperty("TESTRAY_ROUTINE_ID");

			TestrayProject testrayProject = getTestrayProject();

			if ((testrayRoutineID != null) &&
				testrayRoutineID.matches("\\d+")) {

				testrayRoutine = testrayProject.getTestrayRoutineByID(
					Integer.parseInt(testrayRoutineID));
			}

			String testrayRoutineName = System.getProperty(
				"TESTRAY_ROUTINE_NAME");

			if ((testrayRoutine == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {

				testrayRoutine = testrayProject.getTestrayRoutineByName(
					_replaceEnvVars(testrayRoutineName));
			}

			testrayRoutineID = _getBuildParameter("TESTRAY_ROUTINE_ID");

			if ((testrayRoutine == null) && (testrayRoutineID != null) &&
				testrayRoutineID.matches("\\d+")) {

				testrayRoutine = testrayProject.getTestrayRoutineByID(
					Integer.parseInt(testrayRoutineID));
			}

			testrayRoutineName = _getBuildParameter("TESTRAY_ROUTINE_NAME");

			if ((testrayRoutine == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {

				testrayRoutine = testrayProject.getTestrayRoutineByName(
					_replaceEnvVars(testrayRoutineName));
			}

			testrayRoutineName = _getBuildParameter("TESTRAY_BUILD_TYPE");

			if ((testrayRoutine == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {

				testrayRoutine = testrayProject.getTestrayRoutineByName(
					_replaceEnvVars(testrayRoutineName));
			}

			Job job = getJob();

			testrayRoutineID = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.routine.id", job.getJobName(),
				_topLevelBuild.getTestSuiteName());

			if ((testrayRoutine == null) && (testrayRoutineID != null) &&
				testrayRoutineID.matches("\\d+")) {

				testrayRoutine = testrayProject.getTestrayRoutineByID(
					Integer.parseInt(testrayRoutineID));
			}

			testrayRoutineName = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.routine.name",
				job.getJobName(), _topLevelBuild.getTestSuiteName());

			if ((testrayRoutine == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {

				testrayRoutine = testrayProject.getTestrayRoutineByName(
					_replaceEnvVars(testrayRoutineName));
			}
		}
		finally {
			if (testrayRoutine != null) {
				_testrayRoutine = testrayRoutine;

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Routine ",
						String.valueOf(_testrayRoutine.getURL()),
						" created in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));

				return _testrayRoutine;
			}
		}

		return null;
	}

	public TestrayServer getTestrayServer() {
		if (_testrayServer != null) {
			return _testrayServer;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();
		TestrayServer testrayServer = null;

		try {
			String testrayServerURL = System.getProperty("TESTRAY_SERVER_URL");

			if ((testrayServerURL != null) &&
				testrayServerURL.matches("https?://.*")) {

				testrayServer = new TestrayServer(testrayServerURL);
			}

			testrayServerURL = _getBuildParameter("TESTRAY_SERVER_URL");

			if ((testrayServer == null) && (testrayServerURL != null) &&
				testrayServerURL.matches("https?://.*")) {

				testrayServer = new TestrayServer(testrayServerURL);
			}

			Job job = getJob();

			testrayServerURL = JenkinsResultsParserUtil.getProperty(
				job.getJobProperties(), "testray.server.url", job.getJobName(),
				_topLevelBuild.getTestSuiteName());

			if ((testrayServer == null) && (testrayServerURL != null) &&
				testrayServerURL.matches("https?://.*")) {

				testrayServer = new TestrayServer(testrayServerURL);
			}
		}
		finally {
			if (testrayServer != null) {
				_testrayServer = testrayServer;

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Server ",
						String.valueOf(_testrayServer.getURL()), " created in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));

				return _testrayServer;
			}
		}

		return null;
	}

	public TopLevelBuild getTopLevelBuild() {
		return _topLevelBuild;
	}

	public void recordTestrayCases() {
		Job job = getJob();

		List<AxisTestClassGroup> axisTestClassGroups =
			job.getAxisTestClassGroups();

		if (job instanceof BatchDependentJob) {
			BatchDependentJob batchDependentJob = (BatchDependentJob)job;

			axisTestClassGroups.addAll(
				batchDependentJob.getDependentAxisTestClassGroups());
		}

		List<TestrayCase> testrayCases = new ArrayList<>();

		for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
			if (axisTestClassGroup instanceof CucumberAxisTestClassGroup ||
				axisTestClassGroup instanceof FunctionalAxisTestClassGroup ||
				axisTestClassGroup instanceof JUnitAxisTestClassGroup) {

				for (TestClassGroup.TestClass testClass :
						axisTestClassGroup.getTestClasses()) {

					testrayCases.add(
						TestrayCaseFactory.newTestrayCase(
							getTestrayBuild(), getTopLevelBuild(),
							axisTestClassGroup, testClass));
				}

				continue;
			}

			testrayCases.add(
				TestrayCaseFactory.newTestrayCase(
					getTestrayBuild(), getTopLevelBuild(), axisTestClassGroup,
					null));
		}

		for (TestrayCase testrayCase : testrayCases) {
			System.out.println(testrayCase);
		}
	}

	private String _getBuildParameter(String buildParameterName) {
		Map<String, String> buildParameters = new HashMap<>();

		Build controllerBuild = _topLevelBuild.getControllerBuild();

		if (controllerBuild != null) {
			buildParameters.putAll(controllerBuild.getParameters());
		}

		buildParameters.putAll(_topLevelBuild.getParameters());

		return buildParameters.get(buildParameterName);
	}

	private PortalGitWorkingDirectory _getPortalGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			_topLevelBuild.getBranchName());
	}

	private String _replaceEnvVars(String string) {
		string = _replaceEnvVarsControllerBuild(string);
		string = _replaceEnvVarsPluginsTopLevelBuild(string);
		string = _replaceEnvVarsPortalAppReleaseTopLevelBuild(string);
		string = _replaceEnvVarsPortalBranchInformationBuild(string);
		string = _replaceEnvVarsPortalRelease(string);
		string = _replaceEnvVarsPullRequestBuild(string);
		string = _replaceEnvVarsQAWebsitesTopLevelBuild(string);
		string = _replaceEnvVarsTopLevelBuild(string);

		return string;
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

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		string = string.replace(
			"$(portal.version)",
			portalGitWorkingDirectory.getMajorPortalVersion());

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

	private String _replaceEnvVarsPortalRelease(String string) {
		PortalRelease portalRelease = getPortalRelease();

		if (portalRelease != null) {
			String tomcatURL = String.valueOf(portalRelease.getTomcatURL());

			string = string.replace("$(portal.release.tomcat.url)", tomcatURL);
			string = string.replace(
				"$(portal.release.version)", portalRelease.getPortalVersion());

			Matcher matcher = _releaseArtifactURLPattern.matcher(tomcatURL);

			if (matcher.find()) {
				string = string.replace(
					"$(portal.release.tomcat.name)",
					matcher.group("releaseFileName"));
			}
		}

		PortalFixpackRelease portalFixpackRelease = getPortalFixpackRelease();

		if (portalFixpackRelease != null) {
			String portalFixpackURL = String.valueOf(
				portalFixpackRelease.getPortalFixpackURL());

			string = string.replace(
				"$(portal.fixpack.release.url)", portalFixpackURL);

			string = string.replace(
				"$(portal.fixpack.release.version)",
				portalFixpackRelease.getPortalFixpackVersion());

			Matcher matcher = _releaseArtifactURLPattern.matcher(
				portalFixpackURL);

			if (matcher.find()) {
				string = string.replace(
					"$(portal.fixpack.release.name)",
					matcher.group("releaseFileName"));
			}
		}

		PortalHotfixRelease portalHotfixRelease = getPortalHotfixRelease();

		if (portalHotfixRelease != null) {
			String portalHotfixURL = String.valueOf(
				portalHotfixRelease.getPortalHotfixReleaseURL());

			string = string.replace(
				"$(portal.hotfix.release.url)", portalHotfixURL);

			string = string.replace(
				"$(portal.hotfix.release.version)",
				portalHotfixRelease.getPortalHotfixReleaseVersion());

			Matcher matcher = _releaseArtifactURLPattern.matcher(
				portalHotfixURL);

			if (matcher.find()) {
				string = string.replace(
					"$(portal.hotfix.release.name)",
					matcher.group("releaseFileName"));
			}
		}

		return string;
	}

	private String _replaceEnvVarsPullRequestBuild(String string) {
		PullRequest pullRequest = getPullRequest();

		if (pullRequest == null) {
			return string;
		}

		string = string.replace(
			"$(pull.request.number)", pullRequest.getNumber());
		string = string.replace(
			"$(pull.request.url)", pullRequest.getHtmlURL());
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

	private static final Pattern _releaseArtifactURLPattern = Pattern.compile(
		"https?://.+/(?<releaseFileName>[^/]+)");

	private Job _job;
	private TestrayBuild _testrayBuild;
	private TestrayProductVersion _testrayProductVersion;
	private TestrayProject _testrayProject;
	private TestrayRoutine _testrayRoutine;
	private TestrayServer _testrayServer;
	private final TopLevelBuild _topLevelBuild;

}