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

package com.liferay.portal.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.action.UpdateLanguageAction;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Ricardo Couso
 */
@RunWith(Arquillian.class)
public class UpdateLanguageActionTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
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
				LocaleUtil.GERMANY, LocaleUtil.FRANCE, LocaleUtil.UK,
				LocaleUtil.US),
			LocaleUtil.US);

		PropsValues.LOCALES_ENABLED = new String[] {
			_language.getLanguageId(LocaleUtil.GERMANY),
			_language.getLanguageId(LocaleUtil.FRANCE),
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
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				_defaultLocale, "Page in Default Locale"
			).put(
				_sourceLocale, "Page in Source Locale"
			).put(
				_targetLocale, "Page in Target Locale"
			).build(),
			HashMapBuilder.put(
				_defaultLocale, "/page-in-default-locale"
			).put(
				_sourceLocale, "/page-in-source-locale"
			).put(
				_targetLocale, "/page-in-target-locale"
			).build());

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				_defaultLocale, "asset"
			).put(
				_sourceLocale, "assetsource"
			).put(
				_sourceUKLocale, "assetuksource"
			).put(
				_targetLocale, "assettarget"
			).build(),
			null,
			HashMapBuilder.put(
				_defaultLocale, "c1"
			).build(),
			_layout.getUuid(), LocaleUtil.getSiteDefault(), null, false, true,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testGetRedirect() throws Exception {
		_testGetRedirectWithPortletFriendlyURL(false);
		_testGetRedirectWithPortletFriendlyURL(true);

		_testGetRedirectWithControlPanelURL(false);
		_testGetRedirectWithControlPanelURL(true);

		_testGetRedirectWithFriendlyURL(false);
		_testGetRedirectWithFriendlyURL(true);
	}

	@Test
	public void testGetRedirectWithFriendlyURLWithVirtualHost()
		throws Exception {

		UpdateLanguageAction updateLanguageAction = new UpdateLanguageAction();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName(_VIRTUAL_HOSTNAME);

		HttpSession httpSession = mockHttpServletRequest.getSession();

		httpSession.setAttribute(WebKeys.LOCALE, _targetLocale);

		mockHttpServletRequest.setParameter(
			"redirect",
			StringBundler.concat(
				StringPool.SLASH, _sourceUKLocale.toLanguageTag(),
				_getFriendlyURLSeparatorPart(_sourceUKLocale), "?queryString"));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		LayoutSet layoutSet = _layout.getLayoutSet();

		layoutSet.setVirtualHostname(_VIRTUAL_HOSTNAME);

		themeDisplay.setI18nLanguageId(_sourceUKLocale.getLanguage());
		themeDisplay.setI18nPath("/" + _sourceUKLocale.getLanguage());
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_group.getPublicLayoutSet());
		themeDisplay.setLocale(_sourceUKLocale);
		themeDisplay.setPortalDomain(_VIRTUAL_HOSTNAME);
		themeDisplay.setPortalURL(Http.HTTP_WITH_SLASH + _VIRTUAL_HOSTNAME);
		themeDisplay.setSiteGroupId(_group.getGroupId());

		Assert.assertEquals(
			StringBundler.concat(
				Http.HTTP_WITH_SLASH, _VIRTUAL_HOSTNAME,
				_getFriendlyURLSeparatorPart(_targetLocale), "?queryString"),
			updateLanguageAction.getRedirect(
				mockHttpServletRequest, themeDisplay, _targetLocale));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetRedirectWithInvalidRedirectParameter() throws Exception {
		UpdateLanguageAction updateLanguageAction = new UpdateLanguageAction();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"redirect",
			RandomTestUtil.randomString() + " " +
				RandomTestUtil.randomString());

		updateLanguageAction.getRedirect(
			mockHttpServletRequest, new ThemeDisplay(), _targetLocale);
	}

	@Test(expected = NoSuchLayoutException.class)
	public void testGetRedirectWithNoSuchLayoutRedirectParameter()
		throws Exception {

		UpdateLanguageAction updateLanguageAction = new UpdateLanguageAction();

		String testURLSeparator = null;

		for (String urlSeparator :
				FriendlyURLResolverRegistryUtil.getURLSeparators()) {

			if (!Portal.FRIENDLY_URL_SEPARATOR.equals(urlSeparator) &&
				!VirtualLayoutConstants.CANONICAL_URL_SEPARATOR.equals(
					urlSeparator)) {

				testURLSeparator = urlSeparator;

				break;
			}
		}

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter("p_l_id", "0");
		mockHttpServletRequest.setParameter(
			"redirect", testURLSeparator + "no-such-page");

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(_layout);

		updateLanguageAction.getRedirect(
			mockHttpServletRequest, themeDisplay, _targetLocale);
	}

	private void _assertRedirect(
			ThemeDisplay themeDisplay, String expectedRedirect, String url)
		throws Exception {

		UpdateLanguageAction updateLanguageAction = new UpdateLanguageAction();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		HttpSession httpSession = mockHttpServletRequest.getSession();

		httpSession.setAttribute(WebKeys.LOCALE, _targetLocale);

		mockHttpServletRequest.setParameter("redirect", url);

		String redirect = updateLanguageAction.getRedirect(
			mockHttpServletRequest, themeDisplay, _targetLocale);

		Assert.assertEquals(expectedRedirect, redirect);
	}

	private String _getFriendlyURLSeparatorPart(Locale locale)
		throws Exception {

		return _getFriendlyURLSeparatorPart(
			locale, Portal.FRIENDLY_URL_SEPARATOR);
	}

	private String _getFriendlyURLSeparatorPart(Locale locale, String separator)
		throws Exception {

		Map<Locale, String> friendlyURLMap =
			_journalArticle.getFriendlyURLMap();

		return separator + friendlyURLMap.get(locale);
	}

	private void _testGetRedirectWithControlPanelURL(boolean i18n)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		if (i18n) {
			themeDisplay.setI18nLanguageId(_sourceLocale.getLanguage());
			themeDisplay.setI18nPath("/" + _sourceLocale.getLanguage());
			themeDisplay.setLocale(_sourceLocale);
		}

		Layout controlPanelLayout = LayoutLocalServiceUtil.getLayout(
			PortalUtil.getControlPanelPlid(_group.getCompanyId()));

		themeDisplay.setLayout(controlPanelLayout);

		String controlPanelURL = StringBundler.concat(
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
			_group.getFriendlyURL(),
			VirtualLayoutConstants.CANONICAL_URL_SEPARATOR,
			GroupConstants.CONTROL_PANEL_FRIENDLY_URL,
			controlPanelLayout.getFriendlyURL());

		controlPanelURL += "?queryString";

		_assertRedirect(themeDisplay, controlPanelURL, controlPanelURL);

		if (i18n) {
			_assertRedirect(
				themeDisplay, controlPanelURL,
				"/" + _sourceLocale.getLanguage() + controlPanelURL);
		}
		else {
			_assertRedirect(
				themeDisplay,
				"/" + _sourceLocale.getLanguage() + controlPanelURL,
				"/" + _sourceLocale.getLanguage() + controlPanelURL);
		}
	}

	private void _testGetRedirectWithFriendlyURL(boolean i18n)
		throws Exception {

		_testGetRedirectWithFriendlyURL(i18n, "", "");
		_testGetRedirectWithFriendlyURL(
			i18n, _getFriendlyURLSeparatorPart(_sourceLocale),
			_getFriendlyURLSeparatorPart(_targetLocale));
		_testGetRedirectWithFriendlyURL(
			i18n,
			_getFriendlyURLSeparatorPart(
				_sourceLocale,
				FriendlyURLResolverConstants.URL_SEPARATOR_JOURNAL_ARTICLE),
			_getFriendlyURLSeparatorPart(
				_targetLocale,
				FriendlyURLResolverConstants.URL_SEPARATOR_JOURNAL_ARTICLE));
	}

	private void _testGetRedirectWithFriendlyURL(
			boolean i18n, String sourceFriendlyURLSeparatorPart,
			String targetFriendlyURLSeparatorPart)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		if (i18n) {
			themeDisplay.setI18nLanguageId(_sourceLocale.getLanguage());
			themeDisplay.setI18nPath("/" + _sourceLocale.getLanguage());
			themeDisplay.setLocale(_sourceLocale);
		}

		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_group.getPublicLayoutSet());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		String targetURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL(_targetLocale);

		targetURL += targetFriendlyURLSeparatorPart + "?queryString";

		String sourceURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL(_sourceLocale);

		sourceURL += sourceFriendlyURLSeparatorPart + "?queryString";

		_assertRedirect(themeDisplay, targetURL, sourceURL);
		_assertRedirect(
			themeDisplay, targetURL,
			"/" + _sourceLocale.getLanguage() + sourceURL);
	}

	private void _testGetRedirectWithPortletFriendlyURL(boolean i18n)
		throws Exception {

		Map<Locale, String> friendlyURLMap =
			_journalArticle.getFriendlyURLMap();

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			_group.getDefaultLanguageId());

		String path =
			_PORTLET_FRIENDLY_URL_PART_ASSET_PUBLISHER +
				friendlyURLMap.get(defaultLocale);

		_testGetRedirectWithPortletFriendlyURL(i18n, path);
	}

	private void _testGetRedirectWithPortletFriendlyURL(
			boolean i18n, String path)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		if (i18n) {
			themeDisplay.setI18nLanguageId(_sourceLocale.getLanguage());
			themeDisplay.setI18nPath("/" + _sourceLocale.getLanguage());
			themeDisplay.setLocale(_sourceLocale);
		}

		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_group.getPublicLayoutSet());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		String targetURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL(_targetLocale);

		targetURL += path + "?queryString";

		String sourceURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL(_sourceLocale);

		sourceURL += path + "?queryString";

		_assertRedirect(themeDisplay, targetURL, sourceURL);
		_assertRedirect(
			themeDisplay, targetURL,
			"/" + _sourceLocale.getLanguage() + sourceURL);
	}

	private static final String _PORTLET_FRIENDLY_URL_PART_ASSET_PUBLISHER =
		"/-/asset_publisher/instanceID/content/";

	private static final String _VIRTUAL_HOSTNAME = "test.com";

	private static Set<Locale> _availableLocales;
	private static Locale _defaultLocale;

	@Inject
	private static Language _language;

	private static String[] _localesEnabled;

	@Inject
	private static Portal _portal;

	private Group _group;
	private JournalArticle _journalArticle;
	private Layout _layout;
	private final Locale _sourceLocale = LocaleUtil.FRANCE;
	private final Locale _sourceUKLocale = LocaleUtil.UK;
	private final Locale _targetLocale = LocaleUtil.GERMANY;

}