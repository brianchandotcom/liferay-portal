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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
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

		jsonObject.put("target_duration", getTargetAxisDuration());
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

	@Override
	protected int getAxisMaxSize() {
		long targetAxisDuration = getTargetAxisDuration();

		if (targetAxisDuration > 0) {
			return AXES_SIZE_MAX_DEFAULT;
		}

		return super.getAxisMaxSize();
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

	protected List<List<TestClass>> getPoshiTestClassGroups(File testBaseDir) {
		String query = getTestBatchRunPropertyQuery(testBaseDir);

		if (JenkinsResultsParserUtil.isNullOrEmpty(query)) {
			return new ArrayList<>();
		}

		synchronized (_poshiTestCasePattern) {
			File cachedTestBaseDir = _testBaseDirAtomicReference.get();

			if ((cachedTestBaseDir == null) ||
				!cachedTestBaseDir.equals(testBaseDir)) {

				_testBaseDirAtomicReference.set(testBaseDir);

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
						portalWorkingDirectory, "portal-web/poshi.properties"),
					new File(
						portalWorkingDirectory,
						"portal-web/poshi-ext.properties"));

				if (!JenkinsResultsParserUtil.isNullOrEmpty(testBaseDirPath)) {
					properties.setProperty(
						"test.base.dir.name", testBaseDirPath);
				}

				PropsUtil.clear();

				PropsUtil.setProperties(properties);

				try {
					PoshiContext.clear();

					PoshiContext.readFiles();
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}

			try {
				return getTestClassGroups(
					PoshiContext.getTestBatchGroups(query, getAxisMaxSize()));
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	protected List<List<TestClass>> getTestClassGroups(
		List<List<String>> testBatchGroups) {

		List<List<TestClass>> testClassGroups = new ArrayList<>();

		if (testBatchGroups.isEmpty()) {
			return testClassGroups;
		}

		for (List<String> testBatchGroup : testBatchGroups) {
			List<TestClass> testClassGroup = new ArrayList<>();

			for (String testClassMethodName : testBatchGroup) {
				testClassGroup.add(
					TestClassFactory.newTestClass(this, testClassMethodName));
			}

			testClassGroups.add(testClassGroup);
		}

		return testClassGroups;
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

			List<List<TestClass>> poshiTestClassGroups =
				getPoshiTestClassGroups(testBaseDir);

			long targetAxisDuration = getTargetAxisDuration();

			for (List<TestClass> poshiTestClassGroup : poshiTestClassGroups) {
				if (poshiTestClassGroup.isEmpty()) {
					continue;
				}

				if (targetAxisDuration > 0) {
					Collections.sort(
						poshiTestClassGroup, new TestClassDurationComparator());

					while (!poshiTestClassGroup.isEmpty()) {
						List<TestClass> axisTestClasses = new ArrayList<>();

						for (TestClass poshiTestClass : poshiTestClassGroup) {
							if (axisTestClasses.isEmpty()) {
								axisTestClasses.add(poshiTestClass);

								continue;
							}

							long duration = 0L;
							long totalOverheadDuration = 0L;

							for (TestClass axisTestClass : axisTestClasses) {
								duration += axisTestClass.getAverageDuration();
								totalOverheadDuration +=
									axisTestClass.getAverageOverheadDuration();
							}

							duration += poshiTestClass.getAverageDuration();
							totalOverheadDuration +=
								poshiTestClass.getAverageOverheadDuration();

							duration +=
								totalOverheadDuration /
									(axisTestClasses.size() + 1);

							if (duration >= targetAxisDuration) {
								continue;
							}

							axisTestClasses.add(poshiTestClass);
						}

						poshiTestClassGroup.removeAll(axisTestClasses);

						AxisTestClassGroup axisTestClassGroup =
							TestClassGroupFactory.newAxisTestClassGroup(
								this, testBaseDir);

						for (TestClass axisTestClass : axisTestClasses) {
							axisTestClassGroup.addTestClass(axisTestClass);
						}

						axisTestClassGroups.add(axisTestClassGroup);
					}
				}
				else {
					AxisTestClassGroup axisTestClassGroup =
						TestClassGroupFactory.newAxisTestClassGroup(
							this, testBaseDir);

					for (TestClass testClass : poshiTestClassGroup) {
						axisTestClassGroup.addTestClass(testClass);
					}

					axisTestClassGroups.add(axisTestClassGroup);
				}
			}
		}
	}

	private String _concatPQL(File file, String concatedPQL) {
		if (file == null) {
			return null;
		}

		if (JenkinsResultsParserUtil.isPoshiFile(file)) {
			return "";
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

		File testPropertiesFile = new File(canonicalFile, "test.properties");

		if (modulesBaseDirPath.equals(parentFilePath) &&
			!testPropertiesFile.exists()) {

			return concatedPQL;
		}

		if (!canonicalFile.isDirectory() || !testPropertiesFile.exists()) {
			return _concatPQL(parentFile, concatedPQL);
		}

		if (_traversedPropertyFiles.contains(testPropertiesFile)) {
			return concatedPQL;
		}

		_traversedPropertyFiles.add(testPropertiesFile);

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

		Properties testProperties = JenkinsResultsParserUtil.getProperties(
			testPropertiesFile);

		boolean ignoreParents = Boolean.valueOf(
			JenkinsResultsParserUtil.getProperty(
				testProperties, "ignoreParents", false, getTestSuiteName()));

		if (ignoreParents ||
			parentFile.equals(
				portalGitWorkingDirectory.getWorkingDirectory())) {

			return concatedPQL;
		}

		if (!parentFilePath.equals(modulesBaseDirPath)) {
			return _concatPQL(parentFile, concatedPQL);
		}

		return concatedPQL;
	}

	private String _getTestBatchRunPropertyQuery(File testBaseDir) {
		if (!testRelevantChanges) {
			return getDefaultTestBatchRunPropertyQuery(
				testBaseDir, testSuiteName);
		}

		StringBuilder sb = new StringBuilder();

		for (File modifiedFile :
				portalGitWorkingDirectory.getModifiedFilesList()) {

			String testBatchPQL = _concatPQL(modifiedFile, "");

			if (JenkinsResultsParserUtil.isNullOrEmpty(testBatchPQL) ||
				testBatchPQL.equals("false")) {

				continue;
			}

			if (sb.indexOf(testBatchPQL) == -1) {
				if (!JenkinsResultsParserUtil.isNullOrEmpty(sb.toString())) {
					sb.append(" OR ");
				}

				sb.append("(");
				sb.append(testBatchPQL);
				sb.append(")");
			}
		}

		String defaultPQL = getDefaultTestBatchRunPropertyQuery(
			testBaseDir, testSuiteName);

		if (!JenkinsResultsParserUtil.isNullOrEmpty(defaultPQL) &&
			(sb.indexOf(defaultPQL) == -1)) {

			if (sb.length() > 0) {
				sb.append(" OR ");
			}

			sb.append("(");
			sb.append(defaultPQL);
			sb.append(")");
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
				(sb.indexOf(jobPropertyValue) == -1)) {

				recordJobProperty(jobProperty);

				if (sb.length() > 0) {
					sb.append(" OR ");
				}

				sb.append("(");
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
	private static final AtomicReference<File> _testBaseDirAtomicReference =
		new AtomicReference<>();

	private final Map<File, String> _testBatchRunPropertyQueries =
		new HashMap<>();
	private final Set<File> _traversedPropertyFiles = new HashSet<>();

}