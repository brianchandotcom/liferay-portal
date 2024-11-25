/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.client.dto.v1_0.UtilityPage;
import com.liferay.headless.admin.site.client.pagination.Page;
import com.liferay.headless.admin.site.client.pagination.Pagination;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.client.resource.v1_0.UtilityPageResource;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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

		UtilityPageResource curUtilityPageResource = _getUtilityPageResource();

		_assertNestedFields(
			curUtilityPageResource.
				getSiteSiteByExternalReferenceCodeUtilityPage(
					testGroup.getExternalReferenceCode(),
					postUtilityPage.getExternalReferenceCode()));
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagesPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodeUtilityPagesPage();

		_testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithNestedFields();
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
			Boolean.FALSE,
			_getUtilityPage(
				Boolean.FALSE, utilityPage.getExternalReferenceCode()));
		_testPatchSiteSiteByExternalReferenceCodeUtilityPage(
			Boolean.TRUE,
			_getUtilityPage(
				Boolean.TRUE, utilityPage.getExternalReferenceCode()));
		_testPatchSiteSiteByExternalReferenceCodeUtilityPage(
			Boolean.TRUE,
			_getUtilityPage(null, utilityPage.getExternalReferenceCode()));

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

	private void _assertNestedFields(UtilityPage utilityPage) throws Exception {
		FriendlyUrlHistory friendlyUrlHistory =
			utilityPage.getFriendlyUrlHistory();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			GetterUtil.getString(friendlyUrlHistory.getFriendlyUrlPath_i18n()));

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.
				getLayoutUtilityPageEntryByExternalReferenceCode(
					utilityPage.getExternalReferenceCode(),
					testGroup.getGroupId());

		Layout layout = _layoutLocalService.getLayout(
			layoutUtilityPageEntry.getPlid());

		Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

		Assert.assertEquals(
			jsonObject.toString(), friendlyURLMap.size(), jsonObject.length());

		for (Map.Entry<Locale, String> entry : friendlyURLMap.entrySet()) {
			String key = LocaleUtil.toBCP47LanguageId(entry.getKey());

			JSONArray jsonArray = jsonObject.getJSONArray(key);

			Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());
			Assert.assertEquals(
				jsonArray.toString(), entry.getValue(), jsonArray.getString(0));
		}
	}

	private UtilityPage _getUtilityPage(
			Boolean markedAsDefault, String masterPageExternalReferenceCode)
		throws Exception {

		UtilityPage utilityPage = randomUtilityPage();

		utilityPage.setExternalReferenceCode(masterPageExternalReferenceCode);
		utilityPage.setMarkedAsDefault(markedAsDefault);

		return utilityPage;
	}

	private UtilityPage _getUtilityPage(
		String externalReferenceCode, List<UtilityPage> utilityPages) {

		for (UtilityPage utilityPage : utilityPages) {
			if (Objects.equals(
					utilityPage.getExternalReferenceCode(),
					externalReferenceCode)) {

				return utilityPage;
			}
		}

		return null;
	}

	private UtilityPageResource _getUtilityPageResource() throws Exception {
		User omniadminUser = UserTestUtil.addOmniadminUser();

		String password = RandomTestUtil.randomString();

		_userLocalService.updatePassword(
			omniadminUser.getUserId(), password, password, false, true);

		return UtilityPageResource.builder(
		).authentication(
			omniadminUser.getEmailAddress(), password
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "friendlyUrlHistory"
		).build();
	}

	private void _testGetSiteSiteByExternalReferenceCodeUtilityPagesPageWithNestedFields()
		throws Exception {

		Page<UtilityPage> page =
			utilityPageResource.
				getSiteSiteByExternalReferenceCodeUtilityPagesPage(
					testGroup.getExternalReferenceCode(), null, null, null,
					Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		UtilityPage utilityPage =
			testGetSiteSiteByExternalReferenceCodeUtilityPagesPage_addUtilityPage(
				testGroup.getExternalReferenceCode(), randomUtilityPage());

		UtilityPageResource curUtilityPageResource = _getUtilityPageResource();

		page =
			curUtilityPageResource.
				getSiteSiteByExternalReferenceCodeUtilityPagesPage(
					testGroup.getExternalReferenceCode(), null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());

		_assertNestedFields(
			_getUtilityPage(
				utilityPage.getExternalReferenceCode(),
				(List<UtilityPage>)page.getItems()));
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
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}