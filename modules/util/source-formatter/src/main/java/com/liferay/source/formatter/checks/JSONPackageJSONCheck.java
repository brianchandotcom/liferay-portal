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
import com.liferay.source.formatter.util.FileUtil;

import org.json.JSONObject;

/**
 * @author Alan Huang
 * @author Hugo Huijser
 */
public class JSONPackageJSONCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/package.json") ||
			!absolutePath.contains("/modules/apps/")) {

			return content;
		}

		String dirName = absolutePath.substring(0, absolutePath.length() - 12);

		if (!FileUtil.exists(dirName + "build.gradle") &&
			!FileUtil.exists(dirName + "bnd.bnd")) {

			return content;
		}

		JSONObject jsonObject = new JSONObject(content);

		_checkIncorrectEntry(fileName, jsonObject, "devDependencies");

		if (jsonObject.isNull("scripts")) {
			return content;
		}

		JSONObject scriptsJSONObject = jsonObject.getJSONObject("scripts");

		if (absolutePath.contains("/modules/apps/frontend-theme")) {
			_checkScript(
				fileName, scriptsJSONObject, "build",
				"liferay-npm-scripts theme build", false);
		}
		else {
			_checkScript(
				fileName, scriptsJSONObject, "build",
				"liferay-npm-scripts build", false);
		}

		_checkScript(
			fileName, scriptsJSONObject, "checkFormat",
			"liferay-npm-scripts lint", true);
		_checkScript(
			fileName, scriptsJSONObject, "format", "liferay-npm-scripts format",
			true);

		return content;
	}

	private void _checkIncorrectEntry(
		String fileName, JSONObject jsonObject, String entryName) {

		if (!jsonObject.isNull(entryName)) {
			addMessage(fileName, "Entry '" + entryName + "' is not allowed");
		}
	}

	private void _checkScript(
		String fileName, JSONObject scriptsJSONObject, String key,
		String expectedValue, boolean requiredScript) {

		if (scriptsJSONObject.isNull(key)) {
			if (requiredScript) {
				addMessage(
					fileName, "Missing entry '" + key + "' in 'scripts'");
			}

			return;
		}

		String value = scriptsJSONObject.getString(key);

		if (!value.contains(expectedValue)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Value '", value, "' for entry '", key,
					"' does not contain '", expectedValue, "'"));
		}
	}

}