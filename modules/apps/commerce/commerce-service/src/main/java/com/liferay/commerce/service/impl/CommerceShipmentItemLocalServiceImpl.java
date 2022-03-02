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

import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.exception.CommerceShipmentInactiveWarehouseException;
import com.liferay.commerce.exception.CommerceShipmentItemQuantityException;
import com.liferay.commerce.exception.CommerceShipmentStatusException;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.inventory.type.constants.CommerceInventoryAuditTypeConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.base.CommerceShipmentItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
public class CommerceShipmentItemLocalServiceImpl
	extends CommerceShipmentItemLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShipmentItem addCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			int quantity, ServiceContext serviceContext)
		throws PortalException {

		// Commerce shipment item

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItemId);

		validate(
			commerceOrderItem,
			commerceShipmentLocalService.getCommerceShipment(
				commerceShipmentId),
			commerceInventoryWarehouseId, quantity, quantity);

		long commerceShipmentItemId = counterLocalService.increment();

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.create(commerceShipmentItemId);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		commerceShipmentItem.setExternalReferenceCode(externalReferenceCode);
		commerceShipmentItem.setGroupId(groupId);
		commerceShipmentItem.setCompanyId(user.getCompanyId());
		commerceShipmentItem.setUserId(user.getUserId());
		commerceShipmentItem.setUserName(user.getFullName());
		commerceShipmentItem.setCommerceShipmentId(commerceShipmentId);
		commerceShipmentItem.setCommerceOrderItemId(commerceOrderItemId);
		commerceShipmentItem.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceShipmentItem.setQuantity(quantity);

		commerceShipmentItem = commerceShipmentItemPersistence.update(
			commerceShipmentItem);

		// Stock quantity

		_updateStockQuantity(
			commerceOrderItem, commerceShipmentItem.getCommerceShipmentItemId(),
			commerceShipmentItem.getQuantity());

		// Commerce Order Item

		commerceOrderItemLocalService.incrementShippedQuantity(
			commerceShipmentItem.getCommerceOrderItemId(), quantity);

		return commerceShipmentItem;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShipmentItem addDeliverySubscriptionCommerceShipmentItem(
			long groupId, long userId, long commerceShipmentId,
			long commerceOrderItemId)
		throws PortalException {

		long commerceShipmentItemId = counterLocalService.increment();

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.create(commerceShipmentItemId);

		commerceShipmentItem.setGroupId(groupId);

		User user = userLocalService.getUser(userId);

		commerceShipmentItem.setCompanyId(user.getCompanyId());
		commerceShipmentItem.setUserId(user.getUserId());
		commerceShipmentItem.setUserName(user.getFullName());

		commerceShipmentItem.setCommerceShipmentId(commerceShipmentId);
		commerceShipmentItem.setCommerceOrderItemId(commerceOrderItemId);

		return commerceShipmentItemPersistence.update(commerceShipmentItem);
	}

	@Override
	public CommerceShipmentItem addOrUpdateCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			int quantity, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.fetchByC_ERC(
				serviceContext.getCompanyId(), externalReferenceCode);

		if (commerceShipmentItem == null) {
			return commerceShipmentItemLocalService.addCommerceShipmentItem(
				externalReferenceCode, commerceShipmentId, commerceOrderItemId,
				commerceInventoryWarehouseId, quantity, serviceContext);
		}

		return commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItem.getCommerceShipmentItemId(),
			commerceInventoryWarehouseId, quantity);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceShipmentItem deleteCommerceShipmentItem(
			CommerceShipmentItem commerceShipmentItem,
			boolean restoreStockQuantity)
		throws PortalException {

		commerceShipmentItemPersistence.remove(commerceShipmentItem);

		// Commerce order item

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.fetchCommerceOrderItem(
				commerceShipmentItem.getCommerceOrderItemId());

		if (!restoreStockQuantity) {
			if (commerceOrderItem != null) {
				commerceOrderItemLocalService.updateCommerceOrderItem(
					commerceShipmentItem.getCommerceOrderItemId(), 0);
			}

			return commerceShipmentItem;
		}

		// Commerce order item

		int shippedQuantity = Math.negateExact(
			commerceShipmentItem.getQuantity());

		try {
			commerceOrderItem =
				commerceOrderItemLocalService.incrementShippedQuantity(
					commerceShipmentItem.getCommerceOrderItemId(),
					shippedQuantity);

			// Stock quantity

			if ((commerceShipmentItem.getCommerceInventoryWarehouseId() > 0) &&
				(commerceShipmentItem.getQuantity() > 0)) {

				_restoreStockQuantity(
					commerceOrderItem, commerceShipmentItem,
					commerceShipmentItem.getQuantity());
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return commerceShipmentItem;
	}

	@Override
	public void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem, restoreStockQuantity);
	}

	@Override
	public void deleteCommerceShipmentItems(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws PortalException {

		List<CommerceShipmentItem> commerceShipmentItems =
			commerceShipmentItemPersistence.findByCommerceShipmentId(
				commerceShipmentId);

		for (CommerceShipmentItem commerceShipmentItem :
				commerceShipmentItems) {

			commerceShipmentItemLocalService.deleteCommerceShipmentItem(
				commerceShipmentItem, restoreStockQuantity);
		}
	}

	@Override
	public CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentId, long commerceOrderItemId,
		long commerceInventoryWarehouseId) {

		return commerceShipmentItemPersistence.fetchByC_C_C(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceOrderItemId) {

		return commerceShipmentItemLocalService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return commerceShipmentItemPersistence.findByCommerceShipmentId(
			commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, long commerceOrderItemId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return commerceShipmentItemPersistence.findByC_C(
			commerceShipmentId, commerceOrderItemId, start, end,
			orderByComparator);
	}

	@Override
	public List<CommerceShipmentItem>
		getCommerceShipmentItemsByCommerceOrderItemId(
			long commerceOrderItemId) {

		return commerceShipmentItemPersistence.findByCommerceOrderItemId(
			commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId) {
		return commerceShipmentItemPersistence.countByCommerceShipmentId(
			commerceShipmentId);
	}

	@Override
	public int getCommerceShipmentItemsCountByCommerceOrderItemId(
		long commerceOrderItemId) {

		return commerceShipmentItemPersistence.countByCommerceOrderItemId(
			commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentOrderItemsQuantity(
		long commerceShipmentId, long commerceOrderItemId) {

		return commerceShipmentItemFinder.getCommerceShipmentOrderItemsQuantity(
			commerceShipmentId, commerceOrderItemId);
	}

	@Override
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, int quantity)
		throws PortalException {

		// Commerce shipment item

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		return updateCommerceShipmentItem(
			commerceShipmentItemId,
			commerceShipmentItem.getCommerceInventoryWarehouseId(), quantity);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, long commerceInventoryWarehouseId,
			int quantity)
		throws PortalException {

		// Commerce shipment item

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.getCommerceOrderItem(
				commerceShipmentItem.getCommerceOrderItemId());

		int originalQuantity = commerceShipmentItem.getQuantity();

		validate(
			commerceOrderItem, commerceShipmentItem.getCommerceShipment(),
			commerceInventoryWarehouseId, originalQuantity, quantity);

		commerceShipmentItem.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceShipmentItem.setQuantity(quantity);

		commerceShipmentItem = commerceShipmentItemPersistence.update(
			commerceShipmentItem);

		int quantityDelta = quantity - originalQuantity;

		// Stock quantity

		if (commerceOrderItem.getQuantity() ==
				commerceOrderItem.getShippedQuantity()) {

			_restoreStockQuantity(
				commerceOrderItem, commerceShipmentItem,
				Math.abs(quantityDelta));
		}
		else {
			_updateStockQuantity(
				commerceOrderItem,
				commerceShipmentItem.getCommerceShipmentItemId(),
				quantityDelta);
		}

		// Commerce order item

		commerceOrderItemLocalService.incrementShippedQuantity(
			commerceShipmentItem.getCommerceOrderItemId(), quantityDelta);

		return commerceShipmentItem;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShipmentItem updateCommerceShipmentItemExternalReferenceCode(
			String externalReferenceCode, long commerceShipmentItemId)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		commerceShipmentItem.setExternalReferenceCode(externalReferenceCode);

		return commerceShipmentItemPersistence.update(commerceShipmentItem);
	}

	protected void validate(
			CommerceOrderItem commerceOrderItem,
			CommerceShipment commerceShipment,
			long commerceInventoryWarehouseId, int quantity, int newQuantity)
		throws PortalException {

		if ((commerceShipment != null) &&
			(commerceShipment.getStatus() !=
				CommerceShipmentConstants.SHIPMENT_STATUS_PROCESSING)) {

			throw new CommerceShipmentStatusException();
		}

		if (commerceInventoryWarehouseId <= 0) {
			return;
		}

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouse(commerceInventoryWarehouseId);

		if (!commerceInventoryWarehouse.isActive()) {
			throw new CommerceShipmentInactiveWarehouseException();
		}

		int availableQuantity =
			commerceOrderItem.getQuantity() -
				commerceOrderItem.getShippedQuantity();

		CommerceShipmentItem commerceShipmentItem = fetchCommerceShipmentItem(
			commerceShipment.getCommerceShipmentId(),
			commerceOrderItem.getCommerceOrderItemId(),
			commerceInventoryWarehouseId);

		if (commerceShipmentItem != null) {
			availableQuantity =
				availableQuantity + commerceShipmentItem.getQuantity();
		}

		int commerceInventoryWarehouseQuantity =
			commerceOrderItemLocalService.
				getCommerceInventoryWarehouseItemQuantity(
					commerceOrderItem.getCommerceOrderItemId(),
					commerceInventoryWarehouseId);

		if (((newQuantity > quantity) && (newQuantity > availableQuantity)) ||
			(newQuantity > commerceInventoryWarehouseQuantity)) {

			throw new CommerceShipmentItemQuantityException();
		}
	}

	private CommerceInventoryWarehouseItem _fetchCommerceInventoryWarehouseItem(
			long commerceShipmentItemId, String sku)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		return _commerceInventoryWarehouseItemLocalService.
			fetchCommerceInventoryWarehouseItem(
				commerceShipmentItem.getCommerceInventoryWarehouseId(), sku);
	}

	private void _restoreStockQuantity(
			CommerceOrderItem commerceOrderItem,
			CommerceShipmentItem commerceShipmentItem, int quantity)
		throws PortalException {

		_commerceInventoryEngine.increaseStockQuantity(
			commerceShipmentItem.getUserId(),
			commerceShipmentItem.getCommerceInventoryWarehouseId(),
			commerceOrderItem.getSku(), quantity);

		_commerceInventoryBookedQuantityLocalService.
			resetCommerceBookedQuantity(
				commerceOrderItem.getBookedQuantityId(),
				commerceOrderItem.getUserId(), commerceOrderItem.getSku(),
				quantity, null,
				HashMapBuilder.put(
					CommerceInventoryAuditTypeConstants.ORDER_ID,
					String.valueOf(commerceOrderItem.getCommerceOrderId())
				).put(
					CommerceInventoryAuditTypeConstants.ORDER_ITEM_ID,
					String.valueOf(commerceOrderItem.getCommerceOrderItemId())
				).put(
					CommerceInventoryAuditTypeConstants.SHIPMENT_ID,
					String.valueOf(commerceShipmentItem.getCommerceShipmentId())
				).build());
	}

	private void _updateStockQuantity(
			CommerceOrderItem commerceOrderItem, long commerceShipmentItemId,
			int quantity)
		throws PortalException {

		if (commerceOrderItem == null) {
			return;
		}

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_fetchCommerceInventoryWarehouseItem(
				commerceShipmentItemId, commerceOrderItem.getSku());

		if (commerceInventoryWarehouseItem == null) {
			return;
		}

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		_commerceInventoryEngine.consumeQuantity(
			commerceShipmentItem.getUserId(),
			commerceShipmentItem.getCommerceInventoryWarehouseId(),
			commerceOrderItem.getSku(), quantity,
			commerceOrderItem.getBookedQuantityId(),
			HashMapBuilder.put(
				CommerceInventoryAuditTypeConstants.ORDER_ID,
				String.valueOf(commerceOrderItem.getCommerceOrderId())
			).put(
				CommerceInventoryAuditTypeConstants.ORDER_ITEM_ID,
				String.valueOf(commerceOrderItem.getCommerceOrderItemId())
			).put(
				CommerceInventoryAuditTypeConstants.SHIPMENT_ID,
				String.valueOf(commerceShipmentItem.getCommerceShipmentId())
			).put(
				CommerceInventoryAuditTypeConstants.SHIPMENT_ITEM_ID,
				String.valueOf(commerceShipmentItemId)
			).build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShipmentItemLocalServiceImpl.class);

	@ServiceReference(type = CommerceInventoryBookedQuantityLocalService.class)
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@ServiceReference(type = CommerceInventoryEngine.class)
	private CommerceInventoryEngine _commerceInventoryEngine;

	@ServiceReference(type = CommerceInventoryWarehouseItemLocalService.class)
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@ServiceReference(type = CommerceInventoryWarehouseLocalService.class)
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

}