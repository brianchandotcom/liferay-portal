/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.transaction.Transactional;
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
import com.liferay.portal.model.impl.ResourceBlockImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceBlockIdsBag;
import com.liferay.portal.service.PersistedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.base.ResourceBlockLocalServiceBaseImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.sql.DataSource;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * resource blocks.
 *
 * @author Connor McKay
 * @author Shuyang Zhou
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

	@Transactional(
		isolation = Isolation.READ_COMMITTED,
		propagation = Propagation.REQUIRES_NEW)
	public void releasePermissionedModelResourceBlock(
			PermissionedModel permissionedModel)
		throws SystemException {

		releaseResourceBlock(permissionedModel.getResourceBlockId());
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
	 * @throws SystemException if a system exception occurred
	 */
	@Transactional(
		isolation = Isolation.READ_COMMITTED,
		propagation = Propagation.REQUIRES_NEW)
	public void releaseResourceBlock(long resourceBlockId)
		throws SystemException {

		Session session = resourceBlockPersistence.openSession();

		while (true) {
			try {
				String sql = CustomSQLUtil.get(_RELEASE_RESOURCE_BLOCK);

				SQLQuery sqlQuery = session.createSQLQuery(sql);

				QueryPos qPos = QueryPos.getInstance(sqlQuery);

				qPos.add(resourceBlockId);

				if (sqlQuery.executeUpdate() > 0) {
					ResourceBlock resourceBlock = (ResourceBlock)session.get(
						ResourceBlockImpl.class, Long.valueOf(resourceBlockId));

					if (resourceBlock.getReferenceCount() == 0) {
						sql = CustomSQLUtil.get(_DELETE_RESOURCE_BLOCK);

						sqlQuery = session.createSQLQuery(sql);

						qPos = QueryPos.getInstance(sqlQuery);

						qPos.add(resourceBlockId);

						sqlQuery.executeUpdate();
					}
				}

				resourceBlockPersistence.closeSession(session);

				break;
			}
			catch (ORMException orme) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to decrement reference count for resource " +
							"block " + resourceBlockId + ". Retrying.");
				}
			}
		}
	}

	/**
	 * Decrements the reference count of the resource block and updates it in
	 * the database or deletes the resource block if the reference count reaches
	 * zero.
	 *
	 * @param  resourceBlock the resource block
	 * @throws SystemException if a system exception occurred
	 */
	@Transactional(
		isolation = Isolation.READ_COMMITTED,
		propagation = Propagation.REQUIRES_NEW)
	public void releaseResourceBlock(ResourceBlock resourceBlock)
		throws SystemException {

		releaseResourceBlock(resourceBlock.getResourceBlockId());
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

			resourceBlockLocalService.releaseResourceBlock(resourceBlock);
		}

		resourceBlockPermissionsContainer.setPermissions(roleId, actionIdsLong);

		String permissionsHash =
			resourceBlockPermissionsContainer.getPermissionsHash();

		resourceBlockLocalService.updateResourceBlockId(
			companyId, groupId, name, permissionedModel, permissionsHash,
			resourceBlockPermissionsContainer);

		PermissionCacheUtil.clearCache();
	}

	@Transactional(
		isolation = Isolation.READ_COMMITTED,
		propagation = Propagation.REQUIRES_NEW)
	public ResourceBlock updateResourceBlockId(
			long companyId, long groupId, String name,
			final PermissionedModel permissionedModel, String permissionsHash,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws SystemException {

		ResourceBlock resourceBlock = null;

		while (true) {
			resourceBlock = resourceBlockPersistence.fetchByC_G_N_P(
				companyId, groupId, name, permissionsHash, false);

			if (resourceBlock == null) {
				try {
					resourceBlock = addResourceBlock(
						companyId, groupId, name, permissionsHash,
						resourceBlockPermissionsContainer);

					// On success, manually flush to enforce database row lock

					resourceBlockPersistence.flush();
				}
				catch (SystemException se) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to add a new resource block. Retrying");
					}

					// On failure, cancel all pending persistent entities

					Session session =
						resourceBlockPersistence.getCurrentSession();

					session.clear();

					DB db = DBFactoryUtil.getDB();

					if (!db.isSupportsQueryingAfterException()) {
						DataSource dataSource =
							resourceBlockPersistence.getDataSource();

						Connection connection =
							CurrentConnectionUtil.getConnection(dataSource);

						try {
							connection.rollback();

							connection.setAutoCommit(false);
						}
						catch (SQLException sqle) {
							throw new SystemException(sqle);
						}
					}

					continue;
				}

				break;
			}

			Session session = resourceBlockPersistence.openSession();

			try {
				String sql = CustomSQLUtil.get(_RETAIN_RESOURCE_BLOCK);

				SQLQuery sqlQuery = session.createSQLQuery(sql);

				QueryPos qPos = QueryPos.getInstance(sqlQuery);

				qPos.add(resourceBlock.getResourceBlockId());

				if (sqlQuery.executeUpdate() > 0) {

					// We currently use an "update set" SQL statement to
					// increment the reference count in a discontinuous manner.
					// We can change it to an "update where" SQL statement to
					// guarantee continuous counts. We are using a SQL statement
					// that allows for a discontinuous count since it is cheaper
					// and continuity is not required.

					resourceBlock.setReferenceCount(
						resourceBlock.getReferenceCount() + 1);

					break;
				}
			}
			catch (ORMException orme) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to increment reference count for resource " +
							"block " + resourceBlock.getResourceBlockId() +
								". Retrying");
				}
			}
			finally {

				// Prevent Hibernate from automatically flushing out the first
				// level cache since that will lead to a regular update that
				// will overwrite the previous update causing a lost update.

				session.evict(resourceBlock);

				resourceBlockPersistence.closeSession(session);
			}
		}

		permissionedModel.setResourceBlockId(
			resourceBlock.getResourceBlockId());

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				permissionedModel.persist();

				return null;
			}

		};

		TransactionCommitCallbackRegistryUtil.registerCallback(callable);

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

		String permissionsHash =
			resourceBlockPermissionsContainer.getPermissionsHash();

		resourceBlock.setPermissionsHash(permissionsHash);

		updateResourceBlock(resourceBlock);
	}

	private static final String _DELETE_RESOURCE_BLOCK =
		ResourceBlockLocalServiceImpl.class.getName() +
			".deleteResourceBlock";

	private static final String _RELEASE_RESOURCE_BLOCK =
		ResourceBlockLocalServiceImpl.class.getName() +
			".releaseResourceBlock";

	private static final String _RETAIN_RESOURCE_BLOCK =
		ResourceBlockLocalServiceImpl.class.getName() +
			".retainResourceBlock";

	private static Log _log = LogFactoryUtil.getLog(
		ResourceBlockLocalServiceImpl.class);

}