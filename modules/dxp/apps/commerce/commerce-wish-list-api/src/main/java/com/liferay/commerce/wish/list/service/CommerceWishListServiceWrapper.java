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

package com.liferay.commerce.wish.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceWishListService}.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListService
 * @generated
 */
@ProviderType
public class CommerceWishListServiceWrapper implements CommerceWishListService,
	ServiceWrapper<CommerceWishListService> {
	public CommerceWishListServiceWrapper(
		CommerceWishListService commerceWishListService) {
		_commerceWishListService = commerceWishListService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceWishListService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceWishListService getWrappedService() {
		return _commerceWishListService;
	}

	@Override
	public void setWrappedService(
		CommerceWishListService commerceWishListService) {
		_commerceWishListService = commerceWishListService;
	}

	private CommerceWishListService _commerceWishListService;
}