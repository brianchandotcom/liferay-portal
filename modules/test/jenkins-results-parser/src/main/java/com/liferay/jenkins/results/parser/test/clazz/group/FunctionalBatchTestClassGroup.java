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
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PluginsGitRepositoryJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.util.PropsUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
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

	public String getRelevantTestBatchRunPropertyQuery() {
		return _relevantTestBatchRunPropertyQuery;
	}

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		List<String> segmentIndexes = new ArrayList<>();

		for (int segmentIndex = 0; segmentIndex < getSegmentCount();
			 segmentIndex++) {

			SegmentTestClassGroup segmentTestClassGroup =
				getSegmentTestClassGroup(segmentIndex);

			sb.append(segmentTestClassGroup.getTestCasePropertiesContent());

			sb.append("\n");

			segmentIndexes.add(String.valueOf(segmentIndex));
		}

		sb.append("RUN_TEST_CASE_METHOD_GROUPS=");
		sb.append(JenkinsResultsParserUtil.join(" ", segmentIndexes));
		sb.append("\n");

		return sb.toString();
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
			return PoshiContext.getNamespacedClassCommandNameProperties(
				getTestClassMethodName());
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
		}

		private static File _getTestClassFile(String testClassMethodName) {
			Matcher matcher = _poshiTestCasePattern.matcher(
				testClassMethodName);

			if (!matcher.find()) {
				throw new RuntimeException(
					"Invalid test class method name " + testClassMethodName);
			}

			return new File(
				PoshiContext.getFilePathFromFileName(
					matcher.group("className") + ".testcase",
					matcher.group("namespace")));
		}

		private final String _testClassMethodName;

	}

	protected FunctionalBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		_portalTestClassJob = portalTestClassJob;

		_setRelevantTestBatchRunPropertyQuery();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	protected String getTestBaseDirName() {
		Job job = getJob();

		if (job instanceof PluginsGitRepositoryJob) {
			PluginsGitRepositoryJob pluginsGitRepositoryJob =
				(PluginsGitRepositoryJob)job;

			File pluginTestBaseDir =
				pluginsGitRepositoryJob.getPluginTestBaseDir();

			if ((pluginTestBaseDir != null) && pluginTestBaseDir.exists()) {
				return JenkinsResultsParserUtil.getCanonicalPath(
					pluginTestBaseDir);
			}
		}

		String testBaseDirName = System.getenv("test.base.dir.name");

		if ((testBaseDirName == null) || !testBaseDirName.isEmpty()) {
			testBaseDirName = System.getenv("env.TEST_BASE_DIR_NAME");
		}

		if ((testBaseDirName == null) || !testBaseDirName.isEmpty()) {
			testBaseDirName = System.getenv("TEST_BASE_DIR_NAME");
		}

		if ((testBaseDirName == null) || !testBaseDirName.isEmpty()) {
			return null;
		}

		File testBaseDir = new File(testBaseDirName);

		if (!testBaseDir.exists() || !testBaseDir.isDirectory()) {
			return null;
		}

		try {
			return testBaseDir.getCanonicalPath();
		}
		catch (IOException ioException) {
			return null;
		}
	}

	@Override
	protected void setAxisTestClassGroups() {
		if (!axisTestClassGroups.isEmpty()) {
			return;
		}

		for (List<String> poshiTestClassGroup : _getPoshiTestClassGroups()) {
			if (poshiTestClassGroup.isEmpty()) {
				continue;
			}

			AxisTestClassGroup axisTestClassGroup =
				TestClassGroupFactory.newAxisTestClassGroup(this);

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

	private String _getDefaultTestBatchRunPropertyGlobalQuery(
		String testSuiteName) {

		return getFirstPropertyValue(
			"test.batch.run.property.global.query", batchName, testSuiteName);
	}

	private String _getDefaultTestBatchRunPropertyQuery(String testSuiteName) {
		return getFirstPropertyValue(
			"test.batch.run.property.query", batchName, testSuiteName);
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

	private List<List<String>> _getPoshiTestClassGroups() {
		String query = getRelevantTestBatchRunPropertyQuery();

		if (query == null) {
			return new ArrayList<>();
		}

		synchronized (_portalTestClassJob) {
			PortalGitWorkingDirectory portalGitWorkingDirectory =
				_portalTestClassJob.getPortalGitWorkingDirectory();

			File portalDir = portalGitWorkingDirectory.getWorkingDirectory();

			Map<String, String> parameters = new HashMap<>();

			String testBaseDirName = getTestBaseDirName();

			if (testBaseDirName != null) {
				parameters.put("test.base.dir.name", testBaseDirName);
			}

			try {
				AntUtil.callTarget(
					portalDir, "build-test.xml",
					"prepare-poshi-runner-properties", parameters);
			}
			catch (AntException antException) {
				throw new RuntimeException(antException);
			}

			Properties properties = JenkinsResultsParserUtil.getProperties(
				new File(
					portalDir, "portal-web/test/test-portal-web.properties"),
				new File(
					portalDir,
					"portal-web/test/test-portal-web-ext.properties"));

			properties.setProperty("ignore.errors.util.classes", "true");

			for (String propertyName : properties.stringPropertyNames()) {
				String propertyValue = properties.getProperty(propertyName);

				if (propertyValue == null) {
					continue;
				}

				PropsUtil.set(propertyName, propertyValue);
			}

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

	private void _setRelevantTestBatchRunPropertyQuery() {
		if (!testRelevantChanges) {
			_relevantTestBatchRunPropertyQuery =
				_getDefaultTestBatchRunPropertyQuery(testSuiteName);

			return;
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
			sb.append(_getDefaultTestBatchRunPropertyQuery(testSuiteName));
			sb.append(")");
		}

		String stableTestBatchRunPropertyQuery =
			_getDefaultTestBatchRunPropertyQuery(NAME_STABLE_TEST_SUITE);

		if ((stableTestBatchRunPropertyQuery != null) &&
			includeStableTestSuite && isStableTestSuiteBatch()) {

			sb.append(" OR (");
			sb.append(stableTestBatchRunPropertyQuery);
			sb.append(")");
		}

		_relevantTestBatchRunPropertyQuery = sb.toString();

		String defaultGlobalQuery = _getDefaultTestBatchRunPropertyGlobalQuery(
			testSuiteName);

		if ((defaultGlobalQuery != null) && !defaultGlobalQuery.isEmpty()) {
			_relevantTestBatchRunPropertyQuery =
				JenkinsResultsParserUtil.combine(
					"(", defaultGlobalQuery, ") AND (",
					_relevantTestBatchRunPropertyQuery, ")");
		}
	}

	private static final Pattern _poshiTestCasePattern = Pattern.compile(
		"(?<namespace>[^\\.]+)\\.(?<className>[^\\#]+)\\#(?<methodName>.*)");

	private final PortalTestClassJob _portalTestClassJob;
	private String _relevantTestBatchRunPropertyQuery;

}