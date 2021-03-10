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
import com.liferay.jenkins.results.parser.BatchDependentJob;
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.LocalGitBranch;
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
import com.liferay.jenkins.results.parser.QAWebsitesTopLevelBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.io.File;
import java.io.IOException;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.json.JSONArray;
import org.json.JSONObject;

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

	public TestrayBuild getTestrayBuild(File testBaseDir) {
		TestrayBuild testrayBuild = _testrayBuilds.get(testBaseDir);

		if (testrayBuild != null) {
			return testrayBuild;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		Date testrayBuildDate = getTestrayBuildDate();
		String testrayBuildDescription = getTestrayBuildDescription();
		String testrayBuildSHA = getTestrayBuildSHA();

		try {
			String testrayBuildID = System.getProperty("TESTRAY_BUILD_ID");

			TestrayRoutine testrayRoutine = getTestrayRoutine(testBaseDir);
			TestrayProductVersion testrayProductVersion =
				getTestrayProductVersion(testBaseDir);

			if ((testrayBuildID != null) && testrayBuildID.matches("\\d+")) {
				testrayBuild = testrayRoutine.getTestrayBuildByID(
					Integer.parseInt(testrayBuildID));
			}

			String testrayBuildName = System.getProperty("TESTRAY_BUILD_NAME");

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
					testrayProductVersion, _replaceEnvVars(testrayBuildName),
					testrayBuildDate, testrayBuildDescription, testrayBuildSHA);
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

			sb.append("Portal Branch: ");
			sb.append(portalBranchInformation.getUpstreamBranchName());
			sb.append(";\n");

			sb.append("Portal SHA: ");
			sb.append(portalBranchInformation.getSenderBranchSHA());
			sb.append(";\n");
		}

		if (topLevelBuild instanceof PluginsBranchInformationBuild) {
			PluginsBranchInformationBuild pluginsBranchInformationBuild =
				(PluginsBranchInformationBuild)topLevelBuild;

			Build.BranchInformation pluginsBranchInformation =
				pluginsBranchInformationBuild.getPluginsBranchInformation();

			sb.append("Plugins Branch: ");
			sb.append(pluginsBranchInformation.getUpstreamBranchName());
			sb.append(";\n");

			sb.append("Plugins SHA: ");
			sb.append(pluginsBranchInformation.getSenderBranchSHA());
			sb.append(";\n");
		}

		if (topLevelBuild instanceof QAWebsitesBranchInformationBuild) {
			QAWebsitesBranchInformationBuild qaWebsitesBranchInformationBuild =
				(QAWebsitesBranchInformationBuild)topLevelBuild;

			Build.BranchInformation qaWebsitesBranchInformation =
				qaWebsitesBranchInformationBuild.
					getQAWebsitesBranchInformation();

			sb.append("QA Websites SHA: ");
			sb.append(qaWebsitesBranchInformation.getSenderBranchSHA());
			sb.append(";\n");
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

	public TestrayProductVersion getTestrayProductVersion(File testBaseDir) {
		TestrayProductVersion testrayProductVersion =
			_testrayProductVersions.get(testBaseDir);

		if (testrayProductVersion != null) {
			return testrayProductVersion;
		}

		long start = System.currentTimeMillis();

		try {
			TestrayProject testrayProject = getTestrayProject(testBaseDir);

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

	public TestrayProject getTestrayProject(File testBaseDir) {
		TestrayProject testrayProject = _testrayProjects.get(testBaseDir);

		if (testrayProject != null) {
			return testrayProject;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		try {
			String testrayProjectID = System.getProperty("TESTRAY_PROJECT_ID");

			TestrayServer testrayServer = getTestrayServer(testBaseDir);

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

	public TestrayRoutine getTestrayRoutine(File testBaseDir) {
		TestrayRoutine testrayRoutine = _testrayRoutines.get(testBaseDir);

		if (testrayRoutine != null) {
			return testrayRoutine;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		try {
			String testrayRoutineID = System.getProperty("TESTRAY_ROUTINE_ID");

			TestrayProject testrayProject = getTestrayProject(testBaseDir);

			if ((testrayRoutineID != null) &&
				testrayRoutineID.matches("\\d+")) {

				testrayRoutine = testrayProject.getTestrayRoutineByID(
					Integer.parseInt(testrayRoutineID));
			}

			String testrayRoutineName = System.getProperty(
				"TESTRAY_ROUTINE_NAME");

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

				testrayRoutine = testrayProject.createTestrayRoutine(
					_replaceEnvVars(testrayRoutineName));
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

	public TestrayServer getTestrayServer(File testBaseDir) {
		TestrayServer testrayServer = _testrayServers.get(testBaseDir);

		if (testrayServer != null) {
			return testrayServer;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

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

	public void recordTestrayCaseResults() {
		Job job = getJob();

		List<AxisTestClassGroup> axisTestClassGroups =
			job.getAxisTestClassGroups();

		if (job instanceof BatchDependentJob) {
			BatchDependentJob batchDependentJob = (BatchDependentJob)job;

			axisTestClassGroups.addAll(
				batchDependentJob.getDependentAxisTestClassGroups());
		}

		for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
			TestrayBuild testrayBuild = getTestrayBuild(
				axisTestClassGroup.getTestBaseDir());

			TestrayRun testrayRun = new TestrayRun(
				testrayBuild, axisTestClassGroup.getBatchName());

			long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

			Document document = DocumentHelper.createDocument();

			Element rootElement = document.addElement("testsuite");

			Element environmentsElement = rootElement.addElement(
				"environments");

			for (TestrayRun.Factor factor : testrayRun.getFactors()) {
				Element environmentElement = environmentsElement.addElement(
					"environment");

				environmentElement.addAttribute("type", factor.getName());
				environmentElement.addAttribute("option", factor.getValue());
			}

			Map<String, String> propertiesMap = new HashMap<>();

			propertiesMap.put("testray.build.name", testrayBuild.getName());

			TestrayRoutine testrayRoutine = testrayBuild.getTestrayRoutine();

			propertiesMap.put("testray.build.type", testrayRoutine.getName());

			TestrayProductVersion testrayProductVersion =
				testrayBuild.getTestrayProductVersion();

			propertiesMap.put(
				"testray.product.version", testrayProductVersion.getName());

			TestrayProject testrayProject = testrayBuild.getTestrayProject();

			propertiesMap.put("testray.project.name", testrayProject.getName());

			propertiesMap.put("testray.run.id", testrayRun.getRunIDString());

			_addPropertyElements(
				rootElement.addElement("properties"), propertiesMap);

			List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

			int passedCount = 0;
			int failedCount = 0;

			if (axisTestClassGroup instanceof CucumberAxisTestClassGroup ||
				axisTestClassGroup instanceof FunctionalAxisTestClassGroup ||
				axisTestClassGroup instanceof JUnitAxisTestClassGroup) {

				for (TestClassGroup.TestClass testClass :
						axisTestClassGroup.getTestClasses()) {

					testrayCaseResults.add(
						TestrayCaseResultFactory.newTestrayCaseResult(
							testrayBuild, getTopLevelBuild(),
							axisTestClassGroup, testClass));
				}
			}
			else {
				testrayCaseResults.add(
					TestrayCaseResultFactory.newTestrayCaseResult(
						testrayBuild, getTopLevelBuild(), axisTestClassGroup,
						null));
			}

			for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
				Element testcaseElement = rootElement.addElement("testcase");

				Map<String, String> testcasePropertiesMap = new HashMap<>();

				testcasePropertiesMap.put(
					"testray.case.type.name", testrayCaseResult.getType());
				testcasePropertiesMap.put(
					"testray.component.names",
					testrayCaseResult.getSubcomponentNames());
				testcasePropertiesMap.put(
					"testray.main.component.name",
					testrayCaseResult.getComponentName());
				testcasePropertiesMap.put(
					"testray.team.name", testrayCaseResult.getTeamName());
				testcasePropertiesMap.put(
					"testray.testcase.name", testrayCaseResult.getName());
				testcasePropertiesMap.put(
					"testray.testcase.priority",
					String.valueOf(testrayCaseResult.getPriority()));

				TestrayCaseResult.Status testrayCaseStatus =
					testrayCaseResult.getStatus();

				testcasePropertiesMap.put(
					"testray.testcase.status", testrayCaseStatus.getName());

				if (testrayCaseStatus == TestrayCaseResult.Status.PASSED) {
					passedCount++;
				}
				else {
					failedCount++;
				}

				_addPropertyElements(
					testcaseElement.addElement("properties"),
					testcasePropertiesMap);

				Element attachmentsElement = testcaseElement.addElement(
					"attachments");

				for (TestrayCaseResult.Attachment attachment :
						testrayCaseResult.getAttachments()) {

					Element attachmentFileElement =
						attachmentsElement.addElement("file");

					attachmentFileElement.addAttribute(
						"name", attachment.getName());
					attachmentFileElement.addAttribute(
						"url", String.valueOf(attachment.getURL()));
					attachmentFileElement.addAttribute(
						"value", attachment.getValue());
				}
			}

			Map<String, String> summaryMap = new HashMap<>();

			summaryMap.put("failed", String.valueOf(failedCount));
			summaryMap.put("passed", String.valueOf(passedCount));

			_addPropertyElements(rootElement.addElement("summary"), summaryMap);

			TestrayServer testrayServer = testrayBuild.getTestrayServer();

			try {
				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.combine(
						String.valueOf(testrayServer.getURL()),
						"/web/guest/home/-/testray/case_results",
						"/importResults.json"),
					JenkinsResultsParserUtil.combine(
						"results=",
						URLEncoder.encode(
							Dom4JUtil.format(rootElement), "UTF-8"),
						"&type=poshi"));

				if (jsonObject.has("data")) {
					JSONArray dataJSONArray = jsonObject.getJSONArray("data");

					for (int i = 0; i < dataJSONArray.length(); i++) {
						JSONObject dataJSONObject = dataJSONArray.getJSONObject(
							i);

						if (dataJSONObject == JSONObject.NULL) {
							continue;
						}

						TestrayCaseResult testrayCaseResult =
							new TestrayCaseResult(testrayBuild, dataJSONObject);

						System.out.println(
							JenkinsResultsParserUtil.combine(
								String.valueOf(testrayCaseResult.getStatus()),
								" - ", testrayCaseResult.getName(), " - ",
								String.valueOf(testrayCaseResult.getURL())));
					}
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
			finally {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Recorded ", String.valueOf(testrayCaseResults.size()),
						" case results in ",
						JenkinsResultsParserUtil.toDurationString(
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								start)));
			}
		}
	}

	public void setup() {
		_checkoutPortalBranch();

		_checkoutPortalBaseBranch();

		_setupProfileDXP();

		_callPrepareTCK();

		_checkoutPluginsBranch();
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

	private void _checkoutPortalBaseBranch() {
		if (!(_topLevelBuild instanceof PortalBranchInformationBuild)) {
			return;
		}

		PortalBranchInformationBuild portalBranchInformationBuild =
			(PortalBranchInformationBuild)_topLevelBuild;

		Build.BranchInformation branchInformation =
			portalBranchInformationBuild.getPortalBaseBranchInformation();

		if (branchInformation == null) {
			return;
		}

		PortalGitWorkingDirectory portalBaseGitWorkingDirectory =
			GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
				branchInformation.getUpstreamBranchName());

		LocalGitBranch portalBaseLocalGitBranch =
			portalBaseGitWorkingDirectory.checkoutLocalGitBranch(
				branchInformation);

		portalBaseGitWorkingDirectory.displayLog();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			_getPortalGitWorkingDirectory();

		portalGitWorkingDirectory.fetch(
			portalBaseLocalGitBranch.getName(), portalBaseLocalGitBranch);

		try {
			JenkinsResultsParserUtil.write(
				new File(
					portalGitWorkingDirectory.getWorkingDirectory(),
					"git-commit-portal"),
				portalBaseLocalGitBranch.getSHA());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		try {
			AntUtil.callTarget(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"build-working-dir.xml", "prepare-working-dir");
		}
		catch (AntException antException) {
			throw new RuntimeException(antException);
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

		portalGitWorkingDirectory.checkoutLocalGitBranch(
			portalBranchInformationBuild.getPortalBranchInformation());

		portalGitWorkingDirectory.displayLog();
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
					matcher.group("releaseFileName"));
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

	private void _setupProfileDXP() {
		boolean setupProfileDXP = false;

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		String branchName = topLevelBuild.getBranchName();
		String jobName = topLevelBuild.getJobName();

		if (!branchName.startsWith("ee-") && !branchName.endsWith("-private") &&
			jobName.contains("environment")) {

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

	private static final Pattern _releaseArtifactURLPattern = Pattern.compile(
		"https?://.+/(?<releaseFileName>[^/]+)");

	private Job _job;
	private final Map<File, TestrayBuild> _testrayBuilds = new HashMap<>();
	private final Map<File, TestrayProductVersion> _testrayProductVersions =
		new HashMap<>();
	private final Map<File, TestrayProject> _testrayProjects = new HashMap<>();
	private final Map<File, TestrayRoutine> _testrayRoutines = new HashMap<>();
	private final Map<File, TestrayServer> _testrayServers = new HashMap<>();
	private final TopLevelBuild _topLevelBuild;

}