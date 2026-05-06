/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

/**
 * @author Andrea Sbarra
 */
public class DefaultLanguageLabelsUtil {

	public static JSONObject getDefaultLanguageLabelsJSONObject(
			ThemeDisplay themeDisplay, String... keys)
		throws PortalException {

		Company company = themeDisplay.getCompany();

		Locale locale = company.getLocale();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String key : keys) {
			jsonObject.put(key, LanguageUtil.get(locale, key));
		}

		return JSONUtil.put(
			"labels", jsonObject
		).put(
			"locale", LocaleUtil.toLanguageId(locale)
		);
	}

}
