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
 * Provides the remote service utility for CommerceShipmentItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemService
 * @see com.liferay.commerce.service.base.CommerceShipmentItemServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl
 * @generated
 */
@ProviderType
public class CommerceShipmentItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceShipmentItem addCommerceShipmentItem(
		long commerceShipmentId, long commerceOrderItemId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceShipmentItem(commerceShipmentId,
			commerceOrderItemId, quantity, serviceContext);
	}

	public static void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceShipmentItem(commerceShipmentItemId);
	}

	public static com.liferay.commerce.model.CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentItemId) {
		return getService().fetchCommerceShipmentItem(commerceShipmentItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		long groupId, long commerceShipmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipmentItem> orderByComparator) {
		return getService()
				   .getCommerceShipmentItems(groupId, commerceShipmentId,
			start, end, orderByComparator);
	}

	public static int getCommerceShipmentItemsCount(long groupId,
		long commerceShipmentId) {
		return getService()
				   .getCommerceShipmentItemsCount(groupId, commerceShipmentId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceShipmentItem updateCommerceShipmentItem(
		long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceShipmentItem(commerceShipmentItemId, quantity);
	}

	public static CommerceShipmentItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShipmentItemService, CommerceShipmentItemService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceShipmentItemService.class);
}