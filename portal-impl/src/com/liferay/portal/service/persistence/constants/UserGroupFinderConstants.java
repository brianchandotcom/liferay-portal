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

package com.liferay.portal.service.persistence.constants;

/**
 * @author Drew Brokke
 */
public class UserGroupFinderConstants {

	public static final String PARAM_USER_GROUP_GROUP_ROLE =
		"userGroupGroupRole";

	public static final String PARAM_USER_GROUPS_GROUPS = "userGroupsGroups";

	public static final String PARAM_USER_GROUPS_ROLES = "userGroupsRoles";

	public static final String PARAM_USER_GROUPS_TEAMS = "userGroupsTeams";

	public static final String PARAM_USER_GROUPS_USERS = "userGroupsUsers";

	public static final String[] PARAMS_USER_GROUP_FINDER = {
		PARAM_USER_GROUP_GROUP_ROLE, PARAM_USER_GROUPS_GROUPS,
		PARAM_USER_GROUPS_ROLES, PARAM_USER_GROUPS_TEAMS,
		PARAM_USER_GROUPS_USERS
	};

}