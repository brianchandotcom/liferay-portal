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

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pablo Molina
 * @author Jürgen Kappler
 */
public class DisplaySettingsDisplayContext {

	public DisplaySettingsDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public long getLiveGroupId() {
		Group liveGroup = _getLiveGroup();

		return liveGroup.getGroupId();
	}

	public Map<String, Object> getPropsMap() {
		Group liveGroup = _getLiveGroup();

		return HashMapBuilder.<String, Object>put(
			"availableLanguages", _getAvailableLanguagesJSONArray()
		).put(
			"currentLanguages", _getCurrentLanguagesJSONArray()
		).put(
			"defaultLanguageId",
			() -> {
				Locale siteDefaultLocale = PortalUtil.getSiteDefaultLocale(
					liveGroup.getGroupId());

				return LocaleUtil.toLanguageId(siteDefaultLocale);
			}
		).put(
			"inheritLocales",
			() -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					_getTypeSettingsUnicodeProperties();

				return GetterUtil.getBoolean(
					typeSettingsUnicodeProperties.getProperty(
						GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES),
					true);
			}
		).put(
			"liveGroupIsGuest", liveGroup.isGuest()
		).put(
			"liveGroupIsOrganization", liveGroup.isOrganization()
		).put(
			"portletNamespace", _liferayPortletResponse.getNamespace()
		).build();
	}

	private JSONArray _getAvailableLanguagesJSONArray() {
		JSONArray availableLanguagesJSONArray =
			JSONFactoryUtil.createJSONArray();

		Set<JSONObject> availableLanguagesJSONObjects = new TreeSet<>(
			(jsonObject1, jsonObject2) -> {
				String value1 = jsonObject1.getString("value");
				String value2 = jsonObject2.getString("value");

				return value1.compareTo(value2);
			});

		Set<Locale> siteAvailableLocales = _getSiteAvailableLocales();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			if (!siteAvailableLocales.contains(availableLocale)) {
				availableLanguagesJSONObjects.add(
					JSONUtil.put(
						"label",
						availableLocale.getDisplayName(
							_themeDisplay.getLocale())
					).put(
						"value", LocaleUtil.toLanguageId(availableLocale)
					));
			}
		}

		for (JSONObject availableLanguageJSONObject :
				availableLanguagesJSONObjects) {

			availableLanguagesJSONArray.put(availableLanguageJSONObject);
		}

		return availableLanguagesJSONArray;
	}

	private JSONArray _getCurrentLanguagesJSONArray() {
		JSONArray currentLanguagesJSONArray = JSONFactoryUtil.createJSONArray();

		UnicodeProperties typeSettingsUnicodeProperties =
			_getTypeSettingsUnicodeProperties();

		String groupLanguageIds = typeSettingsUnicodeProperties.getProperty(
			PropsKeys.LOCALES);

		if (groupLanguageIds != null) {
			Set<String> companyLanguageIds = SetUtil.fromArray(
				PrefsPropsUtil.getStringArray(
					_themeDisplay.getCompanyId(), PropsKeys.LOCALES,
					StringPool.COMMA, PropsValues.LOCALES_ENABLED));

			for (Locale currentLocale :
					LocaleUtil.fromLanguageIds(
						StringUtil.split(groupLanguageIds))) {

				if (!companyLanguageIds.contains(
						LanguageUtil.getLanguageId(currentLocale))) {

					continue;
				}

				currentLanguagesJSONArray.put(
					JSONUtil.put(
						"label",
						currentLocale.getDisplayName(_themeDisplay.getLocale())
					).put(
						"value", LanguageUtil.getLanguageId(currentLocale)
					));
			}
		}
		else {
			Set<Locale> siteAvailableLocales = _getSiteAvailableLocales();

			for (Locale siteAvailableLocale : siteAvailableLocales) {
				currentLanguagesJSONArray.put(
					JSONUtil.put(
						"label",
						siteAvailableLocale.getDisplayName(
							_themeDisplay.getLocale())
					).put(
						"value", LanguageUtil.getLanguageId(siteAvailableLocale)
					));
			}
		}

		return currentLanguagesJSONArray;
	}

	private Group _getLiveGroup() {
		if (_liveGroup != null) {
			return _liveGroup;
		}

		Group siteGroup = _themeDisplay.getSiteGroup();

		if (siteGroup.isStagingGroup()) {
			_liveGroup = siteGroup.getLiveGroup();
		}
		else {
			_liveGroup = siteGroup;
		}

		return _liveGroup;
	}

	private Set<Locale> _getSiteAvailableLocales() {
		if (_siteAvailableLocales != null) {
			return _siteAvailableLocales;
		}

		Group liveGroup = _getLiveGroup();

		_siteAvailableLocales = LanguageUtil.getAvailableLocales(
			liveGroup.getGroupId());

		return _siteAvailableLocales;
	}

	private UnicodeProperties _getTypeSettingsUnicodeProperties() {
		if (_typeSettingsUnicodeProperties != null) {
			return _typeSettingsUnicodeProperties;
		}

		UnicodeProperties typeSettingsUnicodeProperties = null;

		Group liveGroup = _getLiveGroup();

		if (liveGroup != null) {
			typeSettingsUnicodeProperties =
				liveGroup.getTypeSettingsProperties();
		}
		else {
			typeSettingsUnicodeProperties = new UnicodeProperties();
		}

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;

		return _typeSettingsUnicodeProperties;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Group _liveGroup;
	private Set<Locale> _siteAvailableLocales;
	private final ThemeDisplay _themeDisplay;
	private UnicodeProperties _typeSettingsUnicodeProperties;

}