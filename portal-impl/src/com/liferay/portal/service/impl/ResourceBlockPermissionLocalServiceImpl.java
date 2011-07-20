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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourceBlockPermissionImpl;
import com.liferay.portal.service.base.ResourceBlockPermissionLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.ResourceBlockPermissionPK;
import com.liferay.portal.util.comparator.ResourceBlockPermissionRoleIdComparator;

import java.util.ArrayList;
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

	public void deleteResourceBlockPermissions(long resourceBlockId)
		throws SystemException {

		resourceBlockPermissionPersistence.removeByResourceBlockId(
			resourceBlockId);
	}

	public List<ResourceBlockPermission> getResourceBlockPermissions(
			long resourceBlockId)
		throws SystemException {

		return resourceBlockPermissionPersistence.findByResourceBlockId(
			resourceBlockId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new ResourceBlockPermissionRoleIdComparator());
	}

	public List<ResourceBlockPermission> getResourceBlockPermissions(
			List<ResourcePermission> resourcePermissions)
		throws SystemException {

		List<ResourceBlockPermission> resourceBlockPermissions =
			new ArrayList<ResourceBlockPermission>();

		for (ResourcePermission resourcePermission : resourcePermissions) {
			ResourceBlockPermission resourceBlockPermission =
				new ResourceBlockPermissionImpl();
			resourceBlockPermission.setActionIds(
				resourcePermission.getActionIds());
			resourceBlockPermission.setRoleId(resourcePermission.getRoleId());
			resourceBlockPermissions.add(resourceBlockPermission);
		}

		return resourceBlockPermissions;
	}

	public void updateResourceBlockPermissions(
			long resourceBlockId, List<ResourceBlockPermission> resourceBlockPermissions)
		throws SystemException {
	
		for (ResourceBlockPermission resourceBlockPermission :
			resourceBlockPermissions) {
	
			resourceBlockPermission.setResourceBlockId(resourceBlockId);
			updateResourceBlockPermission(resourceBlockPermission);
		}
	}

}