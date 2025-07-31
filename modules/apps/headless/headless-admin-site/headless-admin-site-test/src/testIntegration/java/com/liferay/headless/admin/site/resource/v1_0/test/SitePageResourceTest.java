/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.custom.field.CustomField;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSettings;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSettings;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.client.pagination.Page;
import com.liferay.headless.admin.site.client.pagination.Pagination;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.client.resource.v1_0.SitePageResource;
import com.liferay.headless.admin.site.resource.v1_0.test.util.LayoutPageTemplateEntryTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.LayoutUtilityPageEntryTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.PageSpecificationsTestUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
@FeatureFlag("LPD-35443")
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		_testDeleteSiteSiteByExternalReferenceCodeSitePage(
			_addLayout(LayoutConstants.TYPE_CONTENT, null, serviceContext),
			_addLayout(
				LayoutConstants.TYPE_PORTLET,
				UnicodePropertiesBuilder.put(
					LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
				).buildString(),
				serviceContext));

		Layout layout = _addLayout(
			LayoutConstants.TYPE_CONTENT, null, serviceContext);

		_assertDeleteSiteSiteByExternalReferenceCodeSitePageProblemException(
			layout.fetchDraftLayout(),
			LayoutPageTemplateEntryTestUtil.
				getBasicLayoutPageTemplateEntryLayout(serviceContext),
			LayoutPageTemplateEntryTestUtil.
				getDisplayPageLayoutPageTemplateEntryLayout(serviceContext),
			LayoutPageTemplateEntryTestUtil.
				getMasterLayoutPageTemplateEntryLayout(serviceContext),
			LayoutUtilityPageEntryTestUtil.getLayoutUtilityPageEntryLayout(
				serviceContext));
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		Layout layout = _addLayout(
			LayoutConstants.TYPE_CONTENT, null, serviceContext);

		Assert.assertFalse(layout.isPublished());

		_testGetSiteSiteByExternalReferenceCodeSitePage(layout);

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		Assert.assertTrue(layout.isPublished());

		_testGetSiteSiteByExternalReferenceCodeSitePage(layout);

		_testGetSiteSiteByExternalReferenceCodeSitePage(
			_addLayout(
				LayoutConstants.TYPE_PORTLET,
				UnicodePropertiesBuilder.put(
					LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
				).buildString(),
				serviceContext));
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSitePagePermissionsPage() throws Exception {
		super.testGetSiteSitePagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		super.testGraphQLGetSiteSiteByExternalReferenceCodeSitePage();
	}

	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		_testPatchSiteSiteByExternalReferenceCodeSitePage(
			SitePage.Type.CONTENT_PAGE);
		_testPatchSiteSiteByExternalReferenceCodeSitePage(
			SitePage.Type.WIDGET_PAGE);
		_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications();
		_testPatchSiteSiteByExternalReferenceCodeSitePageWithPriority();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		Layout layout = _addLayout(
			LayoutConstants.TYPE_CONTENT, null, serviceContext);

		_assertPatchSiteSiteByExternalReferenceCodeSitePageProblemException(
			layout.fetchDraftLayout(),
			LayoutPageTemplateEntryTestUtil.
				getBasicLayoutPageTemplateEntryLayout(serviceContext),
			LayoutPageTemplateEntryTestUtil.
				getDisplayPageLayoutPageTemplateEntryLayout(serviceContext),
			LayoutPageTemplateEntryTestUtil.
				getMasterLayoutPageTemplateEntryLayout(serviceContext),
			LayoutUtilityPageEntryTestUtil.getLayoutUtilityPageEntryLayout(
				serviceContext));
	}

	@Override
	@Test
	public void testPostByExternalReferenceCodeSitePage() throws Exception {
		super.testPostByExternalReferenceCodeSitePage();

		_testPostByExternalReferenceCodeSitePage(
			_getRandomSitePage(SitePage.Type.CONTENT_PAGE));
		_testPostByExternalReferenceCodeSitePage(
			_getRandomSitePage(SitePage.Type.WIDGET_PAGE));

		Layout layout = LayoutTestUtil.addTypePortletLayout(testGroup);

		_testPostByExternalReferenceCodeSitePage(
			_getRandomSitePage(
				StringUtil.toLowerCase(RandomTestUtil.randomString()),
				layout.getExternalReferenceCode(), SitePage.Type.CONTENT_PAGE,
				StringUtil.toLowerCase(RandomTestUtil.randomString())));

		_testPostByExternalReferenceCodeSitePageWithPageSpecifications();
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeSitePagePageSpecification()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(testGroup);

		SitePageResource sitePageResource = _getSitePageResource(
			"pageSpecifications");

		SitePage sitePage =
			sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				layout.getExternalReferenceCode());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		PageSpecificationsTestUtil.
			testPostSiteSiteByExternalReferenceCodePageSpecification(
				layout, sitePage.getPageSpecifications(), serviceContext,
				contentPageSpecification ->
					sitePageResource.
						postSiteSiteByExternalReferenceCodeSitePagePageSpecification(
							testGroup.getExternalReferenceCode(),
							layout.getExternalReferenceCode(),
							contentPageSpecification));

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

	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeSitePage()
		throws Exception {

		_testPutSiteSiteByExternalReferenceCodeSitePage(
			SitePage.Type.CONTENT_PAGE);
		_testPutSiteSiteByExternalReferenceCodeSitePage(
			SitePage.Type.WIDGET_PAGE);
		_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications();
		_testPutSiteSiteByExternalReferenceCodeSitePageWithPriority();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		Layout layout = _addLayout(
			LayoutConstants.TYPE_CONTENT, null, serviceContext);

		_assertPutSiteSiteByExternalReferenceCodeSitePageProblemException(
			layout.fetchDraftLayout(),
			LayoutPageTemplateEntryTestUtil.
				getBasicLayoutPageTemplateEntryLayout(serviceContext),
			LayoutPageTemplateEntryTestUtil.
				getDisplayPageLayoutPageTemplateEntryLayout(serviceContext),
			LayoutPageTemplateEntryTestUtil.
				getMasterLayoutPageTemplateEntryLayout(serviceContext),
			LayoutUtilityPageEntryTestUtil.getLayoutUtilityPageEntryLayout(
				serviceContext));
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSitePagePermissionsPage() throws Exception {
		super.testPutSiteSitePagePermissionsPage();
	}

	@Override
	protected boolean equals(SitePage sitePage1, SitePage sitePage2) {
		super.equals(sitePage1, sitePage2);

		return Objects.deepEquals(
			sitePage1.getPageSettings(), sitePage2.getPageSettings());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "friendlyUrlPath_i18n", "name_i18n",
			"type", "uuid"
		};
	}

	@Override
	protected SitePage randomSitePage() throws Exception {
		return _getRandomSitePage(_getRandomType(_types));
	}

	@Override
	protected SitePage
			testGetSiteSiteByExternalReferenceCodeSitePagesPage_addSitePage(
				String siteExternalReferenceCode, SitePage sitePage)
		throws Exception {

		return sitePageResource.postByExternalReferenceCodeSitePage(
			siteExternalReferenceCode, sitePage);
	}

	@Override
	protected String
		testGetSiteSiteByExternalReferenceCodeSitePagesPage_getIrrelevantSiteExternalReferenceCode() {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
		testGetSiteSiteByExternalReferenceCodeSitePagesPage_getSiteExternalReferenceCode() {

		return testGroup.getExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	protected SitePage testGetSiteSitePagePermissionsPage_addSitePage()
		throws Exception {

		return super.testGetSiteSitePagePermissionsPage_addSitePage();
	}

	@Override
	protected SitePage testPostByExternalReferenceCodeSitePage_addSitePage(
			SitePage sitePage)
		throws Exception {

		return sitePageResource.postByExternalReferenceCodeSitePage(
			testGroup.getExternalReferenceCode(), sitePage);
	}

	@Ignore
	@Override
	@Test
	protected SitePage testPutSiteSitePagePermissionsPage_addSitePage()
		throws Exception {

		return super.testPutSiteSitePagePermissionsPage_addSitePage();
	}

	private Layout _addLayout(
			String type, String typeSettings, ServiceContext serviceContext)
		throws Exception {

		return _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), testGroup.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 0,
			RandomTestUtil.randomLocaleStringMap(), Collections.emptyMap(),
			Collections.emptyMap(), Collections.emptyMap(),
			Collections.emptyMap(), type, typeSettings, false, false,
			Collections.emptyMap(), 0L, serviceContext);
	}

	private void _assertContentSitePage(SitePage sitePage) {
		Assert.assertEquals(SitePage.Type.CONTENT_PAGE, sitePage.getType());

		Assert.assertTrue(
			sitePage.getPageSettings() instanceof ContentPageSettings);
	}

	private void
			_assertDeleteSiteSiteByExternalReferenceCodeSitePageProblemException(
				Layout... layouts)
		throws Exception {

		for (Layout layout : layouts) {
			_assertProblemException(
				null,
				() ->
					sitePageResource.
						deleteSiteSiteByExternalReferenceCodeSitePage(
							testGroup.getExternalReferenceCode(),
							layout.getExternalReferenceCode()));
		}
	}

	private void _assertMapEquals(
		Map<String, String> expectedMap, Map<String, String> map) {

		Assert.assertEquals(
			MapUtil.toString(map), expectedMap.size(), map.size());

		for (Map.Entry<String, String> entry : expectedMap.entrySet()) {
			Assert.assertEquals(entry.getValue(), map.get(entry.getKey()));
		}
	}

	private void _assertNestedFields(SitePage sitePage) throws Exception {
		FriendlyUrlHistory friendlyUrlHistory =
			sitePage.getFriendlyUrlHistory();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			GetterUtil.getString(friendlyUrlHistory.getFriendlyUrlPath_i18n()));

		Layout layout = _layoutLocalService.getLayoutByExternalReferenceCode(
			sitePage.getExternalReferenceCode(), testGroup.getGroupId());

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		if (layout.isPublished()) {
			friendlyURLMap = layout.getFriendlyURLMap();
		}

		Assert.assertEquals(
			jsonObject.toString(), friendlyURLMap.size(), jsonObject.length());

		for (Map.Entry<Locale, String> entry : friendlyURLMap.entrySet()) {
			String key = LocaleUtil.toBCP47LanguageId(entry.getKey());

			JSONArray jsonArray = jsonObject.getJSONArray(key);

			Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());
			Assert.assertEquals(
				jsonArray.toString(), entry.getValue(), jsonArray.getString(0));
		}

		PageSpecificationsTestUtil.assertPageSpecifications(
			layout, sitePage.getPageSpecifications());
	}

	private void _assertPageSpecifications(
			ContentPageSpecification draftContentPageSpecification,
			ContentPageSpecification publishedContentPageSpecification,
			SitePage sitePage)
		throws Exception {

		Layout layout = _layoutLocalService.getLayoutByExternalReferenceCode(
			sitePage.getExternalReferenceCode(), testGroup.getGroupId());

		PageSpecification.Status status = PageSpecification.Status.APPROVED;

		if (!layout.isPublished()) {
			status = PageSpecification.Status.DRAFT;
		}

		PageSpecificationsTestUtil.assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			sitePage.getPageSpecifications(), layout, status);
	}

	private void _assertParentAndPriority(
			String expectedParentSitePageExternalReferenceCode,
			int expectedPriority, SitePage sitePage)
		throws Exception {

		SitePage getSitePage =
			sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode());

		Assert.assertEquals(
			expectedParentSitePageExternalReferenceCode,
			getSitePage.getParentSitePageExternalReferenceCode());

		PageSettings pageSettings = getSitePage.getPageSettings();

		Assert.assertEquals(expectedPriority, (int)pageSettings.getPriority());
	}

	private void _assertPatchSiteSiteByExternalReferenceCodeSitePage(
			SitePage expectedSitePage, SitePage sitePage)
		throws Exception {

		SitePage patchSitePage =
			sitePageResource.patchSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode(), sitePage);

		assertEquals(expectedSitePage, patchSitePage);
		assertValid(patchSitePage);

		_assertSitePage(
			_layoutLocalService.getLayoutByExternalReferenceCode(
				sitePage.getExternalReferenceCode(), testGroup.getGroupId()),
			patchSitePage);
	}

	private void
			_assertPatchSiteSiteByExternalReferenceCodeSitePageProblemException(
				Layout... layouts)
		throws Exception {

		for (Layout layout : layouts) {
			_assertPatchSiteSiteByExternalReferenceCodeSitePageProblemException(
				_getRandomSitePage(
					layout.getExternalReferenceCode(), null,
					SitePage.Type.CONTENT_PAGE, layout.getUuid()));
		}
	}

	private void
			_assertPatchSiteSiteByExternalReferenceCodeSitePageProblemException(
				SitePage sitePage)
		throws Exception {

		_assertProblemException(
			null,
			() -> sitePageResource.patchSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode(), sitePage));
	}

	private void
			_assertPostSiteSiteByExternalReferenceCodeSitePagePageSpecificationProblemException(
				Layout layout)
		throws Exception {

		_assertProblemException(
			null,
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
			String expectedTitle, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();
			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(expectedTitle, problem.getTitle());
		}
	}

	private void
			_assertPutSiteSiteByExternalReferenceCodeSitePageProblemException(
				Layout... layouts)
		throws Exception {

		for (Layout layout : layouts) {
			_assertPutSiteSiteByExternalReferenceCodeSitePageProblemException(
				_getRandomSitePage(
					layout.getExternalReferenceCode(), null,
					SitePage.Type.CONTENT_PAGE, layout.getUuid()));
		}
	}

	private void
			_assertPutSiteSiteByExternalReferenceCodeSitePageProblemException(
				SitePage sitePage)
		throws Exception {

		_assertProblemException(
			null,
			() -> sitePageResource.putSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode(), sitePage));
	}

	private void _assertSitePage(Layout layout, SitePage sitePage)
		throws Exception {

		Assert.assertArrayEquals(
			LocaleUtil.toW3cLanguageIds(layout.getAvailableLanguageIds()),
			sitePage.getAvailableLanguages());
		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			sitePage.getExternalReferenceCode());

		_assertMapEquals(
			LocalizedMapUtil.getI18nMap(true, layout.getFriendlyURLMap()),
			sitePage.getFriendlyUrlPath_i18n());
		_assertMapEquals(
			LocalizedMapUtil.getI18nMap(true, layout.getNameMap()),
			sitePage.getName_i18n());

		if (layout.getParentLayoutId() == 0) {
			Assert.assertTrue(
				Validator.isNull(
					sitePage.getParentSitePageExternalReferenceCode()));
		}
		else {
			Layout parentLayout = _layoutLocalService.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId());

			Assert.assertEquals(
				parentLayout.getExternalReferenceCode(),
				sitePage.getParentSitePageExternalReferenceCode());
		}

		PageSettings pageSettings = sitePage.getPageSettings();

		Assert.assertEquals(
			layout.getPriority(), (int)pageSettings.getPriority());

		Assert.assertEquals(layout.getUuid(), sitePage.getUuid());

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT)) {
			_assertContentSitePage(sitePage);
		}
		else {
			_assertWidgetSitePage(layout, sitePage);
		}
	}

	private void _assertWidgetSitePage(Layout layout, SitePage sitePage) {
		Assert.assertEquals(SitePage.Type.WIDGET_PAGE, sitePage.getType());

		WidgetPageSettings widgetPageSettings =
			(WidgetPageSettings)sitePage.getPageSettings();

		Assert.assertEquals(
			layout.getTypeSettingsProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID),
			widgetPageSettings.getLayoutTemplateId());
	}

	private int _getExpectedPriority(
			String defaultParentSitePageExternalReferenceCode,
			String parentSitePageExternalReferenceCode, Integer priority)
		throws Exception {

		long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		Layout parentLayout = null;

		if ((parentSitePageExternalReferenceCode == null) &&
			Validator.isNotNull(defaultParentSitePageExternalReferenceCode)) {

			parentLayout = _layoutLocalService.getLayoutByExternalReferenceCode(
				defaultParentSitePageExternalReferenceCode,
				testGroup.getGroupId());
		}
		else if (Validator.isNotNull(parentSitePageExternalReferenceCode)) {
			parentLayout = _layoutLocalService.getLayoutByExternalReferenceCode(
				parentSitePageExternalReferenceCode, testGroup.getGroupId());
		}

		if (parentLayout != null) {
			parentLayoutId = parentLayout.getLayoutId();
		}

		int maxPriority = _layoutLocalService.getLayoutsCount(
			testGroup.getGroupId(), false, parentLayoutId);

		if (maxPriority == 0) {
			return 0;
		}

		if ((parentSitePageExternalReferenceCode == null) ||
			Objects.equals(
				defaultParentSitePageExternalReferenceCode,
				parentSitePageExternalReferenceCode)) {

			maxPriority = maxPriority - 1;
		}

		if (priority == null) {
			return maxPriority;
		}

		return Math.min(priority, maxPriority);
	}

	private PageSettings _getPageSettings(SitePage.Type type) throws Exception {
		if (type == SitePage.Type.CONTENT_PAGE) {
			return new ContentPageSettings() {
				{
					setType(Type.CONTENT_PAGE_SETTINGS);
				}
			};
		}

		return new WidgetPageSettings() {
			{
				setLayoutTemplateId("1_column");
				setType(Type.WIDGET_PAGE_SETTINGS);
			}
		};
	}

	private PageSpecification.Type _getPageSpecificationType(
		SitePage.Type type) {

		PageSpecification.Type pageSpecificationType =
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION;

		if (type == SitePage.Type.WIDGET_PAGE) {
			pageSpecificationType =
				PageSpecification.Type.WIDGET_PAGE_SPECIFICATION;
		}

		return pageSpecificationType;
	}

	private SitePage _getRandomSitePage(SitePage.Type type) throws Exception {
		return _getRandomSitePage(
			StringUtil.toLowerCase(RandomTestUtil.randomString()), null, type,
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
	}

	private SitePage _getRandomSitePage(
			String externalReferenceCode,
			String parentSitePageExternalReferenceCode, SitePage.Type type,
			String uuid)
		throws Exception {

		SitePage sitePage = new SitePage();

		sitePage.setAvailableLanguages(
			() -> LocaleUtil.toW3cLanguageIds(
				new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN}));
		sitePage.setExternalReferenceCode(externalReferenceCode);
		sitePage.setFriendlyUrlPath_i18n(
			() -> HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
				StringPool.FORWARD_SLASH +
					StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
				StringPool.FORWARD_SLASH +
					StringUtil.toLowerCase(RandomTestUtil.randomString())
			).build());
		sitePage.setName_i18n(
			() -> HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
				RandomTestUtil.randomString()
			).put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
				RandomTestUtil.randomString()
			).build());

		PageSettings pageSettings = _getPageSettings(type);

		pageSettings.setHiddenFromNavigation(RandomTestUtil::randomBoolean);
		pageSettings.setPriority(
			_priorities.merge(
				parentSitePageExternalReferenceCode, 0,
				(oldPriority, defaultPriority) -> oldPriority + 1));

		sitePage.setPageSettings(pageSettings);

		sitePage.setParentSitePageExternalReferenceCode(
			parentSitePageExternalReferenceCode);
		sitePage.setType(type);
		sitePage.setUuid(uuid);

		return sitePage;
	}

	private SitePage.Type _getRandomType(List<SitePage.Type> types) {
		return types.get(RandomTestUtil.randomInt(0, types.size() - 1));
	}

	private SitePageResource _getSitePageResource(String nestedFields)
		throws Exception {

		User user = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		return SitePageResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", nestedFields
		).build();
	}

	private SitePage
			_postByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type type)
		throws Exception {

		SitePage randomSitePage = _getRandomSitePage(type);

		PageSpecification[] pageSpecifications =
			PageSpecificationsTestUtil.
				getPostPageSpecificationsWithCustomFields(
					randomSitePage.getExternalReferenceCode(),
					_getPageSpecificationType(type));

		randomSitePage.setPageSpecifications(pageSpecifications);

		SitePageResource sitePageResource = _getSitePageResource(
			"pageSpecifications");

		SitePage postSitePage =
			sitePageResource.postByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(), randomSitePage);

		PageSpecificationsTestUtil.assertCustomFields(
			pageSpecifications, testGroup.getGroupId(),
			postSitePage.getPageSpecifications());

		return postSitePage;
	}

	private void _testDeleteSiteSiteByExternalReferenceCodeSitePage(
			Layout... layouts)
		throws Exception {

		for (Layout layout : layouts) {
			sitePageResource.deleteSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				layout.getExternalReferenceCode());

			Assert.assertNull(
				_layoutLocalService.fetchLayoutByExternalReferenceCode(
					layout.getExternalReferenceCode(), testGroup.getGroupId()));
		}
	}

	private void _testGetSiteSiteByExternalReferenceCodeSitePage(Layout layout)
		throws Exception {

		SitePage sitePage =
			sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				layout.getExternalReferenceCode());

		_assertSitePage(layout, sitePage);
		_testGetSiteSiteByExternalReferenceCodeSitePageWithNestedFields(
			sitePage);
	}

	private void
			_testGetSiteSiteByExternalReferenceCodeSitePageWithNestedFields(
				SitePage sitePage)
		throws Exception {

		SitePageResource sitePageResource = _getSitePageResource(
			"friendlyUrlHistory,pageSpecifications");

		_assertNestedFields(
			sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode()));
	}

	private void _testPatchSiteSiteByExternalReferenceCodeSitePage(
			SitePage.Type type)
		throws Exception {

		SitePage sitePage = testPostByExternalReferenceCodeSitePage_addSitePage(
			_getRandomSitePage(type));

		_assertSitePage(
			_layoutLocalService.getLayoutByExternalReferenceCode(
				sitePage.getExternalReferenceCode(), testGroup.getGroupId()),
			sitePage);

		sitePage.setFriendlyUrlPath_i18n(
			() -> HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
				StringPool.FORWARD_SLASH +
					StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
				StringPool.FORWARD_SLASH +
					StringUtil.toLowerCase(RandomTestUtil.randomString())
			).build());

		_assertPatchSiteSiteByExternalReferenceCodeSitePage(
			sitePage,
			new SitePage() {
				{
					setExternalReferenceCode(
						sitePage::getExternalReferenceCode);
					setFriendlyUrlPath_i18n(sitePage::getFriendlyUrlPath_i18n);
					setType(sitePage::getType);
					setUuid(sitePage::getUuid);
				}
			});

		sitePage.setName_i18n(
			() -> HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
				RandomTestUtil.randomString()
			).put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
				RandomTestUtil.randomString()
			).build());

		_assertPatchSiteSiteByExternalReferenceCodeSitePage(
			sitePage,
			new SitePage() {
				{
					setExternalReferenceCode(
						sitePage::getExternalReferenceCode);
					setName_i18n(sitePage::getName_i18n);
					setType(sitePage::getType);
					setUuid(sitePage::getUuid);
				}
			});

		PageSettings pageSettings = sitePage.getPageSettings();

		pageSettings.setHiddenFromNavigation(
			() -> !pageSettings.getHiddenFromNavigation());

		_assertPatchSiteSiteByExternalReferenceCodeSitePage(
			sitePage,
			new SitePage() {
				{
					setExternalReferenceCode(
						sitePage::getExternalReferenceCode);
					setPageSettings(sitePage::getPageSettings);
					setType(sitePage::getType);
					setUuid(sitePage::getUuid);
				}
			});

		Layout layout = LayoutTestUtil.addTypePortletLayout(testGroup);

		sitePage.setParentSitePageExternalReferenceCode(
			layout.getExternalReferenceCode());

		pageSettings.setPriority(0);

		_assertPatchSiteSiteByExternalReferenceCodeSitePage(
			sitePage,
			new SitePage() {
				{
					setExternalReferenceCode(
						sitePage::getExternalReferenceCode);
					setParentSitePageExternalReferenceCode(
						layout::getExternalReferenceCode);
					setType(sitePage::getType);
					setUuid(sitePage::getUuid);
				}
			});

		_assertPatchSiteSiteByExternalReferenceCodeSitePageProblemException(
			_getRandomSitePage(
				sitePage.getExternalReferenceCode(), null,
				_getRandomType(
					ListUtil.filter(
						_types, curType -> !Objects.equals(curType, type))),
				sitePage.getUuid()));
	}

	private void _testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications()
		throws Exception {

		_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT,
			PageSpecification.Status.APPROVED);
		_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT,
			PageSpecification.Status.DRAFT);
		_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED);
		_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.DRAFT,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT);
		_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields();
	}

	private void
			_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
				PageSpecification.Status newDraftLayoutStatus,
				PageSpecification.Status newPublishedLayoutStatus,
				PageSpecification.Status oldDraftLayoutStatus,
				PageSpecification.Status oldPublishedLayoutStatus)
		throws Exception {

		SitePage sitePage = _getRandomSitePage(SitePage.Type.CONTENT_PAGE);

		ContentPageSpecification draftContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				null, oldDraftLayoutStatus);

		ContentPageSpecification publishedContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				draftContentPageSpecification.getExternalReferenceCode(),
				oldPublishedLayoutStatus);

		publishedContentPageSpecification.setExternalReferenceCode(
			sitePage.getExternalReferenceCode());

		sitePage.setPageSpecifications(
			() -> new PageSpecification[] {
				publishedContentPageSpecification, draftContentPageSpecification
			});

		SitePageResource sitePageResource = _getSitePageResource(
			"pageSpecifications");

		SitePage postSitePage =
			sitePageResource.postByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(), sitePage);

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			postSitePage);

		draftContentPageSpecification.setStatus(newDraftLayoutStatus);
		publishedContentPageSpecification.setStatus(newPublishedLayoutStatus);

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			sitePageResource.patchSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				postSitePage.getExternalReferenceCode(),
				new SitePage() {
					{
						setPageSpecifications(
							() -> new PageSpecification[] {
								publishedContentPageSpecification,
								draftContentPageSpecification
							});
					}
				}));
	}

	private void _testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields()
		throws Exception {

		try (PageSpecificationsTestUtil.ExpandoTableAutocloseable
				expandoTableAutoCloseable =
					PageSpecificationsTestUtil.getExpandoTableAutoCloseable()) {

			_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type.CONTENT_PAGE);
			_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type.WIDGET_PAGE);
		}
	}

	private void
			_testPatchSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type type)
		throws Exception {

		SitePage postSitePage =
			_postByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				type);

		CustomField[][] postCustomFields = {
			ArrayUtil.clone(
				postSitePage.getPageSpecifications()[0].getCustomFields())
		};

		if (type == SitePage.Type.CONTENT_PAGE) {
			postCustomFields = ArrayUtil.append(
				postCustomFields,
				ArrayUtil.clone(
					postSitePage.getPageSpecifications()[1].getCustomFields()));
		}

		SitePageResource sitePageResource = _getSitePageResource(
			"pageSpecifications");

		SitePage patchBodySitePage = new SitePage() {
			{
				setExternalReferenceCode(
					postSitePage.getExternalReferenceCode());
				setPageSpecifications(
					PageSpecificationsTestUtil.getPatchPageSpecifications(
						postSitePage.getPageSpecifications()));
				setType(postSitePage.getType());
			}
		};

		PageSpecification[] patchBodyPagePageSpecifications =
			patchBodySitePage.getPageSpecifications();

		CustomField[][] updateCustomFields =
			PageSpecificationsTestUtil.getCustomFields(
				_getPageSpecificationType(type));

		CustomField[] publishedCustomFields = updateCustomFields[0];

		patchBodyPagePageSpecifications[0].setCustomFields(
			new CustomField[] {
				publishedCustomFields[0], publishedCustomFields[1]
			});

		if (patchBodyPagePageSpecifications.length == 2) {
			CustomField[] draftCustomFields = updateCustomFields[1];

			patchBodyPagePageSpecifications[1].setCustomFields(
				new CustomField[] {draftCustomFields[0], draftCustomFields[1]});
		}

		SitePage patchSitePage =
			sitePageResource.patchSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				postSitePage.getExternalReferenceCode(), patchBodySitePage);

		PageSpecificationsTestUtil.assertUpdateCustomFields(
			testGroup.getGroupId(), patchSitePage.getPageSpecifications(),
			postCustomFields, updateCustomFields);
	}

	private void _testPatchSiteSiteByExternalReferenceCodeSitePageWithPriority()
		throws Exception {

		_testUpdateSiteSiteByExternalReferenceCodeSitePageWithPriority(
			(curParentSitePageExternalReferenceCode, curPriority, sitePage) -> {
				int expectedPriority = _getExpectedPriority(
					sitePage.getParentSitePageExternalReferenceCode(),
					curParentSitePageExternalReferenceCode, curPriority);

				sitePage.setParentSitePageExternalReferenceCode(
					() -> curParentSitePageExternalReferenceCode);

				PageSettings curPageSettings = sitePage.getPageSettings();

				curPageSettings.setPriority(() -> curPriority);

				SitePage patchSitePage =
					sitePageResource.
						patchSiteSiteByExternalReferenceCodeSitePage(
							testGroup.getExternalReferenceCode(),
							sitePage.getExternalReferenceCode(),
							new SitePage() {
								{
									setPageSettings(() -> curPageSettings);
									setParentSitePageExternalReferenceCode(
										() ->
											curParentSitePageExternalReferenceCode);
								}
							});

				curPageSettings.setPriority(expectedPriority);

				assertEquals(sitePage, patchSitePage);
				assertValid(patchSitePage);

				_assertSitePage(
					_layoutLocalService.getLayoutByExternalReferenceCode(
						sitePage.getExternalReferenceCode(),
						testGroup.getGroupId()),
					patchSitePage);
			});
	}

	private void _testPostByExternalReferenceCodeSitePage(SitePage sitePage)
		throws Exception {

		SitePage postSitePage =
			testPostByExternalReferenceCodeSitePage_addSitePage(sitePage);

		assertEquals(sitePage, postSitePage);
		assertValid(postSitePage);

		_assertSitePage(
			_layoutLocalService.getLayoutByExternalReferenceCode(
				sitePage.getExternalReferenceCode(), testGroup.getGroupId()),
			postSitePage);
	}

	private void _testPostByExternalReferenceCodeSitePageWithPageSpecifications()
		throws Exception {

		_testPostByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED);
		_testPostByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT);
		_testPostByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.APPROVED);
		_testPostByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.DRAFT);
		_testPostByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields();

		SitePage sitePage = _getRandomSitePage(SitePage.Type.CONTENT_PAGE);

		ContentPageSpecification draftContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				null, PageSpecification.Status.APPROVED);

		ContentPageSpecification publishedContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				draftContentPageSpecification.getExternalReferenceCode(),
				PageSpecification.Status.APPROVED);

		sitePage.setPageSpecifications(
			() -> new PageSpecification[] {
				publishedContentPageSpecification, draftContentPageSpecification
			});

		_assertProblemException(
			StringBundler.concat(
				"Site page external reference code ",
				sitePage.getExternalReferenceCode(),
				" does not match published page specification external ",
				"reference code ",
				publishedContentPageSpecification.getExternalReferenceCode()),
			() -> sitePageResource.postByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(), sitePage));
	}

	private void _testPostByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status draftLayoutStatus,
			PageSpecification.Status publishedLayoutStatus)
		throws Exception {

		SitePage sitePage = _getRandomSitePage(SitePage.Type.CONTENT_PAGE);

		ContentPageSpecification draftContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				null, draftLayoutStatus);

		ContentPageSpecification publishedContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				draftContentPageSpecification.getExternalReferenceCode(),
				publishedLayoutStatus);

		publishedContentPageSpecification.setExternalReferenceCode(
			sitePage.getExternalReferenceCode());

		sitePage.setPageSpecifications(
			() -> new PageSpecification[] {
				publishedContentPageSpecification, draftContentPageSpecification
			});

		SitePageResource sitePageResource = _getSitePageResource(
			"pageSpecifications");

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			sitePageResource.postByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(), sitePage));
	}

	private void _testPostByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields()
		throws Exception {

		try (PageSpecificationsTestUtil.ExpandoTableAutocloseable
				expandoTableAutoCloseable =
					PageSpecificationsTestUtil.getExpandoTableAutoCloseable()) {

			_postByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type.CONTENT_PAGE);
			_postByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type.WIDGET_PAGE);
		}
	}

	private void _testPutSiteSiteByExternalReferenceCodeSitePage(
			SitePage.Type type)
		throws Exception {

		SitePage sitePage = testPostByExternalReferenceCodeSitePage_addSitePage(
			_getRandomSitePage(type));

		_assertSitePage(
			_layoutLocalService.getLayoutByExternalReferenceCode(
				sitePage.getExternalReferenceCode(), testGroup.getGroupId()),
			sitePage);

		Layout layout = LayoutTestUtil.addTypePortletLayout(testGroup);

		sitePage = _getRandomSitePage(
			sitePage.getExternalReferenceCode(),
			layout.getExternalReferenceCode(), type, sitePage.getUuid());

		SitePage putSitePage =
			sitePageResource.putSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode(), sitePage);

		assertEquals(sitePage, putSitePage);
		assertValid(putSitePage);

		_assertSitePage(
			_layoutLocalService.getLayoutByExternalReferenceCode(
				sitePage.getExternalReferenceCode(), testGroup.getGroupId()),
			putSitePage);

		_assertPutSiteSiteByExternalReferenceCodeSitePageProblemException(
			_getRandomSitePage(
				sitePage.getExternalReferenceCode(), null,
				_getRandomType(
					ListUtil.filter(
						_types, curType -> !Objects.equals(curType, type))),
				sitePage.getUuid()));
	}

	private void _testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications()
		throws Exception {

		_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT,
			PageSpecification.Status.APPROVED);
		_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT,
			PageSpecification.Status.DRAFT);
		_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED);
		_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.DRAFT,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT);
		_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields();
	}

	private void
			_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecifications(
				PageSpecification.Status newDraftLayoutStatus,
				PageSpecification.Status newPublishedLayoutStatus,
				PageSpecification.Status oldDraftLayoutStatus,
				PageSpecification.Status oldPublishedLayoutStatus)
		throws Exception {

		SitePage sitePage = _getRandomSitePage(SitePage.Type.CONTENT_PAGE);

		ContentPageSpecification draftContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				null, oldDraftLayoutStatus);

		ContentPageSpecification publishedContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				draftContentPageSpecification.getExternalReferenceCode(),
				oldPublishedLayoutStatus);

		publishedContentPageSpecification.setExternalReferenceCode(
			sitePage.getExternalReferenceCode());

		sitePage.setPageSpecifications(
			() -> new PageSpecification[] {
				publishedContentPageSpecification, draftContentPageSpecification
			});

		SitePageResource sitePageResource = _getSitePageResource(
			"pageSpecifications");

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			sitePageResource.putSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode(), sitePage));

		draftContentPageSpecification.setStatus(newDraftLayoutStatus);
		publishedContentPageSpecification.setStatus(newPublishedLayoutStatus);

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			sitePageResource.putSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode(), sitePage));
	}

	private void _testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields()
		throws Exception {

		try (PageSpecificationsTestUtil.ExpandoTableAutocloseable
				expandoTableAutoCloseable =
					PageSpecificationsTestUtil.getExpandoTableAutoCloseable()) {

			_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type.CONTENT_PAGE);
			_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type.WIDGET_PAGE);
		}
	}

	private void
			_testPutSiteSiteByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				SitePage.Type type)
		throws Exception {

		SitePage postSitePage =
			_postByExternalReferenceCodeSitePageWithPageSpecificationsWithCustomFields(
				type);

		CustomField[][] postCustomFields = {
			ArrayUtil.clone(
				postSitePage.getPageSpecifications()[0].getCustomFields())
		};

		if (type == SitePage.Type.CONTENT_PAGE) {
			postCustomFields = ArrayUtil.append(
				postCustomFields,
				ArrayUtil.clone(
					postSitePage.getPageSpecifications()[1].getCustomFields()));
		}

		CustomField[][] updateCustomFields =
			PageSpecificationsTestUtil.getCustomFields(
				_getPageSpecificationType(type));

		SitePageResource sitePageResource = _getSitePageResource(
			"pageSpecifications");

		PageSpecification[] putBodyPagePageSpecifications =
			postSitePage.getPageSpecifications();

		CustomField[] publishedCustomFields = updateCustomFields[0];

		putBodyPagePageSpecifications[0].setCustomFields(
			new CustomField[] {
				publishedCustomFields[0], publishedCustomFields[1]
			});

		if (putBodyPagePageSpecifications.length == 2) {
			CustomField[] draftCustomFields = updateCustomFields[1];

			putBodyPagePageSpecifications[1].setCustomFields(
				new CustomField[] {draftCustomFields[0], draftCustomFields[1]});
		}

		SitePage updateSitePage =
			sitePageResource.putSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				postSitePage.getExternalReferenceCode(), postSitePage);

		PageSpecificationsTestUtil.assertUpdateCustomFields(
			testGroup.getGroupId(), updateSitePage.getPageSpecifications(),
			postCustomFields, updateCustomFields);
	}

	private void _testPutSiteSiteByExternalReferenceCodeSitePageWithPriority()
		throws Exception {

		_testUpdateSiteSiteByExternalReferenceCodeSitePageWithPriority(
			(parentSitePageExternalReferenceCode, priority, sitePage) -> {
				int expectedPriority = _getExpectedPriority(
					sitePage.getParentSitePageExternalReferenceCode(),
					parentSitePageExternalReferenceCode, priority);

				sitePage.setParentSitePageExternalReferenceCode(
					parentSitePageExternalReferenceCode);

				PageSettings pageSettings = sitePage.getPageSettings();

				pageSettings.setPriority(priority);

				SitePage putSitePage =
					sitePageResource.putSiteSiteByExternalReferenceCodeSitePage(
						testGroup.getExternalReferenceCode(),
						sitePage.getExternalReferenceCode(), sitePage);

				pageSettings.setPriority(expectedPriority);

				assertEquals(sitePage, putSitePage);
				assertValid(putSitePage);

				_assertSitePage(
					_layoutLocalService.getLayoutByExternalReferenceCode(
						sitePage.getExternalReferenceCode(),
						testGroup.getGroupId()),
					putSitePage);
			});
	}

	private void _testUpdateSiteSiteByExternalReferenceCodeSitePageWithPriority(
			UnsafeTriConsumer<String, Integer, SitePage, Exception>
				unsafeTriConsumer)
		throws Exception {

		Page<SitePage> page =
			sitePageResource.getSiteSiteByExternalReferenceCodeSitePagesPage(
				testGroup.getExternalReferenceCode(), null, null, null,
				Pagination.of(0, 0), null);

		for (SitePage sitePage : page.getItems()) {
			if (Validator.isNotNull(
					sitePage.getParentSitePageExternalReferenceCode())) {

				continue;
			}

			sitePageResource.deleteSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode());
		}

		_priorities.clear();

		SitePage sitePage1 =
			testPostByExternalReferenceCodeSitePage_addSitePage(
				randomSitePage());
		SitePage sitePage2 =
			testPostByExternalReferenceCodeSitePage_addSitePage(
				randomSitePage());
		SitePage sitePage3 =
			testPostByExternalReferenceCodeSitePage_addSitePage(
				randomSitePage());
		SitePage sitePage4 =
			testPostByExternalReferenceCodeSitePage_addSitePage(
				randomSitePage());
		SitePage sitePage5 =
			testPostByExternalReferenceCodeSitePage_addSitePage(
				randomSitePage());

		_assertParentAndPriority(null, 0, sitePage1);
		_assertParentAndPriority(null, 1, sitePage2);
		_assertParentAndPriority(null, 2, sitePage3);
		_assertParentAndPriority(null, 3, sitePage4);
		_assertParentAndPriority(null, 4, sitePage5);

		unsafeTriConsumer.accept(null, 1, sitePage4);

		_assertParentAndPriority(null, 0, sitePage1);
		_assertParentAndPriority(null, 1, sitePage4);
		_assertParentAndPriority(null, 2, sitePage2);
		_assertParentAndPriority(null, 3, sitePage3);
		_assertParentAndPriority(null, 4, sitePage5);

		unsafeTriConsumer.accept(null, 5, sitePage5);

		_assertParentAndPriority(null, 0, sitePage1);
		_assertParentAndPriority(null, 1, sitePage4);
		_assertParentAndPriority(null, 2, sitePage2);
		_assertParentAndPriority(null, 3, sitePage3);
		_assertParentAndPriority(null, 4, sitePage5);

		unsafeTriConsumer.accept(null, null, sitePage3);

		_assertParentAndPriority(null, 0, sitePage1);
		_assertParentAndPriority(null, 1, sitePage4);
		_assertParentAndPriority(null, 2, sitePage2);
		_assertParentAndPriority(null, 3, sitePage5);
		_assertParentAndPriority(null, 4, sitePage3);

		unsafeTriConsumer.accept(
			sitePage1.getExternalReferenceCode(), 2, sitePage2);

		_assertParentAndPriority(null, 0, sitePage1);
		_assertParentAndPriority(null, 1, sitePage4);
		_assertParentAndPriority(null, 3, sitePage5);
		_assertParentAndPriority(null, 4, sitePage3);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 0, sitePage2);

		unsafeTriConsumer.accept(
			sitePage1.getExternalReferenceCode(), 1, sitePage4);

		_assertParentAndPriority(null, 0, sitePage1);
		_assertParentAndPriority(null, 3, sitePage5);
		_assertParentAndPriority(null, 4, sitePage3);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 0, sitePage2);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 1, sitePage4);

		unsafeTriConsumer.accept(
			sitePage1.getExternalReferenceCode(), 3, sitePage3);

		_assertParentAndPriority(null, 0, sitePage1);
		_assertParentAndPriority(null, 3, sitePage5);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 0, sitePage2);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 1, sitePage4);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 2, sitePage3);

		unsafeTriConsumer.accept(
			sitePage1.getExternalReferenceCode(), 0, sitePage3);

		_assertParentAndPriority(null, 0, sitePage1);
		_assertParentAndPriority(null, 3, sitePage5);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 0, sitePage3);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 1, sitePage2);
		_assertParentAndPriority(
			sitePage1.getExternalReferenceCode(), 2, sitePage4);
	}

	private static final List<SitePage.Type> _types = Arrays.asList(
		SitePage.Type.CONTENT_PAGE, SitePage.Type.WIDGET_PAGE);

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private final Map<String, Integer> _priorities = new HashMap<>();

}