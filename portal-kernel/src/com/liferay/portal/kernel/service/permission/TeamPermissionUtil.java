/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class TeamPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, teamId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Team.class.getName(), teamId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, team, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Team.class.getName(), team.getTeamId(),
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, TeamLocalServiceUtil.getTeam(teamId), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, team.getGroupId(), Team.class.getName(),
			team.getTeamId(), StringPool.BLANK, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, team.getGroupId(),
				ActionKeys.MANAGE_TEAMS) ||
			permissionChecker.hasOwnerPermission(
				team.getCompanyId(), Team.class.getName(), team.getTeamId(),
				team.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			team.getGroupId(), Team.class.getName(), team.getTeamId(),
			actionId);
	}

}