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

package com.liferay.commerce.cart.service.impl;

import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.service.base.CommerceCartLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommerceCartLocalServiceImpl
	extends CommerceCartLocalServiceBaseImpl {

	@Override
	public CommerceCart addCommerceCart(
			String name, int type, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceCartId = counterLocalService.increment();

		CommerceCart commerceCart = commerceCartPersistence.create(
			commerceCartId);

		commerceCart.setGroupId(groupId);
		commerceCart.setCompanyId(user.getCompanyId());
		commerceCart.setUserId(user.getUserId());
		commerceCart.setUserName(user.getFullName());
		commerceCart.setName(name);
		commerceCart.setType(type);
		commerceCart.setExpandoBridgeAttributes(serviceContext);

		commerceCartPersistence.update(commerceCart);

		return commerceCart;
	}

	@Override
	public CommerceCart deleteCommerceCart(CommerceCart commerceCart)
		throws PortalException {

		// Commerce cart

		commerceCartPersistence.remove(commerceCart);

		// Commerce cart items

		commerceCartItemLocalService.deleteCommerceCartItems(
			commerceCart.getCommerceCartId());

		// Expando

		expandoRowLocalService.deleteRows(commerceCart.getCommerceCartId());

		return commerceCart;
	}

	@Override
	public CommerceCart deleteCommerceCart(long commerceCartId)
		throws PortalException {

		CommerceCart commerceCart = commerceCartPersistence.findByPrimaryKey(
			commerceCartId);

		return commerceCartLocalService.deleteCommerceCart(commerceCart);
	}

	public CommerceCart fetchCommerceCart(long commerceCartId) {
		return commerceCartPersistence.fetchByPrimaryKey(commerceCartId);
	}

	@Override
	public List<CommerceCart> getCommerceCarts(
		long groupId, int type, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {

		return commerceCartPersistence.findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCartsCount(long groupId, int type) {
		return commerceCartPersistence.countByG_T(groupId, type);
	}

}