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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.TeamLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TeamPermissionImpl implements TeamPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, teamId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, team, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException, SystemException {

		Team team = TeamLocalServiceUtil.getTeam(teamId);

		return contains(permissionChecker, team, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException, SystemException {

		if (GroupPermissionUtil.contains(
				permissionChecker, team.getGroupId(),
				ActionKeys.MANAGE_TEAMS)) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				team.getCompanyId(), Team.class.getName(), team.getTeamId(),
				team.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			team.getGroupId(), Team.class.getName(), team.getTeamId(),
			actionId);
	}

}