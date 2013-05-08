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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ResourceBlockPermission. This utility wraps
 * {@link com.liferay.portal.service.impl.ResourceBlockPermissionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionLocalService
 * @see com.liferay.portal.service.base.ResourceBlockPermissionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceBlockPermissionLocalServiceImpl
 * @generated
 */
public class ResourceBlockPermissionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceBlockPermissionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the resource block permission to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermission the resource block permission
	* @return the resource block permission that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission addResourceBlockPermission(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResourceBlockPermission(resourceBlockPermission);
	}

	/**
	* Creates a new resource block permission with the primary key. Does not add the resource block permission to the database.
	*
	* @param resourceBlockPermissionId the primary key for the new resource block permission
	* @return the new resource block permission
	*/
	public static com.liferay.portal.model.ResourceBlockPermission createResourceBlockPermission(
		long resourceBlockPermissionId) {
		return getService()
				   .createResourceBlockPermission(resourceBlockPermissionId);
	}

	/**
	* Deletes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission that was removed
	* @throws PortalException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission deleteResourceBlockPermission(
		long resourceBlockPermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteResourceBlockPermission(resourceBlockPermissionId);
	}

	/**
	* Deletes the resource block permission from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermission the resource block permission
	* @return the resource block permission that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission deleteResourceBlockPermission(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteResourceBlockPermission(resourceBlockPermission);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.model.ResourceBlockPermission fetchResourceBlockPermission(
		long resourceBlockPermissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .fetchResourceBlockPermission(resourceBlockPermissionId);
	}

	/**
	* Returns the resource block permission with the primary key.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission
	* @throws PortalException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission getResourceBlockPermission(
		long resourceBlockPermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlockPermission(resourceBlockPermissionId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> getResourceBlockPermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlockPermissions(start, end);
	}

	/**
	* Returns the number of resource block permissions.
	*
	* @return the number of resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int getResourceBlockPermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlockPermissionsCount();
	}

	/**
	* Updates the resource block permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermission the resource block permission
	* @return the resource block permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission updateResourceBlockPermission(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateResourceBlockPermission(resourceBlockPermission);
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

	public static void addResourceBlockPermissions(long resourceBlockId,
		com.liferay.portal.model.ResourceBlockPermissionsContainer resourceBlockPermissionsContainer)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResourceBlockPermissions(resourceBlockId,
			resourceBlockPermissionsContainer);
	}

	public static void deleteResourceBlockPermissions(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourceBlockPermissions(resourceBlockId);
	}

	public static java.util.Map<java.lang.Long, java.util.Set<java.lang.String>> getAvailableResourceBlockPermissionActionIds(
		long[] roleIds, java.lang.String name, long primKey,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getAvailableResourceBlockPermissionActionIds(roleIds, name,
			primKey, actionIds);
	}

	public static com.liferay.portal.model.ResourceBlockPermissionsContainer getResourceBlockPermissionsContainer(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlockPermissionsContainer(resourceBlockId);
	}

	public static int getResourceBlockPermissionsCount(long resourceBlockId,
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getResourceBlockPermissionsCount(resourceBlockId, roleId);
	}

	public static void updateResourceBlockPermission(long resourceBlockId,
		long roleId, long actionIdsLong, int operator)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateResourceBlockPermission(resourceBlockId, roleId,
			actionIdsLong, operator);
	}

	public static ResourceBlockPermissionLocalService getService() {
		if (_service == null) {
			_service = (ResourceBlockPermissionLocalService)PortalBeanLocatorUtil.locate(ResourceBlockPermissionLocalService.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockPermissionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ResourceBlockPermissionLocalService service) {
	}

	private static ResourceBlockPermissionLocalService _service;
}