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

package com.liferay.site.memberships.web.internal.util;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Eudaldo Alonso
 */
public class DepotRolesUtil {

	/**
	 * @see com.liferay.depot.web.internal.display.context.DepotAdminMembershipsDisplayContext.Step2#_filterGroupRoles(
	 *List)
	 */
	public static List<Role> filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupOwner(groupId)) {

			Stream<Role> stream = roles.stream();

			return stream.filter(
				role ->
					!Objects.equals(
						role.getName(),
						DepotRolesConstants.
							ASSET_LIBRARY_CONNECTED_SITE_MEMBER) &&
					!Objects.equals(
						role.getName(),
						DepotRolesConstants.ASSET_LIBRARY_MEMBER)
			).collect(
				Collectors.toList()
			);
		}

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.ASSIGN_USER_ROLES)) {

			return Collections.emptyList();
		}

		Stream<Role> stream = roles.stream();

		return stream.filter(
			role ->
				!Objects.equals(
					role.getName(),
					DepotRolesConstants.ASSET_LIBRARY_CONNECTED_SITE_MEMBER) &&
				!Objects.equals(
					role.getName(), DepotRolesConstants.ASSET_LIBRARY_MEMBER) &&
				!Objects.equals(
					role.getName(),
					DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR) &&
				!Objects.equals(
					role.getName(), DepotRolesConstants.ASSET_LIBRARY_OWNER) &&
				RolePermissionUtil.contains(
					permissionChecker, groupId, role.getRoleId(),
					ActionKeys.ASSIGN_MEMBERS)
		).collect(
			Collectors.toList()
		);
	}

}