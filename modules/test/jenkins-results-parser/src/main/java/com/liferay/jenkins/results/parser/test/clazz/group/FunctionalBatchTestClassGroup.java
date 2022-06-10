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

import java.nio.file.Path;

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
				new File(portalWorkingDirectory, "portal-web/poshi.properties"),
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

	private String _concatPQL(File file, File testBaseDir, String concatedPQL) {
		if (file == null) {
			return null;
		}

		File canonicalFile = JenkinsResultsParserUtil.getCanonicalFile(file);

		File parentFile = canonicalFile.getParentFile();

		if ((parentFile == null) || !parentFile.exists()) {
			return "";
		}

		File modulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		Path modulesBaseDirPath = modulesBaseDir.toPath();

		Path parentFilePath = parentFile.toPath();

		if (parentFilePath.equals(modulesBaseDirPath)) {
			return concatedPQL;
		}

		if (!canonicalFile.isDirectory()) {
			return _concatPQL(parentFile, testBaseDir, concatedPQL);
		}

		File testPropertiesFile = new File(canonicalFile, "test.properties");

		if (!testPropertiesFile.exists()) {
			return _concatPQL(parentFile, testBaseDir, concatedPQL);
		}

		JobProperty jobProperty = getJobProperty(
			"test.batch.run.property.query", getTestSuiteName(), batchName,
			canonicalFile, JobProperty.Type.MODULE_TEST_DIR);

		String testBatchPropertyQuery = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testBatchPropertyQuery) &&
			!testBatchPropertyQuery.equals("false") &&
			!concatedPQL.contains(testBatchPropertyQuery)) {

			recordJobProperty(jobProperty);

			if (!concatedPQL.isEmpty()) {
				concatedPQL += JenkinsResultsParserUtil.combine(
					" OR (", testBatchPropertyQuery, ")");
			}
			else {
				concatedPQL += testBatchPropertyQuery;
			}
		}

		if (!parentFilePath.equals(modulesBaseDirPath)) {
			return _concatPQL(parentFile, testBaseDir, concatedPQL);
		}

		return concatedPQL;
	}

	private String _getTestBatchRunPropertyQuery(File testBaseDir) {
		if (!testRelevantChanges) {
			return getDefaultTestBatchRunPropertyQuery(
				testBaseDir, testSuiteName);
		}

		Set<File> modifiedFilesList = new HashSet<>();

		modifiedFilesList.addAll(
			portalGitWorkingDirectory.getModifiedFilesList());

		StringBuilder sb = new StringBuilder();

		String sbToString = sb.toString();

		for (File modifiedFile : modifiedFilesList) {
			String testBatchPQL = _concatPQL(
				modifiedFile, testBaseDir, new String());

			if (JenkinsResultsParserUtil.isNullOrEmpty(testBatchPQL) ||
				testBatchPQL.equals("false")) {

				continue;
			}

			if (!sbToString.contains(testBatchPQL)) {
				if (!JenkinsResultsParserUtil.isNullOrEmpty(sbToString)) {
					sb.append(" OR ");
				}

				sb.append("(");
				sb.append(testBatchPQL);

				sb.append(")");

				sbToString = sb.toString();
			}
		}

		String defaultPQL = getDefaultTestBatchRunPropertyQuery(
			testBaseDir, testSuiteName);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(defaultPQL) &&
			!sbToString.contains(defaultPQL)) {

			sb.append(" OR (");

			sb.append(defaultPQL);

			sb.append(")");

			sbToString = sb.toString();
		}

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
				isStableTestSuiteBatch(batchName) &&
				!sbToString.contains(jobPropertyValue)) {

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

	// 	private static final String _combinedTestBatchRunPropertyQuery =

	//		new String();
	private static final Pattern _poshiTestCasePattern = Pattern.compile(
		"(?<namespace>[^\\.]+)\\.(?<className>[^\\#]+)\\#(?<methodName>.*)");

	private final Map<File, String> _testBatchRunPropertyQueries =
		new HashMap<>();

}