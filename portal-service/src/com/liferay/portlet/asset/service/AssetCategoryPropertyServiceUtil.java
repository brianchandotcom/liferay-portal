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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for AssetCategoryProperty. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetCategoryPropertyServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyService
 * @see com.liferay.portlet.asset.service.base.AssetCategoryPropertyServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetCategoryPropertyServiceImpl
 * @generated
 */
@ProviderType
public class AssetCategoryPropertyServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetCategoryPropertyServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.asset.model.AssetCategoryProperty addCategoryProperty(
		long entryId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCategoryProperty(entryId, key, value);
	}

	public static void deleteCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCategoryProperty(categoryPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties(
		long entryId) {
		return getService().getCategoryProperties(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryPropertyValues(
		long companyId, java.lang.String key) {
		return getService().getCategoryPropertyValues(companyId, key);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long categoryPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCategoryProperty(categoryPropertyId, key, value);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long userId, long categoryPropertyId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCategoryProperty(userId, categoryPropertyId, key,
			value);
	}

	public static AssetCategoryPropertyService getService() {
		if (_service == null) {
			_service = (AssetCategoryPropertyService)PortalBeanLocatorUtil.locate(AssetCategoryPropertyService.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryPropertyServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static AssetCategoryPropertyService _service;
}