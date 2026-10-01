/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.ReflectionTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightTestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Calum Ragan
 */
public class PlaywrightBatchTestClassGroupTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testParsePlaywrightJSONObjectsDescribeBlocks() {
		String projectName = RandomTestUtil.randomString();
		String specFilePath = _newSpecFilePath();
		String suiteTitle1 = RandomTestUtil.randomString();
		String suiteTitle2 = RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();

		File rootDir = new File(RandomTestUtil.randomString());

		Map<String, Map<File, TestClass>> testClassesByProjectMap =
			_parsePlaywrightJSONObjects(
				rootDir,
				new JSONArray(
				).put(
					new JSONObject(
					).put(
						"file", specFilePath
					).put(
						"suites",
						new JSONArray(
						).put(
							_newSuiteJSONObject(
								specFilePath,
								new JSONArray(
								).put(
									_newSpecJSONObject(
										RandomTestUtil.randomString(),
										specFilePath, projectName, title)
								),
								suiteTitle1)
						).put(
							_newSuiteJSONObject(
								specFilePath,
								new JSONArray(
								).put(
									_newSpecJSONObject(
										RandomTestUtil.randomString(),
										specFilePath, projectName, title)
								),
								suiteTitle2)
						)
					).put(
						"title", specFilePath
					)
				));

		Map<File, TestClass> testClassesMap = testClassesByProjectMap.get(
			projectName);

		PlaywrightJUnitTestClass playwrightJUnitTestClass =
			(PlaywrightJUnitTestClass)testClassesMap.get(
				new File(rootDir, specFilePath));

		Assert.assertEquals(
			Arrays.asList(
				suiteTitle1 + " › " + title, suiteTitle2 + " › " + title),
			_getTestNames(playwrightJUnitTestClass));
	}

	@Test
	public void testParsePlaywrightJSONObjectsRepeated() {
		String projectName = RandomTestUtil.randomString();
		String specFilePath = _newSpecFilePath();
		String title1 = RandomTestUtil.randomString();
		String title2 = RandomTestUtil.randomString();

		JSONArray suitesJSONArray = new JSONArray(
		).put(
			_newSuiteJSONObject(
				specFilePath,
				new JSONArray(
				).put(
					_newSpecJSONObject(
						RandomTestUtil.randomString(), specFilePath,
						projectName, title1)
				).put(
					_newSpecJSONObject(
						RandomTestUtil.randomString(), specFilePath,
						projectName, title2)
				),
				specFilePath)
		);

		File rootDir = new File(RandomTestUtil.randomString());

		_parsePlaywrightJSONObjects(rootDir, suitesJSONArray);

		Map<String, Map<File, TestClass>> testClassesByProjectMap =
			_parsePlaywrightJSONObjects(rootDir, suitesJSONArray);

		Map<File, TestClass> testClassesMap = testClassesByProjectMap.get(
			projectName);

		Assert.assertEquals(
			Arrays.asList(title1, title2),
			_getTestNames(testClassesMap.get(new File(rootDir, specFilePath))));
	}

	@Test
	public void testParsePlaywrightJSONObjectsSharedSpec() {
		String projectName1 = RandomTestUtil.randomString();
		String projectName2 = RandomTestUtil.randomString();
		String specFilePath = _newSpecFilePath();
		String title1 = RandomTestUtil.randomString();
		String title2 = RandomTestUtil.randomString();

		File rootDir = new File(RandomTestUtil.randomString());

		Map<String, Map<File, TestClass>> testClassesByProjectMap =
			_parsePlaywrightJSONObjects(
				rootDir,
				new JSONArray(
				).put(
					_newSuiteJSONObject(
						specFilePath,
						new JSONArray(
						).put(
							_newSpecJSONObject(
								"skip", specFilePath, projectName1, title1)
						).put(
							_newSpecJSONObject(
								RandomTestUtil.randomString(), specFilePath,
								projectName1, title2)
						).put(
							_newSpecJSONObject(
								RandomTestUtil.randomString(), specFilePath,
								projectName2, title1)
						),
						specFilePath)
				));

		File specFile = new File(rootDir, specFilePath);

		Map<File, TestClass> testClassesMap1 = testClassesByProjectMap.get(
			projectName1);

		PlaywrightJUnitTestClass playwrightJUnitTestClass =
			(PlaywrightJUnitTestClass)testClassesMap1.get(specFile);

		Assert.assertEquals(
			Arrays.asList(title1, title2),
			_getTestNames(playwrightJUnitTestClass));

		Map<File, TestClass> testClassesMap2 = testClassesByProjectMap.get(
			projectName2);

		Assert.assertSame(
			playwrightJUnitTestClass, testClassesMap2.get(specFile));

		List<TestClassMethod> testClassMethods =
			playwrightJUnitTestClass.getTestClassMethods();

		TestClassMethod testClassMethod = testClassMethods.get(0);

		Assert.assertTrue(testClassMethod.isIgnored());
	}

	private List<String> _getTestNames(TestClass testClass) {
		List<String> testNames = new ArrayList<>();

		for (TestClassMethod testClassMethod :
				testClass.getTestClassMethods()) {

			PlaywrightTestClassMethod playwrightTestClassMethod =
				(PlaywrightTestClassMethod)testClassMethod;

			testNames.add(playwrightTestClassMethod.getTestName());
		}

		return testNames;
	}

	private String _newSpecFilePath() {
		return RandomTestUtil.randomString() + "/" +
			RandomTestUtil.randomString();
	}

	private JSONObject _newSpecJSONObject(
		String annotationType, String file, String projectName, String title) {

		return new JSONObject(
		).put(
			"file", file
		).put(
			"tests",
			new JSONArray(
			).put(
				new JSONObject(
				).put(
					"annotations",
					new JSONArray(
					).put(
						new JSONObject(
						).put(
							"type", annotationType
						)
					)
				).put(
					"projectName", projectName
				)
			)
		).put(
			"title", title
		);
	}

	private JSONObject _newSuiteJSONObject(
		String file, JSONArray specsJSONArray, String title) {

		return new JSONObject(
		).put(
			"file", file
		).put(
			"specs", specsJSONArray
		).put(
			"title", title
		);
	}

	private Map<String, Map<File, TestClass>> _parsePlaywrightJSONObjects(
		File rootDir, JSONArray suitesJSONArray) {

		PlaywrightBatchTestClassGroup playwrightBatchTestClassGroup =
			Mockito.mock(PlaywrightBatchTestClassGroup.class);

		Mockito.doReturn(
			Mockito.mock(PortalGitWorkingDirectory.class)
		).when(
			playwrightBatchTestClassGroup
		).getPortalGitWorkingDirectory();

		Map<String, Map<File, TestClass>> testClassesByProjectMap =
			new HashMap<>();

		ReflectionTestUtil.invoke(
			playwrightBatchTestClassGroup, "_parsePlaywrightJSONObjects",
			new Class<?>[] {File.class, JSONArray.class, Map.class}, rootDir,
			suitesJSONArray, testClassesByProjectMap);

		return testClassesByProjectMap;
	}

}