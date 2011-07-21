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

import com.liferay.portal.ResourceBlocksNotSupportedException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.service.PersistedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.base.ResourceBlockLocalServiceBaseImpl;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Manages the creation and upkeep of resource blocks and the resources they
 * contain.
 *
 * @author Connor McKay
 */
public class ResourceBlockLocalServiceImpl
	extends ResourceBlockLocalServiceBaseImpl {

	/**
	 * Adds a resource block if necessary and associates the resource block permissions with
	 * it. The resource block will have an initial reference count of one.
	 *
	 * @param  companyId the primary key of the resource block's company
	 * @param  groupId the primary key of the resource block's group
	 * @param  resourceBlockPermissions the resource block permissions
	 * @return the new resource block
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlock addResourceBlock(
			long companyId, long groupId, String name, String permissionsHash,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws SystemException {

		long resourceBlockId = counterLocalService.increment(
			ResourceBlock.class.getName());

		ResourceBlock resourceBlock = resourceBlockPersistence.create(resourceBlockId);

		resourceBlock.setCompanyId(companyId);
		resourceBlock.setGroupId(groupId);
		resourceBlock.setName(name);
		resourceBlock.setPermissionsHash(permissionsHash);
		resourceBlock.setReferenceCount(1);

		updateResourceBlock(resourceBlock);

		resourceBlockPermissionLocalService.addResourceBlockPermissions(
			resourceBlockId, resourceBlockPermissionsContainer);

		return resourceBlock;
	}

	/**
	 * Returns the permissions hash of the resource permissions. The permissions
	 * hash is a representation of all the roles with access to the resource
	 * along with the actions they can perform.
	 *
	 * @param  resourceBlockPermissionsContainer the resource block permissions
	 * @return the permissions hash of the resource permissions
	 */
	public String getPermissionsHash(
		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer) {

		SortedMap<Long, Long> permissions =
			resourceBlockPermissionsContainer.getPermissions();

		ByteBuffer buffer = ByteBuffer.allocate(permissions.size() * 16);

		for (Map.Entry<Long, Long> permission :	permissions.entrySet()) {

			buffer.putLong(permission.getKey());
			buffer.putLong(permission.getValue());
		}

		buffer.flip();

		return DigesterUtil.digestHex("SHA-1", buffer);
	}

	public List<ResourceBlock> getResourceBlocks(
			long companyId, long groupId, String name, long[] roleIds,
			long actionId)
		throws SystemException {

		return resourceBlockFinder.findByC_G_N_R_A(
			companyId, groupId, name, roleIds, actionId);
	}

	public boolean isSupported(String name) {
		return PersistedModelLocalServiceRegistryUtil.
			isPermissionedModelLocalService(name);
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
	 * @param  resourceBlockId the primary key of the resource block
	 * @throws SystemException if a system exception occurred
	 */
	public void retain(long resourceBlockId)
		throws PortalException, SystemException {

		ResourceBlock resourceBlock = getResourceBlock(resourceBlockId);
		retain(resourceBlock);
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
	 * @param  resourceBlockId the primary key of the resource block
	 * @throws SystemException if a system exception occurred
	 */
	public void release(long resourceBlockId)
		throws PortalException, SystemException {

		ResourceBlock resourceBlock = getResourceBlock(resourceBlockId);
		release(resourceBlock);
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

	public void setCompanyScopePermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		setPermissions(companyId, name, roleId, actionIdsLong);
	}

	public void setGroupTemplateScopePermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		setPermissions(companyId, name, roleId, actionIdsLong);
	}

	public void setGroupScopePermissions(
			long companyId, long groupId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		setPermissions(companyId, groupId, name, roleId, actionIdsLong);
	}

	public void setIndividualScopePermissions(
			long companyId, String name, long primKey, long roleId,
			long actionIdsLong)
		throws PortalException, SystemException {

		PermissionedModel model = getPermissionedModel(name, primKey);

		if (model == null) {
			return;
		}

		long groupId = 0;

		if (model instanceof GroupedModel) {
			groupId = ((GroupedModel)model).getGroupId();
		}

		ResourceBlock resourceBlock =
			resourceBlockPersistence.fetchByPrimaryKey(
			model.getResourceBlockId());

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer;

		if (resourceBlock == null) {
			resourceBlockPermissionsContainer =
				resourceTypePermissionLocalService.
				getResourceBlockPermissionsContainer(
				companyId, groupId, name);
		}
		else {
			resourceBlockPermissionsContainer =
				resourceBlockPermissionLocalService.
				getResourceBlockPermissionsContainer(
				resourceBlock.getPrimaryKey());

			long oldActionIdsLong =
				resourceBlockPermissionsContainer.getActionIds(roleId);

			if (oldActionIdsLong == actionIdsLong) {
				return;
			}

			release(resourceBlock);
		}

		resourceBlockPermissionsContainer.setPermissions(roleId, actionIdsLong);

		String permissionsHash =
			getPermissionsHash(resourceBlockPermissionsContainer);

		updateResourceBlockId(
			companyId, groupId, name, model, permissionsHash,
			resourceBlockPermissionsContainer);
	}

	public ResourceBlock verifyResourceBlockId(
			long companyId, String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel model = getPermissionedModel(name, primKey);

		long groupId = 0;

		if (model instanceof GroupedModel) {
			groupId = ((GroupedModel)model).getGroupId();
		}

		ResourceBlockPermissionsContainer
		resourceBlockPermissionsContainer =
			resourceBlockPermissionLocalService.
			getResourceBlockPermissionsContainer(
			companyId, groupId, name, primKey);

		ResourceBlock resourceBlock =
			resourceBlockPersistence.fetchByPrimaryKey(
			model.getResourceBlockId());

		String permissionsHash =
			getPermissionsHash(resourceBlockPermissionsContainer);

		if (permissionsHash != resourceBlock.getPermissionsHash()) {
			resourceBlock = updateResourceBlockId(
				companyId, groupId, name, model, permissionsHash,
				resourceBlockPermissionsContainer);
		}

		return resourceBlock;
	}

	protected PermissionedModel getPermissionedModel(String name, long primKey)
		throws PortalException, SystemException {

		PersistedModelLocalService localService =
			PersistedModelLocalServiceRegistryUtil.
			getPersistedModelLocalService(name);

		if (localService == null) {
			throw new ResourceBlocksNotSupportedException();
		}

		PersistedModel model = localService.getPersistedModel(primKey);

		try {
			return (PermissionedModel)model;
		}
		catch (ClassCastException e) {
			throw new ResourceBlocksNotSupportedException();
		}
	}

	protected void setPermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		resourceTypePermissionLocalService.setResourceTypePermissions(
			companyId, name, roleId, actionIdsLong);

		List<ResourceBlock> resourceBlocks =
			resourceBlockPersistence.findByC_N(companyId, name);

		setPermissions(resourceBlocks, roleId, actionIdsLong);
	}

	protected void setPermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIdsLong)
		throws SystemException {

		resourceTypePermissionLocalService.setResourceTypePermissions(
			companyId, groupId, name, roleId, actionIdsLong);

		List<ResourceBlock> resourceBlocks =
			resourceBlockPersistence.findByC_G_N(companyId, groupId, name);

		setPermissions(resourceBlocks, roleId, actionIdsLong);
	}

	protected void setPermissions(
			List<ResourceBlock> resourceBlocks, long roleId, long actionIdsLong)
		throws SystemException {

		for (ResourceBlock resourceBlock : resourceBlocks) {
			resourceBlockPermissionLocalService.setResourceBlockPermission(
				resourceBlock.getPrimaryKey(), roleId, actionIdsLong);

			updatePermissionsHash(resourceBlock);
		}
	}

	protected void updatePermissionsHash(ResourceBlock resourceBlock)
		throws SystemException {

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			resourceBlockPermissionLocalService.
			getResourceBlockPermissionsContainer(resourceBlock.getPrimaryKey());

		String permissionsHash =
			getPermissionsHash(resourceBlockPermissionsContainer);

		resourceBlock.setPermissionsHash(permissionsHash);
		updateResourceBlock(resourceBlock);
	}

	protected ResourceBlock updateResourceBlockId(long companyId, long groupId,
			String name, PermissionedModel model, String permissionsHash,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws SystemException {

		ResourceBlock resourceBlock = resourceBlockPersistence.fetchByC_G_N_P(
			companyId, groupId, name, permissionsHash);

		if (resourceBlock == null) {
			resourceBlock = addResourceBlock(
				companyId, groupId, name, permissionsHash,
				resourceBlockPermissionsContainer);
		}
		else {
			retain(resourceBlock);
		}

		model.setResourceBlockId(resourceBlock.getResourceBlockId());
		model.persist();

		return resourceBlock;
	}

}