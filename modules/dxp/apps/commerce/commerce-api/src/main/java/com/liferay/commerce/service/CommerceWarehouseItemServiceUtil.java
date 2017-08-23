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
 * Provides the remote service utility for CommerceWarehouseItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceWarehouseItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseItemService
 * @see com.liferay.commerce.service.base.CommerceWarehouseItemServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceWarehouseItemServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWarehouseItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceWarehouseItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceWarehouseItem addCommerceWarehouseItem(
		long commerceWarehouseId, java.lang.String className, long classPK,
		int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWarehouseItem(commerceWarehouseId, className,
			classPK, quantity, serviceContext);
	}

	public static void deleteCommerceWarehouseItem(long commerceWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceWarehouseItem(commerceWarehouseItemId);
	}

	public static com.liferay.commerce.model.CommerceWarehouseItem getCommerceWarehouseItem(
		long commerceWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouseItem(commerceWarehouseItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouseItems(className, classPK);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouseItem> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWarehouseItems(className, classPK, start, end,
			orderByComparator);
	}

	public static int getCommerceWarehouseItemsCount(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouseItemsCount(className, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceWarehouseItem updateCommerceWarehouseItem(
		long commerceWarehouseItemId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceWarehouseItem(commerceWarehouseItemId,
			quantity, serviceContext);
	}

	public static CommerceWarehouseItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWarehouseItemService, CommerceWarehouseItemService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceWarehouseItemService.class);
}