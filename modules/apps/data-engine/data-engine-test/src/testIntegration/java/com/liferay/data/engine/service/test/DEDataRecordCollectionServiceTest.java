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
import com.liferay.data.engine.constants.DEActionKeys;
import com.liferay.data.engine.constants.DEDataDefinitionRuleConstants;
import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionRule;
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionGetRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionGetRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionListRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionListResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class DEDataRecordCollectionServiceTest {

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
	public void testAddDataRecordCollectionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
						new String[] {role.getName()}
					).allowAddDataRecordCollection(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				user, _group, deDataDefinition, _deDataRecordCollectionService);

		Assert.assertTrue(
			deDataRecordCollection.getDEDataRecordCollectionId() > 0);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testAddDataRecordCollectionPermissionByGuestUser()
		throws Exception {

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowAddDataRecordCollection(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testAddDataRecordCollectionPermissionBySiteMember()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _siteMember.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowAddDataRecordCollection(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testAddDataRecordCollectionPermissionToGuestUser()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
						new String[] {RoleConstants.GUEST}
					).allowAddDataRecordCollection(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDefinePermissionsByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDefinePermissionsBySiteMember() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _siteMember.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.ORGANIZATION_USER}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testDefinePermissionsToGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {RoleConstants.GUEST}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testDefinePermissionToAllowAddDataRecord() throws Exception {
		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user2 = UserTestUtil.addGroupUser(
			_group, RoleConstants.ORGANIZATION_USER);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.ORGANIZATION_USER}
						).allowAddDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.insertDEDataRecord(
			user2, _group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test
	public void testDefinePermissionToAllowAddDataRecordCollection()
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
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
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
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role2.getName()}
					).allowAddDataRecordCollection(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				user2, _group, deDataDefinition,
				_deDataRecordCollectionService);

		Assert.assertTrue(
			deDataRecordCollection.getDEDataRecordCollectionId() > 0);
	}

	@Test
	public void testDefinePermissionToAllowDeleteDataRecord() throws Exception {
		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user2 = UserTestUtil.addGroupUser(
			_group, RoleConstants.ORGANIZATION_USER);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.ORGANIZATION_USER}
						).allowDeleteDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecord(
			user2, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testDefinePermissionToAllowDeleteDataRecordCollection()
		throws Exception {

		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user2 = UserTestUtil.addGroupUser(
			_group, RoleConstants.ORGANIZATION_USER);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.ORGANIZATION_USER}
						).allowDelete(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			user2, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testDefinePermissionToAllowUpdateDataRecord() throws Exception {
		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		User user2 = UserTestUtil.addGroupUser(
			_group, RoleConstants.ORGANIZATION_USER);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecord.getDEDataRecordCollectionId(),
							new String[] {RoleConstants.ORGANIZATION_USER}
						).allowUpdateDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord deDataRecordAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecord(
				user2, _group, deDataRecord, _deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecord.getDEDataRecordId(),
			deDataRecordAfterUpdate.getDEDataRecordId());
	}

	@Test
	public void testDefinePermissionToAllowUpdateDataRecordCollection()
		throws Exception {

		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user2 = UserTestUtil.addGroupUser(
			_group, RoleConstants.ORGANIZATION_USER);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.ORGANIZATION_USER}
						).allowUpdate(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollectionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecordCollection(
				user2, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollection.getDEDataRecordCollectionId(),
			deDataRecordCollectionAfterUpdate.getDEDataRecordCollectionId());
	}

	@Test
	public void testDefinePermissionToAllowViewDataRecord() throws Exception {
		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user2 = UserTestUtil.addGroupUser(
			_group, RoleConstants.ORGANIZATION_USER);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.ORGANIZATION_USER}
						).allowViewDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord expectedDEDataRecord =
			DEDataEngineTestUtil.insertDEDataRecord(
				_adminUser, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.getDEDataRecord(
			user2, _group, expectedDEDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(expectedDEDataRecord, deDataRecord);
	}

	@Test
	public void testDefinePermissionToAllowViewDataRecordCollection()
		throws Exception {

		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user1 = UserTestUtil.addGroupUser(_group, role1.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollectionExpected =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user2 = UserTestUtil.addGroupUser(
			_group, RoleConstants.ORGANIZATION_USER);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext2);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecordCollectionExpected.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.ORGANIZATION_USER}
						).allowView(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.getDEDataRecordCollection(
				user2, _group.getGroupId(),
				deDataRecordCollectionExpected.getDEDataRecordCollectionId(),
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollectionExpected, deDataRecordCollection);
	}

	@Test
	public void testDeleteDataRecord() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		long deDataRecordId = DEDataEngineTestUtil.deleteDEDataRecord(
			_adminUser, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(deDataRecord.getDEDataRecordId(), deDataRecordId);
	}

	@Test
	public void testDeleteDataRecordAndKeepTheOther() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord1 = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		DEDataRecord deDataRecord2 = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecord(
			_adminUser, _group, deDataRecord1.getDEDataRecordId(),
			_deDataRecordCollectionService);

		DEDataRecord deDataRecord2AfterDelete =
			DEDataEngineTestUtil.getDEDataRecord(
				_adminUser, _group, deDataRecord2.getDEDataRecordId(),
				_deDataRecordCollectionService);

		Assert.assertEquals(deDataRecord2, deDataRecord2AfterDelete);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDeleteDataRecordByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecord(
			user, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDeleteDataRecordBySiteMember() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecord(
			_siteMember, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testDeleteDataRecordCollection() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.NoSuchDataRecord.class)
	public void testDeleteDataRecordCollectionAndCheckIfDataRecordsAreDeleted()
		throws Exception {

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.getDEDataRecord(
			_adminUser, _group, deDataRecord.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testDeleteDataRecordCollectionAndKeepTheOther()
		throws Exception {

		DEDataRecordCollection deDataRecordCollection1 =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecordCollection deDataRecordCollection2 =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_adminUser, _group.getGroupId(),
			deDataRecordCollection1.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_adminUser, _group.getGroupId(),
			deDataRecordCollection2.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDeleteDataRecordCollectionByGuest() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, deDataDefinition,
				_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			user, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDeleteDataRecordCollectionBySiteMember() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, deDataDefinition,
				_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_siteMember, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testDeleteDataRecordCollectionWithDataRecordAssociated()
		throws Exception {

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test(
		expected = DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testDeleteDataRecordCollectionWithNoIDParameter()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionDeleteRequest
				deDataRecordCollectionDeleteRequest =
					DEDataRecordCollectionRequestBuilder.
						deleteBuilder().build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDeleteDataRecordCollectionWithNoPermission()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			user, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDeleteDataRecordWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecord(
			user, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.NoSuchDataRecord.class)
	public void testDeleteNoSuchDataRecord() throws Exception {
		DEDataEngineTestUtil.deleteDEDataRecord(
			_siteMember, _group, 1, _deDataRecordCollectionService);
	}

	@Test(
		expected = DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testDeleteNoSuchDataRecordCollection() throws Exception {
		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_adminUser, _group.getGroupId(), 1, _deDataRecordCollectionService);
	}

	@Test(
		expected = DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testDeleteNoSuchDataRecordCollection2() throws Exception {
		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_adminUser, _group.getGroupId(), Long.MAX_VALUE,
			_deDataRecordCollectionService);
	}

	@Test
	public void testGetDataRecord() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.getDEDataRecord(
			_adminUser, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testGetDataRecordByGuestUser() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getUserId());

		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.GUEST, DEActionKeys.VIEW_DATA_RECORD);

		serviceContext.setModelPermissions(modelPermissions);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		try {
			deDataRecordCollection = new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");
			deDataRecordCollection.addDescription(
				LocaleUtil.US, "Data record list description");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Descrição da lista de registro de dados");
			deDataRecordCollection.setDEDataDefinition(deDataDefinition);

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						_adminUser.getUserId()
					).inGroup(
						_group.getGroupId()
					).build();

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					_deDataRecordCollectionService.execute(
						deDataRecordCollectionSaveRequest);

			deDataRecordCollection =
				deDataRecordCollectionSaveResponse.getDEDataRecordCollection();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord expectedDEDataRecord =
			DEDataEngineTestUtil.insertDEDataRecord(
				_adminUser, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.getDEDataRecord(
			user, _group, expectedDEDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(expectedDEDataRecord, deDataRecord);
	}

	@Test
	public void testGetDataRecordBySiteMember() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getUserId());

		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.SITE_MEMBER, DEActionKeys.VIEW_DATA_RECORD);

		serviceContext.setModelPermissions(modelPermissions);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		try {
			deDataRecordCollection = new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");
			deDataRecordCollection.addDescription(
				LocaleUtil.US, "Data record list description");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Descrição da lista de registro de dados");
			deDataRecordCollection.setDEDataDefinition(deDataDefinition);

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						_adminUser.getUserId()
					).inGroup(
						_group.getGroupId()
					).build();

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					_deDataRecordCollectionService.execute(
						deDataRecordCollectionSaveRequest);

			deDataRecordCollection =
				deDataRecordCollectionSaveResponse.getDEDataRecordCollection();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord expectedDEDataRecord =
			DEDataEngineTestUtil.insertDEDataRecord(
				_adminUser, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		User user = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_MEMBER);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.getDEDataRecord(
			user, _group, expectedDEDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(expectedDEDataRecord, deDataRecord);
	}

	@Test
	public void testGetDataRecordCollection() throws Exception {
		DEDataRecordCollection deDataRecordCollectionExpected =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.getDEDataRecordCollection(
				_adminUser, _group.getGroupId(),
				deDataRecordCollectionExpected.getDEDataRecordCollectionId(),
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollectionExpected, deDataRecordCollection);
	}

	@Test
	public void testGetDataRecordCollectionByGuestUser() throws Exception {
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

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollectionExpected =
			new DEDataRecordCollection();

		deDataRecordCollectionExpected.addName(
			LocaleUtil.US, "Data record list");
		deDataRecordCollectionExpected.addDescription(
			LocaleUtil.BRAZIL, "Lista de registro de dados");
		deDataRecordCollectionExpected.setDEDataDefinition(deDataDefinition);

		try {
			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollectionExpected
					).onBehalfOf(
						_adminUser.getUserId()
					).inGroup(
						_group.getGroupId()
					).build();

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					_deDataRecordCollectionService.execute(
						deDataRecordCollectionSaveRequest);

			deDataRecordCollectionExpected =
				deDataRecordCollectionSaveResponse.getDEDataRecordCollection();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.getDEDataRecordCollection(
				user, _group.getGroupId(),
				deDataRecordCollectionExpected.getDEDataRecordCollectionId(),
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollectionExpected, deDataRecordCollection);
	}

	@Test
	public void testGetDataRecordCollectionBySiteMember() throws Exception {
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

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollectionExpected =
			new DEDataRecordCollection();

		deDataRecordCollectionExpected.addName(
			LocaleUtil.US, "Data record list");
		deDataRecordCollectionExpected.addDescription(
			LocaleUtil.BRAZIL, "Lista de registro de dados");
		deDataRecordCollectionExpected.setDEDataDefinition(deDataDefinition);

		try {
			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollectionExpected
					).onBehalfOf(
						_adminUser.getUserId()
					).inGroup(
						_group.getGroupId()
					).build();

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					_deDataRecordCollectionService.execute(
						deDataRecordCollectionSaveRequest);

			deDataRecordCollectionExpected =
				deDataRecordCollectionSaveResponse.getDEDataRecordCollection();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.getDEDataRecordCollection(
				_siteMember, _group.getGroupId(),
				deDataRecordCollectionExpected.getDEDataRecordCollectionId(),
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollectionExpected, deDataRecordCollection);
	}

	@Test(
		expected = DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testGetDataRecordCollectionWithNoId() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionGetRequest deDataRecordCollectionGetRequest =
				DEDataRecordCollectionRequestBuilder.getBuilder().build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionGetRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(
		expected = DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testGetDataRecordCollectionWithNonexistingId()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionGetRequest deDataRecordCollectionGetRequest =
				DEDataRecordCollectionRequestBuilder.getBuilder(
				).byId(
					1
				).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionGetRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.NoSuchDataRecord.class)
	public void testGetDataRecordWithNonexistingId() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		try {
			DEDataRecordCollectionGetRecordRequest
				deDataRecordCollectionGetRecordRequest =
					DEDataRecordCollectionRequestBuilder.getRecordBuilder(
						1
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionGetRecordRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testGetDataRecordWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.getDEDataRecord(
			user, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testGrantAddDataRecordPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowAddDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.insertDEDataRecord(
			user, _group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testGrantAddDataRecordPermissionToGuestUser() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.GUEST}
						).allowAddDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testGrantDeleteDataRecordCollectionPermissionToGuestUser()
		throws Exception {

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.GUEST}
						).allowDelete(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGrantDeleteDataRecordCollectionPermissionToSiteMember()
		throws Exception {

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.SITE_MEMBER}
						).allowDelete(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_siteMember, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testGrantDeleteDataRecordPermissionToGuestUser()
		throws Exception {

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecord.getDEDataRecordCollectionId(),
							new String[] {RoleConstants.GUEST}
						).allowDeleteDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGrantDeleteDataRecordPermissionToSiteMember()
		throws Exception {

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecord.getDEDataRecordCollectionId(),
							new String[] {RoleConstants.SITE_MEMBER}
						).allowDeleteDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		long deDataRecordId = DEDataEngineTestUtil.deleteDEDataRecord(
			_siteMember, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(deDataRecord.getDEDataRecordId(), deDataRecordId);
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testGrantUpdateDataRecordCollectionPermissionToGuestUser()
		throws Exception {

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.GUEST}
						).allowUpdate(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGrantUpdateDataRecordCollectionPermissionToSiteMember()
		throws Exception {

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.SITE_MEMBER}
						).allowUpdate(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollectionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecordCollection(
				_siteMember, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollection, deDataRecordCollectionAfterUpdate);
	}

	@Test
	public void testGrantUpdateDataRecordPermission() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecord.getDEDataRecordCollectionId(),
							new String[] {RoleConstants.SITE_MEMBER}
						).allowUpdateDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay2");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecord deDataRecordAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecord(
				_siteMember, _group, deDataRecord,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecord.getDEDataRecordId(),
			deDataRecordAfterUpdate.getDEDataRecordId());
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testGrantUpdateDataRecordPermissionToGuestUser()
		throws Exception {

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.GUEST}
						).allowUpdateDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGrantViewDataRecordCollectionPermissionToSiteMember()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollectionExpected =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollectionExpected.
								getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowView(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.getDEDataRecordCollection(
				user, _group.getGroupId(),
				deDataRecordCollectionExpected.getDEDataRecordCollectionId(),
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollectionExpected, deDataRecordCollection);
	}

	@Test
	public void testGrantViewDataRecordPermissionToGuestUser()
		throws Exception {

		DEDataRecord expectedDEDataRecord =
			DEDataEngineTestUtil.insertDEDataRecord(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							expectedDEDataRecord.getDEDataRecordCollectionId(),
							new String[] {RoleConstants.GUEST}
						).allowViewDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.getDEDataRecord(
			user, _group, expectedDEDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(expectedDEDataRecord, deDataRecord);
	}

	@Test
	public void testGrantViewDataRecordPermissionToSiteMember()
		throws Exception {

		DEDataRecord expectedDEDataRecord =
			DEDataEngineTestUtil.insertDEDataRecord(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							expectedDEDataRecord.getDEDataRecordCollectionId(),
							new String[] {RoleConstants.SITE_MEMBER}
						).allowViewDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord deDataRecord = DEDataEngineTestUtil.getDEDataRecord(
			_siteMember, _group, expectedDEDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(expectedDEDataRecord, deDataRecord);
	}

	@Test
	public void testInsertDataRecord() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		Assert.assertTrue(deDataRecord.getDEDataRecordId() > 0);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertDataRecordByGuest() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataEngineTestUtil.insertDEDataRecord(
			user, _group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test
	public void testInsertDataRecordCollection() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		Assert.assertTrue(
			deDataRecordCollection.getDEDataRecordCollectionId() > 0);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertDataRecordCollectionByGuestUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			user, _group, deDataDefinition, _deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertDataRecordCollectionBySiteMember() throws Exception {
		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			_siteMember, _group, deDataDefinition,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testInsertDataRecordCollectionWithBadRequest()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						_adminUser.getUserId()
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testInsertDataRecordCollectionWithBadRequest2()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).inGroup(
						_group.getGroupId()
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testInsertDataRecordCollectionWithNoDataDefinition()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						_adminUser.getUserId()
					).inGroup(
						_group.getGroupId()
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testInsertDataRecordCollectionWithNonexistentGroup()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, _deDataDefinitionService);

			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");
			deDataRecordCollection.setDEDataDefinition(deDataDefinition);

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						_adminUser.getUserId()
					).inGroup(
						1
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testInsertDataRecordCollectionWithNonexistentUser()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, _deDataDefinitionService);

			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");
			deDataRecordCollection.setDEDataDefinition(deDataDefinition);

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						1
					).inGroup(
						_group.getGroupId()
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertDataRecordCollectionWithNoPermission()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			user, _group, deDataDefinition, _deDataRecordCollectionService);
	}

	@Test(
		expected = DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testInsertDataRecordWithNoDataCollection() throws Exception {
		DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, (DEDataRecordCollection)null,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.AddDataRecord.class)
	public void testInsertDataRecordWithNoGroup() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = new DEDataRecord();

		deDataRecord.setDEDataRecordCollection(deDataRecordCollection);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecordCollectionSaveRecordRequest
			deDataRecordCollectionSaveRecordRequest =
				DEDataRecordCollectionRequestBuilder.saveRecordBuilder(
					deDataRecord
				).onBehalfOf(
					_adminUser.getUserId()
				).build();

		DEDataEngineTestUtil.saveDataRecord(
			_adminUser, _group, _deDataRecordCollectionService,
			deDataRecordCollectionSaveRecordRequest);
	}

	@Test(expected = DEDataRecordCollectionException.NoSuchFields.class)
	public void testInsertDataRecordWithNonexistingField() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = new DEDataRecord();

		deDataRecord.setDEDataRecordCollection(deDataRecordCollection);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay");
				put("email", "test@liferay.com");
				put("phone", "333-333-333");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecordCollectionSaveRecordRequest
			deDataRecordCollectionSaveRecordRequest =
				DEDataRecordCollectionRequestBuilder.saveRecordBuilder(
					deDataRecord
				).inGroup(
					_group.getGroupId()
				).onBehalfOf(
					_adminUser.getUserId()
				).build();

		DEDataEngineTestUtil.saveDataRecord(
			_adminUser, _group, _deDataRecordCollectionService,
			deDataRecordCollectionSaveRecordRequest);
	}

	@Test(expected = DEDataRecordCollectionException.AddDataRecord.class)
	public void testInsertDataRecordWithNonexistingGroup() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = new DEDataRecord();

		deDataRecord.setDEDataRecordCollection(deDataRecordCollection);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecordCollectionSaveRecordRequest
			deDataRecordCollectionSaveRecordRequest =
				DEDataRecordCollectionRequestBuilder.saveRecordBuilder(
					deDataRecord
				).inGroup(
					1
				).onBehalfOf(
					_adminUser.getUserId()
				).build();

		DEDataEngineTestUtil.saveDataRecord(
			_adminUser, _group, _deDataRecordCollectionService,
			deDataRecordCollectionSaveRecordRequest);
	}

	@Test(expected = DEDataRecordCollectionException.AddDataRecord.class)
	public void testInsertDataRecordWithNonexistingUser() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = new DEDataRecord();

		deDataRecord.setDEDataRecordCollection(deDataRecordCollection);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecordCollectionSaveRecordRequest
			deDataRecordCollectionSaveRecordRequest =
				DEDataRecordCollectionRequestBuilder.saveRecordBuilder(
					deDataRecord
				).inGroup(
					_group.getGroupId()
				).onBehalfOf(
					1
				).build();

		DEDataEngineTestUtil.saveDataRecord(
			_adminUser, _group, _deDataRecordCollectionService,
			deDataRecordCollectionSaveRecordRequest);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertDataRecordWithNoPermission() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataEngineTestUtil.insertDEDataRecord(
			_siteMember, _group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.AddDataRecord.class)
	public void testInsertDataRecordWithNoUser() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = new DEDataRecord();

		deDataRecord.setDEDataRecordCollection(deDataRecordCollection);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecordCollectionSaveRecordRequest
			deDataRecordCollectionSaveRecordRequest =
				DEDataRecordCollectionRequestBuilder.saveRecordBuilder(
					deDataRecord
				).inGroup(
					_group.getGroupId()
				).build();

		DEDataEngineTestUtil.saveDataRecord(
			_adminUser, _group, _deDataRecordCollectionService,
			deDataRecordCollectionSaveRecordRequest);
	}

	@Test(expected = DEDataRecordCollectionException.InvalidDataRecord.class)
	public void testInsertRecordWithAnInvalidFieldValue() throws Exception {
		DEDataDefinitionRule deDataDefinitionRule = new DEDataDefinitionRule();

		deDataDefinitionRule.setName(
			DEDataDefinitionRuleConstants.EMAIL_ADDRESS_RULE);
		deDataDefinitionRule.setRuleType(
			DEDataDefinitionRuleConstants.VALIDATION_RULE_TYPE);

		deDataDefinitionRule.getDEDataDefinitionFieldNames().add("email");

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinitionWithRuleFunction(
				_adminUser, _group, _deDataDefinitionService,
				deDataDefinitionRule);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, deDataDefinition,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = new DEDataRecord();

		deDataRecord.setDEDataRecordCollection(deDataRecordCollection);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay");
				put("email", "test");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecordCollectionSaveRecordRequest
			deDataRecordCollectionSaveRecordRequest =
				DEDataRecordCollectionRequestBuilder.saveRecordBuilder(
					deDataRecord
				).inGroup(
					_group.getGroupId()
				).onBehalfOf(
					_adminUser.getUserId()
				).build();

		DEDataEngineTestUtil.saveDataRecord(
			_adminUser, _group, _deDataRecordCollectionService,
			deDataRecordCollectionSaveRecordRequest);
	}

	@Test
	public void testInsertRecordWithAValidFieldValue() throws Exception {
		DEDataDefinitionRule deDataDefinitionRule = new DEDataDefinitionRule();

		deDataDefinitionRule.setName(
			DEDataDefinitionRuleConstants.EMAIL_ADDRESS_RULE);
		deDataDefinitionRule.setRuleType(
			DEDataDefinitionRuleConstants.VALIDATION_RULE_TYPE);

		deDataDefinitionRule.getDEDataDefinitionFieldNames().add("email");

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinitionWithRuleFunction(
				_adminUser, _group, _deDataDefinitionService,
				deDataDefinitionRule);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, deDataDefinition,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		Assert.assertTrue(deDataRecord.getDEDataRecordId() > 0);
	}

	@Test(
		expected = DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testInsertRecordWithNoDataCollection() throws Exception {
		DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, (DEDataRecordCollection)null,
			_deDataRecordCollectionService);
	}

	@Test
	public void testListDataRecordCollectionPaginatedMiddleInsideRange()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), 1, 5);

		Assert.assertEquals(
			deDataRecordCollections.toString(), 4,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionPaginatedMiddleOutOfRange()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), 4, 10);

		Assert.assertEquals(
			deDataRecordCollections.toString(), 1,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionPaginatedOutOfRange()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), 6, 10);

		Assert.assertEquals(
			deDataRecordCollections.toString(), 0,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionPaginatedReturnLast()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), 4, 5);

		Assert.assertEquals(
			deDataRecordCollections.toString(), 1,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionPaginatedStartingAtMinusOne()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), -2, 5);

		Assert.assertEquals(
			deDataRecordCollections.toString(), 5,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionPaginatedStartingAtMinusTwo()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), -1, 5);

		Assert.assertEquals(
			deDataRecordCollections.toString(), 5,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionPaginatedStartingAtZero()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), 0, 5);

		Assert.assertEquals(
			deDataRecordCollections.toString(), 5,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionWithInvalidGroupId()
		throws Exception {

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(-1, null, null);

		Assert.assertTrue(deDataRecordCollections.isEmpty());
	}

	@Test
	public void testListDataRecordCollectionWithNonexistentGroup()
		throws Exception {

		int total = 3;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(1, null, null);

		Assert.assertTrue(deDataRecordCollections.isEmpty());
	}

	@Test
	public void testListDataRecordCollectionWithNoRecords() throws Exception {
		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), null, null);

		Assert.assertTrue(deDataRecordCollections.isEmpty());
	}

	@Test
	public void testListDataRecordCollectionWithoutSpecifyingFinalIndex()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		DEDataRecordCollectionListRequest deDataRecordCollectionListRequest =
			DEDataRecordCollectionRequestBuilder.listBuilder(
			).inGroup(
				_group.getGroupId()
			).startingAt(
				3
			).build();

		DEDataRecordCollectionListResponse deDataRecordCollectionListResponse =
			_deDataRecordCollectionService.execute(
				deDataRecordCollectionListRequest);

		List<DEDataRecordCollection> deDataRecordCollections =
			deDataRecordCollectionListResponse.getDEDataRecordCollections();

		Assert.assertTrue(deDataRecordCollections.isEmpty());
	}

	@Test
	public void testListDataRecordCollectionWithoutSpecifyingGroup()
		throws Exception {

		int total = 3;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		DEDataRecordCollectionListRequest deDataRecordCollectionListRequest =
			DEDataRecordCollectionRequestBuilder.listBuilder(
			).endingAt(
				GetterUtil.getInteger(null, QueryUtil.ALL_POS)
			).startingAt(
				GetterUtil.getInteger(null, QueryUtil.ALL_POS)
			).build();

		DEDataRecordCollectionListResponse deDataRecordCollectionListResponse =
			_deDataRecordCollectionService.execute(
				deDataRecordCollectionListRequest);

		List<DEDataRecordCollection> deDataRecordCollections =
			deDataRecordCollectionListResponse.getDEDataRecordCollections();

		Assert.assertTrue(deDataRecordCollections.isEmpty());
	}

	@Test
	public void testListDataRecordCollectionWithoutSpecifyingPagination()
		throws Exception {

		int total = 3;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		DEDataRecordCollectionListRequest deDataRecordCollectionListRequest =
			DEDataRecordCollectionRequestBuilder.listBuilder(
			).inGroup(
				_group.getGroupId()
			).build();

		DEDataRecordCollectionListResponse deDataRecordCollectionListResponse =
			_deDataRecordCollectionService.execute(
				deDataRecordCollectionListRequest);

		List<DEDataRecordCollection> deDataRecordCollections =
			deDataRecordCollectionListResponse.getDEDataRecordCollections();

		Assert.assertEquals(
			deDataRecordCollections.toString(), 3,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionWithoutSpecifyingStartIndex()
		throws Exception {

		int total = 5;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		DEDataRecordCollectionListRequest deDataRecordCollectionListRequest =
			DEDataRecordCollectionRequestBuilder.listBuilder(
			).inGroup(
				_group.getGroupId()
			).endingAt(
				3
			).build();

		DEDataRecordCollectionListResponse deDataRecordCollectionListResponse =
			_deDataRecordCollectionService.execute(
				deDataRecordCollectionListRequest);

		List<DEDataRecordCollection> deDataRecordCollections =
			deDataRecordCollectionListResponse.getDEDataRecordCollections();

		Assert.assertEquals(
			deDataRecordCollections.toString(), 3,
			deDataRecordCollections.size());
	}

	@Test
	public void testListDataRecordCollectionWithRecords() throws Exception {
		int total = 3;

		for (int i = 0; i < total; i++) {
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);
		}

		List<DEDataRecordCollection> deDataRecordCollections =
			listDEDataRecordCollections(_group.getGroupId(), null, null);

		Assert.assertEquals(
			deDataRecordCollections.toString(), 3,
			deDataRecordCollections.size());
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeAddDataRecordCollectionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role.getName()}
					).allowAddDataRecordCollection(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				user, _group, deDataDefinition, _deDataRecordCollectionService);

		Assert.assertTrue(
			deDataRecordCollection.getDEDataRecordCollectionId() > 0);

		DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			new String[] {role.getName()}, _deDataRecordCollectionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			user, _group, deDataDefinition, _deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeAddDataRecordPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowAddDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			new String[] {DEActionKeys.ADD_DATA_RECORD},
			new String[] {role.getName()}, _deDataRecordCollectionService);

		DEDataEngineTestUtil.insertDEDataRecord(
			user, _group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeDefinePermissions() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

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
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
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
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role2.getName()}
						).allowUpdate(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecordCollection deDataRecordCollectionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecordCollection(
				user2, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollection.getDEDataRecordCollectionId(),
			deDataRecordCollectionAfterUpdate.getDEDataRecordCollectionId());

		DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			new String[] {role1.getName()}, _deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user1));

		ServiceContext serviceContext3 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext3);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							user1.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role2.getName()}
						).allowUpdate(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeDeleteDataRecordCollectionPermission()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowDelete(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			new String[] {ActionKeys.DELETE}, new String[] {role.getName()},
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			user, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeDeleteDataRecordPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowDeleteDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			new String[] {DEActionKeys.DELETE_DATA_RECORD},
			new String[] {role.getName()}, _deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecord(
			user, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeModelPermissionsWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), user, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			new String[] {ActionKeys.UPDATE},
			new String[] {RoleConstants.ORGANIZATION_USER},
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokePermissionsWithNoPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
			TestPropsValues.getCompanyId(), user, _group.getGroupId(),
			new String[] {RoleConstants.ORGANIZATION_USER},
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeUpdateDataRecordCollectionPermission()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowUpdate(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			user, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			new String[] {ActionKeys.UPDATE}, new String[] {role.getName()},
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			user, _group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeUpdateDataRecordPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecord.getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowUpdateDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecord.getDEDataRecordCollectionId(),
			new String[] {DEActionKeys.UPDATE_DATA_RECORD},
			new String[] {role.getName()}, _deDataRecordCollectionService);

		DEDataEngineTestUtil.updateDEDataRecord(
			user, _group, deDataRecord, _deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeViewDataRecordCollectionPermission()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowView(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			new String[] {ActionKeys.VIEW}, new String[] {role.getName()},
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.getDEDataRecordCollection(
			user, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeViewDataRecordPermissionToGuestUser()
		throws Exception {

		User guestUser = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {RoleConstants.GUEST}
						).allowViewDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord deDataRecord2 = DEDataEngineTestUtil.getDEDataRecord(
			guestUser, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(deDataRecord, deDataRecord2);

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			new String[] {DEActionKeys.VIEW_DATA_RECORD},
			new String[] {RoleConstants.GUEST}, _deDataRecordCollectionService);

		DEDataEngineTestUtil.getDEDataRecord(
			guestUser, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testRevokeViewDataRecordPermissionToSiteMember()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _adminUser.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
						saveModelPermissionsBuilder(
							TestPropsValues.getCompanyId(), _group.getGroupId(),
							_adminUser.getUserId(), _group.getGroupId(),
							deDataRecordCollection.
								getDEDataRecordCollectionId(),
							new String[] {role.getName()}
						).allowViewDataRecord(
						).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		DEDataRecord deDataRecord2 = DEDataEngineTestUtil.getDEDataRecord(
			user, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);

		Assert.assertEquals(deDataRecord, deDataRecord2);

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(),
			new String[] {DEActionKeys.VIEW_DATA_RECORD},
			new String[] {role.getName()}, _deDataRecordCollectionService);

		DEDataEngineTestUtil.getDEDataRecord(
			user, _group, deDataRecord.getDEDataRecordId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testUpdateDataRecord() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay2");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecord deDataRecordAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecord(
				_adminUser, _group, deDataRecord,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecord.getDEDataRecordId(),
			deDataRecordAfterUpdate.getDEDataRecordId());
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testUpdateDataRecordByGuest() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay2");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		deDataRecord = DEDataEngineTestUtil.updateDEDataRecord(
			user, _group, deDataRecord, _deDataRecordCollectionService);
	}

	@Test
	public void testUpdateDataRecordCollectionAfterUpdate() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 2");

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			_adminUser, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 3");

		DEDataRecordCollection deDataRecordCollectionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecordCollection(
				_adminUser, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollection.getDEDataRecordCollectionId(),
			deDataRecordCollectionAfterUpdate.getDEDataRecordCollectionId());
	}

	@Test
	public void testUpdateDataRecordCollectionAfterUpdateDataRecord()
		throws Exception {

		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay2");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		deDataRecord = DEDataEngineTestUtil.updateDEDataRecord(
			_adminUser, _group, deDataRecord, _deDataRecordCollectionService);

		values = new HashMap() {
			{
				put("name", "Liferay3");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		DEDataRecord deDataRecordAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecord(
				_adminUser, _group, deDataRecord,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecord.getDEDataRecordId(),
			deDataRecordAfterUpdate.getDEDataRecordId());
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testUpdateDataRecordCollectionByGuestUser() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 2");

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			user, _group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testUpdateDataRecordCollectionBySiteMember() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 2");

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			_siteMember, _group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testUpdateDataRecordWithNoPermission() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_adminUser, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		Map<String, Object> values = new HashMap() {
			{
				put("name", "Liferay2");
				put("email", "test@liferay.com");
			}
		};

		deDataRecord.setValues(values);

		deDataRecord = DEDataEngineTestUtil.updateDEDataRecord(
			_siteMember, _group, deDataRecord, _deDataRecordCollectionService);
	}

	protected List<DEDataRecordCollection> listDEDataRecordCollections(
			long groupId, Integer start, Integer end)
		throws Exception {

		DEDataRecordCollectionListRequest deDataRecordCollectionListRequest =
			DEDataRecordCollectionRequestBuilder.listBuilder(
			).endingAt(
				GetterUtil.getInteger(end, QueryUtil.ALL_POS)
			).inGroup(
				groupId
			).startingAt(
				GetterUtil.getInteger(start, QueryUtil.ALL_POS)
			).build();

		DEDataRecordCollectionListResponse deDataRecordCollectionListResponse =
			_deDataRecordCollectionService.execute(
				deDataRecordCollectionListRequest);

		return deDataRecordCollectionListResponse.getDEDataRecordCollections();
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@Inject(type = DEDataRecordCollectionService.class)
	private DEDataRecordCollectionService _deDataRecordCollectionService;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteMember;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}