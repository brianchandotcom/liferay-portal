/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Iterator;
import java.util.Locale;

/**
 * @author Gabriel Albuquerque
 */
public class LocalizedValueUtil {

	public static JSONObject toJSONObject(LocalizedValue localizedValue) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (localizedValue == null) {
			return jsonObject;
		}

		for (Locale availableLocale : localizedValue.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				localizedValue.getString(availableLocale));
		}

		return jsonObject;
	}

	public static LocalizedValue toLocalizedValue(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		LocalizedValue localizedValue = new LocalizedValue();

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String languageId = iterator.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
	}

}