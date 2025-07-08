/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.definition.setting.builder.ObjectDefinitionSettingBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marco Galluzzi
 */
public abstract class BaseSectionDisplayContextTestCase
	extends BaseDisplayContextTestCase {

	@Test
	@TestInfo("LPD-50664")
	public void testGetCreationMenu() throws Exception {
		Map<String, String> expectedResultMap = getExpectedCreationMenuItems();

		_testGetCreationMenu(getCreationMenu(), expectedResultMap);

		ObjectFolder objectFolder = null;

		for (String objectFolderExternalReferenceCode :
				getObjectFolderExternalReferenceCodes()) {

			objectFolder =
				objectFolderLocalService.getObjectFolderByExternalReferenceCode(
					objectFolderExternalReferenceCode,
					TestPropsValues.getCompanyId());

			ObjectDefinition objectDefinition = addCustomObjectDefinition(
				objectFolder.getObjectFolderId(), true, true,
				ObjectDefinitionConstants.SCOPE_DEPOT,
				WorkflowConstants.STATUS_APPROVED);

			expectedResultMap.put(
				objectDefinition.getLabel(LocaleUtil.US),
				getRedirect(
					objectDefinition,
					_getRootObjectEntryFolderExternalReferenceCode(
						objectFolderExternalReferenceCode)));
		}

		addCustomObjectDefinition(
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			false, true, ObjectDefinitionConstants.SCOPE_DEPOT,
			WorkflowConstants.STATUS_APPROVED);

		addCustomObjectDefinition(
			objectFolder.getObjectFolderId(), true, true,
			ObjectDefinitionConstants.SCOPE_COMPANY,
			WorkflowConstants.STATUS_APPROVED);
		addCustomObjectDefinition(
			objectFolder.getObjectFolderId(), false, true,
			ObjectDefinitionConstants.SCOPE_DEPOT,
			WorkflowConstants.STATUS_APPROVED);
		addCustomObjectDefinition(
			objectFolder.getObjectFolderId(), true, false,
			ObjectDefinitionConstants.SCOPE_DEPOT,
			WorkflowConstants.STATUS_APPROVED);
		addCustomObjectDefinition(
			objectFolder.getObjectFolderId(), true, true,
			ObjectDefinitionConstants.SCOPE_DEPOT,
			WorkflowConstants.STATUS_DRAFT);
		addCustomObjectDefinition(
			objectFolder.getObjectFolderId(), true, true,
			ObjectDefinitionConstants.SCOPE_SITE,
			WorkflowConstants.STATUS_APPROVED);

		_testGetCreationMenu(getCreationMenu(), expectedResultMap);
	}

	@Test
	@TestInfo("LPD-57827")
	public void testGetDepotEntriesJSONArrayWithMultipleDepotEntries()
		throws Exception {

		String depotName = StringUtil.randomString();

		DepotEntry depotEntry = _addDepotEntry(depotName);

		try {
			List<DepotEntry> depotEntries =
				_depotEntryLocalService.getDepotEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Assert.assertEquals(
				depotEntries.toString(), 2, depotEntries.size());

			DepotEntry defaultDepotEntry = depotEntries.get(0);

			Group defaultDepotGroup = _groupLocalService.fetchGroup(
				defaultDepotEntry.getGroupId());

			Assert.assertEquals("Default", defaultDepotGroup.getGroupKey());

			Group depotGroup = _groupLocalService.fetchGroup(
				depotEntry.getGroupId());

			Assert.assertEquals(depotName, depotGroup.getGroupKey());

			_testGetDepotEntriesJSONArray(depotEntries, null, null);
			_testGetDepotEntriesJSONArray(
				List.of(depotEntry), null,
				String.valueOf(depotGroup.getGroupId()));
			_testGetDepotEntriesJSONArray(
				List.of(defaultDepotEntry), null,
				String.valueOf(defaultDepotGroup.getGroupId()));

			if (getRootObjectEntryFolderExternalReferenceCode() != null) {
				ObjectEntryFolder objectEntryFolder = _addObjectFolderEntry(
					depotGroup);

				_testGetDepotEntriesJSONArray(
					List.of(depotEntry), objectEntryFolder, null);
				_testGetDepotEntriesJSONArray(
					List.of(depotEntry), objectEntryFolder,
					String.valueOf(depotGroup.getGroupId()));
				_testGetDepotEntriesJSONArray(
					null, objectEntryFolder,
					String.valueOf(defaultDepotGroup.getGroupId()));
			}
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);
		}
	}

	@Test
	@TestInfo("LPD-57827")
	public void testGetDepotEntriesJSONArrayWithOneDepotEntryOnly()
		throws Exception {

		List<DepotEntry> depotEntries = _depotEntryLocalService.getDepotEntries(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(depotEntries.toString(), 1, depotEntries.size());

		DepotEntry depotEntry = depotEntries.get(0);

		Group depotGroup = _groupLocalService.fetchGroup(
			depotEntry.getGroupId());

		Assert.assertEquals("Default", depotGroup.getGroupKey());

		_testGetDepotEntriesJSONArray(depotEntries, null, null);
		_testGetDepotEntriesJSONArray(
			depotEntries, null, String.valueOf(depotGroup.getGroupId()));

		if (getRootObjectEntryFolderExternalReferenceCode() != null) {
			ObjectEntryFolder objectEntryFolder = _addObjectFolderEntry(
				depotGroup);

			_testGetDepotEntriesJSONArray(
				List.of(depotEntry), objectEntryFolder, null);
			_testGetDepotEntriesJSONArray(
				List.of(depotEntry), objectEntryFolder,
				String.valueOf(depotGroup.getGroupId()));
		}
	}

	protected ObjectDefinition addCustomObjectDefinition(
			String objectDefinitionSettingName,
			String objectDefinitionSettingValue)
		throws Exception {

		ObjectFolder objectFolder =
			objectFolderLocalService.getObjectFolderByExternalReferenceCode(
				getObjectFolderExternalReferenceCode(),
				TestPropsValues.getCompanyId());

		return addCustomObjectDefinition(
			objectFolder.getObjectFolderId(), true, true,
			Collections.singletonList(
				new ObjectDefinitionSettingBuilder(
				).name(
					objectDefinitionSettingName
				).value(
					objectDefinitionSettingValue
				).build()),
			ObjectDefinitionConstants.SCOPE_DEPOT,
			WorkflowConstants.STATUS_APPROVED);
	}

	protected CreationMenu getCreationMenu() throws Exception {
		return getCreationMenu(null);
	}

	protected CreationMenu getCreationMenu(ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return ReflectionTestUtil.invoke(
			getSectionDisplayContext(
				getMockHttpServletRequest(objectEntryFolder)),
			"getCreationMenu", new Class<?>[0]);
	}

	protected abstract Map<String, String> getExpectedCreationMenuItems()
		throws PortalException;

	protected abstract String getObjectFolderExternalReferenceCode();

	protected List<String> getObjectFolderExternalReferenceCodes() {
		return List.of(getObjectFolderExternalReferenceCode());
	}

	protected String getRedirect(
		ObjectDefinition objectDefinition,
		String objectEntryFolderExternalReferenceCode) {

		StringBundler sb = new StringBundler(5);

		sb.append("/cms/add_structured_content_item?objectDefinitionId=");
		sb.append(objectDefinition.getObjectDefinitionId());
		sb.append("&objectEntryFolderExternalReferenceCode=");
		sb.append(objectEntryFolderExternalReferenceCode);
		sb.append("&plid=0&redirect=http://localhost:8080/currentURL");

		return sb.toString();
	}

	protected String getRedirect(String objectDefinitionExternalReferenceCode)
		throws PortalException {

		return getRedirect(
			objectDefinitionExternalReferenceCode,
			getRootObjectEntryFolderExternalReferenceCode());
	}

	protected String getRedirect(
			String objectDefinitionExternalReferenceCode,
			String rootObjectEntryFolderExternalReferenceCode)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode,
					TestPropsValues.getCompanyId());

		return getRedirect(
			objectDefinition, rootObjectEntryFolderExternalReferenceCode);
	}

	protected String getRootObjectEntryFolderExternalReferenceCode() {
		return null;
	}

	protected abstract Object getSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception;

	private DepotEntry _addDepotEntry(String name) throws Exception {
		return _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), name
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private ObjectEntryFolder _addObjectFolderEntry(Group group)
		throws Exception {

		ObjectEntryFolder rootObjectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					getRootObjectEntryFolderExternalReferenceCode(),
					group.getGroupId(), group.getCompanyId());

		return _objectEntryFolderLocalService.addObjectEntryFolder(
			StringUtil.randomString(), group.getGroupId(),
			TestPropsValues.getUserId(),
			rootObjectEntryFolder.getObjectEntryFolderId(),
			RandomTestUtil.randomString(), null, StringUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private void _assertCreationMenuContainsDropdownItem(
		CreationMenu creationMenu, JSONArray expectedAssetLibrariesJSONArray,
		String expectedLabel) {

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertFalse(dropdownItems.toString(), dropdownItems.isEmpty());

		HashMap<String, Object> dropdownItemData = null;

		for (DropdownItem dropdownItem : dropdownItems) {
			if (Objects.equals(dropdownItem.get("label"), expectedLabel)) {
				dropdownItemData = (HashMap<String, Object>)dropdownItem.get(
					"data");

				break;
			}
		}

		Assert.assertNotNull(
			String.format(
				"Creation menu does not contain label '%s': %s", expectedLabel,
				dropdownItems),
			dropdownItemData);

		JSONArray assetLibrariesJSONArray = (JSONArray)dropdownItemData.get(
			"assetLibraries");

		Assert.assertNotNull(assetLibrariesJSONArray);

		Assert.assertEquals(
			assetLibrariesJSONArray.toString(),
			expectedAssetLibrariesJSONArray.length(),
			assetLibrariesJSONArray.length());

		Assert.assertTrue(
			assetLibrariesJSONArray.toString(),
			JSONUtil.equals(
				expectedAssetLibrariesJSONArray, assetLibrariesJSONArray));
	}

	private void _assertCreationMenuNotContainsDropdownItem(
		CreationMenu creationMenu, String notExpectedLabel) {

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertFalse(dropdownItems.toString(), dropdownItems.isEmpty());

		HashMap<String, Object> dropdownItemData = null;

		for (DropdownItem dropdownItem : dropdownItems) {
			if (Objects.equals(dropdownItem.get("label"), notExpectedLabel)) {
				dropdownItemData = (HashMap<String, Object>)dropdownItem.get(
					"data");

				break;
			}
		}

		Assert.assertNull(
			String.format(
				"Creation menu contains label '%s': %s", notExpectedLabel,
				dropdownItems),
			dropdownItemData);
	}

	private DropdownItem _get(List<DropdownItem> dropdownItems, String label) {
		for (DropdownItem dropdownItem : dropdownItems) {
			if (label.equals(dropdownItem.get("label"))) {
				return dropdownItem;
			}
		}

		return null;
	}

	private JSONArray _getJSONArray(List<DepotEntry> depotEntries) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DepotEntry depotEntry : depotEntries) {
			Group group = _groupLocalService.fetchGroup(
				depotEntry.getGroupId());

			if (group != null) {
				jsonArray.put(
					JSONUtil.put(
						"groupId", group.getGroupId()
					).put(
						"name", group.getName(LocaleUtil.getDefault())
					));
			}
		}

		return jsonArray;
	}

	private String _getRedirect(DropdownItem dropdownItem) {
		Map<String, Object> map = (HashMap<String, Object>)dropdownItem.get(
			"data");

		if (map == null) {
			return null;
		}

		return (String)map.get("redirect");
	}

	private String _getRootObjectEntryFolderExternalReferenceCode(
		String objectFolderExternalReferenceCode) {

		if (Objects.equals(
				objectFolderExternalReferenceCode,
				ObjectFolderConstants.
					EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES)) {

			return ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS;
		}

		return ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_FILES;
	}

	private void _testGetCreationMenu(
		CreationMenu creationMenu, Map<String, String> expectedResultMap) {

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertEquals(
			dropdownItems.toString(), expectedResultMap.size(),
			dropdownItems.size());

		for (Map.Entry<String, String> entry : expectedResultMap.entrySet()) {
			DropdownItem dropdownItem = _get(dropdownItems, entry.getKey());

			Assert.assertNotNull(
				"Not found DropdownItem with label " + entry.getKey(),
				dropdownItem);

			if (Validator.isNull(entry.getValue())) {
				Assert.assertNull(_getRedirect(dropdownItem));
			}
			else {
				Assert.assertEquals(
					entry.getValue(), _getRedirect(dropdownItem));
			}
		}
	}

	private void _testGetDepotEntriesJSONArray(
			List<DepotEntry> depotEntries, ObjectEntryFolder objectEntryFolder,
			String acceptedGroupIds)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		if (acceptedGroupIds != null) {
			objectDefinition = addCustomObjectDefinition(
				ObjectDefinitionSettingConstants.NAME_ACCEPTED_GROUP_IDS,
				acceptedGroupIds);
		}
		else {
			objectDefinition = addCustomObjectDefinition(
				ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS,
				StringPool.TRUE);
		}

		try {
			CreationMenu creationMenu = getCreationMenu(objectEntryFolder);

			if (depotEntries != null) {
				_assertCreationMenuContainsDropdownItem(
					creationMenu, _getJSONArray(depotEntries),
					objectDefinition.getLabel(LocaleUtil.getDefault()));
			}
			else {
				_assertCreationMenuNotContainsDropdownItem(
					creationMenu,
					objectDefinition.getLabel(LocaleUtil.getDefault()));
			}
		}
		finally {
			objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}