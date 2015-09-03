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
public class GroupLocalServiceStagingAdvice extends LiveGroupStagingAdvice {

	@Override
	public void replaceStagingGroupIds(String methodName, Object[] arguments) {
		if (methodName.equals("addOrganizationGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("addOrganizationGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("addRoleGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("addRoleGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("addUserGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("addUserGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("addUserGroupGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("addUserGroupGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("deleteOrganizationGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("deleteOrganizationGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("deleteRoleGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("deleteRoleGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("deleteUserGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("deleteUserGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("deleteUserGroupGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("deleteUserGroupGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("getUserGroupPrimaryKeys")) {
			replace(arguments, 0);
		}
		else if (methodName.equals("hasOrganizationGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("hasRoleGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("hasUserGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("hasUserGroupGroup")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("setOrganizationGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("setRoleGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("setUserGroupGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("setUserGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("unsetRoleGroups")) {
			replace(arguments, 1);
		}
		else if (methodName.equals("unsetUserGroups")) {
			replace(arguments, 1);
		}
	}

}