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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.xml.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alexander Chow
 * @author Jorge Ferrer
 * @author Mauro Mariuzzo
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 * @see    Localization
 */
public class LocalizationUtil {

	public static Object deserialize(JSONObject jsonObject) {
		return getLocalization().deserialize(jsonObject);
	}

	public static String[] getAvailableLanguageIds(Document document) {
		return getLocalization().getAvailableLanguageIds(document);
	}

	public static String[] getAvailableLanguageIds(String xml) {
		return getLocalization().getAvailableLanguageIds(xml);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getAvailableLanguageIds(String)}
	 */
	@Deprecated
	public static String[] getAvailableLocales(String xml) {
		return getAvailableLanguageIds(xml);
	}

	public static Locale getDefaultImportLocale(
		String className, long classPK, Locale contentDefaultLocale,
		Locale[] contentAvailableLocales) {

		return getLocalization().getDefaultImportLocale(
			className, classPK, contentDefaultLocale, contentAvailableLocales);
	}

	public static String getDefaultLanguageId(Document document) {
		return getLocalization().getDefaultLanguageId(document);
	}

	public static String getDefaultLanguageId(
		Document document, Locale defaultLocale) {

		return getLocalization().getDefaultLanguageId(document, defaultLocale);
	}

	public static String getDefaultLanguageId(String xml) {
		return getLocalization().getDefaultLanguageId(xml);
	}

	public static String getDefaultLanguageId(
		String xml, Locale defaultLocale) {

		return getLocalization().getDefaultLanguageId(xml, defaultLocale);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getDefaultLanguageId(String)}
	 */
	@Deprecated
	public static String getDefaultLocale(String xml) {
		return getDefaultLanguageId(xml);
	}

	public static Localization getLocalization() {
		PortalRuntimePermission.checkGetBeanProperty(LocalizationUtil.class);

		return _localization;
	}

	public static String getLocalization(
		String xml, String requestedLanguageId) {

		return getLocalization().getLocalization(xml, requestedLanguageId);
	}

	public static String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault) {

		return getLocalization().getLocalization(
			xml, requestedLanguageId, useDefault);
	}

	public static String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault,
		String defaultValue) {

		return getLocalization().getLocalization(
			xml, requestedLanguageId, useDefault, defaultValue);
	}

	public static Map<Locale, String> getLocalizationMap(
		Collection<Locale> locales, String key) {

		return getLocalization().getLocalizationMap(locales, key);
	}

	public static Map<Locale, String> getLocalizationMap(
		HttpServletRequest request, String parameter) {

		return getLocalization().getLocalizationMap(request, parameter);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletPreferences preferences, String preferenceName) {

		return getLocalization().getLocalizationMap(
			preferences, preferenceName);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletPreferences preferences, String preferenceName,
		String propertyName) {

		return getLocalization().getLocalizationMap(
			preferences, preferenceName, propertyName);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletPreferences preferences, String preferenceName,
		String propertyName, String defaultPropertyValue,
		ClassLoader classLoader) {

		return getLocalization().getLocalizationMap(
			preferences, preferenceName, propertyName, defaultPropertyValue,
			classLoader);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter) {

		return getLocalization().getLocalizationMap(portletRequest, parameter);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter,
		Map<Locale, String> defaultValues) {

		return getLocalization().getLocalizationMap(
			portletRequest, parameter, defaultValues);
	}

	public static Map<Locale, String> getLocalizationMap(String xml) {
		return getLocalization().getLocalizationMap(xml);
	}

	public static Map<Locale, String> getLocalizationMap(
		String xml, boolean useDefault) {

		return getLocalization().getLocalizationMap(xml, useDefault);
	}

	public static Map<Locale, String> getLocalizationMap(
		String bundleName, ClassLoader classLoader, String key,
		boolean includeBetaLocales) {

		return getLocalization().getLocalizationMap(
			bundleName, classLoader, key, includeBetaLocales);
	}

	public static Map<Locale, String> getLocalizationMap(
		String[] languageIds, String[] values) {

		return getLocalization().getLocalizationMap(languageIds, values);
	}

	public static String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter) {

		return getLocalization().getLocalizationXmlFromPreferences(
			preferences, portletRequest, parameter);
	}

	public static String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter, String defaultValue) {

		return getLocalization().getLocalizationXmlFromPreferences(
			preferences, portletRequest, parameter, defaultValue);
	}

	public static String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter, String prefix, String defaultValue) {

		return getLocalization().getLocalizationXmlFromPreferences(
			preferences, portletRequest, parameter, prefix, defaultValue);
	}

	public static String getLocalizedName(String name, String languageId) {
		return getLocalization().getLocalizedName(name, languageId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getLocalizationMap}
	 */
	@Deprecated
	public static Map<Locale, String> getLocalizedParameter(
		PortletRequest portletRequest, String parameter) {

		return getLocalization().getLocalizedParameter(
			portletRequest, parameter);
	}

	public static Map<Locale, String> getMap(
		LocalizedValuesMap localizedValuesMap) {

		return getLocalization().getMap(localizedValuesMap);
	}

	public static List<Locale> getModifiedLocales(
		Map<Locale, String> oldMap, Map<Locale, String> newMap) {

		if ((newMap == null) || newMap.isEmpty()) {
			return Collections.emptyList();
		}

		List<Locale> modifiedLocales = new ArrayList<>();

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String oldValue = oldMap.get(locale);
			String newValue = newMap.get(locale);

			if (!oldValue.equals(newValue)) {
				modifiedLocales.add(locale);
			}
		}

		return modifiedLocales;
	}

	/**
	 * @deprecated As of 7.0.0 replaced by {@link #getLocalizedName(String,
	 *             String)}
	 */
	@Deprecated
	public static String getPreferencesKey(String key, String languageId) {
		return getLocalization().getPreferencesKey(key, languageId);
	}

	public static String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId) {

		return getLocalization().getPreferencesValue(
			preferences, key, languageId);
	}

	public static String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		return getLocalization().getPreferencesValue(
			preferences, key, languageId, useDefault);
	}

	public static String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId) {

		return getLocalization().getPreferencesValues(
			preferences, key, languageId);
	}

	public static String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		return getLocalization().getPreferencesValues(
			preferences, key, languageId, useDefault);
	}

	public static String getSettingsValue(
		Settings settings, String key, String languageId) {

		return getLocalization().getSettingsValue(settings, key, languageId);
	}

	public static String getSettingsValue(
		Settings settings, String key, String languageId, boolean useDefault) {

		return getLocalization().getSettingsValue(
			settings, key, languageId, useDefault);
	}

	public static String[] getSettingsValues(
		Settings settings, String key, String languageId) {

		return getLocalization().getSettingsValues(settings, key, languageId);
	}

	public static String[] getSettingsValues(
		Settings settings, String key, String languageId, boolean useDefault) {

		return getLocalization().getSettingsValues(
			settings, key, languageId, useDefault);
	}

	public static String getXml(
		LocalizedValuesMap localizedValuesMap, String key) {

		return getLocalization().getXml(localizedValuesMap, key);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId) {

		return getLocalization().removeLocalization(
			xml, key, requestedLanguageId);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata) {

		return getLocalization().removeLocalization(
			xml, key, requestedLanguageId, cdata);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata,
		boolean localized) {

		return getLocalization().removeLocalization(
			xml, key, requestedLanguageId, cdata, localized);
	}

	public static void setLocalizedPreferencesValues(
			PortletRequest portletRequest, PortletPreferences preferences,
			String parameter)
		throws Exception {

		getLocalization().setLocalizedPreferencesValues(
			portletRequest, preferences, parameter);
	}

	public static void setPreferencesValue(
			PortletPreferences preferences, String key, String languageId,
			String value)
		throws Exception {

		getLocalization().setPreferencesValue(
			preferences, key, languageId, value);
	}

	public static void setPreferencesValues(
			PortletPreferences preferences, String key, String languageId,
			String[] values)
		throws Exception {

		getLocalization().setPreferencesValues(
			preferences, key, languageId, values);
	}

	public static String updateLocalization(
		Map<Locale, String> localizationMap, String xml, String key,
		String defaultLanguageId) {

		return getLocalization().updateLocalization(
			localizationMap, xml, key, defaultLanguageId);
	}

	public static String updateLocalization(
		String xml, String key, String value) {

		return getLocalization().updateLocalization(xml, key, value);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId) {

		return getLocalization().updateLocalization(
			xml, key, value, requestedLanguageId);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId) {

		return getLocalization().updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata) {

		return getLocalization().updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, cdata);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata, boolean localized) {

		return getLocalization().updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, cdata,
			localized);
	}

	public void setLocalization(Localization localization) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_localization = localization;
	}

	private static Localization _localization;

}