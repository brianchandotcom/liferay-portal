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
 * The utility for the resource block role action local service. This utility wraps {@link com.liferay.portal.service.impl.ResourceBlockRoleActionLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockRoleActionLocalService
 * @see com.liferay.portal.service.base.ResourceBlockRoleActionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceBlockRoleActionLocalServiceImpl
 * @generated
 */
public class ResourceBlockRoleActionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceBlockRoleActionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the resource block role action to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockRoleAction the resource block role action
	* @return the resource block role action that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction addResourceBlockRoleAction(
		com.liferay.portal.model.ResourceBlockRoleAction resourceBlockRoleAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResourceBlockRoleAction(resourceBlockRoleAction);
	}

	/**
	* Creates a new resource block role action with the primary key. Does not add the resource block role action to the database.
	*
	* @param resourceBlockRoleActionPK the primary key for the new resource block role action
	* @return the new resource block role action
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction createResourceBlockRoleAction(
		com.liferay.portal.service.persistence.ResourceBlockRoleActionPK resourceBlockRoleActionPK) {
		return getService()
				   .createResourceBlockRoleAction(resourceBlockRoleActionPK);
	}

	/**
	* Deletes the resource block role action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockRoleActionPK the primary key of the resource block role action
	* @throws PortalException if a resource block role action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteResourceBlockRoleAction(
		com.liferay.portal.service.persistence.ResourceBlockRoleActionPK resourceBlockRoleActionPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourceBlockRoleAction(resourceBlockRoleActionPK);
	}

	/**
	* Deletes the resource block role action from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockRoleAction the resource block role action
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteResourceBlockRoleAction(
		com.liferay.portal.model.ResourceBlockRoleAction resourceBlockRoleAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourceBlockRoleAction(resourceBlockRoleAction);
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
	* Returns the resource block role action with the primary key.
	*
	* @param resourceBlockRoleActionPK the primary key of the resource block role action
	* @return the resource block role action
	* @throws PortalException if a resource block role action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction getResourceBlockRoleAction(
		com.liferay.portal.service.persistence.ResourceBlockRoleActionPK resourceBlockRoleActionPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlockRoleAction(resourceBlockRoleActionPK);
	}

	/**
	* Returns a range of all the resource block role actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource block role actions
	* @param end the upper bound of the range of resource block role actions (not inclusive)
	* @return the range of resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockRoleAction> getResourceBlockRoleActions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlockRoleActions(start, end);
	}

	/**
	* Returns the number of resource block role actions.
	*
	* @return the number of resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static int getResourceBlockRoleActionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceBlockRoleActionsCount();
	}

	/**
	* Updates the resource block role action in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockRoleAction the resource block role action
	* @return the resource block role action that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction updateResourceBlockRoleAction(
		com.liferay.portal.model.ResourceBlockRoleAction resourceBlockRoleAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateResourceBlockRoleAction(resourceBlockRoleAction);
	}

	/**
	* Updates the resource block role action in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockRoleAction the resource block role action
	* @param merge whether to merge the resource block role action with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the resource block role action that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction updateResourceBlockRoleAction(
		com.liferay.portal.model.ResourceBlockRoleAction resourceBlockRoleAction,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateResourceBlockRoleAction(resourceBlockRoleAction, merge);
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

	public static ResourceBlockRoleActionLocalService getService() {
		if (_service == null) {
			_service = (ResourceBlockRoleActionLocalService)PortalBeanLocatorUtil.locate(ResourceBlockRoleActionLocalService.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockRoleActionLocalServiceUtil.class,
				"_service");
			MethodCache.remove(ResourceBlockRoleActionLocalService.class);
		}

		return _service;
	}

	public void setService(ResourceBlockRoleActionLocalService service) {
		MethodCache.remove(ResourceBlockRoleActionLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(ResourceBlockRoleActionLocalServiceUtil.class,
			"_service");
		MethodCache.remove(ResourceBlockRoleActionLocalService.class);
	}

	private static ResourceBlockRoleActionLocalService _service;
}