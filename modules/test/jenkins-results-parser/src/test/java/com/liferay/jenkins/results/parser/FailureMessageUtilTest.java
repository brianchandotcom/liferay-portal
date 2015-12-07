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

import org.apache.tools.ant.Project;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class FailureMessageUtilTest extends BaseJenkinsResultsParserTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample(
			"generic-1", "0,label_exp=!master", "129",
			"test-portal-acceptance-pullrequest-batch(master)", "test-4-1");
		downloadSample(
			"generic-2", "1,label_exp=!master", "4961",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-13");
		downloadSample(
			"rebase-1", null, "267",
			"test-portal-acceptance-pullrequest-source(ee-6.2.x)", "test-1-1");
		downloadSample(
			"plugin-compile-1", "9,label_exp=!master", "233",
			"test-portal-acceptance-pullrequest-batch(ee-6.2.x)", "test-1-20");
		downloadSample(
			"sourceformat-1", "1,label_exp=!master", "7031",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-14");
	}

	@Test
	public void testGetFailureMessage() throws Exception {
		assertSamples();
	}

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		downloadSampleURL(sampleDir, url, "/api/json");
		downloadSampleURL(sampleDir, url, "/logText/progressiveText");
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

		downloadSample(sampleKey + "-" + jobName, url);
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		Project project = getProject();

		return FailureMessageUtil.getFailureMessage(project, urlString);
	}

	protected Project getProject() {
		Project project = new Project();

		project.setProperty(
			"github.pull.request.head.branch", "junit-pr-head-branch");
		project.setProperty(
			"github.pull.request.head.username", "junit-pr-head-username");
		project.setProperty("plugins.branch.name", "junit-plugins-branch-name");
		project.setProperty("plugins.repository", "junit-plugins-repository");
		project.setProperty("portal.repository", "junit-portal-repository");
		project.setProperty("repository", "junit-repository");

		return project;
	}

}