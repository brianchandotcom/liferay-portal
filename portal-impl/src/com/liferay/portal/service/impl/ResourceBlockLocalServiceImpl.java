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

import com.liferay.portal.NoSuchResourceBlockException;
import com.liferay.portal.ResourceBlocksNotSupportedException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourceBlockConstants;
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceBlockIdsBag;
import com.liferay.portal.service.PersistedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.base.ResourceBlockLocalServiceBaseImpl;

import java.nio.ByteBuffer;

import java.util.ArrayList;
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

	public void addCompanyScopePermission(
			long companyId, String name, long roleId, String actionId)
		throws PortalException, SystemException {

		addCompanyScopePermissions(
			companyId, name, roleId, getActionId(name, actionId));
	}

	public void addCompanyScopePermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		updateCompanyScopePermissions(
			companyId, name, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_ADD);
	}

	public void addGroupScopePermission(
			long companyId, long groupId, String name, long roleId,
			String actionId)
		throws PortalException, SystemException {

		addGroupScopePermissions(
			companyId, groupId, name, roleId, getActionId(name, actionId));
	}

	public void addGroupScopePermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIdsLong)
		throws SystemException {

		updateGroupScopePermissions(
			companyId, groupId, name, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_ADD);
	}

	public void addIndividualScopePermission(
			long companyId, long groupId, String name, long primKey,
			long roleId, String actionId)
		throws PortalException, SystemException {

		addIndividualScopePermissions(
			companyId, groupId, name, primKey, roleId,
			getActionId(name, actionId));
	}

	public void addIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, long actionIdsLong)
		throws PortalException, SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, primKey, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_ADD);
	}

	/**
	 * Adds a resource block if necessary and associates the resource block
	 * permissions with it. The resource block will have an initial reference
	 * count of one.
	 *
	 * @param  companyId the primary key of the resource block's company
	 * @param  groupId the primary key of the resource block's group
	 * @return the new resource block
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlock addResourceBlock(
			long companyId, long groupId, String name, String permissionsHash,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
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

		resourceBlockPermissionLocalService.addResourceBlockPermissions(
			resourceBlockId, resourceBlockPermissionsContainer);

		return resourceBlock;
	}

	public ResourceBlock convertResourcePermissions(
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

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			resourceBlockPermissionLocalService.
			getResourceBlockPermissionsContainer(
			companyId, groupId, name, primKey);

		String permissionsHash =
			getPermissionsHash(resourceBlockPermissionsContainer);

		if (resourceBlock != null) {
			if (resourceBlock.getPermissionsHash() == permissionsHash) {
				return resourceBlock;
			}

			release(resourceBlock);
		}

		resourceBlock = updateResourceBlockId(
			companyId, groupId, name, model, permissionsHash,
			resourceBlockPermissionsContainer);

		return resourceBlock;
	}

	public long getActionId(String name, String actionId)
		throws PortalException {

		ResourceAction resourcAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		return resourcAction.getBitwiseValue();
	}

	// Should getActionIds be in ResourceActionLocalService?

	public long getActionIds(String name, List<String> actionIds)
		throws PortalException {

		long actionIdsLong = 0;

		for (String actionId : actionIds) {
			ResourceAction resourceAction =
				resourceActionLocalService.getResourceAction(name, actionId);

			actionIdsLong |= resourceAction.getBitwiseValue();
		}

		return actionIdsLong;
	}

	public List<String> getActionIds(String name, long actionIdsLong)
		throws SystemException {

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(name);

		List<String> actionIds = new ArrayList<String>();

		for (ResourceAction resourceAction : resourceActions) {
			if ((actionIdsLong & resourceAction.getBitwiseValue()) ==
				resourceAction.getBitwiseValue()) {

				actionIds.add(resourceAction.getActionId());
			}
		}

		return actionIds;
	}

	public List<String> getCompanyScopePermissions(
			ResourceBlock resourceBlock, long roleId)
		throws SystemException {

		return getActionIds(
			resourceBlock.getName(),
			resourceTypePermissionLocalService.getCompanyScopeActionIds(
			resourceBlock.getCompanyId(), resourceBlock.getName(), roleId));
	}

	public List<String> getGroupScopePermissions(
			ResourceBlock resourceBlock, long roleId)
		throws SystemException {

		return getActionIds(
			resourceBlock.getName(),
			resourceTypePermissionLocalService.getGroupScopeActionIds(
			resourceBlock.getCompanyId(), resourceBlock.getGroupId(),
			resourceBlock.getName(), roleId));
	}

	public List<String> getPermissions(
			ResourceBlock resourceBlock, long roleId)
		throws SystemException {

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			resourceBlockPermissionLocalService.
			getResourceBlockPermissionsContainer(resourceBlock.getPrimaryKey());

		return getActionIds(
			resourceBlock.getName(),
			resourceBlockPermissionsContainer.getActionIds(
			roleId));
	}

	public ResourceBlock getResourceBlock(String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel model = getPermissionedModel(name, primKey);
		return getResourceBlock(model.getResourceBlockId());
	}

	public PermissionedModel getPermissionedModel(String name, long primKey)
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

	public List<Long> getResourceBlockIds(
			ResourceBlockIdsBag resourceBlockIdsBag, String name,
			String actionId)
		throws PortalException {

		long actionIdsLong = getActionId(name, actionId);

		return resourceBlockIdsBag.getResourceBlockIds(actionIdsLong);
	}

	public ResourceBlockIdsBag getResourceBlockIdsBag(
			long companyId, long groupId, String name, long[] roleIds)
		throws SystemException {

		return resourceBlockFinder.findByC_G_N_R(
			companyId, groupId, name, roleIds);
	}

	public boolean hasPermission(
			String name, long primKey, String actionId,
			ResourceBlockIdsBag resourceBlockIdsBag)
		throws PortalException, SystemException {

		PermissionedModel model = getPermissionedModel(name, primKey);
		return hasPermission(name, model, actionId, resourceBlockIdsBag);
	}

	public boolean hasPermission(
			String name, PermissionedModel model, String actionId,
			ResourceBlockIdsBag resourceBlockIdsBag)
		throws PortalException {

		long actionIdsLong = getActionId(name, actionId);

		return resourceBlockIdsBag.hasResourceBlockId(
			model.getResourceBlockId(), actionIdsLong);
	}

	public boolean isSupported(String name) {
		return PersistedModelLocalServiceRegistryUtil.
			isPermissionedModelLocalService(name);
	}

	public void releasePermissionedModelResourceBlock(String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel model = getPermissionedModel(name, primKey);

		try {
			release(model.getResourceBlockId());
		}
		catch (NoSuchResourceBlockException nsrbe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Resource block " + model.getResourceBlockId() +
					" missing for " + name + "#" + primKey);
			}
		}
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

	public void removeAllGroupScopePermissions(
			long companyId, String name, long roleId, String actionId)
		throws PortalException, SystemException {

		removeAllGroupScopePermissions(
			companyId, name, roleId, getActionId(name, actionId));
	}

	public void removeAllGroupScopePermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		List<ResourceTypePermission> resourceTypePermissions =
			resourceTypePermissionLocalService.findByGroupScope(
			companyId, name, roleId);

		for (ResourceTypePermission resourceTypePermission :
			resourceTypePermissions) {

			removeGroupScopePermissions(
				companyId, resourceTypePermission.getGroupId(), name, roleId,
				actionIdsLong);
		}
	}

	public void removeCompanyScopePermission(
			long companyId, String name, long roleId, String actionId)
		throws PortalException, SystemException {

		removeCompanyScopePermissions(
			companyId, name, roleId, getActionId(name, actionId));
	}

	public void removeCompanyScopePermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		updateCompanyScopePermissions(
			companyId, name, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_REMOVE);
	}

	public void removeGroupScopePermission(
			long companyId, long groupId, String name, long roleId,
			String actionId)
		throws PortalException, SystemException {

		removeGroupScopePermissions(
			companyId, groupId, name, roleId, getActionId(name, actionId));
	}

	public void removeGroupScopePermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIdsLong)
		throws SystemException {

		updateGroupScopePermissions(
			companyId, groupId, name, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_REMOVE);
	}

	public void removeIndividualScopePermission(
			long companyId, long groupId, String name, long primKey,
			long roleId, String actionId)
		throws PortalException, SystemException {

		removeIndividualScopePermissions(
			companyId, groupId, name, primKey, roleId,
			getActionId(name, actionId));
	}

	public void removeIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, long actionIdsLong)
		throws PortalException, SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, primKey, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_REMOVE);
	}

	public void setCompanyScopePermissions(
			long companyId, String name, long roleId, List<String> actionIds)
		throws PortalException, SystemException {

		setCompanyScopePermissions(
			companyId, name, roleId, getActionIds(name, actionIds));
	}

	public void setCompanyScopePermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		updateCompanyScopePermissions(
			companyId, name, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_SET);
	}

	public void setGroupScopePermissions(
			long companyId, long groupId, String name, long roleId,
			List<String> actionIds)
		throws PortalException, SystemException {

		setGroupScopePermissions(
			companyId, groupId, name, roleId,
			getActionIds(name, actionIds));
	}

	public void setGroupScopePermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIdsLong)
		throws SystemException {

		updateGroupScopePermissions(
			companyId, groupId, name, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_SET);
	}

	public void setIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, List<String> actionIds)
		throws PortalException, SystemException {

		setIndividualScopePermissions(
			companyId, groupId, name, primKey, roleId,
			getActionIds(name, actionIds));
	}

	public void setIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, long actionIdsLong)
		throws PortalException, SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, primKey, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_SET);
	}

	public void verifyResourceBlockId(
			long companyId, String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel model = getPermissionedModel(name, primKey);

		ResourceBlock resourceBlock =
			resourceBlockPersistence.fetchByPrimaryKey(
			model.getResourceBlockId());

		if (resourceBlock == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Resource block " + model.getResourceBlockId() +
					" missing for " + name + "#" + primKey);
			}

			long groupId = 0;
			long ownerId = 0;

			if (model instanceof GroupedModel) {
				groupId = ((GroupedModel)model).getGroupId();
				ownerId = ((GroupedModel)model).getUserId();
			}
			else if (model instanceof AuditedModel) {
				ownerId = ((AuditedModel)model).getUserId();
			}

			resourceLocalService.addResources(
				companyId, groupId, ownerId, name, primKey, false, true, true);
		}
	}

	protected void updatePermissions(
			List<ResourceBlock> resourceBlocks, long roleId, long actionIdsLong,
			int operator)
		throws SystemException {

		for (ResourceBlock resourceBlock : resourceBlocks) {
			resourceBlockPermissionLocalService.updateResourceBlockPermission(
				resourceBlock.getPrimaryKey(), roleId, actionIdsLong, operator);

			updatePermissionsHash(resourceBlock);
		}
	}

	protected void updateCompanyScopePermissions(
			long companyId, String name, long roleId,
			long actionIdsLong, int operator)
		throws SystemException {

		resourceTypePermissionLocalService.
			updateCompanyScopeResourceTypePermissions(
			companyId, name, roleId, actionIdsLong, operator);

		List<ResourceBlock> resourceBlocks =
			resourceBlockPersistence.findByC_N(companyId, name);

		updatePermissions(resourceBlocks, roleId, actionIdsLong, operator);

		PermissionCacheUtil.clearCache();
	}

	protected void updateGroupScopePermissions(
			long companyId, long groupId, String name, long roleId,
			long actionIdsLong, int operator)
		throws SystemException {

		resourceTypePermissionLocalService.
			updateGroupScopeResourceTypePermissions(
			companyId, groupId, name, roleId, actionIdsLong, operator);

		List<ResourceBlock> resourceBlocks =
			resourceBlockPersistence.findByC_G_N(companyId, groupId, name);

		updatePermissions(resourceBlocks, roleId, actionIdsLong, operator);

		PermissionCacheUtil.clearCache();
	}

	protected void updateIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, long actionIdsLong, int operator)
		throws PortalException, SystemException {

		PermissionedModel model = getPermissionedModel(name, primKey);

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
		}

		long oldActionIdsLong =
			resourceBlockPermissionsContainer.getActionIds(roleId);

		if (operator == ResourceBlockConstants.OPERATOR_ADD) {
			actionIdsLong |= oldActionIdsLong;
		}
		else if (operator == ResourceBlockConstants.OPERATOR_REMOVE) {
			actionIdsLong =
				oldActionIdsLong & (~actionIdsLong);
		}

		if (resourceBlock != null) {
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

		PermissionCacheUtil.clearCache();
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

	private static Log _log = LogFactoryUtil.getLog(
		ResourceBlockLocalServiceImpl.class);

}