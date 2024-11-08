/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class MasterPageResourceTest extends BaseMasterPageResourceTestCase {

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeMasterPage()
		throws Exception {

		MasterPage postMasterPage =
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					postMasterPage.getExternalReferenceCode(),
					testGroup.getGroupId()));

		masterPageResource.deleteSiteSiteByExternalReferenceCodeMasterPage(
			testGroup.getExternalReferenceCode(),
			postMasterPage.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					postMasterPage.getExternalReferenceCode(),
					testGroup.getGroupId()));

		try {
			masterPageResource.deleteSiteSiteByExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				postMasterPage.getExternalReferenceCode());

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
	public void testGetSiteSiteByExternalReferenceCodeMasterPage()
		throws Exception {

		MasterPage postMasterPage =
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		MasterPage getMasterPage =
			masterPageResource.getSiteSiteByExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				postMasterPage.getExternalReferenceCode());

		assertEquals(postMasterPage, getMasterPage);
		assertValid(getMasterPage);

		try {
			masterPageResource.getSiteSiteByExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString());

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
	public void testPatchSiteSiteByExternalReferenceCodeMasterPage()
		throws Exception {

		MasterPage masterPage =
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_testPatchSiteSiteByExternalReferenceCodeMasterPage(
			Boolean.TRUE,
			_getMasterPage(
				Boolean.TRUE, masterPage.getExternalReferenceCode()));

		_testPatchSiteSiteByExternalReferenceCodeMasterPage(
			Boolean.TRUE,
			_getMasterPage(null, masterPage.getExternalReferenceCode()));

		_testPatchSiteSiteByExternalReferenceCodeMasterPage(
			Boolean.FALSE,
			_getMasterPage(
				Boolean.FALSE, masterPage.getExternalReferenceCode()));

		try {
			masterPageResource.patchSiteSiteByExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString(), randomMasterPage());

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
	public void testPutSiteSiteByExternalReferenceCodeMasterPage()
		throws Exception {

		_testPutSiteSiteByExternalReferenceCodeMasterPage(randomMasterPage());

		MasterPage masterPage =
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_testPutSiteSiteByExternalReferenceCodeMasterPage(
			_getMasterPage(null, masterPage.getExternalReferenceCode()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected MasterPage
			testGetSiteSiteByExternalReferenceCodeMasterPagesPage_addMasterPage(
				String siteExternalReferenceCode, MasterPage masterPage)
		throws Exception {

		return masterPageResource.postSiteSiteByExternalReferenceCodeMasterPage(
			siteExternalReferenceCode, masterPage);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeMasterPagesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeMasterPagesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected MasterPage
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				MasterPage masterPage)
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodeMasterPagesPage_addMasterPage(
			testGroup.getExternalReferenceCode(), masterPage);
	}

	private MasterPage _getMasterPage(
			Boolean markedAsDefault, String masterPageExternalReferenceCode)
		throws Exception {

		MasterPage masterPage = randomMasterPage();

		masterPage.setExternalReferenceCode(masterPageExternalReferenceCode);
		masterPage.setMarkedAsDefault(markedAsDefault);

		return masterPage;
	}

	private void _testPatchSiteSiteByExternalReferenceCodeMasterPage(
			Boolean expectedMarkedAsDefault, MasterPage masterPage)
		throws Exception {

		MasterPage patchMasterPage =
			masterPageResource.patchSiteSiteByExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				masterPage.getExternalReferenceCode(), masterPage);

		assertEquals(masterPage, patchMasterPage);
		assertValid(patchMasterPage);

		Assert.assertEquals(
			expectedMarkedAsDefault, patchMasterPage.getMarkedAsDefault());
	}

	private void _testPutSiteSiteByExternalReferenceCodeMasterPage(
			MasterPage masterPage)
		throws Exception {

		MasterPage putMasterPage =
			masterPageResource.putSiteSiteByExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				masterPage.getExternalReferenceCode(), masterPage);

		assertEquals(masterPage, putMasterPage);
		assertValid(putMasterPage);
	}

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}