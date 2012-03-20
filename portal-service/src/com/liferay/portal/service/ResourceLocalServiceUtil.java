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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the resource local service. This utility wraps {@link com.liferay.portal.service.impl.ResourceLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceLocalService
 * @see com.liferay.portal.service.base.ResourceLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceLocalServiceImpl
 * @generated
 */
public class ResourceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the resource to the database. Also notifies the appropriate model listeners.
	*
	* @param resource the resource
	* @return the resource that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Resource addResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResource(resource);
	}

	/**
	* Creates a new resource with the primary key. Does not add the resource to the database.
	*
	* @param resourceId the primary key for the new resource
	* @return the new resource
	*/
	public static com.liferay.portal.model.Resource createResource(
		long resourceId) {
		return getService().createResource(resourceId);
	}

	/**
	* Deletes the resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceId the primary key of the resource
	* @return the resource that was removed
	* @throws PortalException if a resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Resource deleteResource(
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteResource(resourceId);
	}

	/**
	* Deletes the resource from the database. Also notifies the appropriate model listeners.
	*
	* @param resource the resource
	* @return the resource that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Resource deleteResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteResource(resource);
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

	public static com.liferay.portal.model.Resource fetchResource(
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchResource(resourceId);
	}

	/**
	* Returns the resource with the primary key.
	*
	* @param resourceId the primary key of the resource
	* @return the resource
	* @throws PortalException if a resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Resource getResource(long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResource(resourceId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resources
	* @param end the upper bound of the range of resources (not inclusive)
	* @return the range of resources
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Resource> getResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResources(start, end);
	}

	/**
	* Returns the number of resources.
	*
	* @return the number of resources
	* @throws SystemException if a system exception occurred
	*/
	public static int getResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourcesCount();
	}

	/**
	* Updates the resource in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resource the resource
	* @return the resource that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Resource updateResource(
		com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResource(resource);
	}

	/**
	* Updates the resource in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resource the resource
	* @param merge whether to merge the resource with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the resource that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Resource updateResource(
		com.liferay.portal.model.Resource resource, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResource(resource, merge);
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

	public static void addModelResources(
		com.liferay.portal.model.AuditedModel auditedModel,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addModelResources(auditedModel, serviceContext);
	}

	public static void addModelResources(long companyId, long groupId,
		long userId, java.lang.String name, long primKey,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addModelResources(companyId, groupId, userId, name, primKey,
			groupPermissions, guestPermissions);
	}

	public static void addModelResources(long companyId, long groupId,
		long userId, java.lang.String name, java.lang.String primKey,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addModelResources(companyId, groupId, userId, name, primKey,
			groupPermissions, guestPermissions);
	}

	public static com.liferay.portal.model.Resource addResource(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResource(companyId, name, scope, primKey);
	}

	public static void addResources(long companyId, long groupId, long userId,
		java.lang.String name, long primKey, boolean portletActions,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResources(companyId, groupId, userId, name, primKey,
			portletActions, addGroupPermissions, addGuestPermissions);
	}

	public static void addResources(long companyId, long groupId, long userId,
		java.lang.String name, java.lang.String primKey,
		boolean portletActions, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResources(companyId, groupId, userId, name, primKey,
			portletActions, addGroupPermissions, addGuestPermissions);
	}

	public static void addResources(long companyId, long groupId,
		java.lang.String name, boolean portletActions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addResources(companyId, groupId, name, portletActions);
	}

	public static void deleteResource(
		com.liferay.portal.model.AuditedModel auditedModel, int scope)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResource(auditedModel, scope);
	}

	public static void deleteResource(long companyId, java.lang.String name,
		int scope, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResource(companyId, name, scope, primKey);
	}

	public static void deleteResource(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResource(companyId, name, scope, primKey);
	}

	public static void deleteResources(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResources(name);
	}

	public static com.liferay.portal.model.Resource fetchResource(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchResource(companyId, name, scope, primKey);
	}

	public static long getLatestResourceId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestResourceId();
	}

	public static com.liferay.portal.model.Resource getResource(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResource(companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.Resource> getResources()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResources();
	}

	public static void updateModelResources(
		com.liferay.portal.model.AuditedModel auditedModel,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateModelResources(auditedModel, serviceContext);
	}

	public static void updateResources(long companyId, long groupId,
		java.lang.String name, long primKey,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateResources(companyId, groupId, name, primKey,
			groupPermissions, guestPermissions);
	}

	public static void updateResources(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateResources(companyId, groupId, name, primKey,
			groupPermissions, guestPermissions);
	}

	public static void updateResources(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, java.lang.String newPrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateResources(companyId, name, scope, primKey, newPrimKey);
	}

	public static ResourceLocalService getService() {
		if (_service == null) {
			_service = (ResourceLocalService)PortalBeanLocatorUtil.locate(ResourceLocalService.class.getName());

			ReferenceRegistry.registerReference(ResourceLocalServiceUtil.class,
				"_service");
			MethodCache.remove(ResourceLocalService.class);
		}

		return _service;
	}

	public void setService(ResourceLocalService service) {
		MethodCache.remove(ResourceLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(ResourceLocalServiceUtil.class,
			"_service");
		MethodCache.remove(ResourceLocalService.class);
	}

	private static ResourceLocalService _service;
}