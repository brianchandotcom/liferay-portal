/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.GroupConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.manager.SitemapManager;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
@FeatureFlags("LPS-187793")
@RunWith(Arquillian.class)
public class SitemapManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_setUpThemeDisplay();
	}

	@Test
	public void testSitemapIncludePagesCompanyDisabledGroupDisabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_SITEMAP_COMPANY_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", false
						).build());
			GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _SITEMAP_GROUP_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", false
						).build())) {

			_assertEmptySitemap(_layout.getUuid());
		}
	}

	@Test
	public void testSitemapIncludePagesCompanyDisabledGroupEnabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_SITEMAP_COMPANY_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", false
						).build());
			GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _SITEMAP_GROUP_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", true
						).put(
							"includeWebContent", false
						).build())) {

			_assertEmptySitemap(_layout.getUuid());
		}
	}

	@Test
	public void testSitemapIncludePagesCompanyEnabledGroupDisabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_SITEMAP_COMPANY_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", true
						).put(
							"includeWebContent", false
						).build());
			GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _SITEMAP_GROUP_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", false
						).build())) {

			_assertEmptySitemap(_layout.getUuid());
		}
	}

	@Test
	public void testSitemapIncludePagesCompanyEnabledGroupEnabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_SITEMAP_COMPANY_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", true
						).put(
							"includeWebContent", false
						).build());
			GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _SITEMAP_GROUP_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", true
						).put(
							"includeWebContent", false
						).build())) {

			_assertSitemap(
				_layout.getUuid(),
				_portal.getCanonicalURL(
					_portal.getLayoutFullURL(_layout, _themeDisplay),
					_themeDisplay, _layout));
		}
	}

	@Test
	public void testSitemapIncludeWebContentCompanyDisabledGroupDisabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_SITEMAP_COMPANY_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", false
						).build());
			GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _SITEMAP_GROUP_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", false
						).build())) {

			JournalArticle journalArticle = _addJournalArticle();

			AssetDisplayPageEntry assetDisplayPageEntry =
				_addJournalArticleAssetDisplayPageEntry(journalArticle);

			Layout layout = _layoutLocalService.getLayout(
				assetDisplayPageEntry.getPlid());

			_assertEmptySitemap(layout.getUuid());
		}
	}

	@Test
	public void testSitemapIncludeWebContentCompanyDisabledGroupEnabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_SITEMAP_COMPANY_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", false
						).build());
			GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _SITEMAP_GROUP_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", true
						).build())) {

			JournalArticle journalArticle = _addJournalArticle();

			AssetDisplayPageEntry assetDisplayPageEntry =
				_addJournalArticleAssetDisplayPageEntry(journalArticle);

			Layout layout = _layoutLocalService.getLayout(
				assetDisplayPageEntry.getPlid());

			_assertEmptySitemap(layout.getUuid());
		}
	}

	@Test
	public void testSitemapIncludeWebContentCompanyEnabledGroupDisabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_SITEMAP_COMPANY_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", true
						).build());
			GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _SITEMAP_GROUP_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", false
						).build())) {

			JournalArticle journalArticle = _addJournalArticle();

			AssetDisplayPageEntry assetDisplayPageEntry =
				_addJournalArticleAssetDisplayPageEntry(journalArticle);

			Layout layout = _layoutLocalService.getLayout(
				assetDisplayPageEntry.getPlid());

			_assertEmptySitemap(layout.getUuid());
		}
	}

	@Test
	public void testSitemapIncludeWebContentCompanyEnabledGroupEnabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_SITEMAP_COMPANY_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", true
						).build());
			GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _SITEMAP_GROUP_CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"includeCategories", false
						).put(
							"includePages", false
						).put(
							"includeWebContent", true
						).build())) {

			JournalArticle journalArticle = _addJournalArticle();

			AssetDisplayPageEntry assetDisplayPageEntry =
				_addJournalArticleAssetDisplayPageEntry(journalArticle);

			Layout layout = _layoutLocalService.getLayout(
				assetDisplayPageEntry.getPlid());

			_assertSitemap(
				layout.getUuid(),
				_portal.getCanonicalURL(
					StringBundler.concat(
						_portal.getGroupFriendlyURL(
							_layout.getLayoutSet(), _themeDisplay, false,
							false),
						FriendlyURLResolverConstants.
							URL_SEPARATOR_JOURNAL_ARTICLE,
						journalArticle.getUrlTitle()),
					_themeDisplay, _layout));
		}
	}

	private AssetDisplayPageEntry _addAssetDisplayPageEntry(
			long classNameId, long classPK, long classTypeId, int type)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0, classNameId,
				classTypeId, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0, true, 0,
				0, 0, 0, _serviceContext);

		return _assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			classPK, layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			type, _serviceContext);
	}

	private JournalArticle _addJournalArticle() throws Exception {
		Locale locale = LocaleUtil.getSiteDefault();

		return JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, StringPool.BLANK,
			true, RandomTestUtil.randomLocaleStringMap(locale),
			RandomTestUtil.randomLocaleStringMap(locale),
			RandomTestUtil.randomLocaleStringMap(locale), null, locale, null,
			false, false, _serviceContext);
	}

	private AssetDisplayPageEntry _addJournalArticleAssetDisplayPageEntry(
			JournalArticle journalArticle)
		throws Exception {

		return _addAssetDisplayPageEntry(
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle.getResourcePrimKey(),
			journalArticle.getDDMStructureId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC);
	}

	private void _assertEmptySitemap(String uuid) throws Exception {
		Assert.assertEquals(
			StringPool.BLANK,
			_sitemapManager.getSitemap(
				uuid, _group.getGroupId(), false, _themeDisplay));
	}

	private void _assertSitemap(String uuid, String... urls) throws Exception {
		String xml = _sitemapManager.getSitemap(
			uuid, _group.getGroupId(), false, _themeDisplay);

		Document document = _saxReader.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> elements = rootElement.elements();

		Assert.assertEquals(elements.toString(), urls.length, elements.size());

		for (int i = 0; i < urls.length; i++) {
			Element urlElement = elements.get(i);

			Assert.assertEquals(
				urlElement.getName(), "url", urlElement.getName());

			Element locElement = urlElement.element("loc");

			Assert.assertNotNull(locElement);

			Assert.assertEquals(
				locElement.getName(), _sitemapManager.encodeXML(urls[i]),
				locElement.getData());
		}
	}

	private void _setUpThemeDisplay() throws Exception {
		_themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_company, _group, _layout);

		_themeDisplay.setPortalURL("http://localhost:8080");

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		_themeDisplay.setRequest(mockHttpServletRequest);

		_themeDisplay.setServerName("localhost");
		_themeDisplay.setServerPort(8080);
	}

	private static final String _SITEMAP_COMPANY_CONFIGURATION_PID =
		"com.liferay.site.internal.configuration.SitemapCompanyConfiguration";

	private static final String _SITEMAP_GROUP_CONFIGURATION_PID =
		"com.liferay.site.internal.configuration.SitemapGroupConfiguration";

	@Inject
	private AssetCategoryService _assetCategoryService;

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private AssetVocabularyService _assetVocabularyService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SAXReader _saxReader;

	private ServiceContext _serviceContext;

	@Inject
	private SitemapManager _sitemapManager;

	private ThemeDisplay _themeDisplay;

}