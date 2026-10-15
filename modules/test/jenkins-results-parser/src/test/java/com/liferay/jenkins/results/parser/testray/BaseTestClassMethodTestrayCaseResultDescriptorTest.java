/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class BaseTestClassMethodTestrayCaseResultDescriptorTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetTestClass() {
		TestClass testClass = Mockito.mock(TestClass.class);

		TestClassMethod testClassMethod = Mockito.mock(TestClassMethod.class);

		Mockito.doReturn(
			testClass
		).when(
			testClassMethod
		).getTestClass();

		BaseTestClassMethodTestrayCaseResultDescriptor
			<TestClass, TestClassMethod>
				baseTestClassMethodTestrayCaseResultDescriptor =
					new BaseTestClassMethodTestrayCaseResultDescriptor
						<TestClass, TestClassMethod>(
							Mockito.mock(AxisTestClassGroup.class), null,
							testClassMethod) {

						@Override
						public String getName() {
							return null;
						}

					};

		testSame(
			testClass,
			baseTestClassMethodTestrayCaseResultDescriptor.getTestClass());
	}

}