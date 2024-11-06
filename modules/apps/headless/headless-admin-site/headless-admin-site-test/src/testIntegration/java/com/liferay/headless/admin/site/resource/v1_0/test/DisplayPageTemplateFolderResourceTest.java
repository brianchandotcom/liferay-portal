/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

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

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPageWithPagination();
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
	public void testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder displayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		Assert.assertNull(
			displayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());

		_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
			displayPageTemplateFolder.getExternalReferenceCode(),
			parentDisplayPageTemplateFolder.getExternalReferenceCode());

		_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
			displayPageTemplateFolder.getExternalReferenceCode(), null);

		try {
			displayPageTemplateFolderResource.
				patchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					RandomTestUtil.randomString(),
					randomDisplayPageTemplateFolder());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder();

		_testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithExistingParentExternalReferenceCode();
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
		return new String[] {"description", "externalReferenceCode", "name"};
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

	@Override
	protected DisplayPageTemplateFolder
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				String siteExternalReferenceCode,
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		return testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
			displayPageTemplateFolder);
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

	private void
			_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				String displayPageTemplateFolderExternalReferenceCode,
				String parentDisplayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		DisplayPageTemplateFolder getDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateFolderExternalReferenceCode);

		DisplayPageTemplateFolder randomDisplayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		randomDisplayPageTemplateFolder.setExternalReferenceCode(
			displayPageTemplateFolderExternalReferenceCode);
		randomDisplayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolderExternalReferenceCode);

		DisplayPageTemplateFolder patchDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				patchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateFolderExternalReferenceCode,
					randomDisplayPageTemplateFolder);

		assertEquals(
			randomDisplayPageTemplateFolder, patchDisplayPageTemplateFolder);
		assertValid(patchDisplayPageTemplateFolder);

		if (parentDisplayPageTemplateFolderExternalReferenceCode == null) {
			parentDisplayPageTemplateFolderExternalReferenceCode =
				getDisplayPageTemplateFolder.
					getParentDisplayPageTemplateFolderExternalReferenceCode();
		}

		Assert.assertEquals(
			parentDisplayPageTemplateFolderExternalReferenceCode,
			patchDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());
	}

	private void _testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithExistingParentExternalReferenceCode()
		throws Exception {

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder randomDisplayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		randomDisplayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolder.getExternalReferenceCode());

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder);

		assertEquals(
			randomDisplayPageTemplateFolder, postDisplayPageTemplateFolder);
		Assert.assertEquals(
			randomDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode(),
			postDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());
		assertValid(postDisplayPageTemplateFolder);
		Assert.assertNotNull(
			postDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());
	}

	@Inject
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

}