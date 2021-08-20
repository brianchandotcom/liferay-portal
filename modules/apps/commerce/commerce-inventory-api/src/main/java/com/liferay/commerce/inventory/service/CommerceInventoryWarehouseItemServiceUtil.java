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
				long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		return getService().addCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseId, sku, quantity);
	}

	public static CommerceInventoryWarehouseItem
			addCommerceInventoryWarehouseItem(
				String externalReferenceCode, long commerceInventoryWarehouseId,
				String sku, int quantity)
		throws PortalException {

		return getService().addCommerceInventoryWarehouseItem(
			externalReferenceCode, commerceInventoryWarehouseId, sku, quantity);
	}

	public static CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		return getService().addOrUpdateCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseId, sku, quantity);
	}

	public static CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				String externalReferenceCode, long companyId,
				long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		return getService().addOrUpdateCommerceInventoryWarehouseItem(
			externalReferenceCode, companyId, commerceInventoryWarehouseId, sku,
			quantity);
	}

	public static void deleteCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId)
		throws PortalException {

		getService().deleteCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId);
	}

	public static void deleteCommerceInventoryWarehouseItems(
			long companyId, String sku)
		throws PortalException {

		getService().deleteCommerceInventoryWarehouseItems(companyId, sku);
	}

	public static CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku)
		throws PortalException {

		return getService().fetchCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseId, sku);
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

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #getCommerceInventoryWarehouseItemByReferenceCode(String,
	 long)}
	 */
	@Deprecated
	public static CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItemByReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItemByReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemByReferenceCode(
			externalReferenceCode, companyId);
	}

	public static List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(
				long commerceInventoryWarehouseId, int start, int end)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItems(
			commerceInventoryWarehouseId, start, end);
	}

	public static List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(
				long companyId, String sku, int start, int end)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItems(
			companyId, sku, start, end);
	}

	public static List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyId(
				long companyId, int start, int end)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemsByCompanyId(
			companyId, start, end);
	}

	public static List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
				long companyId, String sku, int start, int end)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
			companyId, sku, start, end);
	}

	public static int getCommerceInventoryWarehouseItemsCount(
			long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemsCount(
			commerceInventoryWarehouseId);
	}

	public static int getCommerceInventoryWarehouseItemsCount(
			long companyId, String sku)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseItemsCount(
			companyId, sku);
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

	public static CommerceInventoryWarehouseItem
			increaseCommerceInventoryWarehouseItemQuantity(
				long commerceInventoryWarehouseItemId, int quantity)
		throws PortalException {

		return getService().increaseCommerceInventoryWarehouseItemQuantity(
			commerceInventoryWarehouseItemId, quantity);
	}

	public static void moveQuantitiesBetweenWarehouses(
			long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		getService().moveQuantitiesBetweenWarehouses(
			fromCommerceInventoryWarehouseId, toCommerceInventoryWarehouseId,
			sku, quantity);
	}

	public static CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId, int quantity,
				int reservedQuantity, long mvccVersion)
		throws PortalException {

		return getService().updateCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId, quantity, reservedQuantity,
			mvccVersion);
	}

	public static CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId, int quantity,
				long mvccVersion)
		throws PortalException {

		return getService().updateCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId, quantity, mvccVersion);
	}

	public static CommerceInventoryWarehouseItemService getService() {
		return _service;
	}

	private static volatile CommerceInventoryWarehouseItemService _service;

}