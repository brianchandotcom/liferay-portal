/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightTestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.PlaywrightSegmentTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class PlaywrightTestClassMethodTestrayCaseResultDescriptorTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetName() {
		String parentDirName = RandomTestUtil.randomString();
		String variantName = RandomTestUtil.randomString();

		String projectName = JenkinsResultsParserUtil.combine(
			parentDirName, ".", variantName);

		String otherDirName = RandomTestUtil.randomString();
		String sharedDirName = RandomTestUtil.randomString();
		String specFileName = RandomTestUtil.randomString();
		String testName = RandomTestUtil.randomString();
		String testClassMethodName = RandomTestUtil.randomString();

		_testGetName(
			JenkinsResultsParserUtil.combine(
				otherDirName, "/", variantName, "/", specFileName, " > ",
				testName),
			_mockPlaywrightSegmentTestClassGroup(projectName),
			JenkinsResultsParserUtil.combine(
				otherDirName, "/", sharedDirName, "/", specFileName),
			testClassMethodName, testName);

		String subDirName = RandomTestUtil.randomString();

		_testGetName(
			JenkinsResultsParserUtil.combine(
				parentDirName, "/", subDirName, "/", variantName, "/",
				specFileName, " > ", testName),
			_mockPlaywrightSegmentTestClassGroup(
				JenkinsResultsParserUtil.combine(
					parentDirName, ".", subDirName, ".", variantName)),
			JenkinsResultsParserUtil.combine(
				parentDirName, "/", subDirName, "/", sharedDirName, "/",
				specFileName),
			testClassMethodName, testName);

		_testGetName(
			JenkinsResultsParserUtil.combine(
				parentDirName, "/", variantName, "/", specFileName, " > ",
				testName),
			_mockPlaywrightSegmentTestClassGroup(projectName),
			JenkinsResultsParserUtil.combine(parentDirName, "/", specFileName),
			testClassMethodName, testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				variantName, "/", specFileName, " > ", testName),
			_mockPlaywrightSegmentTestClassGroup(variantName),
			JenkinsResultsParserUtil.combine(sharedDirName, "/", specFileName),
			testClassMethodName, testName);
		_testGetName(
			testClassMethodName, Mockito.mock(SegmentTestClassGroup.class),
			JenkinsResultsParserUtil.combine(sharedDirName, "/", specFileName),
			testClassMethodName, testName);
		_testGetName(
			testClassMethodName, _mockPlaywrightSegmentTestClassGroup(""),
			JenkinsResultsParserUtil.combine(sharedDirName, "/", specFileName),
			testClassMethodName, testName);
		_testGetName(
			testClassMethodName, _mockPlaywrightSegmentTestClassGroup(null),
			JenkinsResultsParserUtil.combine(sharedDirName, "/", specFileName),
			testClassMethodName, testName);
		_testGetName(
			testClassMethodName,
			_mockPlaywrightSegmentTestClassGroup(projectName), "",
			testClassMethodName, testName);
		_testGetName(
			testClassMethodName,
			_mockPlaywrightSegmentTestClassGroup(projectName),
			JenkinsResultsParserUtil.combine(
				parentDirName, "/", variantName, "/", specFileName),
			testClassMethodName, testName);
		_testGetName(
			testClassMethodName,
			_mockPlaywrightSegmentTestClassGroup(projectName), null,
			testClassMethodName, testName);
		_testGetName(
			testClassMethodName,
			_mockPlaywrightSegmentTestClassGroup(projectName), specFileName,
			testClassMethodName, testName);
	}

	private PlaywrightSegmentTestClassGroup
		_mockPlaywrightSegmentTestClassGroup(String projectName) {

		PlaywrightSegmentTestClassGroup playwrightSegmentTestClassGroup =
			Mockito.mock(PlaywrightSegmentTestClassGroup.class);

		Mockito.doReturn(
			projectName
		).when(
			playwrightSegmentTestClassGroup
		).getProjectName();

		return playwrightSegmentTestClassGroup;
	}

	private void _testGetName(
		String expectedName, SegmentTestClassGroup segmentTestClassGroup,
		String specFilePath, String testClassMethodName, String testName) {

		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		Mockito.doReturn(
			segmentTestClassGroup
		).when(
			axisTestClassGroup
		).getSegmentTestClassGroup();

		PlaywrightJUnitTestClass playwrightJUnitTestClass = Mockito.mock(
			PlaywrightJUnitTestClass.class);

		Mockito.doReturn(
			specFilePath
		).when(
			playwrightJUnitTestClass
		).getSpecFilePath();

		PlaywrightTestClassMethod playwrightTestClassMethod = Mockito.mock(
			PlaywrightTestClassMethod.class);

		Mockito.doReturn(
			testClassMethodName
		).when(
			playwrightTestClassMethod
		).getName();

		Mockito.doReturn(
			playwrightJUnitTestClass
		).when(
			playwrightTestClassMethod
		).getTestClass();

		Mockito.doReturn(
			testName
		).when(
			playwrightTestClassMethod
		).getTestName();

		PlaywrightTestClassMethodTestrayCaseResultDescriptor
			playwrightTestClassMethodTestrayCaseResultDescriptor =
				new PlaywrightTestClassMethodTestrayCaseResultDescriptor(
					axisTestClassGroup, null, playwrightTestClassMethod);

		testEquals(
			expectedName,
			playwrightTestClassMethodTestrayCaseResultDescriptor.getName());
	}

}