/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.BadRequestException;

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

	public static Map<String, String> getI18nMap(
		Map<Locale, String> localizedMap) {

		return getI18nMap(true, localizedMap);
	}

	public static Map<String, String> getLanguageIdMap(
		Map<Locale, String> localizedMap) {

		Map<String, String> languageIdMap = new HashMap<>();

		localizedMap.forEach(
			(locale, value) -> languageIdMap.put(
				LocaleUtil.toLanguageId(locale), value));

		return Collections.unmodifiableMap(languageIdMap);
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

	public static Map<Locale, String> getLocalizedMap(String label) {
		return Collections.singletonMap(LocaleUtil.getDefault(), label);
	}

	public static Map<String, String> mergeI18nMap(
		Map<String, String> i18nMap, String locale, String value) {

		if (Validator.isNull(locale)) {
			return i18nMap;
		}

		if (i18nMap == null) {
			return Collections.singletonMap(locale, value);
		}

		if (Validator.isNotNull(value)) {
			i18nMap.put(locale, value);
		}
		else {
			i18nMap.remove(locale);
		}

		return i18nMap;
	}

	public static Map<Locale, String> mergeLocalizedMap(
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

	public static Map<Locale, String> mergeLocalizedMap(
		Map<Locale, String> localizedMap, Map.Entry<Locale, String> entry) {

		if (entry == null) {
			return localizedMap;
		}

		return mergeLocalizedMap(
			localizedMap, entry.getKey(), entry.getValue());
	}

	public static Map<Locale, String> patchLocalizedMap(
		Map<Locale, String> localizedMap, Locale locale, String value) {

		if (value != null) {
			localizedMap.put(locale, value);
		}

		return localizedMap;
	}

	public static Map<Locale, String> patchLocalizedMap(
		Map<Locale, String> localizedMap, Locale defaultLocale,
		String defaultValue, Map<String, String> i18nMap) {

		Map<Locale, String> resultLocalizedMap = new HashMap<>();

		if (localizedMap != null) {
			resultLocalizedMap.putAll(localizedMap);
		}

		resultLocalizedMap = patchLocalizedMap(
			resultLocalizedMap, defaultLocale, defaultValue);

		if (i18nMap == null) {
			return resultLocalizedMap;
		}

		for (Map.Entry<String, String> entry : i18nMap.entrySet()) {
			Locale locale = _getLocale(entry.getKey());

			if (locale != null) {
				resultLocalizedMap = patchLocalizedMap(
					resultLocalizedMap, locale, entry.getValue());
			}
		}

		return resultLocalizedMap;
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
			StringBundler sb = new StringBundler(
				(notFoundLocales.size() * 2) + 2);

			sb.append(entityName);
			sb.append(" title missing in the languages: ");

			for (Locale locale : notFoundLocales) {
				sb.append(LocaleUtil.toW3cLanguageId(locale));
				sb.append(",");
			}

			sb.setIndex(sb.index() - 1);

			throw new BadRequestException(sb.toString());
		}
	}

	private static Locale _getLocale(String languageId) {
		return LocaleUtil.fromLanguageId(languageId, true, false);
	}

}