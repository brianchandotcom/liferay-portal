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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.action.UpdateLanguageAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;

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
	public static void setUpClass() {
		_defaultLocale = LocaleUtil.getDefault();

		LocaleUtil.setDefault(
			LocaleUtil.US.getLanguage(), LocaleUtil.US.getCountry(),
			LocaleUtil.US.getVariant());

		_sourceLocalePrepend = StringPool.SLASH + _sourceLocale.getLanguage();
		_targetLocalePrepend = StringPool.SLASH + _targetLocale.getLanguage();
	}

	@AfterClass
	public static void tearDownClass() {
		LocaleUtil.setDefault(
			_defaultLocale.getLanguage(), _defaultLocale.getCountry(),
			_defaultLocale.getVariant());
	}

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

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			company.getCompanyId());

		_controlPanelLayout = LayoutLocalServiceUtil.getLayout(
			controlPanelPlid);
	}

	@Test
	public void testAssetI18nRedirect() throws PortalException {
		ThemeDisplay themeDisplay = _getLayoutThemeDisplay(true);

		String[] urls = _getAssetURLs();

		String bareSourceURL = urls[0];
		String bareDefaultURL = urls[1];

		_assertRedirect(themeDisplay, bareDefaultURL, bareSourceURL);
		_assertRedirect(
			themeDisplay, bareDefaultURL, _sourceLocalePrepend + bareSourceURL);
	}

	@Test
	public void testAssetRedirect() throws PortalException {
		ThemeDisplay themeDisplay = _getLayoutThemeDisplay(false);

		String[] urls = _getAssetURLs();

		String bareSourceURL = urls[0];
		String bareDefaultURL = urls[1];

		_assertRedirect(themeDisplay, bareDefaultURL, bareSourceURL);
		_assertRedirect(
			themeDisplay, bareDefaultURL, _sourceLocalePrepend + bareSourceURL);
	}

	@Test
	public void testControlPanelI18nRedirect() throws PortalException {
		ThemeDisplay themeDisplay = _getControlPanelThemeDisplay(true);

		String bareURL = _getControlPanelURL();

		_assertRedirect(themeDisplay, bareURL, bareURL);
		_assertRedirect(themeDisplay, bareURL, _sourceLocalePrepend + bareURL);
	}

	@Test
	public void testControlPanelRedirect() throws PortalException {
		ThemeDisplay themeDisplay = _getControlPanelThemeDisplay(false);

		String bareURL = _getControlPanelURL();

		_assertRedirect(themeDisplay, bareURL, bareURL);
		_assertRedirect(
			themeDisplay, _sourceLocalePrepend + bareURL,
			_sourceLocalePrepend + bareURL);
	}

	@Test
	public void testPublicPageI18nRedirect() throws PortalException {
		ThemeDisplay themeDisplay = _getLayoutThemeDisplay(true);

		String[] urls = _getPublicPageURLs();

		String bareSourceURL = urls[0];
		String bareDefaultURL = urls[1];

		_assertRedirect(themeDisplay, bareDefaultURL, bareSourceURL);
		_assertRedirect(
			themeDisplay, bareDefaultURL, _sourceLocalePrepend + bareSourceURL);
	}

	@Test
	public void testPublicPageRedirect() throws PortalException {
		ThemeDisplay themeDisplay = _getLayoutThemeDisplay(false);

		String[] urls = _getPublicPageURLs();

		String bareSourceURL = urls[0];
		String bareDefaultURL = urls[1];

		_assertRedirect(themeDisplay, bareDefaultURL, bareSourceURL);
		_assertRedirect(
			themeDisplay, bareDefaultURL, _sourceLocalePrepend + bareSourceURL);
	}

	private void _assertRedirect(
			ThemeDisplay themeDisplay, String expectedRedirect, String url)
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter("redirect", url);

		UpdateLanguageAction updateLanguageAction = new UpdateLanguageAction();

		String redirect = updateLanguageAction.getRedirect(
			mockHttpServletRequest, themeDisplay, _targetLocale);

		Assert.assertEquals(expectedRedirect, redirect);
	}

	private String[] _getAssetURLs() {
		String sourceLocaleLayoutURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL(_sourceLocale);

		String defaultLocaleLayoutURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() +
					_layout.getFriendlyURL(_defaultLocale);

		String assetFriendlyURL = Portal.FRIENDLY_URL_SEPARATOR + "asset";

		String queryString = StringPool.QUESTION + "queryString";

		String bareSourceURL = StringBundler.concat(
			sourceLocaleLayoutURL, assetFriendlyURL, queryString);

		String bareDefaultURL = StringBundler.concat(
			defaultLocaleLayoutURL, assetFriendlyURL, queryString);

		return new String[] {bareSourceURL, bareDefaultURL};
	}

	private ThemeDisplay _getControlPanelThemeDisplay(boolean i18n) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		if (i18n) {
			themeDisplay.setI18nLanguageId(_sourceLocale.getLanguage());
			themeDisplay.setI18nPath(_sourceLocalePrepend);
		}

		themeDisplay.setLayout(_controlPanelLayout);

		return themeDisplay;
	}

	private String _getControlPanelURL() {
		String controlPanelLayoutURL = StringBundler.concat(
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
			_group.getFriendlyURL(),
			VirtualLayoutConstants.CANONICAL_URL_SEPARATOR,
			GroupConstants.CONTROL_PANEL_FRIENDLY_URL,
			_controlPanelLayout.getFriendlyURL());

		String queryString = StringPool.QUESTION + "queryString";

		return controlPanelLayoutURL + queryString;
	}

	private ThemeDisplay _getLayoutThemeDisplay(boolean i18n) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		if (i18n) {
			themeDisplay.setI18nLanguageId(_sourceLocale.getLanguage());
			themeDisplay.setI18nPath(_sourceLocalePrepend);
		}

		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_group.getPublicLayoutSet());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	private String[] _getPublicPageURLs() {
		String sourceLocaleLayoutURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL(_sourceLocale);

		String defaultLocaleLayoutURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() +
					_layout.getFriendlyURL(_defaultLocale);

		String queryString = StringPool.QUESTION + "queryString";

		String bareSourceURL = sourceLocaleLayoutURL + queryString;
		String bareDefaultURL = defaultLocaleLayoutURL + queryString;

		return new String[] {bareSourceURL, bareDefaultURL};
	}

	private static Locale _defaultLocale;
	private static final Locale _sourceLocale = LocaleUtil.FRANCE;
	private static String _sourceLocalePrepend;
	private static final Locale _targetLocale = LocaleUtil.GERMAN;
	private static String _targetLocalePrepend;

	private Layout _controlPanelLayout;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

}