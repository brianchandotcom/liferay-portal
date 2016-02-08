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

import com.liferay.shopping.model.ShoppingCategory;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ShoppingCategory in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCategory
 * @generated
 */
@ProviderType
public class ShoppingCategoryCacheModel implements CacheModel<ShoppingCategory>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingCategoryCacheModel)) {
			return false;
		}

		ShoppingCategoryCacheModel shoppingCategoryCacheModel = (ShoppingCategoryCacheModel)obj;

		if (categoryId == shoppingCategoryCacheModel.categoryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, categoryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{categoryId=");
		sb.append(categoryId);
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
		sb.append(", parentCategoryId=");
		sb.append(parentCategoryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ShoppingCategory toEntityModel() {
		ShoppingCategoryImpl shoppingCategoryImpl = new ShoppingCategoryImpl();

		shoppingCategoryImpl.setCategoryId(categoryId);
		shoppingCategoryImpl.setGroupId(groupId);
		shoppingCategoryImpl.setCompanyId(companyId);
		shoppingCategoryImpl.setUserId(userId);

		if (userName == null) {
			shoppingCategoryImpl.setUserName(StringPool.BLANK);
		}
		else {
			shoppingCategoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			shoppingCategoryImpl.setCreateDate(null);
		}
		else {
			shoppingCategoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			shoppingCategoryImpl.setModifiedDate(null);
		}
		else {
			shoppingCategoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		shoppingCategoryImpl.setParentCategoryId(parentCategoryId);

		if (name == null) {
			shoppingCategoryImpl.setName(StringPool.BLANK);
		}
		else {
			shoppingCategoryImpl.setName(name);
		}

		if (description == null) {
			shoppingCategoryImpl.setDescription(StringPool.BLANK);
		}
		else {
			shoppingCategoryImpl.setDescription(description);
		}

		shoppingCategoryImpl.resetOriginalValues();

		return shoppingCategoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		categoryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentCategoryId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(categoryId);

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

		objectOutput.writeLong(parentCategoryId);

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
	}

	public long categoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentCategoryId;
	public String name;
	public String description;
}