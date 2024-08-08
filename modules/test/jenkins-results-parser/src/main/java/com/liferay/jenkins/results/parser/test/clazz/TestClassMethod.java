/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestClassMethod implements Comparable<TestClassMethod> {

	@Override
	public int compareTo(TestClassMethod testClassMethod) {
		if (testClassMethod == null) {
			throw new NullPointerException("Test class is null");
		}

		return _name.compareTo(testClassMethod.getName());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestClassMethod)) {
			return false;
		}

		if (Objects.equals(hashCode(), object.hashCode())) {
			return true;
		}

		return false;
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		return _name;
	}

	public TestClass getTestClass() {
		return _testClass;
	}

	@Override
	public int hashCode() {
		String name = getName();

		return name.hashCode();
	}

	public boolean isIgnored() {
		return _ignored;
	}

	protected TestClassMethod(
		boolean ignored, String name, TestClass testClass) {

		_ignored = ignored;
		_name = name;
		_testClass = testClass;

		_jsonObject = new JSONObject();

		_jsonObject.put(
			"ignored", ignored
		).put(
			"name", name
		);
	}

	protected TestClassMethod(JSONObject jsonObject, TestClass testClass) {
		_jsonObject = jsonObject;
		_testClass = testClass;

		_ignored = jsonObject.getBoolean("ignored");
		_name = jsonObject.getString("name");
	}

	private final boolean _ignored;
	private final JSONObject _jsonObject;
	private final String _name;
	private final TestClass _testClass;

}