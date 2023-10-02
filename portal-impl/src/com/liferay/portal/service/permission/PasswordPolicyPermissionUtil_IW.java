/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

/**
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyPermissionUtil_IW {
	public static PasswordPolicyPermissionUtil_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long passwordPolicyId, java.lang.String actionId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		PasswordPolicyPermissionUtil.check(permissionChecker, passwordPolicyId,
			actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long passwordPolicyId, java.lang.String actionId) {
		return PasswordPolicyPermissionUtil.contains(permissionChecker,
			passwordPolicyId, actionId);
	}

	private PasswordPolicyPermissionUtil_IW() {
	}

	private static PasswordPolicyPermissionUtil_IW _instance = new PasswordPolicyPermissionUtil_IW();
}