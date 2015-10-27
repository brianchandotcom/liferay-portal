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

package com.liferay.jenkins.results.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtilTest {

	@Test
	public void testFixJSON() {
		String[] expectedResultArray = new String[] {
			"&#09;", "&#34; ", "&#39;", "&#40;", "&#41;", "&#60;", "&#62;",
			"&#91;", "&#92;", "&#93;", "&#123;", "&#125;", "<br />"
		};

		String[] testArray = new String[] {
			"\t", "\"", "'", "(", ")", "<", ">", "[", "\\", "]", "{", "}", "\n"
		};

		if (expectedResultArray.length != testArray.length) {
			throw
			new RuntimeException(
				"Expected results array length does not equal test array" +
				" length.");
		}

		for (int i = 0; i<expectedResultArray.length; i++) {
			String expectedResult = "ABC" + expectedResultArray[i] + "123";
			String test = "ABC" + testArray[i] + "123";

			assertEquals(
				expectedResult, JenkinsResultsParserUtil.fixJSON(test));
		}
	}

	@Test
	public void testFixURL() {
		String[] expectedResultArray = new String[] {
			"%28", "%29", "%5B", "%5D"
		};

		String[] testArray = new String[] {"(", ")", "[", "]"};

		if (expectedResultArray.length != testArray.length) {
			throw
			new RuntimeException(
				"expected results array length does not equal test array" +
				" length.");
		}

		for (int i = 0; i<expectedResultArray.length; i++) {
			String expectedResult = "ABC" + expectedResultArray[i] + "123";
			String test = "ABC" + testArray[i] + "123";

			assertEquals(JenkinsResultsParserUtil.fixURL(test), expectedResult);
		}
	}

	@Test
	public void testGetAxisVariable() throws Exception {
		JSONObject axisJson =
			new JSONObject(
				_read(
					new File(
						_testDependenciesDir.getPath() +
						"/testGetAxisVariable.json")));

		assertEquals("ABC", JenkinsResultsParserUtil.getAxisVariable(axisJson));
	}

	@Test
	public void testGetJobVariant() throws Exception {
		String functionalJSONString =
			_read(
				new File(
					_testDependenciesDir.getPath() +
					"/testGetJobVariant_functional.json"));
		String integrationJSONString =
			_read(
				new File(
					_testDependenciesDir.getPath() +
					"/testGetJobVariant_integration.json"));

		JSONObject functionalJSONObject = new JSONObject(functionalJSONString);
		JSONObject integrationJSONObject = new JSONObject(
			integrationJSONString);

		assertEquals(
			"functional-tomcat-mysql/0",
			JenkinsResultsParserUtil.getJobVariant(functionalJSONObject));
		assertEquals(
			"integration-db2",
			JenkinsResultsParserUtil.getJobVariant(integrationJSONObject));
	}

	@Test
	public void testGetLocalURL() {
		String[] localURLArray = new String[] {
			"http://test-8/8/", "http://test-1-20/", "http://test-4-1/"
		};
		String[] remoteURLArray = new String[] {
			"https://test.liferay.com/8/", "https://test-1-20.liferay.com/",
			"http://test-4-1/"
		};

		if (localURLArray.length != remoteURLArray.length) {
			throw
			new RuntimeException(
				"Remote URL array length does not equal local URL array " +
				"length.");
		}

		for (int i = 0; i<localURLArray.length; i++) {
			assertEquals(
				localURLArray[i],
				JenkinsResultsParserUtil.getLocalURL(remoteURLArray[i]));
		}
	}

	@Test
	public void testToJSONObject() throws Exception {
		File functionalJSONFile =
			new File(
				_testDependenciesDir.getPath() +
				"/testGetJobVariant_functional.json");
		File integrationJSONFile =
			new File(
				_testDependenciesDir.getPath() +
				"/testGetJobVariant_integration.json");

		JSONObject expectedFunctionalJSONObject = new JSONObject(
			_read(functionalJSONFile));
		JSONObject expectedIntegrationJSONObject = new JSONObject(
			_read(integrationJSONFile));

		URI functionalJSONUri = functionalJSONFile.toURI();
		URI integrationJSONUri = integrationJSONFile.toURI();

		JSONObject testFunctionalJSONObject =
			JenkinsResultsParserUtil.toJSONObject(
				functionalJSONUri.toASCIIString());
		JSONObject testIntegrationJSONObject =
			JenkinsResultsParserUtil.toJSONObject(
				integrationJSONUri.toASCIIString());

		assertTrue(
			_equals(expectedFunctionalJSONObject, testFunctionalJSONObject));
		assertTrue(
			_equals(expectedIntegrationJSONObject, testIntegrationJSONObject));
	}

	@Test
	public void testToString() throws Exception {
		File functionalJSONFile =
			new File(
				_testDependenciesDir.getPath() +
				"/testGetJobVariant_functional.json");
		File integrationJSONFile =
			new File(
				_testDependenciesDir.getPath() +
				"/testGetJobVariant_integration.json");

		String functionalJSONString = _read(functionalJSONFile);
		String integrationJSONString = _read(integrationJSONFile);

		URI functionalJSONUri = functionalJSONFile.toURI();
		URI integrationJSONUri = integrationJSONFile.toURI();

		String functionalToStringJSONString = JenkinsResultsParserUtil.toString(
			functionalJSONUri.toASCIIString());
		String integrationToStringJSONString =
			JenkinsResultsParserUtil.toString(
				integrationJSONUri.toASCIIString());

		// Remove carriage returns because the toString method handles them
		// differently but they don't have an impact on actual equality.

		functionalJSONString = functionalJSONString.replace("\n", "");
		functionalToStringJSONString = functionalToStringJSONString.replace(
			"\n", "");
		integrationJSONString = integrationJSONString.replace("\n", "");
		integrationToStringJSONString = integrationToStringJSONString.replace(
			"\n", "");

		assertEquals(functionalJSONString, functionalToStringJSONString);
		assertEquals(integrationJSONString, integrationToStringJSONString);
	}

	private static boolean _equals(JSONArray jsonArray1, JSONArray jsonArray2)
		throws Exception {

		if (((jsonArray1 == null) && (jsonArray2 != null)) ||
			((jsonArray1 != null) && (jsonArray2 == null))) {
				return false;
		}

		if (jsonArray1.length() != jsonArray2.length()) {
			return false;
		}

		for (int i = 0; i<jsonArray1.length(); i++) {
			Object obj1Child = jsonArray1.get(i);
			Object obj2Child = jsonArray2.get(i);

			if (!_equalsJSONChildObjectHandler(obj1Child, obj2Child)) {
				return false;
			}
		}

		return true;
	}

	private static boolean _equals(JSONObject jsonObj1, JSONObject jsonObj2)
		throws Exception {

		if (((jsonObj1 == null) && (jsonObj2 != null)) ||
			((jsonObj1 != null) && (jsonObj2 == null))) {
				return false;
		}

		if (jsonObj1.length() != jsonObj2.length()) {
			return false;
		}

		Iterator<String> iterator = jsonObj1.keys();
		while (iterator.hasNext()) {
			String key = iterator.next();

			Object obj1Child = jsonObj1.get(key);
			Object obj2Child = jsonObj2.get(key);

			if (!_equalsJSONChildObjectHandler(obj1Child, obj2Child)) {
				return false;
			}
		}

		return true;
	}

	private static boolean _equalsJSONChildObjectHandler(
		Object obj1, Object obj2)
			throws
				Exception {

		if ((obj1 == null) && (obj2 == null)) {
			return true;
		}

		if (((obj1 == null) && (obj2 != null)) ||
			((obj1 != null) && (obj2 == null))) {
				return false;
		}

		if (obj1.getClass() != obj2.getClass()) {
			return false;
		}

		if (obj1 instanceof JSONObject) {
			return _equals((JSONObject)obj1, (JSONObject)obj2);
		}

		if (obj1 instanceof JSONArray) {
			return _equals((JSONArray)obj1, (JSONArray)obj2);
		}

		return obj1.equals(obj2);
	}

	private static String _read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	private static final File _testDependenciesDir = new File(
		"src/test/resources/com/liferay/results/parser/dependencies/" +
		"JenkinsResultsParserUtilTest");

}