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

package com.liferay.portal.vulcan.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizedMapUtil {

	public static Map<String, String> getI18nMap(
		boolean acceptAllLanguages, Map<Locale, String> localizedMap) {

		if (!acceptAllLanguages) {
			return null;
		}

		Map<String, String> i18nMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localizedMap.entrySet()) {
			Locale locale = entry.getKey();

			i18nMap.put(locale.toLanguageTag(), entry.getValue());
		}

		return i18nMap;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getI18nMap(boolean, Map)}
	 */
	@Deprecated
	public static Map<String, String> getLocalizedMap(
		boolean acceptAllLanguages, Map<Locale, String> localizedMap) {

		return getI18nMap(acceptAllLanguages, localizedMap);
	}

	public static Map<Locale, String> getLocalizedMap(
		Locale defaultLocale, String defaultValue,
		Map<String, String> i18nMap) {

		Map<Locale, String> localizedMap = getLocalizedMap(i18nMap);

		localizedMap.put(defaultLocale, defaultValue);

		return localizedMap;
	}

	public static Map<Locale, String> getLocalizedMap(
		Locale defaultLocale, String defaultValue, Map<String, String> i18nMap,
		Map<Locale, String> fallbackMap) {

		Map<Locale, String> localizedMap;

		if (i18nMap != null) {
			localizedMap = getLocalizedMap(i18nMap);
		}
		else if (defaultValue != null) {
			localizedMap = new HashMap<>(fallbackMap);
		}
		else {
			localizedMap = new HashMap<>();
		}

		localizedMap.put(defaultLocale, defaultValue);

		return localizedMap;
	}

	public static Map<Locale, String> getLocalizedMap(
		Map<String, String> i18nMap) {

		Map<Locale, String> localizedMap = new HashMap<>();

		if (i18nMap == null) {
			return localizedMap;
		}

		for (Map.Entry<String, String> entry : i18nMap.entrySet()) {
			Locale locale = _getLocale(entry.getKey());
			String localizedValue = entry.getValue();

			if ((locale != null) && (localizedValue != null)) {
				localizedMap.put(locale, localizedValue);
			}
		}

		return localizedMap;
	}

	public static Map<Locale, String> merge(
		Map<Locale, String> map, Locale locale, String value) {

		if (locale == null) {
			return map;
		}

		if (map == null) {
			return Collections.singletonMap(locale, value);
		}

		if (value != null) {
			map.put(locale, value);
		}
		else {
			map.remove(locale);
		}

		return map;
	}

	public static Map<Locale, String> merge(
		Map<Locale, String> map, Map.Entry<Locale, String> entry) {

		if (entry == null) {
			return map;
		}

		return merge(map, entry.getKey(), entry.getValue());
	}

	public static Map<Locale, String> patch(
		Map<Locale, String> map, Locale locale, String value) {

		if (value != null) {
			map.put(locale, value);
		}

		return map;
	}

	public static Map<Locale, String> patch(
		Map<Locale, String> localizedMap, Locale defaultLocale,
		String defaultValue, Map<String, String> i18nMap) {

		Map<Locale, String> result = new HashMap<>();

		if (localizedMap != null) {
			result.putAll(localizedMap);
		}

		result = patch(result, defaultLocale, defaultValue);

		if (i18nMap == null) {
			return result;
		}

		for (Map.Entry<String, String> localizedEntry : i18nMap.entrySet()) {
			Locale locale = _getLocale(localizedEntry.getKey());

			if (locale != null) {
				result = patch(result, locale, localizedEntry.getValue());
			}
		}

		return result;
	}

	private static Locale _getLocale(String localeTag) {
		return LocaleUtil.fromLanguageId(
			StringUtil.replace(localeTag, CharPool.MINUS, CharPool.UNDERLINE),
			true, false);
	}

}