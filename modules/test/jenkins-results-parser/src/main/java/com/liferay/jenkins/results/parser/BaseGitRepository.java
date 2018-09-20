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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public abstract class BaseGitRepository
	extends JSONObject implements GitRepository {

	@Override
	public String getName() {
		return getString("name");
	}

	@Override
	public JSONObject put(String key, Object value) throws JSONException {
		if (has(key)) {
			throw new RuntimeException("Already contains " + key);
		}

		super.put(key, value);

		return this;
	}

	protected BaseGitRepository(String name) {
		super("{}");

		if ((name == null) || name.isEmpty()) {
			throw new IllegalArgumentException("Name is null");
		}

		put("name", name);

		validateKeys(_REQUIRED_KEYS);
	}

	protected File getFile(String key) {
		return new File(getString(key));
	}

	protected void validateKeys(String[] requiredKeys) {
		for (String requiredKey : requiredKeys) {
			if (!has(requiredKey)) {
				throw new RuntimeException("Missing " + requiredKey);
			}
		}
	}

	private static final String[] _REQUIRED_KEYS = {"name"};

}