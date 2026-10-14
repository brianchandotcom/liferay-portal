/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestClassMethodTestrayCaseResultDescriptor
	<T extends TestClass, M extends TestClassMethod>
		extends BaseTestrayCaseResultDescriptor {

	@Override
	public AxisTestClassGroup getAxisTestClassGroup() {
		return _axisTestClassGroup;
	}

	@Override
	public T getTestClass() {
		return (T)_testClassMethod.getTestClass();
	}

	public M getTestClassMethod() {
		return _testClassMethod;
	}

	protected BaseTestClassMethodTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor,
		M testClassMethod) {

		super(axisTestClassGroup.getJob(), parentTestrayCaseResultDescriptor);

		_axisTestClassGroup = axisTestClassGroup;
		_testClassMethod = testClassMethod;
	}

	private final AxisTestClassGroup _axisTestClassGroup;
	private final M _testClassMethod;

}