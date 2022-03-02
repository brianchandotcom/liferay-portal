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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShipmentItemService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemService
 * @generated
 */
public class CommerceShipmentItemServiceWrapper
	implements CommerceShipmentItemService,
			   ServiceWrapper<CommerceShipmentItemService> {

	public CommerceShipmentItemServiceWrapper() {
		this(null);
	}

	public CommerceShipmentItemServiceWrapper(
		CommerceShipmentItemService commerceShipmentItemService) {

		_commerceShipmentItemService = commerceShipmentItemService;
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			addCommerceShipmentItem(
				String externalReferenceCode, long commerceShipmentId,
				long commerceOrderItemId, long commerceInventoryWarehouseId,
				int quantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.addCommerceShipmentItem(
			externalReferenceCode, commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			addOrUpdateCommerceShipmentItem(
				String externalReferenceCode, long commerceShipmentId,
				long commerceOrderItemId, long commerceInventoryWarehouseId,
				int quantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.addOrUpdateCommerceShipmentItem(
			externalReferenceCode, commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	@Override
	public void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShipmentItemService.deleteCommerceShipmentItem(
			commerceShipmentItemId);
	}

	@Override
	public void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShipmentItemService.deleteCommerceShipmentItem(
			commerceShipmentItemId, restoreStockQuantity);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			fetchCommerceShipmentItem(
				long commerceShipmentId, long commerceOrderItemId,
				long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.fetchCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			fetchCommerceShipmentItem(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.fetchCommerceShipmentItem(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			getCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.getCommerceShipmentItem(
			commerceShipmentItemId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem>
			getCommerceShipmentItems(long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.getCommerceShipmentItems(
			commerceOrderItemId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem>
			getCommerceShipmentItems(
				long commerceShipmentId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceShipmentItem>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.getCommerceShipmentItems(
			commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem>
			getCommerceShipmentItemsByCommerceOrderItemId(
				long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.getCommerceShipmentItemsCount(
			commerceShipmentId);
	}

	@Override
	public int getCommerceShipmentItemsCountByCommerceOrderItemId(
			long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.
			getCommerceShipmentItemsCountByCommerceOrderItemId(
				commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentOrderItemsQuantity(
			long commerceShipmentId, long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.
			getCommerceShipmentOrderItemsQuantity(
				commerceShipmentId, commerceOrderItemId);
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
	public com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItem(
				long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.updateCommerceShipmentItem(
			commerceShipmentItemId, quantity);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItem(
				long commerceShipmentItemId, long commerceInventoryWarehouseId,
				int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.updateCommerceShipmentItem(
			commerceShipmentItemId, commerceInventoryWarehouseId, quantity);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItemExternalReferenceCode(
				String externalReferenceCode, long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemService.
			updateCommerceShipmentItemExternalReferenceCode(
				externalReferenceCode, commerceShipmentItemId);
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