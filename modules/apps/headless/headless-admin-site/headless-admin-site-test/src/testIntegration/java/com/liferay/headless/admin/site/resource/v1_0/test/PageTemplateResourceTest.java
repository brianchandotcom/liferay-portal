/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageTemplateSettings;
import com.liferay.headless.admin.site.client.dto.v1_0.NavigationSettings;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageTemplateSettings;
import com.liferay.headless.admin.site.client.pagination.Page;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.client.resource.v1_0.PageTemplateResource;
import com.liferay.headless.admin.site.resource.v1_0.test.util.AssetTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.LayoutPageTemplateEntryTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.PageSpecificationsTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.SettingsTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
public class PageTemplateResourceTest extends BasePageTemplateResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		PageTemplate pageTemplate =
			testPostSiteSiteByExternalReferenceCodePageTemplate_addPageTemplate(
				randomPageTemplate());

		_testDeleteSiteSiteByExternalReferenceCodePageTemplate(
			testGroup, pageTemplate.getExternalReferenceCode());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateResource.
					deleteSiteSiteByExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						pageTemplate.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, widgetPageTemplate) -> {
				_postSiteSiteByExternalReferenceCodePageTemplate(
					widgetPageTemplate, group.getExternalReferenceCode());

				_testDeleteSiteSiteByExternalReferenceCodePageTemplate(
					group, widgetPageTemplate.getExternalReferenceCode());
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() ->
					pageTemplateResource.
						deleteSiteSiteByExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							RandomTestUtil.randomString())));
	}

	@Ignore
	@Override
	@Test
	public void testGetSitePageTemplatePermissionsPage() throws Exception {
		super.testGetSitePageTemplatePermissionsPage();
	}

	@Override
	@Test
	@TestInfo("LPD-44414")
	public void testGetSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		PageTemplate pageTemplate =
			testPostSiteSiteByExternalReferenceCodePageTemplate_addPageTemplate(
				randomPageTemplate());

		_testGetSiteSiteByExternalReferenceCodePageTemplate(pageTemplate);

		_testGetSiteSiteByExternalReferenceCodePageTemplateWithNestedFields(
			_getContentPageTemplate(testGroup));
		_testGetSiteSiteByExternalReferenceCodePageTemplateWithNestedFields(
			_getWidgetPageTemplate(testGroup));

		_assertProblemException(
			"NOT_FOUND",
			() ->
				pageTemplateResource.
					getSiteSiteByExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						RandomTestUtil.randomString()));

		_enableLocalStaging();

		_testGetSiteSiteByExternalReferenceCodePageTemplate(pageTemplate);

		_withCompanyGroupWidgetPageTemplate(
			(group, widgetPageTemplate) ->
				_postSiteSiteByExternalReferenceCodePageTemplate(
					widgetPageTemplate, group.getExternalReferenceCode()));

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() ->
					pageTemplateResource.
						getSiteSiteByExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							RandomTestUtil.randomString())));
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPage()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodePageTemplatesPage();

		long totalCount =
			_getSiteSiteByExternalReferenceCodePageTemplatesPageTotalCount(
				testGroup.getExternalReferenceCode());

		_enableLocalStaging();

		Assert.assertEquals(
			totalCount,
			_getSiteSiteByExternalReferenceCodePageTemplatesPageTotalCount(
				testGroup.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, widgetPageTemplate) -> {
				long curTotalCount =
					_getSiteSiteByExternalReferenceCodePageTemplatesPageTotalCount(
						group.getExternalReferenceCode());

				_postSiteSiteByExternalReferenceCodePageTemplate(
					widgetPageTemplate, group.getExternalReferenceCode());

				Assert.assertEquals(
					curTotalCount + 1,
					_getSiteSiteByExternalReferenceCodePageTemplatesPageTotalCount(
						group.getExternalReferenceCode()));
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() ->
					_getSiteSiteByExternalReferenceCodePageTemplatesPageTotalCount(
						group.getExternalReferenceCode())));
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
	public void testGraphQLGetSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testGraphQLGetSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		ContentPageTemplate contentPageTemplate =
			(ContentPageTemplate)
				pageTemplateResource.
					postSiteSiteByExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						_getContentPageTemplate(testGroup));

		_testPatchSiteSiteByExternalReferenceCodePageTemplate(
			_getUpdatedContentPageTemplate(
				testGroup, contentPageTemplate.getExternalReferenceCode()),
			testGroup.getExternalReferenceCode());

		WidgetPageTemplate widgetPageTemplate =
			(WidgetPageTemplate)
				pageTemplateResource.
					postSiteSiteByExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						_getWidgetPageTemplate(testGroup));

		_testPatchSiteSiteByExternalReferenceCodePageTemplate(
			_getUpdatedWidgetPageTemplate(
				testGroup, widgetPageTemplate.getExternalReferenceCode()),
			testGroup.getExternalReferenceCode());

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateResource.
					patchSiteSiteByExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						widgetPageTemplate.getExternalReferenceCode(),
						new WidgetPageTemplate() {
							{
								setExternalReferenceCode(
									widgetPageTemplate::
										getExternalReferenceCode);
								setPageTemplateSet(
									() -> new PageTemplateSet() {
										{
											setExternalReferenceCode(
												() -> StringPool.BLANK);
										}
									});
								setType(
									() ->
										PageTemplate.Type.WIDGET_PAGE_TEMPLATE);
							}
						}));

		_testPatchSiteSiteByExternalReferenceCodePageTemplateWithPageSpecifications();

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() -> _testPatchSiteSiteByExternalReferenceCodePageTemplate(
				contentPageTemplate, testGroup.getExternalReferenceCode()));

		_assertProblemException(
			"BAD_REQUEST",
			() -> _testPatchSiteSiteByExternalReferenceCodePageTemplate(
				widgetPageTemplate, testGroup.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, curWidgetPageTemplate) -> {
				_postSiteSiteByExternalReferenceCodePageTemplate(
					curWidgetPageTemplate, group.getExternalReferenceCode());

				_testPatchSiteSiteByExternalReferenceCodePageTemplate(
					_getUpdatedWidgetPageTemplate(
						group,
						curWidgetPageTemplate.getExternalReferenceCode()),
					group.getExternalReferenceCode());

				_assertProblemException(
					"BAD_REQUEST",
					() -> {
						ContentPageTemplate curContentPageTemplate =
							_getContentPageTemplate(group);

						pageTemplateResource.
							putSiteSiteByExternalReferenceCodePageTemplate(
								group.getExternalReferenceCode(),
								curContentPageTemplate.
									getExternalReferenceCode(),
								curContentPageTemplate);
					});
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() -> {
					PageTemplate pageTemplate = _getPageTemplate(group);

					pageTemplateResource.
						putSiteSiteByExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							pageTemplate.getExternalReferenceCode(),
							pageTemplate);
				}));
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		PageTemplate randomPageTemplate = randomPageTemplate();

		randomPageTemplate.setKey(StringPool.BLANK);

		PageTemplate postPageTemplate =
			testPostSiteSiteByExternalReferenceCodePageTemplate_addPageTemplate(
				randomPageTemplate);

		assertEquals(randomPageTemplate, postPageTemplate);
		assertValid(postPageTemplate);

		Assert.assertTrue(Validator.isNotNull(postPageTemplate.getKey()));

		ContentPageTemplate contentPageTemplate = _getContentPageTemplate(
			testGroup);

		postPageTemplate =
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(), contentPageTemplate);

		Assert.assertEquals(
			contentPageTemplate.getKey(), postPageTemplate.getKey());

		_postSiteSiteByExternalReferenceCodePageTemplate(
			_getContentPageTemplate(testGroup),
			testGroup.getExternalReferenceCode());

		_postSiteSiteByExternalReferenceCodePageTemplate(
			_getWidgetPageTemplate(testGroup),
			testGroup.getExternalReferenceCode());

		_testPostSiteSiteByExternalReferenceCodePageTemplateWithPageSpecifications();

		WidgetPageTemplate widgetPageTemplate = _getWidgetPageTemplate(
			testGroup);

		widgetPageTemplate.setPageTemplateSet(() -> null);

		_assertProblemException(
			"BAD_REQUEST",
			() -> _postSiteSiteByExternalReferenceCodePageTemplate(
				widgetPageTemplate, testGroup.getExternalReferenceCode()));

		widgetPageTemplate.setPageTemplateSet(
			() -> new PageTemplateSet() {
				{
					setExternalReferenceCode(() -> StringPool.BLANK);
				}
			});

		_assertProblemException(
			"BAD_REQUEST",
			() -> _postSiteSiteByExternalReferenceCodePageTemplate(
				widgetPageTemplate, testGroup.getExternalReferenceCode()));

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() -> _postSiteSiteByExternalReferenceCodePageTemplate(
				_getPageTemplate(testGroup),
				testGroup.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, companyGroupWidgetPageTemplate) -> {
				_postSiteSiteByExternalReferenceCodePageTemplate(
					companyGroupWidgetPageTemplate,
					group.getExternalReferenceCode());

				_assertProblemException(
					"BAD_REQUEST",
					() ->
						pageTemplateResource.
							postSiteSiteByExternalReferenceCodePageTemplate(
								group.getExternalReferenceCode(),
								_getContentPageTemplate(group)));
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() ->
					pageTemplateResource.
						postSiteSiteByExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							_getPageTemplate(group))));
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageTemplatePageSpecification()
		throws Exception {

		PageTemplateResource pageTemplateResource = _getPageTemplateResource();

		PageTemplate pageTemplate =
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(),
					_getContentPageTemplate(testGroup));

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		PageSpecificationsTestUtil.
			testPostSiteSiteByExternalReferenceCodePageSpecification(
				_layoutLocalService.getLayout(
					layoutPageTemplateEntry.getPlid()),
				pageTemplate.getPageSpecifications(), serviceContext,
				contentPageSpecification ->
					pageTemplateResource.
						postSiteSiteByExternalReferenceCodePageTemplatePageSpecification(
							testGroup.getExternalReferenceCode(),
							pageTemplate.getExternalReferenceCode(),
							contentPageSpecification));

		PageTemplate widgetPageTemplate =
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(),
					_getWidgetPageTemplate(testGroup));

		_assertPostSiteSiteByExternalReferenceCodePageTemplatePageSpecificationProblemException(
			widgetPageTemplate.getExternalReferenceCode());

		layoutPageTemplateEntry =
			LayoutPageTemplateEntryTestUtil.
				getDisplayPageLayoutPageTemplateEntry(serviceContext);

		_assertPostSiteSiteByExternalReferenceCodePageTemplatePageSpecificationProblemException(
			layoutPageTemplateEntry.getExternalReferenceCode());

		layoutPageTemplateEntry =
			LayoutPageTemplateEntryTestUtil.getMasterLayoutPageTemplateEntry(
				serviceContext, WorkflowConstants.STATUS_DRAFT);

		_assertPostSiteSiteByExternalReferenceCodePageTemplatePageSpecificationProblemException(
			layoutPageTemplateEntry.getExternalReferenceCode());
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate()
		throws Exception {

		_testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPutSitePageTemplatePermissionsPage() throws Exception {
		super.testPutSitePageTemplatePermissionsPage();
	}

	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		ContentPageTemplate contentPageTemplate = _getContentPageTemplate(
			testGroup);

		_testPutSiteSiteByExternalReferenceCodePageTemplate(
			contentPageTemplate, testGroup.getExternalReferenceCode());

		_testPutSiteSiteByExternalReferenceCodePageTemplate(
			_getUpdatedContentPageTemplate(
				testGroup, contentPageTemplate.getExternalReferenceCode()),
			testGroup.getExternalReferenceCode());

		WidgetPageTemplate widgetPageTemplate = _getWidgetPageTemplate(
			testGroup);

		_testPutSiteSiteByExternalReferenceCodePageTemplate(
			widgetPageTemplate, testGroup.getExternalReferenceCode());

		_testPutSiteSiteByExternalReferenceCodePageTemplate(
			_getUpdatedWidgetPageTemplate(
				testGroup, widgetPageTemplate.getExternalReferenceCode()),
			testGroup.getExternalReferenceCode());

		WidgetPageTemplate expectedWidgetPageTemplate =
			_getUpdatedWidgetPageTemplate(
				testGroup, widgetPageTemplate.getExternalReferenceCode());

		WidgetPageTemplate putWidgetPageTemplate = new WidgetPageTemplate();

		BeanTestUtil.copyProperties(
			expectedWidgetPageTemplate, putWidgetPageTemplate);

		expectedWidgetPageTemplate.setPageTemplateSettings(
			() -> new WidgetPageTemplateSettings() {
				{
					setLayoutTemplateId(PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);
					setNavigationSettings(
						new NavigationSettings() {
							{
								setTargetType(TargetType.SPECIFIC_FRAME);
							}
						});
					setType(Type.WIDGET_PAGE_TEMPLATE_SETTINGS);
				}
			});

		putWidgetPageTemplate.setPageTemplateSettings(() -> null);

		assertEquals(
			expectedWidgetPageTemplate,
			pageTemplateResource.putSiteSiteByExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				putWidgetPageTemplate.getExternalReferenceCode(),
				putWidgetPageTemplate));

		putWidgetPageTemplate.setPageTemplateSet(() -> null);

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateResource.
					putSiteSiteByExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						putWidgetPageTemplate.getExternalReferenceCode(),
						putWidgetPageTemplate));

		putWidgetPageTemplate.setPageTemplateSet(
			() -> new PageTemplateSet() {
				{
					setExternalReferenceCode(() -> StringPool.BLANK);
				}
			});

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateResource.
					putSiteSiteByExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						putWidgetPageTemplate.getExternalReferenceCode(),
						putWidgetPageTemplate));

		_testPutSiteSiteByExternalReferenceCodePageTemplateWithPageSpecifications();

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() -> _testPutSiteSiteByExternalReferenceCodePageTemplate(
				contentPageTemplate, testGroup.getExternalReferenceCode()));

		_assertProblemException(
			"BAD_REQUEST",
			() -> _testPutSiteSiteByExternalReferenceCodePageTemplate(
				widgetPageTemplate, testGroup.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, curWidgetPageTemplate) -> {
				_testPutSiteSiteByExternalReferenceCodePageTemplate(
					curWidgetPageTemplate, group.getExternalReferenceCode());

				_testPutSiteSiteByExternalReferenceCodePageTemplate(
					_getUpdatedWidgetPageTemplate(
						group,
						curWidgetPageTemplate.getExternalReferenceCode()),
					group.getExternalReferenceCode());

				_assertProblemException(
					"BAD_REQUEST",
					() -> {
						ContentPageTemplate curContentPageTemplate =
							_getContentPageTemplate(group);

						pageTemplateResource.
							putSiteSiteByExternalReferenceCodePageTemplate(
								group.getExternalReferenceCode(),
								curContentPageTemplate.
									getExternalReferenceCode(),
								curContentPageTemplate);
					});
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() -> {
					PageTemplate pageTemplate = _getPageTemplate(group);

					pageTemplateResource.
						putSiteSiteByExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							pageTemplate.getExternalReferenceCode(),
							pageTemplate);
				}));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"active", "description_i18n", "externalReferenceCode", "keywords",
			"name", "name_i18n", "pageTemplateSet", "pageTemplateSettings",
			"taxonomyCategoryItemExternalReferences"
		};
	}

	@Override
	protected PageTemplate randomIrrelevantPageTemplate() throws Exception {
		return _getPageTemplate(irrelevantGroup);
	}

	@Override
	protected PageTemplate randomPageTemplate() throws Exception {
		return _getPageTemplate(testGroup);
	}

	@Ignore
	@Override
	@Test
	protected PageTemplate
			testGetSitePageTemplatePermissionsPage_addPageTemplate()
		throws Exception {

		return super.testGetSitePageTemplatePermissionsPage_addPageTemplate();
	}

	@Override
	protected PageTemplate
			testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage_addPageTemplate(
				String siteExternalReferenceCode,
				String pageTemplateSetExternalReferenceCode,
				PageTemplate pageTemplate)
		throws Exception {

		return pageTemplateResource.
			postSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate(
				siteExternalReferenceCode, pageTemplateSetExternalReferenceCode,
				pageTemplate);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage_getIrrelevantPageTemplateSetExternalReferenceCode()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(irrelevantGroup);

		return layoutPageTemplateCollection.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage_getPageTemplateSetExternalReferenceCode()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(testGroup);

		return layoutPageTemplateCollection.getExternalReferenceCode();
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
	protected PageTemplate
			testPostSiteSiteByExternalReferenceCodePageTemplate_addPageTemplate(
				PageTemplate pageTemplate)
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodePageTemplatesPage_addPageTemplate(
			testGroup.getExternalReferenceCode(), pageTemplate);
	}

	@Override
	protected PageTemplate
			testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate_addPageTemplate(
				PageTemplate pageTemplate)
		throws Exception {

		PageTemplateSet pageTemplateSet = pageTemplate.getPageTemplateSet();

		return pageTemplateResource.
			postSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate(
				testGroup.getExternalReferenceCode(),
				pageTemplateSet.getExternalReferenceCode(), pageTemplate);
	}

	@Ignore
	@Override
	@Test
	protected PageTemplate
			testPutSitePageTemplatePermissionsPage_addPageTemplate()
		throws Exception {

		return super.testPutSitePageTemplatePermissionsPage_addPageTemplate();
	}

	private void _assertPageSpecifications(
			ContentPageSpecification draftContentPageSpecification,
			ContentPageSpecification publishedContentPageSpecification,
			PageTemplate pageTemplate)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId());

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		PageSpecification.Status status = PageSpecification.Status.APPROVED;

		if (layout.isPublished()) {
			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED,
				layoutPageTemplateEntry.getStatus());
		}
		else {
			Assert.assertEquals(
				WorkflowConstants.STATUS_DRAFT,
				layoutPageTemplateEntry.getStatus());

			status = PageSpecification.Status.DRAFT;
		}

		PageSpecificationsTestUtil.assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			pageTemplate.getPageSpecifications(), layout, status);
	}

	private void
			_assertPostSiteSiteByExternalReferenceCodePageTemplatePageSpecificationProblemException(
				String pageTemplateExternalReferenceCode)
		throws Exception {

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateResource.
					postSiteSiteByExternalReferenceCodePageTemplatePageSpecification(
						testGroup.getExternalReferenceCode(),
						pageTemplateExternalReferenceCode,
						new ContentPageSpecification() {
							{
								setType(() -> Type.CONTENT_PAGE_SPECIFICATION);
							}
						}));
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

	private void _assertWidgetPageSpecifications(
		PageSpecification[] pageSpecifications,
		WidgetPageSpecification widgetPageSpecification) {

		Assert.assertEquals(
			Arrays.toString(pageSpecifications), 1, pageSpecifications.length);

		PageSpecificationsTestUtil.assertWidgetPageSpecification(
			widgetPageSpecification,
			(WidgetPageSpecification)pageSpecifications[0]);
	}

	private void _enableLocalStaging() throws Exception {
		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), testGroup, true, false,
			ServiceContextTestUtil.getServiceContext(
				testGroup, TestPropsValues.getUserId()));
	}

	private ContentPageTemplate _getContentPageTemplate(Group group)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		return new ContentPageTemplate() {
			{
				creatorExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				keywords = AssetTestUtil.randomKeywords(serviceContext);
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				pageTemplateSet = _getPageTemplateSet(group);
				pageTemplateSettings = new ContentPageTemplateSettings() {
					{
						setType(Type.CONTENT_PAGE_TEMPLATE_SETTINGS);
					}
				};
				taxonomyCategoryItemExternalReferences =
					AssetTestUtil.randomTaxonomyCategoryItemExternalReferences(
						testCompany.getGroupId(), serviceContext);
				type = Type.CONTENT_PAGE_TEMPLATE;
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private LayoutPageTemplateCollection _getLayoutPageTemplateCollection(
			Group group)
		throws Exception {

		return _layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateCollectionTypeConstants.BASIC,
				ServiceContextTestUtil.getServiceContext(
					group, TestPropsValues.getUserId()));
	}

	private PageTemplate _getPageTemplate(Group group) throws Exception {
		List<UnsafeSupplier<PageTemplate, Exception>> unsafeSuppliers =
			Arrays.asList(
				() -> _getContentPageTemplate(group),
				() -> _getWidgetPageTemplate(group));

		UnsafeSupplier<PageTemplate, Exception> unsafeSupplier =
			unsafeSuppliers.get(
				RandomTestUtil.randomInt(0, unsafeSuppliers.size() - 1));

		return unsafeSupplier.get();
	}

	private PageTemplateResource _getPageTemplateResource() throws Exception {
		User user = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		return PageTemplateResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "pageSpecifications"
		).build();
	}

	private PageTemplateSet _getPageTemplateSet(Group group) throws Exception {
		if (group.isCompany() || group.isDepot()) {
			return null;
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(group);

		return new PageTemplateSet() {
			{
				setDateCreated(layoutPageTemplateCollection::getCreateDate);
				setDateModified(layoutPageTemplateCollection::getModifiedDate);
				setDescription(layoutPageTemplateCollection::getDescription);
				setExternalReferenceCode(
					layoutPageTemplateCollection::getExternalReferenceCode);
				setKey(
					layoutPageTemplateCollection::
						getLayoutPageTemplateCollectionKey);
				setName(layoutPageTemplateCollection::getName);
			}
		};
	}

	private long _getSiteSiteByExternalReferenceCodePageTemplatesPageTotalCount(
			String siteExternalReferenceCode)
		throws Exception {

		Page<PageTemplate> page =
			pageTemplateResource.
				getSiteSiteByExternalReferenceCodePageTemplatesPage(
					siteExternalReferenceCode, null, null, null, null, null);

		return page.getTotalCount();
	}

	private ContentPageTemplate _getUpdatedContentPageTemplate(
			Group group, String pageTemplateExternalReferenceCode)
		throws Exception {

		ContentPageTemplate contentPageTemplate =
			(ContentPageTemplate)
				pageTemplateResource.
					getSiteSiteByExternalReferenceCodePageTemplate(
						group.getExternalReferenceCode(),
						pageTemplateExternalReferenceCode);

		contentPageTemplate.setName(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
		contentPageTemplate.setPageTemplateSet(_getPageTemplateSet(group));

		return contentPageTemplate;
	}

	private WidgetPageTemplate _getUpdatedWidgetPageTemplate(
			Group group, String pageTemplateExternalReferenceCode)
		throws Exception {

		WidgetPageTemplate widgetPageTemplate =
			(WidgetPageTemplate)
				pageTemplateResource.
					getSiteSiteByExternalReferenceCodePageTemplate(
						group.getExternalReferenceCode(),
						pageTemplateExternalReferenceCode);

		widgetPageTemplate.setActive(RandomTestUtil.randomBoolean());
		widgetPageTemplate.setDescription_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
				RandomTestUtil.randomString()
			).build());

		String name = StringUtil.toLowerCase(RandomTestUtil.randomString());

		widgetPageTemplate.setName(name);
		widgetPageTemplate.setName_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()), name
			).build());

		widgetPageTemplate.setPageTemplateSet(_getPageTemplateSet(group));
		widgetPageTemplate.setPageTemplateSettings(
			_getWidgetPageTemplateSettings());

		return widgetPageTemplate;
	}

	private WidgetPageTemplate _getWidgetPageTemplate(Group group)
		throws Exception {

		String randomName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		return new WidgetPageTemplate() {
			{
				active = RandomTestUtil.randomBoolean();
				creatorExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description_i18n = HashMapBuilder.put(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
					RandomTestUtil.randomString()
				).build();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				hiddenFromNavigation = RandomTestUtil.randomBoolean();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				keywords = AssetTestUtil.randomKeywords(serviceContext);
				name = randomName;
				name_i18n = HashMapBuilder.put(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
					randomName
				).build();
				pageTemplateSet = _getPageTemplateSet(group);
				pageTemplateSettings = _getWidgetPageTemplateSettings();
				taxonomyCategoryItemExternalReferences =
					AssetTestUtil.randomTaxonomyCategoryItemExternalReferences(
						testCompany.getGroupId(), serviceContext);
				type = PageTemplate.Type.WIDGET_PAGE_TEMPLATE;
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private WidgetPageTemplateSettings _getWidgetPageTemplateSettings() {
		return new WidgetPageTemplateSettings() {
			{
				setLayoutTemplateId(
					() -> {
						if (RandomTestUtil.randomBoolean()) {
							return "1_column";
						}

						return "2_columns_ii";
					});
				setNavigationSettings(
					new NavigationSettings() {
						{
							if (RandomTestUtil.randomBoolean()) {
								setTarget("_blank");
								setTargetType(() -> TargetType.NEW_TAB);
							}
							else {
								setTarget(RandomTestUtil::randomString);
								setTargetType(() -> TargetType.SPECIFIC_FRAME);
							}
						}
					});
				setType(Type.WIDGET_PAGE_TEMPLATE_SETTINGS);
			}
		};
	}

	private void _postSiteSiteByExternalReferenceCodePageTemplate(
			PageTemplate pageTemplate, String siteExternalReferenceCode)
		throws Exception {

		assertEquals(
			pageTemplate,
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					siteExternalReferenceCode, pageTemplate));
	}

	private void
			_postSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate(
				PageTemplate pageTemplate, String siteExternalReferenceCode)
		throws Exception {

		PageTemplateSet pageTemplateSet = pageTemplate.getPageTemplateSet();

		assertEquals(
			pageTemplate,
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate(
					siteExternalReferenceCode,
					pageTemplateSet.getExternalReferenceCode(), pageTemplate));
	}

	private void _testDeleteSiteSiteByExternalReferenceCodePageTemplate(
			Group group, String pageTemplateExternalReferenceCode)
		throws Exception {

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplateExternalReferenceCode, group.getGroupId()));

		pageTemplateResource.deleteSiteSiteByExternalReferenceCodePageTemplate(
			group.getExternalReferenceCode(),
			pageTemplateExternalReferenceCode);

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplateExternalReferenceCode, group.getGroupId()));

		_assertProblemException(
			"NOT_FOUND",
			() ->
				pageTemplateResource.
					deleteSiteSiteByExternalReferenceCodePageTemplate(
						group.getExternalReferenceCode(),
						pageTemplateExternalReferenceCode));
	}

	private void _testGetSiteSiteByExternalReferenceCodePageTemplate(
			PageTemplate pageTemplate)
		throws Exception {

		PageTemplate getPageTemplate =
			pageTemplateResource.getSiteSiteByExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				pageTemplate.getExternalReferenceCode());

		assertEquals(pageTemplate, getPageTemplate);
		assertValid(getPageTemplate);
	}

	private void
			_testGetSiteSiteByExternalReferenceCodePageTemplateWithNestedFields(
				PageTemplate pageTemplate)
		throws Exception {

		PageTemplateResource pageTemplateResource = _getPageTemplateResource();

		PageTemplate postPageTemplate =
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(), pageTemplate);

		PageTemplate getPageTemplate =
			pageTemplateResource.getSiteSiteByExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				postPageTemplate.getExternalReferenceCode());

		assertEquals(postPageTemplate, getPageTemplate);
		assertValid(getPageTemplate);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					getPageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId());

		PageSpecificationsTestUtil.assertPageSpecifications(
			_layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid()),
			getPageTemplate.getPageSpecifications());
	}

	private void _testPatchSiteSiteByExternalReferenceCodePageTemplate(
			PageTemplate pageTemplate, String siteExternalReferenceCode)
		throws Exception {

		assertEquals(
			pageTemplate,
			pageTemplateResource.
				patchSiteSiteByExternalReferenceCodePageTemplate(
					siteExternalReferenceCode,
					pageTemplate.getExternalReferenceCode(), pageTemplate));
	}

	private void
			_testPatchSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
				PageSpecification.Status newDraftLayoutStatus,
				PageSpecification.Status newPublishedLayoutStatus,
				PageSpecification.Status oldDraftLayoutStatus,
				PageSpecification.Status oldPublishedLayoutStatus)
		throws Exception {

		PageTemplateResource pageTemplateResource = _getPageTemplateResource();

		PageTemplate pageTemplate = _getContentPageTemplate(testGroup);

		ContentPageSpecification draftContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				null, oldDraftLayoutStatus);

		ContentPageSpecification publishedContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				draftContentPageSpecification.getExternalReferenceCode(),
				oldPublishedLayoutStatus);

		pageTemplate.setPageSpecifications(
			() -> new PageSpecification[] {
				publishedContentPageSpecification, draftContentPageSpecification
			});

		PageTemplate postPageTemplate =
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(), pageTemplate);

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			postPageTemplate);

		draftContentPageSpecification.setStatus(newDraftLayoutStatus);

		publishedContentPageSpecification.setStatus(newPublishedLayoutStatus);

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			pageTemplateResource.
				patchSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(),
					postPageTemplate.getExternalReferenceCode(),
					new ContentPageTemplate() {
						{
							setPageSpecifications(
								() -> new PageSpecification[] {
									publishedContentPageSpecification,
									draftContentPageSpecification
								});
							setType(Type.CONTENT_PAGE_TEMPLATE);
						}
					}));
	}

	private void _testPatchSiteSiteByExternalReferenceCodePageTemplateWidgetPageTemplateWithPageSpecifications()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryTestUtil.
				getWidgetPageLayoutPageTemplateEntry(serviceContext);

		PageTemplateResource pageTemplateResource = _getPageTemplateResource();

		PageTemplate pageTemplate =
			pageTemplateResource.getSiteSiteByExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				layoutPageTemplateEntry.getExternalReferenceCode());

		PageSpecification[] pageSpecifications =
			pageTemplate.getPageSpecifications();

		Assert.assertEquals(
			Arrays.toString(pageSpecifications), 1, pageSpecifications.length);

		WidgetPageSpecification widgetPageSpecification =
			(WidgetPageSpecification)pageSpecifications[0];

		SettingsTestUtil.modifySettings(
			_cetManager, serviceContext, widgetPageSpecification.getSettings());

		pageTemplate =
			pageTemplateResource.
				patchSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(),
					pageTemplate.getExternalReferenceCode(),
					new WidgetPageTemplate() {
						{
							setPageSpecifications(
								() -> new PageSpecification[] {
									PageSpecificationsTestUtil.
										getWidgetPageSpecification(
											null, null,
											widgetPageSpecification.
												getSettings(),
											PageSpecification.Status.APPROVED)
								});
							setType(Type.WIDGET_PAGE_TEMPLATE);
						}
					});

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId());

		SettingsTestUtil.assertPageSpecificationSetting(
			_layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid()),
			widgetPageSpecification.getSettings());

		_assertWidgetPageSpecifications(
			pageTemplate.getPageSpecifications(), widgetPageSpecification);

		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getExternalReferenceCode(),
			testGroup.getGroupId());
	}

	private void _testPatchSiteSiteByExternalReferenceCodePageTemplateWithPageSpecifications()
		throws Exception {

		_testPatchSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT,
			PageSpecification.Status.APPROVED);
		_testPatchSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT,
			PageSpecification.Status.DRAFT);
		_testPatchSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED);
		_testPatchSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.DRAFT,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT);
		_testPatchSiteSiteByExternalReferenceCodePageTemplateWidgetPageTemplateWithPageSpecifications();
	}

	private void
			_testPostSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
				PageSpecification.Status draftLayoutStatus,
				PageSpecification.Status publishedLayoutStatus)
		throws Exception {

		PageTemplateResource pageTemplateResource = _getPageTemplateResource();

		PageTemplate pageTemplate = _getContentPageTemplate(testGroup);

		ContentPageSpecification draftContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				null, draftLayoutStatus);

		ContentPageSpecification publishedContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				draftContentPageSpecification.getExternalReferenceCode(),
				publishedLayoutStatus);

		pageTemplate.setPageSpecifications(
			() -> new PageSpecification[] {
				publishedContentPageSpecification, draftContentPageSpecification
			});

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(), pageTemplate));
	}

	private void _testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate()
		throws Exception {

		PageTemplate randomPageTemplate = randomPageTemplate();

		PageTemplate postPageTemplate =
			testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate_addPageTemplate(
				randomPageTemplate);

		assertEquals(randomPageTemplate, postPageTemplate);
		assertValid(postPageTemplate);

		_postSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate(
			_getContentPageTemplate(testGroup),
			testGroup.getExternalReferenceCode());

		_postSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate(
			_getWidgetPageTemplate(testGroup),
			testGroup.getExternalReferenceCode());
	}

	private void _testPostSiteSiteByExternalReferenceCodePageTemplateWidgetPageTemplateWithPageSpecifications()
		throws Exception {

		PageTemplateResource pageTemplateResource = _getPageTemplateResource();

		PageTemplate pageTemplate = _getWidgetPageTemplate(testGroup);

		pageTemplate.setPageSpecifications(
			() -> new PageSpecification[] {
				PageSpecificationsTestUtil.getContentPageSpecification(
					null, PageSpecification.Status.APPROVED)
			});

		_assertProblemException(
			"BAD_REQUEST",
			() -> _postSiteSiteByExternalReferenceCodePageTemplate(
				pageTemplate, testGroup.getExternalReferenceCode()));

		WidgetPageSpecification widgetPageSpecification =
			PageSpecificationsTestUtil.getWidgetPageSpecification(
				null, pageTemplate.getExternalReferenceCode(), null,
				PageSpecification.Status.APPROVED);

		pageTemplate.setPageSpecifications(
			() -> new PageSpecification[] {
				widgetPageSpecification, widgetPageSpecification
			});

		_assertProblemException(
			"BAD_REQUEST",
			() -> _postSiteSiteByExternalReferenceCodePageTemplate(
				pageTemplate, testGroup.getExternalReferenceCode()));

		widgetPageSpecification.setStatus(PageSpecification.Status.DRAFT);

		pageTemplate.setPageSpecifications(
			() -> new PageSpecification[] {widgetPageSpecification});

		_assertProblemException(
			"BAD_REQUEST",
			() -> _postSiteSiteByExternalReferenceCodePageTemplate(
				pageTemplate, testGroup.getExternalReferenceCode()));

		widgetPageSpecification.setExternalReferenceCode(
			RandomTestUtil::randomString);
		widgetPageSpecification.setStatus(PageSpecification.Status.APPROVED);

		_assertProblemException(
			"BAD_REQUEST",
			() -> _postSiteSiteByExternalReferenceCodePageTemplate(
				pageTemplate, testGroup.getExternalReferenceCode()));

		widgetPageSpecification.setExternalReferenceCode(
			pageTemplate.getExternalReferenceCode());

		widgetPageSpecification.setSettings(
			SettingsTestUtil.getSettings(
				_cetManager,
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId(), TestPropsValues.getUserId())));

		PageTemplate postPageTemplate =
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(), pageTemplate);

		_assertWidgetPageSpecifications(
			postPageTemplate.getPageSpecifications(), widgetPageSpecification);

		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			postPageTemplate.getExternalReferenceCode(),
			testGroup.getGroupId());
	}

	private void _testPostSiteSiteByExternalReferenceCodePageTemplateWithPageSpecifications()
		throws Exception {

		_testPostSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED);
		_testPostSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT);
		_testPostSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.APPROVED);
		_testPostSiteSiteByExternalReferenceCodePageTemplateContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.DRAFT);
		_testPostSiteSiteByExternalReferenceCodePageTemplateWidgetPageTemplateWithPageSpecifications();
	}

	private void
			_testPutSiteSiteByExternalReferenceCodeContentPageTemplateWithPageSpecifications(
				PageSpecification.Status newDraftLayoutStatus,
				PageSpecification.Status newPublishedLayoutStatus,
				PageSpecification.Status oldDraftLayoutStatus,
				PageSpecification.Status oldPublishedLayoutStatus)
		throws Exception {

		PageTemplateResource pageTemplateResource = _getPageTemplateResource();

		PageTemplate pageTemplate = _getContentPageTemplate(testGroup);

		ContentPageSpecification draftContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				null, oldDraftLayoutStatus);

		ContentPageSpecification publishedContentPageSpecification =
			PageSpecificationsTestUtil.getContentPageSpecification(
				draftContentPageSpecification.getExternalReferenceCode(),
				oldPublishedLayoutStatus);

		pageTemplate.setPageSpecifications(
			() -> new PageSpecification[] {
				publishedContentPageSpecification, draftContentPageSpecification
			});

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			pageTemplateResource.putSiteSiteByExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				pageTemplate.getExternalReferenceCode(), pageTemplate));

		draftContentPageSpecification.setStatus(newDraftLayoutStatus);

		publishedContentPageSpecification.setStatus(newPublishedLayoutStatus);

		_assertPageSpecifications(
			draftContentPageSpecification, publishedContentPageSpecification,
			pageTemplateResource.putSiteSiteByExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				pageTemplate.getExternalReferenceCode(), pageTemplate));
	}

	private void _testPutSiteSiteByExternalReferenceCodePageTemplate(
			PageTemplate pageTemplate, String siteExternalReferenceCode)
		throws Exception {

		assertEquals(
			pageTemplate,
			pageTemplateResource.putSiteSiteByExternalReferenceCodePageTemplate(
				siteExternalReferenceCode,
				pageTemplate.getExternalReferenceCode(), pageTemplate));
	}

	private void _testPutSiteSiteByExternalReferenceCodePageTemplateWidgetPageTemplateWithPageSpecifications()
		throws Exception {

		PageTemplateResource pageTemplateResource = _getPageTemplateResource();

		PageTemplate pageTemplate = _getWidgetPageTemplate(testGroup);

		WidgetPageSpecification widgetPageSpecification =
			PageSpecificationsTestUtil.getWidgetPageSpecification(
				null, pageTemplate.getExternalReferenceCode(),
				SettingsTestUtil.getSettings(
					_cetManager,
					ServiceContextTestUtil.getServiceContext(
						testGroup.getGroupId(), TestPropsValues.getUserId())),
				PageSpecification.Status.APPROVED);

		pageTemplate.setPageSpecifications(
			() -> new PageSpecification[] {widgetPageSpecification});

		PageTemplate putPageTemplate =
			pageTemplateResource.putSiteSiteByExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				pageTemplate.getExternalReferenceCode(), pageTemplate);

		_assertWidgetPageSpecifications(
			putPageTemplate.getPageSpecifications(), widgetPageSpecification);

		widgetPageSpecification.setSettings(
			SettingsTestUtil.getSettings(
				_cetManager,
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId(), TestPropsValues.getUserId())));

		putPageTemplate =
			pageTemplateResource.putSiteSiteByExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				pageTemplate.getExternalReferenceCode(), pageTemplate);

		_assertWidgetPageSpecifications(
			putPageTemplate.getPageSpecifications(), widgetPageSpecification);

		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			putPageTemplate.getExternalReferenceCode(), testGroup.getGroupId());
	}

	private void _testPutSiteSiteByExternalReferenceCodePageTemplateWithPageSpecifications()
		throws Exception {

		_testPutSiteSiteByExternalReferenceCodeContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT,
			PageSpecification.Status.APPROVED);
		_testPutSiteSiteByExternalReferenceCodeContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT,
			PageSpecification.Status.DRAFT);
		_testPutSiteSiteByExternalReferenceCodeContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED,
			PageSpecification.Status.APPROVED);
		_testPutSiteSiteByExternalReferenceCodeContentPageTemplateWithPageSpecifications(
			PageSpecification.Status.DRAFT, PageSpecification.Status.DRAFT,
			PageSpecification.Status.APPROVED, PageSpecification.Status.DRAFT);
		_testPutSiteSiteByExternalReferenceCodePageTemplateWidgetPageTemplateWithPageSpecifications();
	}

	private void _withCompanyGroupWidgetPageTemplate(
			UnsafeBiConsumer<Group, WidgetPageTemplate, Exception>
				unsafeBiConsumer)
		throws Exception {

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group group = company.getGroup();

		WidgetPageTemplate widgetPageTemplate = _getWidgetPageTemplate(group);

		try {
			unsafeBiConsumer.accept(group, widgetPageTemplate);
		}
		finally {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntryByExternalReferenceCode(
						widgetPageTemplate.getExternalReferenceCode(),
						group.getGroupId());

			if (layoutPageTemplateEntry != null) {
				_layoutPageTemplateEntryLocalService.
					deleteLayoutPageTemplateEntry(layoutPageTemplateEntry);
			}
		}
	}

	private void _withDepotEntry(
			UnsafeConsumer<Group, Exception> unsafeConsumer)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			new HashMap<>(), DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());

		try {
			unsafeConsumer.accept(depotEntry.getGroup());
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);
		}
	}

	@Inject
	private CETManager _cetManager;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}