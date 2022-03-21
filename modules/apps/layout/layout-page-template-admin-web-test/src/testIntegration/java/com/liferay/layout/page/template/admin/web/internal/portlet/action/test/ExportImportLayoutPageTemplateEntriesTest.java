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

package com.liferay.layout.page.template.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporterResultEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.File;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class ExportImportLayoutPageTemplateEntriesTest {

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
	}

	@Test
	public void testExportImportLayoutPageTemplateEntry() throws Exception {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), _group1.getGroupId(),
					"Page Template Collection", StringPool.BLANK,
					_serviceContext1);

		LayoutPageTemplateEntry layoutPageTemplateEntry1 =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext1.getUserId(),
				_serviceContext1.getScopeGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				"Page Template One",
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0,
				WorkflowConstants.STATUS_APPROVED, _serviceContext1);

		String html =
			"<lfr-editable id=\"element-text\" type=\"text\">Test Text " +
				"Fragment</lfr-editable>";

		FragmentEntry fragmentEntry = _addFragmentEntry(
			_group1.getGroupId(), "test-text-fragment", "Test Text Fragment",
			html);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group1.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0,
				layoutPageTemplateEntry1.getPlid(), StringPool.BLANK, html,
				StringPool.BLANK,
				_read("export_import_fragment_field_text_config.json"),
				_read("export_import_fragment_field_text_editable_values.json"),
				StringPool.BLANK, 0, null, _serviceContext1);

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layoutPageTemplateEntry1.getPlid());

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group1.getGroupId(), layoutPageTemplateEntry1.getPlid(),
				defaultSegmentsExperienceId,
				StringUtil.replace(
					_read("export_import_layout_data.json"), "${", "}",
					HashMapBuilder.put(
						"FRAGMENT_ENTRY_LINK1_ID",
						String.valueOf(
							fragmentEntryLink.getFragmentEntryLinkId())
					).build()));

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			_group1.getGroupId(), RandomTestUtil.randomString(),
			_serviceContext1);

		Class<?> clazz = getClass();

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			_group1.getGroupId(), TestPropsValues.getUserId(),
			LayoutPageTemplateEntry.class.getName(),
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId(),
			RandomTestUtil.randomString(), repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry1.getLayoutPageTemplateEntryId(),
			fileEntry.getFileEntryId());

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry1.getLayoutPageTemplateEntryId()
			});

		_addFragmentEntry(
			_group2.getGroupId(), "test-text-fragment", "Test Text Fragment",
			html);

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(_serviceContext2);

		try {
			layoutPageTemplatesImporterResultEntries =
				_layoutPageTemplatesImporter.importFile(
					TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
					false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutPageTemplatesImporterResultEntries);

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 1,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplatesImporterResultEntry layoutPageTemplateImportEntry =
			layoutPageTemplatesImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED,
			layoutPageTemplateImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplateImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry2 =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_group2.getGroupId(), layoutPageTemplateEntryKey);

		Assert.assertNotNull(layoutPageTemplateEntry2);

		LayoutPageTemplateStructure layoutPageTemplateStructure1 =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry1.getGroupId(),
					layoutPageTemplateEntry1.getPlid());
		LayoutPageTemplateStructure layoutPageTemplateStructure2 =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateEntry2.getGroupId(),
					layoutPageTemplateEntry2.getPlid());

		LayoutStructure layoutStructure1 = LayoutStructure.of(
			layoutPageTemplateStructure1.getData(0));
		LayoutStructure layoutStructure2 = LayoutStructure.of(
			layoutPageTemplateStructure2.getData(0));

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem1 =
			_getContainerLayoutStructureItem(layoutStructure1);
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem2 =
			_getContainerLayoutStructureItem(layoutStructure2);

		_validateContainerLayoutStructureItem(
			containerStyledLayoutStructureItem1,
			containerStyledLayoutStructureItem2);

		List<String> containerLayoutStructureItemChildrenItemIds1 =
			containerStyledLayoutStructureItem1.getChildrenItemIds();
		List<String> containerLayoutStructureItemChildrenItemIds2 =
			containerStyledLayoutStructureItem2.getChildrenItemIds();

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem1 =
			(RowStyledLayoutStructureItem)
				layoutStructure1.getLayoutStructureItem(
					containerLayoutStructureItemChildrenItemIds1.get(0));
		RowStyledLayoutStructureItem rowStyledLayoutStructureItem2 =
			(RowStyledLayoutStructureItem)
				layoutStructure2.getLayoutStructureItem(
					containerLayoutStructureItemChildrenItemIds2.get(0));

		_validateRowLayoutStructureItem(
			rowStyledLayoutStructureItem1, rowStyledLayoutStructureItem2);

		List<String> rowLayoutStructureItemChildrenItemIds1 =
			rowStyledLayoutStructureItem1.getChildrenItemIds();
		List<String> rowLayoutStructureItemChildrenItemIds2 =
			rowStyledLayoutStructureItem2.getChildrenItemIds();

		ColumnLayoutStructureItem columnLayoutStructureItem1 =
			(ColumnLayoutStructureItem)layoutStructure1.getLayoutStructureItem(
				rowLayoutStructureItemChildrenItemIds1.get(0));
		ColumnLayoutStructureItem columnLayoutStructureItem2 =
			(ColumnLayoutStructureItem)layoutStructure2.getLayoutStructureItem(
				rowLayoutStructureItemChildrenItemIds2.get(0));

		_validateColumnLayoutStructureItem(
			columnLayoutStructureItem1, columnLayoutStructureItem2);

		List<String> columnLayoutStructureItemChildrenItemIds1 =
			columnLayoutStructureItem1.getChildrenItemIds();
		List<String> columnLayoutStructureItemChildrenItemIds2 =
			columnLayoutStructureItem2.getChildrenItemIds();

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem1 =
			(FragmentStyledLayoutStructureItem)
				layoutStructure1.getLayoutStructureItem(
					columnLayoutStructureItemChildrenItemIds1.get(0));
		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem2 =
			(FragmentStyledLayoutStructureItem)
				layoutStructure2.getLayoutStructureItem(
					columnLayoutStructureItemChildrenItemIds2.get(0));

		_validateFragmentLayoutStructureItem(
			fragmentStyledLayoutStructureItem1,
			fragmentStyledLayoutStructureItem2);
	}

	private FragmentEntry _addFragmentEntry(
			long groupId, String key, String name, String html)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), groupId, "Test Collection",
				StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), groupId,
			fragmentCollection.getFragmentCollectionId(), key, name,
			StringPool.BLANK, html, StringPool.BLANK, false, StringPool.BLANK,
			null, 0, FragmentConstants.TYPE_COMPONENT,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private ContainerStyledLayoutStructureItem _getContainerLayoutStructureItem(
		LayoutStructure layoutStructure) {

		LayoutStructureItem layoutStructureItem =
			_getMainChildLayoutStructureItem(layoutStructure);

		Assert.assertTrue(
			layoutStructureItem instanceof ContainerStyledLayoutStructureItem);

		return (ContainerStyledLayoutStructureItem)layoutStructureItem;
	}

	private LayoutStructureItem _getMainChildLayoutStructureItem(
		LayoutStructure layoutStructure) {

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<String> childrenItemIds =
			mainLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		String childItemId = childrenItemIds.get(0);

		return layoutStructure.getLayoutStructureItem(childItemId);
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _validateColumnLayoutStructureItem(
		ColumnLayoutStructureItem expectedColumnLayoutStructureItem,
		ColumnLayoutStructureItem actualColumnLayoutStructureItem) {

		Assert.assertEquals(
			expectedColumnLayoutStructureItem.getSize(),
			actualColumnLayoutStructureItem.getSize());
	}

	private void _validateContainerLayoutStructureItem(
		ContainerStyledLayoutStructureItem
			expectedContainerStyledLayoutStructureItem,
		ContainerStyledLayoutStructureItem
			actualContainerStyledLayoutStructureItem) {

		Assert.assertEquals(
			expectedContainerStyledLayoutStructureItem.
				getBackgroundColorCssClass(),
			actualContainerStyledLayoutStructureItem.
				getBackgroundColorCssClass());

		JSONObject expectedBackgroundImageJSONObject =
			expectedContainerStyledLayoutStructureItem.
				getBackgroundImageJSONObject();
		JSONObject actualBackgroundImageJSONObject =
			actualContainerStyledLayoutStructureItem.
				getBackgroundImageJSONObject();

		Assert.assertEquals(
			expectedBackgroundImageJSONObject.toJSONString(),
			actualBackgroundImageJSONObject.toJSONString());

		Assert.assertEquals(
			expectedContainerStyledLayoutStructureItem.getPaddingBottom(),
			actualContainerStyledLayoutStructureItem.getPaddingBottom());
		Assert.assertEquals(
			expectedContainerStyledLayoutStructureItem.getPaddingLeft(),
			actualContainerStyledLayoutStructureItem.getPaddingLeft());
		Assert.assertEquals(
			expectedContainerStyledLayoutStructureItem.getPaddingRight(),
			actualContainerStyledLayoutStructureItem.getPaddingRight());
		Assert.assertEquals(
			expectedContainerStyledLayoutStructureItem.getPaddingTop(),
			actualContainerStyledLayoutStructureItem.getPaddingTop());
		Assert.assertEquals(
			expectedContainerStyledLayoutStructureItem.getWidthType(),
			actualContainerStyledLayoutStructureItem.getWidthType());
	}

	private void _validateFragmentLayoutStructureItem(
			FragmentStyledLayoutStructureItem
				expectedFragmentStyledLayoutStructureItem,
			FragmentStyledLayoutStructureItem
				actualFragmentStyledLayoutStructureItem)
		throws Exception {

		long expectedFragmentEntryLinkId =
			expectedFragmentStyledLayoutStructureItem.getFragmentEntryLinkId();
		long actualFragmentEntryLinkId =
			actualFragmentStyledLayoutStructureItem.getFragmentEntryLinkId();

		FragmentEntryLink expectedFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				expectedFragmentEntryLinkId);
		FragmentEntryLink actualFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				actualFragmentEntryLinkId);

		String expectedEditableValues =
			expectedFragmentEntryLink.getEditableValues();
		String actualEditableValues =
			actualFragmentEntryLink.getEditableValues();

		JSONObject expectedEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(expectedEditableValues);
		JSONObject actualEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(actualEditableValues);

		JSONObject expectedBackgroundImageFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.background.image." +
					"BackgroundImageFragmentEntryProcessor");
		JSONObject actualBackgroundImageFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.background.image." +
					"BackgroundImageFragmentEntryProcessor");

		Assert.assertEquals(
			expectedBackgroundImageFragmentEntryProcessorJSONObject.
				toJSONString(),
			actualBackgroundImageFragmentEntryProcessorJSONObject.
				toJSONString());

		JSONObject expectedEditableFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor");
		JSONObject actualEditableFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor");

		JSONObject expectedElementTextJSONObject =
			expectedEditableFragmentEntryProcessorJSONObject.getJSONObject(
				"element-text");
		JSONObject actualElementTextJSONObject =
			actualEditableFragmentEntryProcessorJSONObject.getJSONObject(
				"element-text");

		Assert.assertEquals(
			expectedElementTextJSONObject.getString("en_US"),
			actualElementTextJSONObject.getString("en_US"));

		Assert.assertEquals(
			expectedElementTextJSONObject.getString("es_ES"),
			actualElementTextJSONObject.getString("es_ES"));

		JSONObject expectedElementTextConfigJSONObject =
			expectedElementTextJSONObject.getJSONObject("config");
		JSONObject actualElementTextConfigJSONObject =
			actualElementTextJSONObject.getJSONObject("config");

		Assert.assertEquals(
			expectedElementTextConfigJSONObject.toJSONString(),
			actualElementTextConfigJSONObject.toJSONString());

		JSONObject expectedFreeMarkerFragmentEntryProcessorJSONObject =
			expectedEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.freemarker." +
					"FreeMarkerFragmentEntryProcessor");
		JSONObject actualFreeMarkerFragmentEntryProcessorJSONObject =
			actualEditableValuesJSONObject.getJSONObject(
				"com.liferay.fragment.entry.processor.freemarker." +
					"FreeMarkerFragmentEntryProcessor");

		Assert.assertEquals(
			expectedFreeMarkerFragmentEntryProcessorJSONObject.toJSONString(),
			actualFreeMarkerFragmentEntryProcessorJSONObject.toJSONString());

		Assert.assertEquals(
			expectedFragmentEntryLink.getPosition(),
			actualFragmentEntryLink.getPosition());
	}

	private void _validateRowLayoutStructureItem(
		RowStyledLayoutStructureItem expectedRowStyledLayoutStructureItem,
		RowStyledLayoutStructureItem actualRowStyledLayoutStructureItem) {

		Assert.assertEquals(
			expectedRowStyledLayoutStructureItem.isGutters(),
			actualRowStyledLayoutStructureItem.isGutters());
		Assert.assertEquals(
			expectedRowStyledLayoutStructureItem.getNumberOfColumns(),
			actualRowStyledLayoutStructureItem.getNumberOfColumns());
	}

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_page_template_admin/export_layout_page_template_entries"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext1;
	private ServiceContext _serviceContext2;

}