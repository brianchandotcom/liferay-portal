/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.test.clazz.ModulesJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;
import com.liferay.jenkins.results.parser.test.task.TestTask;
import com.liferay.jenkins.results.parser.test.task.TestTaskFactory;

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
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"average_duration", getAverageDuration()
		).put(
			"axis_name", getAxisName()
		);

		JSONArray testTasksJSONArray = new JSONArray();

		jsonObject.put("test_tasks", testTasksJSONArray);

		for (TestTask testTask : getTestTasks()) {
			testTasksJSONArray.put(testTask.getJSONObject());
		}

		return jsonObject;
	}

	public List<TestTask> getTestTasks() {
		if (_testTasks != null) {
			return _testTasks;
		}

		_testTasks = new ArrayList<>();

		for (ModulesJUnitTestClass modulesJUnitTestClass :
				_getModulesJUnitTestClasses()) {

			TestTask testTask = modulesJUnitTestClass.getTestTask();

			if (_testTasks.contains(testTask)) {
				continue;
			}

			_testTasks.add(testTask);
		}

		return _testTasks;
	}

	protected ModulesJUnitAxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		super(jsonObject, segmentTestClassGroup);

		JSONArray testTasksJSONArray = jsonObject.getJSONArray("test_tasks");

		if ((testTasksJSONArray == null) || testTasksJSONArray.isEmpty()) {
			return;
		}

		_testTasks = new ArrayList<>();

		for (int i = 0; i < testTasksJSONArray.length(); i++) {
			JSONObject testTaskJSONObject = testTasksJSONArray.getJSONObject(i);

			JSONArray testClassesJSONArray = testTaskJSONObject.getJSONArray(
				"test_classes");

			if (testClassesJSONArray == null) {
				continue;
			}

			TestTask testTask = TestTaskFactory.newTestTask(
				testTaskJSONObject.getString("name"),
				testTaskJSONObject.getLong("average_duration"));

			for (int j = 0; j < testClassesJSONArray.length(); j++) {
				JSONObject testClassJSONObject =
					testClassesJSONArray.getJSONObject(j);

				if (testClassJSONObject == null) {
					continue;
				}

				TestClass testClass = TestClassFactory.newTestClass(
					getBatchTestClassGroup(), testClassJSONObject);

				addTestClass(testClass);

				testTask.addTestClass(testClass);
			}

			if (_testTasks.contains(testTask)) {
				continue;
			}

			_testTasks.add(testTask);
		}
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

	private List<TestTask> _testTasks;

}