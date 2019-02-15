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

import org.json.JSONException;
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
		try {
			JSONObject jsonObject = new JSONObject(content);

			if (jsonObject.isNull("devDependencies")) {
				return;
			}

			JSONObject devDependenciesJSONObject = jsonObject.getJSONObject(
				"devDependencies");

			for (Map.Entry<String, String> entry :
					_missingScriptsMap.entrySet()) {

				String packageName = entry.getKey();

				if (devDependenciesJSONObject.isNull(packageName)) {
					continue;
				}

				for (String missingScript :
						StringUtil.split(entry.getValue())) {

					String message = StringBundler.concat(
						"For Using '" + packageName + "', '", missingScript,
						"' should be enforced");

					if (jsonObject.isNull("scripts")) {
						addMessage(fileName, message);

						continue;
					}

					JSONObject scriptsJSONObject = jsonObject.getJSONObject(
						"scripts");

					if (scriptsJSONObject.isNull(missingScript)) {
						addMessage(fileName, message);

						continue;
					}

					String scriptValue = scriptsJSONObject.getString(
						missingScript);

					if (!scriptValue.startsWith(packageName + CharPool.SPACE)) {
						addMessage(fileName, message);
					}
				}
			}
		}
		catch (JSONException jsone) {
			addMessage(fileName, jsone.getMessage());
		}
	}

	private static final Map<String, String> _missingScriptsMap =
		new HashMap<String, String>() {
			{
				put("liferay-npm-scripts", "csf,format");
			}
		};

}