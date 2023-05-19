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

package com.liferay.roles.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.security.audit.storage.service.AuditEventService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class RoleLocalServiceSystemRolesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAnalyticsAdminRolePermissions() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();
		_user = UserTestUtil.addUser();
		_userGroup = UserGroupTestUtil.addUserGroup();

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ANALYTICS_ADMINISTRATOR);

		_roleLocalService.addUserRole(_user.getUserId(), role.getRoleId());

		PermissionChecker oldPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		try {
			_groupService.getGtGroups(
				0, TestPropsValues.getCompanyId(), 0, true, 5);

			_userService.getCompanyUsers(
				TestPropsValues.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
			_userService.getCompanyUsersCount(TestPropsValues.getCompanyId());
			_userService.getGtCompanyUsers(
				0, TestPropsValues.getCompanyId(), 5);
			_userService.getGtOrganizationUsers(
				0, _organization.getOrganizationId(), 5);
			_userService.getGtUserGroupUsers(0, _userGroup.getUserGroupId(), 5);
			_userService.getOrganizationsAndUserGroupsUsersCount(
				new long[] {_organization.getOrganizationId()},
				new long[] {_userGroup.getUserGroupId()});

			_auditEventService.getAuditEvents(
				TestPropsValues.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
			_auditEventService.getAuditEvents(
				TestPropsValues.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
			_auditEventService.getAuditEvents(
				TestPropsValues.getCompanyId(), 0, _user.getUserId(),
				_user.getScreenName(), null, null, null, null, null, null, null,
				null, 0, null, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			_auditEventService.getAuditEvents(
				TestPropsValues.getCompanyId(), 0, _user.getUserId(),
				_user.getScreenName(), null, null, null, null, null, null, null,
				null, 0, null, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(oldPermissionChecker);
		}
	}

	@Test
	public void testCheckSystemnRole() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		long groupId = TestPropsValues.getGroupId();
		_user = UserTestUtil.addUser();

		Role powerUserRole = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), RoleConstants.POWER_USER);
		Role userRole = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		boolean hasViewPermission = permissionChecker.hasPermission(
			groupId, Role.class.getName(), powerUserRole.getRoleId(),
			ActionKeys.VIEW);

		try {
			_resourcePermissionLocalService.removeResourcePermission(
				companyId, Role.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(powerUserRole.getRoleId()), userRole.getRoleId(),
				ActionKeys.VIEW);

			_roleLocalService.checkSystemRoles(companyId);

			Assert.assertFalse(
				permissionChecker.hasPermission(
					groupId, Role.class.getName(), powerUserRole.getRoleId(),
					ActionKeys.VIEW));
		}
		finally {
			if (hasViewPermission) {
				_resourcePermissionLocalService.setResourcePermissions(
					companyId, Role.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(powerUserRole.getRoleId()),
					userRole.getRoleId(), new String[] {ActionKeys.VIEW});
			}
		}
	}

	@Inject
	private AuditEventService _auditEventService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupService _groupService;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@Inject
	private UserService _userService;

}