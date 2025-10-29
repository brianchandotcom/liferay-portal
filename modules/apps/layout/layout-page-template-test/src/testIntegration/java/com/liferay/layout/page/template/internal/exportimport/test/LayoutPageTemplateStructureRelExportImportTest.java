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
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
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
import com.liferay.segments.service.SegmentsExperienceLocalService;

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
		Layout exportedLayout = LayoutTestUtil.addTypeContentLayout(group);

		// Delete and readd to ensure a different layout ID (not ID or UUID).
		// See LPS-32132.

		LayoutLocalServiceUtil.deleteLayout(
			exportedLayout, new ServiceContext());

		exportedLayout = LayoutTestUtil.addTypeContentLayout(group);

		FileEntry exportedFileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".png", ContentTypes.IMAGE_PNG,
			_read("dependencies/sample.png"), null, null, null,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		ContentLayoutTestUtil.addItemToLayout(
			JSONUtil.put(
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
					))
			).toString(),
			LayoutDataItemTypeConstants.TYPE_CONTAINER, exportedLayout,
			_layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				exportedLayout.getPlid()));

		exportImportLayouts(
			new long[] {exportedLayout.getLayoutId()}, getImportParameterMap());

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			exportedLayout.getUuid(), importedGroup.getGroupId(), false);

		LayoutStructure importedLayoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				importedLayout.getPlid(),
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(importedLayout.getPlid()));

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

	@Test
	@TestInfo("LPD-67912")
	public void testFormContainerSuccessMessageWithPage() throws Exception {
		Layout layout1 = LayoutTestUtil.addTypeContentLayout(group);
		Layout layout2 = LayoutTestUtil.addTypeContentLayout(group);

		ContentLayoutTestUtil.addItemToLayout(
			JSONUtil.put(
				"classNameId", "0"
			).put(
				"classTypeId", "0"
			).put(
				"successMessage",
				JSONUtil.put(
					"layout",
					JSONUtil.put(
						"groupId", layout2.getGroupId()
					).put(
						"layoutId", layout2.getLayoutId()
					).put(
						"layoutUuid", layout2.getUuid()
					).put(
						"privateLayout", layout2.isPrivateLayout()
					))
			).toString(),
			LayoutDataItemTypeConstants.TYPE_FORM, layout1,
			_layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout1.getPlid()));

		exportImportLayouts(
			new long[] {layout2.getLayoutId(), layout1.getLayoutId()},
			getImportParameterMap());

		Layout importedLayout1 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout1.getUuid(), importedGroup.getGroupId(), false);

		LayoutStructure importedLayoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				importedLayout1.getPlid(),
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(
						importedLayout1.getPlid()));

		List<FormStyledLayoutStructureItem> formStyledLayoutStructureItems =
			importedLayoutStructure.getFormStyledLayoutStructureItems();

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			formStyledLayoutStructureItems.get(0);

		JSONObject successMessageJSONObject =
			formStyledLayoutStructureItem.getSuccessMessageJSONObject();

		JSONObject layoutJSONObject = successMessageJSONObject.getJSONObject(
			"layout");

		Assert.assertEquals(
			importedGroup.getGroupId(), layoutJSONObject.getLong("groupId"));

		Layout importedLayout2 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout2.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertEquals(
			importedLayout2.getLayoutId(),
			layoutJSONObject.getLong("layoutId"));
	}

	@Test
	@TestInfo("LPD-67912")
	public void testFormContainerSuccessMessageWithPageFromGuestSite()
		throws Exception {

		Layout layout1 = LayoutTestUtil.addTypeContentLayout(group);

		Group guestGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		Layout layout2 = LayoutTestUtil.addTypeContentLayout(guestGroup);

		ContentLayoutTestUtil.addItemToLayout(
			JSONUtil.put(
				"classNameId", "0"
			).put(
				"classTypeId", "0"
			).put(
				"successMessage",
				JSONUtil.put(
					"layout",
					JSONUtil.put(
						"groupId", layout2.getGroupId()
					).put(
						"layoutId", layout2.getLayoutId()
					).put(
						"layoutUuid", layout2.getUuid()
					).put(
						"privateLayout", layout2.isPrivateLayout()
					))
			).toString(),
			LayoutDataItemTypeConstants.TYPE_FORM, layout1,
			_layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout1.getPlid()));

		exportImportLayouts(
			new long[] {layout1.getLayoutId()}, getImportParameterMap());

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout1.getUuid(), importedGroup.getGroupId(), false);

		LayoutStructure importedLayoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				importedLayout.getPlid(),
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(importedLayout.getPlid()));

		List<FormStyledLayoutStructureItem> formStyledLayoutStructureItems =
			importedLayoutStructure.getFormStyledLayoutStructureItems();

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			formStyledLayoutStructureItems.get(0);

		JSONObject successMessageJSONObject =
			formStyledLayoutStructureItem.getSuccessMessageJSONObject();

		JSONObject layoutJSONObject = successMessageJSONObject.getJSONObject(
			"layout");

		Assert.assertEquals(
			layout2.getGroupId(), layoutJSONObject.getLong("groupId"));
		Assert.assertEquals(
			layout2.getLayoutId(), layoutJSONObject.getLong("layoutId"));
	}

	private byte[] _read(String fileName) throws Exception {
		return FileUtil.getBytes(
			LayoutPageTemplateStructureRelExportImportTest.class, fileName);
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Language _language;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}