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

package com.liferay.object.util;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Guilherme Camacho
 */
public class LocalizedMapUtil {

	public static Map<String, String> getLanguageIdMap(
		Map<Locale, String> map) {

		Map<String, String> localizedMap = new HashMap<>();

		map.forEach(
			(locale, value) -> localizedMap.put(
				LocaleUtil.toLanguageId(locale), value));

		return Collections.unmodifiableMap(localizedMap);
	}

	public static Map<Locale, String> getLocalizedMap(
		Map<String, String> i18nMap) {

		Map<Locale, String> localizedMap = new HashMap<>();

		if (i18nMap == null) {
			return localizedMap;
		}

		for (Map.Entry<String, String> entry : i18nMap.entrySet()) {
			Locale locale = _getLocale(entry.getKey());
			String value = entry.getValue();

			if ((locale != null) && (value != null)) {
				localizedMap.put(locale, value);
			}
		}

		return localizedMap;
	}

	public static Map<Locale, String> getLocalizedMap(String label) {
		return Collections.singletonMap(LocaleUtil.getDefault(), label);
	}

	private static Locale _getLocale(String languageId) {
		return LocaleUtil.fromLanguageId(languageId, true, false);
	}

}