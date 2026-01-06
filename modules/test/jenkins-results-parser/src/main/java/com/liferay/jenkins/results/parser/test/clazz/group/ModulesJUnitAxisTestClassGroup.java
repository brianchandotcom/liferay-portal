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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ModulesJUnitAxisTestClassGroup extends JUnitAxisTestClassGroup {

	@Override
	public long getAverageDuration() {
		if (_averageDuration != null) {
			return _averageDuration;
		}

		GroupingStrategy groupingStrategy = getGroupingStrategy();

		if (groupingStrategy == GroupingStrategy.DEFAULT) {
			_averageDuration = super.getAverageDuration();

			return _averageDuration;
		}

		_averageDuration =
			getAverageOverheadDuration() + getAverageTotalTestTaskDuration();

		return _averageDuration;
	}

	public long getAverageTotalTestTaskDuration() {
		if (_averageTotalTestTaskDuration != null) {
			return _averageTotalTestTaskDuration;
		}

		_averageTotalTestTaskDuration = 0L;

		GroupingStrategy groupingStrategy = getGroupingStrategy();

		for (TestTask testTask : getTestTasks()) {
			if (groupingStrategy ==
					GroupingStrategy.TEST_TASK_AVERAGE_DURATION) {

				_averageTotalTestTaskDuration += testTask.getAverageDuration();
			}
			else if (groupingStrategy ==
						GroupingStrategy.TEST_TASK_AVERAGE_TOTAL_DURATION) {

				_averageTotalTestTaskDuration +=
					testTask.getAverageTotalDuration();
			}
			else if (groupingStrategy ==
						GroupingStrategy.TEST_TASK_LONGEST_DURATION) {

				_averageTotalTestTaskDuration += testTask.getLongestDuration();
			}
		}

		return _averageTotalTestTaskDuration;
	}

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
		if (!_testTasks.isEmpty()) {
			return new ArrayList<>(_testTasks.values());
		}

		TestClassGroup.GroupingStrategy groupingStrategy =
			getGroupingStrategy();

		for (ModulesJUnitTestClass modulesJUnitTestClass :
				_getModulesJUnitTestClasses()) {

			String testTaskName = modulesJUnitTestClass.getTestTaskName();

			TestTask testTask = _testTasks.get(testTaskName);

			if (testTask == null) {
				testTask = TestTaskFactory.newTestTask(
					modulesJUnitTestClass.getAverageTestTaskDuration(),
					modulesJUnitTestClass.getAverageTotalTestTaskDuration(),
					groupingStrategy,
					modulesJUnitTestClass.isLatestReportMissing(),
					modulesJUnitTestClass.getLongestTestTaskDuration(),
					testTaskName);

				_testTasks.put(testTaskName, testTask);
			}

			testTask.addTestClass(modulesJUnitTestClass);

			modulesJUnitTestClass.setTestTask(testTask);
		}

		return new ArrayList<>(_testTasks.values());
	}

	protected ModulesJUnitAxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		super(jsonObject, segmentTestClassGroup);

		JSONArray testTasksJSONArray = jsonObject.getJSONArray("test_tasks");

		if ((testTasksJSONArray == null) || testTasksJSONArray.isEmpty()) {
			return;
		}

		TestClassGroup.GroupingStrategy groupingStrategy =
			getGroupingStrategy();

		for (int i = 0; i < testTasksJSONArray.length(); i++) {
			JSONObject testTaskJSONObject = testTasksJSONArray.getJSONObject(i);

			JSONArray testClassesJSONArray = testTaskJSONObject.getJSONArray(
				"test_classes");

			if (testClassesJSONArray == null) {
				continue;
			}

			String testTaskName = testTaskJSONObject.getString("name");

			TestTask testTask = TestTaskFactory.newTestTask(
				testTaskJSONObject.getLong("average_duration"),
				testTaskJSONObject.getLong("average_total_duration"),
				groupingStrategy,
				testTaskJSONObject.getBoolean("latest_report_missing"),
				testTaskJSONObject.getLong("longest_duration"), testTaskName);

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

				if (!(testClass instanceof ModulesJUnitTestClass)) {
					continue;
				}

				ModulesJUnitTestClass modulesJUnitTestClass =
					(ModulesJUnitTestClass)testClass;

				modulesJUnitTestClass.setTestTask(testTask);
			}

			_testTasks.put(testTaskName, testTask);
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

	private Long _averageDuration;
	private Long _averageTotalTestTaskDuration;
	private final Map<String, TestTask> _testTasks = new HashMap<>();

}