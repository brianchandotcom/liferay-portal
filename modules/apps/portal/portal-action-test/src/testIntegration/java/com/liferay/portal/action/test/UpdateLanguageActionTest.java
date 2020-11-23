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
import com.liferay.portal.action.UpdateLanguageAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(
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
		_testGetRedirectWithAssetPublisherPortlet(false);
		_testGetRedirectWithAssetPublisherPortlet(true);

		_testGetRedirectWithControlPanelURL(false);
		_testGetRedirectWithControlPanelURL(true);

		_testGetRedirectWithFriendlyURL(false);
		_testGetRedirectWithFriendlyURL(true);
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

	private void _testGetRedirectWithAssetPublisherPortlet(boolean i18n)
		throws Exception {

		Map<Locale, String> friendlyURLMap =
			_journalArticle.getFriendlyURLMap();

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			_group.getDefaultLanguageId());

		String path =
			_ASSET_PUBLISHER_URL_PART + friendlyURLMap.get(defaultLocale);

		_testGetRedirectWithAssetPublisherPortlet(i18n, path);
	}

	private void _testGetRedirectWithAssetPublisherPortlet(
			boolean i18n, String path)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		if (i18n) {
			themeDisplay.setI18nLanguageId(_sourceLocale.getLanguage());
			themeDisplay.setI18nPath("/" + _sourceLocale.getLanguage());
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

	private void _testGetRedirectWithControlPanelURL(boolean i18n)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		if (i18n) {
			themeDisplay.setI18nLanguageId(_sourceLocale.getLanguage());
			themeDisplay.setI18nPath("/" + _sourceLocale.getLanguage());
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
				_sourceLocale, _FRIENDLY_URL_SEPARATOR_JOURNAL_ARTICLE),
			_getFriendlyURLSeparatorPart(
				_targetLocale, _FRIENDLY_URL_SEPARATOR_JOURNAL_ARTICLE));
	}

	private void _testGetRedirectWithFriendlyURL(
			boolean i18n, String sourceFriendlyURLSeparatorPart,
			String targetFriendlyURLSeparatorPart)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		if (i18n) {
			themeDisplay.setI18nLanguageId(_sourceLocale.getLanguage());
			themeDisplay.setI18nPath("/" + _sourceLocale.getLanguage());
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

	private static final String _ASSET_PUBLISHER_URL_PART =
		"/-/asset_publisher/instanceID/content/";

	private static final String _FRIENDLY_URL_SEPARATOR_JOURNAL_ARTICLE = "/w/";

	private final Locale _defaultLocale = LocaleUtil.US;
	private Group _group;
	private JournalArticle _journalArticle;
	private Layout _layout;
	private final Locale _sourceLocale = LocaleUtil.FRANCE;
	private final Locale _targetLocale = LocaleUtil.GERMANY;

}