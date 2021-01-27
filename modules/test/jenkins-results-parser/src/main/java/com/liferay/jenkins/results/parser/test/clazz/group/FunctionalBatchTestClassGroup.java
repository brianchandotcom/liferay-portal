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

/**
 * @author Yi-Chen Tsai
 */
public class FunctionalBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		return axisTestClassGroups.size();
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

	public static class FunctionalTestClass extends BaseTestClass {

		public Properties getPoshiProperties() {
			return _poshiProperties;
		}

		public String getTestClassMethodName() {
			return _testClassMethodName;
		}

		protected static FunctionalTestClass getInstance(
			String testClassMethodName) {

			return new FunctionalTestClass(testClassMethodName);
		}

		protected FunctionalTestClass(String testClassMethodName) {
			super(_getTestClassFile(testClassMethodName));

			addTestClassMethod(testClassMethodName);

			_testClassMethodName = testClassMethodName;

			_poshiProperties =
				PoshiContext.getNamespacedClassCommandNameProperties(
					getTestClassMethodName());
		}

		private static File _getTestClassFile(String testClassMethodName) {
			Matcher matcher = _poshiTestCasePattern.matcher(
				testClassMethodName);

			if (!matcher.find()) {
				throw new RuntimeException(
					"Invalid test class method name " + testClassMethodName);
			}

			String className = matcher.group("className");
			String namespace = matcher.group("namespace");

			File testClassFile = null;

			try {
				testClassFile = new File(
					PoshiContext.getFilePathFromFileName(
						className + ".testcase", namespace));
			}
			catch (Exception exception) {
				testClassFile = new File(
					PoshiContext.getFilePathFromFileName(
						className + ".prose", namespace));
			}

			return testClassFile;
		}

		private final Properties _poshiProperties;
		private final String _testClassMethodName;

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

		String propertyQuery = System.getenv("TEST_BATCH_RUN_PROPERTY_QUERY");

		if ((propertyQuery != null) && !propertyQuery.isEmpty()) {
			return propertyQuery;
		}

		return JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.run.property.query", batchName,
			testSuiteName, getJobName());
	}

	protected List<List<String>> getPoshiTestClassGroups(File testBaseDir) {
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
				new File(
					portalWorkingDirectory,
					"portal-web/test/test-portal-web.properties"),
				new File(
					portalWorkingDirectory,
					"portal-web/test/test-portal-web-ext.properties"));

			properties.setProperty("ignore.errors.util.classes", "true");

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
						FunctionalTestClass.getInstance(testClassMethodName));
				}

				axisTestClassGroups.add(axisTestClassGroup);
			}
		}
	}

	private String _getDefaultTestBatchRunPropertyGlobalQuery(
		String testSuiteName) {

		return JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.run.property.global.query", batchName,
			testSuiteName, getJobName());
	}

	private List<File> _getFunctionalRequiredModuleDirs(List<File> moduleDirs) {
		List<File> functionalRequiredModuleDirs = Lists.newArrayList(
			moduleDirs);

		File modulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		for (File moduleDir : moduleDirs) {
			Properties moduleDirTestProperties =
				JenkinsResultsParserUtil.getProperties(
					new File(moduleDir, "test.properties"));

			String functionalRequiredModuleDirPaths =
				moduleDirTestProperties.getProperty(
					"modules.includes.required.functional[" + testSuiteName +
						"]");

			if (functionalRequiredModuleDirPaths == null) {
				continue;
			}

			for (String functionalRequiredModuleDirPath :
					functionalRequiredModuleDirPaths.split(",")) {

				File functionalRequiredModuleDir = new File(
					modulesBaseDir, functionalRequiredModuleDirPath);

				if (!functionalRequiredModuleDir.exists()) {
					continue;
				}

				if (functionalRequiredModuleDirs.contains(
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
			File modifiedDirTestProperties = new File(
				modifiedDir, "test.properties");

			if (!modifiedDirTestProperties.exists()) {
				continue;
			}

			Properties testProperties = JenkinsResultsParserUtil.getProperties(
				modifiedDirTestProperties);

			String testBatchRunPropertyQuery = null;

			String firstMatchingPropertyName = getFirstMatchingPropertyName(
				"test.batch.run.property.query", testProperties, testSuiteName);

			if (firstMatchingPropertyName != null) {
				testBatchRunPropertyQuery =
					JenkinsResultsParserUtil.getProperty(
						testProperties, firstMatchingPropertyName);
			}

			if (testBatchRunPropertyQuery == null) {
				testBatchRunPropertyQuery =
					JenkinsResultsParserUtil.getProperty(
						testProperties,
						JenkinsResultsParserUtil.combine(
							"test.batch.run.property.query[", batchName, "][",
							testSuiteName, "]"));
			}

			if (testBatchRunPropertyQuery != null) {
				if (sb.length() > 0) {
					sb.append(" OR (");
				}
				else {
					sb.append("(");
				}

				sb.append(testBatchRunPropertyQuery);
				sb.append(")");
			}
		}

		if (sb.length() == 0) {
			sb.append("(");

			sb.append(
				getDefaultTestBatchRunPropertyQuery(
					testBaseDir, testSuiteName));

			sb.append(")");
		}

		String stableTestBatchRunPropertyQuery =
			getDefaultTestBatchRunPropertyQuery(
				testBaseDir, NAME_STABLE_TEST_SUITE);

		if ((stableTestBatchRunPropertyQuery != null) &&
			includeStableTestSuite && isStableTestSuiteBatch()) {

			sb.append(" OR (");
			sb.append(stableTestBatchRunPropertyQuery);
			sb.append(")");
		}

		String testBatchRunPropertyQuery = sb.toString();

		String defaultGlobalQuery = _getDefaultTestBatchRunPropertyGlobalQuery(
			testSuiteName);

		if ((defaultGlobalQuery != null) && !defaultGlobalQuery.isEmpty()) {
			testBatchRunPropertyQuery = JenkinsResultsParserUtil.combine(
				"(", defaultGlobalQuery, ") AND (", testBatchRunPropertyQuery,
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