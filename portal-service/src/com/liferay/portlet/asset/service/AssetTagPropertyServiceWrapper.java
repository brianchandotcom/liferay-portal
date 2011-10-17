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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagPropertyService}.
 * </p>
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

	public com.liferay.portlet.asset.model.AssetTagProperty addTagProperty(
		long tagId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyService.addTagProperty(tagId, key, value);
	}

	public void deleteTagProperty(long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyService.deleteTagProperty(tagPropertyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyService.getTagProperties(tagId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagPropertyValues(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyService.getTagPropertyValues(companyId, key);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyService.updateTagProperty(tagPropertyId, key,
			value);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public AssetTagPropertyService getWrappedAssetTagPropertyService() {
		return _assetTagPropertyService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
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