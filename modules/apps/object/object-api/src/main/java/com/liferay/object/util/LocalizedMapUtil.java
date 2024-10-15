/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.util;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class LocalizedMapUtil {

	public static Map<String, String> getI18nMap(
		String defaultLanguageId, Map<String, String> i18nMap,
		String siteDefaultValue) {

		if ((defaultLanguageId == null) && (siteDefaultValue == null)) {
			return i18nMap;
		}

		String siteDefaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		if (MapUtil.isEmpty(i18nMap)) {
			return HashMapBuilder.put(
				siteDefaultLanguageId, siteDefaultValue
			).build();
		}

		i18nMap = new HashMap<>(i18nMap);

		i18nMap.putIfAbsent(
			siteDefaultLanguageId,
			MapUtil.getString(i18nMap, defaultLanguageId, siteDefaultValue));

		return i18nMap;
	}

	public static Map<Locale, String> getLocalizedMap(
		String defaultLanguageId, Map<String, String> i18nMap) {

		return getLocalizedMap(defaultLanguageId, i18nMap, null);
	}

	public static Map<Locale, String> getLocalizedMap(
		String defaultLanguageId, Map<String, String> i18nMap,
		String siteDefaultValue) {

		return com.liferay.portal.vulcan.util.LocalizedMapUtil.getLocalizedMap(
			getI18nMap(defaultLanguageId, i18nMap, siteDefaultValue));
	}

}