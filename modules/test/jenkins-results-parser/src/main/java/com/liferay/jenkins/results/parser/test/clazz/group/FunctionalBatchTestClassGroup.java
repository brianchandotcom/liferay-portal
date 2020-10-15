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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.poshi.core.PoshiContext;

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.List;
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
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		return super.getAxisCount();
	}

	@Override
	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		if (axisId != 0) {
			throw new IllegalArgumentException("axisId is not 0");
		}

		return super.getAxisTestClassGroup(axisId);
	}

	public String getRelevantTestBatchRunPropertyQuery() {
		return _relevantTestBatchRunPropertyQuery;
	}

	public static class FunctionalTestClass extends BaseTestClass {

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
		String batchName, BuildProfile buildProfile,
		PortalTestClassJob portalTestClassJob) {

		super(batchName, buildProfile, portalTestClassJob);

		axisTestClassGroups.add(0, new AxisTestClassGroup(this));

		_setRelevantTestBatchRunPropertyQuery();
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

	private String _relevantTestBatchRunPropertyQuery;

}