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
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureServiceUtil;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
	@TestInfo("LPD-72839")
	public void testContainer() throws Exception {
		layout = LayoutTestUtil.addTypeContentLayout(group);

		FileEntry fileEntry1 = _addFileEntry(group);

		JSONObject jsonObject = ContentLayoutTestUtil.addItemToLayout(
			JSONUtil.put(
				"link",
				_createMappedLinkJSONObject(
					fileEntry1.getFileEntryId(), null, null)
			).put(
				"styles",
				JSONUtil.put(
					"backgroundImage",
					_createBackgroundImageJSONObject(
						fileEntry1.getFileEntryId(), null, null))
			).toString(),
			LayoutDataItemTypeConstants.TYPE_CONTAINER, layout,
			_layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		String itemId = jsonObject.getString("addedItemId");

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(), false);

		FileEntry importedFileEntry =
			_dlAppLocalService.getFileEntryByUuidAndGroupId(
				fileEntry1.getUuid(), importedGroup.getGroupId());

		_assertContainerConfig(
			importedFileEntry.getFileEntryId(), null,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		String externalReferenceCode = RandomTestUtil.randomString();

		_updateLayoutStructureItem(
			jsonObject1 -> {
				jsonObject1.put(
					"link",
					_createMappedLinkJSONObject(
						0, externalReferenceCode, null));

				JSONObject stylesJSONObject = jsonObject1.getJSONObject(
					"styles");

				stylesJSONObject.put(
					"backgroundImage",
					_createBackgroundImageJSONObject(
						0, externalReferenceCode, null));

				return jsonObject1;
			},
			itemId, layout);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertContainerConfig(
			0, externalReferenceCode,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		FileEntry fileEntry2 = _addFileEntry(group);

		_updateLayoutStructureItem(
			jsonObject1 -> {
				jsonObject1.put(
					"link",
					_createMappedLinkJSONObject(
						0, fileEntry2.getExternalReferenceCode(), null));

				JSONObject stylesJSONObject = jsonObject1.getJSONObject(
					"styles");

				stylesJSONObject.put(
					"backgroundImage",
					_createBackgroundImageJSONObject(
						0, fileEntry2.getExternalReferenceCode(), null));

				return jsonObject1;
			},
			itemId, layout);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedFileEntry = _dlAppLocalService.getFileEntryByUuidAndGroupId(
			fileEntry2.getUuid(), importedGroup.getGroupId());

		_assertContainerConfig(
			0, importedFileEntry.getExternalReferenceCode(),
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		Group guestGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		FileEntry fileEntry3 = _addFileEntry(guestGroup);

		_updateLayoutStructureItem(
			jsonObject1 -> {
				jsonObject1.put(
					"link",
					_createMappedLinkJSONObject(
						fileEntry3.getFileEntryId(), null, null));

				JSONObject stylesJSONObject = jsonObject1.getJSONObject(
					"styles");

				stylesJSONObject.put(
					"backgroundImage",
					_createBackgroundImageJSONObject(
						fileEntry3.getFileEntryId(), null, null));

				return jsonObject1;
			},
			itemId, layout);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertContainerConfig(
			fileEntry3.getFileEntryId(), null,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		_updateLayoutStructureItem(
			jsonObject1 -> {
				jsonObject1.put(
					"link",
					_createMappedLinkJSONObject(
						0, fileEntry3.getExternalReferenceCode(),
						guestGroup.getExternalReferenceCode()));

				JSONObject stylesJSONObject = jsonObject1.getJSONObject(
					"styles");

				stylesJSONObject.put(
					"backgroundImage",
					_createBackgroundImageJSONObject(
						0, fileEntry3.getExternalReferenceCode(),
						guestGroup.getExternalReferenceCode()));

				return jsonObject1;
			},
			itemId, layout);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertContainerConfig(
			0, fileEntry3.getExternalReferenceCode(),
			_getLayoutStructureItem(itemId, importedLayout.getPlid()),
			guestGroup.getExternalReferenceCode());
	}

	@Test
	@TestInfo({"LPD-67912", "LPD-72839"})
	public void testFormContainerSuccessMessageWithMappedLayout()
		throws Exception {

		layout = LayoutTestUtil.addTypeContentLayout(group);

		Layout layout2 = LayoutTestUtil.addTypeContentLayout(group);

		JSONObject jsonObject = ContentLayoutTestUtil.addItemToLayout(
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
			LayoutDataItemTypeConstants.TYPE_FORM, layout,
			_layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		String itemId = jsonObject.getString("addedItemId");

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		Layout importedLayout1 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(), false);

		Layout importedLayout2 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout2.getUuid(), importedGroup.getGroupId(), false);

		_assertFormContainerSuccessMessage(
			null, importedGroup.getGroupId(), importedLayout2.getLayoutId(),
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()), null);

		Group guestGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		Layout layout3 = LayoutTestUtil.addTypeContentLayout(guestGroup);

		_updateLayoutStructureItem(
			jsonObject1 -> {
				JSONObject successMessageJSONObject = jsonObject1.getJSONObject(
					"successMessage");

				successMessageJSONObject.put(
					"layout",
					_createLayoutJSONObject(
						null, guestGroup.getGroupId(), layout3.getLayoutId(),
						layout3.getUuid(), layout3.isPrivateLayout(), null));

				return jsonObject1;
			},
			itemId, layout);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedLayout1 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(), false);

		_assertFormContainerSuccessMessage(
			null, guestGroup.getGroupId(), layout3.getLayoutId(),
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()), null);

		String externalReferenceCode = RandomTestUtil.randomString();

		_updateLayoutStructureItem(
			jsonObject1 -> {
				JSONObject successMessageJSONObject = jsonObject1.getJSONObject(
					"successMessage");

				successMessageJSONObject.put(
					"layout",
					_createLayoutJSONObject(
						externalReferenceCode, 0, 0, null, null, null));

				return jsonObject1;
			},
			itemId, layout);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertFormContainerSuccessMessage(
			externalReferenceCode, 0, 0,
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()), null);

		Layout layout4 = LayoutTestUtil.addTypeContentLayout(group);

		_updateLayoutStructureItem(
			jsonObject1 -> {
				JSONObject successMessageJSONObject = jsonObject1.getJSONObject(
					"successMessage");

				successMessageJSONObject.put(
					"layout",
					_createLayoutJSONObject(
						layout4.getExternalReferenceCode(), 0, 0, null, null,
						null));

				return jsonObject1;
			},
			itemId, layout);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedLayout2 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout4.getUuid(), importedGroup.getGroupId(), false);

		_assertFormContainerSuccessMessage(
			importedLayout2.getExternalReferenceCode(), 0, 0,
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()), null);

		Layout layout5 = LayoutTestUtil.addTypeContentLayout(guestGroup);

		_updateLayoutStructureItem(
			jsonObject1 -> {
				JSONObject successMessageJSONObject = jsonObject1.getJSONObject(
					"successMessage");

				successMessageJSONObject.put(
					"layout",
					_createLayoutJSONObject(
						layout5.getExternalReferenceCode(), 0, 0, null, null,
						guestGroup.getExternalReferenceCode()));

				return jsonObject1;
			},
			itemId, layout);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertFormContainerSuccessMessage(
			layout5.getExternalReferenceCode(), 0, 0,
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()),
			guestGroup.getExternalReferenceCode());
	}

	@Test
	@TestInfo("LPD-67912")
	public void testFormContainerSuccessMessageWithMappedLayoutWithScope()
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

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> importParameterMap =
			super.getImportParameterMap();

		importParameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});

		importParameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.FALSE.toString()});

		return importParameterMap;
	}

	private FileEntry _addFileEntry(Group group) throws Exception {
		return _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".png", ContentTypes.IMAGE_PNG,
			_read("dependencies/sample.png"), null, null, null,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private void _assertContainerConfig(
		long classPK, String externalReferenceCode,
		LayoutStructureItem layoutStructureItem,
		String scopeExternalReferenceCode) {

		JSONObject itemConfigJSONObject =
			layoutStructureItem.getItemConfigJSONObject();

		JSONObject linkJSONObject = itemConfigJSONObject.getJSONObject("link");

		_assertMappedField(
			classPK, externalReferenceCode, linkJSONObject,
			scopeExternalReferenceCode);

		JSONObject stylesJSONObject = itemConfigJSONObject.getJSONObject(
			"styles");

		JSONObject backgroundImageJSONObject = stylesJSONObject.getJSONObject(
			"backgroundImage");

		_assertMappedField(
			classPK, externalReferenceCode, backgroundImageJSONObject,
			scopeExternalReferenceCode);
	}

	private void _assertFormContainerSuccessMessage(
		String externalReferenceCode, long groupId, long layoutId,
		LayoutStructureItem layoutStructureItem,
		String scopeExternalReferenceCode) {

		JSONObject itemConfigJSONObject =
			layoutStructureItem.getItemConfigJSONObject();

		JSONObject successMessageJSONObject =
			itemConfigJSONObject.getJSONObject("successMessage");

		JSONObject layoutJSONObject = successMessageJSONObject.getJSONObject(
			"layout");

		if (groupId > 0) {
			Assert.assertEquals(groupId, layoutJSONObject.getLong("groupId"));
		}

		if (Validator.isNotNull(externalReferenceCode)) {
			Assert.assertEquals(
				externalReferenceCode,
				layoutJSONObject.getString("externalReferenceCode"));
		}

		if (layoutId > 0) {
			Assert.assertEquals(layoutId, layoutJSONObject.getLong("layoutId"));
		}

		if (Validator.isNotNull(scopeExternalReferenceCode)) {
			Assert.assertEquals(
				scopeExternalReferenceCode,
				layoutJSONObject.getString("scopeExternalReferenceCode"));
		}
	}

	private void _assertMappedField(
		long classPK, String externalReferenceCode, JSONObject jsonObject,
		String scopeExternalReferenceCode) {

		if (classPK > 0) {
			Assert.assertEquals(classPK, jsonObject.getLong("classPK"));
		}

		if (Validator.isNotNull(externalReferenceCode)) {
			Assert.assertEquals(
				externalReferenceCode,
				jsonObject.getString("externalReferenceCode"));
		}

		if (Validator.isNotNull(scopeExternalReferenceCode)) {
			Assert.assertEquals(
				jsonObject.getString("scopeExternalReferenceCode"),
				jsonObject.getString("scopeExternalReferenceCode"));
		}
	}

	private JSONObject _createBackgroundImageJSONObject(
		long classPK, String externalReferenceCode,
		String scopeExternalReferenceCode) {

		JSONObject jsonObject = JSONUtil.put(
			"className", FileEntry.class.getName()
		).put(
			"classNameId", _portal.getClassNameId(FileEntry.class)
		).put(
			"classTypeId",
			String.valueOf(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
		).put(
			"itemSubtype",
			_language.get(
				LocaleUtil.ENGLISH,
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT)
		).put(
			"itemType", "Document"
		).put(
			"type",
			"com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType"
		);

		if (classPK > 0) {
			jsonObject.put("classPK", classPK);
		}

		if (Validator.isNotNull(externalReferenceCode)) {
			jsonObject.put("externalReferenceCode", externalReferenceCode);
		}

		if (Validator.isNotNull(scopeExternalReferenceCode)) {
			jsonObject.put(
				"scopeExternalReferenceCode", scopeExternalReferenceCode);
		}

		return jsonObject;
	}

	private JSONObject _createLayoutJSONObject(
		String externalReferenceCode, long groupId, long layoutId,
		String layoutUuid, Boolean privateLayout,
		String scopeExternalReferenceCode) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(externalReferenceCode)) {
			jsonObject.put("externalReferenceCode", externalReferenceCode);
		}

		if ((groupId > 0) && (layoutId > 0) &&
			Validator.isNotNull(layoutUuid) && (privateLayout != null)) {

			jsonObject.put(
				"groupId", groupId
			).put(
				"layoutId", layoutId
			).put(
				"layoutUuid", layoutUuid
			).put(
				"privateLayout", privateLayout
			);
		}

		if (Validator.isNotNull(scopeExternalReferenceCode)) {
			jsonObject.put(
				"scopeExternalReferenceCode", scopeExternalReferenceCode);
		}

		return jsonObject;
	}

	private JSONObject _createMappedLinkJSONObject(
		long classPK, String externalReferenceCode,
		String scopeExternalReferenceCode) {

		JSONObject jsonObject = _createBackgroundImageJSONObject(
			classPK, externalReferenceCode, scopeExternalReferenceCode);

		return jsonObject.put("fieldId", "FileEntry_title");
	}

	private LayoutStructureItem _getLayoutStructureItem(
		String itemId, long plid) {

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				plid);

		LayoutStructure layoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				plid, segmentsExperienceId);

		return layoutStructure.getLayoutStructureItem(itemId);
	}

	private byte[] _read(String fileName) throws Exception {
		return FileUtil.getBytes(
			LayoutPageTemplateStructureRelExportImportTest.class, fileName);
	}

	private void _updateLayoutStructureItem(
			Function<JSONObject, JSONObject> function, String itemId,
			Layout layout)
		throws Exception {

		try {
			ServiceContextThreadLocal.pushServiceContext(
				ServiceContextTestUtil.getServiceContext(layout.getGroupId()));

			long segmentsExperienceId =
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid());

			LayoutStructure layoutStructure =
				_layoutStructureProvider.getLayoutStructure(
					layout.getPlid(), segmentsExperienceId);

			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItem(itemId);

			layoutStructureItem.updateItemConfig(
				function.apply(layoutStructureItem.getItemConfigJSONObject()));

			LayoutPageTemplateStructureServiceUtil.
				updateLayoutPageTemplateStructureData(
					layout.getGroupId(), layout.getPlid(), segmentsExperienceId,
					layoutStructure.toString());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
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