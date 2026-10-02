/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Brittney Nguyen
 */
public class ControllerPortalTopLevelBuildDataTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetTestrayBuildName() {
		_testGetTestrayBuildName(
			"[master] ci:test:upstream-dxp", RandomTestUtil.randomString(),
			null);

		String testrayRoutineName = RandomTestUtil.randomString();

		_testGetTestrayBuildName(null, null, testrayRoutineName);
		_testGetTestrayBuildName(
			testrayRoutineName, RandomTestUtil.randomString(),
			testrayRoutineName);
	}

	@Test
	public void testGetTestrayRoutineName() {
		String testrayProjectName = RandomTestUtil.randomString();

		_testGetTestrayRoutineName(
			"[master] ci:test:upstream-dxp", " ", testrayProjectName, " ");
		_testGetTestrayRoutineName(
			"[master] ci:test:upstream-dxp", "", testrayProjectName, "");
		_testGetTestrayRoutineName(
			"[master] ci:test:upstream-dxp", null, testrayProjectName, null);

		String testrayBuildType = RandomTestUtil.randomString();
		String testrayRoutineName = RandomTestUtil.randomString();

		_testGetTestrayRoutineName(
			null, testrayBuildType, " ", testrayRoutineName);
		_testGetTestrayRoutineName(
			null, testrayBuildType, "", testrayRoutineName);
		_testGetTestrayRoutineName(
			null, testrayBuildType, null, testrayRoutineName);

		_testGetTestrayRoutineName(
			testrayBuildType, testrayBuildType, testrayProjectName, " ");
		_testGetTestrayRoutineName(
			testrayBuildType, testrayBuildType, testrayProjectName, "");
		_testGetTestrayRoutineName(
			testrayBuildType, testrayBuildType, testrayProjectName, null);
		_testGetTestrayRoutineName(
			testrayRoutineName, testrayBuildType, testrayProjectName,
			testrayRoutineName);
	}

	private ControllerPortalTopLevelBuildData
		_getControllerPortalTopLevelBuildData() {

		ControllerPortalTopLevelBuildData controllerPortalTopLevelBuildData =
			Mockito.mock(ControllerPortalTopLevelBuildData.class);

		Mockito.doReturn(
			"master"
		).when(
			controllerPortalTopLevelBuildData
		).getPortalUpstreamBranchName();

		Mockito.doReturn(
			"upstream-dxp"
		).when(
			controllerPortalTopLevelBuildData
		).getTestSuiteName();

		Mockito.doCallRealMethod(
		).when(
			controllerPortalTopLevelBuildData
		).getTestrayProjectName();

		Mockito.doCallRealMethod(
		).when(
			controllerPortalTopLevelBuildData
		).getTestrayRoutineName();

		return controllerPortalTopLevelBuildData;
	}

	private void _testGetTestrayBuildName(
		String expectedTestrayRoutineName, String testrayProjectName,
		String testrayRoutineName) {

		Map<String, String> environmentMap = new HashMap<>();

		if (testrayProjectName != null) {
			environmentMap.put("TESTRAY_PROJECT_NAME", testrayProjectName);
		}

		if (testrayRoutineName != null) {
			environmentMap.put("TESTRAY_ROUTINE_NAME", testrayRoutineName);
		}

		mockEnvironment(environmentMap);

		ControllerPortalTopLevelBuildData controllerPortalTopLevelBuildData =
			_getControllerPortalTopLevelBuildData();

		Integer buildNumber = RandomTestUtil.randomInt();

		Mockito.doReturn(
			buildNumber
		).when(
			controllerPortalTopLevelBuildData
		).getBuildNumber();

		Mockito.doReturn(
			1790823600000L
		).when(
			controllerPortalTopLevelBuildData
		).getStartTime();

		Mockito.doCallRealMethod(
		).when(
			controllerPortalTopLevelBuildData
		).getTestrayBuildName();

		String expectedTestrayBuildName = null;

		if (expectedTestrayRoutineName != null) {
			expectedTestrayBuildName = JenkinsResultsParserUtil.combine(
				expectedTestrayRoutineName, " - ", String.valueOf(buildNumber),
				" - 2026-09-30[20:00:00]");
		}

		Assert.assertEquals(
			expectedTestrayBuildName,
			controllerPortalTopLevelBuildData.getTestrayBuildName());
	}

	private void _testGetTestrayRoutineName(
		String expectedTestrayRoutineName, String testrayBuildType,
		String testrayProjectName, String testrayRoutineName) {

		Map<String, String> environmentMap = new HashMap<>();

		if (testrayBuildType != null) {
			environmentMap.put("TESTRAY_BUILD_TYPE", testrayBuildType);
		}

		if (testrayProjectName != null) {
			environmentMap.put("TESTRAY_PROJECT_NAME", testrayProjectName);
		}

		if (testrayRoutineName != null) {
			environmentMap.put("TESTRAY_ROUTINE_NAME", testrayRoutineName);
		}

		mockEnvironment(environmentMap);

		ControllerPortalTopLevelBuildData controllerPortalTopLevelBuildData =
			_getControllerPortalTopLevelBuildData();

		Assert.assertEquals(
			expectedTestrayRoutineName,
			controllerPortalTopLevelBuildData.getTestrayRoutineName());
	}

}