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

package com.liferay.portal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Juan González
 */
@RunWith(Arquillian.class)
public class I18nServletTest extends I18nServlet {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_availableLocales = _language.getAvailableLocales();
		_defaultLocale = LocaleUtil.getDefault();
		_localesEnabled = PropsValues.LOCALES_ENABLED;

		_language.init();

		CompanyTestUtil.resetCompanyLocales(
			_portal.getDefaultCompanyId(),
			Arrays.asList(
				LocaleUtil.CANADA_FRENCH, LocaleUtil.SPAIN, LocaleUtil.UK,
				LocaleUtil.US),
			LocaleUtil.US);

		PropsValues.LOCALES_ENABLED = new String[] {
			_language.getLanguageId(LocaleUtil.CANADA_FRENCH),
			_language.getLanguageId(LocaleUtil.SPAIN),
			_language.getLanguageId(LocaleUtil.UK),
			_language.getLanguageId(LocaleUtil.US)
		};
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_language.init();

		CompanyTestUtil.resetCompanyLocales(
			_portal.getDefaultCompanyId(), _availableLocales, _defaultLocale);

		PropsValues.LOCALES_ENABLED = _localesEnabled;
	}

	@Before
	public void setUp() throws Exception {
		_originalLocaleUseDefaultIfNotAvailable =
			PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE;

		_language.init();

		_group = GroupTestUtil.addGroup();

		UnicodeProperties typeSettingsProperties =
			_group.getTypeSettingsProperties();

		typeSettingsProperties.put(
			GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES, "false");

		_group.setTypeSettingsProperties(typeSettingsProperties);

		_groupLocalService.updateGroup(_group);
	}

	@After
	public void tearDown() {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE =
			_originalLocaleUseDefaultIfNotAvailable;
	}

	@Test
	public void testDefaultCompanyI18nData() throws Exception {
		_testIsDefaultLocale(null, LocaleUtil.US);
		_testIsDefaultOrFirstI18nData(null, LocaleUtil.US);

		_testIsFirstLocale(null, LocaleUtil.CANADA_FRENCH);
		_testIsDefaultOrFirstI18nData(null, LocaleUtil.CANADA_FRENCH);
		_testIsFirstLocale(null, LocaleUtil.SPAIN);
		_testIsDefaultOrFirstI18nData(null, LocaleUtil.SPAIN);

		_testIsNotDefaultOrFirstLocale(null, LocaleUtil.UK);
		_testIsNotDefaultOrFirstI18nData(null, LocaleUtil.UK, LocaleUtil.US);
	}

	@Test
	public void testDefaultGroupI18nData() throws Exception {
		_language.resetAvailableGroupLocales(_group.getGroupId());

		_testIsDefaultLocale(_group, LocaleUtil.US);
		_testIsDefaultOrFirstI18nData(_group, LocaleUtil.US);

		_testIsFirstLocale(_group, LocaleUtil.CANADA_FRENCH);
		_testIsDefaultOrFirstI18nData(_group, LocaleUtil.CANADA_FRENCH);
		_testIsFirstLocale(_group, LocaleUtil.SPAIN);
		_testIsDefaultOrFirstI18nData(_group, LocaleUtil.SPAIN);

		_testIsNotDefaultOrFirstLocale(_group, LocaleUtil.UK);
		_testIsNotDefaultOrFirstI18nData(_group, LocaleUtil.UK, LocaleUtil.US);
	}

	@Test
	public void testI18nLanguageTag() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = false;

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);
		mockHttpServletRequest.setServletPath(
			StringPool.SLASH + LocaleUtil.CANADA_FRENCH.toLanguageTag());

		Assert.assertEquals(
			getI18nData(LocaleUtil.CANADA_FRENCH),
			getI18nData(mockHttpServletRequest));
	}

	@Test
	public void testI18nNotUseDefaultExistentLocale() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = false;

		_testGetI18nData(
			LocaleUtil.getDefault(), getI18nData(LocaleUtil.getDefault()));
	}

	@Test
	public void testI18nNotUseDefaultNondefaultLocale() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = false;

		_testGetI18nData(LocaleUtil.SPAIN, getI18nData(LocaleUtil.SPAIN));
	}

	@Test
	public void testI18nNotUseDefaultNonexistentLocale() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = false;

		_testGetI18nData(LocaleUtil.CHINA, null);
	}

	@Test
	public void testI18nUseDefault() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = true;

		_testGetI18nData(
			LocaleUtil.getDefault(), getI18nData(LocaleUtil.getDefault()));
	}

	@Test
	public void testI18nUseDefaultNonexistentLocale() throws Exception {
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = true;

		_testGetI18nData(
			LocaleUtil.CHINA, getI18nData(LocaleUtil.getDefault()));
	}

	@Test
	public void testModifiedGroupI18nData() throws Exception {
		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(
				LocaleUtil.CANADA_FRENCH, LocaleUtil.SPAIN, LocaleUtil.UK,
				LocaleUtil.US),
			LocaleUtil.SPAIN);

		_testIsDefaultLocale(_group, LocaleUtil.SPAIN);
		_testIsDefaultOrFirstI18nData(_group, LocaleUtil.SPAIN);

		_testIsFirstLocale(_group, LocaleUtil.CANADA_FRENCH);
		_testIsDefaultOrFirstI18nData(_group, LocaleUtil.CANADA_FRENCH);
		_testIsFirstLocale(_group, LocaleUtil.UK);
		_testIsDefaultOrFirstI18nData(_group, LocaleUtil.UK);

		_testIsNotDefaultOrFirstLocale(_group, LocaleUtil.US);
		_testIsNotDefaultOrFirstI18nData(_group, LocaleUtil.US, LocaleUtil.UK);
	}

	@Test
	public void testSendRedirectWithContext() throws Exception {
		MockServletContext mockServletContext = new MockServletContext();

		String contextPath = StringPool.SLASH + RandomTestUtil.randomString(10);

		mockServletContext.setContextPath(contextPath);

		init(new MockServletConfig(mockServletContext));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServletPath(
			String.format(
				"/%s_%s", LocaleUtil.CANADA_FRENCH.getLanguage(),
				LocaleUtil.CANADA_FRENCH.getCountry()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		sendRedirect(
			mockHttpServletRequest, mockHttpServletResponse,
			getI18nData(mockHttpServletRequest));

		Assert.assertEquals(
			HttpServletResponse.SC_MOVED_PERMANENTLY,
			mockHttpServletResponse.getStatus());

		Assert.assertEquals(
			String.format(
				"%s/%s-%s/", contextPath,
				LocaleUtil.CANADA_FRENCH.getLanguage(),
				LocaleUtil.CANADA_FRENCH.getCountry()),
			mockHttpServletResponse.getHeader("Location"));
	}

	@Test
	public void testSendRedirectWithoutContext() throws Exception {
		init(new MockServletConfig(new MockServletContext()));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServletPath(
			String.format(
				"/%s_%s", LocaleUtil.CANADA_FRENCH.getLanguage(),
				LocaleUtil.CANADA_FRENCH.getCountry()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		sendRedirect(
			mockHttpServletRequest, mockHttpServletResponse,
			getI18nData(mockHttpServletRequest));

		Assert.assertEquals(
			HttpServletResponse.SC_MOVED_PERMANENTLY,
			mockHttpServletResponse.getStatus());

		Assert.assertEquals(
			String.format(
				"/%s-%s/", LocaleUtil.CANADA_FRENCH.getLanguage(),
				LocaleUtil.CANADA_FRENCH.getCountry()),
			mockHttpServletResponse.getHeader("Location"));
	}

	private Locale _getDefaultLocale(Group group) throws Exception {
		if (group != null) {
			return _portal.getSiteDefaultLocale(group);
		}

		return LocaleUtil.getDefault();
	}

	private Locale _getFirstLocale(Group group, String language) {
		if (group != null) {
			return _language.getLocale(group.getGroupId(), language);
		}

		return _language.getLocale(language);
	}

	private I18nServlet.I18nData _getI18nData(Group group, String path)
		throws PortalException {

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

		return getI18nData(mockHttpServletRequest);
	}

	private void _testGetI18nData(
			Locale locale, I18nServlet.I18nData expectedI18nData)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);
		mockHttpServletRequest.setServletPath(
			StringPool.SLASH + LocaleUtil.toLanguageId(locale));

		Assert.assertEquals(
			expectedI18nData, getI18nData(mockHttpServletRequest));
	}

	private void _testIsDefaultLocale(Group group, Locale expectedDefaultLocale)
		throws Exception {

		Assert.assertEquals(expectedDefaultLocale, _getDefaultLocale(group));
	}

	private void _testIsDefaultOrFirstI18nData(Group group, Locale locale)
		throws Exception {

		String languageId = LocaleUtil.toLanguageId(locale);

		I18nServlet.I18nData languageAndLocalePath = _getI18nData(
			group, languageId);

		Assert.assertEquals(languageAndLocalePath.getLanguageId(), languageId);

		Assert.assertEquals(
			_getI18nData(group, locale.getLanguage()), languageAndLocalePath);
	}

	private void _testIsFirstLocale(Group group, Locale expectedFirstLocale)
		throws Exception {

		Assert.assertEquals(
			expectedFirstLocale,
			_getFirstLocale(group, expectedFirstLocale.getLanguage()));

		Assert.assertNotEquals(expectedFirstLocale, _getDefaultLocale(group));
	}

	private void _testIsNotDefaultOrFirstI18nData(
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

	private void _testIsNotDefaultOrFirstLocale(Group group, Locale locale)
		throws Exception {

		Locale defaultLocale = _getDefaultLocale(group);

		if (!_language.isSameLanguage(defaultLocale, locale)) {
			defaultLocale = _getFirstLocale(group, locale.getLanguage());
		}

		Assert.assertNotEquals(defaultLocale, locale);
	}

	private static Set<Locale> _availableLocales;
	private static Locale _defaultLocale;

	@Inject
	private static Language _language;

	private static String[] _localesEnabled;

	@Inject
	private static Portal _portal;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private boolean _originalLocaleUseDefaultIfNotAvailable;

}