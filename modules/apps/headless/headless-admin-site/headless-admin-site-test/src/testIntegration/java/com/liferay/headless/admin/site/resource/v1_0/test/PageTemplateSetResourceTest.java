/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplateSet;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class PageTemplateSetResourceTest
	extends BasePageTemplateSetResourceTestCase {

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodePageTemplateSet()
		throws Exception {

		PageTemplateSet pageTemplateSet =
			testGetSiteSiteByExternalReferenceCodePageTemplateSetsPage_addPageTemplateSet(
				testGroup.getExternalReferenceCode(), randomPageTemplateSet());

		pageTemplateSetResource.
			deleteSiteSiteByExternalReferenceCodePageTemplateSet(
				testGroup.getExternalReferenceCode(),
				pageTemplateSet.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollectionByExternalReferenceCode(
					pageTemplateSet.getExternalReferenceCode(),
					testGroup.getGroupId()));
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplateSet()
		throws Exception {

		PageTemplateSet pageTemplateSet =
			testGetSiteSiteByExternalReferenceCodePageTemplateSetsPage_addPageTemplateSet(
				testGroup.getExternalReferenceCode(), randomPageTemplateSet());

		PageTemplateSet getPageTemplateSet =
			pageTemplateSetResource.
				getSiteSiteByExternalReferenceCodePageTemplateSet(
					testGroup.getExternalReferenceCode(),
					pageTemplateSet.getExternalReferenceCode());

		assertEquals(pageTemplateSet, getPageTemplateSet);
		assertValid(getPageTemplateSet);
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplateSetPermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplateSetPermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplateSetsPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplateSetsPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage();
	}

	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodePageTemplateSet()
		throws Exception {

		PageTemplateSet pageTemplateSet = randomPageTemplateSet();

		pageTemplateSetResource.
			putSiteSiteByExternalReferenceCodePageTemplateSet(
				testGroup.getExternalReferenceCode(),
				pageTemplateSet.getExternalReferenceCode(), pageTemplateSet);

		pageTemplateSet.setDescription(RandomTestUtil.randomString());

		PageTemplateSet patchPageTemplateSet =
			pageTemplateSetResource.
				patchSiteSiteByExternalReferenceCodePageTemplateSet(
					testGroup.getExternalReferenceCode(),
					pageTemplateSet.getExternalReferenceCode(),
					pageTemplateSet);

		assertEquals(pageTemplateSet, patchPageTemplateSet);
		assertValid(patchPageTemplateSet);

		pageTemplateSet.setName(RandomTestUtil.randomString());

		patchPageTemplateSet =
			pageTemplateSetResource.
				patchSiteSiteByExternalReferenceCodePageTemplateSet(
					testGroup.getExternalReferenceCode(),
					pageTemplateSet.getExternalReferenceCode(),
					pageTemplateSet);

		assertEquals(pageTemplateSet, patchPageTemplateSet);
		assertValid(patchPageTemplateSet);
	}

	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageTemplateSet()
		throws Exception {

		PageTemplateSet pageTemplateSet = randomPageTemplateSet();

		PageTemplateSet putPageTemplateSet =
			pageTemplateSetResource.
				putSiteSiteByExternalReferenceCodePageTemplateSet(
					testGroup.getExternalReferenceCode(),
					pageTemplateSet.getExternalReferenceCode(),
					pageTemplateSet);

		assertEquals(pageTemplateSet, putPageTemplateSet);
		assertValid(putPageTemplateSet);
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageTemplateSetPermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteByExternalReferenceCodePageTemplateSetPermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "description", "name"};
	}

	@Override
	protected PageTemplateSet
			testGetSiteSiteByExternalReferenceCodePageTemplateSetsPage_addPageTemplateSet(
				String siteExternalReferenceCode,
				PageTemplateSet pageTemplateSet)
		throws Exception {

		return pageTemplateSetResource.
			putSiteSiteByExternalReferenceCodePageTemplateSet(
				siteExternalReferenceCode,
				pageTemplateSet.getExternalReferenceCode(), pageTemplateSet);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageTemplateSetsPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageTemplateSet
			testPostSiteSiteByExternalReferenceCodePageTemplateSet_addPageTemplateSet(
				PageTemplateSet pageTemplateSet)
		throws Exception {

		return pageTemplateSetResource.
			putSiteSiteByExternalReferenceCodePageTemplateSet(
				testGroup.getExternalReferenceCode(),
				pageTemplateSet.getExternalReferenceCode(), pageTemplateSet);
	}

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

}