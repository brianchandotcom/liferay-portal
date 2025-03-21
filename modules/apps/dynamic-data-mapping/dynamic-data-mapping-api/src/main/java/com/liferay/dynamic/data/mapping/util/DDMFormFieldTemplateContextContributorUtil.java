/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Pedro Leite
 */
public class DDMFormFieldTemplateContextContributorUtil {

	public static Map<Locale, String> getListTypeEntryNameMap(
		DDMFormField ddmFormField, String key,
		ListTypeEntryLocalService listTypeEntryLocalService) {

		long listTypeDefinitionId = GetterUtil.getLong(
			ddmFormField.getProperty("listTypeDefinitionId"));

		if (listTypeDefinitionId == 0) {
			return null;
		}

		ListTypeEntry listTypeEntry =
			listTypeEntryLocalService.fetchListTypeEntry(
				listTypeDefinitionId, key);

		if (listTypeEntry == null) {
			return null;
		}

		return listTypeEntry.getNameMap();
	}

	public static Map<String, Object> getLocalizationParameters(
		DDMFormField ddmFormField, Locale defaultLocale) {

		JSONObject localeJSONObject = _getLocaleJSONObject(defaultLocale);

		return HashMapBuilder.<String, Object>put(
			"availableLocales",
			JSONUtil.toJSONArray(
				LanguageUtil.getAvailableLocales(),
				locale -> _getLocaleJSONObject(locale), _log)
		).put(
			"defaultLocale", localeJSONObject
		).put(
			"editingLocale", localeJSONObject
		).put(
			"editOnlyInDefaultLanguage",
			() -> {
				if (!ddmFormField.hasProperty("editOnlyInDefaultLanguage")) {
					return null;
				}

				return GetterUtil.getBoolean(
					ddmFormField.getProperty("editOnlyInDefaultLanguage"));
			}
		).put(
			"isLocalizationSupported",
			() -> {
				if (!ddmFormField.hasProperty("isLocalizationSupported")) {
					return null;
				}

				return GetterUtil.getBoolean(
					ddmFormField.getProperty("isLocalizationSupported"));
			}
		).build();
	}

	private static JSONObject _getLocaleJSONObject(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return JSONUtil.put(
			"displayName", locale.getDisplayName(locale)
		).put(
			"icon",
			StringUtil.toLowerCase(StringUtil.replace(languageId, '_', "-"))
		).put(
			"localeId", languageId
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldTemplateContextContributorUtil.class);

}