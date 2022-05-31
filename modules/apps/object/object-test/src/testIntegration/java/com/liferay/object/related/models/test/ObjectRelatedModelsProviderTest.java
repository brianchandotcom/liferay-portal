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

package com.liferay.object.related.models.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 */
@RunWith(Arquillian.class)
public class ObjectRelatedModelsProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);
		_user = UserTestUtil.addUser();
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		_objectDefinition1 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId());

		_objectDefinition2 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		_objectDefinition2 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition2.getObjectDefinitionId());

		_setUser(TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Ignore
	@Test
	public void testObjectEntry1to1ObjectRelatedModelsProviderImpl()
		throws Exception {

		ObjectRelationship objectRelationship = _createObjectRelationship(
			ObjectRelationshipConstants.TYPE_ONE_TO_ONE);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition2.getClassName(),
				ObjectRelationshipConstants.TYPE_ONE_TO_ONE);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		ObjectEntry objectEntryA = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		ObjectEntry objectEntryB = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		try {
			_objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition2.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					objectField.getName(), objectEntry1.getObjectEntryId()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertTrue(
				StringUtil.startsWith(
					objectEntryValuesException.getMessage(),
					"One to one constraint violation for "));
		}

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryA.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					objectField.getName(), objectEntry1.getObjectEntryId()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertTrue(
				StringUtil.startsWith(
					objectEntryValuesException.getMessage(),
					"One to one constraint violation for "));
		}

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryB.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());
		Assert.assertEquals(
			objectEntries,
			_objectEntryLocalService.getOneToManyRelatedObjectEntries(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS));

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryB.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), 0
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	@Test
	public void testObjectEntry1toMObjectRelatedModelsProviderImpl()
		throws Exception {

		_testSystemObjectEntry1toMObjectRelatedModels();

		ObjectRelationship objectRelationship = _createObjectRelationship(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition2.getClassName(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		ObjectEntry objectEntryA = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		ObjectEntry objectEntryB = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryA.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 3, objectEntries.size());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryB.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), 0
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_setUser(_user);

		_testViewPermission(
			objectRelatedModelsProvider, objectRelationship, objectEntry1,
			objectEntryA.getObjectEntryId());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	@Test
	public void testObjectEntryMtoMObjectRelatedModelsProviderImpl()
		throws Exception {

		ObjectRelationship objectRelationship = _createObjectRelationship(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition2.getClassName(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());
		ObjectEntry objectEntry2 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry2.getObjectEntryId(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		objectEntry2 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry2.getObjectEntryId(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_setUser(_user);

		_testViewPermission(
			objectRelatedModelsProvider, objectRelationship, objectEntry1,
			objectEntry2.getObjectEntryId());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	private long[] _addUsers(int numberOfUsers) throws Exception {
		long[] userIds = new long[numberOfUsers];

		for (int i = 0; i < numberOfUsers; i++) {
			User user = UserTestUtil.addUser();

			userIds[i] = user.getUserId();
		}

		return userIds;
	}

	private ObjectRelationship _createObjectRelationship(
			String relationshipType)
		throws Exception {

		return _objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(),
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), relationshipType);
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testSystemObjectEntry1toMObjectRelatedModels()
		throws Exception {

		long[] userIds = _addUsers(3);

		ObjectDefinition systemObjectDefinition = null;

		for (ObjectDefinition objectDefinition :
				_objectDefinitionLocalService.getSystemObjectDefinitions()) {

			if (StringUtil.equals(objectDefinition.getName(), "User")) {
				systemObjectDefinition = objectDefinition;

				break;
			}
		}

		Assert.assertNotNull(systemObjectDefinition);

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition2.getObjectDefinitionId(),
				systemObjectDefinition.getObjectDefinitionId(),
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		ObjectRelatedModelsProvider objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				systemObjectDefinition.getClassName(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		List<BaseModel<?>> relatedObjectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry.getPrimaryKey(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 0, relatedObjectEntries.size());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			systemObjectDefinition.getObjectDefinitionId(), userIds[0],
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			systemObjectDefinition.getObjectDefinitionId(), userIds[1],
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			systemObjectDefinition.getObjectDefinitionId(), userIds[2],
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		relatedObjectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 3, relatedObjectEntries.size());

		objectRelatedModelsProvider.disassociateRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), userIds[0]);

		relatedObjectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 2, relatedObjectEntries.size());

		try {
			_objectEntryLocalService.deleteObjectEntry(objectEntry);
		}
		catch (RequiredObjectRelationshipException
					requiredObjectRelationshipException) {

			Assert.assertEquals(
				StringBundler.concat(
					"Object relationship ",
					objectRelationship.getObjectRelationshipId(),
					" does not allow deletes"),
				requiredObjectRelationshipException.getMessage());
		}

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(),
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
				objectRelationship.getLabelMap());

		objectEntry = _objectEntryLocalService.deleteObjectEntry(objectEntry);

		relatedObjectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 0, relatedObjectEntries.size());

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(),
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				objectRelationship.getLabelMap());

		objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		relatedObjectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 0, relatedObjectEntries.size());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			systemObjectDefinition.getObjectDefinitionId(), userIds[0],
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			systemObjectDefinition.getObjectDefinitionId(), userIds[1],
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		relatedObjectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 2, relatedObjectEntries.size());

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		try {
			_userLocalService.getUser(userIds[0]);
		}
		catch (NoSuchUserException noSuchUserException) {
			Assert.assertEquals(
				"No User exists with the primary key " + userIds[0],
				noSuchUserException.getMessage());
		}

		try {
			_userLocalService.getUser(userIds[1]);
		}
		catch (NoSuchUserException noSuchUserException) {
			Assert.assertEquals(
				"No User exists with the primary key " + userIds[1],
				noSuchUserException.getMessage());
		}
	}

	private void _testViewPermission(
			int expectedRelatedModelsCount,
			ObjectRelatedModelsProvider<ObjectEntry>
				objectRelatedModelsProvider,
			ObjectRelationship objectRelationship,
			ObjectEntry parentObjectEntry, long primKey, int scope)
		throws Exception {

		Assert.assertEquals(
			0,
			objectRelatedModelsProvider.getRelatedModelsCount(
				0, objectRelationship.getObjectRelationshipId(),
				parentObjectEntry.getObjectEntryId()));

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), _objectDefinition2.getClassName(),
			scope, String.valueOf(primKey), _role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		Assert.assertEquals(
			expectedRelatedModelsCount,
			objectRelatedModelsProvider.getRelatedModelsCount(
				0, objectRelationship.getObjectRelationshipId(),
				parentObjectEntry.getObjectEntryId()));

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(), _objectDefinition2.getClassName(),
			scope, String.valueOf(primKey), _role.getRoleId(), ActionKeys.VIEW);
	}

	private void _testViewPermission(
			ObjectRelatedModelsProvider<ObjectEntry>
				objectRelatedModelsProvider,
			ObjectRelationship objectRelationship,
			ObjectEntry parentObjectEntry, long primKey)
		throws Exception {

		_testViewPermission(
			2, objectRelatedModelsProvider, objectRelationship,
			parentObjectEntry, TestPropsValues.getCompanyId(),
			ResourceConstants.SCOPE_COMPANY);
		_testViewPermission(
			1, objectRelatedModelsProvider, objectRelationship,
			parentObjectEntry, primKey, ResourceConstants.SCOPE_INDIVIDUAL);
	}

	private static PermissionChecker _originalPermissionChecker;
	private static Role _role;
	private static User _user;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private UserLocalService _userLocalService;

}