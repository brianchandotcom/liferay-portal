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

import com.liferay.shopping.model.ShoppingItemPrice;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ShoppingItemPrice in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemPrice
 * @generated
 */
@ProviderType
public class ShoppingItemPriceCacheModel implements CacheModel<ShoppingItemPrice>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingItemPriceCacheModel)) {
			return false;
		}

		ShoppingItemPriceCacheModel shoppingItemPriceCacheModel = (ShoppingItemPriceCacheModel)obj;

		if (itemPriceId == shoppingItemPriceCacheModel.itemPriceId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, itemPriceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{itemPriceId=");
		sb.append(itemPriceId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", itemId=");
		sb.append(itemId);
		sb.append(", minQuantity=");
		sb.append(minQuantity);
		sb.append(", maxQuantity=");
		sb.append(maxQuantity);
		sb.append(", price=");
		sb.append(price);
		sb.append(", discount=");
		sb.append(discount);
		sb.append(", taxable=");
		sb.append(taxable);
		sb.append(", shipping=");
		sb.append(shipping);
		sb.append(", useShippingFormula=");
		sb.append(useShippingFormula);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ShoppingItemPrice toEntityModel() {
		ShoppingItemPriceImpl shoppingItemPriceImpl = new ShoppingItemPriceImpl();

		shoppingItemPriceImpl.setItemPriceId(itemPriceId);
		shoppingItemPriceImpl.setCompanyId(companyId);
		shoppingItemPriceImpl.setItemId(itemId);
		shoppingItemPriceImpl.setMinQuantity(minQuantity);
		shoppingItemPriceImpl.setMaxQuantity(maxQuantity);
		shoppingItemPriceImpl.setPrice(price);
		shoppingItemPriceImpl.setDiscount(discount);
		shoppingItemPriceImpl.setTaxable(taxable);
		shoppingItemPriceImpl.setShipping(shipping);
		shoppingItemPriceImpl.setUseShippingFormula(useShippingFormula);
		shoppingItemPriceImpl.setStatus(status);

		shoppingItemPriceImpl.resetOriginalValues();

		return shoppingItemPriceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		itemPriceId = objectInput.readLong();

		companyId = objectInput.readLong();

		itemId = objectInput.readLong();

		minQuantity = objectInput.readInt();

		maxQuantity = objectInput.readInt();

		price = objectInput.readDouble();

		discount = objectInput.readDouble();

		taxable = objectInput.readBoolean();

		shipping = objectInput.readDouble();

		useShippingFormula = objectInput.readBoolean();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(itemPriceId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(itemId);

		objectOutput.writeInt(minQuantity);

		objectOutput.writeInt(maxQuantity);

		objectOutput.writeDouble(price);

		objectOutput.writeDouble(discount);

		objectOutput.writeBoolean(taxable);

		objectOutput.writeDouble(shipping);

		objectOutput.writeBoolean(useShippingFormula);

		objectOutput.writeInt(status);
	}

	public long itemPriceId;
	public long companyId;
	public long itemId;
	public int minQuantity;
	public int maxQuantity;
	public double price;
	public double discount;
	public boolean taxable;
	public double shipping;
	public boolean useShippingFormula;
	public int status;
}