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

package com.liferay.commerce.cart.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.model.CommerceCartItem;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceCartItem in entity cache.
 *
 * @author Marco Leo
 * @see CommerceCartItem
 * @generated
 */
@ProviderType
public class CommerceCartItemCacheModel implements CacheModel<CommerceCartItem>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCartItemCacheModel)) {
			return false;
		}

		CommerceCartItemCacheModel commerceCartItemCacheModel = (CommerceCartItemCacheModel)obj;

		if (CommerceCartItemId == commerceCartItemCacheModel.CommerceCartItemId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CommerceCartItemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{CommerceCartItemId=");
		sb.append(CommerceCartItemId);
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
		sb.append(", CommerceCartId=");
		sb.append(CommerceCartId);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", json=");
		sb.append(json);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceCartItem toEntityModel() {
		CommerceCartItemImpl commerceCartItemImpl = new CommerceCartItemImpl();

		commerceCartItemImpl.setCommerceCartItemId(CommerceCartItemId);
		commerceCartItemImpl.setGroupId(groupId);
		commerceCartItemImpl.setCompanyId(companyId);
		commerceCartItemImpl.setUserId(userId);

		if (userName == null) {
			commerceCartItemImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceCartItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceCartItemImpl.setCreateDate(null);
		}
		else {
			commerceCartItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceCartItemImpl.setModifiedDate(null);
		}
		else {
			commerceCartItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceCartItemImpl.setCommerceCartId(CommerceCartId);
		commerceCartItemImpl.setCPDefinitionId(CPDefinitionId);
		commerceCartItemImpl.setCPInstanceId(CPInstanceId);
		commerceCartItemImpl.setQuantity(quantity);

		if (json == null) {
			commerceCartItemImpl.setJson(StringPool.BLANK);
		}
		else {
			commerceCartItemImpl.setJson(json);
		}

		commerceCartItemImpl.resetOriginalValues();

		return commerceCartItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CommerceCartItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CommerceCartId = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		CPInstanceId = objectInput.readLong();

		quantity = objectInput.readInt();
		json = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(CommerceCartItemId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(CommerceCartId);

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(CPInstanceId);

		objectOutput.writeInt(quantity);

		if (json == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(json);
		}
	}

	public long CommerceCartItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CommerceCartId;
	public long CPDefinitionId;
	public long CPInstanceId;
	public int quantity;
	public String json;
}