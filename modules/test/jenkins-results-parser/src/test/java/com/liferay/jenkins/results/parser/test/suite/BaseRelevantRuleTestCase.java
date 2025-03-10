/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.JobFactory;
import com.liferay.jenkins.results.parser.PortalAcceptancePullRequestJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;

import java.io.File;

import java.util.List;

import org.json.JSONObject;

import org.junit.After;
import org.junit.BeforeClass;

/**
 * @author Kenji Heigel
 */
public abstract class BaseRelevantRuleTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		jsonObject.put(
			"build_profile", "DXP"
		).put(
			"git_repository_dir", "liferay-portal"
		).put(
			"job_name", "test-portal-acceptance-pullrequest("
		).put(
			"test_suite_name", "relevant"
		).put(
			"upstream_branch_name", "master"
		);
	}

	@After
	public void tearDown() {
		RelevantRuleEngine.clear();
	}

	protected File getBaseDir() {
		if (_baseDir != null) {
			return _baseDir;
		}

		File baseDir = new File(
			"src/test/resources/dependencies/test/suite" +
				"/RelevantRuleEngineTest");

		_baseDir = JenkinsResultsParserUtil.getCanonicalFile(baseDir);

		return _baseDir;
	}

	protected PortalAcceptancePullRequestJob
		getPortalAcceptancePullRequestJob() {

		return getPortalAcceptancePullRequestJob(null);
	}

	protected PortalAcceptancePullRequestJob getPortalAcceptancePullRequestJob(
		JSONObject jsonObject) {

		if (_portalAcceptancePullRequestJob != null) {
			return _portalAcceptancePullRequestJob;
		}

		String upstreamBranchName = "master";
		String repositoryName = "liferay-portal";

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					upstreamBranchName, getPortalDir(null), repositoryName);

		_portalAcceptancePullRequestJob =
			(PortalAcceptancePullRequestJob)JobFactory.newJob(
				Job.BuildProfile.DXP,
				"test-portal-acceptance-pullrequest(master)", jsonObject,
				portalGitWorkingDirectory, upstreamBranchName, null,
				repositoryName, "relevant", upstreamBranchName);

		List<File> jobPropertiesFiles =
			_portalAcceptancePullRequestJob.getJobPropertiesFiles();

		jobPropertiesFiles.clear();

		jobPropertiesFiles.add(new File(getBaseDir(), "test.properties"));

		return _portalAcceptancePullRequestJob;
	}

	protected File getPortalDir(File file) {
		if (file == null) {
			file = new File(".");

			file = JenkinsResultsParserUtil.getCanonicalFile(file);
		}

		String fileName = file.getName();

		if (fileName.equals("liferay-portal")) {
			return file;
		}

		file = file.getParentFile();

		if (file == null) {
			throw new RuntimeException(
				"Unable to find portal directory from: " + file);
		}

		return getPortalDir(file);
	}

	protected RelevantRuleEngine getRelevantRuleEngine() {
		return getRelevantRuleEngine(null);
	}

	protected RelevantRuleEngine getRelevantRuleEngine(JSONObject jsonObject) {
		RelevantRuleEngine relevantRuleEngine = RelevantRuleEngine.getInstance(
			getPortalAcceptancePullRequestJob(jsonObject));

		relevantRuleEngine.setBaseDir(getBaseDir());

		return relevantRuleEngine;
	}

	protected static JSONObject jsonObject = new JSONObject();

	private File _baseDir;
	private PortalAcceptancePullRequestJob _portalAcceptancePullRequestJob;

}