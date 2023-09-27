/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		UserTestUtil.setUser(user);

		permissionChecker = PermissionThreadLocal.getPermissionChecker();

		addPortletModelViewPermission();
	}

	@After
	public void tearDown() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		removePortletModelViewPermission();
	}

	protected void addPortletModelViewPermission() throws Exception {
		RoleTestUtil.addResourcePermission(
			getRoleName(), getResourceName(), ResourceConstants.SCOPE_GROUP,
			String.valueOf(group.getGroupId()), ActionKeys.VIEW);

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
			String.valueOf(group.getGroupId()), ActionKeys.VIEW);

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