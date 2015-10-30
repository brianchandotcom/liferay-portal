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

import java.io.File;

import java.net.URL;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtilTest extends BaseMessageUtilTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample(
			"axis-integration-db2-1", "0,label_exp=!master", "129",
			"test-portal-acceptance-pullrequest-batch(master)", "test-4-1");
		downloadSample(
			"axis-plugin-1", "9,label_exp=!master", "233",
			"test-portal-acceptance-pullrequest-batch(ee-6.2.x)", "test-1-20");
		downloadSample(
			"job-1", null, "267",
			"test-portal-acceptance-pullrequest-source(ee-6.2.x)", "test-1-1");
	}

	@Test
	public void testFixJSON() {
		Assert.assertEquals(
			"ABC&#09;123", JenkinsResultsParserUtil.fixJSON("ABC\t123"));
		Assert.assertEquals(
			"ABC&#34;123", JenkinsResultsParserUtil.fixJSON("ABC\"123"));
		Assert.assertEquals(
			"ABC&#39;123", JenkinsResultsParserUtil.fixJSON("ABC'123"));
		Assert.assertEquals(
			"ABC&#40;123", JenkinsResultsParserUtil.fixJSON("ABC(123"));
		Assert.assertEquals(
			"ABC&#41;123", JenkinsResultsParserUtil.fixJSON("ABC)123"));
		Assert.assertEquals(
			"ABC&#60;123", JenkinsResultsParserUtil.fixJSON("ABC<123"));
		Assert.assertEquals(
			"ABC&#62;123", JenkinsResultsParserUtil.fixJSON("ABC>123"));
		Assert.assertEquals(
			"ABC&#91;123", JenkinsResultsParserUtil.fixJSON("ABC[123"));
		Assert.assertEquals(
			"ABC&#92;123", JenkinsResultsParserUtil.fixJSON("ABC\\123"));
		Assert.assertEquals(
			"ABC&#93;123", JenkinsResultsParserUtil.fixJSON("ABC]123"));
		Assert.assertEquals(
			"ABC&#123;123", JenkinsResultsParserUtil.fixJSON("ABC{123"));
		Assert.assertEquals(
			"ABC&#125;123", JenkinsResultsParserUtil.fixJSON("ABC}123"));
		Assert.assertEquals(
			"ABC<br />123", JenkinsResultsParserUtil.fixJSON("ABC\n123"));
	}

	@Test
	public void testFixURL() {
		Assert.assertEquals(
			"ABC%28123", JenkinsResultsParserUtil.fixURL("ABC(123"));
		Assert.assertEquals(
			"ABC%29123", JenkinsResultsParserUtil.fixURL("ABC)123"));
		Assert.assertEquals(
			"ABC%5B123", JenkinsResultsParserUtil.fixURL("ABC[123"));
		Assert.assertEquals(
			"ABC%5D123", JenkinsResultsParserUtil.fixURL("ABC]123"));
	}

	@Test
	public void testGetJobVariant() throws Exception {
		Assert.assertEquals(
			"integration-db2",
			JenkinsResultsParserUtil.getJobVariant(
				new JSONObject(
					read(
						new File(
							dependenciesDir,
							"/axis-integration-db2-1/api/json")))));

		Assert.assertEquals(
			"plugins",
			JenkinsResultsParserUtil.getJobVariant(
				new JSONObject(
					read(
						new File(
							dependenciesDir, "/axis-plugin-1/api/json")))));
		Assert.assertEquals(
			"",
			JenkinsResultsParserUtil.getJobVariant(
				new JSONObject(
					read(new File(dependenciesDir, "/job-1/api/json")))));
	}

	@Test
	public void testGetLocalURL() {
		Assert.assertEquals(
			"http://test-8/8/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getLocalURL(
				"https://test.liferay.com/8/ABC?123=456&xyz=abc"));
		Assert.assertEquals(
			"http://test-1-20/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getLocalURL(
				"https://test-1-20.liferay.com/ABC?123=456&xyz=abc"));
		Assert.assertEquals(
			"http://test-4-1/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getLocalURL(
				"http://test-4-1/ABC?123=456&xyz=abc"));
	}

	@Test
	public void testToJSONObject() throws Exception {
		for (File file : dependenciesDir.listFiles()) {
			assertToJSONObjectSample(new File(file, "api/json"));
		}
	}

	@Test
	public void testToString() throws Exception {
		for (File file : dependenciesDir.listFiles()) {
			assertToStringSample(new File(file, "api/json"));
		}
	}

	protected void assertToJSONObjectSample(File jsonFile) throws Exception {
		JSONObject expectedJSONObject = new JSONObject(read(jsonFile));
		JSONObject actualJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(toURLString(jsonFile)));

		Assert.assertTrue(_equals(expectedJSONObject, actualJSONObject));
	}

	protected void assertToStringSample(File jsonFile) throws Exception {
		String expectedJSONString = read(jsonFile);
		String actualJSONString = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(toURLString(jsonFile)));

		Assert.assertEquals(
			expectedJSONString.replace("\n", ""),
			actualJSONString.replace("\n", ""));
	}

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		downloadSampleURL(sampleDir, url, "/api/json");
	}

	protected void downloadSample(
			String sampleKey, String axisVariable, String buildNumber,
			String jobName, String hostName)
		throws Exception {

		String urlString =
			"https://${hostName}.liferay.com/job/${jobName}/" +
				"/${buildNumber}/";

		if (axisVariable != null) {
			urlString =
				"https://${hostName}.liferay.com/job/${jobName}/" +
					"AXIS_VARIABLE=${axis}/${buildNumber}/";

			urlString = replaceToken(urlString, "axis", axisVariable);
		}

		urlString = replaceToken(urlString, "buildNumber", buildNumber);
		urlString = replaceToken(urlString, "hostName", hostName);
		urlString = replaceToken(urlString, "jobName", jobName);

		URL url = createURL(urlString);

		downloadSample(sampleKey, url);
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		return null;
	}

	@Override
	protected void writeExpectedMessage(File sampleDir) throws Exception {

		// Do nothing

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

		Iterator<?> iterator = jsonObj1.keys();

		while (iterator.hasNext()) {
			String key = (String)iterator.next();

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

}