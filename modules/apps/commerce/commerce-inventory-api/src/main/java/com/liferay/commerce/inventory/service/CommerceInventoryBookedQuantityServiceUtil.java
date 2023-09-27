/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceInventoryBookedQuantity. This utility wraps
 * <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryBookedQuantityServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryBookedQuantityService
 * @generated
 */
public class CommerceInventoryBookedQuantityServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryBookedQuantityServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<CommerceInventoryBookedQuantity>
			getCommerceInventoryBookedQuantities(
				long companyId, String sku, String unitOfMeasureKey, int start,
				int end)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getCommerceInventoryBookedQuantities(
			companyId, sku, unitOfMeasureKey, start, end);
	}

	public static List<CommerceInventoryBookedQuantity>
			getCommerceInventoryBookedQuantities(
				long companyId, String keywords, String sku,
				String unitOfMeasureKey, int start, int end)
		throws PortalException {

		return getService().getCommerceInventoryBookedQuantities(
			companyId, keywords, sku, unitOfMeasureKey, start, end);
	}

	public static int getCommerceInventoryBookedQuantitiesCount(
			long companyId, String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getCommerceInventoryBookedQuantitiesCount(
			companyId, sku, unitOfMeasureKey);
	}

	public static int getCommerceInventoryBookedQuantitiesCount(
			long companyId, String keywords, String sku,
			String unitOfMeasureKey)
		throws PortalException {

		return getService().getCommerceInventoryBookedQuantitiesCount(
			companyId, keywords, sku, unitOfMeasureKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceInventoryBookedQuantityService getService() {
		return _service;
	}

	public static void setService(
		CommerceInventoryBookedQuantityService service) {

		_service = service;
	}

	private static volatile CommerceInventoryBookedQuantityService _service;

}