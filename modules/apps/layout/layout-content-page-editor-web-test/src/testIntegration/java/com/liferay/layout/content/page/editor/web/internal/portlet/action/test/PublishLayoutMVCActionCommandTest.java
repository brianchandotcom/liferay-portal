/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.web.internal.portlet.constants.LayoutContentPageEditorWebPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.BulkLayoutConverter;
import com.liferay.layout.util.structure.DeletedLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class PublishLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
	}

	@Test
	public void testDeletedItemPortletPreferencesAreRemovedWhenLayoutIsPublished()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		FragmentEntryLink fragmentEntryLink = _addPortletToLayout(draftLayout);

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		String encodePortletId = PortletIdCodec.encode(
			LayoutContentPageEditorWebPortletKeys.
				LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET,
			editableValuesJSONObject.getString("instanceId"));

		_assertNotNullPortletPreferences(
			draftLayout.getPlid(), encodePortletId);

		_assertNullPortletPreferences(layout.getPlid(), encodePortletId);

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		_assertNotNullPortletPreferences(
			draftLayout.getPlid(), encodePortletId);

		_assertNotNullPortletPreferences(layout.getPlid(), encodePortletId);

		LayoutStructure layoutStructure = _getLayoutStructure(draftLayout);

		LayoutStructureItem portletLayoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());

		layoutStructure.markLayoutStructureItemForDeletion(
			portletLayoutStructureItem.getItemId(), Collections.emptyList());

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), draftLayout.getPlid(),
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid()),
				layoutStructure.toString());

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		_assertNullPortletPreferences(draftLayout.getPlid(), encodePortletId);

		_assertNullPortletPreferences(layout.getPlid(), encodePortletId);
	}

	@Test
	public void testDeletedItemsAreRemovedWhenLayoutIsPublished()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		LayoutStructure layoutStructure = _getLayoutStructure(draftLayout);

		LayoutStructureItem layoutStructureItem1 =
			layoutStructure.addContainerStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0);

		LayoutStructureItem layoutStructureItem2 =
			layoutStructure.addRowStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0, 3);

		LayoutStructureItem layoutStructureItem3 =
			layoutStructure.addRowStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0, 3);

		layoutStructure.markLayoutStructureItemForDeletion(
			layoutStructureItem1.getItemId(), Collections.emptyList());

		layoutStructure.markLayoutStructureItemForDeletion(
			layoutStructureItem2.getItemId(), Collections.emptyList());

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), draftLayout.getPlid(),
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid()),
				layoutStructure.toString());

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		layoutStructure = _getLayoutStructure(draftLayout);

		List<DeletedLayoutStructureItem> deletedLayoutStructureItems =
			layoutStructure.getDeletedLayoutStructureItems();

		Assert.assertEquals(
			deletedLayoutStructureItems.toString(), 0,
			deletedLayoutStructureItems.size());

		Assert.assertNull(
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem1.getItemId()));
		Assert.assertNull(
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem2.getItemId()));
		Assert.assertNotNull(
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem3.getItemId()));
	}

	@Test
	public void testLayoutContentIsIndexedAfterPublishing() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Assert.assertFalse(layout.isPublished());

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid());

		FragmentEntryLink fragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				"{}", draftLayout, segmentsExperienceId);

		String keywords = fragmentEntryLink.getHtml();

		Assert.assertTrue(keywords, Validator.isNotNull(keywords));

		IndexerFixture<Layout> layoutIndexerFixture = new IndexerFixture<>(
			Layout.class);

		layoutIndexerFixture.searchNoOne(keywords);

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		layout = _layoutLocalService.getLayout(layout.getPlid());

		Assert.assertTrue(layout.isPublished());

		Locale locale = LocaleUtil.getSiteDefault();

		Document document = layoutIndexerFixture.searchOnlyOne(
			keywords, locale);

		Assert.assertNotNull(document);

		String content = document.get(
			Field.getLocalizedName(locale, Field.CONTENT));

		Assert.assertTrue(
			content, StringUtil.contains(content, keywords, StringPool.BLANK));

		Assert.assertEquals(
			document.get(Field.ENTRY_CLASS_PK),
			String.valueOf(layout.getPlid()));
	}

	@Test
	public void testPublishConversionDraftCreatedByDeletedUser()
		throws Exception {

		User user = UserTestUtil.addCompanyAdminUser(
			_companyLocalService.getCompany(_group.getCompanyId()));

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), user.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			Layout originalLayout = _layoutLocalService.addLayout(
				user.getUserId(), _group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
				RandomTestUtil.randomString(), StringPool.BLANK,
				StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
				StringPool.BLANK, serviceContext);

			Assert.assertEquals(user.getUserId(), originalLayout.getUserId());
			Assert.assertTrue(originalLayout.isTypePortlet());

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			_deleteUser(user, serviceContext);

			_bulkLayoutConverter.convertLayout(originalLayout.getPlid());

			originalLayout = _layoutLocalService.getLayout(
				originalLayout.getPlid());

			Assert.assertTrue(originalLayout.isTypePortlet());

			Layout conversionDraftLayout = originalLayout.fetchDraftLayout();

			Assert.assertNotNull(conversionDraftLayout);
			Assert.assertEquals(
				TestPropsValues.getUserId(), conversionDraftLayout.getUserId());
			Assert.assertTrue(conversionDraftLayout.isTypeContent());

			ContentLayoutTestUtil.publishLayout(
				conversionDraftLayout, originalLayout);

			originalLayout = _layoutLocalService.getLayout(
				originalLayout.getPlid());

			Assert.assertTrue(originalLayout.isPublished());
			Assert.assertTrue(originalLayout.isTypeContent());

			Layout draftLayout = originalLayout.fetchDraftLayout();

			Assert.assertNotNull(draftLayout);
			Assert.assertEquals(
				conversionDraftLayout.getPlid(), draftLayout.getPlid());

			Assert.assertTrue(draftLayout.isApproved());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private FragmentEntryLink _addPortletToLayout(Layout layout)
		throws Exception {

		List<FragmentEntryLink> originalFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), layout.getPlid());

		JSONObject processAddPortletJSONObject =
			ContentLayoutTestUtil.addPortletToLayout(
				layout,
				LayoutContentPageEditorWebPortletKeys.
					LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET);

		Assert.assertNotNull(processAddPortletJSONObject);

		JSONObject fragmentEntryLinkJSONObject =
			processAddPortletJSONObject.getJSONObject("fragmentEntryLink");

		Assert.assertNotNull(fragmentEntryLinkJSONObject);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLinkJSONObject.getLong("fragmentEntryLinkId"));

		Assert.assertNotNull(fragmentEntryLink);

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), layout.getPlid());

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + 1,
			actualFragmentEntryLinks.size());

		return fragmentEntryLink;
	}

	private void _assertNotNullPortletPreferences(
		long plid, String encodePortletId) {

		Assert.assertNotNull(
			_portletPreferencesLocalService.fetchPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, encodePortletId));
	}

	private void _assertNullPortletPreferences(
		long plid, String encodePortletId) {

		Assert.assertNull(
			_portletPreferencesLocalService.fetchPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, encodePortletId));
	}

	private void _deleteUser(User user, ServiceContext serviceContext)
		throws PortalException {

		_userLocalService.updateStatus(
			user.getUserId(), WorkflowConstants.STATUS_INACTIVE,
			serviceContext);

		_userLocalService.deleteUser(user.getUserId());

		Assert.assertNull(_userLocalService.fetchUser(user.getUserId()));
	}

	private LayoutStructure _getLayoutStructure(Layout layout)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layout.getPlid());

		return LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());
	}

	@Inject
	private BulkLayoutConverter _bulkLayoutConverter;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private UserLocalService _userLocalService;

}