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

package com.liferay.portal.security.permission.test;

import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.SimplePermissionChecker;

/**
 * @author Adolfo Pérez
 */
public class ConstantPermissionChecker extends SimplePermissionChecker {

	public static final PermissionChecker getAlwaysAllowingPermissionChecker(
		User user) {

		return new ConstantPermissionChecker(user, true);
	}

	public static final PermissionChecker getAlwaysDenyingPermissionChecker(
		User user) {

		return new ConstantPermissionChecker(user, false);
	}

	public ConstantPermissionChecker(User user, boolean hasPermission) {
		init(user);

		_hasPermission = hasPermission;
	}

	@Override
	public boolean hasPermission(String actionId) {
		return _hasPermission;
	}

	private final boolean _hasPermission;

}