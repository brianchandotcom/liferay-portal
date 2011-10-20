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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the asset tag property remote service. This utility wraps {@link com.liferay.portlet.asset.service.impl.AssetTagPropertyServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagPropertyService
 * @see com.liferay.portlet.asset.service.base.AssetTagPropertyServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetTagPropertyServiceImpl
 * @generated
 */
public class AssetTagPropertyServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetTagPropertyServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds an asset tag property.
	*
	* @param tagId the primary key of the tag
	* @param key the key to be associated to the value
	* @param value the value to which the key will refer
	* @return the created asset tag property
	* @throws PortalException if the user did not have permission to update
	the asset tag, or if the key or value were invalid
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty addTagProperty(
		long tagId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTagProperty(tagId, key, value);
	}

	/**
	* Deletes the asset tag property with the specified ID.
	*
	* @param tagPropertyId the primary key of the asset tag property instance
	* @throws PortalException if an asset tag property with the primary key
	could not be found or if the user did not have permission to
	update the asset tag property
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTagProperty(long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTagProperty(tagPropertyId);
	}

	/**
	* Returns all the asset tag property instances with the specified tag ID.
	*
	* @param tagId the primary key of the tag
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTagProperties(tagId);
	}

	/**
	* Returns asset tag properties with the specified group and key.
	*
	* @param companyId the primary key of the company
	* @param key the key that refers to some value
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagPropertyValues(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTagPropertyValues(companyId, key);
	}

	/**
	* Updates the asset tag property.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @param key the new key to be associated to the value
	* @param value the new value to which the key will refer
	* @return the updated asset tag property
	* @throws PortalException if an asset tag property with the primary key
	could not be found, if the user did not have permission to
	update the asset tag, or if the key or value were invalid
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTagProperty(tagPropertyId, key, value);
	}

	public static AssetTagPropertyService getService() {
		if (_service == null) {
			_service = (AssetTagPropertyService)PortalBeanLocatorUtil.locate(AssetTagPropertyService.class.getName());

			ReferenceRegistry.registerReference(AssetTagPropertyServiceUtil.class,
				"_service");
			MethodCache.remove(AssetTagPropertyService.class);
		}

		return _service;
	}

	public void setService(AssetTagPropertyService service) {
		MethodCache.remove(AssetTagPropertyService.class);

		_service = service;

		ReferenceRegistry.registerReference(AssetTagPropertyServiceUtil.class,
			"_service");
		MethodCache.remove(AssetTagPropertyService.class);
	}

	private static AssetTagPropertyService _service;
}