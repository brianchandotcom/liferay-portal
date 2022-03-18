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

import com.liferay.jenkins.results.parser.AntException;
import com.liferay.jenkins.results.parser.AntUtil;
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.NotificationUtil;
import com.liferay.jenkins.results.parser.ParallelExecutor;
import com.liferay.jenkins.results.parser.PluginsBranchInformationBuild;
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
import com.liferay.jenkins.results.parser.QAWebsitesBranchInformationBuild;
import com.liferay.jenkins.results.parser.QAWebsitesGitRepositoryJob;
import com.liferay.jenkins.results.parser.QAWebsitesTopLevelBuild;
import com.liferay.jenkins.results.parser.Retryable;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.Workspace;
import com.liferay.jenkins.results.parser.WorkspaceBuild;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.job.property.JobPropertyFactory;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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

	public String getJenkinsBuildDescription() {
		Document document = DocumentHelper.createDocument();

		Element rootElement = document.addElement("div");

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Dom4JUtil.addToElement(
			rootElement,
			_getJenkinsBuildDescriptionElement(
				"Jenkins Build",
				JenkinsResultsParserUtil.combine(
					topLevelBuild.getJobName(), "#",
					String.valueOf(topLevelBuild.getBuildNumber())),
				topLevelBuild.getBuildURL()),
			_getJenkinsBuildDescriptionElement(
				"Jenkins Report", "jenkins-report.html",
				topLevelBuild.getJenkinsReportURL()),
			_getJenkinsBuildDescriptionElement(
				"Jenkins Suite", topLevelBuild.getTestSuiteName()));

		PullRequest pullRequest = getPullRequest();

		if (pullRequest != null) {
			Dom4JUtil.addToElement(
				rootElement,
				_getJenkinsBuildDescriptionElement(
					"Pull Request",
					JenkinsResultsParserUtil.combine(
						pullRequest.getReceiverUsername(), "#",
						pullRequest.getNumber()),
					pullRequest.getHtmlURL()));
		}

		Map<Integer, TestrayBuild> testrayBuildMap = new HashMap<>();

		for (TestrayBuild testrayBuild : _testrayBuilds.values()) {
			testrayBuildMap.put(testrayBuild.getID(), testrayBuild);
		}

		int i = 0;

		for (Map.Entry<Integer, TestrayBuild> testrayBuildEntry :
				testrayBuildMap.entrySet()) {

			String testrayBuildTitle = "Testray Build";

			if (i > 0) {
				testrayBuildTitle = JenkinsResultsParserUtil.combine(
					testrayBuildTitle, " (", String.valueOf(i), ")");
			}

			String testrayRoutineTitle = "Testray Routine";

			if (i > 0) {
				testrayRoutineTitle = JenkinsResultsParserUtil.combine(
					testrayRoutineTitle, " (", String.valueOf(i), ")");
			}

			TestrayBuild testrayBuild = testrayBuildEntry.getValue();

			TestrayRoutine testrayRoutine = testrayBuild.getTestrayRoutine();

			Dom4JUtil.addToElement(
				rootElement,
				_getJenkinsBuildDescriptionElement(
					testrayRoutineTitle, testrayRoutine.getName(),
					String.valueOf(testrayRoutine.getURL())),
				_getJenkinsBuildDescriptionElement(
					testrayBuildTitle, testrayBuild.getName(),
					String.valueOf(testrayBuild.getURL())));

			i++;
		}

		String currentJobName = System.getenv("JOB_NAME");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(currentJobName)) {
			Dom4JUtil.addToElement(
				rootElement,
				_getJenkinsBuildDescriptionElement(
					"Testray Importer",
					JenkinsResultsParserUtil.combine(
						currentJobName, "#", System.getenv("BUILD_NUMBER")),
					System.getenv("BUILD_URL")));
		}

		try {
			String buildDescription = Dom4JUtil.format(rootElement, false);

			return buildDescription.replaceAll("\n", "<br />");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
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

	public synchronized TestrayBuild getTestrayBuild(File testBaseDir) {
		TestrayBuild testrayBuild = _testrayBuilds.get(testBaseDir);

		if (testrayBuild != null) {
			return testrayBuild;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		Date testrayBuildDate = getTestrayBuildDate();
		String testrayBuildDescription = getTestrayBuildDescription();
		String testrayBuildSHA = getTestrayBuildSHA();

		try {
			String testrayBuildID = System.getenv("TESTRAY_BUILD_ID");

			TestrayRoutine testrayRoutine = getTestrayRoutine(testBaseDir);
			TestrayProductVersion testrayProductVersion =
				getTestrayProductVersion(testBaseDir);

			if ((testrayBuildID != null) && testrayBuildID.matches("\\d+")) {
				testrayBuild = testrayRoutine.getTestrayBuildByID(
					Integer.parseInt(testrayBuildID));
			}

			String testrayBuildName = System.getenv("TESTRAY_BUILD_NAME");

			if ((testrayBuild == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildName)) {

				testrayBuild = testrayRoutine.createTestrayBuild(
					testrayProductVersion, _replaceEnvVars(testrayBuildName),
					testrayBuildDate, testrayBuildDescription, testrayBuildSHA);
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
					testrayProductVersion, _replaceEnvVars(testrayBuildName),
					testrayBuildDate, testrayBuildDescription, testrayBuildSHA);
			}

			if (testrayBuild == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.build.id", testBaseDir);

				testrayBuildID = jobProperty.getValue();

				if ((testrayBuildID != null) &&
					testrayBuildID.matches("\\d+")) {

					testrayBuild = testrayRoutine.getTestrayBuildByID(
						Integer.parseInt(testrayBuildID));
				}
			}

			if (testrayBuild == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.build.name", testBaseDir);

				testrayBuildName = jobProperty.getValue();

				if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildName)) {
					testrayBuild = testrayRoutine.createTestrayBuild(
						testrayProductVersion,
						_replaceEnvVars(testrayBuildName), testrayBuildDate,
						testrayBuildDescription, testrayBuildSHA);
				}
			}
		}
		finally {
			if (testrayBuild != null) {
				_testrayBuilds.put(testBaseDir, testrayBuild);

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Build ", String.valueOf(testrayBuild.getURL()),
						" created in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));

				return testrayBuild;
			}
		}

		throw new RuntimeException("Please set TESTRAY_BUILD_NAME");
	}

	public Date getTestrayBuildDate() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Build controllerBuild = topLevelBuild.getControllerBuild();

		if (controllerBuild != null) {
			return new Date(controllerBuild.getStartTime());
		}

		return new Date(topLevelBuild.getStartTime());
	}

	public String getTestrayBuildDescription() {
		StringBuilder sb = new StringBuilder();

		PortalRelease portalRelease = getPortalRelease();

		if (portalRelease != null) {
			sb.append("Portal Release: ");
			sb.append(portalRelease.getPortalVersion());
			sb.append(";\n");
		}

		PortalFixpackRelease portalFixpackRelease = getPortalFixpackRelease();

		if (portalFixpackRelease != null) {
			sb.append("Portal Fixpack: ");
			sb.append(portalFixpackRelease.getPortalFixpackVersion());
			sb.append(";\n");
		}

		PortalHotfixRelease portalHotfixRelease = getPortalHotfixRelease();

		if (portalHotfixRelease != null) {
			sb.append("Portal Hotfix: ");
			sb.append(portalHotfixRelease.getPortalHotfixReleaseVersion());
			sb.append(";\n");
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild instanceof PortalBranchInformationBuild) {
			PortalBranchInformationBuild portalBranchInformationBuild =
				(PortalBranchInformationBuild)topLevelBuild;

			Build.BranchInformation portalBranchInformation =
				portalBranchInformationBuild.getPortalBranchInformation();

			if (portalBranchInformation != null) {
				sb.append("Portal Branch: ");
				sb.append(portalBranchInformation.getUpstreamBranchName());
				sb.append(";\n");

				sb.append("Portal SHA: ");
				sb.append(portalBranchInformation.getSenderBranchSHA());
				sb.append(";\n");
			}
		}

		if (topLevelBuild instanceof PluginsBranchInformationBuild) {
			PluginsBranchInformationBuild pluginsBranchInformationBuild =
				(PluginsBranchInformationBuild)topLevelBuild;

			Build.BranchInformation pluginsBranchInformation =
				pluginsBranchInformationBuild.getPluginsBranchInformation();

			if (pluginsBranchInformation != null) {
				sb.append("Plugins Branch: ");
				sb.append(pluginsBranchInformation.getUpstreamBranchName());
				sb.append(";\n");

				sb.append("Plugins SHA: ");
				sb.append(pluginsBranchInformation.getSenderBranchSHA());
				sb.append(";\n");
			}
		}

		if (topLevelBuild instanceof QAWebsitesBranchInformationBuild) {
			QAWebsitesBranchInformationBuild qaWebsitesBranchInformationBuild =
				(QAWebsitesBranchInformationBuild)topLevelBuild;

			Build.BranchInformation qaWebsitesBranchInformation =
				qaWebsitesBranchInformationBuild.
					getQAWebsitesBranchInformation();

			if (qaWebsitesBranchInformation != null) {
				sb.append("QA Websites Branch: ");
				sb.append(qaWebsitesBranchInformation.getUpstreamBranchName());
				sb.append(";\n");

				sb.append("QA Websites SHA: ");
				sb.append(qaWebsitesBranchInformation.getSenderBranchSHA());
				sb.append(";\n");
			}
		}

		return sb.toString();
	}

	public String getTestrayBuildSHA() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild instanceof PortalBranchInformationBuild) {
			PortalBranchInformationBuild portalBranchInformationBuild =
				(PortalBranchInformationBuild)topLevelBuild;

			Build.BranchInformation portalBranchInformation =
				portalBranchInformationBuild.getPortalBranchInformation();

			return portalBranchInformation.getSenderBranchSHA();
		}

		if (topLevelBuild instanceof PluginsBranchInformationBuild) {
			PluginsBranchInformationBuild pluginsBranchInformationBuild =
				(PluginsBranchInformationBuild)topLevelBuild;

			Build.BranchInformation pluginsBranchInformation =
				pluginsBranchInformationBuild.getPluginsBranchInformation();

			return pluginsBranchInformation.getSenderBranchSHA();
		}

		if (topLevelBuild instanceof QAWebsitesBranchInformationBuild) {
			QAWebsitesBranchInformationBuild qaWebsitesBranchInformationBuild =
				(QAWebsitesBranchInformationBuild)topLevelBuild;

			Build.BranchInformation qaWebsitesBranchInformation =
				qaWebsitesBranchInformationBuild.
					getQAWebsitesBranchInformation();

			return qaWebsitesBranchInformation.getSenderBranchSHA();
		}

		return null;
	}

	public synchronized TestrayProductVersion getTestrayProductVersion(
		File testBaseDir) {

		TestrayProductVersion testrayProductVersion =
			_testrayProductVersions.get(testBaseDir);

		if (testrayProductVersion != null) {
			return testrayProductVersion;
		}

		long start = System.currentTimeMillis();

		try {
			TestrayProject testrayProject = getTestrayProject(testBaseDir);

			String testrayProductVersionID = System.getenv(
				"TESTRAY_PRODUCT_VERSION_ID");

			if ((testrayProductVersionID != null) &&
				testrayProductVersionID.matches("\\d+")) {

				testrayProductVersion =
					testrayProject.getTestrayProductVersionByID(
						Integer.parseInt(testrayProductVersionID));
			}

			String testrayProductVersionName = System.getenv(
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

			if (testrayProductVersion == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.product.version.id", testBaseDir);

				testrayProductVersionID = jobProperty.getValue();

				if ((testrayProductVersionID != null) &&
					testrayProductVersionID.matches("\\d+")) {

					testrayProductVersion =
						testrayProject.getTestrayProductVersionByID(
							Integer.parseInt(testrayProductVersionID));
				}
			}

			if (testrayProductVersion == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.product.version.name", testBaseDir);

				testrayProductVersionName = jobProperty.getValue();

				if (!JenkinsResultsParserUtil.isNullOrEmpty(
						testrayProductVersionName)) {

					testrayProductVersion =
						testrayProject.createTestrayProductVersion(
							_replaceEnvVars(testrayProductVersionName));
				}
			}

			Job job = getJob();

			String jobName = job.getJobName();

			if ((testrayProductVersion == null) &&
				(jobName.equals("test-qa-websites-functional-daily") ||
				 jobName.equals("test-qa-websites-functional-weekly"))) {

				testrayProductVersion =
					testrayProject.createTestrayProductVersion("1.x");
			}
		}
		finally {
			if (testrayProductVersion != null) {
				_testrayProductVersions.put(testBaseDir, testrayProductVersion);

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Product Version ",
						String.valueOf(testrayProductVersion.getURL()),
						" created in ",
						JenkinsResultsParserUtil.toDurationString(
							System.currentTimeMillis() - start)));

				return testrayProductVersion;
			}
		}

		return null;
	}

	public synchronized TestrayProject getTestrayProject(File testBaseDir) {
		TestrayProject testrayProject = _testrayProjects.get(testBaseDir);

		if (testrayProject != null) {
			return testrayProject;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		try {
			String testrayProjectID = System.getenv("TESTRAY_PROJECT_ID");

			TestrayServer testrayServer = getTestrayServer(testBaseDir);

			if ((testrayProjectID != null) &&
				testrayProjectID.matches("\\d+")) {

				testrayProject = testrayServer.getTestrayProjectByID(
					Integer.parseInt(testrayProjectID));
			}

			String testrayProjectName = System.getenv("TESTRAY_PROJECT_NAME");

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

			if (testrayProject == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.project.id", testBaseDir);

				testrayProjectID = jobProperty.getValue();

				if ((testrayProjectID != null) &&
					testrayProjectID.matches("\\d+")) {

					testrayProject = testrayServer.getTestrayProjectByID(
						Integer.parseInt(testrayProjectID));
				}
			}

			if (testrayProject == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.project.name", testBaseDir);

				testrayProjectName = jobProperty.getValue();

				if (!JenkinsResultsParserUtil.isNullOrEmpty(
						testrayProjectName)) {

					testrayProject = testrayServer.getTestrayProjectByName(
						_replaceEnvVars(testrayProjectName));
				}
			}
		}
		finally {
			if (testrayProject != null) {
				_testrayProjects.put(testBaseDir, testrayProject);

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Project ",
						String.valueOf(testrayProject.getURL()), " created in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));

				return testrayProject;
			}
		}

		throw new RuntimeException("Please set TESTRAY_PROJECT_NAME");
	}

	public synchronized TestrayRoutine getTestrayRoutine(File testBaseDir) {
		TestrayRoutine testrayRoutine = _testrayRoutines.get(testBaseDir);

		if (testrayRoutine != null) {
			return testrayRoutine;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		try {
			String testrayRoutineID = System.getenv("TESTRAY_ROUTINE_ID");

			TestrayProject testrayProject = getTestrayProject(testBaseDir);

			if ((testrayRoutineID != null) &&
				testrayRoutineID.matches("\\d+")) {

				testrayRoutine = testrayProject.getTestrayRoutineByID(
					Integer.parseInt(testrayRoutineID));
			}

			String testrayRoutineName = System.getenv("TESTRAY_ROUTINE_NAME");

			if ((testrayRoutine == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {

				testrayRoutine = testrayProject.createTestrayRoutine(
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

				testrayRoutine = testrayProject.createTestrayRoutine(
					_replaceEnvVars(testrayRoutineName));
			}

			testrayRoutineName = _getBuildParameter("TESTRAY_BUILD_TYPE");

			if ((testrayRoutine == null) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {

				testrayRoutine = testrayProject.createTestrayRoutine(
					_replaceEnvVars(testrayRoutineName));
			}

			if (testrayRoutine == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.routine.id", testBaseDir);

				testrayRoutineID = jobProperty.getValue();

				if ((testrayRoutineID != null) &&
					testrayRoutineID.matches("\\d+")) {

					testrayRoutine = testrayProject.getTestrayRoutineByID(
						Integer.parseInt(testrayRoutineID));
				}
			}

			if (testrayRoutine == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.routine.name", testBaseDir);

				testrayRoutineName = jobProperty.getValue();

				if (!JenkinsResultsParserUtil.isNullOrEmpty(
						testrayRoutineName)) {

					testrayRoutine = testrayProject.createTestrayRoutine(
						_replaceEnvVars(testrayRoutineName));
				}
			}
		}
		finally {
			if (testrayRoutine != null) {
				_testrayRoutines.put(testBaseDir, testrayRoutine);

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Routine ",
						String.valueOf(testrayRoutine.getURL()), " created in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));

				return testrayRoutine;
			}
		}

		throw new RuntimeException("Please set TESTRAY_ROUTINE_NAME");
	}

	public synchronized TestrayServer getTestrayServer(File testBaseDir) {
		TestrayServer testrayServer = _testrayServers.get(testBaseDir);

		if (testrayServer != null) {
			return testrayServer;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		try {
			String testrayServerURL = System.getenv("TESTRAY_SERVER_URL");

			if ((testrayServerURL != null) &&
				testrayServerURL.matches("https?://.*")) {

				testrayServer = TestrayFactory.newTestrayServer(
					testrayServerURL);
			}

			testrayServerURL = _getBuildParameter("TESTRAY_SERVER_URL");

			if ((testrayServer == null) && (testrayServerURL != null) &&
				testrayServerURL.matches("https?://.*")) {

				testrayServer = TestrayFactory.newTestrayServer(
					testrayServerURL);
			}

			if (testrayServer == null) {
				JobProperty jobProperty = _getJobProperty(
					"testray.server.url", testBaseDir);

				testrayServerURL = jobProperty.getValue();

				if ((testrayServerURL != null) &&
					testrayServerURL.matches("https?://.*")) {

					testrayServer = TestrayFactory.newTestrayServer(
						testrayServerURL);
				}
			}
		}
		finally {
			if (testrayServer != null) {
				_testrayServers.put(testBaseDir, testrayServer);

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Testray Server ",
						String.valueOf(testrayServer.getURL()), " created in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));

				return testrayServer;
			}
		}

		throw new RuntimeException("Please set TESTRAY_SERVER_URL");
	}

	public TopLevelBuild getTopLevelBuild() {
		return _topLevelBuild;
	}

	public void postSlackNotification() {
		List<Integer> testrayBuildIDs = new ArrayList<>();

		for (Map.Entry<File, TestrayBuild> testrayBuildEntry :
				_testrayBuilds.entrySet()) {

			File testBaseDir = testrayBuildEntry.getKey();

			TestrayBuild testrayBuild = testrayBuildEntry.getValue();

			if (testrayBuildIDs.contains(testrayBuild.getID())) {
				continue;
			}

			testrayBuildIDs.add(testrayBuild.getID());

			String slackChannels = _getSlackChannels(testBaseDir);

			if (JenkinsResultsParserUtil.isNullOrEmpty(slackChannels)) {
				continue;
			}

			for (String slackChannel : slackChannels.split(",")) {
				NotificationUtil.sendSlackNotification(
					_getSlackBody(testBaseDir), slackChannel,
					_getSlackIconEmoji(testBaseDir),
					_getSlackSubject(testBaseDir),
					_getSlackUsername(testBaseDir));
			}
		}
	}

	public void recordTestrayCaseResults() {
		final Job job = getJob();

		List<AxisTestClassGroup> axisTestClassGroups = new ArrayList<>(
			job.getAxisTestClassGroups());

		axisTestClassGroups.addAll(job.getDependentAxisTestClassGroups());

		List<Callable<Void>> callables = new ArrayList<>();

		for (final AxisTestClassGroup axisTestClassGroup :
				axisTestClassGroups) {

			callables.add(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						TestrayBuild testrayBuild = getTestrayBuild(
							axisTestClassGroup.getTestBaseDir());

						TestrayRun testrayRun = new TestrayRun(
							testrayBuild, axisTestClassGroup.getBatchName(),
							job.getJobPropertiesFiles());

						long start =
							JenkinsResultsParserUtil.getCurrentTimeMillis();

						Document document = DocumentHelper.createDocument();

						Element rootElement = document.addElement("testsuite");

						Element environmentsElement = rootElement.addElement(
							"environments");

						for (TestrayRun.Factor factor :
								testrayRun.getFactors()) {

							Element environmentElement =
								environmentsElement.addElement("environment");

							environmentElement.addAttribute(
								"type", factor.getName());
							environmentElement.addAttribute(
								"option", factor.getValue());
						}

						Map<String, String> propertiesMap = new HashMap<>();

						propertiesMap.put(
							"testray.build.name", testrayBuild.getName());

						TestrayRoutine testrayRoutine =
							testrayBuild.getTestrayRoutine();

						propertiesMap.put(
							"testray.build.type", testrayRoutine.getName());

						TestrayProductVersion testrayProductVersion =
							testrayBuild.getTestrayProductVersion();

						if (testrayProductVersion != null) {
							propertiesMap.put(
								"testray.product.version",
								testrayProductVersion.getName());
						}

						TestrayProject testrayProject =
							testrayBuild.getTestrayProject();

						propertiesMap.put(
							"testray.project.name", testrayProject.getName());

						propertiesMap.put(
							"testray.run.id", testrayRun.getRunIDString());

						_addPropertyElements(
							rootElement.addElement("properties"),
							propertiesMap);

						List<TestrayCaseResult> testrayCaseResults =
							new ArrayList<>();

						if (axisTestClassGroup instanceof
								FunctionalAxisTestClassGroup ||
							axisTestClassGroup instanceof
								JUnitAxisTestClassGroup) {

							for (TestClass testClass :
									axisTestClassGroup.getTestClasses()) {

								testrayCaseResults.add(
									TestrayFactory.newTestrayCaseResult(
										testrayBuild, getTopLevelBuild(),
										axisTestClassGroup, testClass));
							}
						}
						else {
							testrayCaseResults.add(
								TestrayFactory.newTestrayCaseResult(
									testrayBuild, getTopLevelBuild(),
									axisTestClassGroup, null));
						}

						for (TestrayCaseResult testrayCaseResult :
								testrayCaseResults) {

							Element testcaseElement = rootElement.addElement(
								"testcase");

							Map<String, String> testcasePropertiesMap =
								new HashMap<>();

							testcasePropertiesMap.put(
								"testray.case.type.name",
								testrayCaseResult.getType());
							testcasePropertiesMap.put(
								"testray.component.names",
								testrayCaseResult.getSubcomponentNames());
							testcasePropertiesMap.put(
								"testray.main.component.name",
								testrayCaseResult.getComponentName());
							testcasePropertiesMap.put(
								"testray.team.name",
								testrayCaseResult.getTeamName());

							String testrayCaseName =
								testrayCaseResult.getName();

							if (testrayCaseName.length() > 150) {
								testrayCaseName = testrayCaseName.substring(
									0, 150);
							}

							testcasePropertiesMap.put(
								"testray.testcase.name", testrayCaseName);

							testcasePropertiesMap.put(
								"testray.testcase.priority",
								String.valueOf(
									testrayCaseResult.getPriority()));

							TestrayCaseResult.Status testrayCaseStatus =
								testrayCaseResult.getStatus();

							testcasePropertiesMap.put(
								"testray.testcase.status",
								testrayCaseStatus.getName());

							Element propertiesElement =
								testcaseElement.addElement("properties");

							_addPropertyElements(
								propertiesElement, testcasePropertiesMap);

							String[] warnings = testrayCaseResult.getWarnings();

							if ((warnings != null) && (warnings.length > 0)) {
								Element warningsPropertyElement =
									propertiesElement.addElement("property");

								warningsPropertyElement.addAttribute(
									"name", "testray.testcase.warnings");

								for (String warning : warnings) {
									Element warningPropertyElement =
										warningsPropertyElement.addElement(
											"value");

									warningPropertyElement.addText(
										StringEscapeUtils.escapeHtml(warning));
								}
							}

							Element attachmentsElement =
								testcaseElement.addElement("attachments");

							for (TestrayAttachment testrayAttachment :
									testrayCaseResult.getTestrayAttachments()) {

								Element attachmentFileElement =
									attachmentsElement.addElement("file");

								attachmentFileElement.addAttribute(
									"name", testrayAttachment.getName());
								attachmentFileElement.addAttribute(
									"url",
									testrayAttachment.getURL() + "?authuser=0");
								attachmentFileElement.addAttribute(
									"value",
									testrayAttachment.getKey() + "?authuser=0");
							}

							String errors = testrayCaseResult.getErrors();

							if (!JenkinsResultsParserUtil.isNullOrEmpty(
									errors)) {

								Element failureElement =
									testcaseElement.addElement("failure");

								failureElement.addAttribute("message", errors);
							}
						}

						TestrayServer testrayServer =
							testrayBuild.getTestrayServer();

						TopLevelBuild topLevelBuild = getTopLevelBuild();

						JenkinsMaster jenkinsMaster =
							topLevelBuild.getJenkinsMaster();

						try {
							String axisName = axisTestClassGroup.getAxisName();

							testrayServer.writeCaseResult(
								JenkinsResultsParserUtil.combine(
									"TESTS-", jenkinsMaster.getName(), "_",
									topLevelBuild.getJobName(), "_",
									String.valueOf(
										topLevelBuild.getBuildNumber()),
									"_", axisName.replace("/", "_"), ".xml"),
								Dom4JUtil.format(rootElement));
						}
						catch (IOException ioException) {
							throw new RuntimeException(ioException);
						}

						long currentTimeMillis =
							JenkinsResultsParserUtil.getCurrentTimeMillis();

						System.out.println(
							JenkinsResultsParserUtil.combine(
								"Recorded ",
								String.valueOf(testrayCaseResults.size()),
								" case results for ",
								axisTestClassGroup.getAxisName(), " in ",
								JenkinsResultsParserUtil.toDurationString(
									currentTimeMillis - start)));

						return null;
					}

				});
		}

		ParallelExecutor<Void> parallelExecutor = new ParallelExecutor<>(
			callables, _executorService);

		parallelExecutor.execute();

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		List<Integer> testrayBuildIDs = new ArrayList<>();

		for (TestrayBuild testrayBuild : _testrayBuilds.values()) {
			if (testrayBuildIDs.contains(testrayBuild.getID())) {
				continue;
			}

			testrayBuildIDs.add(testrayBuild.getID());

			TestrayServer testrayServer = testrayBuild.getTestrayServer();

			testrayServer.importCaseResults(topLevelBuild);
		}

		_sendPullRequestNotification();
	}

	public void setup() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild instanceof WorkspaceBuild) {
			WorkspaceBuild workspaceBuild = (WorkspaceBuild)topLevelBuild;

			Workspace workspace = workspaceBuild.getWorkspace();

			workspace.setUp();

			_setupPortalBundle();

			return;
		}

		_checkoutPortalBranch();

		_setupProfileDXP();

		_setupPortalBundle();

		_callPrepareTCK();

		_checkoutPluginsBranch();
		_checkoutQAWebsitesBranch();
	}

	private void _addPropertyElements(
		Element propertiesElement, Map<String, String> propertiesMap) {

		for (Map.Entry<String, String> propertyEntry :
				propertiesMap.entrySet()) {

			Element propertyElement = propertiesElement.addElement("property");

			String propertyName = propertyEntry.getKey();
			String propertyValue = propertyEntry.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(propertyName) ||
				JenkinsResultsParserUtil.isNullOrEmpty(propertyValue)) {

				continue;
			}

			propertyElement.addAttribute("name", propertyName);
			propertyElement.addAttribute("value", propertyValue);
		}
	}

	private void _callPrepareTCK() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		Map<String, String> parameters = new HashMap<>();

		String portalUpstreamBranchName =
			portalGitWorkingDirectory.getUpstreamBranchName();

		if (!portalUpstreamBranchName.contains("ee-")) {
			GitWorkingDirectory jenkinsGitWorkingDirectory =
				_getJenkinsGitWorkingDirectory();

			Properties testProperties = JenkinsResultsParserUtil.getProperties(
				new File(
					jenkinsGitWorkingDirectory.getWorkingDirectory(),
					"commands/dependencies/test.properties"));

			parameters.put(
				"tck.home",
				JenkinsResultsParserUtil.getProperty(
					testProperties, "tck.home"));
		}

		try {
			AntUtil.callTarget(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"build-test-tck.xml", "prepare-tck", parameters);
		}
		catch (AntException antException) {
			throw new RuntimeException(antException);
		}
	}

	private void _checkoutPluginsBranch() {
		if (!(_topLevelBuild instanceof PluginsBranchInformationBuild)) {
			return;
		}

		PluginsBranchInformationBuild pluginsBranchInformationBuild =
			(PluginsBranchInformationBuild)_topLevelBuild;

		Build.BranchInformation branchInformation =
			pluginsBranchInformationBuild.getPluginsBranchInformation();

		if (branchInformation == null) {
			return;
		}

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String upstreamBranchName = branchInformation.getUpstreamBranchName();

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			buildProperties, "plugins.dir", upstreamBranchName);
		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			buildProperties, "plugins.repository", upstreamBranchName);

		GitWorkingDirectory pluginsGitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, upstreamDirPath, upstreamRepository);

		pluginsGitWorkingDirectory.clean();

		pluginsGitWorkingDirectory.checkoutLocalGitBranch(branchInformation);

		pluginsGitWorkingDirectory.displayLog();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		File releasePropertiesFile = new File(
			portalGitWorkingDirectory.getWorkingDirectory(),
			JenkinsResultsParserUtil.combine(
				"release.", System.getenv("HOSTNAME"), ".properties"));

		try {
			JenkinsResultsParserUtil.write(
				releasePropertiesFile,
				JenkinsResultsParserUtil.combine(
					"lp.plugins.dir=",
					JenkinsResultsParserUtil.getCanonicalPath(
						pluginsGitWorkingDirectory.getWorkingDirectory())));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _checkoutPortalBranch() {
		if (!(_topLevelBuild instanceof PortalBranchInformationBuild)) {
			return;
		}

		PortalBranchInformationBuild portalBranchInformationBuild =
			(PortalBranchInformationBuild)_topLevelBuild;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		portalGitWorkingDirectory.clean();

		portalGitWorkingDirectory.checkoutLocalGitBranch(
			portalBranchInformationBuild.getPortalBranchInformation());

		portalGitWorkingDirectory.displayLog();

		try {
			JenkinsResultsParserUtil.write(
				new File(
					portalGitWorkingDirectory.getWorkingDirectory(),
					JenkinsResultsParserUtil.combine(
						"app.server.", System.getenv("HOSTNAME"),
						".properties")),
				JenkinsResultsParserUtil.combine(
					"app.server.parent.dir=",
					JenkinsResultsParserUtil.getCanonicalPath(
						portalGitWorkingDirectory.getWorkingDirectory()),
					"/bundles"));

			JenkinsResultsParserUtil.write(
				new File(
					portalGitWorkingDirectory.getWorkingDirectory(),
					JenkinsResultsParserUtil.combine(
						"build.", System.getenv("HOSTNAME"), ".properties")),
				JenkinsResultsParserUtil.combine(
					"liferay.home=",
					JenkinsResultsParserUtil.getCanonicalPath(
						portalGitWorkingDirectory.getWorkingDirectory()),
					"/bundles"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _checkoutQAWebsitesBranch() {
		if (!(_topLevelBuild instanceof QAWebsitesBranchInformationBuild)) {
			return;
		}

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		QAWebsitesBranchInformationBuild qaWebsitesBranchInformationBuild =
			(QAWebsitesBranchInformationBuild)_topLevelBuild;

		Build.BranchInformation branchInformation =
			qaWebsitesBranchInformationBuild.getQAWebsitesBranchInformation();

		String upstreamBranchName = branchInformation.getUpstreamBranchName();

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			buildProperties, "qa.websites.dir", upstreamBranchName);
		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			buildProperties, "qa.websites.repository", upstreamBranchName);

		GitWorkingDirectory qaWebsitesGitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, upstreamDirPath, upstreamRepository);

		qaWebsitesGitWorkingDirectory.clean();

		qaWebsitesGitWorkingDirectory.checkoutLocalGitBranch(
			qaWebsitesBranchInformationBuild.getQAWebsitesBranchInformation());

		qaWebsitesGitWorkingDirectory.displayLog();
	}

	private String _fixSlackString(String string) {
		string = string.replace("*", "&#42;");
		string = string.replace(">", "&gt;");
		string = string.replace("<", "&lt;");

		return string.replace("|", "&vert;");
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

	private Element _getJenkinsBuildDescriptionElement(
		String title, String name) {

		return _getJenkinsBuildDescriptionElement(title, name, null);
	}

	private Element _getJenkinsBuildDescriptionElement(
		String title, String name, String url) {

		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("div");

		Element titleElement = element.addElement("strong");

		titleElement.addText(title + ":");

		Element spaceElement = element.addElement("span");

		spaceElement.addText(" ");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(url)) {
			Element linkElement = element.addElement("a");

			linkElement.addAttribute("href", url);
			linkElement.addText(name);
		}
		else {
			element.addText(name);
		}

		element.addElement("br");

		return element;
	}

	private GitWorkingDirectory _getJenkinsGitWorkingDirectory() {
		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String upstreamBranchName = "master";

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			buildProperties, "jenkins.dir", upstreamBranchName);

		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			buildProperties, "jenkins.repository", upstreamBranchName);

		return GitWorkingDirectoryFactory.newGitWorkingDirectory(
			upstreamBranchName, upstreamDirPath, upstreamRepository);
	}

	private JobProperty _getJobProperty(
		String basePropertyName, File testBaseDir) {

		Job job = getJob();

		if (job instanceof QAWebsitesGitRepositoryJob) {
			JobProperty jobProperty = JobPropertyFactory.newJobProperty(
				basePropertyName, job, testBaseDir,
				JobProperty.Type.QA_WEBSITES_TEST_DIR);

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					jobProperty.getValue())) {

				return jobProperty;
			}
		}

		return JobPropertyFactory.newJobProperty(basePropertyName, job);
	}

	private PortalGitWorkingDirectory _getPortalGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			_topLevelBuild.getBranchName());
	}

	private String _getSlackBody(File testBaseDir) {
		JobProperty jobProperty = _getJobProperty(
			"testray.slack.body", testBaseDir);

		String slackBody = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(slackBody)) {
			StringBuilder sb = new StringBuilder();

			sb.append("*Jenkins Testray Importer:* ");
			sb.append("<$(testray.importer.build.url)|");
			sb.append("$(testray.importer.job.name)#");
			sb.append("$(testray.importer.build.number)>\n");

			sb.append("*Testray Build:* ");
			sb.append("<$(testray.build.url)|$(testray.build.name)>");

			slackBody = sb.toString();
		}

		return _replaceSlackEnvVars(slackBody, testBaseDir);
	}

	private String _getSlackChannels(File testBaseDir) {
		JobProperty jobProperty = _getJobProperty(
			"testray.slack.channels", testBaseDir);

		String slackChannels = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(slackChannels)) {
			slackChannels = "testray-reports";
		}

		return _replaceSlackEnvVars(slackChannels, testBaseDir);
	}

	private String _getSlackIconEmoji(File testBaseDir) {
		JobProperty jobProperty = _getJobProperty(
			"testray.slack.icon.emoji", testBaseDir);

		String slackIconEmoji = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(slackIconEmoji)) {
			return _replaceSlackEnvVars(slackIconEmoji, testBaseDir);
		}

		return ":liferay-ci:";
	}

	private String _getSlackSubject(File testBaseDir) {
		JobProperty jobProperty = _getJobProperty(
			"testray.slack.subject", testBaseDir);

		String slackSubject = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(slackSubject)) {
			return _replaceSlackEnvVars(slackSubject, testBaseDir);
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return JenkinsResultsParserUtil.combine(
			topLevelBuild.getJobName(), "#",
			String.valueOf(topLevelBuild.getBuildNumber()));
	}

	private String _getSlackUsername(File testBaseDir) {
		JobProperty jobProperty = _getJobProperty(
			"testray.slack.username", testBaseDir);

		String slackUsername = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(slackUsername)) {
			return _replaceSlackEnvVars(slackUsername, testBaseDir);
		}

		return "Liferay CI";
	}

	private String _replaceEnvVars(String string) {
		string = _replaceEnvVarsControllerBuild(string);
		string = _replaceEnvVarsPluginsBranchInformationBuild(string);
		string = _replaceEnvVarsPluginsTopLevelBuild(string);
		string = _replaceEnvVarsPortalAppReleaseTopLevelBuild(string);
		string = _replaceEnvVarsPortalBranchInformationBuild(string);
		string = _replaceEnvVarsPortalRelease(string);
		string = _replaceEnvVarsPullRequestBuild(string);
		string = _replaceEnvVarsQAWebsitesTopLevelBuild(string);
		string = _replaceEnvVarsTopLevelBuild(string);

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		String jobName = topLevelBuild.getJobName();

		if (jobName.contains("subrepository")) {
			string = _replaceEnvVarsSubrepository(string);
		}

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

	private String _replaceEnvVarsPluginsBranchInformationBuild(String string) {
		if (!(_topLevelBuild instanceof PluginsBranchInformationBuild)) {
			return string;
		}

		PluginsBranchInformationBuild pluginsBranchInformationBuild =
			(PluginsBranchInformationBuild)_topLevelBuild;

		Build.BranchInformation pluginsBranchInformation =
			pluginsBranchInformationBuild.getPluginsBranchInformation();

		if (pluginsBranchInformation == null) {
			return string;
		}

		string = string.replace(
			"$(plugins.branch.name)",
			pluginsBranchInformation.getUpstreamBranchName());
		string = string.replace(
			"$(plugins.custom.branch.name)",
			pluginsBranchInformation.getSenderBranchName());
		string = string.replace(
			"$(plugins.custom.branch.username)",
			pluginsBranchInformation.getSenderUsername());
		string = string.replace(
			"$(plugins.repository)",
			pluginsBranchInformation.getRepositoryName());

		return string.replace(
			"$(plugins.sha)", pluginsBranchInformation.getSenderBranchSHA());
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

		if (buildProfile == Job.BuildProfile.PORTAL) {
			string = string.replace("$(portal.type)", "CE");
		}
		else {
			string = string.replace("$(portal.type)", "EE");
		}

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
					matcher.group("releaseName"));
			}

			String portalReleaseBuildVersion = _topLevelBuild.getParameterValue(
				"TEST_PORTAL_RELEASE_VERSION");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					portalReleaseBuildVersion)) {

				string = string.replace(
					"$(portal.release.build.version)",
					portalReleaseBuildVersion);
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
					matcher.group("releaseName"));
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
					matcher.group("releaseName"));
			}
		}

		StringBuilder sb = new StringBuilder();

		if (portalRelease == null) {
			PortalGitWorkingDirectory portalGitWorkingDirectory =
				_getPortalGitWorkingDirectory();

			sb.append(portalGitWorkingDirectory.getMajorPortalVersion());

			sb.append(".x");
		}
		else {
			sb.append(portalRelease.getPortalVersion());

			if (portalFixpackRelease != null) {
				sb.append(" FP");
				sb.append(portalFixpackRelease.getPortalFixpackVersion());
			}

			if (portalHotfixRelease != null) {
				sb.append(" HF");
				sb.append(portalHotfixRelease.getPortalHotfixReleaseVersion());
			}
		}

		return string.replace("$(portal.release.name)", sb.toString());
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

	private String _replaceEnvVarsSubrepository(String string) {
		string = string.replace(
			"$(github.upstream.branch.name)",
			_topLevelBuild.getParameterValue("GITHUB_UPSTREAM_BRANCH_NAME"));

		return string.replace(
			"$(repository.name)",
			_topLevelBuild.getParameterValue("REPOSITORY_NAME"));
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

	private String _replaceSlackEnvVars(String string, File testBaseDir) {
		string = _replaceEnvVars(string);

		string = _replaceSlackEnvVarsTestrayInformation(string, testBaseDir);
		string = _replaceSlackEnvVarsTestrayImporter(string);

		return string;
	}

	private String _replaceSlackEnvVarsTestrayImporter(String string) {
		String buildNumber = System.getenv("BUILD_NUMBER");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(buildNumber)) {
			string = string.replace(
				"$(testray.importer.build.number)", buildNumber);
		}

		String buildURL = System.getenv("BUILD_URL");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(buildURL)) {
			string = string.replace("$(testray.importer.build.url)", buildURL);
		}

		String jobName = System.getenv("JOB_NAME");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jobName)) {
			string = string.replace("$(testray.importer.job.name)", jobName);
		}

		return string;
	}

	private String _replaceSlackEnvVarsTestrayInformation(
		String string, File testBaseDir) {

		TestrayServer testrayServer = getTestrayServer(testBaseDir);

		if (testrayServer != null) {
			string = string.replace(
				"$(testray.server.url)",
				String.valueOf(testrayServer.getURL()));
		}

		TestrayProject testrayProject = getTestrayProject(testBaseDir);

		if (testrayProject != null) {
			string = string.replace(
				"$(testray.project.name)",
				_fixSlackString(testrayProject.getName()));

			string = string.replace(
				"$(testray.project.url)",
				String.valueOf(testrayProject.getURL()));
		}

		TestrayProductVersion testrayProductVersion = getTestrayProductVersion(
			testBaseDir);

		if (testrayProductVersion != null) {
			string = string.replace(
				"$(testray.product.version.name)",
				_fixSlackString(testrayProductVersion.getName()));
			string = string.replace(
				"$(testray.product.version.url)",
				String.valueOf(testrayProductVersion.getURL()));
		}

		TestrayRoutine testrayRoutine = getTestrayRoutine(testBaseDir);

		if (testrayRoutine != null) {
			string = string.replace(
				"$(testray.routine.name)",
				_fixSlackString(testrayRoutine.getName()));
			string = string.replace(
				"$(testray.routine.url)",
				String.valueOf(testrayRoutine.getURL()));
		}

		TestrayBuild testrayBuild = getTestrayBuild(testBaseDir);

		if (testrayBuild != null) {
			string = string.replace(
				"$(testray.build.name)",
				_fixSlackString(testrayBuild.getName()));
			string = string.replace(
				"$(testray.build.url)", String.valueOf(testrayBuild.getURL()));
		}

		return string;
	}

	private void _sendPullRequestNotification() {
		PullRequest pullRequest = getPullRequest();

		if (pullRequest == null) {
			return;
		}

		pullRequest.addComment(getJenkinsBuildDescription());
	}

	private void _setupPortalBundle() {
		final PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		if (portalGitWorkingDirectory == null) {
			return;
		}

		PortalRelease portalRelease = getPortalRelease();

		if (portalRelease == null) {
			return;
		}

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(
			"liferay.portal.bundle", portalRelease.getPortalVersion());

		String tomcatURL = String.valueOf(portalRelease.getTomcatURL());

		if (tomcatURL.startsWith("https://release.liferay.com")) {
			try {
				tomcatURL = tomcatURL.replaceAll(
					"https://(release\\.liferay\\.com.*)",
					JenkinsResultsParserUtil.combine(
						"https://",
						JenkinsResultsParserUtil.getBuildProperty(
							"jenkins.admin.user.name"),
						":",
						JenkinsResultsParserUtil.getBuildProperty(
							"jenkins.admin.user.password"),
						"@$1"));
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		parameters.put("test.build.bundle.zip.url", tomcatURL);

		PortalFixpackRelease portalFixpackRelease = getPortalFixpackRelease();
		PortalHotfixRelease portalHotfixRelease = getPortalHotfixRelease();

		if ((portalFixpackRelease != null) || (portalHotfixRelease != null)) {
			try {
				parameters.put(
					"test.fix.pack.base.url",
					JenkinsResultsParserUtil.getBuildProperty(
						"portal.test.properties[test.fix.pack.base.url]"));
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			if (portalHotfixRelease != null) {
				parameters.put(
					"test.build.fix.pack.zip.url",
					String.valueOf(
						portalHotfixRelease.getPortalHotfixReleaseURL()));
			}
			else {
				parameters.put(
					"test.build.fix.pack.zip.url",
					String.valueOf(portalFixpackRelease.getPortalFixpackURL()));
			}
		}

		final File workingDirectory =
			portalGitWorkingDirectory.getWorkingDirectory();

		Retryable<Object> retryable = new Retryable<Object>(true, 3, 15, true) {

			@Override
			public Object execute() {
				try {
					AntUtil.callTarget(
						workingDirectory, "build-test.xml",
						"set-tomcat-version-number", parameters);

					String upstreamBranchName =
						portalGitWorkingDirectory.getUpstreamBranchName();

					if (upstreamBranchName.contains("6.1") ||
						upstreamBranchName.contains("6.2")) {

						parameters.put(
							"test.app.server.bin.dir",
							JenkinsResultsParserUtil.getProperty(
								portalGitWorkingDirectory.
									getAppServerProperties(),
								"app.server.tomcat.bin.dir"));
					}

					AntUtil.callTarget(
						workingDirectory, "build-test.xml",
						"prepare-test-bundle", parameters);
				}
				catch (AntException antException) {
					throw new RuntimeException(antException);
				}

				return null;
			}

		};

		retryable.execute();
	}

	private void _setupProfileDXP() {
		boolean setupProfileDXP = false;

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		String branchName = topLevelBuild.getBranchName();
		String jobName = topLevelBuild.getJobName();

		if (!branchName.startsWith("ee-") && !branchName.endsWith("-private") &&
			jobName.contains("environment")) {

			setupProfileDXP = true;
		}

		if (!branchName.startsWith("ee-") &&
			(jobName.contains("fixpack") || jobName.contains("hotfix"))) {

			setupProfileDXP = true;
		}

		String portalBuildProfile = topLevelBuild.getParameterValue(
			"TEST_PORTAL_BUILD_PROFILE");

		if ((portalBuildProfile != null) && portalBuildProfile.equals("dxp")) {
			setupProfileDXP = true;
		}

		if (!setupProfileDXP) {
			return;
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		try {
			AntUtil.callTarget(
				portalGitWorkingDirectory.getWorkingDirectory(), "build.xml",
				"setup-profile-dxp");
		}
		catch (AntException antException) {
			throw new RuntimeException(antException);
		}
	}

	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(10, true);
	private static final Pattern _releaseArtifactURLPattern = Pattern.compile(
		"https?://.+/(?<releaseName>[^/]+)(.7z|.tar.gz|.war|.zip)");

	private Job _job;
	private final Map<File, TestrayBuild> _testrayBuilds =
		Collections.synchronizedMap(new HashMap<File, TestrayBuild>());
	private final Map<File, TestrayProductVersion> _testrayProductVersions =
		Collections.synchronizedMap(new HashMap<File, TestrayProductVersion>());
	private final Map<File, TestrayProject> _testrayProjects =
		Collections.synchronizedMap(new HashMap<File, TestrayProject>());
	private final Map<File, TestrayRoutine> _testrayRoutines =
		Collections.synchronizedMap(new HashMap<File, TestrayRoutine>());
	private final Map<File, TestrayServer> _testrayServers =
		Collections.synchronizedMap(new HashMap<File, TestrayServer>());
	private final TopLevelBuild _topLevelBuild;

}