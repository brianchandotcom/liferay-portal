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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author Iván Zaera
 */
public class LocalizedValuesMap {

	public LocalizedValuesMap() {
		this(null);
	}

	public LocalizedValuesMap(String defaultValue) {
		_defaultValue = defaultValue;
	}

	public String get(Locale locale) {
		String value = _values.get(locale);

		if (value == null) {
			value = _defaultValue;
		}

		return value;
	}

	public String getDefaultValue() {
		return _defaultValue;
	}

	public Map<Locale, String> getLocalizationMap() {
		HashMap<Locale, String> map = new HashMap<>(_values);

		Locale locale = LocaleUtil.getDefault();

		if (map.get(locale) == null) {
			map.put(locale, getDefaultValue());
		}

		return map;
	}

	public String getLocalizationXml(
		String key, Locale defaultLocale, Locale[] availableLocales) {

		XMLStreamWriter xmlStreamWriter = null;

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

			xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(
				unsyncStringWriter);

			xmlStreamWriter.writeStartDocument();

			xmlStreamWriter.writeStartElement("root");

			xmlStreamWriter.writeAttribute(
				"available-locales", StringUtil.merge(availableLocales));
			xmlStreamWriter.writeAttribute(
				"default-locale", defaultLocale.toString());

			for (Locale locale : availableLocales) {
				String value = get(locale);

				if (value != null) {
					xmlStreamWriter.writeStartElement(key);

					xmlStreamWriter.writeAttribute(
						"language-id", locale.toString());

					xmlStreamWriter.writeCharacters(value);

					xmlStreamWriter.writeEndElement();
				}
			}

			xmlStreamWriter.writeEndElement();

			xmlStreamWriter.writeEndDocument();

			return unsyncStringWriter.toString();
		}
		catch (XMLStreamException xmlse) {
			throw new RuntimeException(xmlse);
		}
		finally {
			_close(xmlStreamWriter);
		}
	}

	public void put(Locale locale, String value) {
		_values.put(locale, value);
	}

	private void _close(XMLStreamWriter xmlStreamWriter) {
		if (xmlStreamWriter != null) {
			try {
				xmlStreamWriter.close();
			}
			catch (XMLStreamException xmlse) {
			}
		}
	}

	private final String _defaultValue;
	private final Map<Locale, String> _values = new HashMap<>();

}