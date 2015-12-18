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

package com.liferay.jenkins.results.parser.load.balancer;

import com.liferay.jenkins.results.parser.BaseJenkinsResultsParserTestCase;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.net.URL;

import java.util.Map;

import org.apache.tools.ant.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class LoadBalancerUtilTest extends BaseJenkinsResultsParserTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample("test-1", null);
		downloadSample("test-2", null);
	}

	@After
	public void tearDown() throws Exception {
		Project project = getTestProject(null);

		deleteFile(project.getProperty("jenkins.shared.dir"));
	}

	@Test
	public void testGetMostAvailableMasterURL() throws Exception {
		LoadBalancerUtil.recentJobPeriod = 0;

		assertSamples();
	}

	protected static Project getDownloadProject(String baseInvocationHostName) {
		Project project = new Project();

		project.setProperty(
			"base.invocation.url",
			"http://" + baseInvocationHostName + ".liferay.com");
		project.setProperty(
			"jenkins.shared.dir", "mnt/mfs-ssd1-10.10/jenkins/tmp");
		project.setProperty("jenkins.local.url[test-1-1]", "http://test-1-1");
		project.setProperty("jenkins.local.url[test-1-2]", "http://test-1-2");
		project.setProperty("jenkins.local.url[test-1-3]", "http://test-1-3");
		project.setProperty("jenkins.local.url[test-1-4]", "http://test-1-4");
		project.setProperty("jenkins.local.url[test-1-5]", "http://test-1-5");
		project.setProperty("jenkins.local.url[test-1-6]", "http://test-1-6");
		project.setProperty("jenkins.local.url[test-1-7]", "http://test-1-7");
		project.setProperty("jenkins.local.url[test-1-8]", "http://test-1-8");
		project.setProperty("jenkins.local.url[test-1-9]", "http://test-1-9");
		project.setProperty("jenkins.local.url[test-1-10]", "http://test-1-10");
		project.setProperty("jenkins.local.url[test-1-11]", "http://test-1-11");
		project.setProperty("jenkins.local.url[test-1-12]", "http://test-1-12");
		project.setProperty("jenkins.local.url[test-1-13]", "http://test-1-13");
		project.setProperty("jenkins.local.url[test-1-14]", "http://test-1-14");
		project.setProperty("jenkins.local.url[test-1-15]", "http://test-1-15");
		project.setProperty("jenkins.local.url[test-1-16]", "http://test-1-16");
		project.setProperty("jenkins.local.url[test-1-17]", "http://test-1-17");
		project.setProperty("jenkins.local.url[test-1-18]", "http://test-1-18");
		project.setProperty("jenkins.local.url[test-1-19]", "http://test-1-19");
		project.setProperty("jenkins.local.url[test-1-20]", "http://test-1-20");
		project.setProperty("jenkins.local.url[test-2-1]", "http://test-2-1");
		project.setProperty("jenkins.local.url[test-3-1]", "http://test-3-1");
		project.setProperty("jenkins.local.url[test-3-2]", "http://test-3-2");
		project.setProperty("jenkins.local.url[test-3-3]", "http://test-3-3");
		project.setProperty("invoked.batch.size", "2");

		return project;
	}

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		Project project = getDownloadProject(sampleDir.getName());

		int hostNameCount = LoadBalancerUtil.getHostNameCount(
			project, sampleDir.getName());

		for (int i = 1; i <= hostNameCount; i++) {
			downloadSampleURL(
				new File(sampleDir, sampleDir.getName() + "-" + i),
				JenkinsResultsParserUtil.createURL(
					project.getProperty(
						"jenkins.local.url[" + sampleDir.getName() + "-" + i +
						"]")),
					"/computer/api/json?pretty&tree=computer" +
						"[displayName,idle,offline]");
			downloadSampleURL(
				new File(sampleDir, sampleDir.getName() + "-" + i),
				JenkinsResultsParserUtil.createURL(
					project.getProperty(
						"jenkins.local.url[" + sampleDir.getName() + "-" + i +
						"]")),
				"/queue/api/json");
		}
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		File sampleDir = new File(urlString.substring("file:".length()));

		Project project = getTestProject(sampleDir.getName());

		return LoadBalancerUtil.getMostAvailableMasterURL(project);
	}

	protected Project getTestProject(String baseInvocationHostName) {
		Project project = getDownloadProject(baseInvocationHostName);

		Map<String, Object> properties = project.getProperties();

		for (String key : properties.keySet()) {
			if (key.equals("base.invocation.url")) {
				continue;
			}

			String value = (String)properties.get(key);

			if (value.contains("http://")) {
				value = value.replace(
					"http://",
					"file:" + dependenciesDir.getAbsolutePath() + "/" +
						baseInvocationHostName + "/");

				project.setProperty(key, value);
			}
		}

		return project;
	}

}