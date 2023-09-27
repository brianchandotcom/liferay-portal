/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.category.property.service;

import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for AssetCategoryProperty. This utility wraps
 * <code>com.liferay.asset.category.property.service.impl.AssetCategoryPropertyServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyService
 * @generated
 */
public class AssetCategoryPropertyServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.category.property.service.impl.AssetCategoryPropertyServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AssetCategoryProperty addCategoryProperty(
			long entryId, String key, String value)
		throws PortalException {

		return getService().addCategoryProperty(entryId, key, value);
	}

	public static void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException {

		getService().deleteCategoryProperty(categoryPropertyId);
	}

	public static List<AssetCategoryProperty> getCategoryProperties(
		long entryId) {

		return getService().getCategoryProperties(entryId);
	}

	public static List<AssetCategoryProperty> getCategoryPropertyValues(
		long companyId, String key) {

		return getService().getCategoryPropertyValues(companyId, key);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AssetCategoryProperty updateCategoryProperty(
			long userId, long categoryPropertyId, String key, String value)
		throws PortalException {

		return getService().updateCategoryProperty(
			userId, categoryPropertyId, key, value);
	}

	public static AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException {

		return getService().updateCategoryProperty(
			categoryPropertyId, key, value);
	}

	public static AssetCategoryPropertyService getService() {
		return _service;
	}

	public static void setService(AssetCategoryPropertyService service) {
		_service = service;
	}

	private static volatile AssetCategoryPropertyService _service;

}