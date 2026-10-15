/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;

import java.io.File;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class AppServerBundleBuildTestrayCaseResultDescriptorTest
	extends com.liferay.jenkins.results.parser.Test {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		File cacheDir = TestrayCaseResultDescriptorTestUtil.newCacheDir();

		mockEnvironment(
			Collections.singletonMap("CACHE_DIR", cacheDir.getPath()));
	}

	@After
	@Override
	public void tearDown() {
		JenkinsResultsParserUtil.setBuildProperties(
			(Hashtable<Object, Object>)null);

		super.tearDown();
	}

	@Test
	public void testGetComponentName() {
		String componentName = RandomTestUtil.randomString();

		_testGetComponentName(componentName, componentName);

		_testGetComponentName(null, "App Server Bundle Builder");
	}

	@Test
	public void testGetName() {
		String appServerType = RandomTestUtil.randomString();

		AppServerBundleBuildTestrayCaseResultDescriptor
			appServerBundleBuildTestrayCaseResultDescriptor =
				new AppServerBundleBuildTestrayCaseResultDescriptor(
					appServerType, null, null);

		testEquals(
			"App Server Bundle Builder > " + appServerType,
			appServerBundleBuildTestrayCaseResultDescriptor.getName());
	}

	private void _testGetComponentName(
		String componentName, String expectedComponentName) {

		Properties buildProperties = new Properties();

		if (componentName != null) {
			buildProperties.setProperty(
				"testray.case.component[app-server-bundle-builder]",
				componentName);
		}

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		AppServerBundleBuildTestrayCaseResultDescriptor
			appServerBundleBuildTestrayCaseResultDescriptor =
				new AppServerBundleBuildTestrayCaseResultDescriptor(
					RandomTestUtil.randomString(), null, null);

		testEquals(
			expectedComponentName,
			appServerBundleBuildTestrayCaseResultDescriptor.getComponentName());
	}

}