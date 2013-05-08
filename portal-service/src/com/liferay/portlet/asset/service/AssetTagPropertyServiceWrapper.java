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

package com.liferay.portlet.asset.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetTagPropertyService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPropertyService
 * @generated
 */
public class AssetTagPropertyServiceWrapper implements AssetTagPropertyService,
	ServiceWrapper<AssetTagPropertyService> {
	public AssetTagPropertyServiceWrapper(
		AssetTagPropertyService assetTagPropertyService) {
		_assetTagPropertyService = assetTagPropertyService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _assetTagPropertyService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetTagPropertyService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds an asset tag property.
	*
	* @param tagId the primary key of the tag
	* @param key the key to be associated to the value
	* @param value the value to which the key will refer
	* @return the created asset tag property
	* @throws PortalException if the user did not have permission to update the
	asset tag, or if the key or value were invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty addTagProperty(
		long tagId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyService.addTagProperty(tagId, key, value);
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
	public void deleteTagProperty(long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyService.deleteTagProperty(tagPropertyId);
	}

	/**
	* Returns all the asset tag property instances with the specified tag ID.
	*
	* @param tagId the primary key of the tag
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyService.getTagProperties(tagId);
	}

	/**
	* Returns asset tag properties with the specified group and key.
	*
	* @param companyId the primary key of the company
	* @param key the key that refers to some value
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagPropertyValues(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyService.getTagPropertyValues(companyId, key);
	}

	/**
	* Updates the asset tag property.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @param key the new key to be associated to the value
	* @param value the new value to which the key will refer
	* @return the updated asset tag property
	* @throws PortalException if an asset tag property with the primary key
	could not be found, if the user did not have permission to update
	the asset tag, or if the key or value were invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyService.updateTagProperty(tagPropertyId, key,
			value);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AssetTagPropertyService getWrappedAssetTagPropertyService() {
		return _assetTagPropertyService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAssetTagPropertyService(
		AssetTagPropertyService assetTagPropertyService) {
		_assetTagPropertyService = assetTagPropertyService;
	}

	public AssetTagPropertyService getWrappedService() {
		return _assetTagPropertyService;
	}

	public void setWrappedService(
		AssetTagPropertyService assetTagPropertyService) {
		_assetTagPropertyService = assetTagPropertyService;
	}

	private AssetTagPropertyService _assetTagPropertyService;
}