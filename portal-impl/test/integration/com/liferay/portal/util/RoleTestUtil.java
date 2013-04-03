/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;

/**
 * @author Roberto Díaz
 */
public class RoleTestUtil {

	public static long addGroupRole(long groupId) throws Exception {
		Role role = ServiceTestUtil.addRole(
			ServiceTestUtil.randomString(), RoleConstants.TYPE_SITE);

		RoleLocalServiceUtil.addGroupRole(groupId, role.getRoleId());

		return role.getRoleId();
	}

	public static long addOrganizationRole(long groupId) throws Exception {
		Role role = ServiceTestUtil.addRole(
			ServiceTestUtil.randomString(), RoleConstants.TYPE_ORGANIZATION);

		RoleLocalServiceUtil.addGroupRole(groupId, role.getRoleId());

		return role.getRoleId();
	}

	public static long addRegularRole(long groupId) throws Exception {
		Role role = ServiceTestUtil.addRole(
			ServiceTestUtil.randomString(), RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(groupId, role.getRoleId());

		return role.getRoleId();
	}

}