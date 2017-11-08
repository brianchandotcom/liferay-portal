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
import com.liferay.commerce.model.CommerceOrderItem;
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
import com.liferay.portal.kernel.util.OrderByComparator;

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

		commerceOrderItemLocalService.updateCommerceOrderItemShippedQuantity(
			commerceOrderItemId, quantity);

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

		try {
			int shippedQuantity = commerceShipmentItem.getQuantity() * -1;

			commerceOrderItemLocalService.
				updateCommerceOrderItemShippedQuantity(
					commerceShipmentItem.getCommerceOrderItemId(),
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
	public void deleteCommerceShipmentItems(
		long groupId, long commerceShipment) {

		List<CommerceShipmentItem> commerceShipmentItems =
			commerceShipmentItemPersistence.findByG_C(
				groupId, commerceShipment);

		for (CommerceShipmentItem commerceShipmentItem :
				commerceShipmentItems) {

			commerceShipmentItemLocalService.deleteCommerceShipmentItem(
				commerceShipmentItem);
		}
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long groupId, long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return commerceShipmentItemPersistence.findByG_C(
			groupId, commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentItemsCount(
		long groupId, long commerceShipmentId) {

		return commerceShipmentItemPersistence.countByG_C(
			groupId, commerceShipmentId);
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

		validate(commerceOrderItem, quantity, newQuantity);

		commerceOrderItemLocalService.updateCommerceOrderItemShippedQuantity(
			commerceOrderItem.getCommerceOrderItemId(), newQuantity);

		return commerceShipmentItem;
	}

	protected void validate(
			CommerceOrderItem commerceOrderItem, int quantity, int newQuantity)
		throws PortalException {

		int availableQuantity =
			commerceOrderItem.getQuantity() -
				commerceOrderItem.getShippedQuantity();

		if ((quantity == 0) || (newQuantity > availableQuantity)) {
			throw new CommerceShipmentItemQuantityException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShipmentItemLocalServiceImpl.class);

}