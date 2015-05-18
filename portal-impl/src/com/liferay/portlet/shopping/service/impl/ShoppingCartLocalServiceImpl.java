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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.shopping.CartMinQuantityException;
import com.liferay.portlet.shopping.CouponActiveException;
import com.liferay.portlet.shopping.CouponEndDateException;
import com.liferay.portlet.shopping.CouponStartDateException;
import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.ShoppingCartItem;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.impl.ShoppingCartItemImpl;
import com.liferay.portlet.shopping.service.base.ShoppingCartLocalServiceBaseImpl;
import com.liferay.portlet.shopping.util.ShoppingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCartLocalServiceImpl
	extends ShoppingCartLocalServiceBaseImpl {

	@Override
	public void deleteGroupCarts(long groupId) {
		List<ShoppingCart> carts = shoppingCartPersistence.findByGroupId(
			groupId);

		for (ShoppingCart cart : carts) {
			deleteShoppingCart(cart);
		}
	}

	@Override
	public void deleteUserCarts(long userId) {
		List<ShoppingCart> shoppingCarts = shoppingCartPersistence.findByUserId(
			userId);

		for (ShoppingCart shoppingCart : shoppingCarts) {
			deleteShoppingCart(shoppingCart);
		}
	}

	@Override
	public ShoppingCart getCart(long userId, long groupId)
		throws PortalException {

		return shoppingCartPersistence.findByG_U(groupId, userId);
	}

	@Override
	public Map<ShoppingCartItem, Integer> getItems(
		long groupId, String itemIds) {

		Map<ShoppingCartItem, Integer> items = new TreeMap<>();

		String[] itemIdsArray = StringUtil.split(itemIds);

		for (int i = 0; i < itemIdsArray.length; i++) {
			long itemId = ShoppingUtil.getItemId(itemIdsArray[i]);
			String fields = ShoppingUtil.getItemFields(itemIdsArray[i]);

			ShoppingItem item = shoppingItemPersistence.fetchByPrimaryKey(
				itemId);

			if (item != null) {
				ShoppingCategory category = item.getCategory();

				if (category.getGroupId() == groupId) {
					ShoppingCartItem cartItem = new ShoppingCartItemImpl(
						item, fields);

					Integer count = items.get(cartItem);

					if (count == null) {
						count = new Integer(1);
					}
					else {
						count = new Integer(count.intValue() + 1);
					}

					items.put(cartItem, count);
				}
			}
		}

		return items;
	}

	@Override
	public ShoppingCart updateCart(
			long userId, long groupId, String itemIds, String couponCodes,
			int altShipping, boolean insure)
		throws PortalException {

		List<Long> badItemIds = new ArrayList<>();

		Map<ShoppingCartItem, Integer> items = getItems(groupId, itemIds);

		boolean minQtyMultiple = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SHOPPING_CART_MIN_QTY_MULTIPLE));

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();
			Integer count = entry.getValue();

			ShoppingItem item = cartItem.getItem();

			int minQuantity = ShoppingUtil.getMinQuantity(item);

			if (minQuantity <= 0) {
				continue;
			}

			if (minQtyMultiple) {
				if ((count.intValue() % minQuantity) > 0) {
					badItemIds.add(item.getItemId());
				}
			}
			else {
				if (count.intValue() < minQuantity) {
					badItemIds.add(item.getItemId());
				}
			}
		}

		if (!badItemIds.isEmpty()) {
			throw new CartMinQuantityException(
				StringUtil.merge(
					badItemIds.toArray(new Long[badItemIds.size()])));
		}

		String[] couponCodesArray = StringUtil.split(couponCodes);

		for (int i = 0; i < couponCodesArray.length; i++) {
			try {
				ShoppingCoupon coupon = shoppingCouponPersistence.findByCode(
					couponCodesArray[i]);

				if (coupon.getGroupId() != groupId) {
					throw new NoSuchCouponException(couponCodesArray[i]);
				}
				else if (!coupon.isActive()) {
					throw new CouponActiveException(couponCodesArray[i]);
				}
				else if (!coupon.hasValidStartDate()) {
					throw new CouponStartDateException(couponCodesArray[i]);
				}
				else if (!coupon.hasValidEndDate()) {
					throw new CouponEndDateException(couponCodesArray[i]);
				}
			}
			catch (NoSuchCouponException nsce) {
				throw new NoSuchCouponException(couponCodesArray[i]);
			}

			// Temporarily disable stacking of coupon codes

			break;
		}

		User user = userPersistence.findByPrimaryKey(userId);

		ShoppingCart cart = null;

		if (user.isDefaultUser()) {
			cart = shoppingCartPersistence.create(0);

			cart.setGroupId(groupId);
			cart.setCompanyId(user.getCompanyId());
			cart.setUserId(userId);
			cart.setUserName(user.getFullName());
		}
		else {
			cart = shoppingCartPersistence.fetchByG_U(groupId, userId);

			if (cart == null) {
				long cartId = counterLocalService.increment();

				cart = shoppingCartPersistence.create(cartId);

				cart.setGroupId(groupId);
				cart.setCompanyId(user.getCompanyId());
				cart.setUserId(userId);
				cart.setUserName(user.getFullName());
			}
		}

		cart.setItemIds(checkItemIds(groupId, itemIds));
		cart.setCouponCodes(couponCodes);
		cart.setAltShipping(altShipping);
		cart.setInsure(insure);

		if (!user.isDefaultUser()) {
			shoppingCartPersistence.update(cart);
		}

		return cart;
	}

	protected String checkItemIds(long groupId, String itemIds) {
		String[] itemIdsArray = StringUtil.split(itemIds);

		for (int i = 0; i < itemIdsArray.length; i++) {
			long itemId = ShoppingUtil.getItemId(itemIdsArray[i]);

			ShoppingItem item = null;

			try {
				item = shoppingItemPersistence.findByPrimaryKey(itemId);

				ShoppingCategory category = item.getCategory();

				if (category.getGroupId() != groupId) {
					item = null;
				}
			}
			catch (Exception e) {
			}

			if (item == null) {
				itemIds = StringUtil.removeFromList(itemIds, itemIdsArray[i]);
			}
		}

		return itemIds;
	}

}