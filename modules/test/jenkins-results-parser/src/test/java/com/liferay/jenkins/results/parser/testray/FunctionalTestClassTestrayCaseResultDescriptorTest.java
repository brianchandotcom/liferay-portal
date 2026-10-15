/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.FunctionalTestClass;
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
public class FunctionalTestClassTestrayCaseResultDescriptorTest
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

		_testGetComponentName(null, null);
	}

	@Test
	public void testGetName() {
		String testClassMethodName = RandomTestUtil.randomString();

		FunctionalTestClass functionalTestClass = Mockito.mock(
			FunctionalTestClass.class);

		Mockito.doReturn(
			testClassMethodName
		).when(
			functionalTestClass
		).getTestClassMethodName();

		FunctionalTestClassTestrayCaseResultDescriptor
			functionalTestClassTestrayCaseResultDescriptor =
				new FunctionalTestClassTestrayCaseResultDescriptor(
					Mockito.mock(AxisTestClassGroup.class), functionalTestClass,
					null);

		testEquals(
			testClassMethodName,
			functionalTestClassTestrayCaseResultDescriptor.getName());
	}

	@Test
	public void testGetPriority() {
		_testGetPriority(2, "2");
		_testGetPriority(5, "99999999999");
		_testGetPriority(5, "high");
		_testGetPriority(5, null);
	}

	@Test
	public void testGetSubcomponentNames() {
		Properties poshiProperties = new Properties();

		String subcomponentNames = RandomTestUtil.randomString();

		poshiProperties.setProperty(
			"testray.component.names", subcomponentNames);

		FunctionalTestClassTestrayCaseResultDescriptor
			functionalTestClassTestrayCaseResultDescriptor =
				_newFunctionalTestClassTestrayCaseResultDescriptor(
					Mockito.mock(AxisTestClassGroup.class), poshiProperties);

		testEquals(
			subcomponentNames,
			functionalTestClassTestrayCaseResultDescriptor.
				getSubcomponentNames());
	}

	private FunctionalTestClassTestrayCaseResultDescriptor
		_newFunctionalTestClassTestrayCaseResultDescriptor(
			AxisTestClassGroup axisTestClassGroup, Properties poshiProperties) {

		FunctionalTestClass functionalTestClass = Mockito.mock(
			FunctionalTestClass.class);

		Mockito.doReturn(
			poshiProperties
		).when(
			functionalTestClass
		).getPoshiProperties();

		return new FunctionalTestClassTestrayCaseResultDescriptor(
			axisTestClassGroup, functionalTestClass, null);
	}

	private void _testGetComponentName(
		String componentName, String expectedComponentName) {

		Properties buildProperties = new Properties();

		String batchName = RandomTestUtil.randomString();

		buildProperties.setProperty(
			"testray.case.component[" + batchName + "]",
			RandomTestUtil.randomString());

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Properties jobProperties = new Properties();

		jobProperties.setProperty(
			"testray.component.name[" + batchName + "]",
			RandomTestUtil.randomString());

		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		Mockito.doReturn(
			batchName
		).when(
			axisTestClassGroup
		).getBatchName();

		Mockito.doReturn(
			TestrayCaseResultDescriptorTestUtil.newJob(jobProperties)
		).when(
			axisTestClassGroup
		).getJob();

		Properties poshiProperties = new Properties();

		if (componentName != null) {
			poshiProperties.setProperty(
				"testray.main.component.name", componentName);
		}

		FunctionalTestClassTestrayCaseResultDescriptor
			functionalTestClassTestrayCaseResultDescriptor =
				_newFunctionalTestClassTestrayCaseResultDescriptor(
					axisTestClassGroup, poshiProperties);

		testEquals(
			expectedComponentName,
			functionalTestClassTestrayCaseResultDescriptor.getComponentName());
	}

	private void _testGetPriority(int expectedPriority, String priority) {
		Properties poshiProperties = new Properties();

		if (priority != null) {
			poshiProperties.setProperty("priority", priority);
		}

		FunctionalTestClassTestrayCaseResultDescriptor
			functionalTestClassTestrayCaseResultDescriptor =
				_newFunctionalTestClassTestrayCaseResultDescriptor(
					Mockito.mock(AxisTestClassGroup.class), poshiProperties);

		testEquals(
			expectedPriority,
			functionalTestClassTestrayCaseResultDescriptor.getPriority());
	}

}