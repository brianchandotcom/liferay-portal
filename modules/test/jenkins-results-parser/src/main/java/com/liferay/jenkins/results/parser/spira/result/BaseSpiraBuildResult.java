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

import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalGitRepositoryJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraRelease;
import com.liferay.jenkins.results.parser.spira.SpiraReleaseBuild;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseFolder;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseProductVersion;

/**
 * @author Michael Hashimoto
 */
public class BaseSpiraBuildResult implements SpiraBuildResult {

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

	protected BaseSpiraBuildResult(TopLevelBuild topLevelBuild) {
		_topLevelBuild = topLevelBuild;

		_portalGitWorkingDirectory = _getPortalGitWorkingDirectory();

		_spiraProject = _getSpiraProject();

		_spiraRelease = _getSpiraRelease(_spiraProject);
		_spiraTestCaseFolder = _getSpiraTestCaseFolder(_spiraProject);
		_spiraTestCaseProductVersion = _getSpiraTestCaseProductVersion(
			_spiraProject);

		_spiraReleaseBuild = _getSpiraReleaseBuild(
			_spiraProject, _spiraRelease);
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

	private String _getPortalTestSuiteProperty(String propertyName) {
		return JenkinsResultsParserUtil.getProperty(
			_portalGitWorkingDirectory.getTestProperties(), propertyName,
			_topLevelBuild.getJobName(), _topLevelBuild.getTestSuiteName());
	}

	private SpiraProject _getSpiraProject() {
		long start = System.currentTimeMillis();

		String spiraProjectID = System.getenv("TEST_SPIRA_PROJECT_ID");

		if ((spiraProjectID == null) || !spiraProjectID.matches("\\d+")) {
			spiraProjectID = _getPortalTestSuiteProperty(
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
					"Spira Project created in ",
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
				spiraProject, spiraReleasePath);
		}

		spiraReleaseID = _getPortalTestSuiteProperty(
			"test.batch.spira.release.id");

		if ((spiraRelease == null) && (spiraReleaseID != null) &&
			spiraReleaseID.matches("\\d+")) {

			spiraRelease = spiraProject.getSpiraReleaseByID(
				Integer.valueOf(spiraReleaseID));
		}

		spiraReleasePath = _getPortalTestSuiteProperty(
			"test.batch.spira.release.path");

		if ((spiraRelease == null) && (spiraReleasePath != null) &&
			spiraReleasePath.matches("\\/.+")) {

			spiraRelease = SpiraRelease.createSpiraReleaseByPath(
				spiraProject, spiraReleasePath);
		}

		if (spiraRelease != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Spira Release created in ",
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
				spiraProject, spiraRelease, spiraReleaseBuildName,
				_getSpiraReleaseBuildDescription(),
				_getSpiraReleaseBuildStatus(), _topLevelBuild.getStartTime());
		}

		spiraReleaseBuildID = _getPortalTestSuiteProperty(
			"test.batch.spira.build.id");

		if ((spiraReleaseBuild == null) && (spiraReleaseBuildID != null) &&
			spiraReleaseBuildID.matches("\\d+")) {

			spiraReleaseBuild = spiraRelease.getSpiraReleaseBuildByID(
				Integer.valueOf(spiraReleaseBuildID));
		}

		spiraReleaseBuildName = _getPortalTestSuiteProperty(
			"test.batch.spira.build.name");

		if ((spiraReleaseBuild == null) && (spiraReleaseBuildName != null) &&
			!spiraReleaseBuildName.isEmpty()) {

			spiraReleaseBuild = SpiraReleaseBuild.createSpiraReleaseBuild(
				spiraProject, spiraRelease, spiraReleaseBuildName,
				_getSpiraReleaseBuildDescription(),
				_getSpiraReleaseBuildStatus(), _topLevelBuild.getStartTime());
		}

		if (spiraReleaseBuild != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Spira Release Build created in ",
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

			spiraReleaseBuildDescription = _getPortalTestSuiteProperty(
				"test.batch.spira.build.description");
		}

		if (spiraReleaseBuildDescription == null) {
			spiraReleaseBuildDescription = "";
		}

		return spiraReleaseBuildDescription;
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
					spiraProject, spiraTestCaseFolderPath);
		}

		spiraTestCaseFolderID = _getPortalTestSuiteProperty(
			"test.batch.spira.base.test.case.folder.id");

		if ((spiraTestCaseFolder == null) && (spiraTestCaseFolderID != null) &&
			spiraTestCaseFolderID.matches("\\d+")) {

			spiraTestCaseFolder = spiraProject.getSpiraTestCaseFolderByID(
				Integer.valueOf(spiraTestCaseFolderID));
		}

		spiraTestCaseFolderPath = _getPortalTestSuiteProperty(
			"test.batch.spira.base.test.case.folder.path");

		if ((spiraTestCaseFolder == null) &&
			(spiraTestCaseFolderPath != null) &&
			spiraTestCaseFolderPath.matches("\\/.+")) {

			spiraTestCaseFolder =
				SpiraTestCaseFolder.createSpiraTestCaseFolderByPath(
					spiraProject, spiraTestCaseFolderPath);
		}

		if (spiraTestCaseFolder != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Base Spira Test Case Folder created in ",
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

	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final SpiraProject _spiraProject;
	private final SpiraRelease _spiraRelease;
	private final SpiraReleaseBuild _spiraReleaseBuild;
	private final SpiraTestCaseFolder _spiraTestCaseFolder;
	private final SpiraTestCaseProductVersion _spiraTestCaseProductVersion;
	private final TopLevelBuild _topLevelBuild;

}