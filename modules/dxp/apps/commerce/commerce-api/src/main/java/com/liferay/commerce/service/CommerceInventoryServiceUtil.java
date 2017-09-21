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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceInventory. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceInventoryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceInventoryService
 * @see com.liferay.commerce.service.base.CommerceInventoryServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceInventoryServiceImpl
 * @generated
 */
@ProviderType
public class CommerceInventoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceInventoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceInventory addCommerceInventory(
		long cpDefinitionId, java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceInventory(cpDefinitionId,
			commerceInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

	public static void deleteCommerceInventory(long commerceInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceInventory(commerceInventoryId);
	}

	public static com.liferay.commerce.model.CommerceInventory fetchCommerceInventory(
		long commerceInventoryId) {
		return getService().fetchCommerceInventory(commerceInventoryId);
	}

	public static com.liferay.commerce.model.CommerceInventory fetchCommerceInventoryByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCommerceInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceInventory updateCommerceInventory(
		long commerceInventoryId, java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceInventory(commerceInventoryId,
			commerceInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

	public static CommerceInventoryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceInventoryService, CommerceInventoryService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceInventoryService.class);
}