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
import com.liferay.portal.kernel.model.ResourceBlock;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.model.impl.ResourceBlockModelImpl;

/**
 * @author Preston Crary
 */
public class ResourceBlockModelListener
	extends BaseModelListener<ResourceBlock> {

	@Override
	public void onAfterCreate(ResourceBlock resourceBlock) {
		_clearCache(resourceBlock);
	}

	@Override
	public void onAfterRemove(ResourceBlock resourceBlock) {
		_clearCache(resourceBlock);
	}

	@Override
	public void onAfterUpdate(ResourceBlock resourceBlock) {
		_clearCache(resourceBlock);
	}

	@Override
	public void onBeforeUpdate(ResourceBlock resourceBlock) {
		ResourceBlockModelImpl resourceBlockModelImpl =
			(ResourceBlockModelImpl)resourceBlock;

		long columnBitmask = resourceBlockModelImpl.getColumnBitmask();

		if ((columnBitmask & _CLEAR_ON_BEFORE_BITMASK) != 0) {
			PermissionCacheUtil.clearResourceBlockCache(
				resourceBlockModelImpl.getOriginalCompanyId(),
				resourceBlockModelImpl.getOriginalGroupId(),
				resourceBlockModelImpl.getOriginalName());
		}
	}

	private void _clearCache(ResourceBlock resourceBlock) {
		if (resourceBlock != null) {
			PermissionCacheUtil.clearResourceBlockCache(
				resourceBlock.getCompanyId(), resourceBlock.getGroupId(),
				resourceBlock.getName());

			PermissionCacheUtil.clearResourcePermissionCache(
				ResourceConstants.SCOPE_INDIVIDUAL, resourceBlock.getName(),
				String.valueOf(resourceBlock.getPrimaryKey()));
		}
	}

	private static final long _CLEAR_ON_BEFORE_BITMASK =
		ResourceBlockModelImpl.COMPANYID_COLUMN_BITMASK |
		ResourceBlockModelImpl.GROUPID_COLUMN_BITMASK |
		ResourceBlockModelImpl.NAME_COLUMN_BITMASK;

}