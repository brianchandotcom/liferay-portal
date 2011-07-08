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
	 * Adds a resource block and creates associations between it and the roles
	 * specified in the resource permissions. The resource block will have an
	 * initial reference count of one.
	 *
	 * @param  companyId the primary key of the resource block's company
	 * @param  groupId the primary key of the resource block's group
	 * @param  permissionsHash the resource block's permissions hash
	 * @param  resourcePermissions the resource permissions
	 * @return the new resource block
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlock addResourceBlock(
			long companyId, long groupId, String name, String permissionsHash,
			List<ResourcePermission> resourcePermissions)
		throws SystemException {

		long resourceBlockId = counterLocalService.increment();

		ResourceBlock resourceBlock =
			resourceBlockPersistence.create(resourceBlockId);

		resourceBlock.setCompanyId(companyId);
		resourceBlock.setGroupId(groupId);
		resourceBlock.setName(name);
		resourceBlock.setPermissionsHash(permissionsHash);
		resourceBlock.setReferenceCount(1);

		resourceBlockPersistence.update(resourceBlock, false);

		resourceBlockPermissionLocalService.addResourceBlockPermissions(
			resourceBlockId, resourcePermissions);

		return resourceBlock;
	}

	/**
	 * Returns the permissions hash for the resource. The permissions hash is a
	 * representation of all the roles with access to the resource along with
	 * the actions they can perform.
	 *
	 * @param  companyId the primary key of the resource's company
	 * @param  groupId the primary key of the resource's group
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @return the permissions hash for the resource
	 * @throws SystemException if a system exception occurred
	 */
	public String getPermissionsHash(
			long companyId, long groupId, String name, String primKey)
		throws SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionLocalService.getResourceResourcePermissions(
			companyId, groupId, name, primKey);

		return getPermissionsHash(resourcePermissions);
	}

	/**
	 * Returns the permissions hash of the resource permissions. The permissions
	 * hash is a representation of all the roles with access to the resource
	 * along with the actions they can perform.
	 *
	 * @param  resourcePermissions the resource permissions
	 * @return the permissions hash of the resource permissions
	 */
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

	@Override
	public void deleteResourceBlock(long resourceBlockId)
		throws PortalException, SystemException {

		ResourceBlock resourceBlock =
			resourceBlockPersistence.findByPrimaryKey(resourceBlockId);

		deleteResourceBlock(resourceBlock);
	}

	@Override
	public void deleteResourceBlock(ResourceBlock resourceBlock)
		throws SystemException {

		resourceBlockPermissionLocalService.deleteResourceBlockPermissions(
				resourceBlock.getPrimaryKey());

		resourceBlockPersistence.remove(resourceBlock);
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
	 * blocks as necessary. Only use this method for resources that do not
	 * belong to a group, such as users.
	 *
	 * @param  companyId the primary key of the resource's company
	 * @param  resource the resource
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @throws SystemException if a system exception occurred
	 */
	public void updateResourceBlockId(
			long companyId, PermissionedModel resource, String name,
			String primKey)
		throws SystemException {

		updateResourceBlockId(companyId, 0, resource, name, primKey);
	}

	/**
	 * Updates which resource block the resource is a member of, and updates it
	 * in the database. Automatically retains, releases, and creates resource
	 * blocks as necessary.
	 *
	 * @param  companyId the primary key of the resource's company
	 * @param  groupId the primary key of the resource's group
	 * @param  resource the resource
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @throws SystemException if a system exception occurred
	 */
	public void updateResourceBlockId(
			long companyId, long groupId, PermissionedModel resource,
			String name, String primKey)
		throws SystemException {

		ResourceBlock resourceBlock;

		try {
			resourceBlock =
				getResourceBlock(resource.getResourceBlockId());
		}
		catch (PortalException e) {
			throw new SystemException(e);
		}

		List<ResourcePermission> resourcePermissions =
			resourcePermissionLocalService.getResourceResourcePermissions(
			companyId, groupId, name, primKey);

		String newPermissionsHash = getPermissionsHash(resourcePermissions);

		String currentPermissionsHash =	resourceBlock.getPermissionsHash();

		// If the permissions hash is still the same, nothing needs to be done

		if (currentPermissionsHash.equals(newPermissionsHash)) {
			return;
		}

		// Otherwise, release the old resource block

		release(resourceBlock);

		resourceBlock =
			resourceBlockPersistence.fetchByG_P(groupId, newPermissionsHash);

		if (resourceBlock == null) {
			resourceBlock =
				addResourceBlock(companyId, groupId, name, newPermissionsHash,
				resourcePermissions);
		}
		else {
			retain(resourceBlock);
		}

		resource.setResourceBlockId(resourceBlock.getResourceBlockId());
		resource.save();
	}

}