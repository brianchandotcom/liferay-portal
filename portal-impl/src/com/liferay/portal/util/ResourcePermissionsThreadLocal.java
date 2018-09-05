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

package com.liferay.portal.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.model.ResourcePermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ResourcePermissionsThreadLocal {

	public static Map<Long, ResourcePermission> getResourcePermissions() {
		return _resourcePermissions.get();
	}

	public static void setResourcePermissions(
		List<ResourcePermission> resourcePermissions) {

		if (resourcePermissions != null) {
			Map<Long, ResourcePermission> resourcePermissionMap =
				new HashMap<>();

			for (ResourcePermission resourcePermission : resourcePermissions) {
				resourcePermissionMap.put(
					resourcePermission.getRoleId(), resourcePermission);
			}

			_resourcePermissions.set(resourcePermissionMap);
		}
		else {
			_resourcePermissions.remove();
		}
	}

	private static final ThreadLocal<Map<Long, ResourcePermission>>
		_resourcePermissions = new CentralizedThreadLocal<>(
			ResourcePermissionsThreadLocal.class + "._resourcePermissions",
			() -> null, false);

}