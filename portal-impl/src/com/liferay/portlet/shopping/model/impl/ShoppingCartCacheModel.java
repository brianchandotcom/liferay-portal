/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.shopping.model.ShoppingCart;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing ShoppingCart in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCart
 * @generated
 */
public class ShoppingCartCacheModel implements CacheModel<ShoppingCart>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{cartId=");
		sb.append(cartId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", itemIds=");
		sb.append(itemIds);
		sb.append(", couponCodes=");
		sb.append(couponCodes);
		sb.append(", altShipping=");
		sb.append(altShipping);
		sb.append(", insure=");
		sb.append(insure);
		sb.append("}");

		return sb.toString();
	}

	public ShoppingCart toEntityModel() {
		ShoppingCartImpl shoppingCartImpl = new ShoppingCartImpl();

		shoppingCartImpl.setCartId(cartId);
		shoppingCartImpl.setGroupId(groupId);
		shoppingCartImpl.setCompanyId(companyId);
		shoppingCartImpl.setUserId(userId);

		if (userName == null) {
			shoppingCartImpl.setUserName(StringPool.BLANK);
		}
		else {
			shoppingCartImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			shoppingCartImpl.setCreateDate(null);
		}
		else {
			shoppingCartImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			shoppingCartImpl.setModifiedDate(null);
		}
		else {
			shoppingCartImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (itemIds == null) {
			shoppingCartImpl.setItemIds(StringPool.BLANK);
		}
		else {
			shoppingCartImpl.setItemIds(itemIds);
		}

		if (couponCodes == null) {
			shoppingCartImpl.setCouponCodes(StringPool.BLANK);
		}
		else {
			shoppingCartImpl.setCouponCodes(couponCodes);
		}

		shoppingCartImpl.setAltShipping(altShipping);
		shoppingCartImpl.setInsure(insure);

		shoppingCartImpl.resetOriginalValues();

		return shoppingCartImpl;
	}

	public long cartId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String itemIds;
	public String couponCodes;
	public int altShipping;
	public boolean insure;
}