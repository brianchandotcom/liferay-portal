/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.util;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class DepotLanguageUtil {

	public static JSONArray getAvailableLocalesJSONArray(Locale locale) {
		JSONArray availableLocalesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			availableLocalesJSONArray.put(
				JSONUtil.put(
					"displayName", availableLocale.getDisplayName(locale)
				).put(
					"localeId", LocaleUtil.toLanguageId(availableLocale)
				));
		}

		return availableLocalesJSONArray;
	}

	public static JSONArray getDepotAvailableLocalesJSONArray(
		Group group, Locale locale) {

		JSONArray depotAvailableLocalesJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (Locale depotAvailableLocale : _getDepotAvailableLocales(group)) {
			depotAvailableLocalesJSONArray.put(
				JSONUtil.put(
					"displayName", depotAvailableLocale.getDisplayName(locale)
				).put(
					"localeId", LocaleUtil.toLanguageId(depotAvailableLocale)
				));
		}

		return depotAvailableLocalesJSONArray;
	}

	private static Locale[] _getDepotAvailableLocales(Group group) {
		UnicodeProperties typeSettingsUnicodeProperties =
			group.getTypeSettingsProperties();

		String groupLanguageIds = typeSettingsUnicodeProperties.getProperty(
			PropsKeys.LOCALES);

		if (groupLanguageIds != null) {
			return LocaleUtil.fromLanguageIds(
				StringUtil.split(groupLanguageIds));
		}

		Set<Locale> depotAvailableLocalesSet = LanguageUtil.getAvailableLocales(
			group.getGroupId());

		return depotAvailableLocalesSet.toArray(new Locale[0]);
	}

}