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
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

/**
 * @author Connor McKay
 */
public class LocalizationImplTest extends BaseTestCase {

	public LocalizationImplTest() {
		InitUtil.initWithSpring();
	}

	@Override
	public void setUp() throws Exception {
		_english = new Locale("en", "US");
		_englishId = LocaleUtil.toLanguageId(_english);

		_german = new Locale("de", "DE");
		_germanId = LocaleUtil.toLanguageId(_german);
	}

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

		assertNotNull(translation);
		assertEquals("foo&bar", translation);
		assertEquals(7, translation.length());

		translation = LocalizationUtil.getLocalization(sb.toString(), "en_US");

		assertNotNull(translation);
		assertEquals(18, translation.length());
	}

	public void testLocalizationsXML() {
		String xml = StringPool.BLANK;

		xml = LocalizationUtil.updateLocalization(
			xml, "greeting", _englishHello, _englishId, _englishId);
		xml = LocalizationUtil.updateLocalization(
			xml, "greeting", _germanHello, _germanId, _englishId);

		assertEquals(
			_englishHello, LocalizationUtil.getLocalization(xml, _englishId));
		assertEquals(
			_germanHello, LocalizationUtil.getLocalization(xml, _germanId));
	}

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

		assertTrue(sb.length() > totalSize);

		String translation = LocalizationUtil.getLocalization(
			sb.toString(), "es_ES");

		assertNotNull(translation);
		assertEquals(totalSize, translation.length());

		translation = LocalizationUtil.getLocalization(sb.toString(), "en_US");

		assertNotNull(translation);
		assertEquals(18, translation.length());
	}

	public void testPreferencesLocalization() throws Exception {
		PortletPreferences preferences = new PortletPreferencesImpl();

		LocalizationUtil.setPreferencesValue(
			preferences, "greeting", _englishId, _englishHello);
		LocalizationUtil.setPreferencesValue(
			preferences, "greeting", _germanId, _germanHello);

		assertEquals(
			_englishHello,
			LocalizationUtil.getPreferencesValue(
				preferences, "greeting", _englishId));
		assertEquals(
			_germanHello,
			LocalizationUtil.getPreferencesValue(
				preferences, "greeting", _germanId));
	}

	public void testSetLocalizedPreferencesValues() throws Exception {
		final PortletRequest request = _mockery.mock(PortletRequest.class);

		Expectations expectations = new Expectations() {
			{
				oneOf(request).getParameter("greeting_en_US");
				will(returnValue(_englishHello));

				oneOf(request).getParameter("greeting_de_DE");
				will(returnValue(_germanHello));

				allowing(request).getParameter(with(aNonNull(String.class)));
				will(returnValue(null));
			}
		};

		_mockery.checking(expectations);

		PortletPreferences preferences = new PortletPreferencesImpl();

		LocalizationUtil.setLocalizedPreferencesValues(
			request, preferences, "greeting");

		assertEquals(
			_englishHello,
			LocalizationUtil.getPreferencesValue(
				preferences, "greeting", _englishId));
		assertEquals(
			_germanHello,
			LocalizationUtil.getPreferencesValue(
				preferences, "greeting", _germanId));
	}

	private Locale _english;
	private String _englishHello = "Hello World";
	private String _englishId;
	private Locale _german;
	private String _germanHello = "Hallo Welt";
	private String _germanId;
	private Mockery _mockery = new JUnit4Mockery();

}