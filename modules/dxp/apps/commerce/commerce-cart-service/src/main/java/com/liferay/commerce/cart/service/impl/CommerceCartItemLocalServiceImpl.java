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

import com.liferay.commerce.cart.model.CommerceCartItem;
import com.liferay.commerce.cart.service.base.CommerceCartItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartItemLocalServiceImpl
	extends CommerceCartItemLocalServiceBaseImpl {

	@Override
	public CommerceCartItem addCommerceCartItem(
			long commerceCartId, long cpDefinitionId, long cpInstanceId,
			int quantity, String json, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceCartItemId = counterLocalService.increment();

		CommerceCartItem commerceCartItem = commerceCartItemPersistence.create(
			commerceCartItemId);

		commerceCartItem.setGroupId(groupId);
		commerceCartItem.setCompanyId(user.getCompanyId());
		commerceCartItem.setUserId(user.getUserId());
		commerceCartItem.setUserName(user.getFullName());
		commerceCartItem.setCommerceCartId(commerceCartId);
		commerceCartItem.setCPDefinitionId(cpDefinitionId);
		commerceCartItem.setCPInstanceId(cpInstanceId);
		commerceCartItem.setQuantity(quantity);
		commerceCartItem.setJson(json);

		commerceCartItemPersistence.update(commerceCartItem);

		return commerceCartItem;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceCartItem deleteCommerceCartItem(
			CommerceCartItem commerceCartItem)
		throws PortalException {

		commerceCartItemPersistence.remove(commerceCartItem);

		return commerceCartItem;
	}

	@Override
	public CommerceCartItem deleteCommerceCartItem(long commerceCartItemId)
		throws PortalException {

		CommerceCartItem commerceCartItem =
			commerceCartItemPersistence.findByPrimaryKey(commerceCartItemId);

		return commerceCartItemLocalService.deleteCommerceCartItem(
			commerceCartItem);
	}

	@Override
	public void deleteCommerceCartItems(long commerceCartId)
		throws PortalException {

		List<CommerceCartItem> commerceCartItems = getCommerceCartItems(
			commerceCartId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			deleteCommerceCartItem(commerceCartItem);
		}
	}

	@Override
	public List<CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {

		return commerceCartItemPersistence.findByCommerceCartId(
			commerceCartId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCartItemsCount(long commerceCartId) {
		return commerceCartItemPersistence.countByCommerceCartId(
			commerceCartId);
	}

	@Override
	public CommerceCartItem updateCommerceCartItem(
			long commerceCartItemId, int quantity, String json)
		throws PortalException {

		CommerceCartItem commerceCartItem =
			commerceCartItemPersistence.findByPrimaryKey(commerceCartItemId);

		commerceCartItem.setQuantity(quantity);
		commerceCartItem.setJson(json);

		commerceCartItemPersistence.update(commerceCartItem);

		return commerceCartItem;
	}

}