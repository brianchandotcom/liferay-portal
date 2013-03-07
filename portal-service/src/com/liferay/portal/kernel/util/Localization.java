/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Stores and retrieves localized strings from XML, and provides utility methods
 * for updating localizations from JSON, portlet requests, and maps. Used for
 * adding localization to strings, most often for model properties.
 *
 * <p>
 * Localized values are cached in this class rather than in the value object
 * since value objects get flushed from cache fairly quickly. Though lookups
 * performed on a key based on an XML file are slower than lookups done at the
 * value object level in general, the value object will get flushed at a rate
 * which works against the performance gain. The cache is a soft hash map which
 * prevents memory leaks within the system while enabling the cache to live
 * longer than in a weak hash map.
 * </p>
 *
 * @author Alexander Chow
 * @author Jorge Ferrer
 * @author Mauro Mariuzzo
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public interface Localization {

	/**
	 * Deserializes the JSON object into a map of locales and localized strings.
	 *
	 * @param  jsonObject the JSON object
	 * @return the locales and localized strings
	 */
	public Object deserialize(JSONObject jsonObject);

	/**
	 * Returns the available locales from the localizations XML.
	 *
	 * @param  xml the localizations XML
	 * @return the language IDs of the available locales
	 */
	public String[] getAvailableLocales(String xml);

	/**
	 * Returns a valid default locale for importing a localized entity.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary keys of the entity
	 * @param  contentDefaultLocale the default Locale of the entity
	 * @param  contentAvailableLocales the available locales of the entity
	 * @return the valid locale
	 */
	public Locale getDefaultImportLocale(
		String className, long classPK, Locale contentDefaultLocale,
		Locale[] contentAvailableLocales);

	/**
	 * Returns the default locale from the localizations XML.
	 *
	 * @param  xml the localizations XML
	 * @return the language ID of the default locale, or the system default
	 *         locale if the default locale cannot be retrieved from the XML
	 */
	public String getDefaultLocale(String xml);

	/**
	 * Returns the localized string from the localizations XML in the language.
	 * Uses the default language if no localization exists for the requested
	 * language.
	 *
	 * @param  xml the localizations XML
	 * @param  requestedLanguageId the ID of the language
	 * @return the localized string
	 */
	public String getLocalization(String xml, String requestedLanguageId);

	/**
	 * Returns the localized string from the localizations XML in the language,
	 * optionally using the default language if the no localization exists for
	 * the requested language.
	 *
	 * @param  xml the localizations XML
	 * @param  requestedLanguageId the ID of the language
	 * @param  useDefault whether to use the default language if no localization
	 *         exists for the requested language
	 * @return the localized string. If <code>useDefault</code> is
	 *         <code>false</code> and no localization exists for the requested
	 *         language, an empty string will be returned.
	 */
	public String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault);

	/**
	 * Returns a map of locales and localized strings for the parameter in the
	 * request.
	 *
	 * @param  request the request
	 * @param  parameter the prefix of the parameters containing the localized
	 *         strings. Each localization will be loaded from a parameter with
	 *         this prefix, followed by an underscore, and the language ID.
	 * @return the locales and localized strings
	 */
	public Map<Locale, String> getLocalizationMap(
		HttpServletRequest request, String parameter);

	/**
	 * Returns a map of locales and localized strings for the parameter in the
	 * preferences container.
	 *
	 * @param  preferences the preferences container
	 * @param  parameter the prefix of the parameters containing the localized
	 *         strings. Each localization will be loaded from a parameter with
	 *         this prefix, followed by an underscore, and the language ID.
	 * @return the locales and localized strings
	 */
	public Map<Locale, String> getLocalizationMap(
		PortletPreferences preferences, String parameter);

	/**
	 * Returns a map of locales and localized strings for the parameter in the
	 * portlet request.
	 *
	 * @param  portletRequest the portlet request
	 * @param  parameter the prefix of the parameters containing the localized
	 *         strings. Each localization will be loaded from a parameter with
	 *         this prefix, followed by an underscore, and the language ID.
	 * @return the locales and localized strings
	 */
	public Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter);

	/**
	 * Returns a map of locales and localized strings from the localizations
	 * XML.
	 *
	 * @param  xml the localizations XML
	 * @return the locales and localized strings
	 */
	public Map<Locale, String> getLocalizationMap(String xml);

	public Map<Locale, String> getLocalizationMap(
		String bundleName, ClassLoader classLoader, String key,
		boolean includeBetaLocales);

	/**
	 * Returns a map of locales and localized strings for the given languageIds
	 * and values.
	 *
	 * @param  languageIds the languageIds of the localized Strings
	 * @param  values the localized strings for the different languageId
	 * @return the map of locales and values for the given parameters
	 */
	public Map<Locale, String> getLocalizationMap(
		String[] languageIds, String[] values);

	/**
	 * Returns the localizations XML for the parameter in the portlet request,
	 * attempting to get data from the preferences container when it is not
	 * available in the portlet request.
	 *
	 * @param  preferences the preferences container
	 * @param  portletRequest the portlet request
	 * @param  parameter the prefix of the parameters containing the localized
	 *         strings. Each localization will be loaded from a parameter with
	 *         this prefix, followed by an underscore, and the language ID.
	 * @return the locales and localized strings
	 */
	public String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getLocalizationMap(PortletRequest, String)}
	 */
	public Map<Locale, String> getLocalizedParameter(
		PortletRequest portletRequest, String parameter);

	/**
	 * Returns the localized preferences key in the language. Generally this is
	 * just the preferences key, followed by an underscore, and the language ID.
	 *
	 * @param  key the preferences key
	 * @param  languageId the ID of the language
	 * @return the localized preferences key
	 */
	public String getPreferencesKey(String key, String languageId);

	/**
	 * Returns the localized preferences value for the key in the language. Uses
	 * the default language if no localization exists for the requested
	 * language.
	 *
	 * @param  preferences the preferences container
	 * @param  key the preferences key
	 * @param  languageId the ID of the language
	 * @return the localized preferences value
	 */
	public String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId);

	/**
	 * Returns the localized preferences value for the key in the language,
	 * optionally using the default language if the no localization exists for
	 * the requested language.
	 *
	 * @param  preferences the preferences container
	 * @param  key the preferences key
	 * @param  languageId the ID of the language
	 * @param  useDefault whether to use the default language if no localization
	 *         exists for the requested language
	 * @return the localized preferences value. If <code>useDefault</code> is
	 *         <code>false</code> and no localization exists for the requested
	 *         language, an empty string will be returned.
	 */
	public String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault);

	/**
	 * Returns the localized preferences values for the key in the language.
	 * Uses the default language if no localization exists for the requested
	 * language.
	 *
	 * @param  preferences the preferences container
	 * @param  key the preferences key
	 * @param  languageId the ID of the language
	 * @return the localized preferences values
	 */
	public String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId);

	/**
	 * Returns the localized preferences values for the key in the language,
	 * optionally using the default language if the no localization exists for
	 * the requested language.
	 *
	 * @param  preferences the preferences container
	 * @param  key the preferences key
	 * @param  languageId the ID of the language
	 * @param  useDefault whether to use the default language if no localization
	 *         exists for the requested language
	 * @return the localized preferences values. If <code>useDefault</code> is
	 *         <code>false</code> and no localization exists for the requested
	 *         language, an empty array will be returned.
	 */
	public String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault);

	/**
	 * Removes the localization for the language from the localizations XML.
	 * Stores the localized strings as characters in the XML.
	 *
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  requestedLanguageId the ID of the language
	 * @return the localizations XML with the language removed
	 */
	public String removeLocalization(
		String xml, String key, String requestedLanguageId);

	/**
	 * Removes the localization for the language from the localizations XML,
	 * optionally storing the localized strings as CDATA in the XML.
	 *
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  requestedLanguageId the ID of the language
	 * @param  cdata whether to store localized strings as CDATA in the XML
	 * @return the localizations XML with the language removed
	 */
	public String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata);

	/**
	 * Removes the localization for the language from the localizations XML,
	 * optionally storing the localized strings as CDATA in the XML.
	 *
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  requestedLanguageId the ID of the language
	 * @param  cdata whether to store localized strings as CDATA in the XML
	 * @param  localized whether there is a localized field
	 * @return the localizations XML with the language removed
	 */
	public String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata,
		boolean localized);

	/**
	 * Sets the localized preferences values for the parameter in the portlet
	 * request.
	 *
	 * @param  portletRequest the portlet request
	 * @param  preferences the preferences container
	 * @param  parameter the prefix of the parameters containing the localized
	 *         strings. Each localization will be loaded from a parameter with
	 *         this prefix, followed by an underscore, and the language ID.
	 * @throws Exception if an exception occurred
	 */
	public void setLocalizedPreferencesValues(
			PortletRequest portletRequest, PortletPreferences preferences,
			String parameter)
		throws Exception;

	/**
	 * Sets the localized preferences value for the key in the language.
	 *
	 * @param  preferences the preferences container
	 * @param  key the preferences key
	 * @param  languageId the ID of the language
	 * @param  value the localized value
	 * @throws Exception if an exception occurred
	 */
	public void setPreferencesValue(
			PortletPreferences preferences, String key, String languageId,
			String value)
		throws Exception;

	/**
	 * Sets the localized preferences values for the key in the language.
	 *
	 * @param  preferences the preferences container
	 * @param  key the preferences key
	 * @param  languageId the ID of the language
	 * @param  values the localized values
	 * @throws Exception if an exception occurred
	 */
	public void setPreferencesValues(
			PortletPreferences preferences, String key, String languageId,
			String[] values)
		throws Exception;

	/**
	 * Updates the localized string for all the available languages in the
	 * localizations XML for the map of locales and localized strings and
	 * changes the default language. Stores the localized strings as characters
	 * in the XML.
	 *
	 * @param  localizationMap the locales and localized strings
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  defaultLanguageId the ID of the default language
	 * @return the updated localizations XML
	 */
	public String updateLocalization(
		Map<Locale, String> localizationMap, String xml, String key,
		String defaultLanguageId);

	/**
	 * Updates the localized string for the system default language in the
	 * localizations XML. Stores the localized strings as characters in the XML.
	 *
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  value the localized string
	 * @return the updated localizations XML
	 */
	public String updateLocalization(String xml, String key, String value);

	/**
	 * Updates the localized string for the language in the localizations XML.
	 * Stores the localized strings as characters in the XML.
	 *
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  value the localized string
	 * @param  requestedLanguageId the ID of the language
	 * @return the updated localizations XML
	 */
	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId);

	/**
	 * Updates the localized string for the language in the localizations XML
	 * and changes the default language. Stores the localized strings as
	 * characters in the XML.
	 *
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  value the localized string
	 * @param  requestedLanguageId the ID of the language
	 * @param  defaultLanguageId the ID of the default language
	 * @return the updated localizations XML
	 */
	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId);

	/**
	 * Updates the localized string for the language in the localizations XML
	 * and changes the default language, optionally storing the localized
	 * strings as CDATA in the XML.
	 *
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  value the localized string
	 * @param  requestedLanguageId the ID of the language
	 * @param  defaultLanguageId the ID of the default language
	 * @param  cdata whether to store localized strings as CDATA in the XML
	 * @return the updated localizations XML
	 */
	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata);

	/**
	 * Updates the localized string for the language in the localizations XML
	 * and changes the default language, optionally storing the localized
	 * strings as CDATA in the XML.
	 *
	 * @param  xml the localizations XML
	 * @param  key the name of the localized string, such as &quot;Title&quot;
	 * @param  value the localized string
	 * @param  requestedLanguageId the ID of the language
	 * @param  defaultLanguageId the ID of the default language
	 * @param  cdata whether to store localized strings as CDATA in the XML
	 * @param  localized whether there is a localized field
	 * @return the updated localizations XML
	 */
	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata, boolean localized);

}