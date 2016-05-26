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

package com.liferay.shopping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.shopping.model.ShoppingOrderItem;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ShoppingOrderItem in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderItem
 * @generated
 */
@ProviderType
public class ShoppingOrderItemCacheModel implements CacheModel<ShoppingOrderItem>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingOrderItemCacheModel)) {
			return false;
		}

		ShoppingOrderItemCacheModel shoppingOrderItemCacheModel = (ShoppingOrderItemCacheModel)obj;

		if (orderItemId == shoppingOrderItemCacheModel.orderItemId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, orderItemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{orderItemId=");
		sb.append(orderItemId);
		sb.append(", companyId=");
		sb.append(companyId);
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

	@Override
	public ShoppingOrderItem toEntityModel() {
		ShoppingOrderItemImpl shoppingOrderItemImpl = new ShoppingOrderItemImpl();

		shoppingOrderItemImpl.setOrderItemId(orderItemId);
		shoppingOrderItemImpl.setCompanyId(companyId);
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

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		orderItemId = objectInput.readLong();

		companyId = objectInput.readLong();

		orderId = objectInput.readLong();
		itemId = objectInput.readUTF();
		sku = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		properties = objectInput.readUTF();

		price = objectInput.readDouble();

		quantity = objectInput.readInt();
		shippedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(orderItemId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(orderId);

		if (itemId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(itemId);
		}

		if (sku == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(sku);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (properties == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(properties);
		}

		objectOutput.writeDouble(price);

		objectOutput.writeInt(quantity);
		objectOutput.writeLong(shippedDate);
	}

	public long orderItemId;
	public long companyId;
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