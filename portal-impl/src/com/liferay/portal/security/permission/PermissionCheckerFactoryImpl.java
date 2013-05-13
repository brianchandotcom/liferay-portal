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

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PropsValues;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class PermissionCheckerFactoryImpl implements PermissionCheckerFactory {

	public PermissionCheckerFactoryImpl() throws Exception {
		Class<PermissionChecker> clazz =
			(Class<PermissionChecker>)Class.forName(
				PropsValues.PERMISSIONS_CHECKER);

		_permissionChecker = clazz.newInstance();
	}

	@Override
	public PermissionChecker create(User user) throws Exception {
		PermissionChecker permissionChecker = _permissionChecker.clone();

		permissionChecker.init(user);

		return permissionChecker;
	}

	private PermissionChecker _permissionChecker;

}