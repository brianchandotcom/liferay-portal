/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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