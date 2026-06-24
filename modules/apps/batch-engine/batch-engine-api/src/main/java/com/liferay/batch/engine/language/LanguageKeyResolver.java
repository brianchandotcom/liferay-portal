/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.language;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.util.Map;

/**
 * @author Vendel Töreki
 */
public interface LanguageKeyResolver {

	public static final String FOR_EACH_LANGUAGE_ID =
		"[$FOR_EACH_LANGUAGE_ID$]";

	public void expand(long companyId, JSONObject jsonObject)
		throws ConfigurationException;

	public void expand(long companyId, Map<String, Object> fieldNameValueMap)
		throws ConfigurationException;

}