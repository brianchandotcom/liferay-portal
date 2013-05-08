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
 * Provides the local service utility for ResourceTypePermission. This utility wraps
 * {@link com.liferay.portal.service.impl.ResourceTypePermissionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionLocalService
 * @see com.liferay.portal.service.base.ResourceTypePermissionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceTypePermissionLocalServiceImpl
 * @generated
 */
public class ResourceTypePermissionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceTypePermissionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the resource type permission to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermission the resource type permission
	* @return the resource type permission that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceTypePermission addResourceTypePermission(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResourceTypePermission(resourceTypePermission);
	}

	/**
	* Creates a new resource type permission with the primary key. Does not add the resource type permission to the database.
	*
	* @param resourceTypePermissionId the primary key for the new resource type permission
	* @return the new resource type permission
	*/
	public static com.liferay.portal.model.ResourceTypePermission createResourceTypePermission(
		long resourceTypePermissionId) {
		return getService()
				   .createResourceTypePermission(resourceTypePermissionId);
	}

	/**
	* Deletes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission that was removed
	* @throws PortalException if a resource type permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceTypePermission deleteResourceTypePermission(
		long resourceTypePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteResourceTypePermission(resourceTypePermissionId);
	}

	/**
	* Deletes the resource type permission from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermission the resource type permission
	* @return the resource type permission that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceTypePermission deleteResourceTypePermission(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteResourceTypePermission(resourceTypePermission);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.model.ResourceTypePermission fetchResourceTypePermission(
		long resourceTypePermissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchResourceTypePermission(resourceTypePermissionId);
	}

	/**
	* Returns the resource type permission with the primary key.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission
	* @throws PortalException if a resource type permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceTypePermission getResourceTypePermission(
		long resourceTypePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceTypePermission(resourceTypePermissionId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> getResourceTypePermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceTypePermissions(start, end);
	}

	/**
	* Returns the number of resource type permissions.
	*
	* @return the number of resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int getResourceTypePermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceTypePermissionsCount();
	}

	/**
	* Updates the resource type permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermission the resource type permission
	* @return the resource type permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceTypePermission updateResourceTypePermission(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourceTypePermission(resourceTypePermission);
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

	public static long getCompanyScopeActionIds(long companyId,
		java.lang.String name, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyScopeActionIds(companyId, name, roleId);
	}

	public static long getGroupScopeActionIds(long companyId, long groupId,
		java.lang.String name, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupScopeActionIds(companyId, groupId, name, roleId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> getGroupScopeResourceTypePermissions(
		long companyId, java.lang.String name, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupScopeResourceTypePermissions(companyId, name, roleId);
	}

	public static com.liferay.portal.model.ResourceBlockPermissionsContainer getResourceBlockPermissionsContainer(
		long companyId, long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getResourceBlockPermissionsContainer(companyId, groupId,
			name);
	}

	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> getRoleResourceTypePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleResourceTypePermissions(roleId);
	}

	public static boolean hasCompanyScopePermission(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasCompanyScopePermission(companyId, name, roleId, actionId);
	}

	public static boolean hasEitherScopePermission(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasEitherScopePermission(companyId, name, roleId, actionId);
	}

	public static boolean hasGroupScopePermission(long companyId, long groupId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasGroupScopePermission(companyId, groupId, name, roleId,
			actionId);
	}

	public static void updateCompanyScopeResourceTypePermissions(
		long companyId, java.lang.String name, long roleId, long actionIdsLong,
		long operator)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateCompanyScopeResourceTypePermissions(companyId, name, roleId,
			actionIdsLong, operator);
	}

	public static void updateGroupScopeResourceTypePermissions(long companyId,
		long groupId, java.lang.String name, long roleId, long actionIdsLong,
		long operator)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateGroupScopeResourceTypePermissions(companyId, groupId, name,
			roleId, actionIdsLong, operator);
	}

	public static ResourceTypePermissionLocalService getService() {
		if (_service == null) {
			_service = (ResourceTypePermissionLocalService)PortalBeanLocatorUtil.locate(ResourceTypePermissionLocalService.class.getName());

			ReferenceRegistry.registerReference(ResourceTypePermissionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ResourceTypePermissionLocalService service) {
	}

	private static ResourceTypePermissionLocalService _service;
}