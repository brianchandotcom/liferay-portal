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

package com.liferay.jenkins.results.parser;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public abstract class BaseGitRepository implements GitRepository {

	@Override
	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	@Override
	public String getName() {
		return getString("name");
	}

	@Override
	public boolean isSubrepository() {
		String name = getName();

		if (name.startsWith("com-liferay-")) {
			return true;
		}

		return false;
	}

	protected BaseGitRepository(JSONObject jsonObject) {
		_jsonObject = jsonObject;

		validateKeys(_KEYS_REQUIRED);
	}

	protected BaseGitRepository(String name) {
		_jsonObject = new JSONObject();

		_setName(name);

		validateKeys(_KEYS_REQUIRED);
	}

	protected File getFile(String key) {
		return new File(getString(key));
	}

	protected JSONArray getJSONArray(String key) {
		return _jsonObject.getJSONArray(key);
	}

	protected String getString(String key) {
		return _jsonObject.getString(key);
	}

	protected boolean has(String key) {
		return _jsonObject.has(key);
	}

	protected String optString(String key) {
		return _jsonObject.optString(key);
	}

	protected String optString(String key, String defaultValue) {
		return _jsonObject.optString(key, defaultValue);
	}

	protected void put(String key, Object value) {
		_jsonObject.put(key, value);
	}

	protected void validateKeys(String[] requiredKeys) {
		for (String requiredKey : requiredKeys) {
			if (!has(requiredKey)) {
				throw new RuntimeException("Missing " + requiredKey);
			}
		}
	}

	private void _setName(String name) {
		if ((name == null) || name.isEmpty()) {
			throw new IllegalArgumentException("Name is null");
		}

		put("name", name);
	}

	private static final String[] _KEYS_REQUIRED = {"name"};

	private final JSONObject _jsonObject;

}