/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceVirtualOrderItemService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemService
 * @generated
 */
public class CommerceVirtualOrderItemServiceWrapper
	implements CommerceVirtualOrderItemService,
			   ServiceWrapper<CommerceVirtualOrderItemService> {

	public CommerceVirtualOrderItemServiceWrapper() {
		this(null);
	}

	public CommerceVirtualOrderItemServiceWrapper(
		CommerceVirtualOrderItemService commerceVirtualOrderItemService) {

		_commerceVirtualOrderItemService = commerceVirtualOrderItemService;
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem fetchCommerceVirtualOrderItem(
				long commerceVirtualOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemService.fetchCommerceVirtualOrderItem(
			commerceVirtualOrderItemId);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					long commerceOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemService.
			fetchCommerceVirtualOrderItemByCommerceOrderItemId(
				commerceOrderItemId);
	}

	@Override
	public java.io.File getFile(long commerceVirtualOrderItemId)
		throws Exception {

		return _commerceVirtualOrderItemService.getFile(
			commerceVirtualOrderItemId);
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
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
				long commerceVirtualOrderItemId, long fileEntryId, String url,
				int activationStatus, long duration, int usages, int maxUsages,
				boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemService.updateCommerceVirtualOrderItem(
			commerceVirtualOrderItemId, fileEntryId, url, activationStatus,
			duration, usages, maxUsages, active);
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