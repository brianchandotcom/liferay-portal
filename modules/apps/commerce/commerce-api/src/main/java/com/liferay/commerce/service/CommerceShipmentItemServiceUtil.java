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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
	public static com.liferay.commerce.model.CommerceShipmentItem
			addCommerceShipmentItem(
				long commerceShipmentId, long commerceOrderItemId,
				long commerceInventoryWarehouseId, int quantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	public static void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceShipmentItem(commerceShipmentItemId);
	}

	public static void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceShipmentItem(
			commerceShipmentItemId, restoreStockQuantity);
	}

	public static com.liferay.commerce.model.CommerceShipmentItem
			fetchCommerceShipmentItem(
				long commerceShipmentId, long commerceOrderItemId,
				long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	public static com.liferay.commerce.model.CommerceShipmentItem
			getCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceShipmentItem(commerceShipmentItemId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static java.util.List
		<com.liferay.commerce.model.CommerceShipmentItem>
				getCommerceShipmentItems(long commerceOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceShipmentItems(commerceOrderItemId);
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceShipmentItem>
				getCommerceShipmentItems(
					long commerceShipmentId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.model.CommerceShipmentItem>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceShipmentItems(
			commerceShipmentId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceShipmentItem>
				getCommerceShipmentItemsByCommerceOrderItemId(
					long commerceOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceShipmentItemsByCommerceOrderItemId(
			commerceOrderItemId);
	}

	public static int getCommerceShipmentItemsCount(long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceShipmentItemsCount(commerceShipmentId);
	}

	public static int getCommerceShipmentItemsCountByCommerceOrderItemId(
			long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceShipmentItemsCountByCommerceOrderItemId(
			commerceOrderItemId);
	}

	public static int getCommerceShipmentOrderItemsQuantity(
			long commerceShipmentId, long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

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

	public static com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItem(
				long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceShipmentItem(
			commerceShipmentItemId, quantity);
	}

	public static com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItem(
				long commerceShipmentItemId, long commerceInventoryWarehouseId,
				int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceShipmentItem(
			commerceShipmentItemId, commerceInventoryWarehouseId, quantity);
	}

	public static CommerceShipmentItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceShipmentItemService, CommerceShipmentItemService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceShipmentItemService.class);

		ServiceTracker<CommerceShipmentItemService, CommerceShipmentItemService>
			serviceTracker =
				new ServiceTracker
					<CommerceShipmentItemService, CommerceShipmentItemService>(
						bundle.getBundleContext(),
						CommerceShipmentItemService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}