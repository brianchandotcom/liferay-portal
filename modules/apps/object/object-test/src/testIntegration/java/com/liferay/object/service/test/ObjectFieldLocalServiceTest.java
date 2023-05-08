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
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectDefinitionEnableLocalizationException;
import com.liferay.object.exception.ObjectFieldBusinessTypeException;
import com.liferay.object.exception.ObjectFieldDBTypeException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldListTypeDefinitionIdException;
import com.liferay.object.exception.ObjectFieldLocalizedException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.ObjectFieldSettingNameException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.exception.ObjectFieldStateException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.ObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
@FeatureFlags({"LPS-146755", "LPS-163716"})
@RunWith(Arquillian.class)
public class ObjectFieldLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_listTypeEntryKey = RandomTestUtil.randomString();

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.singletonList(
					ListTypeEntryUtil.createListTypeEntry(_listTypeEntryKey)));
	}

	@Test
	public void testAddCustomObjectField() throws Exception {

		// Localization is not enabled

		_assertFailure(
			ObjectDefinitionEnableLocalizationException.class, null,
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).localized(
						true
					).build())));

		// List type definition ID is 0

		_assertFailure(
			ObjectFieldListTypeDefinitionIdException.class,
			"List type definition ID is 0",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new ObjectFieldBuilder(
					).businessType(
						ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).build())));

		// Localization is not supported

		_assertFailure(
			ObjectFieldLocalizedException.class,
			"Only LongText,RichText and Text business types support " +
				"localization",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new DateObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).localized(
						true
					).build())));

		// Reserved names

		String[] reservedNames = {
			"actions", "companyId", "createDate", "creator", "dateCreated",
			"dateModified", "externalReferenceCode", "groupId", "id",
			"lastPublishDate", "modifiedDate", "statusByUserId",
			"statusByUserName", "statusDate", "userId", "userName"
		};

		for (String reservedName : reservedNames) {
			_assertFailure(
				ObjectFieldNameException.class, "Reserved name " + reservedName,
				() -> ObjectDefinitionTestUtil.addObjectDefinition(
					_objectDefinitionLocalService,
					Arrays.asList(
						new TextObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							reservedName
						).build())));
		}

		// Object field setting invalid value

		String defaultValue = RandomTestUtil.randomString();

		_assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			"The value " + defaultValue +
				" of setting defaultValue is invalid for object field picklist",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new PicklistObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).listTypeDefinitionId(
						_listTypeDefinition.getListTypeDefinitionId()
					).name(
						"picklist"
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
							).value(
								defaultValue
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_DEFAULT_VALUE_TYPE
							).value(
								ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
							).build())
					).build())));

		String uniqueValues = RandomTestUtil.randomString();

		_assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			"The value " + uniqueValues +
				" of setting uniqueValues is invalid for object field text",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"text"
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
							).value(
								uniqueValues
							).build())
					).build())));

		_assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			"The value expressionBuilder of setting defaultValueType is " +
				"invalid for object field picklist",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new PicklistObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).listTypeDefinitionId(
						_listTypeDefinition.getListTypeDefinitionId()
					).name(
						"picklist"
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
							).value(
								_listTypeEntryKey
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_DEFAULT_VALUE_TYPE
							).value(
								ObjectFieldSettingConstants.
									VALUE_EXPRESSION_BUILDER
							).build())
					).required(
						true
					).state(
						true
					).build())));

		// Object field setting missing required values

		_assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			"The settings acceptedFileExtensions, fileSource, " +
				"maximumFileSize are required for object field upload",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new AttachmentObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"upload"
					).objectFieldSettings(
						Collections.emptyList()
					).build())));

		_assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			"The settings defaultValue, defaultValueType are required for " +
				"object field picklist",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new PicklistObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).listTypeDefinitionId(
						_listTypeDefinition.getListTypeDefinitionId()
					).name(
						"picklist"
					).objectFieldSettings(
						Collections.emptyList()
					).required(
						true
					).state(
						true
					).build())));

		_assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			"The settings maxLength are required for object field text",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"text"
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_SHOW_COUNTER
							).value(
								"true"
							).build())
					).build())));

		// Object field setting not allowed names

		_assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings anySetting are not allowed for object field text",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"text"
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								"anySetting"
							).value(
								"10"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_SHOW_COUNTER
							).value(
								"true"
							).build())
					).build())));

		_assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings defaultValue, defaultValueType are not allowed for " +
				"object field text",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"text"
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
							).value(
								_listTypeEntryKey
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_DEFAULT_VALUE_TYPE
							).value(
								ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_SHOW_COUNTER
							).value(
								"false"
							).build())
					).build())));

		_assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings maxLength are not allowed for object field text",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"text"
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_LENGTH
							).value(
								null
							).build())
					).build())));

		_assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings maxLength are not allowed for object field text",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"text"
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_LENGTH
							).value(
								"10"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_SHOW_COUNTER
							).value(
								"false"
							).build())
					).build())));

		// Object field must be required when the state is true

		_assertFailure(
			ObjectFieldStateException.class,
			"Object field must be required when the state is true",
			() -> ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					new PicklistObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).listTypeDefinitionId(
						_listTypeDefinition.getListTypeDefinitionId()
					).name(
						"a" + RandomTestUtil.randomString()
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
							).value(
								_listTypeEntryKey
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_DEFAULT_VALUE_TYPE
							).value(
								ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
							).build())
					).state(
						true
					).build())));
	}

	@Test
	public void testAddSystemObjectField() throws Exception {

		// Business types

		List<ObjectFieldBusinessType> objectFieldBusinessTypes =
			_objectFieldBusinessTypeRegistry.getObjectFieldBusinessTypes();

		for (ObjectFieldBusinessType objectFieldBusinessType :
				objectFieldBusinessTypes) {

			if (Objects.equals(
					objectFieldBusinessType.getName(),
					ObjectFieldConstants.BUSINESS_TYPE_ENCRYPTED) ||
				Objects.equals(
					objectFieldBusinessType.getName(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST) ||
				Objects.equals(
					objectFieldBusinessType.getName(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

				continue;
			}

			_addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					objectFieldBusinessType.getName(), StringPool.BLANK, "Able",
					"able"));
		}

		_assertFailure(
			ObjectFieldBusinessTypeException.class,
			"Invalid business type businessType",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					"businessType", StringPool.BLANK, "Able", "able")));

		// Blob type is not indexable

		_assertFailure(
			ObjectFieldDBTypeException.class, "Blob type is not indexable",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_LARGE_FILE, null,
					ObjectFieldConstants.DB_TYPE_BLOB, true, false, "", "",
					"able", false, true)));

		// Indexed language ID can only be applied with type \"String\" that
		// is not indexed as a keyword

		String errorMessage =
			"Indexed language ID can only be applied with type \"Clob\" or " +
				"\"String\" that is not indexed as a keyword";

		_assertFailure(
			ObjectFieldDBTypeException.class, errorMessage,
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, null,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, "en_US", "",
					"able", false, true)));
		_assertFailure(
			ObjectFieldDBTypeException.class, errorMessage,
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, null,
					ObjectFieldConstants.DB_TYPE_LONG, true, true, "en_US", "",
					"able", false, true)));
		_assertFailure(
			ObjectFieldDBTypeException.class, errorMessage,
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, "en_US",
					"", 0, "able", Collections.emptyList(), false, true)));

		// Invalid DB type

		for (String dbType :
				_objectFieldBusinessTypeRegistry.getObjectFieldDBTypes()) {

			_addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					StringPool.BLANK, dbType, "Able", "able"));
		}

		_assertFailure(
			ObjectFieldDBTypeException.class, "Invalid DB type STRING",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					StringPool.BLANK, "STRING", "Able", "able")));

		// Label is null

		_assertFailure(
			ObjectFieldLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "", "able")));

		// Duplicate name

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "Able", "able")));

		_assertFailure(
			ObjectFieldNameException.class, "Duplicate name able",
			() -> _objectFieldLocalService.addSystemObjectField(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				ObjectFieldConstants.DB_TYPE_STRING, false, true, "",
				LocalizedMapUtil.getLocalizedMap("Able"), "able", false,
				false));

		// Name is null

		_assertFailure(
			ObjectFieldNameException.class, "Name is null",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "Able", "")));

		// Name must be less than 41 characters

		_addUnmodifiableSystemObjectDefinition(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING,
				"a123456789a123456789a123456789a1234567891"));

		_assertFailure(
			ObjectFieldNameException.class,
			"Name must be less than 41 characters",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					"a123456789a123456789a123456789a12345678912")));

		// Name must only contain letters and digits

		_addUnmodifiableSystemObjectDefinition(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, " able "));

		_assertFailure(
			ObjectFieldNameException.class,
			"Name must only contain letters and digits",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "abl e")));

		_assertFailure(
			ObjectFieldNameException.class,
			"Name must only contain letters and digits",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "abl-e")));

		// Reserved name

		String objectDefinitionName = "A" + RandomTestUtil.randomString();

		String pkObjectFieldName = TextFormatter.format(
			objectDefinitionName + "Id", TextFormatter.I);

		_assertFailure(
			ObjectFieldNameException.class,
			"Reserved name " + pkObjectFieldName,
			() -> _addUnmodifiableSystemObjectDefinition(
				objectDefinitionName,
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, pkObjectFieldName)));

		// The first character of a name must be an upper case letter

		_assertFailure(
			ObjectFieldNameException.class,
			"The first character of a name must be a lower case letter",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "Able")));
	}

	@Test
	public void testDeleteObjectField() throws Exception {

		// Delete object field from custom object definition

		ObjectDefinition customObjectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "able")));

		_assertDeleteObjectField(false, customObjectDefinition, "able");

		ObjectField ableObjectField = _addCustomObjectField(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"able"
			).objectDefinitionId(
				customObjectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Collections.emptyList()
			).build());

		Assert.assertFalse(
			_hasColumn(customObjectDefinition.getDBTableName(), "able_"));

		customObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				customObjectDefinition.getObjectDefinitionId());

		_assertFailure(
			RequiredObjectFieldException.class,
			"At least one custom field must be added",
			() -> _objectFieldLocalService.deleteObjectField(ableObjectField));

		Assert.assertTrue(
			_hasColumn(ableObjectField.getDBTableName(), "able_"));

		_addCustomObjectField(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"baker"
			).objectDefinitionId(
				customObjectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Collections.emptyList()
			).build());

		_assertDeleteObjectField(true, customObjectDefinition, "baker");

		// Delete object field business type attachment

		ObjectField uploadObjectField = _addCustomObjectField(
			new AttachmentObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"upload"
			).objectDefinitionId(
				customObjectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.
							NAME_ACCEPTED_FILE_EXTENSIONS
					).value(
						"txt"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_FILE_SOURCE
					).value(
						ObjectFieldSettingConstants.VALUE_USER_COMPUTER
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
					).value(
						"100"
					).build())
			).build());

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			customObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"upload",
				() -> {
					FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
						TestPropsValues.getGroupId(),
						TestPropsValues.getUserId(), StringUtil.randomString(),
						TempFileEntryUtil.getTempFileName(
							StringUtil.randomString() + ".txt"),
						FileUtil.createTempFile(RandomTestUtil.randomBytes()),
						ContentTypes.TEXT_PLAIN);

					return fileEntry.getFileEntryId();
				}
			).build(),
			ServiceContextTestUtil.getServiceContext());

		long persistedFileEntryId = MapUtil.getLong(
			objectEntry.getValues(), "upload");

		Assert.assertNotNull(
			_dlAppLocalService.getFileEntry(persistedFileEntryId));

		_objectFieldLocalService.deleteObjectField(
			uploadObjectField.getObjectFieldId());

		_assertFailure(
			NoSuchFileEntryException.class,
			StringBundler.concat(
				"No FileEntry exists with the key {fileEntryId=",
				persistedFileEntryId, "}"),
			() -> _dlAppLocalService.getFileEntry(persistedFileEntryId));

		//  Delete object field from system object definition

		ObjectDefinition systemObjectDefinition =
			_addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "able",
					Collections.emptyList()));

		ObjectField ableSystemObjectField =
			_objectFieldLocalService.fetchObjectField(
				systemObjectDefinition.getObjectDefinitionId(), "able");

		Assert.assertFalse(
			_hasColumn(ableSystemObjectField.getDBTableName(), "able_"));

		_assertFailure(
			RequiredObjectFieldException.class,
			"At least one custom field must be added",
			() -> _objectFieldLocalService.deleteObjectField(
				ableSystemObjectField));

		_addCustomObjectField(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"baker"
			).objectDefinitionId(
				systemObjectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Collections.emptyList()
			).build());

		_assertDeleteObjectField(true, systemObjectDefinition, "baker");

		_objectDefinitionLocalService.deleteObjectDefinition(
			customObjectDefinition);
		_objectDefinitionLocalService.deleteObjectDefinition(
			systemObjectDefinition);
	}

	@Test
	public void testObjectFieldSettings() throws Exception {

		// Business type attachment

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "text",
						StringUtil.randomId())));

		ObjectField attachmentObjectField = _addCustomObjectField(
			new AttachmentObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"upload"
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.
							NAME_ACCEPTED_FILE_EXTENSIONS
					).value(
						"jpg, png"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_FILE_SOURCE
					).value(
						ObjectFieldSettingConstants.VALUE_USER_COMPUTER
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
					).value(
						"100"
					).build())
			).build());

		_assertObjectFieldSettingsValues(
			attachmentObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_ACCEPTED_FILE_EXTENSIONS,
				"jpg, png"
			).put(
				ObjectFieldSettingConstants.NAME_FILE_SOURCE,
				ObjectFieldSettingConstants.VALUE_USER_COMPUTER
			).put(
				ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE, "100"
			).build());

		_updateCustomObjectField(
			attachmentObjectField,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_ACCEPTED_FILE_EXTENSIONS
				).value(
					"jpg"
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_FILE_SOURCE
				).value(
					ObjectFieldSettingConstants.VALUE_DOCS_AND_MEDIA
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
				).value(
					"10"
				).build()));

		_assertObjectFieldSettingsValues(
			attachmentObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_ACCEPTED_FILE_EXTENSIONS, "jpg"
			).put(
				ObjectFieldSettingConstants.NAME_FILE_SOURCE,
				ObjectFieldSettingConstants.VALUE_DOCS_AND_MEDIA
			).put(
				ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE, "10"
			).build());

		// Business type integer

		ObjectField integerObjectField = _addCustomObjectField(
			new IntegerObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"integer"
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
					).value(
						"TRUE"
					).build())
			).build());

		_assertObjectFieldSettingsValues(
			integerObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_UNIQUE_VALUES, "TRUE"
			).build());

		_updateCustomObjectField(
			integerObjectField,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
				).value(
					"False"
				).build()));

		_assertObjectFieldSettingsValues(
			integerObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_UNIQUE_VALUES, "False"
			).build());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		_assertFailure(
			ObjectFieldSettingValueException.UnmodifiableValue.class,
			"The value of setting uniqueValues is unmodifiable when object " +
				"definition is published",
			() -> _updateCustomObjectField(
				integerObjectField,
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
					).value(
						"true"
					).build())));

		// Business type picklist

		ObjectField picklistObjectField = _addPicklistObjectField(
			objectDefinition, false, false);

		_assertObjectFieldSettingsValues(
			picklistObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				_listTypeEntryKey
			).put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
			).build());

		_assertObjectEntryDefaultValue(
			_listTypeEntryKey, picklistObjectField, new HashMap<>());

		_updateCustomObjectField(
			picklistObjectField,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
				).value(
					"text"
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE
				).value(
					ObjectFieldSettingConstants.VALUE_EXPRESSION_BUILDER
				).build()));

		_assertObjectFieldSettingsValues(
			picklistObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE, "text"
			).put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				ObjectFieldSettingConstants.VALUE_EXPRESSION_BUILDER
			).build());

		_assertObjectEntryDefaultValue(
			_listTypeEntryKey, picklistObjectField,
			HashMapBuilder.<String, Serializable>put(
				"text", _listTypeEntryKey
			).build());

		picklistObjectField = _addPicklistObjectField(
			objectDefinition, true, true);

		_assertObjectFieldSettingsValues(
			picklistObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
				_listTypeEntryKey
			).put(
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
			).build());

		_assertObjectEntryDefaultValue(
			_listTypeEntryKey, picklistObjectField, new HashMap<>());

		// Business type text

		ObjectField textObjectField = _addCustomObjectField(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_SHOW_COUNTER
					).value(
						"false"
					).build())
			).build());

		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				textObjectField.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_ACCEPTED_FILE_EXTENSIONS));
		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				textObjectField.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_FILE_SOURCE));
		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				textObjectField.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE));

		_updateCustomObjectField(
			textObjectField,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_MAX_LENGTH
				).value(
					"10"
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_SHOW_COUNTER
				).value(
					"true"
				).build()));

		_assertObjectFieldSettingsValues(
			textObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_MAX_LENGTH, "10"
			).put(
				ObjectFieldSettingConstants.NAME_SHOW_COUNTER, "true"
			).build());

		_updateCustomObjectField(
			textObjectField,
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_SHOW_COUNTER
				).value(
					"false"
				).build()));

		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				textObjectField.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_MAX_LENGTH));

		_assertObjectFieldSettingsValues(
			textObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_SHOW_COUNTER, "false"
			).build());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testUpdateCustomObjectField() throws Exception {
		ObjectFieldBuilder objectFieldBuilder =
			new LongIntegerObjectFieldBuilder();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					objectFieldBuilder.userId(
						TestPropsValues.getUserId()
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
					).build()));

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), "able");

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
			).listTypeDefinitionId(
				_listTypeDefinition.getListTypeDefinitionId()
			).build());

		_testUpdateCustomObjectField(
			objectFieldBuilder.objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
					).value(
						_listTypeEntryKey
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE
					).value(
						ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
					).build())
			).required(
				true
			).state(
				true
			).build());

		_testUpdateCustomObjectField(
			objectFieldBuilder.businessType(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT
			).dbColumnName(
				"baker_"
			).indexed(
				true
			).indexedLanguageId(
				LanguageUtil.getLanguageId(LocaleUtil.getDefault())
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("baker")
			).listTypeDefinitionId(
				0
			).name(
				"baker"
			).objectFieldSettings(
				Collections.emptyList()
			).state(
				false
			).build());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		objectField = _updateCustomObjectField(
			objectFieldBuilder.businessType(
				ObjectFieldConstants.BUSINESS_TYPE_INTEGER
			).dbType(
				ObjectFieldConstants.DB_TYPE_INTEGER
			).indexed(
				false
			).indexedAsKeyword(
				true
			).indexedLanguageId(
				StringPool.BLANK
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("charlie")
			).name(
				"charlie"
			).objectFieldSettings(
				Collections.emptyList()
			).build());

		Assert.assertEquals("baker_", objectField.getDBColumnName());
		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_STRING, objectField.getDBType());
		Assert.assertFalse(objectField.isIndexed());
		Assert.assertTrue(objectField.isIndexedAsKeyword());
		Assert.assertEquals(
			StringPool.BLANK, objectField.getIndexedLanguageId());
		Assert.assertEquals(
			objectField.getLabelMap(),
			LocalizedMapUtil.getLocalizedMap("charlie"));
		Assert.assertEquals("baker", objectField.getName());
		Assert.assertTrue(objectField.isRequired());

		// Object field label needs to be replicated when there is an update
		// with another default language

		LocaleUtil.setDefault(
			LocaleUtil.GERMANY.getLanguage(), LocaleUtil.GERMANY.getCountry(),
			LocaleUtil.GERMANY.getVariant());

		objectField = _updateCustomObjectField(
			objectField, objectField.getObjectFieldSettings());

		Map<Locale, String> labelMap = objectField.getLabelMap();

		Assert.assertEquals(
			labelMap.get(LocaleUtil.GERMANY), labelMap.get(LocaleUtil.US));

		// Object field relationship name and DB type cannot be changed

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService);

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				relatedObjectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"relationship", ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField relationshipObjectField =
			_objectFieldLocalService.fetchObjectField(
				relatedObjectDefinition.getObjectDefinitionId(),
				"r_relationship_" + objectDefinition.getPKObjectFieldName());

		_assertFailure(
			ObjectFieldRelationshipTypeException.class,
			"Object field relationship name and DB type cannot be changed",
			() -> _updateCustomObjectField(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"able"
				).objectFieldId(
					relationshipObjectField.getObjectFieldId()
				).objectFieldSettings(
					Collections.emptyList()
				).build()));

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
		_objectDefinitionLocalService.deleteObjectDefinition(
			relatedObjectDefinition);
	}

	@Test
	public void testUpdateRequired() throws Exception {

		// Deletion type cascade

		ObjectDefinition objectDefinition1 = _publishCustomObjectDefinition();
		ObjectDefinition objectDefinition2 = _publishCustomObjectDefinition();

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinition1.getObjectDefinitionId(),
				objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField objectField = _objectFieldLocalService.updateRequired(
			objectRelationship.getObjectFieldId2(), true);

		Assert.assertTrue(objectField.isRequired());

		// Deletion type disassociate

		_objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship.getObjectRelationshipId(),
			objectRelationship.getParameterObjectFieldId(),
			ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
			objectRelationship.getLabelMap());

		objectField = _objectFieldLocalService.fetchObjectField(
			objectRelationship.getObjectFieldId2());

		Assert.assertFalse(objectField.isRequired());

		_assertFailure(
			PortalException.class,
			"Object field cannot be required because the relationship " +
				"deletion type is disassociate",
			() -> _objectFieldLocalService.updateRequired(
				objectRelationship.getObjectFieldId2(), true));

		// Deletion type prevent

		_objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship.getObjectRelationshipId(),
			objectRelationship.getParameterObjectFieldId(),
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			objectRelationship.getLabelMap());

		objectField = _objectFieldLocalService.updateRequired(
			objectRelationship.getObjectFieldId2(), true);

		Assert.assertTrue(objectField.isRequired());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition2);
	}

	private ObjectField _addCustomObjectField(ObjectField objectField)
		throws Exception {

		return _objectFieldLocalService.addCustomObjectField(
			objectField.getExternalReferenceCode(), TestPropsValues.getUserId(),
			objectField.getListTypeDefinitionId(),
			objectField.getObjectDefinitionId(), objectField.getBusinessType(),
			objectField.getDBType(), objectField.isIndexed(),
			objectField.isIndexedAsKeyword(),
			objectField.getIndexedLanguageId(), objectField.getLabelMap(),
			objectField.isLocalized(), objectField.getName(),
			objectField.isRequired(), objectField.isState(),
			objectField.getObjectFieldSettings());
	}

	private ObjectField _addPicklistObjectField(
			ObjectDefinition objectDefinition, boolean required, boolean state)
		throws Exception {

		return _addCustomObjectField(
			new PicklistObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).listTypeDefinitionId(
				_listTypeDefinition.getListTypeDefinitionId()
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
					).value(
						_listTypeEntryKey
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE
					).value(
						ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
					).build())
			).required(
				required
			).state(
				state
			).build());
	}

	private ObjectDefinition _addUnmodifiableSystemObjectDefinition(
			ObjectField... objectFields)
		throws Exception {

		return _addUnmodifiableSystemObjectDefinition(
			"A" + RandomTestUtil.randomString(), objectFields);
	}

	private ObjectDefinition _addUnmodifiableSystemObjectDefinition(
			String objectDefinitionName, ObjectField... objectFields)
		throws Exception {

		return ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			objectDefinitionName, null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
			_objectDefinitionLocalService, Arrays.asList(objectFields));
	}

	private void _assertDeleteObjectField(
			boolean hasColumn, ObjectDefinition objectDefinition,
			String objectFieldName)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), objectFieldName);

		Assert.assertEquals(
			hasColumn,
			_hasColumn(
				objectField.getDBTableName(), objectField.getDBColumnName()));

		_objectFieldLocalService.deleteObjectField(
			objectField.getObjectFieldId());

		Assert.assertNull(
			_objectFieldLocalService.fetchObjectField(
				objectDefinition.getObjectDefinitionId(), objectFieldName));

		Assert.assertFalse(
			_hasColumn(
				objectField.getDBTableName(), objectField.getDBColumnName()));
	}

	private void _assertFailure(
		Class<?> clazz, String expectedMessage,
		UnsafeSupplier<Object, Exception> unsafeSupplier) {

		try {
			unsafeSupplier.get();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(expectedMessage, exception.getMessage());
			Assert.assertTrue(clazz.isInstance(exception));
		}
	}

	private void _assertObjectEntryDefaultValue(
			String expectedDefaultValue, ObjectField objectField,
			Map<String, Serializable> values)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0, objectField.getObjectDefinitionId(),
			values, ServiceContextTestUtil.getServiceContext());

		values = objectEntry.getValues();

		Assert.assertEquals(
			expectedDefaultValue, values.get(objectField.getName()));
	}

	private void _assertObjectFieldSettingsValues(
			long objectFieldId, Map<String, String> objectFieldSettingsValues)
		throws Exception {

		for (Map.Entry<String, String> entry :
				objectFieldSettingsValues.entrySet()) {

			ObjectFieldSetting objectFieldSetting =
				_objectFieldSettingLocalService.fetchObjectFieldSetting(
					objectFieldId, entry.getKey());

			Assert.assertEquals(
				entry.getValue(), objectFieldSetting.getValue());
		}
	}

	private boolean _hasColumn(String tableName, String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasColumn(tableName, columnName);
		}
	}

	private ObjectDefinition _publishCustomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private void _testUpdateCustomObjectField(ObjectField expectedObjectField)
		throws Exception {

		ObjectField objectField = _updateCustomObjectField(
			expectedObjectField, expectedObjectField.getObjectFieldSettings());

		Assert.assertEquals(
			expectedObjectField.getExternalReferenceCode(),
			objectField.getExternalReferenceCode());
		Assert.assertEquals(
			expectedObjectField.getDBColumnName(),
			objectField.getDBColumnName());
		Assert.assertEquals(
			expectedObjectField.getDBType(), objectField.getDBType());
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

	private ObjectField _updateCustomObjectField(ObjectField objectField)
		throws Exception {

		return _updateCustomObjectField(objectField, Collections.emptyList());
	}

	private ObjectField _updateCustomObjectField(
			ObjectField objectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws Exception {

		return _objectFieldLocalService.updateCustomObjectField(
			objectField.getExternalReferenceCode(),
			objectField.getObjectFieldId(),
			objectField.getListTypeDefinitionId(),
			objectField.getBusinessType(), objectField.getDBType(),
			objectField.isIndexed(), objectField.isIndexedAsKeyword(),
			objectField.getIndexedLanguageId(), objectField.getLabelMap(),
			objectField.isLocalized(), objectField.getName(),
			objectField.isRequired(), objectField.isState(),
			objectFieldSettings);
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private String _listTypeEntryKey;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}