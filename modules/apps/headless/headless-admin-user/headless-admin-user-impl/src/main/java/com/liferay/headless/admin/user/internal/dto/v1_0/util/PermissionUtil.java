/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.portal.kernel.service.PermissionService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.vulcan.permission.Permission;

import java.util.Collection;

/**
 * @author Crescenzo Rega
 */
public class PermissionUtil {

	public static Permission[] toPermissions(
			long companyId, long groupId, long id, String name,
			PermissionService permissionService,
			ResourceActionLocalService resourceActionLocalService)
		throws Exception {

		permissionService.checkPermission(groupId, name, id);

		Collection<Permission> permissions =
			com.liferay.portal.vulcan.permission.PermissionUtil.getPermissions(
				companyId, resourceActionLocalService.getResourceActions(name),
				id, name, null);

		return permissions.toArray(new Permission[0]);
	}

}