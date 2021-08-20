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

package com.liferay.commerce.inventory.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceInventoryWarehouseItemService}.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseItemService
 * @generated
 */
public class CommerceInventoryWarehouseItemServiceWrapper
	implements CommerceInventoryWarehouseItemService,
			   ServiceWrapper<CommerceInventoryWarehouseItemService> {

	public CommerceInventoryWarehouseItemServiceWrapper(
		CommerceInventoryWarehouseItemService
			commerceInventoryWarehouseItemService) {

		_commerceInventoryWarehouseItemService =
			commerceInventoryWarehouseItemService;
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			addCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			addCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku, quantity);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			addCommerceInventoryWarehouseItem(
				String externalReferenceCode, long commerceInventoryWarehouseId,
				String sku, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			addCommerceInventoryWarehouseItem(
				externalReferenceCode, commerceInventoryWarehouseId, sku,
				quantity);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			addOrUpdateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku, quantity);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				String externalReferenceCode, long companyId,
				long commerceInventoryWarehouseId, String sku, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			addOrUpdateCommerceInventoryWarehouseItem(
				externalReferenceCode, companyId, commerceInventoryWarehouseId,
				sku, quantity);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceInventoryWarehouseItemService.
			deleteCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItems(
			long companyId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceInventoryWarehouseItemService.
			deleteCommerceInventoryWarehouseItems(companyId, sku);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			fetchCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItem(commerceInventoryWarehouseItemId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #getCommerceInventoryWarehouseItemByReferenceCode(String,
	 long)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItemByReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItemByReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemByReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItems(
					long commerceInventoryWarehouseId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItems(
				commerceInventoryWarehouseId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItems(
					long companyId, String sku, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItems(companyId, sku, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItemsByCompanyId(
					long companyId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsByCompanyId(
				companyId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
					long companyId, String sku, int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
				companyId, sku, start, end);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
			long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCount(
				commerceInventoryWarehouseId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
			long companyId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCount(companyId, sku);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCountByCompanyId(
			long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCountByCompanyId(companyId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCountByModifiedDate(
			long companyId, java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				companyId, startDate, endDate);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItemsCountByModifiedDate(
					long companyId, java.util.Date startDate,
					java.util.Date endDate, int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				companyId, startDate, endDate, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceInventoryWarehouseItemService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			increaseCommerceInventoryWarehouseItemQuantity(
				long commerceInventoryWarehouseItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			increaseCommerceInventoryWarehouseItemQuantity(
				commerceInventoryWarehouseItemId, quantity);
	}

	@Override
	public void moveQuantitiesBetweenWarehouses(
			long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, String sku, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceInventoryWarehouseItemService.moveQuantitiesBetweenWarehouses(
			fromCommerceInventoryWarehouseId, toCommerceInventoryWarehouseId,
			sku, quantity);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId, int quantity,
				int reservedQuantity, long mvccVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId, quantity, reservedQuantity,
				mvccVersion);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId, int quantity,
				long mvccVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId, quantity, mvccVersion);
	}

	@Override
	public CommerceInventoryWarehouseItemService getWrappedService() {
		return _commerceInventoryWarehouseItemService;
	}

	@Override
	public void setWrappedService(
		CommerceInventoryWarehouseItemService
			commerceInventoryWarehouseItemService) {

		_commerceInventoryWarehouseItemService =
			commerceInventoryWarehouseItemService;
	}

	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

}