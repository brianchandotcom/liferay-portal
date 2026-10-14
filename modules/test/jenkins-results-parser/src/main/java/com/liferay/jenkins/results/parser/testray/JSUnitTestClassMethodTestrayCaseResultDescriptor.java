/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.test.clazz.JSUnitJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class JSUnitTestClassMethodTestrayCaseResultDescriptor
	extends BaseTestClassMethodTestrayCaseResultDescriptor
		<JSUnitJUnitTestClass, TestClassMethod> {

	public JSUnitTestClassMethodTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor,
		TestClassMethod testClassMethod) {

		super(
			axisTestClassGroup, parentTestrayCaseResultDescriptor,
			testClassMethod);
	}

	@Override
	public String getName() {
		TestClassMethod testClassMethod = getTestClassMethod();

		return testClassMethod.getName();
	}

}