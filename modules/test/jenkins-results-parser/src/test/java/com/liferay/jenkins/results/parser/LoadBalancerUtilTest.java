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

import java.util.List;
import java.util.Map;
import java.util.Properties;

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

		LoadBalancerUtil.setUpdateInterval(0);
	}

	@After
	public void tearDown() throws Exception {
		Properties properties = getTestProperties(null);

		deleteFile(properties.getProperty("jenkins.shared.dir"));
	}

	@Test
	public void testGetMostAvailableMasterURL() throws Exception {
		JenkinsMaster.maxRecentBatchAge = 0;

		assertSamples();
	}

	protected static Properties getDownloadProperties(
		String baseInvocationHostName) {

		Properties properties = new Properties();

		properties.setProperty(
			"base.invocation.url",
			"http://" + baseInvocationHostName + ".liferay.com");
		properties.setProperty(
			"jenkins.shared.dir", "mnt/mfs-ssd1-10.10/jenkins/tmp");
		properties.setProperty(
			"jenkins.local.url[test-1-1]", "http://test-1-1");
		properties.setProperty(
			"jenkins.local.url[test-1-2]", "http://test-1-2");
		properties.setProperty(
			"jenkins.local.url[test-1-3]", "http://test-1-3");
		properties.setProperty(
			"jenkins.local.url[test-1-4]", "http://test-1-4");
		properties.setProperty(
			"jenkins.local.url[test-1-5]", "http://test-1-5");
		properties.setProperty(
			"jenkins.local.url[test-1-6]", "http://test-1-6");
		properties.setProperty(
			"jenkins.local.url[test-1-7]", "http://test-1-7");
		properties.setProperty(
			"jenkins.local.url[test-1-8]", "http://test-1-8");
		properties.setProperty(
			"jenkins.local.url[test-1-9]", "http://test-1-9");
		properties.setProperty(
			"jenkins.local.url[test-1-10]", "http://test-1-10");
		properties.setProperty(
			"jenkins.local.url[test-1-11]", "http://test-1-11");
		properties.setProperty(
			"jenkins.local.url[test-1-12]", "http://test-1-12");
		properties.setProperty(
			"jenkins.local.url[test-1-13]", "http://test-1-13");
		properties.setProperty(
			"jenkins.local.url[test-1-14]", "http://test-1-14");
		properties.setProperty(
			"jenkins.local.url[test-1-15]", "http://test-1-15");
		properties.setProperty(
			"jenkins.local.url[test-1-16]", "http://test-1-16");
		properties.setProperty(
			"jenkins.local.url[test-1-17]", "http://test-1-17");
		properties.setProperty(
			"jenkins.local.url[test-1-18]", "http://test-1-18");
		properties.setProperty(
			"jenkins.local.url[test-1-19]", "http://test-1-19");
		properties.setProperty(
			"jenkins.local.url[test-1-20]", "http://test-1-20");
		properties.setProperty(
			"jenkins.local.url[test-2-1]", "http://test-2-1");
		properties.setProperty(
			"jenkins.local.url[test-3-1]", "http://test-3-1");
		properties.setProperty(
			"jenkins.local.url[test-3-2]", "http://test-3-2");
		properties.setProperty(
			"jenkins.local.url[test-3-3]", "http://test-3-3");

		for (int i = 1; i <= 20; i++) {
			properties.setProperty("master.slaves(test-1-" + i + ")", "");
		}

		properties.setProperty("master.slaves(test-2-1)", "");

		for (int i = 1; i <= 3; i++) {
			properties.setProperty("master.slaves(test-3-" + i + ")", "");
		}

		properties.setProperty("invoked.batch.size", "2");

		return properties;
	}

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		Properties properties = getDownloadProperties(sampleDir.getName());

		JenkinsResultsParserUtil.setBuildProperties(properties);

		List<JenkinsMaster> jenkinsMasters =
			JenkinsResultsParserUtil.getJenkinsMasters(
				properties, sampleDir.getName());

		for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
			downloadSampleURL(
				new File(sampleDir, jenkinsMaster.getMasterName()),
				JenkinsResultsParserUtil.createURL(
					jenkinsMaster.getMasterURL()),
				"/computer/api/json?pretty&tree=computer" +
					"[displayName,idle,offline]");

			downloadSampleURL(
				new File(sampleDir, jenkinsMaster.getMasterName()),
				JenkinsResultsParserUtil.createURL(
					jenkinsMaster.getMasterURL()),
				"/queue/api/json");
		}
	}

	@Override
	protected String getMessage(File sampleDir) throws Exception {
		Properties properties = getTestProperties(sampleDir.getName());

		JenkinsResultsParserUtil.setBuildProperties(properties);

		return LoadBalancerUtil.getMostAvailableMasterURL(properties);
	}

	protected Properties getTestProperties(String baseInvocationHostName) {
		Properties properties = getDownloadProperties(baseInvocationHostName);

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			Object key = entry.getKey();

			if (key.equals("base.invocation.url")) {
				continue;
			}

			String value = (String)entry.getValue();

			if (value.contains("http://")) {
				Class<?> clazz = getClass();

				value = value.replace(
					"http://",
					"${dependencies.url}" + clazz.getSimpleName() + "/" +
						baseInvocationHostName + "/");

				entry.setValue(value);
			}
		}

		return properties;
	}

}