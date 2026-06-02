/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.renderer.internal.servlet;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Larissa Ribeiro
 */
public class DDMFormContextProviderServletTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpLanguageUtil();

		_ddmFormContextProviderServlet = new DDMFormContextProviderServlet();
		_language = Mockito.mock(Language.class);

		ReflectionTestUtil.setFieldValue(
			_ddmFormContextProviderServlet, "_language", _language);
	}

	@Test
	public void testGetLocaleWhenLanguageIdIsNull() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			_language.getLanguageId(httpServletRequest)
		).thenReturn(
			"en_US"
		);

		Assert.assertEquals(
			LocaleUtil.US,
			ReflectionTestUtil.invoke(
				_ddmFormContextProviderServlet, "_getLocale",
				new Class<?>[] {HttpServletRequest.class}, httpServletRequest));
	}

	@Test
	public void testGetLocaleWhenLanguageIdIsPresent() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("languageId")
		).thenReturn(
			"pt_BR"
		);

		Assert.assertEquals(
			LocaleUtil.BRAZIL,
			ReflectionTestUtil.invoke(
				_ddmFormContextProviderServlet, "_getLocale",
				new Class<?>[] {HttpServletRequest.class}, httpServletRequest));
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.doReturn(
			Boolean.TRUE
		).when(
			language
		).isAvailableLocale(
			Mockito.any(Locale.class)
		);

		languageUtil.setLanguage(language);
	}

	private DDMFormContextProviderServlet _ddmFormContextProviderServlet;
	private Language _language;

}