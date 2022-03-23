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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class PortalImplAlternateURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortalException {
		_defaultLocale = LocaleUtil.getDefault();
		_defaultPrependStyle = PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		LocaleUtil.setDefault(
			LocaleUtil.US.getLanguage(), LocaleUtil.US.getCountry(),
			LocaleUtil.US.getVariant());

		_virtualHostLocalService.updateVirtualHosts(
			TestPropsValues.getCompanyId(), 0,
			TreeMapBuilder.put(
				"localhost", StringPool.BLANK
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		LocaleUtil.setDefault(
			_defaultLocale.getLanguage(), _defaultLocale.getCountry(),
			_defaultLocale.getVariant());

		TestPropsUtil.set(
			PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE,
			GetterUtil.getString(_defaultPrependStyle));
	}

	@Test
	public void testAlternateURLsMatchSiteAvailableLocalesFromSitemap()
		throws Exception {

		_testAlternateURLsForSitemapFromGuestGroup(
			"localhost",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.US);
	}

	@Test
	public void testAlternativeVirtualHostDefaultPortalLocaleAlternateURL()
		throws Exception {

		_testAlternateURLWithVirtualHosts(
			"test.com", null, null, LocaleUtil.US, StringPool.BLANK);
	}

	@Test
	public void testAlternativeVirtualHostLocalizedSiteCustomSiteLocaleAlternateURL()
		throws Exception {

		_testAlternateURLWithVirtualHosts(
			"test.com",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.SPAIN, LocaleUtil.US, "/en");
	}

	@Test
	public void testCustomPortalLocaleAlternateURL() throws Exception {
		_testAlternateURL("localhost", null, null, LocaleUtil.SPAIN, "/es");
	}

	@Test
	public void testDefaultPortalLocaleAlternateURL() throws Exception {
		_testAlternateURL(
			"localhost", null, null, LocaleUtil.US, StringPool.BLANK);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleAlternateURL()
		throws Exception {

		_testAlternateURL(
			"localhost",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.SPAIN, LocaleUtil.US, "/en");
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleAlternateURL()
		throws Exception {

		_testAlternateURL(
			"localhost",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.SPAIN, LocaleUtil.SPAIN, StringPool.BLANK);
	}

	@Test
	public void testNonlocalhostCustomPortalLocaleAlternateURL()
		throws Exception {

		_testAlternateURL("liferay.com", null, null, LocaleUtil.SPAIN, "/es");
	}

	@Test
	public void testNonlocalhostDefaultPortalLocaleAlternateURL()
		throws Exception {

		_testAlternateURL(
			"liferay.com", null, null, LocaleUtil.US, StringPool.BLANK);
	}

	@Test
	public void testNonlocalhostLocalizedSiteCustomSiteLocaleAlternateURL()
		throws Exception {

		_testAlternateURL(
			"liferay.com",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.SPAIN, LocaleUtil.US, "/en");
	}

	@Test
	public void testNonlocalhostLocalizedSiteDefaultSiteLocaleAlternateURL()
		throws Exception {

		_testAlternateURL(
			"liferay.com",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.SPAIN, LocaleUtil.SPAIN, StringPool.BLANK);
	}

	private String _generateAssetPublisherContentURL(
		String portalDomain, String languageId, String groupFriendlyURL) {

		return StringBundler.concat(
			"http://", portalDomain, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			Portal.FRIENDLY_URL_SEPARATOR, "asset_publisher", groupFriendlyURL,
			"/content/content-title");
	}

	private String _generateURL(
		String portalDomain, String languageId, String groupFriendlyURL,
		String layoutFriendlyURL) {

		return StringBundler.concat(
			"http://", portalDomain, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, layoutFriendlyURL);
	}

	private ThemeDisplay _getThemeDisplay(Group group, String portalURL)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		themeDisplay.setLayoutSet(group.getPublicLayoutSet());
		themeDisplay.setPortalDomain(_http.getDomain(portalURL));
		themeDisplay.setPortalURL(portalURL);
		themeDisplay.setSiteGroupId(group.getGroupId());

		return themeDisplay;
	}

	private ThemeDisplay _getThemeDisplayWithVirtualHosts(
			Group group, String portalURL)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		LayoutSet layoutSet = group.getPublicLayoutSet();

		layoutSet.setVirtualHostnames(
			TreeMapBuilder.put(
				"liferay.com", StringPool.BLANK
			).put(
				"test.com", LocaleUtil.US.toString()
			).build());

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setPortalDomain(_http.getDomain(portalURL));
		themeDisplay.setPortalURL(portalURL);
		themeDisplay.setSiteGroupId(group.getGroupId());

		return themeDisplay;
	}

	private void _testAlternateURL(
			String portalDomain, Collection<Locale> groupAvailableLocales,
			Locale groupDefaultLocale, Locale alternateLocale,
			String expectedI18nPath)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), "welcome", false);

		String canonicalURL = _generateURL(
			portalDomain, StringPool.BLANK, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		String expectedAlternateURL = _generateURL(
			portalDomain, expectedI18nPath, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		Assert.assertEquals(
			expectedAlternateURL,
			_portal.getAlternateURL(
				canonicalURL, _getThemeDisplay(_group, canonicalURL),
				alternateLocale, layout));

		String canonicalAssetPublisherContentURL =
			_generateAssetPublisherContentURL(
				portalDomain, StringPool.BLANK, _group.getFriendlyURL());

		String expectedAssetPublisherContentAlternateURL =
			_generateAssetPublisherContentURL(
				portalDomain, expectedI18nPath, _group.getFriendlyURL());

		Assert.assertEquals(
			expectedAssetPublisherContentAlternateURL,
			_portal.getAlternateURL(
				canonicalAssetPublisherContentURL,
				_getThemeDisplay(_group, canonicalAssetPublisherContentURL),
				alternateLocale, layout));

		TestPropsUtil.set(PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE, "2");

		Assert.assertEquals(
			expectedAlternateURL,
			_portal.getAlternateURL(
				canonicalURL, _getThemeDisplay(_group, canonicalURL),
				alternateLocale, layout));

		Assert.assertEquals(
			expectedAssetPublisherContentAlternateURL,
			_portal.getAlternateURL(
				canonicalAssetPublisherContentURL,
				_getThemeDisplay(_group, canonicalAssetPublisherContentURL),
				alternateLocale, layout));
	}

	private void _testAlternateURLsForSitemapFromGuestGroup(
			String portalDomain, Collection<Locale> groupAvailableLocales,
			Locale groupDefaultLocale)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), "welcome", false);

		String canonicalURL = _generateURL(
			portalDomain, StringPool.BLANK, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		Map<Locale, String> alternateURLs = _portal.getAlternateURLs(
			canonicalURL,
			_getThemeDisplay(
				GroupLocalServiceUtil.getGroup(
					_group.getCompanyId(), GroupConstants.GUEST),
				canonicalURL),
			layout);

		Assert.assertEquals(
			alternateURLs.toString(), groupAvailableLocales.size(),
			alternateURLs.size());

		for (Locale locale : groupAvailableLocales) {
			Assert.assertTrue(
				alternateURLs.toString(), alternateURLs.containsKey(locale));
		}
	}

	private void _testAlternateURLWithVirtualHosts(
			String portalDomain, Collection<Locale> groupAvailableLocales,
			Locale groupDefaultLocale, Locale alternateLocale,
			String expectedI18nPath)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), "welcome", false);

		String canonicalURL = _generateURL(
			portalDomain, StringPool.BLANK, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		String expectedAlternateURL = _generateURL(
			portalDomain, expectedI18nPath, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		Assert.assertEquals(
			expectedAlternateURL,
			_portal.getAlternateURL(
				canonicalURL, _getThemeDisplay(_group, canonicalURL),
				alternateLocale, layout));

		String canonicalAssetPublisherContentURL =
			_generateAssetPublisherContentURL(
				portalDomain, StringPool.BLANK, _group.getFriendlyURL());

		String expectedAssetPublisherContentAlternateURL =
			_generateAssetPublisherContentURL(
				portalDomain, expectedI18nPath, _group.getFriendlyURL());

		Assert.assertEquals(
			expectedAssetPublisherContentAlternateURL,
			_portal.getAlternateURL(
				canonicalAssetPublisherContentURL,
				_getThemeDisplayWithVirtualHosts(
					_group, canonicalAssetPublisherContentURL),
				alternateLocale, layout));

		TestPropsUtil.set(PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE, "2");

		Assert.assertEquals(
			expectedAlternateURL,
			_portal.getAlternateURL(
				canonicalURL,
				_getThemeDisplayWithVirtualHosts(_group, canonicalURL),
				alternateLocale, layout));

		Assert.assertEquals(
			expectedAssetPublisherContentAlternateURL,
			_portal.getAlternateURL(
				canonicalAssetPublisherContentURL,
				_getThemeDisplayWithVirtualHosts(
					_group, canonicalAssetPublisherContentURL),
				alternateLocale, layout));
	}

	private static Locale _defaultLocale;
	private static int _defaultPrependStyle;

	@Inject
	private static VirtualHostLocalService _virtualHostLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Http _http;

	@Inject
	private Portal _portal;

}