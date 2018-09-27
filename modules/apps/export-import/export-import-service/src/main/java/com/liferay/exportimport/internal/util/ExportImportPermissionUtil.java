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

package com.liferay.exportimport.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Daniel Kocsis
 */
public class ExportImportPermissionUtil {

	public static final String ROLE_TEAM_PREFIX = "ROLE_TEAM_,*";

	public static Map<Long, Set<String>> getRoleIdsToActionIds(
		long companyId, String resourceName, long resourcePK) {

		return getRoleIdsToActionIds(
			companyId, resourceName, String.valueOf(resourcePK));
	}

	public static Map<Long, Set<String>> getRoleIdsToActionIds(
		long companyId, String resourceName, String resourcePK) {

		List<String> existingActionIds =
			ResourceActionsUtil.getModelResourceActions(resourceName);

		Map<Long, Set<String>> existingRoleIdsToActionIds = new HashMap<>();

		try {
			existingRoleIdsToActionIds = getRoleIdsToActionIds(
				companyId, resourceName, resourcePK, existingActionIds);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return existingRoleIdsToActionIds;
	}

	public static Map<Long, Set<String>> getRoleIdsToActionIds(
			long companyId, String className, String primKey,
			List<String> actionIds)
		throws PortalException {

		return ResourcePermissionLocalServiceUtil.
			getAvailableResourcePermissionActionIds(
				companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
				primKey, actionIds);
	}

	public static String getTeamRoleName(String roleName) {
		return ROLE_TEAM_PREFIX + roleName;
	}

	public static boolean isTeamRoleName(String roleName) {
		return roleName.startsWith(ROLE_TEAM_PREFIX);
	}

	public static Map<Long, String[]>
		mergeImportedPermissionsWithExistingPermissions(
			Map<Long, Set<String>> existingRoleIdsToActionIds,
			Map<Long, String[]> importedRoleIdsToActionIds) {

		Map<Long, String[]> mergedRoleIdsToActionIds = new HashMap<>();

		for (Map.Entry<Long, Set<String>> roleIdsToActionIds :
				existingRoleIdsToActionIds.entrySet()) {

			long roleId = roleIdsToActionIds.getKey();

			if (importedRoleIdsToActionIds.containsKey(roleId)) {
				String[] actionIds = importedRoleIdsToActionIds.remove(roleId);

				mergedRoleIdsToActionIds.put(roleId, actionIds);
			}
			else {
				mergedRoleIdsToActionIds.put(roleId, new String[0]);
			}
		}

		mergedRoleIdsToActionIds.putAll(importedRoleIdsToActionIds);

		return mergedRoleIdsToActionIds;
	}

	public static void updateResourcePermissions(
			long companyId, long groupId, String resourceName, long resourcePK,
			Map<Long, String[]> roleIdsToActionIds)
		throws PortalException {

		updateResourcePermissions(
			companyId, groupId, resourceName, String.valueOf(resourcePK),
			roleIdsToActionIds);
	}

	public static void updateResourcePermissions(
			long companyId, long groupId, String resourceName,
			String resourcePK, Map<Long, String[]> roleIdsToActionIds)
		throws PortalException {

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getResourcePermissions(
				companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
				resourcePK);

		Map<Long, String[]> mergedRoleIdsToActionIds = new HashMap<>(
			roleIdsToActionIds);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			mergedRoleIdsToActionIds.putIfAbsent(
				resourcePermission.getRoleId(), new String[0]);
		}

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
			resourcePK, mergedRoleIdsToActionIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportPermissionUtil.class);

}