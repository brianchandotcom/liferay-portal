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

package com.liferay.portal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.collections.map.ReferenceMap;

/**
 * @author Alexander Chow
 * @author Jorge Ferrer
 * @author Mauro Mariuzzo
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 * @author Connor McKay
 */
public class LocalizationImpl implements Localization {

	public Object deserialize(JSONObject jsonObject) {
		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String value = jsonObject.getString(languageId);

			if (Validator.isNotNull(value)) {
				map.put(locale, value);
			}
		}

		return map;
	}

	public String[] getAvailableLocales(String xml) {
		String attributeValue = _getRootAttribute(
			xml, _AVAILABLE_LOCALES, StringPool.BLANK);

		return StringUtil.split(attributeValue);
	}

	public Locale getDefaultImportLocale(
		String className, long classPK, Locale contentDefaultLocale,
		Locale[] contentAvailableLocales) {

		Locale[] availableLocales = LanguageUtil.getAvailableLocales();

		if (ArrayUtil.contains(availableLocales, contentDefaultLocale)) {
			return contentDefaultLocale;
		}

		Locale defaultLocale = LocaleUtil.getDefault();

		if (ArrayUtil.contains(contentAvailableLocales, defaultLocale)) {
			return defaultLocale;
		}

		for (Locale contentAvailableLocale : contentAvailableLocales) {
			if (ArrayUtil.contains(availableLocales, contentAvailableLocale)) {
				return contentAvailableLocale;
			}
		}

		if (_log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(9);

			sb.append("Language ");
			sb.append(LocaleUtil.toLanguageId(contentDefaultLocale));
			sb.append(" is missing for ");
			sb.append(className);
			sb.append(" with primary key ");
			sb.append(classPK);
			sb.append(". Setting default language to ");
			sb.append(LocaleUtil.toLanguageId(defaultLocale));
			sb.append(".");

			_log.warn(sb.toString());
		}

		return defaultLocale;
	}

	public String getDefaultLocale(String xml) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return _getRootAttribute(xml, _DEFAULT_LOCALE, defaultLanguageId);
	}

	public String getLocalization(String xml, String requestedLanguageId) {
		return getLocalization(xml, requestedLanguageId, true);
	}

	public String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault) {

		String systemDefaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (!Validator.isXml(xml)) {
			if (useDefault ||
				requestedLanguageId.equals(systemDefaultLanguageId)) {

				return xml;
			}
			else {
				return StringPool.BLANK;
			}
		}

		String value = _getCachedValue(xml, requestedLanguageId, useDefault);

		if (value != null) {
			return value;
		}
		else {
			value = StringPool.BLANK;
		}

		String priorityLanguageId = null;

		Locale requestedLocale = LocaleUtil.fromLanguageId(requestedLanguageId);

		if (useDefault &&
			LanguageUtil.isDuplicateLanguageCode(
				requestedLocale.getLanguage())) {

			Locale priorityLocale = LanguageUtil.getLocale(
				requestedLocale.getLanguage());

			if (!requestedLanguageId.equals(priorityLanguageId)) {
				priorityLanguageId = LocaleUtil.toLanguageId(priorityLocale);
			}
		}

		XMLStreamReader xmlStreamReader = null;

		ClassLoader portalClassLoader =
			PACLClassLoaderUtil.getPortalClassLoader();

		ClassLoader contextClassLoader =
			PACLClassLoaderUtil.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				PACLClassLoaderUtil.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			String defaultLanguageId = StringPool.BLANK;

			// Skip root node

			if (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();

				defaultLanguageId = xmlStreamReader.getAttributeValue(
					null, _DEFAULT_LOCALE);

				if (Validator.isNull(defaultLanguageId)) {
					defaultLanguageId = systemDefaultLanguageId;
				}
			}

			// Find specified language and/or default language

			String defaultValue = StringPool.BLANK;
			String priorityValue = StringPool.BLANK;

			while (xmlStreamReader.hasNext()) {
				int event = xmlStreamReader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					String languageId = xmlStreamReader.getAttributeValue(
						null, _LANGUAGE_ID);

					if (Validator.isNull(languageId)) {
						languageId = defaultLanguageId;
					}

					if (languageId.equals(defaultLanguageId) ||
						languageId.equals(priorityLanguageId) ||
						languageId.equals(requestedLanguageId)) {

						String text = xmlStreamReader.getElementText();

						if (languageId.equals(defaultLanguageId)) {
							defaultValue = text;
						}

						if (languageId.equals(priorityLanguageId)) {
							priorityValue = text;
						}

						if (languageId.equals(requestedLanguageId)) {
							value = text;
						}

						if (Validator.isNotNull(value)) {
							break;
						}
					}
				}
				else if (event == XMLStreamConstants.END_DOCUMENT) {
					break;
				}
			}

			if (useDefault && Validator.isNotNull(priorityLanguageId) &&
				Validator.isNull(value) && Validator.isNotNull(priorityValue)) {

				value = priorityValue;
			}

			if (useDefault && Validator.isNull(value)) {
				value = defaultValue;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				PACLClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}

			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		_setCachedValue(xml, requestedLanguageId, useDefault, value);

		return value;
	}

	public Map<Locale, String> getLocalizationMap(
		PortletPreferences preferences, String parameter) {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String localeParameter = parameter.concat(
				StringPool.UNDERLINE).concat(languageId);

			map.put(
				locale,
				preferences.getValue(localeParameter, StringPool.BLANK));
		}

		return map;
	}

	public Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter) {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String localeParameter = parameter.concat(
				StringPool.UNDERLINE).concat(languageId);

			map.put(
				locale, ParamUtil.getString(portletRequest, localeParameter));
		}

		return map;
	}

	public Map<Locale, String> getLocalizationMap(String xml) {
		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			map.put(locale, getLocalization(xml, languageId, false));
		}

		return map;
	}

	public Map<Locale, String> getLocalizationMap(
		String bundleName, ClassLoader classLoader, String key,
		boolean includeBetaLocales) {

		if (key == null) {
			return null;
		}

		Map<Locale, String> map = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		String defaultValue = _getLocalization(
			bundleName, defaultLocale, classLoader, key, key);

		map.put(defaultLocale, defaultValue);

		Locale[] locales = null;

		if (includeBetaLocales) {
			locales = LanguageUtil.getAvailableLocales();
		}
		else {
			locales = LanguageUtil.getSupportedLocales();
		}

		for (Locale locale : locales) {
			if (locale.equals(defaultLocale)) {
				continue;
			}

			String value = _getLocalization(
				bundleName, locale, classLoader, key, null);

			if (Validator.isNotNull(value) && !value.equals(defaultValue)) {
				map.put(locale, value);
			}
		}

		return map;
	}

	public Map<Locale, String> getLocalizationMap(
		String[] languageIds, String[] values) {

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (int i = 0; i < values.length; i++) {
			Locale locale = LocaleUtil.fromLanguageId(languageIds[i]);

			map.put(locale, values[i]);
		}

		return map;
	}

	public String getLocalizationXmlFromPreferences(
		PortletPreferences preferences, PortletRequest portletRequest,
		String parameter) {

		String xml = StringPool.BLANK;

		Locale[] locales = LanguageUtil.getAvailableLocales();
		Locale defaultLocale = LocaleUtil.getDefault();
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String localParameter =
				parameter + StringPool.UNDERLINE + languageId;

			String value = PrefsParamUtil.getString(
				preferences, portletRequest, localParameter);

			if (Validator.isNotNull(value)) {
				xml = updateLocalization(xml, parameter, value, languageId);
			}
		}

		if (Validator.isNull(getLocalization(xml, defaultLanguageId))) {
			String oldValue = PrefsParamUtil.getString(
				preferences, portletRequest, parameter);

			if (Validator.isNotNull(oldValue)) {
				xml = updateLocalization(xml, parameter, oldValue);
			}
		}

		return xml;
	}

	public Map<Locale, String> getLocalizedParameter(
		PortletRequest portletRequest, String parameter) {

		return getLocalizationMap(portletRequest, parameter);
	}

	public String getPreferencesKey(String key, String languageId) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (!languageId.equals(defaultLanguageId)) {
			key += StringPool.UNDERLINE + languageId;
		}

		return key;
	}

	public String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId) {

		return getPreferencesValue(preferences, key, languageId, true);
	}

	public String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		String localizedKey = getPreferencesKey(key, languageId);

		String value = preferences.getValue(localizedKey, StringPool.BLANK);

		if (useDefault && Validator.isNull(value)) {
			value = preferences.getValue(key, StringPool.BLANK);
		}

		return value;
	}

	public String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId) {

		return getPreferencesValues(preferences, key, languageId, true);
	}

	public String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		String localizedKey = getPreferencesKey(key, languageId);

		String[] values = preferences.getValues(localizedKey, new String[0]);

		if (useDefault && Validator.isNull(values)) {
			values = preferences.getValues(key, new String[0]);
		}

		return values;
	}

	public String removeLocalization(
		String xml, String key, String requestedLanguageId) {

		return removeLocalization(xml, key, requestedLanguageId, false);
	}

	public String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata) {

		return removeLocalization(xml, key, requestedLanguageId, cdata, true);
	}

	public String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata,
		boolean localized) {

		if (Validator.isNull(xml)) {
			return StringPool.BLANK;
		}

		if (!Validator.isXml(xml)) {
			return xml;
		}

		xml = _sanitizeXML(xml);

		String systemDefaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		XMLStreamReader xmlStreamReader = null;
		XMLStreamWriter xmlStreamWriter = null;

		ClassLoader portalClassLoader =
			PACLClassLoaderUtil.getPortalClassLoader();

		ClassLoader contextClassLoader =
			PACLClassLoaderUtil.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				PACLClassLoaderUtil.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			String availableLocales = StringPool.BLANK;
			String defaultLanguageId = StringPool.BLANK;

			// Read root node

			if (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();

				availableLocales = xmlStreamReader.getAttributeValue(
					null, _AVAILABLE_LOCALES);
				defaultLanguageId = xmlStreamReader.getAttributeValue(
					null, _DEFAULT_LOCALE);

				if (Validator.isNull(defaultLanguageId)) {
					defaultLanguageId = systemDefaultLanguageId;
				}
			}

			if ((availableLocales != null) &&
				availableLocales.contains(requestedLanguageId)) {

				availableLocales = StringUtil.remove(
					availableLocales, requestedLanguageId, StringPool.COMMA);

				UnsyncStringWriter unsyncStringWriter =
					new UnsyncStringWriter();

				XMLOutputFactory xmlOutputFactory =
					XMLOutputFactory.newInstance();

				xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(
					unsyncStringWriter);

				xmlStreamWriter.writeStartDocument();
				xmlStreamWriter.writeStartElement(_ROOT);

				if (localized) {
					xmlStreamWriter.writeAttribute(
						_AVAILABLE_LOCALES, availableLocales);
					xmlStreamWriter.writeAttribute(
						_DEFAULT_LOCALE, defaultLanguageId);
				}

				_copyNonExempt(
					xmlStreamReader, xmlStreamWriter, requestedLanguageId,
					defaultLanguageId, cdata);

				xmlStreamWriter.writeEndElement();
				xmlStreamWriter.writeEndDocument();

				xmlStreamWriter.close();
				xmlStreamWriter = null;

				xml = unsyncStringWriter.toString();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				PACLClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}

			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}

			if (xmlStreamWriter != null) {
				try {
					xmlStreamWriter.close();
				}
				catch (Exception e) {
				}
			}
		}

		return xml;
	}

	public void setLocalizedPreferencesValues(
			PortletRequest portletRequest, PortletPreferences preferences,
			String parameter)
		throws Exception {

		Map<Locale, String> map = getLocalizationMap(portletRequest, parameter);

		for (Map.Entry<Locale, String> entry : map.entrySet()) {
			String languageId = LocaleUtil.toLanguageId(entry.getKey());
			String value = entry.getValue();

			setPreferencesValue(preferences, parameter, languageId, value);
		}
	}

	public void setPreferencesValue(
			PortletPreferences preferences, String key, String languageId,
			String value)
		throws Exception {

		preferences.setValue(getPreferencesKey(key, languageId), value);
	}

	public void setPreferencesValues(
			PortletPreferences preferences, String key, String languageId,
			String[] values)
		throws Exception {

		preferences.setValues(getPreferencesKey(key, languageId), values);
	}

	public String updateLocalization(
		Map<Locale, String> localizationMap, String xml, String key,
		String defaultLanguageId) {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String value = localizationMap.get(locale);

			String languageId = LocaleUtil.toLanguageId(locale);

			if (Validator.isNotNull(value)) {
				xml = updateLocalization(
					xml, key, value, languageId, defaultLanguageId);
			}
			else {
				xml = removeLocalization(xml, key, languageId);
			}
		}

		return xml;
	}

	public String updateLocalization(String xml, String key, String value) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return updateLocalization(
			xml, key, value, defaultLanguageId, defaultLanguageId);
	}

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId) {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId);
	}

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId) {

		return updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, false);
	}

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata) {

		return updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, cdata,
			true);
	}

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata, boolean localized) {

		xml = _sanitizeXML(xml);

		XMLStreamReader xmlStreamReader = null;
		XMLStreamWriter xmlStreamWriter = null;

		ClassLoader portalClassLoader =
			PACLClassLoaderUtil.getPortalClassLoader();

		ClassLoader contextClassLoader =
			PACLClassLoaderUtil.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				PACLClassLoaderUtil.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			String availableLocales = StringPool.BLANK;

			// Read root node

			if (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();

				availableLocales = xmlStreamReader.getAttributeValue(
					null, _AVAILABLE_LOCALES);

				if (Validator.isNull(availableLocales)) {
					availableLocales = defaultLanguageId;
				}

				if (!availableLocales.contains(requestedLanguageId)) {
					availableLocales = StringUtil.add(
						availableLocales, requestedLanguageId,
						StringPool.COMMA);
				}
			}

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

			xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(
				unsyncStringWriter);

			xmlStreamWriter.writeStartDocument();
			xmlStreamWriter.writeStartElement(_ROOT);

			if (localized) {
				xmlStreamWriter.writeAttribute(
					_AVAILABLE_LOCALES, availableLocales);
				xmlStreamWriter.writeAttribute(
					_DEFAULT_LOCALE, defaultLanguageId);
			}

			_copyNonExempt(
				xmlStreamReader, xmlStreamWriter, requestedLanguageId,
				defaultLanguageId, cdata);

			xmlStreamWriter.writeStartElement(key);

			if (localized) {
				xmlStreamWriter.writeAttribute(
					_LANGUAGE_ID, requestedLanguageId);
			}

			if (cdata) {
				xmlStreamWriter.writeCData(value);
			}
			else {
				xmlStreamWriter.writeCharacters(value);
			}

			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndDocument();

			xmlStreamWriter.close();
			xmlStreamWriter = null;

			xml = unsyncStringWriter.toString();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				PACLClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}

			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}

			if (xmlStreamWriter != null) {
				try {
					xmlStreamWriter.close();
				}
				catch (Exception e) {
				}
			}
		}

		return xml;
	}

	private void _copyNonExempt(
			XMLStreamReader xmlStreamReader, XMLStreamWriter xmlStreamWriter,
			String exemptLanguageId, String defaultLanguageId, boolean cdata)
		throws XMLStreamException {

		while (xmlStreamReader.hasNext()) {
			int event = xmlStreamReader.next();

			if (event == XMLStreamConstants.START_ELEMENT) {
				String languageId = xmlStreamReader.getAttributeValue(
					null, _LANGUAGE_ID);

				if (Validator.isNull(languageId)) {
					languageId = defaultLanguageId;
				}

				if (!languageId.equals(exemptLanguageId)) {
					xmlStreamWriter.writeStartElement(
						xmlStreamReader.getLocalName());
					xmlStreamWriter.writeAttribute(_LANGUAGE_ID, languageId);

					while (xmlStreamReader.hasNext()) {
						event = xmlStreamReader.next();

						if ((event == XMLStreamConstants.CHARACTERS) ||
							(event == XMLStreamConstants.CDATA)) {

							String text = xmlStreamReader.getText();

							if (cdata) {
								xmlStreamWriter.writeCData(text);
							}
							else {
								xmlStreamWriter.writeCharacters(
									xmlStreamReader.getText());
							}

							break;
						}
						else if (event == XMLStreamConstants.END_ELEMENT) {
							break;
						}
					}

					xmlStreamWriter.writeEndElement();
				}
			}
			else if (event == XMLStreamConstants.END_DOCUMENT) {
				break;
			}
		}
	}

	private String _getCachedValue(
		String xml, String requestedLanguageId, boolean useDefault) {

		String value = null;

		Map<Tuple, String> valueMap = _cache.get(xml);

		if (valueMap != null) {
			Tuple subkey = new Tuple(useDefault, requestedLanguageId);

			value = valueMap.get(subkey);
		}

		return value;
	}

	private String _getLocalization(
		String bundleName, Locale locale, ClassLoader classLoader, String key,
		String defaultValue) {

		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			bundleName, locale, classLoader);

		String value = null;

		try {
			value = resourceBundle.getString(key);

			value = new String(
				value.getBytes(StringPool.ISO_8859_1), StringPool.UTF8);
		}

		catch (Exception e) {
		}

		if (Validator.isNotNull(value)) {
			value = LanguageResources.fixValue(value);
		}
		else {
			value = LanguageUtil.get(locale, key, defaultValue);
		}

		return value;
	}

	private String _getRootAttribute(
		String xml, String name, String defaultValue) {

		String value = null;

		XMLStreamReader xmlStreamReader = null;

		ClassLoader portalClassLoader =
			PACLClassLoaderUtil.getPortalClassLoader();

		ClassLoader contextClassLoader =
			PACLClassLoaderUtil.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				PACLClassLoaderUtil.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			if (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();

				value = xmlStreamReader.getAttributeValue(null, name);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				PACLClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}

			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		if (Validator.isNull(value)) {
			value = defaultValue;
		}

		return value;
	}

	private String _sanitizeXML(String xml) {
		if (Validator.isNull(xml) || !xml.contains("<root")) {
			xml = _EMPTY_ROOT_NODE;
		}

		return xml;
	}

	private void _setCachedValue(
		String xml, String requestedLanguageId, boolean useDefault,
		String value) {

		if (Validator.isNotNull(xml) && !xml.equals(_EMPTY_ROOT_NODE)) {
			synchronized (_cache) {
				Map<Tuple, String> map = _cache.get(xml);

				if (map == null) {
					map = new HashMap<Tuple, String>();
				}

				Tuple subkey = new Tuple(useDefault, requestedLanguageId);

				map.put(subkey, value);

				_cache.put(xml, map);
			}
		}
	}

	private static final String _AVAILABLE_LOCALES = "available-locales";

	private static final String _DEFAULT_LOCALE = "default-locale";

	private static final String _EMPTY_ROOT_NODE = "<root />";

	private static final String _LANGUAGE_ID = "language-id";

	private static final String _ROOT = "root";

	private static Log _log = LogFactoryUtil.getLog(LocalizationImpl.class);

	private Map<String, Map<Tuple, String>> _cache = new ReferenceMap(
		ReferenceMap.SOFT, ReferenceMap.HARD);

}