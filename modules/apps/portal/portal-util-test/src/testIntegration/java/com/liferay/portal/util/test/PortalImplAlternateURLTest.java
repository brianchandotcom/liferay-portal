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
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutFriendlyURLRandomizerBumper;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

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
	public void testAlternateURLWithAssetDisplayPageEntry() throws Exception {
		_group = GroupTestUtil.addGroup();

		Collection<Locale> availableLocales = Arrays.asList(
			LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY);

		Locale defaultLocale = LocaleUtil.US;

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, defaultLocale);

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.GERMANY,
			FriendlyURLNormalizerUtil.normalize(
				RandomTestUtil.randomString(
					LayoutFriendlyURLRandomizerBumper.INSTANCE))
		).put(
			LocaleUtil.SPAIN,
			FriendlyURLNormalizerUtil.normalize(
				RandomTestUtil.randomString(
					LayoutFriendlyURLRandomizerBumper.INSTANCE))
		).put(
			LocaleUtil.US,
			FriendlyURLNormalizerUtil.normalize(
				RandomTestUtil.randomString(
					LayoutFriendlyURLRandomizerBumper.INSTANCE))
		).build();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, friendlyURLMap);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addAssetDisplayPageEntry(journalArticle);

		ThemeDisplay themeDisplay = _getThemeDisplay(
			_group,
			_layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid()));

		_testAlternateURLWithAssetDisplayPageEntry(
			availableLocales, defaultLocale, friendlyURLMap, 0,
			journalArticle.getResourcePrimKey(), themeDisplay);
		_testAlternateURLWithAssetDisplayPageEntry(
			availableLocales, defaultLocale, friendlyURLMap, 1,
			journalArticle.getResourcePrimKey(), themeDisplay);
		_testAlternateURLWithAssetDisplayPageEntry(
			availableLocales, defaultLocale, friendlyURLMap, 2,
			journalArticle.getResourcePrimKey(), themeDisplay);
		_testAlternateURLWithAssetDisplayPageEntry(
			availableLocales, defaultLocale, friendlyURLMap, 3,
			journalArticle.getResourcePrimKey(), themeDisplay);
	}

	@Test
	public void testAlternateURLWithFriendlyURL() throws Exception {
		_testAlternateURLWithFriendlyURL(
			"liferay.com",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.US, LocaleUtil.BRAZIL, "/pt-BR");
		_testAlternateURLWithFriendlyURL(
			"liferay.com",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.US, LocaleUtil.SPAIN, "/es");
		_testAlternateURLWithFriendlyURL(
			"localhost",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.US, LocaleUtil.BRAZIL, "/pt-BR");
		_testAlternateURLWithFriendlyURL(
			"localhost",
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY),
			LocaleUtil.US, LocaleUtil.SPAIN, "/es");
	}

	@Test
	public void testAlternateURLWithLayout() throws Exception {
		_group = GroupTestUtil.addGroup();

		Collection<Locale> availableLocales = Arrays.asList(
			LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY);

		Locale defaultLocale = LocaleUtil.US;

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, defaultLocale);

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			LocaleUtil.GERMANY,
			StringPool.SLASH.concat(
				FriendlyURLNormalizerUtil.normalize(
					RandomTestUtil.randomString(
						LayoutFriendlyURLRandomizerBumper.INSTANCE)))
		).put(
			LocaleUtil.SPAIN,
			StringPool.SLASH.concat(
				FriendlyURLNormalizerUtil.normalize(
					RandomTestUtil.randomString(
						LayoutFriendlyURLRandomizerBumper.INSTANCE)))
		).put(
			LocaleUtil.US,
			StringPool.SLASH.concat(
				FriendlyURLNormalizerUtil.normalize(
					RandomTestUtil.randomString(
						LayoutFriendlyURLRandomizerBumper.INSTANCE)))
		).build();

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				LocaleUtil.GERMANY, RandomTestUtil.randomString()
			).put(
				LocaleUtil.SPAIN, RandomTestUtil.randomString()
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			friendlyURLMap);

		ThemeDisplay themeDisplay = _getThemeDisplay(_group, layout);

		_testAlternateURLWithLayout(
			availableLocales, defaultLocale, friendlyURLMap, 0, themeDisplay);
		_testAlternateURLWithLayout(
			availableLocales, defaultLocale, friendlyURLMap, 1, themeDisplay);
		_testAlternateURLWithLayout(
			availableLocales, defaultLocale, friendlyURLMap, 2, themeDisplay);
		_testAlternateURLWithLayout(
			availableLocales, defaultLocale, friendlyURLMap, 3, themeDisplay);
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

	private LayoutPageTemplateEntry _addAssetDisplayPageEntry(
			JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				journalArticle.getGroupId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(), journalArticle.getGroupId(), 0,
				_portal.getClassNameId(JournalArticle.class.getName()),
				ddmStructure.getStructureId(), RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0, serviceContext);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), journalArticle.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC, serviceContext);

		return layoutPageTemplateEntry;
	}

	private String _generateAssetDisplayPageEntryURL(
		Locale defaultLocale, String friendlyURL, String groupFriendlyURL,
		Locale locale, String portalURL) {

		return StringBundler.concat(
			portalURL, _getI18nPath(defaultLocale, locale),
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL,
			FriendlyURLResolverConstants.URL_SEPARATOR_JOURNAL_ARTICLE,
			friendlyURL);
	}

	private String _generateAssetPublisherContentURL(
		String portalDomain, String languageId, String groupFriendlyURL) {

		return StringBundler.concat(
			"http://", portalDomain, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			Portal.FRIENDLY_URL_SEPARATOR, "asset_publisher", groupFriendlyURL,
			"/content/content-title");
	}

	private String _generateLayoutURL(
		Locale defaultLocale, String friendlyURL, String groupFriendlyURL,
		Locale locale, String portalURL) {

		return StringBundler.concat(
			portalURL, _getI18nPath(defaultLocale, locale),
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, friendlyURL);
	}

	private String _generateURL(
		String portalDomain, String languageId, String groupFriendlyURL,
		String layoutFriendlyURL) {

		return StringBundler.concat(
			"http://", portalDomain, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, layoutFriendlyURL);
	}

	private String _getI18nPath(Locale defaultLocale, Locale locale) {
		String i18nPath = StringPool.BLANK;

		if ((PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2) ||
			((PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE != 0) &&
			 !locale.equals(defaultLocale))) {

			i18nPath = StringPool.SLASH + locale.getLanguage();
		}

		return i18nPath;
	}

	private ThemeDisplay _getThemeDisplay(Group group, Layout layout)
		throws Exception {

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		ThemeDisplay themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			company, _group, layout);

		themeDisplay.setPortalDomain("localhost");
		themeDisplay.setPortalURL(company.getPortalURL(group.getGroupId()));
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		themeDisplay.setRequest(mockHttpServletRequest);

		return themeDisplay;
	}

	private ThemeDisplay _getThemeDisplay(Group group, String portalURL)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		themeDisplay.setLayoutSet(group.getPublicLayoutSet());
		themeDisplay.setPortalDomain(HttpComponentsUtil.getDomain(portalURL));
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

		themeDisplay.setPortalDomain(HttpComponentsUtil.getDomain(portalURL));
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

	private void _testAlternateURLWithAssetDisplayPageEntry(
			Collection<Locale> availableLocales, Locale defaultLocale,
			Map<Locale, String> friendlyURLMap, int prependFriendlyURLStyle,
			long resourcePrimKey, ThemeDisplay themeDisplay)
		throws Exception {

		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				prependFriendlyURLStyle;

			for (Locale alternateLocale : availableLocales) {
				String expectedAlternateURL = _generateAssetDisplayPageEntryURL(
					defaultLocale, friendlyURLMap.get(alternateLocale),
					_group.getFriendlyURL(), alternateLocale,
					themeDisplay.getPortalURL());

				String canonicalURL =
					_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
						JournalArticle.class.getName(), resourcePrimKey,
						alternateLocale, themeDisplay);

				Assert.assertEquals(
					expectedAlternateURL,
					_portal.getAlternateURL(
						canonicalURL, themeDisplay, alternateLocale,
						themeDisplay.getLayout()));
			}
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	private void _testAlternateURLWithFriendlyURL(
			String portalDomain, Collection<Locale> groupAvailableLocales,
			Locale groupDefaultLocale, Locale alternateLocale,
			String expectedI18nPath)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> friendlyURLMap = new HashMap<>();

		for (Locale availableLocale : groupAvailableLocales) {
			nameMap.put(
				availableLocale, "welcome-" + availableLocale.getCountry());
			friendlyURLMap.put(
				availableLocale,
				"/friendlyurl-" + availableLocale.getCountry());
		}

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), false, nameMap, friendlyURLMap);

		String canonicalURL = _generateURL(
			portalDomain, StringPool.BLANK, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		String expectedAlternateURL = _generateURL(
			portalDomain, expectedI18nPath, _group.getFriendlyURL(),
			layout.getFriendlyURL(alternateLocale));

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

	private void _testAlternateURLWithLayout(
			Collection<Locale> availableLocales, Locale defaultLocale,
			Map<Locale, String> friendlyURLMap, int prependFriendlyURLStyle,
			ThemeDisplay themeDisplay)
		throws Exception {

		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				prependFriendlyURLStyle;

			String canonicalURL = _generateLayoutURL(
				defaultLocale, friendlyURLMap.get(defaultLocale),
				_group.getFriendlyURL(), defaultLocale,
				themeDisplay.getPortalURL());

			for (Locale alternateLocale : availableLocales) {
				String expectedAlternateURL = _generateLayoutURL(
					defaultLocale, friendlyURLMap.get(alternateLocale),
					_group.getFriendlyURL(), alternateLocale,
					themeDisplay.getPortalURL());

				Assert.assertEquals(
					expectedAlternateURL,
					_portal.getAlternateURL(
						canonicalURL, themeDisplay, alternateLocale,
						themeDisplay.getLayout()));
			}
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
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
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Language _language;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

}