/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.task.TestTask;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

				List<TestClass> testClasses = testTask.getTestClasses();

				for (TestClass testClass : testClasses) {
					Matcher matcher = _pattern.matcher(
						String.valueOf(testClass.getTestClassFile()));

					if (!matcher.find()) {
						continue;
					}

					JUnitTestClass jUnitTestClass = (JUnitTestClass)testClass;

					String testClassFileName = matcher.group(
						"testClassFileName");

					testClassFileName = testClassFileName.replace(
						".java", ".class");

					List<String> testClassMethodNames =
						jUnitTestClass.getTestClassMethodNames();

					if ((testClassMethodNames != null) &&
						!testClassMethodNames.isEmpty()) {

						for (String testClassMethodName :
								testClassMethodNames) {

							sb.append(testClassFileName);
							sb.append("#");
							sb.append(testClassMethodName);
							sb.append(",");
						}
					}
					else {
						sb.append(testClassFileName);

						sb.append(",");
					}
				}

				if (!testClasses.isEmpty()) {
					sb.setLength(sb.length() - 1);
				}

				sb.append("];");
			}

			if (!testTasks.isEmpty()) {
				sb.setLength(sb.length() - 1);
			}
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

	private static final Pattern _pattern = Pattern.compile(
		".*/(?<testClassFileName>com/.*)");

}