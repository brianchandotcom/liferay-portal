/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.job.property.JobPropertyFactory;
import com.liferay.jenkins.results.parser.test.clazz.JSUnitJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Brittney Nguyen
 */
public class JSUnitModulesBatchTestClassGroupTest
	extends com.liferay.jenkins.results.parser.Test {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_workingDirectory = JenkinsResultsParserUtil.getCanonicalFile(
			temporaryFolder.getRoot());

		File gitDir = new File(_workingDirectory, ".git");

		gitDir.mkdir();

		_moduleDirPath1 = _newModuleDirPath();
		_moduleDirPath2 = _newModuleDirPath();

		_jsUnitFilePaths = Arrays.asList(
			_moduleDirPath1 + "/test/js/a1.test.js",
			_moduleDirPath1 + "/test/js/b2.test.ts",
			_moduleDirPath1 + "/test/js/nested/c3.test.tsx",
			_moduleDirPath2 + "/test/js/d4.test.js");

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(null);

		_testSuiteName = jsUnitModulesBatchTestClassGroup.getTestSuiteName();
	}

	@Test
	public void testGetJSONObject() {
		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(
				_getJobProperties(
					"test.batch.test.file.excludes[js-unit]",
					"**/nested/**,**/test/js/{b2,d4}.test.*",
					"test.batch.test.file.includes[js-unit]",
					"modules/apps/**"));

		JSONObject jsonObject =
			jsUnitModulesBatchTestClassGroup.getJSONObject();

		testEquals(
			Arrays.asList("**/nested/**", "**/test/js/{b2,d4}.test.*"),
			_getGlobs(jsonObject, "test_file_exclude_globs"));
		testEquals(
			Collections.singletonList("modules/apps/**"),
			_getGlobs(jsonObject, "test_file_include_globs"));

		BatchTestClassGroup batchTestClassGroup =
			TestClassGroupFactory.newBatchTestClassGroup(
				jsUnitModulesBatchTestClassGroup.getJob(),
				new JSONObject(jsonObject.toString()));

		testEquals(
			_getTestClassFileReportedValues(
				_getAxisTestClasses(jsUnitModulesBatchTestClassGroup)),
			_getTestClassFileReportedValues(
				_getAxisTestClasses(batchTestClassGroup)));
		testEquals(
			_getTestClassMethodNames(
				_getAxisTestClasses(jsUnitModulesBatchTestClassGroup)),
			_getTestClassMethodNames(_getAxisTestClasses(batchTestClassGroup)));
	}

	@Test
	public void testHasTestFileGlobs() {
		_testHasTestFileGlobs(
			false,
			_getJobProperties("test.batch.test.file.excludes[js-unit]", ""));
		_testHasTestFileGlobs(
			false,
			_getJobProperties(
				JenkinsResultsParserUtil.combine(
					"test.batch.test.file.includes[js-unit][",
					RandomTestUtil.randomString(), "]"),
				"**/nested/**"));
		_testHasTestFileGlobs(false, null);
		_testHasTestFileGlobs(
			true,
			_getJobProperties(
				"test.batch.test.file.excludes[js-unit]", "**/nested/**"));
		_testHasTestFileGlobs(
			true,
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]", "**/nested/**"));
		_testHasTestFileGlobs(
			true,
			_getJobProperties(
				JenkinsResultsParserUtil.combine(
					"test.batch.test.file.includes[js-unit][", _testSuiteName,
					"]"),
				"**/nested/**"));
	}

	@Test
	public void testIsTestClassFileReported() {
		_testIsTestClassFileReported(
			false,
			_getJobProperties(
				"test.batch.report.type[js-unit]",
				RandomTestUtil.randomString()));
		_testIsTestClassFileReported(false, null);
		_testIsTestClassFileReported(
			true,
			_getJobProperties("test.batch.report.type[js-unit]", "test-file"));
		_testIsTestClassFileReported(
			true,
			_getJobProperties(
				"test.batch.report.type[js-unit]",
				RandomTestUtil.randomString(),
				"test.batch.test.file.includes[js-unit]", "modules/apps/**"));
		_testIsTestClassFileReported(
			true,
			_getJobProperties(
				"test.batch.test.file.excludes[js-unit]", "**/nested/**"));
		_testIsTestClassFileReported(
			true,
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]", "modules/apps/**"));
	}

	@Test
	public void testSetTestClasses() {
		_testSetTestClasses(
			Arrays.asList(
				_moduleDirPath1 + "/test/js/a1.test.js",
				_moduleDirPath1 + "/test/js/b2.test.ts"),
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]",
				"**/test/js/[ab]*.test.*"));
		_testSetTestClasses(
			Arrays.asList(
				_moduleDirPath1 + "/test/js/a1.test.js",
				_moduleDirPath1 + "/test/js/b2.test.ts",
				_moduleDirPath1 + "/test/js/nested/c3.test.tsx",
				_moduleDirPath2 + "/test/js/d4.test.js"),
			_getJobProperties(
				JenkinsResultsParserUtil.combine(
					"test.batch.test.file.includes[js-unit][",
					RandomTestUtil.randomString(), "]"),
				"**/nested/**"));
		_testSetTestClasses(
			Arrays.asList(
				_moduleDirPath1 + "/test/js/a1.test.js",
				_moduleDirPath1 + "/test/js/b2.test.ts",
				_moduleDirPath1 + "/test/js/nested/c3.test.tsx",
				_moduleDirPath2 + "/test/js/d4.test.js"),
			null);
		_testSetTestClasses(
			Arrays.asList(
				_moduleDirPath1 + "/test/js/a1.test.js",
				_moduleDirPath1 + "/test/js/b2.test.ts",
				_moduleDirPath2 + "/test/js/d4.test.js"),
			_getJobProperties(
				"test.batch.test.file.excludes[js-unit]", "**/nested/**"));
		_testSetTestClasses(
			Arrays.asList(
				_moduleDirPath1 + "/test/js/a1.test.js",
				_moduleDirPath1 + "/test/js/nested/c3.test.tsx"),
			_getJobProperties(
				"test.batch.test.file.excludes[js-unit]", "**/d4.test.js",
				"test.batch.test.file.includes[js-unit]",
				"modules/apps/**/test/js/*.test.js,**/nested/**"));
		_testSetTestClasses(
			Arrays.asList(
				_moduleDirPath1 + "/test/js/a1.test.js",
				_moduleDirPath2 + "/test/js/d4.test.js"),
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]",
				"**/test/js/{a1,d4}.test.js"));
		_testSetTestClasses(
			Arrays.asList(
				_moduleDirPath1 + "/test/js/a1.test.js",
				_moduleDirPath2 + "/test/js/d4.test.js"),
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]",
				"modules/apps/*/*/test/js/*.test.js"));
		_testSetTestClasses(
			Collections.singletonList(_moduleDirPath1 + "/test/js/a1.test.js"),
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]",
				"**/test/js/?1.test.js"));
		_testSetTestClasses(
			Collections.singletonList(
				_moduleDirPath1 + "/test/js/nested/c3.test.tsx"),
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]",
				"modules/apps/**/nested/**"));
		_testSetTestClasses(
			Collections.singletonList(
				_moduleDirPath1 + "/test/js/nested/c3.test.tsx"),
			_getJobProperties(
				JenkinsResultsParserUtil.combine(
					"test.batch.test.file.includes[js-unit][", _testSuiteName,
					"]"),
				"**/nested/**"));
	}

	@Test
	public void testSetTestClassesFailure() {
		_testSetTestClassesFailure(
			_getJobProperties(
				"test.batch.test.file.excludes[js-unit]", "modules/**"));
		_testSetTestClassesFailure(
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]", "**/missing/**"));
		_testSetTestClassesFailure(
			_getJobProperties(
				"test.batch.test.file.includes[js-unit]", "apps/**/nested/**"));
	}

	@Test
	public void testSetTestClassesNoJSUnitFiles() {
		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(
				_getJobProperties(
					"test.batch.test.file.includes[js-unit]", "**/nested/**"),
				Collections.emptyList());

		testEquals(
			Collections.emptyList(),
			jsUnitModulesBatchTestClassGroup.getTestClasses());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private List<TestClass> _getAxisTestClasses(
		BatchTestClassGroup batchTestClassGroup) {

		List<TestClass> testClasses = new ArrayList<>();

		for (SegmentTestClassGroup segmentTestClassGroup :
				batchTestClassGroup.getSegmentTestClassGroups()) {

			for (AxisTestClassGroup axisTestClassGroup :
					segmentTestClassGroup.getAxisTestClassGroups()) {

				testClasses.addAll(axisTestClassGroup.getTestClasses());
			}
		}

		return testClasses;
	}

	private List<Object> _getGlobs(JSONObject jsonObject, String key) {
		JSONArray jsonArray = jsonObject.getJSONArray(key);

		return jsonArray.toList();
	}

	private Properties _getJobProperties(String... keysAndValues) {
		Properties jobProperties = new Properties();

		for (int i = 0; i < keysAndValues.length; i += 2) {
			jobProperties.setProperty(keysAndValues[i], keysAndValues[i + 1]);
		}

		return jobProperties;
	}

	private List<Boolean> _getTestClassFileReportedValues(
		List<TestClass> testClasses) {

		List<Boolean> testClassFileReportedValues = new ArrayList<>();

		for (TestClass testClass : testClasses) {
			JSUnitJUnitTestClass jsUnitJUnitTestClass =
				(JSUnitJUnitTestClass)testClass;

			testClassFileReportedValues.add(
				jsUnitJUnitTestClass.isTestClassFileReported());
		}

		return testClassFileReportedValues;
	}

	private List<String> _getTestClassMethodNames(List<TestClass> testClasses) {
		List<String> testClassMethodNames = new ArrayList<>();

		for (TestClass testClass : testClasses) {
			for (TestClassMethod testClassMethod :
					testClass.getTestClassMethods()) {

				testClassMethodNames.add(testClassMethod.getName());
			}
		}

		Collections.sort(testClassMethodNames);

		return testClassMethodNames;
	}

	private JSUnitModulesBatchTestClassGroup
		_newJSUnitModulesBatchTestClassGroup(Properties jobProperties) {

		return _newJSUnitModulesBatchTestClassGroup(
			jobProperties, _jsUnitFilePaths);
	}

	private JSUnitModulesBatchTestClassGroup
		_newJSUnitModulesBatchTestClassGroup(
			Properties jobProperties, List<String> jsUnitFilePaths) {

		JobPropertyFactory.clear();

		List<File> jsUnitFiles = new ArrayList<>();

		for (String jsUnitFilePath : jsUnitFilePaths) {
			jsUnitFiles.add(new File(_workingDirectory, jsUnitFilePath));
		}

		return BatchTestClassGroupTestUtil.newJSUnitModulesBatchTestClassGroup(
			Arrays.asList(
				new File(_workingDirectory, _moduleDirPath1),
				new File(_workingDirectory, _moduleDirPath2)),
			jobProperties, jsUnitFiles, _workingDirectory);
	}

	private String _newModuleDirPath() throws Exception {
		String moduleName = RandomTestUtil.randomString();

		String moduleDirPath = JenkinsResultsParserUtil.combine(
			"modules/apps/", moduleName, "/", moduleName, "-web");

		File moduleDir = new File(_workingDirectory, moduleDirPath);

		moduleDir.mkdirs();

		File buildGradleFile = new File(moduleDir, "build.gradle");

		buildGradleFile.createNewFile();

		JSONObject packageJSONObject = new JSONObject();

		packageJSONObject.put(
			"scripts",
			new JSONObject(
			).put(
				"test", RandomTestUtil.randomString()
			));

		JenkinsResultsParserUtil.write(
			new File(moduleDir, "package.json"), packageJSONObject.toString());

		return moduleDirPath;
	}

	private void _testHasTestFileGlobs(
		boolean expectedHasTestFileGlobs, Properties jobProperties) {

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(jobProperties);

		testEquals(
			expectedHasTestFileGlobs,
			jsUnitModulesBatchTestClassGroup.hasTestFileGlobs());
	}

	private void _testIsTestClassFileReported(
		boolean expectedTestClassFileReported, Properties jobProperties) {

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(jobProperties);

		List<Boolean> testClassFileReportedValues =
			_getTestClassFileReportedValues(
				jsUnitModulesBatchTestClassGroup.getTestClasses());

		Assert.assertFalse(testClassFileReportedValues.isEmpty());

		for (Boolean testClassFileReported : testClassFileReportedValues) {
			testEquals(expectedTestClassFileReported, testClassFileReported);
		}
	}

	private void _testSetTestClasses(
		List<String> expectedTestClassMethodNames, Properties jobProperties) {

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(jobProperties);

		expectedTestClassMethodNames = new ArrayList<>(
			expectedTestClassMethodNames);

		Collections.sort(expectedTestClassMethodNames);

		testEquals(
			expectedTestClassMethodNames,
			_getTestClassMethodNames(
				jsUnitModulesBatchTestClassGroup.getTestClasses()));

		for (TestClass testClass :
				jsUnitModulesBatchTestClassGroup.getTestClasses()) {

			Assert.assertTrue(testClass.hasTestClassMethods());
		}
	}

	private void _testSetTestClassesFailure(Properties jobProperties) {
		try {
			_newJSUnitModulesBatchTestClassGroup(jobProperties);

			Assert.fail("Expected RuntimeException");
		}
		catch (RuntimeException runtimeException) {
			String message = runtimeException.getMessage();

			for (String propertyName :
					Arrays.asList(
						"modules.excludes", "modules.includes",
						"test.batch.test.file.excludes",
						"test.batch.test.file.includes")) {

				Assert.assertTrue(message.contains("\"" + propertyName + "\""));
			}
		}
	}

	private List<String> _jsUnitFilePaths;
	private String _moduleDirPath1;
	private String _moduleDirPath2;
	private String _testSuiteName;
	private File _workingDirectory;

}