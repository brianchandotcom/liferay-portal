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

package com.liferay.shopping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.model.ShoppingItemPrice;
import com.liferay.shopping.service.base.ShoppingItemServiceBaseImpl;
import com.liferay.shopping.service.permission.ShoppingCategoryPermission;
import com.liferay.shopping.service.permission.ShoppingItemPermission;

import java.io.File;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemServiceImpl extends ShoppingItemServiceBaseImpl {

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

		getShoppingCategoryPermission().check(
			getPermissionChecker(), groupId, categoryId, ActionKeys.ADD_ITEM);

		return shoppingItemLocalService.addItem(
			getUserId(), groupId, categoryId, sku, name, description,
			properties, fieldsQuantities, requiresShipping, stockQuantity,
			featured, sale, smallImage, smallImageURL, smallFile, mediumImage,
			mediumImageURL, mediumFile, largeImage, largeImageURL, largeFile,
			itemFields, itemPrices, serviceContext);
	}

	@Override
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		_shoppingCategoryPermissionTracker = new ServiceTracker<>(
			bundleContext, ShoppingCategoryPermission.class, null);

		_shoppingCategoryPermissionTracker.open();

		_shoppingItemPermissionTracker = new ServiceTracker<>(
			bundleContext, ShoppingItemPermission.class, null);

		_shoppingItemPermissionTracker.open();
	}

	@Override
	public void deleteItem(long itemId) throws PortalException {
		getShoppingItemPermission().check(
			getPermissionChecker(), itemId, ActionKeys.DELETE);

		shoppingItemLocalService.deleteItem(itemId);
	}

	@Override
	public void destroy() {
		_shoppingCategoryPermissionTracker.close();
		_shoppingItemPermissionTracker.close();
	}

	@Override
	public int getCategoriesItemsCount(long groupId, List<Long> categoryIds) {
		return shoppingItemFinder.filterCountByG_C(groupId, categoryIds);
	}

	@Override
	public ShoppingItem getItem(long itemId) throws PortalException {
		getShoppingItemPermission().check(
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
		OrderByComparator<ShoppingItem> obc) {

		return shoppingItemPersistence.filterFindByG_C(
			groupId, categoryId, start, end, obc);
	}

	@Override
	public int getItemsCount(long groupId, long categoryId) {
		return shoppingItemPersistence.filterCountByG_C(groupId, categoryId);
	}

	@Override
	public ShoppingItem[] getItemsPrevAndNext(
			long itemId, OrderByComparator<ShoppingItem> obc)
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

		getShoppingItemPermission().check(
			getPermissionChecker(), itemId, ActionKeys.UPDATE);

		return shoppingItemLocalService.updateItem(
			getUserId(), itemId, groupId, categoryId, sku, name, description,
			properties, fieldsQuantities, requiresShipping, stockQuantity,
			featured, sale, smallImage, smallImageURL, smallFile, mediumImage,
			mediumImageURL, mediumFile, largeImage, largeImageURL, largeFile,
			itemFields, itemPrices, serviceContext);
	}

	private ShoppingCategoryPermission getShoppingCategoryPermission() {
		return _shoppingCategoryPermissionTracker.getService();
	}

	private ShoppingItemPermission getShoppingItemPermission() {
		return _shoppingItemPermissionTracker.getService();
	}

	private ServiceTracker
		<ShoppingCategoryPermission, ShoppingCategoryPermission>
			_shoppingCategoryPermissionTracker;
	private ServiceTracker
		<ShoppingItemPermission, ShoppingItemPermission>
			_shoppingItemPermissionTracker;

}