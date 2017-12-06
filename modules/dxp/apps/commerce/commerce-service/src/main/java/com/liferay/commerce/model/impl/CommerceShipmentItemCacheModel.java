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

import com.liferay.commerce.model.CommerceShipmentItem;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceShipmentItem in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItem
 * @generated
 */
@ProviderType
public class CommerceShipmentItemCacheModel implements CacheModel<CommerceShipmentItem>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShipmentItemCacheModel)) {
			return false;
		}

		CommerceShipmentItemCacheModel commerceShipmentItemCacheModel = (CommerceShipmentItemCacheModel)obj;

		if (commerceShipmentItemId == commerceShipmentItemCacheModel.commerceShipmentItemId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceShipmentItemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceShipmentItemId=");
		sb.append(commerceShipmentItemId);
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
		sb.append(", commerceShipmentId=");
		sb.append(commerceShipmentId);
		sb.append(", commerceOrderItemId=");
		sb.append(commerceOrderItemId);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShipmentItem toEntityModel() {
		CommerceShipmentItemImpl commerceShipmentItemImpl = new CommerceShipmentItemImpl();

		commerceShipmentItemImpl.setCommerceShipmentItemId(commerceShipmentItemId);
		commerceShipmentItemImpl.setGroupId(groupId);
		commerceShipmentItemImpl.setCompanyId(companyId);
		commerceShipmentItemImpl.setUserId(userId);

		if (userName == null) {
			commerceShipmentItemImpl.setUserName("");
		}
		else {
			commerceShipmentItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShipmentItemImpl.setCreateDate(null);
		}
		else {
			commerceShipmentItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShipmentItemImpl.setModifiedDate(null);
		}
		else {
			commerceShipmentItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceShipmentItemImpl.setCommerceShipmentId(commerceShipmentId);
		commerceShipmentItemImpl.setCommerceOrderItemId(commerceOrderItemId);
		commerceShipmentItemImpl.setQuantity(quantity);

		commerceShipmentItemImpl.resetOriginalValues();

		return commerceShipmentItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceShipmentItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceShipmentId = objectInput.readLong();

		commerceOrderItemId = objectInput.readLong();

		quantity = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceShipmentItemId);

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

		objectOutput.writeLong(commerceShipmentId);

		objectOutput.writeLong(commerceOrderItemId);

		objectOutput.writeInt(quantity);
	}

	public long commerceShipmentItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceShipmentId;
	public long commerceOrderItemId;
	public int quantity;
}