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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.service.base.ShoppingItemServiceBaseImpl;
import com.liferay.portlet.shopping.service.permission.ShoppingCategoryPermission;
import com.liferay.portlet.shopping.service.permission.ShoppingItemPermission;

import java.io.File;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemServiceImpl extends ShoppingItemServiceBaseImpl {

	@Override
	public void addBookItems(long groupId, long categoryId, String[] isbns)
		throws PortalException {

		ShoppingCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.ADD_ITEM);

		shoppingItemLocalService.addBookItems(
			getUserId(), groupId, categoryId, isbns);
	}

	@Override
	public ShoppingItem addItem(
			long groupId, long categoryId, String sku, String name,
			String description, String properties, String fieldsQuantities,
			boolean requiresShipping, int stockQuantity, boolean featured,
			Boolean sale, boolean smallImage, String smallImageURL,
			File smallFile, boolean mediumImage, String mediumImageURL,
			File mediumFile, boolean largeImage, String largeImageURL,
			File largeFile, List<ShoppingItemField> itemFields,
			List<ShoppingItemPrice> itemPrices, ServiceContext serviceContext)
		throws PortalException {

		ShoppingCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.ADD_ITEM);

		return shoppingItemLocalService.addItem(
			getUserId(), groupId, categoryId, sku, name, description,
			properties, fieldsQuantities, requiresShipping, stockQuantity,
			featured, sale, smallImage, smallImageURL, smallFile, mediumImage,
			mediumImageURL, mediumFile, largeImage, largeImageURL, largeFile,
			itemFields, itemPrices, serviceContext);
	}

	@Override
	public void deleteItem(long itemId) throws PortalException {
		ShoppingItemPermission.check(
			getPermissionChecker(), itemId, ActionKeys.DELETE);

		shoppingItemLocalService.deleteItem(itemId);
	}

	@Override
	public int getCategoriesItemsCount(long groupId, List<Long> categoryIds) {
		return shoppingItemFinder.filterCountByG_C(groupId, categoryIds);
	}

	@Override
	public ShoppingItem getItem(long itemId) throws PortalException {
		ShoppingItemPermission.check(
			getPermissionChecker(), itemId, ActionKeys.VIEW);

		return shoppingItemLocalService.getItem(itemId);
	}

	@Override
	public List<ShoppingItem> getItems(long groupId, long categoryId) {
		return shoppingItemPersistence.filterFindByG_C(groupId, categoryId);
	}

	@Override
	public List<ShoppingItem> getItems(
		long groupId, long categoryId, int start, int end,
		OrderByComparator obc) {

		return shoppingItemPersistence.filterFindByG_C(
			groupId, categoryId, start, end, obc);
	}

	@Override
	public int getItemsCount(long groupId, long categoryId) {
		return shoppingItemPersistence.filterCountByG_C(groupId, categoryId);
	}

	@Override
	public ShoppingItem[] getItemsPrevAndNext(
			long itemId, OrderByComparator obc)
		throws PortalException {

		ShoppingItem item = shoppingItemPersistence.findByPrimaryKey(itemId);

		return shoppingItemPersistence.filterFindByG_C_PrevAndNext(
			item.getItemId(), item.getGroupId(), item.getCategoryId(), obc);
	}

	@Override
	public ShoppingItem updateItem(
			long itemId, long groupId, long categoryId, String sku, String name,
			String description, String properties, String fieldsQuantities,
			boolean requiresShipping, int stockQuantity, boolean featured,
			Boolean sale, boolean smallImage, String smallImageURL,
			File smallFile, boolean mediumImage, String mediumImageURL,
			File mediumFile, boolean largeImage, String largeImageURL,
			File largeFile, List<ShoppingItemField> itemFields,
			List<ShoppingItemPrice> itemPrices, ServiceContext serviceContext)
		throws PortalException {

		ShoppingItemPermission.check(
			getPermissionChecker(), itemId, ActionKeys.UPDATE);

		return shoppingItemLocalService.updateItem(
			getUserId(), itemId, groupId, categoryId, sku, name, description,
			properties, fieldsQuantities, requiresShipping, stockQuantity,
			featured, sale, smallImage, smallImageURL, smallFile, mediumImage,
			mediumImageURL, mediumFile, largeImage, largeImageURL, largeFile,
			itemFields, itemPrices, serviceContext);
	}

}