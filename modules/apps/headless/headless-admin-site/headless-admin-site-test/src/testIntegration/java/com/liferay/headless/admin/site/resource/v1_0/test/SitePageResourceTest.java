/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.client.resource.v1_0.SitePageResource;
import com.liferay.headless.admin.site.resource.v1_0.test.util.LayoutPageTemplateEntryTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.LayoutUtilityPageEntryTestUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
public class SitePageResourceTest extends BaseSitePageResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		SitePage postSitePage =
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				testGroup.getExternalReferenceCode(), randomSitePage());

		sitePageResource.deleteSiteSiteByExternalReferenceCodeSitePage(
			testGroup.getExternalReferenceCode(),
			postSitePage.getExternalReferenceCode());

		Assert.assertNull(
			_layoutLocalService.fetchLayoutByExternalReferenceCode(
				postSitePage.getExternalReferenceCode(),
				testGroup.getGroupId()));
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

		_testGetSiteSiteByExternalReferenceCodeSitePageWithNestedFields(
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				testGroup.getExternalReferenceCode(), randomSitePage()));
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodeSitePagePermissionsPage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagesPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodeSitePagesPage();
	}

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

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeSitePagePageSpecification()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(testGroup);

		ContentPageSpecification contentPageSpecification =
			_getContentPageSpecification(
				layout.fetchDraftLayout(), layout.getExternalReferenceCode());

		_testPostSiteSiteByExternalReferenceCodeSitePagePageSpecification(
			contentPageSpecification, layout);

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		_testPostSiteSiteByExternalReferenceCodeSitePagePageSpecification(
			contentPageSpecification, layout);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		_assertPostSiteSiteByExternalReferenceCodeSitePagePageSpecificationProblemException(
			LayoutTestUtil.addTypePortletLayout(testGroup));
		_assertPostSiteSiteByExternalReferenceCodeSitePagePageSpecificationProblemException(
			LayoutPageTemplateEntryTestUtil.
				getBasicLayoutPageTemplateEntryLayout(serviceContext));
		_assertPostSiteSiteByExternalReferenceCodeSitePagePageSpecificationProblemException(
			LayoutPageTemplateEntryTestUtil.
				getDisplayPageLayoutPageTemplateEntryLayout(serviceContext));
		_assertPostSiteSiteByExternalReferenceCodeSitePagePageSpecificationProblemException(
			LayoutPageTemplateEntryTestUtil.
				getMasterLayoutPageTemplateEntryLayout(serviceContext));
		_assertPostSiteSiteByExternalReferenceCodeSitePagePageSpecificationProblemException(
			LayoutUtilityPageEntryTestUtil.getLayoutUtilityPageEntryLayout(
				serviceContext));
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

		return testPostByExternalReferenceCodeSitePage_addSitePage(sitePage);
	}

	@Override
	protected String
		testGetSiteSiteByExternalReferenceCodeSitePagesPage_getSiteExternalReferenceCode() {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected SitePage testPostByExternalReferenceCodeSitePage_addSitePage(
			SitePage sitePage)
		throws Exception {

		return sitePageResource.postByExternalReferenceCodeSitePage(
			testGroup.getExternalReferenceCode(), sitePage);
	}

	private void _assertContentPageSpecification(
		Layout layout, PageSpecification pageSpecification) {

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification.getExternalReferenceCode());

		if (layout.isDraftLayout()) {
			Assert.assertEquals(
				PageSpecification.Status.DRAFT, pageSpecification.getStatus());
		}
		else {
			Assert.assertEquals(
				PageSpecification.Status.APPROVED,
				pageSpecification.getStatus());
		}

		Assert.assertEquals(
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION,
			pageSpecification.getType());
	}

	private void _assertContentPageSpecifications(
		Layout layout, PageSpecification[] pageSpecifications) {

		Layout draftLayout = layout.fetchDraftLayout();

		if (!layout.isPublished()) {
			Assert.assertEquals(
				Arrays.toString(pageSpecifications), 1,
				pageSpecifications.length);

			_assertContentPageSpecification(draftLayout, pageSpecifications[0]);

			return;
		}

		if (Objects.equals(
				draftLayout.getStatus(), WorkflowConstants.STATUS_APPROVED)) {

			Assert.assertEquals(
				Arrays.toString(pageSpecifications), 1,
				pageSpecifications.length);

			_assertContentPageSpecification(layout, pageSpecifications[0]);

			return;
		}

		Assert.assertEquals(
			Arrays.toString(pageSpecifications), 2, pageSpecifications.length);

		PageSpecification pageSpecification1 = pageSpecifications[0];

		Assert.assertEquals(
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION,
			pageSpecification1.getType());

		PageSpecification pageSpecification2 = pageSpecifications[1];

		Assert.assertEquals(
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION,
			pageSpecification2.getType());

		if (Objects.equals(
				layout.getExternalReferenceCode(),
				pageSpecification1.getExternalReferenceCode())) {

			Assert.assertEquals(
				PageSpecification.Status.APPROVED,
				pageSpecification1.getStatus());

			Assert.assertEquals(
				draftLayout.getExternalReferenceCode(),
				pageSpecification2.getExternalReferenceCode());
			Assert.assertEquals(
				PageSpecification.Status.DRAFT, pageSpecification2.getStatus());

			return;
		}

		Assert.assertEquals(
			draftLayout.getExternalReferenceCode(),
			pageSpecification1.getExternalReferenceCode());
		Assert.assertEquals(
			PageSpecification.Status.DRAFT, pageSpecification1.getStatus());

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification2.getExternalReferenceCode());
		Assert.assertEquals(
			PageSpecification.Status.APPROVED, pageSpecification2.getStatus());
	}

	private void _assertNestedFields(SitePage sitePage) throws Exception {
		FriendlyUrlHistory friendlyUrlHistory =
			sitePage.getFriendlyUrlHistory();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			GetterUtil.getString(friendlyUrlHistory.getFriendlyUrlPath_i18n()));

		Layout layout = _layoutLocalService.getLayoutByExternalReferenceCode(
			sitePage.getExternalReferenceCode(), testGroup.getGroupId());

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

		PageSpecification[] pageSpecifications =
			sitePage.getPageSpecifications();

		Assert.assertTrue(ArrayUtil.isNotEmpty(pageSpecifications));

		if (!layout.isTypeAssetDisplay() && !layout.isTypeContent()) {
			_assertWidgetPageSpecifications(layout, pageSpecifications);

			return;
		}

		_assertContentPageSpecifications(layout, pageSpecifications);
	}

	private void
			_assertPostSiteSiteByExternalReferenceCodeSitePagePageSpecificationProblemException(
				Layout layout)
		throws Exception {

		_assertProblemException(
			() ->
				sitePageResource.
					postSiteSiteByExternalReferenceCodeSitePagePageSpecification(
						testGroup.getExternalReferenceCode(),
						layout.getExternalReferenceCode(),
						new ContentPageSpecification() {
							{
								setExternalReferenceCode(
									layout::getExternalReferenceCode);
								setStatus(() -> Status.DRAFT);
								setType(() -> Type.CONTENT_PAGE_SPECIFICATION);
							}
						}));
	}

	private void _assertProblemException(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();
			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	private void _assertWidgetPageSpecifications(
		Layout layout, PageSpecification[] pageSpecifications) {

		Assert.assertEquals(
			Arrays.toString(pageSpecifications), 1, pageSpecifications.length);

		PageSpecification pageSpecification = pageSpecifications[0];

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification.getExternalReferenceCode());
		Assert.assertEquals(
			PageSpecification.Status.APPROVED, pageSpecification.getStatus());
		Assert.assertEquals(
			PageSpecification.Type.WIDGET_PAGE_SPECIFICATION,
			pageSpecification.getType());
	}

	private ContentPageSpecification _getContentPageSpecification(
			Layout layout, String sitePageExternalReferenceCode)
		throws Exception {

		SitePageResource curSitePageResource = _getSitePageResource();

		SitePage sitePage =
			curSitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePageExternalReferenceCode);

		PageSpecification[] pageSpecifications =
			sitePage.getPageSpecifications();

		Assert.assertEquals(
			Arrays.toString(pageSpecifications), 1, pageSpecifications.length);

		PageSpecification pageSpecification = pageSpecifications[0];

		_assertContentPageSpecification(layout, pageSpecification);

		return (ContentPageSpecification)pageSpecification;
	}

	private SitePageResource _getSitePageResource() throws Exception {
		User user = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		return SitePageResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "friendlyUrlHistory,pageSpecifications"
		).build();
	}

	private void
			_testGetSiteSiteByExternalReferenceCodeSitePageWithNestedFields(
				SitePage sitePage)
		throws Exception {

		SitePageResource curSitePageResource = _getSitePageResource();

		_assertNestedFields(
			curSitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode()));
	}

	private void
			_testPostSiteSiteByExternalReferenceCodeSitePagePageSpecification(
				ContentPageSpecification contentPageSpecification,
				Layout layout)
		throws Exception {

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_APPROVED);

		contentPageSpecification.setExternalReferenceCode(
			layout.getExternalReferenceCode());

		_assertProblemException(
			() ->
				sitePageResource.
					postSiteSiteByExternalReferenceCodeSitePagePageSpecification(
						testGroup.getExternalReferenceCode(),
						layout.getExternalReferenceCode(),
						contentPageSpecification));

		contentPageSpecification.setExternalReferenceCode(
			draftLayout.getExternalReferenceCode());

		_assertContentPageSpecification(
			draftLayout,
			sitePageResource.
				postSiteSiteByExternalReferenceCodeSitePagePageSpecification(
					testGroup.getExternalReferenceCode(),
					layout.getExternalReferenceCode(),
					contentPageSpecification));

		draftLayout = _layoutLocalService.getLayout(draftLayout.getPlid());

		Assert.assertEquals(
			draftLayout.getStatus(), WorkflowConstants.STATUS_DRAFT);

		contentPageSpecification.setExternalReferenceCode(
			layout.getExternalReferenceCode());

		_assertProblemException(
			() ->
				sitePageResource.
					postSiteSiteByExternalReferenceCodeSitePagePageSpecification(
						testGroup.getExternalReferenceCode(),
						layout.getExternalReferenceCode(),
						contentPageSpecification));

		contentPageSpecification.setExternalReferenceCode(
			draftLayout.getExternalReferenceCode());

		_assertProblemException(
			() ->
				sitePageResource.
					postSiteSiteByExternalReferenceCodeSitePagePageSpecification(
						testGroup.getExternalReferenceCode(),
						layout.getExternalReferenceCode(),
						contentPageSpecification));
	}

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private UserLocalService _userLocalService;

}