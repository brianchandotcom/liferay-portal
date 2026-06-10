/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.language;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Map;

/**
 * @author Vendel Töreki
 */
public interface LanguageKeyResolver {

	public static final String FOR_EACH_LANGUAGE_ID =
		"[$FOR_EACH_LANGUAGE_ID$]";

	public void expand(JSONObject jsonObject);

	public void expand(Map<String, Object> fieldNameValueMap);

}