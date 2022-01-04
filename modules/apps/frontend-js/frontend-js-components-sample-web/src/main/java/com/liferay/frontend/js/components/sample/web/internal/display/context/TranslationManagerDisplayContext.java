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

package com.liferay.frontend.js.components.sample.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Carlos Lancha
 */
public class TranslationManagerDisplayContext {

	public List<String> getActiveLanguageIds() {
		if (_activeLanguageIds != null) {
			return _activeLanguageIds;
		}

		_activeLanguageIds = new ArrayList<>();

		_activeLanguageIds.add(getDefaultLanguageId());
		_activeLanguageIds.add("ca_ES");
		_activeLanguageIds.add("fr_FR");

		return _activeLanguageIds;
	}

	public Set<Locale> getAvailableLocales() {
		if (_availableLocales != null) {
			return _availableLocales;
		}

		_availableLocales = LanguageUtil.getAvailableLocales();

		return _availableLocales;
	}

	public JSONArray getAvailableLocalesJSONArray() {
		if (_availableLocalesJSONArray != null) {
			return _availableLocalesJSONArray;
		}

		Set<Locale> availableLocales = getAvailableLocales();

		JSONArray availableLocalesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : availableLocales) {
			String id =
				availableLocale.getLanguage() + "-" +
					availableLocale.getCountry();

			availableLocalesJSONArray.put(
				JSONUtil.put(
					"displayName", availableLocale.getDisplayName()
				).put(
					"id", StringUtil.replace(id, '-', "_")
				).put(
					"label", id
				).put(
					"symbol", StringUtil.toLowerCase(id)
				));
		}

		return availableLocalesJSONArray;
	}

	public String getDefaultLanguageId() {
		if (_defaultLanguageId != null) {
			return _defaultLanguageId;
		}

		_defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		return _defaultLanguageId;
	}

	public Map<String, Object> getTranslations() {
		if (_translations != null) {
			return _translations;
		}

		_translations = HashMapBuilder.<String, Object>put(
			"ca-ES", "Lorem"
		).build();

		return _translations;
	}

	private List<String> _activeLanguageIds;
	private Set<Locale> _availableLocales;
	private JSONArray _availableLocalesJSONArray;
	private String _defaultLanguageId;
	private Map<String, Object> _translations;

}