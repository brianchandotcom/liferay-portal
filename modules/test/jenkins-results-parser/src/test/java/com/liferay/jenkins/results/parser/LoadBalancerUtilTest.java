/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

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
public class LoadBalancerUtilTest
	extends com.liferay.jenkins.results.parser.Test {

	@Before
	@Override
	public void setUp() throws Exception {
		downloadSample("test-1", null);
		downloadSample("test-2", null);

		LoadBalancerUtil.setUpdateInterval(0);
	}

	@After
	public void tearDown() {
		Properties properties = getTestProperties();

		deleteFile(properties.getProperty("jenkins.shared.dir"));
	}

	@Test
	public void testGetMostAvailableMasterURL() throws Exception {
		JenkinsMaster.maxRecentBatchAge = 0;

		expectedMessageGenerator = new ExpectedMessageGenerator() {

			@Override
			public String getMessage(TestSample testSample) throws Exception {
				Properties properties = getTestProperties(testSample);

				JenkinsResultsParserUtil.setBuildProperties(properties);

				return LoadBalancerUtil.getMostAvailableMasterURL(properties);
			}

		};

		assertSamples();
	}

	protected static Properties getDownloadProperties(
		String baseInvocationHostName) {

		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to download build properties", ioException);
		}

		Properties properties = new Properties();

		_copyProperties(
			buildProperties, properties, "jenkins.admin.user.name",
			"jenkins.admin.user.token");

		properties.setProperty(
			"base.invocation.url",
			"http://" + baseInvocationHostName + ".liferay.com");
		properties.setProperty(
			"jenkins.shared.dir", "mnt/mfs-ssd1-10.10/jenkins/tmp");

		for (int i = 1; i <= 20; i++) {
			String jenkinsMasterName = "test-1-" + i;

			properties.put(
				JenkinsResultsParserUtil.combine(
					"jenkins.local.url[", jenkinsMasterName, "]"),
				"http://" + jenkinsMasterName);

			_copyProperties(
				buildProperties, properties,
				JenkinsResultsParserUtil.combine(
					"master.slaves(", jenkinsMasterName, ")"));
		}

		properties.put(
			JenkinsResultsParserUtil.combine("jenkins.local.url[test-2-1]"),
			"http://test-2-1");

		_copyProperties(
			buildProperties, properties, "jenkins.local.url[test-2-1]",
			"master.slaves(test-2-1)");

		for (int i = 1; i <= 2; i++) {
			String jenkinsMasterName = "test-3-" + i;

			properties.put(
				JenkinsResultsParserUtil.combine(
					"jenkins.local.url[", jenkinsMasterName, "]"),
				"http://" + jenkinsMasterName);

			_copyProperties(
				buildProperties, properties,
				JenkinsResultsParserUtil.combine(
					"master.slaves(", jenkinsMasterName, ")"));
		}

		properties.setProperty("invoked.batch.size", "2");

		return properties;
	}

	@Override
	protected void downloadSample(TestSample testSample, URL url)
		throws Exception {

		String sampleKey = testSample.getSampleKey();

		Properties properties = getDownloadProperties(sampleKey);

		JenkinsResultsParserUtil.setBuildProperties(properties);

		List<JenkinsMaster> jenkinsMasters =
			JenkinsResultsParserUtil.getJenkinsMasters(
				properties, JenkinsMaster.getSlaveRAMMinimumDefault(),
				JenkinsMaster.getSlavesPerHostDefault(), sampleKey);

		File sampleDir = testSample.getSampleDir();

		for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
			downloadSampleURL(
				new File(sampleDir, jenkinsMaster.getName()),
				JenkinsResultsParserUtil.createURL(jenkinsMaster.getURL()),
				JenkinsResultsParserUtil.combine(
					"/computer/api/json?tree=computer[displayName,",
					"executors[currentExecutable[url]],idle,offline]"));

			downloadSampleURL(
				new File(sampleDir, jenkinsMaster.getName()),
				JenkinsResultsParserUtil.createURL(jenkinsMaster.getURL()),
				JenkinsResultsParserUtil.combine(
					"/queue/api/json?tree=items[task[name,url],url,why]"));
		}
	}

	protected Properties getTestProperties() {
		return getTestProperties(null, null);
	}

	protected Properties getTestProperties(
		String hostName, String sampleDirName) {

		Properties properties = getDownloadProperties(hostName);

		if (sampleDirName != null) {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				Object key = entry.getKey();

				if (key.equals("base.invocation.url")) {
					continue;
				}

				String value = (String)entry.getValue();

				if (value.contains("http://")) {
					value = value.replace(
						"http://", "${dependencies.url}" + sampleDirName + "/");

					entry.setValue(value);
				}
			}
		}

		return properties;
	}

	protected Properties getTestProperties(TestSample testSample) {
		return getTestProperties(
			testSample.getSampleKey(), testSample.getSampleDirName());
	}

	private static void _copyProperties(
		Properties sourceProperties, Properties targetProperties,
		String... propertyNames) {

		for (String propertyName : propertyNames) {
			if (sourceProperties.containsKey(propertyName)) {
				targetProperties.put(
					propertyName, sourceProperties.getProperty(propertyName));
			}
		}
	}

}