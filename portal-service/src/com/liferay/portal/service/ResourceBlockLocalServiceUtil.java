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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ResourceBlock. This utility wraps
 * {@link com.liferay.portal.service.impl.ResourceBlockLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockLocalService
 * @see com.liferay.portal.service.base.ResourceBlockLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceBlockLocalServiceImpl
 * @generated
 */
@ProviderType
public class ResourceBlockLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceBlockLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCompanyScopePermission(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addCompanyScopePermission(companyId, name, roleId, actionId);
	}

	public static void addCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong) {
		getService()
			.addCompanyScopePermissions(companyId, name, roleId, actionIdsLong);
	}

	public static void addGroupScopePermission(long companyId, long groupId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addGroupScopePermission(companyId, groupId, name, roleId, actionId);
	}

	public static void addGroupScopePermissions(long companyId, long groupId,
		java.lang.String name, long roleId, long actionIdsLong) {
		getService()
			.addGroupScopePermissions(companyId, groupId, name, roleId,
			actionIdsLong);
	}

	public static void addIndividualScopePermission(long companyId,
		long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addIndividualScopePermission(companyId, groupId, name,
			permissionedModel, roleId, actionId);
	}

	public static void addIndividualScopePermission(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addIndividualScopePermission(companyId, groupId, name, primKey,
			roleId, actionId);
	}

	public static void addIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, long actionIdsLong) {
		getService()
			.addIndividualScopePermissions(companyId, groupId, name,
			permissionedModel, roleId, actionIdsLong);
	}

	public static void addIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		long actionIdsLong)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addIndividualScopePermissions(companyId, groupId, name, primKey,
			roleId, actionIdsLong);
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
	public static com.liferay.portal.model.ResourceBlock addResourceBlock(
		long companyId, long groupId, java.lang.String name,
		java.lang.String permissionsHash,
		com.liferay.portal.model.ResourceBlockPermissionsContainer resourceBlockPermissionsContainer) {
		return getService()
				   .addResourceBlock(companyId, groupId, name, permissionsHash,
			resourceBlockPermissionsContainer);
	}

	/**
	* Adds the resource block to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @return the resource block that was added
	*/
	public static com.liferay.portal.model.ResourceBlock addResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		return getService().addResourceBlock(resourceBlock);
	}

	/**
	* Creates a new resource block with the primary key. Does not add the resource block to the database.
	*
	* @param resourceBlockId the primary key for the new resource block
	* @return the new resource block
	*/
	public static com.liferay.portal.model.ResourceBlock createResourceBlock(
		long resourceBlockId) {
		return getService().createResourceBlock(resourceBlockId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the resource block from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @return the resource block that was removed
	*/
	public static com.liferay.portal.model.ResourceBlock deleteResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		return getService().deleteResourceBlock(resourceBlock);
	}

	/**
	* Deletes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block that was removed
	* @throws PortalException if a resource block with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlock deleteResourceBlock(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteResourceBlock(resourceBlockId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.model.ResourceBlock fetchResourceBlock(
		long resourceBlockId) {
		return getService().fetchResourceBlock(resourceBlockId);
	}

	public static long getActionId(java.lang.String name,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getActionId(name, actionId);
	}

	public static long getActionIds(java.lang.String name,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getActionIds(name, actionIds);
	}

	public static java.util.List<java.lang.String> getActionIds(
		java.lang.String name, long actionIdsLong) {
		return getService().getActionIds(name, actionIdsLong);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<java.lang.String> getCompanyScopePermissions(
		com.liferay.portal.model.ResourceBlock resourceBlock, long roleId) {
		return getService().getCompanyScopePermissions(resourceBlock, roleId);
	}

	public static java.util.List<java.lang.String> getGroupScopePermissions(
		com.liferay.portal.model.ResourceBlock resourceBlock, long roleId) {
		return getService().getGroupScopePermissions(resourceBlock, roleId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PermissionedModel getPermissionedModel(
		java.lang.String name, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPermissionedModel(name, primKey);
	}

	public static java.util.List<java.lang.String> getPermissions(
		com.liferay.portal.model.ResourceBlock resourceBlock, long roleId) {
		return getService().getPermissions(resourceBlock, roleId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portal.model.ResourceBlock getResourceBlock(
		java.lang.String name, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getResourceBlock(name, primKey);
	}

	/**
	* Returns the resource block with the primary key.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block
	* @throws PortalException if a resource block with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlock getResourceBlock(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getResourceBlock(resourceBlockId);
	}

	public static java.util.List<java.lang.Long> getResourceBlockIds(
		com.liferay.portal.security.permission.ResourceBlockIdsBag resourceBlockIdsBag,
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getResourceBlockIds(resourceBlockIdsBag, name, actionId);
	}

	public static com.liferay.portal.security.permission.ResourceBlockIdsBag getResourceBlockIdsBag(
		long companyId, long groupId, java.lang.String name, long[] roleIds) {
		return getService()
				   .getResourceBlockIdsBag(companyId, groupId, name, roleIds);
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
	public static java.util.List<com.liferay.portal.model.ResourceBlock> getResourceBlocks(
		int start, int end) {
		return getService().getResourceBlocks(start, end);
	}

	/**
	* Returns the number of resource blocks.
	*
	* @return the number of resource blocks
	*/
	public static int getResourceBlocksCount() {
		return getService().getResourceBlocksCount();
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		java.lang.String name, long primKey, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRoles(name, primKey, actionId);
	}

	public static boolean hasPermission(java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		java.lang.String actionId,
		com.liferay.portal.security.permission.ResourceBlockIdsBag resourceBlockIdsBag)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .hasPermission(name, permissionedModel, actionId,
			resourceBlockIdsBag);
	}

	public static boolean hasPermission(java.lang.String name, long primKey,
		java.lang.String actionId,
		com.liferay.portal.security.permission.ResourceBlockIdsBag resourceBlockIdsBag)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .hasPermission(name, primKey, actionId, resourceBlockIdsBag);
	}

	public static boolean isSupported(java.lang.String name) {
		return getService().isSupported(name);
	}

	public static void releasePermissionedModelResourceBlock(
		java.lang.String name, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().releasePermissionedModelResourceBlock(name, primKey);
	}

	public static void releasePermissionedModelResourceBlock(
		com.liferay.portal.model.PermissionedModel permissionedModel) {
		getService().releasePermissionedModelResourceBlock(permissionedModel);
	}

	/**
	* Decrements the reference count of the resource block and updates it in
	* the database or deletes the resource block if the reference count reaches
	* zero.
	*
	* @param resourceBlock the resource block
	*/
	public static void releaseResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		getService().releaseResourceBlock(resourceBlock);
	}

	/**
	* Decrements the reference count of the resource block and updates it in
	* the database or deletes the resource block if the reference count reaches
	* zero.
	*
	* @param resourceBlockId the primary key of the resource block
	*/
	public static void releaseResourceBlock(long resourceBlockId) {
		getService().releaseResourceBlock(resourceBlockId);
	}

	public static void removeAllGroupScopePermissions(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeAllGroupScopePermissions(companyId, name, roleId, actionId);
	}

	public static void removeAllGroupScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong) {
		getService()
			.removeAllGroupScopePermissions(companyId, name, roleId,
			actionIdsLong);
	}

	public static void removeCompanyScopePermission(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeCompanyScopePermission(companyId, name, roleId, actionId);
	}

	public static void removeCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong) {
		getService()
			.removeCompanyScopePermissions(companyId, name, roleId,
			actionIdsLong);
	}

	public static void removeGroupScopePermission(long companyId, long groupId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeGroupScopePermission(companyId, groupId, name, roleId,
			actionId);
	}

	public static void removeGroupScopePermissions(long companyId,
		long groupId, java.lang.String name, long roleId, long actionIdsLong) {
		getService()
			.removeGroupScopePermissions(companyId, groupId, name, roleId,
			actionIdsLong);
	}

	public static void removeIndividualScopePermission(long companyId,
		long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeIndividualScopePermission(companyId, groupId, name,
			permissionedModel, roleId, actionId);
	}

	public static void removeIndividualScopePermission(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeIndividualScopePermission(companyId, groupId, name, primKey,
			roleId, actionId);
	}

	public static void removeIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, long actionIdsLong) {
		getService()
			.removeIndividualScopePermissions(companyId, groupId, name,
			permissionedModel, roleId, actionIdsLong);
	}

	public static void removeIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		long actionIdsLong)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.removeIndividualScopePermissions(companyId, groupId, name,
			primKey, roleId, actionIdsLong);
	}

	public static void setCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setCompanyScopePermissions(companyId, name, roleId, actionIds);
	}

	public static void setCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong) {
		getService()
			.setCompanyScopePermissions(companyId, name, roleId, actionIdsLong);
	}

	public static void setGroupScopePermissions(long companyId, long groupId,
		java.lang.String name, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setGroupScopePermissions(companyId, groupId, name, roleId,
			actionIds);
	}

	public static void setGroupScopePermissions(long companyId, long groupId,
		java.lang.String name, long roleId, long actionIdsLong) {
		getService()
			.setGroupScopePermissions(companyId, groupId, name, roleId,
			actionIdsLong);
	}

	public static void setIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setIndividualScopePermissions(companyId, groupId, name,
			permissionedModel, roleId, actionIds);
	}

	public static void setIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, long actionIdsLong) {
		getService()
			.setIndividualScopePermissions(companyId, groupId, name,
			permissionedModel, roleId, actionIdsLong);
	}

	public static void setIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setIndividualScopePermissions(companyId, groupId, name, primKey,
			roleId, actionIds);
	}

	public static void setIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name, long primKey, long roleId,
		long actionIdsLong)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setIndividualScopePermissions(companyId, groupId, name, primKey,
			roleId, actionIdsLong);
	}

	public static void setIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name, long primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setIndividualScopePermissions(companyId, groupId, name, primKey,
			roleIdsToActionIds);
	}

	public static void updateCompanyScopePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong, int operator) {
		getService()
			.updateCompanyScopePermissions(companyId, name, roleId,
			actionIdsLong, operator);
	}

	public static void updateGroupScopePermissions(long companyId,
		long groupId, java.lang.String name, long roleId, long actionIdsLong,
		int operator) {
		getService()
			.updateGroupScopePermissions(companyId, groupId, name, roleId,
			actionIdsLong, operator);
	}

	public static void updateIndividualScopePermissions(long companyId,
		long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		long roleId, long actionIdsLong, int operator) {
		getService()
			.updateIndividualScopePermissions(companyId, groupId, name,
			permissionedModel, roleId, actionIdsLong, operator);
	}

	/**
	* Updates the resource block in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @return the resource block that was updated
	*/
	public static com.liferay.portal.model.ResourceBlock updateResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock) {
		return getService().updateResourceBlock(resourceBlock);
	}

	public static com.liferay.portal.model.ResourceBlock updateResourceBlockId(
		long companyId, long groupId, java.lang.String name,
		com.liferay.portal.model.PermissionedModel permissionedModel,
		java.lang.String permissionsHash,
		com.liferay.portal.model.ResourceBlockPermissionsContainer resourceBlockPermissionsContainer) {
		return getService()
				   .updateResourceBlockId(companyId, groupId, name,
			permissionedModel, permissionsHash,
			resourceBlockPermissionsContainer);
	}

	public static void verifyResourceBlockId(long companyId,
		java.lang.String name, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().verifyResourceBlockId(companyId, name, primKey);
	}

	public static ResourceBlockLocalService getService() {
		if (_service == null) {
			_service = (ResourceBlockLocalService)PortalBeanLocatorUtil.locate(ResourceBlockLocalService.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ResourceBlockLocalService service) {
	}

	private static ResourceBlockLocalService _service;
}