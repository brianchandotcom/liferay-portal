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
 * Provides a wrapper for {@link CommerceWarehouseItemService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseItemService
 * @generated
 */
@ProviderType
public class CommerceWarehouseItemServiceWrapper
	implements CommerceWarehouseItemService,
		ServiceWrapper<CommerceWarehouseItemService> {
	public CommerceWarehouseItemServiceWrapper(
		CommerceWarehouseItemService commerceWarehouseItemService) {
		_commerceWarehouseItemService = commerceWarehouseItemService;
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouseItem addCommerceWarehouseItem(
		long commerceWarehouseId, long cpInstanceId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItemService.addCommerceWarehouseItem(commerceWarehouseId,
			cpInstanceId, quantity, serviceContext);
	}

	@Override
	public void deleteCommerceWarehouseItem(long commerceWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceWarehouseItemService.deleteCommerceWarehouseItem(commerceWarehouseItemId);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouseItem fetchCommerceWarehouseItem(
		long commerceWarehouseId, long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItemService.fetchCommerceWarehouseItem(commerceWarehouseId,
			cpInstanceId);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouseItem getCommerceWarehouseItem(
		long commerceWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItemService.getCommerceWarehouseItem(commerceWarehouseItemId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItemService.getCommerceWarehouseItems(cpInstanceId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouseItem> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItemService.getCommerceWarehouseItems(cpInstanceId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehouseItemsCount(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItemService.getCommerceWarehouseItemsCount(cpInstanceId);
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItemService.getCPInstanceQuantity(cpInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceWarehouseItemService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouseItem updateCommerceWarehouseItem(
		long commerceWarehouseItemId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseItemService.updateCommerceWarehouseItem(commerceWarehouseItemId,
			quantity, serviceContext);
	}

	@Override
	public CommerceWarehouseItemService getWrappedService() {
		return _commerceWarehouseItemService;
	}

	@Override
	public void setWrappedService(
		CommerceWarehouseItemService commerceWarehouseItemService) {
		_commerceWarehouseItemService = commerceWarehouseItemService;
	}

	private CommerceWarehouseItemService _commerceWarehouseItemService;
}