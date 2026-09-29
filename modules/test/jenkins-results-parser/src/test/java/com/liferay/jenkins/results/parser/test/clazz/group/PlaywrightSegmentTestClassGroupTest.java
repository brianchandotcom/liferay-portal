/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Brittney Nguyen
 */
public class PlaywrightSegmentTestClassGroupTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetTestCasePropertiesContent() {
		String projectName = RandomTestUtil.randomString();

		String testCasePropertiesContent = _getTestCasePropertiesContent(
			projectName, null);

		Assert.assertFalse(
			testCasePropertiesContent.contains("PLAYWRIGHT_WORKSPACE_NAME"));
		Assert.assertTrue(
			testCasePropertiesContent.endsWith(
				"PLAYWRIGHT_PROJECT_NAME=" + projectName));

		String workspaceName = RandomTestUtil.randomString();

		testEquals(
			testCasePropertiesContent + "\nPLAYWRIGHT_WORKSPACE_NAME=" +
				workspaceName,
			_getTestCasePropertiesContent(projectName, workspaceName));

		testEquals(
			testCasePropertiesContent,
			_getTestCasePropertiesContent(projectName, ""));
	}

	@Test
	public void testGetWorkspaceName() {
		String workspaceName1 = RandomTestUtil.randomString();

		_testGetWorkspaceName(
			workspaceName1, Arrays.asList(null, "", workspaceName1));

		String workspaceName2 = RandomTestUtil.randomString();

		_testGetWorkspaceName(
			workspaceName1, Arrays.asList(workspaceName1, workspaceName2));

		_testGetWorkspaceName(null, Arrays.asList("", null));
		_testGetWorkspaceName(null, Collections.emptyList());
	}

	private String _getTestCasePropertiesContent(
		String projectName, String workspaceName) {

		PlaywrightBatchTestClassGroup playwrightBatchTestClassGroup =
			Mockito.mock(PlaywrightBatchTestClassGroup.class);

		Mockito.doReturn(
			Mockito.mock(JobProperty.class)
		).when(
			playwrightBatchTestClassGroup
		).getJobProperty(
			Mockito.anyString(), Mockito.nullable(String.class),
			Mockito.nullable(String.class)
		);

		PlaywrightSegmentTestClassGroup playwrightSegmentTestClassGroup =
			Mockito.mock(PlaywrightSegmentTestClassGroup.class);

		Mockito.doReturn(
			playwrightBatchTestClassGroup
		).when(
			playwrightSegmentTestClassGroup
		).getBatchTestClassGroup();

		Mockito.doReturn(
			Mockito.mock(Job.class)
		).when(
			playwrightSegmentTestClassGroup
		).getJob();

		Mockito.doReturn(
			projectName
		).when(
			playwrightSegmentTestClassGroup
		).getProjectName();

		if (workspaceName != null) {
			Mockito.doReturn(
				workspaceName
			).when(
				playwrightSegmentTestClassGroup
			).getWorkspaceName();
		}

		Mockito.doCallRealMethod(
		).when(
			playwrightSegmentTestClassGroup
		).getTestCasePropertiesContent();

		return playwrightSegmentTestClassGroup.getTestCasePropertiesContent();
	}

	private void _testGetWorkspaceName(
		String expectedWorkspaceName, List<String> workspaceNames) {

		List<AxisTestClassGroup> axisTestClassGroups = new ArrayList<>();

		for (String workspaceName : workspaceNames) {
			PlaywrightAxisTestClassGroup playwrightAxisTestClassGroup =
				Mockito.mock(PlaywrightAxisTestClassGroup.class);

			if (workspaceName != null) {
				Mockito.doReturn(
					workspaceName
				).when(
					playwrightAxisTestClassGroup
				).getWorkspaceName();
			}

			axisTestClassGroups.add(playwrightAxisTestClassGroup);
		}

		PlaywrightSegmentTestClassGroup playwrightSegmentTestClassGroup =
			Mockito.mock(PlaywrightSegmentTestClassGroup.class);

		Mockito.doReturn(
			axisTestClassGroups
		).when(
			playwrightSegmentTestClassGroup
		).getAxisTestClassGroups();

		Mockito.doCallRealMethod(
		).when(
			playwrightSegmentTestClassGroup
		).getWorkspaceName();

		testEquals(
			expectedWorkspaceName,
			playwrightSegmentTestClassGroup.getWorkspaceName());
	}

}