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

import com.liferay.portal.kernel.util.LocaleUtil;

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

			i18nMap.put(LocaleUtil.toBCP47LanguageId(locale), entry.getValue());
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
		Map<Locale, String> fallbackLocalizedMap) {

		Map<Locale, String> localizedMap = null;

		if (i18nMap != null) {
			localizedMap = getLocalizedMap(i18nMap);
		}
		else if (defaultValue != null) {
			localizedMap = new HashMap<>(fallbackLocalizedMap);
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
			String value = entry.getValue();

			if ((locale != null) && (value != null)) {
				localizedMap.put(locale, value);
			}
		}

		return localizedMap;
	}

	public static Map<Locale, String> merge(
		Map<Locale, String> localizedMap, Locale locale, String value) {

		if (locale == null) {
			return localizedMap;
		}

		if (localizedMap == null) {
			return Collections.singletonMap(locale, value);
		}

		if (value != null) {
			localizedMap.put(locale, value);
		}
		else {
			localizedMap.remove(locale);
		}

		return localizedMap;
	}

	public static Map<Locale, String> merge(
		Map<Locale, String> localizedMap, Map.Entry<Locale, String> entry) {

		if (entry == null) {
			return localizedMap;
		}

		return merge(localizedMap, entry.getKey(), entry.getValue());
	}

	public static Map<Locale, String> patch(
		Map<Locale, String> localizedMap, Locale locale, String value) {

		if (value != null) {
			localizedMap.put(locale, value);
		}

		return localizedMap;
	}

	public static Map<Locale, String> patch(
		Map<Locale, String> localizedMap, Locale defaultLocale,
		String defaultValue, Map<String, String> i18nMap) {

		Map<Locale, String> resultLocalizedMap = new HashMap<>();

		if (localizedMap != null) {
			resultLocalizedMap.putAll(localizedMap);
		}

		resultLocalizedMap = patch(
			resultLocalizedMap, defaultLocale, defaultValue);

		if (i18nMap == null) {
			return resultLocalizedMap;
		}

		for (Map.Entry<String, String> entry : i18nMap.entrySet()) {
			Locale locale = _getLocale(entry.getKey());

			if (locale != null) {
				resultLocalizedMap = patch(
					resultLocalizedMap, locale, entry.getValue());
			}
		}

		return resultLocalizedMap;
	}

	private static Locale _getLocale(String languageId) {
		return LocaleUtil.fromLanguageId(languageId, true, false);
	}

}