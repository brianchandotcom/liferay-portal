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

package com.liferay.portal.language;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import org.apache.struts.mock.MockHttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Enclosed.class)
public class LanguageImplTest {

	public static final class WhenFormattingFromLocale {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() {
			_languageImpl = (LanguageImpl)PortalBeanLocatorUtil.locate(
				"com.liferay.portal.language.LanguageImpl");
		}

		@Test
		public void testFormatWithKeyNull() {
			Assert.assertEquals(
				null,
				_languageImpl.format(
					LocaleThreadLocal.getDefaultLocale(), null, "31"));
		}

		@Test
		public void testFormatWithLocaleNull() {
			Locale defaultLocale = LocaleThreadLocal.getDefaultLocale();

			Locale nullableLocale = null;

			try {
				Assert.assertEquals(
					_LANG_KEY_WITH_ARGUMENT,
					_languageImpl.format(
						nullableLocale, _LANG_KEY_WITH_ARGUMENT, "31"));
			}
			finally {
				LocaleThreadLocal.setDefaultLocale(defaultLocale);
			}
		}

		@Test
		public void testFormatWithOneArgument() {
			String value = _languageImpl.format(
				LocaleUtil.US, _LANG_KEY_WITH_ARGUMENT, "31");

			Assert.assertEquals("31 Hours", value);
		}

		@Test
		public void testFormatWithOneNontranslatableAmericanArgument() {
			Locale locale = LocaleUtil.US;

			String value = _languageImpl.format(
				locale, _LANG_KEY_WITH_ARGUMENT, _BIG_INTEGER, false);

			Assert.assertEquals("1,234,567,890 Hours", value);

			value = _languageImpl.format(
				locale, _LANG_KEY_WITH_ARGUMENT, _BIG_DOUBLE, false);

			Assert.assertEquals("1,234,567,890.12 Hours", value);

			value = _languageImpl.format(
				locale, _LANG_KEY_WITH_ARGUMENT, _BIG_FLOAT, false);

			Assert.assertEquals("1,234,567.875 Hours", value);
		}

		@Test
		public void testFormatWithOneNontranslatableSpanishArgument() {
			Locale locale = LocaleUtil.SPAIN;

			String value = _languageImpl.format(
				locale, _LANG_KEY_WITH_ARGUMENT, _BIG_INTEGER, false);

			Assert.assertEquals("1.234.567.890 horas", value);

			value = _languageImpl.format(
				locale, _LANG_KEY_WITH_ARGUMENT, _BIG_DOUBLE, false);

			Assert.assertEquals("1.234.567.890,12 horas", value);

			value = _languageImpl.format(
				locale, _LANG_KEY_WITH_ARGUMENT, _BIG_FLOAT, false);

			Assert.assertEquals("1.234.567,875 horas", value);
		}

		@Test
		public void testFormatWithTwoArguments() {
			String value = _languageImpl.format(
				LocaleUtil.US, _LANG_KEY_WITH_ARGUMENTS,
				new Object[] {"A", "B"});

			Assert.assertEquals("A has invited you to join B.", value);
		}

	}

	public static final class WhenFormattingFromRequest {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() {
			_languageImpl = (LanguageImpl)PortalBeanLocatorUtil.locate(
				"com.liferay.portal.language.LanguageImpl");
		}

		@Test
		public void testFormatWithOneArgument() {
			MockLanguageServletRequest mockLanguageServletRequest =
				new MockLanguageServletRequest(LocaleUtil.US);

			String value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENT, "31");

			Assert.assertEquals("31 Hours", value);
		}

		@Test
		public void testFormatWithOneLanguageWrapper() {
			MockLanguageServletRequest mockLanguageServletRequest =
				new MockLanguageServletRequest(LocaleUtil.US);

			String value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENT, new LanguageWrapper("a", "31", "a"));

			Assert.assertEquals("a31a Hours", value);
		}

		@Test
		public void testFormatWithOneNontranslatableAmericanArgument() {
			MockLanguageServletRequest mockLanguageServletRequest =
				new MockLanguageServletRequest(LocaleUtil.US);

			String value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENT, _BIG_INTEGER, false);

			Assert.assertEquals("1,234,567,890 Hours", value);

			value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENT, _BIG_DOUBLE, false);

			Assert.assertEquals("1,234,567,890.12 Hours", value);

			value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENT, _BIG_FLOAT, false);

			Assert.assertEquals("1,234,567.875 Hours", value);
		}

		@Test
		public void testFormatWithOneNontranslatableSpanishArgument() {
			MockLanguageServletRequest mockLanguageServletRequest =
				new MockLanguageServletRequest(LocaleUtil.SPAIN);

			String value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENT, _BIG_INTEGER, false);

			Assert.assertEquals("1.234.567.890 horas", value);

			value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENT, _BIG_DOUBLE, false);

			Assert.assertEquals("1.234.567.890,12 horas", value);

			value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENT, _BIG_FLOAT, false);

			Assert.assertEquals("1.234.567,875 horas", value);
		}

		@Test
		public void testFormatWithTwoArguments() {
			MockLanguageServletRequest mockLanguageServletRequest =
				new MockLanguageServletRequest(LocaleUtil.US);

			String value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENTS, new Object[] {"A", "B"});

			Assert.assertEquals("A has invited you to join B.", value);
		}

		@Test
		public void testFormatWithTwoLanguageWrappers() {
			MockLanguageServletRequest mockLanguageServletRequest =
				new MockLanguageServletRequest(LocaleUtil.US);

			LanguageWrapper[] languageWrappers = new LanguageWrapper[2];

			languageWrappers[0] = new LanguageWrapper("a", "A", "a");
			languageWrappers[1] = new LanguageWrapper("b", "B", "b");

			String value = _languageImpl.format(
				mockLanguageServletRequest.getRequest(),
				_LANG_KEY_WITH_ARGUMENTS, languageWrappers);

			Assert.assertEquals("aAa has invited you to join bBb.", value);
		}

	}

	private static final Double _BIG_DOUBLE = 1234567890.12D;

	private static final Float _BIG_FLOAT = 1234567.85F;

	private static final Integer _BIG_INTEGER = 1234567890;

	private static final String _LANG_KEY_WITH_ARGUMENT = "x-hours";

	private static final String _LANG_KEY_WITH_ARGUMENTS =
		"x-has-invited-you-to-join-x";

	private static LanguageImpl _languageImpl;

	private static final class MockLanguageServletRequest {

		public MockLanguageServletRequest(Locale locale) {
			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setLocale(locale);

			_mockHttpServletRequest = new MockHttpServletRequest();

			_mockHttpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);
		}

		public MockHttpServletRequest getRequest() {
			return _mockHttpServletRequest;
		}

		private final MockHttpServletRequest _mockHttpServletRequest;

	}

}