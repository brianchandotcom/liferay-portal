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
			long resourceBlockId, long roleId, long actionIdsLong)
		throws SystemException {

		ResourceBlockPermission resourceBlockPermission =
			resourceBlockPermissionPersistence.fetchByR_R(resourceBlockId,
			roleId);

		if (resourceBlockPermission == null) {
			ResourceBlockPermissionPK pk =
				new ResourceBlockPermissionPK(resourceBlockId, roleId,
				actionIdsLong);

			resourceBlockPermissionPersistence.create(pk);

			updateResourceBlockPermission(resourceBlockPermission);

			return resourceBlockPermission;
		}

		resourceBlockPermission.setActionIds(actionIdsLong);
		updateResourceBlockPermission(resourceBlockPermission);

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

	public List<ResourceBlockPermission> getResourceBlockPermissions(
			long resourceBlockId)
		throws SystemException {

		return resourceBlockPermissionPersistence.findByResourceBlockId(
			resourceBlockId);
	}

	public void setPermissions(
			List<ResourceBlock> resourceBlocks, long roleId, long actionIdsLong)
		throws SystemException {

		for (ResourceBlock resourceBlock : resourceBlocks) {
			ResourceBlockPermission resourceBlockPermission =
				resourceBlockPermissionPersistence.fetchByR_R(
				resourceBlock.getPrimaryKey(), roleId);

			if (resourceBlockPermission == null) {
				if (actionIdsLong == 0) {
					continue;
				}

				resourceBlockPermission =
					addResourceBlockPermission(
					resourceBlock.getPrimaryKey(), roleId, actionIdsLong);
			}
			else {
				if (actionIdsLong == 0) {
					deleteResourceBlockPermission(resourceBlockPermission);
				}
				else {
					resourceBlockPermission.setActionIds(actionIdsLong);
					updateResourceBlockPermission(resourceBlockPermission);
				}
			}

			resourceBlockLocalService.updatePermissionsHash(resourceBlock);
		}
	}

	public void setPermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		List<ResourceBlock> resourceBlocks = resourceBlockPersistence.findByC_N(companyId, name);

		setPermissions(resourceBlocks, roleId, actionIdsLong);
	}

	public void setPermissions(
			long companyId, String name, long groupId, long roleId,
			long actionIdsLong)
		throws SystemException {

		List<ResourceBlock> resourceBlocks = resourceBlockPersistence.findByC_G_N(companyId, groupId, name);

		setPermissions(resourceBlocks, roleId, actionIdsLong);
	}

}