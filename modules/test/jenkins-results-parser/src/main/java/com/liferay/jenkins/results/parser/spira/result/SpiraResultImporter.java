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

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.AnalyticsCloudBranchInformationBuild;
import com.liferay.jenkins.results.parser.AntException;
import com.liferay.jenkins.results.parser.AntUtil;
import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.BatchDependentJob;
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.BuildDatabase;
import com.liferay.jenkins.results.parser.BuildDatabaseUtil;
import com.liferay.jenkins.results.parser.BuildFactory;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsNode;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.JenkinsSlave;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.LocalGitBranch;
import com.liferay.jenkins.results.parser.NotificationUtil;
import com.liferay.jenkins.results.parser.ParallelExecutor;
import com.liferay.jenkins.results.parser.PluginsBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PullRequest;
import com.liferay.jenkins.results.parser.QAWebsitesBranchInformationBuild;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.SpiraAutomationHost;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraRelease;
import com.liferay.jenkins.results.parser.spira.SpiraReleaseBuild;
import com.liferay.jenkins.results.parser.spira.SpiraRestAPIUtil;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseComponent;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
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

/**
 * @author Michael Hashimoto
 */
public class SpiraResultImporter {

	public SpiraResultImporter(String buildURL) {
		Build build = BuildFactory.newBuild(buildURL, null);

		if (!(build instanceof TopLevelBuild)) {
			throw new RuntimeException("Invalid top level build " + buildURL);
		}

		_topLevelBuild = (TopLevelBuild)build;

		_spiraBuildResult = SpiraResultFactory.newSpiraBuildResult(
			_topLevelBuild);
	}

	public void record() {
		long start = System.currentTimeMillis();

		Job job = _topLevelBuild.getJob();

		String spiraEnabled = JenkinsResultsParserUtil.getProperty(
			job.getJobProperties(), "test.batch.spira.enabled",
			_topLevelBuild.getJobName(), _topLevelBuild.getTestSuiteName());

		if ((spiraEnabled == null) || !spiraEnabled.equals("true")) {
			return;
		}

		_cacheBuildDatabase();
		_cacheBuildResults();
		_cacheSpiraAutomationHosts();
		_cacheSpiraTestCaseComponents();
		_cacheSpiraTestCaseObjects();

		List<SpiraTestResult> spiraTestResults = new ArrayList<>();

		spiraTestResults.add(
			SpiraResultFactory.newSpiraTestResult(
				_spiraBuildResult, null, null));

		List<AxisTestClassGroup> axisTestClassGroups =
			job.getAxisTestClassGroups();

		if (job instanceof BatchDependentJob) {
			BatchDependentJob batchDependentJob = (BatchDependentJob)job;

			axisTestClassGroups.addAll(
				batchDependentJob.getDependentAxisTestClassGroups());
		}

		for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
			if (axisTestClassGroup instanceof CucumberAxisTestClassGroup ||
				axisTestClassGroup instanceof FunctionalAxisTestClassGroup ||
				axisTestClassGroup instanceof JUnitAxisTestClassGroup) {

				for (TestClassGroup.TestClass testClass :
						axisTestClassGroup.getTestClasses()) {

					spiraTestResults.add(
						SpiraResultFactory.newSpiraTestResult(
							_spiraBuildResult, axisTestClassGroup, testClass));
				}

				continue;
			}

			spiraTestResults.add(
				SpiraResultFactory.newSpiraTestResult(
					_spiraBuildResult, axisTestClassGroup, null));
		}

		List<List<SpiraTestResult>> partition = Lists.partition(
			spiraTestResults, _GROUP_SIZE);

		List<Callable<List<SpiraTestCaseRun>>> callableList = new ArrayList<>();

		System.out.println("Created " + partition.size() + " groups");

		for (int i = 0; i < partition.size(); i++) {
			final List<SpiraTestResult> results = partition.get(i);
			final String groupName = "group-" + i;

			callableList.add(
				new Callable<List<SpiraTestCaseRun>>() {

					@Override
					public List<SpiraTestCaseRun> call() throws Exception {
						long startGroup = System.currentTimeMillis();

						System.out.println(
							JenkinsResultsParserUtil.combine(
								"[", groupName, "] ",
								JenkinsResultsParserUtil.toDateString(
									new Date(startGroup)),
								" - Start recording ",
								String.valueOf(results.size()), " tests"));

						List<SpiraTestCaseRun> spiraTestCaseRuns =
							SpiraTestCaseRun.recordSpiraTestCaseRuns(
								_spiraBuildResult.getSpiraProject(),
								results.toArray(new SpiraTestResult[0]));

						long endGroup = System.currentTimeMillis();

						System.out.println(
							JenkinsResultsParserUtil.combine(
								"[", groupName, "] ",
								JenkinsResultsParserUtil.toDateString(
									new Date(startGroup)),
								" - Completed recording ",
								String.valueOf(spiraTestCaseRuns.size()),
								" tests in ",
								JenkinsResultsParserUtil.toDurationString(
									endGroup - startGroup)));

						return spiraTestCaseRuns;
					}

				});
		}

		if (!callableList.isEmpty()) {
			Callable<List<SpiraTestCaseRun>> callable = callableList.get(0);

			List<SpiraTestCaseRun> spiraTestCaseRuns = new ArrayList<>();

			try {
				spiraTestCaseRuns.addAll(callable.call());
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			ParallelExecutor<List<SpiraTestCaseRun>> parallelExecutor =
				new ParallelExecutor<>(
					callableList.subList(1, callableList.size()),
					_groupExecutorService);

			for (List<SpiraTestCaseRun> spiraTestCaseRunsList :
					parallelExecutor.execute()) {

				spiraTestCaseRuns.addAll(spiraTestCaseRunsList);
			}

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Recorded ", String.valueOf(spiraTestCaseRuns.size()),
					" tests in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - start)));
		}

		SpiraRestAPIUtil.summarizeRequests();

		_updateCurrentBuildDescription();
		_updatePullRequest();
		_updateSlackChannel();
		_updateTopLevelBuildDescription();
	}

	public void setup() {
		_checkoutPortalBranch();

		_checkoutPortalBaseBranch();

		_setupProfileDXP();

		_callPrepareTCK();

		_checkoutOSBFaroBranch();
		_checkoutPluginsBranch();
		_checkoutQAWebsitesBranch();
	}

	private void _cacheBuildDatabase() {
		long start = System.currentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Started loading for Build Database at ",
				JenkinsResultsParserUtil.toDateString(new Date(start))));

		try {
			BuildDatabaseUtil.getBuildDatabase(_topLevelBuild);
		}
		catch (Exception exception) {
			System.out.println("Unable to find build-database.json");
		}

		long end = System.currentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Completed loading Build Database in ",
				JenkinsResultsParserUtil.toDurationString(end - start), " at ",
				JenkinsResultsParserUtil.toDateString(new Date(end))));
	}

	private void _cacheBuildResults() {
		long start = System.currentTimeMillis();

		List<Callable<List<TestResult>>> callables = new ArrayList<>();

		List<AxisBuild> downstreamAxisBuilds =
			_topLevelBuild.getDownstreamAxisBuilds();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Started loading for Build Results in ",
				String.valueOf(downstreamAxisBuilds.size()), " Axis Builds at ",
				JenkinsResultsParserUtil.toDateString(new Date(start))));

		for (int i = 0; i < downstreamAxisBuilds.size(); i++) {
			final AxisBuild axisBuild = downstreamAxisBuilds.get(i);
			final String axisBuildName = "axis-" + i;

			callables.add(
				new Callable<List<TestResult>>() {

					@Override
					public List<TestResult> call() throws Exception {
						long start = System.currentTimeMillis();

						System.out.println(
							JenkinsResultsParserUtil.combine(
								"[", axisBuildName,
								"] Start loading Test Results for ",
								axisBuild.getAxisName(), " at ",
								JenkinsResultsParserUtil.toDateString(
									new Date(start))));

						try {
							String result = axisBuild.getResult();

							if (result.equals("UNSTABLE") ||
								result.equals("SUCCESS")) {

								return axisBuild.getTestResults(null);
							}

							return new ArrayList<>();
						}
						catch (Exception exception) {
							throw new RuntimeException(exception);
						}
						finally {
							System.out.println(
								JenkinsResultsParserUtil.combine(
									"[", axisBuildName,
									"] Completed loading Test Results for ",
									axisBuild.getAxisName(), " in ",
									JenkinsResultsParserUtil.toDurationString(
										System.currentTimeMillis() - start),
									" at ",
									JenkinsResultsParserUtil.toDateString(
										new Date())));
						}
					}

				});
		}

		List<TestResult> testResults = new ArrayList<>();

		ParallelExecutor<List<TestResult>> parallelExecutor =
			new ParallelExecutor<>(callables, _buildResultExecutorService);

		for (List<TestResult> testResultList : parallelExecutor.execute()) {
			testResults.addAll(testResultList);
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Completed loading ", String.valueOf(testResults.size()),
				" Build Results in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start),
				" at ", JenkinsResultsParserUtil.toDateString(new Date())));
	}

	private void _cacheSpiraAutomationHosts() {
		if (_spiraAutomationHosts != null) {
			return;
		}

		long start = System.currentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Started searching for Spira Automation Hosts at ",
				JenkinsResultsParserUtil.toDateString(new Date(start))));

		Map<String, SpiraAutomationHost> spiraAutomationHostMap =
			new HashMap<>();

		SpiraProject spiraProject = _spiraBuildResult.getSpiraProject();

		for (SpiraAutomationHost spiraAutomationHost :
				spiraProject.getSpiraAutomationHosts()) {

			spiraAutomationHostMap.put(
				spiraAutomationHost.getName(), spiraAutomationHost);
		}

		Properties buildProperties = _getBuildProperties();

		for (String buildPropertyName : buildProperties.stringPropertyNames()) {
			Matcher masterPropertyNameMatcher =
				_masterPropertyNamePattern.matcher(buildPropertyName);

			if (!masterPropertyNameMatcher.find()) {
				continue;
			}

			String masterHostname = masterPropertyNameMatcher.group(
				"masterHostname");

			if (!spiraAutomationHostMap.containsKey(masterHostname)) {
				SpiraAutomationHost spiraAutomationHost =
					_getSpiraAutomationHost(new JenkinsMaster(masterHostname));

				spiraAutomationHostMap.put(
					spiraAutomationHost.getName(), spiraAutomationHost);

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Creating ", spiraAutomationHost.getName(), " at ",
						spiraAutomationHost.getURL()));
			}

			String slaveHostnameRanges = JenkinsResultsParserUtil.getProperty(
				_getBuildProperties(), buildPropertyName);

			for (String slaveHostnameRange : slaveHostnameRanges.split(",")) {
				Matcher slaveHostnameRangeMatcher =
					_slaveHostnameRangePattern.matcher(slaveHostnameRange);

				if (!slaveHostnameRangeMatcher.find()) {
					continue;
				}

				String slaveHostnamePrefix = slaveHostnameRangeMatcher.group(
					"slaveHostnamePrefix");

				int first = Integer.valueOf(
					slaveHostnameRangeMatcher.group("first"));

				int last = Integer.valueOf(
					slaveHostnameRangeMatcher.group("last"));

				for (int j = first; j <= last; j++) {
					String slaveHostname = JenkinsResultsParserUtil.combine(
						slaveHostnamePrefix, "-", String.valueOf(j));

					if (spiraAutomationHostMap.containsKey(slaveHostname)) {
						continue;
					}

					SpiraAutomationHost spiraAutomationHost =
						_getSpiraAutomationHost(
							new JenkinsSlave(slaveHostname));

					spiraAutomationHostMap.put(
						spiraAutomationHost.getName(), spiraAutomationHost);

					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Creating ", spiraAutomationHost.getName(), " at ",
							spiraAutomationHost.getURL()));
				}
			}
		}

		_spiraAutomationHosts = new ArrayList<>(
			spiraAutomationHostMap.values());

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Loaded ", String.valueOf(_spiraAutomationHosts.size()),
				" Spira Automation Hosts in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start)));
	}

	private void _cacheSpiraTestCaseComponents() {
		if (_spiraTestCaseComponents != null) {
			return;
		}

		long start = System.currentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Started searching for Spira Test Case Components at ",
				JenkinsResultsParserUtil.toDateString(new Date(start))));

		Map<String, SpiraTestCaseComponent> spiraTestCaseComponentsMap =
			new HashMap<>();

		SpiraProject spiraProject = _spiraBuildResult.getSpiraProject();

		for (SpiraTestCaseComponent spiraTestCaseComponent :
				spiraProject.getSpiraTestCaseComponents()) {

			spiraTestCaseComponentsMap.put(
				spiraTestCaseComponent.getName(), spiraTestCaseComponent);
		}

		String componentNames = JenkinsResultsParserUtil.getProperty(
			_spiraBuildResult.getPortalTestProperties(),
			"testray.available.component.names");

		if ((componentNames != null) && !componentNames.isEmpty()) {
			for (String componentName : componentNames.split(",")) {
				componentName = componentName.trim();

				if (spiraTestCaseComponentsMap.containsKey(componentName)) {
					continue;
				}

				SpiraTestCaseComponent spiraTestCaseComponent =
					SpiraTestCaseComponent.createSpiraTestCaseComponent(
						spiraProject, componentName);

				System.out.println(
					"Created component " + spiraTestCaseComponent.getName());

				spiraTestCaseComponentsMap.put(
					spiraTestCaseComponent.getName(), spiraTestCaseComponent);
			}
		}

		_spiraTestCaseComponents = new ArrayList<>(
			spiraTestCaseComponentsMap.values());

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Loaded ", String.valueOf(_spiraTestCaseComponents.size()),
				" Spira Test Case Components in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start)));
	}

	private void _cacheSpiraTestCaseObjects() {
		if (_spiraTestCaseObjects != null) {
			return;
		}

		long start = System.currentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Started searching for Spira Test Cases at ",
				JenkinsResultsParserUtil.toDateString(new Date(start))));

		SpiraProject spiraProject = _spiraBuildResult.getSpiraProject();

		_spiraTestCaseObjects = spiraProject.getSpiraTestCaseObjects(
			Integer.valueOf(
				JenkinsResultsParserUtil.getProperty(
					_getBuildProperties(), "spira.test.case.count")),
			_spiraBuildResult.getSpiraTestCaseProductVersion());

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Loaded ", String.valueOf(_spiraTestCaseObjects.size()),
				" Spira Test Cases in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start)));
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

	private void _checkoutOSBFaroBranch() {
		if (!(_topLevelBuild instanceof AnalyticsCloudBranchInformationBuild)) {
			return;
		}

		AnalyticsCloudBranchInformationBuild
			analyticsCloudBranchInformationBuild =
				(AnalyticsCloudBranchInformationBuild)_topLevelBuild;

		Build.BranchInformation branchInformation =
			analyticsCloudBranchInformationBuild.getOSBFaroBranchInformation();

		if (branchInformation == null) {
			return;
		}

		String upstreamBranchName = branchInformation.getUpstreamBranchName();

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "osb.faro.dir", upstreamBranchName);
		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "osb.faro.repository", upstreamBranchName);

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, upstreamDirPath, upstreamRepository);

		gitWorkingDirectory.checkoutLocalGitBranch(branchInformation);

		gitWorkingDirectory.displayLog();
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

		String upstreamBranchName = branchInformation.getUpstreamBranchName();

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "plugins.dir", upstreamBranchName);
		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "plugins.repository", upstreamBranchName);

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, upstreamDirPath, upstreamRepository);

		gitWorkingDirectory.checkoutLocalGitBranch(branchInformation);

		gitWorkingDirectory.displayLog();
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

		String upstreamBranchName = branchInformation.getUpstreamBranchName();

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "portal.dir", upstreamBranchName);
		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "portal.repository", upstreamBranchName);

		GitWorkingDirectory portalBaseGitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, upstreamDirPath, upstreamRepository);

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

	private void _checkoutQAWebsitesBranch() {
		if (!(_topLevelBuild instanceof QAWebsitesBranchInformationBuild)) {
			return;
		}

		QAWebsitesBranchInformationBuild qaWebsitesBranchInformationBuild =
			(QAWebsitesBranchInformationBuild)_topLevelBuild;

		Build.BranchInformation branchInformation =
			qaWebsitesBranchInformationBuild.getQAWebsitesBranchInformation();

		if (branchInformation == null) {
			return;
		}

		String upstreamBranchName = branchInformation.getUpstreamBranchName();

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "qa.websites.dir", upstreamBranchName);
		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "qa.websites.repository",
			upstreamBranchName);

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, upstreamDirPath, upstreamRepository);

		gitWorkingDirectory.checkoutLocalGitBranch(branchInformation);

		gitWorkingDirectory.displayLog();
	}

	private Properties _getBuildProperties() {
		try {
			return JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private GitWorkingDirectory _getJenkinsGitWorkingDirectory() {
		String upstreamBranchName = "master";

		String upstreamDirPath = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "jenkins.dir", upstreamBranchName);

		String upstreamRepository = JenkinsResultsParserUtil.getProperty(
			_getBuildProperties(), "jenkins.repository", upstreamBranchName);

		return GitWorkingDirectoryFactory.newGitWorkingDirectory(
			upstreamBranchName, upstreamDirPath, upstreamRepository);
	}

	private PortalGitWorkingDirectory _getPortalGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			_topLevelBuild.getBranchName());
	}

	private SpiraAutomationHost _getSpiraAutomationHost(
		JenkinsNode jenkinsNode) {

		return SpiraAutomationHost.createSpiraAutomationHost(
			_spiraBuildResult.getSpiraProject(), jenkinsNode);
	}

	private String _getSpiraReport() {
		StringBuilder sb = new StringBuilder();

		sb.append("<strong>Jenkins Build:</strong> <a href=\"");
		sb.append(_topLevelBuild.getBuildURL());
		sb.append("\">");
		sb.append(_topLevelBuild.getJobName());
		sb.append("#");
		sb.append(_topLevelBuild.getBuildNumber());
		sb.append("</a><br />");

		sb.append("<strong>Jenkins Report:</strong> <a href=\"");
		sb.append(_topLevelBuild.getJenkinsReportURL());
		sb.append("\">jenkins-report.html</a><br />");

		String testSuiteName = _topLevelBuild.getTestSuiteName();

		if ((testSuiteName != null) && !testSuiteName.isEmpty()) {
			sb.append("<strong>Jenkins Suite:</strong> ");
			sb.append(testSuiteName);
			sb.append("<br />");
		}

		PullRequest pullRequest = _spiraBuildResult.getPullRequest();

		if (pullRequest != null) {
			sb.append("<strong>Pull Request:</strong> <a href=\"");
			sb.append(pullRequest.getHtmlURL());
			sb.append("\">");
			sb.append(pullRequest.getReceiverUsername());
			sb.append("#");
			sb.append(pullRequest.getNumber());
			sb.append("</a><br />");
		}

		SpiraRelease spiraRelease = _spiraBuildResult.getSpiraRelease();

		if (spiraRelease != null) {
			sb.append("<strong>Spira Release:</strong> <a href=\"");
			sb.append(spiraRelease.getURL());
			sb.append("\">");
			sb.append(spiraRelease.getPath());
			sb.append("</a><br />");
		}

		SpiraReleaseBuild spiraReleaseBuild =
			_spiraBuildResult.getSpiraReleaseBuild();

		if (spiraReleaseBuild != null) {
			sb.append("<strong>Spira Release Build:</strong> <a href=\"");
			sb.append(spiraReleaseBuild.getURL());
			sb.append("\">");
			sb.append(spiraReleaseBuild.getName());
			sb.append("</a><br />");
		}

		String currentJobName = System.getenv("JOB_NAME");

		if (currentJobName.equals("publish-spira-report")) {
			sb.append("<strong>Spira Jenkins Build:</strong> <a href=\"");
			sb.append(System.getenv("BUILD_URL"));
			sb.append("\">");
			sb.append(currentJobName);
			sb.append("#");
			sb.append(System.getenv("BUILD_NUMBER"));
			sb.append("</a><br />");
		}

		return sb.toString();
	}

	private void _setupProfileDXP() {
		boolean setupProfileDXP = false;

		TopLevelBuild topLevelBuild = _spiraBuildResult.getTopLevelBuild();

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

	private void _updateCurrentBuildDescription() {
		String buildURL = System.getenv("BUILD_URL");

		if ((buildURL == null) || !buildURL.contains("publish-spira-report")) {
			return;
		}

		String workspace = System.getenv("WORKSPACE");

		if ((workspace == null) || workspace.isEmpty()) {
			return;
		}

		Build build = BuildFactory.newBuild(buildURL, null);

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase(build);

		Properties properties = buildDatabase.getProperties("start.properties");

		properties.setProperty("BUILD_DESCRIPTION", _getSpiraReport());

		buildDatabase.putProperties("stop.properties", properties);
	}

	private void _updatePullRequest() {
		PullRequest pullRequest = _spiraBuildResult.getPullRequest();

		if (pullRequest == null) {
			return;
		}

		pullRequest.addComment(_getSpiraReport());
	}

	private void _updateSlackChannel() {
		String buildName = JenkinsResultsParserUtil.combine(
			_topLevelBuild.getJobName(), "#",
			String.valueOf(_topLevelBuild.getBuildNumber()));

		StringBuilder sb = new StringBuilder();

		sb.append("*Jenkins Build:* <");
		sb.append(_topLevelBuild.getBuildURL());
		sb.append("|");
		sb.append(StringEscapeUtils.escapeHtml(buildName));
		sb.append(">\n");

		sb.append("*Jenkins Report:* <");
		sb.append(_topLevelBuild.getJenkinsReportURL());
		sb.append("|jenkins-report.html>\n");

		String testSuiteName = _topLevelBuild.getTestSuiteName();

		if ((testSuiteName != null) && !testSuiteName.isEmpty()) {
			sb.append("*Jenkins Suite:* ");
			sb.append(testSuiteName);
			sb.append("\n");
		}

		PullRequest pullRequest = _spiraBuildResult.getPullRequest();

		if (pullRequest != null) {
			sb.append("*Pull Request:* <");
			sb.append(pullRequest.getHtmlURL());
			sb.append("|");
			sb.append(pullRequest.getReceiverUsername());
			sb.append("#");
			sb.append(pullRequest.getNumber());
			sb.append(">\n");
		}

		SpiraRelease spiraRelease = _spiraBuildResult.getSpiraRelease();

		if (spiraRelease != null) {
			sb.append("*Spira Release:* <");
			sb.append(spiraRelease.getURL());
			sb.append("|");
			sb.append(StringEscapeUtils.escapeHtml(spiraRelease.getPath()));
			sb.append(">\n");
		}

		SpiraReleaseBuild spiraReleaseBuild =
			_spiraBuildResult.getSpiraReleaseBuild();

		if (spiraReleaseBuild != null) {
			sb.append("*Spira Release Build:* <");
			sb.append(spiraReleaseBuild.getURL());
			sb.append("|");
			sb.append(
				StringEscapeUtils.escapeHtml(spiraReleaseBuild.getName()));
			sb.append(">\n");
		}

		String currentJobName = System.getenv("JOB_NAME");

		if (currentJobName.equals("publish-spira-report")) {
			sb.append("*Spira Jenkins Build:* <");
			sb.append(System.getenv("BUILD_URL"));
			sb.append("|");
			sb.append(currentJobName);
			sb.append("#");
			sb.append(System.getenv("BUILD_NUMBER"));
			sb.append(">\n");
		}

		NotificationUtil.sendSlackNotification(
			sb.toString(), "#spira-reports", ":liferay-ci:", buildName,
			"Liferay CI");
	}

	private void _updateTopLevelBuildDescription() {
		SpiraReleaseBuild spiraReleaseBuild =
			_spiraBuildResult.getSpiraReleaseBuild();

		if (spiraReleaseBuild == null) {
			return;
		}

		String buildDescription = _topLevelBuild.getBuildDescription();

		String text = ">Jenkins Report</a>";

		if (!buildDescription.contains(text) ||
			buildDescription.contains("Spira Report")) {

			return;
		}

		int x = buildDescription.indexOf(text) + text.length();

		StringBuilder sb = new StringBuilder();

		sb.append(buildDescription.substring(0, x));
		sb.append(" - <a href=\"");
		sb.append(spiraReleaseBuild.getURL());
		sb.append("\">Spira Report</a>");
		sb.append(buildDescription.substring(x));

		JenkinsMaster jenkinsMaster = _topLevelBuild.getJenkinsMaster();

		JenkinsResultsParserUtil.updateBuildDescription(
			sb.toString(), _topLevelBuild.getBuildNumber(),
			_topLevelBuild.getJobName(), jenkinsMaster.getName());
	}

	private static final int _BUILD_RESULT_THREAD_COUNT = 50;

	private static final int _GROUP_SIZE = 25;

	private static final int _GROUP_THREAD_COUNT = 10;

	private static final ExecutorService _buildResultExecutorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(
			_BUILD_RESULT_THREAD_COUNT, true);
	private static final ExecutorService _groupExecutorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(
			_GROUP_THREAD_COUNT, true);
	private static final Pattern _masterPropertyNamePattern = Pattern.compile(
		"master\\.slaves\\((?<masterHostname>test-\\d+-\\d+)\\)");
	private static final Pattern _slaveHostnameRangePattern = Pattern.compile(
		"(?<slaveHostnamePrefix>cloud-\\d+-\\d+-\\d+)-(?<first>\\d+)\\.\\." +
			"(?<last>\\d+)");

	private List<SpiraAutomationHost> _spiraAutomationHosts;
	private final SpiraBuildResult _spiraBuildResult;
	private List<SpiraTestCaseComponent> _spiraTestCaseComponents;
	private List<SpiraTestCaseObject> _spiraTestCaseObjects;
	private final TopLevelBuild _topLevelBuild;

}