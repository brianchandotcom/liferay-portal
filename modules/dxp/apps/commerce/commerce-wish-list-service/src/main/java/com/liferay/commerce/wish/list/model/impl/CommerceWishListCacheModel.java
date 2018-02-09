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

package com.liferay.commerce.wish.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.wish.list.model.CommerceWishList;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceWishList in entity cache.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishList
 * @generated
 */
@ProviderType
public class CommerceWishListCacheModel implements CacheModel<CommerceWishList>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceWishListCacheModel)) {
			return false;
		}

		CommerceWishListCacheModel commerceWishListCacheModel = (CommerceWishListCacheModel)obj;

		if (commerceWishListId == commerceWishListCacheModel.commerceWishListId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceWishListId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceWishListId=");
		sb.append(commerceWishListId);
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
		sb.append(", defaultWishList=");
		sb.append(defaultWishList);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceWishList toEntityModel() {
		CommerceWishListImpl commerceWishListImpl = new CommerceWishListImpl();

		if (uuid == null) {
			commerceWishListImpl.setUuid("");
		}
		else {
			commerceWishListImpl.setUuid(uuid);
		}

		commerceWishListImpl.setCommerceWishListId(commerceWishListId);
		commerceWishListImpl.setGroupId(groupId);
		commerceWishListImpl.setCompanyId(companyId);
		commerceWishListImpl.setUserId(userId);

		if (userName == null) {
			commerceWishListImpl.setUserName("");
		}
		else {
			commerceWishListImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceWishListImpl.setCreateDate(null);
		}
		else {
			commerceWishListImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceWishListImpl.setModifiedDate(null);
		}
		else {
			commerceWishListImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceWishListImpl.setName("");
		}
		else {
			commerceWishListImpl.setName(name);
		}

		commerceWishListImpl.setDefaultWishList(defaultWishList);

		commerceWishListImpl.resetOriginalValues();

		return commerceWishListImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceWishListId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		defaultWishList = objectInput.readBoolean();
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

		objectOutput.writeLong(commerceWishListId);

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

		objectOutput.writeBoolean(defaultWishList);
	}

	public String uuid;
	public long commerceWishListId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public boolean defaultWishList;
}