/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.test.util.MockInfoServiceRegistrationHolder;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.taglib.servlet.taglib.RenderLayoutStructureTag;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.exception.InfoFormException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class RenderLayoutStructureTagTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testRemovedLayoutTemplateId() throws Exception {
		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, _serviceContext);

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID,
			"removed-template-id");

		layout = LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			typeSettingsUnicodeProperties.toString());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		Assert.assertEquals(
			"removed-template-id", layoutTypePortlet.getLayoutTemplateId());

		RenderLayoutStructureTag renderLayoutStructureTag =
			new RenderLayoutStructureTag();

		renderLayoutStructureTag.setLayoutStructure(
			_getDefaultMasterLayoutStructure());

		renderLayoutStructureTag.doTag(
			_getMockHttpServletRequest(layout), new MockHttpServletResponse());

		Assert.assertEquals(
			PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID,
			layoutTypePortlet.getLayoutTemplateId());

		layout = _layoutLocalService.fetchLayout(layout.getPlid());

		layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		Assert.assertEquals(
			"removed-template-id", layoutTypePortlet.getLayoutTemplateId());
	}

	@Test
	public void testRenderCollectionStyledLayoutStructureItem()
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				_addCollectionStyledLayoutStructureItem(
					assetListEntry, layout, segmentsExperienceId);

		_addFragmentEntryLinks(
			layout, collectionStyledLayoutStructureItem.getItemId(),
			segmentsExperienceId);

		List<AssetEntry> assetEntries = _addAssetEntries(assetListEntry);

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout);
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		RenderLayoutStructureTag renderLayoutStructureTag =
			_getRenderLayoutStructureTag(
				layout, mockHttpServletRequest, mockHttpServletResponse,
				segmentsExperienceId);

		_entityCache.clearCache();
		_multiVMPool.clear();

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);
		}

		String content = mockHttpServletResponse.getContentAsString();

		for (AssetEntry assetEntry : assetEntries) {
			int count = StringUtil.count(
				content,
				assetEntry.getTitle(assetEntry.getDefaultLanguageId()));

			Assert.assertTrue(
				String.valueOf(count), count >= _COUNT_FRAGMENT_ENTRY_LINKS);
		}
	}

	@Test
	public void testRenderCollectionStyledLayoutStructureItemSelectingSegmentsExperienceWithDifferentSegmentsEntry()
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		JournalArticle expectedJournalArticle1 = _addJournalArticle(
			ddmStructure);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			expectedJournalArticle1.getResourcePrimKey());

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {assetEntry1.getEntryId()},
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		JournalArticle expectedJournalArticle2 = _addJournalArticle(
			ddmStructure);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			expectedJournalArticle2.getResourcePrimKey());

		SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName("Test");

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {assetEntry2.getEntryId()},
			segmentsEntry1.getSegmentsEntryId(), _serviceContext);

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel1 =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRel(
					assetListEntry.getAssetListEntryId(),
					SegmentsEntryConstants.ID_DEFAULT);

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel2 =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRel(
					assetListEntry.getAssetListEntryId(),
					segmentsEntry1.getSegmentsEntryId());

		_assetListEntrySegmentsEntryRelLocalService.updateVariationsPriority(
			new long[] {
				assetListEntrySegmentsEntryRel2.
					getAssetListEntrySegmentsEntryRelId(),
				assetListEntrySegmentsEntryRel1.
					getAssetListEntrySegmentsEntryRelId()
			});

		SegmentsEntry segmentsEntry2 = _addSegmentsEntryByFirstName("User");

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), segmentsEntry2.getSegmentsEntryId(),
				layout.getPlid());

		_createLayoutStructure(
			assetListEntry.getAssetListEntryId(), layout,
			segmentsExperience.getSegmentsExperienceId());

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout);

		mockHttpServletRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(segmentsExperience.getSegmentsExperienceId()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		RenderLayoutStructureTag renderLayoutStructureTag =
			_getRenderLayoutStructureTag(
				layout, mockHttpServletRequest, mockHttpServletResponse,
				segmentsExperience.getSegmentsExperienceId());

		renderLayoutStructureTag.doTag(
			mockHttpServletRequest, mockHttpServletResponse);

		List<JournalArticle> actualJournalArticles =
			(List<JournalArticle>)mockHttpServletRequest.getAttribute(
				"liferay-info:info-list-grid:infoListObjects");

		Assert.assertNotNull(actualJournalArticles);
		Assert.assertEquals(
			actualJournalArticles.toString(), 1, actualJournalArticles.size());

		JournalArticle actualJournalArticle1 = actualJournalArticles.get(0);

		Assert.assertEquals(
			expectedJournalArticle1.getArticleId(),
			actualJournalArticle1.getArticleId());
	}

	@Test
	public void testRenderCollectionStyledLayoutStructureItemSelectingSegmentsExperienceWithSameSegmentsEntry()
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		JournalArticle expectedJournalArticle1 = _addJournalArticle(
			ddmStructure);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			expectedJournalArticle1.getResourcePrimKey());

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {assetEntry1.getEntryId()},
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		JournalArticle expectedJournalArticle2 = _addJournalArticle(
			ddmStructure);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			expectedJournalArticle2.getResourcePrimKey());

		SegmentsEntry segmentsEntry = _addSegmentsEntryByFirstName("Test");

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {assetEntry2.getEntryId()},
			segmentsEntry.getSegmentsEntryId(), _serviceContext);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), segmentsEntry.getSegmentsEntryId(),
				layout.getPlid());

		_createLayoutStructure(
			assetListEntry.getAssetListEntryId(), layout,
			segmentsExperience.getSegmentsExperienceId());

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout);

		mockHttpServletRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(segmentsExperience.getSegmentsExperienceId()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		RenderLayoutStructureTag renderLayoutStructureTag =
			_getRenderLayoutStructureTag(
				layout, mockHttpServletRequest, mockHttpServletResponse,
				segmentsExperience.getSegmentsExperienceId());

		renderLayoutStructureTag.doTag(
			mockHttpServletRequest, mockHttpServletResponse);

		List<JournalArticle> actualJournalArticles =
			(List<JournalArticle>)mockHttpServletRequest.getAttribute(
				"liferay-info:info-list-grid:infoListObjects");

		Assert.assertNotNull(actualJournalArticles);
		Assert.assertEquals(
			actualJournalArticles.toString(), 1, actualJournalArticles.size());

		JournalArticle actualJournalArticle = actualJournalArticles.get(0);

		Assert.assertEquals(
			expectedJournalArticle2.getArticleId(),
			actualJournalArticle.getArticleId());
	}

	@Test
	public void testRenderCollectionStyledLayoutStructureItemWithoutSelectingSegmentsExperience()
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		JournalArticle expectedJournalArticle1 = _addJournalArticle(
			ddmStructure);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			expectedJournalArticle1.getResourcePrimKey());

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {assetEntry1.getEntryId()},
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		JournalArticle expectedJournalArticle2 = _addJournalArticle(
			ddmStructure);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			expectedJournalArticle2.getResourcePrimKey());

		SegmentsEntry segmentsEntry = _addSegmentsEntryByFirstName("Test");

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {assetEntry2.getEntryId()},
			segmentsEntry.getSegmentsEntryId(), _serviceContext);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_createLayoutStructure(
			assetListEntry.getAssetListEntryId(), layout,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		RenderLayoutStructureTag renderLayoutStructureTag =
			_getRenderLayoutStructureTagDefaultSegmentsExperience(
				layout, mockHttpServletRequest, mockHttpServletResponse);

		renderLayoutStructureTag.doTag(
			mockHttpServletRequest, mockHttpServletResponse);

		List<JournalArticle> actualJournalArticles =
			(List<JournalArticle>)mockHttpServletRequest.getAttribute(
				"liferay-info:info-list-grid:infoListObjects");

		Assert.assertNotNull(actualJournalArticles);
		Assert.assertEquals(
			actualJournalArticles.toString(), 1, actualJournalArticles.size());

		JournalArticle actualJournalArticle = actualJournalArticles.get(0);

		Assert.assertEquals(
			expectedJournalArticle1.getArticleId(),
			actualJournalArticle.getArticleId());
	}

	@Test
	public void testRenderFormWithInfoFormException() throws Exception {
		InfoField<TextInfoFieldType> infoField = _getInfoField(false);

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_portal, _editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			InfoFormException infoFormException = new InfoFormException();

			SessionErrors.add(
				mockHttpServletRequest, formItemId, infoFormException);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			RenderLayoutStructureTag renderLayoutStructureTag =
				_getRenderLayoutStructureTagDefaultSegmentsExperience(
					layout, mockHttpServletRequest, mockHttpServletResponse);

			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);

			Assert.assertFalse(
				SessionErrors.contains(mockHttpServletRequest, formItemId));

			String content = mockHttpServletResponse.getContentAsString();

			_assertErrorMessage(
				content,
				infoFormException.getLocalizedMessage(
					_portal.getSiteDefaultLocale(_group)));

			_assertInfoFieldInput(infoField, content);
		}
	}

	@Test
	public void testRenderFormWithInfoFormValidationException()
		throws Exception {

		InfoField<TextInfoFieldType> infoField = _getInfoField(false);

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_portal, _editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			InfoFormValidationException infoFormValidationException =
				new InfoFormValidationException(infoField.getUniqueId());

			SessionErrors.add(
				mockHttpServletRequest, formItemId,
				infoFormValidationException);
			SessionErrors.add(
				mockHttpServletRequest, infoField.getUniqueId(),
				infoFormValidationException);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			RenderLayoutStructureTag renderLayoutStructureTag =
				_getRenderLayoutStructureTagDefaultSegmentsExperience(
					layout, mockHttpServletRequest, mockHttpServletResponse);

			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);

			Assert.assertFalse(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertFalse(
				SessionErrors.contains(
					mockHttpServletRequest, infoField.getUniqueId()));

			String content = mockHttpServletResponse.getContentAsString();

			Locale locale = _portal.getSiteDefaultLocale(_group);

			_assertErrorMessage(
				content,
				infoFormValidationException.getLocalizedMessage(
					infoField.getLabel(locale), locale));

			_assertInfoFieldInput(infoField, content);
		}
	}

	@Test
	public void testRenderFormWithoutErrors() throws Exception {
		InfoField<TextInfoFieldType> infoField = _getInfoField(false);
		InfoField<TextInfoFieldType> readOnlyInfoField = _getInfoField(true);

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField, readOnlyInfoField)
						).build(),
						_portal, _editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField,
				readOnlyInfoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			RenderLayoutStructureTag renderLayoutStructureTag =
				_getRenderLayoutStructureTagDefaultSegmentsExperience(
					layout, mockHttpServletRequest, mockHttpServletResponse);

			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);

			String content = mockHttpServletResponse.getContentAsString();

			String errorHTML = "<div class=\"alert alert-danger\">";

			Assert.assertFalse(content.contains(errorHTML));

			_assertInfoFieldInput(infoField, content);
			_assertInfoFieldInput(readOnlyInfoField, content);

			Matcher matcher = _inputJSONObjectPattern.matcher(content);

			Assert.assertTrue(matcher.find());

			Locale locale = _portal.getSiteDefaultLocale(_group);

			_assertInfoFieldInputJSONObject(
				infoField, matcher.group(1), locale);

			Assert.assertTrue(matcher.find());

			_assertInfoFieldInputJSONObject(
				readOnlyInfoField, matcher.group(1), locale);
		}
	}

	@Test
	public void testRenderFormWithSuccessMessage() throws Exception {
		InfoField<TextInfoFieldType> infoField = _getInfoField(false);

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_portal, _editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			SessionMessages.add(mockHttpServletRequest, formItemId);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			RenderLayoutStructureTag renderLayoutStructureTag =
				_getRenderLayoutStructureTagDefaultSegmentsExperience(
					layout, mockHttpServletRequest, mockHttpServletResponse);

			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);

			String content = mockHttpServletResponse.getContentAsString();

			String formStartHTML = "<form action=\"";

			Assert.assertFalse(content.contains(formStartHTML));

			Locale locale = _portal.getSiteDefaultLocale(_group);

			String expectedSuccessMessage = LanguageUtil.get(
				locale,
				"thank-you.-your-information-was-successfully-received");

			String expectedSuccessHTML = StringBundler.concat(
				"<div class=\"bg-white font-weight-semi-bold p-5 text-3 ",
				"text-center text-secondary\">", expectedSuccessMessage,
				"</div>");

			Assert.assertTrue(content.contains(expectedSuccessHTML));

			String expectedInfoFieldInput =
				"<p>InputName:" + infoField.getName() + "</p>";

			Assert.assertFalse(content.contains(expectedInfoFieldInput));
		}
	}

	private List<AssetEntry> _addAssetEntries(AssetListEntry assetListEntry)
		throws Exception {

		List<AssetEntry> assetEntries = new ArrayList<>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		for (int i = 0; i < _COUNT_INFO_LIST_ITEMS; i++) {
			JournalArticle journalArticle = _addJournalArticle(ddmStructure);

			assetEntries.add(
				_assetEntryLocalService.fetchEntry(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey()));
		}

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			TransformUtil.transformToLongArray(
				assetEntries, assetEntry -> assetEntry.getEntryId()),
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		return assetEntries;
	}

	private CollectionStyledLayoutStructureItem
			_addCollectionStyledLayoutStructureItem(
				AssetListEntry assetListEntry, Layout layout,
				long segmentsExperienceId)
		throws Exception {

		LayoutStructure layoutStructure =
			_addCollectionStyledLayoutStructureItemAndGetLayoutStructure(
				JSONUtil.put(
					"classNameId", _portal.getClassNameId(AssetListEntry.class)
				).put(
					"classPK", assetListEntry.getAssetListEntryId()
				).put(
					"itemType", JournalArticle.class.getName()
				).put(
					"type", InfoListItemSelectorReturnType.class.getName()
				),
				JSONUtil.put(
					"displayAllPages", true
				).put(
					"numberOfItems", _COUNT_INFO_LIST_ITEMS
				).put(
					"numberOfItemsPerPage", _COUNT_INFO_LIST_ITEMS
				).put(
					"paginationType", "none"
				).put(
					"showAllItems", true
				),
				layout, null, segmentsExperienceId);

		List<CollectionStyledLayoutStructureItem>
			collectionStyledLayoutStructureItems =
				layoutStructure.getCollectionStyledLayoutStructureItems();

		Assert.assertEquals(
			collectionStyledLayoutStructureItems.toString(), 1,
			collectionStyledLayoutStructureItems.size());

		return collectionStyledLayoutStructureItems.get(0);
	}

	private LayoutStructure
			_addCollectionStyledLayoutStructureItemAndGetLayoutStructure(
				JSONObject collectionJSONObject,
				JSONObject displayConfigJSONObject, Layout layout,
				String listStyle, long segmentsExperienceId)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)
					layoutStructure.addCollectionStyledLayoutStructureItem(
						layoutStructure.getMainItemId(), 0);

		collectionStyledLayoutStructureItem.setCollectionJSONObject(
			collectionJSONObject);

		if (displayConfigJSONObject != null) {
			JSONObject itemConfigJSONObject =
				collectionStyledLayoutStructureItem.getItemConfigJSONObject();

			for (String key : displayConfigJSONObject.keySet()) {
				itemConfigJSONObject.put(key, displayConfigJSONObject.get(key));
			}

			collectionStyledLayoutStructureItem.updateItemConfig(
				itemConfigJSONObject);
		}

		collectionStyledLayoutStructureItem.setListStyle(listStyle);
		collectionStyledLayoutStructureItem.setNamespace(
			RandomTestUtil.randomString());

		layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructureData(
					_group.getGroupId(), layout.getPlid(), segmentsExperienceId,
					layoutStructure.toString());

		return LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());
	}

	private void _addFragmentEntryLinks(
			Layout layout, String parentItemId, long segmentsExperienceId)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK, _serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK,
				"<h1 data-lfr-editable-id=\"element-text\" " +
					"data-lfr-editable-type=\"text\">Heading Example</h1>",
				StringPool.BLANK, false, StringPool.BLANK, null, 0, false,
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		for (int i = 0; i < _COUNT_FRAGMENT_ENTRY_LINKS; i++) {
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						"element-text",
						JSONUtil.put(
							"collectionFieldId", "JournalArticle_title"))
				).toString(),
				fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
				fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), layout,
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				parentItemId, i, segmentsExperienceId);
		}
	}

	private JournalArticle _addJournalArticle(DDMStructure ddmStructure)
		throws Exception {

		return _journalArticleLocalService.addArticle(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			DDMStructureTestUtil.getSampleStructuredContent(),
			ddmStructure.getStructureId(), StringPool.BLANK, null, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false, 0, 0,
			null, null, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private SegmentsEntry _addSegmentsEntryByFirstName(String firstName)
		throws Exception {

		Criteria criteria = new Criteria();

		_segmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", firstName),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria));
	}

	private void _assertErrorMessage(
		String content, String expectedErrorMessage) {

		String expectedErrorHTML =
			"<div class=\"alert alert-danger\">" + expectedErrorMessage +
				"</div>";

		Assert.assertTrue(content.contains(expectedErrorHTML));
	}

	private void _assertInfoFieldInput(
		InfoField<TextInfoFieldType> infoField, String content) {

		String expectedInfoFieldInput =
			"<p>InputName:" + infoField.getName() + "</p>";

		Assert.assertTrue(content.contains(expectedInfoFieldInput));
	}

	private void _assertInfoFieldInputJSONObject(
			InfoField<TextInfoFieldType> infoField, String jsonString,
			Locale locale)
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonString);

		Assert.assertEquals(
			infoField.getLabel(locale), jsonObject.getString("label"));
		Assert.assertEquals(
			infoField.isReadOnly(), jsonObject.getBoolean("readOnly"));
		Assert.assertEquals("text", jsonObject.getString("type"));
	}

	private void _createLayoutStructure(
			long assetListEntryId, Layout layout, long segmentsExperienceId)
		throws Exception {

		_addCollectionStyledLayoutStructureItemAndGetLayoutStructure(
			JSONUtil.put(
				"classNameId", _portal.getClassNameId(AssetListEntry.class)
			).put(
				"classPK", assetListEntryId
			).put(
				"itemType", JournalArticle.class.getName()
			).put(
				"type", InfoListItemSelectorReturnType.class.getName()
			),
			null, layout,
			"com.liferay.journal.web.internal.info.list.renderer." +
				"BulletedJournalArticleBasicInfoListRenderer",
			segmentsExperienceId);
	}

	private LayoutStructure _getDefaultMasterLayoutStructure() {
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		layoutStructure.addDropZoneLayoutStructureItem(
			rootLayoutStructureItem.getItemId(), 0);

		return layoutStructure;
	}

	private InfoField<TextInfoFieldType> _getInfoField(boolean readOnly) {
		return InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			RandomTestUtil.randomString()
		).name(
			RandomTestUtil.randomString()
		).labelInfoLocalizedValue(
			InfoLocalizedValue.singleValue(RandomTestUtil.randomString())
		).localizable(
			true
		).readOnly(
			readOnly
		).build();
	}

	private MockHttpServletRequest _getMockHttpServletRequest(Layout layout)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_companyLocalService.getCompany(layout.getCompanyId()), _group,
				layout);

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setRequest(mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			"ORIGINAL_HTTP_SERVLET_REQUEST", mockHttpServletRequest);

		return mockHttpServletRequest;
	}

	private RenderLayoutStructureTag _getRenderLayoutStructureTag(
		Layout layout, MockHttpServletRequest mockHttpServletRequest,
		MockHttpServletResponse mockHttpServletResponse,
		long selectedSegmentsExperienceId) {

		RenderLayoutStructureTag renderLayoutStructureTag =
			new RenderLayoutStructureTag();

		renderLayoutStructureTag.setLayoutStructure(
			_layoutStructureProvider.getLayoutStructure(
				layout.getPlid(), selectedSegmentsExperienceId));
		renderLayoutStructureTag.setPageContext(
			new MockPageContext(
				null, mockHttpServletRequest, mockHttpServletResponse));

		return renderLayoutStructureTag;
	}

	private RenderLayoutStructureTag
		_getRenderLayoutStructureTagDefaultSegmentsExperience(
			Layout layout, MockHttpServletRequest mockHttpServletRequest,
			MockHttpServletResponse mockHttpServletResponse) {

		return _getRenderLayoutStructureTag(
			layout, mockHttpServletRequest, mockHttpServletResponse,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));
	}

	private static final int _COUNT_FRAGMENT_ENTRY_LINKS = 5;

	private static final int _COUNT_INFO_LIST_ITEMS = 5;

	private static final Pattern _inputJSONObjectPattern = Pattern.compile(
		"<p>InputJSONObject:(.*?)<\\/p>");

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "info.item.capability.key=" + EditPageInfoItemCapability.KEY
	)
	private InfoItemCapability _editPageInfoItemCapability;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}