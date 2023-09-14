/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.object.exception.ObjectFieldReadOnlyConditionExpressionException;
import com.liferay.object.exception.ObjectFieldReadOnlyException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.ObjectFieldRequiredException;
import com.liferay.object.exception.ObjectFieldSettingNameException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.exception.ObjectFieldStateException;
import com.liferay.object.exception.ObjectFieldSystemException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.field.builder.AggregationObjectFieldBuilder;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.EncryptedObjectFieldBuilder;
import com.liferay.object.field.builder.FormulaObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.MultiselectPicklistObjectFieldBuilder;
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
import com.liferay.object.service.test.util.ObjectFieldTestUtil;
import com.liferay.object.service.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.test.util.TreeTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.AssertUtils;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
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
import java.util.Set;

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
@FeatureFlags("LPS-187142")
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
				false,
				Collections.singletonList(
					ListTypeEntryUtil.createListTypeEntry(_listTypeEntryKey)));
	}

	@Test
	public void testAddCustomObjectField() throws Exception {
		AssertUtils.assertFailure(
			ObjectDefinitionEnableLocalizationException.class, null,
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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

		AssertUtils.assertFailure(
			ObjectFieldBusinessTypeException.class,
			"Business type encrypted can only be used in object definitions " +
				"with a default storage type",
			() -> _addCustomObjectDefinitionWithEncryptedObjectField(
				"AES", true, ObjectFieldTestUtil.generateKey("AES"),
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE));

		AssertUtils.assertFailure(
			ObjectFieldBusinessTypeException.class,
			"Business type encrypted is disabled",
			() -> _addCustomObjectDefinitionWithEncryptedObjectField(
				"", false, "", ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT));
		AssertUtils.assertFailure(
			ObjectFieldBusinessTypeException.class,
			"Encryption algorithm is required for business type encrypted",
			() -> _addCustomObjectDefinitionWithEncryptedObjectField(
				"", true, ObjectFieldTestUtil.generateKey("AES"),
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT));
		AssertUtils.assertFailure(
			ObjectFieldBusinessTypeException.class,
			"Encryption key is required for business type encrypted",
			() -> _addCustomObjectDefinitionWithEncryptedObjectField(
				"AES", true, "",
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT));
		AssertUtils.assertFailure(
			ObjectFieldBusinessTypeException.class,
			"Salesforce storage type does not support aggregation and " +
				"attachment business types",
			() -> _objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Arrays.asList(
					new AggregationObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).objectFieldSettings(
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								"function"
							).value(
								"MAX"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								"objectFieldName"
							).value(
								"integer"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								"objectRelationshipName"
							).value(
								"oneToManyRelationshipName"
							).build())
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldBusinessTypeException.class,
			"Salesforce storage type does not support aggregation and " +
				"attachment business types",
			() -> _objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Arrays.asList(
					new AttachmentObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).listTypeDefinitionId(
						_listTypeDefinition.getListTypeDefinitionId()
					).name(
						"a" + RandomTestUtil.randomString()
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldListTypeDefinitionIdException.class,
			"List type definition ID is 0",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					new MultiselectPicklistObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldLocalizedException.class,
			"Localized object fields must not be required",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				true, _objectDefinitionLocalService,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).localized(
						true
					).required(
						true
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldLocalizedException.class,
			"Only LongText,RichText and Text business types support " +
				"localization",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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

		String[] reservedNames = {
			"actions", "companyId", "createDate", "creator", "dateCreated",
			"dateModified", "externalReferenceCode", "groupId", "id",
			"lastPublishDate", "modifiedDate", "statusByUserId",
			"statusByUserName", "statusDate", "userId", "userName"
		};

		for (String reservedName : reservedNames) {
			AssertUtils.assertFailure(
				ObjectFieldNameException.MustNotBeReserved.class,
				"Reserved name " + reservedName,
				() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
					false, _objectDefinitionLocalService,
					Arrays.asList(
						new TextObjectFieldBuilder(
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							reservedName
						).build())));
		}

		String defaultValue = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			"The value " + defaultValue +
				" of setting defaultValue is invalid for object field picklist",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			"The value " + uniqueValues +
				" of setting uniqueValues is invalid for object field text",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.InvalidValue.class,
			"The value expressionBuilder of setting defaultValueType is " +
				"invalid for object field picklist",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			"The settings acceptedFileExtensions, fileSource, " +
				"maximumFileSize are required for object field upload",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					new AttachmentObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"upload"
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			"The settings defaultValue, defaultValueType are required for " +
				"object field picklist",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					new PicklistObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).listTypeDefinitionId(
						_listTypeDefinition.getListTypeDefinitionId()
					).name(
						"picklist"
					).required(
						true
					).state(
						true
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			"The settings maxLength are required for object field text",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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
		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.MissingRequiredValues.class,
			"The settings timeStorage are required for object field datetime",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Collections.singletonList(
					new ObjectFieldBuilder(
					).businessType(
						ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME
					).dbType(
						ObjectFieldConstants.DB_TYPE_DATE_TIME
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"datetime"
					).objectFieldSettings(
						Collections.emptyList()
					).build())));
		AssertUtils.assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings anySetting are not allowed for object field text",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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
		AssertUtils.assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings defaultValue, defaultValueType are not allowed for " +
				"object field text",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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
		AssertUtils.assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings maxLength are not allowed for object field text",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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
		AssertUtils.assertFailure(
			ObjectFieldSettingNameException.NotAllowedNames.class,
			"The settings maxLength are not allowed for object field text",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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
		AssertUtils.assertFailure(
			ObjectFieldStateException.class,
			"Object field must be required when the state is true",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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

		_testAddCustomObjectFieldReadOnly();
	}

	@Test
	public void testAddOrUpdateCustomObjectField() throws Exception {
		ObjectFieldBuilder objectFieldBuilder =
			new LongIntegerObjectFieldBuilder();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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
					).build()));

		ObjectField objectField1 = _objectFieldLocalService.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), "able");

		Assert.assertEquals("able_", objectField1.getDBColumnName());
		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_LONG, objectField1.getDBType());
		Assert.assertFalse(objectField1.isIndexed());
		Assert.assertTrue(objectField1.isIndexedAsKeyword());
		Assert.assertEquals("", objectField1.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("able"),
			objectField1.getLabelMap());
		Assert.assertEquals("able", objectField1.getName());
		Assert.assertFalse(objectField1.isRequired());

		_testAddOrUpdateCustomObjectField(
			objectFieldBuilder.dbColumnName(
				objectField1.getDBColumnName()
			).objectFieldId(
				objectField1.getObjectFieldId()
			).externalReferenceCode(
				objectField1.getExternalReferenceCode()
			).build());

		_testAddOrUpdateCustomObjectField(
			objectFieldBuilder.businessType(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST
			).dbType(
				ObjectFieldConstants.DB_TYPE_STRING
			).indexedAsKeyword(
				false
			).listTypeDefinitionId(
				_listTypeDefinition.getListTypeDefinitionId()
			).build());

		_testAddOrUpdateCustomObjectField(
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

		_testAddOrUpdateCustomObjectField(
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

		objectField1 = _addOrUpdateCustomObjectField(
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
			).build());

		Assert.assertEquals("baker_", objectField1.getDBColumnName());
		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_STRING, objectField1.getDBType());
		Assert.assertFalse(objectField1.isIndexed());
		Assert.assertTrue(objectField1.isIndexedAsKeyword());
		Assert.assertEquals(
			StringPool.BLANK, objectField1.getIndexedLanguageId());
		Assert.assertEquals(
			objectField1.getLabelMap(),
			LocalizedMapUtil.getLocalizedMap("charlie"));
		Assert.assertEquals("baker", objectField1.getName());
		Assert.assertTrue(objectField1.isRequired());

		// Object field label needs to be replicated when there is an update
		// with another default language

		Locale defaultLocale = LocaleUtil.getDefault();

		LocaleUtil.setDefault(
			LocaleUtil.GERMANY.getLanguage(), LocaleUtil.GERMANY.getCountry(),
			LocaleUtil.GERMANY.getVariant());

		objectField1 = _addOrUpdateCustomObjectField(
			objectField1, objectField1.getObjectFieldSettings());

		Map<Locale, String> labelMap = objectField1.getLabelMap();

		Assert.assertEquals(
			labelMap.get(LocaleUtil.GERMANY), labelMap.get(LocaleUtil.US));

		LocaleUtil.setDefault(
			defaultLocale.getLanguage(), defaultLocale.getCountry(),
			defaultLocale.getVariant());

		// Object field read only

		objectDefinition = ObjectDefinitionTestUtil.addCustomObjectDefinition(
			false, _objectDefinitionLocalService, Collections.emptyList());

		objectField1 = _addCustomObjectField(
			_getReadOnlyTextObjectField(
				objectDefinition.getObjectDefinitionId(), null, null));

		ObjectField finalObjectField = objectField1;

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyConditionExpressionException.class,
			"Read only condition expression is required",
			() -> _updateReadOnlyObjectField(
				finalObjectField, ObjectFieldConstants.READ_ONLY_CONDITIONAL,
				null));

		String invalidDDMScript = RandomTestUtil.randomString() + "()";

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyConditionExpressionException.class,
			"Syntax error in: " + invalidDDMScript,
			() -> _updateReadOnlyObjectField(
				finalObjectField, ObjectFieldConstants.READ_ONLY_CONDITIONAL,
				invalidDDMScript));

		String invalidReadOnly = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyException.class,
			"Unknown read only: " + invalidReadOnly,
			() -> _updateReadOnlyObjectField(
				finalObjectField, invalidReadOnly, null));

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					_getIntegerObjectField(0, Collections.emptyList())));

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			relatedObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"oneToManyRelationshipName", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertReadOnly(
			invalidDDMScript, invalidReadOnly,
			_addReadOnlyAggregationObjectField(
				objectDefinition.getObjectDefinitionId(), null, null));
		_assertReadOnly(
			invalidDDMScript, invalidReadOnly,
			_addReadOnlyFormulaObjectField(
				objectDefinition.getObjectDefinitionId(), null, null));

		// Object field relationship

		relatedObjectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				_objectDefinitionLocalService);

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				relatedObjectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"relationship", false,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField relationshipObjectField =
			_objectFieldLocalService.fetchObjectField(
				relatedObjectDefinition.getObjectDefinitionId(),
				"r_relationship_" + objectDefinition.getPKObjectFieldName());

		long relationshipObjectFieldId =
			relationshipObjectField.getObjectFieldId();

		relationshipObjectField = _addOrUpdateCustomObjectField(
			relationshipObjectField,
			Arrays.asList(
				_objectFieldSettingLocalService.fetchObjectFieldSetting(
					relationshipObjectFieldId,
					ObjectFieldSettingConstants.
						NAME_OBJECT_DEFINITION_1_SHORT_NAME),
				_objectFieldSettingLocalService.fetchObjectFieldSetting(
					relationshipObjectFieldId,
					ObjectFieldSettingConstants.
						NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME)));

		String relationshipObjectFieldDBColumnName =
			relationshipObjectField.getDBColumnName();

		Assert.assertNotEquals(
			StringPool.UNDERLINE,
			relationshipObjectFieldDBColumnName.charAt(
				relationshipObjectFieldDBColumnName.length() - 1));

		AssertUtils.assertFailure(
			ObjectFieldRelationshipTypeException.class,
			"Object field relationship name and DB type cannot be changed",
			() -> _addOrUpdateCustomObjectField(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"able"
				).objectFieldId(
					relationshipObjectFieldId
				).build()));

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
		_objectDefinitionLocalService.deleteObjectDefinition(
			relatedObjectDefinition);

		objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService,
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				"A", _objectDefinitionLocalService),
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				"AA", _objectDefinitionLocalService));

		TreeTestUtil.bind(
			_objectDefinitionLocalService,
			Collections.singletonList(objectRelationship));

		ObjectField objectField2 = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		objectField2.setRequired(false);

		AssertUtils.assertFailure(
			ObjectFieldRequiredException.class, null,
			() -> _addOrUpdateCustomObjectField(objectField2));

		TreeTestUtil.deleteObjectDefinitionHierarchy(
			_objectDefinitionLocalService, new String[] {"C_A", "C_AA"});
	}

	@Test
	public void testAddOrUpdateSystemObjectField() throws Exception {
		ObjectDefinition modifiableSystemObjectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				_objectDefinitionLocalService, Collections.emptyList());

		ObjectField systemObjectField =
			_objectFieldLocalService.addOrUpdateSystemObjectField(
				null, TestPropsValues.getUserId(), 0L,
				modifiableSystemObjectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT, null, null,
				ObjectFieldConstants.DB_TYPE_STRING, false, false, "",
				LocalizedMapUtil.getLocalizedMap("Able"), "able",
				ObjectFieldConstants.READ_ONLY_FALSE, null, false, false,
				Collections.emptyList());

		Assert.assertEquals("able", systemObjectField.getDBColumnName());
		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_STRING, systemObjectField.getDBType());
		Assert.assertFalse(systemObjectField.isIndexed());
		Assert.assertFalse(systemObjectField.isIndexedAsKeyword());
		Assert.assertEquals(
			StringPool.BLANK, systemObjectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Able"),
			systemObjectField.getLabelMap());
		Assert.assertEquals("able", systemObjectField.getName());
		Assert.assertFalse(systemObjectField.isRequired());
		Assert.assertTrue(systemObjectField.isSystem());

		systemObjectField =
			_objectFieldLocalService.addOrUpdateSystemObjectField(
				systemObjectField.getExternalReferenceCode(),
				TestPropsValues.getUserId(), 0L,
				modifiableSystemObjectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				ObjectFieldConstants.DB_TYPE_STRING, true, true, "",
				LocalizedMapUtil.getLocalizedMap("Baker"), "able",
				ObjectFieldConstants.READ_ONLY_FALSE, null, true, false,
				Collections.emptyList());

		Assert.assertEquals("able_", systemObjectField.getDBColumnName());
		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_STRING, systemObjectField.getDBType());
		Assert.assertTrue(systemObjectField.isIndexed());
		Assert.assertTrue(systemObjectField.isIndexedAsKeyword());
		Assert.assertEquals(
			StringPool.BLANK, systemObjectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Baker"),
			systemObjectField.getLabelMap());
		Assert.assertEquals("able", systemObjectField.getName());
		Assert.assertTrue(systemObjectField.isRequired());
		Assert.assertTrue(systemObjectField.isSystem());

		// Requests from forbidden bundles can only update the label

		String liferayMode = SystemProperties.get("liferay.mode");

		SystemProperties.clear("liferay.mode");

		try {
			systemObjectField =
				_objectFieldLocalService.addOrUpdateSystemObjectField(
					RandomTestUtil.randomString(), TestPropsValues.getUserId(),
					0L,
					modifiableSystemObjectDefinition.getObjectDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					ObjectFieldConstants.DB_TYPE_STRING, false, false, "",
					LocalizedMapUtil.getLocalizedMap("Charlie"), "able",
					ObjectFieldConstants.READ_ONLY_FALSE, null, false, false,
					Collections.emptyList());

			Assert.assertEquals("able_", systemObjectField.getDBColumnName());
			Assert.assertEquals(
				ObjectFieldConstants.DB_TYPE_STRING,
				systemObjectField.getDBType());
			Assert.assertTrue(systemObjectField.isIndexed());
			Assert.assertTrue(systemObjectField.isIndexedAsKeyword());
			Assert.assertEquals(
				StringPool.BLANK, systemObjectField.getIndexedLanguageId());
			Assert.assertEquals(
				LocalizedMapUtil.getLocalizedMap("Charlie"),
				systemObjectField.getLabelMap());
			Assert.assertEquals("able", systemObjectField.getName());
			Assert.assertTrue(systemObjectField.isRequired());
			Assert.assertTrue(systemObjectField.isSystem());
		}
		finally {
			SystemProperties.set("liferay.mode", liferayMode);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			modifiableSystemObjectDefinition);
	}

	@Test
	public void testAddSystemObjectField() throws Exception {
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

		AssertUtils.assertFailure(
			ObjectFieldBusinessTypeException.class,
			"Invalid business type businessType",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					"businessType", StringPool.BLANK, "Able", "able")));

		for (String dbType :
				_objectFieldBusinessTypeRegistry.getObjectFieldDBTypes()) {

			_addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					StringPool.BLANK, dbType, "Able", "able"));
		}

		AssertUtils.assertFailure(
			ObjectFieldDBTypeException.class, "Blob type is not indexable",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_LARGE_FILE, null,
					ObjectFieldConstants.DB_TYPE_BLOB, true, false, "", "",
					"able", false, true)));

		String errorMessage =
			"Indexed language ID can only be applied with type \"Clob\" or " +
				"\"String\" that is not indexed as a keyword";

		AssertUtils.assertFailure(
			ObjectFieldDBTypeException.class, errorMessage,
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, null,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, "en_US", "",
					"able", false, true)));
		AssertUtils.assertFailure(
			ObjectFieldDBTypeException.class, errorMessage,
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, null,
					ObjectFieldConstants.DB_TYPE_LONG, true, true, "en_US", "",
					"able", false, true)));
		AssertUtils.assertFailure(
			ObjectFieldDBTypeException.class, errorMessage,
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, "en_US",
					"", 0, "able", Collections.emptyList(),
					ObjectFieldConstants.READ_ONLY_FALSE, null, false, true)));

		AssertUtils.assertFailure(
			ObjectFieldDBTypeException.class, "Invalid DB type STRING",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					StringPool.BLANK, "STRING", "Able", "able")));
		AssertUtils.assertFailure(
			ObjectFieldLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "", "able")));

		_addUnmodifiableSystemObjectDefinition(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, " able "));
		_addUnmodifiableSystemObjectDefinition(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING,
				"a123456789a123456789a123456789a1234567891"));

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "Able", "able")));

		AssertUtils.assertFailure(
			ObjectFieldNameException.MustBeLessThan41Characters.class,
			"Name must be less than 41 characters",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					"a123456789a123456789a123456789a12345678912")));
		AssertUtils.assertFailure(
			ObjectFieldNameException.MustBeginWithLowerCaseLetter.class,
			"The first character of a name must be a lower case letter",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "Able")));
		AssertUtils.assertFailure(
			ObjectFieldNameException.MustNotBeDuplicate.class,
			"Duplicate name able",
			() -> _addSystemObjectField(
				objectDefinition.getObjectDefinitionId(), "able", null));
		AssertUtils.assertFailure(
			ObjectFieldNameException.MustNotBeNull.class, "Name is null",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "Able", "")));
		AssertUtils.assertFailure(
			ObjectFieldNameException.MustOnlyContainLettersAndDigits.class,
			"Name must only contain letters and digits",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "abl e")));
		AssertUtils.assertFailure(
			ObjectFieldNameException.MustOnlyContainLettersAndDigits.class,
			"Name must only contain letters and digits",
			() -> _addUnmodifiableSystemObjectDefinition(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "abl-e")));

		ObjectDefinition modifiableSystemObjectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				_objectDefinitionLocalService, Collections.emptyList());

		AssertUtils.assertFailure(
			ObjectFieldSystemException.class, false,
			"Only allowed bundles can add system object fields",
			() -> _addSystemObjectField(
				modifiableSystemObjectDefinition.getObjectDefinitionId(),
				"able", Collections.emptyList()));

		String objectDefinitionName = "A" + RandomTestUtil.randomString();

		String pkObjectFieldName = TextFormatter.format(
			objectDefinitionName + "Id", TextFormatter.I);

		AssertUtils.assertFailure(
			ObjectFieldNameException.MustNotBeReserved.class,
			"Reserved name " + pkObjectFieldName,
			() -> _addUnmodifiableSystemObjectDefinition(
				objectDefinitionName,
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, pkObjectFieldName)));

		_objectDefinitionLocalService.deleteObjectDefinition(
			modifiableSystemObjectDefinition);
	}

	@Test
	public void testDeleteObjectField() throws Exception {

		// Delete object field from custom object definition

		ObjectDefinition customObjectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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
			).build());

		Assert.assertFalse(
			_hasColumn(customObjectDefinition.getDBTableName(), "able_"));

		customObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				customObjectDefinition.getObjectDefinitionId());

		AssertUtils.assertFailure(
			RequiredObjectFieldException.class,
			"At least one object field must be added",
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
			).build());

		_assertDeleteObjectField(true, customObjectDefinition, "able");

		_addCustomObjectField(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"charlie"
			).objectDefinitionId(
				customObjectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Collections.emptyList()
			).build());

		_assertDeleteObjectField(true, customObjectDefinition, "charlie");

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

		AssertUtils.assertFailure(
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

		AssertUtils.assertFailure(
			RequiredObjectFieldException.class,
			"At least one object field must be added",
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
			).build());

		_assertDeleteObjectField(true, systemObjectDefinition, "baker");

		_objectDefinitionLocalService.deleteObjectDefinition(
			customObjectDefinition);
		_objectDefinitionLocalService.deleteObjectDefinition(
			systemObjectDefinition);

		// Delete system object field from modifiable system object definition

		ObjectDefinition modifiableSystemObjectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				_objectDefinitionLocalService, Collections.emptyList());

		ObjectField systemObjectField1 = _addSystemObjectField(
			modifiableSystemObjectDefinition.getObjectDefinitionId(),
			StringUtil.randomId(), Collections.emptyList());

		AssertUtils.assertFailure(
			ObjectFieldSystemException.class, false,
			"Only allowed bundles can delete system object fields",
			() -> _objectFieldLocalService.deleteObjectField(
				systemObjectField1));

		_assertDeleteObjectField(
			false, modifiableSystemObjectDefinition,
			systemObjectField1.getName());

		ObjectField systemObjectField2 = _addSystemObjectField(
			modifiableSystemObjectDefinition.getObjectDefinitionId(),
			StringUtil.randomId(), Collections.emptyList());

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			TestPropsValues.getUserId(),
			modifiableSystemObjectDefinition.getObjectDefinitionId());

		AssertUtils.assertFailure(
			RequiredObjectFieldException.class,
			"At least one object field must be added",
			() -> _objectFieldLocalService.deleteObjectField(
				systemObjectField2));

		_addSystemObjectField(
			modifiableSystemObjectDefinition.getObjectDefinitionId(), "able",
			Collections.emptyList());

		_assertDeleteObjectField(
			true, modifiableSystemObjectDefinition, "able");

		_objectDefinitionLocalService.deleteObjectDefinition(
			modifiableSystemObjectDefinition);
	}

	@Test
	public void testObjectFieldSettings() throws Exception {

		// Business type attachment

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
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

		_addOrUpdateCustomObjectField(
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
			_getIntegerObjectField(
				objectDefinition.getObjectDefinitionId(),
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
					).value(
						"TRUE"
					).build())));

		_assertObjectFieldSettingsValues(
			integerObjectField.getObjectFieldId(),
			HashMapBuilder.put(
				ObjectFieldSettingConstants.NAME_UNIQUE_VALUES, "TRUE"
			).build());

		_addOrUpdateCustomObjectField(
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

		AssertUtils.assertFailure(
			ObjectFieldSettingValueException.UnmodifiableValue.class,
			"The value of setting uniqueValues is unmodifiable when object " +
				"definition is published",
			() -> _addOrUpdateCustomObjectField(
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

		_addOrUpdateCustomObjectField(
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

		_addOrUpdateCustomObjectField(
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

		_addOrUpdateCustomObjectField(
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
				StringUtil.randomId(), false,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField objectField = _objectFieldLocalService.updateRequired(
			objectRelationship.getObjectFieldId2(), true);

		Assert.assertTrue(objectField.isRequired());

		// Deletion type disassociate

		_objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship.getObjectRelationshipId(),
			objectRelationship.getParameterObjectFieldId(),
			ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE, false,
			objectRelationship.getLabelMap());

		objectField = _objectFieldLocalService.fetchObjectField(
			objectRelationship.getObjectFieldId2());

		Assert.assertFalse(objectField.isRequired());

		AssertUtils.assertFailure(
			PortalException.class,
			"Object field cannot be required because the relationship " +
				"deletion type is disassociate",
			() -> _objectFieldLocalService.updateRequired(
				objectRelationship.getObjectFieldId2(), true));

		// Deletion type prevent

		_objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship.getObjectRelationshipId(),
			objectRelationship.getParameterObjectFieldId(),
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT, false,
			objectRelationship.getLabelMap());

		objectField = _objectFieldLocalService.updateRequired(
			objectRelationship.getObjectFieldId2(), true);

		Assert.assertTrue(objectField.isRequired());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition2);
	}

	private void _addCustomObjectDefinitionWithEncryptedObjectField(
			String algorithm, boolean enabled, String key, String storageType)
		throws Exception {

		ObjectFieldTestUtil.withEncryptedObjectFieldProperties(
			algorithm, enabled, key,
			() -> _objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY, storageType,
				Arrays.asList(
					new EncryptedObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).build())));
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
			objectField.getReadOnly(),
			objectField.getReadOnlyConditionExpression(),
			objectField.isRequired(), objectField.isState(),
			objectField.getObjectFieldSettings());
	}

	private ObjectField _addOrUpdateCustomObjectField(ObjectField objectField)
		throws Exception {

		return _addOrUpdateCustomObjectField(
			objectField, Collections.emptyList());
	}

	private ObjectField _addOrUpdateCustomObjectField(
			ObjectField objectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws Exception {

		return _objectFieldLocalService.addOrUpdateCustomObjectField(
			objectField.getExternalReferenceCode(),
			objectField.getObjectFieldId(), TestPropsValues.getUserId(),
			objectField.getListTypeDefinitionId(),
			objectField.getObjectDefinitionId(), objectField.getBusinessType(),
			objectField.getDBType(), objectField.isIndexed(),
			objectField.isIndexedAsKeyword(),
			objectField.getIndexedLanguageId(), objectField.getLabelMap(),
			objectField.isLocalized(), objectField.getName(),
			objectField.getReadOnly(),
			objectField.getReadOnlyConditionExpression(),
			objectField.isRequired(), objectField.isState(),
			objectFieldSettings);
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

	private ObjectField _addReadOnlyAggregationObjectField(
			long objectDefinitionId, String readOnly,
			String readOnlyConditionExpression)
		throws Exception {

		return _addCustomObjectField(
			new AggregationObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				objectDefinitionId
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						"function"
					).value(
						"MAX"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						"objectFieldName"
					).value(
						"integer"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						"objectRelationshipName"
					).value(
						"oneToManyRelationshipName"
					).build())
			).readOnly(
				readOnly
			).readOnlyConditionExpression(
				readOnlyConditionExpression
			).build());
	}

	private ObjectField _addReadOnlyFormulaObjectField(
			long objectDefinitionId, String readOnly,
			String readOnlyConditionExpression)
		throws Exception {

		return _addCustomObjectField(
			new FormulaObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				objectDefinitionId
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						"script"
					).value(
						"weight + 10"
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						"output"
					).value(
						ObjectFieldConstants.BUSINESS_TYPE_DECIMAL
					).build())
			).readOnly(
				readOnly
			).readOnlyConditionExpression(
				readOnlyConditionExpression
			).build());
	}

	private ObjectField _addSystemObjectField(
			long objectDefinitionId, String name,
			List<ObjectFieldSetting> objectFieldSettings)
		throws Exception {

		return _objectFieldLocalService.addSystemObjectField(
			null, TestPropsValues.getUserId(), 0L, objectDefinitionId,
			ObjectFieldConstants.BUSINESS_TYPE_TEXT, null, null,
			ObjectFieldConstants.DB_TYPE_STRING, false, true, "",
			LocalizedMapUtil.getLocalizedMap(name), name,
			ObjectFieldConstants.READ_ONLY_FALSE, null, false, false,
			objectFieldSettings);
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
			null, TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			null,
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

	private void _assertReadOnly(
			String invalidDDMScript, String invalidReadOnly,
			ObjectField objectField)
		throws Exception {

		_updateReadOnlyObjectField(objectField, invalidReadOnly, null);

		_assertReadOnlyTrue(objectField);

		_updateReadOnlyObjectField(
			objectField, ObjectFieldConstants.READ_ONLY_CONDITIONAL,
			invalidDDMScript);

		_assertReadOnlyTrue(objectField);

		String validDDMScript = "isEmpty(able)";

		_updateReadOnlyObjectField(
			objectField, ObjectFieldConstants.READ_ONLY_CONDITIONAL,
			validDDMScript);

		_assertReadOnlyTrue(objectField);

		_updateReadOnlyObjectField(
			objectField, ObjectFieldConstants.READ_ONLY_FALSE, null);

		_assertReadOnlyTrue(objectField);
	}

	private void _assertReadOnlyFalse(ObjectField objectField) {
		Assert.assertEquals(
			ObjectFieldConstants.READ_ONLY_FALSE, objectField.getReadOnly());
		Assert.assertEquals(
			StringPool.BLANK, objectField.getReadOnlyConditionExpression());
	}

	private void _assertReadOnlyTrue(ObjectField objectField) {
		Assert.assertEquals(
			ObjectFieldConstants.READ_ONLY_TRUE, objectField.getReadOnly());
		Assert.assertEquals(
			StringPool.BLANK, objectField.getReadOnlyConditionExpression());
	}

	private ObjectField _getIntegerObjectField(
		long objectDefinitionId, List<ObjectFieldSetting> objectFieldSettings) {

		return new IntegerObjectFieldBuilder(
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).name(
			"integer"
		).objectDefinitionId(
			objectDefinitionId
		).objectFieldSettings(
			objectFieldSettings
		).build();
	}

	private ObjectField _getReadOnlyTextObjectField(
		long objectDefinitionId, String readOnly,
		String readOnlyConditionExpression) {

		return new TextObjectFieldBuilder(
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).name(
			"a" + RandomTestUtil.randomString()
		).objectDefinitionId(
			objectDefinitionId
		).readOnly(
			readOnly
		).readOnlyConditionExpression(
			readOnlyConditionExpression
		).build();
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
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private void _testAddCustomObjectFieldReadOnly() throws Exception {
		AssertUtils.assertFailure(
			ObjectFieldReadOnlyConditionExpressionException.class,
			"Read only condition expression is required",
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					_getReadOnlyTextObjectField(
						0, ObjectFieldConstants.READ_ONLY_CONDITIONAL, null))));

		String invalidDDMScript = RandomTestUtil.randomString() + "()";

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyConditionExpressionException.class,
			"Syntax error in: " + invalidDDMScript,
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					_getReadOnlyTextObjectField(
						0, ObjectFieldConstants.READ_ONLY_CONDITIONAL,
						invalidDDMScript))));

		String invalidReadOnly = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyException.class,
			"Unknown read only: " + invalidReadOnly,
			() -> ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					_getReadOnlyTextObjectField(0, invalidReadOnly, null))));

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService, Collections.emptyList());

		for (String objectFieldName : _readOnlyObjectFieldNames) {
			_assertReadOnlyTrue(
				_objectFieldLocalService.getObjectField(
					objectDefinition1.getObjectDefinitionId(),
					objectFieldName));
		}

		_assertReadOnlyFalse(
			_objectFieldLocalService.getObjectField(
				objectDefinition1.getObjectDefinitionId(),
				"externalReferenceCode"));
		_assertReadOnlyFalse(
			_addCustomObjectField(
				_getReadOnlyTextObjectField(
					objectDefinition1.getObjectDefinitionId(), null, null)));

		String validDDMScript = "isEmpty(able)";

		ObjectField objectField = _addCustomObjectField(
			_getReadOnlyTextObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_CONDITIONAL, validDDMScript));

		Assert.assertEquals(
			objectField.getReadOnly(),
			ObjectFieldConstants.READ_ONLY_CONDITIONAL);
		Assert.assertEquals(
			validDDMScript, objectField.getReadOnlyConditionExpression());

		_assertReadOnlyFalse(
			_addCustomObjectField(
				_getReadOnlyTextObjectField(
					objectDefinition1.getObjectDefinitionId(),
					ObjectFieldConstants.READ_ONLY_FALSE, null)));
		_assertReadOnlyTrue(
			_addCustomObjectField(
				_getReadOnlyTextObjectField(
					objectDefinition1.getObjectDefinitionId(),
					ObjectFieldConstants.READ_ONLY_TRUE, null)));
		_assertReadOnlyTrue(
			_addReadOnlyFormulaObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_CONDITIONAL, null));
		_assertReadOnlyTrue(
			_addReadOnlyFormulaObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_CONDITIONAL, invalidDDMScript));
		_assertReadOnlyTrue(
			_addReadOnlyFormulaObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_CONDITIONAL, validDDMScript));
		_assertReadOnlyTrue(
			_addReadOnlyFormulaObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_FALSE, null));
		_assertReadOnlyTrue(
			_addReadOnlyFormulaObjectField(
				objectDefinition1.getObjectDefinitionId(),
				RandomTestUtil.randomString(), null));

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					_getIntegerObjectField(0, Collections.emptyList())));

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			objectDefinition1.getObjectDefinitionId(),
			objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"oneToManyRelationshipName", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertReadOnlyTrue(
			_addReadOnlyAggregationObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_CONDITIONAL, null));
		_assertReadOnlyTrue(
			_addReadOnlyAggregationObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_CONDITIONAL, invalidDDMScript));
		_assertReadOnlyTrue(
			_addReadOnlyAggregationObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_CONDITIONAL, validDDMScript));
		_assertReadOnlyTrue(
			_addReadOnlyAggregationObjectField(
				objectDefinition1.getObjectDefinitionId(),
				ObjectFieldConstants.READ_ONLY_FALSE, null));
		_assertReadOnlyTrue(
			_addReadOnlyAggregationObjectField(
				objectDefinition1.getObjectDefinitionId(),
				RandomTestUtil.randomString(), null));

		objectDefinition1 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Collections.emptyList());

		_assertReadOnlyFalse(
			_addCustomObjectField(
				_getReadOnlyTextObjectField(
					objectDefinition1.getObjectDefinitionId(),
					ObjectFieldConstants.READ_ONLY_CONDITIONAL,
					validDDMScript)));
		_assertReadOnlyFalse(
			_addCustomObjectField(
				_getReadOnlyTextObjectField(
					objectDefinition1.getObjectDefinitionId(),
					ObjectFieldConstants.READ_ONLY_TRUE, null)));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition1);
	}

	private void _testAddOrUpdateCustomObjectField(
			ObjectField expectedObjectField)
		throws Exception {

		ObjectField objectField = _addOrUpdateCustomObjectField(
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

	private ObjectField _updateReadOnlyObjectField(
			ObjectField objectField, String readOnly,
			String readOnlyConditionExpression)
		throws PortalException {

		return _objectFieldLocalService.addOrUpdateCustomObjectField(
			objectField.getExternalReferenceCode(),
			objectField.getObjectFieldId(), TestPropsValues.getUserId(),
			objectField.getListTypeDefinitionId(),
			objectField.getObjectDefinitionId(), objectField.getBusinessType(),
			objectField.getDBType(), objectField.isIndexed(),
			objectField.isIndexedAsKeyword(),
			objectField.getIndexedLanguageId(), objectField.getLabelMap(),
			objectField.isLocalized(), objectField.getName(), readOnly,
			readOnlyConditionExpression, objectField.isRequired(),
			objectField.isState(), objectField.getObjectFieldSettings());
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

	private final Set<String> _readOnlyObjectFieldNames = SetUtil.fromArray(
		"createDate", "creator", "id", "modifiedDate", "status");

}