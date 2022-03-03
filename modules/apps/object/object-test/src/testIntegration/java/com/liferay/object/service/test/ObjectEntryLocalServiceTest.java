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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
import java.util.Queue;

import org.junit.After;
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
public class ObjectEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_irrelevantObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		_irrelevantObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_irrelevantObjectDefinition.getObjectDefinitionId());

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()));

		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "listTypeEntryKey1",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "listTypeEntryKey2",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));
		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "listTypeEntryKey3",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"LongInteger", "Long", true, false, null,
						"Age of Death", "ageOfDeath", false),
					ObjectFieldUtil.createObjectField(
						"Boolean", "Boolean", true, false, null,
						"Author of Gospel", "authorOfGospel", false),
					ObjectFieldUtil.createObjectField(
						"Date", "Date", true, false, null, "Birthday",
						"birthday", false),
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null, "Email Address",
						"emailAddress", true),
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						"Email Address Domain", "emailAddressDomain", false),
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, false, null, "First Name",
						"firstName", false),
					ObjectFieldUtil.createObjectField(
						"Decimal", "Double", true, false, null, "Height",
						"height", false),
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, false, null, "Last Name",
						"lastName", false),
					ObjectFieldUtil.createObjectField(
						_listTypeDefinition.getListTypeDefinitionId(), "Text",
						null, "String", true, false, null,
						"List Type Entry Key", "listTypeEntryKey", false),
					ObjectFieldUtil.createObjectField(
						_listTypeDefinition.getListTypeDefinitionId(), "Text",
						null, "String", true, false, null,
						"List Type Entry Key Required",
						"listTypeEntryKeyRequired", true),
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, false, null, "Middle Name",
						"middleName", false),
					ObjectFieldUtil.createObjectField(
						"Integer", "Integer", true, false, null,
						"Number of Books Written", "numberOfBooksWritten",
						false),
					ObjectFieldUtil.createObjectField(
						"LargeFile", "Blob", false, false, null, "Portrait",
						"portrait", false),
					ObjectFieldUtil.createObjectField(
						"LongText", "Clob", false, false, null, "Script",
						"script", false),
					ObjectFieldUtil.createObjectField(
						"Attachment", "Long", true, false, null, "Upload",
						"upload", false)));

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		/*Destination destination = MessageBusUtil.getDestination(
			_objectDefinition.getDestinationName());

		destination.register(
			new MessageListener() {

				public void receive(Message message) {
					_messages.add(message);
				}

			});*/

		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), "PrecisionDecimal",
			"BigDecimal", true, false, null,
			LocalizedMapUtil.getLocalizedMap("Speed"), "speed", false,
			Collections.emptyList());
		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), "Decimal", "Double",
			true, false, null, LocalizedMapUtil.getLocalizedMap("Weight"),
			"weight", false, Collections.emptyList());
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
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(1);

		/*Assert.assertEquals(4, _messages.size());

		Message message = _messages.poll();

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject(
			(String)message.getPayload());

		Assert.assertEquals(
			"onBeforeCreate", payloadJSONObject.getString("webhookEventKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			"Peter",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertNull(
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry"));

		message = _messages.poll();

		payloadJSONObject = _jsonFactory.createJSONObject(
			(String)message.getPayload());

		Assert.assertEquals(
			"onAfterCreate", payloadJSONObject.getString("webhookEventKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			"Peter",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertNull(
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry"));

		message = _messages.poll();

		payloadJSONObject = _jsonFactory.createJSONObject(
			(String)message.getPayload());

		Assert.assertEquals(
			"onBeforeUpdate", payloadJSONObject.getString("webhookEventKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"Object/status"));

		message = _messages.poll();

		payloadJSONObject = _jsonFactory.createJSONObject(
			(String)message.getPayload());

		Assert.assertEquals(
			"onAfterUpdate", payloadJSONObject.getString("webhookEventKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"Object/status"));*/

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(2);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(3);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"listTypeEntryKey", "listTypeEntryKey1"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(4);

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", "john@liferay.com"
				).put(
					"listTypeEntryKeyRequired", RandomTestUtil.randomString()
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
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
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertEquals(
				"No value was provided for required object field " +
					"\"emailAddress\"",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", "john@liferay.com"
				).put(
					"firstName", "Judas"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertEquals(
				"No value was provided for required object field " +
					"\"listTypeEntryKeyRequired\"",
				objectEntryValuesException.getMessage());
		}

		try {
			_addObjectEntry(
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", "matthew@liferay.com"
				).put(
					"firstName", RandomTestUtil.randomString(281)
				).put(
					"listTypeEntryKeyRequired", "listTypeEntryKey1"
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertEquals(
				"Object field \"firstName\" value exceeds 280 characters.",
				objectEntryValuesException.getMessage());
		}
	}

	@Test
	public void testAddOrUpdateObjectEntry() throws Exception {
		_assertCount(0);

		ObjectEntry objectEntry = _addOrUpdateObjectEntry(
			"peter", 0,
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		_assertCount(1);

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));

		_addOrUpdateObjectEntry(
			"peter", 0,
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "pedro@liferay.com"
			).put(
				"firstName", "Pedro"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build());

		_assertCount(1);

		values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals("pedro@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Pedro", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));

		_addOrUpdateObjectEntry(
			"james", 0,
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		_assertCount(2);

		_addOrUpdateObjectEntry(
			"john", 0,
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
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
	public void testDeleteObjectEntry() throws Exception {
		ObjectEntry objectEntry1 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		String fileContent = StringUtil.merge(RandomTestUtil.randomStrings(20));

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			fileContent.getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext());

		ObjectEntry objectEntry2 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).put(
				"upload", fileEntry.getFileEntryId()
			).build());

		ObjectEntry objectEntry3 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
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

		Assert.assertNotNull(
			_dlAppLocalService.getFileEntry(fileEntry.getFileEntryId()));

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry2.getObjectEntryId());

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

		_assertCount(1);

		_objectEntryLocalService.deleteObjectEntry(objectEntry3);

		_assertCount(0);
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
				"emailAddress", "peter@liferay.com"
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

		Map<String, Serializable> values = _getValuesFromCacheField(
			objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
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

		values = _getValuesFromCacheField(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = _getValuesFromCacheField(objectEntries.get(1));

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
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

		values = _getValuesFromCacheField(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = _getValuesFromCacheField(objectEntries.get(1));

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = _getValuesFromCacheField(objectEntries.get(2));

		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		objectEntries = _objectEntryLocalService.getObjectEntries(
			0, _irrelevantObjectDefinition.getObjectDefinitionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
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
		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(0D, values.get("height"));
		Assert.assertEquals(null, values.get("lastName"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(0, values.get("numberOfBooksWritten"));
		Assert.assertEquals(null, values.get("listTypeEntryKey"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(null, values.get("portrait"));
		Assert.assertEquals(StringPool.BLANK, values.get("script"));
		Assert.assertEquals(_getBigDecimal(0L), values.get("speed"));
		Assert.assertEquals(0D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 18, values.size());

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
				_objectDefinition.getObjectDefinitionId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 0, valuesList.size());

		_assertCount(0);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		valuesList = _objectEntryLocalService.getValuesList(
			_objectDefinition.getObjectDefinitionId(), null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 1, valuesList.size());

		_assertCount(1);

		Map<String, Serializable> values = valuesList.get(0);

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build());

		valuesList = _objectEntryLocalService.getValuesList(
			_objectDefinition.getObjectDefinitionId(), null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 2, valuesList.size());

		_assertCount(2);

		values = valuesList.get(0);

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = valuesList.get(1);

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		valuesList = _objectEntryLocalService.getValuesList(
			_objectDefinition.getObjectDefinitionId(), null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 3, valuesList.size());

		_assertCount(3);

		values = valuesList.get(0);

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = valuesList.get(1);

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = valuesList.get(2);

		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		valuesList = _objectEntryLocalService.getValuesList(
			_irrelevantObjectDefinition.getObjectDefinitionId(), null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

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
				"emailAddress", "peter@liferay.com"
			).put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"firstName", "Peter"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
			).build());

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<ObjectEntry> objectEntries = baseModelSearchResult.getBaseModels();

		Map<String, Serializable> values = _getValuesFromCacheField(
			objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"firstName", "James"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey2"
			).build());

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(2, baseModelSearchResult.getLength());

		objectEntries = baseModelSearchResult.getBaseModels();

		values = _getValuesFromCacheField(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = _getValuesFromCacheField(objectEntries.get(1));

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey3"
			).build());

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(3, baseModelSearchResult.getLength());

		objectEntries = baseModelSearchResult.getBaseModels();

		values = _getValuesFromCacheField(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = _getValuesFromCacheField(objectEntries.get(1));

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

		values = _getValuesFromCacheField(objectEntries.get(2));

		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(values.toString(), 18, values.size());

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
			_objectDefinition.getObjectDefinitionId(), "emailAddress");

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			_objectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
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
				"emailAddress", "john@liferay.com"
			).put(
				"firstName", "John"
			).put(
				"listTypeEntryKeyRequired", "listTypeEntryKey1"
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
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertCount(1);

		/*Assert.assertEquals(2, _messages.size());

		Message message = _messages.poll();

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject(
			(String)message.getPayload());

		Assert.assertEquals(
			"onBeforeUpdate", payloadJSONObject.getString("webhookEventKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			"João",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"Object/status"));
		Assert.assertEquals(
			"John",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"JSONObject/values", "Object/firstName"));

		message = _messages.poll();

		payloadJSONObject = _jsonFactory.createJSONObject(
			(String)message.getPayload());

		Assert.assertEquals(
			"onAfterUpdate", payloadJSONObject.getString("webhookEventKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			"João",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"Object/status"));
		Assert.assertEquals(
			"John",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"JSONObject/values", "Object/firstName"));*/

		objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntry.getObjectEntryId());

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(objectEntry, "_values"));

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(_getValuesFromCacheField(objectEntry), values);

		objectEntry.setValues(null);

		Assert.assertEquals(_getValuesFromDatabase(objectEntry), values);

		Assert.assertEquals(0L, values.get("ageOfDeath"));
		Assert.assertFalse((boolean)values.get("authorOfGospel"));
		Assert.assertEquals(null, values.get("birthday"));
		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(0D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(null, values.get("listTypeEntryKey"));
		Assert.assertEquals(
			"listTypeEntryKey2", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(0, values.get("numberOfBooksWritten"));
		Assert.assertEquals(null, values.get("portrait"));
		Assert.assertEquals(StringPool.BLANK, values.get("script"));
		Assert.assertEquals(_getBigDecimal(0L), values.get("speed"));
		Assert.assertEquals(0L, values.get("upload"));
		Assert.assertEquals(0D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 18, values.size());

		Calendar calendar = new GregorianCalendar();

		calendar.set(6, Calendar.DECEMBER, 27);

		Date birthdayDate = calendar.getTime();

		String fileContent = StringUtil.merge(RandomTestUtil.randomStrings(20));

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			fileContent.getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext());

		String portrait = "In the beginning was the Logos";
		String script = RandomTestUtil.randomString(1500);

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
				"numberOfBooksWritten", 5D
			).put(
				"portrait", portrait.getBytes()
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
		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(180D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKey"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(5, values.get("numberOfBooksWritten"));
		Assert.assertArrayEquals(
			portrait.getBytes(), (byte[])values.get("portrait"));
		Assert.assertEquals(script, values.get("script"));
		Assert.assertEquals(_getBigDecimal(45L), values.get("speed"));
		Assert.assertEquals(fileEntry.getFileEntryId(), values.get("upload"));
		Assert.assertEquals(60D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 18, values.size());

		Assert.assertNotNull(
			_dlAppLocalService.getFileEntry(fileEntry.getFileEntryId()));

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
		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(180D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(
			"listTypeEntryKey1", values.get("listTypeEntryKey"));
		Assert.assertEquals(
			"listTypeEntryKey3", values.get("listTypeEntryKeyRequired"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(5, values.get("numberOfBooksWritten"));
		Assert.assertArrayEquals(
			portrait.getBytes(), (byte[])values.get("portrait"));
		Assert.assertEquals(script, values.get("script"));
		Assert.assertEquals(_getBigDecimal(45L), values.get("speed"));
		Assert.assertEquals(0L, values.get("upload"));
		Assert.assertEquals(65D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(),
			values.get(_objectDefinition.getPKObjectFieldName()));
		Assert.assertEquals(values.toString(), 18, values.size());

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
					"firstName", RandomTestUtil.randomString(281)
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertEquals(
				"Object field \"firstName\" value exceeds 280 characters.",
				objectEntryValuesException.getMessage());
		}
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

	private int _count() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from " + _objectDefinition.getDBTableName());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			return resultSet.getInt(1);
		}
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
				scope,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

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

	private void _testUpdateStatus() throws Exception {
		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), 0,
			_objectDefinition.getClassName(), 0, 0, "Single Approver", 1);

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
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

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}