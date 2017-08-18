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

import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.base.CommerceOrderLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderLocalServiceImpl
	extends CommerceOrderLocalServiceBaseImpl {

	@Override
	public CommerceOrder addCommerceOrder(
			long orderUserId, int status, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceOrderId = counterLocalService.increment();

		CommerceOrder commerceOrder = commerceOrderPersistence.create(
			commerceOrderId);

		commerceOrder.setGroupId(groupId);
		commerceOrder.setCompanyId(user.getCompanyId());
		commerceOrder.setUserId(user.getUserId());
		commerceOrder.setUserName(user.getFullName());
		commerceOrder.setOrderUserId(orderUserId);
		commerceOrder.setStatus(status);
		commerceOrder.setExpandoBridgeAttributes(serviceContext);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Override
	public CommerceOrder addCommerceOrderFromCart(
			long commerceCartId, ServiceContext serviceContext)
		throws PortalException {

		// Commerce order

		CommerceCart commerceCart = commerceCartLocalService.getCommerceCart(
			commerceCartId);

		CommerceOrder commerceOrder =
			commerceOrderLocalService.addCommerceOrder(
				commerceCart.getUserId(), 0, serviceContext);

		// Commerce order items

		List<CommerceCartItem> commerceCartItems =
			commerceCartItemLocalService.getCommerceCartItems(
				commerceCart.getCommerceCartId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			commerceOrderItemLocalService.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				commerceCartItem.getCPDefinitionId(),
				commerceCartItem.getCPInstanceId(),
				commerceCartItem.getQuantity(), commerceCartItem.getJson(),
				serviceContext);
		}

		// Commerce cart

		commerceCartLocalService.deleteCommerceCart(commerceCart);

		return commerceOrder;
	}

	@Override
	public CommerceOrder deleteCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException {

		// Commerce order

		commerceOrderPersistence.remove(commerceOrder);

		// Commerce order items

		commerceOrderItemLocalService.deleteCommerceOrderItems(
			commerceOrder.getCommerceOrderId());

		// Expando

		expandoRowLocalService.deleteRows(commerceOrder.getCommerceOrderId());

		return commerceOrder;
	}

	@Override
	public CommerceOrder deleteCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		return commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
	}

}