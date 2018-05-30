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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShipmentItemService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemService
 * @generated
 */
@ProviderType
public class CommerceShipmentItemServiceWrapper
	implements CommerceShipmentItemService,
		ServiceWrapper<CommerceShipmentItemService> {
	public CommerceShipmentItemServiceWrapper(
		CommerceShipmentItemService commerceShipmentItemService) {
		_commerceShipmentItemService = commerceShipmentItemService;
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem addCommerceShipmentItem(
		long commerceShipmentId, long commerceOrderItemId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentItemService.addCommerceShipmentItem(commerceShipmentId,
			commerceOrderItemId, quantity, serviceContext);
	}

	@Override
	public void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceShipmentItemService.deleteCommerceShipmentItem(commerceShipmentItemId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentItemId) {
		return _commerceShipmentItemService.fetchCommerceShipmentItem(commerceShipmentItemId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipmentItem> orderByComparator) {
		return _commerceShipmentItemService.getCommerceShipmentItems(commerceShipmentId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId) {
		return _commerceShipmentItemService.getCommerceShipmentItemsCount(commerceShipmentId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShipmentItemService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem updateCommerceShipmentItem(
		long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentItemService.updateCommerceShipmentItem(commerceShipmentItemId,
			quantity);
	}

	@Override
	public CommerceShipmentItemService getWrappedService() {
		return _commerceShipmentItemService;
	}

	@Override
	public void setWrappedService(
		CommerceShipmentItemService commerceShipmentItemService) {
		_commerceShipmentItemService = commerceShipmentItemService;
	}

	private CommerceShipmentItemService _commerceShipmentItemService;
}