/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.web.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collection;
import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public class ExportTranslationUtil {

	public static JSONArray getLocalesJSONArray(
		Locale locale, Collection<Locale> locales) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		locales.forEach(
			currentLocale -> jsonArray.put(
				_getLocaleJSONObject(currentLocale, locale)));

		return jsonArray;
	}

	private static JSONObject _getLocaleJSONObject(
		Locale currentLocale, Locale locale) {

		return JSONUtil.put(
			"displayName",
			LocaleUtil.getLocaleDisplayName(currentLocale, locale)
		).put(
			"languageId", LocaleUtil.toLanguageId(currentLocale)
		);
	}

}