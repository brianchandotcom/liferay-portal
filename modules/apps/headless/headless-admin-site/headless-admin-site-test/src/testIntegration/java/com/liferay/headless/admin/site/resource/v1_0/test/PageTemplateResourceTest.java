/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplate;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class PageTemplateResourceTest extends BasePageTemplateResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testDeleteSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortDateTime()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortDouble()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortInteger()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testPatchSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageTemplatePageSpecification()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodePageTemplatePageSpecification();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteByExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected PageTemplate
			testGetSiteSiteByExternalReferenceCodePageTemplatesPage_addPageTemplate(
				String siteExternalReferenceCode, PageTemplate pageTemplate)
		throws Exception {

		return pageTemplateResource.
			postSiteSiteByExternalReferenceCodePageTemplate(
				siteExternalReferenceCode, pageTemplate);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageTemplatesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageTemplatesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageTemplate
			testPostSiteSiteByExternalReferenceCodePageTemplate_addPageTemplate(
				PageTemplate pageTemplate)
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodePageTemplatesPage_addPageTemplate(
			testGroup.getExternalReferenceCode(), pageTemplate);
	}

}