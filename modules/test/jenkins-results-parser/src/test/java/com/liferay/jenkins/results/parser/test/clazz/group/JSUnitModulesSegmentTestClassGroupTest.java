/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.job.property.JobPropertyFactory;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Peter Yoo
 */
public class JSUnitModulesSegmentTestClassGroupTest
	extends com.liferay.jenkins.results.parser.Test {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_workingDirectory = BatchTestClassGroupTestUtil.newGitWorkingDirectory(
			temporaryFolder.getRoot());

		_moduleDirPath1 = BatchTestClassGroupTestUtil.newJSUnitModuleDirPath(
			_workingDirectory);
		_moduleDirPath2 = BatchTestClassGroupTestUtil.newJSUnitModuleDirPath(
			_workingDirectory);
	}

	@Test
	public void testGetTestTaskNames() {
		_testGetTestTaskNames(false, null);
		_testGetTestTaskNames(
			true,
			_getJobProperties(
				"test.batch.test.file.excludes[js-unit]", "**/c4.test.js"));
		_testGetTestTaskNames(
			true,
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]", "**/b2.test.js"));
		_testGetTestTaskNames(
			true,
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]",
				"**/test/js/a*.test.js"));
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private Properties _getJobProperties(String key, String value) {
		Properties jobProperties = new Properties();

		jobProperties.setProperty(key, value);

		return jobProperties;
	}

	private Map<String, Set<String>> _getTestFilePathsMap(
		String testTaskNames) {

		Map<String, Set<String>> testFilePathsMap = new HashMap<>();

		for (String testTaskName : testTaskNames.split(",")) {
			List<String> tokens = Arrays.asList(testTaskName.split("#"));

			testFilePathsMap.put(
				tokens.get(0), new TreeSet<>(tokens.subList(1, tokens.size())));
		}

		return testFilePathsMap;
	}

	private void _testGetTestTaskNames(
		boolean expectedTestFilePaths, Properties jobProperties) {

		JobPropertyFactory.clear();

		List<File> jsUnitFiles = new ArrayList<>();

		for (String jsUnitFilePath :
				Arrays.asList(
					_moduleDirPath1 + "/test/js/a1.test.js",
					_moduleDirPath1 + "/test/js/b2.test.js",
					_moduleDirPath2 + "/test/js/a3.test.js",
					_moduleDirPath2 + "/test/js/c4.test.js")) {

			jsUnitFiles.add(new File(_workingDirectory, jsUnitFilePath));
		}

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			BatchTestClassGroupTestUtil.newJSUnitModulesBatchTestClassGroup(
				Arrays.asList(
					new File(_workingDirectory, _moduleDirPath1),
					new File(_workingDirectory, _moduleDirPath2)),
				jobProperties, jsUnitFiles, _workingDirectory);

		JSONObject jsonObject =
			jsUnitModulesBatchTestClassGroup.getJSONObject();

		BatchTestClassGroup batchTestClassGroup =
			TestClassGroupFactory.newBatchTestClassGroup(
				jsUnitModulesBatchTestClassGroup.getJob(),
				new JSONObject(jsonObject.toString()));

		List<SegmentTestClassGroup> segmentTestClassGroups =
			jsUnitModulesBatchTestClassGroup.getSegmentTestClassGroups();

		List<SegmentTestClassGroup> rebuiltSegmentTestClassGroups =
			batchTestClassGroup.getSegmentTestClassGroups();

		Assert.assertFalse(segmentTestClassGroups.isEmpty());

		testEquals(
			segmentTestClassGroups.size(),
			rebuiltSegmentTestClassGroups.size());

		for (int i = 0; i < segmentTestClassGroups.size(); i++) {
			JSUnitModulesSegmentTestClassGroup
				jsUnitModulesSegmentTestClassGroup =
					(JSUnitModulesSegmentTestClassGroup)
						segmentTestClassGroups.get(i);

			JSUnitModulesSegmentTestClassGroup
				rebuiltJSUnitModulesSegmentTestClassGroup =
					(JSUnitModulesSegmentTestClassGroup)
						rebuiltSegmentTestClassGroups.get(i);

			for (int axisIndex = 0;
				 axisIndex < jsUnitModulesSegmentTestClassGroup.getAxisCount();
				 axisIndex++) {

				List<String> expectedTestTaskNames = new ArrayList<>();

				Map<String, Set<String>> expectedTestFilePathsMap =
					new HashMap<>();

				AxisTestClassGroup axisTestClassGroup =
					jsUnitModulesSegmentTestClassGroup.getAxisTestClassGroup(
						axisIndex);

				for (TestClass testClass :
						axisTestClassGroup.getTestClasses()) {

					String testTaskName = testClass.getTestTaskName();

					expectedTestTaskNames.add(testTaskName);

					Set<String> testFilePaths = new TreeSet<>();

					if (expectedTestFilePaths) {
						for (TestClassMethod testClassMethod :
								testClass.getTestClassMethods()) {

							testFilePaths.add(testClassMethod.getName());
						}
					}

					expectedTestFilePathsMap.put(testTaskName, testFilePaths);
				}

				String testTaskNames =
					jsUnitModulesSegmentTestClassGroup.getTestTaskNames(
						axisIndex);

				testEquals(
					expectedTestFilePathsMap,
					_getTestFilePathsMap(testTaskNames));

				if (!expectedTestFilePaths) {
					testEquals(
						JenkinsResultsParserUtil.join(
							",", expectedTestTaskNames),
						testTaskNames);
				}

				testEquals(
					testTaskNames,
					rebuiltJSUnitModulesSegmentTestClassGroup.getTestTaskNames(
						axisIndex));
			}
		}
	}

	private String _moduleDirPath1;
	private String _moduleDirPath2;
	private File _workingDirectory;

}