/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
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

		setShellCommandOutput(
			"git remote -v", mockShell(),
			"upstream\tgit@github.com:liferay/liferay-portal.git (fetch)\n" +
				"upstream\tgit@github.com:liferay/liferay-portal.git (push)\n");

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"jenkins.tmp.dir",
			JenkinsResultsParserUtil.combine(
				JenkinsResultsParserUtil.getCanonicalPath(
					temporaryFolder.newFolder("jenkins-tmp")),
				"/"));

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		_workingDirectory = temporaryFolder.getRoot();

		_moduleDir = new File(_workingDirectory, _MODULE_DIR_PATH);

		_moduleDir.mkdirs();

		File buildGradleFile = new File(_moduleDir, "build.gradle");

		buildGradleFile.createNewFile();

		JSONObject packageJSONObject = new JSONObject();

		JSONObject scriptsJSONObject = new JSONObject();

		scriptsJSONObject.put("test", "echo");

		packageJSONObject.put("scripts", scriptsJSONObject);

		JenkinsResultsParserUtil.write(
			new File(_moduleDir, "package.json"), packageJSONObject.toString());

		_jsUnitFiles = new ArrayList<>();

		String[] jsUnitFilePaths = {
			"src/content/main_view.test.tsx", "src/content/side_view.test.tsx",
			"src/page/page_view.test.js"
		};

		for (String jsUnitFilePath : jsUnitFilePaths) {
			_jsUnitFiles.add(new File(_moduleDir, jsUnitFilePath));
		}
	}

	@Test
	public void testGetJSONObject() throws Exception {
		Properties jobProperties = new Properties();

		jobProperties.setProperty(
			"test.batch.test.file.excludes[js-unit]", _GLOB_EXCLUDES);
		jobProperties.setProperty(
			"test.batch.test.file.includes[js-unit]", _GLOB_INCLUDES);

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(jobProperties);

		JSONObject jsonObject =
			jsUnitModulesBatchTestClassGroup.getJSONObject();

		testEquals(
			Collections.singletonList(_GLOB_EXCLUDES),
			_getGlobs(jsonObject, "test_file_exclude_globs"));
		testEquals(
			Collections.singletonList(_GLOB_INCLUDES),
			_getGlobs(jsonObject, "test_file_include_globs"));

		testEquals(
			_getTestClassMethodNames(jsUnitModulesBatchTestClassGroup),
			_getTestClassMethodNames(
				TestClassGroupFactory.newBatchTestClassGroup(
					jsUnitModulesBatchTestClassGroup.getJob(),
					new JSONObject(jsonObject.toString()))));
	}

	@Test
	public void testGetTestCasePropertiesContent() throws Exception {
		testEquals(_TEST_TASK_NAME, _getTestClassGroup(null));
	}

	@Test
	public void testGetTestCasePropertiesContentWithTestFileIncludes()
		throws Exception {

		Properties jobProperties = new Properties();

		jobProperties.setProperty(
			"test.batch.test.file.includes[js-unit]", _GLOB_INCLUDES);

		testEquals(
			JenkinsResultsParserUtil.combine(
				_TEST_TASK_NAME, "#", _MODULE_DIR_PATH,
				"/src/content/main_view.test.tsx#", _MODULE_DIR_PATH,
				"/src/content/side_view.test.tsx"),
			_getTestClassGroup(jobProperties));
	}

	@Test
	public void testSetTestClasses() throws Exception {
		_testSetTestClasses(
			null, _MODULE_DIR_PATH + "/src/content/main_view.test.tsx",
			_MODULE_DIR_PATH + "/src/content/side_view.test.tsx",
			_MODULE_DIR_PATH + "/src/page/page_view.test.js");
	}

	@Test
	public void testSetTestClassesWithTestFileExcludes() throws Exception {
		Properties jobProperties = new Properties();

		jobProperties.setProperty(
			"test.batch.test.file.excludes[js-unit]", _GLOB_EXCLUDES);

		_testSetTestClasses(
			jobProperties, _MODULE_DIR_PATH + "/src/content/main_view.test.tsx",
			_MODULE_DIR_PATH + "/src/content/side_view.test.tsx");
	}

	@Test
	public void testSetTestClassesWithTestFileExcludesMatchingAllTestFiles()
		throws Exception {

		Properties jobProperties = new Properties();

		jobProperties.setProperty(
			"test.batch.test.file.excludes[js-unit]",
			"**/site-cms-site-initializer/**");

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(jobProperties);

		testEquals(
			Collections.emptyList(),
			jsUnitModulesBatchTestClassGroup.getTestClasses());
	}

	@Test
	public void testSetTestClassesWithTestFileIncludes() throws Exception {
		Properties jobProperties = new Properties();

		jobProperties.setProperty(
			"test.batch.test.file.includes[js-unit]", _GLOB_INCLUDES);

		_testSetTestClasses(
			jobProperties, _MODULE_DIR_PATH + "/src/content/main_view.test.tsx",
			_MODULE_DIR_PATH + "/src/content/side_view.test.tsx");
	}

	@Test
	public void testSetTestClassesWithTestFileIncludesAndExcludes()
		throws Exception {

		Properties jobProperties = new Properties();

		jobProperties.setProperty(
			"test.batch.test.file.excludes[js-unit]",
			"**/src/content/side_view.test.tsx");
		jobProperties.setProperty(
			"test.batch.test.file.includes[js-unit]",
			"**/src/content/**,**/src/page/**");

		_testSetTestClasses(
			jobProperties, _MODULE_DIR_PATH + "/src/content/main_view.test.tsx",
			_MODULE_DIR_PATH + "/src/page/page_view.test.js");
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private List<Object> _getGlobs(JSONObject jsonObject, String key) {
		JSONArray globsJSONArray = jsonObject.getJSONArray(key);

		return globsJSONArray.toList();
	}

	private String _getTestClassGroup(Properties jobProperties) {
		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(jobProperties);

		List<SegmentTestClassGroup> segmentTestClassGroups =
			jsUnitModulesBatchTestClassGroup.getSegmentTestClassGroups();

		SegmentTestClassGroup segmentTestClassGroup =
			segmentTestClassGroups.get(0);

		String testCasePropertiesContent =
			segmentTestClassGroup.getTestCasePropertiesContent();

		String testClassGroupKey = "TEST_CLASS_GROUP_0=";

		for (String line : testCasePropertiesContent.split("\n")) {
			if (line.startsWith(testClassGroupKey)) {
				return line.substring(testClassGroupKey.length());
			}
		}

		return null;
	}

	private List<String> _getTestClassMethodNames(
		BatchTestClassGroup batchTestClassGroup) {

		List<String> testClassMethodNames = new ArrayList<>();

		for (SegmentTestClassGroup segmentTestClassGroup :
				batchTestClassGroup.getSegmentTestClassGroups()) {

			for (AxisTestClassGroup axisTestClassGroup :
					segmentTestClassGroup.getAxisTestClassGroups()) {

				for (TestClass testClass :
						axisTestClassGroup.getTestClasses()) {

					for (TestClassMethod testClassMethod :
							testClass.getTestClassMethods()) {

						testClassMethodNames.add(testClassMethod.getName());
					}
				}
			}
		}

		Collections.sort(testClassMethodNames);

		return testClassMethodNames;
	}

	private JSUnitModulesBatchTestClassGroup
		_newJSUnitModulesBatchTestClassGroup(Properties jobProperties) {

		return BatchTestClassGroupTestUtil.newJSUnitModulesBatchTestClassGroup(
			Collections.singletonList(_moduleDir), jobProperties, _jsUnitFiles,
			_workingDirectory);
	}

	private void _testSetTestClasses(
		Properties jobProperties, String... expectedTestClassMethodNames) {

		JSUnitModulesBatchTestClassGroup jsUnitModulesBatchTestClassGroup =
			_newJSUnitModulesBatchTestClassGroup(jobProperties);

		testEquals(
			Arrays.asList(expectedTestClassMethodNames),
			_getTestClassMethodNames(jsUnitModulesBatchTestClassGroup));
	}

	private static final String _GLOB_EXCLUDES =
		"**/site-cms-site-initializer/src/page/**";

	private static final String _GLOB_INCLUDES = "**/src/content/**";

	private static final String _MODULE_DIR_PATH =
		"modules/apps/site/site-cms-site-initializer";

	private static final String _TEST_TASK_NAME =
		":apps:site:site-cms-site-initializer:packageRunTest";

	private List<File> _jsUnitFiles;
	private File _moduleDir;
	private File _workingDirectory;

}