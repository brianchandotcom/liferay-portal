/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.model;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Keven Leone
 */
public class Authorization {

	public Authorization(String accessToken, String settings, String url) {
		this.accessToken = accessToken;
		this.settings = settings;
		this.url = url;
	}

	public JSONObject toJSONObject() throws JSONException {
		return JSONUtil.put(
			"accessToken", accessToken
		).put(
			"settings", JSONFactoryUtil.createJSONObject(settings)
		).put(
			"url", url
		);
	}

	public String accessToken;
	public String settings;
	public String url;

}