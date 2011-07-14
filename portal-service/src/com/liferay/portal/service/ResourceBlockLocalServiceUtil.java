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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the resource block local service. This utility wraps {@link com.liferay.portal.service.impl.ResourceBlockLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockLocalService
 * @see com.liferay.portal.service.base.ResourceBlockLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceBlockLocalServiceImpl
 * @generated
 */
public class ResourceBlockLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceBlockLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the resource block to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @return the resource block that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlock addResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* Deletes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockId the primary key of the resource block
	* @throws PortalException if a resource block with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteResourceBlock(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourceBlock(resourceBlockId);
	}

	/**
	* Deletes the resource block from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourceBlock(resourceBlock);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the resource block with the primary key.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block
	* @throws PortalException if a resource block with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlock getResourceBlock(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlock(resourceBlockId);
	}

	/**
	* Returns a range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlock> getResourceBlocks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlocks(start, end);
	}

	/**
	* Returns the number of resource blocks.
	*
	* @return the number of resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public static int getResourceBlocksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlocksCount();
	}

	/**
	* Updates the resource block in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @return the resource block that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlock updateResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourceBlock(resourceBlock);
	}

	/**
	* Updates the resource block in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlock the resource block
	* @param merge whether to merge the resource block with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the resource block that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlock updateResourceBlock(
		com.liferay.portal.model.ResourceBlock resourceBlock, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourceBlock(resourceBlock, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a resource block and creates associations between it and the roles
	* specified in the resource permissions. The resource block will have an
	* initial reference count of one.
	*
	* @param companyId the primary key of the resource block's company
	* @param groupId the primary key of the resource block's group
	* @param permissionsHash the resource block's permissions hash
	* @param resourcePermissions the resource permissions
	* @return the new resource block
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlock addResourceBlock(
		long companyId, long groupId, java.lang.String name,
		java.lang.String permissionsHash,
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addResourceBlock(companyId, groupId, name, permissionsHash,
			resourcePermissions);
	}

	/**
	* Returns the permissions hash for the resource. The permissions hash is a
	* representation of all the roles with access to the resource along with
	* the actions they can perform.
	*
	* @param companyId the primary key of the resource's company
	* @param groupId the primary key of the resource's group
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param primKey the primary key of the resource
	* @return the permissions hash for the resource
	* @throws SystemException if a system exception occurred
	*/
	public static java.lang.String getPermissionsHash(long companyId,
		long groupId, java.lang.String name, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermissionsHash(companyId, groupId, name, primKey);
	}

	/**
	* Returns the permissions hash of the resource permissions. The permissions
	* hash is a representation of all the roles with access to the resource
	* along with the actions they can perform.
	*
	* @param resourcePermissions the resource permissions
	* @return the permissions hash of the resource permissions
	*/
	public static java.lang.String getPermissionsHash(
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions) {
		return getService().getPermissionsHash(resourcePermissions);
	}

	public static java.util.List<com.liferay.portal.model.ResourceBlock> getResourceBlocks(
		long companyId, long groupId, java.lang.String name, long[] roleIds,
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getResourceBlocks(companyId, groupId, name, roleIds,
			actionId);
	}

	/**
	* Increments the reference count of the resource block and updates it in
	* the database.
	*
	* @param resourceBlockId the primary key of the resource block
	* @throws SystemException if a system exception occurred
	*/
	public static void retain(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().retain(resourceBlockId);
	}

	/**
	* Increments the reference count of the resource block and updates it in
	* the database.
	*
	* @param resourceBlock the resource block
	* @throws SystemException if a system exception occurred
	*/
	public static void retain(
		com.liferay.portal.model.ResourceBlock resourceBlock)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().retain(resourceBlock);
	}

	/**
	* Decrements the reference count of the resource block and updates it in
	* the database or deletes the resource block if the reference count reaches
	* zero.
	*
	* @param resourceBlockId the primary key of the resource block
	* @throws SystemException if a system exception occurred
	*/
	public static void release(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().release(resourceBlockId);
	}

	/**
	* Decrements the reference count of the resource block and updates it in
	* the database or deletes the resource block if the reference count reaches
	* zero.
	*
	* @param resourceBlock the resource block
	* @throws SystemException if a system exception occurred
	*/
	public static void release(
		com.liferay.portal.model.ResourceBlock resourceBlock)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().release(resourceBlock);
	}

	/**
	* Updates which resource block the resource is a member of, and updates it
	* in the database. Automatically retains, releases, and creates resource
	* blocks as necessary. Only use this method for resources that do not
	* belong to a group, such as users.
	*
	* @param companyId the primary key of the resource's company
	* @param model the permissioned model instance
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param primKey the primary key of the resource
	* @throws SystemException if a system exception occurred
	*/
	public static void updateResourceBlockId(long companyId,
		com.liferay.portal.model.PermissionedModel model,
		java.lang.String name, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().updateResourceBlockId(companyId, model, name, primKey);
	}

	/**
	* Updates which resource block the resource is a member of, and updates it
	* in the database. Automatically retains, releases, and creates resource
	* blocks as necessary.
	*
	* @param companyId the primary key of the resource's company
	* @param groupId the primary key of the resource's group
	* @param model the permissioned model instance
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param primKey the primary key of the resource
	* @throws SystemException if a system exception occurred
	*/
	public static void updateResourceBlockId(long companyId, long groupId,
		com.liferay.portal.model.PermissionedModel model,
		java.lang.String name, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateResourceBlockId(companyId, groupId, model, name, primKey);
	}

	public static ResourceBlockLocalService getService() {
		if (_service == null) {
			_service = (ResourceBlockLocalService)PortalBeanLocatorUtil.locate(ResourceBlockLocalService.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockLocalServiceUtil.class,
				"_service");
			MethodCache.remove(ResourceBlockLocalService.class);
		}

		return _service;
	}

	public void setService(ResourceBlockLocalService service) {
		MethodCache.remove(ResourceBlockLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(ResourceBlockLocalServiceUtil.class,
			"_service");
		MethodCache.remove(ResourceBlockLocalService.class);
	}

	private static ResourceBlockLocalService _service;
}