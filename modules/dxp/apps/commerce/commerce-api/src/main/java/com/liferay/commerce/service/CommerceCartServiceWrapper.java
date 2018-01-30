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
 * Provides a wrapper for {@link CommerceCartService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartService
 * @generated
 */
@ProviderType
public class CommerceCartServiceWrapper implements CommerceCartService,
	ServiceWrapper<CommerceCartService> {
	public CommerceCartServiceWrapper(CommerceCartService commerceCartService) {
		_commerceCartService = commerceCartService;
	}

	@Override
	public com.liferay.commerce.model.CommerceCart addCommerceCart(
		java.lang.String name, boolean defaultCart, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartService.addCommerceCart(name, defaultCart, type,
			serviceContext);
	}

	@Override
	public void deleteCommerceCart(long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartService.deleteCommerceCart(commerceCartId);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart fetchCommerceCart(
		long commerceCartId) {
		return _commerceCartService.fetchCommerceCart(commerceCartId);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart fetchCommerceCart(
		java.lang.String uuid, long groupId) {
		return _commerceCartService.fetchCommerceCart(uuid, groupId);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart fetchDefaultCommerceCart(
		long groupId, long userId, boolean defaultCart, int type) {
		return _commerceCartService.fetchDefaultCommerceCart(groupId, userId,
			defaultCart, type);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart getCommerceCart(
		long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartService.getCommerceCart(commerceCartId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCart> getCommerceCarts(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCart> orderByComparator) {
		return _commerceCartService.getCommerceCarts(groupId, type, start, end,
			orderByComparator);
	}

	@Override
	public int getCommerceCartsCount(long groupId, int type) {
		return _commerceCartService.getCommerceCartsCount(groupId, type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceCartService.getOSGiServiceIdentifier();
	}

	@Override
	public void mergeGuestCommerceCart(long guestCommerceCartId,
		long userCommerceCartId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartService.mergeGuestCommerceCart(guestCommerceCartId,
			userCommerceCartId, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart updateCommerceCart(
		long commerceCartId, long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		java.lang.String shippingOptionName, double shippingPrice)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartService.updateCommerceCart(commerceCartId,
			billingAddressId, shippingAddressId, commercePaymentMethodId,
			commerceShippingMethodId, shippingOptionName, shippingPrice);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart updateCommerceCart(
		long commerceCartId, java.lang.String name, boolean defaultCart)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartService.updateCommerceCart(commerceCartId, name,
			defaultCart);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart updateUser(
		long commerceCartId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartService.updateUser(commerceCartId, userId);
	}

	@Override
	public CommerceCartService getWrappedService() {
		return _commerceCartService;
	}

	@Override
	public void setWrappedService(CommerceCartService commerceCartService) {
		_commerceCartService = commerceCartService;
	}

	private CommerceCartService _commerceCartService;
}