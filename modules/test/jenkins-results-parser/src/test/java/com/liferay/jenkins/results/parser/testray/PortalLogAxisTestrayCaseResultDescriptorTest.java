/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.File;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class PortalLogAxisTestrayCaseResultDescriptorTest
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
		Properties buildProperties = new Properties();

		String componentName = RandomTestUtil.randomString();

		buildProperties.setProperty("testray.case.component", componentName);

		String batchName = RandomTestUtil.randomString();

		buildProperties.setProperty(
			"testray.case.component[" + batchName + "]",
			RandomTestUtil.randomString());

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		Mockito.doReturn(
			batchName
		).when(
			axisTestClassGroup
		).getBatchName();

		PortalLogAxisTestrayCaseResultDescriptor
			portalLogAxisTestrayCaseResultDescriptor =
				new PortalLogAxisTestrayCaseResultDescriptor(
					axisTestClassGroup, null);

		testEquals(
			componentName,
			portalLogAxisTestrayCaseResultDescriptor.getComponentName());
	}

	@Test
	public void testGetName() {
		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		String axisName = RandomTestUtil.randomString();

		Mockito.doReturn(
			axisName
		).when(
			axisTestClassGroup
		).getAxisName();

		PortalLogAxisTestrayCaseResultDescriptor
			portalLogAxisTestrayCaseResultDescriptor =
				new PortalLogAxisTestrayCaseResultDescriptor(
					axisTestClassGroup, null);

		testEquals(
			"PortalLogAssertorTest-" + axisName,
			portalLogAxisTestrayCaseResultDescriptor.getName());
	}

}