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
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
		setUpPermissionThreadLocal();

		_group = GroupTestUtil.addGroup();

		_siteMember = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_MEMBER);

		_adminUser = UserTestUtil.addOmniAdminUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testAddDataDefinitionPermission() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		Role role = RoleTestUtil.addRole(
			"Test Role", RoleConstants.TYPE_REGULAR);

		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, "Test Role");

		DEDataDefinitionSavePermissionsRequest
			deDataDefinitionSavePermissionsRequest =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					role.getName()
				).allowAddDataDefinition(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSavePermissionsRequest);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				user, group, _deDataDefinitionService);

		Assert.assertTrue(deDataDefinition.getDEDataDefinitionId() > 0);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testAddDataDefinitionPermissionWithNoPermission()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		DEDataDefinitionSavePermissionsRequest
			deDataDefinitionSavePermissionsRequest =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), user.getGroupId(),
					RoleConstants.ORGANIZATION_USER
				).allowAddDataDefinition(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSavePermissionsRequest);
	}

	@Test(expected = DEDataDefinitionException.PrincipalException.class)
	public void testAddDataDefinitionToGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataDefinitionSavePermissionsRequest
			deDataDefinitionSavePermissionsRequest =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
					RoleConstants.GUEST
				).allowAddDataDefinition(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSavePermissionsRequest);
	}

	@Test
	public void testCount() throws Exception {
		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, _deDataDefinitionService);

		int deDataDefinitionTotal = countDataDefinition(_group);

		Assert.assertEquals(2, deDataDefinitionTotal);
	}

	@Test
	public void testCountAfterDelete() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		int deDataDefinitionTotal = countDataDefinition(_group);

		Assert.assertEquals(1, deDataDefinitionTotal);

		deleteDataDefinition(
			_adminUser, deDataDefinition.getDEDataDefinitionId());

		int deDataDefinitionTotalAfterDelete = countDataDefinition(_group);

		Assert.assertEquals(0, deDataDefinitionTotalAfterDelete);
	}

	@Test
	public void testCountWithNoRecords() throws Exception {
		int deDataDefinitionTotal = countDataDefinition(_group);

		Assert.assertEquals(0, deDataDefinitionTotal);
	}

	@Test
	public void testCountWithoutPermission() throws Exception {
		DEDataEngineTestUtil.insertDEDataDefinition(
			_adminUser, _group, _deDataDefinitionService);

		Group otherGroup = GroupTestUtil.addGroup();

		int deDataDefinitionTotal = countDataDefinition(otherGroup);

		Assert.assertEquals(0, deDataDefinitionTotal);
	}

	@Test
	public void testDefinePermissions() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		Role role1 = RoleTestUtil.addRole(
			"Test Role 1", RoleConstants.TYPE_REGULAR);

		Group group1 = GroupTestUtil.addGroup();

		User user1 = UserTestUtil.addGroupUser(group1, "Test Role 1");

		DEDataDefinitionSavePermissionsRequest
			deDataDefinitionSavePermissionsRequest =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					role1.getName()
				).allowDefinePermissions(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSavePermissionsRequest);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		Role role2 = RoleTestUtil.addRole(
			"Test Role 2", RoleConstants.TYPE_REGULAR);

		Group group2 = GroupTestUtil.addGroup();

		User user2 = UserTestUtil.addGroupUser(group2, "Test Role 2");

		DEDataDefinitionSavePermissionsRequest
			deDataDefinitionSavePermissionsRequest2 =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), group1.getGroupId(),
					role2.getName()
				).allowAddDataDefinition(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSavePermissionsRequest2);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				user2, group2, _deDataDefinitionService);

		Assert.assertTrue(deDataDefinition.getDEDataDefinitionId() > 0);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDefinePermissionsByGuestUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		DEDataDefinitionSavePermissionsRequest
			deDataDefinitionSavePermissionsRequest =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), user.getGroupId(),
					RoleConstants.ORGANIZATION_USER
				).allowDefinePermissions(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSavePermissionsRequest);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDefinePermissionsBySiteMember() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionSavePermissionsRequest
			deDataDefinitionSavePermissionsRequest =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _siteMember.getGroupId(),
					RoleConstants.ORGANIZATION_USER
				).allowDefinePermissions(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSavePermissionsRequest);
	}

	@Test(expected = DEDataDefinitionException.PrincipalException.class)
	public void testDefinePermissionsToGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataDefinitionSavePermissionsRequest
			deDataDefinitionSavePermissionsRequest =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
					RoleConstants.GUEST
				).allowDefinePermissions(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSavePermissionsRequest);
	}

	@Test
	public void testDelete() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataDefinitionSaveModelPermissionsRequest
			deDataDefinitionSaveModelPermissionsRequest =
				DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					deDataDefinition.getDEDataDefinitionId()
				).grantTo(
					_siteMember.getUserId()
				).inGroup(
					_group.getGroupId()
				).allowDelete(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSaveModelPermissionsRequest);

		deleteDataDefinition(
			_siteMember, deDataDefinition.getDEDataDefinitionId());
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

	@Test(expected = DEDataDefinitionException.class)
	public void testDeleteWithNoIDParameter() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
			DEDataDefinitionRequestBuilder.deleteBuilder().build();

		_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDeleteWithNoPermission() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		deleteDataDefinition(user, deDataDefinition.getDEDataDefinitionId());
	}

	@Test
	public void testGetByGuestUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.GUEST);

		DEDataDefinition expectedDEDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataDefinitionSaveModelPermissionsRequest
			deDataDefinitionSaveModelPermissionsRequest =
				DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					expectedDEDataDefinition.getDEDataDefinitionId()
				).grantTo(
					user.getUserId()
				).inGroup(
					user.getGroupId()
				).allowView(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSaveModelPermissionsRequest);

		DEDataDefinition deDataDefinition = getDataDefinition(
			user, expectedDEDataDefinition.getDEDataDefinitionId());

		Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);
	}

	@Test
	public void testGetBySiteMember() throws Exception {
		DEDataDefinition expectedDEDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataDefinitionSaveModelPermissionsRequest
			deDataDefinitionSaveModelPermissionsRequest =
				DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					expectedDEDataDefinition.getDEDataDefinitionId()
				).grantTo(
					_siteMember.getUserId()
				).inGroup(
					_group.getGroupId()
				).allowView(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSaveModelPermissionsRequest);

		DEDataDefinition deDataDefinition = getDataDefinition(
			_siteMember, expectedDEDataDefinition.getDEDataDefinitionId());

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
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		getDataDefinition(user, deDataDefinition.getDEDataDefinitionId());
	}

	@Test
	public void testInsert() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_siteMember, _group, _deDataDefinitionService);

		Assert.assertTrue(deDataDefinition.getDEDataDefinitionId() > 0);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testInsertWithNoPermission() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		DEDataEngineTestUtil.insertDEDataDefinition(
			user, group, _deDataDefinitionService);
	}

	@Test
	public void testListPaginatedMiddleInsideRange() throws Exception {
		int dataDefinitionTotal = 5;

		for (int i = 0; i < dataDefinitionTotal; i++) {
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
		int dataDefinitionTotal = 5;

		for (int i = 0; i < dataDefinitionTotal; i++) {
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
		int dataDefinitionTotal = 5;

		for (int i = 0; i < dataDefinitionTotal; i++) {
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
		int dataDefinitionTotal = 5;

		for (int i = 0; i < dataDefinitionTotal; i++) {
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
		int dataDefinitionTotal = 5;

		for (int i = 0; i < dataDefinitionTotal; i++) {
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
		int dataDefinitionTotal = 5;

		for (int i = 0; i < dataDefinitionTotal; i++) {
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
		int dataDefinitionTotal = 5;

		for (int i = 0; i < dataDefinitionTotal; i++) {
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
		int dataDefinitionTotal = 5;

		for (int i = 0; i < dataDefinitionTotal; i++) {
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);
		}

		List<DEDataDefinition> deDataDefinitions = listDEDataDefinitions(
			_group, null, null);

		Assert.assertEquals(
			deDataDefinitions.toString(), 5, deDataDefinitions.size());
	}

	@Test
	public void testUpdate() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataDefinitionSaveModelPermissionsRequest
			deDataDefinitionSaveModelPermissionsRequest =
				DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					deDataDefinition.getDEDataDefinitionId()
				).grantTo(
					_siteMember.getUserId()
				).inGroup(
					_group.getGroupId()
				).allowUpdate(
				).build();

		_deDataDefinitionService.execute(
			deDataDefinitionSaveModelPermissionsRequest);

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, _siteMember.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			deDataDefinition.addName(LocaleUtil.BRAZIL, "Definition BR");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					_siteMember.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			Assert.assertTrue(
				deDataDefinitionSaveResponse.getDEDataDefinitionId() > 0);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected int countDataDefinition(Group group) throws Exception {
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

	protected void deleteDataDefinition(User user, long deDataDefinitionId)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
			DEDataDefinitionRequestBuilder.deleteBuilder(
			).byId(
				deDataDefinitionId
			).build();

		_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
	}

	protected DEDataDefinition getDataDefinition(
			User user, long deDataDefinitionId)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		DEDataDefinitionGetRequest deDataDefinitionGetRequest =
			DEDataDefinitionRequestBuilder.getBuilder(
			).byId(
				deDataDefinitionId
			).build();

		DEDataDefinitionGetResponse deDataDefinitionGetResponse =
			_deDataDefinitionService.execute(deDataDefinitionGetRequest);

		return deDataDefinitionGetResponse.getDeDataDefinition();
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

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteMember;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}