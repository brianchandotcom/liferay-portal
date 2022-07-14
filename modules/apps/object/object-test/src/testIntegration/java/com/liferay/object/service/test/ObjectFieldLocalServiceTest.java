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
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectFieldBusinessTypeException;
import com.liferay.object.exception.ObjectFieldDBTypeException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.ObjectFieldSettingNameException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldBuilder;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));

		_listTypeEntryKey = RandomTestUtil.randomString();

		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), _listTypeEntryKey,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
	}

	@Test
	public void testAddCustomObjectField() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "true"
			).build());

		// Reserved name

		String[] reservedNames = {
			"actions", "companyId", "createDate", "creator", "dateCreated",
			"dateModified", "externalReferenceCode", "groupId", "id",
			"lastPublishDate", "modifiedDate", "statusByUserId",
			"statusByUserName", "statusDate", "userId", "userName"
		};

		for (String reservedName : reservedNames) {
			try {
				_testAddCustomObjectField(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, reservedName,
						_getObjectFieldSettings(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

				Assert.fail();
			}
			catch (ObjectFieldNameException objectFieldNameException) {
				Assert.assertEquals(
					"Reserved name " + reservedName,
					objectFieldNameException.getMessage());
			}
		}

		ObjectFieldBuilder objectFieldBuilder = new ObjectFieldBuilder();

		String defaultValue = RandomTestUtil.randomString();

		_testAddCustomObjectField(
			StringBundler.concat(
				"Default value \"", defaultValue, "\" is not a list entry in ",
				"list definition ",
				_listTypeDefinition.getListTypeDefinitionId()),
			objectFieldBuilder.businessType(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST
			).listTypeDefinitionId(
				_listTypeDefinition.getListTypeDefinitionId()
			).defaultValue(
				defaultValue
			).build());

		_testAddCustomObjectField(
			StringBundler.concat(
				"Object field can only have a default type when the business ",
				"type is \"", ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
				"\""),
			objectFieldBuilder.businessType(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT
			).dbType(
				ObjectFieldConstants.DB_TYPE_STRING
			).defaultValue(
				RandomTestUtil.randomString()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"a" + RandomTestUtil.randomString()
			).objectFieldSettings(
				_getObjectFieldSettings(ObjectFieldConstants.BUSINESS_TYPE_TEXT)
			).build());

		_testAddCustomObjectField(
			"Object field default value can only be set when the picklist is " +
				"a state",
			objectFieldBuilder.businessType(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST
			).defaultValue(
				_listTypeEntryKey
			).build());

		_testAddCustomObjectField(
			"Object field must be required when the state is true",
			objectFieldBuilder.state(
				true
			).build());

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "false"
			).build());
	}

	@Test
	public void testAddSystemObjectField() throws Exception {

		// Blob type is not indexable

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_LARGE_FILE, null,
					ObjectFieldConstants.DB_TYPE_BLOB, true, false, "", "",
					"able", false, true));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Blob type is not indexable",
				objectFieldDBTypeException.getMessage());
		}

		// Business types

		String[] businessTypes = {
			ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
			ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
			ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.BUSINESS_TYPE_LARGE_FILE,
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
			ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP,
			ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT,
			ObjectFieldConstants.BUSINESS_TYPE_TEXT
		};

		for (String businessType : businessTypes) {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					businessType, "", "Able", "able",
					_getObjectFieldSettings(businessType)));
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
			ObjectFieldConstants.DB_TYPE_BIG_DECIMAL,
			ObjectFieldConstants.DB_TYPE_BLOB,
			ObjectFieldConstants.DB_TYPE_CLOB,
			ObjectFieldConstants.DB_TYPE_BOOLEAN,
			ObjectFieldConstants.DB_TYPE_DATE,
			ObjectFieldConstants.DB_TYPE_DOUBLE,
			ObjectFieldConstants.DB_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG,
			ObjectFieldConstants.DB_TYPE_STRING
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
					0, ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, null,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, "en_US", "",
					"able", false, true));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type \"Clob\" " +
					"or \"String\" that is not indexed as a keyword",
				objectFieldDBTypeException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, null,
					ObjectFieldConstants.DB_TYPE_LONG, true, true, "en_US", "",
					"able", false, true));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type \"Clob\" " +
					"or \"String\" that is not indexed as a keyword",
				objectFieldDBTypeException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, "en_US",
					"", 0, "able",
					_getObjectFieldSettings(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT),
					false, true));

			Assert.fail();
		}
		catch (ObjectFieldDBTypeException objectFieldDBTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type \"Clob\" " +
					"or \"String\" that is not indexed as a keyword",
				objectFieldDBTypeException.getMessage());
		}

		// Label is null

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "", "able",
					_getObjectFieldSettings(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

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
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "Able", "",
					_getObjectFieldSettings(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name is null", objectFieldNameException.getMessage());
		}

		// Name must only contain letters and digits

		_testAddSystemObjectField(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, " able ",
				_getObjectFieldSettings(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "abl e",
					_getObjectFieldSettings(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectFieldNameException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "abl-e",
					_getObjectFieldSettings(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

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
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "Able",
					_getObjectFieldSettings(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

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
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING,
				"a123456789a123456789a123456789a1234567891",
				_getObjectFieldSettings(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					"a123456789a123456789a123456789a12345678912",
					_getObjectFieldSettings(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Names must be less than 41 characters",
				objectFieldNameException.getMessage());
		}

		// Reserved name

		String objectDefinitionName = "A" + RandomTestUtil.randomString();

		String pkObjectFieldName = TextFormatter.format(
			objectDefinitionName + "Id", TextFormatter.I);

		try {
			_testAddSystemObjectField(
				objectDefinitionName,
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, pkObjectFieldName,
					_getObjectFieldSettings(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Reserved name " + pkObjectFieldName,
				objectFieldNameException.getMessage());
		}

		// Duplicate name

		try {
			ObjectDefinition objectDefinition =
				ObjectDefinitionTestUtil.addObjectDefinition(
					_objectDefinitionLocalService,
					Collections.singletonList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING, "Able", "able",
							_getObjectFieldSettings(
								ObjectFieldConstants.BUSINESS_TYPE_TEXT))));

			_objectFieldLocalService.addSystemObjectField(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
				LocalizedMapUtil.getLocalizedMap("Able"), "able", false, false);

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
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "able",
			_getObjectFieldSettings(ObjectFieldConstants.BUSINESS_TYPE_TEXT));

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
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
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
			LocalizedMapUtil.getLocalizedMap("able"), "able", false, false,
			_getObjectFieldSettings(ObjectFieldConstants.BUSINESS_TYPE_TEXT));

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
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
			LocalizedMapUtil.getLocalizedMap("baker"), "baker", false, false,
			_getObjectFieldSettings(ObjectFieldConstants.BUSINESS_TYPE_TEXT));

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
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "able",
			_getObjectFieldSettings(ObjectFieldConstants.BUSINESS_TYPE_TEXT));

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
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
			LocalizedMapUtil.getLocalizedMap("baker"), "baker", false, false,
			_getObjectFieldSettings("Text"));

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
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_DATE,
						ObjectFieldConstants.DB_TYPE_DATE, true, false, null,
						"Birthday", "birthday", false)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
			ObjectFieldConstants.DB_TYPE_LONG, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"upload", false, false,
			Arrays.asList(
				_createObjectFieldSetting("acceptedFileExtensions", "txt"),
				_createObjectFieldSetting("fileSource", "userComputer"),
				_createObjectFieldSetting("maximumFileSize", "100")));

		FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			objectDefinition.getPortletId(),
			TempFileEntryUtil.getTempFileName(
				StringUtil.randomString() + ".txt"),
			FileUtil.createTempFile(RandomTestUtil.randomBytes()),
			ContentTypes.TEXT_PLAIN);

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"upload", fileEntry.getFileEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Map<String, Serializable> values = objectEntry.getValues();

		long persistedFileEntryId = GetterUtil.getLong(values.get("upload"));

		Assert.assertNotNull(
			_dlAppLocalService.getFileEntry(persistedFileEntryId));

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

		_objectFieldLocalService.deleteObjectField(
			objectField.getObjectFieldId());

		try {
			_dlAppLocalService.getFileEntry(persistedFileEntryId);

			Assert.fail();
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			Assert.assertEquals(
				StringBundler.concat(
					"No FileEntry exists with the key {fileEntryId=",
					persistedFileEntryId, "}"),
				noSuchFileEntryException.getMessage());
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testObjectFieldSettings() throws Exception {

		// Missing required values

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService);

		try {
			_objectFieldLocalService.addCustomObjectField(
				TestPropsValues.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
				ObjectFieldConstants.DB_TYPE_LONG, null, true, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"upload", false, false, Collections.emptyList());
		}
		catch (ObjectFieldSettingValueException.MissingRequiredValues
					objectFieldSettingValueException) {

			Assert.assertEquals(
				"The settings acceptedFileExtensions, fileSource, " +
					"maximumFileSize are required for object field upload",
				objectFieldSettingValueException.getMessage());
		}

		try {
			_objectFieldLocalService.addCustomObjectField(
				TestPropsValues.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, null, true, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"text", false, false,
				Collections.singletonList(
					_createObjectFieldSetting("showCounter", "true")));
		}
		catch (ObjectFieldSettingValueException.MissingRequiredValues
					objectFieldSettingValueException) {

			Assert.assertEquals(
				"The settings maxLength are required for object field text",
				objectFieldSettingValueException.getMessage());
		}

		// Not allowed names

		try {
			_objectFieldLocalService.addCustomObjectField(
				TestPropsValues.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, null, true, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"text", false, false,
				Arrays.asList(
					_createObjectFieldSetting("anySetting", "10"),
					_createObjectFieldSetting("showCounter", "true")));
		}
		catch (ObjectFieldSettingNameException.NotAllowedNames
					objectFieldSettingNameException) {

			Assert.assertEquals(
				"The settings anySetting are not allowed for object field text",
				objectFieldSettingNameException.getMessage());
		}

		try {
			_objectFieldLocalService.addCustomObjectField(
				TestPropsValues.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, null, true, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"text", false, false,
				Arrays.asList(_createObjectFieldSetting("maxLength", null)));
		}
		catch (ObjectFieldSettingNameException.NotAllowedNames
					objectFieldSettingNameException) {

			Assert.assertEquals(
				"The settings maxLength are not allowed for object field text",
				objectFieldSettingNameException.getMessage());
		}

		try {
			_objectFieldLocalService.addCustomObjectField(
				TestPropsValues.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, null, true, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"text", false, false,
				Arrays.asList(
					_createObjectFieldSetting("maxLength", "10"),
					_createObjectFieldSetting("showCounter", "false")));
		}
		catch (ObjectFieldSettingNameException.NotAllowedNames
					objectFieldSettingNameException) {

			Assert.assertEquals(
				"The settings maxLength are not allowed for object field text",
				objectFieldSettingNameException.getMessage());
		}

		// Business type attachment

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
			ObjectFieldConstants.DB_TYPE_LONG, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false, false,
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
			objectField.getObjectFieldId(), StringPool.BLANK, 0,
			ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
			ObjectFieldConstants.DB_TYPE_LONG, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false, false,
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

		// Business type text

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), StringPool.BLANK, 0,
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false, false,
			_getObjectFieldSettings("Text"));

		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "acceptedFileExtensions"));
		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "fileSource"));
		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "maximumFileSize"));

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), StringPool.BLANK, 0,
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false, false,
			Arrays.asList(
				_createObjectFieldSetting("maxLength", "10"),
				_createObjectFieldSetting("showCounter", "true")));

		_assertObjectFieldSetting(
			"maxLength", objectField.getObjectFieldId(), "10");
		_assertObjectFieldSetting(
			"showCounter", objectField.getObjectFieldId(), "true");

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), StringPool.BLANK, 0,
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false, false,
			Collections.singletonList(
				_createObjectFieldSetting("showCounter", "false")));

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "maxLength");

		Assert.assertNull(objectFieldSetting);

		_assertObjectFieldSetting(
			"showCounter", objectField.getObjectFieldId(), "false");

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testUpdateCustomObjectField() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "true"
			).build());

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService);

		ObjectFieldBuilder objectFieldBuilder = new ObjectFieldBuilder();

		ObjectField objectField = objectFieldBuilder.userId(
			TestPropsValues.getUserId()
		).objectDefinitionId(
			objectDefinition.getObjectDefinitionId()
		).businessType(
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER
		).dbType(
			ObjectFieldConstants.DB_TYPE_LONG
		).defaultValue(
			StringPool.BLANK
		).indexedAsKeyword(
			true
		).indexedLanguageId(
			StringPool.BLANK
		).labelMap(
			LocalizedMapUtil.getLocalizedMap("able")
		).name(
			"able"
		).objectFieldSettings(
			Collections.emptyList()
		).build();

		objectField = _objectFieldLocalService.addCustomObjectField(
			objectField.getUserId(), objectField.getListTypeDefinitionId(),
			objectField.getObjectDefinitionId(), objectField.getBusinessType(),
			objectField.getDBType(), objectField.getDefaultValue(),
			objectField.isIndexed(), objectField.isIndexedAsKeyword(),
			objectField.getIndexedLanguageId(), objectField.getLabelMap(),
			objectField.getName(), objectField.isRequired(),
			objectField.isState(), objectField.getObjectFieldSettings());

		Assert.assertEquals("able_", objectField.getDBColumnName());
		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_LONG, objectField.getDBType());
		Assert.assertFalse(objectField.isIndexed());
		Assert.assertTrue(objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("able"),
			objectField.getLabelMap());
		Assert.assertEquals("able", objectField.getName());
		Assert.assertFalse(objectField.isRequired());

		_testUpdateCustomObjectField(
			objectFieldBuilder.dbColumnName(
				objectField.getDBColumnName()
			).objectFieldId(
				objectField.getObjectFieldId()
			).externalReferenceCode(
				objectField.getExternalReferenceCode()
			).build());

		_testUpdateCustomObjectField(
			objectFieldBuilder.businessType(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST
			).dbType(
				ObjectFieldConstants.DB_TYPE_STRING
			).indexedAsKeyword(
				false
			).build());

		_testUpdateCustomObjectField(
			objectFieldBuilder.defaultValue(
				_listTypeEntryKey
			).listTypeDefinitionId(
				_listTypeDefinition.getListTypeDefinitionId()
			).required(
				true
			).state(
				true
			).build());

		String indexedLanguageId = LanguageUtil.getLanguageId(
			LocaleUtil.getDefault());

		objectField = objectFieldBuilder.businessType(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT
		).dbColumnName(
			"baker_"
		).defaultValue(
			StringPool.BLANK
		).indexed(
			true
		).indexedLanguageId(
			indexedLanguageId
		).labelMap(
			LocalizedMapUtil.getLocalizedMap("baker")
		).listTypeDefinitionId(
			0
		).name(
			"baker"
		).state(
			false
		).objectFieldSettings(
			_getObjectFieldSettings(ObjectFieldConstants.BUSINESS_TYPE_TEXT)
		).build();

		_testUpdateCustomObjectField(objectField);

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), StringPool.BLANK, 0,
			ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_INTEGER, StringPool.BLANK, false, true,
			StringPool.BLANK, LocalizedMapUtil.getLocalizedMap("charlie"),
			"charlie", false, false,
			_getObjectFieldSettings(ObjectFieldConstants.BUSINESS_TYPE_TEXT));

		Assert.assertEquals("baker_", objectField.getDBColumnName());
		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_STRING, objectField.getDBType());
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

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "false"
			).build());
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

	private List<ObjectFieldSetting> _getObjectFieldSettings(
		String businessType) {

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT) ||
			Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_TEXT)) {

			ObjectFieldSetting objectFieldSetting =
				ObjectFieldSettingLocalServiceUtil.createObjectFieldSetting(0);

			objectFieldSetting.setName("showCounter");
			objectFieldSetting.setValue("false");

			return Collections.singletonList(objectFieldSetting);
		}

		return null;
	}

	private boolean _hasColumn(String tableName, String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasColumn(tableName, columnName);
		}
	}

	private void _testAddCustomObjectField(ObjectField... objectFields)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				_objectDefinitionLocalService.addCustomObjectDefinition(
					TestPropsValues.getUserId(),
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"A" + RandomTestUtil.randomString(), null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					ObjectDefinitionConstants.SCOPE_COMPANY,
					ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
					Arrays.asList(objectFields));
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testAddCustomObjectField(
		String expectedMessage, ObjectField objectField) {

		try {
			_testAddCustomObjectField(objectField);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(expectedMessage, exception.getMessage());
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

	private void _testUpdateCustomObjectField(ObjectField expectedObjectField)
		throws Exception {

		ObjectField objectField =
			_objectFieldLocalService.updateCustomObjectField(
				expectedObjectField.getObjectFieldId(),
				expectedObjectField.getExternalReferenceCode(),
				expectedObjectField.getListTypeDefinitionId(),
				expectedObjectField.getBusinessType(),
				expectedObjectField.getDBType(),
				expectedObjectField.getDefaultValue(),
				expectedObjectField.isIndexed(),
				expectedObjectField.isIndexedAsKeyword(),
				expectedObjectField.getIndexedLanguageId(),
				expectedObjectField.getLabelMap(),
				expectedObjectField.getName(), expectedObjectField.isRequired(),
				expectedObjectField.isState(),
				expectedObjectField.getObjectFieldSettings());

		Assert.assertEquals(
			expectedObjectField.getExternalReferenceCode(),
			objectField.getExternalReferenceCode());
		Assert.assertEquals(
			expectedObjectField.getDBColumnName(),
			objectField.getDBColumnName());
		Assert.assertEquals(
			expectedObjectField.getDBType(), objectField.getDBType());
		Assert.assertEquals(
			expectedObjectField.getDefaultValue(),
			objectField.getDefaultValue());
		Assert.assertEquals(
			expectedObjectField.isIndexed(), objectField.isIndexed());
		Assert.assertEquals(
			expectedObjectField.isIndexedAsKeyword(),
			objectField.isIndexedAsKeyword());
		Assert.assertEquals(
			expectedObjectField.getIndexedLanguageId(),
			objectField.getIndexedLanguageId());
		Assert.assertEquals(
			expectedObjectField.getLabelMap(), objectField.getLabelMap());
		Assert.assertEquals(
			expectedObjectField.getName(), objectField.getName());
		Assert.assertEquals(
			expectedObjectField.isRequired(), objectField.isRequired());
		Assert.assertEquals(
			expectedObjectField.isState(), objectField.isState());
	}

	private void
			_testUpdateCustomObjectFieldThrowsObjectFieldRelationshipTypeException(
				ObjectDefinition objectDefinition1)
		throws Exception {

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService);

		String name = StringUtil.randomId();

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			objectDefinition1.getObjectDefinitionId(),
			objectDefinition2.getObjectDefinitionId(), 0,
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
				objectField.getObjectFieldId(), StringPool.BLANK,
				objectField.getListTypeDefinitionId(),
				objectField.getBusinessType(),
				ObjectFieldConstants.DB_TYPE_STRING,
				objectField.getDefaultValue(), objectField.isIndexed(),
				objectField.isIndexedAsKeyword(),
				objectField.getIndexedLanguageId(), objectField.getLabelMap(),
				"able", objectField.isRequired(), objectField.isState(),
				Collections.emptyList());

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

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private String _listTypeEntryKey;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

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