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

package com.liferay.commerce.product.type.virtual.order.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceVirtualOrderItemService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemService
 * @generated
 */
@ProviderType
public class CommerceVirtualOrderItemServiceWrapper
	implements CommerceVirtualOrderItemService,
		ServiceWrapper<CommerceVirtualOrderItemService> {
	public CommerceVirtualOrderItemServiceWrapper(
		CommerceVirtualOrderItemService commerceVirtualOrderItemService) {
		_commerceVirtualOrderItemService = commerceVirtualOrderItemService;
	}

	@Override
	public java.io.File getFile(long commerceVirtualOrderItemId)
		throws Exception {
		return _commerceVirtualOrderItemService.getFile(commerceVirtualOrderItemId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceVirtualOrderItemService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId, long fileEntryId, String url,
		int activationStatus, long duration, int usages, int maxUsages,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemService.updateCommerceVirtualOrderItem(commerceVirtualOrderItemId,
			fileEntryId, url, activationStatus, duration, usages, maxUsages,
			active);
	}

	@Override
	public CommerceVirtualOrderItemService getWrappedService() {
		return _commerceVirtualOrderItemService;
	}

	@Override
	public void setWrappedService(
		CommerceVirtualOrderItemService commerceVirtualOrderItemService) {
		_commerceVirtualOrderItemService = commerceVirtualOrderItemService;
	}

	private CommerceVirtualOrderItemService _commerceVirtualOrderItemService;
}