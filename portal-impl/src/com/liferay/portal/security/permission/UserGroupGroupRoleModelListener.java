/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.UserGroupGroupRole;

/**
 * @author Preston Crary
 */
public class UserGroupGroupRoleModelListener
	extends BaseModelListener<UserGroupGroupRole> {

	@Override
	public void onAfterCreate(UserGroupGroupRole userGroupGroupRole) {
		PermissionCacheUtil.clearCache();
	}

	@Override
	public void onAfterRemove(UserGroupGroupRole userGroupGroupRole) {
		PermissionCacheUtil.clearCache();
	}

	@Override
	public void onAfterUpdate(
		UserGroupGroupRole originalUserGroupGroupRole,
		UserGroupGroupRole userGroupGroupRole) {

		PermissionCacheUtil.clearCache();
	}

}