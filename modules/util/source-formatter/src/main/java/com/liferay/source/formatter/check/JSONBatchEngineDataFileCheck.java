/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Qi Zhang
 */
public class JSONBatchEngineDataFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith(".batch-engine-data.json") ||
			!absolutePath.matches(".+/workspaces/.+/client-extensions/.+")) {

			return content;
		}

		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObjectImpl(content);
		}
		catch (JSONException jsonException) {
			return content;
		}

		for (String unnecessaryAttribute : _UNNECESSARY_ATTRIBUTES) {
			_checkChildJsonObject(jsonObject, unnecessaryAttribute);
		}

		return JSONUtil.toString(jsonObject);
	}

	private void _checkChildJsonObject(
		JSONObject jsonObject, String unnecessaryAttribute) {

		int index = unnecessaryAttribute.indexOf(":");

		if (index != -1) {
			String key = unnecessaryAttribute.substring(0, index);

			JSONObject childJSONObject = jsonObject.getJSONObject(key);

			if (childJSONObject == null) {
				return;
			}

			_checkChildJsonObject(
				childJSONObject, unnecessaryAttribute.substring(index + 1));

			jsonObject.put(key, childJSONObject);

			return;
		}

		String[] attributes = unnecessaryAttribute.split(",");

		for (String attribute : attributes) {
			jsonObject.remove(attribute);
		}
	}

	private static final String[] _UNNECESSARY_ATTRIBUTES = {
		"actions", "configuration:companyId,userId,version", "facets"
	};

}