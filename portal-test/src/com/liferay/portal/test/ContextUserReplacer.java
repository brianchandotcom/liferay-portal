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

package com.liferay.portal.test;

import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;

/**
 * @author Adolfo Pérez
 */
public class ContextUserReplacer implements AutoCloseable {

	public ContextUserReplacer(User user) throws Exception {
		this(user, PermissionCheckerFactoryUtil.create(user));
	}

	public ContextUserReplacer(User user, PermissionChecker permissionChecker) {
		_name = PrincipalThreadLocal.getName();
		_permissionCheker = PermissionThreadLocal.getPermissionChecker();

		PrincipalThreadLocal.setName(user.getUserId());
		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@Override
	public void close() {
		PrincipalThreadLocal.setName(_name);
		PermissionThreadLocal.setPermissionChecker(_permissionCheker);
	}

	private final String _name;
	private final PermissionChecker _permissionCheker;

}