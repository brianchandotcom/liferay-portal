/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.test.clazz.JSUnitJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class JSUnitTestClassTestrayCaseResultDescriptor
	extends BaseTestClassTestrayCaseResultDescriptor<JSUnitJUnitTestClass> {

	public JSUnitTestClassTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		JSUnitJUnitTestClass jsUnitJUnitTestClass,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		super(
			axisTestClassGroup, parentTestrayCaseResultDescriptor,
			jsUnitJUnitTestClass);
	}

	@Override
	public String getName() {
		JSUnitJUnitTestClass jsUnitJUnitTestClass = getTestClass();

		return jsUnitJUnitTestClass.getTestTaskName();
	}

}