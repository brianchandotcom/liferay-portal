/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.FunctionalTestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class FunctionalTestClassTestrayCaseResultDescriptor
	extends BaseTestClassTestrayCaseResultDescriptor<FunctionalTestClass> {

	public FunctionalTestClassTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		FunctionalTestClass functionalTestClass,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		super(
			axisTestClassGroup, parentTestrayCaseResultDescriptor,
			functionalTestClass);
	}

	@Override
	public String getComponentName() {
		return _getPoshiProperty("testray.main.component.name");
	}

	@Override
	public String getName() {
		FunctionalTestClass functionalTestClass = getTestClass();

		return functionalTestClass.getTestClassMethodName();
	}

	@Override
	public int getPriority() {
		return getPriority(_getPoshiProperty("priority"));
	}

	@Override
	public String getSubcomponentNames() {
		return _getPoshiProperty("testray.component.names");
	}

	private String _getPoshiProperty(String propertyName) {
		FunctionalTestClass functionalTestClass = getTestClass();

		return JenkinsResultsParserUtil.getProperty(
			functionalTestClass.getPoshiProperties(), propertyName);
	}

}