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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.QAWebsitesGitRepositoryJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.util.PropsUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesFunctionalBatchTestClassGroup
	extends FunctionalBatchTestClassGroup {

	@Override
	public List<File> getTestBaseDirs() {
		List<File> testBaseDirs = new ArrayList<>();

		GitWorkingDirectory qaWebsitesGitWorkingDirectory =
			_getQAWebsitesGitWorkingDirectory();

		QAWebsitesGitRepositoryJob qaWebsitesGitRepositoryJob =
			_getQAWebsitesGitRepositoryJob();

		for (String projectName :
				qaWebsitesGitRepositoryJob.getProjectNames()) {

			testBaseDirs.add(
				new File(
					qaWebsitesGitWorkingDirectory.getWorkingDirectory(),
					projectName));
		}

		return testBaseDirs;
	}

	protected QAWebsitesFunctionalBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected QAWebsitesFunctionalBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected String getDefaultTestBatchRunPropertyQuery(
		File testBaseDir, String testSuiteName) {

		String query = System.getenv("TEST_QA_WEBSITES_PROPERTY_QUERY");

		if (JenkinsResultsParserUtil.isNullOrEmpty(query)) {
			query = getBuildStartProperty("TEST_QA_WEBSITES_PROPERTY_QUERY");
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(query)) {
			return query;
		}

		JobProperty jobProperty = getJobProperty(
			"test.batch.property.query", testBaseDir,
			JobProperty.Type.QA_WEBSITES_TEST_DIR);

		recordJobProperty(jobProperty);

		return jobProperty.getValue();
	}

	@Override
	protected List<List<String>> getPoshiTestClassGroups(File testBaseDir) {
		String query = getTestBatchRunPropertyQuery(testBaseDir);

		if (JenkinsResultsParserUtil.isNullOrEmpty(query)) {
			return new ArrayList<>();
		}

		synchronized (portalTestClassJob) {
			String testBaseDirPath = null;

			if ((testBaseDir != null) && testBaseDir.exists()) {
				testBaseDirPath = JenkinsResultsParserUtil.getCanonicalPath(
					testBaseDir);
			}

			Properties properties = JenkinsResultsParserUtil.getProperties(
				new File(testBaseDir.getParentFile(), "test.properties"),
				new File(testBaseDir, "poshi-ext.properties"),
				new File(testBaseDir, "poshi.properties"),
				new File(testBaseDir, "test.properties"));

			if (!JenkinsResultsParserUtil.isNullOrEmpty(testBaseDirPath)) {
				properties.setProperty("test.base.dir.name", testBaseDirPath);
			}

			PropsUtil.clear();

			PropsUtil.setProperties(properties);

			try {
				PoshiContext.clear();

				PoshiContext.readFiles();

				return PoshiContext.getTestBatchGroups(query, getAxisMaxSize());
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	private QAWebsitesGitRepositoryJob _getQAWebsitesGitRepositoryJob() {
		if (!(portalTestClassJob instanceof QAWebsitesGitRepositoryJob)) {
			throw new RuntimeException(
				"Invalid job type " + portalTestClassJob);
		}

		return (QAWebsitesGitRepositoryJob)portalTestClassJob;
	}

	private GitWorkingDirectory _getQAWebsitesGitWorkingDirectory() {
		QAWebsitesGitRepositoryJob qaWebsitesGitRepositoryJob =
			_getQAWebsitesGitRepositoryJob();

		return qaWebsitesGitRepositoryJob.getGitWorkingDirectory();
	}

}