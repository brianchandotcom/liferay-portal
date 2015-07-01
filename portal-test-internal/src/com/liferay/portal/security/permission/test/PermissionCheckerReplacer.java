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

import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Adolfo Pérez
 */
public class PermissionCheckerReplacer implements Closeable {

	public PermissionCheckerReplacer(PermissionChecker permissionChecker) {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@Override
	public void close() throws IOException {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	private final PermissionChecker _originalPermissionChecker;

}