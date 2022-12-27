/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.utility.page.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.importer.LayoutsImporterResultEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.File;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bárbara Cabrera
 */
@RunWith(Arquillian.class)
public class ExportImportLayoutUtilityPageTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_serviceContext1 = ServiceContextTestUtil.getServiceContext(
			_group1, TestPropsValues.getUserId());
		_serviceContext2 = ServiceContextTestUtil.getServiceContext(
			_group2, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext1);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testExportImportLayoutUtilityPageEntry() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		LayoutUtilityPageEntry layoutUtilityPageEntry1 =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				externalReferenceCode, _serviceContext1.getUserId(),
				_serviceContext1.getScopeGroupId(), 0, 0, false,
				StringUtil.randomString(), "LAYOUT", 0);

		_addItemsToLayout(
			layoutUtilityPageEntry1.getGroupId(),
			layoutUtilityPageEntry1.getPlid(),
			LayoutDataItemTypeConstants.TYPE_CONTAINER,
			LayoutDataItemTypeConstants.TYPE_ROW);

		Repository repository = _portletFileRepository.fetchPortletRepository(
			_group1.getGroupId(), LayoutAdminPortletKeys.GROUP_PAGES);

		if (repository == null) {
			repository = _portletFileRepository.addPortletRepository(
				_group1.getGroupId(), LayoutAdminPortletKeys.GROUP_PAGES,
				_serviceContext1);
		}

		Class<?> clazz = getClass();

		FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
			null, _group1.getGroupId(), TestPropsValues.getUserId(),
			LayoutUtilityPageEntry.class.getName(),
			layoutUtilityPageEntry1.getLayoutUtilityPageEntryId(),
			RandomTestUtil.randomString(), repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			layoutUtilityPageEntry1.getLayoutUtilityPageEntryId() +
				"_preview.png",
			ContentTypes.IMAGE_PNG, false);

		layoutUtilityPageEntry1 =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				layoutUtilityPageEntry1.getLayoutUtilityPageEntryId(),
				fileEntry.getFileEntryId());

		Assert.assertNotEquals(
			0, layoutUtilityPageEntry1.getPreviewFileEntryId());

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getFile", new Class<?>[] {long[].class},
			new long[] {layoutUtilityPageEntry1.getLayoutUtilityPageEntryId()});

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(_serviceContext2);

		try {
			layoutsImporterResultEntries = _layoutsImporter.importFile(
				TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
				false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutsImporterResultEntries);

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 1,
			layoutsImporterResultEntries.size());

		LayoutsImporterResultEntry layoutUtilityPageImportEntry =
			layoutsImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutsImporterResultEntry.Status.IMPORTED,
			layoutUtilityPageImportEntry.getStatus());

		LayoutUtilityPageEntry layoutUtilityPageEntry2 =
			_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					externalReferenceCode, _group2.getGroupId());

		Assert.assertNotNull(layoutUtilityPageEntry2);

		Assert.assertEquals(
			layoutUtilityPageEntry1.getName(),
			layoutUtilityPageEntry2.getName());
		Assert.assertEquals(
			layoutUtilityPageEntry1.getType(),
			layoutUtilityPageEntry2.getType());

		Assert.assertNotEquals(
			0, layoutUtilityPageEntry2.getPreviewFileEntryId());

		_assertExpectedLayoutStructureItem(
			layoutUtilityPageEntry2.getGroupId(),
			layoutUtilityPageEntry2.getPlid(),
			LayoutDataItemTypeConstants.TYPE_CONTAINER,
			LayoutDataItemTypeConstants.TYPE_ROW);
	}

	private void _addItemsToLayout(long groupId, long plid, String... itemTypes)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(plid);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(groupId, plid);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		String parentItemId = layoutStructure.getMainItemId();

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		for (String itemType : itemTypes) {
			JSONObject addItemJSONObject =
				ContentLayoutTestUtil.addItemToLayout(
					"{}", itemType, layout, parentItemId, 0,
					segmentsExperienceId);

			parentItemId = addItemJSONObject.getString("addedItemId");
		}

		_assertExpectedLayoutStructureItem(groupId, plid, itemTypes);
	}

	private void _assertExpectedLayoutStructureItem(
		long groupId, long plid, String... itemTypes) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(groupId, plid);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		for (String itemType : itemTypes) {
			List<String> childrenItemIds =
				layoutStructureItem.getChildrenItemIds();

			Assert.assertEquals(
				childrenItemIds.toString(), 1, childrenItemIds.size());

			layoutStructureItem = layoutStructure.getLayoutStructureItem(
				childrenItemIds.get(0));

			Assert.assertEquals(itemType, layoutStructureItem.getItemType());
		}
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutsImporter _layoutsImporter;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_admin/export_layout_utility_page_entries"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private PortletFileRepository _portletFileRepository;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext1;
	private ServiceContext _serviceContext2;

}