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
				long commerceInventoryWarehouseId, String sku,
				java.util.Date availabilityDate, int quantity)
		throws PortalException {

		return getService().addCommerceInventoryReplenishmentItem(
			commerceInventoryWarehouseId, sku, availabilityDate, quantity);
	}

	public static void deleteCommerceInventoryReplenishmentItem(
			long commerceInventoryReplenishmentItemId)
		throws PortalException {

		getService().deleteCommerceInventoryReplenishmentItem(
			commerceInventoryReplenishmentItemId);
	}

	public static CommerceInventoryReplenishmentItem
			getCommerceInventoryReplenishmentItem(
				long commerceInventoryReplenishmentItemId)
		throws PortalException {

		return getService().getCommerceInventoryReplenishmentItem(
			commerceInventoryReplenishmentItemId);
	}

	public static List<CommerceInventoryReplenishmentItem>
			getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
				long companyId, String sku, int start, int end)
		throws PortalException {

		return getService().
			getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
				companyId, sku, start, end);
	}

	public static long getCommerceInventoryReplenishmentItemsCount(
			long commerceInventoryWarehouseId, String sku)
		throws PortalException {

		return getService().getCommerceInventoryReplenishmentItemsCount(
			commerceInventoryWarehouseId, sku);
	}

	public static int
			getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
				long companyId, String sku)
		throws PortalException {

		return getService().
			getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
				companyId, sku);
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
				long commerceInventoryReplenishmentItemId,
				java.util.Date availabilityDate, int quantity, long mvccVersion)
		throws PortalException {

		return getService().updateCommerceInventoryReplenishmentItem(
			commerceInventoryReplenishmentItemId, availabilityDate, quantity,
			mvccVersion);
	}

	public static CommerceInventoryReplenishmentItemService getService() {
		return _service;
	}

	private static volatile CommerceInventoryReplenishmentItemService _service;

}