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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectFieldBusinessTypeException;
import com.liferay.object.exception.ObjectFieldDBTypeException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class ObjectFieldLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddSystemObjectField() throws Exception {

		// Blob type is not indexable

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, "LargeFile", null, "Blob", true, false, "", "", "able",
					false));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Blob type is not indexable",
				objectFieldDBTypeException.getMessage());
		}

		// Business types

		String[] businessTypes = {
			"Boolean", "Date", "Decimal", "Integer", "LargeFile", "LongInteger",
			"LongText", "Picklist", "PrecisionDecimal", "Relationship", "Text"
		};

		for (String businessType : businessTypes) {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					businessType, "", "Able", "able"));
		}

		String businessType = RandomTestUtil.randomString();

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					businessType, "", "Able", "able"));

			Assert.fail();
		}
		catch (ObjectFieldBusinessTypeException
					objectFieldBusinessTypeException) {

			Assert.assertEquals(
				"Invalid business type " + businessType,
				objectFieldBusinessTypeException.getMessage());
		}

		// DB types

		String[] dbTypes = {
			"BigDecimal", "Blob", "Clob", "Boolean", "Date", "Double",
			"Integer", "Long", "String"
		};

		for (String dbType : dbTypes) {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("", dbType, "Able", "able"));
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					"", "STRING", "Able", "able"));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Invalid DB type STRING",
				objectFieldDBTypeException.getMessage());
		}

		// Indexed language ID can only be applied with type \"String\" that
		// is not indexed as a keyword

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, "LongInteger", null, "Long", true, false, "en_US", "",
					"able", false));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type " +
					"\"String\" that is not indexed as a keyword",
				objectFieldDBTypeException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, "LongInteger", null, "Long", true, true, "en_US", "",
					"able", false));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type " +
					"\"String\" that is not indexed as a keyword",
				objectFieldDBTypeException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, "Text", null, "String", true, true, "en_US", "", "able",
					false));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type " +
					"\"String\" that is not indexed as a keyword",
				objectFieldDBTypeException.getMessage());
		}

		// Label is null

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					"Text", "String", "", "able"));

			Assert.fail();
		}
		catch (ObjectFieldLabelException objectFieldLabelException) {
			Assert.assertEquals(
				"Label is null for locale " + LocaleUtil.US.getDisplayName(),
				objectFieldLabelException.getMessage());
		}

		// Name is null

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					"Text", "String", "Able", ""));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name is null", objectFieldNameException.getMessage());
		}

		// Name must only contain letters and digits

		_testAddSystemObjectField(
			ObjectFieldUtil.createObjectField("Text", "String", " able "));

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("Text", "String", "abl e"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectFieldNameException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("Text", "String", "abl-e"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectFieldNameException.getMessage());
		}

		// The first character of a name must be an upper case letter

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("Text", "String", "Able"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"The first character of a name must be a lower case letter",
				objectFieldNameException.getMessage());
		}

		// Names must be less than 41 characters

		_testAddSystemObjectField(
			ObjectFieldUtil.createObjectField(
				"Text", "String", "a123456789a123456789a123456789a1234567891"));

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					"Text", "String",
					"a123456789a123456789a123456789a12345678912"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Names must be less than 41 characters",
				objectFieldNameException.getMessage());
		}

		// Reserved name

		String[] reservedNames = {
			"actions", "companyId", "createDate", "creator", "dateCreated",
			"dateModified", "externalReferenceCode", "groupId", "id",
			"lastPublishDate", "modifiedDate", "status", "statusByUserId",
			"statusByUserName", "statusDate", "userId", "userName"
		};

		for (String reservedName : reservedNames) {
			try {
				_testAddSystemObjectField(
					ObjectFieldUtil.createObjectField(
						"Text", "String", reservedName));

				Assert.fail();
			}
			catch (ObjectFieldNameException objectFieldNameException) {
				Assert.assertEquals(
					"Reserved name " + reservedName,
					objectFieldNameException.getMessage());
			}
		}

		String objectDefinitionName = "A" + RandomTestUtil.randomString();

		String pkObjectFieldName = TextFormatter.format(
			objectDefinitionName + "Id", TextFormatter.I);

		try {
			_testAddSystemObjectField(
				objectDefinitionName,
				ObjectFieldUtil.createObjectField(
					"Text", "String", pkObjectFieldName));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Reserved name " + pkObjectFieldName,
				objectFieldNameException.getMessage());
		}

		// Duplicate name

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					"Text", "String", "Able", "able"),
				ObjectFieldUtil.createObjectField(
					"Text", "String", "Able", "able"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Duplicate name able", objectFieldNameException.getMessage());
		}
	}

	@Test
	public void testDeleteObjectField1() throws Exception {
		ObjectField ableObjectField = ObjectFieldUtil.createObjectField(
			"Text", "String", "able");

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.singletonList(ableObjectField));

		ableObjectField = _objectFieldLocalService.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), "able");

		Assert.assertNotNull(ableObjectField);

		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "able_"));

		_objectFieldLocalService.deleteObjectField(
			ableObjectField.getObjectFieldId());

		ableObjectField = _objectFieldLocalService.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), "able");

		Assert.assertNull(ableObjectField);

		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "able_"));

		ableObjectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			true, "", LocalizedMapUtil.getLocalizedMap("able"), "able", false,
			Collections.emptyList());

		Assert.assertNotNull(ableObjectField);

		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "able_"));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		Assert.assertTrue(
			_hasColumn(objectDefinition.getDBTableName(), "able_"));

		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			true, "", LocalizedMapUtil.getLocalizedMap("baker"), "baker", false,
			Collections.emptyList());

		ObjectField bakerObjectField =
			_objectFieldLocalService.fetchObjectField(
				objectDefinition.getObjectDefinitionId(), "baker");

		Assert.assertNotNull(bakerObjectField);

		Assert.assertTrue(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "baker_"));

		try {
			_objectFieldLocalService.deleteObjectField(
				ableObjectField.getObjectFieldId());

			Assert.fail();
		}
		catch (RequiredObjectFieldException requiredObjectFieldException) {
			Assert.assertNotNull(requiredObjectFieldException);
		}

		Assert.assertTrue(
			_hasColumn(objectDefinition.getDBTableName(), "able_"));

		_objectFieldLocalService.deleteObjectField(
			bakerObjectField.getObjectFieldId());

		Assert.assertFalse(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "baker_"));

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testDeleteObjectField2() throws Exception {
		ObjectField ableObjectField = ObjectFieldUtil.createObjectField(
			"Text", "String", "able");

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, 1,
				Collections.singletonList(ableObjectField));

		ableObjectField = _objectFieldLocalService.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), "able");

		Assert.assertNotNull(ableObjectField);

		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "able_"));

		try {
			_objectFieldLocalService.deleteObjectField(
				ableObjectField.getObjectFieldId());

			Assert.fail();
		}
		catch (RequiredObjectFieldException requiredObjectFieldException) {
			Assert.assertNotNull(requiredObjectFieldException);
		}

		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			true, "", LocalizedMapUtil.getLocalizedMap("baker"), "baker", false,
			Collections.emptyList());

		ObjectField bakerObjectField =
			_objectFieldLocalService.fetchObjectField(
				objectDefinition.getObjectDefinitionId(), "baker");

		Assert.assertNotNull(bakerObjectField);

		Assert.assertTrue(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "baker_"));

		_objectFieldLocalService.deleteObjectField(
			bakerObjectField.getObjectFieldId());

		Assert.assertFalse(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "baker_"));

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testDeleteObjectFieldAttachment() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Date", "Date", true, false, null, "Birthday",
						"birthday", false)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Attachment", "Long",
			true, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"upload", false,
			Arrays.asList(
				_createObjectFieldSetting("acceptedFileExtensions", "txt"),
				_createObjectFieldSetting("fileSource", "userComputer"),
				_createObjectFieldSetting("maximumFileSize", "100")));

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(), null, null,
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"upload", fileEntry.getFileEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(
			_dlAppLocalService.getFileEntry(fileEntry.getFileEntryId()));

		_objectFieldLocalService.deleteObjectField(
			objectField.getObjectFieldId());

		try {
			_dlAppLocalService.getFileEntry(fileEntry.getFileEntryId());

			Assert.fail();
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			Assert.assertEquals(
				StringBundler.concat(
					"No FileEntry exists with the key {fileEntryId=",
					fileEntry.getFileEntryId(), "}"),
				noSuchFileEntryException.getMessage());
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testObjectFieldSettings() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.emptyList());

		try {
			_objectFieldLocalService.addCustomObjectField(
				TestPropsValues.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(), "Attachment", "Long",
				true, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"upload", false, Collections.emptyList());
		}
		catch (ObjectFieldSettingValueException
					objectFieldSettingValueException) {

			Assert.assertEquals(
				"The settings acceptedFileExtensions, fileSource, " +
					"maximumFileSize are required for object field upload",
				objectFieldSettingValueException.getMessage());
		}

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Attachment", "Long",
			true, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false,
			Arrays.asList(
				_createObjectFieldSetting("acceptedFileExtensions", "jpg, png"),
				_createObjectFieldSetting("fileSource", "userComputer"),
				_createObjectFieldSetting("maximumFileSize", "100")));

		_assertObjectFieldSetting(
			"acceptedFileExtensions", objectField.getObjectFieldId(),
			"jpg, png");
		_assertObjectFieldSetting(
			"fileSource", objectField.getObjectFieldId(), "userComputer");
		_assertObjectFieldSetting(
			"maximumFileSize", objectField.getObjectFieldId(), "100");

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), 0, "Attachment", "Long", true,
			false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false,
			Arrays.asList(
				_createObjectFieldSetting("acceptedFileExtensions", "jpg"),
				_createObjectFieldSetting("fileSource", "documentsAndMedia"),
				_createObjectFieldSetting("maximumFileSize", "10")));

		_assertObjectFieldSetting(
			"acceptedFileExtensions", objectField.getObjectFieldId(), "jpg");
		_assertObjectFieldSetting(
			"fileSource", objectField.getObjectFieldId(), "documentsAndMedia");
		_assertObjectFieldSetting(
			"maximumFileSize", objectField.getObjectFieldId(), "10");

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), 0, "Text", "String", true, false,
			null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false, Collections.emptyList());

		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "acceptedFileExtensions"));
		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "fileSource"));
		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "maximumFileSize"));

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testUpdateCustomObjectField() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, null);

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "LongInteger", "Long",
			false, true, "", LocalizedMapUtil.getLocalizedMap("able"), "able",
			false, Collections.emptyList());

		Assert.assertEquals("able_", objectField.getDBColumnName());
		Assert.assertEquals("Long", objectField.getDBType());
		Assert.assertFalse(objectField.isIndexed());
		Assert.assertTrue(objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("able"),
			objectField.getLabelMap());
		Assert.assertEquals("able", objectField.getName());
		Assert.assertFalse(objectField.isRequired());

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), 0, "LongInteger", "Long", false,
			true, "", LocalizedMapUtil.getLocalizedMap("able"), "able", false,
			Collections.emptyList());

		Assert.assertEquals("able_", objectField.getDBColumnName());
		Assert.assertEquals("Long", objectField.getDBType());
		Assert.assertFalse(objectField.isIndexed());
		Assert.assertTrue(objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("able"),
			objectField.getLabelMap());
		Assert.assertEquals("able", objectField.getName());
		Assert.assertFalse(objectField.isRequired());

		String indexedLanguageId = LanguageUtil.getLanguageId(
			LocaleUtil.getDefault());

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), 0, "Text", "String", true, false,
			indexedLanguageId, LocalizedMapUtil.getLocalizedMap("baker"),
			"baker", true, Collections.emptyList());

		Assert.assertEquals("baker_", objectField.getDBColumnName());
		Assert.assertEquals("String", objectField.getDBType());
		Assert.assertTrue(objectField.isIndexed());
		Assert.assertFalse(objectField.isIndexedAsKeyword());
		Assert.assertEquals(
			indexedLanguageId, objectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("baker"),
			objectField.getLabelMap());
		Assert.assertEquals("baker", objectField.getName());
		Assert.assertTrue(objectField.isRequired());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), 0, "Integer", "Integer", false,
			true, "", LocalizedMapUtil.getLocalizedMap("charlie"), "charlie",
			false, Collections.emptyList());

		Assert.assertEquals("baker_", objectField.getDBColumnName());
		Assert.assertEquals("String", objectField.getDBType());
		Assert.assertTrue(objectField.isIndexed());
		Assert.assertFalse(objectField.isIndexedAsKeyword());
		Assert.assertEquals(
			indexedLanguageId, objectField.getIndexedLanguageId());
		Assert.assertEquals(
			objectField.getLabelMap(),
			LocalizedMapUtil.getLocalizedMap("charlie"));
		Assert.assertEquals("baker", objectField.getName());
		Assert.assertTrue(objectField.isRequired());

		_testUpdateCustomObjectFieldThrowsObjectFieldRelationshipTypeException(
			objectDefinition);

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	private void _assertObjectFieldSetting(
			String name, long objectFieldId, String value)
		throws Exception {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId, name);

		Assert.assertEquals(name, objectFieldSetting.getName());
		Assert.assertEquals(value, objectFieldSetting.getValue());
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	private boolean _hasColumn(String tableName, String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasColumn(tableName, columnName);
		}
	}

	private String _testAddSystemObjectField(ObjectField... objectFields)
		throws Exception {

		return _testAddSystemObjectField(
			"A" + RandomTestUtil.randomString(), objectFields);
	}

	private String _testAddSystemObjectField(
			String objectDefinitionName, ObjectField... objectFields)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				_objectDefinitionLocalService.addSystemObjectDefinition(
					TestPropsValues.getUserId(), RandomTestUtil.randomString(),
					null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					objectDefinitionName, null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					ObjectDefinitionConstants.SCOPE_COMPANY, 1,
					Arrays.asList(objectFields));
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}

		return objectDefinitionName;
	}

	private void
			_testUpdateCustomObjectFieldThrowsObjectFieldRelationshipTypeException(
				ObjectDefinition objectDefinition1)
		throws Exception {

		ObjectDefinition objectDefinition2 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.<ObjectField>emptyList());

		String name = StringUtil.randomId();

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			objectDefinition1.getObjectDefinitionId(),
			objectDefinition2.getObjectDefinitionId(),
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			name, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		try {
			String objectFieldNamePrefix = "r_" + name + "_";

			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				objectDefinition2.getObjectDefinitionId(),
				objectFieldNamePrefix +
					objectDefinition1.getPKObjectFieldName());

			_objectFieldLocalService.updateCustomObjectField(
				objectField.getObjectFieldId(),
				objectField.getListTypeDefinitionId(),
				objectField.getBusinessType(), "String",
				objectField.isIndexed(), objectField.isIndexedAsKeyword(),
				objectField.getIndexedLanguageId(), objectField.getLabelMap(),
				"able", objectField.isRequired(), Collections.emptyList());

			Assert.fail();
		}
		catch (ObjectFieldRelationshipTypeException
					objectFieldRelationshipTypeException) {

			Assert.assertEquals(
				"Object field relationship name and DB type cannot be changed",
				objectFieldRelationshipTypeException.getMessage());
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition2);
		}
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}