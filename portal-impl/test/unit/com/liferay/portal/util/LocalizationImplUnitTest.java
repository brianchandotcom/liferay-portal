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

package com.liferay.portal.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Manuel de la Peña
 */
public class LocalizationImplUnitTest extends PowerMockito {

	@Test
	public void testGetDefaultImportLocaleUseCase1() {
		verifyDefaultImportLocale("es_ES", "es_ES,en_US,de_DE", "es_ES", true);
	}

	@Test
	public void testGetDefaultImportLocaleUseCase2() {
		verifyDefaultImportLocale("en_US", "bg_BG,en_US,de_DE", "en_US", true);
	}

	@Test
	public void testGetDefaultImportLocaleUseCase3() {
		verifyDefaultImportLocale("bg_BG", "bg_BG,en_US,de_DE", "en_US", true);
	}

	@Test
	public void testGetDefaultImportLocaleUseCase4() {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			LocalizationImpl.class.getName(), Level.WARNING);

		try {
			verifyDefaultImportLocale("bg_BG", "bg_BG,fr_FR", "bg_BG", true);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Language es_ES is missing for com.liferay.portal.className " +
					"with primary key 0. Setting default language to bg_BG.",
				logRecord.getMessage());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testGetDefaultImportLocaleWrongUseCase1() {
		verifyDefaultImportLocale("es_ES", "es_ES,en_US,de_DE", "en_US", false);
	}

	protected Locale[] getContentAvailableLocales(String locales) {
		String[] localeIds = StringUtil.split(locales);

		Locale[] array = new Locale[localeIds.length];

		for (int i = 0; i < localeIds.length; i++) {
			array[i] =  LocaleUtil.fromLanguageId(localeIds[i], false);
		}

		return array;
	}

	protected void verifyDefaultImportLocale(
		String defaultContentLocale, final String portalAvailableLocales,
		String expectedLocale, boolean expectedResult) {

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(
			(Language)ProxyUtil.newProxyInstance(
				Language.class.getClassLoader(),
				new Class<?>[] {Language.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						String methodName = method.getName();

						if (methodName.equals("getAvailableLocales")) {
							return getContentAvailableLocales(
								portalAvailableLocales);
						}

						if (methodName.equals("isAvailableLocale")) {
							Locale locale = (Locale)args[0];

							Locale[] portalLocales = getContentAvailableLocales(
								portalAvailableLocales);

							return ArrayUtil.contains(portalLocales, locale);
						}

						throw new UnsupportedOperationException();
					}

				}));

		Locale locale = LocaleUtil.fromLanguageId(defaultContentLocale);

		LocaleUtil.setDefault(
			locale.getLanguage(), locale.getCountry(), locale.getVariant());

		LocalizationUtil localizationUtil = new LocalizationUtil();

		localizationUtil.setLocalization(new LocalizationImpl());

		Locale contentDefaultLocale = LocaleUtil.fromLanguageId("es_ES");

		Locale[] contentAvailableLocales = getContentAvailableLocales(
			"es_ES,en_US,de_DE");

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			"com.liferay.portal.className", 0L, contentDefaultLocale,
			contentAvailableLocales);

		if (expectedResult) {
			Assert.assertTrue(
				LocaleUtil.equals(
					LocaleUtil.fromLanguageId(expectedLocale),
					defaultImportLocale));
		}
		else {
			Assert.assertFalse(
				LocaleUtil.equals(
					LocaleUtil.fromLanguageId(expectedLocale),
					defaultImportLocale));
		}
	}

}