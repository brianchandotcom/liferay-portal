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
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.object.exception.NoSuchEntryException;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
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
import java.util.List;
import java.util.Map;

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
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_irrelevantObjectDefinition =
			ObjectDefinitionLocalServiceUtil.addObjectDefinition(
				TestPropsValues.getUserId(), "Irrelevant",
				Collections.<ObjectField>emptyList());

		List<ObjectField> objectFields = Arrays.asList(
			_createObjectField(true, false, "ageOfDeath", "Long"),
			_createObjectField(true, false, "authorOfGospel", "Boolean"),
			_createObjectField(true, false, "birthday", "Date"),
			_createObjectField(true, true, "emailAddress", "String"),
			_createObjectField(true, true, "emailAddressDomain", "String"),
			_createObjectField(true, false, "firstName", "String"),
			_createObjectField(true, false, "height", "Double"),
			_createObjectField(true, false, "lastName", "String"),
			_createObjectField(true, false, "middleName", "String"),
			_createObjectField(true, false, "numberOfBooksWritten", "Integer"),
			_createObjectField(false, false, "portrait", "Blob"),
			_createObjectField(true, false, "speed", "BigDecimal"),
			_createObjectField(true, false, "weight", "Double"));

		_objectDefinition =
			ObjectDefinitionLocalServiceUtil.addObjectDefinition(
				TestPropsValues.getUserId(), "Test", objectFields);
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		_assertCount(0);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).build());

		_assertCount(1);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"firstName", "James"
			).build());

		_assertCount(2);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"firstName", "John"
			).build());

		_assertCount(3);
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		ObjectEntry objectEntry1 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).build());
		ObjectEntry objectEntry2 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"firstName", "James"
			).build());
		ObjectEntry objectEntry3 = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"firstName", "John"
			).build());

		_assertCount(3);

		ObjectEntryLocalServiceUtil.deleteObjectEntry(
			objectEntry1.getObjectEntryId());

		try {
			ObjectEntryLocalServiceUtil.deleteObjectEntry(
				objectEntry1.getObjectEntryId());

			Assert.fail();
		}
		catch (NoSuchEntryException noSuchEntryException) {
			Assert.assertEquals(
				"No ObjectEntry exists with the primary key " +
					objectEntry1.getObjectEntryId(),
				noSuchEntryException.getMessage());
		}

		try {
			ObjectEntryLocalServiceUtil.deleteObjectEntry(objectEntry1);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertNotNull(nullPointerException);
		}

		try {
			ObjectEntryLocalServiceUtil.getValues(
				objectEntry1.getObjectEntryId());

			Assert.fail();
		}
		catch (NoSuchEntryException noSuchEntryException) {
			Assert.assertEquals(
				"No ObjectEntry exists with the primary key " +
					objectEntry1.getObjectEntryId(),
				noSuchEntryException.getMessage());
		}

		_assertCount(2);

		ObjectEntryLocalServiceUtil.deleteObjectEntry(
			objectEntry2.getObjectEntryId());

		_assertCount(1);

		ObjectEntryLocalServiceUtil.deleteObjectEntry(objectEntry3);

		_assertCount(0);
	}

	@Test
	public void testGetObjectEntries() throws Exception {
		List<ObjectEntry> objectEntries =
			ObjectEntryLocalServiceUtil.getObjectEntries(
				_objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_assertCount(0);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).build());

		objectEntries = ObjectEntryLocalServiceUtil.getObjectEntries(
			_objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_assertCount(1);

		Map<String, Serializable> values = _getValues(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"firstName", "James"
			).build());

		objectEntries = ObjectEntryLocalServiceUtil.getObjectEntries(
			_objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_assertCount(2);

		values = _getValues(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = _getValues(objectEntries.get(1));

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"firstName", "John"
			).build());

		objectEntries = ObjectEntryLocalServiceUtil.getObjectEntries(
			_objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 3, objectEntries.size());

		_assertCount(3);

		values = _getValues(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = _getValues(objectEntries.get(1));

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = _getValues(objectEntries.get(2));

		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		objectEntries = ObjectEntryLocalServiceUtil.getObjectEntries(
			_irrelevantObjectDefinition.getObjectDefinitionId(),
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
			).build());

		Map<String, Serializable> values =
			ObjectEntryLocalServiceUtil.getValues(
				objectEntry.getObjectEntryId());

		Assert.assertEquals(0L, values.get("ageOfDeath"));
		Assert.assertEquals(false, values.get("authorOfGospel"));
		Assert.assertEquals(null, values.get("birthday"));
		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(0D, values.get("height"));
		Assert.assertEquals(null, values.get("lastName"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(0, values.get("numberOfBooksWritten"));
		Assert.assertEquals(null, values.get("portrait"));
		Assert.assertEquals(null, values.get("speed"));
		Assert.assertEquals(0D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(), values.get("testId"));
		Assert.assertEquals(values.toString(), 14, values.size());

		try {
			ObjectEntryLocalServiceUtil.getValues(0);

			Assert.fail();
		}
		catch (NoSuchEntryException noSuchEntryException) {
			Assert.assertEquals(
				"No ObjectEntry exists with the primary key 0",
				noSuchEntryException.getMessage());
		}
	}

	@Test
	public void testGetValuesList() throws Exception {
		List<Map<String, Serializable>> valuesList =
			ObjectEntryLocalServiceUtil.getValuesList(
				_objectDefinition.getObjectDefinitionId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 0, valuesList.size());

		_assertCount(0);

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
			).build());

		valuesList = ObjectEntryLocalServiceUtil.getValuesList(
			_objectDefinition.getObjectDefinitionId(), null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 1, valuesList.size());

		_assertCount(1);

		Map<String, Serializable> values = valuesList.get(0);

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"firstName", "James"
			).build());

		valuesList = ObjectEntryLocalServiceUtil.getValuesList(
			_objectDefinition.getObjectDefinitionId(), null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 2, valuesList.size());

		_assertCount(2);

		values = valuesList.get(0);

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = valuesList.get(1);

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"firstName", "John"
			).build());

		valuesList = ObjectEntryLocalServiceUtil.getValuesList(
			_objectDefinition.getObjectDefinitionId(), null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 3, valuesList.size());

		_assertCount(3);

		values = valuesList.get(0);

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = valuesList.get(1);

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = valuesList.get(2);

		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		valuesList = ObjectEntryLocalServiceUtil.getValuesList(
			_irrelevantObjectDefinition.getObjectDefinitionId(), null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(valuesList.toString(), 0, valuesList.size());
	}

	@Test
	public void testSearchObjectEntries() throws Exception {

		// Without keywords

		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			ObjectEntryLocalServiceUtil.searchObjectEntries(
				_objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"firstName", "Peter"
			).build());

		baseModelSearchResult = ObjectEntryLocalServiceUtil.searchObjectEntries(
			_objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<ObjectEntry> objectEntries = baseModelSearchResult.getBaseModels();

		Map<String, Serializable> values = _getValues(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "james@liferay.com"
			).put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"firstName", "James"
			).build());

		baseModelSearchResult = ObjectEntryLocalServiceUtil.searchObjectEntries(
			_objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(2, baseModelSearchResult.getLength());

		objectEntries = baseModelSearchResult.getBaseModels();

		values = _getValues(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = _getValues(objectEntries.get(1));

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"emailAddressDomain", "@liferay.com"
			).put(
				"firstName", "John"
			).build());

		baseModelSearchResult = ObjectEntryLocalServiceUtil.searchObjectEntries(
			_objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(3, baseModelSearchResult.getLength());

		objectEntries = baseModelSearchResult.getBaseModels();

		values = _getValues(objectEntries.get(0));

		Assert.assertEquals("peter@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("Peter", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = _getValues(objectEntries.get(1));

		Assert.assertEquals("james@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("James", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		values = _getValues(objectEntries.get(2));

		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("@liferay.com", values.get("emailAddressDomain"));
		Assert.assertEquals("John", values.get("firstName"));
		Assert.assertEquals(values.toString(), 14, values.size());

		// With keywords

		_assertKeywords("@ liferay.com", 0);
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
		_assertKeywords("peter", 1);

		// Irrelevant object definition

		baseModelSearchResult = ObjectEntryLocalServiceUtil.searchObjectEntries(
			_irrelevantObjectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());
	}

	@Test
	public void testUpdateObjectEntry() throws Exception {
		_assertCount(0);

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "john@liferay.com"
			).put(
				"firstName", "John"
			).build());

		_assertCount(1);

		ObjectEntryLocalServiceUtil.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "João"
			).put(
				"lastName", "o Discípulo Amado"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertCount(1);

		Map<String, Serializable> values =
			ObjectEntryLocalServiceUtil.getValues(
				objectEntry.getObjectEntryId());

		Assert.assertEquals(0L, values.get("ageOfDeath"));
		Assert.assertEquals(false, values.get("authorOfGospel"));
		Assert.assertEquals(null, values.get("birthday"));
		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(0D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(0, values.get("numberOfBooksWritten"));
		Assert.assertEquals(null, values.get("portrait"));
		Assert.assertEquals(null, values.get("speed"));
		Assert.assertEquals(0D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(), values.get("testId"));
		Assert.assertEquals(values.toString(), 14, values.size());

		Calendar calendar = new GregorianCalendar();

		calendar.set(6, Calendar.DECEMBER, 27);

		Date birthdayDate = calendar.getTime();

		String portrait = "In the beginning was the Logos";

		ObjectEntryLocalServiceUtil.updateObjectEntry(
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
				"numberOfBooksWritten", 5D
			).put(
				"portrait", portrait.getBytes()
			).put(
				"speed", BigDecimal.valueOf(45L)
			).put(
				"weight", 60
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertCount(1);

		values = ObjectEntryLocalServiceUtil.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(94L, values.get("ageOfDeath"));
		Assert.assertEquals(true, values.get("authorOfGospel"));
		Assert.assertEquals(birthdayDate, values.get("birthday"));
		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(180D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(5, values.get("numberOfBooksWritten"));
		Assert.assertArrayEquals(
			portrait.getBytes(), (byte[])values.get("portrait"));
		Assert.assertEquals(_getBigDecimal(45L), values.get("speed"));
		Assert.assertEquals(60D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(), values.get("testId"));
		Assert.assertEquals(values.toString(), 14, values.size());

		ObjectEntryLocalServiceUtil.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"weight", 65D
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertCount(1);

		values = ObjectEntryLocalServiceUtil.getValues(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(94L, values.get("ageOfDeath"));
		Assert.assertEquals(true, values.get("authorOfGospel"));
		Assert.assertEquals(birthdayDate, values.get("birthday"));
		Assert.assertEquals("john@liferay.com", values.get("emailAddress"));
		Assert.assertEquals("João", values.get("firstName"));
		Assert.assertEquals(180D, values.get("height"));
		Assert.assertEquals("o Discípulo Amado", values.get("lastName"));
		Assert.assertEquals(null, values.get("middleName"));
		Assert.assertEquals(5, values.get("numberOfBooksWritten"));
		Assert.assertArrayEquals(
			portrait.getBytes(), (byte[])values.get("portrait"));
		Assert.assertEquals(_getBigDecimal(45L), values.get("speed"));
		Assert.assertEquals(65D, values.get("weight"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(), values.get("testId"));
		Assert.assertEquals(values.toString(), 14, values.size());

		try {
			ObjectEntryLocalServiceUtil.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				new HashMap<String, Serializable>(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertEquals(
				"No values were provided for object definition " +
					_objectDefinition.getObjectDefinitionId(),
				objectEntryValuesException.getMessage());
		}

		try {
			ObjectEntryLocalServiceUtil.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"invalidName", ""
				).put(
					"testId", ""
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertEquals(
				"No values were provided for object definition " +
					_objectDefinition.getObjectDefinitionId(),
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

		return ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			_objectDefinition.getObjectDefinitionId(), values,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertCount(int count) throws Exception {
		Assert.assertEquals(
			count,
			AssetEntryLocalServiceUtil.getEntriesCount(
				new AssetEntryQuery() {
					{
						setClassName(_objectDefinition.getClassName());
						setVisible(null);
					}
				}));
		Assert.assertEquals(
			count,
			ObjectEntryLocalServiceUtil.getObjectEntriesCount(
				_objectDefinition.getObjectDefinitionId()));
		Assert.assertEquals(count, _count());
	}

	private void _assertKeywords(String keywords, int count) throws Exception {
		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			ObjectEntryLocalServiceUtil.searchObjectEntries(
				_objectDefinition.getObjectDefinitionId(), keywords, 0, 20);

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

	private ObjectField _createObjectField(
		boolean indexed, boolean indexedAsKeyword, String name, String type) {

		ObjectField objectField = ObjectFieldLocalServiceUtil.createObjectField(
			0);

		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setName(name);
		objectField.setType(type);

		return objectField;
	}

	private BigDecimal _getBigDecimal(long value) {
		BigDecimal bigDecimal = BigDecimal.valueOf(value);

		return bigDecimal.setScale(16);
	}

	private Map<String, Serializable> _getValues(ObjectEntry objectEntry)
		throws Exception {

		// TODO

		//objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(
		//	objectEntry.getObjectEntryId());

		return objectEntry.getValues();
	}

	private void _testUpdateStatus() throws Exception {
		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			TestPropsValues.getGroupId(), _objectDefinition.getClassName(), 0,
			0, "Single Approver", 1);

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"emailAddress", "peter@liferay.com"
			).put(
				"firstName", "Peter"
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

		objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(
			objectEntry.getObjectEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectEntry.getStatus());
	}

	@DeleteAfterTestRun
	private ObjectDefinition _irrelevantObjectDefinition;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}