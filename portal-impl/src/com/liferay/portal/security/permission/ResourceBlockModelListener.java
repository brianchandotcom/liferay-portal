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

/**
 * @author     Preston Crary
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
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
	}

	private void _clearCache(ResourceBlock resourceBlock) {
		if (resourceBlock != null) {
			PermissionCacheUtil.clearResourcePermissionCache(
				ResourceConstants.SCOPE_INDIVIDUAL, resourceBlock.getName(),
				String.valueOf(resourceBlock.getPrimaryKey()));
		}
	}

}