/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.PlaywrightJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Brittney Nguyen
 */
public class PlaywrightAxisTestClassGroupTest
	extends com.liferay.jenkins.results.parser.Test {

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

	private void _testGetWorkspaceName(
		String expectedWorkspaceName, List<String> workspaceNames) {

		List<TestClass> testClasses = new ArrayList<>();

		for (String workspaceName : workspaceNames) {
			PlaywrightJUnitTestClass playwrightJUnitTestClass = Mockito.mock(
				PlaywrightJUnitTestClass.class);

			if (workspaceName != null) {
				Mockito.doReturn(
					workspaceName
				).when(
					playwrightJUnitTestClass
				).getWorkspaceName();
			}

			testClasses.add(playwrightJUnitTestClass);
		}

		PlaywrightAxisTestClassGroup playwrightAxisTestClassGroup =
			Mockito.mock(PlaywrightAxisTestClassGroup.class);

		Mockito.doReturn(
			testClasses
		).when(
			playwrightAxisTestClassGroup
		).getTestClasses();

		Mockito.doCallRealMethod(
		).when(
			playwrightAxisTestClassGroup
		).getWorkspaceName();

		testEquals(
			expectedWorkspaceName,
			playwrightAxisTestClassGroup.getWorkspaceName());
	}

}