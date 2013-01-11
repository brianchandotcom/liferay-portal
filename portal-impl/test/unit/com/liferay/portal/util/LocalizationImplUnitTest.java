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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@PrepareForTest({LanguageUtil.class, LocaleUtil.class, LocalizationUtil.class})
@RunWith(PowerMockRunner.class)
public class LocalizationImplUnitTest extends PowerMockito {

	@After
	public void tearDown() {
		verifyStatic();
	}

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
		verifyDefaultImportLocale("bg_BG", "bg_BG,fr_FR", "bg_BG", true);
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
		String defaultContentLocale, String portalAvailableLocales,
		String expectedLocale, boolean expectedResult) {

		spy(LocaleUtil.class);

		when(
			LocaleUtil.getDefault()
		).thenReturn(
			new Locale(defaultContentLocale)
		);

		mockStatic(LanguageUtil.class);

		Locale[] portalLocales = getContentAvailableLocales(
			portalAvailableLocales);

		when(
			LanguageUtil.getAvailableLocales()
		).thenReturn(
			portalLocales
		);

		spy(LocalizationUtil.class);

		when(
			LocalizationUtil.getLocalization()
		).thenReturn(
			_localization
		);

		Locale contentDefaultLocale = LocaleUtil.fromLanguageId("es_ES");

		Locale[] contentAvailableLocales = getContentAvailableLocales(
			"es_ES,en_US,de_DE");

		Locale defaultImportLocale =
			LocalizationUtil.getDefaultImportLocale(
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

	private LocalizationImpl _localization = new LocalizationImpl();

}