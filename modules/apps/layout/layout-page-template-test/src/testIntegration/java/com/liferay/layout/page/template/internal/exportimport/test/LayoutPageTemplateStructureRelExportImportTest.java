/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateStructureRelExportImportTest
	extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testBackgroundImageMappedValuesImport() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Layout exportedLayout = LayoutTestUtil.addTypeContentLayout(group);

		// Delete and readd to ensure a different layout ID (not ID or UUID).
		// See LPS-32132.

		LayoutLocalServiceUtil.deleteLayout(
			exportedLayout, new ServiceContext());

		exportedLayout = LayoutTestUtil.addTypeContentLayout(group);

		LayoutPageTemplateStructure exportedLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					group.getGroupId(), exportedLayout.getPlid());

		LayoutPageTemplateStructureRel exportedLayoutPageTemplateStructureRel =
			_layoutPageTemplateStructureRelLocalService.
				fetchLayoutPageTemplateStructureRel(
					exportedLayoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					SegmentsExperienceLocalServiceUtil.
						fetchDefaultSegmentsExperienceId(
							exportedLayout.getPlid()));

		LayoutStructure exportedLayoutStructure = LayoutStructure.of(
			exportedLayoutPageTemplateStructureRel.getData());

		ContainerStyledLayoutStructureItem
			exportedContainerStyledLayoutStructureItem =
				(ContainerStyledLayoutStructureItem)
					exportedLayoutStructure.
						addContainerStyledLayoutStructureItem(
							exportedLayoutStructure.getMainItemId(), 0);

		FileEntry exportedFileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".png", ContentTypes.IMAGE_PNG,
			_read("dependencies/sample.png"), null, null, serviceContext);

		JSONObject exportedItemConfigJSONObject = JSONUtil.put(
			"styles",
			JSONUtil.put(
				"backgroundImage",
				JSONUtil.put(
					"className", FileEntry.class.getName()
				).put(
					"classNameId", _portal.getClassNameId(FileEntry.class)
				).put(
					"classPK", exportedFileEntry.getFileEntryId()
				).put(
					"classTypeId",
					String.valueOf(
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
				).put(
					"itemSubtype",
					_language.get(
						LocaleUtil.ENGLISH,
						DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT)
				).put(
					"itemType", "Document"
				).put(
					"title", exportedFileEntry.getTitle()
				).put(
					"type",
					"com.liferay.item.selector.criteria." +
						"InfoItemItemSelectorReturnType"
				)));

		exportedContainerStyledLayoutStructureItem.updateItemConfig(
			exportedItemConfigJSONObject);

		exportedLayoutPageTemplateStructureRel.setData(
			exportedLayoutStructure.toString());

		_layoutPageTemplateStructureRelLocalService.
			updateLayoutPageTemplateStructureRel(
				exportedLayoutPageTemplateStructureRel);

		exportImportLayouts(
			new long[] {exportedLayout.getLayoutId()}, getImportParameterMap());

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			exportedLayout.getUuid(), importedGroup.getGroupId(), false);

		LayoutPageTemplateStructure importedLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					importedGroup.getGroupId(), importedLayout.getPlid());

		LayoutStructure importedLayoutStructure = LayoutStructure.of(
			importedLayoutPageTemplateStructure.
				getDefaultSegmentsExperienceData());

		LayoutStructureItem mainLayoutStructureItem =
			importedLayoutStructure.getMainLayoutStructureItem();

		List<String> childrenItemIds =
			mainLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		LayoutStructureItem layoutStructureItem =
			importedLayoutStructure.getLayoutStructureItem(
				childrenItemIds.get(0));

		Assert.assertTrue(
			layoutStructureItem instanceof ContainerStyledLayoutStructureItem);

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)layoutStructureItem;

		JSONObject backgroundImageJSONObject =
			containerStyledLayoutStructureItem.getBackgroundImageJSONObject();

		FileEntry importedDLFileEntry =
			_dlAppLocalService.getFileEntryByUuidAndGroupId(
				exportedFileEntry.getUuid(), importedGroup.getGroupId());

		Assert.assertEquals(
			importedDLFileEntry.getFileEntryId(),
			backgroundImageJSONObject.getLong("classPK"));
	}

	private byte[] _read(String fileName) throws Exception {
		return FileUtil.getBytes(
			LayoutPageTemplateStructureRelExportImportTest.class, fileName);
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private Language _language;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private Portal _portal;

}