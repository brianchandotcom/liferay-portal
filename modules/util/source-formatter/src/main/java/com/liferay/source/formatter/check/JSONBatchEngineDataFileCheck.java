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
			String fileName, String absolutePath, String content)
		throws JSONException {

		if (!absolutePath.endsWith(".batch-engine-data.json")) {
			return content;
		}

		JSONObject jsonObject = new JSONObjectImpl(content);

		jsonObject.remove("actions");
		jsonObject.remove("facets");

		JSONObject configurationJSONObject = jsonObject.getJSONObject(
			"configuration");

		if (configurationJSONObject != null) {
			configurationJSONObject.remove("companyId");

			boolean multiCompany = configurationJSONObject.getBoolean(
				"multiCompany");

			if (!multiCompany) {
				configurationJSONObject.remove("multiCompany");
			}

			configurationJSONObject.remove("userId");
			configurationJSONObject.remove("version");

			jsonObject.put("configuration", configurationJSONObject);
		}

		return JSONUtil.toString(jsonObject);
	}

}