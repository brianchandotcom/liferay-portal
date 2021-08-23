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
import com.liferay.object.exception.ReservedObjectFieldException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;

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
					null, true, false, "", "", "able", false, "Blob"));

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
					null, true, false, "en_US", "", "able", false, "Long"));

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
					null, true, true, "en_US", "", "able", false, "String"));

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

		// Reserved name is the primary key

		try {
			_testAddSystemObjectField(
				ObjectFieldUtil.createObjectField("testId", "String"));

			Assert.fail();
		}
		catch (ReservedObjectFieldException reservedObjectFieldException) {
			Assert.assertEquals(
				"Reserved name testId",
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
	public void testUpdateCustomObjectField() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Test"), "Test", null, null,
				LocalizedMapUtil.getLocalizedMap("Tests"),
				ObjectDefinitionConstants.SCOPE_COMPANY, null);

		ObjectField objectField =
			ObjectFieldLocalServiceUtil.addCustomObjectField(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(), false, true, "",
				LocalizedMapUtil.getLocalizedMap("able"), "able", false,
				"Long");

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

		objectField = ObjectFieldLocalServiceUtil.updateCustomObjectField(
			objectField.getObjectFieldId(), true, false, indexedLanguageId,
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

		ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		objectField = ObjectFieldLocalServiceUtil.updateCustomObjectField(
			objectField.getObjectFieldId(), false, true, "",
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

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	private void _testAddSystemObjectField(ObjectField... objectFields)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
					TestPropsValues.getUserId(), null,
					LocalizedMapUtil.getLocalizedMap("Test"), "Test", null,
					null, LocalizedMapUtil.getLocalizedMap("Tests"),
					ObjectDefinitionConstants.SCOPE_COMPANY, 1,
					Arrays.asList(objectFields));
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

}