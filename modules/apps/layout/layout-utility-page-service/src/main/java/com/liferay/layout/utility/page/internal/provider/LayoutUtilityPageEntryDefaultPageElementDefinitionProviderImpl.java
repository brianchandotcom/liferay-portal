/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.provider;

import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.provider.LayoutUtilityPageEntryDefaultPageElementDefinitionProvider;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	service = LayoutUtilityPageEntryDefaultPageElementDefinitionProvider.class
)
public class LayoutUtilityPageEntryDefaultPageElementDefinitionProviderImpl
	implements LayoutUtilityPageEntryDefaultPageElementDefinitionProvider {

	@Override
	public String getDefaultPageElementJSON(String type) {
		if (!_errorCodeNames.containsKey(type)) {
			return null;
		}

		JSONObject errorCodeI18nJSONObject = _jsonFactory.createJSONObject();
		JSONObject instructionsI18nJSONObject = _jsonFactory.createJSONObject();
		JSONObject layoutUtilityPageEntryDescriptionI18nJSONObject =
			_jsonFactory.createJSONObject();
		JSONObject layoutUtilityPageEntryInstructionsI18nJSONObject =
			_jsonFactory.createJSONObject();
		JSONObject layoutUtilityPageEntryTitleI18nJSONObject =
			_jsonFactory.createJSONObject();

		Set<Locale> locales = new HashSet<>(_language.getAvailableLocales());

		for (Locale locale : locales) {
			errorCodeI18nJSONObject.put(
				LocaleUtil.toLanguageId(locale),
				_language.format(
					locale, "error-code-x", _errorCodeNames.get(type)));
			instructionsI18nJSONObject.put(
				LocaleUtil.toLanguageId(locale),
				_language.get(locale, "instructions"));
			layoutUtilityPageEntryDescriptionI18nJSONObject.put(
				LocaleUtil.toLanguageId(locale),
				_language.get(
					locale,
					"layout-utility-page-entry-description[" + type + "]"));
			layoutUtilityPageEntryInstructionsI18nJSONObject.put(
				LocaleUtil.toLanguageId(locale),
				_language.get(
					locale,
					"layout-utility-page-entry-instructions[" + type + "]"));
			layoutUtilityPageEntryTitleI18nJSONObject.put(
				LocaleUtil.toLanguageId(locale),
				_language.get(
					locale, "layout-utility-page-entry-title[" + type + "]"));
		}

		return StringUtil.replace(
			StringUtil.read(
				LayoutUtilityPageEntryDefaultPageElementDefinitionProviderImpl.
					class,
				"default-layout-page-template-entry-page-element.json"),
			"\"[$", "$]\"",
			HashMapBuilder.put(
				"ERROR_CODE_I18N_JSON_VALUE", errorCodeI18nJSONObject.toString()
			).put(
				"INSTRUCTIONS_I18N_JSON_VALUE",
				instructionsI18nJSONObject.toString()
			).put(
				"LAYOUT_UTILITY_PAGE_ENTRY_DESCRIPTION_I18N_JSON_VALUE",
				layoutUtilityPageEntryDescriptionI18nJSONObject.toString()
			).put(
				"LAYOUT_UTILITY_PAGE_ENTRY_INSTRUCTIONS_I18N_JSON_VALUE",
				layoutUtilityPageEntryInstructionsI18nJSONObject.toString()
			).put(
				"LAYOUT_UTILITY_PAGE_ENTRY_TITLE_I18N_JSON_VALUE",
				layoutUtilityPageEntryTitleI18nJSONObject.toString()
			).build());
	}

	private static final Map<String, String> _errorCodeNames =
		HashMapBuilder.put(
			LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR, "500"
		).put(
			LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND, "404"
		).build();

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}