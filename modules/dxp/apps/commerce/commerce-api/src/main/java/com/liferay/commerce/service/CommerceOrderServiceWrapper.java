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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderService
 * @generated
 */
@ProviderType
public class CommerceOrderServiceWrapper implements CommerceOrderService,
	ServiceWrapper<CommerceOrderService> {
	public CommerceOrderServiceWrapper(
		CommerceOrderService commerceOrderService) {
		_commerceOrderService = commerceOrderService;
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder addCommerceOrderFromCart(
		long commerceCartId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderService.addCommerceOrderFromCart(commerceCartId,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderService.fetchCommerceOrder(commerceOrderId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder getCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderService.getCommerceOrder(commerceOrderId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder getCommerceOrderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderService.getCommerceOrderByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrders(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderService.getCommerceOrders(groupId, start, end,
			orderByComparator);
	}

	@Override
	public int getCommerceOrdersCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderService.getCommerceOrdersCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceOrderService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updatePurchaseOrderNumber(
		long commerceOrderId, java.lang.String purchaseOrderNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderService.updatePurchaseOrderNumber(commerceOrderId,
			purchaseOrderNumber);
	}

	@Override
	public CommerceOrderService getWrappedService() {
		return _commerceOrderService;
	}

	@Override
	public void setWrappedService(CommerceOrderService commerceOrderService) {
		_commerceOrderService = commerceOrderService;
	}

	private CommerceOrderService _commerceOrderService;
}