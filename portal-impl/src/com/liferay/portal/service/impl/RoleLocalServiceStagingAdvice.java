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

package com.liferay.portal.service.impl;

/**
 * @author Tomas Polesovsky
 */
public class RoleLocalServiceStagingAdvice extends LiveGroupStagingAdvice {

	public RoleLocalServiceStagingAdvice() {
		initGroupServiceBuilderMethods("Role");
	}

	@Override
	public void replaceStagingGroupIds(String methodName, Object[] arguments) {
		if (methodName.equals("getDefaultGroupRole")) {
			replace(arguments, 0);
		}
		else if (methodName.equals("getGroupRelatedRoles")) {
			replace(arguments, 0);
		}
		else if (methodName.equals("getTeamRoleMap")) {
			replace(arguments, 0);
		}
		else if (methodName.equals("getTeamRoles")) {
			replace(arguments, 0);
		}
		else if (methodName.equals("getUserGroupGroupRoles")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("getUserGroupGroupRolesCount")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("getUserGroupRoles")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("getUserRelatedRoles")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("getUserRelatedRoles")) {
			replace(arguments, 1);
		}
	}

}