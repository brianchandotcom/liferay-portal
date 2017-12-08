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

import com.liferay.commerce.model.CommerceOrder;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceOrder in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrder
 * @generated
 */
@ProviderType
public class CommerceOrderCacheModel implements CacheModel<CommerceOrder>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceOrderCacheModel)) {
			return false;
		}

		CommerceOrderCacheModel commerceOrderCacheModel = (CommerceOrderCacheModel)obj;

		if (commerceOrderId == commerceOrderCacheModel.commerceOrderId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceOrderId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{commerceOrderId=");
		sb.append(commerceOrderId);
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
		sb.append(", orderUserId=");
		sb.append(orderUserId);
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
		sb.append(", subtotal=");
		sb.append(subtotal);
		sb.append(", shippingPrice=");
		sb.append(shippingPrice);
		sb.append(", total=");
		sb.append(total);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceOrder toEntityModel() {
		CommerceOrderImpl commerceOrderImpl = new CommerceOrderImpl();

		commerceOrderImpl.setCommerceOrderId(commerceOrderId);
		commerceOrderImpl.setGroupId(groupId);
		commerceOrderImpl.setCompanyId(companyId);
		commerceOrderImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderImpl.setUserName("");
		}
		else {
			commerceOrderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderImpl.setCreateDate(null);
		}
		else {
			commerceOrderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderImpl.setModifiedDate(null);
		}
		else {
			commerceOrderImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceOrderImpl.setOrderUserId(orderUserId);
		commerceOrderImpl.setBillingAddressId(billingAddressId);
		commerceOrderImpl.setShippingAddressId(shippingAddressId);
		commerceOrderImpl.setCommercePaymentMethodId(commercePaymentMethodId);
		commerceOrderImpl.setCommerceShippingMethodId(commerceShippingMethodId);

		if (shippingOptionName == null) {
			commerceOrderImpl.setShippingOptionName("");
		}
		else {
			commerceOrderImpl.setShippingOptionName(shippingOptionName);
		}

		commerceOrderImpl.setSubtotal(subtotal);
		commerceOrderImpl.setShippingPrice(shippingPrice);
		commerceOrderImpl.setTotal(total);
		commerceOrderImpl.setStatus(status);

		commerceOrderImpl.resetOriginalValues();

		return commerceOrderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceOrderId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		orderUserId = objectInput.readLong();

		billingAddressId = objectInput.readLong();

		shippingAddressId = objectInput.readLong();

		commercePaymentMethodId = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();
		shippingOptionName = objectInput.readUTF();

		subtotal = objectInput.readDouble();

		shippingPrice = objectInput.readDouble();

		total = objectInput.readDouble();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceOrderId);

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

		objectOutput.writeLong(orderUserId);

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

		objectOutput.writeDouble(subtotal);

		objectOutput.writeDouble(shippingPrice);

		objectOutput.writeDouble(total);

		objectOutput.writeInt(status);
	}

	public long commerceOrderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long orderUserId;
	public long billingAddressId;
	public long shippingAddressId;
	public long commercePaymentMethodId;
	public long commerceShippingMethodId;
	public String shippingOptionName;
	public double subtotal;
	public double shippingPrice;
	public double total;
	public int status;
}