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

package com.liferay.portal.service.permission;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;

import org.junit.After;
import org.junit.Before;

/**
 * @author Shinn Lok
 */
public abstract class BasePermissionTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
		user = UserTestUtil.addUser();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());

		doSetUp();

		ServiceTestUtil.setUser(user);

		permissionChecker = PermissionThreadLocal.getPermissionChecker();

		addPortletModelViewPermission();
	}

	@After
	public void tearDown() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		removePortletModelViewPermission();
	}

	protected void addPortletModelViewPermission() throws Exception {
		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, getResourceName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			ActionKeys.VIEW);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			group.getCompanyId(), getResourceName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(group.getGroupId()), role.getRoleId(),
			new String[] {ActionKeys.VIEW});
	}

	protected abstract void doSetUp() throws Exception;

	protected abstract String getResourceName();

	protected void removePortletModelViewPermission() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, getResourceName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			ActionKeys.VIEW);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, getResourceName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(group.getGroupId()), ActionKeys.VIEW);
	}

	@DeleteAfterTestRun
	protected Group group;

	protected PermissionChecker permissionChecker;
	protected ServiceContext serviceContext;

	@DeleteAfterTestRun
	protected User user;

}