/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.task;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestClassTask implements TestClassTask {

	@Override
	public void addTestClasses(TestClass testClass) {
		if (_testClasses.contains(testClass)) {
			return;
		}

		_testClasses.add(testClass);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestClass)) {
			return false;
		}

		return Objects.equals(hashCode(), object.hashCode());
	}

	@Override
	public long getAverageDuration() {
		return _averageDuration;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"average_duration", getAverageDuration()
		).put(
			"task_name", getTaskName()
		);

		JSONArray testClassesJSONArray = new JSONArray();

		for (TestClass testClass : getTestClasses()) {
			testClassesJSONArray.put(testClass.getJSONObject());
		}

		jsonObject.put("test_classes", testClassesJSONArray);

		return jsonObject;
	}

	@Override
	public String getTaskName() {
		return _taskName;
	}

	@Override
	public List<TestClass> getTestClasses() {
		return _testClasses;
	}

	@Override
	public int hashCode() {
		String testTaskName = getTaskName();

		return testTaskName.hashCode();
	}

	protected BaseTestClassTask(String taskName, long averageDuration) {
		_taskName = taskName;
		_averageDuration = averageDuration;
	}

	private final long _averageDuration;
	private final String _taskName;
	private final List<TestClass> _testClasses = new ArrayList<>();

}