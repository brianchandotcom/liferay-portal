/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.TestClassReport;
import com.liferay.jenkins.results.parser.TestReport;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightTestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.PlaywrightSegmentTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Calum Ragan
 */
public class PlaywrightBatchBuildTestrayCaseResultTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testFindTestReport() {
		String projectDirName = RandomTestUtil.randomString();
		String testName = RandomTestUtil.randomString();

		String specFilePath = JenkinsResultsParserUtil.combine(
			projectDirName, "/", RandomTestUtil.randomString(), "/",
			RandomTestUtil.randomString());

		PlaywrightBatchBuildTestrayCaseResult
			playwrightBatchBuildTestrayCaseResult =
				_mockPlaywrightBatchBuildTestrayCaseResult(
					_mockPlaywrightSegmentTestClassGroup(
						JenkinsResultsParserUtil.combine(
							projectDirName, ".",
							RandomTestUtil.randomString())),
					specFilePath, testName);

		Mockito.doCallRealMethod(
		).when(
			playwrightBatchBuildTestrayCaseResult
		).findTestReport();

		TestReport testReport = _mockTestReport(specFilePath, testName);

		TestClassReport testClassReport = Mockito.mock(TestClassReport.class);

		Mockito.doReturn(
			specFilePath
		).when(
			testClassReport
		).getTestClassName();

		Mockito.doReturn(
			Arrays.asList(
				_mockTestReport(specFilePath, RandomTestUtil.randomString()),
				testReport)
		).when(
			testClassReport
		).getTestReports();

		DownstreamBuildReport downstreamBuildReport = Mockito.mock(
			DownstreamBuildReport.class);

		Mockito.doReturn(
			Collections.singletonList(testClassReport)
		).when(
			downstreamBuildReport
		).getTestClassReports();

		Mockito.doReturn(
			downstreamBuildReport
		).when(
			playwrightBatchBuildTestrayCaseResult
		).getDownstreamBuildReport();

		Assert.assertSame(
			testReport, playwrightBatchBuildTestrayCaseResult.findTestReport());
	}

	@Test
	public void testGetName() {
		String otherDirName = RandomTestUtil.randomString();
		String projectDirName = RandomTestUtil.randomString();
		String projectSubdirName = RandomTestUtil.randomString();
		String sharedDirName = RandomTestUtil.randomString();
		String specFileName = RandomTestUtil.randomString();
		String specSubdirName = RandomTestUtil.randomString();
		String testName = RandomTestUtil.randomString();

		String projectLeafName = JenkinsResultsParserUtil.combine(
			sharedDirName, "-", RandomTestUtil.randomString());

		PlaywrightSegmentTestClassGroup playwrightSegmentTestClassGroup =
			_mockPlaywrightSegmentTestClassGroup(
				JenkinsResultsParserUtil.combine(
					projectDirName, ".", projectLeafName));

		_testGetName(
			JenkinsResultsParserUtil.combine("null > ", testName),
			playwrightSegmentTestClassGroup, null, testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				otherDirName, "/", projectLeafName, "/", specFileName, " > ",
				testName),
			playwrightSegmentTestClassGroup,
			JenkinsResultsParserUtil.combine(
				otherDirName, "/", sharedDirName, "/", specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectLeafName, "/", specFileName, " > ",
				testName),
			playwrightSegmentTestClassGroup,
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectLeafName, "/", specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectLeafName, "/", specFileName, " > ",
				testName),
			playwrightSegmentTestClassGroup,
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", sharedDirName, "/", specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectLeafName, "/", specFileName, " > ",
				testName),
			playwrightSegmentTestClassGroup,
			JenkinsResultsParserUtil.combine(projectDirName, "/", specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectLeafName, "/", specSubdirName, "/",
				specFileName, " > ", testName),
			playwrightSegmentTestClassGroup,
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectLeafName, "/", specSubdirName, "/",
				specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectLeafName, "/", specSubdirName, "/",
				specFileName, " > ", testName),
			playwrightSegmentTestClassGroup,
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", sharedDirName, "/", specSubdirName, "/",
				specFileName),
			testName);

		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectSubdirName, "/", projectLeafName,
				"/", specFileName, " > ", testName),
			_mockPlaywrightSegmentTestClassGroup(
				JenkinsResultsParserUtil.combine(
					projectDirName, ".", projectSubdirName, ".",
					projectLeafName)),
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", projectSubdirName, "/", sharedDirName, "/",
				specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", sharedDirName, "/", specFileName, " > ",
				testName),
			Mockito.mock(SegmentTestClassGroup.class),
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", sharedDirName, "/", specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", sharedDirName, "/", specFileName, " > ",
				testName),
			_mockPlaywrightSegmentTestClassGroup(null),
			JenkinsResultsParserUtil.combine(
				projectDirName, "/", sharedDirName, "/", specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(
				projectLeafName, "/", specFileName, " > ", testName),
			_mockPlaywrightSegmentTestClassGroup(projectLeafName),
			JenkinsResultsParserUtil.combine(sharedDirName, "/", specFileName),
			testName);
		_testGetName(
			JenkinsResultsParserUtil.combine(specFileName, " > ", testName),
			playwrightSegmentTestClassGroup, specFileName, testName);
	}

	private PlaywrightBatchBuildTestrayCaseResult
		_mockPlaywrightBatchBuildTestrayCaseResult(
			SegmentTestClassGroup segmentTestClassGroup, String specFilePath,
			String testName) {

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
			JenkinsResultsParserUtil.combine(specFilePath, " > ", testName)
		).when(
			playwrightTestClassMethod
		).getName();

		Mockito.doReturn(
			testName
		).when(
			playwrightTestClassMethod
		).getTestName();

		PlaywrightBatchBuildTestrayCaseResult
			playwrightBatchBuildTestrayCaseResult = Mockito.mock(
				PlaywrightBatchBuildTestrayCaseResult.class);

		Mockito.doReturn(
			axisTestClassGroup
		).when(
			playwrightBatchBuildTestrayCaseResult
		).getAxisTestClassGroup();

		Mockito.doCallRealMethod(
		).when(
			playwrightBatchBuildTestrayCaseResult
		).getName();

		Mockito.doReturn(
			playwrightJUnitTestClass
		).when(
			playwrightBatchBuildTestrayCaseResult
		).getTestClass();

		Mockito.doReturn(
			playwrightTestClassMethod
		).when(
			playwrightBatchBuildTestrayCaseResult
		).getTestClassMethod();

		return playwrightBatchBuildTestrayCaseResult;
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

	private TestReport _mockTestReport(String testClassName, String testName) {
		TestReport testReport = Mockito.mock(TestReport.class);

		Mockito.doReturn(
			testClassName
		).when(
			testReport
		).getTestClassName();

		Mockito.doReturn(
			testName
		).when(
			testReport
		).getTestName();

		return testReport;
	}

	private void _testGetName(
		String expectedName, SegmentTestClassGroup segmentTestClassGroup,
		String specFilePath, String testName) {

		PlaywrightBatchBuildTestrayCaseResult
			playwrightBatchBuildTestrayCaseResult =
				_mockPlaywrightBatchBuildTestrayCaseResult(
					segmentTestClassGroup, specFilePath, testName);

		Assert.assertEquals(
			expectedName, playwrightBatchBuildTestrayCaseResult.getName());
	}

}