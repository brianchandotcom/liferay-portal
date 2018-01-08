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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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

	public static final class GetAvailableLocales {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_company = CompanyTestUtil.addCompany();
		}

		@Test
		public void testCompanyThreadLocalIsDefaultWithNoArgs()
			throws Exception {

			long companyId = CompanyThreadLocal.getCompanyId();

			try {
				_resetCompanyLocales();

				Assert.assertEquals(_locales, _language.getAvailableLocales());
			}
			finally {
				CompanyThreadLocal.setCompanyId(companyId);
			}
		}

		@Test
		public void testGroupWithoutLocalesInheritsFromCompany()
			throws Exception {

			long companyId = CompanyThreadLocal.getCompanyId();

			try {
				_resetCompanyLocales();
			}
			finally {
				CompanyThreadLocal.setCompanyId(companyId);
			}

			Assert.assertEquals(
				_locales, _language.getAvailableLocales(_getGuestGroupId()));
		}

		@Test
		public void testGroupWithSpecificLocales() throws Exception {
			long groupId = _getGuestGroupId();

			GroupTestUtil.updateDisplaySettings(
				groupId, _locales, LocaleUtil.US);

			Assert.assertEquals(
				_locales, _language.getAvailableLocales(groupId));
		}

		private long _getGuestGroupId() throws PortalException {
			Group group = _groupLocalService.getGroup(
				_company.getCompanyId(), GroupConstants.GUEST);

			return group.getGroupId();
		}

		private void _resetCompanyLocales() throws Exception {
			CompanyTestUtil.resetCompanyLocales(
				_company.getCompanyId(), _locales, LocaleUtil.US);
		}

		@Inject
		private static GroupLocalService _groupLocalService;

		@Inject
		private static Language _language;

		private static final Set<Locale> _locales = new HashSet<>(
			Arrays.asList(
				LocaleUtil.BRAZIL, LocaleUtil.HUNGARY, LocaleUtil.JAPAN,
				LocaleUtil.US));

		@DeleteAfterTestRun
		private Company _company;

	}

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