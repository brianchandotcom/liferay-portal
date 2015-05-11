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

package com.liferay.site.navigation.language.web.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;
import com.liferay.site.navigation.language.web.configuration.LanguagePortletInstanceConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class LanguageDisplayContext {

	public LanguageDisplayContext(HttpServletRequest request)
		throws SettingsException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.util.WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_languagePortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				LanguagePortletInstanceConfiguration.class);

		_themeDisplay = themeDisplay;
	}

	public String[] getAvailableLanguageIds() {
		if (_availableLanguageIds != null) {
			return _availableLanguageIds;
		}

		_availableLanguageIds = LocaleUtil.toLanguageIds(
			LanguageUtil.getAvailableLocales(_themeDisplay.getSiteGroupId()));

		return _availableLanguageIds;
	}

	public List<KeyValuePair> getAvailableLanguageList() {
		Set<String> availableLanguageIdsSet = SetUtil.fromArray(
			getAvailableLanguageIds());

		String[] languageIds = getLanguageIds();

		Arrays.sort(languageIds);

		List<KeyValuePair> availableLanguageList = new ArrayList<>();

		for (String languageId : availableLanguageIdsSet) {
			if (Arrays.binarySearch(languageIds, languageId) < 0) {
				availableLanguageList.add(
					new KeyValuePair(
						languageId,
						LocaleUtil.fromLanguageId(
							languageId).getDisplayName(
								_themeDisplay.getLocale())));
			}
		}

		availableLanguageList = ListUtil.sort(
			availableLanguageList, new KeyValuePairComparator(false, true));

		return availableLanguageList;
	}

	public List<KeyValuePair> getCurrentLanguageList() {
		List<KeyValuePair> currentLanguageList = new ArrayList<>();

		String[] languageIds = getLanguageIds();

		for (String languageId : languageIds) {
			currentLanguageList.add(
				new KeyValuePair(
					languageId,
					LocaleUtil.fromLanguageId(
						languageId).getDisplayName(_themeDisplay.getLocale())));
		}

		return currentLanguageList;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String displayStyle = getDisplayStyle();

		if (displayStyle != null) {
			_ddmTemplateKey = PortletDisplayTemplateUtil.getDDMTemplateKey(
				displayStyle);
		}

		return _ddmTemplateKey;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = _languagePortletInstanceConfiguration.displayStyle();

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_languagePortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			_displayStyleGroupId = _themeDisplay.getSiteGroupId();
		}

		return _displayStyleGroupId;
	}

	public String[] getLanguageIds() {
		if (_languageIds != null) {
			return _languageIds;
		}

		_languageIds = StringUtil.split(
			_languagePortletInstanceConfiguration.languageIds());

		if (ArrayUtil.isEmpty(_languageIds)) {
			_languageIds = getAvailableLanguageIds();
		}

		return _languageIds;
	}

	public boolean isDisplayCurrentLocale() {
		if (_displayCurrentLocale != null) {
			return _displayCurrentLocale;
		}

		_displayCurrentLocale =
			_languagePortletInstanceConfiguration.displayCurrentLocale();

		return _displayCurrentLocale;
	}

	private String[] _availableLanguageIds;
	private String _ddmTemplateKey;
	private Boolean _displayCurrentLocale;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private String[] _languageIds;
	private final LanguagePortletInstanceConfiguration
		_languagePortletInstanceConfiguration;
	private final ThemeDisplay _themeDisplay;

}