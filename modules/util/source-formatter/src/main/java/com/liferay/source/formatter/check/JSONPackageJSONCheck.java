/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.util.FileUtil;

import java.io.IOException;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Alan Huang
 * @author Hugo Huijser
 */
public class JSONPackageJSONCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/package.json") ||
			(!absolutePath.contains("/modules/apps/") &&
			 !absolutePath.contains("/modules/dxp/apps/") &&
			 !absolutePath.contains("/modules/private/apps/"))) {

			return content;
		}

		String dirName = absolutePath.substring(0, absolutePath.length() - 12);

		if (!FileUtil.exists(dirName + "build.gradle") &&
			!FileUtil.exists(dirName + "bnd.bnd")) {

			return content;
		}

		JSONObject jsonObject = new JSONObject(content);

		if (jsonObject.isNull("scripts")) {
			return content;
		}

		JSONObject scriptsJSONObject = jsonObject.getJSONObject("scripts");

		if (!scriptsJSONObject.isNull("build") &&
			Objects.equals(
				scriptsJSONObject.get("build"), "liferay-npm-bundler")) {

			return content;
		}

		_checkIncorrectEntry(fileName, jsonObject, "devDependencies");

		if (absolutePath.endsWith("frontend-theme-admin") ||
			absolutePath.endsWith("frontend-theme-classic") ||
			absolutePath.endsWith("frontend-theme-styled") ||
			absolutePath.endsWith("frontend-theme-unstyled")) {

			_checkScript(
				fileName, scriptsJSONObject, "build", false, "theme build");
		}
		else {
			_checkScript(
				fileName, scriptsJSONObject, "build", false, "build",
				"webpack");
		}

		_checkScript(fileName, scriptsJSONObject, "checkFormat", true, "check");
		_checkScript(fileName, scriptsJSONObject, "format", true, "fix");

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
		boolean requiredScript, String... allowedValues) {

		if (scriptsJSONObject.isNull(key)) {
			if (requiredScript) {
				addMessage(
					fileName, "Missing entry '" + key + "' in 'scripts'");
			}

			return;
		}

		String value = scriptsJSONObject.getString(key);

		for (String allowedValue : allowedValues) {
			if (value.endsWith(StringPool.SPACE + allowedValue)) {
				return;
			}
		}

		if (allowedValues.length == 1) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Value '", value, "' for entry '", key,
					"' should end with '", allowedValues[0], "'"));

			return;
		}

		StringBundler sb = new StringBundler((allowedValues.length * 3) + 5);

		sb.append("Value '");
		sb.append(value);
		sb.append("' for entry '");
		sb.append(key);
		sb.append("' should end with one of the following values: ");

		for (String allowedValue : allowedValues) {
			sb.append(StringPool.APOSTROPHE);
			sb.append(allowedValue);
			sb.append("', ");
		}

		sb.setIndex(sb.index() - 1);

		addMessage(fileName, sb.toString());
	}

}