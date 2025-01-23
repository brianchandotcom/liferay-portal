/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.exception.DuplicateObjectEntryFolderExternalReferenceCodeException;
import com.liferay.object.exception.ObjectEntryFolderNameException;
import com.liferay.object.exception.ObjectEntryFolderScopeException;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class ObjectEntryFolderLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_objectDefinition = _addGroupCustomObjectDefinition();
	}

	@Test
	public void testAddObjectEntryFolder() throws Exception {

		// Duplicate external reference code

		String externalReferenceCode = StringUtil.randomString();

		AssertUtils.assertFailure(
			DuplicateObjectEntryFolderExternalReferenceCodeException.class,
			"Duplicate object entry folder with external reference code " +
				externalReferenceCode,
			() -> {
				_addObjectEntryFolder(
					externalReferenceCode, _group.getGroupId(),
					StringUtil.randomString(),
					ObjectEntryFolderConstants.
						DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);
				_addObjectEntryFolder(
					externalReferenceCode, _group.getGroupId(),
					StringUtil.randomString(),
					ObjectEntryFolderConstants.
						DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);
			});

		// Duplicate name

		String name = StringUtil.randomString();

		AssertUtils.assertFailure(
			ObjectEntryFolderNameException.MustNotBeDuplicate.class,
			"Duplicate name " + name,
			() -> {
				_addObjectEntryFolder(
					StringUtil.randomString(), _group.getGroupId(), name,
					ObjectEntryFolderConstants.
						DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);
				_addObjectEntryFolder(
					StringUtil.randomString(), _group.getGroupId(), name,
					ObjectEntryFolderConstants.
						DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);
			});

		// Null label map

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				StringUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), null, StringUtil.randomString(),
				ObjectEntryFolderConstants.
					DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID,
				ServiceContextTestUtil.getServiceContext());

		AssertUtils.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), objectEntryFolder.getName()
			).build(),
			objectEntryFolder.getLabelMap());

		// Null name

		AssertUtils.assertFailure(
			ObjectEntryFolderNameException.MustNotBeNull.class, "Name is null",
			() -> _addObjectEntryFolder(
				StringUtil.randomString(), _group.getGroupId(), null,
				ObjectEntryFolderConstants.
					DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID));

		// Scope

		AssertUtils.assertFailure(
			ObjectEntryFolderScopeException.class,
			StringBundler.concat(
				"Group ID ", TestPropsValues.getGroupId(),
				" does not match parent folder group ID ", _group.getGroupId()),
			() -> {
				ObjectEntryFolder parentObjectEntryFolder =
					_addObjectEntryFolder(
						StringUtil.randomString(), _group.getGroupId(),
						StringUtil.randomString(),
						ObjectEntryFolderConstants.
							DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

				_addObjectEntryFolder(
					StringUtil.randomString(), TestPropsValues.getGroupId(),
					StringUtil.randomString(),
					parentObjectEntryFolder.getObjectEntryFolderId());
			});
	}

	@Test
	public void testDeleteObjectEntryFolder() throws Exception {
		ObjectEntryFolder objectEntryFolder1 = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

		ObjectEntryFolder objectEntryFolder2 = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			objectEntryFolder1.getObjectEntryFolderId());
		ObjectEntryFolder objectEntryFolder3 = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			objectEntryFolder1.getObjectEntryFolderId());

		ObjectEntry objectEntry = _addObjectEntry(
			objectEntryFolder1.getObjectEntryFolderId());

		_objectEntryFolderLocalService.deleteObjectEntryFolder(
			objectEntryFolder1.getObjectEntryFolderId());

		Assert.assertNull(
			_objectEntryFolderLocalService.fetchObjectEntryFolder(
				objectEntryFolder1.getObjectEntryFolderId()));

		Assert.assertNull(
			_objectEntryFolderLocalService.fetchObjectEntryFolder(
				objectEntryFolder2.getObjectEntryFolderId()));
		Assert.assertNull(
			_objectEntryFolderLocalService.fetchObjectEntryFolder(
				objectEntryFolder3.getObjectEntryFolderId()));

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry.getObjectEntryId()));
	}

	@Test
	public void testUpdateObjectEntryFolder() throws Exception {

		// Duplicate name

		String name = StringUtil.randomString();

		AssertUtils.assertFailure(
			ObjectEntryFolderNameException.MustNotBeDuplicate.class,
			"Duplicate name " + name,
			() -> {
				_addObjectEntryFolder(
					StringUtil.randomString(), _group.getGroupId(), name,
					ObjectEntryFolderConstants.
						DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

				ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder(
					StringUtil.randomString(), _group.getGroupId(),
					StringUtil.randomString(),
					ObjectEntryFolderConstants.
						DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

				_objectEntryFolderLocalService.updateObjectEntryFolder(
					TestPropsValues.getUserId(),
					objectEntryFolder.getObjectEntryFolderId(),
					objectEntryFolder.getLabelMap(), name,
					objectEntryFolder.getParentObjectEntryFolderId());
			});

		// Null label map

		ObjectEntryFolder objectEntryFolder1 = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

		Assert.assertNotEquals(
			objectEntryFolder1.getName(),
			objectEntryFolder1.getLabel(LocaleUtil.getSiteDefault()));

		ObjectEntryFolder objectEntryFolder2 =
			_objectEntryFolderLocalService.updateObjectEntryFolder(
				TestPropsValues.getUserId(),
				objectEntryFolder1.getObjectEntryFolderId(), null,
				objectEntryFolder1.getName(),
				ObjectEntryFolderConstants.
					DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

		AssertUtils.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), objectEntryFolder2.getName()
			).build(),
			objectEntryFolder2.getLabelMap());

		// Null name

		AssertUtils.assertFailure(
			ObjectEntryFolderNameException.MustNotBeNull.class, "Name is null",
			() -> {
				ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder(
					StringUtil.randomString(), _group.getGroupId(),
					StringUtil.randomString(),
					ObjectEntryFolderConstants.
						DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

				_objectEntryFolderLocalService.updateObjectEntryFolder(
					TestPropsValues.getUserId(),
					objectEntryFolder.getObjectEntryFolderId(),
					objectEntryFolder.getLabelMap(), null,
					objectEntryFolder.getParentObjectEntryFolderId());
			});

		// Scope

		AssertUtils.assertFailure(
			ObjectEntryFolderScopeException.class,
			StringBundler.concat(
				"Group ID ", _group.getGroupId(),
				" does not match parent folder group ID ",
				TestPropsValues.getGroupId()),
			() -> {
				ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder(
					StringUtil.randomString(), _group.getGroupId(),
					StringUtil.randomString(),
					ObjectEntryFolderConstants.
						DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

				ObjectEntryFolder parentObjectEntryFolder =
					_addObjectEntryFolder(
						StringUtil.randomString(), TestPropsValues.getGroupId(),
						StringUtil.randomString(),
						ObjectEntryFolderConstants.
							DEFAULT_PARENT_OBJECT_ENTRY_FOLDER_ID);

				_objectEntryFolderLocalService.updateObjectEntryFolder(
					TestPropsValues.getUserId(),
					objectEntryFolder.getObjectEntryFolderId(),
					objectEntryFolder.getLabelMap(),
					objectEntryFolder.getName(),
					parentObjectEntryFolder.getObjectEntryFolderId());
			});
	}

	private ObjectDefinition _addGroupCustomObjectDefinition()
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, null, false, true, false, false,
				false,
				LocalizedMapUtil.getLocalizedMap(StringUtil.randomString()),
				"A" + StringUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_SITE,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), "fieldName")));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private ObjectEntry _addObjectEntry(long objectEntryFolderId)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"fieldName", StringUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		objectEntry.setObjectEntryFolderId(objectEntryFolderId);
		objectEntry.setTreePath(objectEntry.buildTreePath());

		return _objectEntryLocalService.updateObjectEntry(objectEntry);
	}

	private ObjectEntryFolder _addObjectEntryFolder(
			String externalReferenceCode, long groupId, String name,
			long parentObjectEntryFolderId)
		throws Exception {

		return _objectEntryFolderLocalService.addObjectEntryFolder(
			externalReferenceCode, TestPropsValues.getUserId(), groupId,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			name, parentObjectEntryFolderId,
			ServiceContextTestUtil.getServiceContext());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}