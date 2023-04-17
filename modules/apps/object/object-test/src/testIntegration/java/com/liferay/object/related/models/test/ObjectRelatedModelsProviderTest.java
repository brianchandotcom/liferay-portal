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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.object.field.util.ObjectFieldUtil;
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
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletCategoryKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
		_objectDefinition1 = _addObjectDefinition();
		_objectDefinition2 = _addObjectDefinition();

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

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_ONE);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition2.getClassName(),
				_objectDefinition2.getCompanyId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_ONE);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		ObjectEntry objectEntry2 = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		ObjectEntry objectEntry3 = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		try {
			_addObjectEntry(
				_objectDefinition2.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					objectField.getName(), objectEntry1.getObjectEntryId()
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertTrue(
				StringUtil.startsWith(
					objectEntryValuesException.getMessage(),
					"One to one constraint violation for "));
		}

		try {
			_updateObjectEntry(
				objectEntry2.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					objectField.getName(), objectEntry1.getObjectEntryId()
				).build());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertTrue(
				StringUtil.startsWith(
					objectEntryValuesException.getMessage(),
					"One to one constraint violation for "));
		}

		_updateObjectEntry(
			objectEntry3.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());
		Assert.assertEquals(
			objectEntries,
			_objectEntryLocalService.getOneToManyObjectEntries(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS));

		_updateObjectEntry(
			objectEntry3.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), 0
			).build());

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

		_testSystemObjectEntry1toMObjectRelatedModelsProviderImpl();

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition2.getClassName(),
				_objectDefinition2.getCompanyId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		ObjectEntry objectEntry2 = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		ObjectEntry objectEntry3 = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_updateObjectEntry(
			objectEntry2.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 3, objectEntries.size());

		_updateObjectEntry(
			objectEntry3.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), 0
			).build());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_setUser(_user);

		_assertViewPermission(
			_objectDefinition2, objectRelatedModelsProvider, objectRelationship,
			objectEntry1, objectEntry2.getObjectEntryId());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		_setUser(TestPropsValues.getUser());

		ObjectDefinition scopeSiteObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"C" + RandomTestUtil.randomString(), null,
				PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		scopeSiteObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				scopeSiteObjectDefinition.getObjectDefinitionId());

		objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				scopeSiteObjectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				scopeSiteObjectDefinition.getClassName(),
				scopeSiteObjectDefinition.getCompanyId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry4 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry4.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		Group group = GroupTestUtil.addGroup();

		ObjectField objectRelationshipObjectField2 =
			_objectFieldLocalService.getObjectField(
				objectRelationship.getObjectFieldId2());

		ObjectEntry objectEntry5 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), group.getGroupId(),
			scopeSiteObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectRelationshipObjectField2.getName(),
				objectEntry4.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry4.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_objectEntryLocalService.deleteObjectEntry(objectEntry4);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry5.getObjectEntryId()));

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				objectRelationship.getLabelMap());

		ObjectEntry objectEntry6 = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		ObjectEntry objectEntry7 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), group.getGroupId(),
			scopeSiteObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectRelationshipObjectField2.getName(),
				objectEntry6.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry6.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		try {
			_objectEntryLocalService.deleteObjectEntry(objectEntry6);

			Assert.fail();
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
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
				objectRelationship.getLabelMap());

		_objectEntryLocalService.deleteObjectEntry(objectEntry6);

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry7.getObjectEntryId()));

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry6.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		_objectDefinitionLocalService.deleteObjectDefinition(
			scopeSiteObjectDefinition);
	}

	@Test
	public void testObjectEntry1toMObjectUnrelatedModelsProviderImpl()
		throws Exception {

		_testObjectEntry1toMObjectUnrelatedModelsProviderImpl(
			CompanyThreadLocal.getCompanyId());

		Company company = CompanyTestUtil.addCompany();

		_testObjectEntry1toMObjectUnrelatedModelsProviderImpl(
			company.getCompanyId());

		_companyLocalService.deleteCompany(company);
	}

	@Test
	public void testObjectEntryMtoMObjectRelatedModelsProviderImpl()
		throws Exception {

		_testObjectEntryMtoMObjectRelatedModelsProviderImpl(
			_objectDefinition1, _objectDefinition1);
		_testObjectEntryMtoMObjectRelatedModelsProviderImpl(
			_objectDefinition1, _objectDefinition2);

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

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition1.getObjectDefinitionId(),
			systemObjectDefinition.getObjectDefinitionId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				systemObjectDefinition.getClassName(),
				systemObjectDefinition.getCompanyId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry.getObjectEntryId(), userIds[0],
			ServiceContextTestUtil.getServiceContext());
		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry.getObjectEntryId(), userIds[1],
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		ObjectRelationship reverseObjectRelationship =
			_objectRelationshipLocalService.fetchReverseObjectRelationship(
				objectRelationship, true);

		objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition1.getClassName(),
				_objectDefinition1.getCompanyId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		List<ObjectEntry> reverseObjectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, reverseObjectRelationship.getObjectRelationshipId(),
				userIds[0], QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			reverseObjectEntries.toString(), 1, reverseObjectEntries.size());

		try {
			_objectEntryLocalService.deleteObjectEntry(objectEntry);

			Assert.fail();
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
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
				objectRelationship.getLabelMap());

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				systemObjectDefinition.getClassName(),
				systemObjectDefinition.getCompanyId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		objectEntry = _addObjectEntry(
			_objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry.getObjectEntryId(), userIds[1],
			ServiceContextTestUtil.getServiceContext());
		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry.getObjectEntryId(), userIds[2],
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				objectRelationship.getLabelMap());

		_userLocalService.deleteUser(userIds[2]);

		try {
			_userLocalService.getUser(userIds[2]);

			Assert.fail();
		}
		catch (NoSuchUserException noSuchUserException) {
			Assert.assertEquals(
				"No User exists with the primary key " + userIds[2],
				noSuchUserException.getMessage());
		}

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry.getObjectEntryId()));

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry.getObjectEntryId()));

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		try {
			_userLocalService.getUser(userIds[1]);

			Assert.fail();
		}
		catch (NoSuchUserException noSuchUserException) {
			Assert.assertEquals(
				"No User exists with the primary key " + userIds[1],
				noSuchUserException.getMessage());
		}

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		Assert.assertNull(
			_objectRelationshipLocalService.fetchObjectRelationship(
				reverseObjectRelationship.getObjectRelationshipId()));
	}

	private AccountEntry _addAccountEntry(long userId) throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			userId, 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null, null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private ObjectDefinition _addObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private ObjectEntry _addObjectEntry(
			long objectDefinitionId, Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0, objectDefinitionId, values,
			ServiceContextTestUtil.getServiceContext());
	}

	private ObjectRelationship _addObjectRelationship(
			long objectDefinitionId1, long objectDefinitionId2,
			String relationshipType)
		throws Exception {

		return _objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(), objectDefinitionId1,
			objectDefinitionId2, 0,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), relationshipType);
	}

	private long[] _addUsers(int count) throws Exception {
		long[] userIds = new long[count];

		for (int i = 0; i < count; i++) {
			User user = UserTestUtil.addUser();

			userIds[i] = user.getUserId();
		}

		return userIds;
	}

	private void _assertViewPermission(
			int expectedRelatedModelsCount, ObjectDefinition objectDefinition,
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
			TestPropsValues.getCompanyId(), objectDefinition.getClassName(),
			scope, String.valueOf(primKey), _role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		Assert.assertEquals(
			expectedRelatedModelsCount,
			objectRelatedModelsProvider.getRelatedModelsCount(
				0, objectRelationship.getObjectRelationshipId(),
				parentObjectEntry.getObjectEntryId()));

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(), objectDefinition.getClassName(),
			scope, String.valueOf(primKey), _role.getRoleId(), ActionKeys.VIEW);
	}

	private void _assertViewPermission(
			ObjectDefinition objectDefinition,
			ObjectRelatedModelsProvider<ObjectEntry>
				objectRelatedModelsProvider,
			ObjectRelationship objectRelationship,
			ObjectEntry parentObjectEntry, long primKey)
		throws Exception {

		_assertViewPermission(
			2, objectDefinition, objectRelatedModelsProvider,
			objectRelationship, parentObjectEntry,
			TestPropsValues.getCompanyId(), ResourceConstants.SCOPE_COMPANY);
		_assertViewPermission(
			1, objectDefinition, objectRelatedModelsProvider,
			objectRelationship, parentObjectEntry, primKey,
			ResourceConstants.SCOPE_INDIVIDUAL);
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testObjectEntry1toMObjectUnrelatedModelsProviderImpl(
			long companyId)
		throws Exception {

		User user = UserTestUtil.getAdminUser(companyId);

		Assert.assertNotNull(user);

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			_setUser(user);

			ObjectDefinition objectDefinition = _addObjectDefinition();

			ObjectDefinition systemObjectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					companyId, AccountEntry.class.getName());

			ObjectRelationship objectRelationship = _addObjectRelationship(
				objectDefinition.getObjectDefinitionId(),
				systemObjectDefinition.getObjectDefinitionId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			AccountEntry accountEntry1 = _addAccountEntry(user.getUserId());
			AccountEntry accountEntry2 = _addAccountEntry(user.getUserId());

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				user.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
				Collections.emptyMap(),
				ServiceContextTestUtil.getServiceContext());

			ObjectField objectRelationshipObjectField2 =
				_objectFieldLocalService.getObjectField(
					objectRelationship.getObjectFieldId2());

			_objectEntryLocalService.
				addOrUpdateExtensionDynamicObjectDefinitionTableValues(
					user.getUserId(), systemObjectDefinition,
					accountEntry1.getAccountEntryId(),
					HashMapBuilder.<String, Serializable>put(
						objectRelationshipObjectField2.getName(),
						objectEntry.getObjectEntryId()
					).build(),
					ServiceContextTestUtil.getServiceContext());

			ObjectRelatedModelsProvider<ObjectEntry>
				objectRelatedModelsProvider =
					_objectRelatedModelsProviderRegistry.
						getObjectRelatedModelsProvider(
							systemObjectDefinition.getClassName(),
							systemObjectDefinition.getCompanyId(),
							ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			List<ObjectEntry> unrelatedObjectEntries =
				objectRelatedModelsProvider.getUnrelatedModels(
					companyId, 0, systemObjectDefinition,
					objectEntry.getObjectEntryId(),
					objectRelationship.getObjectRelationshipId());

			Assert.assertEquals(
				unrelatedObjectEntries.toString(), 1,
				unrelatedObjectEntries.size());

			_objectEntryLocalService.
				addOrUpdateExtensionDynamicObjectDefinitionTableValues(
					user.getUserId(), systemObjectDefinition,
					accountEntry2.getAccountEntryId(),
					HashMapBuilder.<String, Serializable>put(
						objectRelationshipObjectField2.getName(),
						objectEntry.getObjectEntryId()
					).build(),
					ServiceContextTestUtil.getServiceContext());

			unrelatedObjectEntries =
				objectRelatedModelsProvider.getUnrelatedModels(
					companyId, 0, systemObjectDefinition,
					objectEntry.getObjectEntryId(),
					objectRelationship.getObjectRelationshipId());

			Assert.assertEquals(
				unrelatedObjectEntries.toString(), 0,
				unrelatedObjectEntries.size());

			_objectRelationshipLocalService.deleteObjectRelationship(
				objectRelationship.getObjectRelationshipId());

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition.getObjectDefinitionId());

			_accountEntryLocalService.deleteAccountEntries(
				new long[] {
					accountEntry1.getAccountEntryId(),
					accountEntry2.getAccountEntryId()
				});
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	private void _testObjectEntryMtoMObjectRelatedModelsProviderImpl(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2)
		throws Exception {

		ObjectRelationship objectRelationship = _addObjectRelationship(
			objectDefinition1.getObjectDefinitionId(),
			objectDefinition2.getObjectDefinitionId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				objectDefinition2.getClassName(),
				objectDefinition2.getCompanyId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _addObjectEntry(
			objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());
		ObjectEntry objectEntry2 = _addObjectEntry(
			objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

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

		ObjectEntry objectEntry3 = _addObjectEntry(
			objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry3.getObjectEntryId(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_setUser(_user);

		_assertViewPermission(
			objectDefinition2, objectRelatedModelsProvider, objectRelationship,
			objectEntry1, objectEntry2.getObjectEntryId());

		_setUser(TestPropsValues.getUser());

		try {
			_objectEntryLocalService.deleteObjectEntry(objectEntry1);

			Assert.fail();
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

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry1.getObjectEntryId()));

		_objectEntryLocalService.deleteObjectEntry(objectEntry3);

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
				objectRelationship.getLabelMap());

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				objectRelationship.getLabelMap());

		objectEntry1 = _addObjectEntry(
			objectDefinition1.getObjectDefinitionId(), Collections.emptyMap());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry2.getObjectEntryId(),
			ServiceContextTestUtil.getServiceContext());

		objectEntry3 = _addObjectEntry(
			objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry3.getObjectEntryId(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_objectEntryLocalService.deleteObjectEntry(objectEntry3);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry3.getObjectEntryId()));

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry1.getObjectEntryId()));

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry1.getObjectEntryId()));

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry2.getObjectEntryId()));

		ObjectRelationship reverseObjectRelationship =
			_objectRelationshipLocalService.fetchReverseObjectRelationship(
				objectRelationship, true);

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		Assert.assertNull(
			_objectRelationshipLocalService.fetchObjectRelationship(
				reverseObjectRelationship.getObjectRelationshipId()));
	}

	private void _testSystemObjectEntry1toMObjectRelatedModelsProviderImpl()
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

		ObjectRelationship objectRelationship = _addObjectRelationship(
			_objectDefinition2.getObjectDefinitionId(),
			systemObjectDefinition.getObjectDefinitionId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectEntry objectEntry = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		ObjectRelatedModelsProvider objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				systemObjectDefinition.getClassName(),
				systemObjectDefinition.getCompanyId(),
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
			TestPropsValues.getUserId(),
			systemObjectDefinition.getObjectDefinitionId(), userIds[0],
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			TestPropsValues.getUserId(),
			systemObjectDefinition.getObjectDefinitionId(), userIds[1],
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			TestPropsValues.getUserId(),
			systemObjectDefinition.getObjectDefinitionId(), userIds[2],
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		relatedObjectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 3, relatedObjectEntries.size());

		objectRelatedModelsProvider.disassociateRelatedModels(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), userIds[0]);

		relatedObjectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 2, relatedObjectEntries.size());

		try {
			_objectEntryLocalService.deleteObjectEntry(objectEntry);

			Assert.fail();
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
				objectRelationship.getObjectRelationshipId(), 0,
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
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				objectRelationship.getLabelMap());

		objectEntry = _addObjectEntry(
			_objectDefinition2.getObjectDefinitionId(), Collections.emptyMap());

		relatedObjectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry.getPrimaryKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedObjectEntries.toString(), 0, relatedObjectEntries.size());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			TestPropsValues.getUserId(),
			systemObjectDefinition.getObjectDefinitionId(), userIds[0],
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).put(
				objectField.getName(), objectEntry.getObjectEntryId()
			).build());

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			TestPropsValues.getUserId(),
			systemObjectDefinition.getObjectDefinitionId(), userIds[1],
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).put(
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

			Assert.fail();
		}
		catch (NoSuchUserException noSuchUserException) {
			Assert.assertEquals(
				"No User exists with the primary key " + userIds[0],
				noSuchUserException.getMessage());
		}

		try {
			_userLocalService.getUser(userIds[1]);

			Assert.fail();
		}
		catch (NoSuchUserException noSuchUserException) {
			Assert.assertEquals(
				"No User exists with the primary key " + userIds[1],
				noSuchUserException.getMessage());
		}
	}

	private void _updateObjectEntry(
			long objectEntryId, Map<String, Serializable> values)
		throws Exception {

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryId, values,
			ServiceContextTestUtil.getServiceContext());
	}

	private static PermissionChecker _originalPermissionChecker;
	private static Role _role;
	private static User _user;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

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