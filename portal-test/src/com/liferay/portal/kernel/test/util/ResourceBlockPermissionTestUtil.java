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

package com.liferay.portal.kernel.test.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceBlockPermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceBlockPermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author     Alberto Chaparro
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ResourceBlockPermissionTestUtil {

	public static ResourceBlockPermission addResourceBlockPermission(
			long resourceBlockId, long roleId, long actionIds)
		throws Exception {

		long resourceBlockPermissionId = CounterLocalServiceUtil.increment(
			ResourceBlockPermission.class.getName());

		ResourceBlockPermission resourceBlockPermission =
			ResourceBlockPermissionLocalServiceUtil.
				createResourceBlockPermission(resourceBlockPermissionId);

		resourceBlockPermission.setResourceBlockId(resourceBlockId);
		resourceBlockPermission.setRoleId(roleId);
		resourceBlockPermission.setActionIds(actionIds);

		return ResourceBlockPermissionLocalServiceUtil.
			addResourceBlockPermission(resourceBlockPermission);
	}

	public static void removeResourceBlockPermissions(
			long companyId, long groupId, String portletResource,
			String resourceName, long classPK, String[] roleNames,
			List<String> actionIds)
		throws PortalException {

		List<String> resourceActions = ResourceActionsUtil.getResourceActions(
			portletResource, resourceName);

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		for (String roleName : roleNames) {
			Role role = RoleLocalServiceUtil.getRole(companyId, roleName);

			List<String> roleActionIds = ListUtil.copy(resourceActions);

			if (roleName.equals(RoleConstants.GUEST)) {
				List<String> unsupportedActionIds =
					ResourceActionsUtil.getResourceGuestUnsupportedActions(
						portletResource, resourceName);

				roleActionIds.removeAll(unsupportedActionIds);
			}

			roleActionIds.removeAll(actionIds);

			roleIdsToActionIds.put(
				role.getRoleId(), ArrayUtil.toStringArray(roleActionIds));
		}

		ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
			companyId, groupId, resourceName, classPK, roleIdsToActionIds);
	}

}