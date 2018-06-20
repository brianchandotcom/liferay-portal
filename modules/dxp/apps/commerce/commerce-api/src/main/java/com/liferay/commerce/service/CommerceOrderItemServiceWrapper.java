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
 * Provides a wrapper for {@link CommerceOrderItemService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItemService
 * @generated
 */
@ProviderType
public class CommerceOrderItemServiceWrapper implements CommerceOrderItemService,
	ServiceWrapper<CommerceOrderItemService> {
	public CommerceOrderItemServiceWrapper(
		CommerceOrderItemService commerceOrderItemService) {
		_commerceOrderItemService = commerceOrderItemService;
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem addCommerceOrderItem(
		long commerceOrderId, long cpInstanceId, int quantity,
		int shippedQuantity, String json, java.math.BigDecimal price,
		com.liferay.commerce.context.CommerceContext commerceContext,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.addCommerceOrderItem(commerceOrderId,
			cpInstanceId, quantity, shippedQuantity, json, price,
			commerceContext, serviceContext);
	}

	@Override
	public void deleteCommerceOrderItem(long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrderItemService.deleteCommerceOrderItem(commerceOrderItemId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem fetchCommerceOrderItem(
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.fetchCommerceOrderItem(commerceOrderItemId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderItem> getAvailableForShipmentCommerceOrderItems(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.getAvailableForShipmentCommerceOrderItems(commerceOrderId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem getCommerceOrderItem(
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.getCommerceOrderItem(commerceOrderItemId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.getCommerceOrderItems(commerceOrderId,
			start, end);
	}

	@Override
	public int getCommerceOrderItemsCount(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.getCommerceOrderItemsCount(commerceOrderId);
	}

	@Override
	public int getCommerceOrderItemsQuantity(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.getCommerceOrderItemsQuantity(commerceOrderId);
	}

	@Override
	public int getCommerceWarehouseItemQuantity(long commerceOrderItemId,
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.getCommerceWarehouseItemQuantity(commerceOrderItemId,
			commerceWarehouseId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderItemService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		long commerceOrderId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.search(commerceOrderId, keywords,
			start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		long commerceOrderId, String sku, String name, boolean andOperator,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.search(commerceOrderId, sku, name,
			andOperator, start, end, sort);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		long commerceOrderItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.updateCommerceOrderItem(commerceOrderItemId,
			quantity);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		long commerceOrderItemId, int quantity, String json,
		java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemService.updateCommerceOrderItem(commerceOrderItemId,
			quantity, json, price);
	}

	@Override
	public CommerceOrderItemService getWrappedService() {
		return _commerceOrderItemService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderItemService commerceOrderItemService) {
		_commerceOrderItemService = commerceOrderItemService;
	}

	private CommerceOrderItemService _commerceOrderItemService;
}