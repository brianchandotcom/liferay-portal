/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.util.TestPropsValues;

import java.util.Random;

/**
 * @author Alberto Chaparro
 */
public class UserTestUtil {

	public static User addGroupAdminUser(Group group) throws Exception {
		return UserTestUtil.addGroupUser(
			group, RoleConstants.SITE_ADMINISTRATOR);
	}

	public static User addGroupOwnerUser(Group group) throws Exception {
		return UserTestUtil.addGroupUser(group, RoleConstants.SITE_OWNER);
	}

	public static User addGroupUser(Group group, String roleName)
		throws Exception {

		User groupUser = ServiceTestUtil.addUser(
			"groupUser" + _random.nextInt(), group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		long[] userIds = {groupUser.getUserId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, group.getGroupId(), role.getRoleId());

		return groupUser;
	}

	public static User addOrganizationAdminUser(Organization organization)
		throws Exception {

		return UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_ADMINISTRATOR);
	}

	public static User addOrganizationOwnerUser(Organization organization)
		throws Exception {

		return UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_OWNER);
	}

	public static User addOrganizationUser(
		Organization organization, String roleName) throws Exception {

		User organizationUser = ServiceTestUtil.addUser(
			"organizationUser" + _random.nextInt(), organization.getGroupId());

		long[] userIds = {organizationUser.getUserId()};

		UserLocalServiceUtil.addOrganizationUsers(
			organization.getOrganizationId(), userIds);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, organization.getGroupId(), role.getRoleId());

		return organizationUser;
	}

	static Random _random = new Random();

}