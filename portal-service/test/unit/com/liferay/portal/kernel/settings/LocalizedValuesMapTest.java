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

import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class LocalizedValuesMapTest {

	@Before
	public void setUp() {
		_localizedValuesMap = new LocalizedValuesMap(_DEFAULT_VALUE);

		for (Locale locale : _AVAILABLE_LOCALES) {
			_localizedValuesMap.put(locale, "value " + locale.toString());
		}

		List<Locale> locales = new ArrayList<>();

		locales.addAll(Arrays.asList(_AVAILABLE_LOCALES));
		locales.add(_UNAVAILABLE_LOCALE);

		_locales = locales.toArray(new Locale[locales.size()]);
	}

	@Test
	public void testGetLocalizationMap() {
		Map<Locale, String> map = _localizedValuesMap.getLocalizationMap();

		Assert.assertEquals(_AVAILABLE_LOCALES.length, map.size());

		for (Locale locale : _AVAILABLE_LOCALES) {
			Assert.assertEquals("value " + locale.toString(), map.get(locale));
		}

		Assert.assertEquals(null, map.get(_UNAVAILABLE_LOCALE));
	}

	@Test
	public void testGetLocalizationXml() {
		String xml = _localizedValuesMap.getLocalizationXml(
			_KEY, _DEFAULT_LOCALE, _locales);

		Assert.assertEquals(
			"<?xml version='1.0' encoding='UTF-8'?>" +
				"<root available-locales=\"es_ES,en_GB,en_US,pt_BR\" " +
						"default-locale=\"es_ES\">" +
					"<key language-id=\"es_ES\">value es_ES</key>" +
					"<key language-id=\"en_GB\">value en_GB</key>" +
					"<key language-id=\"en_US\">value en_US</key>" +
					"<key language-id=\"pt_BR\">defaultValue</key>" +
				"</root>",
			xml);
	}

	@Test
	public void testLocalizationMapReturnsDefaultValueForDefaultLocale() {
		_localizedValuesMap = new LocalizedValuesMap(_DEFAULT_VALUE);

		LocaleUtil.setDefault(
			_DEFAULT_LOCALE.getLanguage(), _DEFAULT_LOCALE.getCountry(),
			_DEFAULT_LOCALE.getVariant());

		Map<Locale, String> localizationMap =
			_localizedValuesMap.getLocalizationMap();

		Assert.assertEquals(
			_DEFAULT_VALUE, localizationMap.get(_DEFAULT_LOCALE));
	}

	private static final Locale[] _AVAILABLE_LOCALES = {
		LocaleUtil.SPAIN, LocaleUtil.UK, LocaleUtil.US
	};

	private static final Locale _DEFAULT_LOCALE = LocaleUtil.SPAIN;

	private static final String _DEFAULT_VALUE = "defaultValue";

	private static final String _KEY = "key";

	private static final Locale _UNAVAILABLE_LOCALE = LocaleUtil.BRAZIL;

	private Locale[] _locales;
	private LocalizedValuesMap _localizedValuesMap;

}