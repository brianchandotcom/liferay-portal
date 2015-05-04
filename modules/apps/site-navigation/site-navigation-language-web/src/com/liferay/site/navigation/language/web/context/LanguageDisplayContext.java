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

package com.liferay.site.navigation.language.web.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;
import com.liferay.site.navigation.language.web.configuration.LanguagePortletInstanceConfiguration;

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

		_request = request;
	}

	public String[] getAvailableLanguageIds() {
		if (_availableLanguageIds != null) {
			return _availableLanguageIds;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_availableLanguageIds = LocaleUtil.toLanguageIds(
			LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()));

		return _availableLanguageIds;
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
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getSiteGroupId();
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
	private final HttpServletRequest _request;

}