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

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceShipmentItem. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemService
 * @generated
 */
public class CommerceShipmentItemServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceShipmentItem addCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			int quantity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceShipmentItem(
			externalReferenceCode, commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, serviceContext);
	}

	public static CommerceShipmentItem addOrUpdateCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			int quantity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCommerceShipmentItem(
			externalReferenceCode, commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	public static void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws PortalException {

		getService().deleteCommerceShipmentItem(commerceShipmentItemId);
	}

	public static void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws PortalException {

		getService().deleteCommerceShipmentItem(
			commerceShipmentItemId, restoreStockQuantity);
	}

	public static CommerceShipmentItem fetchCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().fetchCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	public static CommerceShipmentItem fetchCommerceShipmentItem(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().fetchCommerceShipmentItem(
			companyId, externalReferenceCode);
	}

	public static CommerceShipmentItem getCommerceShipmentItem(
			long commerceShipmentItemId)
		throws PortalException {

		return getService().getCommerceShipmentItem(commerceShipmentItemId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceOrderItemId)
		throws PortalException {

		return getService().getCommerceShipmentItems(commerceOrderItemId);
	}

	public static List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceShipmentId, int start, int end,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws PortalException {

		return getService().getCommerceShipmentItems(
			commerceShipmentId, start, end, orderByComparator);
	}

	public static List<CommerceShipmentItem>
			getCommerceShipmentItemsByCommerceOrderItemId(
				long commerceOrderItemId)
		throws PortalException {

		return getService().getCommerceShipmentItemsByCommerceOrderItemId(
			commerceOrderItemId);
	}

	public static int getCommerceShipmentItemsCount(long commerceShipmentId)
		throws PortalException {

		return getService().getCommerceShipmentItemsCount(commerceShipmentId);
	}

	public static int getCommerceShipmentItemsCountByCommerceOrderItemId(
			long commerceOrderItemId)
		throws PortalException {

		return getService().getCommerceShipmentItemsCountByCommerceOrderItemId(
			commerceOrderItemId);
	}

	public static int getCommerceShipmentOrderItemsQuantity(
			long commerceShipmentId, long commerceOrderItemId)
		throws PortalException {

		return getService().getCommerceShipmentOrderItemsQuantity(
			commerceShipmentId, commerceOrderItemId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, int quantity)
		throws PortalException {

		return getService().updateCommerceShipmentItem(
			commerceShipmentItemId, quantity);
	}

	public static CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, long commerceInventoryWarehouseId,
			int quantity)
		throws PortalException {

		return getService().updateCommerceShipmentItem(
			commerceShipmentItemId, commerceInventoryWarehouseId, quantity);
	}

	public static CommerceShipmentItem
			updateCommerceShipmentItemExternalReferenceCode(
				String externalReferenceCode, long commerceShipmentItemId)
		throws PortalException {

		return getService().updateCommerceShipmentItemExternalReferenceCode(
			externalReferenceCode, commerceShipmentItemId);
	}

	public static CommerceShipmentItemService getService() {
		return _service;
	}

	private static volatile CommerceShipmentItemService _service;

}