/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JSUnitModulesSegmentTestClassGroup
	extends ModulesSegmentTestClassGroup {

	protected JSUnitModulesSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		super(batchTestClassGroup);
	}

	protected JSUnitModulesSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

	@Override
	protected String getTestTaskNames(int axisIndex) {
		AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup(
			axisIndex);

		StringBuilder sb = new StringBuilder();

		Set<String> testTaskNames = new TreeSet<>();

		for (TestClass testClass : axisTestClassGroup.getTestClasses()) {
			testTaskNames.add(testClass.getTestTaskName());
		}

		for (String testTaskName : testTaskNames) {
			sb.append(testTaskName);
			sb.append(",");
		}

		if (!testTaskNames.isEmpty()) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

}