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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseCommit implements Commit {

	public BaseCommit(String message, String sha) {
		_message = message;
		_sha = sha;
	}

	@Override
	public boolean equals(Object o) {
		if (o.hashCode() == hashCode()) {
			return true;
		}

		return false;
	}

	public String getAbbreviatedSha() {
		return _sha.substring(0, 7);
	}

	public String getMessage() {
		return _message;
	}

	public String getSha() {
		return _sha;
	}

	@Override
	public int hashCode() {
		JSONObject jsonObject = _toJSONObject();

		String jsonObjectString = jsonObject.toString();

		return jsonObjectString.hashCode();
	}

	private JSONObject _toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", _message);
		jsonObject.put("sha", _sha);

		return jsonObject;
	}

	private static final Map<String, Integer> _map = new HashMap<>();

	private final String _message;
	private final String _sha;

}