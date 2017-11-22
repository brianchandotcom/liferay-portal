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

import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.service.base.CommerceCartItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommerceCartItemServiceImpl
	extends CommerceCartItemServiceBaseImpl {

	@Override
	public CommerceCartItem addCommerceCartItem(
			long commerceCartId, long cpDefinitionId, long cpInstanceId,
			int quantity, String json, ServiceContext serviceContext)
		throws PortalException {

		return commerceCartItemLocalService.addCommerceCartItem(
			commerceCartId, cpDefinitionId, cpInstanceId, quantity, json,
			serviceContext);
	}

	@Override
	public void deleteCommerceCartItem(long commerceCartItemId)
		throws PortalException {

		commerceCartItemLocalService.deleteCommerceCartItem(commerceCartItemId);
	}

	@Override
	public CommerceCartItem fetchCommerceCartItem(long commerceCartItemId)
		throws PortalException {

		return commerceCartItemLocalService.fetchCommerceCartItem(
			commerceCartItemId);
	}

	@Override
	public CommerceCartItem getCommerceCartItem(long commerceCartItemId)
		throws PortalException {

		return commerceCartItemLocalService.getCommerceCartItem(
			commerceCartItemId);
	}

	@Override
	public List<CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end) {

		return commerceCartItemLocalService.getCommerceCartItems(
			commerceCartId, start, end);
	}

	@Override
	public List<CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {

		return commerceCartItemLocalService.getCommerceCartItems(
			commerceCartId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCartItemsCount(long commerceCartId) {
		return commerceCartItemLocalService.getCommerceCartItemsCount(
			commerceCartId);
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId) throws PortalException {
		return commerceCartItemLocalService.getCPInstanceQuantity(cpInstanceId);
	}

	@Override
	public CommerceCartItem updateCommerceCartItem(
			long commerceCartItemId, int quantity, String json)
		throws PortalException {

		return commerceCartItemLocalService.updateCommerceCartItem(
			commerceCartItemId, quantity, json);
	}

}