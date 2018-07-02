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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author     Alexander Chow
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class DLFileEntryTypePermission {

	public static void check(
			PermissionChecker permissionChecker, DLFileEntryType fileEntryType,
			String actionId)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			permissionChecker, fileEntryType, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long fileEntryTypeId,
			String actionId)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			permissionChecker, fileEntryTypeId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFileEntryType fileEntryType,
			String actionId)
		throws PortalException {

		return _dlFileEntryTypeModelResourcePermission.contains(
			permissionChecker, fileEntryType, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fileEntryTypeId,
			String actionId)
		throws PortalException {

		return _dlFileEntryTypeModelResourcePermission.contains(
			permissionChecker, fileEntryTypeId, actionId);
	}

	private static volatile ModelResourcePermission<DLFileEntryType>
		_dlFileEntryTypeModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, DLFileEntryTypePermission.class,
				"_dlFileEntryTypeModelResourcePermission",
				"(model.class.name=" + DLFileEntryType.class.getName() + ")",
				true);

}