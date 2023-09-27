/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DefaultPortalJob
	extends BaseJob implements PortalTestClassJob, TestSuiteJob {

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("test_suite_name", _testSuiteName);

		return jsonObject;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		if (_portalGitWorkingDirectory != null) {
			return _portalGitWorkingDirectory;
		}

		File portalRepositoryDir = new File(".");

		File portalTestPropertiesFile = new File(
			portalRepositoryDir, "test.properties");

		Properties portalTestProperties =
			JenkinsResultsParserUtil.getProperties(portalTestPropertiesFile);

		String portalUpstreamBranchName = portalTestProperties.getProperty(
			"liferay.portal.branch", "master");

		String portalRepositoryName = "liferay-portal";

		if (!portalUpstreamBranchName.equals("master")) {
			portalRepositoryName += "-ee";
		}

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				portalUpstreamBranchName, portalRepositoryDir,
				portalRepositoryName);

		if (!(gitWorkingDirectory instanceof PortalGitWorkingDirectory)) {
			throw new RuntimeException("Invalid portal Git working directory");
		}

		_portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)gitWorkingDirectory;

		return _portalGitWorkingDirectory;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected DefaultPortalJob(
		BuildProfile buildProfile, String jobName,
		PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(buildProfile, jobName);

		_portalGitWorkingDirectory = portalGitWorkingDirectory;
		_testSuiteName = testSuiteName;

		_initialize();
	}

	protected DefaultPortalJob(
		BuildProfile buildProfile, String jobName, String testSuiteName) {

		this(buildProfile, jobName, null, testSuiteName);
	}

	protected DefaultPortalJob(JSONObject jsonObject) {
		super(jsonObject);

		_testSuiteName = jsonObject.getString("test_suite_name");

		_initialize();
	}

	private void _initialize() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File portalWorkingDirectory =
			portalGitWorkingDirectory.getWorkingDirectory();

		jobPropertiesFiles.add(
			new File(portalWorkingDirectory, "build.properties"));
		jobPropertiesFiles.add(
			new File(portalWorkingDirectory, "test.properties"));
	}

	private PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final String _testSuiteName;

}