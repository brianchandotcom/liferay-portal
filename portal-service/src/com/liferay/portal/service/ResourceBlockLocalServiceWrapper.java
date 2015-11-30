/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ResourceBlockLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockLocalService
 * @generated
 */
@ProviderType
public class ResourceBlockLocalServiceWrapper
	implements ResourceBlockLocalService,
		ServiceWrapper<ResourceBlockLocalService> {
	public ResourceBlockLocalServiceWrapper(
		ResourceBlockLocalService resourceBlockLocalService) {
		_resourceBlockLocalService = resourceBlockLocalService;
	}

	@Override
	public void addCompanyScopePermission(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.addCompanyScopePermission(companyId, name,
			roleId, actionId);
	}

	@Override
	public void addCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong) {
		_resourceBlockLocalService.addCompanyScopePermissions(companyId, name,
			roleId, actionIdsLong);
	}

	@Override
	public void addGroupScopePermission(long companyId, long groupId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.addGroupScopePermission(companyId, groupId,
			name, roleId, actionId);
	}

	@Override
	public void addGroupScopePermissions(long companyId, long groupId,
		java.lang.String name, long roleId, long actionIdsLong) {
		_resourceBlockLocalService.addGroupScopePermissions(companyId, groupId,
			name, roleId, actionIdsLong);
	}

	@Override
	public void addIndividualScopePermission(long companyId, long groupId,
		java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.addIndividualScopePermission(companyId,
			groupId, name, permissionedModel, roleId, actionId);
	}

	@Override
	public void addIndividualScopePermission(long companyId, long groupId,
		java.lang.String name, long primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.addIndividualScopePermission(companyId,
			groupId, name, primKey, roleId, actionId);
	}

	@Override
	public void addIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, long actionIdsLong) {
		_resourceBlockLocalService.addIndividualScopePermissions(companyId,
			groupId, name, permissionedModel, roleId, actionIdsLong);
	}

	@Override
	public void addIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name, long primKey, long roleId, long actionIdsLong)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.addIndividualScopePermissions(companyId,
			groupId, name, primKey, roleId, actionIdsLong);
	}

	/**
	* Adds a resource block if necessary and associates the resource block
	* permissions with it. The resource block will have an initial reference
	* count of one.
	*
	* @param companyId the primary key of the resource block's company
	* @param groupId the primary key of the resource block's group
	* @param name the resource block's name
	* @param permissionsHash the resource block's permission hash
	* @param resourceBlockPermissionsContainer the resource block's
	permissions container
	* @return the new resource block
	*/
	@Override
	public com.liferay.portal.model.ResourceBlock addResourceBlock(
		long companyId, long groupId, java.lang.String name,
		java.lang.String permissionsHash,
		com.liferay.portal.model.ResourceBlockPermissionsContainer resourceBlockPermissionsContainer) {
		return _resourceBlockLocalService.addResourceBlock(companyId, groupId,
			name, permissionsHash, resourceBlockPermissionsContainer);
	}

	/**
	* Adds the resource block to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @return the resource block that was added
	*/
	@Override
	public com.liferay.portal.model.ResourceBlock addResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		return _resourceBlockLocalService.addResourceBlock(resourceBlock);
	}

	/**
	* Creates a new resource block with the primary key. Does not add the resource block to the database.
	*
	* @param resourceBlockId the primary key for the new resource block
	* @return the new resource block
	*/
	@Override
	public com.liferay.portal.model.ResourceBlock createResourceBlock(
		long resourceBlockId) {
		return _resourceBlockLocalService.createResourceBlock(resourceBlockId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the resource block from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @return the resource block that was removed
	*/
	@Override
	public com.liferay.portal.model.ResourceBlock deleteResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		return _resourceBlockLocalService.deleteResourceBlock(resourceBlock);
	}

	/**
	* Deletes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block that was removed
	* @throws PortalException if a resource block with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ResourceBlock deleteResourceBlock(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.deleteResourceBlock(resourceBlockId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _resourceBlockLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _resourceBlockLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _resourceBlockLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _resourceBlockLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _resourceBlockLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _resourceBlockLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.ResourceBlock fetchResourceBlock(
		long resourceBlockId) {
		return _resourceBlockLocalService.fetchResourceBlock(resourceBlockId);
	}

	@Override
	public long getActionId(java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.getActionId(name, actionId);
	}

	@Override
	public long getActionIds(java.lang.String name,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.getActionIds(name, actionIds);
	}

	@Override
	public java.util.List<java.lang.String> getActionIds(
		java.lang.String name, long actionIdsLong) {
		return _resourceBlockLocalService.getActionIds(name, actionIdsLong);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _resourceBlockLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<java.lang.String> getCompanyScopePermissions(
		com.liferay.portal.model.ResourceBlock resourceBlock, long roleId) {
		return _resourceBlockLocalService.getCompanyScopePermissions(resourceBlock,
			roleId);
	}

	@Override
	public java.util.List<java.lang.String> getGroupScopePermissions(
		com.liferay.portal.model.ResourceBlock resourceBlock, long roleId) {
		return _resourceBlockLocalService.getGroupScopePermissions(resourceBlock,
			roleId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _resourceBlockLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _resourceBlockLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PermissionedModel getPermissionedModel(
		java.lang.String name, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.getPermissionedModel(name, primKey);
	}

	@Override
	public java.util.List<java.lang.String> getPermissions(
		com.liferay.portal.model.ResourceBlock resourceBlock, long roleId) {
		return _resourceBlockLocalService.getPermissions(resourceBlock, roleId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.model.ResourceBlock getResourceBlock(
		java.lang.String name, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.getResourceBlock(name, primKey);
	}

	/**
	* Returns the resource block with the primary key.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block
	* @throws PortalException if a resource block with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ResourceBlock getResourceBlock(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.getResourceBlock(resourceBlockId);
	}

	@Override
	public java.util.List<java.lang.Long> getResourceBlockIds(
		com.liferay.portal.security.permission.ResourceBlockIdsBag resourceBlockIdsBag,
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.getResourceBlockIds(resourceBlockIdsBag,
			name, actionId);
	}

	@Override
	public com.liferay.portal.security.permission.ResourceBlockIdsBag getResourceBlockIdsBag(
		long companyId, long groupId, java.lang.String name, long[] roleIds) {
		return _resourceBlockLocalService.getResourceBlockIdsBag(companyId,
			groupId, name, roleIds);
	}

	/**
	* Returns a range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of resource blocks
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourceBlock> getResourceBlocks(
		int start, int end) {
		return _resourceBlockLocalService.getResourceBlocks(start, end);
	}

	/**
	* Returns the number of resource blocks.
	*
	* @return the number of resource blocks
	*/
	@Override
	public int getResourceBlocksCount() {
		return _resourceBlockLocalService.getResourceBlocksCount();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Role> getRoles(
		java.lang.String name, long primKey, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.getRoles(name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		java.lang.String actionId,
		com.liferay.portal.security.permission.ResourceBlockIdsBag resourceBlockIdsBag)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.hasPermission(name,
			permissionedModel, actionId, resourceBlockIdsBag);
	}

	@Override
	public boolean hasPermission(java.lang.String name, long primKey,
		java.lang.String actionId,
		com.liferay.portal.security.permission.ResourceBlockIdsBag resourceBlockIdsBag)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceBlockLocalService.hasPermission(name, primKey,
			actionId, resourceBlockIdsBag);
	}

	@Override
	public boolean isSupported(java.lang.String name) {
		return _resourceBlockLocalService.isSupported(name);
	}

	@Override
	public void releasePermissionedModelResourceBlock(java.lang.String name,
		long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.releasePermissionedModelResourceBlock(name,
			primKey);
	}

	@Override
	public void releasePermissionedModelResourceBlock(
		com.liferay.portal.model.PermissionedModel permissionedModel) {
		_resourceBlockLocalService.releasePermissionedModelResourceBlock(permissionedModel);
	}

	/**
	* Decrements the reference count of the resource block and updates it in
	* the database or deletes the resource block if the reference count reaches
	* zero.
	*
	* @param resourceBlock the resource block
	*/
	@Override
	public void releaseResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		_resourceBlockLocalService.releaseResourceBlock(resourceBlock);
	}

	/**
	* Decrements the reference count of the resource block and updates it in
	* the database or deletes the resource block if the reference count reaches
	* zero.
	*
	* @param resourceBlockId the primary key of the resource block
	*/
	@Override
	public void releaseResourceBlock(long resourceBlockId) {
		_resourceBlockLocalService.releaseResourceBlock(resourceBlockId);
	}

	@Override
	public void removeAllGroupScopePermissions(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.removeAllGroupScopePermissions(companyId,
			name, roleId, actionId);
	}

	@Override
	public void removeAllGroupScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong) {
		_resourceBlockLocalService.removeAllGroupScopePermissions(companyId,
			name, roleId, actionIdsLong);
	}

	@Override
	public void removeCompanyScopePermission(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.removeCompanyScopePermission(companyId,
			name, roleId, actionId);
	}

	@Override
	public void removeCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong) {
		_resourceBlockLocalService.removeCompanyScopePermissions(companyId,
			name, roleId, actionIdsLong);
	}

	@Override
	public void removeGroupScopePermission(long companyId, long groupId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.removeGroupScopePermission(companyId,
			groupId, name, roleId, actionId);
	}

	@Override
	public void removeGroupScopePermissions(long companyId, long groupId,
		java.lang.String name, long roleId, long actionIdsLong) {
		_resourceBlockLocalService.removeGroupScopePermissions(companyId,
			groupId, name, roleId, actionIdsLong);
	}

	@Override
	public void removeIndividualScopePermission(long companyId, long groupId,
		java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.removeIndividualScopePermission(companyId,
			groupId, name, permissionedModel, roleId, actionId);
	}

	@Override
	public void removeIndividualScopePermission(long companyId, long groupId,
		java.lang.String name, long primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.removeIndividualScopePermission(companyId,
			groupId, name, primKey, roleId, actionId);
	}

	@Override
	public void removeIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, long actionIdsLong) {
		_resourceBlockLocalService.removeIndividualScopePermissions(companyId,
			groupId, name, permissionedModel, roleId, actionIdsLong);
	}

	@Override
	public void removeIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name, long primKey, long roleId, long actionIdsLong)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.removeIndividualScopePermissions(companyId,
			groupId, name, primKey, roleId, actionIdsLong);
	}

	@Override
	public void setCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.setCompanyScopePermissions(companyId, name,
			roleId, actionIds);
	}

	@Override
	public void setCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong) {
		_resourceBlockLocalService.setCompanyScopePermissions(companyId, name,
			roleId, actionIdsLong);
	}

	@Override
	public void setGroupScopePermissions(long companyId, long groupId,
		java.lang.String name, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.setGroupScopePermissions(companyId, groupId,
			name, roleId, actionIds);
	}

	@Override
	public void setGroupScopePermissions(long companyId, long groupId,
		java.lang.String name, long roleId, long actionIdsLong) {
		_resourceBlockLocalService.setGroupScopePermissions(companyId, groupId,
			name, roleId, actionIdsLong);
	}

	@Override
	public void setIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.setIndividualScopePermissions(companyId,
			groupId, name, permissionedModel, roleId, actionIds);
	}

	@Override
	public void setIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, long actionIdsLong) {
		_resourceBlockLocalService.setIndividualScopePermissions(companyId,
			groupId, name, permissionedModel, roleId, actionIdsLong);
	}

	@Override
	public void setIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name, long primKey, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.setIndividualScopePermissions(companyId,
			groupId, name, primKey, roleId, actionIds);
	}

	@Override
	public void setIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name, long primKey, long roleId, long actionIdsLong)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.setIndividualScopePermissions(companyId,
			groupId, name, primKey, roleId, actionIdsLong);
	}

	@Override
	public void setIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name, long primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.setIndividualScopePermissions(companyId,
			groupId, name, primKey, roleIdsToActionIds);
	}

	@Override
	public void updateCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong, int operator) {
		_resourceBlockLocalService.updateCompanyScopePermissions(companyId,
			name, roleId, actionIdsLong, operator);
	}

	@Override
	public void updateGroupScopePermissions(long companyId, long groupId,
		java.lang.String name, long roleId, long actionIdsLong, int operator) {
		_resourceBlockLocalService.updateGroupScopePermissions(companyId,
			groupId, name, roleId, actionIdsLong, operator);
	}

	@Override
	public void updateIndividualScopePermissions(long companyId, long groupId,
		java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, long actionIdsLong, int operator) {
		_resourceBlockLocalService.updateIndividualScopePermissions(companyId,
			groupId, name, permissionedModel, roleId, actionIdsLong, operator);
	}

	/**
	* Updates the resource block in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @return the resource block that was updated
	*/
	@Override
	public com.liferay.portal.model.ResourceBlock updateResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		return _resourceBlockLocalService.updateResourceBlock(resourceBlock);
	}

	@Override
	public com.liferay.portal.model.ResourceBlock updateResourceBlockId(
		long companyId, long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		java.lang.String permissionsHash,
		com.liferay.portal.model.ResourceBlockPermissionsContainer resourceBlockPermissionsContainer) {
		return _resourceBlockLocalService.updateResourceBlockId(companyId,
			groupId, name, permissionedModel, permissionsHash,
			resourceBlockPermissionsContainer);
	}

	@Override
	public void verifyResourceBlockId(long companyId, java.lang.String name,
		long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourceBlockLocalService.verifyResourceBlockId(companyId, name,
			primKey);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ResourceBlockLocalService getWrappedResourceBlockLocalService() {
		return _resourceBlockLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedResourceBlockLocalService(
		ResourceBlockLocalService resourceBlockLocalService) {
		_resourceBlockLocalService = resourceBlockLocalService;
	}

	@Override
	public ResourceBlockLocalService getWrappedService() {
		return _resourceBlockLocalService;
	}

	@Override
	public void setWrappedService(
		ResourceBlockLocalService resourceBlockLocalService) {
		_resourceBlockLocalService = resourceBlockLocalService;
	}

	private ResourceBlockLocalService _resourceBlockLocalService;
}