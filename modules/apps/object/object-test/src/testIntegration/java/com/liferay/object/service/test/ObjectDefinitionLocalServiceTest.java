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
import com.liferay.object.exception.DuplicateObjectDefinitionException;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectDefinitionLabelException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectDefinitionPluralLabelException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectDefinitionVersionException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
public class ObjectDefinitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddCustomObjectDefinition() throws Exception {

		// Label is null

		try {
			_testAddCustomObjectDefinition("", "Test", "Tests");

			Assert.fail();
		}
		catch (ObjectDefinitionLabelException objectDefinitionLabelException) {
			Assert.assertEquals(
				"Label is null for locale " + LocaleUtil.US.getDisplayName(),
				objectDefinitionLabelException.getMessage());
		}

		// Name is null

		try {
			_testAddCustomObjectDefinition("Test", "", "Tests");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name is null", objectDefinitionNameException.getMessage());
		}

		// Plural label is null

		try {
			_testAddCustomObjectDefinition("Test", "Test", "");

			Assert.fail();
		}
		catch (ObjectDefinitionPluralLabelException
					objectDefinitionPluralLabelException) {

			Assert.assertEquals(
				"Plural label is null for locale " +
					LocaleUtil.US.getDisplayName(),
				objectDefinitionPluralLabelException.getMessage());
		}

		// Custom object definition names are automatically prepended with
		// with "C_"

		try {
			_testAddCustomObjectDefinition("Test");
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			throw objectDefinitionNameException;
		}

		// Name must only contain letters and digits

		_testAddCustomObjectDefinition(" Test ");

		try {
			_testAddCustomObjectDefinition("Tes t");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectDefinitionNameException.getMessage());
		}

		try {
			_testAddCustomObjectDefinition("Tes-t");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectDefinitionNameException.getMessage());
		}

		// The first character of a name must be an upper case letter

		try {
			_testAddCustomObjectDefinition("test");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"The first character of a name must be an upper case letter",
				objectDefinitionNameException.getMessage());
		}

		// Names must be less than 41 characters

		_testAddCustomObjectDefinition(
			"A123456789a123456789a123456789a1234567891");

		try {
			_testAddCustomObjectDefinition(
				"A123456789a123456789a123456789a12345678912");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Names must be less than 41 characters",
				objectDefinitionNameException.getMessage());
		}

		// Duplicate name

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), _labelMap, "Test", null, null,
				_pluralLabelMap, ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.<ObjectField>emptyList());

		ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		try {
			_testAddCustomObjectDefinition("Test");
		}
		catch (DuplicateObjectDefinitionException
					duplicateObjectDefinitionException) {

			Assert.assertEquals(
				"Duplicate name C_Test",
				duplicateObjectDefinitionException.getMessage());
		}

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		// Scope is null

		try {
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), _labelMap, "Test", null, null,
				_pluralLabelMap, "", Collections.<ObjectField>emptyList());

			Assert.fail();
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				"Scope is null", objectDefinitionScopeException.getMessage());
		}

		// No object scope provider found with key

		String scope = RandomTestUtil.randomString();

		try {
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), _labelMap, "Test", null, null,
				_pluralLabelMap, scope, Collections.<ObjectField>emptyList());

			Assert.fail();
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				"No object scope provider found with key " + scope,
				objectDefinitionScopeException.getMessage());
		}

		// Database table, resources, and status

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), _labelMap, "Test", null, null,
				_pluralLabelMap, ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Able", "able", false, "String"),
					ObjectFieldUtil.createObjectField(
						"Baker", "baker", false, "String")));

		ObjectFieldLocalServiceUtil.addCustomObjectField(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), false, false, null,
			LocalizedMapUtil.getLocalizedMap("Charlie"), 0, "charlie", true,
			"String");

		// Before publish, database table

		Assert.assertEquals(
			false, _hasTable(objectDefinition.getDBTableName()));
		Assert.assertEquals(
			false, _hasTable(objectDefinition.getExtensionDBTableName()));

		// Before publish, resources

		Assert.assertEquals(
			0,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			0,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			0,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			1,
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		// Before publish, status

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, objectDefinition.getStatus());

		// Publish

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		ObjectFieldLocalServiceUtil.addCustomObjectField(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), false, false, null,
			LocalizedMapUtil.getLocalizedMap("Dog"), 0, "dog", true, "String");

		// After publish, database table

		Assert.assertEquals(
			false, _hasColumn(objectDefinition.getDBTableName(), "able"));
		Assert.assertEquals(
			true, _hasColumn(objectDefinition.getDBTableName(), "able_"));
		Assert.assertEquals(
			false, _hasColumn(objectDefinition.getDBTableName(), "baker"));
		Assert.assertEquals(
			true, _hasColumn(objectDefinition.getDBTableName(), "baker_"));
		Assert.assertEquals(
			false, _hasColumn(objectDefinition.getDBTableName(), "charlie"));
		Assert.assertEquals(
			true, _hasColumn(objectDefinition.getDBTableName(), "charlie_"));
		Assert.assertEquals(
			false,
			_hasColumn(objectDefinition.getExtensionDBTableName(), "dog"));
		Assert.assertEquals(
			true,
			_hasColumn(objectDefinition.getExtensionDBTableName(), "dog_"));
		Assert.assertEquals(true, _hasTable(objectDefinition.getDBTableName()));
		Assert.assertEquals(
			true, _hasTable(objectDefinition.getExtensionDBTableName()));

		// After publish, resources

		Assert.assertEquals(
			4,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			6,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			2,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			1,
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		// After publish, status

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectDefinition.getStatus());

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testAddOrUpdateSystemObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addOrUpdateSystemObjectDefinition(
				TestPropsValues.getCompanyId(),
				new BaseSystemObjectDefinitionMetadata() {

					@Override
					public Map<Locale, String> getLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Event");
					}

					@Override
					public String getName() {
						return "UserNotificationEvent";
					}

					@Override
					public List<ObjectField> getObjectFields() {
						return Arrays.asList(
							createObjectField(
								"Action Required", "actionRequired", true,
								"Boolean"),
							createObjectField(
								"Delivery Type", "deliveryType", false, "Long"),
							createObjectField(
								"type_", "Type", "type", true, "String"));
					}

					@Override
					public Map<Locale, String> getPluralLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Events");
					}

					@Override
					public String getScope() {
						return ObjectDefinitionConstants.SCOPE_COMPANY;
					}

					@Override
					public int getVersion() {
						return 1;
					}

				});

		Assert.assertEquals(
			"UserNotificationEvent", objectDefinition.getDBTableName());
		Assert.assertEquals(
			"userNotificationEventId",
			objectDefinition.getPKObjectFieldDBColumnName());
		Assert.assertEquals(
			"userNotificationEventId", objectDefinition.getPKObjectFieldName());
		Assert.assertEquals(objectDefinition.isSystem(), true);
		Assert.assertEquals(1, objectDefinition.getVersion());

		_assertObjectField(
			objectDefinition, "actionRequired", "actionRequired", true,
			"Boolean");

		try {
			ObjectFieldLocalServiceUtil.getObjectField(
				objectDefinition.getObjectDefinitionId(), "archived");

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		_assertObjectField(
			objectDefinition, "deliveryType", "deliveryType", false, "Long");
		_assertObjectField(objectDefinition, "type_", "type", true, "String");

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.addOrUpdateSystemObjectDefinition(
				TestPropsValues.getCompanyId(),
				new BaseSystemObjectDefinitionMetadata() {

					@Override
					public Map<Locale, String> getLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Event");
					}

					@Override
					public String getName() {
						return "UserNotificationEvent";
					}

					@Override
					public List<ObjectField> getObjectFields() {
						return Arrays.asList(
							createObjectField(
								"Archived", "archived", true, "Boolean"),
							createObjectField(
								"Delivery Type", "deliveryType", true, "Long"),
							createObjectField(
								"type_", "Type", "type", false, "String"));
					}

					@Override
					public Map<Locale, String> getPluralLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Events");
					}

					@Override
					public String getScope() {
						return ObjectDefinitionConstants.SCOPE_COMPANY;
					}

					@Override
					public int getVersion() {
						return 2;
					}

				});

		Assert.assertEquals(2, objectDefinition.getVersion());

		try {
			ObjectFieldLocalServiceUtil.getObjectField(
				objectDefinition.getObjectDefinitionId(), "actionRequired");

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		_assertObjectField(
			objectDefinition, "archived", "archived", true, "Boolean");
		_assertObjectField(
			objectDefinition, "deliveryType", "deliveryType", true, "Long");
		_assertObjectField(objectDefinition, "type_", "type", false, "String");

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition);
	}

	@Test
	public void testAddSystemObjectDefinition() throws Exception {

		// Label is null

		try {
			_testAddSystemObjectDefinition("", "Test");

			Assert.fail();
		}
		catch (ObjectDefinitionLabelException objectDefinitionLabelException) {
			Assert.assertEquals(
				"Label is null for locale " + LocaleUtil.US.getDisplayName(),
				objectDefinitionLabelException.getMessage());
		}

		// Name is null

		try {
			_testAddSystemObjectDefinition("");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name is null", objectDefinitionNameException.getMessage());
		}

		// System object definition names must not start with "C_"

		try {
			_testAddSystemObjectDefinition("C_Test");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"System object definition names must not start with \"C_\"",
				objectDefinitionNameException.getMessage());
		}

		try {
			_testAddSystemObjectDefinition("c_Test");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"System object definition names must not start with \"C_\"",
				objectDefinitionNameException.getMessage());
		}

		// Name must only contain letters and digits

		_testAddSystemObjectDefinition(" Test ");

		try {
			_testAddSystemObjectDefinition("Tes t");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectDefinitionNameException.getMessage());
		}

		try {
			_testAddSystemObjectDefinition("Tes-t");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectDefinitionNameException.getMessage());
		}

		// The first character of a name must be an upper case letter

		try {
			_testAddSystemObjectDefinition("test");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"The first character of a name must be an upper case letter",
				objectDefinitionNameException.getMessage());
		}

		// Names must be less than 41 characters

		_testAddSystemObjectDefinition(
			"A123456789a123456789a123456789a1234567891");

		try {
			_testAddSystemObjectDefinition(
				"A123456789a123456789a123456789a12345678912");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Names must be less than 41 characters",
				objectDefinitionNameException.getMessage());
		}

		// Duplicate name

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _labelMap, "Test", null,
				null, _pluralLabelMap, ObjectDefinitionConstants.SCOPE_COMPANY,
				1, Collections.<ObjectField>emptyList());

		try {
			_testAddSystemObjectDefinition("Test");
		}
		catch (DuplicateObjectDefinitionException
					duplicateObjectDefinitionException) {

			Assert.assertEquals(
				"Duplicate name Test",
				duplicateObjectDefinitionException.getMessage());
		}

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		// Scope is null

		try {
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _labelMap, "Test", null,
				null, _pluralLabelMap, "", 1,
				Collections.<ObjectField>emptyList());

			Assert.fail();
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				"Scope is null", objectDefinitionScopeException.getMessage());
		}

		// No object scope provider found with key

		String scope = RandomTestUtil.randomString();

		try {
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _labelMap, "Test", null,
				null, _pluralLabelMap, scope, 1,
				Collections.<ObjectField>emptyList());

			Assert.fail();
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				"No object scope provider found with key " + scope,
				objectDefinitionScopeException.getMessage());
		}

		// System object definition versions must greater than 0

		try {
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _labelMap, "Test", null,
				null, _pluralLabelMap, ObjectDefinitionConstants.SCOPE_COMPANY,
				-1, Collections.<ObjectField>emptyList());
		}
		catch (ObjectDefinitionVersionException
					objectDefinitionVersionException) {

			Assert.assertEquals(
				"System object definition versions must greater than 0",
				objectDefinitionVersionException.getMessage());
		}

		try {
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _labelMap, "Test", null,
				null, _pluralLabelMap, ObjectDefinitionConstants.SCOPE_COMPANY,
				0, Collections.<ObjectField>emptyList());
		}
		catch (ObjectDefinitionVersionException
					objectDefinitionVersionException) {

			Assert.assertEquals(
				"System object definition versions must greater than 0",
				objectDefinitionVersionException.getMessage());
		}

		// Database table, resources, and status

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _labelMap, "Test", null,
				null, _pluralLabelMap, ObjectDefinitionConstants.SCOPE_COMPANY,
				1, Collections.<ObjectField>emptyList());

		ObjectFieldLocalServiceUtil.addCustomObjectField(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), false, false, null,
			LocalizedMapUtil.getLocalizedMap("Able"), 0, "able", true,
			"String");

		// Database table

		Assert.assertEquals(
			false,
			_hasColumn(objectDefinition.getExtensionDBTableName(), "able"));
		Assert.assertEquals(
			true,
			_hasColumn(objectDefinition.getExtensionDBTableName(), "able_"));
		Assert.assertEquals(
			false, _hasTable(objectDefinition.getDBTableName()));
		Assert.assertEquals(
			true, _hasTable(objectDefinition.getExtensionDBTableName()));

		// Resources

		try {
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getClassName());

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}

		try {
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getPortletId());

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}

		try {
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getResourceName());

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}

		Assert.assertEquals(
			1,
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		// Status

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectDefinition.getStatus());

		// Publish

		try {
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

			Assert.fail();
		}
		catch (ObjectDefinitionStatusException
					objectDefinitionStatusException) {

			Assert.assertNotNull(objectDefinitionStatusException);
		}

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testDeleteObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), _labelMap, "Test", null, null,
				_pluralLabelMap, ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.<ObjectField>emptyList());

		ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		// Database table

		Assert.assertEquals(
			false, _hasTable(objectDefinition.getDBTableName()));
		Assert.assertEquals(
			false, _hasTable(objectDefinition.getExtensionDBTableName()));

		// Resources

		Assert.assertEquals(
			0,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			0,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			0,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			0,
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));
	}

	@Test
	public void testUpdateCustomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				LocalizedMapUtil.getLocalizedMap("Ables"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.emptyList());

		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Able"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Able", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Ables"),
			objectDefinition.getPluralLabelMap());

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.updateCustomObjectDefinition(
				objectDefinition.getObjectDefinitionId(),
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				LocalizedMapUtil.getLocalizedMap("Ables"),
				objectDefinition.getScope());

		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Able"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Able", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Ables"),
			objectDefinition.getPluralLabelMap());

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.updateCustomObjectDefinition(
				objectDefinition.getObjectDefinitionId(),
				LocalizedMapUtil.getLocalizedMap("Baker"), "Baker", null, null,
				LocalizedMapUtil.getLocalizedMap("Bakers"),
				objectDefinition.getScope());

		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Baker"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Baker", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Bakers"),
			objectDefinition.getPluralLabelMap());

		ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.updateCustomObjectDefinition(
				objectDefinition.getObjectDefinitionId(),
				LocalizedMapUtil.getLocalizedMap("Charlie"), "Charlie", null,
				null, LocalizedMapUtil.getLocalizedMap("Charlies"),
				objectDefinition.getScope());

		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Charlie"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Baker", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Charlies"),
			objectDefinition.getPluralLabelMap());
	}

	private void _assertObjectField(
			ObjectDefinition objectDefinition, String dbColumnName, String name,
			boolean required, String type)
		throws Exception {

		ObjectField objectField = ObjectFieldLocalServiceUtil.getObjectField(
			objectDefinition.getObjectDefinitionId(), name);

		Assert.assertEquals(dbColumnName, objectField.getDBColumnName());
		Assert.assertEquals(false, objectField.isIndexed());
		Assert.assertEquals(false, objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(required, objectField.isRequired());
		Assert.assertEquals(type, objectField.getType());
	}

	private boolean _hasColumn(String tableName, String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasColumn(tableName, columnName);
		}
	}

	private boolean _hasTable(String tableName) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasTable(tableName);
		}
	}

	private void _testAddCustomObjectDefinition(String name) throws Exception {
		_testAddCustomObjectDefinition(name, name, name);
	}

	private void _testAddCustomObjectDefinition(
			String label, String name, String pluralLabel)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
					TestPropsValues.getUserId(),
					LocalizedMapUtil.getLocalizedMap(label), name, null, null,
					LocalizedMapUtil.getLocalizedMap(pluralLabel),
					ObjectDefinitionConstants.SCOPE_COMPANY,
					Collections.<ObjectField>emptyList());

			objectDefinition =
				ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
					TestPropsValues.getUserId(),
					objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testAddSystemObjectDefinition(String name) throws Exception {
		_testAddSystemObjectDefinition("Test", name);
	}

	private void _testAddSystemObjectDefinition(String label, String name)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
					TestPropsValues.getUserId(), null,
					LocalizedMapUtil.getLocalizedMap(label), name, null, null,
					_pluralLabelMap, ObjectDefinitionConstants.SCOPE_COMPANY, 1,
					Collections.<ObjectField>emptyList());
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private final Map<Locale, String> _labelMap =
		LocalizedMapUtil.getLocalizedMap("Test");
	private final Map<Locale, String> _pluralLabelMap =
		LocalizedMapUtil.getLocalizedMap("Tests");

}