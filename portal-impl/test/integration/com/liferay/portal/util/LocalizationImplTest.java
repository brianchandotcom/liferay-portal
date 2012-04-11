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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Connor McKay
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LocalizationImplTest {

	@Before
	public void setUp() throws Exception {
		_english = new Locale("en", "US");
		_englishId = LocaleUtil.toLanguageId(_english);

		_german = new Locale("de", "DE");
		_germanId = LocaleUtil.toLanguageId(_german);
	}

	@Test
	public void testChunkedText() {
		StringBundler sb = new StringBundler();

		sb.append("<?xml version='1.0' encoding='UTF-8'?>");

		sb.append("<root available-locales=\"en_US,es_ES\" ");
		sb.append("default-locale=\"en_US\">");
		sb.append("<static-content language-id=\"es_ES\">");
		sb.append("foo&amp;bar");
		sb.append("</static-content>");
		sb.append("<static-content language-id=\"en_US\">");
		sb.append("<![CDATA[Example in English]]>");
		sb.append("</static-content>");
		sb.append("</root>");

		String translation = LocalizationUtil.getLocalization(
			sb.toString(), "es_ES");

		Assert.assertNotNull(translation);
		Assert.assertEquals("foo&bar", translation);
		Assert.assertEquals(7, translation.length());

		translation = LocalizationUtil.getLocalization(sb.toString(), "en_US");

		Assert.assertNotNull(translation);
		Assert.assertEquals(18, translation.length());
	}

	@Test
	public void testLocalizationsXML() {
		String xml = StringPool.BLANK;

		xml = LocalizationUtil.updateLocalization(
			xml, "greeting", _englishHello, _englishId, _englishId);
		xml = LocalizationUtil.updateLocalization(
			xml, "greeting", _germanHello, _germanId, _englishId);

		Assert.assertEquals(
			_englishHello, LocalizationUtil.getLocalization(xml, _englishId));
		Assert.assertEquals(
			_germanHello, LocalizationUtil.getLocalization(xml, _germanId));
	}

	@Test
	public void testLongTranslationText() {
		StringBundler sb = new StringBundler();

		sb.append("<?xml version='1.0' encoding='UTF-8'?>");

		sb.append("<root available-locales=\"en_US,es_ES\" ");
		sb.append("default-locale=\"en_US\">");
		sb.append("<static-content language-id=\"es_ES\">");
		sb.append("<![CDATA[");

		int loops = 2000000;

		for (int i = 0; i < loops; i++) {
			sb.append("1234567890");
		}

		sb.append("]]>");
		sb.append("</static-content>");
		sb.append("<static-content language-id=\"en_US\">");
		sb.append("<![CDATA[Example in English]]>");
		sb.append("</static-content>");
		sb.append("</root>");

		int totalSize = loops * 10;

		Assert.assertTrue(sb.length() > totalSize);

		String translation = LocalizationUtil.getLocalization(
			sb.toString(), "es_ES");

		Assert.assertNotNull(translation);
		Assert.assertEquals(totalSize, translation.length());

		translation = LocalizationUtil.getLocalization(sb.toString(), "en_US");

		Assert.assertNotNull(translation);
		Assert.assertEquals(18, translation.length());
	}

	@Test
	public void testPreferencesLocalization() throws Exception {
		PortletPreferences preferences = new PortletPreferencesImpl();

		LocalizationUtil.setPreferencesValue(
			preferences, "greeting", _englishId, _englishHello);
		LocalizationUtil.setPreferencesValue(
			preferences, "greeting", _germanId, _germanHello);

		Assert.assertEquals(
			_englishHello,
			LocalizationUtil.getPreferencesValue(
				preferences, "greeting", _englishId));
		Assert.assertEquals(
			_germanHello,
			LocalizationUtil.getPreferencesValue(
				preferences, "greeting", _germanId));
	}

	@Test
	public void testSetLocalizedPreferencesValues() throws Exception {
		MockPortletRequest request = new MockPortletRequest();

		request.setParameter("greeting_" + _englishId, _englishHello);
		request.setParameter("greeting_" + _germanId, _germanHello);

		PortletPreferences preferences = new PortletPreferencesImpl();

		LocalizationUtil.setLocalizedPreferencesValues(
			request, preferences, "greeting");

		Assert.assertEquals(
			_englishHello,
			LocalizationUtil.getPreferencesValue(
				preferences, "greeting", _englishId));
		Assert.assertEquals(
			_germanHello,
			LocalizationUtil.getPreferencesValue(
				preferences, "greeting", _germanId));
	}

	@Test
	public void testUpdateLocalizationXmlFromMap() {
		Map<Locale, String>localizationMap = new HashMap<Locale, String>();
		localizationMap.put(_english, _englishHello);

		StringBundler sb = new StringBundler();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<root available-locales=\"en_US,de_DE\" ");
		sb.append("default-locale=\"en_US\">");
		sb.append("<greeting language-id=\"de_DE\">");
		sb.append("<![CDATA[Beispiel auf Deutschl]]>");
		sb.append("</greeting>");
		sb.append("<greeting language-id=\"en_US\">");
		sb.append("<![CDATA[Example in English]]>");
		sb.append("</greeting>");
		sb.append("</root>");

		String xml = LocalizationUtil.updateLocalizationXmlFromMap(
			localizationMap, sb.toString(), "greeting",
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

		Assert.assertEquals(
			_englishHello, LocalizationUtil.getLocalization(
				xml, _englishId, false));
		Assert.assertEquals(
			StringPool.BLANK, LocalizationUtil.getLocalization(
				xml, _germanId, false));
	}

	private Locale _english;
	private String _englishHello = "Hello World";
	private String _englishId;
	private Locale _german;
	private String _germanHello = "Hallo Welt";
	private String _germanId;

}