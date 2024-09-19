/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSettings;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;

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
		sitePage.setSiteExternalReferenceCode(
			testGroup.getExternalReferenceCode());
		sitePage.setType(SitePage.Type.WIDGET_PAGE);

		return sitePage;
	}

	@Override
	protected SitePage
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				String siteExternalReferenceCode, SitePage sitePage)
		throws Exception {

		return testPostByExternalReferenceCodeSitePage_addSitePage(sitePage);
	}

	@Override
	protected SitePage testPostByExternalReferenceCodeSitePage_addSitePage(
			SitePage sitePage)
		throws Exception {

		return sitePageResource.postByExternalReferenceCodeSitePage(
			sitePage.getSiteExternalReferenceCode(), sitePage);
	}

}