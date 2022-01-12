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

import java.net.URI;
import java.net.URL;

import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtilTest
	extends com.liferay.jenkins.results.parser.Test {

	@Before
	@Override
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
	public void testExpandSlaveRange() {
		testEquals(
			"cloud-10-50-0-151,cloud-10-50-0-152,cloud-10-50-0-153," +
				"cloud-10-50-0-154,cloud-10-50-0-155,cloud-10-50-0-156",
			JenkinsResultsParserUtil.expandSlaveRange(
				"cloud-10-50-0-151..156"));
		testEquals(
			"cloud-10-50-0-47,cloud-10-50-0-0,cloud-10-50-0-1," +
				"cloud-10-50-0-2,cloud-10-50-0-49,cloud-10-50-0-50",
			JenkinsResultsParserUtil.expandSlaveRange(
				"cloud-10-50-0-47, cloud-10-50-0-0..2, cloud-10-50-0-49..50"));
	}

	@Test
	public void testFixJSON() {
		testEquals("ABC&#09;123", JenkinsResultsParserUtil.fixJSON("ABC\t123"));
		testEquals("ABC&#34;123", JenkinsResultsParserUtil.fixJSON("ABC\"123"));
		testEquals("ABC&#39;123", JenkinsResultsParserUtil.fixJSON("ABC'123"));
		testEquals("ABC&#40;123", JenkinsResultsParserUtil.fixJSON("ABC(123"));
		testEquals("ABC&#41;123", JenkinsResultsParserUtil.fixJSON("ABC)123"));
		testEquals("ABC&#60;123", JenkinsResultsParserUtil.fixJSON("ABC<123"));
		testEquals("ABC&#62;123", JenkinsResultsParserUtil.fixJSON("ABC>123"));
		testEquals("ABC&#91;123", JenkinsResultsParserUtil.fixJSON("ABC[123"));
		testEquals("ABC&#92;123", JenkinsResultsParserUtil.fixJSON("ABC\\123"));
		testEquals("ABC&#93;123", JenkinsResultsParserUtil.fixJSON("ABC]123"));
		testEquals("ABC&#123;123", JenkinsResultsParserUtil.fixJSON("ABC{123"));
		testEquals("ABC&#125;123", JenkinsResultsParserUtil.fixJSON("ABC}123"));
		testEquals(
			"ABC<br />123", JenkinsResultsParserUtil.fixJSON("ABC\n123"));
	}

	@Test
	public void testFixURL() {
		testEquals("ABC%28123", _fixURLMultipleTimes("ABC(123"));
		testEquals("ABC%29123", _fixURLMultipleTimes("ABC)123"));
		testEquals("ABC%5B123", _fixURLMultipleTimes("ABC[123"));
		testEquals("ABC%5D123", _fixURLMultipleTimes("ABC]123"));
		testEquals("!master", _fixURLMultipleTimes("!master"));
		testEquals("0%201%202", _fixURLMultipleTimes("0 1 2"));
		testEquals(
			"https://test-1-1.liferay.com/job(master)?" +
				"AXIS_VARIABLE=0%201&label_exp=!master&job=test%287.2.x%29",
			_fixURLMultipleTimes(
				"https://test-1-1.liferay.com/job(master)?" +
					"AXIS_VARIABLE=0 1&label_exp=!master&job=test(7.2.x)"));
	}

	@Test
	public void testGetJobVariant() throws Exception {
		TestSample testSample = testSamples.get("axis-integration-db2-1");

		testEquals(
			"integration-db2",
			JenkinsResultsParserUtil.getJobVariant(
				read(testSample.getSampleDir(), "/api/json")));

		testSample = testSamples.get("axis-plugin-1");

		testEquals(
			"plugins",
			JenkinsResultsParserUtil.getJobVariant(
				read(testSample.getSampleDir(), "/api/json")));

		testSample = testSamples.get("job-1");

		testEquals(
			"",
			JenkinsResultsParserUtil.getJobVariant(
				read(testSample.getSampleDir(), "/api/json")));
	}

	@Test
	public void testGetLocalURL() {
		testEquals(
			"http://test-8/8/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getLocalURL(
				"https://test.liferay.com/8/ABC?123=456&xyz=abc"));
		testEquals(
			"http://test-1-20/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getLocalURL(
				"https://test-1-20.liferay.com/ABC?123=456&xyz=abc"));
		testEquals(
			"http://test-4-1/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getLocalURL(
				"http://test-4-1/ABC?123=456&xyz=abc"));
		testEquals(
			"https://release.liferay.com/1/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getLocalURL(
				"https://release.liferay.com/1/ABC?123=456&xyz=abc"));
		testEquals(
			"http://release-1/1/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getLocalURL(
				"http://release-1/1/ABC?123=456&xyz=abc"));
		testEquals(
			"http://mirrors.lax.liferay.com/files.liferay.com/private/",
			JenkinsResultsParserUtil.getLocalURL(
				"http://mirrors.lax.liferay.com/files.liferay.com/private/"));
		testEquals(
			"http://mirrors.lax.liferay.com/files.liferay.com/private/",
			JenkinsResultsParserUtil.getLocalURL(
				"http://mirrors.dlc.liferay.com/files.liferay.com/private/"));
		testEquals(
			"http://mirrors.lax.liferay.com/files.liferay.com/private/",
			JenkinsResultsParserUtil.getLocalURL(
				"http://mirrors/files.liferay.com/private/"));
		testEquals(
			"http://mirrors.lax.liferay.com/files.liferay.com/private/",
			JenkinsResultsParserUtil.getLocalURL(
				"https://files.liferay.com/private/"));
		testEquals(
			"http://mirrors.lax.liferay.com/releases.liferay.com/portal/",
			JenkinsResultsParserUtil.getLocalURL(
				"http://mirrors.lax.liferay.com/releases.liferay.com/portal/"));
		testEquals(
			"http://mirrors.lax.liferay.com/releases.liferay.com/portal/",
			JenkinsResultsParserUtil.getLocalURL(
				"http://mirrors.dlc.liferay.com/releases.liferay.com/portal/"));
		testEquals(
			"http://mirrors.lax.liferay.com/releases.liferay.com/portal/",
			JenkinsResultsParserUtil.getLocalURL(
				"http://mirrors/releases.liferay.com/portal/"));
		testEquals(
			"http://mirrors.lax.liferay.com/releases.liferay.com/portal/",
			JenkinsResultsParserUtil.getLocalURL(
				"https://releases.liferay.com/portal/"));
	}

	@Test
	public void testGetProperty() {
		Properties properties = new Properties();

		properties.setProperty("base", "0");
		properties.setProperty("base[opt0]", "1");
		properties.setProperty("base[opt0][opt2]", "2");
		properties.setProperty("base[opt0][opt3]", "3");
		properties.setProperty("base[opt1]", "4");
		properties.setProperty("base0[opt[0]]", "5");
		properties.setProperty("base0[opt[1][1][1]]", "6");
		properties.setProperty("base0[opt[1][1][1]][opt[2][2][2]]", "7");
		properties.setProperty("base1[opt1]", "8");
		properties.setProperty("base1[opt1][opt2]", "");

		_testGetProperty("0", properties, "base");
		_testGetProperty(null, properties, "invalid");
		_testGetProperty("1", properties, "base", "opt0", "invalid");
		_testGetProperty("2", properties, "base[opt0]", "opt2");
		_testGetProperty("3", properties, "base", "opt0", "opt3");
		_testGetProperty("4", properties, "base", "opt1", null, "invalid");
		_testGetProperty("5", properties, "base0", "opt[0]");
		_testGetProperty("6", properties, "base0", "opt[1][1][1]", "invalid");
		_testGetProperty(
			"7", properties, "base0", "opt[2][2][2]", "invalid", "opt[1][1][1]",
			null);
		_testGetProperty("", properties, "base1", "opt1", "opt2");

		testEquals(
			"1",
			JenkinsResultsParserUtil.getProperty(properties, "base[opt0]"));
		testEquals(
			"1",
			JenkinsResultsParserUtil.getProperty(
				properties, "base[opt0]", true, "invalid"));
		testEquals(
			null,
			JenkinsResultsParserUtil.getProperty(
				properties, "base[opt0]", false, "invalid"));
	}

	@Test
	public void testGetPropertyName() {
		Properties properties = new Properties();

		properties.setProperty("base", "0");
		properties.setProperty("base[opt0]", "1");
		properties.setProperty("base[opt0][opt2]", "2");
		properties.setProperty("base[opt0][opt3]", "3");
		properties.setProperty("base[opt1]", "4");
		properties.setProperty("base0[opt[0]]", "5");
		properties.setProperty("base0[opt[1][1][1]]", "6");
		properties.setProperty("base0[opt[1][1][1]][opt[2][2][2]]", "7");
		properties.setProperty("base1[opt1]", "8");
		properties.setProperty("base1[opt1][opt2]", "");

		_testGetPropertyName("base", "0", properties, "base");
		_testGetPropertyName("invalid", null, properties, "invalid");
		_testGetPropertyName(
			"base[opt0]", "1", properties, "base", "opt0", "invalid");
		_testGetPropertyName(
			"base[opt0][opt2]", "2", properties, "base[opt0]", "opt2");
		_testGetPropertyName(
			"base[opt0][opt3]", "3", properties, "base", "opt0", "opt3");
		_testGetPropertyName(
			"base[opt1]", "4", properties, "base", "opt1", null, "invalid");
		_testGetPropertyName(
			"base0[opt[0]]", "5", properties, "base0", "opt[0]");
		_testGetPropertyName(
			"base0[opt[1][1][1]]", "6", properties, "base0", "opt[1][1][1]",
			"invalid");
		_testGetPropertyName(
			"base0[opt[1][1][1]][opt[2][2][2]]", "7", properties, "base0",
			"opt[2][2][2]", "invalid", "opt[1][1][1]", null);
		_testGetPropertyName(
			"base1[opt1][opt2]", "", properties, "base1", "opt1", "opt2");
	}

	@Test
	public void testGetRemoteURL() {
		testEquals(
			"https://test.liferay.com/8/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://test-8/8/ABC?123=456&xyz=abc"));
		testEquals(
			"https://test-1-20.liferay.com/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://test-1-20/ABC?123=456&xyz=abc"));
		testEquals(
			"https://test-4-1.liferay.com/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getRemoteURL(
				"https://test-4-1.liferay.com/ABC?123=456&xyz=abc"));
		testEquals(
			"https://release.liferay.com/1/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getRemoteURL(
				"https://release.liferay.com/1/ABC?123=456&xyz=abc"));
		testEquals(
			"https://release.liferay.com/1/ABC?123=456&xyz=abc",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://release-1/1/ABC?123=456&xyz=abc"));
		testEquals(
			"https://files.liferay.com/private/",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://mirrors.lax.liferay.com/files.liferay.com/private/"));
		testEquals(
			"https://files.liferay.com/private/",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://mirrors.dlc.liferay.com/files.liferay.com/private/"));
		testEquals(
			"https://files.liferay.com/private/",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://mirrors/files.liferay.com/private/"));
		testEquals(
			"https://files.liferay.com/private/",
			JenkinsResultsParserUtil.getRemoteURL(
				"https://files.liferay.com/private/"));
		testEquals(
			"https://releases.liferay.com/portal/",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://mirrors.lax.liferay.com/releases.liferay.com/portal/"));
		testEquals(
			"https://releases.liferay.com/portal/",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://mirrors.dlc.liferay.com/releases.liferay.com/portal/"));
		testEquals(
			"https://releases.liferay.com/portal/",
			JenkinsResultsParserUtil.getRemoteURL(
				"http://mirrors/releases.liferay.com/portal/"));
		testEquals(
			"https://releases.liferay.com/portal/",
			JenkinsResultsParserUtil.getRemoteURL(
				"https://releases.liferay.com/portal/"));
	}

	@Test
	public void testIsJSONArrayEqual() {
		JSONArray expectedJSONArray = new JSONArray();

		expectedJSONArray.put(true);
		expectedJSONArray.put(1.1);
		expectedJSONArray.put(1);
		expectedJSONArray.put("value");

		JSONArray actualJSONArray = new JSONArray();

		actualJSONArray.put(true);
		actualJSONArray.put(1.1);
		actualJSONArray.put(1);
		actualJSONArray.put("value");

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("boolean", true);
		jsonObject.put("double", 1.1);
		jsonObject.put("int", 1);
		jsonObject.put("string", "value");

		expectedJSONArray.put(jsonObject);
		actualJSONArray.put(jsonObject);

		JSONArray jsonArray = new JSONArray();

		jsonArray.put(true);
		jsonArray.put(1.1);
		jsonArray.put(1);
		jsonArray.put("value");

		expectedJSONArray.put(jsonArray);
		actualJSONArray.put(jsonArray);

		if (!JenkinsResultsParserUtil.isJSONArrayEqual(
				expectedJSONArray, actualJSONArray)) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected does not match actual\nExpected: ",
						expectedJSONArray.toString(), "\nActual:   ",
						actualJSONArray.toString())));
		}

		actualJSONArray.put("string2");

		if (JenkinsResultsParserUtil.isJSONArrayEqual(
				expectedJSONArray, actualJSONArray)) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected should not match actual\nExpected: ",
						expectedJSONArray.toString(), "\nActual:   ",
						actualJSONArray.toString())));
		}
	}

	@Test
	public void testIsJSONObjectEqual() {
		JSONObject expectedJSONObject = new JSONObject();

		expectedJSONObject.put("boolean", true);
		expectedJSONObject.put("double", 1.1);
		expectedJSONObject.put("int", 1);
		expectedJSONObject.put("string", "value");

		JSONObject actualJSONObject = new JSONObject();

		actualJSONObject.put("boolean", true);
		actualJSONObject.put("double", 1.1);
		actualJSONObject.put("int", 1);
		actualJSONObject.put("string", "value");

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("boolean", true);
		jsonObject.put("double", 1.1);
		jsonObject.put("int", 1);
		jsonObject.put("string", "value");

		expectedJSONObject.put("json_object", jsonObject);
		actualJSONObject.put("json_object", jsonObject);

		JSONArray jsonArray = new JSONArray();

		jsonArray.put(true);
		jsonArray.put(1.1);
		jsonArray.put(1);
		jsonArray.put("value");

		expectedJSONObject.put("json_array", jsonArray);
		actualJSONObject.put("json_array", jsonArray);

		if (!JenkinsResultsParserUtil.isJSONObjectEqual(
				expectedJSONObject, actualJSONObject)) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected does not match actual\nExpected: ",
						expectedJSONObject.toString(), "\nActual:   ",
						actualJSONObject.toString())));
		}

		actualJSONObject.put("string", "value2");

		if (JenkinsResultsParserUtil.isJSONObjectEqual(
				expectedJSONObject, actualJSONObject)) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected should not match actual\nExpected: ",
						expectedJSONObject.toString(), "\nActual:   ",
						actualJSONObject.toString())));
		}
	}

	@Test
	public void testToJSONObject() throws Exception {
		for (TestSample testSample : testSamples.values()) {
			testToJSONObject(new File(testSample.getSampleDir(), "api/json"));
		}
	}

	@Test
	public void testToString() throws Exception {
		for (TestSample testSample : testSamples.values()) {
			testToString(new File(testSample.getSampleDir(), "api/json"));
		}
	}

	@Override
	protected void downloadSample(TestSample testSample, URL url)
		throws Exception {

		downloadSampleURL(testSample.getSampleDir(), url, "/api/json");
	}

	protected void testToJSONObject(File file) throws Exception {
		JSONObject expectedJSONObject = new JSONObject(read(file));
		JSONObject actualJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(toURLString(file)));

		testEquals(expectedJSONObject.toString(), actualJSONObject.toString());
	}

	protected void testToString(File file) throws Exception {
		String expectedJSON = read(file);
		String actualJSON = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(toURLString(file)));

		testEquals(
			expectedJSON.replace("\n", ""), actualJSON.replace("\n", ""));
	}

	@Override
	protected String toURLString(File file) throws Exception {
		URI uri = file.toURI();

		URL url = uri.toURL();

		return url.toString();
	}

	private String _fixURLMultipleTimes(String urlString) {
		return JenkinsResultsParserUtil.fixURL(
			JenkinsResultsParserUtil.fixURL(
				JenkinsResultsParserUtil.fixURL(urlString)));
	}

	private void _testGetProperty(
		String expectedPropertyValue, Properties properties,
		String basePropertyName, String... propertyOpts) {

		testEquals(
			expectedPropertyValue,
			JenkinsResultsParserUtil.getProperty(
				properties, basePropertyName, propertyOpts));
	}

	private void _testGetPropertyName(
		String expectedPropertyName, String expectedPropertyValue,
		Properties properties, String basePropertyName,
		String... propertyOpts) {

		String actualPropertyName = JenkinsResultsParserUtil.getPropertyName(
			properties, basePropertyName, propertyOpts);

		testEquals(expectedPropertyName, actualPropertyName);

		testEquals(
			expectedPropertyValue,
			JenkinsResultsParserUtil.getProperty(
				properties, actualPropertyName));
	}

}