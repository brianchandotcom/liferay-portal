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
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.base.ResourceBlockLocalServiceBaseImpl;

import java.nio.ByteBuffer;

import java.util.List;

/**
 * @author Connor McKay
 */
public class ResourceBlockLocalServiceImpl
	extends ResourceBlockLocalServiceBaseImpl {

	/**
	 * Returns the permissions hash for the resource. The permissions hash is a
	 * representation of all the roles with access to the resource along with
	 * the actions they can perform.
	 *
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @return the permissions hash for the resource
	 * @throws SystemException if a system exception occurred
	 */
	public String getPermissionsHash(String name, String primKey)
		throws SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionLocalService.getResourceResourcePermissions(
			name, primKey);

		ByteBuffer buffer =
			ByteBuffer.allocate(resourcePermissions.size() * 16);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			buffer.putLong(resourcePermission.getRoleId());
			buffer.putLong(resourcePermission.getActionIds());
		}

		buffer.flip();

		return DigesterUtil.digestHex("SHA-1", buffer);
	}

}