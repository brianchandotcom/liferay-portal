/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.Settings;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSpecification;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

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
public class PageSpecificationResourceTest
	extends BasePageSpecificationResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Ignore
	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodePageSpecification()
		throws Exception {

		super.testDeleteSiteSiteByExternalReferenceCodePageSpecification();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatePageSpecificationsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatePageSpecificationsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeMasterPagePageSpecificationsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeMasterPagePageSpecificationsPage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageSpecification()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		_testGetSiteSiteByExternalReferenceCodePageSpecification(
			_addLayout(LayoutConstants.TYPE_PORTLET, serviceContext));

		_testGetSiteSiteByExternalReferenceCodePageSpecificationWithLayoutWithDraftLayout(
			_addLayout(LayoutConstants.TYPE_CONTENT, serviceContext),
			serviceContext);

		_testGetSiteSiteByExternalReferenceCodePageSpecificationWithLayoutWithDraftLayout(
			_getBasicLayoutPageTemplateEntryLayout(serviceContext),
			serviceContext);
		_testGetSiteSiteByExternalReferenceCodePageSpecificationWithLayoutWithDraftLayout(
			_getDisplayPageLayoutPageTemplateEntryLayout(serviceContext),
			serviceContext);
		_testGetSiteSiteByExternalReferenceCodePageSpecificationWithLayoutWithDraftLayout(
			_getLayoutUtilityPageEntryLayout(serviceContext), serviceContext);
		_testGetSiteSiteByExternalReferenceCodePageSpecificationWithLayoutWithDraftLayout(
			_getMasterLayoutPageTemplateEntryLayout(serviceContext),
			serviceContext);
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatePageSpecificationsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatePageSpecificationsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePagePageSpecificationsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeSitePagePageSpecificationsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPagePageSpecificationsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeUtilityPagePageSpecificationsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodePageSpecification()
		throws Exception {

		super.testPatchSiteSiteByExternalReferenceCodePageSpecification();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageSpecificationPublish()
		throws Exception {

		super.testPostSiteSiteByExternalReferenceCodePageSpecificationPublish();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageSpecification()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodePageSpecification();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "settings", "status", "type"
		};
	}

	private Layout _addLayout(String type, ServiceContext serviceContext)
		throws Exception {

		return _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), testGroup.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 0,
			RandomTestUtil.randomLocaleStringMap(), Collections.emptyMap(),
			Collections.emptyMap(), Collections.emptyMap(),
			Collections.emptyMap(), type, _getTypeSettings(), false, false,
			Collections.emptyMap(), _getMasterLayoutPlid(serviceContext),
			serviceContext);
	}

	private void _assertContentPageSpecification(
			ContentPageSpecification contentPageSpecification, Layout layout)
		throws Exception {

		Assert.assertEquals(
			PageSpecification.Type.CONTENT_PAGE_SPECIFICATION,
			contentPageSpecification.getType());

		PageExperience[] pageExperiences =
			contentPageSpecification.getPageExperiences();

		Assert.assertEquals(
			Arrays.toString(pageExperiences),
			_segmentsExperienceService.getSegmentsExperiencesCount(
				layout.getGroupId(), layout.getPlid(), true),
			pageExperiences.length);
	}

	private void _assertPageSpecificationSetting(
			Layout layout, Settings settings)
		throws Exception {

		if (Validator.isNull(layout.getColorSchemeId()) ||
			Validator.isNull(layout.getThemeId())) {

			Assert.assertTrue(Validator.isNull(settings.getColorSchemeName()));
		}
		else {
			ColorScheme colorScheme = _themeLocalService.getColorScheme(
				layout.getCompanyId(), layout.getThemeId(),
				layout.getColorSchemeId());

			Assert.assertEquals(
				colorScheme.getName(), settings.getColorSchemeName());
		}

		if (Validator.isNull(layout.getCss())) {
			Assert.assertTrue(Validator.isNull(settings.getCss()));
		}
		else {
			Assert.assertEquals(layout.getCss(), settings.getCss());
		}

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		Assert.assertEquals(
			unicodeProperties.getProperty("javascript", null),
			settings.getJavascript());

		ItemExternalReference masterPageItemExternalReference =
			settings.getMasterPageItemExternalReference();

		if (layout.getMasterLayoutPlid() == 0) {
			Assert.assertNull(masterPageItemExternalReference);
		}
		else {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntryByPlid(
						layout.getMasterLayoutPlid());

			Assert.assertEquals(
				layoutPageTemplateEntry.getExternalReferenceCode(),
				masterPageItemExternalReference.getExternalReferenceCode());
		}

		ItemExternalReference styleBookItemExternalReference =
			settings.getStyleBookItemExternalReference();

		if (layout.getStyleBookEntryId() == 0) {
			Assert.assertNull(styleBookItemExternalReference);
		}
		else {
			StyleBookEntry styleBookEntry =
				_styleBookEntryLocalService.getStyleBookEntry(
					layout.getStyleBookEntryId());

			Assert.assertEquals(
				styleBookEntry.getExternalReferenceCode(),
				styleBookItemExternalReference.getExternalReferenceCode());
		}

		if (Validator.isNull(layout.getThemeId())) {
			Assert.assertTrue(Validator.isNull(settings.getThemeName()));
		}
		else {
			Theme theme = _themeLocalService.getTheme(
				layout.getCompanyId(), layout.getThemeId());

			Assert.assertEquals(theme.getName(), settings.getThemeName());
		}

		UnicodeProperties themeSettingsUnicodeProperties =
			_getThemeSettingsUnicodeProperties(unicodeProperties);

		if (themeSettingsUnicodeProperties.isEmpty()) {
			Assert.assertNull(settings.getThemeSettings());
		}
		else {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				(String)settings.getThemeSettings());

			Assert.assertEquals(
				themeSettingsUnicodeProperties.size(), jsonObject.length());

			for (Map.Entry<String, String> entry :
					themeSettingsUnicodeProperties.entrySet()) {

				Assert.assertEquals(
					entry.getValue(), jsonObject.getString(entry.getKey()));
			}
		}
	}

	private void _assertProblemException(Layout layout) throws Exception {
		try {
			pageSpecificationResource.
				getSiteSiteByExternalReferenceCodePageSpecification(
					testGroup.getExternalReferenceCode(),
					layout.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	private void _assertWidgetPageSpecification(
		WidgetPageSpecification widgetPageSpecification) {

		Assert.assertEquals(
			PageSpecification.Type.WIDGET_PAGE_SPECIFICATION,
			widgetPageSpecification.getType());

		Assert.assertNull(widgetPageSpecification.getWidgetPageSections());
	}

	private Layout _getBasicLayoutPageTemplateEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(),
					serviceContext.getScopeGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.BASIC, 0,
				WorkflowConstants.STATUS_DRAFT, serviceContext);

		return _layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid());
	}

	private Layout _getDisplayPageLayoutPageTemplateEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				_portal.getClassNameId(
					"com.liferay.asset.kernel.model.AssetCategory"),
				0, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_DRAFT, serviceContext);

		return _layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid());
	}

	private Layout _getLayoutUtilityPageEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(), 0, 0, false,
				RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR,
				0, serviceContext);

		return _layoutLocalService.getLayout(layoutUtilityPageEntry.getPlid());
	}

	private Layout _getMasterLayoutPageTemplateEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_DRAFT, serviceContext);

		return _layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid());
	}

	private long _getMasterLayoutPlid(ServiceContext serviceContext)
		throws Exception {

		if (RandomTestUtil.randomBoolean()) {
			return 0;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		return layoutPageTemplateEntry.getPlid();
	}

	private UnicodeProperties _getThemeSettingsUnicodeProperties(
		UnicodeProperties unicodeProperties) {

		UnicodeProperties themeSettingsUnicodeProperties =
			new UnicodeProperties();

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith("lfr-theme:")) {
				themeSettingsUnicodeProperties.setProperty(
					key, entry.getValue());
			}
		}

		return themeSettingsUnicodeProperties;
	}

	private String _getTypeSettings() throws Exception {
		if (RandomTestUtil.randomBoolean()) {
			return StringPool.BLANK;
		}

		return UnicodePropertiesBuilder.put(
			"javascript", RandomTestUtil.randomString()
		).put(
			"lfr-theme:regular:show-maximize-minimize-application-links", true
		).buildString();
	}

	private void _testGetSiteSiteByExternalReferenceCodePageSpecification(
			Layout layout)
		throws Exception {

		PageSpecification pageSpecification =
			pageSpecificationResource.
				getSiteSiteByExternalReferenceCodePageSpecification(
					testGroup.getExternalReferenceCode(),
					layout.getExternalReferenceCode());

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification.getExternalReferenceCode());

		_assertPageSpecificationSetting(
			layout, pageSpecification.getSettings());

		if (layout.isDraftLayout()) {
			Assert.assertEquals(
				PageSpecification.Status.DRAFT, pageSpecification.getStatus());
		}
		else {
			Assert.assertEquals(
				PageSpecification.Status.APPROVED,
				pageSpecification.getStatus());
		}

		if (layout.isTypeAssetDisplay() || layout.isTypeContent()) {
			_assertContentPageSpecification(
				(ContentPageSpecification)pageSpecification, layout);
		}
		else {
			_assertWidgetPageSpecification(
				(WidgetPageSpecification)pageSpecification);
		}
	}

	private void
			_testGetSiteSiteByExternalReferenceCodePageSpecificationWithLayoutWithDraftLayout(
				Layout layout, ServiceContext serviceContext)
		throws Exception {

		_assertProblemException(layout);

		Layout draftLayout = layout.fetchDraftLayout();

		_testGetSiteSiteByExternalReferenceCodePageSpecification(draftLayout);

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		_assertProblemException(draftLayout);

		_testGetSiteSiteByExternalReferenceCodePageSpecification(layout);

		_layoutLocalService.updateStatus(
			TestPropsValues.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		_testGetSiteSiteByExternalReferenceCodePageSpecification(draftLayout);
		_testGetSiteSiteByExternalReferenceCodePageSpecification(layout);

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		_assertProblemException(draftLayout);
		_testGetSiteSiteByExternalReferenceCodePageSpecification(layout);
	}

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceService _segmentsExperienceService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject
	private ThemeLocalService _themeLocalService;

}