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

package com.liferay.commerce.wish.list.service.impl;

import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.base.CommerceWishListItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWishListItemServiceImpl
	extends CommerceWishListItemServiceBaseImpl {

	@Override
	public CommerceWishListItem addCommerceWishListItem(
			long commerceWishListId, long cpDefinitionId, long cpInstanceId,
			String json, ServiceContext serviceContext)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.UPDATE);

		return commerceWishListItemLocalService.addCommerceWishListItem(
			commerceWishListId, cpDefinitionId, cpInstanceId, json,
			serviceContext);
	}

	@Override
	public void deleteCommerceWishListItem(long commerceWishListItemId)
		throws PortalException {

		CommerceWishListItem commerceWishListItem =
			commerceWishListItemLocalService.getCommerceWishListItem(
				commerceWishListItemId);

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(),
			commerceWishListItem.getCommerceWishListId(), ActionKeys.UPDATE);

		commerceWishListItemLocalService.deleteCommerceWishListItem(
			commerceWishListItem);
	}

	@Override
	public CommerceWishListItem getCommerceWishListItem(
			long commerceWishListItemId)
		throws PortalException {

		CommerceWishListItem commerceWishListItem =
			commerceWishListItemLocalService.getCommerceWishListItem(
				commerceWishListItemId);

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(),
			commerceWishListItem.getCommerceWishListId(), ActionKeys.VIEW);

		return commerceWishListItem;
	}

	@Override
	public List<CommerceWishListItem> getCommerceWishListItems(
			long commerceWishListId, int start, int end,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.VIEW);

		return commerceWishListItemLocalService.getCommerceWishListItems(
			commerceWishListId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListItemsCount(long commerceWishListId)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.VIEW);

		return commerceWishListItemLocalService.getCommerceWishListItemsCount(
			commerceWishListId);
	}

	private static volatile ModelResourcePermission<CommerceWishList>
		_commerceWishListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceWishListItemServiceImpl.class,
				"_commerceWishListModelResourcePermission",
				CommerceWishList.class);

}