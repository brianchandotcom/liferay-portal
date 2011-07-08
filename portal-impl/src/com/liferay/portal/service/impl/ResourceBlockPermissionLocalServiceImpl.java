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
			long resourceBlockId, long roleId, long actionIds)
		throws SystemException {

		ResourceBlockPermissionPK pk =
			new ResourceBlockPermissionPK(resourceBlockId, roleId,
			actionIds);

		ResourceBlockPermission resourceBlockPermission =
			resourceBlockPermissionPersistence.fetchByPrimaryKey(pk);

		if (resourceBlockPermission == null) {
			resourceBlockPermissionPersistence.create(pk);

			resourceBlockPermissionPersistence.update(
				resourceBlockPermission, false);
		}

		return resourceBlockPermission;
	}

	public void addResourceBlockPermissions(
			long resourceBlockId, List<ResourcePermission> resourcePermissions)
		throws SystemException {

		for (ResourcePermission resourcePermission : resourcePermissions) {
			addResourceBlockPermission(
				resourceBlockId, resourcePermission.getRoleId(),
				resourcePermission.getActionIds());
		}
	}

	public void deleteResourceBlockPermissions(long resourceBlockId)
		throws SystemException {

		resourceBlockPermissionPersistence.removeByResourceBlockId(
			resourceBlockId);
	}

}