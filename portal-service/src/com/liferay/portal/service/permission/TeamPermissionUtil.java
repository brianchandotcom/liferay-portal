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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class TeamPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException, SystemException {

		getTeamPermission().check(permissionChecker, teamId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException, SystemException {

		getTeamPermission().check(permissionChecker, team, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException, SystemException {

		return getTeamPermission().contains(
			permissionChecker, teamId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException, SystemException {

		return getTeamPermission().contains(permissionChecker, team, actionId);
	}

	public static TeamPermission getTeamPermission() {
		PortalRuntimePermission.checkGetBeanProperty(TeamPermissionUtil.class);

		return _teamPermission;
	}

	public void setTeamPermission(TeamPermission teamPermission) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_teamPermission = teamPermission;
	}

	private static TeamPermission _teamPermission;

}