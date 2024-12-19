/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.headless.admin.site.client.dto.v1_0.ClassNameReference;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionPageSettings;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSettings;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSettings;
import com.liferay.headless.admin.site.client.dto.v1_0.Scope;
import com.liferay.headless.admin.site.client.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.client.resource.v1_0.SitePageResource;
import com.liferay.headless.admin.site.dto.v1_0.CollectionReference;
import com.liferay.headless.admin.site.resource.v1_0.test.util.LayoutPageTemplateEntryTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.LayoutUtilityPageEntryTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.PageSpecificationsTestUtil;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		_testDeleteSiteSiteByExternalReferenceCodeSitePage(
			_addLayout(LayoutConstants.TYPE_COLLECTION, null, serviceContext),
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

		_testGetSiteSiteByExternalReferenceCodeSitePage(
			_addLayout(LayoutConstants.TYPE_COLLECTION, null, serviceContext));
		_testGetSiteSiteByExternalReferenceCodeSitePage(
			_addLayout(LayoutConstants.TYPE_CONTENT, null, serviceContext));
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

		SitePageResource sitePageResource = _getSitePageResource();

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
		List<SitePage.Type> types = Arrays.asList(
			SitePage.Type.COLLECTION_PAGE, SitePage.Type.CONTENT_PAGE,
			SitePage.Type.WIDGET_PAGE);

		return _getRandomSitePage(
			types.get(RandomTestUtil.randomInt(0, types.size() - 1)));
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

	private void _assertCollectionSitePage(Layout layout, SitePage sitePage)
		throws Exception {

		Assert.assertEquals(SitePage.Type.COLLECTION_PAGE, sitePage.getType());

		String collectionType = layout.getTypeSettingsProperty(
			"collectionType");

		if (Validator.isNull(collectionType)) {
			Assert.assertTrue(
				sitePage.getPageSettings() instanceof CollectionPageSettings);

			return;
		}

		CollectionPageSettings collectionPageSettings =
			(CollectionPageSettings)sitePage.getPageSettings();

		if (Objects.equals(
				collectionType,
				InfoListProviderItemSelectorReturnType.class.getName())) {

			ClassNameReference classNameReference =
				(ClassNameReference)
					collectionPageSettings.getCollectionReference();

			Assert.assertEquals(
				layout.getTypeSettingsProperty("collectionPK"),
				classNameReference.getClassName());
			Assert.assertEquals(
				CollectionReference.CollectionType.COLLECTION_PROVIDER,
				classNameReference.getCollectionType());

			return;
		}

		ItemExternalReference itemExternalReference =
			(ItemExternalReference)
				collectionPageSettings.getCollectionReference();

		Assert.assertEquals(
			AssetListEntry.class.getName(),
			itemExternalReference.getClassName());
		Assert.assertEquals(
			CollectionReference.CollectionType.COLLECTION,
			itemExternalReference.getCollectionType());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.getAssetListEntry(
				GetterUtil.getLong(
					layout.getTypeSettingsProperty("collectionPK")));

		Assert.assertEquals(
			assetListEntry.getExternalReferenceCode(),
			itemExternalReference.getExternalReferenceCode());

		Scope scope = itemExternalReference.getScope();

		if (assetListEntry.getGroupId() == layout.getGroupId()) {
			Assert.assertNull(scope);

			return;
		}

		Group group = _groupLocalService.getGroup(assetListEntry.getGroupId());

		Assert.assertEquals(
			group.getExternalReferenceCode(), scope.getExternalReferenceCode());

		Scope.Type scopeType = Scope.Type.SITE;

		if (group.isDepot()) {
			scopeType = Scope.Type.ASSET_LIBRARY;
		}

		Assert.assertEquals(scopeType, scope.getType());
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

		PageSpecificationsTestUtil.assertPageSpecifications(
			layout, sitePage.getPageSpecifications());
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

		Assert.assertEquals(layout.getUuid(), sitePage.getUuid());

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_COLLECTION)) {
			_assertCollectionSitePage(layout, sitePage);
		}
		else if (Objects.equals(
					layout.getType(), LayoutConstants.TYPE_CONTENT)) {

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

	private ClassNameReference _getClassNameReference() {
		return new ClassNameReference() {
			{
				setClassName(
					() ->
						"com.liferay.asset.internal.info.collection.provider." +
							"RecentContentInfoCollectionProvider");
				setCollectionType(() -> CollectionType.COLLECTION_PROVIDER);
			}
		};
	}

	private CollectionPageSettings _getCollectionPageSettings()
		throws Exception {

		if (RandomTestUtil.randomBoolean()) {
			return new CollectionPageSettings() {
				{
					setCollectionReference(() -> _getClassNameReference());
					setType(Type.COLLECTION_PAGE_SETTINGS);
				}
			};
		}

		ItemExternalReference itemExternalReference =
			new ItemExternalReference() {
				{
					setClassName(() -> AssetListEntry.class.getName());
					setCollectionType(() -> CollectionType.COLLECTION);
				}
			};

		AssetListEntry assetListEntry;

		if (RandomTestUtil.randomBoolean()) {
			assetListEntry = AssetListEntryLocalServiceUtil.addAssetListEntry(
				null, TestPropsValues.getUserId(), testGroup.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC,
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId()));
		}
		else {
			assetListEntry = AssetListEntryLocalServiceUtil.addAssetListEntry(
				null, TestPropsValues.getUserId(), irrelevantGroup.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC,
				ServiceContextTestUtil.getServiceContext(
					irrelevantGroup.getGroupId()));

			itemExternalReference.setScope(
				() -> new Scope() {
					{
						setExternalReferenceCode(
							irrelevantGroup::getExternalReferenceCode);
						setType(Type.SITE);
					}
				});
		}

		itemExternalReference.setExternalReferenceCode(
			assetListEntry::getExternalReferenceCode);

		return new CollectionPageSettings() {
			{
				setCollectionReference(() -> itemExternalReference);
				setType(Type.COLLECTION_PAGE_SETTINGS);
			}
		};
	}

	private PageSettings _getPageSettings(SitePage.Type type) throws Exception {
		if (type == SitePage.Type.COLLECTION_PAGE) {
			return _getCollectionPageSettings();
		}

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

	private SitePage _getRandomSitePage(SitePage.Type curType)
		throws Exception {

		SitePage sitePage = new SitePage() {
			{
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};

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
			() -> {
				PageSettings pageSettings = _getPageSettings(curType);

				pageSettings.setHiddenFromNavigation(
					RandomTestUtil::randomBoolean);

				return pageSettings;
			});
		sitePage.setType(() -> curType);

		return sitePage;
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

		SitePageResource sitePageResource = _getSitePageResource();

		_assertNestedFields(
			sitePageResource.getSiteSiteByExternalReferenceCodeSitePage(
				testGroup.getExternalReferenceCode(),
				sitePage.getExternalReferenceCode()));
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private UserLocalService _userLocalService;

}