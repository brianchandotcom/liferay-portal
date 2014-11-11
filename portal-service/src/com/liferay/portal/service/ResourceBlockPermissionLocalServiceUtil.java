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
@ProviderType
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
	*/
	public static com.liferay.portal.model.ResourceBlockPermission addResourceBlockPermission(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission) {
		return getService().addResourceBlockPermission(resourceBlockPermission);
	}

	public static void addResourceBlockPermissions(long resourceBlockId,
		com.liferay.portal.model.ResourceBlockPermissionsContainer resourceBlockPermissionsContainer) {
		getService()
			.addResourceBlockPermissions(resourceBlockId,
			resourceBlockPermissionsContainer);
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
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the resource block permission from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermission the resource block permission
	* @return the resource block permission that was removed
	*/
	public static com.liferay.portal.model.ResourceBlockPermission deleteResourceBlockPermission(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission) {
		return getService()
				   .deleteResourceBlockPermission(resourceBlockPermission);
	}

	/**
	* Deletes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission that was removed
	* @throws PortalException if a resource block permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission deleteResourceBlockPermission(
		long resourceBlockPermissionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteResourceBlockPermission(resourceBlockPermissionId);
	}

	public static void deleteResourceBlockPermissions(long resourceBlockId) {
		getService().deleteResourceBlockPermissions(resourceBlockId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.model.ResourceBlockPermission fetchResourceBlockPermission(
		long resourceBlockPermissionId) {
		return getService()
				   .fetchResourceBlockPermission(resourceBlockPermissionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.Map<java.lang.Long, java.util.Set<java.lang.String>> getAvailableResourceBlockPermissionActionIds(
		java.lang.String name, long primKey,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getAvailableResourceBlockPermissionActionIds(name, primKey,
			actionIds);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#getAvailableResourceBlockPermissionActionIds(String, long,
	List)}
	*/
	@Deprecated
	public static java.util.Map<java.lang.Long, java.util.Set<java.lang.String>> getAvailableResourceBlockPermissionActionIds(
		long[] roleIds, java.lang.String name, long primKey,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getAvailableResourceBlockPermissionActionIds(roleIds, name,
			primKey, actionIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the resource block permission with the primary key.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission
	* @throws PortalException if a resource block permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission getResourceBlockPermission(
		long resourceBlockPermissionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getResourceBlockPermission(resourceBlockPermissionId);
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
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> getResourceBlockPermissions(
		int start, int end) {
		return getService().getResourceBlockPermissions(start, end);
	}

	public static com.liferay.portal.model.ResourceBlockPermissionsContainer getResourceBlockPermissionsContainer(
		long resourceBlockId) {
		return getService().getResourceBlockPermissionsContainer(resourceBlockId);
	}

	/**
	* Returns the number of resource block permissions.
	*
	* @return the number of resource block permissions
	*/
	public static int getResourceBlockPermissionsCount() {
		return getService().getResourceBlockPermissionsCount();
	}

	public static int getResourceBlockPermissionsCount(long resourceBlockId,
		long roleId) {
		return getService()
				   .getResourceBlockPermissionsCount(resourceBlockId, roleId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void updateResourceBlockPermission(long resourceBlockId,
		long roleId, long actionIdsLong, int operator) {
		getService()
			.updateResourceBlockPermission(resourceBlockId, roleId,
			actionIdsLong, operator);
	}

	/**
	* Updates the resource block permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermission the resource block permission
	* @return the resource block permission that was updated
	*/
	public static com.liferay.portal.model.ResourceBlockPermission updateResourceBlockPermission(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission) {
		return getService()
				   .updateResourceBlockPermission(resourceBlockPermission);
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
	@Deprecated
	public void setService(ResourceBlockPermissionLocalService service) {
	}

	private static ResourceBlockPermissionLocalService _service;
}