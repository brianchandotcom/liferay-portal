/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureServiceUtil;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
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
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Map;

import org.junit.After;
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

		layout = LayoutTestUtil.addTypeContentLayout(group);

		_segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	@TestInfo("LPD-72839")
	public void testCollectionDisplay() throws Exception {
		AssetListEntry assetListEntry1 = _addAssetList(group);

		String itemId = ContentLayoutTestUtil.addCollectionDisplayToLayout(
			_getCollectionJSONObject(
				assetListEntry1.getAssetListEntryId(), null, null),
			layout, _layoutStructureProvider, null, null, 0,
			_segmentsExperienceId);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		AssetListEntry importedAssetListEntry =
			_assetListEntryLocalService.getAssetListEntryByUuidAndGroupId(
				assetListEntry1.getUuid(), importedGroup.getGroupId());

		importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(), false);

		_assertCollectionConfig(
			importedAssetListEntry.getAssetListEntryId(), null,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		Group guestGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		AssetListEntry assetListEntry2 = _addAssetList(guestGroup);

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> itemConfigJSONObject.put(
				"collection",
				_getCollectionJSONObject(
					assetListEntry2.getAssetListEntryId(), null, null)));

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertCollectionConfig(
			assetListEntry2.getAssetListEntryId(), null,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		String externalReferenceCode = RandomTestUtil.randomString();

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> itemConfigJSONObject.put(
				"collection",
				_getCollectionJSONObject(0, externalReferenceCode, null)));

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertCollectionConfig(
			0, externalReferenceCode,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		AssetListEntry assetListEntry3 = _addAssetList(group);

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> itemConfigJSONObject.put(
				"collection",
				_getCollectionJSONObject(
					0, assetListEntry3.getExternalReferenceCode(), null)));

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedAssetListEntry =
			_assetListEntryLocalService.getAssetListEntryByUuidAndGroupId(
				assetListEntry3.getUuid(), importedGroup.getGroupId());

		_assertCollectionConfig(
			0, importedAssetListEntry.getExternalReferenceCode(),
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		AssetListEntry assetListEntry4 = _addAssetList(guestGroup);

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> itemConfigJSONObject.put(
				"collection",
				_getCollectionJSONObject(
					0, assetListEntry4.getExternalReferenceCode(),
					guestGroup.getExternalReferenceCode())));

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertCollectionConfig(
			0, assetListEntry4.getExternalReferenceCode(),
			_getLayoutStructureItem(itemId, importedLayout.getPlid()),
			guestGroup.getExternalReferenceCode());
	}

	@Test
	@TestInfo("LPD-72839")
	public void testContainer() throws Exception {
		FileEntry fileEntry1 = _addFileEntry(_serviceContext);

		JSONObject jsonObject = ContentLayoutTestUtil.addItemToLayout(
			JSONUtil.put(
				"link",
				_getFileEntryJSONObject(
					fileEntry1.getFileEntryId(), null, null, "FileEntry_title")
			).put(
				"styles",
				JSONUtil.put(
					"backgroundImage",
					_getFileEntryJSONObject(
						fileEntry1.getFileEntryId(), null, null, null))
			).toString(),
			LayoutDataItemTypeConstants.TYPE_CONTAINER, layout,
			_layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		String itemId = jsonObject.getString("addedItemId");

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(), false);

		FileEntry importedFileEntry =
			_dlAppLocalService.getFileEntryByUuidAndGroupId(
				fileEntry1.getUuid(), importedGroup.getGroupId());

		_assertContainerConfig(
			importedFileEntry.getFileEntryId(), null,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		String externalReferenceCode = RandomTestUtil.randomString();

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> {
				itemConfigJSONObject.put(
					"link",
					_getFileEntryJSONObject(
						0, externalReferenceCode, null, "FileEntry_title"));

				JSONObject stylesJSONObject =
					itemConfigJSONObject.getJSONObject("styles");

				stylesJSONObject.put(
					"backgroundImage",
					_getFileEntryJSONObject(
						0, externalReferenceCode, null, null));

				return itemConfigJSONObject;
			});

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertContainerConfig(
			0, externalReferenceCode,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		FileEntry fileEntry2 = _addFileEntry(_serviceContext);

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> {
				itemConfigJSONObject.put(
					"link",
					_getFileEntryJSONObject(
						0, fileEntry2.getExternalReferenceCode(), null,
						"FileEntry_title"));

				JSONObject stylesJSONObject =
					itemConfigJSONObject.getJSONObject("styles");

				stylesJSONObject.put(
					"backgroundImage",
					_getFileEntryJSONObject(
						0, fileEntry2.getExternalReferenceCode(), null, null));

				return itemConfigJSONObject;
			});

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedFileEntry = _dlAppLocalService.getFileEntryByUuidAndGroupId(
			fileEntry2.getUuid(), importedGroup.getGroupId());

		_assertContainerConfig(
			0, importedFileEntry.getExternalReferenceCode(),
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		Group guestGroup = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		FileEntry fileEntry3 = _addFileEntry(
			ServiceContextTestUtil.getServiceContext(guestGroup.getGroupId()));

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> {
				itemConfigJSONObject.put(
					"link",
					_getFileEntryJSONObject(
						fileEntry3.getFileEntryId(), null, null,
						"FileEntry_title"));

				JSONObject stylesJSONObject =
					itemConfigJSONObject.getJSONObject("styles");

				stylesJSONObject.put(
					"backgroundImage",
					_getFileEntryJSONObject(
						fileEntry3.getFileEntryId(), null, null, null));

				return itemConfigJSONObject;
			});

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertContainerConfig(
			fileEntry3.getFileEntryId(), null,
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> {
				itemConfigJSONObject.put(
					"link",
					_getFileEntryJSONObject(
						0, fileEntry3.getExternalReferenceCode(),
						guestGroup.getExternalReferenceCode(),
						"FileEntry_title"));

				JSONObject stylesJSONObject =
					itemConfigJSONObject.getJSONObject("styles");

				stylesJSONObject.put(
					"backgroundImage",
					_getFileEntryJSONObject(
						0, fileEntry3.getExternalReferenceCode(),
						guestGroup.getExternalReferenceCode(), null));

				return itemConfigJSONObject;
			});

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertContainerConfig(
			0, fileEntry3.getExternalReferenceCode(),
			_getLayoutStructureItem(itemId, importedLayout.getPlid()),
			guestGroup.getExternalReferenceCode());

		Layout layout1 = LayoutTestUtil.addTypeContentLayout(group);

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> itemConfigJSONObject.put(
				"link",
				JSONUtil.put(
					"layout",
					_getLayoutJSONObject(
						null, group.getGroupId(), layout1.getLayoutId(),
						layout1.getUuid(), layout1.isPrivateLayout(), null))));

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		Layout importedLayout1 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout1.getUuid(), importedGroup.getGroupId(), false);

		_assertContainerLinkMappedLayout(
			null, importedGroup.getGroupId(), importedLayout1.getLayoutId(),
			_getLayoutStructureItem(itemId, importedLayout.getPlid()), null);
	}

	@Test
	@TestInfo({"LPD-67912", "LPD-72839"})
	public void testFormContainerSuccessMessageWithMappedLayout()
		throws Exception {

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
					_getLayoutJSONObject(
						null, layout2.getGroupId(), layout2.getLayoutId(),
						layout2.getUuid(), layout2.isPrivateLayout(), null))
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
			itemId,
			itemConfigJSONObject -> {
				JSONObject successMessageJSONObject =
					itemConfigJSONObject.getJSONObject("successMessage");

				successMessageJSONObject.put(
					"layout",
					_getLayoutJSONObject(
						null, guestGroup.getGroupId(), layout3.getLayoutId(),
						layout3.getUuid(), layout3.isPrivateLayout(), null));

				return itemConfigJSONObject;
			});

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedLayout1 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(), false);

		_assertFormContainerSuccessMessage(
			null, guestGroup.getGroupId(), layout3.getLayoutId(),
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()), null);

		String externalReferenceCode = RandomTestUtil.randomString();

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> {
				JSONObject successMessageJSONObject =
					itemConfigJSONObject.getJSONObject("successMessage");

				successMessageJSONObject.put(
					"layout",
					_getLayoutJSONObject(
						externalReferenceCode, 0, 0, null, null, null));

				return itemConfigJSONObject;
			});

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertFormContainerSuccessMessage(
			externalReferenceCode, 0, 0,
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()), null);

		Layout layout4 = LayoutTestUtil.addTypeContentLayout(group);

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> {
				JSONObject successMessageJSONObject =
					itemConfigJSONObject.getJSONObject("successMessage");

				successMessageJSONObject.put(
					"layout",
					_getLayoutJSONObject(
						layout4.getExternalReferenceCode(), 0, 0, null, null,
						null));

				return itemConfigJSONObject;
			});

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedLayout2 = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout4.getUuid(), importedGroup.getGroupId(), false);

		_assertFormContainerSuccessMessage(
			importedLayout2.getExternalReferenceCode(), 0, 0,
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()), null);

		Layout layout5 = LayoutTestUtil.addTypeContentLayout(guestGroup);

		_updateLayoutStructureItem(
			itemId,
			itemConfigJSONObject -> {
				JSONObject successMessageJSONObject =
					itemConfigJSONObject.getJSONObject("successMessage");

				successMessageJSONObject.put(
					"layout",
					_getLayoutJSONObject(
						layout5.getExternalReferenceCode(), 0, 0, null, null,
						guestGroup.getExternalReferenceCode()));

				return itemConfigJSONObject;
			});

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		_assertFormContainerSuccessMessage(
			layout5.getExternalReferenceCode(), 0, 0,
			_getLayoutStructureItem(itemId, importedLayout1.getPlid()),
			guestGroup.getExternalReferenceCode());
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

	private AssetListEntry _addAssetList(Group group) throws Exception {
		return _assetListEntryLocalService.addAssetListEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_DYNAMIC,
			UnicodePropertiesBuilder.create(
				true
			).put(
				"anyAssetType",
				String.valueOf(_portal.getClassNameId(JournalArticle.class))
			).buildString(),
			_serviceContext);
	}

	private FileEntry _addFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".png", ContentTypes.IMAGE_PNG,
			_read("dependencies/sample.png"), null, null, null, serviceContext);
	}

	private void _assertCollectionConfig(
		long classPK, String externalReferenceCode,
		LayoutStructureItem layoutStructureItem,
		String scopeExternalReferenceCode) {

		JSONObject itemConfigJSONObject =
			layoutStructureItem.getItemConfigJSONObject();

		JSONObject collectionJSONObject = itemConfigJSONObject.getJSONObject(
			"collection");

		_assertMappedField(
			classPK, externalReferenceCode, collectionJSONObject,
			scopeExternalReferenceCode);
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

	private void _assertContainerLinkMappedLayout(
		String externalReferenceCode, long groupId, long layoutId,
		LayoutStructureItem layoutStructureItem,
		String scopeExternalReferenceCode) {

		JSONObject itemConfigJSONObject =
			layoutStructureItem.getItemConfigJSONObject();

		JSONObject linkJSONObject = itemConfigJSONObject.getJSONObject("link");

		if (linkJSONObject.has("layout")) {
			_assertLayoutJSONObject(
				externalReferenceCode, groupId, layoutId,
				scopeExternalReferenceCode,
				linkJSONObject.getJSONObject("layout"));
		}
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

		_assertLayoutJSONObject(
			externalReferenceCode, groupId, layoutId,
			scopeExternalReferenceCode, layoutJSONObject);
	}

	private void _assertLayoutJSONObject(
		String externalReferenceCode, long groupId, long layoutId,
		String scopeExternalReferenceCode, JSONObject layoutJSONObject) {

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
				scopeExternalReferenceCode,
				jsonObject.getString("scopeExternalReferenceCode"));
		}
	}

	private JSONObject _getCollectionJSONObject(
		long classPK, String externalReferenceCode,
		String scopeExternalReferenceCode) {

		return JSONUtil.put(
			"classPK",
			() -> {
				if (classPK <= 0) {
					return null;
				}

				return classPK;
			}
		).put(
			"externalReferenceCode",
			() -> {
				if (Validator.isNull(externalReferenceCode)) {
					return null;
				}

				return externalReferenceCode;
			}
		).put(
			"scopeExternalReferenceCode",
			() -> {
				if (Validator.isNull(scopeExternalReferenceCode)) {
					return null;
				}

				return scopeExternalReferenceCode;
			}
		).put(
			"type",
			"com.liferay.item.selector.criteria.InfoListItemSelectorReturnType"
		);
	}

	private JSONObject _getFileEntryJSONObject(
		long classPK, String externalReferenceCode,
		String scopeExternalReferenceCode, String fieldId) {

		return JSONUtil.put(
			"className", FileEntry.class.getName()
		).put(
			"classNameId", _portal.getClassNameId(FileEntry.class)
		).put(
			"classPK",
			() -> {
				if (classPK <= 0) {
					return null;
				}

				return classPK;
			}
		).put(
			"classTypeId",
			String.valueOf(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
		).put(
			"externalReferenceCode",
			() -> {
				if (Validator.isNull(externalReferenceCode)) {
					return null;
				}

				return externalReferenceCode;
			}
		).put(
			"fieldId", fieldId
		).put(
			"itemSubtype",
			_language.get(
				LocaleUtil.ENGLISH,
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT)
		).put(
			"itemType", "Document"
		).put(
			"scopeExternalReferenceCode",
			() -> {
				if (Validator.isNull(scopeExternalReferenceCode)) {
					return null;
				}

				return scopeExternalReferenceCode;
			}
		).put(
			"type",
			"com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType"
		);
	}

	private JSONObject _getLayoutJSONObject(
		String externalReferenceCode, long groupId, long layoutId,
		String layoutUuid, Boolean privateLayout,
		String scopeExternalReferenceCode) {

		return JSONUtil.put(
			"externalReferenceCode",
			() -> {
				if (Validator.isNull(externalReferenceCode)) {
					return null;
				}

				return externalReferenceCode;
			}
		).put(
			"groupId",
			() -> {
				if (groupId <= 0) {
					return null;
				}

				return groupId;
			}
		).put(
			"layoutId",
			() -> {
				if (layoutId <= 0) {
					return null;
				}

				return layoutId;
			}
		).put(
			"layoutUuid",
			() -> {
				if (Validator.isNull(layoutUuid)) {
					return null;
				}

				return layoutUuid;
			}
		).put(
			"privateLayout", privateLayout
		).put(
			"scopeExternalReferenceCode",
			() -> {
				if (Validator.isNull(scopeExternalReferenceCode)) {
					return null;
				}

				return scopeExternalReferenceCode;
			}
		);
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
			String itemId,
			UnsafeFunction<JSONObject, JSONObject, Exception> unsafeFunction)
		throws Exception {

		LayoutStructure layoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				layout.getPlid(), _segmentsExperienceId);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		layoutStructureItem.updateItemConfig(
			unsafeFunction.apply(
				layoutStructureItem.getItemConfigJSONObject()));

		LayoutPageTemplateStructureServiceUtil.
			updateLayoutPageTemplateStructureData(
				layout.getGroupId(), layout.getPlid(), _segmentsExperienceId,
				layoutStructure.toString());
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

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

	private long _segmentsExperienceId;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}