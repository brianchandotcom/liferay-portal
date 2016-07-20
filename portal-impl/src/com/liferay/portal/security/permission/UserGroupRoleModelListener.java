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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.model.impl.UserGroupRoleModelImpl;

/**
 * @author Preston Crary
 */
public class UserGroupRoleModelListener
	extends BaseModelListener<UserGroupRole> {

	@Override
	public void onAfterCreate(UserGroupRole userGroupRole) {
		_clearCache(userGroupRole);
	}

	@Override
	public void onAfterRemove(UserGroupRole userGroupRole) {
		_clearCache(userGroupRole);
	}

	@Override
	public void onAfterUpdate(UserGroupRole userGroupRole) {
		_clearCache(userGroupRole);
	}

	@Override
	public void onBeforeUpdate(UserGroupRole userGroupRole) {
		UserGroupRoleModelImpl userGroupRoleModelImpl =
			(UserGroupRoleModelImpl)userGroupRole;

		long originalUserId = userGroupRoleModelImpl.getOriginalUserId();

		if (originalUserId != userGroupRoleModelImpl.getUserId()) {
			PermissionCacheUtil.clearCache(originalUserId);
		}
	}

	private void _clearCache(UserGroupRole userGroupRole) {
		if (userGroupRole != null) {
			PermissionCacheUtil.clearCache(userGroupRole.getUserId());
		}
	}

}