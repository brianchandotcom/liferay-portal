/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.DateTimeObjectFieldBuilder;
import com.liferay.object.field.builder.DecimalObjectFieldBuilder;
import com.liferay.object.field.builder.EncryptedObjectFieldBuilder;
import com.liferay.object.field.builder.FormulaObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.PrecisionDecimalObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.service.test.util.ObjectFieldTestUtil;
import com.liferay.object.validation.rule.ObjectValidationRuleResult;
import com.liferay.object.validation.rule.setting.builder.ObjectValidationRuleSettingBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.encryptor.EncryptorException;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.math.BigDecimal;

import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

import javax.crypto.spec.SecretKeySpec;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@FeatureFlags("LPS-187846")
@RunWith(Arquillian.class)
public class ObjectEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_irrelevantObjectDefinition = _publishCustomObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					RandomTestUtil.randomString(), StringUtil.randomId())));

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				false,
				ListUtil.concat(
					_createListTypeEntries(
						"listTypeEntryKey", "List Type Entry Key ", 3),
					_createListTypeEntries(
						"multipleListTypeEntryKey",
						"Multiple List Type Entry Key ", 6)));

		_objectDefinition = _publishCustomObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
					"Age of Death", "ageOfDeath", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
					ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
					"Author of Gospel", "authorOfGospel", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE,
					ObjectFieldConstants.DB_TYPE_DATE, true, false, null,
					"Birthday", "birthday", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					"Email Address", "emailAddress",
					Arrays.asList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
						).value(
							Boolean.TRUE.toString()
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					"Email Address Required", "emailAddressRequired", true),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					"Email Address Domain", "emailAddressDomain", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					"First Name", "firstName", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
					ObjectFieldConstants.DB_TYPE_DOUBLE, true, false, null,
					"Height", "height", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					"Last Name", "lastName", false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					"List Type Entry Key", "listTypeEntryKey", false, false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					"List Type Entry Key Required", "listTypeEntryKeyRequired",
					true, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					"Middle Name", "middleName", false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
					null, ObjectFieldConstants.DB_TYPE_STRING, true, false,
					null, "Multiple List Type Entries Key",
					"multipleListTypeEntriesKey", false, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
					ObjectFieldConstants.DB_TYPE_INTEGER, true, false, null,
					"Number of Books Written", "numberOfBooksWritten", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
					ObjectFieldConstants.DB_TYPE_CLOB, false, false, null,
					"Script", "script", false)));

		_addCustomObjectField(
			new PrecisionDecimalObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Speed")
			).name(
				"speed"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).build());
		_addCustomObjectField(
			new PicklistObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("State")
			).listTypeDefinitionId(
				_listTypeDefinition.getListTypeDefinitionId()
			).name(
				"state"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
					).value(
						"listTypeEntryKey1"
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
		_addCustomObjectField(
			new DateTimeObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Time")
			).name(
				"time"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Collections.singletonList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_TIME_STORAGE
					).value(
						ObjectFieldSettingConstants.VALUE_USE_INPUT_AS_ENTERED
					).build())
			).build());
		_addCustomObjectField(
			new AttachmentObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Upload")
			).name(
				"upload"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
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
		_addCustomObjectField(
			new DecimalObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Weight")
			).name(
				"weight"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).build());
	}

	@After
	public void tearDown() throws Exception {

		// Do not rely on @DeleteAfterTestRun because object entries that
		// reference a required list type entry cannot be deleted before it is
		// unreferenced

		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		_assertCount(0);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(1);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(2);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(3);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"listTypeEntryKey", "listTypeEntryKey1"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(4);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"multipleListTypeEntriesKey",
				"multipleListTypeEntryKey1, multipleListTypeEntryKey2"
			).build());

		_assertCount(5);

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsIntegerSize.class,
			"Object entry value exceeds integer field allowed size",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"numberOfBooksWritten", "2147483648"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsIntegerSize.class,
			"Object entry value exceeds integer field allowed size",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"numberOfBooksWritten", "-2147483649"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsLongMaxSize.class,
			"Object entry value exceeds maximum long field allowed size",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "9007199254740992"
				).put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsLongMinSize.class,
			"Object entry value falls below minimum long field allowed size",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "-9007199254740992"
				).put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsLongSize.class,
			"Object entry value exceeds long field allowed size",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "9223372036854775808"
				).put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsLongSize.class,
			"Object entry value exceeds long field allowed size",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "-9223372036854775809"
				).put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsTextMaxLength.class,
			"Object entry value exceeds the maximum length of 280 characters " +
				"for object field \"firstName\"",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"firstName", RandomTestUtil.randomString(281)
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsTextMaxLength.class,
			"Object entry value exceeds the maximum length of 65000 " +
				"characters for object field \"script\"",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"script", RandomTestUtil.randomString(65001)
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidFileExtension.class,
			"The file extension txt is invalid for object field \"upload\"",
			() -> {
				ObjectField objectField =
					_objectFieldLocalService.fetchObjectField(
						_objectDefinition.getObjectDefinitionId(), "upload");

				ObjectFieldSetting objectFieldSetting =
					_objectFieldSettingLocalService.fetchObjectFieldSetting(
						objectField.getObjectFieldId(),
						"acceptedFileExtensions");

				_objectFieldSettingLocalService.updateObjectFieldSetting(
					objectFieldSetting.getObjectFieldSettingId(), "jpg, png");

				_addObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"emailAddressRequired", "peter@liferay.com"
					).put(
						"listTypeEntryKeyRequired", "listTypeEntryKey1"
					).put(
						"upload",
						_addTempFileEntry(
							StringUtil.randomString()
						).getFileEntryId()
					).build());
			});

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ListTypeEntry.class,
			"Object field name \"listTypeEntryKeyRequired\" is not mapped to " +
				"a valid list type entry",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "john@liferay.com"
				).put(
					"listTypeEntryKeyRequired", RandomTestUtil.randomString()
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ListTypeEntry.class,
			"Object field name \"multipleListTypeEntriesKey\" is not mapped " +
				"to a valid list type entry",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "john@liferay.com"
				).put(
					"multipleListTypeEntriesKey",
					(Serializable)Arrays.asList(
						"multipleListTypeEntryKey1",
						RandomTestUtil.randomString())
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.Required.class,
			"No value was provided for required object field " +
				"\"emailAddressRequired\"",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"firstName", "Judas"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.Required.class,
			"No value was provided for required object field " +
				"\"listTypeEntryKeyRequired\"",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "john@liferay.com"
				).put(
					"firstName", "Judas"
				).build()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.UniqueValueConstraintViolation.class,
			"Unique value constraint violation for " +
				_objectDefinition.getDBTableName() +
					".emailAddress_ with value james@liferay.com",
			() -> _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", "james@liferay.com"
				).put(
					"emailAddressRequired", "james@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build()));
	}

	@Test
	public void testAddObjectEntryWithEncryptedObjectField() throws Exception {
		String key = ObjectFieldTestUtil.generateKey("AES");

		ObjectFieldTestUtil.withEncryptedObjectFieldProperties(
			"AES", true, key,
			() -> {
				_addCustomObjectField(
					new EncryptedObjectFieldBuilder(
					).externalReferenceCode(
						"encryptedObjectFieldERC"
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"encrypted"
					).objectDefinitionId(
						_objectDefinition.getObjectDefinitionId()
					).build());

				ObjectEntry objectEntry = _addObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"emailAddress", RandomTestUtil.randomString()
					).put(
						"emailAddressRequired", "athanasius@liferay.com"
					).put(
						"encrypted", "test"
					).put(
						"externalReferenceCode", "objectEntryERC"
					).put(
						"listTypeEntryKeyRequired", "listTypeEntryKey1"
					).build());

				_assertCount(1);

				Assert.assertEquals(
					"test",
					MapUtil.getString(
						_objectEntryLocalService.getValues(
							objectEntry.getObjectEntryId()),
						"encrypted"));
			});

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			"objectEntryERC", _objectDefinition.getObjectDefinitionId());

		ObjectFieldTestUtil.withEncryptedObjectFieldProperties(
			"", true, "",
			() -> {
				AssertUtils.assertFailure(
					IllegalArgumentException.class,
					"Please insert an encryption key or remove the object's " +
						"encryption field to recover these entries.",
					() -> _objectEntryLocalService.getValues(
						objectEntry.getObjectEntryId()));

				AssertUtils.assertFailure(
					SystemException.class,
					IllegalArgumentException.class.getName() + ": Empty key",
					() -> _addObjectEntry(
						HashMapBuilder.<String, Serializable>put(
							"emailAddress", RandomTestUtil.randomString()
						).put(
							"emailAddressRequired", "athanasius@liferay.com"
						).put(
							"encrypted", RandomTestUtil.randomString()
						).put(
							"listTypeEntryKeyRequired", "listTypeEntryKey1"
						).build()));

				_assertCount(1);
			});
		ObjectFieldTestUtil.withEncryptedObjectFieldProperties(
			"", true, key,
			() -> {
				AssertUtils.assertFailure(
					PortalException.class,
					StringBundler.concat(
						EncryptorException.class.getName(), ": ",
						EncryptorException.class.getName(), ": ",
						NoSuchAlgorithmException.class.getName(),
						": Invalid transformation format:"),
					() -> _objectEntryLocalService.getValues(
						objectEntry.getObjectEntryId()));

				AssertUtils.assertFailure(
					SystemException.class,
					StringBundler.concat(
						EncryptorException.class.getName(), ": ",
						EncryptorException.class.getName(), ": ",
						NoSuchAlgorithmException.class.getName(),
						": Invalid transformation format:"),
					() -> _addObjectEntry(
						HashMapBuilder.<String, Serializable>put(
							"emailAddress", RandomTestUtil.randomString()
						).put(
							"emailAddressRequired", "athanasius@liferay.com"
						).put(
							"encrypted", RandomTestUtil.randomString()
						).put(
							"listTypeEntryKeyRequired", "listTypeEntryKey1"
						).build()));

				_assertCount(1);
			});
		ObjectFieldTestUtil.withEncryptedObjectFieldProperties(
			"AES", true, "",
			() -> {
				AssertUtils.assertFailure(
					IllegalArgumentException.class,
					"Please insert an encryption key or remove the object's " +
						"encryption field to recover these entries.",
					() -> _objectEntryLocalService.getValues(
						objectEntry.getObjectEntryId()));

				AssertUtils.assertFailure(
					SystemException.class,
					IllegalArgumentException.class.getName() + ": Empty key",
					() -> _addObjectEntry(
						HashMapBuilder.<String, Serializable>put(
							"emailAddress", RandomTestUtil.randomString()
						).put(
							"emailAddressRequired", "athanasius@liferay.com"
						).put(
							"encrypted", RandomTestUtil.randomString()
						).put(
							"listTypeEntryKeyRequired", "listTypeEntryKey1"
						).build()));

				_assertCount(1);
			});

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			"encryptedObjectFieldERC",
			_objectDefinition.getObjectDefinitionId());

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ", objectField.getDBColumnName(), " from ",
					_objectDefinition.getExtensionDBTableName(), " where ",
					_objectDefinition.getPKObjectFieldDBColumnName(), " = ",
					objectEntry.getObjectEntryId()));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			Assert.assertEquals(
				_encryptor.encrypt(
					new SecretKeySpec(Base64.decode(key), "AES"), "test"),
				resultSet.getString(1));
		}

		_objectFieldLocalService.deleteObjectField(objectField);
	}

	@Test
	public void testAddObjectEntryWithFormulaObjectField() throws Exception {
		ObjectField objectField = _addCustomObjectField(
			new FormulaObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Overweight")
			).name(
				"overweight"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
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
			).build());

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", RandomTestUtil.randomString()
			).put(
				"emailAddressRequired", "athanasius@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"weight", 65D
			).build());

		Assert.assertEquals(
			75D,
			MapUtil.getDouble(
				_objectEntryLocalService.getValues(
					objectEntry.getObjectEntryId()),
				"overweight"),
			0);

		_objectFieldLocalService.deleteObjectField(objectField);
	}

	@Test
	public void testAddObjectEntryWithObjectValidationRule() throws Exception {

		// Date time must be in the future

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			"yyyy-MM-dd HH:mm");

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			_objectDefinition.getObjectDefinitionId(), "time");

		ObjectValidationRule objectValidationRule1 = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap("Date time must be in the future"),
			ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION,
			String.format(
				"futureDates(%s, \"%s\")", objectField.getName(),
				dateTimeFormatter.format(LocalDateTime.now())),
			Collections.singletonList(
				new ObjectValidationRuleSettingBuilder(
				).name(
					ObjectValidationRuleSettingConstants.
						NAME_OUTPUT_OBJECT_FIELD_ID
				).value(
					String.valueOf(objectField.getObjectFieldId())
				).build()));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", RandomTestUtil.randomString()
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"time", dateTimeFormatter.format(LocalDateTime.now())
			).build());

		_assertCount(1);

		// Field must be an email address

		ObjectValidationRule objectValidationRule2 = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap("Field must be an email address"),
			"isEmailAddress(emailAddress)");

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "bob@liferay.com"
			).put(
				"emailAddressRequired", "bob@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"time", dateTimeFormatter.format(LocalDateTime.now())
			).build());

		_assertCount(2);

		// Must be over 18 years old

		Class<?> clazz = getClass();

		ObjectValidationRule objectValidationRule3 = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
			LocalizedMapUtil.getLocalizedMap("Must be over 18 years old"),
			StringUtil.read(
				clazz,
				StringBundler.concat(
					"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
					testName.getMethodName(), ".groovy")));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"birthday", "2000-12-25"
			).put(
				"emailAddressRequired", "bob@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"time", dateTimeFormatter.format(LocalDateTime.now())
			).build());

		_assertCount(3);

		// Names must be equals

		ObjectValidationRule objectValidationRule4 = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap("Names must be equals"),
			"equals(lastName, middleName)");

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"birthday", "2000-12-25"
			).put(
				"emailAddress", "john@liferay.com"
			).put(
				"emailAddressRequired", "bob@liferay.com"
			).put(
				"lastName", "Doe"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"middleName", "Doe"
			).put(
				"time", dateTimeFormatter.format(LocalDateTime.now())
			).build());

		_assertCount(4);

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"birthday", "2010-12-25"
				).put(
					"emailAddress", RandomTestUtil.randomString()
				).put(
					"emailAddressRequired", RandomTestUtil.randomString()
				).put(
					"lastName", RandomTestUtil.randomString()
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"middleName", RandomTestUtil.randomString()
				).put(
					"time", "2000-12-25 08:50"
				).build());

			Assert.fail();
		}
		catch (ModelListenerException modelListenerException) {
			ObjectValidationRuleEngineException
				objectValidationRuleEngineException =
					(ObjectValidationRuleEngineException)
						modelListenerException.getCause();

			List<ObjectValidationRuleResult> objectValidationRuleResults =
				objectValidationRuleEngineException.
					getObjectValidationRuleResults();

			Assert.assertEquals(
				objectValidationRuleResults.toString(), 4,
				objectValidationRuleResults.size());

			_assertObjectValidationRuleResult(
				objectValidationRule1.getErrorLabel(LocaleUtil.getDefault()),
				objectField.getName(), objectValidationRuleResults.get(0));
			_assertObjectValidationRuleResult(
				objectValidationRule2.getErrorLabel(LocaleUtil.getDefault()),
				null, objectValidationRuleResults.get(1));
			_assertObjectValidationRuleResult(
				objectValidationRule3.getErrorLabel(LocaleUtil.getDefault()),
				null, objectValidationRuleResults.get(2));
			_assertObjectValidationRuleResult(
				objectValidationRule4.getErrorLabel(LocaleUtil.getDefault()),
				null, objectValidationRuleResults.get(3));
		}

		// Deactivate object validation rule

		objectValidationRule4.setActive(false);

		_objectValidationRuleLocalService.updateObjectValidationRule(
			objectValidationRule4);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"birthday", "2000-12-25"
			).put(
				"emailAddressRequired", "bob@liferay.com"
			).put(
				"lastName", RandomTestUtil.randomString()
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"middleName", RandomTestUtil.randomString()
			).put(
				"time", dateTimeFormatter.format(LocalDateTime.now())
			).build());

		_assertCount(5);
	}

	@Test
	public void testAddOrUpdateObjectEntry() throws Exception {
		_assertCount(0);

		Map<String, Serializable> values =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build();

		ObjectEntry objectEntry = _addOrUpdateObjectEntry("peter", 0, values);

		_assertCount(1);

		_assertObjectEntryValues(
			21, values,
			_objectEntryLocalService.getValues(objectEntry.getObjectEntryId()));

		values = HashMapBuilder.<String, Serializable>put(
			"emailAddressRequired", "peter@liferay.com"
		).put(
			"firstName", "Pedro"
		).put(
			"listTypeEntryKeyRequired", "listTypeEntryKey2"
		).build();

		_addOrUpdateObjectEntry("peter", 0, values);

		_assertCount(1);

		_assertObjectEntryValues(
			21, values,
			_objectEntryLocalService.getValues(objectEntry.getObjectEntryId()));

		_addOrUpdateObjectEntry(
			"james", 0,
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		_assertCount(2);

		_addOrUpdateObjectEntry(
			"john", 0,
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(3);

		// TODO Test where group ID is not 0

		// TODO Test where group ID does not belong to right company

		// TODO Test object entries scoped to company vs. scoped to group

	}

	@Test
	public void testAuditRouter() throws Exception {
		Queue<AuditMessage> auditMessages = new LinkedList<>();

		AuditRouter auditRouter =
			(AuditRouter)ReflectionTestUtil.getAndSetFieldValue(
				_objectEntryModelListener, "_auditRouter",
				ProxyUtil.newProxyInstance(
					AuditRouter.class.getClassLoader(),
					new Class<?>[] {AuditRouter.class},
					(proxy, method, arguments) -> {
						auditMessages.add((AuditMessage)arguments[0]);

						return null;
					}));

		_objectDefinition.setEnableObjectEntryHistory(true);

		_objectDefinition =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition);

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"upload",
				() -> {
					FileEntry fileEntry = _addTempFileEntry("Old Testament");

					return fileEntry.getFileEntryId();
				}
			).build());

		AuditMessage auditMessage = auditMessages.poll();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired",
				JSONUtil.put(
					"key", "listTypeEntryKey1"
				).put(
					"name", "List Type Entry Key 1"
				)
			).put(
				"upload", JSONUtil.put("title", "Old Testament")
			).toString(),
			String.valueOf(auditMessage.getAdditionalInfo()),
			JSONCompareMode.STRICT_ORDER);

		auditMessages.clear();

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).put(
				"upload",
				() -> {
					FileEntry fileEntry = _addTempFileEntry("New Testament");

					return fileEntry.getFileEntryId();
				}
			).build(),
			ServiceContextTestUtil.getServiceContext());

		auditMessage = auditMessages.poll();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"attributes",
				JSONUtil.putAll(
					JSONUtil.put(
						"name", "emailAddressRequired"
					).put(
						"newValue", "peter@liferay.com"
					).put(
						"oldValue", "james@liferay.com"
					),
					JSONUtil.put(
						"name", "firstName"
					).put(
						"newValue", "Peter"
					).put(
						"oldValue", "James"
					),
					JSONUtil.put(
						"name", "listTypeEntryKeyRequired"
					).put(
						"newValue",
						JSONUtil.put(
							"key", "listTypeEntryKey3"
						).put(
							"name", "List Type Entry Key 3"
						)
					).put(
						"oldValue",
						JSONUtil.put(
							"key", "listTypeEntryKey1"
						).put(
							"name", "List Type Entry Key 1"
						)
					),
					JSONUtil.put(
						"name", "upload"
					).put(
						"newValue", JSONUtil.put("title", "New Testament")
					).put(
						"oldValue", JSONUtil.put("title", "Old Testament")
					))
			).toString(),
			String.valueOf(auditMessage.getAdditionalInfo()),
			JSONCompareMode.STRICT_ORDER);

		auditMessages.clear();

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry.getObjectEntryId());

		auditMessage = auditMessages.poll();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired",
				JSONUtil.put(
					"key", "listTypeEntryKey3"
				).put(
					"name", "List Type Entry Key 3"
				)
			).put(
				"upload", JSONUtil.put("title", "New Testament")
			).toString(),
			String.valueOf(auditMessage.getAdditionalInfo()),
			JSONCompareMode.STRICT_ORDER);

		ReflectionTestUtil.setFieldValue(
			_objectEntryModelListener, "_auditRouter", auditRouter);
	}

	@Test
	public void testCachedValues() throws Exception {
		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		FinderCacheUtil.clearDSLQueryCache(_objectDefinition.getDBTableName());
		FinderCacheUtil.clearDSLQueryCache(
			_objectDefinition.getExtensionDBTableName());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.SQL", LoggerTestUtil.DEBUG)) {

			objectEntry = _objectEntryLocalService.getObjectEntry(
				objectEntry.getObjectEntryId());

			Assert.assertNull(
				ReflectionTestUtil.getFieldValue(objectEntry, "_values"));

			objectEntry.getValues();

			Assert.assertNotNull(
				ReflectionTestUtil.getFieldValue(objectEntry, "_values"));

			Assert.assertTrue(_containsObjectEntryValuesSQLQuery(logCapture));
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.SQL", LoggerTestUtil.DEBUG)) {

			objectEntry = _objectEntryLocalService.getObjectEntry(
				objectEntry.getObjectEntryId());

			objectEntry.getValues();

			Assert.assertFalse(_containsObjectEntryValuesSQLQuery(logCapture));
		}
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		ObjectEntry objectEntry1 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());
		ObjectEntry objectEntry2 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).put(
				"upload",
				() -> {
					FileEntry fileEntry = _addTempFileEntry("Old Testament");

					return fileEntry.getFileEntryId();
				}
			).build());
		ObjectEntry objectEntry3 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		_assertCount(3);

		// Delete first object entry

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry1.getObjectEntryId());

		AssertUtils.assertFailure(
			NoSuchObjectEntryException.class,
			"No ObjectEntry exists with the primary key " +
				objectEntry1.getObjectEntryId(),
			() -> _objectEntryLocalService.deleteObjectEntry(
				objectEntry1.getObjectEntryId()));

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);

		AssertUtils.assertFailure(
			NoSuchObjectEntryException.class,
			"No ObjectEntry exists with the primary key " +
				objectEntry1.getObjectEntryId(),
			() -> _objectEntryLocalService.getValues(
				objectEntry1.getObjectEntryId()));

		_assertCount(2);

		// Delete second object entry

		long fileEntryId = MapUtil.getLong(objectEntry2.getValues(), "upload");

		Assert.assertNotNull(_dlAppLocalService.getFileEntry(fileEntryId));

		_objectEntryLocalService.deleteObjectEntry(objectEntry2);

		AssertUtils.assertFailure(
			NoSuchFileEntryException.class,
			StringBundler.concat(
				"No FileEntry exists with the key {fileEntryId=", fileEntryId,
				"}"),
			() -> _dlAppLocalService.getFileEntry(fileEntryId));

		_assertCount(1);

		// Delete third object entry

		_objectEntryLocalService.deleteObjectEntry(objectEntry3);

		_assertCount(0);

		// Delete object entry with an inactive definition

		ObjectEntry objectEntry4 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		_objectDefinitionLocalService.updateCustomObjectDefinition(
			_objectDefinition.getExternalReferenceCode(),
			_objectDefinition.getObjectDefinitionId(),
			_objectDefinition.getAccountEntryRestrictedObjectFieldId(),
			_objectDefinition.getDescriptionObjectFieldId(), 0,
			_objectDefinition.getTitleObjectFieldId(),
			_objectDefinition.isAccountEntryRestricted(), false,
			_objectDefinition.isEnableCategorization(),
			_objectDefinition.isEnableComments(),
			_objectDefinition.isEnableLocalization(),
			_objectDefinition.isEnableObjectEntryDraft(),
			_objectDefinition.isEnableObjectEntryHistory(),
			_objectDefinition.getLabelMap(), _objectDefinition.getName(),
			_objectDefinition.getPanelAppOrder(),
			_objectDefinition.getPanelCategoryKey(),
			_objectDefinition.isPortlet(),
			_objectDefinition.getPluralLabelMap(),
			_objectDefinition.getScope());

		_objectEntryLocalService.deleteObjectEntry(objectEntry4);

		_assertCount(0);
	}

	@Test
	public void testGetExtensionDynamicObjectDefinitionTableValues()
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), User.class.getName());

		_addCustomObjectField(
			new LongIntegerObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"longField"
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).build());
		_addCustomObjectField(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"textField"
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		User user = UserTestUtil.addUser();

		try {
			_objectEntryLocalService.
				addOrUpdateExtensionDynamicObjectDefinitionTableValues(
					TestPropsValues.getUserId(), objectDefinition,
					user.getUserId(), Collections.emptyMap(),
					ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.Required objectEntryValuesException) {
			Assert.assertEquals(
				"No value was provided for required object field \"textField\"",
				objectEntryValuesException.getMessage());
		}

		Map<String, Serializable> values =
			HashMapBuilder.<String, Serializable>put(
				"longField", 10L
			).put(
				"textField", "Value"
			).build();

		_objectEntryLocalService.
			addOrUpdateExtensionDynamicObjectDefinitionTableValues(
				TestPropsValues.getUserId(), objectDefinition, user.getUserId(),
				values, ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			values,
			_objectEntryLocalService.
				getExtensionDynamicObjectDefinitionTableValues(
					objectDefinition, user.getUserId()));

		values = HashMapBuilder.<String, Serializable>put(
			"longField", 1000L
		).put(
			"textField", "New Value"
		).build();

		_objectEntryLocalService.
			addOrUpdateExtensionDynamicObjectDefinitionTableValues(
				TestPropsValues.getUserId(), objectDefinition, user.getUserId(),
				values, ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			values,
			_objectEntryLocalService.
				getExtensionDynamicObjectDefinitionTableValues(
					objectDefinition, user.getUserId()));

		_objectEntryLocalService.
			deleteExtensionDynamicObjectDefinitionTableValues(
				objectDefinition, user.getUserId());

		Assert.assertTrue(
			MapUtil.isEmpty(
				_objectEntryLocalService.
					getExtensionDynamicObjectDefinitionTableValues(
						objectDefinition, user.getUserId())));
	}

	@Test
	public void testGetObjectEntries() throws Exception {
		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_assertCount(0);

		// Add first object entry

		Map<String, Serializable> values1 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build();

		_addObjectEntry(values1);

		objectEntries = _objectEntryLocalService.getObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_assertCount(1);

		_assertObjectEntryValues(
			21, values1, _getValuesFromDatabase(objectEntries.get(0)));

		// Add second object entry

		Map<String, Serializable> values2 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build();

		_addObjectEntry(values2);

		objectEntries = _objectEntryLocalService.getObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_assertCount(2);

		_assertObjectEntryValues(
			21, values1, _getValuesFromDatabase(objectEntries.get(0)));
		_assertObjectEntryValues(
			21, values2, _getValuesFromDatabase(objectEntries.get(1)));

		// Add third object entry

		Map<String, Serializable> values3 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build();

		_addObjectEntry(values3);

		objectEntries = _objectEntryLocalService.getObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 3, objectEntries.size());

		_assertCount(3);

		_assertObjectEntryValues(
			21, values1, _getValuesFromDatabase(objectEntries.get(0)));
		_assertObjectEntryValues(
			21, values2, _getValuesFromDatabase(objectEntries.get(1)));
		_assertObjectEntryValues(
			21, values3, _getValuesFromDatabase(objectEntries.get(2)));

		// Irrelevant object definition

		objectEntries = _objectEntryLocalService.getObjectEntries(
			0, _irrelevantObjectDefinition.getObjectDefinitionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		Map<String, Serializable> systemValues =
			_objectEntryLocalService.getSystemValues(objectEntry);

		_assertTimestamp(
			objectEntry.getCreateDate(),
			(Timestamp)systemValues.get("createDate"));
		Assert.assertEquals(
			objectEntry.getExternalReferenceCode(),
			systemValues.get("externalReferenceCode"));
		_assertTimestamp(
			objectEntry.getModifiedDate(),
			(Timestamp)systemValues.get("modifiedDate"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(), systemValues.get("objectEntryId"));
		Assert.assertEquals(
			objectEntry.getStatus(), systemValues.get("status"));
		Assert.assertEquals(
			objectEntry.getUserName(), systemValues.get("userName"));

		Assert.assertEquals(systemValues.toString(), 6, systemValues.size());

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(0L, values.get("ageOfDeath"));
		Assert.assertFalse((boolean)values.get("authorOfGospel"));
		Assert.assertEquals(null, values.get("birthday"));
		Assert.assertEquals(
			"john@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(0D, values.get("height"));
		Assert.assertEquals(null, values.get("lastName"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(0, values.get("numberOfBooksWritten"));
		Assert.assertEquals(null, values.get("listTypeEntryKey"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(StringPool.BLANK, values.get("script"));
		Assert.assertEquals(_getBigDecimal(0L), values.get("speed"));
		Assert.assertEquals("listTypeEntryKey1", values.get("state"));
		Assert.assertEquals(null, values.get("time"));
		Assert.assertEquals(0D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 21, values.size());

		AssertUtils.assertFailure(
			NoSuchObjectEntryException.class,
			"No ObjectEntry exists with the primary key 0",
			() -> _objectEntryLocalService.getValues(0));
	}

	@Test
	public void testGetValuesList() throws Exception {
		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), null, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(valuesList.toString(), 0, valuesList.size());

		_assertCount(0);

		// Add first object entry

		Map<String, Serializable> values1 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build();

		_addObjectEntry(values1);

		valuesList = _objectEntryLocalService.getValuesList(
			0, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), null, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(valuesList.toString(), 1, valuesList.size());

		_assertCount(1);

		_assertObjectEntryValues(27, values1, valuesList.get(0));

		// Add second object entry

		Map<String, Serializable> values2 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build();

		_addObjectEntry(values2);

		valuesList = _objectEntryLocalService.getValuesList(
			0, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), null, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(valuesList.toString(), 2, valuesList.size());

		_assertCount(2);

		_assertObjectEntryValues(27, values1, valuesList.get(0));
		_assertObjectEntryValues(27, values2, valuesList.get(1));

		// Add third object entry

		Map<String, Serializable> values3 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build();

		_addObjectEntry(values3);

		valuesList = _objectEntryLocalService.getValuesList(
			0, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), null, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(valuesList.toString(), 3, valuesList.size());

		_assertCount(3);

		_assertObjectEntryValues(27, values1, valuesList.get(0));
		_assertObjectEntryValues(27, values2, valuesList.get(1));
		_assertObjectEntryValues(27, values3, valuesList.get(2));

		// Irrelevant object definition

		valuesList = _objectEntryLocalService.getValuesList(
			0, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_irrelevantObjectDefinition.getObjectDefinitionId(), null, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(valuesList.toString(), 0, valuesList.size());
	}

	@Test
	public void testScope() throws Exception {

		// Scope by company

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_testScope(0, ObjectDefinitionConstants.SCOPE_COMPANY, true);
		_testScope(
			depotEntry.getGroupId(), ObjectDefinitionConstants.SCOPE_COMPANY,
			false);
		_testScope(
			TestPropsValues.getGroupId(),
			ObjectDefinitionConstants.SCOPE_COMPANY, false);

		// Scope by depot

		// TODO Turn on theses tests once depot is reenabled

		/*_testScope(0, ObjectDefinitionConstants.SCOPE_DEPOT, false);
		_testScope(
			depotEntryGroupId, ObjectDefinitionConstants.SCOPE_DEPOT, true);
		_testScope(siteGroupId, ObjectDefinitionConstants.SCOPE_DEPOT, false);*/

		// Scope by site

		_testScope(0, ObjectDefinitionConstants.SCOPE_SITE, false);
		_testScope(
			depotEntry.getGroupId(), ObjectDefinitionConstants.SCOPE_SITE,
			false);
		_testScope(
			TestPropsValues.getGroupId(), ObjectDefinitionConstants.SCOPE_SITE,
			true);
	}

	@Test
	public void testSearchObjectEntries() throws Exception {

		// Without keywords

		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			_objectEntryLocalService.searchObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());

		// Add first object entry

		Map<String, Serializable> values1 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build();

		_addObjectEntry(values1);

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<ObjectEntry> objectEntries = baseModelSearchResult.getBaseModels();

		_assertObjectEntryValues(
			21, values1, _getValuesFromDatabase(objectEntries.get(0)));

		// Add second object entry

		Map<String, Serializable> values2 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build();

		_addObjectEntry(values2);

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(2, baseModelSearchResult.getLength());

		objectEntries = baseModelSearchResult.getBaseModels();

		_assertObjectEntryValues(
			21, values1, _getValuesFromDatabase(objectEntries.get(0)));
		_assertObjectEntryValues(
			21, values2, _getValuesFromDatabase(objectEntries.get(1)));

		// Add third object entry

		Map<String, Serializable> values3 =
			HashMapBuilder.<String, Serializable>put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build();

		_addObjectEntry(values3);

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(3, baseModelSearchResult.getLength());

		objectEntries = baseModelSearchResult.getBaseModels();

		_assertObjectEntryValues(
			21, values1, _getValuesFromDatabase(objectEntries.get(0)));
		_assertObjectEntryValues(
			21, values2, _getValuesFromDatabase(objectEntries.get(1)));
		_assertObjectEntryValues(
			21, values3, _getValuesFromDatabase(objectEntries.get(2)));

		// With keywords

		_assertKeywords("@ liferay.com", 3);
		_assertKeywords("@-liferay.com", 0);
		_assertKeywords("@life", 3);
		_assertKeywords("@liferay", 3);
		_assertKeywords("@liferay.com", 3);
		_assertKeywords("Peter", 1);
		_assertKeywords("j0hn", 0);
		_assertKeywords("john", 1);
		_assertKeywords("life", 0);
		_assertKeywords("liferay", 0);
		_assertKeywords("liferay.com", 0);
		_assertKeywords("listTypeEntryKey1", 1);
		_assertKeywords("listTypeEntryKey2", 1);
		_assertKeywords("listTypeEntryKey3", 1);
		_assertKeywords("peter", 1);

		// Irrelevant object definition

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _irrelevantObjectDefinition.getObjectDefinitionId(), null, 0,
			20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());
	}

	@Test
	public void testUpdateAsset() throws Exception {
		ObjectField objectField = _objectFieldLocalService.getObjectField(
			_objectDefinition.getObjectDefinitionId(), "emailAddressRequired");

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			_objectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			_objectDefinition.getClassName(), objectEntry.getObjectEntryId());

		Assert.assertEquals("john@liferay.com", assetEntry.getTitle());
	}

	@Test
	public void testUpdateObjectEntry() throws Exception {
		_assertCount(0);

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"multipleListTypeEntriesKey",
				(Serializable)Arrays.asList(
					"multipleListTypeEntryKey1", "multipleListTypeEntryKey2")
			).build());

		_assertCount(1);

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(objectEntry, "_values"));

		_getValuesFromCacheField(objectEntry);

		//Assert.assertNotNull(
		//	ReflectionTestUtil.getFieldValue(objectEntry, "_values"));

		objectEntry = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "João"
			).put(
				"lastName", "o Discípulo Amado"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).put(
				"multipleListTypeEntriesKey",
				(Serializable)Arrays.asList(
					"multipleListTypeEntryKey3", "multipleListTypeEntryKey4")
			).put(
				"state", "listTypeEntryKey1"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertCount(1);

		objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntry.getObjectEntryId());

		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(objectEntry, "_values"));

		Map<String, Serializable> values = objectEntry.getValues();

		Assert.assertEquals(_getValuesFromCacheField(objectEntry), values);
		Assert.assertEquals(0L, values.get("ageOfDeath"));
		Assert.assertFalse((boolean)values.get("authorOfGospel"));
		Assert.assertEquals(null, values.get("birthday"));
		Assert.assertEquals(
			"john@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(0D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(null, values.get("listTypeEntryKey"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(
			"multipleListTypeEntryKey3, multipleListTypeEntryKey4",
			values.get("multipleListTypeEntriesKey"));
		Assert.assertEquals(0, values.get("numberOfBooksWritten"));
		Assert.assertEquals(StringPool.BLANK, values.get("script"));
		Assert.assertEquals(_getBigDecimal(0L), values.get("speed"));
		Assert.assertEquals("listTypeEntryKey1", values.get("state"));
		Assert.assertEquals(null, values.get("time"));
		Assert.assertEquals(0L, values.get("upload"));
		Assert.assertEquals(0D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 21, values.size());

		Calendar calendar = new GregorianCalendar();

		calendar.set(6, Calendar.DECEMBER, 27);
		calendar.setTimeInMillis(0);

		Date birthdayDate = calendar.getTime();

		String script = RandomTestUtil.randomString(1500);
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		FileEntry fileEntry = _addTempFileEntry(StringUtil.randomString());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"ageOfDeath", "94"
			).put(
				"authorOfGospel", true
			).put(
				"birthday", birthdayDate
			).put(
				"height", 180
			).put(
				"listTypeEntryKey", "listTypeEntryKey1"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).put(
				"multipleListTypeEntriesKey",
				(Serializable)Arrays.asList(
					"multipleListTypeEntryKey5", "multipleListTypeEntryKey6")
			).put(
				"numberOfBooksWritten", 5
			).put(
				"script", script
			).put(
				"speed", BigDecimal.valueOf(45L)
			).put(
				"state", "listTypeEntryKey2"
			).put(
				"time", timestamp
			).put(
				"upload", fileEntry.getFileEntryId()
			).put(
				"weight", 60
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertCount(1);

		values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(94L, values.get("ageOfDeath"));
		Assert.assertTrue((boolean)values.get("authorOfGospel"));
		Assert.assertEquals(birthdayDate, values.get("birthday"));
		Assert.assertEquals(
			"john@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(180D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKey"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(
			"multipleListTypeEntryKey5, multipleListTypeEntryKey6",
			values.get("multipleListTypeEntriesKey"));
		Assert.assertEquals(5, values.get("numberOfBooksWritten"));
		Assert.assertEquals(script, values.get("script"));
		Assert.assertEquals(_getBigDecimal(45L), values.get("speed"));
		Assert.assertEquals("listTypeEntryKey2", values.get("state"));
		Assert.assertEquals(timestamp, values.get("time"));
		Assert.assertNotEquals(
			fileEntry.getFileEntryId(), values.get("upload"));
		Assert.assertEquals(60D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 21, values.size());

		// LPS-180587 Partial updates should not delete existing files

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"state", "listTypeEntryKey3"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		long persistedFileEntryId = GetterUtil.getLong(values.get("upload"));

		Assert.assertNotNull(
			_dlAppLocalService.getFileEntry(persistedFileEntryId));

		long fileEntryId = fileEntry.getFileEntryId();

		AssertUtils.assertFailure(
			NoSuchFileEntryException.class,
			StringBundler.concat(
				"No FileEntry exists with the key {fileEntryId=", fileEntryId,
				"}"),
			() -> _dlAppLocalService.getFileEntry(fileEntryId));

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"state", "listTypeEntryKey3"
			).put(
				"upload", 0L
			).put(
				"weight", 65D
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertCount(1);

		values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(94L, values.get("ageOfDeath"));
		Assert.assertTrue((boolean)values.get("authorOfGospel"));
		Assert.assertEquals(birthdayDate, values.get("birthday"));
		Assert.assertEquals(
			"john@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(180D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKey"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(
			"multipleListTypeEntryKey5, multipleListTypeEntryKey6",
			values.get("multipleListTypeEntriesKey"));
		Assert.assertEquals(5, values.get("numberOfBooksWritten"));
		Assert.assertEquals(script, values.get("script"));
		Assert.assertEquals(_getBigDecimal(45L), values.get("speed"));
		Assert.assertEquals("listTypeEntryKey3", values.get("state"));
		Assert.assertEquals(timestamp, values.get("time"));
		Assert.assertEquals(0L, values.get("upload"));
		Assert.assertEquals(65D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 21, values.size());

		AssertUtils.assertFailure(
			NoSuchFileEntryException.class,
			StringBundler.concat(
				"No FileEntry exists with the key {fileEntryId=", fileEntryId,
				"}"),
			() -> _dlAppLocalService.getFileEntry(fileEntryId));

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			new HashMap<String, Serializable>(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				_objectDefinition.getPKObjectFieldName(), ""
			).put(
				"invalidName", ""
			).build(),
			ServiceContextTestUtil.getServiceContext());

		long objectEntryId = objectEntry.getObjectEntryId();

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsIntegerSize.class,
			"Object entry value exceeds integer field allowed size",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"numberOfBooksWritten", "2147483648"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsIntegerSize.class,
			"Object entry value exceeds integer field allowed size",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"numberOfBooksWritten", "-2147483649"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsLongMaxSize.class,
			"Object entry value exceeds maximum long field allowed size",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "9007199254740992"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsLongMinSize.class,
			"Object entry value falls below minimum long field allowed size",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "-9007199254740992"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsLongSize.class,
			"Object entry value exceeds long field allowed size",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "9223372036854775808"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsLongSize.class,
			"Object entry value exceeds long field allowed size",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "-9223372036854775809"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		AssertUtils.assertFailure(
			ObjectEntryValuesException.ExceedsTextMaxLength.class,
			"Object entry value exceeds the maximum length of 280 characters " +
				"for object field \"firstName\"",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomTestUtil.randomString(281)
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		AssertUtils.assertFailure(
			ObjectEntryValuesException.UniqueValueConstraintViolation.class,
			"Unique value constraint violation for " +
				_objectDefinition.getDBTableName() +
					".emailAddress_ with value james@liferay.com",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", "james@liferay.com"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		_testUpdateObjectEntryExternalReferenceCode();
		_testUpdateObjectEntryObjectStateTransitions();
	}

	@Test
	public void testUpdateStatus() throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

			_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
				TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), 0,
				_objectDefinition.getClassName(), 0, 0, "Single Approver", 1);

			ObjectEntry objectEntry = _addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "peter@liferay.com"
				).put(
					"firstName", "Peter"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.assertEquals(
				WorkflowConstants.STATUS_PENDING, objectEntry.getStatus());

			List<WorkflowTask> workflowTasks =
				_workflowTaskManager.getWorkflowTasksBySubmittingUser(
					TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
					false, 0, 1, null);

			WorkflowTask workflowTask = workflowTasks.get(0);

			_workflowTaskManager.assignWorkflowTaskToUser(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				workflowTask.getWorkflowTaskId(), TestPropsValues.getUserId(),
				StringPool.BLANK, null, null);

			_workflowTaskManager.completeWorkflowTask(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				workflowTask.getWorkflowTaskId(), Constants.APPROVE,
				StringPool.BLANK, null);

			objectEntry = _objectEntryLocalService.getObjectEntry(
				objectEntry.getObjectEntryId());

			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, objectEntry.getStatus());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Rule
	public TestName testName = new TestName();

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

	private ObjectEntry _addObjectEntry(Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), values,
			ServiceContextTestUtil.getServiceContext());
	}

	private ObjectValidationRule _addObjectValidationRule(
			String engine, Map<Locale, String> errorLabelMap, String script)
		throws Exception {

		return _addObjectValidationRule(
			engine, errorLabelMap,
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION, script,
			Collections.emptyList());
	}

	private ObjectValidationRule _addObjectValidationRule(
			String engine, Map<Locale, String> errorLabelMap, String outputType,
			String script,
			List<ObjectValidationRuleSetting> objectValidationRuleSettings)
		throws Exception {

		return _objectValidationRuleLocalService.addObjectValidationRule(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, engine,
			errorLabelMap,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			outputType, script, false, objectValidationRuleSettings);
	}

	private ObjectEntry _addOrUpdateObjectEntry(
			String externalReferenceCode, long groupId,
			Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addOrUpdateObjectEntry(
			externalReferenceCode, TestPropsValues.getUserId(), groupId,
			_objectDefinition.getObjectDefinitionId(), values,
			ServiceContextTestUtil.getServiceContext());
	}

	private FileEntry _addTempFileEntry(String title) throws Exception {
		return TempFileEntryUtil.addTempFileEntry(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			_objectDefinition.getPortletId(),
			TempFileEntryUtil.getTempFileName(title + ".txt"),
			FileUtil.createTempFile(RandomTestUtil.randomBytes()),
			ContentTypes.TEXT_PLAIN);
	}

	private void _assertCount(int count) throws Exception {
		Assert.assertEquals(
			count,
			_assetEntryLocalService.getEntriesCount(
				new AssetEntryQuery() {
					{
						setClassName(_objectDefinition.getClassName());
						setVisible(null);
					}
				}));
		Assert.assertEquals(
			count,
			_objectEntryLocalService.getObjectEntriesCount(
				0, _objectDefinition.getObjectDefinitionId()));
		Assert.assertEquals(count, _count());
	}

	private void _assertKeywords(String keywords, int count) throws Exception {
		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			_objectEntryLocalService.searchObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), keywords, 0, 20);

		Assert.assertEquals(count, baseModelSearchResult.getLength());
	}

	private void _assertObjectEntryValues(
		int expectedValuesSize, Map<String, Serializable> expectedValues,
		Map<String, Serializable> actualValues) {

		Assert.assertEquals(
			expectedValues.get("emailAddressDomain"),
			actualValues.get("emailAddressDomain"));
		Assert.assertEquals(
			expectedValues.get("emailAddressRequired"),
			actualValues.get("emailAddressRequired"));
		Assert.assertEquals(
			expectedValues.get("firstName"), actualValues.get("firstName"));
		Assert.assertEquals(
			expectedValues.get("listTypeEntryKeyRequired"),
			actualValues.get("listTypeEntryKeyRequired"));

		Assert.assertEquals(
			actualValues.toString(), expectedValuesSize, actualValues.size());
	}

	private void _assertObjectValidationRuleResult(
		String expectedErrorMessage, String expectedObjectFieldName,
		ObjectValidationRuleResult objectValidationRuleResult) {

		Assert.assertEquals(
			expectedErrorMessage, objectValidationRuleResult.getErrorMessage());
		Assert.assertEquals(
			expectedObjectFieldName,
			objectValidationRuleResult.getObjectFieldName());
	}

	private void _assertTimestamp(Date date, Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Assert.assertEquals(
			new Timestamp(calendar.getTimeInMillis()), timestamp);
	}

	private boolean _containsObjectEntryValuesSQLQuery(LogCapture logCapture) {
		List<LogEntry> logEntries = logCapture.getLogEntries();

		for (LogEntry logEntry : logEntries) {
			String message = logEntry.getMessage();

			if (message.startsWith("select") &&
				message.contains(_objectDefinition.getDBTableName()) &&
				message.contains(_objectDefinition.getExtensionDBTableName())) {

				return true;
			}
		}

		return false;
	}

	private int _count() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from " + _objectDefinition.getDBTableName());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			return resultSet.getInt(1);
		}
	}

	private List<ListTypeEntry> _createListTypeEntries(
		String prefixKey, String prefixName, int size) {

		List<ListTypeEntry> listTypeEntries = new ArrayList<>();

		for (int i = 1; i <= size; i++) {
			listTypeEntries.add(
				ListTypeEntryUtil.createListTypeEntry(
					prefixKey + i,
					Collections.singletonMap(LocaleUtil.US, prefixName + i)));
		}

		return listTypeEntries;
	}

	private BigDecimal _getBigDecimal(long value) {
		BigDecimal bigDecimal = BigDecimal.valueOf(value);

		return bigDecimal.setScale(16);
	}

	private Map<String, Serializable> _getValuesFromCacheField(
			ObjectEntry objectEntry)
		throws Exception {

		Map<String, Serializable> values = null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.object.model.impl.ObjectEntryImpl",
				LoggerTestUtil.DEBUG)) {

			values = objectEntry.getValues();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				logEntry.getMessage(),
				"Use cached values for object entry " +
					objectEntry.getObjectEntryId());
		}

		return values;
	}

	private Map<String, Serializable> _getValuesFromDatabase(
			ObjectEntry objectEntry)
		throws Exception {

		Map<String, Serializable> values = null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.object.model.impl.ObjectEntryImpl",
				LoggerTestUtil.DEBUG)) {

			values = objectEntry.getValues();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				logEntry.getMessage(),
				"Get values for object entry " +
					objectEntry.getObjectEntryId());
		}

		return values;
	}

	private ObjectDefinition _publishCustomObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				false, _objectDefinitionLocalService, objectFields);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private void _testScope(long groupId, String scope, boolean expectSuccess)
		throws Exception {

		ObjectDefinition objectDefinition = _publishCustomObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					RandomTestUtil.randomString(), StringUtil.randomId())));

		objectDefinition.setScope(scope);

		_objectDefinitionLocalService.updateObjectDefinition(objectDefinition);

		Assert.assertEquals(
			0,
			_objectEntryLocalService.getObjectEntriesCount(
				groupId, objectDefinition.getObjectDefinitionId()));

		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			_objectEntryLocalService.searchObjectEntries(
				groupId, objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());

		if (!expectSuccess) {
			AssertUtils.assertFailure(
				ObjectDefinitionScopeException.class,
				StringBundler.concat(
					"Group ID ", groupId, " is not valid for scope \"", scope,
					"\""),
				() -> _objectEntryLocalService.addObjectEntry(
					TestPropsValues.getUserId(), groupId,
					objectDefinition.getObjectDefinitionId(),
					Collections.<String, Serializable>emptyMap(),
					ServiceContextTestUtil.getServiceContext()));
		}
		else {
			_objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), groupId,
				objectDefinition.getObjectDefinitionId(),
				Collections.<String, Serializable>emptyMap(),
				ServiceContextTestUtil.getServiceContext());

			Assert.assertEquals(
				1,
				_objectEntryLocalService.getObjectEntriesCount(
					groupId, objectDefinition.getObjectDefinitionId()));

			baseModelSearchResult =
				_objectEntryLocalService.searchObjectEntries(
					groupId, objectDefinition.getObjectDefinitionId(), null, 0,
					20);

			Assert.assertEquals(1, baseModelSearchResult.getLength());
		}

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	private void _testUpdateObjectEntryExternalReferenceCode()
		throws Exception {

		ObjectEntry objectEntry1 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		Assert.assertEquals(
			objectEntry1.getUuid(), objectEntry1.getExternalReferenceCode());

		long objectEntryId1 = objectEntry1.getObjectEntryId();

		objectEntry1 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryId1,
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", "newExternalReferenceCode"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			"newExternalReferenceCode",
			objectEntry1.getExternalReferenceCode());

		ObjectEntry objectEntry2 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "matthew@liferay.com"
			).put(
				"firstName", "Matthew"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build());

		long objectEntryId2 = objectEntry2.getObjectEntryId();

		AssertUtils.assertFailure(
			ObjectEntryValuesException.MustNotBeDuplicate.class,
			"Duplicate value newExternalReferenceCode",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId2,
				HashMapBuilder.<String, Serializable>put(
					"externalReferenceCode", "newExternalReferenceCode"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		objectEntry2 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryId2,
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", ""
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			objectEntry2.getUuid(), objectEntry2.getExternalReferenceCode());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryId2,
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", objectEntry1.getUuid()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		AssertUtils.assertFailure(
			ObjectEntryValuesException.MustNotBeDuplicate.class,
			"Duplicate value " + objectEntry1.getUuid(),
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId1,
				HashMapBuilder.<String, Serializable>put(
					"externalReferenceCode", ""
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		String randomString = RandomTestUtil.randomString();

		objectEntry1 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryId1,
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", randomString
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			randomString, objectEntry1.getExternalReferenceCode());

		_objectEntryLocalService.deleteObjectEntry(objectEntryId1);
		_objectEntryLocalService.deleteObjectEntry(objectEntryId2);
	}

	private void _testUpdateObjectEntryObjectStateTransitions()
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			_objectDefinition.getObjectDefinitionId(), "state");

		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.fetchObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());

		List<ObjectState> objectStates =
			_objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId());

		for (ObjectState objectState : objectStates) {
			List<ObjectStateTransition> objectStateTransitions =
				_objectStateTransitionLocalService.
					getObjectStateObjectStateTransitions(
						objectState.getObjectStateId());

			objectState.setObjectStateTransitions(
				Collections.singletonList(objectStateTransitions.get(0)));
		}

		objectStateFlow.setObjectStates(objectStates);

		_objectStateTransitionLocalService.updateObjectStateTransitions(
			objectStateFlow);

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		ObjectState objectStateListTypeEntryKey1 = objectStates.get(0);
		ObjectState objectStateListTypeEntryKey2 = objectStates.get(1);
		ObjectState objectStateListTypeEntryKey3 = objectStates.get(2);

		long objectEntryId = objectEntry.getObjectEntryId();

		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidObjectStateTransition.class,
			StringBundler.concat(
				"Object state ID ",
				objectStateListTypeEntryKey1.getObjectStateId(),
				" cannot be transitioned to object state ID ",
				objectStateListTypeEntryKey3.getObjectStateId()),
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"state", "listTypeEntryKey3"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		objectEntry = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"state", "listTypeEntryKey2"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		AssertUtils.assertFailure(
			ObjectEntryValuesException.InvalidObjectStateTransition.class,
			StringBundler.concat(
				"Object state ID ",
				objectStateListTypeEntryKey2.getObjectStateId(),
				" cannot be transitioned to object state ID ",
				objectStateListTypeEntryKey3.getObjectStateId()),
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"state", "listTypeEntryKey3"
				).build(),
				ServiceContextTestUtil.getServiceContext()));

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry.getObjectEntryId());
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private Encryptor _encryptor;

	@DeleteAfterTestRun
	private ObjectDefinition _irrelevantObjectDefinition;

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.object.internal.model.listener.ObjectEntryModelListener"
	)
	private ModelListener<ObjectEntry> _objectEntryModelListener;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Inject
	private ObjectStateLocalService _objectStateLocalService;

	@Inject
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

	@Inject
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}