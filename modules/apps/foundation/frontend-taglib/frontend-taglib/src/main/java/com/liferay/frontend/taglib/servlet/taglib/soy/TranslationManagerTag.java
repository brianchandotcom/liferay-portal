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

package com.liferay.frontend.taglib.servlet.taglib.soy;

import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Set;

/**
 * @author Carlos Lancha
 */
public class TranslationManagerTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Set<Locale> locales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		JSONObject localesJSON = JSONFactoryUtil.createJSONObject();

		JSONArray availableLocalesArray = JSONFactoryUtil.createJSONArray();

		for (Locale curLocale : locales) {
			String w3cLanguageId = LocaleUtil.toW3cLanguageId(curLocale);

			String languageId = LocaleUtil.toLanguageId(curLocale);

			JSONObject localeJSON = JSONFactoryUtil.createJSONObject();

			localeJSON.put("code", w3cLanguageId);
			localeJSON.put("icon", StringUtil.toLowerCase(w3cLanguageId));
			localeJSON.put("id", languageId);
			localeJSON.put(
				"name", curLocale.getDisplayName(themeDisplay.getLocale()));

			localesJSON.put(languageId, localeJSON);

			if (ArrayUtil.contains(_availableLocales, curLocale)) {
				availableLocalesArray.put(languageId);
			}
		}

		putValue("availableLocales", availableLocalesArray);
		putValue("locales", localesJSON);
		putValue("pathThemeImages", themeDisplay.getPathThemeImages());

		setTemplateNamespace("TranslationManager.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "frontend-taglib/translation_manager/TranslationManager.es";
	}

	public void setAvailableLocales(Locale[] availableLocales) {
		_availableLocales = availableLocales;
		putValue("availableLocales", availableLocales);
	}

	public void setChangeableDefaultLanguage(
		boolean changeableDefaultLanguage) {

		putValue("changeableDefaultLanguage", changeableDefaultLanguage);
	}

	public void setCssClass(java.lang.String cssClass) {
		putValue("elementClasses", cssClass);
	}

	public void setDefaultLanguageId(java.lang.String defaultLanguageId) {
		putValue("defaultLocale", defaultLanguageId);
	}

	public void setEditingLanguageId(java.lang.String editingLanguageId) {
		putValue("editingLanguageId", editingLanguageId);
	}

	public void setId(java.lang.String id) {
		putValue("id", id);
	}

	public void setInitialize(boolean initialize) {
		putValue("initialize", initialize);
	}

	public void setReadOnly(boolean readOnly) {
		putValue("readOnly", readOnly);
	}

	private Locale[] _availableLocales;

}