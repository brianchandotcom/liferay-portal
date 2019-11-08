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

package com.liferay.site.navigation.language.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.KeyValuePairComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.language.web.internal.configuration.SiteNavigationLanguagePortletInstanceConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class SiteNavigationLanguageDisplayContext {

	public SiteNavigationLanguageDisplayContext(
			HttpServletRequest httpServletRequest)
		throws ConfigurationException {

		_portletDisplayTemplate =
			(PortletDisplayTemplate)httpServletRequest.getAttribute(
				WebKeys.PORTLET_DISPLAY_TEMPLATE);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_siteNavigationLanguagePortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteNavigationLanguagePortletInstanceConfiguration.class);
	}

	public List<KeyValuePair> getAvailableLanguageIdKVPs() {
		List<KeyValuePair> availableLanguageIdKVPs = new ArrayList<>();

		String[] languageIds = getLanguageIds();

		Arrays.sort(languageIds);

		Set<String> availableLanguageIdsSet = SetUtil.fromArray(
			getAvailableLanguageIds());

		for (String languageId : availableLanguageIdsSet) {
			if (Arrays.binarySearch(languageIds, languageId) < 0) {
				Locale locale = LocaleUtil.fromLanguageId(languageId);

				availableLanguageIdKVPs.add(
					new KeyValuePair(
						languageId,
						locale.getDisplayName(_themeDisplay.getLocale())));
			}
		}

		availableLanguageIdKVPs = ListUtil.sort(
			availableLanguageIdKVPs, new KeyValuePairComparator(false, true));

		return availableLanguageIdKVPs;
	}

	public String[] getAvailableLanguageIds() {
		if (_availableLanguageIds != null) {
			return _availableLanguageIds;
		}

		_availableLanguageIds = LocaleUtil.toLanguageIds(
			LanguageUtil.getAvailableLocales(_themeDisplay.getSiteGroupId()));

		return _availableLanguageIds;
	}

	public List<KeyValuePair> getCurrentLanguageIdKVPs() {
		List<KeyValuePair> currentLanguageIdKVPs = new ArrayList<>();

		String[] languageIds = getLanguageIds();

		for (String languageId : languageIds) {
			Locale locale = LocaleUtil.fromLanguageId(languageId);

			currentLanguageIdKVPs.add(
				new KeyValuePair(
					languageId,
					locale.getDisplayName(_themeDisplay.getLocale())));
		}

		return currentLanguageIdKVPs;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String displayStyle =
			_siteNavigationLanguagePortletInstanceConfiguration.displayStyle();

		if (displayStyle != null) {
			_ddmTemplateKey = _portletDisplayTemplate.getDDMTemplateKey(
				displayStyle);
		}

		return _ddmTemplateKey;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_siteNavigationLanguagePortletInstanceConfiguration.
				displayStyleGroupId();

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
			_siteNavigationLanguagePortletInstanceConfiguration.languageIds());

		if (ArrayUtil.isEmpty(_languageIds)) {
			_languageIds = getAvailableLanguageIds();
		}
		else {
			List<String> filteredLanguageIds = new ArrayList<>();

			for (String languageId : _languageIds) {
				if (ArrayUtil.contains(getAvailableLanguageIds(), languageId)) {
					filteredLanguageIds.add(languageId);
				}
			}

			_languageIds = ArrayUtil.toStringArray(filteredLanguageIds);
		}

		return _languageIds;
	}

	public SiteNavigationLanguagePortletInstanceConfiguration
		getSiteNavigationLanguagePortletInstanceConfiguration() {

		return _siteNavigationLanguagePortletInstanceConfiguration;
	}

	private String[] _availableLanguageIds;
	private String _ddmTemplateKey;
	private long _displayStyleGroupId;
	private String[] _languageIds;
	private final PortletDisplayTemplate _portletDisplayTemplate;
	private final SiteNavigationLanguagePortletInstanceConfiguration
		_siteNavigationLanguagePortletInstanceConfiguration;
	private final ThemeDisplay _themeDisplay;

}