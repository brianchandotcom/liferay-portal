/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.xml.Document;

import java.io.Serializable;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

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
		return _localization.deserialize(jsonObject);
	}

	public static String[] getAvailableLanguageIds(Document document) {
		return _localization.getAvailableLanguageIds(document);
	}

	public static String[] getAvailableLanguageIds(String xml) {
		return _localization.getAvailableLanguageIds(xml);
	}

	public static Locale getDefaultImportLocale(
		String className, long classPK, Locale contentDefaultLocale,
		Locale[] contentAvailableLocales) {

		return _localization.getDefaultImportLocale(
			className, classPK, contentDefaultLocale, contentAvailableLocales);
	}

	public static Locale getDefaultImportLocale(
		String className, Serializable primaryKey, Locale contentDefaultLocale,
		Locale[] contentAvailableLocales) {

		return _localization.getDefaultImportLocale(
			className, primaryKey, contentDefaultLocale,
			contentAvailableLocales);
	}

	public static String getDefaultLanguageId(Document document) {
		return _localization.getDefaultLanguageId(document);
	}

	public static String getDefaultLanguageId(
		Document document, Locale defaultLocale) {

		return _localization.getDefaultLanguageId(document, defaultLocale);
	}

	public static String getDefaultLanguageId(String xml) {
		return _localization.getDefaultLanguageId(xml);
	}

	public static String getDefaultLanguageId(
		String xml, Locale defaultLocale) {

		return _localization.getDefaultLanguageId(xml, defaultLocale);
	}

	public static Localization getLocalization() {
		return _localization;
	}

	public static String getLocalization(
		Function<String, String> localizationFunction,
		String requestedLanguageId, String defaultLanguageId) {

		return _localization.getLocalization(
			localizationFunction, requestedLanguageId, defaultLanguageId);
	}

	public static String getLocalization(
		String xml, String requestedLanguageId) {

		return _localization.getLocalization(xml, requestedLanguageId);
	}

	public static String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault) {

		return _localization.getLocalization(
			xml, requestedLanguageId, useDefault);
	}

	public static String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault,
		String defaultValue) {

		return _localization.getLocalization(
			xml, requestedLanguageId, useDefault, defaultValue);
	}

	public static Map<Locale, String> getLocalizationMap(
		Collection<Locale> locales, Locale defaultLocale, String key) {

		return _localization.getLocalizationMap(locales, defaultLocale, key);
	}

	public static Map<Locale, String> getLocalizationMap(
		HttpServletRequest httpServletRequest, String parameter) {

		return _localization.getLocalizationMap(httpServletRequest, parameter);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletPreferences preferences, String preferenceName) {

		return _localization.getLocalizationMap(preferences, preferenceName);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletPreferences preferences, String preferenceName,
		String propertyName) {

		return _localization.getLocalizationMap(
			preferences, preferenceName, propertyName);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletPreferences preferences, String preferenceName,
		String propertyName, String defaultPropertyValue,
		ClassLoader classLoader) {

		return _localization.getLocalizationMap(
			preferences, preferenceName, propertyName, defaultPropertyValue,
			classLoader);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter) {

		return _localization.getLocalizationMap(portletRequest, parameter);
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter,
		Map<Locale, String> defaultValues) {

		return _localization.getLocalizationMap(
			portletRequest, parameter, defaultValues);
	}

	public static Map<Locale, String> getLocalizationMap(String xml) {
		return _localization.getLocalizationMap(xml);
	}

	public static Map<Locale, String> getLocalizationMap(
		String xml, boolean useDefault) {

		return _localization.getLocalizationMap(xml, useDefault);
	}

	public static Map<Locale, String> getLocalizationMap(
		String bundleName, ClassLoader classLoader, String key,
		boolean includeBetaLocales) {

		return _localization.getLocalizationMap(
			bundleName, classLoader, key, includeBetaLocales);
	}

	public static Map<Locale, String> getLocalizationMap(
		String[] languageIds, String[] values) {

		return _localization.getLocalizationMap(languageIds, values);
	}

	public static String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter) {

		return _localization.getLocalizationXmlFromPreferences(
			preferences, portletRequest, parameter);
	}

	public static String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter, String defaultValue) {

		return _localization.getLocalizationXmlFromPreferences(
			preferences, portletRequest, parameter, defaultValue);
	}

	public static String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter, String prefix, String defaultValue) {

		return _localization.getLocalizationXmlFromPreferences(
			preferences, portletRequest, parameter, prefix, defaultValue);
	}

	public static String getLocalizedName(String name, String languageId) {
		return _localization.getLocalizedName(name, languageId);
	}

	public static Map<Locale, String> getMap(
		LocalizedValuesMap localizedValuesMap) {

		return _localization.getMap(localizedValuesMap);
	}

	public static String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId) {

		return _localization.getPreferencesValue(preferences, key, languageId);
	}

	public static String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		return _localization.getPreferencesValue(
			preferences, key, languageId, useDefault);
	}

	public static String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId) {

		return _localization.getPreferencesValues(preferences, key, languageId);
	}

	public static String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		return _localization.getPreferencesValues(
			preferences, key, languageId, useDefault);
	}

	public static String getSettingsValue(
		Settings settings, String key, String languageId) {

		return _localization.getSettingsValue(settings, key, languageId);
	}

	public static String getSettingsValue(
		Settings settings, String key, String languageId, boolean useDefault) {

		return _localization.getSettingsValue(
			settings, key, languageId, useDefault);
	}

	public static String[] getSettingsValues(
		Settings settings, String key, String languageId) {

		return _localization.getSettingsValues(settings, key, languageId);
	}

	public static String[] getSettingsValues(
		Settings settings, String key, String languageId, boolean useDefault) {

		return _localization.getSettingsValues(
			settings, key, languageId, useDefault);
	}

	public static String getXml(
		LocalizedValuesMap localizedValuesMap, String key) {

		return _localization.getXml(localizedValuesMap, key);
	}

	public static String getXml(
		Map<String, String> map, String defaultLanguageId, String key) {

		return _localization.getXml(map, defaultLanguageId, key);
	}

	public static String getXml(
		Map<String, String> map, String defaultLanguageId, String key,
		boolean cdata) {

		return _localization.getXml(map, defaultLanguageId, key, cdata);
	}

	public static Map<Locale, String> populateLocalizationMap(
		Map<Locale, String> localizationMap, String defaultLanguageId,
		long groupId) {

		return _localization.populateLocalizationMap(
			localizationMap, defaultLanguageId, groupId);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId) {

		return _localization.removeLocalization(xml, key, requestedLanguageId);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata) {

		return _localization.removeLocalization(
			xml, key, requestedLanguageId, cdata);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata,
		boolean localized) {

		return _localization.removeLocalization(
			xml, key, requestedLanguageId, cdata, localized);
	}

	public static void setLocalizedPreferencesValues(
			PortletRequest portletRequest, PortletPreferences preferences,
			String parameter)
		throws Exception {

		_localization.setLocalizedPreferencesValues(
			portletRequest, preferences, parameter);
	}

	public static void setPreferencesValue(
			PortletPreferences preferences, String key, String languageId,
			String value)
		throws Exception {

		_localization.setPreferencesValue(preferences, key, languageId, value);
	}

	public static void setPreferencesValues(
			PortletPreferences preferences, String key, String languageId,
			String[] values)
		throws Exception {

		_localization.setPreferencesValues(
			preferences, key, languageId, values);
	}

	public static String updateLocalization(
		Map<Locale, String> localizationMap, String xml, String key,
		String defaultLanguageId) {

		return _localization.updateLocalization(
			localizationMap, xml, key, defaultLanguageId);
	}

	public static String updateLocalization(
		String xml, String key, String value) {

		return _localization.updateLocalization(xml, key, value);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId) {

		return _localization.updateLocalization(
			xml, key, value, requestedLanguageId);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId) {

		return _localization.updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata) {

		return _localization.updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, cdata);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata, boolean localized) {

		return _localization.updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, cdata,
			localized);
	}

	public void setLocalization(Localization localization) {
		_localization = localization;
	}

	private static Localization _localization;

}