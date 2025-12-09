/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mikel Lorza
 */
public class LocalizedValueUtil {

	public static List<String> getAvailableLanguageIds() {
		return TransformUtil.transform(
			LanguageUtil.getAvailableLocales(), LocaleUtil::toBCP47LanguageId);
	}

	public static JSONObject toJSONObject(Map<String, String> localizedValues) {
		return toJSONObject(localizedValues, value -> value);
	}

	public static <T, R, E extends Throwable> JSONObject toJSONObject(
			Map<String, T> localizedValues,
			UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		if (localizedValues == null) {
			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<String> availableLanguageIds = getAvailableLanguageIds();

		for (Map.Entry<String, T> entry : localizedValues.entrySet()) {
			if (availableLanguageIds.contains(entry.getKey())) {
				jsonObject.put(
					entry.getKey(), unsafeFunction.apply(entry.getValue()));
			}
		}

		return jsonObject;
	}

	public static Map<String, String> toLocalizedValues(JSONObject jsonObject) {
		return toLocalizedValues(jsonObject, key -> jsonObject.getString(key));
	}

	public static <R, E extends Throwable> Map<String, R> toLocalizedValues(
			JSONObject jsonObject, UnsafeFunction<String, R, E> unsafeFunction)
		throws E {

		if (jsonObject == null) {
			return null;
		}

		return new HashMap<>() {
			{
				List<String> availableLanguageIds = getAvailableLanguageIds();

				for (String key : jsonObject.keySet()) {
					if (availableLanguageIds.contains(key)) {
						put(key, unsafeFunction.apply(key));
					}
				}
			}
		};
	}

}