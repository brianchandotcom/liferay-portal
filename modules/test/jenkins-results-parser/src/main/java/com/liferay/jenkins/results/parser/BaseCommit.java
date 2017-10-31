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
		this(message, sha, null);
	}

	public BaseCommit(String message, String sha, Type type) {
		_message = message;
		_sha = sha;
		_type = type;
	}

	@Override
	public boolean equals(Object object) {
		if (object.hashCode() == hashCode()) {
			return true;
		}

		return false;
	}

	public String getAbbreviatedSHA() {
		return _sha.substring(0, 7);
	}

	public String getMessage() {
		return _message;
	}

	public String getSHA() {
		return _sha;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		JSONObject jsonObject = _toJSONObject();

		String json = jsonObject.toString();

		return json.hashCode();
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
	private final Type _type;

}