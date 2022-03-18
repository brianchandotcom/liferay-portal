/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser.test.clazz;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestClassMethod {

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		return _name;
	}

	public TestClass getTestClass() {
		return _testClass;
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

		_jsonObject.put("ignored", ignored);
		_jsonObject.put("name", name);
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