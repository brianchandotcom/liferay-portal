/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.UtilityPage;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
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
public class UtilityPageResourceTest extends BaseUtilityPageResourceTestCase {

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeUtilityPage()
		throws Exception {

		UtilityPage postUtilityPage =
			testPostSiteSiteByExternalReferenceCodeUtilityPage_addUtilityPage(
				randomUtilityPage());

		Assert.assertNotNull(
			_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					postUtilityPage.getExternalReferenceCode(),
					testGroup.getGroupId()));

		utilityPageResource.deleteSiteSiteByExternalReferenceCodeUtilityPage(
			testGroup.getExternalReferenceCode(),
			postUtilityPage.getExternalReferenceCode());

		Assert.assertNull(
			_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					postUtilityPage.getExternalReferenceCode(),
					testGroup.getGroupId()));

		try {
			utilityPageResource.
				deleteSiteSiteByExternalReferenceCodeUtilityPage(
					testGroup.getExternalReferenceCode(),
					postUtilityPage.getExternalReferenceCode());

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
	public void testGetSiteSiteByExternalReferenceCodeUtilityPage()
		throws Exception {

		UtilityPage postUtilityPage =
			testPostSiteSiteByExternalReferenceCodeUtilityPage_addUtilityPage(
				randomUtilityPage());

		UtilityPage getUtilityPage =
			utilityPageResource.getSiteSiteByExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				postUtilityPage.getExternalReferenceCode());

		assertEquals(postUtilityPage, getUtilityPage);
		assertValid(getUtilityPage);

		try {
			utilityPageResource.getSiteSiteByExternalReferenceCodeUtilityPage(
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

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithSortDateTime()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithSortDouble()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithSortInteger()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithSortString()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeUtilityPage()
		throws Exception {

		UtilityPage utilityPage =
			testPostSiteSiteByExternalReferenceCodeUtilityPage_addUtilityPage(
				randomUtilityPage());

		_testPatchSiteSiteByExternalReferenceCodeUtilityPage(
			Boolean.TRUE,
			_getUtilityPage(
				Boolean.TRUE, utilityPage.getExternalReferenceCode()));

		_testPatchSiteSiteByExternalReferenceCodeUtilityPage(
			Boolean.TRUE,
			_getUtilityPage(null, utilityPage.getExternalReferenceCode()));

		_testPatchSiteSiteByExternalReferenceCodeUtilityPage(
			Boolean.FALSE,
			_getUtilityPage(
				Boolean.FALSE, utilityPage.getExternalReferenceCode()));

		try {
			utilityPageResource.patchSiteSiteByExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString(), randomUtilityPage());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeUtilityPagePageSpecification()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodeUtilityPagePageSpecification();
	}

	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeUtilityPage()
		throws Exception {

		_testPutSiteSiteByExternalReferenceCodeUtilityPage(randomUtilityPage());

		UtilityPage utilityPage =
			testPostSiteSiteByExternalReferenceCodeUtilityPage_addUtilityPage(
				randomUtilityPage());

		_testPutSiteSiteByExternalReferenceCodeUtilityPage(
			_getUtilityPage(null, utilityPage.getExternalReferenceCode()));
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected UtilityPage randomUtilityPage() throws Exception {
		UtilityPage utilityPage = super.randomUtilityPage();

		utilityPage.setType(UtilityPage.Type.ERROR);

		return utilityPage;
	}

	@Override
	protected UtilityPage
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPage_addUtilityPage(
				String siteExternalReferenceCode, UtilityPage utilityPage)
		throws Exception {

		return utilityPageResource.
			postSiteSiteByExternalReferenceCodeUtilityPage(
				siteExternalReferenceCode, utilityPage);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected UtilityPage
			testPostSiteSiteByExternalReferenceCodeUtilityPage_addUtilityPage(
				UtilityPage utilityPage)
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodeUtilityPagesPage_addUtilityPage(
			testGroup.getExternalReferenceCode(), utilityPage);
	}

	private UtilityPage _getUtilityPage(
			Boolean markedAsDefault, String masterPageExternalReferenceCode)
		throws Exception {

		UtilityPage utilityPage = randomUtilityPage();

		utilityPage.setExternalReferenceCode(masterPageExternalReferenceCode);
		utilityPage.setMarkedAsDefault(markedAsDefault);

		return utilityPage;
	}

	private void _testPatchSiteSiteByExternalReferenceCodeUtilityPage(
			Boolean expectedMarkedAsDefault, UtilityPage utilityPage)
		throws Exception {

		UtilityPage pathUtilityPage =
			utilityPageResource.patchSiteSiteByExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				utilityPage.getExternalReferenceCode(), utilityPage);

		assertEquals(utilityPage, pathUtilityPage);
		assertValid(pathUtilityPage);

		Assert.assertEquals(
			expectedMarkedAsDefault, pathUtilityPage.getMarkedAsDefault());
	}

	private void _testPutSiteSiteByExternalReferenceCodeUtilityPage(
			UtilityPage utilityPage)
		throws Exception {

		UtilityPage putUtilityPage =
			utilityPageResource.putSiteSiteByExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				utilityPage.getExternalReferenceCode(), utilityPage);

		assertEquals(utilityPage, putUtilityPage);
		assertValid(putUtilityPage);
	}

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}