/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.QAWebsitesGitRepositoryJob;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.ReflectionTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
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
public class BaseTestrayCaseResultDescriptorTest
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
	public void testGetBatchName() {
		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		String batchName = RandomTestUtil.randomString();

		Mockito.doReturn(
			batchName
		).when(
			axisTestClassGroup
		).getBatchName();

		_testGetBatchName(axisTestClassGroup, batchName);

		_testGetBatchName(null, null);
	}

	@Test
	public void testGetComponentName() {
		String buildComponentName = RandomTestUtil.randomString();

		_testGetComponentName(
			false, buildComponentName, buildComponentName, null, null);
		_testGetComponentName(
			true, buildComponentName, buildComponentName, "", null);

		String testrayMainComponentName = RandomTestUtil.randomString();
		String jobComponentName = RandomTestUtil.randomString();

		_testGetComponentName(
			true, buildComponentName, jobComponentName, jobComponentName, "");
		_testGetComponentName(
			true, buildComponentName, testrayMainComponentName,
			jobComponentName, testrayMainComponentName);

		_testGetComponentName(true, null, null, null, null);
	}

	@Test
	public void testGetComponentNameQAWebsites() throws Exception {
		String batchName = RandomTestUtil.randomString();
		String componentName = RandomTestUtil.randomString();

		Path testBaseDirPath = Files.createTempDirectory("testray-descriptor-");

		File testBaseDir = testBaseDirPath.toFile();

		JenkinsResultsParserUtil.write(
			new File(testBaseDir, "test.properties"),
			"testray.component.name[" + batchName + "]=" + componentName);

		QAWebsitesGitRepositoryJob qaWebsitesGitRepositoryJob = Mockito.mock(
			QAWebsitesGitRepositoryJob.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			qaWebsitesGitRepositoryJob
		).getJobName();

		Mockito.doReturn(
			new ArrayList<>()
		).when(
			qaWebsitesGitRepositoryJob
		).getJobPropertyOptions();

		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		Mockito.doReturn(
			qaWebsitesGitRepositoryJob
		).when(
			axisTestClassGroup
		).getJob();

		Mockito.doReturn(
			testBaseDir
		).when(
			axisTestClassGroup
		).getTestBaseDir();

		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			_mockBaseTestrayCaseResultDescriptor(batchName);

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getComponentName();

		Mockito.doReturn(
			axisTestClassGroup
		).when(
			baseTestrayCaseResultDescriptor
		).getAxisTestClassGroup();

		testEquals(
			componentName, baseTestrayCaseResultDescriptor.getComponentName());
	}

	@Test
	public void testGetPriority() {
		_testGetPriority(1, "1");
		_testGetPriority(5, "");
		_testGetPriority(5, "-1");
		_testGetPriority(5, "1.5");
		_testGetPriority(5, "99999999999");
		_testGetPriority(5, "high");
		_testGetPriority(5, null);
		_testGetPriority(7, "007");
	}

	@Test
	public void testGetSubcomponentNames() {
		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			Mockito.mock(BaseTestrayCaseResultDescriptor.class);

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getSubcomponentNames();

		testEquals("", baseTestrayCaseResultDescriptor.getSubcomponentNames());
	}

	@Test
	public void testGetTeamName() {
		Properties buildProperties = new Properties();

		String batchName = RandomTestUtil.randomString();
		String buildTeamName = RandomTestUtil.randomString();

		buildProperties.setProperty(
			"testray.case.team[" + batchName + "]", buildTeamName);

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Properties matchingJobProperties = new Properties();

		String firstTeamName = RandomTestUtil.randomString();

		matchingJobProperties.setProperty(
			"testray.team." + firstTeamName + ".component.names[" + batchName +
				"]",
			RandomTestUtil.randomString());

		String componentName = RandomTestUtil.randomString();

		matchingJobProperties.setProperty(
			"testray.team.beta-team-two.component.names[" + batchName + "]",
			RandomTestUtil.randomString() + "," + componentName);

		String lastTeamName = RandomTestUtil.randomString();

		matchingJobProperties.setProperty(
			"testray.team." + lastTeamName + ".component.names[" + batchName +
				"]",
			componentName);

		matchingJobProperties.setProperty(
			"testray.team.names[" + batchName + "]",
			JenkinsResultsParserUtil.join(
				",", firstTeamName, "beta-team-two", lastTeamName));

		Properties missingComponentsJobProperties = new Properties();

		missingComponentsJobProperties.setProperty(
			"testray.team.delta-team.component.names[" + batchName + "]",
			componentName);
		missingComponentsJobProperties.setProperty(
			"testray.team.names[" + batchName + "]",
			firstTeamName + ",delta-team");

		Properties unmatchedJobProperties = new Properties();

		unmatchedJobProperties.setProperty(
			"testray.team." + firstTeamName + ".component.names[" + batchName +
				"]",
			RandomTestUtil.randomString());
		unmatchedJobProperties.setProperty(
			"testray.team.names[" + batchName + "]", firstTeamName);

		_testGetTeamName(
			false, batchName, componentName, buildTeamName, new Properties());
		_testGetTeamName(
			true, batchName, componentName, "Beta Team Two",
			matchingJobProperties);
		_testGetTeamName(
			true, batchName, componentName, "Delta Team",
			missingComponentsJobProperties);
		_testGetTeamName(
			true, batchName, componentName, buildTeamName, new Properties());
		_testGetTeamName(
			true, batchName, componentName, buildTeamName,
			unmatchedJobProperties);
	}

	@Test
	public void testGetTestBaseDir() {
		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		File testBaseDir = new File(RandomTestUtil.randomString());

		Mockito.doReturn(
			testBaseDir
		).when(
			axisTestClassGroup
		).getTestBaseDir();

		_testGetTestBaseDir(axisTestClassGroup, testBaseDir);

		_testGetTestBaseDir(null, null);
	}

	@Test
	public void testGetTestSuiteName() {
		Job job = Mockito.mock(Job.class);

		String testSuiteName = RandomTestUtil.randomString();

		Mockito.doReturn(
			testSuiteName
		).when(
			job
		).getTestSuiteName();

		_testGetTestSuiteName(null, null);
		_testGetTestSuiteName(testSuiteName, job);
	}

	@Test
	public void testGetType() {
		_testGetType(null, null);

		String type = RandomTestUtil.randomString();

		_testGetType(type, type);
	}

	private BaseTestrayCaseResultDescriptor
		_mockBaseTestrayCaseResultDescriptor(String batchName) {

		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			Mockito.mock(BaseTestrayCaseResultDescriptor.class);

		Mockito.doReturn(
			batchName
		).when(
			baseTestrayCaseResultDescriptor
		).getBatchName();

		return baseTestrayCaseResultDescriptor;
	}

	private void _testGetBatchName(
		AxisTestClassGroup axisTestClassGroup, String expectedBatchName) {

		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			Mockito.mock(BaseTestrayCaseResultDescriptor.class);

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getBatchName();

		Mockito.doReturn(
			axisTestClassGroup
		).when(
			baseTestrayCaseResultDescriptor
		).getAxisTestClassGroup();

		testEquals(
			expectedBatchName, baseTestrayCaseResultDescriptor.getBatchName());
	}

	private void _testGetComponentName(
		boolean axis, String buildComponentName, String expectedComponentName,
		String jobComponentName, String testrayMainComponentName) {

		Properties buildProperties = new Properties();

		String batchName = RandomTestUtil.randomString();

		if (buildComponentName != null) {
			buildProperties.setProperty(
				"testray.case.component[" + batchName + "]",
				buildComponentName);
		}

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			_mockBaseTestrayCaseResultDescriptor(batchName);

		if (axis) {
			AxisTestClassGroup axisTestClassGroup = Mockito.mock(
				AxisTestClassGroup.class);

			Properties jobProperties = new Properties();

			if (jobComponentName != null) {
				jobProperties.setProperty(
					"testray.component.name[" + batchName + "]",
					jobComponentName);
			}

			Mockito.doReturn(
				TestrayCaseResultDescriptorTestUtil.newJob(jobProperties)
			).when(
				axisTestClassGroup
			).getJob();

			Mockito.doReturn(
				axisTestClassGroup
			).when(
				baseTestrayCaseResultDescriptor
			).getAxisTestClassGroup();
		}

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getComponentName();

		if (testrayMainComponentName != null) {
			TestClass testClass = Mockito.mock(TestClass.class);

			Mockito.doReturn(
				testrayMainComponentName
			).when(
				testClass
			).getTestrayMainComponentName();

			Mockito.doReturn(
				testClass
			).when(
				baseTestrayCaseResultDescriptor
			).getTestClass();
		}

		testEquals(
			expectedComponentName,
			baseTestrayCaseResultDescriptor.getComponentName());
	}

	private void _testGetPriority(int expectedPriority, String priority) {
		Properties buildProperties = new Properties();

		String batchName = RandomTestUtil.randomString();

		if (priority != null) {
			buildProperties.setProperty(
				"testray.case.priority[" + batchName + "]", priority);
		}

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			_mockBaseTestrayCaseResultDescriptor(batchName);

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getPriority();

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getPriority(
			Mockito.any()
		);

		testEquals(
			expectedPriority, baseTestrayCaseResultDescriptor.getPriority());
	}

	private void _testGetTeamName(
		boolean axis, String batchName, String componentName,
		String expectedTeamName, Properties jobProperties) {

		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			_mockBaseTestrayCaseResultDescriptor(batchName);

		if (axis) {
			AxisTestClassGroup axisTestClassGroup = Mockito.mock(
				AxisTestClassGroup.class);

			Mockito.doReturn(
				TestrayCaseResultDescriptorTestUtil.newJob(jobProperties)
			).when(
				axisTestClassGroup
			).getJob();

			Mockito.doReturn(
				axisTestClassGroup
			).when(
				baseTestrayCaseResultDescriptor
			).getAxisTestClassGroup();
		}

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getTeamName();

		Mockito.doReturn(
			componentName
		).when(
			baseTestrayCaseResultDescriptor
		).getComponentName();

		testEquals(
			expectedTeamName, baseTestrayCaseResultDescriptor.getTeamName());
	}

	private void _testGetTestBaseDir(
		AxisTestClassGroup axisTestClassGroup, File expectedTestBaseDir) {

		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			Mockito.mock(BaseTestrayCaseResultDescriptor.class);

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getTestBaseDir();

		Mockito.doReturn(
			axisTestClassGroup
		).when(
			baseTestrayCaseResultDescriptor
		).getAxisTestClassGroup();

		testEquals(
			expectedTestBaseDir,
			baseTestrayCaseResultDescriptor.getTestBaseDir());
	}

	private void _testGetTestSuiteName(String expectedTestSuiteName, Job job) {
		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			Mockito.mock(BaseTestrayCaseResultDescriptor.class);

		ReflectionTestUtil.setFieldValue(
			baseTestrayCaseResultDescriptor, "_job", job);

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getTestSuiteName();

		testEquals(
			expectedTestSuiteName,
			baseTestrayCaseResultDescriptor.getTestSuiteName());
	}

	private void _testGetType(String expectedType, String type) {
		Properties buildProperties = new Properties();

		String batchName = RandomTestUtil.randomString();

		if (type != null) {
			buildProperties.setProperty(
				"testray.case.type[" + batchName + "]", type);
		}

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		BaseTestrayCaseResultDescriptor baseTestrayCaseResultDescriptor =
			_mockBaseTestrayCaseResultDescriptor(batchName);

		Mockito.doCallRealMethod(
		).when(
			baseTestrayCaseResultDescriptor
		).getType();

		testEquals(expectedType, baseTestrayCaseResultDescriptor.getType());
	}

}