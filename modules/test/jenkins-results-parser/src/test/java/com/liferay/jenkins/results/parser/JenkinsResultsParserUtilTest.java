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

import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtilTest
	extends BaseJenkinsResultsParserTestCase {

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
	public void testExpandSlaveRange() {
		Assert.assertEquals(
			"cloud-10-50-0-151,cloud-10-50-0-152,cloud-10-50-0-153," +
				"cloud-10-50-0-154,cloud-10-50-0-155,cloud-10-50-0-156",
			JenkinsResultsParserUtil.expandSlaveRange(
				"cloud-10-50-0-151..156"));
		Assert.assertEquals(
			"cloud-10-50-0-47,cloud-10-50-0-0,cloud-10-50-0-1," +
				"cloud-10-50-0-2,cloud-10-50-0-49,cloud-10-50-0-50",
				JenkinsResultsParserUtil.expandSlaveRange(
					"cloud-10-50-0-47, cloud-10-50-0-0..2, " +
					"cloud-10-50-0-49..50"));
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
				read(dependenciesDir, "/axis-integration-db2-1/api/json")));
		Assert.assertEquals(
			"plugins",
			JenkinsResultsParserUtil.getJobVariant(
				read(dependenciesDir, "/axis-plugin-1/api/json")));
		Assert.assertEquals(
			"",
			JenkinsResultsParserUtil.getJobVariant(
				read(dependenciesDir, "/job-1/api/json")));
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
			testToJSONObject(new File(file, "api/json"));
		}
	}

	@Test
	public void testToString() throws Exception {
		for (File file : dependenciesDir.listFiles()) {
			testToString(new File(file, "api/json"));
		}
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

		URL url = JenkinsResultsParserUtil.createURL(urlString);

		downloadSample(sampleKey, url);
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		return null;
	}

	protected void testToJSONObject(File file) throws Exception {
		JSONObject expectedJSONObject = new JSONObject(read(file));
		JSONObject actualJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(toURLString(file)));

		Assert.assertEquals(
			expectedJSONObject.toString(), actualJSONObject.toString());
	}

	protected void testToString(File file) throws Exception {
		String expectedJSONString = read(file);
		String actualJSONString = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(toURLString(file)));

		Assert.assertEquals(
			expectedJSONString.replace("\n", ""),
			actualJSONString.replace("\n", ""));
	}

	@Override
	protected void writeExpectedMessage(File sampleDir) throws Exception {
	}

}