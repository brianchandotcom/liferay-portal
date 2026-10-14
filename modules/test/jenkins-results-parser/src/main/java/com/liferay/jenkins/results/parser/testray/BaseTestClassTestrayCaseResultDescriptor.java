/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestClassTestrayCaseResultDescriptor
	<T extends TestClass>
		extends BaseTestrayCaseResultDescriptor {

	@Override
	public AxisTestClassGroup getAxisTestClassGroup() {
		return _axisTestClassGroup;
	}

	@Override
	public String getName() {
		if (_testClass == null) {
			return _axisTestClassGroup.getAxisName();
		}

		return JenkinsResultsParserUtil.combine(
			getBatchName(), "[", _testClass.getName(), "]");
	}

	@Override
	public T getTestClass() {
		return _testClass;
	}

	protected BaseTestClassTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor,
		T testClass) {

		super(axisTestClassGroup.getJob(), parentTestrayCaseResultDescriptor);

		_axisTestClassGroup = axisTestClassGroup;
		_testClass = testClass;
	}

	private final AxisTestClassGroup _axisTestClassGroup;
	private final T _testClass;

}