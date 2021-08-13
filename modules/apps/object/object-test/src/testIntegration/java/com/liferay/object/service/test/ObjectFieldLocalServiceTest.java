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
import com.liferay.object.exception.DuplicateObjectFieldException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldTypeException;
import com.liferay.object.exception.ReservedObjectFieldException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
				_createObjectField(true, false, "", "", "able", "Blob"));

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
				_createObjectField(true, false, "en_US", "", "able", "Long"));

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
				_createObjectField(true, true, "en_US", "", "able", "String"));

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
			_testAddSystemObjectField(_createObjectField("", "able", "String"));

			Assert.fail();
		}
		catch (ObjectFieldLabelException objectFieldLabelException) {
			Assert.assertEquals(
				"Label is null for locale " + LocaleUtil.US.getDisplayName(),
				objectFieldLabelException.getMessage());
		}

		// Name is null

		try {
			_testAddSystemObjectField(_createObjectField("Able", "", "String"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name is null", objectFieldNameException.getMessage());
		}

		// Name must only contain letters and digits

		_testAddSystemObjectField(_createObjectField(" able ", "String"));

		try {
			_testAddSystemObjectField(_createObjectField("abl e", "String"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectFieldNameException.getMessage());
		}

		try {
			_testAddSystemObjectField(_createObjectField("abl-e", "String"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectFieldNameException.getMessage());
		}

		// The first character of a name must be an upper case letter

		try {
			_testAddSystemObjectField(_createObjectField("Able", "String"));

			Assert.fail();
		}
		catch (ObjectFieldNameException objectFieldNameException) {
			Assert.assertEquals(
				"The first character of a name must be a lower case letter",
				objectFieldNameException.getMessage());
		}

		// Names must be less than 41 characters

		_testAddSystemObjectField(
			_createObjectField(
				"a123456789a123456789a123456789a1234567891", "String"));

		try {
			_testAddSystemObjectField(
				_createObjectField(
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
					_createObjectField(reservedName, "String"));

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
			_testAddSystemObjectField(_createObjectField("testId", "String"));

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
				_createObjectField("Able", "able", "String"),
				_createObjectField("Able", "able", "String"));

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
			_testAddSystemObjectField(_createObjectField("Able", "able", type));
		}

		try {
			_testAddSystemObjectField(
				_createObjectField("Able", "able", "STRING"));

			Assert.fail();
		}
		catch (ObjectFieldTypeException objectFieldTypeException) {
			Assert.assertEquals(
				"Invalid type STRING", objectFieldTypeException.getMessage());
		}
	}

	private ObjectField _createObjectField(
		boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
		String label, String name, String type) {

		return ObjectFieldUtil.createObjectField(
			null, indexed, indexedAsKeyword, indexedLanguageId,
			Collections.singletonMap(LocaleUtil.US, label), name, false, type);
	}

	private ObjectField _createObjectField(String name, String type) {
		return _createObjectField(name, name, type);
	}

	private ObjectField _createObjectField(
		String label, String name, String type) {

		return ObjectFieldUtil.createObjectField(
			LocaleUtil.US, label, name, false, type);
	}

	private void _testAddSystemObjectField(ObjectField... objectFields)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
					TestPropsValues.getUserId(), null,
					Collections.singletonMap(LocaleUtil.US, "Test"), "Test",
					null, null, 1, Arrays.asList(objectFields));
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

}