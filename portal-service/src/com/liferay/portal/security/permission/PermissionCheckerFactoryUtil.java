/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.User;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerFactoryUtil {

	public static PermissionChecker create(User user) throws Exception {
		return getPermissionCheckerFactory().create(user);
	}

	/**
	 * @deprecated {@link #create(User)}
	 */
	public static PermissionChecker create(User user, boolean checkGuest)
		throws Exception {

		return getPermissionCheckerFactory().create(user);
	}

	public static PermissionCheckerFactory getPermissionCheckerFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PermissionCheckerFactoryUtil.class);

		return _permissionCheckerFactory;
	}

	public void setPermissionCheckerFactory(
		PermissionCheckerFactory permissionCheckerFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_permissionCheckerFactory = permissionCheckerFactory;
	}

	private static PermissionCheckerFactory _permissionCheckerFactory;

}