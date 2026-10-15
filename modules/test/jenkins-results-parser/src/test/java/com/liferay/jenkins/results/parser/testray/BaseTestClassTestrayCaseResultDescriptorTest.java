/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class BaseTestClassTestrayCaseResultDescriptorTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetAxisTestClassGroup() {
		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		BaseTestClassTestrayCaseResultDescriptor<TestClass>
			baseTestClassTestrayCaseResultDescriptor =
				new BaseTestClassTestrayCaseResultDescriptor<TestClass>(
					axisTestClassGroup, null, Mockito.mock(TestClass.class)) {
				};

		testSame(
			axisTestClassGroup,
			baseTestClassTestrayCaseResultDescriptor.getAxisTestClassGroup());
	}

	@Test
	public void testGetName() {
		String testClassName = RandomTestUtil.randomString();

		TestClass testClass = Mockito.mock(TestClass.class);

		Mockito.doReturn(
			testClassName
		).when(
			testClass
		).getName();

		String axisName = RandomTestUtil.randomString();
		String batchName = RandomTestUtil.randomString();

		_testGetName(axisName, batchName, axisName, null);
		_testGetName(
			axisName, batchName, batchName + "[" + testClassName + "]",
			testClass);
	}

	private void _testGetName(
		String axisName, String batchName, String expectedName,
		TestClass testClass) {

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

		BaseTestClassTestrayCaseResultDescriptor<TestClass>
			baseTestClassTestrayCaseResultDescriptor =
				new BaseTestClassTestrayCaseResultDescriptor<TestClass>(
					axisTestClassGroup, null, testClass) {
				};

		testEquals(
			expectedName, baseTestClassTestrayCaseResultDescriptor.getName());
	}

}