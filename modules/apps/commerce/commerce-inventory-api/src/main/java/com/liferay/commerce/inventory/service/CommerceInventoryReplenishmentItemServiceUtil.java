/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceInventoryReplenishmentItem. This utility wraps
 * <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryReplenishmentItemServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryReplenishmentItemService
 * @generated
 */
public class CommerceInventoryReplenishmentItemServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryReplenishmentItemServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceInventoryReplenishmentItem
			addCommerceInventoryReplenishmentItem(
				String externalReferenceCode, long commerceInventoryWarehouseId,
				java.util.Date availabilityDate, java.math.BigDecimal quantity,
				String sku, String unitOfMeasureKey)
		throws PortalException {

		return getService().addCommerceInventoryReplenishmentItem(
			externalReferenceCode, commerceInventoryWarehouseId,
			availabilityDate, quantity, sku, unitOfMeasureKey);
	}

	public static void deleteCommerceInventoryReplenishmentItem(
			long commerceInventoryReplenishmentItemId)
		throws PortalException {

		getService().deleteCommerceInventoryReplenishmentItem(
			commerceInventoryReplenishmentItemId);
	}

	public static void deleteCommerceInventoryReplenishmentItems(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		getService().deleteCommerceInventoryReplenishmentItems(
			companyId, sku, unitOfMeasureKey);
	}

	public static CommerceInventoryReplenishmentItem
			fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().
			fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	public static CommerceInventoryReplenishmentItem
			getCommerceInventoryReplenishmentItem(
				long commerceInventoryReplenishmentItemId)
		throws PortalException {

		return getService().getCommerceInventoryReplenishmentItem(
			commerceInventoryReplenishmentItemId);
	}

	public static List<CommerceInventoryReplenishmentItem>
			getCommerceInventoryReplenishmentItemsByCommerceInventoryWarehouseId(
				long commerceInventoryWarehouseId, int start, int end)
		throws PortalException {

		return getService().
			getCommerceInventoryReplenishmentItemsByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId, start, end);
	}

	public static List<CommerceInventoryReplenishmentItem>
			getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey(
				long companyId, String sku, String unitOfMeasureKey, int start,
				int end)
		throws PortalException {

		return getService().
			getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey(
				companyId, sku, unitOfMeasureKey, start, end);
	}

	public static java.math.BigDecimal
			getCommerceInventoryReplenishmentItemsCount(
				long commerceInventoryWarehouseId, String sku,
				String unitOfMeasureKey)
		throws PortalException {

		return getService().getCommerceInventoryReplenishmentItemsCount(
			commerceInventoryWarehouseId, sku, unitOfMeasureKey);
	}

	public static int
			getCommerceInventoryReplenishmentItemsCountByCommerceInventoryWarehouseId(
				long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().
			getCommerceInventoryReplenishmentItemsCountByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId);
	}

	public static int
			getCommerceInventoryReplenishmentItemsCountByCompanyIdSkuAndUnitOfMeasureKey(
				long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		return getService().
			getCommerceInventoryReplenishmentItemsCountByCompanyIdSkuAndUnitOfMeasureKey(
				companyId, sku, unitOfMeasureKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceInventoryReplenishmentItem
			updateCommerceInventoryReplenishmentItem(
				String externalReferenceCode,
				long commerceInventoryReplenishmentItemId,
				java.util.Date availabilityDate, java.math.BigDecimal quantity,
				long mvccVersion)
		throws PortalException {

		return getService().updateCommerceInventoryReplenishmentItem(
			externalReferenceCode, commerceInventoryReplenishmentItemId,
			availabilityDate, quantity, mvccVersion);
	}

	public static CommerceInventoryReplenishmentItemService getService() {
		return _service;
	}

	public static void setService(
		CommerceInventoryReplenishmentItemService service) {

		_service = service;
	}

	private static volatile CommerceInventoryReplenishmentItemService _service;

}