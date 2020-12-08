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
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.BuildFactory;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.LocalGitBranch;
import com.liferay.jenkins.results.parser.PluginsBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalGitRepositoryJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.QAWebsitesBranchInformationBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
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
		Job job = _topLevelBuild.getJob();

		List<SpiraTestResult> spiraTestResults = new ArrayList<>();

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

	private void _checkoutOSBFaroBranch() {
		if (!(_topLevelBuild instanceof AnalyticsCloudBranchInformationBuild)) {
			return;
		}

		AnalyticsCloudBranchInformationBuild
			analyticsCloudBranchInformationBuild =
				(AnalyticsCloudBranchInformationBuild)_topLevelBuild;

		Build.BranchInformation branchInformation =
			analyticsCloudBranchInformationBuild.getOSBFaroBranchInformation();

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
		Job job = _topLevelBuild.getJob();

		if (job instanceof PortalGitRepositoryJob) {
			PortalGitRepositoryJob portalGitRepositoryJob =
				(PortalGitRepositoryJob)job;

			return portalGitRepositoryJob.getPortalGitWorkingDirectory();
		}

		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			"master");
	}

	private final SpiraBuildResult _spiraBuildResult;
	private final TopLevelBuild _topLevelBuild;

}