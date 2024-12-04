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

	@Test(
		expected = DuplicateObjectEntryFolderExternalReferenceCodeException.class
	)
	public void testAddObjectEntryFolderWithDuplicateExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = StringUtil.randomString();

		_addObjectEntryFolder(
			externalReferenceCode, _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);
		_addObjectEntryFolder(
			externalReferenceCode, _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);
	}

	@Test(expected = ObjectEntryFolderNameException.MustNotBeDuplicate.class)
	public void testAddObjectEntryFolderWithDuplicateName() throws Exception {
		String name = StringUtil.randomString();

		_addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(), name,
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);
		_addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(), name,
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);
	}

	@Test
	public void testAddObjectEntryFolderWithNullLabelMap() throws Exception {
		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				StringUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), null, StringUtil.randomString(),
				ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER,
				ServiceContextTestUtil.getServiceContext());

		AssertUtils.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), objectEntryFolder.getName()
			).build(),
			objectEntryFolder.getLabelMap());
	}

	@Test(expected = ObjectEntryFolderNameException.MustNotBeNull.class)
	public void testAddObjectEntryFolderWithNullName() throws Exception {
		_addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(), null,
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);
	}

	@Test(expected = ObjectEntryFolderScopeException.class)
	public void testAddObjectEntryFolderWithParentInOtherGroup()
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

		_addObjectEntryFolder(
			StringUtil.randomString(), TestPropsValues.getGroupId(),
			StringUtil.randomString(),
			objectEntryFolder.getObjectEntryFolderId());
	}

	@Test
	public void testDeleteObjectEntryFolderWithChildren() throws Exception {
		ObjectEntryFolder objectEntryFolder1 = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

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

	@Test(expected = ObjectEntryFolderNameException.MustNotBeDuplicate.class)
	public void testUpdateObjectEntryFolderWithDuplicateName()
		throws Exception {

		String name = StringUtil.randomString();

		_addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(), name,
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

		ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

		_objectEntryFolderLocalService.updateObjectEntryFolder(
			TestPropsValues.getUserId(),
			objectEntryFolder.getObjectEntryFolderId(),
			objectEntryFolder.getLabelMap(), name,
			objectEntryFolder.getParentObjectEntryFolderId());
	}

	@Test
	public void testUpdateObjectEntryFolderWithNullLabelMap() throws Exception {
		ObjectEntryFolder objectEntryFolder1 = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

		Assert.assertNotEquals(
			objectEntryFolder1.getName(),
			objectEntryFolder1.getLabel(LocaleUtil.getSiteDefault()));

		ObjectEntryFolder objectEntryFolder2 =
			_objectEntryFolderLocalService.updateObjectEntryFolder(
				TestPropsValues.getUserId(),
				objectEntryFolder1.getObjectEntryFolderId(), null,
				objectEntryFolder1.getName(),
				ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

		AssertUtils.assertEquals(
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), objectEntryFolder2.getName()
			).build(),
			objectEntryFolder2.getLabelMap());
	}

	@Test(expected = ObjectEntryFolderNameException.MustNotBeNull.class)
	public void testUpdateObjectEntryFolderWithNullName() throws Exception {
		ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

		_objectEntryFolderLocalService.updateObjectEntryFolder(
			TestPropsValues.getUserId(),
			objectEntryFolder.getObjectEntryFolderId(),
			objectEntryFolder.getLabelMap(), null,
			objectEntryFolder.getParentObjectEntryFolderId());
	}

	@Test(expected = ObjectEntryFolderScopeException.class)
	public void testUpdateObjectEntryFolderWithParentInOtherGroup()
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder(
			StringUtil.randomString(), _group.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

		ObjectEntryFolder parentObjectEntryFolder = _addObjectEntryFolder(
			StringUtil.randomString(), TestPropsValues.getGroupId(),
			StringUtil.randomString(),
			ObjectEntryFolderConstants.DEFAULT_PARENT_OBJECT_ENTRY_FOLDER);

		_objectEntryFolderLocalService.updateObjectEntryFolder(
			TestPropsValues.getUserId(),
			objectEntryFolder.getObjectEntryFolderId(),
			objectEntryFolder.getLabelMap(), objectEntryFolder.getName(),
			parentObjectEntryFolder.getObjectEntryFolderId());
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