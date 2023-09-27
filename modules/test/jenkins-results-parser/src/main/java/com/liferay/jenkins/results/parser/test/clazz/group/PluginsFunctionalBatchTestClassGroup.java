/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.AntException;
import com.liferay.jenkins.results.parser.AntUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PluginsGitRepositoryJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.util.PropsUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsFunctionalBatchTestClassGroup
	extends FunctionalBatchTestClassGroup {

	@Override
	public List<File> getTestBaseDirs() {
		if (!(portalTestClassJob instanceof PluginsGitRepositoryJob)) {
			return new ArrayList<>();
		}

		PluginsGitRepositoryJob pluginsGitRepositoryJob =
			(PluginsGitRepositoryJob)portalTestClassJob;

		return pluginsGitRepositoryJob.getPluginsTestBaseDirs();
	}

	protected PluginsFunctionalBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected PluginsFunctionalBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected String getDefaultTestBatchRunPropertyQuery(
		File testBaseDir, String testSuiteName) {

		String query = System.getenv("TEST_BATCH_RUN_PROPERTY_QUERY");

		if (JenkinsResultsParserUtil.isNullOrEmpty(query)) {
			query = getBuildStartProperty("TEST_BATCH_RUN_PROPERTY_QUERY");
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(query)) {
			return query;
		}

		JobProperty jobProperty = getJobProperty(
			"test.batch.run.property.query", testBaseDir,
			JobProperty.Type.PLUGIN_TEST_DIR);

		recordJobProperty(jobProperty);

		return jobProperty.getValue();
	}

	@Override
	protected List<List<TestClass>> getPoshiTestClassGroups(File testBaseDir) {
		String query = getTestBatchRunPropertyQuery(testBaseDir);

		if (JenkinsResultsParserUtil.isNullOrEmpty(query)) {
			return new ArrayList<>();
		}

		synchronized (portalTestClassJob) {
			PortalGitWorkingDirectory portalGitWorkingDirectory =
				portalTestClassJob.getPortalGitWorkingDirectory();

			File portalWorkingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			Map<String, String> parameters = new HashMap<>();

			String testBaseDirPath = null;

			if ((testBaseDir != null) && testBaseDir.exists()) {
				testBaseDirPath = JenkinsResultsParserUtil.getCanonicalPath(
					testBaseDir);

				parameters.put("test.base.dir.name", testBaseDirPath);
			}

			try {
				AntUtil.callTarget(
					portalWorkingDirectory, "build-test.xml",
					"prepare-poshi-runner-properties", parameters);
			}
			catch (AntException antException) {
				throw new RuntimeException(antException);
			}

			Properties properties = JenkinsResultsParserUtil.getProperties(
				new File(portalWorkingDirectory, "portal-web/poshi.properties"),
				new File(
					portalWorkingDirectory, "portal-web/poshi-ext.properties"),
				new File(testBaseDir, "test.properties"));

			if (!JenkinsResultsParserUtil.isNullOrEmpty(testBaseDirPath)) {
				properties.setProperty("test.base.dir.name", testBaseDirPath);
			}

			PropsUtil.clear();

			PropsUtil.setProperties(properties);

			try {
				PoshiContext.clear();

				PoshiContext.readFiles();

				return getTestClassGroups(
					PoshiContext.getTestBatchGroups(query, getAxisMaxSize()));
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

}