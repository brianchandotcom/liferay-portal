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

	public static Map<Locale, String> getLocaleMap(
		Locale defaultLocale, String defaultValue,
		Map<String, String> localizedMap) {

		Map<Locale, String> localeMap = getLocaleMap(localizedMap);

		localeMap.put(defaultLocale, defaultValue);

		return localeMap;
	}

	public static Map<Locale, String> getLocaleMap(
		Locale defaultLocale, String defaultValue,
		Map<String, String> localizedMap, Map<Locale, String> fallbackMap) {

		Map<Locale, String> localeMap;

		if (localizedMap != null) {
			localeMap = getLocaleMap(localizedMap);
		}
		else if (defaultValue != null) {
			localeMap = new HashMap<>(fallbackMap);
		}
		else {
			localeMap = new HashMap<>();
		}

		localeMap.put(defaultLocale, defaultValue);

		return localeMap;
	}

	public static Map<Locale, String> getLocaleMap(
		Map<String, String> localizedMap) {

		Map<Locale, String> localeMap = new HashMap<>();

		if (localizedMap == null) {
			return localeMap;
		}

		for (Map.Entry<String, String> entry : localizedMap.entrySet()) {
			Locale locale = _getLocale(entry.getKey());
			String localizedValue = entry.getValue();

			if ((locale != null) && (localizedValue != null)) {
				localeMap.put(locale, localizedValue);
			}
		}

		return localeMap;
	}

	public static Map<String, String> getLocalizedMap(
		boolean acceptAllLanguages, Map<Locale, String> localeMap) {

		if (!acceptAllLanguages) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localeMap.entrySet()) {
			Locale locale = entry.getKey();

			map.put(locale.toLanguageTag(), entry.getValue());
		}

		return map;
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
		Map<Locale, String> map, Locale defaultLocale, String defaultValue,
		Map<String, String> localizedMap) {

		Map<Locale, String> result = new HashMap<>();

		if (map != null) {
			result.putAll(map);
		}

		result = patch(result, defaultLocale, defaultValue);

		if (localizedMap == null) {
			return result;
		}

		for (Map.Entry<String, String> localizedEntry :
				localizedMap.entrySet()) {

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