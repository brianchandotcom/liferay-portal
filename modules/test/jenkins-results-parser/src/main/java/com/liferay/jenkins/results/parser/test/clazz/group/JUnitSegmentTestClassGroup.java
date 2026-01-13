/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JUnitSegmentTestClassGroup extends SegmentTestClassGroup {

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());

		List<String> axisIndexes = new ArrayList<>();

		for (int axisIndex = 0; axisIndex < getAxisCount(); axisIndex++) {
			axisIndexes.add(String.valueOf(axisIndex));

			sb.append("TEST_CLASS_GROUP_");
			sb.append(axisIndex);
			sb.append("=");

			AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup(
				axisIndex);

			List<String> testClassFileMethodNames = new ArrayList<>();

			for (TestClass testClass : axisTestClassGroup.getTestClasses()) {
				if (!(testClass instanceof JUnitTestClass)) {
					continue;
				}

				JUnitTestClass jUnitTestClass = (JUnitTestClass)testClass;

				testClassFileMethodNames.addAll(
					jUnitTestClass.getTestClassFileMethodNames());
			}

			sb.append(
				JenkinsResultsParserUtil.join(",", testClassFileMethodNames));

			sb.append("\n");
		}

		sb.append("TEST_CLASS_GROUPS=");
		sb.append(JenkinsResultsParserUtil.join(" ", axisIndexes));
		sb.append("\n");

		return sb.toString();
	}

	protected JUnitSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		super(batchTestClassGroup);
	}

	protected JUnitSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

}