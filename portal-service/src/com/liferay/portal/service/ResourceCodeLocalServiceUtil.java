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
 * The utility for the resource code local service. This utility wraps {@link com.liferay.portal.service.impl.ResourceCodeLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceCodeLocalService
 * @see com.liferay.portal.service.base.ResourceCodeLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceCodeLocalServiceImpl
 * @generated
 */
public class ResourceCodeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceCodeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the resource code to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceCode the resource code
	* @return the resource code that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceCode addResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResourceCode(resourceCode);
	}

	/**
	* Creates a new resource code with the primary key. Does not add the resource code to the database.
	*
	* @param codeId the primary key for the new resource code
	* @return the new resource code
	*/
	public static com.liferay.portal.model.ResourceCode createResourceCode(
		long codeId) {
		return getService().createResourceCode(codeId);
	}

	/**
	* Deletes the resource code with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param codeId the primary key of the resource code
	* @return the resource code that was removed
	* @throws PortalException if a resource code with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceCode deleteResourceCode(
		long codeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteResourceCode(codeId);
	}

	/**
	* Deletes the resource code from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceCode the resource code
	* @return the resource code that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceCode deleteResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteResourceCode(resourceCode);
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

	public static com.liferay.portal.model.ResourceCode fetchResourceCode(
		long codeId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchResourceCode(codeId);
	}

	/**
	* Returns the resource code with the primary key.
	*
	* @param codeId the primary key of the resource code
	* @return the resource code
	* @throws PortalException if a resource code with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceCode getResourceCode(
		long codeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceCode(codeId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the resource codes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource codes
	* @param end the upper bound of the range of resource codes (not inclusive)
	* @return the range of resource codes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceCode> getResourceCodes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceCodes(start, end);
	}

	/**
	* Returns the number of resource codes.
	*
	* @return the number of resource codes
	* @throws SystemException if a system exception occurred
	*/
	public static int getResourceCodesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceCodesCount();
	}

	/**
	* Updates the resource code in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceCode the resource code
	* @return the resource code that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceCode updateResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourceCode(resourceCode);
	}

	/**
	* Updates the resource code in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceCode the resource code
	* @param merge whether to merge the resource code with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the resource code that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceCode updateResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourceCode(resourceCode, merge);
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

	public static com.liferay.portal.model.ResourceCode addResourceCode(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResourceCode(companyId, name, scope);
	}

	public static com.liferay.portal.model.ResourceCode getResourceCode(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceCode(companyId, name, scope);
	}

	public static ResourceCodeLocalService getService() {
		if (_service == null) {
			_service = (ResourceCodeLocalService)PortalBeanLocatorUtil.locate(ResourceCodeLocalService.class.getName());

			ReferenceRegistry.registerReference(ResourceCodeLocalServiceUtil.class,
				"_service");
			MethodCache.remove(ResourceCodeLocalService.class);
		}

		return _service;
	}

	public void setService(ResourceCodeLocalService service) {
		MethodCache.remove(ResourceCodeLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(ResourceCodeLocalServiceUtil.class,
			"_service");
		MethodCache.remove(ResourceCodeLocalService.class);
	}

	private static ResourceCodeLocalService _service;
}