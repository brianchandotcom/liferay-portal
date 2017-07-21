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

package com.liferay.commerce.cart.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCartItemService}.
 *
 * @author Marco Leo
 * @see CommerceCartItemService
 * @generated
 */
@ProviderType
public class CommerceCartItemServiceWrapper implements CommerceCartItemService,
	ServiceWrapper<CommerceCartItemService> {
	public CommerceCartItemServiceWrapper(
		CommerceCartItemService commerceCartItemService) {
		_commerceCartItemService = commerceCartItemService;
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCartItem getCommerceCartItem(
		long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartItemService.getCommerceCartItem(commerceCartItemId);
	}

	@Override
	public int getCommerceCartItemsCount(long commerceCartId) {
		return _commerceCartItemService.getCommerceCartItemsCount(commerceCartId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceCartItemService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.cart.model.CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end) {
		return _commerceCartItemService.getCommerceCartItems(commerceCartId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.cart.model.CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CommerceCartItem> orderByComparator) {
		return _commerceCartItemService.getCommerceCartItems(commerceCartId,
			start, end, orderByComparator);
	}

	@Override
	public void deleteCommerceCartItem(long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartItemService.deleteCommerceCartItem(commerceCartItemId);
	}

	@Override
	public CommerceCartItemService getWrappedService() {
		return _commerceCartItemService;
	}

	@Override
	public void setWrappedService(
		CommerceCartItemService commerceCartItemService) {
		_commerceCartItemService = commerceCartItemService;
	}

	private CommerceCartItemService _commerceCartItemService;
}