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

package com.liferay.portal.util.test;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;

import java.util.List;

/**
 * @author Alberto Chaparro
 */
public class ResourcePermissionTestUtil {

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, long roleId)
		throws Exception {

		return addResourcePermission(
			actionIds, name, RandomTestUtil.randomString(), roleId,
			RandomTestUtil.nextInt());
	}

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, String primKey, int scope)
		throws Exception {

		return addResourcePermission(
			actionIds, name, primKey, RandomTestUtil.nextInt(), scope);
	}

	public static ResourcePermission addResourcePermission(
			long actionIds, String name, String primKey, long roleId, int scope)
		throws Exception {

		long resourcePermissionId = CounterLocalServiceUtil.increment(
			ResourcePermission.class.getName());

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.createResourcePermission(
				resourcePermissionId);

		resourcePermission.setCompanyId(TestPropsValues.getCompanyId());
		resourcePermission.setName(name);
		resourcePermission.setScope(scope);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setRoleId(roleId);
		resourcePermission.setActionIds(actionIds);

		return ResourcePermissionLocalServiceUtil.addResourcePermission(
			resourcePermission);
	}

	public static void unsetResourcePermission(
			long companyId, String name, int scope, String primKey,
			String portletResource, String roleName, String actionId)
		throws Exception {

		removeGuestPermissions(companyId, name, scope, primKey);

		List<String> actions = ResourceActionsUtil.getResourceActions(
			portletResource, name);

		Role userRole = RoleLocalServiceUtil.getRole(companyId, roleName);

		List<String> availableResourcePermissionActionIds =
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					companyId, name, scope, primKey, userRole.getRoleId(),
					actions);

		availableResourcePermissionActionIds.remove(actionId);

		String[] actionIds = new String[0];

		for (String availableResourcePermissionActionId :
				availableResourcePermissionActionIds) {

			actionIds = ArrayUtil.append(
				actionIds, availableResourcePermissionActionId);
		}

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			companyId, name, scope, primKey, userRole.getRoleId(), actionIds);
	}

	protected static void removeGuestPermissions(
			long companyId, String name, int scope, String primKey)
		throws Exception {

		Role guestRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			companyId, name, scope, primKey, guestRole.getRoleId(),
			new String[0]);
	}

}