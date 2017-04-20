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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CommerceProductOption;

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
 * The cache model class for representing CommerceProductOption in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductOption
 * @generated
 */
@ProviderType
public class CommerceProductOptionCacheModel implements CacheModel<CommerceProductOption>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductOptionCacheModel)) {
			return false;
		}

		CommerceProductOptionCacheModel commerceProductOptionCacheModel = (CommerceProductOptionCacheModel)obj;

		if (commerceProductOptionId == commerceProductOptionCacheModel.commerceProductOptionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceProductOptionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceProductOptionId=");
		sb.append(commerceProductOptionId);
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
		sb.append(", description=");
		sb.append(description);
		sb.append(", DDMFormFieldTypeName=");
		sb.append(DDMFormFieldTypeName);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceProductOption toEntityModel() {
		CommerceProductOptionImpl commerceProductOptionImpl = new CommerceProductOptionImpl();

		if (uuid == null) {
			commerceProductOptionImpl.setUuid(StringPool.BLANK);
		}
		else {
			commerceProductOptionImpl.setUuid(uuid);
		}

		commerceProductOptionImpl.setCommerceProductOptionId(commerceProductOptionId);
		commerceProductOptionImpl.setGroupId(groupId);
		commerceProductOptionImpl.setCompanyId(companyId);
		commerceProductOptionImpl.setUserId(userId);

		if (userName == null) {
			commerceProductOptionImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceProductOptionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceProductOptionImpl.setCreateDate(null);
		}
		else {
			commerceProductOptionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceProductOptionImpl.setModifiedDate(null);
		}
		else {
			commerceProductOptionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceProductOptionImpl.setName(StringPool.BLANK);
		}
		else {
			commerceProductOptionImpl.setName(name);
		}

		if (description == null) {
			commerceProductOptionImpl.setDescription(StringPool.BLANK);
		}
		else {
			commerceProductOptionImpl.setDescription(description);
		}

		if (DDMFormFieldTypeName == null) {
			commerceProductOptionImpl.setDDMFormFieldTypeName(StringPool.BLANK);
		}
		else {
			commerceProductOptionImpl.setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}

		commerceProductOptionImpl.resetOriginalValues();

		return commerceProductOptionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceProductOptionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		DDMFormFieldTypeName = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commerceProductOptionId);

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

		if (DDMFormFieldTypeName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(DDMFormFieldTypeName);
		}
	}

	public String uuid;
	public long commerceProductOptionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String DDMFormFieldTypeName;
}