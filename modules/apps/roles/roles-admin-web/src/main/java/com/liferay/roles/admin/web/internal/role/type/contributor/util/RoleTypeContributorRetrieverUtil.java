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

package com.liferay.roles.admin.web.internal.role.type.contributor.util;

import com.liferay.roles.admin.web.internal.constants.RolesAdminWebKeys;
import com.liferay.roles.admin.web.internal.role.type.contributor.RoleTypeContributor;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class RoleTypeContributorRetrieverUtil {

	public static RoleTypeContributor getCurrentRoleTypeContributor(
		HttpServletRequest httpServletRequest) {

		return (RoleTypeContributor)httpServletRequest.getAttribute(
			RolesAdminWebKeys.CURRENT_ROLE_TYPE);
	}

	public static RoleTypeContributor getCurrentRoleTypeContributor(
		PortletRequest portletRequest) {

		return (RoleTypeContributor)portletRequest.getAttribute(
			RolesAdminWebKeys.CURRENT_ROLE_TYPE);
	}

	public static List<RoleTypeContributor> getRoleTypeContributors(
		HttpServletRequest httpServletRequest) {

		return (List<RoleTypeContributor>)httpServletRequest.getAttribute(
			RolesAdminWebKeys.ROLE_TYPES);
	}

	public static List<RoleTypeContributor> getRoleTypeContributors(
		PortletRequest portletRequest) {

		return (List<RoleTypeContributor>)portletRequest.getAttribute(
			RolesAdminWebKeys.ROLE_TYPES);
	}

}