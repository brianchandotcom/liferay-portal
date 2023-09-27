/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public CommerceInventoryWarehouseItemServiceWrapper() {
		this(null);
	}

	public CommerceInventoryWarehouseItemServiceWrapper(
		CommerceInventoryWarehouseItemService
			commerceInventoryWarehouseItemService) {

		_commerceInventoryWarehouseItemService =
			commerceInventoryWarehouseItemService;
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			addCommerceInventoryWarehouseItem(
				String externalReferenceCode, long commerceInventoryWarehouseId,
				java.math.BigDecimal quantity, String sku,
				String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			addCommerceInventoryWarehouseItem(
				externalReferenceCode, commerceInventoryWarehouseId, quantity,
				sku, unitOfMeasureKey);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				String externalReferenceCode, long companyId,
				long commerceInventoryWarehouseId,
				java.math.BigDecimal quantity, String sku,
				String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			addOrUpdateCommerceInventoryWarehouseItem(
				externalReferenceCode, companyId, commerceInventoryWarehouseId,
				quantity, sku, unitOfMeasureKey);
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
			long companyId, String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceInventoryWarehouseItemService.
			deleteCommerceInventoryWarehouseItems(
				companyId, sku, unitOfMeasureKey);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku,
				String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			fetchCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku, unitOfMeasureKey);
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

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku,
				String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku, unitOfMeasureKey);
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
				getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey(
					long companyId, String sku, String unitOfMeasureKey,
					int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey(
				companyId, sku, unitOfMeasureKey, start, end);
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
			long companyId, long groupId, String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCount(
				companyId, groupId, sku, unitOfMeasureKey);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
			long companyId, String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			getCommerceInventoryWarehouseItemsCount(
				companyId, sku, unitOfMeasureKey);
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
	public java.math.BigDecimal getStockQuantity(
		long companyId, long groupId, String sku, String unitOfMeasureKey) {

		return _commerceInventoryWarehouseItemService.getStockQuantity(
			companyId, groupId, sku, unitOfMeasureKey);
	}

	@Override
	public java.math.BigDecimal getStockQuantity(
		long companyId, String sku, String unitOfMeasureKey) {

		return _commerceInventoryWarehouseItemService.getStockQuantity(
			companyId, sku, unitOfMeasureKey);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			increaseCommerceInventoryWarehouseItemQuantity(
				long commerceInventoryWarehouseItemId,
				java.math.BigDecimal quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			increaseCommerceInventoryWarehouseItemQuantity(
				commerceInventoryWarehouseItemId, quantity);
	}

	@Override
	public void moveQuantitiesBetweenWarehouses(
			long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, java.math.BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceInventoryWarehouseItemService.moveQuantitiesBetweenWarehouses(
			fromCommerceInventoryWarehouseId, toCommerceInventoryWarehouseId,
			quantity, sku, unitOfMeasureKey);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId,
				java.math.BigDecimal quantity,
				java.math.BigDecimal reservedQuantity, long mvccVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId, quantity, reservedQuantity,
				mvccVersion);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId, long mvccVersion,
				java.math.BigDecimal quantity, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseItemService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId, mvccVersion, quantity,
				unitOfMeasureKey);
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