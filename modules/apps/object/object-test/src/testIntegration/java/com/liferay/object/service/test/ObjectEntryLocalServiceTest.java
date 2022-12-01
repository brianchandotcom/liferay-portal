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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.exception.ObjectDefinitionAccountEntryRestrictedException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.field.util.ObjectFieldFormulaEvaluatorUtil;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import org.hamcrest.CoreMatchers;

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
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-158776", "true"
			).build());

		_irrelevantObjectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		_irrelevantObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_irrelevantObjectDefinition.getObjectDefinitionId());

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()));

		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "listTypeEntryKey1",
			Collections.singletonMap(LocaleUtil.US, "List Type Entry Key 1"));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "listTypeEntryKey2",
			Collections.singletonMap(LocaleUtil.US, "List Type Entry Key 2"));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "listTypeEntryKey3",
			Collections.singletonMap(LocaleUtil.US, "List Type Entry Key 3"));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			"multipleListTypeEntryKey1",
			Collections.singletonMap(
				LocaleUtil.US, "Multiple List Type Entry Key 1"));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			"multipleListTypeEntryKey2",
			Collections.singletonMap(
				LocaleUtil.US, "Multiple List Type Entry Key 2"));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			"multipleListTypeEntryKey3",
			Collections.singletonMap(
				LocaleUtil.US, "Multiple List Type Entry Key 3"));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			"multipleListTypeEntryKey4",
			Collections.singletonMap(
				LocaleUtil.US, "Multiple List Type Entry Key 4"));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			"multipleListTypeEntryKey5",
			Collections.singletonMap(
				LocaleUtil.US, "Multiple List Type Entry Key 5"));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			"multipleListTypeEntryKey6",
			Collections.singletonMap(
				LocaleUtil.US, "Multiple List Type Entry Key 6"));

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService,
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
					"Email Address", "emailAddress", false),
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

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		_objectFieldLocalService.addCustomObjectField(
			null, TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
			ObjectFieldConstants.DB_TYPE_BIG_DECIMAL, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap("Speed"), "speed", false, false,
			Collections.emptyList());
		_objectFieldLocalService.addCustomObjectField(
			null, TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
			ObjectFieldConstants.DB_TYPE_LONG, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap("Upload"), "upload", false, false,
			Arrays.asList(
				_createObjectFieldSetting("acceptedFileExtensions", "txt"),
				_createObjectFieldSetting("fileSource", "userComputer"),
				_createObjectFieldSetting("maximumFileSize", "100")));
		_objectFieldLocalService.addCustomObjectField(
			null, TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
			ObjectFieldConstants.DB_TYPE_DOUBLE, null, true, false, null,
			LocalizedMapUtil.getLocalizedMap("Weight"), "weight", false, false,
			Collections.emptyList());
	}

	@After
	public void tearDown() throws Exception {

		// Do not rely on @DeleteAfterTestRun because object entries that
		// reference a required list type entry cannot be deleted before it is
		// unreferenced

		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-158776", "false"
			).build());
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

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"numberOfBooksWritten", "2147483648"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsIntegerSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds integer field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"numberOfBooksWritten", "-2147483649"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsIntegerSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds integer field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "9007199254740992"
				).put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsLongMaxSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds maximum long field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "-9007199254740992"
				).put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsLongMinSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value falls below minimum long field allowed " +
					"size",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "9223372036854775808"
				).put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsLongSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds long field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "-9223372036854775809"
				).put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsLongSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds long field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"firstName", RandomTestUtil.randomString(281)
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsTextMaxLength
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds the maximum length of 280 " +
					"characters for object field \"firstName\"",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "matthew@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"script", RandomTestUtil.randomString(65001)
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsTextMaxLength
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds the maximum length of 65000 " +
					"characters for object field \"script\"",
				objectEntryValuesException.getMessage());
		}

		try {
			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				_objectDefinition.getObjectDefinitionId(), "upload");

			ObjectFieldSetting objectFieldSetting =
				_objectFieldSettingLocalService.fetchObjectFieldSetting(
					objectField.getObjectFieldId(), "acceptedFileExtensions");

			_objectFieldSettingLocalService.updateObjectFieldSetting(
				objectFieldSetting.getObjectFieldSettingId(), "jpg, png");

			FileEntry fileEntry = _addTempFileEntry(StringUtil.randomString());

			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "peter@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"upload", fileEntry.getFileEntryId()
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.InvalidFileExtension
					objectEntryValuesException) {

			Assert.assertEquals(
				"The file extension txt is invalid for object field \"upload\"",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "john@liferay.com"
				).put(
					"listTypeEntryKeyRequired", RandomTestUtil.randomString()
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ListTypeEntry
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object field name \"listTypeEntryKeyRequired\" is not " +
					"mapped to a valid list type entry",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"firstName", "Judas"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.Required objectEntryValuesException) {
			Assert.assertEquals(
				"No value was provided for required object field " +
					"\"emailAddressRequired\"",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddressRequired", "john@liferay.com"
				).put(
					"firstName", "Judas"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.Required objectEntryValuesException) {
			Assert.assertEquals(
				"No value was provided for required object field " +
					"\"listTypeEntryKeyRequired\"",
				objectEntryValuesException.getMessage());
		}
	}

	@Test
	public void testAddObjectEntryWithFormulaObjectField() throws Exception {
		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			null, TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_FORMULA,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, false, null,
			LocalizedMapUtil.getLocalizedMap("Full Name"), "fullName", false,
			false,
			Arrays.asList(
				_createObjectFieldSetting(
					"script",
					"concat(firstName, \" \", middleName, \" \", lastName)"),
				_createObjectFieldSetting(
					"output", ObjectFieldConstants.BUSINESS_TYPE_TEXT)));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ObjectFieldFormulaEvaluatorUtil.class.getName(),
				LoggerTestUtil.ERROR)) {

			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", RandomTestUtil.randomString()
				).put(
					"emailAddressRequired", "athanasius@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());
		}

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", RandomTestUtil.randomString()
			).put(
				"emailAddressRequired", "athanasius@liferay.com"
			).put(
				"firstName", "Athanasius"
			).put(
				"lastName", "Mundum"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"middleName", "Contra"
			).build());

		Assert.assertNotNull(objectEntry);

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals("Athanasius Contra Mundum", values.get("fullName"));

		_objectFieldLocalService.deleteObjectField(objectField);
	}

	@Test
	public void testAddObjectEntryWithObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule =
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap(
					"Field must be an email address"),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"isEmailAddress(emailAddress)");

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", RandomTestUtil.randomString()
				).put(
					"emailAddressRequired", "john@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ModelListenerException modelListenerException) {
			String message = modelListenerException.getMessage();

			Assert.assertTrue(
				message.contains("Field must be an email address"));
		}

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"emailAddressRequired", "bob@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		Assert.assertNotNull(objectEntry);

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));

		_objectValidationRuleLocalService.updateObjectValidationRule(
			objectValidationRule.getObjectValidationRuleId(), false,
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"isEmailAddress(emailAddress)");

		objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", RandomTestUtil.randomString()
			).put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		Assert.assertNotNull(objectEntry);

		objectValidationRule =
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap("Names must be equals"),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"equals(lastName, middleName)");

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", "john@liferay.com"
				).put(
					"emailAddressRequired", "bob@liferay.com"
				).put(
					"lastName", RandomTestUtil.randomString()
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).put(
					"middleName", RandomTestUtil.randomString()
				).build());

			Assert.fail();
		}
		catch (ModelListenerException modelListenerException) {
			String message = modelListenerException.getMessage();

			Assert.assertTrue(message.contains("Names must be equals"));
		}

		objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"emailAddressRequired", "bob@liferay.com"
			).put(
				"lastName", "Doe"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"middleName", "Doe"
			).build());

		Assert.assertNotNull(objectEntry);

		values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals("Doe", values.get("lastName"));
		Assert.assertEquals("Doe", values.get("middleName"));

		_objectValidationRuleLocalService.updateObjectValidationRule(
			objectValidationRule.getObjectValidationRuleId(), false,
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"equals(lastName, middleName)");

		Class<?> clazz = getClass();

		_objectValidationRuleLocalService.addObjectValidationRule(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
			LocalizedMapUtil.getLocalizedMap("Must be over 18 years old"),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.read(
				clazz,
				StringBundler.concat(
					"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
					testName.getMethodName(), ".groovy")));

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"birthday", "2010-12-25"
				).put(
					"emailAddressRequired", "bob@liferay.com"
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ModelListenerException modelListenerException) {
			String message = modelListenerException.getMessage();

			Assert.assertTrue(message.contains("Must be over 18 years old"));
		}

		objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"birthday", "2000-12-25"
			).put(
				"emailAddressRequired", "bob@liferay.com"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		Assert.assertNotNull(objectEntry);
	}

	@Test
	public void testAddOrUpdateObjectEntry() throws Exception {
		_assertCount(0);

		ObjectEntry objectEntry = _addOrUpdateObjectEntry(
			"peter", 0,
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(1);

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));

		_addOrUpdateObjectEntry(
			"peter", 0,
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "pedro@liferay.com"
			).put(
				"firstName", "Pedro"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build());

		_assertCount(1);

		values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(
			"pedro@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("Pedro", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));

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
	public void testAddOrUpdateObjectEntryWithAccountEntryRestrictedObjectFieldId()
		throws Exception {

		_user = UserTestUtil.addUser();

		ObjectDefinition accountObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_user.getCompanyId(), "accountEntry");

		ObjectDefinition customObjectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "text")));

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				_user.getUserId(),
				accountObjectDefinition.getObjectDefinitionId(),
				customObjectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"relationship", ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		customObjectDefinition.setAccountEntryRestricted(true);

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			customObjectDefinition.getObjectDefinitionId(),
			"r_relationship_accountEntryId");

		customObjectDefinition.setAccountEntryRestrictedObjectFieldId(
			objectField.getObjectFieldId());

		_objectDefinitionLocalService.updateObjectDefinition(
			customObjectDefinition);

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			_user.getUserId(), customObjectDefinition.getObjectDefinitionId());

		long invalidAccountEntryId = -1;

		try {
			_objectEntryLocalService.addObjectEntry(
				_user.getUserId(), 0,
				customObjectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"r_relationship_accountEntryId", invalidAccountEntryId
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(
				exception.getCause() instanceof
					ObjectDefinitionAccountEntryRestrictedException);

			ObjectDefinitionAccountEntryRestrictedException
				objectDefinitionAccountEntryRestrictedException =
					(ObjectDefinitionAccountEntryRestrictedException)
						exception.getCause();

			Assert.assertEquals(
				StringBundler.concat(
					"The account entry ", invalidAccountEntryId,
					" does not exist or the user ", _user.getUserId(),
					" does not belong to it"),
				objectDefinitionAccountEntryRestrictedException.getMessage());
		}

		AccountEntry accountEntry1 = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			"account", null, null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		AccountEntryUserRel accountEntryUserRel1 =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountEntry1.getAccountEntryId(), _user.getUserId());

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), "Administrator");

		_userLocalService.addRoleUser(role.getRoleId(), _user);

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			_user.getUserId(), 0,
			customObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"r_relationship_accountEntryId",
				accountEntry1.getAccountEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_userLocalService.deleteRoleUser(role.getRoleId(), _user);

		Map<String, Serializable> values = objectEntry.getValues();

		Assert.assertEquals(
			accountEntry1.getAccountEntryId(),
			values.get("r_relationship_accountEntryId"));

		try {
			objectEntry = _objectEntryLocalService.updateObjectEntry(
				_user.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"r_relationship_accountEntryId", invalidAccountEntryId
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(
				exception.getCause() instanceof
					ObjectDefinitionAccountEntryRestrictedException);

			ObjectDefinitionAccountEntryRestrictedException
				objectDefinitionAccountEntryRestrictedException =
					(ObjectDefinitionAccountEntryRestrictedException)
						exception.getCause();

			Assert.assertEquals(
				StringBundler.concat(
					"The account entry ", invalidAccountEntryId,
					" does not exist or the user ", _user.getUserId(),
					" does not belong to it"),
				objectDefinitionAccountEntryRestrictedException.getMessage());
		}

		AccountEntry accountEntry2 = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			"account", null, null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		AccountEntryUserRel accountEntryUserRel2 =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountEntry2.getAccountEntryId(), _user.getUserId());

		_userLocalService.addRoleUser(role.getRoleId(), _user);

		objectEntry = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"r_relationship_accountEntryId",
				accountEntry2.getAccountEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		values = objectEntry.getValues();

		Assert.assertEquals(
			accountEntry2.getAccountEntryId(),
			values.get("r_relationship_accountEntryId"));

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(
			customObjectDefinition);

		_accountEntryUserRelLocalService.deleteAccountEntryUserRel(
			accountEntryUserRel1);
		_accountEntryUserRelLocalService.deleteAccountEntryUserRel(
			accountEntryUserRel2);

		_accountEntryLocalService.deleteAccountEntry(accountEntry1);
		_accountEntryLocalService.deleteAccountEntry(accountEntry2);
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

		_objectDefinitionLocalService.updateObjectDefinition(_objectDefinition);

		FileEntry fileEntry = _addTempFileEntry("Old Testament");

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).put(
				"upload", fileEntry.getFileEntryId()
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

		fileEntry = _addTempFileEntry("New Testament");

		objectEntry = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).put(
				"upload", fileEntry.getFileEntryId()
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

		FileEntry fileEntry = _addTempFileEntry(StringUtil.randomString());

		ObjectEntry objectEntry2 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).put(
				"upload", fileEntry.getFileEntryId()
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

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry1.getObjectEntryId());

		try {
			_objectEntryLocalService.deleteObjectEntry(
				objectEntry1.getObjectEntryId());

			Assert.fail();
		}
		catch (NoSuchObjectEntryException noSuchObjectEntryException) {
			Assert.assertEquals(
				"No ObjectEntry exists with the primary key " +
					objectEntry1.getObjectEntryId(),
				noSuchObjectEntryException.getMessage());
		}

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);

		try {
			_objectEntryLocalService.getValues(objectEntry1.getObjectEntryId());

			Assert.fail();
		}
		catch (NoSuchObjectEntryException noSuchObjectEntryException) {
			Assert.assertEquals(
				"No ObjectEntry exists with the primary key " +
					objectEntry1.getObjectEntryId(),
				noSuchObjectEntryException.getMessage());
		}

		_assertCount(2);

		Map<String, Serializable> values = objectEntry2.getValues();

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

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry2.getObjectEntryId());

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

		_assertCount(1);

		_objectEntryLocalService.deleteObjectEntry(objectEntry3);

		_assertCount(0);
	}

	@Test
	public void testGetExtensionDynamicObjectDefinitionTableValues()
		throws Exception {

		Queue<String> cacheKeyPrefixes = new LinkedList<>();

		FinderCache finderCache = FinderCacheUtil.getFinderCache();

		ReflectionTestUtil.setFieldValue(
			FinderCacheUtil.class, "_finderCache",
			ProxyUtil.newProxyInstance(
				FinderCache.class.getClassLoader(),
				new Class<?>[] {FinderCache.class},
				(proxy, method, arguments) -> {
					if (Objects.equals(method.getName(), "removeResult")) {
						FinderPath finderPath = (FinderPath)arguments[0];

						cacheKeyPrefixes.add(finderPath.getCacheKeyPrefix());
					}

					return method.invoke(finderCache, arguments);
				}));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), User.class.getName());

		_objectFieldLocalService.addCustomObjectField(
			null, TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG, null,
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"longField", false, false, Collections.emptyList());
		_objectFieldLocalService.addCustomObjectField(
			null, TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null,
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"textField", true, false, Collections.emptyList());

		User user = UserTestUtil.addUser();

		Assert.assertTrue(
			MapUtil.isEmpty(
				_objectEntryLocalService.
					getExtensionDynamicObjectDefinitionTableValues(
						objectDefinition, user.getUserId())));

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

		Assert.assertEquals(0, cacheKeyPrefixes.size());

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

		_assertCacheKeyPrefixes(
			cacheKeyPrefixes, objectDefinition.getExtensionDBTableName());

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

		_assertCacheKeyPrefixes(
			cacheKeyPrefixes, objectDefinition.getExtensionDBTableName());

		Assert.assertEquals(
			values,
			_objectEntryLocalService.
				getExtensionDynamicObjectDefinitionTableValues(
					objectDefinition, user.getUserId()));

		_objectEntryLocalService.
			deleteExtensionDynamicObjectDefinitionTableValues(
				objectDefinition, user.getUserId());

		_assertCacheKeyPrefixes(
			cacheKeyPrefixes, objectDefinition.getExtensionDBTableName());

		Assert.assertTrue(
			MapUtil.isEmpty(
				_objectEntryLocalService.
					getExtensionDynamicObjectDefinitionTableValues(
						objectDefinition, user.getUserId())));

		ReflectionTestUtil.setFieldValue(
			FinderCacheUtil.class, "_finderCache", finderCache);
	}

	@Test
	public void testGetObjectEntries() throws Exception {
		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_assertCount(0);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		objectEntries = _objectEntryLocalService.getObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_assertCount(1);

		int expectedValuesSize = 19;

		Map<String, Serializable> values = _getValuesFromDatabase(
			objectEntries.get(0));

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build());

		objectEntries = _objectEntryLocalService.getObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_assertCount(2);

		values = _getValuesFromDatabase(objectEntries.get(0));

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = _getValuesFromDatabase(objectEntries.get(1));

		Assert.assertEquals(
			"james@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		objectEntries = _objectEntryLocalService.getObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 3, objectEntries.size());

		_assertCount(3);

		values = _getValuesFromDatabase(objectEntries.get(0));

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = _getValuesFromDatabase(objectEntries.get(1));

		Assert.assertEquals(
			"james@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = _getValuesFromDatabase(objectEntries.get(2));

		Assert.assertEquals(
			"john@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

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
		Assert.assertEquals(0D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 19, values.size());

		try {
			_objectEntryLocalService.getValues(0);

			Assert.fail();
		}
		catch (NoSuchObjectEntryException noSuchObjectEntryException) {
			Assert.assertEquals(
				"No ObjectEntry exists with the primary key 0",
				noSuchObjectEntryException.getMessage());
		}
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

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		valuesList = _objectEntryLocalService.getValuesList(
			0, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), null, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(valuesList.toString(), 1, valuesList.size());

		_assertCount(1);

		Map<String, Serializable> values = valuesList.get(0);

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));

		int expectedValuesSize = 25;

		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build());

		valuesList = _objectEntryLocalService.getValuesList(
			0, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), null, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(valuesList.toString(), 2, valuesList.size());

		_assertCount(2);

		values = valuesList.get(0);

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = valuesList.get(1);

		Assert.assertEquals(
			"james@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		valuesList = _objectEntryLocalService.getValuesList(
			0, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), null, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(valuesList.toString(), 3, valuesList.size());

		_assertCount(3);

		values = valuesList.get(0);

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = valuesList.get(1);

		Assert.assertEquals(
			"james@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = valuesList.get(2);

		Assert.assertEquals(
			"john@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

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

		long depotEntryGroupId = depotEntry.getGroupId();

		long siteGroupId = TestPropsValues.getGroupId();

		_testScope(0, ObjectDefinitionConstants.SCOPE_COMPANY, true);
		_testScope(
			depotEntryGroupId, ObjectDefinitionConstants.SCOPE_COMPANY, false);
		_testScope(siteGroupId, ObjectDefinitionConstants.SCOPE_COMPANY, false);

		// Scope by depot

		// TODO Turn on theses tests once depot is reenabled

		/*_testScope(0, ObjectDefinitionConstants.SCOPE_DEPOT, false);
		_testScope(
			depotEntryGroupId, ObjectDefinitionConstants.SCOPE_DEPOT, true);
		_testScope(siteGroupId, ObjectDefinitionConstants.SCOPE_DEPOT, false);*/

		// Scope by site

		_testScope(0, ObjectDefinitionConstants.SCOPE_SITE, false);
		_testScope(
			depotEntryGroupId, ObjectDefinitionConstants.SCOPE_SITE, false);
		_testScope(siteGroupId, ObjectDefinitionConstants.SCOPE_SITE, true);
	}

	@Test
	public void testSearchObjectEntries() throws Exception {

		// Without keywords

		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			_objectEntryLocalService.searchObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"emailAddressRequired", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<ObjectEntry> objectEntries = baseModelSearchResult.getBaseModels();

		int expectedValuesSize = 19;

		Map<String, Serializable> values = _getValuesFromDatabase(
			objectEntries.get(0));

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"emailAddressRequired", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build());

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(2, baseModelSearchResult.getLength());

		objectEntries = baseModelSearchResult.getBaseModels();

		values = _getValuesFromDatabase(objectEntries.get(0));

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = _getValuesFromDatabase(objectEntries.get(1));

		Assert.assertEquals(
			"james@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(3, baseModelSearchResult.getLength());

		objectEntries = baseModelSearchResult.getBaseModels();

		values = _getValuesFromDatabase(objectEntries.get(0));

		Assert.assertEquals(
			"peter@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = _getValuesFromDatabase(objectEntries.get(1));

		Assert.assertEquals(
			"james@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

		values = _getValuesFromDatabase(objectEntries.get(2));

		Assert.assertEquals(
			"john@liferay.com", values.get("emailAddressRequired"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(
			values.toString(), expectedValuesSize, values.size());

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

		_messages.clear();

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
		Assert.assertEquals(0L, values.get("upload"));
		Assert.assertEquals(0D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 19, values.size());

		Calendar calendar = new GregorianCalendar();

		calendar.set(6, Calendar.DECEMBER, 27);
		calendar.setTimeInMillis(0);

		Date birthdayDate = calendar.getTime();

		String script = RandomTestUtil.randomString(1500);
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
		Assert.assertNotEquals(
			fileEntry.getFileEntryId(), values.get("upload"));
		Assert.assertEquals(60D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 19, values.size());

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

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
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
		Assert.assertEquals(0L, values.get("upload"));
		Assert.assertEquals(65D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 19, values.size());

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

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"numberOfBooksWritten", "2147483648"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsIntegerSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds integer field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"numberOfBooksWritten", "-2147483649"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsIntegerSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds integer field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "9007199254740992"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsLongMaxSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds maximum long field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "-9007199254740992"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsLongMinSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value falls below minimum long field allowed " +
					"size",
				objectEntryValuesException.getMessage());
		}

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "9223372036854775808"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsLongSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds long field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"ageOfDeath", "-9223372036854775809"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsLongSize
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds long field allowed size",
				objectEntryValuesException.getMessage());
		}

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomTestUtil.randomString(281)
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException.ExceedsTextMaxLength
					objectEntryValuesException) {

			Assert.assertEquals(
				"Object entry value exceeds the maximum length of 280 " +
					"characters for object field \"firstName\"",
				objectEntryValuesException.getMessage());
		}

		_testUpdateExternalReferenceCode();
	}

	@Test
	public void testUpdateStatus() throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

			_testUpdateStatus();
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Rule
	public TestName testName = new TestName();

	private ObjectEntry _addObjectEntry(Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), values,
			ServiceContextTestUtil.getServiceContext());
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

	private void _assertCacheKeyPrefixes(
		Queue<String> cacheKeyPrefixes, String extensionDBTableName) {

		Assert.assertThat(
			StringUtil.removeChar(cacheKeyPrefixes.poll(), CharPool.PERIOD),
			CoreMatchers.containsString(
				"select count(*) COUNT_VALUE from " + extensionDBTableName));
		Assert.assertThat(
			StringUtil.removeChar(cacheKeyPrefixes.poll(), CharPool.PERIOD),
			CoreMatchers.containsString(
				StringBundler.concat(
					"select ", extensionDBTableName, "userId, ",
					extensionDBTableName, "longField_, ", extensionDBTableName,
					"textField_ from ", extensionDBTableName)));
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

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
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

	private void _testScope(long groupId, String scope, boolean expectSuccess)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		Assert.assertEquals(
			0,
			_objectEntryLocalService.getObjectEntriesCount(
				groupId, objectDefinition.getObjectDefinitionId()));

		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			_objectEntryLocalService.searchObjectEntries(
				groupId, objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());

		try {
			_objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), groupId,
				objectDefinition.getObjectDefinitionId(),
				Collections.<String, Serializable>emptyMap(),
				ServiceContextTestUtil.getServiceContext());

			if (!expectSuccess) {
				Assert.fail();
			}
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				StringBundler.concat(
					"Group ID ", groupId, " is not valid for scope \"", scope,
					"\""),
				objectDefinitionScopeException.getMessage());
		}

		if (expectSuccess) {
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

	private void _testUpdateExternalReferenceCode() throws Exception {
		ObjectEntry objectEntry1 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddressRequired", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(objectEntry1, "_values"));
		Assert.assertEquals(
			String.valueOf(objectEntry1.getUuid()),
			objectEntry1.getExternalReferenceCode());

		objectEntry1 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry1.getObjectEntryId(),
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

		try {
			objectEntry2 = _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry2.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"externalReferenceCode", "newExternalReferenceCode"
				).build(),
				ServiceContextTestUtil.getServiceContext());
		}
		catch (ObjectEntryValuesException.MustNotBeDuplicate
					objectEntryValuesException) {

			Assert.assertEquals(
				"Duplicate value newExternalReferenceCode",
				objectEntryValuesException.getMessage());
		}

		objectEntry2 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry2.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", ""
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			String.valueOf(objectEntry2.getObjectEntryId()),
			objectEntry2.getExternalReferenceCode());

		objectEntry2 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry2.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode",
				String.valueOf(objectEntry1.getObjectEntryId())
			).build(),
			ServiceContextTestUtil.getServiceContext());

		try {
			objectEntry1 = _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry1.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"externalReferenceCode", ""
				).build(),
				ServiceContextTestUtil.getServiceContext());
		}
		catch (ObjectEntryValuesException.MustNotBeDuplicate
					objectEntryValuesException) {

			Assert.assertEquals(
				"Duplicate value " +
					String.valueOf(objectEntry1.getObjectEntryId()),
				objectEntryValuesException.getMessage());
		}

		int randomInt = RandomTestUtil.randomInt();

		objectEntry1 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry1.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", randomInt
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			String.valueOf(randomInt), objectEntry1.getExternalReferenceCode());

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry1.getObjectEntryId());

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry2.getObjectEntryId());
	}

	private void _testUpdateStatus() throws Exception {
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

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _irrelevantObjectDefinition;

	@Inject
	private JSONFactory _jsonFactory;

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	private final Queue<Message> _messages = new LinkedList<>();
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
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}