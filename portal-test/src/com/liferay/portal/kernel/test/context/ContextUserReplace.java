/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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