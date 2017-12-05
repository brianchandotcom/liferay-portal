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
 * Provides the remote service utility for CommerceCartItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceCartItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartItemService
 * @see com.liferay.commerce.service.base.CommerceCartItemServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceCartItemServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCartItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceCartItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceCartItem addCommerceCartItem(
		long commerceCartId, long cpDefinitionId, long cpInstanceId,
		int quantity, java.lang.String json,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceCartItem(commerceCartId, cpDefinitionId,
			cpInstanceId, quantity, json, serviceContext);
	}

	public static void deleteCommerceCartItem(long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceCartItem(commerceCartItemId);
	}

	public static com.liferay.commerce.model.CommerceCartItem fetchCommerceCartItem(
		long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceCartItem(commerceCartItemId);
	}

	public static com.liferay.commerce.model.CommerceCartItem getCommerceCartItem(
		long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceCartItem(commerceCartItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end) {
		return getService().getCommerceCartItems(commerceCartId, start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCartItem> orderByComparator) {
		return getService()
				   .getCommerceCartItems(commerceCartId, start, end,
			orderByComparator);
	}

	public static int getCommerceCartItemsCount(long commerceCartId) {
		return getService().getCommerceCartItemsCount(commerceCartId);
	}

	public static int getCPInstanceQuantity(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstanceQuantity(cpInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceCartItem updateCommerceCartItem(
		long commerceCartItemId, int quantity, java.lang.String json)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceCartItem(commerceCartItemId, quantity, json);
	}

	public static CommerceCartItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCartItemService, CommerceCartItemService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCartItemService.class);
}