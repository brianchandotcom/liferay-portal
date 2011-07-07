/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.base.ResourceBlockPermissionLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.ResourceBlockPermissionPK;

import java.util.List;

/**
 * @author Connor McKay
 */
public class ResourceBlockPermissionLocalServiceImpl
	extends ResourceBlockPermissionLocalServiceBaseImpl {

	public ResourceBlockPermission addResourceBlockPermission(
				long actionIds, long resourceBlockId, long roleId)
		throws SystemException {

		ResourceBlockPermissionPK pk =
			new ResourceBlockPermissionPK(actionIds, resourceBlockId, roleId);

		ResourceBlockPermission resourceBlockPermission =
			resourceBlockPermissionPersistence.create(pk);

		resourceBlockPermissionPersistence.update(
			resourceBlockPermission, false);
	}

	public void addResourceBlockPermissions(
			ResourceBlock resourceBlock,
			List<ResourcePermission> resourcePermissions)
		throws SystemException {

		for (ResourcePermission resourcePermission : resourcePermissions) {

		}
	}
}