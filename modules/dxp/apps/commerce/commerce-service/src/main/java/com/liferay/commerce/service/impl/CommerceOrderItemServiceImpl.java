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

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.base.CommerceOrderItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderItemServiceImpl
	extends CommerceOrderItemServiceBaseImpl {

	@Override
	public CommerceOrderItem addCommerceOrderItem(
			long commerceOrderId, long cpInstanceId, int quantity,
			int shippedQuantity, String json, Double price,
			ServiceContext serviceContext)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		return commerceOrderItemLocalService.addCommerceOrderItem(
			commerceOrderId, cpInstanceId, quantity, shippedQuantity, json,
			price, serviceContext);
	}

	@Override
	public void deleteCommerceOrderItem(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItemId);

		checkCommerceOrder(commerceOrderItem.getCommerceOrderId());

		commerceOrderItemLocalService.deleteCommerceOrderItem(
			commerceOrderItem);
	}

	@Override
	public CommerceOrderItem fetchCommerceOrderItem(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.fetchCommerceOrderItem(
				commerceOrderItemId);

		if (commerceOrderItem != null) {
			checkCommerceOrder(commerceOrderItem.getCommerceOrderId());
		}

		return commerceOrderItem;
	}

	@Override
	public CommerceOrderItem getCommerceOrderItem(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItemId);

		checkCommerceOrder(commerceOrderItem.getCommerceOrderId());

		return commerceOrderItem;
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
			long commerceOrderId, int start, int end)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		return commerceOrderItemLocalService.getCommerceOrderItems(
			commerceOrderId, start, end);
	}

	@Override
	public int getCommerceOrderItemsCount(long commerceOrderId)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		return commerceOrderItemLocalService.getCommerceOrderItemsCount(
			commerceOrderId);
	}

	@Override
	public BaseModelSearchResult<CommerceOrderItem> search(
			long commerceOrderId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		return commerceOrderItemLocalService.search(
			commerceOrderId, keywords, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<CommerceOrderItem> search(
			long commerceOrderId, String sku, String title, boolean andOperator,
			int start, int end, Sort sort)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		return commerceOrderItemLocalService.search(
			commerceOrderId, sku, title, andOperator, start, end, sort);
	}

	@Override
	public CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, int quantity, String json, double price)
		throws PortalException {

		CommerceOrderItem commerceOrderItem = getCommerceOrderItem(
			commerceOrderItemId);

		return commerceOrderItemLocalService.updateCommerceOrderItem(
			commerceOrderItem.getCommerceOrderItemId(), quantity, json, price);
	}

	protected void checkCommerceOrder(long commerceOrderId)
		throws PortalException {

		commerceOrderService.getCommerceOrder(commerceOrderId);
	}

}