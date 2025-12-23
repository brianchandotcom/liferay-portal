/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.test.clazz.ModulesJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.task.TestTask;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ModulesJUnitAxisTestClassGroup extends JUnitAxisTestClassGroup {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		JSONArray testTasksJSONArray = new JSONArray();

		jsonObject.put("test_tasks", testTasksJSONArray);

		for (TestTask testTask : getTestTasks()) {
			testTasksJSONArray.put(testTask.getJSONObject());
		}

		return jsonObject;
	}

	public List<TestTask> getTestTasks() {
		List<TestTask> testTasks = new ArrayList<>();

		for (ModulesJUnitTestClass modulesJUnitTestClass :
				_getModulesJUnitTestClasses()) {

			testTasks.add(modulesJUnitTestClass.getTestTask());
		}

		return testTasks;
	}

	protected ModulesJUnitAxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		super(jsonObject, segmentTestClassGroup);
	}

	protected ModulesJUnitAxisTestClassGroup(
		JUnitBatchTestClassGroup jUnitBatchTestClassGroup) {

		super(jUnitBatchTestClassGroup);
	}

	private List<ModulesJUnitTestClass> _getModulesJUnitTestClasses() {
		List<ModulesJUnitTestClass> modulesJUnitTestClasses = new ArrayList<>();

		for (TestClass testClass : getTestClasses()) {
			modulesJUnitTestClasses.add((ModulesJUnitTestClass)testClass);
		}

		return modulesJUnitTestClasses;
	}

}