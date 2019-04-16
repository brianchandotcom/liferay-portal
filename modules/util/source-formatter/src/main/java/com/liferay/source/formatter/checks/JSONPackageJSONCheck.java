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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Alan Huang
 * @author Hugo Huijser
 */
public class JSONPackageJSONCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (absolutePath.endsWith("/package.json") &&
			absolutePath.contains("/modules/apps/")) {

			JSONObject jsonObject = new JSONObject(content);

			_checkIncorrectEntry(fileName, jsonObject, "devDependencies");
			_checkRequiredScripts(fileName, jsonObject);
		}

		return content;
	}

	private void _checkIncorrectEntry(
		String fileName, JSONObject jsonObject, String entryName) {

		if (!jsonObject.isNull(entryName)) {
			addMessage(fileName, "Entry '" + entryName + "' is not allowed");
		}
	}

	private void _checkRequiredScripts(String fileName, JSONObject jsonObject) {
		if (jsonObject.isNull("scripts")) {
			return;
		}

		JSONObject scriptsJSONObject = jsonObject.getJSONObject("scripts");

		for (Map.Entry<String, String> entry : _requiredScriptsMap.entrySet()) {
			String entryName = entry.getKey();

			if (scriptsJSONObject.isNull(entryName)) {
				addMessage(
					fileName, "Missing entry '" + entryName + "' in 'scripts'");

				continue;
			}

			String value = scriptsJSONObject.getString(entryName);

			if (!value.contains(entry.getValue())) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Value '", value, "' for entry '", entryName,
						"' does not match expected value '", entry.getValue(),
						"'"));
			}
		}
	}

	private static final Map<String, String> _requiredScriptsMap =
		new HashMap<String, String>() {
			{
				put("build", "liferay-npm-scripts build");
				put("checkFormat", "liferay-npm-scripts lint");
				put("format", "liferay-npm-scripts format");
			}
		};

}