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
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;

/**
 * @author Preston Crary
 */
public class ResourcePermissionModelListener
	extends BaseModelListener<ResourcePermission> {

	@Override
	public void onAfterCreate(ResourcePermission resourcePermission) {
		_clearCache(resourcePermission);
	}

	@Override
	public void onAfterRemove(ResourcePermission resourcePermission) {
		_clearCache(resourcePermission);
	}

	@Override
	public void onAfterUpdate(ResourcePermission resourcePermission) {
		_clearCache(resourcePermission);
	}

	@Override
	public void onBeforeUpdate(ResourcePermission resourcePermission) {
		ResourcePermissionModelImpl resourcePermissionModelImpl =
			(ResourcePermissionModelImpl)resourcePermission;

		long columnBitmask = resourcePermissionModelImpl.getColumnBitmask();

		if ((columnBitmask & _CLEAR_ON_BEFORE_BITMASK) != 0) {
			PermissionCacheUtil.clearResourcePermissionCache(
				resourcePermissionModelImpl.getOriginalScope(),
				resourcePermissionModelImpl.getOriginalName(),
				resourcePermissionModelImpl.getOriginalPrimKey());
		}
	}

	private void _clearCache(ResourcePermission resourcePermission) {
		if (resourcePermission != null) {
			PermissionCacheUtil.clearResourcePermissionCache(
				resourcePermission.getScope(), resourcePermission.getName(),
				resourcePermission.getPrimKey());
		}
	}

	private static final long _CLEAR_ON_BEFORE_BITMASK =
		ResourcePermissionModelImpl.NAME_COLUMN_BITMASK |
		ResourcePermissionModelImpl.PRIMKEY_COLUMN_BITMASK;

}