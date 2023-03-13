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

package com.liferay.site.navigation.menu.item.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuItemUtil {

	public static UnicodeProperties getSiteNavigationMenuItemProperties(
			PortletRequest portletRequest, String prefix)
		throws PortalException {

		Map<String, String[]> parameterMap = portletRequest.getParameterMap();

		String[] localizableParameters = {};

		for (String key : parameterMap.keySet()) {
			if (key.startsWith(prefix)) {
				continue;
			}

			localizableParameters = ArrayUtil.append(
				localizableParameters, key);
		}

		UnicodeProperties unicodeProperties = PropertiesParamUtil.getProperties(
			portletRequest, prefix);

		for (String localizableParameter : localizableParameters) {
			Map<Locale, String> map = LocalizationUtil.getLocalizationMap(
				portletRequest, localizableParameter);

			if (MapUtil.isEmpty(map)) {
				continue;
			}

			for (Map.Entry<Locale, String> entry : map.entrySet()) {
				String value = entry.getValue();

				if (Validator.isNull(value)) {
					continue;
				}

				String key =
					localizableParameter + "_" +
						LocaleUtil.toLanguageId(entry.getKey());

				unicodeProperties.setProperty(key, value);
			}
		}

		if (!unicodeProperties.containsKey(Field.DEFAULT_LANGUAGE_ID)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			unicodeProperties.setProperty(
				Field.DEFAULT_LANGUAGE_ID,
				LocaleUtil.toLanguageId(
					PortalUtil.getSiteDefaultLocale(
						themeDisplay.getScopeGroup())));
		}

		return unicodeProperties;
	}

	public static String getSiteNavigationMenuItemXML(
			SiteNavigationMenuItem siteNavigationMenuItem, String name)
		throws PortalException {

		if (siteNavigationMenuItem == null) {
			return StringPool.BLANK;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			siteNavigationMenuItem.getGroupId());

		if (Objects.equals(
				siteNavigationMenuItem.getType(),
				SiteNavigationMenuItemTypeConstants.LAYOUT)) {

			String layoutUuid = typeSettingsUnicodeProperties.get("layoutUuid");

			boolean privateLayout = GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.get("privateLayout"));

			Layout layout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
				layoutUuid, siteNavigationMenuItem.getGroupId(), privateLayout);

			Map<Locale, String> nameMap = layout.getNameMap();

			for (Map.Entry<Locale, String> nameEntry : nameMap.entrySet()) {
				String languageId = LocaleUtil.toLanguageId(nameEntry.getKey());

				if (Validator.isNull(
						typeSettingsUnicodeProperties.getProperty(
							"name_" + languageId))) {

					typeSettingsUnicodeProperties.setProperty(
						"name_" + languageId, nameEntry.getValue());
				}
			}
		}

		Map<String, String> map = new HashMap<>();

		for (Locale locale : availableLocales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String value = typeSettingsUnicodeProperties.getProperty(
				name + "_" + languageId);

			if (Validator.isNotNull(value)) {
				map.put(languageId, value);
			}
		}

		if (MapUtil.isEmpty(map)) {
			String defaultLanguageId =
				typeSettingsUnicodeProperties.getProperty(
					Field.DEFAULT_LANGUAGE_ID,
					LocaleUtil.toLanguageId(
						PortalUtil.getSiteDefaultLocale(
							siteNavigationMenuItem.getGroupId())));

			map.put(
				defaultLanguageId,
				GetterUtil.getString(
					typeSettingsUnicodeProperties.getProperty(name)));
		}

		return LocalizationUtil.getXml(
			map, LocaleUtil.toLanguageId(LocaleUtil.getMostRelevantLocale()),
			name);
	}

}