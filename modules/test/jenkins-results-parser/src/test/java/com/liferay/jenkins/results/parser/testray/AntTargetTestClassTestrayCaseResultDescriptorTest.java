/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.BaseAntTargetTestClass;
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
public class AntTargetTestClassTestrayCaseResultDescriptorTest
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
	public void testGetName() {
		String antTargetName = RandomTestUtil.randomString();
		String axisName = RandomTestUtil.randomString();
		String batchName = RandomTestUtil.randomString();
		String testClassName = RandomTestUtil.randomString();

		_testGetName(
			"", axisName, batchName, batchName + "[" + testClassName + "]",
			testClassName);
		_testGetName(
			antTargetName, axisName, batchName,
			batchName + "[" + antTargetName + "]", testClassName);

		_testGetName(null, axisName, batchName, axisName, null);
		_testGetName(
			null, axisName, batchName, batchName + "[" + testClassName + "]",
			testClassName);
	}

	@Test
	public void testGetTeamName() {
		String teamName = RandomTestUtil.randomString();
		String buildTeamName = RandomTestUtil.randomString();

		_testGetTeamName(buildTeamName, buildTeamName, null);
		_testGetTeamName(buildTeamName, teamName, teamName);
	}

	private void _testGetName(
		String antTargetName, String axisName, String batchName,
		String expectedName, String testClassName) {

		BaseAntTargetTestClass baseAntTargetTestClass = null;

		if (testClassName != null) {
			baseAntTargetTestClass = Mockito.mock(BaseAntTargetTestClass.class);

			Mockito.doReturn(
				antTargetName
			).when(
				baseAntTargetTestClass
			).getAntTargetName();

			Mockito.doReturn(
				testClassName
			).when(
				baseAntTargetTestClass
			).getName();
		}

		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		Mockito.doReturn(
			axisName
		).when(
			axisTestClassGroup
		).getAxisName();

		Mockito.doReturn(
			batchName
		).when(
			axisTestClassGroup
		).getBatchName();

		AntTargetTestClassTestrayCaseResultDescriptor
			antTargetTestClassTestrayCaseResultDescriptor =
				new AntTargetTestClassTestrayCaseResultDescriptor(
					axisTestClassGroup, baseAntTargetTestClass, null);

		testEquals(
			expectedName,
			antTargetTestClassTestrayCaseResultDescriptor.getName());
	}

	private void _testGetTeamName(
		String buildTeamName, String expectedTeamName, String teamName) {

		Properties buildProperties = new Properties();

		String batchName = RandomTestUtil.randomString();

		buildProperties.setProperty(
			"testray.case.team[" + batchName + "]", buildTeamName);

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		Mockito.doReturn(
			batchName
		).when(
			axisTestClassGroup
		).getBatchName();

		Mockito.doReturn(
			TestrayCaseResultDescriptorTestUtil.newJob(new Properties())
		).when(
			axisTestClassGroup
		).getJob();

		BaseAntTargetTestClass baseAntTargetTestClass = Mockito.mock(
			BaseAntTargetTestClass.class);

		Mockito.doReturn(
			teamName
		).when(
			baseAntTargetTestClass
		).getTestrayTeamName();

		AntTargetTestClassTestrayCaseResultDescriptor
			antTargetTestClassTestrayCaseResultDescriptor =
				new AntTargetTestClassTestrayCaseResultDescriptor(
					axisTestClassGroup, baseAntTargetTestClass, null);

		testEquals(
			expectedTeamName,
			antTargetTestClassTestrayCaseResultDescriptor.getTeamName());
	}

}