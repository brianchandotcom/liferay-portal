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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceShipmentItemQuantityException;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.service.base.CommerceShipmentItemLocalServiceBaseImpl;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentItemLocalServiceImpl
	extends CommerceShipmentItemLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShipmentItem addCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce shipment item

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceShipmentItemId = counterLocalService.increment();

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.create(commerceShipmentItemId);

		commerceShipmentItem.setGroupId(groupId);
		commerceShipmentItem.setCompanyId(user.getCompanyId());
		commerceShipmentItem.setUserId(user.getUserId());
		commerceShipmentItem.setUserName(user.getFullName());
		commerceShipmentItem.setCommerceShipmentId(commerceShipmentId);
		commerceShipmentItem.setCommerceOrderItemId(commerceOrderItemId);
		commerceShipmentItem.setQuantity(quantity);

		commerceShipmentItem = commerceShipmentItemPersistence.update(
			commerceShipmentItem);

		// Commerce order item

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.incrementShippedQuantity(
				commerceOrderItemId, quantity);

		// Commerce low stock activity

		_checkCommerceLowStockActivity(
			commerceOrderItem, commerceShipmentId, quantity);

		return commerceShipmentItem;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceShipmentItem deleteCommerceShipmentItem(
		CommerceShipmentItem commerceShipmentItem) {

		// Commerce shipment item

		commerceShipmentItemPersistence.remove(commerceShipmentItem);

		// Commerce order item

		CommerceOrderItem commerceOrderItem = null;

		int shippedQuantity = commerceShipmentItem.getQuantity() * -1;

		try {
			commerceOrderItem =
				commerceOrderItemLocalService.incrementShippedQuantity(
					commerceShipmentItem.getCommerceOrderItemId(),
					shippedQuantity);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		// Commerce low stock activity

		try {
			_checkCommerceLowStockActivity(
				commerceOrderItem, commerceShipmentItem.getCommerceShipmentId(),
				shippedQuantity);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return commerceShipmentItem;
	}

	@Override
	public CommerceShipmentItem deleteCommerceShipmentItem(
			long commerceShipmentItemId)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		return commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem);
	}

	@Override
	public void deleteCommerceShipmentItems(long commerceShipment) {
		List<CommerceShipmentItem> commerceShipmentItems =
			commerceShipmentItemPersistence.findByCommerceShipment(
				commerceShipment);

		for (CommerceShipmentItem commerceShipmentItem :
				commerceShipmentItems) {

			commerceShipmentItemLocalService.deleteCommerceShipmentItem(
				commerceShipmentItem);
		}
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return commerceShipmentItemPersistence.findByCommerceShipment(
			commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId) {
		return commerceShipmentItemPersistence.countByCommerceShipment(
			commerceShipmentId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, int quantity)
		throws PortalException {

		// Commerce shipment item

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		int newQuantity = quantity - commerceShipmentItem.getQuantity();

		commerceShipmentItem.setQuantity(quantity);

		commerceShipmentItem = commerceShipmentItemPersistence.update(
			commerceShipmentItem);

		// Commerce order item

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.getCommerceOrderItem(
				commerceShipmentItem.getCommerceOrderItemId());

		validate(
			commerceOrderItem, commerceShipmentItem.getCommerceShipmentId(),
			quantity, newQuantity);

		commerceOrderItemLocalService.incrementShippedQuantity(
			commerceOrderItem.getCommerceOrderItemId(), newQuantity);

		// Commerce low stock activity

		_checkCommerceLowStockActivity(
			commerceOrderItem, commerceShipmentItem.getCommerceShipmentId(),
			newQuantity);

		return commerceShipmentItem;
	}

	protected void validate(
			CommerceOrderItem commerceOrderItem, long commerceShipmentId,
			int quantity, int newQuantity)
		throws PortalException {

		int availableQuantity =
			commerceOrderItem.getQuantity() -
				commerceOrderItem.getShippedQuantity();

		CommerceShipment commerceShipment =
			commerceShipmentLocalService.getCommerceShipment(
				commerceShipmentId);

		int commerceWarehouseQuantity =
			commerceOrderItemLocalService.getCommerceWarehouseItemQuantity(
				commerceOrderItem.getCommerceOrderItemId(),
				commerceShipment.getCommerceWarehouseId());

		if ((quantity == 0) || (newQuantity > availableQuantity) ||
			(newQuantity > commerceWarehouseQuantity)) {

			throw new CommerceShipmentItemQuantityException();
		}
	}

	private void _checkCommerceLowStockActivity(
			CommerceOrderItem commerceOrderItem, long commerceShipmentId,
			int quantity)
		throws PortalException {

		if (commerceOrderItem == null) {
			return;
		}

		CPDefinitionInventory cpDefinitionInventory =
			cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					commerceOrderItem.getCPDefinitionId());

		CommerceLowStockActivity commerceLowStockActivity =
			_commerceLowStockActivityRegistry.getCommerceLowStockActivity(
				cpDefinitionInventory);

		CommerceWarehouseItem commerceWarehouseItem =
			_fetchCommerceWarehouseItem(
				commerceShipmentId, commerceOrderItem.getCPInstanceId());

		commerceLowStockActivity.check(commerceWarehouseItem, quantity);
	}

	private CommerceWarehouseItem _fetchCommerceWarehouseItem(
			long commerceShipmentId, long cpInstanceId)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentLocalService.getCommerceShipment(
				commerceShipmentId);

		return commerceWarehouseItemLocalService.fetchCommerceWarehouseItem(
			commerceShipment.getCommerceWarehouseId(), cpInstanceId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShipmentItemLocalServiceImpl.class);

	@ServiceReference(type = CommerceLowStockActivityRegistry.class)
	private CommerceLowStockActivityRegistry _commerceLowStockActivityRegistry;

}