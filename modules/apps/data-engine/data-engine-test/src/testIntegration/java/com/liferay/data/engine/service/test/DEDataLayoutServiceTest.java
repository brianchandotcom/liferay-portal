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
import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataLayoutCountRequest;
import com.liferay.data.engine.service.DEDataLayoutCountResponse;
import com.liferay.data.engine.service.DEDataLayoutDeleteModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutDeletePermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutDeleteRequest;
import com.liferay.data.engine.service.DEDataLayoutDeleteResponse;
import com.liferay.data.engine.service.DEDataLayoutGetRequest;
import com.liferay.data.engine.service.DEDataLayoutGetResponse;
import com.liferay.data.engine.service.DEDataLayoutListRequest;
import com.liferay.data.engine.service.DEDataLayoutListResponse;
import com.liferay.data.engine.service.DEDataLayoutRequestBuilder;
import com.liferay.data.engine.service.DEDataLayoutSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutSaveRequest;
import com.liferay.data.engine.service.DEDataLayoutSaveResponse;
import com.liferay.data.engine.service.DEDataLayoutService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DEDataLayoutServiceTest {

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

	@Test
	public void testAddDataLayoutPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role.getName()}
					).allowAddDataLayout(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = insertDataLayout(user, _group);

		Assert.assertTrue(deDataLayout.getDEDataLayoutId() > 0);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testAddDataLayoutPermissionByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowAddDataLayout(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testAddDataLayoutPermissionBySiteMember() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _siteMember.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowAddDataLayout(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataLayoutException.PrincipalException.class)
	public void testAddDataLayoutPermissionToGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
						new String[] {RoleConstants.GUEST}
					).allowAddDataLayout(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testCount() throws Exception {
		saveDataLayout(_group, _adminUser, "layout1", "this is a layout1");
		saveDataLayout(_group, _adminUser, "layout2", "this is a layout2");
		int total = countDEDataLayouts(_group);

		Assert.assertEquals(2, total);
	}

	@Test
	public void testCountAfterDelete() throws Exception {
		DEDataLayout deDataLayout = saveDataLayout(
			_group, _adminUser, "layout", "this is a layout");

		int total = countDEDataLayouts(_group);

		Assert.assertEquals(1, total);

		deleteDEDataLayout(
			_adminUser, _group, deDataLayout.getDEDataLayoutId());

		int totalAfterDelete = countDEDataLayouts(_group);

		Assert.assertEquals(0, totalAfterDelete);
	}

	@Test
	public void testCountWithNoRecords() throws Exception {
		int total = countDEDataLayouts(_group);

		Assert.assertEquals(0, total);
	}

	@Test
	public void testCountWithoutPermission() throws Exception {
		saveDataLayout(_group, _adminUser, "layout1", "this is a layout1");

		Group otherGroup = GroupTestUtil.addGroup();

		int total = countDEDataLayouts(otherGroup);

		Assert.assertEquals(0, total);
	}

	@Test
	public void testCreateDEDataLayout() throws Exception {
		DEDataLayout deDataLayout = saveDataLayout(
			_group, _adminUser, "layout", "this is a layout");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataLayoutGetRequest deDataLayoutGetRequest =
			DEDataLayoutRequestBuilder.getBuilder(
			).byId(
				deDataLayout.getDEDataLayoutId()
			).build();

		DEDataLayoutGetResponse deDataLayoutGetResponse =
			_deDataLayoutService.execute(deDataLayoutGetRequest);

		Assert.assertEquals(
			deDataLayout, deDataLayoutGetResponse.getDEDataLayout());
	}

	@Test
	public void testCreateTwoDEDataLayoutsForOneDEDataDefinition()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "description", "definition",
				_deDataDefinitionService);

		DEDataLayout deDataLayout1 = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		deDataLayout1.setDEDataDefinitionId(
			deDataDefinition.getDEDataDefinitionId());

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout1
			).inGroup(
				_adminUser.getGroupId()
			).onBehalfOf(
				_adminUser.getUserId()
			).build();

		DEDataLayoutSaveResponse deDataLayoutSaveResponse =
			_deDataLayoutService.execute(deDataLayoutSaveRequest);

		DEDataLayout deDataLayout2 = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		deDataLayout2.setDEDataDefinitionId(
			deDataDefinition.getDEDataDefinitionId());

		deDataLayoutSaveRequest = DEDataLayoutRequestBuilder.saveBuilder(
			deDataLayout2
		).inGroup(
			_adminUser.getGroupId()
		).onBehalfOf(
			_adminUser.getUserId()
		).build();

		deDataLayoutSaveResponse = _deDataLayoutService.execute(
			deDataLayoutSaveRequest);

		DEDataLayoutGetRequest deDataLayoutGetRequest =
			DEDataLayoutRequestBuilder.getBuilder(
			).byId(
				deDataLayoutSaveResponse.getDEDataLayoutId()
			).build();

		DEDataLayoutGetResponse deDataLayoutGetResponse =
			_deDataLayoutService.execute(deDataLayoutGetRequest);

		deDataLayout2 = deDataLayoutGetResponse.getDEDataLayout();

		Assert.assertEquals(
			deDataLayout1.getDEDataDefinitionId(),
			deDataLayout2.getDEDataDefinitionId());
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testDefinePermissionsByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowDefinePermissions(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testDefinePermissionsBySiteMember() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _siteMember.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(),
						_siteMember.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowDefinePermissions(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataLayoutException.PrincipalException.class)
	public void testDefinePermissionsToGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
						new String[] {RoleConstants.GUEST}
					).allowDefinePermissions(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testDefinePermissionToAllowAddDataLayout() throws Exception {
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
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
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
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest2 =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role2.getName()}
					).allowAddDataLayout(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = insertDataLayout(user2, _group);

		Assert.assertTrue(deDataLayout.getDEDataLayoutId() > 0);
	}

	@Test
	public void testDefinePermissionToAllowDeleteDataLayout() throws Exception {
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
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest =
					DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataLayout.getDEDataLayoutId(),
						new String[] {role2.getName()}
					).allowDelete(
					).build();

			_deDataLayoutService.execute(
				deDataLayoutSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		deleteDEDataLayout(user2, _group, deDataLayout.getDEDataLayoutId());
	}

	@Test
	public void testDefinePermissionToAllowUpdateDataLayout() throws Exception {
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
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest =
					DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataLayout.getDEDataLayoutId(),
						new String[] {role2.getName()}
					).allowUpdate(
					).build();

			_deDataLayoutService.execute(
				deDataLayoutSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayoutAfterUpdate = updateDataLayout(
			user2, _group, deDataLayout);

		Assert.assertEquals(
			deDataLayout.getDEDataLayoutId(),
			deDataLayoutAfterUpdate.getDEDataLayoutId());
	}

	@Test
	public void testDefinePermissionToAllowViewDataLayout() throws Exception {
		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayoutExpected = insertDataLayout(
			_adminUser, _group);

		User user2 = UserTestUtil.addGroupUser(
			_group, RoleConstants.ORGANIZATION_USER);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest2 =
					DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataLayoutExpected.getDEDataLayoutId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowView(
					).build();

			_deDataLayoutService.execute(
				deDataLayoutSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = getDEDataLayout(
			user2, _group, deDataLayoutExpected.getDEDataLayoutId());

		Assert.assertEquals(deDataLayoutExpected, deDataLayout);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testDeleteByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		deleteDEDataLayout(user, _group, deDataLayout.getDEDataLayoutId());
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testDeleteBySiteMember() throws Exception {
		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		deleteDEDataLayout(
			_siteMember, _group, deDataLayout.getDEDataLayoutId());
	}

	@Test
	public void testDeleteDEDataLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "description", "definition",
				_deDataDefinitionService);

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		deDataLayout.setDEDataDefinitionId(
			deDataDefinition.getDEDataDefinitionId());

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout
			).inGroup(
				_adminUser.getGroupId()
			).onBehalfOf(
				_adminUser.getUserId()
			).build();

		DEDataLayoutSaveResponse deDataLayoutSaveResponse =
			_deDataLayoutService.execute(deDataLayoutSaveRequest);

		deDataLayout.setDEDataLayoutId(
			deDataLayoutSaveResponse.getDEDataLayoutId());

		DEDataLayoutDeleteRequest deDataLayoutDeleteRequest =
			DEDataLayoutRequestBuilder.deleteBuilder(
			).byId(
				deDataLayoutSaveResponse.getDEDataLayoutId()
			).build();

		DEDataLayoutDeleteResponse deDataLayoutDeleteResponse =
			_deDataLayoutService.execute(deDataLayoutDeleteRequest);

		Assert.assertEquals(
			deDataLayout.getDEDataLayoutId(),
			deDataLayoutDeleteResponse.getDEDataLayoutId());
	}

	@Test(expected = DEDataLayoutException.class)
	public void testDeleteMultiplesDEDataLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		List<DEDataLayoutSaveResponse> deDataLayoutSaveResponses =
			new ArrayList<>();

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		for (int i = 0; i < 5; i++) {
			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, "description" + i, "definition" + i,
					_deDataDefinitionService);

			DEDataLayout deDataLayout = _createDEDataLayout(
				"layout", "this is a layout", "wizard", "en_US");

			deDataLayout.setDEDataDefinitionId(
				deDataDefinition.getDEDataDefinitionId());

			DEDataLayoutSaveRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveBuilder(
					deDataLayout
				).inGroup(
					_adminUser.getGroupId()
				).onBehalfOf(
					_adminUser.getUserId()
				).build();

			DEDataLayoutSaveResponse deDataLayoutSaveResponse =
				_deDataLayoutService.execute(deDataLayoutSaveRequest);

			deDataLayout.setDEDataLayoutId(
				deDataLayoutSaveResponse.getDEDataLayoutId());

			deDataLayoutSaveResponses.add(deDataLayoutSaveResponse);
		}

		for (DEDataLayoutSaveResponse deDataLayoutSaveResponse :
				deDataLayoutSaveResponses) {

			DEDataLayoutDeleteRequest deDataLayoutDeleteRequest =
				DEDataLayoutRequestBuilder.deleteBuilder(
				).byId(
					deDataLayoutSaveResponse.getDEDataLayoutId()
				).build();

			_deDataLayoutService.execute(deDataLayoutDeleteRequest);
		}

		DEDataLayoutGetRequest deDataLayoutGetRequest =
			DEDataLayoutRequestBuilder.getBuilder(
			).byId(
				deDataLayoutSaveResponses.get(
					0
				).getDEDataLayoutId()
			).build();

		_deDataLayoutService.execute(deDataLayoutGetRequest);
	}

	@Test(expected = DEDataLayoutException.class)
	public void testDeleteOneInCollectionDEDataLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		List<DEDataLayoutSaveResponse> deDataLayoutSaveResponses =
			new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, "description" + i, "definition" + i,
					_deDataDefinitionService);

			DEDataLayout deDataLayout = _createDEDataLayout(
				"layout", "this is a layout", "wizard", "en_US");

			deDataLayout.setDEDataDefinitionId(
				deDataDefinition.getDEDataDefinitionId());

			DEDataLayoutSaveRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveBuilder(
					deDataLayout
				).inGroup(
					_adminUser.getGroupId()
				).onBehalfOf(
					_adminUser.getUserId()
				).build();

			DEDataLayoutSaveResponse deDataLayoutSaveResponse =
				_deDataLayoutService.execute(deDataLayoutSaveRequest);

			deDataLayout.setDEDataLayoutId(
				deDataLayoutSaveResponse.getDEDataLayoutId());

			deDataLayoutSaveResponses.add(deDataLayoutSaveResponse);
		}

		DEDataLayoutDeleteRequest deDataLayoutDeleteRequest =
			DEDataLayoutRequestBuilder.deleteBuilder(
			).byId(
				deDataLayoutSaveResponses.get(
					0
				).getDEDataLayoutId()
			).build();

		_deDataLayoutService.execute(deDataLayoutDeleteRequest);

		for (DEDataLayoutSaveResponse deDataLayoutSaveResponse :
				deDataLayoutSaveResponses) {

			DEDataLayoutGetRequest deDataLayoutGetRequest =
				DEDataLayoutRequestBuilder.getBuilder(
				).byId(
					deDataLayoutSaveResponse.getDEDataLayoutId()
				).build();

			_deDataLayoutService.execute(deDataLayoutGetRequest);
		}
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testDeleteWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		deleteDEDataLayout(user, _group, deDataLayout.getDEDataLayoutId());
	}

	@Test(expected = DEDataLayoutException.class)
	public void testDoNotSaveDEDataLayoutWithInvalidDEDataDefinition()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataLayout deDataLayout1 = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		deDataLayout1.setDEDataDefinitionId(-1L);

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout1
			).inGroup(
				_adminUser.getGroupId()
			).onBehalfOf(
				_adminUser.getUserId()
			).build();

		_deDataLayoutService.execute(deDataLayoutSaveRequest);
	}

	@Test(expected = DEDataLayoutException.class)
	public void testDoNotSaveDEDataLayoutWithInvalidName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "description", "definition",
				_deDataDefinitionService);

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		deDataLayout.setDEDataDefinitionId(
			deDataDefinition.getDEDataDefinitionId());

		deDataLayout.setName(null);

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout
			).inGroup(
				_adminUser.getGroupId()
			).onBehalfOf(
				_adminUser.getUserId()
			).build();

		_deDataLayoutService.execute(deDataLayoutSaveRequest);
	}

	@Test(expected = DEDataLayoutException.class)
	public void testDoNotSaveRemovedDEDataLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "description", "definition",
				_deDataDefinitionService);

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		deDataLayout.setDEDataDefinitionId(
			deDataDefinition.getDEDataDefinitionId());

		deDataLayout.setDEDataLayoutId(-1L);

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout
			).inGroup(
				_adminUser.getGroupId()
			).onBehalfOf(
				_adminUser.getUserId()
			).build();

		_deDataLayoutService.execute(deDataLayoutSaveRequest);
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

		DEDataLayout expectedDEDataLayout = new DEDataLayout();

		try {
			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, "description", "definition",
					_deDataDefinitionService);

			expectedDEDataLayout = _createDEDataLayout(
				"layout", "this is a layout", "wizard", "en_US");

			expectedDEDataLayout.setDEDataDefinitionId(
				deDataDefinition.getDEDataDefinitionId());

			DEDataLayoutSaveRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveBuilder(
					expectedDEDataLayout
				).inGroup(
					_group.getGroupId()
				).onBehalfOf(
					_adminUser.getUserId()
				).build();

			DEDataLayoutSaveResponse deDataLayoutSaveResponse =
				_deDataLayoutService.execute(deDataLayoutSaveRequest);

			expectedDEDataLayout.setDEDataLayoutId(
				deDataLayoutSaveResponse.getDEDataLayoutId());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataLayout deDataLayout = getDEDataLayout(
			user, _group, expectedDEDataLayout.getDEDataLayoutId());

		Assert.assertEquals(expectedDEDataLayout, deDataLayout);
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

		DEDataLayout expectedDEDataLayout = new DEDataLayout();

		try {
			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, "description", "definition",
					_deDataDefinitionService);

			expectedDEDataLayout = _createDEDataLayout(
				"layout", "this is a layout", "wizard", "en_US");

			expectedDEDataLayout.setDEDataDefinitionId(
				deDataDefinition.getDEDataDefinitionId());

			DEDataLayoutSaveRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveBuilder(
					expectedDEDataLayout
				).inGroup(
					_group.getGroupId()
				).onBehalfOf(
					_adminUser.getUserId()
				).build();

			DEDataLayoutSaveResponse deDataLayoutSaveResponse =
				_deDataLayoutService.execute(deDataLayoutSaveRequest);

			expectedDEDataLayout.setDEDataLayoutId(
				deDataLayoutSaveResponse.getDEDataLayoutId());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = getDEDataLayout(
			_siteMember, _group, expectedDEDataLayout.getDEDataLayoutId());

		Assert.assertEquals(expectedDEDataLayout, deDataLayout);
	}

	@Test
	public void testGetDataLayout() throws Exception {
		DEDataLayout deDataLayoutExpected = insertDataLayout(
			_adminUser, _group);

		DEDataLayout deDataLayout = getDEDataLayout(
			_adminUser, _group, deDataLayoutExpected.getDEDataLayoutId());

		Assert.assertEquals(deDataLayoutExpected, deDataLayout);
	}

	@Test(expected = DEDataLayoutException.NoSuchDataLayout.class)
	public void testGetNoSuchDataLayout() throws Exception {
		getDEDataLayout(_adminUser, _group, 1);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testGetWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		getDEDataLayout(user, _group, deDataLayout.getDEDataLayoutId());
	}

	@Test(expected = DEDataLayoutException.PrincipalException.class)
	public void testGrantDeletePermissionToGuestUser() throws Exception {
		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					_adminUser.getUserId(), _group.getGroupId(),
					deDataLayout.getDEDataLayoutId(),
					new String[] {RoleConstants.GUEST}
				).allowDelete(
				).build();

			_deDataLayoutService.execute(deDataLayoutSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGrantDeletePermissionToSiteMember() throws Exception {
		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					_adminUser.getUserId(), _group.getGroupId(),
					deDataLayout.getDEDataLayoutId(),
					new String[] {RoleConstants.SITE_MEMBER}
				).allowDelete(
				).build();

			_deDataLayoutService.execute(deDataLayoutSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		deleteDEDataLayout(
			_siteMember, _group, deDataLayout.getDEDataLayoutId());
	}

	@Test(expected = DEDataLayoutException.PrincipalException.class)
	public void testGrantUpdatePermissionToGuestUser() throws Exception {
		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					_adminUser.getUserId(), _group.getGroupId(),
					deDataLayout.getDEDataLayoutId(),
					new String[] {RoleConstants.GUEST}
				).allowUpdate(
				).build();

			_deDataLayoutService.execute(deDataLayoutSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGrantUpdatePermissionToSiteMember() throws Exception {
		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					_adminUser.getUserId(), _group.getGroupId(),
					deDataLayout.getDEDataLayoutId(),
					new String[] {RoleConstants.SITE_MEMBER}
				).allowUpdate(
				).build();

			_deDataLayoutService.execute(deDataLayoutSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayoutAfterUpdate = updateDataLayout(
			_siteMember, _group, deDataLayout);

		Assert.assertEquals(deDataLayout, deDataLayoutAfterUpdate);
	}

	@Test
	public void testGrantViewPermissionToGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataLayout expectedDEDataLayout = insertDataLayout(
			_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					_adminUser.getUserId(), _group.getGroupId(),
					expectedDEDataLayout.getDEDataLayoutId(),
					new String[] {RoleConstants.GUEST}
				).allowView(
				).build();

			_deDataLayoutService.execute(deDataLayoutSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = getDEDataLayout(
			user, _group, expectedDEDataLayout.getDEDataLayoutId());

		Assert.assertEquals(expectedDEDataLayout, deDataLayout);
	}

	@Test
	public void testGrantViewPermissionToSiteMember() throws Exception {
		DEDataLayout expectedDEDataLayout = insertDataLayout(
			_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					_adminUser.getUserId(), _group.getGroupId(),
					expectedDEDataLayout.getDEDataLayoutId(),
					new String[] {RoleConstants.SITE_MEMBER}
				).allowView(
				).build();

			_deDataLayoutService.execute(deDataLayoutSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = getDEDataLayout(
			_siteMember, _group, expectedDEDataLayout.getDEDataLayoutId());

		Assert.assertEquals(expectedDEDataLayout, deDataLayout);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testInsertByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		insertDataLayout(user, _group);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testInsertBySiteMember() throws Exception {
		insertDataLayout(_siteMember, _group);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testInsertWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		insertDataLayout(user, _group);
	}

	@Test
	public void testListDEDataLayout() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			saveDataLayout(_group, _adminUser, "layout", "this is a layout");
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataLayoutListRequest deDataLayoutListRequest =
			DEDataLayoutRequestBuilder.listBuilder(
			).inGroup(
				_group.getGroupId()
			).build();

		DEDataLayoutListResponse deDataLayoutListResponse =
			_deDataLayoutService.execute(deDataLayoutListRequest);

		List<DEDataLayout> deDataLayouts =
			deDataLayoutListResponse.getDEDataLayouts();

		Assert.assertEquals(deDataLayouts.toString(), 5, deDataLayouts.size());
	}

	@Test
	public void testListDEDataLayoutInvalidGroup() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			saveDataLayout(_group, _adminUser, "layout", "this is a layout");
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataLayoutListRequest deDataLayoutListRequest =
			DEDataLayoutRequestBuilder.listBuilder(
			).inGroup(
				-1
			).build();

		DEDataLayoutListResponse deDataLayoutListResponse =
			_deDataLayoutService.execute(deDataLayoutListRequest);

		List<DEDataLayout> deDataLayouts =
			deDataLayoutListResponse.getDEDataLayouts();

		Assert.assertEquals(deDataLayouts.toString(), 0, deDataLayouts.size());
	}

	@Test
	public void testListDEDataLayoutWithNoData() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataLayoutListRequest deDataLayoutListRequest =
			DEDataLayoutRequestBuilder.listBuilder(
			).inGroup(
				_group.getGroupId()
			).build();

		DEDataLayoutListResponse deDataLayoutListResponse =
			_deDataLayoutService.execute(deDataLayoutListRequest);

		List<DEDataLayout> deDataLayouts =
			deDataLayoutListResponse.getDEDataLayouts();

		Assert.assertEquals(deDataLayouts.toString(), 0, deDataLayouts.size());
	}

	@Test
	public void testListDEDataLayoutWithNoGroup() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			saveDataLayout(_group, _adminUser, "layout", "this is a layout");
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataLayoutListRequest deDataLayoutListRequest =
			DEDataLayoutRequestBuilder.listBuilder(
			).build();

		DEDataLayoutListResponse deDataLayoutListResponse =
			_deDataLayoutService.execute(deDataLayoutListRequest);

		List<DEDataLayout> deDataLayouts =
			deDataLayoutListResponse.getDEDataLayouts();

		Assert.assertEquals(deDataLayouts.toString(), 0, deDataLayouts.size());
	}

	@Test
	public void testListDEDataLayoutWithPagination() throws Exception {
		int total = 5;

		for (int i = 0; i < total; i++) {
			saveDataLayout(_group, _adminUser, "layout", "this is a layout");
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataLayoutListRequest deDataLayoutListRequest =
			DEDataLayoutRequestBuilder.listBuilder(
			).startingAt(
				4
			).endingAt(
				5
			).inGroup(
				_group.getGroupId()
			).build();

		DEDataLayoutListResponse deDataLayoutListResponse =
			_deDataLayoutService.execute(deDataLayoutListRequest);

		List<DEDataLayout> deDataLayouts =
			deDataLayoutListResponse.getDEDataLayouts();

		Assert.assertEquals(deDataLayouts.toString(), 1, deDataLayouts.size());
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testRevokeAddDataLayoutPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role.getName()}
					).allowAddDataLayout(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout = insertDataLayout(user, _group);

		Assert.assertTrue(deDataLayout.getDEDataLayoutId() > 0);

		deleteDEDataLayoutPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			true, false, new String[] {role.getName()});

		insertDataLayout(user, _group);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testRevokeDefinePermissions() throws Exception {
		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

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
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest =
					DEDataLayoutRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataLayoutService.execute(deDataLayoutSavePermissionsRequest);
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
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest =
					DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataLayout.getDEDataLayoutId(),
						new String[] {role2.getName()}
					).allowUpdate(
					).build();

			_deDataLayoutService.execute(
				deDataLayoutSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayoutAfterUpdate = updateDataLayout(
			user2, _group, deDataLayout);

		Assert.assertEquals(
			deDataLayout.getDEDataLayoutId(),
			deDataLayoutAfterUpdate.getDEDataLayoutId());

		deleteDEDataLayoutPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			false, true, new String[] {role1.getName()});

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext3 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext3);

		try {
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest =
					DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						user1.getUserId(), _group.getGroupId(),
						deDataLayout.getDEDataDefinitionId(),
						new String[] {role2.getName()}
					).allowUpdate(
					).build();

			_deDataLayoutService.execute(
				deDataLayoutSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testRevokeDeleteDataLayoutPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest2 =
					DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataLayout.getDEDataLayoutId(),
						new String[] {role.getName()}
					).allowDelete(
					).build();

			_deDataLayoutService.execute(
				deDataLayoutSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		deleteDEDataLayoutModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataLayout.getDEDataLayoutId(), true, false, false,
			new String[] {role.getName()});

		deleteDEDataLayout(user, _group, deDataLayout.getDEDataLayoutId());
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testRevokeModelPermissionsWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		deleteDEDataLayoutModelPermissions(
			TestPropsValues.getCompanyId(), user, _group.getGroupId(),
			deDataLayout.getDEDataLayoutId(), false, true, false,
			new String[] {RoleConstants.ORGANIZATION_USER});
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testRevokePermissionsWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		deleteDEDataLayoutPermissions(
			TestPropsValues.getCompanyId(), user, _group.getGroupId(), true,
			false, new String[] {RoleConstants.ORGANIZATION_USER});
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testRevokeUpdateDataLayoutPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest2 =
					DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataLayout.getDEDataLayoutId(),
						new String[] {role.getName()}
					).allowUpdate(
					).build();

			_deDataLayoutService.execute(
				deDataLayoutSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		updateDataLayout(user, _group, deDataLayout);

		updateDataLayout(_adminUser, _group, deDataLayout);

		deleteDEDataLayoutModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataLayout.getDEDataLayoutId(), false, true, false,
			new String[] {role.getName()});

		updateDataLayout(user, _group, deDataLayout);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testRevokeViewDataLayoutPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest2 =
					DEDataLayoutRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataLayout.getDEDataLayoutId(),
						new String[] {role.getName()}
					).allowView(
					).build();

			_deDataLayoutService.execute(
				deDataLayoutSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataLayout deDataLayout2 = getDEDataLayout(
			user, _group, deDataLayout.getDEDataLayoutId());

		Assert.assertTrue(deDataLayout2.getDEDataLayoutId() > 0);

		deleteDEDataLayoutModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataLayout.getDEDataLayoutId(), false, false, true,
			new String[] {role.getName()});

		getDEDataLayout(user, _group, deDataLayout.getDEDataLayoutId());
	}

	@Test(expected = DEDataLayoutException.class)
	public void testSaveDEDataLayoutWithNoDataDefinition() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout
			).inGroup(
				_adminUser.getGroupId()
			).onBehalfOf(
				_adminUser.getUserId()
			).build();

		_deDataLayoutService.execute(deDataLayoutSaveRequest);
	}

	@Test(expected = DEDataLayoutException.class)
	public void testSaveDEDataLayoutWithNonexistentUser() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, "description", "definition",
				_deDataDefinitionService);

		DEDataLayout deDataLayout = _createDEDataLayout(
			"layout", "this is a layout", "wizard", "en_US");

		deDataLayout.setDEDataDefinitionId(
			deDataDefinition.getDEDataDefinitionId());

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout
			).inGroup(
				_adminUser.getGroupId()
			).onBehalfOf(
				-1L
			).build();

		_deDataLayoutService.execute(deDataLayoutSaveRequest);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testUpdateDataLayoutByGuest() throws Exception {
		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.BRAZIL, "Descrição");

		deDataLayout.setDescription(descriptionMap);

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		deDataLayout = insertDataLayout(user, _group);
	}

	@Test(expected = DEDataLayoutException.MustHavePermission.class)
	public void testUpdateDataLayoutBySiteMember() throws Exception {
		DEDataLayout deDataLayout = insertDataLayout(_adminUser, _group);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.BRAZIL, "Descrição");

		deDataLayout.setDescription(descriptionMap);

		deDataLayout = insertDataLayout(_siteMember, _group);
	}

	@Test
	public void testUpdateDEDataLayout() throws Exception {
		DEDataLayout deDataLayout = saveDataLayout(
			_group, _adminUser, "layout", "this is a layout");

		deDataLayout.setPaginationMode("pagination");

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout
			).inGroup(
				_adminUser.getGroupId()
			).onBehalfOf(
				_adminUser.getUserId()
			).build();

		DEDataLayoutSaveResponse deDataLayoutSaveResponse =
			_deDataLayoutService.execute(deDataLayoutSaveRequest);

		DEDataLayoutGetRequest deDataLayoutGetRequest =
			DEDataLayoutRequestBuilder.getBuilder(
			).byId(
				deDataLayoutSaveResponse.getDEDataLayoutId()
			).build();

		DEDataLayoutGetResponse deDataLayoutGetResponse =
			_deDataLayoutService.execute(deDataLayoutGetRequest);

		Assert.assertEquals(
			deDataLayout, deDataLayoutGetResponse.getDEDataLayout());
	}

	protected int countDEDataLayouts(Group group) throws Exception {
		DEDataLayoutCountRequest deDataLayoutCountRequest =
			DEDataLayoutRequestBuilder.countBuilder(
			).inGroup(
				group.getGroupId()
			).build();

		DEDataLayoutCountResponse deDataLayoutCountResponse =
			_deDataLayoutService.execute(deDataLayoutCountRequest);

		return deDataLayoutCountResponse.getTotal();
	}

	protected void deleteDEDataLayout(
			User user, Group group, long deDataLayoutId)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutDeleteRequest deDataLayoutDeleteRequest =
				DEDataLayoutRequestBuilder.deleteBuilder(
				).byId(
					deDataLayoutId
				).build();

			_deDataLayoutService.execute(deDataLayoutDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected void deleteDEDataLayoutModelPermissions(
			long companyId, User user, long groupId, long deDataLayoutId,
			boolean revokeDelete, boolean revokeUpdate, boolean revokeView,
			String[] roleNames)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutDeleteModelPermissionsRequest.Builder builder =
				DEDataLayoutRequestBuilder.deleteModelPermissionsBuilder(
					companyId, groupId, deDataLayoutId, revokeDelete,
					revokeUpdate, revokeView, roleNames);

			DEDataLayoutDeleteModelPermissionsRequest
				deDataLayoutDeleteModelPermissionsRequest = builder.build();

			_deDataLayoutService.execute(
				deDataLayoutDeleteModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected void deleteDEDataLayoutPermissions(
			long companyId, User user, long groupId,
			boolean revokeAddDataLayout, boolean revokeDefinePermissions,
			String[] roleNames)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutDeletePermissionsRequest.Builder builder =
				DEDataLayoutRequestBuilder.deletePermissionsBuilder(
					companyId, groupId, revokeAddDataLayout,
					revokeDefinePermissions, roleNames);

			DEDataLayoutDeletePermissionsRequest
				deDataLayoutDeletePermissionsRequest = builder.build();

			_deDataLayoutService.execute(deDataLayoutDeletePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected DEDataLayout getDEDataLayout(
			User user, Group group, long deDataLayoutId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		try {
			DEDataLayoutGetRequest deDataLayoutGetRequest =
				DEDataLayoutRequestBuilder.getBuilder(
				).byId(
					deDataLayoutId
				).build();

			DEDataLayoutGetResponse deDataLayoutGetResponse =
				_deDataLayoutService.execute(deDataLayoutGetRequest);

			return deDataLayoutGetResponse.getDEDataLayout();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected DEDataLayout insertDataLayout(User user, Group group)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, "description", "definition",
					_deDataDefinitionService);

			DEDataLayout deDataLayout = _createDEDataLayout(
				"layout", "this is a layout", "wizard", "en_US");

			deDataLayout.setDEDataDefinitionId(
				deDataDefinition.getDEDataDefinitionId());

			DEDataLayoutSaveRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveBuilder(
					deDataLayout
				).inGroup(
					group.getGroupId()
				).onBehalfOf(
					user.getUserId()
				).build();

			DEDataLayoutSaveResponse deDataLayoutSaveResponse =
				_deDataLayoutService.execute(deDataLayoutSaveRequest);

			deDataLayout.setDEDataLayoutId(
				deDataLayoutSaveResponse.getDEDataLayoutId());

			return deDataLayout;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected DEDataLayout saveDataLayout(
			Group group, User user, String nameLayout, String descriptionLayout)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, user.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				user, group, "description", "definition",
				_deDataDefinitionService);

		DEDataLayout deDataLayout = _createDEDataLayout(
			nameLayout, descriptionLayout, "wizard", "en_US");

		deDataLayout.setDEDataDefinitionId(
			deDataDefinition.getDEDataDefinitionId());

		DEDataLayoutSaveRequest deDataLayoutSaveRequest =
			DEDataLayoutRequestBuilder.saveBuilder(
				deDataLayout
			).inGroup(
				group.getGroupId()
			).onBehalfOf(
				user.getUserId()
			).build();

		DEDataLayoutSaveResponse deDataLayoutSaveResponse =
			_deDataLayoutService.execute(deDataLayoutSaveRequest);

		deDataLayout.setDEDataLayoutId(
			deDataLayoutSaveResponse.getDEDataLayoutId());

		return deDataLayout;
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	protected DEDataLayout updateDataLayout(
			User user, Group group, DEDataLayout dataLayout)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataLayoutSaveRequest deDataLayoutSaveRequest =
				DEDataLayoutRequestBuilder.saveBuilder(
					dataLayout
				).inGroup(
					group.getGroupId()
				).onBehalfOf(
					user.getUserId()
				).build();

			DEDataLayoutSaveResponse deDataLayoutSaveResponse =
				_deDataLayoutService.execute(deDataLayoutSaveRequest);

			dataLayout.setDEDataLayoutId(
				deDataLayoutSaveResponse.getDEDataLayoutId());

			return dataLayout;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private DEDataLayout _createDEDataLayout(
		String name, String description, String paginationMode,
		String languageId) {

		DEDataLayoutColumn deDataLayoutColumn = _createDEDataLayoutColumn(
			12, "field");

		Queue<DEDataLayoutColumn> deDataLayoutColumns = new ArrayDeque<>();

		deDataLayoutColumns.add(deDataLayoutColumn);

		DEDataLayoutRow deDataLayoutRow = _createDEDataLayoutRow(
			deDataLayoutColumns);

		Queue<DEDataLayoutRow> deDataLayoutRows = new ArrayDeque<>();

		deDataLayoutRows.add(deDataLayoutRow);

		DEDataLayoutPage deDataLayoutPage = _createDEDataLayoutPage(
			StringPool.BLANK, "Page", deDataLayoutRows);

		Queue<DEDataLayoutPage> deDataLayoutPages = new ArrayDeque<>();

		deDataLayoutPages.add(deDataLayoutPage);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.US, description);

		DEDataLayout deDataLayout = new DEDataLayout();

		deDataLayout.setName(nameMap);
		deDataLayout.setDescription(descriptionMap);
		deDataLayout.setDEDataLayoutPages(deDataLayoutPages);
		deDataLayout.setPaginationMode(paginationMode);
		deDataLayout.setDefaultLanguageId(languageId);

		return deDataLayout;
	}

	private DEDataLayoutColumn _createDEDataLayoutColumn(
		int size, String fieldName) {

		DEDataLayoutColumn deDataLayoutColumn = new DEDataLayoutColumn();

		deDataLayoutColumn.setColumnSize(size);

		List<String> fieldsName = new ArrayList<>();

		fieldsName.add(fieldName);

		deDataLayoutColumn.setFieldsName(fieldsName);

		return deDataLayoutColumn;
	}

	private DEDataLayoutPage _createDEDataLayoutPage(
		String description, String title,
		Queue<DEDataLayoutRow> deDataLayoutRows) {

		DEDataLayoutPage deDataLayoutPage = new DEDataLayoutPage();

		Map<String, String> titleMap = new HashMap<>();

		titleMap.put("en_US", title);

		Map<String, String> descriptionMap = new HashMap<>();

		descriptionMap.put("en_US", description);

		deDataLayoutPage.setTitle(titleMap);
		deDataLayoutPage.setDescription(descriptionMap);
		deDataLayoutPage.setDEDataLayoutRows(deDataLayoutRows);

		return deDataLayoutPage;
	}

	private DEDataLayoutRow _createDEDataLayoutRow(
		Queue<DEDataLayoutColumn> deDataLayoutColumns) {

		DEDataLayoutRow deDataLayoutRow = new DEDataLayoutRow();

		deDataLayoutRow.setDEDataLayoutColumns(deDataLayoutColumns);

		return deDataLayoutRow;
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@Inject(type = DEDataLayoutService.class)
	private DEDataLayoutService _deDataLayoutService;

	private Group _group;
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteMember;

}