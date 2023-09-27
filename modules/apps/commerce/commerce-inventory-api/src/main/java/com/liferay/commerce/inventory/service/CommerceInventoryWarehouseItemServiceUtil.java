/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceInventoryWarehouseItem. This utility wraps
 * <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseItemServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseItemService
 * @generated
 */
public class CommerceInventoryWarehouseItemServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseItemServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceInventoryWarehouseItem
			addCommerceInventoryWarehouseItem(
				String externalReferenceCode, long commerceInventoryWarehouseId,
				java.math.BigDecimal quantity, String sku,
				String unitOfMeasureKey)
		throws PortalException {

		return getService().addCommerceInventoryWarehouseItem(
			externalReferenceCode, commerceInventoryWarehouseId, quantity, sku,
			unitOfMeasureKey);
	}

	public static CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				String externalReferenceCode, long companyId,
				long commerceInventoryWarehouseId,
				java.math.BigDecimal quantity, String sku,
				String unitOfMeasureKey)
		throws PortalException {

		return getService().addOrUpdateCommerceInventoryWarehouseItem(
			externalReferenceCode, companyId, commerceInventoryWarehouseId,
			quantity, sku, unitOfMeasureKey);
	}

	public static void deleteCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId)
		throws PortalException {

		getService().deleteCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId);
	}

	public static void deleteCommerceInventoryWarehouseItems(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		getService().deleteCommerceInventoryWarehouseItems(
			companyId, sku, unitOfMeasureKey);
	}

	public static CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku,
				String unitOfMeasureKey)
		throws PortalException {

		return getService().fetchCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseId, sku, unitOfMeasureKey);
	}

	public static CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().
			fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	public static CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId);
	}

	public static CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku,
				String unitOfMeasureKey)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseId, sku, unitOfMeasureKey);
	}

	public static List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(
				long commerceInventoryWarehouseId, int start, int end)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItems(
			commerceInventoryWarehouseId, start, end);
	}

	public static List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyId(
				long companyId, int start, int end)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemsByCompanyId(
			companyId, start, end);
	}

	public static List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey(
				long companyId, String sku, String unitOfMeasureKey, int start,
				int end)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().
			getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey(
				companyId, sku, unitOfMeasureKey, start, end);
	}

	public static int getCommerceInventoryWarehouseItemsCount(
			long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemsCount(
			commerceInventoryWarehouseId);
	}

	public static int getCommerceInventoryWarehouseItemsCount(
			long companyId, long groupId, String sku, String unitOfMeasureKey)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemsCount(
			companyId, groupId, sku, unitOfMeasureKey);
	}

	public static int getCommerceInventoryWarehouseItemsCount(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemsCount(
			companyId, sku, unitOfMeasureKey);
	}

	public static int getCommerceInventoryWarehouseItemsCountByCompanyId(
			long companyId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemsCountByCompanyId(
			companyId);
	}

	public static int getCommerceInventoryWarehouseItemsCountByModifiedDate(
			long companyId, java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				companyId, startDate, endDate);
	}

	public static List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				long companyId, java.util.Date startDate,
				java.util.Date endDate, int start, int end)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				companyId, startDate, endDate, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.math.BigDecimal getStockQuantity(
		long companyId, long groupId, String sku, String unitOfMeasureKey) {

		return getService().getStockQuantity(
			companyId, groupId, sku, unitOfMeasureKey);
	}

	public static java.math.BigDecimal getStockQuantity(
		long companyId, String sku, String unitOfMeasureKey) {

		return getService().getStockQuantity(companyId, sku, unitOfMeasureKey);
	}

	public static CommerceInventoryWarehouseItem
			increaseCommerceInventoryWarehouseItemQuantity(
				long commerceInventoryWarehouseItemId,
				java.math.BigDecimal quantity)
		throws PortalException {

		return getService().increaseCommerceInventoryWarehouseItemQuantity(
			commerceInventoryWarehouseItemId, quantity);
	}

	public static void moveQuantitiesBetweenWarehouses(
			long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, java.math.BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException {

		getService().moveQuantitiesBetweenWarehouses(
			fromCommerceInventoryWarehouseId, toCommerceInventoryWarehouseId,
			quantity, sku, unitOfMeasureKey);
	}

	public static CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId,
				java.math.BigDecimal quantity,
				java.math.BigDecimal reservedQuantity, long mvccVersion)
		throws PortalException {

		return getService().updateCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId, quantity, reservedQuantity,
			mvccVersion);
	}

	public static CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId, long mvccVersion,
				java.math.BigDecimal quantity, String unitOfMeasureKey)
		throws PortalException {

		return getService().updateCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId, mvccVersion, quantity,
			unitOfMeasureKey);
	}

	public static CommerceInventoryWarehouseItemService getService() {
		return _service;
	}

	public static void setService(
		CommerceInventoryWarehouseItemService service) {

		_service = service;
	}

	private static volatile CommerceInventoryWarehouseItemService _service;

}