/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 * @author Bárbara Cabrera
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class DisplayPageTemplateFolderResourceTest
	extends BaseDisplayPageTemplateFolderResourceTestCase {

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplateFolder());

		displayPageTemplateFolderResource.
			deleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				postDisplayPageTemplateFolder.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					postDisplayPageTemplateFolder.getExternalReferenceCode(),
					testGroup.getGroupId()));
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder getDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					postDisplayPageTemplateFolder.getExternalReferenceCode());

		assertEquals(
			postDisplayPageTemplateFolder, getDisplayPageTemplateFolder);
		assertValid(getDisplayPageTemplateFolder);
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterDateTimeEquals();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterDoubleEquals()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterDoubleEquals();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterStringContains()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterStringContains();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterStringEquals()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterStringEquals();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterStringStartsWith()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilterStringStartsWith();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithPagination();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSortDateTime()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSortDateTime();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSortDouble()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSortDouble();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSortInteger()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSortInteger();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSortString()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage();
	}

	@Override
	@Test
	public void testGraphQLGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		super.
			testGraphQLGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder();
	}

	@Override
	@Test
	public void testGraphQLGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderNotFound()
		throws Exception {

		super.
			testGraphQLGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		super.
			testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder();
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"description", "externalReferenceCode", "key", "name",
			"parentDisplayPageTemplateFolderExternalReferenceCode", "uuid"
		};
	}

	@Override
	protected DisplayPageTemplateFolder randomDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			super.randomDisplayPageTemplateFolder();

		displayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				(String)null);

		return displayPageTemplateFolder;
	}

	@Ignore
	@Override
	@Test
	protected DisplayPageTemplateFolder
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder()
		throws Exception {

		return super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder();
	}

	@Override
	protected DisplayPageTemplateFolder
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				String siteExternalReferenceCode,
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		return testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
			displayPageTemplateFolder);
	}

	@Ignore
	@Override
	@Test
	protected Map<String, Map<String, String>>
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_getExpectedActions(
				String siteExternalReferenceCode)
		throws Exception {

		return super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_getExpectedActions(
				siteExternalReferenceCode);
	}

	@Ignore
	@Override
	@Test
	protected String
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_getIrrelevantSiteExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	protected String
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_getSiteExternalReferenceCode()
		throws Exception {

		return super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_getSiteExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	protected void
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilter(
				String operator, EntityField.Type type)
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithFilter(
				operator, type);
	}

	@Ignore
	@Override
	@Test
	protected void
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, DisplayPageTemplateFolder,
					 DisplayPageTemplateFolder, Exception> unsafeTriConsumer)
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithSort(
				type, unsafeTriConsumer);
	}

	@Ignore
	@Override
	@Test
	protected DisplayPageTemplateFolder
			testGetSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder()
		throws Exception {

		return super.
			testGetSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder();
	}

	@Override
	protected DisplayPageTemplateFolder
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		return displayPageTemplateFolderResource.
			postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				displayPageTemplateFolder);
	}

	@Ignore
	@Override
	@Test
	protected DisplayPageTemplateFolder
			testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder()
		throws Exception {

		return super.
			testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder();
	}

	@Ignore
	@Override
	@Test
	protected DisplayPageTemplateFolder
			testPutSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder()
		throws Exception {

		return super.
			testPutSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder();
	}

	@Inject
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

}