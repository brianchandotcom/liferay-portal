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

package com.liferay.portal.security.permission.test.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;

import java.util.Map;

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
			getRoleName(), getResourceName(), ResourceConstants.SCOPE_GROUP,
			getPrimKey(), ActionKeys.VIEW);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), getRoleName());

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			group.getCompanyId(), getResourceName(),
			ResourceConstants.SCOPE_INDIVIDUAL, getPrimKey(), role.getRoleId(),
			new String[] {ActionKeys.VIEW});
	}

	protected abstract void doSetUp() throws Exception;

	protected String getPrimKey() {
		return String.valueOf(group.getGroupId());
	}

	protected abstract String getResourceName();

	protected String getRoleName() {
		return RoleConstants.GUEST;
	}

	protected void removePortletModelViewPermission() throws Exception {
		RoleTestUtil.removeResourcePermission(
			getRoleName(), getResourceName(), ResourceConstants.SCOPE_GROUP,
			getPrimKey(), ActionKeys.VIEW);

		RoleTestUtil.removeResourcePermission(
			getRoleName(), getResourceName(),
			ResourceConstants.SCOPE_INDIVIDUAL, getPrimKey(), ActionKeys.VIEW);

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		permissionChecksMap.clear();
	}

	@DeleteAfterTestRun
	protected Group group;

	protected PermissionChecker permissionChecker;
	protected ServiceContext serviceContext;

	@DeleteAfterTestRun
	protected User user;

}