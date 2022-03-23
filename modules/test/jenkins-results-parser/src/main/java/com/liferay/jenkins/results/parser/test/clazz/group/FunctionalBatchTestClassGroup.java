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

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.AntException;
import com.liferay.jenkins.results.parser.AntUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;
import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.util.PropsUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Yi-Chen Tsai
 */
public class FunctionalBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		return axisTestClassGroups.size();
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		StringBuilder sb = new StringBuilder();

		sb.append("(");
		sb.append(getTestBatchRunPropertyQuery());
		sb.append(") AND (ignored == null)");

		String testRunEnvironment = PropsUtil.get("test.run.environment");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testRunEnvironment)) {
			sb.append(" AND (test.run.environment == \"");
			sb.append(testRunEnvironment);
			sb.append("\" OR test.run.environment == null)");
		}

		jsonObject.put("pql_query", sb.toString());

		jsonObject.put(
			"test_batch_run_property_queries", _testBatchRunPropertyQueries);

		return jsonObject;
	}

	public List<File> getTestBaseDirs() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		return Arrays.asList(
			new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"portal-web/test/functional/portalweb"));
	}

	public String getTestBatchRunPropertyQuery() {
		List<File> testBaseDirs = getTestBaseDirs();

		if (testBaseDirs.isEmpty()) {
			return null;
		}

		return getTestBatchRunPropertyQuery(testBaseDirs.get(0));
	}

	public String getTestBatchRunPropertyQuery(File testBaseDir) {
		return _testBatchRunPropertyQueries.get(testBaseDir);
	}

	@Override
	public List<TestClass> getTestClasses() {
		List<TestClass> testClasses = new ArrayList<>();

		for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
			testClasses.addAll(axisTestClassGroup.getTestClasses());
		}

		return testClasses;
	}

	protected FunctionalBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);

		JSONObject testBatchRunPropertyQueriesJSONObject =
			jsonObject.optJSONObject("test_batch_run_property_queries");

		if (testBatchRunPropertyQueriesJSONObject == null) {
			return;
		}

		for (String key : testBatchRunPropertyQueriesJSONObject.keySet()) {
			_testBatchRunPropertyQueries.put(
				new File(key),
				testBatchRunPropertyQueriesJSONObject.getString(key));
		}
	}

	protected FunctionalBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		_setTestBatchRunPropertyQueries();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

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
			"test.batch.run.property.query", testSuiteName, batchName);

		recordJobProperty(jobProperty);

		return jobProperty.getValue();
	}

	protected List<List<String>> getPoshiTestClassGroups(File testBaseDir) {
		String query = getTestBatchRunPropertyQuery(testBaseDir);

		if (JenkinsResultsParserUtil.isNullOrEmpty(query)) {
			return new ArrayList<>();
		}

		synchronized (_poshiTestCasePattern) {
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
				new File(
					portalWorkingDirectory, "portal-web/poshi-ext.properties"));

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

	@Override
	protected void setAxisTestClassGroups() {
		if (!axisTestClassGroups.isEmpty()) {
			return;
		}

		for (File testBaseDir : getTestBaseDirs()) {
			String query = getTestBatchRunPropertyQuery(testBaseDir);

			if (query == null) {
				continue;
			}

			List<List<String>> poshiTestClassGroups = getPoshiTestClassGroups(
				testBaseDir);

			for (List<String> poshiTestClassGroup : poshiTestClassGroups) {
				if (poshiTestClassGroup.isEmpty()) {
					continue;
				}

				AxisTestClassGroup axisTestClassGroup =
					TestClassGroupFactory.newAxisTestClassGroup(
						this, testBaseDir);

				for (String testClassMethodName : poshiTestClassGroup) {
					Matcher matcher = _poshiTestCasePattern.matcher(
						testClassMethodName);

					if (!matcher.find()) {
						throw new RuntimeException(
							"Invalid test class method name " +
								testClassMethodName);
					}

					axisTestClassGroup.addTestClass(
						TestClassFactory.newTestClass(
							this, testClassMethodName));
				}

				axisTestClassGroups.add(axisTestClassGroup);
			}
		}
	}

	private List<File> _getFunctionalRequiredModuleDirs(List<File> moduleDirs) {
		List<File> functionalRequiredModuleDirs = Lists.newArrayList(
			moduleDirs);

		File modulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		for (File moduleDir : moduleDirs) {
			JobProperty jobProperty = getJobProperty(
				"modules.includes.required.functional", moduleDir,
				JobProperty.Type.MODULE_TEST_DIR);

			String jobPropertyValue = jobProperty.getValue();

			if (jobPropertyValue == null) {
				continue;
			}

			recordJobProperty(jobProperty);

			for (String functionalRequiredModuleDirPath :
					jobPropertyValue.split(",")) {

				File functionalRequiredModuleDir = new File(
					modulesBaseDir, functionalRequiredModuleDirPath);

				if (!functionalRequiredModuleDir.exists() ||
					functionalRequiredModuleDirs.contains(
						functionalRequiredModuleDir)) {

					continue;
				}

				functionalRequiredModuleDirs.add(functionalRequiredModuleDir);
			}
		}

		return Lists.newArrayList(functionalRequiredModuleDirs);
	}

	private String _getTestBatchRunPropertyQuery(File testBaseDir) {
		if (!testRelevantChanges) {
			return getDefaultTestBatchRunPropertyQuery(
				testBaseDir, testSuiteName);
		}

		Set<File> modifiedDirsList = new HashSet<>();

		try {
			modifiedDirsList.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList());
		}
		catch (IOException ioException) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get module directories in ",
					workingDirectory.getPath()),
				ioException);
		}

		File modulesDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		modifiedDirsList.addAll(
			portalGitWorkingDirectory.getModifiedDirsList(
				false,
				JenkinsResultsParserUtil.toPathMatchers(
					null,
					JenkinsResultsParserUtil.getCanonicalPath(modulesDir)),
				null));

		modifiedDirsList.addAll(
			getRequiredModuleDirs(Lists.newArrayList(modifiedDirsList)));

		modifiedDirsList.addAll(
			_getFunctionalRequiredModuleDirs(
				Lists.newArrayList(modifiedDirsList)));

		StringBuilder sb = new StringBuilder();

		for (File modifiedDir : modifiedDirsList) {
			JobProperty jobProperty = getJobProperty(
				"test.batch.run.property.query", modifiedDir,
				JobProperty.Type.MODULE_TEST_DIR, false);

			String jobPropertyValue = jobProperty.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue) ||
				jobPropertyValue.equals("false")) {

				continue;
			}

			recordJobProperty(jobProperty);

			if (sb.length() > 0) {
				sb.append(" OR (");
			}
			else {
				sb.append("(");
			}

			sb.append(jobPropertyValue);
			sb.append(")");
		}

		if (sb.length() > 0) {
			sb.append(" OR ");
		}

		sb.append("(");

		sb.append(
			getDefaultTestBatchRunPropertyQuery(testBaseDir, testSuiteName));

		sb.append(")");

		if (!NAME_STABLE_TEST_SUITE.equals(getTestSuiteName())) {
			String batchName = getBatchName();

			if (!batchName.endsWith("_stable")) {
				batchName += "_stable";
			}

			JobProperty jobProperty = getJobProperty(
				"test.batch.run.property.query", NAME_STABLE_TEST_SUITE,
				batchName);

			String jobPropertyValue = jobProperty.getValue();

			if ((jobPropertyValue != null) && includeStableTestSuite &&
				isStableTestSuiteBatch(batchName)) {

				recordJobProperty(jobProperty);

				sb.append(" OR (");
				sb.append(jobPropertyValue);
				sb.append(")");
			}
		}

		String testBatchRunPropertyQuery = sb.toString();

		JobProperty jobProperty = getJobProperty(
			"test.batch.run.property.global.query");

		String jobPropertyValue = jobProperty.getValue();

		if (jobPropertyValue != null) {
			recordJobProperty(jobProperty);

			testBatchRunPropertyQuery = JenkinsResultsParserUtil.combine(
				"(", jobPropertyValue, ") AND (", testBatchRunPropertyQuery,
				")");
		}

		return testBatchRunPropertyQuery;
	}

	private void _setTestBatchRunPropertyQueries() {
		for (File testBaseDir : getTestBaseDirs()) {
			String testBatchRunPropertyQuery = _getTestBatchRunPropertyQuery(
				testBaseDir);

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					testBatchRunPropertyQuery)) {

				continue;
			}

			_testBatchRunPropertyQueries.put(
				testBaseDir, testBatchRunPropertyQuery);
		}
	}

	private static final Pattern _poshiTestCasePattern = Pattern.compile(
		"(?<namespace>[^\\.]+)\\.(?<className>[^\\#]+)\\#(?<methodName>.*)");

	private final Map<File, String> _testBatchRunPropertyQueries =
		new HashMap<>();

}