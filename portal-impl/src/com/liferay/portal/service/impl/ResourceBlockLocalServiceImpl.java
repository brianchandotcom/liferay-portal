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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.base.ResourceBlockLocalServiceBaseImpl;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Manages the creation and upkeep of resource blocks and the resources they
 * contain.
 *
 * @author Connor McKay
 */
public class ResourceBlockLocalServiceImpl
	extends ResourceBlockLocalServiceBaseImpl {

	/**
	 * Adds a resource block for the resource and creates associations between
	 * it and the roles with permission to access the resource.
	 *
	 * @param  permissionsHash the resource's permissions hash
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @return the new resource block
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlock addResourceBlock(
			String permissionsHash, String name, String primKey)
		throws SystemException {

		long resourceBlockId = counterLocalService.increment();

		ResourceBlock resourceBlock =
			resourceBlockPersistence.create(resourceBlockId);

		resourceBlock.setPermissionsHash(permissionsHash);
		resourceBlock.setReferenceCount(1);

		resourceBlockPersistence.update(resourceBlock, false);

		resourceBlockRoleActionLocalService.addResourceBlockRoleActions(resourceBlock, name, primKey);

		return resourceBlock;
	}

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

		return getPermissionsHash(resourcePermissions);
	}

	public String getPermissionsHash(
		List<ResourcePermission> resourcePermissions) {

		ByteBuffer buffer =
			ByteBuffer.allocate(resourcePermissions.size() * 16);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			buffer.putLong(resourcePermission.getRoleId());
			buffer.putLong(resourcePermission.getActionIds());
		}

		buffer.flip();

		return DigesterUtil.digestHex("SHA-1", buffer);
	}

	/**
	 * Increments the reference count of the resource block and updates it in
	 * the database.
	 *
	 * @param  resourceBlock the resource block
	 * @throws SystemException if a system exception occurred
	 */
	public void retain(ResourceBlock resourceBlock) throws SystemException {
		resourceBlock.setReferenceCount(resourceBlock.getReferenceCount() + 1);
		updateResourceBlock(resourceBlock);
	}

	/**
	 * Decrements the reference count of the resource block and updates it in
	 * the database or deletes the resource block if the reference count reaches
	 * zero.
	 *
	 * @param  resourceBlock the resource block
	 * @throws SystemException if a system exception occurred
	 */
	public void release(ResourceBlock resourceBlock) throws SystemException {
		long referenceCount = resourceBlock.getReferenceCount() - 1;

		if (referenceCount <= 0) {
			deleteResourceBlock(resourceBlock);
			return;
		}

		resourceBlock.setReferenceCount(referenceCount);
		updateResourceBlock(resourceBlock);
	}

	/**
	 * Updates which resource block the resource is a member of, and updates it
	 * in the database. Automatically retains, releases, and creates resource
	 * blocks as necessary.
	 *
	 * @param  resource the resource block
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @throws SystemException if a system exception occurred
	 */
	public void updateResourceBlockId(
			PermissionedModel resource, String name, String primKey)
		throws SystemException {

		ResourceBlock resourceBlock;

		try {
			resourceBlock =
				getResourceBlock(resource.getResourceBlockId());
		}
		catch (PortalException e) {
			throw new SystemException(e);
		}

		String newPermissionsHash = getPermissionsHash(name, primKey);
		String currentPermissionsHash =
			resourceBlock.getPermissionsHash();

		// If the permissions hash is still the same, nothing needs to be done

		if (currentPermissionsHash.equals(newPermissionsHash)) {
			return;
		}

		// Otherwise, release the old resource block

		release(resourceBlock);

		resourceBlock =
			resourceBlockPersistence.fetchByPermissionsHash(newPermissionsHash);

		if (resourceBlock == null) {
			resourceBlock = addResourceBlock(newPermissionsHash, name, primKey);
		}
		else {
			retain(resourceBlock);
		}

		resource.setResourceBlockId(resourceBlock.getResourceBlockId());
		resource.save();
	}

}