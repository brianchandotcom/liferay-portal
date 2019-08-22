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

package com.liferay.roles.admin.web.internal.role.type.util;

import com.liferay.roles.admin.web.internal.constants.RolesAdminWebKeys;
import com.liferay.roles.admin.web.internal.role.type.RoleType;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class RoleTypeRetrieverUtil {

	public static RoleType getCurrentRoleType(
		HttpServletRequest httpServletRequest) {

		return (RoleType)httpServletRequest.getAttribute(
			RolesAdminWebKeys.CURRENT_ROLE_TYPE);
	}

	public static RoleType getCurrentRoleType(PortletRequest portletRequest) {
		return (RoleType)portletRequest.getAttribute(
			RolesAdminWebKeys.CURRENT_ROLE_TYPE);
	}

	public static List<RoleType> getRoleTypes(
		HttpServletRequest httpServletRequest) {

		return (List<RoleType>)httpServletRequest.getAttribute(
			RolesAdminWebKeys.ROLE_TYPES);
	}

	public static List<RoleType> getRoleTypes(PortletRequest portletRequest) {
		return (List<RoleType>)portletRequest.getAttribute(
			RolesAdminWebKeys.ROLE_TYPES);
	}

}