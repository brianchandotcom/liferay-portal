/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.task;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestTask implements TestTask {

	@Override
	public void addTestClass(TestClass testClass) {
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

		if (!(object instanceof TestTask)) {
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
			"name", getName()
		);

		JSONArray testClassesJSONArray = new JSONArray();

		for (TestClass testClass : getTestClasses()) {
			testClassesJSONArray.put(testClass.getJSONObject());
		}

		jsonObject.put("test_classes", testClassesJSONArray);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<TestClass> getTestClasses() {
		return _testClasses;
	}

	@Override
	public int hashCode() {
		String name = getName();

		return name.hashCode();
	}

	protected BaseTestTask(long averageDuration, String name) {
		_averageDuration = averageDuration;
		_name = name;
	}

	private final long _averageDuration;
	private final String _name;
	private final List<TestClass> _testClasses = new ArrayList<>();

}