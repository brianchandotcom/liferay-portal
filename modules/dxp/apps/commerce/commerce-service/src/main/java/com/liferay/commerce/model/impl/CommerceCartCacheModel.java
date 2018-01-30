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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceCart;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceCart in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCart
 * @generated
 */
@ProviderType
public class CommerceCartCacheModel implements CacheModel<CommerceCart>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCartCacheModel)) {
			return false;
		}

		CommerceCartCacheModel commerceCartCacheModel = (CommerceCartCacheModel)obj;

		if (commerceCartId == commerceCartCacheModel.commerceCartId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceCartId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceCartId=");
		sb.append(commerceCartId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", defaultCart=");
		sb.append(defaultCart);
		sb.append(", type=");
		sb.append(type);
		sb.append(", billingAddressId=");
		sb.append(billingAddressId);
		sb.append(", shippingAddressId=");
		sb.append(shippingAddressId);
		sb.append(", commercePaymentMethodId=");
		sb.append(commercePaymentMethodId);
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", shippingOptionName=");
		sb.append(shippingOptionName);
		sb.append(", shippingPrice=");
		sb.append(shippingPrice);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceCart toEntityModel() {
		CommerceCartImpl commerceCartImpl = new CommerceCartImpl();

		if (uuid == null) {
			commerceCartImpl.setUuid("");
		}
		else {
			commerceCartImpl.setUuid(uuid);
		}

		commerceCartImpl.setCommerceCartId(commerceCartId);
		commerceCartImpl.setGroupId(groupId);
		commerceCartImpl.setCompanyId(companyId);
		commerceCartImpl.setUserId(userId);

		if (userName == null) {
			commerceCartImpl.setUserName("");
		}
		else {
			commerceCartImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceCartImpl.setCreateDate(null);
		}
		else {
			commerceCartImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceCartImpl.setModifiedDate(null);
		}
		else {
			commerceCartImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceCartImpl.setName("");
		}
		else {
			commerceCartImpl.setName(name);
		}

		commerceCartImpl.setDefaultCart(defaultCart);
		commerceCartImpl.setType(type);
		commerceCartImpl.setBillingAddressId(billingAddressId);
		commerceCartImpl.setShippingAddressId(shippingAddressId);
		commerceCartImpl.setCommercePaymentMethodId(commercePaymentMethodId);
		commerceCartImpl.setCommerceShippingMethodId(commerceShippingMethodId);

		if (shippingOptionName == null) {
			commerceCartImpl.setShippingOptionName("");
		}
		else {
			commerceCartImpl.setShippingOptionName(shippingOptionName);
		}

		commerceCartImpl.setShippingPrice(shippingPrice);

		commerceCartImpl.resetOriginalValues();

		return commerceCartImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceCartId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		defaultCart = objectInput.readBoolean();

		type = objectInput.readInt();

		billingAddressId = objectInput.readLong();

		shippingAddressId = objectInput.readLong();

		commercePaymentMethodId = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();
		shippingOptionName = objectInput.readUTF();

		shippingPrice = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commerceCartId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(defaultCart);

		objectOutput.writeInt(type);

		objectOutput.writeLong(billingAddressId);

		objectOutput.writeLong(shippingAddressId);

		objectOutput.writeLong(commercePaymentMethodId);

		objectOutput.writeLong(commerceShippingMethodId);

		if (shippingOptionName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(shippingOptionName);
		}

		objectOutput.writeDouble(shippingPrice);
	}

	public String uuid;
	public long commerceCartId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public boolean defaultCart;
	public int type;
	public long billingAddressId;
	public long shippingAddressId;
	public long commercePaymentMethodId;
	public long commerceShippingMethodId;
	public String shippingOptionName;
	public double shippingPrice;
}