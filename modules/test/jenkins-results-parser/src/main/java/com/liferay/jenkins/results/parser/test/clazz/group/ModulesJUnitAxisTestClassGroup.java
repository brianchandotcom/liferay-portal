/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.test.clazz.ModulesJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.task.TestClassTask;

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

		JSONArray testClassTasksJSONArray = new JSONArray();

		jsonObject.put("test_class_tasks", testClassTasksJSONArray);

		for (TestClassTask testClassTask : getTestClassTasks()) {
			testClassTasksJSONArray.put(testClassTask.getJSONObject());
		}

		return jsonObject;
	}

	public List<TestClassTask> getTestClassTasks() {
		List<TestClassTask> testClassTasks = new ArrayList<>();

		for (ModulesJUnitTestClass modulesJUnitTestClass :
				_getModulesJUnitTestClasses()) {

			testClassTasks.add(modulesJUnitTestClass.getTestClassTask());
		}

		return testClassTasks;
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