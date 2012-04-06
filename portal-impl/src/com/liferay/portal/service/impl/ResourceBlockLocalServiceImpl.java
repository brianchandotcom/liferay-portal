/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
import com.liferay.portal.security.permission.PermissionThreadLocal;
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

		updateCompanyScopePermissions(
			companyId, name, roleId, getActionId(name, actionId),
			ResourceBlockConstants.OPERATOR_ADD);
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

		updateGroupScopePermissions(
			companyId, groupId, name, roleId, getActionId(name, actionId),
			ResourceBlockConstants.OPERATOR_ADD);
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

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId,
			getActionId(name, actionId), ResourceBlockConstants.OPERATOR_ADD);
	}

	public void addIndividualScopePermission(
			long companyId, long groupId, String name,
			PermissionedModel permissionedModel, long roleId, String actionId)
		throws PortalException, SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId,
			getActionId(name, actionId), ResourceBlockConstants.OPERATOR_ADD);
	}

	public void addIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, long actionIdsLong)
		throws PortalException, SystemException {

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_ADD);
	}

	public void addIndividualScopePermissions(
			long companyId, long groupId, String name,
			PermissionedModel permissionedModel, long roleId,
			long actionIdsLong)
		throws SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_ADD);
	}

	/**
	 * Adds a resource block if necessary and associates the resource block
	 * permissions with it. The resource block will have an initial reference
	 * count of one.
	 *
	 * @param  companyId the primary key of the resource block's company
	 * @param  groupId the primary key of the resource block's group
	 * @param  name the resource block's name
	 * @param  permissionsHash the resource block's permission hash
	 * @param  resourceBlockPermissionsContainer the resource block's
	 *         permissions container
	 * @return the new resource block
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlock addResourceBlock(
			long companyId, long groupId, String name, String permissionsHash,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws SystemException {

		long resourceBlockId = counterLocalService.increment(
			ResourceBlock.class.getName());

		ResourceBlock resourceBlock = resourceBlockPersistence.create(
			resourceBlockId);

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

	@Override
	public ResourceBlock deleteResourceBlock(long resourceBlockId)
		throws PortalException, SystemException {

		ResourceBlock resourceBlock = resourceBlockPersistence.findByPrimaryKey(
			resourceBlockId);

		return deleteResourceBlock(resourceBlock);
	}

	@Override
	public ResourceBlock deleteResourceBlock(ResourceBlock resourceBlock)
		throws SystemException {

		resourceBlockPermissionLocalService.deleteResourceBlockPermissions(
			resourceBlock.getPrimaryKey());

		return resourceBlockPersistence.remove(resourceBlock);
	}

	public long getActionId(String name, String actionId)
		throws PortalException {

		ResourceAction resourcAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		return resourcAction.getBitwiseValue();
	}

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

		long actionIdsLong =
			resourceTypePermissionLocalService.getCompanyScopeActionIds(
				resourceBlock.getCompanyId(), resourceBlock.getName(), roleId);

		return getActionIds(resourceBlock.getName(), actionIdsLong);
	}

	public List<String> getGroupScopePermissions(
			ResourceBlock resourceBlock, long roleId)
		throws SystemException {

		long actionIdsLong =
			resourceTypePermissionLocalService.getGroupScopeActionIds(
				resourceBlock.getCompanyId(), resourceBlock.getGroupId(),
				resourceBlock.getName(), roleId);

		return getActionIds(resourceBlock.getName(), actionIdsLong);
	}

	public PermissionedModel getPermissionedModel(String name, long primKey)
		throws PortalException, SystemException {

		PersistedModelLocalService persistedModelLocalService =
			PersistedModelLocalServiceRegistryUtil.
				getPersistedModelLocalService(name);

		if (persistedModelLocalService == null) {
			throw new ResourceBlocksNotSupportedException();
		}

		PersistedModel persistedModel =
			persistedModelLocalService.getPersistedModel(primKey);

		try {
			return (PermissionedModel)persistedModel;
		}
		catch (ClassCastException cce) {
			throw new ResourceBlocksNotSupportedException();
		}
	}

	public List<String> getPermissions(ResourceBlock resourceBlock, long roleId)
		throws SystemException {

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			resourceBlockPermissionLocalService.
				getResourceBlockPermissionsContainer(
					resourceBlock.getPrimaryKey());

		long actionIdsLong = resourceBlockPermissionsContainer.getActionIds(
			roleId);

		return getActionIds(resourceBlock.getName(), actionIdsLong);
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

		// long is 8 bytes, there are 2 longs per permission, so preallocate
		// byte buffer to 16 * the number of permissions.

		ByteBuffer byteBuffer = ByteBuffer.allocate(permissions.size() * 16);

		for (Map.Entry<Long, Long> entry : permissions.entrySet()) {
			byteBuffer.putLong(entry.getKey());
			byteBuffer.putLong(entry.getValue());
		}

		byteBuffer.flip();

		return DigesterUtil.digestHex(Digester.SHA_1, byteBuffer);
	}

	public ResourceBlock getResourceBlock(String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		return getResourceBlock(permissionedModel.getResourceBlockId());
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

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		return hasPermission(
			name, permissionedModel, actionId, resourceBlockIdsBag);
	}

	public boolean hasPermission(
			String name, PermissionedModel permissionedModel, String actionId,
			ResourceBlockIdsBag resourceBlockIdsBag)
		throws PortalException {

		long actionIdsLong = getActionId(name, actionId);

		return resourceBlockIdsBag.hasResourceBlockId(
			permissionedModel.getResourceBlockId(), actionIdsLong);
	}

	public boolean isSupported(String name) {
		return PersistedModelLocalServiceRegistryUtil.
			isPermissionedModelLocalService(name);
	}

	public void releasePermissionedModelResourceBlock(
			PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		try {
			releaseResourceBlock(permissionedModel.getResourceBlockId());
		}
		catch (NoSuchResourceBlockException nsrbe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource block " + permissionedModel.getResourceBlockId() +
						" missing");
			}
		}
	}

	public void releasePermissionedModelResourceBlock(String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		releasePermissionedModelResourceBlock(permissionedModel);
	}

	/**
	 * Decrements the reference count of the resource block and updates it in
	 * the database or deletes the resource block if the reference count reaches
	 * zero.
	 *
	 * @param  resourceBlockId the primary key of the resource block
	 * @throws PortalException if a resource block with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void releaseResourceBlock(long resourceBlockId)
		throws PortalException, SystemException {

		ResourceBlock resourceBlock = getResourceBlock(resourceBlockId);

		releaseResourceBlock(resourceBlock);
	}

	/**
	 * Decrements the reference count of the resource block and updates it in
	 * the database or deletes the resource block if the reference count reaches
	 * zero.
	 *
	 * @param  resourceBlock the resource block
	 * @throws SystemException if a system exception occurred
	 */
	public void releaseResourceBlock(ResourceBlock resourceBlock)
		throws SystemException {

		long referenceCount = resourceBlock.getReferenceCount() - 1;

		if (referenceCount <= 0) {
			deleteResourceBlock(resourceBlock);
			return;
		}

		resourceBlock.setReferenceCount(referenceCount);

		updateResourceBlock(resourceBlock);
	}

	public void removeAllGroupScopePermissions(
			long companyId, String name, long roleId, long actionIdsLong)
		throws SystemException {

		List<ResourceTypePermission> resourceTypePermissions =
			resourceTypePermissionLocalService.
				getGroupScopeResourceTypePermissions(companyId, name, roleId);

		for (ResourceTypePermission resourceTypePermission :
				resourceTypePermissions) {

			removeGroupScopePermissions(
				companyId, resourceTypePermission.getGroupId(), name, roleId,
				actionIdsLong);
		}
	}

	public void removeAllGroupScopePermissions(
			long companyId, String name, long roleId, String actionId)
		throws PortalException, SystemException {

		removeAllGroupScopePermissions(
			companyId, name, roleId, getActionId(name, actionId));
	}

	public void removeCompanyScopePermission(
			long companyId, String name, long roleId, String actionId)
		throws PortalException, SystemException {

		updateCompanyScopePermissions(
			companyId, name, roleId, getActionId(name, actionId),
			ResourceBlockConstants.OPERATOR_REMOVE);
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

		updateGroupScopePermissions(
			companyId, groupId, name, roleId, getActionId(name, actionId),
			ResourceBlockConstants.OPERATOR_REMOVE);
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

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId,
			getActionId(name, actionId),
			ResourceBlockConstants.OPERATOR_REMOVE);
	}

	public void removeIndividualScopePermission(
			long companyId, long groupId, String name,
			PermissionedModel permissionedModel, long roleId, String actionId)
		throws PortalException, SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId,
			getActionId(name, actionId),
			ResourceBlockConstants.OPERATOR_REMOVE);
	}

	public void removeIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, long actionIdsLong)
		throws PortalException, SystemException {

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_REMOVE);
	}

	public void removeIndividualScopePermissions(
			long companyId, long groupId, String name,
			PermissionedModel permissionedModel, long roleId,
			long actionIdsLong)
		throws SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_REMOVE);
	}

	/**
	 * Increments the reference count of the resource block and updates it in
	 * the database.
	 *
	 * @param  resourceBlockId the primary key of the resource block
	 * @throws PortalException if a resource block with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void retainResourceBlock(long resourceBlockId)
		throws PortalException, SystemException {

		ResourceBlock resourceBlock = getResourceBlock(resourceBlockId);

		retainResourceBlock(resourceBlock);
	}

	/**
	 * Increments the reference count of the resource block and updates it in
	 * the database.
	 *
	 * @param  resourceBlock the resource block
	 * @throws SystemException if a system exception occurred
	 */
	public void retainResourceBlock(ResourceBlock resourceBlock)
		throws SystemException {

		resourceBlock.setReferenceCount(resourceBlock.getReferenceCount() + 1);

		updateResourceBlock(resourceBlock);
	}

	public void setCompanyScopePermissions(
			long companyId, String name, long roleId, List<String> actionIds)
		throws PortalException, SystemException {

		updateCompanyScopePermissions(
			companyId, name, roleId, getActionIds(name, actionIds),
			ResourceBlockConstants.OPERATOR_SET);
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

		updateGroupScopePermissions(
			companyId, groupId, name, roleId, getActionIds(name, actionIds),
			ResourceBlockConstants.OPERATOR_SET);
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

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId,
			getActionIds(name, actionIds), ResourceBlockConstants.OPERATOR_SET);
	}

	public void setIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, long actionIdsLong)
		throws PortalException, SystemException {

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_SET);
	}

	public void setIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			Map<Long, String[]> roleIdsToActionIds)
		throws PortalException, SystemException {

		boolean flushEnabled = PermissionThreadLocal.isFlushEnabled();

		PermissionThreadLocal.setIndexEnabled(false);

		try {
			PermissionedModel permissionedModel = getPermissionedModel(
				name, primKey);

			for (Map.Entry<Long, String[]> entry :
					roleIdsToActionIds.entrySet()) {

				long roleId = entry.getKey();
				String[] actionIds = entry.getValue();

				updateIndividualScopePermissions(
					companyId, groupId, name, permissionedModel, roleId,
					getActionIds(name, ListUtil.fromArray(actionIds)),
					ResourceBlockConstants.OPERATOR_SET);
			}
		}
		finally {
			PermissionThreadLocal.setIndexEnabled(flushEnabled);

			PermissionCacheUtil.clearCache();
		}
	}

	public void setIndividualScopePermissions(
			long companyId, long groupId, String name,
			PermissionedModel permissionedModel, long roleId,
			List<String> actionIds)
		throws PortalException, SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId,
			getActionIds(name, actionIds), ResourceBlockConstants.OPERATOR_SET);
	}

	public void setIndividualScopePermissions(
			long companyId, long groupId, String name,
			PermissionedModel permissionedModel, long roleId,
			long actionIdsLong)
		throws SystemException {

		updateIndividualScopePermissions(
			companyId, groupId, name, permissionedModel, roleId, actionIdsLong,
			ResourceBlockConstants.OPERATOR_SET);
	}

	public void updateCompanyScopePermissions(
			long companyId, String name, long roleId, long actionIdsLong,
			int operator)
		throws SystemException {

		resourceTypePermissionLocalService.
			updateCompanyScopeResourceTypePermissions(
				companyId, name, roleId, actionIdsLong, operator);

		List<ResourceBlock> resourceBlocks = resourceBlockPersistence.findByC_N(
			companyId, name);

		updatePermissions(resourceBlocks, roleId, actionIdsLong, operator);

		PermissionCacheUtil.clearCache();
	}

	public void updateGroupScopePermissions(
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

	public void updateIndividualScopePermissions(
			long companyId, long groupId, String name,
			PermissionedModel permissionedModel, long roleId,
			long actionIdsLong, int operator)
		throws SystemException {

		ResourceBlock resourceBlock =
			resourceBlockPersistence.fetchByPrimaryKey(
				permissionedModel.getResourceBlockId());

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			null;

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

		long oldActionIdsLong = resourceBlockPermissionsContainer.getActionIds(
			roleId);

		if (operator == ResourceBlockConstants.OPERATOR_ADD) {
			actionIdsLong |= oldActionIdsLong;
		}
		else if (operator == ResourceBlockConstants.OPERATOR_REMOVE) {
			actionIdsLong = oldActionIdsLong & (~actionIdsLong);
		}

		if (resourceBlock != null) {
			if (oldActionIdsLong == actionIdsLong) {
				return;
			}

			releaseResourceBlock(resourceBlock);
		}

		resourceBlockPermissionsContainer.setPermissions(roleId, actionIdsLong);

		String permissionsHash = getPermissionsHash(
			resourceBlockPermissionsContainer);

		updateResourceBlockId(
			companyId, groupId, name, permissionedModel, permissionsHash,
			resourceBlockPermissionsContainer);

		PermissionCacheUtil.clearCache();
	}

	public ResourceBlock updateResourceBlockId(
			long companyId, long groupId, String name,
			PermissionedModel permissionedModel, String permissionsHash,
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
			retainResourceBlock(resourceBlock);
		}

		permissionedModel.setResourceBlockId(
			resourceBlock.getResourceBlockId());

		permissionedModel.persist();

		return resourceBlock;
	}

	public void verifyResourceBlockId(long companyId, String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel permissionedModel = getPermissionedModel(
			name, primKey);

		ResourceBlock resourceBlock =
				resourceBlockPersistence.fetchByPrimaryKey(
			permissionedModel.getResourceBlockId());

		if (resourceBlock == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Resource block " + permissionedModel.getResourceBlockId() +
						" missing for " + name + "#" + primKey);
			}

			long groupId = 0;
			long ownerId = 0;

			if (permissionedModel instanceof GroupedModel) {
				GroupedModel groupedModel = (GroupedModel)permissionedModel;

				groupId = groupedModel.getGroupId();
				ownerId = groupedModel.getUserId();
			}
			else if (permissionedModel instanceof AuditedModel) {
				AuditedModel auditedModel = (AuditedModel)permissionedModel;

				ownerId = auditedModel.getUserId();
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

	protected void updatePermissionsHash(ResourceBlock resourceBlock)
		throws SystemException {

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			resourceBlockPermissionLocalService.
			getResourceBlockPermissionsContainer(resourceBlock.getPrimaryKey());

		String permissionsHash = getPermissionsHash(
			resourceBlockPermissionsContainer);

		resourceBlock.setPermissionsHash(permissionsHash);

		updateResourceBlock(resourceBlock);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ResourceBlockLocalServiceImpl.class);

}