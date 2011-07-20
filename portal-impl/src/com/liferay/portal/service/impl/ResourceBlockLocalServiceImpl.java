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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourceBlockPermissionImpl;
import com.liferay.portal.service.PersistedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.base.ResourceBlockLocalServiceBaseImpl;
import com.liferay.portal.util.comparator.ResourceBlockPermissionRoleIdComparator;

import java.nio.ByteBuffer;
import java.util.ArrayList;
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
	 * Adds a resource block and associates the resource block permissions with
	 * it. The resource block will have an initial reference count of one.
	 *
	 * @param  companyId the primary key of the resource block's company
	 * @param  groupId the primary key of the resource block's group
	 * @param  permissionsHash the resource block's permissions hash
	 * @param  resourceBlockPermissions the resource block permissions
	 * @return the new resource block
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlock addResourceBlock(
			long companyId, long groupId, String name, String permissionsHash,
			List<ResourceBlockPermission> resourceBlockPermissions)
		throws SystemException {

		long resourceBlockId = counterLocalService.increment(
			ResourceBlock.class.getName());

		ResourceBlock resourceBlock =
			resourceBlockPersistence.create(resourceBlockId);

		resourceBlock.setCompanyId(companyId);
		resourceBlock.setGroupId(groupId);
		resourceBlock.setName(name);
		resourceBlock.setPermissionsHash(permissionsHash);
		resourceBlock.setReferenceCount(1);

		updateResourceBlock(resourceBlock);

		resourceBlockPermissionLocalService.updateResourceBlockPermissions(
			resourceBlockId, resourceBlockPermissions);

		return resourceBlock;
	}

	/**
	 * Returns the permissions hash of the resource permissions. The permissions
	 * hash is a representation of all the roles with access to the resource
	 * along with the actions they can perform.
	 *
	 * @param  resourceBlockPermissions the resource block permissions
	 * @return the permissions hash of the resource permissions
	 */
	public String getPermissionsHash(
		List<ResourceBlockPermission> resourceBlockPermissions) {

		resourceBlockPermissions =
			ListUtil.sort(resourceBlockPermissions,
			new ResourceBlockPermissionRoleIdComparator());

		ByteBuffer buffer =
			ByteBuffer.allocate(resourceBlockPermissions.size() * 16);

		for (ResourceBlockPermission resourceBlockPermission :
				resourceBlockPermissions) {

			buffer.putLong(resourceBlockPermission.getRoleId());
			buffer.putLong(resourceBlockPermission.getActionIds());
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

		List<ResourceBlockPermission> resourceBlockPermissions =
				new ArrayList<ResourceBlockPermission>();

		boolean addNewResourceBlockPermission = true;

		if (resourceBlock != null) {
			List<ResourceBlockPermission> oldResourceBlockPermissions =
				resourceBlockPermissionLocalService.getResourceBlockPermissions(
				resourceBlock.getPrimaryKey());

			for (ResourceBlockPermission oldResourceBlockPermission :
				oldResourceBlockPermissions) {

				ResourceBlockPermission resourceBlockPermission = new ResourceBlockPermissionImpl();
				resourceBlockPermission.setNew(true);

				if (oldResourceBlockPermission.getRoleId() == roleId) {
					if (oldResourceBlockPermission.getActionIds() == actionIdsLong) {
						return;
					}

					resourceBlockPermission.setActionIds(actionIdsLong);
					addNewResourceBlockPermission = false;
				}
				else {
					resourceBlockPermission.setActionIds(
						oldResourceBlockPermission.getActionIds());
				}

				resourceBlockPermission.setRoleId(oldResourceBlockPermission.getRoleId());
				resourceBlockPermissions.add(resourceBlockPermission);
			}

			release(resourceBlock);
		}

		if (addNewResourceBlockPermission) {
			ResourceBlockPermission resourceBlockPermission = new ResourceBlockPermissionImpl();
			resourceBlockPermission.setActionIds(actionIdsLong);
			resourceBlockPermission.setRoleId(roleId);
			resourceBlockPermissions.add(resourceBlockPermission);
		}

		updateResourceBlockId(companyId, groupId, name, model,
			resourceBlockPermissions);
	}

	public ResourceBlock verifyResourceBlockId(
			long companyId, String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel model = getPermissionedModel(name, primKey);

		long groupId = 0;

		if (model instanceof GroupedModel) {
			groupId = ((GroupedModel)model).getGroupId();
		}

		ResourceBlock resourceBlock =
			resourceBlockPersistence.fetchByPrimaryKey(
			model.getResourceBlockId());

		if (resourceBlock == null) {
			List<ResourcePermission> resourcePermissions =
				resourcePermissionLocalService.getResourceResourcePermissions(
				companyId, groupId, name, String.valueOf(primKey));

			List<ResourceBlockPermission> resourceBlockPermissions =
				resourceBlockPermissionLocalService.getResourceBlockPermissions(
				resourcePermissions);

			resourceBlock = updateResourceBlockId(
				companyId, groupId, name, model, resourceBlockPermissions);
		}

		return resourceBlock;
	}

	public void updatePermissionsHash(ResourceBlock resourceBlock)
		throws SystemException {

		List<ResourceBlockPermission> resourceBlockPermissions =
			resourceBlockPermissionLocalService.getResourceBlockPermissions(
			resourceBlock.getPrimaryKey());

		String permissionsHash = getPermissionsHash(resourceBlockPermissions);
		resourceBlock.setPermissionsHash(permissionsHash);
		updateResourceBlock(resourceBlock);
	}

	protected PermissionedModel getPermissionedModel(String name, long primKey)
		throws PortalException, SystemException {

		PersistedModelLocalService localService =
			PersistedModelLocalServiceRegistryUtil.
			getPersistedModelLocalService(name);

		if (localService == null) {
			return null;
		}

		PersistedModel model = localService.getPersistedModel(primKey);

		if (model == null) {
			return null;
		}

		if (model instanceof PermissionedModel) {
			return (PermissionedModel)model;
		}

		return null;
	}

	protected void setPermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		List<ResourceBlock> resourceBlocks =
			resourceBlockPersistence.findByC_N(companyId, name);

		setPermissions(resourceBlocks, roleId, actionIdsLong);
	}

	protected void setPermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIdsLong)
		throws SystemException {

		List<ResourceBlock> resourceBlocks =
			resourceBlockPersistence.findByC_G_N(companyId, groupId, name);

		setPermissions(resourceBlocks, roleId, actionIdsLong);
	}

	protected void setPermissions(
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
					resourceBlockPermissionLocalService.
					addResourceBlockPermission(
					resourceBlock.getPrimaryKey(), roleId, actionIdsLong);
			}
			else {
				if (actionIdsLong == 0) {
					resourceBlockPermissionLocalService.
					deleteResourceBlockPermission(resourceBlockPermission);
				}
				else {
					resourceBlockPermission.setActionIds(actionIdsLong);
					resourceBlockPermissionLocalService.
						updateResourceBlockPermission(resourceBlockPermission);
				}
			}

			updatePermissionsHash(resourceBlock);
		}
	}

	protected ResourceBlock updateResourceBlockId(long companyId, long groupId,
			String name, PermissionedModel model,
			List<ResourceBlockPermission> resourceBlockPermissions)
			throws SystemException {

		String permissionsHash = getPermissionsHash(resourceBlockPermissions);

		ResourceBlock resourceBlock = resourceBlockPersistence.fetchByC_G_N_P(
			companyId, groupId, name, permissionsHash);

		if (resourceBlock == null) {
			resourceBlock =
				addResourceBlock(companyId, groupId, name, permissionsHash,
				resourceBlockPermissions);
		}
		else {
			retain(resourceBlock);
		}

		model.setResourceBlockId(resourceBlock.getResourceBlockId());
		model.persist();

		return resourceBlock;
	}

}