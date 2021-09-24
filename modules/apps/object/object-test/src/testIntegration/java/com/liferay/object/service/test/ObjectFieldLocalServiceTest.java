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
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.exception.DuplicateObjectFieldException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldTypeException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.exception.ReservedObjectFieldException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
					0, null, true, false, "", "", "able", false, "Blob"));

			Assert.fail();
		}
		catch (ObjectFieldTypeException objectFieldTypeException) {
			Assert.assertEquals(
				"Blob type is not indexable",
				objectFieldTypeException.getMessage());
		}

		// Indexed language ID can only be applied with type \"String\" that
		// is not indexed as a keyword

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, null, true, false, "en_US", "", "able", false, "Long"));

			Assert.fail();
		}
		catch (ObjectFieldTypeException objectFieldTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type " +
					"\"String\" that is not indexed as a keyword",
				objectFieldTypeException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, null, true, true, "en_US", "", "able", false, "Long"));

			Assert.fail();
		}
		catch (ObjectFieldTypeException objectFieldTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type " +
					"\"String\" that is not indexed as a keyword",
				objectFieldTypeException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					0, null, true, true, "en_US", "", "able", false, "String"));

			Assert.fail();
		}
		catch (ObjectFieldTypeException objectFieldTypeException) {
			Assert.assertEquals(
				"Indexed language ID can only be applied with type " +
					"\"String\" that is not indexed as a keyword",
				objectFieldTypeException.getMessage());
		}

		// Label is null

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("", "able", "String"));

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
				ObjectFieldUtil.createObjectField("Able", "", "String"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name is null", objectFieldNameException.getMessage());
		}

		// Name must only contain letters and digits

		_testAddSystemObjectField(
			ObjectFieldUtil.createObjectField(" able ", "String"));

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("abl e", "String"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectFieldNameException.getMessage());
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("abl-e", "String"));

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
				ObjectFieldUtil.createObjectField("Able", "String"));

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
				"a123456789a123456789a123456789a1234567891", "String"));

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField(
					"a123456789a123456789a123456789a12345678912", "String"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Names must be less than 41 characters",
				objectFieldNameException.getMessage());
		}

		// Reserved name

		String[] reservedNames = {
			"companyId", "createDate", "groupId", "id", "lastPublishDate",
			"modifiedDate", "status", "statusByUserId", "statusByUserName",
			"statusDate", "userId", "userName"
		};

		for (String reservedName : reservedNames) {
			try {
				_testAddSystemObjectField(
					ObjectFieldUtil.createObjectField(reservedName, "String"));

				Assert.fail();
			}
			catch (ReservedObjectFieldException reservedObjectFieldException) {
				Assert.assertEquals(
					"Reserved name " + reservedName,
					reservedObjectFieldException.getMessage());
			}
		}

		String objectDefinitionName = "A" + RandomTestUtil.randomString();

		String pkObjectFieldName = TextFormatter.format(
			objectDefinitionName + "Id", TextFormatter.I);

		try {
			_testAddSystemObjectField(
				objectDefinitionName,
				ObjectFieldUtil.createObjectField(pkObjectFieldName, "String"));

			Assert.fail();
		}
		catch (ReservedObjectFieldException reservedObjectFieldException) {
			Assert.assertEquals(
				"Reserved name " + pkObjectFieldName,
				reservedObjectFieldException.getMessage());
		}

		// Duplicate name

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("Able", "able", "String"),
				ObjectFieldUtil.createObjectField("Able", "able", "String"));

			Assert.fail();
		}
		catch (DuplicateObjectFieldException duplicateObjectFieldException) {
			Assert.assertEquals(
				"Duplicate name able",
				duplicateObjectFieldException.getMessage());
		}

		// Types

		String[] types = {
			"BigDecimal", "Blob", "Boolean", "Date", "Double", "Integer",
			"Long", "String"
		};

		for (String type : types) {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("Able", "able", type));
		}

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("Able", "able", "STRING"));

			Assert.fail();
		}
		catch (ObjectFieldTypeException objectFieldTypeException) {
			Assert.assertEquals(
				"Invalid type STRING", objectFieldTypeException.getMessage());
		}
	}

	@Test
	public void testDeleteObjectField1() throws Exception {
		ObjectField ableObjectField = ObjectFieldUtil.createObjectField(
			"able", "String");

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
			objectDefinition.getObjectDefinitionId(), false, true, "",
			LocalizedMapUtil.getLocalizedMap("able"), "able", false, "String");

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
			objectDefinition.getObjectDefinitionId(), false, true, "",
			LocalizedMapUtil.getLocalizedMap("baker"), "baker", false,
			"String");

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
			"able", "String");

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
			objectDefinition.getObjectDefinitionId(), false, true, "",
			LocalizedMapUtil.getLocalizedMap("baker"), "baker", false,
			"String");

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
			objectDefinition.getObjectDefinitionId(), false, true, "",
			LocalizedMapUtil.getLocalizedMap("able"), "able", false, "Long");

		Assert.assertFalse(objectField.isIndexed());
		Assert.assertTrue(objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("able"),
			objectField.getLabelMap());
		Assert.assertEquals("able", objectField.getName());
		Assert.assertFalse(objectField.isRequired());
		Assert.assertEquals("Long", objectField.getType());

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), 0, false, true, "",
			LocalizedMapUtil.getLocalizedMap("able"), "able", false, "Long");

		Assert.assertFalse(objectField.isIndexed());
		Assert.assertTrue(objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("able"),
			objectField.getLabelMap());
		Assert.assertEquals("able", objectField.getName());
		Assert.assertFalse(objectField.isRequired());
		Assert.assertEquals("Long", objectField.getType());

		String indexedLanguageId = LanguageUtil.getLanguageId(
			LocaleUtil.getDefault());

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), 0, true, false, indexedLanguageId,
			LocalizedMapUtil.getLocalizedMap("baker"), "baker", true, "String");

		Assert.assertTrue(objectField.isIndexed());
		Assert.assertFalse(objectField.isIndexedAsKeyword());
		Assert.assertEquals(
			indexedLanguageId, objectField.getIndexedLanguageId());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("baker"),
			objectField.getLabelMap());
		Assert.assertEquals("baker", objectField.getName());
		Assert.assertTrue(objectField.isRequired());
		Assert.assertEquals("String", objectField.getType());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		objectField = _objectFieldLocalService.updateCustomObjectField(
			objectField.getObjectFieldId(), 0, false, true, "",
			LocalizedMapUtil.getLocalizedMap("charlie"), "charlie", false,
			"Integer");

		Assert.assertTrue(objectField.isIndexed());
		Assert.assertFalse(objectField.isIndexedAsKeyword());
		Assert.assertEquals(
			indexedLanguageId, objectField.getIndexedLanguageId());
		Assert.assertEquals(
			objectField.getLabelMap(),
			LocalizedMapUtil.getLocalizedMap("charlie"));
		Assert.assertEquals("baker", objectField.getName());
		Assert.assertTrue(objectField.isRequired());
		Assert.assertEquals("String", objectField.getType());

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
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

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

}