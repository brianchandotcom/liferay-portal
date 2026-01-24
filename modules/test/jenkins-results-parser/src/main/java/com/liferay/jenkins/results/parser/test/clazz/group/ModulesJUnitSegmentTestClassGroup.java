/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.task.TestTask;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ModulesJUnitSegmentTestClassGroup
	extends JUnitSegmentTestClassGroup {

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());

		for (int axisIndex = 0; axisIndex < getAxisCount(); axisIndex++) {
			AxisTestClassGroup axisTestClassGroup = getAxisTestClassGroup(
				axisIndex);

			sb.append("TEST_TASK_GROUP_");
			sb.append(axisIndex);
			sb.append("=");

			if (!(axisTestClassGroup instanceof
					ModulesJUnitAxisTestClassGroup)) {

				continue;
			}

			ModulesJUnitAxisTestClassGroup modulesJUnitAxisTestClassGroup =
				(ModulesJUnitAxisTestClassGroup)axisTestClassGroup;

			List<TestTask> testTasks =
				modulesJUnitAxisTestClassGroup.getTestTasks();

			for (TestTask testTask : testTasks) {
				sb.append(testTask.getName());

				sb.append("[");

				List<String> testClassFileMethodNames = new ArrayList<>();

				List<TestClass> testClasses = new ArrayList<>();

				if (!testTask.isSplit()) {
					testClasses.addAll(testTask.getTestClasses());
				}
				else {
					testClasses.addAll(
						modulesJUnitAxisTestClassGroup.getTestClasses());
				}

				for (TestClass testClass : testClasses) {
					if (!(testClass instanceof JUnitTestClass)) {
						continue;
					}

					JUnitTestClass jUnitTestClass = (JUnitTestClass)testClass;

					testClassFileMethodNames.addAll(
						jUnitTestClass.getTestClassFileMethodNames());
				}

				sb.append(
					JenkinsResultsParserUtil.join(
						",", testClassFileMethodNames));

				sb.append("];");
			}

			if (!testTasks.isEmpty()) {
				sb.setLength(sb.length() - 1);
			}

			sb.append("\n");
		}

		return sb.toString();
	}

	protected ModulesJUnitSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		super(batchTestClassGroup);
	}

	protected ModulesJUnitSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

}