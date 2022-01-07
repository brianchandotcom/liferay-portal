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
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectDefinitionLabelException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectDefinitionPluralLabelException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectDefinitionVersionException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.UserNotificationEventTable;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
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
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		try {
			_testAddCustomObjectDefinition("Test");
		}
		catch (ObjectDefinitionNameException.MustNotBeDuplicate
					objectDefinitionNameException) {

			Assert.assertEquals(
				"Duplicate name C_Test",
				objectDefinitionNameException.getMessage());
		}

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		// Scope is null

		try {
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"",
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

			Assert.fail();
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				"Scope is null", objectDefinitionScopeException.getMessage());
		}

		// No object scope provider found with key

		String scope = RandomTestUtil.randomString();

		try {
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				scope,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

			Assert.fail();
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				"No object scope provider found with key " + scope,
				objectDefinitionScopeException.getMessage());
		}

		// Database table, resources, and status

		objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", "Able", "able", false),
					ObjectFieldUtil.createObjectField(
						"Text", "String", "Baker", "baker", false)));

		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			false, null, LocalizedMapUtil.getLocalizedMap("Charlie"), "charlie",
			true);

		// Before publish, database table

		Assert.assertFalse(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertFalse(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		// Before publish, resources

		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		// Before publish, status

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, objectDefinition.getStatus());

		// Publish

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			false, null, LocalizedMapUtil.getLocalizedMap("Dog"), "dog", true);

		// After publish, database table

		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "able"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getDBTableName(), "able_"));
		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "baker"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getDBTableName(), "baker_"));
		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "charlie"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getDBTableName(), "charlie_"));
		Assert.assertFalse(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "dog"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "dog_"));
		Assert.assertTrue(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertTrue(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		// After publish, resources

		Assert.assertEquals(
			4,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			6,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			2,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		// After publish, status

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectDefinition.getStatus());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testAddOrUpdateSystemObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addOrUpdateSystemObjectDefinition(
				TestPropsValues.getCompanyId(),
				new BaseSystemObjectDefinitionMetadata() {

					@Override
					public Map<Locale, String> getLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Event");
					}

					@Override
					public Class<?> getModelClass() {
						return UserNotificationEvent.class;
					}

					@Override
					public List<ObjectField> getObjectFields() {
						return Arrays.asList(
							createObjectField(
								"Boolean", "Boolean", "Action Required",
								"actionRequired", true),
							createObjectField(
								"LongInteger", "Long", "Delivery Type",
								"deliveryType", false),
							createObjectField(
								"Text", "type_", "String", "Type", "type",
								true));
					}

					@Override
					public Map<Locale, String> getPluralLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Events");
					}

					@Override
					public Column<?, Long> getPrimaryKeyColumn() {
						return UserNotificationEventTable.INSTANCE.
							userNotificationEventId;
					}

					@Override
					public String getRESTContextPath() {
						return "/";
					}

					@Override
					public String getScope() {
						return ObjectDefinitionConstants.SCOPE_COMPANY;
					}

					@Override
					public Table getTable() {
						return UserNotificationEventTable.INSTANCE;
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
			objectDefinition, "actionRequired", "Boolean", "actionRequired",
			true);

		try {
			_objectFieldLocalService.getObjectField(
				objectDefinition.getObjectDefinitionId(), "archived");

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		_assertObjectField(
			objectDefinition, "deliveryType", "Long", "deliveryType", false);
		_assertObjectField(objectDefinition, "type_", "String", "type", true);

		objectDefinition =
			_objectDefinitionLocalService.addOrUpdateSystemObjectDefinition(
				TestPropsValues.getCompanyId(),
				new BaseSystemObjectDefinitionMetadata() {

					@Override
					public Map<Locale, String> getLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Event");
					}

					@Override
					public Class<?> getModelClass() {
						return UserNotificationEvent.class;
					}

					@Override
					public List<ObjectField> getObjectFields() {
						return Arrays.asList(
							createObjectField(
								"Boolean", "Boolean", "Archived", "archived",
								true),
							createObjectField(
								"LongInteger", "Long", "Delivery Type",
								"deliveryType", true),
							createObjectField(
								"Text", "type_", "String", "Type", "type",
								false));
					}

					@Override
					public Map<Locale, String> getPluralLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Events");
					}

					@Override
					public Column<?, Long> getPrimaryKeyColumn() {
						return UserNotificationEventTable.INSTANCE.
							userNotificationEventId;
					}

					@Override
					public String getRESTContextPath() {
						return "/";
					}

					@Override
					public String getScope() {
						return ObjectDefinitionConstants.SCOPE_COMPANY;
					}

					@Override
					public Table getTable() {
						return UserNotificationEventTable.INSTANCE;
					}

					@Override
					public int getVersion() {
						return 2;
					}

				});

		Assert.assertEquals(2, objectDefinition.getVersion());

		try {
			_objectFieldLocalService.getObjectField(
				objectDefinition.getObjectDefinitionId(), "actionRequired");

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		_assertObjectField(
			objectDefinition, "archived", "Boolean", "archived", true);
		_assertObjectField(
			objectDefinition, "deliveryType", "Long", "deliveryType", true);
		_assertObjectField(objectDefinition, "type_", "String", "type", false);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
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
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, 1,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		try {
			_testAddSystemObjectDefinition("Test");
		}
		catch (ObjectDefinitionNameException.MustNotBeDuplicate
					objectDefinitionNameException) {

			Assert.assertEquals(
				"Duplicate name Test",
				objectDefinitionNameException.getMessage());
		}

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		// Scope is null

		try {
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"", 1,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

			Assert.fail();
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				"Scope is null", objectDefinitionScopeException.getMessage());
		}

		// No object scope provider found with key

		String scope = RandomTestUtil.randomString();

		try {
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				scope, 1, Collections.<ObjectField>emptyList());

			Assert.fail();
		}
		catch (ObjectDefinitionScopeException objectDefinitionScopeException) {
			Assert.assertEquals(
				"No object scope provider found with key " + scope,
				objectDefinitionScopeException.getMessage());
		}

		// System object definition versions must greater than 0

		try {
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, -1,
				Collections.<ObjectField>emptyList());
		}
		catch (ObjectDefinitionVersionException
					objectDefinitionVersionException) {

			Assert.assertEquals(
				"System object definition versions must greater than 0",
				objectDefinitionVersionException.getMessage());
		}

		try {
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, 0,
				Collections.<ObjectField>emptyList());
		}
		catch (ObjectDefinitionVersionException
					objectDefinitionVersionException) {

			Assert.assertEquals(
				"System object definition versions must greater than 0",
				objectDefinitionVersionException.getMessage());
		}

		// Database table, resources, and status

		objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, 1,
				Collections.<ObjectField>emptyList());

		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			false, null, LocalizedMapUtil.getLocalizedMap("Able"), "able",
			true);

		// Database table

		Assert.assertFalse(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "able"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "able_"));
		Assert.assertFalse(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertTrue(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		// Messaging

		Assert.assertFalse(
			_messageBus.hasDestination(objectDefinition.getDestinationName()));

		// Resources

		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getClassName()));

		try {
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getPortletId());

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}

		try {
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getResourceName());

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}

		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		// Status

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectDefinition.getStatus());

		// Publish

		try {
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

			Assert.fail();
		}
		catch (ObjectDefinitionStatusException
					objectDefinitionStatusException) {

			Assert.assertNotNull(objectDefinitionStatusException);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testDeleteObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		// Database table

		Assert.assertFalse(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertFalse(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		// Messaging

		Assert.assertFalse(
			_messageBus.hasDestination(objectDefinition.getDestinationName()));

		// Resources

		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			0,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));
	}

	@Test
	public void testUpdateCustomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				LocalizedMapUtil.getLocalizedMap("Ables"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.emptyList());

		Assert.assertFalse(objectDefinition.isActive());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Able"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Able", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Ables"),
			objectDefinition.getPluralLabelMap());

		try {
			objectDefinition =
				_objectDefinitionLocalService.updateCustomObjectDefinition(
					objectDefinition.getObjectDefinitionId(),
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
					objectDefinition.isActive(),
					LocalizedMapUtil.getLocalizedMap("Able"), "Able", null,
					null, false, LocalizedMapUtil.getLocalizedMap("Ables"),
					objectDefinition.getScope());

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), true);

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				objectDefinition.getObjectDefinitionId(),
				objectField.getObjectFieldId(), objectField.getObjectFieldId(),
				objectDefinition.isActive(),
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				false, LocalizedMapUtil.getLocalizedMap("Ables"),
				objectDefinition.getScope());

		Assert.assertEquals(
			objectField.getObjectFieldId(),
			objectDefinition.getDescriptionObjectFieldId());
		Assert.assertEquals(
			objectField.getObjectFieldId(),
			objectDefinition.getTitleObjectFieldId());

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				objectDefinition.getObjectDefinitionId(), 0, 0,
				objectDefinition.isActive(),
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				false, LocalizedMapUtil.getLocalizedMap("Ables"),
				objectDefinition.getScope());

		Assert.assertEquals(0, objectDefinition.getDescriptionObjectFieldId());
		Assert.assertEquals(0, objectDefinition.getTitleObjectFieldId());
		Assert.assertFalse(objectDefinition.isActive());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Able"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Able", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Ables"),
			objectDefinition.getPluralLabelMap());

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				objectDefinition.getObjectDefinitionId(), 0, 0,
				objectDefinition.isActive(),
				LocalizedMapUtil.getLocalizedMap("Baker"), "Baker", null, null,
				false, LocalizedMapUtil.getLocalizedMap("Bakers"),
				objectDefinition.getScope());

		Assert.assertFalse(objectDefinition.isActive());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Baker"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Baker", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Bakers"),
			objectDefinition.getPluralLabelMap());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				objectDefinition.getObjectDefinitionId(), 0, 0, true,
				LocalizedMapUtil.getLocalizedMap("Charlie"), "Charlie", null,
				null, false, LocalizedMapUtil.getLocalizedMap("Charlies"),
				objectDefinition.getScope());

		Assert.assertTrue(objectDefinition.isActive());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Charlie"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Baker", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Charlies"),
			objectDefinition.getPluralLabelMap());

		_testUpdateCustomObjectDefinitionThrowsObjectFieldRelationshipTypeException(
			objectDefinition);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	private void _assertObjectField(
			ObjectDefinition objectDefinition, String dbColumnName,
			String dbType, String name, boolean required)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), name);

		Assert.assertEquals(dbColumnName, objectField.getDBColumnName());
		Assert.assertEquals(dbType, objectField.getDBType());
		Assert.assertFalse(objectField.isIndexed());
		Assert.assertFalse(objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(required, objectField.isRequired());
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
				_objectDefinitionLocalService.addCustomObjectDefinition(
					TestPropsValues.getUserId(),
					LocalizedMapUtil.getLocalizedMap(label), name, null, null,
					LocalizedMapUtil.getLocalizedMap(pluralLabel),
					ObjectDefinitionConstants.SCOPE_COMPANY,
					Arrays.asList(
						ObjectFieldUtil.createObjectField(
							"Text", "String", RandomTestUtil.randomString(),
							StringUtil.randomId())));

			objectDefinition =
				_objectDefinitionLocalService.publishCustomObjectDefinition(
					TestPropsValues.getUserId(),
					objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
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
				_objectDefinitionLocalService.addSystemObjectDefinition(
					TestPropsValues.getUserId(), name, null,
					LocalizedMapUtil.getLocalizedMap(label), name, null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					ObjectDefinitionConstants.SCOPE_COMPANY, 1,
					Arrays.asList(
						ObjectFieldUtil.createObjectField(
							"Text", "String", RandomTestUtil.randomString(),
							StringUtil.randomId())));
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void
			_testUpdateCustomObjectDefinitionThrowsObjectFieldRelationshipTypeException(
				ObjectDefinition objectDefinition1)
		throws Exception {

		ObjectDefinition objectDefinition2 =
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

		objectDefinition2 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition2.getObjectDefinitionId());

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinition1.getObjectDefinitionId(),
				objectDefinition2.getObjectDefinitionId(),
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		try {
			objectDefinition2 =
				_objectDefinitionLocalService.updateCustomObjectDefinition(
					objectDefinition2.getObjectDefinitionId(),
					objectRelationship.getObjectFieldId2(), 0,
					objectDefinition2.isActive(),
					LocalizedMapUtil.getLocalizedMap("Able"), "Able", null,
					null, false, LocalizedMapUtil.getLocalizedMap("Ables"),
					objectDefinition2.getScope());

			Assert.fail();
		}
		catch (ObjectFieldRelationshipTypeException
					objectFieldRelationshipTypeException) {

			Assert.assertEquals(
				"Description and title object fields cannot have a " +
					"relationship type",
				objectFieldRelationshipTypeException.getMessage());
		}
		finally {

			// TODO Deleting an object definition should delete any of its
			// object relationships

			//_objectRelationshipLocalService.deleteObjectRelationship(
			//	objectRelationship);

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition2);
		}
	}

	@Inject
	private MessageBus _messageBus;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}