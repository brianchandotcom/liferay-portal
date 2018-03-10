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
import java.io.FileOutputStream;
import java.io.StringReader;

import java.net.URL;

import java.util.Hashtable;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;

/**
 * @author Peter Yoo
 */
public class BuildTest extends Test {

	@Before
	public void setUp() throws Exception {
		JenkinsResultsParserUtil.setBuildProperties(
			JenkinsResultsParserUtil.getBuildProperties());

		downloadSample(
			"test-portal-acceptance-pullrequest(7.0.x-private)" +
				"_validation-passed",
			"77", "test-portal-acceptance-pullrequest(7.0.x-private)",
			"test-1-10");
	}

	@After
	public void tearDown() throws Exception {
		JenkinsResultsParserUtil.setBuildProperties((Hashtable<?, ?>)null);
	}

	@Override
	protected void downloadSample(TestSample testSample, URL url)
		throws Exception {

		Build build = BuildFactory.newBuild(
			JenkinsResultsParserUtil.getLocalURL(url.toExternalForm()), null);

		build.archive(testSample.getSampleDirName());
	}

	protected Properties loadProperties(String sampleName) throws Exception {
		Properties properties = new Properties();

		TestSample testSample = testSamples.get(sampleName);

		String content = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				toURLString(
					new File(testSample.getSampleDir(), "sample.properties"))));

		properties.load(new StringReader(content));

		return properties;
	}

	protected void saveProperties(File file, Properties properties)
		throws Exception {

		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			properties.store(fileOutputStream, null);
		}
	}

}