/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class MasterPageResourceTest extends BaseMasterPageResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

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

		_assertProblemException(
			"NOT_FOUND",
			() ->
				masterPageResource.
					deleteSiteSiteByExternalReferenceCodeMasterPage(
						testGroup.getExternalReferenceCode(),
						postMasterPage.getExternalReferenceCode()));

		MasterPage liveGroupMasterPage =
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				masterPageResource.
					deleteSiteSiteByExternalReferenceCodeMasterPage(
						testGroup.getExternalReferenceCode(),
						liveGroupMasterPage.getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeMasterPage()
		throws Exception {

		MasterPage masterPage =
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_testGetSiteSiteByExternalReferenceCodeMasterPage(masterPage);

		_assertProblemException(
			"NOT_FOUND",
			() ->
				masterPageResource.getSiteSiteByExternalReferenceCodeMasterPage(
					testGroup.getExternalReferenceCode(),
					RandomTestUtil.randomString()));

		_enableLocalStaging();

		_testGetSiteSiteByExternalReferenceCodeMasterPage(masterPage);
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeMasterPagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodeMasterPagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithSortDateTime()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithSortDouble()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithSortInteger()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithSortString()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeMasterPagesPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeMasterPagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodeMasterPagePermissionsPage();
	}

	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeMasterPage()
		throws Exception {

		MasterPage masterPage =
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_updateLayoutPageTemplateEntryStatus(
			masterPage.getExternalReferenceCode());

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

		_assertProblemException(
			"NOT_FOUND",
			() ->
				masterPageResource.
					patchSiteSiteByExternalReferenceCodeMasterPage(
						testGroup.getExternalReferenceCode(),
						RandomTestUtil.randomString(), randomMasterPage()));

		MasterPage liveGroupMasterPage =
			testPostSiteSiteByExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				masterPageResource.
					patchSiteSiteByExternalReferenceCodeMasterPage(
						testGroup.getExternalReferenceCode(),
						liveGroupMasterPage.getExternalReferenceCode(),
						_getMasterPage(
							null,
							liveGroupMasterPage.getExternalReferenceCode())));
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeMasterPagePageSpecification()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodeMasterPagePageSpecification();
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

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				masterPageResource.putSiteSiteByExternalReferenceCodeMasterPage(
					testGroup.getExternalReferenceCode(),
					masterPage.getExternalReferenceCode(),
					_getMasterPage(
						null, masterPage.getExternalReferenceCode())));
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeMasterPagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodeMasterPagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeMasterPagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodeMasterPagePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected MasterPage randomMasterPage() throws Exception {
		MasterPage masterPage = super.randomMasterPage();

		masterPage.setMarkedAsDefault(Boolean.FALSE);

		return masterPage;
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

	private void _assertProblemException(
			String status, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(status, problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	private void _enableLocalStaging() throws Exception {
		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), testGroup, true, false,
			ServiceContextTestUtil.getServiceContext(
				testGroup, TestPropsValues.getUserId()));
	}

	private MasterPage _getMasterPage(
			Boolean markedAsDefault, String masterPageExternalReferenceCode)
		throws Exception {

		MasterPage masterPage = randomMasterPage();

		masterPage.setExternalReferenceCode(masterPageExternalReferenceCode);
		masterPage.setMarkedAsDefault(markedAsDefault);

		return masterPage;
	}

	private void _testGetSiteSiteByExternalReferenceCodeMasterPage(
			MasterPage masterPage)
		throws Exception {

		MasterPage getMasterPage =
			masterPageResource.getSiteSiteByExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				masterPage.getExternalReferenceCode());

		assertEquals(masterPage, getMasterPage);
		assertValid(getMasterPage);
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

	private void _updateLayoutPageTemplateEntryStatus(
			String externalReferenceCode)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					externalReferenceCode, testGroup.getGroupId());

		_layoutPageTemplateEntryLocalService.updateStatus(
			TestPropsValues.getUserId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}