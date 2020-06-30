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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizedMapUtil {

	public static Map<String, String> getLocalizedMap(
		boolean acceptAllLanguages, Map<Locale, String> localizedMap) {

		if (!acceptAllLanguages) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localizedMap.entrySet()) {
			Locale locale = entry.getKey();

			map.put(locale.toLanguageTag(), entry.getValue());
		}

		return map;
	}

	public static Map<Locale, String> getLocalizedMap(
		Locale defaultLocale, String defaultValue,
		Map<String, String> i18nMap) {

		Map<Locale, String> localizedMap = getLocalizedMap(i18nMap);

		if (defaultValue != null) {
			localizedMap.put(defaultLocale, defaultValue);
		}

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

		if (defaultValue != null) {
			localizedMap.put(defaultLocale, defaultValue);
		}

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
		Map<Locale, String> map, Map.Entry<Locale, String> entry) {

		if (map == null) {
			return Stream.of(
				entry
			).collect(
				Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
			);
		}

		if (entry == null) {
			return map;
		}

		if (entry.getValue() == null) {
			map.remove(entry.getKey());

			return map;
		}

		Set<Map.Entry<Locale, String>> mapEntries = map.entrySet();

		return Stream.concat(
			mapEntries.stream(), Stream.of(entry)
		).collect(
			Collectors.toMap(
				Map.Entry::getKey, Map.Entry::getValue,
				(value1, value2) -> value2)
		);
	}

	public static Map<Locale, String> patch(
		Map<Locale, String> map, Locale locale, String value) {

		if (value != null) {
			map.put(locale, value);
		}

		return map;
	}

	public static void validateI18n(
		boolean add, Locale defaultLocale, String entityName,
		Map<Locale, String> localizedMap, Set<Locale> notFoundLocales) {

		if ((add && localizedMap.isEmpty()) ||
			!localizedMap.containsKey(defaultLocale)) {

			throw new BadRequestException(
				entityName + " must include the default language " +
					LocaleUtil.toW3cLanguageId(defaultLocale));
		}

		notFoundLocales.removeAll(localizedMap.keySet());

		if (!notFoundLocales.isEmpty()) {
			Stream<Locale> notFoundLocaleStream = notFoundLocales.stream();

			throw new BadRequestException(
				StringBundler.concat(
					entityName, " title missing in the languages: ",
					notFoundLocaleStream.map(
						LocaleUtil::toW3cLanguageId
					).collect(
						Collectors.joining(",")
					)));
		}
	}

	private static Locale _getLocale(String languageId) {
		return LocaleUtil.fromLanguageId(languageId, true, false);
	}

}