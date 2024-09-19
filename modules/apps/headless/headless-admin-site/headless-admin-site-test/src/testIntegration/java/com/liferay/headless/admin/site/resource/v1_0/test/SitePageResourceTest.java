/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSettings;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class SitePageResourceTest extends BaseSitePageResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		super.testDeleteSiteSiteByExternalReferenceCodeSitePage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		SitePage postSitePage =
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				testGroup.getExternalReferenceCode(), randomSitePage());

		SitePage getSitePage =
			sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				postSitePage.getExternalReferenceCode());

		assertEquals(postSitePage, getSitePage);
		assertValid(getSitePage);
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodeSitePagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagesPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodeSitePagesPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagesPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeSitePagesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodeSitePagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		super.testPatchSiteSiteByExternalReferenceCodeSitePage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodeSitePage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodeSitePagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodeSitePagePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "friendlyUrlPath_i18n", "name_i18n",
			"pageSettings", "type", "uuid"
		};
	}

	@Override
	protected SitePage randomSitePage() throws Exception {
		SitePage sitePage = super.randomSitePage();

		sitePage.setFriendlyUrlPath_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
				StringPool.FORWARD_SLASH +
					StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
				StringPool.FORWARD_SLASH +
					StringUtil.toLowerCase(RandomTestUtil.randomString())
			).build());
		sitePage.setName_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
				RandomTestUtil.randomString()
			).put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
				RandomTestUtil.randomString()
			).build());
		sitePage.setPageSettings(
			new WidgetPageSettings() {
				{
					setHiddenFromNavigation(false);
					setLayoutTemplateId("1_column");
					setType(Type.WIDGET_PAGE_SETTINGS);
				}
			});
		sitePage.setType(SitePage.Type.WIDGET_PAGE);

		return sitePage;
	}

	@Override
	protected SitePage
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				String siteExternalReferenceCode, SitePage sitePage)
		throws Exception {

		Group group = _groupLocalService.fetchGroupByExternalReferenceCode(
			siteExternalReferenceCode, testGroup.getCompanyId());

		Map<String, String> nameI18nMap = sitePage.getName_i18n();
		Map<String, String> friendlyUrlPathI18nMap =
			sitePage.getFriendlyUrlPath_i18n();

		Layout layout = _layoutLocalService.addLayout(
			sitePage.getExternalReferenceCode(), TestPropsValues.getUserId(),
			group.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			0, 0,
			HashMapBuilder.put(
				LocaleUtil.SPAIN,
				nameI18nMap.get(LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN))
			).put(
				LocaleUtil.US,
				nameI18nMap.get(LocaleUtil.toBCP47LanguageId(LocaleUtil.US))
			).build(),
			RandomTestUtil.randomLocaleStringMap(), Collections.emptyMap(),
			Collections.emptyMap(), Collections.emptyMap(),
			LayoutConstants.TYPE_PORTLET,
			UnicodePropertiesBuilder.put(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID,
				() -> {
					WidgetPageSettings widgetPageSettings =
						(WidgetPageSettings)sitePage.getPageSettings();

					return widgetPageSettings.getLayoutTemplateId();
				}
			).buildString(),
			false, false,
			HashMapBuilder.put(
				LocaleUtil.SPAIN,
				friendlyUrlPathI18nMap.get(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN))
			).put(
				LocaleUtil.US,
				friendlyUrlPathI18nMap.get(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.US))
			).build(),
			0,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		sitePage.setDateCreated(layout.getCreateDate());
		sitePage.setDateModified(layout.getModifiedDate());
		sitePage.setDatePublished(layout.getPublishDate());
		sitePage.setSiteExternalReferenceCode(
			layout.getExternalReferenceCode());
		sitePage.setUuid(layout.getUuid());

		return sitePage;
	}

	@Override
	protected SitePage testPostByExternalReferenceCodeSitePage_addSitePage(
			SitePage sitePage)
		throws Exception {

		return sitePageResource.postByExternalReferenceCodeSitePage(
			sitePage.getSiteExternalReferenceCode(), sitePage);
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

}