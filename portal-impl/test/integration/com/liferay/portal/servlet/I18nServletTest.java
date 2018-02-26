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

package com.liferay.portal.servlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Juan Gonzalez
 */
public class I18nServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_availableLocales = LanguageUtil.getAvailableLocales();
		_defaultLocale = LocaleUtil.getDefault();
		_localesEnabled = PropsValues.LOCALES_ENABLED;

		LanguageUtil.init();

		CompanyTestUtil.resetCompanyLocales(
			PortalUtil.getDefaultCompanyId(),
			Arrays.asList(
				LocaleUtil.CANADA_FRENCH, LocaleUtil.SPAIN, LocaleUtil.UK,
				LocaleUtil.US),
			LocaleUtil.US);

		PropsValues.LOCALES_ENABLED = new String[] {
			LanguageUtil.getLanguageId(LocaleUtil.CANADA_FRENCH),
			LanguageUtil.getLanguageId(LocaleUtil.SPAIN),
			LanguageUtil.getLanguageId(LocaleUtil.UK),
			LanguageUtil.getLanguageId(LocaleUtil.US)
		};
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		LanguageUtil.init();

		CompanyTestUtil.resetCompanyLocales(
			PortalUtil.getDefaultCompanyId(), _availableLocales,
			_defaultLocale);

		PropsValues.LOCALES_ENABLED = _localesEnabled;
	}

	@Before
	public void setUp() throws Exception {
		_originalLocaleUseDefaultIfNotAvailable =
			PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE;

		LanguageUtil.init();

		_group = GroupTestUtil.addGroup();

		UnicodeProperties typeSettingsProperties =
			_group.getTypeSettingsProperties();

		typeSettingsProperties.put(
			GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES, "false");

		_group.setTypeSettingsProperties(typeSettingsProperties);

		GroupLocalServiceUtil.updateGroup(_group);
	}

	@After
	public void tearDown() {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE =
			_originalLocaleUseDefaultIfNotAvailable;
	}

	@Test
	public void testDefaultCompanyI18nData() throws Exception {
		testIsDefaultLocale(null, LocaleUtil.US);
		testIsDefaultOrFirstI18nData(null, LocaleUtil.US);

		testIsFirstLocale(null, LocaleUtil.CANADA_FRENCH);
		testIsDefaultOrFirstI18nData(null, LocaleUtil.CANADA_FRENCH);
		testIsFirstLocale(null, LocaleUtil.SPAIN);
		testIsDefaultOrFirstI18nData(null, LocaleUtil.SPAIN);

		testIsNotDefaultOrFirstLocale(null, LocaleUtil.UK);
		testIsNotDefaultOrFirstI18nData(null, LocaleUtil.UK, LocaleUtil.US);
	}

	@Test
	public void testDefaultGroupI18nData() throws Exception {
		LanguageUtil.resetAvailableGroupLocales(_group.getGroupId());

		testIsDefaultLocale(_group, LocaleUtil.US);
		testIsDefaultOrFirstI18nData(_group, LocaleUtil.US);

		testIsFirstLocale(_group, LocaleUtil.CANADA_FRENCH);
		testIsDefaultOrFirstI18nData(_group, LocaleUtil.CANADA_FRENCH);
		testIsFirstLocale(_group, LocaleUtil.SPAIN);
		testIsDefaultOrFirstI18nData(_group, LocaleUtil.SPAIN);

		testIsNotDefaultOrFirstLocale(_group, LocaleUtil.UK);
		testIsNotDefaultOrFirstI18nData(_group, LocaleUtil.UK, LocaleUtil.US);
	}

	@Test
	public void testI18nNotUseDefaultExistentLocale() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = false;

		Locale expectedLocale = LocaleUtil.getDefault();

		testGetI18nData(expectedLocale, getI18nData(expectedLocale));
	}

	@Test
	public void testI18nNotUseDefaultNondefaultLocale() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = false;

		Locale expectedLocale = LocaleUtil.SPAIN;

		testGetI18nData(expectedLocale, getI18nData(expectedLocale));
	}

	@Test
	public void testI18nNotUseDefaultNonexistentLocale() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = false;

		Locale expectedLocale = LocaleUtil.CHINA;

		testGetI18nData(expectedLocale, null);
	}

	@Test
	public void testI18nUseDefault() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = true;

		Locale expectedLocale = LocaleUtil.getDefault();

		testGetI18nData(expectedLocale, getI18nData(expectedLocale));
	}

	@Test
	public void testI18nUseDefaultNonexistentLocale() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = true;

		Locale expectedLocale = LocaleUtil.CHINA;

		testGetI18nData(expectedLocale, getI18nData(expectedLocale));
	}

	@Test
	public void testModifiedGroupI18nData() throws Exception {
		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(
				LocaleUtil.CANADA_FRENCH, LocaleUtil.SPAIN, LocaleUtil.UK,
				LocaleUtil.US),
			LocaleUtil.SPAIN);

		testIsDefaultLocale(_group, LocaleUtil.SPAIN);
		testIsDefaultOrFirstI18nData(_group, LocaleUtil.SPAIN);

		testIsFirstLocale(_group, LocaleUtil.CANADA_FRENCH);
		testIsDefaultOrFirstI18nData(_group, LocaleUtil.CANADA_FRENCH);
		testIsFirstLocale(_group, LocaleUtil.UK);
		testIsDefaultOrFirstI18nData(_group, LocaleUtil.UK);

		testIsNotDefaultOrFirstLocale(_group, LocaleUtil.US);
		testIsNotDefaultOrFirstI18nData(_group, LocaleUtil.US, LocaleUtil.UK);
	}

	protected I18nServlet.I18nData getI18nData(Locale locale) {
		return _i18nServlet.getI18nData(locale);
	}

	protected void testGetI18nData(
			Locale locale, I18nServlet.I18nData expectedI18nData)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);
		mockHttpServletRequest.setServletPath(
			StringPool.SLASH + LocaleUtil.toLanguageId(locale));

		I18nServlet.I18nData actualI18nData = _i18nServlet.getI18nData(
			mockHttpServletRequest);

		Assert.assertEquals(expectedI18nData, actualI18nData);
	}

	protected void testIsDefaultLocale(
			Group group, Locale expectedDefaultLocale)
		throws Exception {

		Locale actualDefaultLocale = _getDefaultLocale(group);

		Assert.assertEquals(expectedDefaultLocale, actualDefaultLocale);
	}

	protected void testIsDefaultOrFirstI18nData(Group group, Locale locale)
		throws Exception {

		I18nServlet.I18nData languageOnlyPath = _getI18nData(
			group, locale.getLanguage());

		String languageId = LocaleUtil.toLanguageId(locale);

		I18nServlet.I18nData languageAndLocalePath = _getI18nData(
			group, languageId);

		Assert.assertEquals(languageAndLocalePath.getLanguageId(), languageId);

		Assert.assertEquals(languageOnlyPath, languageAndLocalePath);
	}

	protected void testIsFirstLocale(Group group, Locale expectedFirstLocale)
		throws Exception {

		Locale actualDefaultLocale = _getDefaultLocale(group);
		Locale actualFirstLocale = _getFirstLocale(
			group, expectedFirstLocale.getLanguage());

		Assert.assertEquals(expectedFirstLocale, actualFirstLocale);

		Assert.assertNotEquals(expectedFirstLocale, actualDefaultLocale);
	}

	protected void testIsNotDefaultOrFirstI18nData(
			Group group, Locale locale, Locale expectedDefaultLocale)
		throws Exception {

		I18nServlet.I18nData languageOnlyPath = _getI18nData(
			group, locale.getLanguage());

		String languageId = LocaleUtil.toLanguageId(locale);

		I18nServlet.I18nData languageAndLocalePath = _getI18nData(
			group, languageId);

		Assert.assertEquals(languageAndLocalePath.getLanguageId(), languageId);

		Assert.assertEquals(
			languageOnlyPath.getLanguageId(),
			LocaleUtil.toLanguageId(expectedDefaultLocale));

		Assert.assertNotEquals(languageOnlyPath, languageAndLocalePath);
	}

	protected void testIsNotDefaultOrFirstLocale(Group group, Locale locale)
		throws Exception {

		Locale defaultLocale = _getDefaultLocale(group);

		if (!LanguageUtil.isSameLanguage(defaultLocale, locale)) {
			defaultLocale = _getFirstLocale(group, locale.getLanguage());
		}

		Assert.assertNotEquals(defaultLocale, locale);
	}

	private Locale _getDefaultLocale(Group group) throws Exception {
		if (group != null) {
			return PortalUtil.getSiteDefaultLocale(group);
		}
		else {
			return LocaleUtil.getDefault();
		}
	}

	private Locale _getFirstLocale(Group group, String language)
		throws Exception {

		if (group != null) {
			return LanguageUtil.getLocale(group.getGroupId(), language);
		}
		else {
			return LanguageUtil.getLocale(language);
		}
	}

	private I18nServlet.I18nData _getI18nData(Group group, String path) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		if (group != null) {
			mockHttpServletRequest.setAttribute(
				WebKeys.COMPANY_ID, group.getCompanyId());

			mockHttpServletRequest.setPathInfo(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
					group.getFriendlyURL());
		}

		mockHttpServletRequest.setServletPath(StringPool.SLASH + path);

		return _i18nServlet.getI18nData(mockHttpServletRequest);
	}

	private static Set<Locale> _availableLocales;
	private static Locale _defaultLocale;

	@DeleteAfterTestRun
	private static Group _group;

	private static String[] _localesEnabled;

	private final I18nServlet _i18nServlet = new I18nServlet();
	private boolean _originalLocaleUseDefaultIfNotAvailable;

}