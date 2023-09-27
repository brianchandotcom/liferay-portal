/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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