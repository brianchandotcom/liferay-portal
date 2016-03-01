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

import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class UnstableMessageUtilTest extends BaseJenkinsResultsParserTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample(
			"junit-exception-1", "4,label_exp=!master", "6890",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-19");
		downloadSample(
			"poshi-1", "UIInfrastructureUsecase#Smoke,label_exp=!master",
			"9616", "test-portal-acceptance-pullrequest-batch(master)",
			"test-1-13");
		downloadSample(
			"sourceformat-1", "0,label_exp=!master", "17503",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-5");
	}

	@Test
	public void testGetUnstableMessage() throws Exception {
		assertSamples();
	}

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		downloadSampleURL(sampleDir, url, "/api/json");
		downloadSampleURL(sampleDir, url, "/logText/progressiveText");
		downloadSampleURL(sampleDir, url, "/testReport/api/json");
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

		downloadSample(sampleKey + "-" + jobName, url);
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		return formatXML(
			"<div>" + UnstableMessageUtil.getUnstableMessage(urlString) +
				"</div>");
	}

}