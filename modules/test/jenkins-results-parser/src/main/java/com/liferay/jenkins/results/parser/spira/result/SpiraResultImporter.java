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

import com.liferay.jenkins.results.parser.AnalyticsCloudBranchInformationBuild;
import com.liferay.jenkins.results.parser.AntException;
import com.liferay.jenkins.results.parser.AntUtil;
import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.BuildFactory;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsNode;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.JenkinsSlave;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.LocalGitBranch;
import com.liferay.jenkins.results.parser.PluginsBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.QAWebsitesBranchInformationBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.SpiraAutomationHost;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseComponent;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class SpiraResultImporter {

	public SpiraResultImporter(String buildURL) {
		Build build = BuildFactory.newBuild(buildURL, null);

		if (!(build instanceof TopLevelBuild)) {
			throw new RuntimeException("Invalid top level build" + buildURL);
		}

		_topLevelBuild = (TopLevelBuild)build;

		_spiraBuildResult = SpiraResultFactory.newSpiraBuildResult(
			_topLevelBuild);
	}

	public void record() {
		_cacheSpiraAutomationHosts();
		_cacheSpiraTestCaseComponents();
		_cacheSpiraTestCaseObjects();

		Job job = _topLevelBuild.getJob();

		List<SpiraTestResult> spiraTestResults = new ArrayList<>();

		spiraTestResults.add(
			SpiraResultFactory.newSpiraTestResult(
				_spiraBuildResult, null, null));

		for (AxisTestClassGroup axisTestClassGroup :
				job.getAxisTestClassGroups()) {

			for (TestClassGroup.TestClass testClass :
					axisTestClassGroup.getTestClasses()) {

				spiraTestResults.add(
					SpiraResultFactory.newSpiraTestResult(
						_spiraBuildResult, axisTestClassGroup, testClass));
			}
		}

		for (SpiraTestResult spiraTestResult : spiraTestResults) {
			spiraTestResult.record();
		}
	}

	public void setup() {
		_checkoutPortalBranch();

		_checkoutPortalBaseBranch();

		_checkoutOSBFaroBranch();

		_checkoutPluginsBranch();

		_checkoutQAWebsitesBranch();
	}

	private void _cacheSpiraAutomationHosts() {
		if (_spiraAutomationHosts != null) {
			return;
		}

		long start = System.currentTimeMillis();

		Map<String, SpiraAutomationHost> spiraAutomationHostMap =
			new HashMap<>();

		SpiraProject spiraProject = _spiraBuildResult.getSpiraProject();

		for (SpiraAutomationHost spiraAutomationHost :
				spiraProject.getSpiraAutomationHosts()) {

			spiraAutomationHostMap.put(
				spiraAutomationHost.getName(), spiraAutomationHost);
		}

		for (AxisBuild axisBuild : _topLevelBuild.getDownstreamAxisBuilds()) {
			JenkinsSlave jenkinsSlave = axisBuild.getJenkinsSlave();

			if ((jenkinsSlave != null) &&
				!spiraAutomationHostMap.containsKey(jenkinsSlave.getName())) {

				SpiraAutomationHost spiraAutomationHost =
					_getSpiraAutomationHost(jenkinsSlave);

				spiraAutomationHostMap.put(
					spiraAutomationHost.getName(), spiraAutomationHost);
			}

			JenkinsMaster jenkinsMaster = axisBuild.getJenkinsMaster();

			if ((jenkinsMaster != null) &&
				!spiraAutomationHostMap.containsKey(jenkinsMaster.getName())) {

				SpiraAutomationHost spiraAutomationHost =
					_getSpiraAutomationHost(jenkinsMaster);

				spiraAutomationHostMap.put(
					spiraAutomationHost.getName(), spiraAutomationHost);
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

	private PortalGitWorkingDirectory _getPortalGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			_topLevelBuild.getBranchName());
	}

	private SpiraAutomationHost _getSpiraAutomationHost(
		JenkinsNode jenkinsNode) {

		return SpiraAutomationHost.createSpiraAutomationHost(
			_spiraBuildResult.getSpiraProject(), jenkinsNode);
	}

	private List<SpiraAutomationHost> _spiraAutomationHosts;
	private final SpiraBuildResult _spiraBuildResult;
	private List<SpiraTestCaseComponent> _spiraTestCaseComponents;
	private List<SpiraTestCaseObject> _spiraTestCaseObjects;
	private final TopLevelBuild _topLevelBuild;

}