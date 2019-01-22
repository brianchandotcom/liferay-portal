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

package com.liferay.data.engine.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionCountResponse;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionListRequest;
import com.liferay.data.engine.service.DEDataDefinitionListResponse;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionSearchCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionSearchCountResponse;
import com.liferay.data.engine.service.DEDataDefinitionSearchRequest;
import com.liferay.data.engine.service.DEDataDefinitionSearchResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DEDataDefinitionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_siteAdminUser = UserTestUtil.addGroupAdminUser(_group);

		_siteMember = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_MEMBER);

		_adminUser = UserTestUtil.addOmniAdminUser();

		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();
	}

	@After
	public void tearDown() {
		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddDataDefinitionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role.getName()}
					).allowAddDataDefinition(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				user, _group, _deDataDefinitionService);

		Assert.assertTrue(deDataDefinition.getDEDataDefinitionId() > 0);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testAddDataDefinitionPermissionByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowAddDataDefinition(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testAddDataDefinitionPermissionBySiteMember() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _siteMember.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowAddDataDefinition(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.PrincipalException.class)
	public void testAddDataDefinitionToGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.GUEST}
					).allowAddDataDefinition(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testCount() throws Exception {
		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, _deDataDefinitionService);

		int total = counDEtDataDefinitions(_group);

		Assert.assertEquals(2, total);
	}

	@Test
	public void testCountAfterDelete() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		int total = counDEtDataDefinitions(_group);

		Assert.assertEquals(1, total);

		deleteDEDataDefinition(
			_adminUser, _group, deDataDefinition.getDEDataDefinitionId());

		int totalAfterDelete = counDEtDataDefinitions(_group);

		Assert.assertEquals(0, totalAfterDelete);
	}

	@Test
	public void testCountWithNoRecords() throws Exception {
		int total = counDEtDataDefinitions(_group);

		Assert.assertEquals(0, total);
	}

	@Test
	public void testCountWithoutPermission() throws Exception {
		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, _deDataDefinitionService);

		Group otherGroup = GroupTestUtil.addGroup();

		int total = counDEtDataDefinitions(otherGroup);

		Assert.assertEquals(0, total);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDefinePermissionsByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowDefinePermissions(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDefinePermissionsBySiteMember() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _siteMember.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(),
						_siteMember.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowDefinePermissions(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.PrincipalException.class)
	public void testDefinePermissionsToGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
						new String[] {RoleConstants.GUEST}
					).allowDefinePermissions(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testDefinePermissionToAllowAddDataDefinition()
		throws Exception {

		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		Role role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user2 = UserTestUtil.addGroupUser(_group, role2.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest2 =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role2.getName()}
					).allowAddDataDefinition(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				user2, _group, _deDataDefinitionService);

		Assert.assertTrue(deDataDefinition.getDEDataDefinitionId() > 0);
	}

	@Test
	public void testDefinePermissionToAllowDeleteDataDefinition()
		throws Exception {

		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		Role role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user2 = UserTestUtil.addGroupUser(_group, role2.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {role2.getName()}
					).allowDelete(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		deleteDEDataDefinition(
			user2, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test
	public void testDefinePermissionToAllowUpdateDataDefinition()
		throws Exception {

		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		Role role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user2 = UserTestUtil.addGroupUser(_group, role2.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {role2.getName()}
					).allowUpdate(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinitionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataDefinition(
				user2, _group, deDataDefinition, _deDataDefinitionService);

		Assert.assertEquals(
			deDataDefinition.getDEDataDefinitionId(),
			deDataDefinitionAfterUpdate.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDeleteByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		deleteDEDataDefinition(
			user, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDeleteBySiteMember() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		deleteDEDataDefinition(
			_siteMember, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDeleteNoSuchDataDefinition() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
			DEDataDefinitionRequestBuilder.deleteBuilder(
			).byId(
				1
			).build();

		_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDeleteNoSuchDataDefinition2() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
			DEDataDefinitionRequestBuilder.deleteBuilder(
			).byId(
				Long.MAX_VALUE
			).build();

		_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDeleteNoSuchDataDefinition3() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
			DEDataDefinitionRequestBuilder.deleteBuilder(
			).byId(
				Long.MAX_VALUE + 1
			).build();

		_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDeleteNoSuchDataDefinition4() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
			DEDataDefinitionRequestBuilder.deleteBuilder(
			).byId(
				Long.MIN_VALUE
			).build();

		_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDeleteNoSuchDataDefinition5() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
			DEDataDefinitionRequestBuilder.deleteBuilder(
			).byId(
				Long.MIN_VALUE - 1
			).build();

		_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
	}

	@Test(
		expected = DEDataDefinitionException.MustHaveNoDataRecordCollection.class
	)
	public void testDeleteWithDEDataRecordCollectionAssociated()
		throws Exception {

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			_adminUser, _group, deDataDefinition,
			_deDataRecordCollectionService);

		deleteDEDataDefinition(
			_adminUser, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDeleteWithNoIDParameter() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
			DEDataDefinitionRequestBuilder.deleteBuilder().build();

		_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDeleteWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		deleteDEDataDefinition(
			user, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test
	public void testGetByGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.GUEST, ActionKeys.VIEW);

		serviceContext.setModelPermissions(modelPermissions);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition();

		try {
			Map<String, String> nameLabels = new HashMap() {
				{
					put("pt_BR", "Nome");
					put("en_US", "Name");
				}
			};

			DEDataDefinitionField deDataDefinitionField1 =
				new DEDataDefinitionField("name", "string");

			deDataDefinitionField1.addLabels(nameLabels);

			Map<String, String> emailLabels = new HashMap() {
				{
					put("pt_BR", "Endereço de Email");
					put("en_US", "Email Address");
				}
			};

			DEDataDefinitionField deDataDefinitionField2 =
				new DEDataDefinitionField("email", "string");

			deDataDefinitionField2.addLabels(emailLabels);

			expectedDEDataDefinition.addDescription(
				LocaleUtil.US, "Contact description");
			expectedDEDataDefinition.addDescription(
				LocaleUtil.BRAZIL, "Descrição do contato");
			expectedDEDataDefinition.addName(LocaleUtil.US, "Contact");
			expectedDEDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
			expectedDEDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(deDataDefinitionField1, deDataDefinitionField2));
			expectedDEDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					expectedDEDataDefinition
				).onBehalfOf(
					_adminUser.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			expectedDEDataDefinition.setDEDataDefinitionId(
				deDataDefinitionSaveResponse.getDEDataDefinitionId());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		DEDataDefinition deDataDefinition = getDEDataDefinition(
			user, _group, expectedDEDataDefinition.getDEDataDefinitionId());

		Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);
	}

	@Test
	public void testGetBySiteMember() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.SITE_MEMBER, ActionKeys.VIEW);

		serviceContext.setModelPermissions(modelPermissions);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition();

		try {
			Map<String, String> nameLabels = new HashMap() {
				{
					put("pt_BR", "Nome");
					put("en_US", "Name");
				}
			};

			DEDataDefinitionField deDataDefinitionField1 =
				new DEDataDefinitionField("name", "string");

			deDataDefinitionField1.addLabels(nameLabels);

			Map<String, String> emailLabels = new HashMap() {
				{
					put("pt_BR", "Endereço de Email");
					put("en_US", "Email Address");
				}
			};

			DEDataDefinitionField deDataDefinitionField2 =
				new DEDataDefinitionField("email", "string");

			deDataDefinitionField2.addLabels(emailLabels);

			expectedDEDataDefinition.addDescription(
				LocaleUtil.US, "Contact description");
			expectedDEDataDefinition.addDescription(
				LocaleUtil.BRAZIL, "Descrição do contato");
			expectedDEDataDefinition.addName(LocaleUtil.US, "Contact");
			expectedDEDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
			expectedDEDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(deDataDefinitionField1, deDataDefinitionField2));
			expectedDEDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					expectedDEDataDefinition
				).onBehalfOf(
					_adminUser.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			expectedDEDataDefinition.setDEDataDefinitionId(
				deDataDefinitionSaveResponse.getDEDataDefinitionId());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		DEDataDefinition deDataDefinition = getDEDataDefinition(
			_siteMember, _group,
			expectedDEDataDefinition.getDEDataDefinitionId());

		Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testGetNoSuchDataDefinition() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionGetRequest deDataDefinitionGetRequest =
			DEDataDefinitionRequestBuilder.getBuilder(
			).byId(
				1
			).build();

		_deDataDefinitionService.execute(deDataDefinitionGetRequest);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testGetWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		getDEDataDefinition(
			user, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.PrincipalException.class)
	public void testGrantDeletePermissionToGuestUser() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {RoleConstants.GUEST}
					).allowDelete(
					).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGrantDeletePermissionToSiteMember() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {RoleConstants.SITE_MEMBER}
					).allowDelete(
					).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		deleteDEDataDefinition(
			_siteMember, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.PrincipalException.class)
	public void testGrantUpdatePermissionToGuestUser() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {RoleConstants.GUEST}
					).allowUpdate(
					).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGrantUpdatePermissionToSiteMember() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {RoleConstants.SITE_MEMBER}
					).allowUpdate(
					).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinitionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataDefinition(
				_siteMember, _group, deDataDefinition,
				_deDataDefinitionService);

		Assert.assertEquals(deDataDefinition, deDataDefinitionAfterUpdate);
	}

	@Test
	public void testGrantViewPermissionToGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataDefinition expectedDEDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						expectedDEDataDefinition.getDEDataDefinitionId(),
						new String[] {RoleConstants.GUEST}
					).allowView(
					).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition = getDEDataDefinition(
			user, _group, expectedDEDataDefinition.getDEDataDefinitionId());

		Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);
	}

	@Test
	public void testGrantViewPermissionToSiteMember() throws Exception {
		DEDataDefinition expectedDEDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						expectedDEDataDefinition.getDEDataDefinitionId(),
						new String[] {RoleConstants.SITE_MEMBER}
					).allowView(
					).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition = getDEDataDefinition(
			_siteMember, _group,
			expectedDEDataDefinition.getDEDataDefinitionId());

		Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testInsertByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataEngineTestUtil.insertDEDataDefinition(
			user, _group, _deDataDefinitionService);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testInsertBySiteMember() throws Exception {
		DEDataEngineTestUtil.insertDEDataDefinition(
			_siteMember, _group, _deDataDefinitionService);
	}

	@Test(expected = DEDataDefinitionException.class)
	public void testInsertWithBadRequest() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			Map<String, String> emailLabels = new HashMap() {
				{
					put("pt_BR", "Endereço de Email");
					put("en_US", "Email Address");
				}
			};

			DEDataDefinitionField deDataDefinitionField =
				new DEDataDefinitionField("email", "string");

			deDataDefinitionField.addLabels(emailLabels);

			DEDataDefinition deDataDefinition = new DEDataDefinition();

			deDataDefinition.addDescription(
				LocaleUtil.US, "Contact description");
			deDataDefinition.addDescription(
				LocaleUtil.BRAZIL, "Descrição do contato");
			deDataDefinition.addName(LocaleUtil.US, "Contact");
			deDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
			deDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(deDataDefinitionField));
			deDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).inGroup(
					_group.getGroupId()
				).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.class)
	public void testInsertWithNonexistentGroup() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			Map<String, String> emailLabels = new HashMap() {
				{
					put("pt_BR", "Endereço de Email");
					put("en_US", "Email Address");
				}
			};

			DEDataDefinitionField deDataDefinitionField =
				new DEDataDefinitionField("email", "string");

			deDataDefinitionField.addLabels(emailLabels);

			DEDataDefinition deDataDefinition = new DEDataDefinition();

			deDataDefinition.addDescription(
				LocaleUtil.US, "Contact description");
			deDataDefinition.addDescription(
				LocaleUtil.BRAZIL, "Descrição do contato");
			deDataDefinition.addName(LocaleUtil.US, "Contact");
			deDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
			deDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(deDataDefinitionField));
			deDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					_adminUser.getUserId()
				).inGroup(
					1
				).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.class)
	public void testInsertWithNonexistentUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			Map<String, String> emailLabels = new HashMap() {
				{
					put("pt_BR", "Endereço de Email");
					put("en_US", "Email Address");
				}
			};

			DEDataDefinitionField deDataDefinitionField =
				new DEDataDefinitionField("email", "string");

			deDataDefinitionField.addLabels(emailLabels);

			DEDataDefinition deDataDefinition = new DEDataDefinition();

			deDataDefinition.addDescription(
				LocaleUtil.US, "Contact description");
			deDataDefinition.addDescription(
				LocaleUtil.BRAZIL, "Descrição do contato");
			deDataDefinition.addName(LocaleUtil.US, "Contact");
			deDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
			deDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(deDataDefinitionField));
			deDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					1
				).inGroup(
					_group.getGroupId()
				).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testInsertWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataEngineTestUtil.insertDEDataDefinition(
			user, _group, _deDataDefinitionService);
	}

	@Test
	public void testListPaginatedMiddleInsideRange() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, 1, 5);

		Assert.assertEquals(
			deDataDefinitions.toString(), 4, deDataDefinitions.size());
	}

	@Test
	public void testListPaginatedMiddleOutOfRange() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, 4, 10);

		Assert.assertEquals(
			deDataDefinitions.toString(), 1, deDataDefinitions.size());
	}

	@Test
	public void testListPaginatedOutOfRange() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, 6, 10);

		Assert.assertEquals(
			deDataDefinitions.toString(), 0, deDataDefinitions.size());
	}

	@Test
	public void testListPaginatedReturnLast() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, 4, 5);

		Assert.assertEquals(
			deDataDefinitions.toString(), 1, deDataDefinitions.size());
	}

	@Test
	public void testListPaginatedStartingAtMinusOne() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, -2, 5);

		Assert.assertEquals(
			deDataDefinitions.toString(), 5, deDataDefinitions.size());
	}

	@Test
	public void testListPaginatedStartingAtMinusTwo() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, -1, 5);

		Assert.assertEquals(
			deDataDefinitions.toString(), 5, deDataDefinitions.size());
	}

	@Test
	public void testListPaginatedStartingAtZero() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, 0, 5);

		Assert.assertEquals(
			deDataDefinitions.toString(), 5, deDataDefinitions.size());
	}

	@Test
	public void testListWithNoRecords() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, null, null);

		Assert.assertTrue(deDataDefinitions.isEmpty());
	}

	@Test
	public void testListWithRecords() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, null, null);

		Assert.assertEquals(
			deDataDefinitions.toString(), 5, deDataDefinitions.size());
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testRevokeAddDataDefinitionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role.getName()}
					).allowAddDataDefinition(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				user, _group, _deDataDefinitionService);

		Assert.assertTrue(deDataDefinition.getDEDataDefinitionId() > 0);

		DEDataEngineTestUtil.deleteDEDataDefinitionPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			new String[] {role.getName()}, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataDefinition(
			user, _group, _deDataDefinitionService);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testRevokeDefinePermissions() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		Role role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user2 = UserTestUtil.addGroupUser(_group, role2.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest =
					DEDataDefinitionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {role2.getName()}
					).allowUpdate(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinitionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataDefinition(
				user2, _group, deDataDefinition, _deDataDefinitionService);

		Assert.assertEquals(
			deDataDefinition.getDEDataDefinitionId(),
			deDataDefinitionAfterUpdate.getDEDataDefinitionId());

		DEDataEngineTestUtil.deleteDEDataDefinitionPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			new String[] {role1.getName()}, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext3 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext3);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {role2.getName()}
					).allowUpdate(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testRevokeDeleteDataDefinitionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest2 =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {role.getName()}
					).allowDelete(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.deleteDEDataDefinitionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataDefinition.getDEDataDefinitionId(),
			new String[] {ActionKeys.DELETE}, new String[] {role.getName()},
			_deDataDefinitionService);

		deleteDEDataDefinition(
			user, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testRevokeModelPermissionsWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.deleteDEDataDefinitionModelPermissions(
			TestPropsValues.getCompanyId(), user, _group.getGroupId(),
			deDataDefinition.getDEDataDefinitionId(),
			new String[] {ActionKeys.UPDATE},
			new String[] {RoleConstants.ORGANIZATION_USER},
			_deDataDefinitionService);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testRevokePermissionsWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataEngineTestUtil.deleteDEDataDefinitionPermissions(
			TestPropsValues.getCompanyId(), user, _group.getGroupId(),
			new String[] {RoleConstants.ORGANIZATION_USER},
			_deDataDefinitionService);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testRevokeUpdateDataDefinitionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest2 =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {role.getName()}
					).allowUpdate(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.updateDEDataDefinition(
			user, _group, deDataDefinition, _deDataDefinitionService);

		DEDataEngineTestUtil.updateDEDataDefinition(
			_adminUser, _group, deDataDefinition, _deDataDefinitionService);

		DEDataEngineTestUtil.deleteDEDataDefinitionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataDefinition.getDEDataDefinitionId(),
			new String[] {ActionKeys.UPDATE}, new String[] {role.getName()},
			_deDataDefinitionService);

		DEDataEngineTestUtil.updateDEDataDefinition(
			user, _group, deDataDefinition, _deDataDefinitionService);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testRevokeViewDataDefinitionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest2 =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataDefinition.getDEDataDefinitionId(),
						new String[] {role.getName()}
					).allowView(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition2 = getDEDataDefinition(
			user, _group, deDataDefinition.getDEDataDefinitionId());

		Assert.assertTrue(deDataDefinition2.getDEDataDefinitionId() > 0);

		DEDataEngineTestUtil.deleteDEDataDefinitionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataDefinition.getDEDataDefinitionId(),
			new String[] {ActionKeys.VIEW}, new String[] {role.getName()},
			_deDataDefinitionService);

		getDEDataDefinition(
			user, _group, deDataDefinition.getDEDataDefinitionId());
	}

	@Test
	public void testSearchBlank() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "Description" + i, "Name" + i,
				_deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = searchDEDataDefinitions(
			_group, "");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					deDataDefinitions.toString(), 5, deDataDefinitions.size());

				return null;
			});
	}

	@Test
	public void testSearchCount() throws Exception {
		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, "description1", "name 1",
			_deDataDefinitionService);
		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, "description2", "name 2",
			_deDataDefinitionService);

		int total = searchCountDEDataDefinitions(_group, "name");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(2, total);

				return null;
			});
	}

	@Test
	public void testSearchCountAfterDeletion() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "description1", "name 1",
				_deDataDefinitionService);

		deleteDEDataDefinition(
			_adminUser, _group, deDataDefinition.getDEDataDefinitionId());

		int total = searchCountDEDataDefinitions(_group, "name");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(0, total);

				return null;
			});
	}

	@Test
	public void testSearchCountWithNoRecords() throws Exception {
		int total = searchCountDEDataDefinitions(_group, "name");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(0, total);

				return null;
			});
	}

	@Test
	public void testSearchCountWithoutPermission() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "description1", "name 1",
				_deDataDefinitionService);

		deleteDEDataDefinition(
			_adminUser, _group, deDataDefinition.getDEDataDefinitionId());

		int total = searchCountDEDataDefinitions(
			GroupTestUtil.addGroup(), "name");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(0, total);

				return null;
			});
	}

	@Test
	public void testSearchExactCaseSensitive() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "Description" + i, "Name" + i,
				_deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = searchDEDataDefinitions(
			_group, "description1");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					deDataDefinitions.toString(), 1, deDataDefinitions.size());

				return null;
			});
	}

	@Test
	public void testSearchExactDescription() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "Description" + i, "Name" + i,
				_deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = searchDEDataDefinitions(
			_group, "Description1");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					deDataDefinitions.toString(), 1, deDataDefinitions.size());

				return null;
			});
	}

	@Test
	public void testSearchExactName() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "Description" + i, "Name" + i,
				_deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = searchDEDataDefinitions(
			_group, "Name1");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					deDataDefinitions.toString(), 1, deDataDefinitions.size());

				return null;
			});
	}

	@Test
	public void testSearchNonascii() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "Description" + i, "Name" + i,
				_deDataDefinitionService);
		}

		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, "nonascii£", "Name", _deDataDefinitionService);

		List<DEDataDefinition> deDataDefinitions = searchDEDataDefinitions(
			_group, "nonascii£");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					deDataDefinitions.toString(), 1, deDataDefinitions.size());

				return null;
			});
	}

	@Test
	public void testSearchNonexistingNameDescription() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "Description" + i, "Name" + i,
				_deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = searchDEDataDefinitions(
			_group, "NonExistingNameDescription");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					deDataDefinitions.toString(), 0, deDataDefinitions.size());

				return null;
			});
	}

	@Test
	public void testSearchParcial() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "Description" + i, "Name" + i,
				_deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = searchDEDataDefinitions(
			_group, "Descrip");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					deDataDefinitions.toString(), 5, deDataDefinitions.size());

				return null;
			});
	}

	@Test
	public void testSearchWithSpace() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "Description " + i, "Name" + i,
				_deDataDefinitionService);
		}

		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, "Spaced Words ", "Name",
			_deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, "Spaced ", "Name", _deDataDefinitionService);

		List<DEDataDefinition> deDataDefinitions = searchDEDataDefinitions(
			_group, "Spaced Words");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Assert.assertEquals(
					deDataDefinitions.toString(), 2, deDataDefinitions.size());

				return null;
			});
	}

	@Test
	public void testUpdateAfterUpdate() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		deDataDefinition.addDescription(LocaleUtil.US, "Data definition 2");

		DEDataEngineTestUtil.updateDEDataDefinition(
			_adminUser, _group, deDataDefinition, _deDataDefinitionService);

		deDataDefinition.addDescription(LocaleUtil.US, "Data definition 3");

		DEDataDefinition deDataDefinitionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataDefinition(
				_adminUser, _group, deDataDefinition, _deDataDefinitionService);

		Assert.assertEquals(
			deDataDefinition.getDEDataDefinitionId(),
			deDataDefinitionAfterUpdate.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testUpdateByGuestUser() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		deDataDefinition.addDescription(LocaleUtil.US, "Data definition 2");

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataEngineTestUtil.updateDEDataDefinition(
			user, _group, deDataDefinition, _deDataDefinitionService);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testUpdateBySiteMember() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		deDataDefinition.addDescription(LocaleUtil.US, "Data definition 2");

		DEDataEngineTestUtil.updateDEDataDefinition(
			_siteMember, _group, deDataDefinition, _deDataDefinitionService);
	}

	protected int counDEtDataDefinitions(Group group) throws Exception {
		DEDataDefinitionCountRequest deDataDefinitionCountRequest =
			DEDataDefinitionRequestBuilder.countBuilder(
			).inCompany(
				group.getCompanyId()
			).inGroup(
				group.getGroupId()
			).build();

		DEDataDefinitionCountResponse deDataDefinitionCountResponse =
			_deDataDefinitionService.execute(deDataDefinitionCountRequest);

		return deDataDefinitionCountResponse.getTotal();
	}

	protected void deleteDEDataDefinition(
			User user, Group group, long deDataDefinitionId)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected DEDataDefinition getDEDataDefinition(
			User user, Group group, long deDataDefinitionId)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionRequestBuilder.getBuilder(
				).byId(
					deDataDefinitionId
				).build();

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				_deDataDefinitionService.execute(deDataDefinitionGetRequest);

			return deDataDefinitionGetResponse.getDeDataDefinition();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected List<DEDataDefinition> listDEDataDefinitions(
			Group group, Integer start, Integer end)
		throws Exception {

		DEDataDefinitionListRequest deDataDefinitionListRequest =
			DEDataDefinitionRequestBuilder.listBuilder(
			).endingAt(
				GetterUtil.getInteger(end, QueryUtil.ALL_POS)
			).inCompany(
				group.getCompanyId()
			).inGroup(
				group.getGroupId()
			).startingAt(
				GetterUtil.getInteger(start, QueryUtil.ALL_POS)
			).build();

		DEDataDefinitionListResponse deDataDefinitionListResponse =
			_deDataDefinitionService.execute(deDataDefinitionListRequest);

		return deDataDefinitionListResponse.getDEDataDefinitions();
	}

	protected int searchCountDEDataDefinitions(Group group, String keywords) {
		DEDataDefinitionSearchCountRequest deDataDefinitionSearchCountRequest =
			DEDataDefinitionRequestBuilder.searchCountBuilder(
			).havingKeywords(
				keywords
			).inCompany(
				group.getCompanyId()
			).inGroup(
				_group.getGroupId()
			).build();

		DEDataDefinitionSearchCountResponse
			deDataDefinitionSearchCountResponse =
				_deDataDefinitionService.execute(
					deDataDefinitionSearchCountRequest);

		return deDataDefinitionSearchCountResponse.getTotal();
	}

	protected List<DEDataDefinition> searchDEDataDefinitions(
			Group group, String keywords)
		throws Exception {

		DEDataDefinitionSearchRequest deDataDefinitionSearchRequest =
			DEDataDefinitionRequestBuilder.searchBuilder(
			).havingKeywords(
				keywords
			).inCompany(
				group.getCompanyId()
			).inGroup(
				group.getGroupId()
			).build();

		DEDataDefinitionSearchResponse deDataDefinitionSearchResponse =
			_deDataDefinitionService.execute(deDataDefinitionSearchRequest);

		return deDataDefinitionSearchResponse.getDEDataDefinitions();
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(_siteAdminUser.getUserId());
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@Inject(type = DEDataRecordCollectionService.class)
	private DEDataRecordCollectionService _deDataRecordCollectionService;

	@DeleteAfterTestRun
	private Group _group;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteAdminUser;

	@DeleteAfterTestRun
	private User _siteMember;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}