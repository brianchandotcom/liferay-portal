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

import com.liferay.portlet.shopping.model.ShoppingOrderItem;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing ShoppingOrderItem in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderItem
 * @generated
 */
public class ShoppingOrderItemCacheModel implements CacheModel<ShoppingOrderItem>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{orderItemId=");
		sb.append(orderItemId);
		sb.append(", orderId=");
		sb.append(orderId);
		sb.append(", itemId=");
		sb.append(itemId);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", properties=");
		sb.append(properties);
		sb.append(", price=");
		sb.append(price);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", shippedDate=");
		sb.append(shippedDate);
		sb.append("}");

		return sb.toString();
	}

	public ShoppingOrderItem toEntityModel() {
		ShoppingOrderItemImpl shoppingOrderItemImpl = new ShoppingOrderItemImpl();

		shoppingOrderItemImpl.setOrderItemId(orderItemId);
		shoppingOrderItemImpl.setOrderId(orderId);

		if (itemId == null) {
			shoppingOrderItemImpl.setItemId(StringPool.BLANK);
		}
		else {
			shoppingOrderItemImpl.setItemId(itemId);
		}

		if (sku == null) {
			shoppingOrderItemImpl.setSku(StringPool.BLANK);
		}
		else {
			shoppingOrderItemImpl.setSku(sku);
		}

		if (name == null) {
			shoppingOrderItemImpl.setName(StringPool.BLANK);
		}
		else {
			shoppingOrderItemImpl.setName(name);
		}

		if (description == null) {
			shoppingOrderItemImpl.setDescription(StringPool.BLANK);
		}
		else {
			shoppingOrderItemImpl.setDescription(description);
		}

		if (properties == null) {
			shoppingOrderItemImpl.setProperties(StringPool.BLANK);
		}
		else {
			shoppingOrderItemImpl.setProperties(properties);
		}

		shoppingOrderItemImpl.setPrice(price);
		shoppingOrderItemImpl.setQuantity(quantity);

		if (shippedDate == Long.MIN_VALUE) {
			shoppingOrderItemImpl.setShippedDate(null);
		}
		else {
			shoppingOrderItemImpl.setShippedDate(new Date(shippedDate));
		}

		shoppingOrderItemImpl.resetOriginalValues();

		return shoppingOrderItemImpl;
	}

	public long orderItemId;
	public long orderId;
	public String itemId;
	public String sku;
	public String name;
	public String description;
	public String properties;
	public double price;
	public int quantity;
	public long shippedDate;
}