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

package com.liferay.jenkins.results.parser.github.webhook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public abstract class Payload {

	public Payload(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	protected String get(String path) {
		return _get(jsonObject, path);
	}

	protected String getAction() {
		if (!jsonObject.has("action")) {
			return "";
		}

		return get("action");
	}

	protected JSONObject jsonObject;

	private String _get(JSONObject jsonObject, String path) {
		Matcher matcher = _jsonPathPattern.matcher(path);

		matcher.find();

		String key = matcher.group(1);

		String remainingPath = matcher.group(2);

		if ((remainingPath == null) || remainingPath.isEmpty()) {
			return jsonObject.getString(key);
		}

		return _get(jsonObject.getJSONObject(key), remainingPath);
	}

	private static final Pattern _jsonPathPattern = Pattern.compile(
		"([^/]+)/*(.*)");

}