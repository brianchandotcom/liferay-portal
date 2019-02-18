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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Alan Huang
 */
public class JSONScriptsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (absolutePath.endsWith("/package.json")) {
			_checkMissingScripts(fileName, content);
		}

		return content;
	}

	private void _checkMissingScripts(String fileName, String content) {
		JSONObject jsonObject = new JSONObject(content);

		if (jsonObject.isNull("devDependencies")) {
			return;
		}

		JSONObject devDependenciesJSONObject = jsonObject.getJSONObject(
			"devDependencies");

		for (Map.Entry<String, String> entry : _requiredScriptsMap.entrySet()) {
			String packageName = entry.getKey();

			if (devDependenciesJSONObject.isNull(packageName)) {
				continue;
			}

			for (String requiredScript : StringUtil.split(entry.getValue())) {
				if (jsonObject.isNull("scripts")) {
					addMessage(
						fileName, _getMessage(packageName, requiredScript));

					continue;
				}

				JSONObject scriptsJSONObject = jsonObject.getJSONObject(
					"scripts");

				if (scriptsJSONObject.isNull(requiredScript)) {
					addMessage(
						fileName, _getMessage(packageName, requiredScript));

					continue;
				}

				String scriptValue = scriptsJSONObject.getString(
					requiredScript);

				if (!scriptValue.startsWith(packageName + CharPool.SPACE)) {
					addMessage(
						fileName, _getMessage(packageName, requiredScript));
				}
			}
		}
	}

	private String _getMessage(String packageName, String requiredScript) {
		return StringBundler.concat(
			"When using '" + packageName + "', a script for '", requiredScript,
			"' is required");
	}

	private static final Map<String, String> _requiredScriptsMap =
		new HashMap<String, String>() {
			{
				put("liferay-npm-scripts", "csf,format");
			}
		};

}