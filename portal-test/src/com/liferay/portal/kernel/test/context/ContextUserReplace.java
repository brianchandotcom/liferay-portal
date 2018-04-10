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

package com.liferay.portal.kernel.test.context;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

/**
 * @author Adolfo Pérez
 */
public class ContextUserReplace implements AutoCloseable {

	public ContextUserReplace(User user) throws Exception {
		this(user, PermissionCheckerFactoryUtil.create(user));
	}

	public ContextUserReplace(User user, PermissionChecker permissionChecker) {
		_name = PrincipalThreadLocal.getName();
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PrincipalThreadLocal.setName(user.getUserId());
		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@Override
	public void close() {
		PrincipalThreadLocal.setName(_name);
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	private final String _name;
	private final PermissionChecker _permissionChecker;

}