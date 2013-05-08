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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Michael C. Han
 */
public class PermissionConverterUtil {

	public static PermissionConverter getPermissionConverter() {

		PortalRuntimePermission.checkGetBeanProperty(
			PermissionConverterUtil.class);

		return _permissionConverter;
	}

	public void setPermissionConverter(
		PermissionConverter permissionConverter) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_permissionConverter = permissionConverter;
	}

	private static PermissionConverter _permissionConverter;

}