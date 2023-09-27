/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesGitRepositoryJob
	extends GitRepositoryJob implements PortalTestClassJob, TestSuiteJob {

	@Override
	public String getBranchName() {
		return _upstreamBranchName;
	}

	@Override
	public Set<String> getDistTypes() {
		return new HashSet<>();
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		return gitWorkingDirectory;
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("project_names", _projectNames);
		jsonObject.put("test_suite_name", _testSuiteName);
		jsonObject.put("upstream_branch_name", _upstreamBranchName);

		return jsonObject;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			"master");
	}

	public List<String> getProjectNames() {
		return _projectNames;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected QAWebsitesGitRepositoryJob(
		BuildProfile buildProfile, String jobName, List<String> projectNames,
		String testSuiteName, String upstreamBranchName) {

		super(buildProfile, jobName);

		_projectNames = projectNames;
		_testSuiteName = testSuiteName;
		_upstreamBranchName = upstreamBranchName;

		_initialize();
	}

	protected QAWebsitesGitRepositoryJob(JSONObject jsonObject) {
		super(jsonObject);

		_testSuiteName = jsonObject.getString("test_suite_name");
		_upstreamBranchName = jsonObject.getString("upstream_branch_name");

		_projectNames = new ArrayList<>();

		JSONArray projectNamesJSONArray = jsonObject.optJSONArray(
			"project_names");

		if (projectNamesJSONArray != null) {
			for (int i = 0; i < projectNamesJSONArray.length(); i++) {
				String projectName = projectNamesJSONArray.getString(i);

				if (JenkinsResultsParserUtil.isNullOrEmpty(projectName)) {
					continue;
				}

				_projectNames.add(projectName);
			}
		}

		_initialize();
	}

	private File _getQAWebsitesGitRepositoryDir() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String qaWebsitesDirPath = JenkinsResultsParserUtil.getProperty(
			buildProperties, "qa.websites.dir", getBranchName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(qaWebsitesDirPath)) {
			throw new RuntimeException(
				"Unable to find QA Websites directory path");
		}

		File qaWebsitesDir = new File(qaWebsitesDirPath);

		if (!qaWebsitesDir.exists()) {
			throw new RuntimeException("Unable to find QA Websites directory");
		}

		return qaWebsitesDir;
	}

	private String _getQAWebsitesRepositoryName() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		String qaWebsitesRepository = JenkinsResultsParserUtil.getProperty(
			buildProperties, "qa.websites.repository", getBranchName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(qaWebsitesRepository)) {
			throw new RuntimeException("Unable to find QA Websites repository");
		}

		return qaWebsitesRepository;
	}

	private void _initialize() {
		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			_upstreamBranchName, _getQAWebsitesGitRepositoryDir(),
			_getQAWebsitesRepositoryName());

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		jobPropertiesFiles.add(new File(gitRepositoryDir, "test.properties"));
	}

	private final List<String> _projectNames;
	private final String _testSuiteName;
	private final String _upstreamBranchName;

}