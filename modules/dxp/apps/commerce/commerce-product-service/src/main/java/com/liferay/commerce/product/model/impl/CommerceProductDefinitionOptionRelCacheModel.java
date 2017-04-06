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

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;

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
 * The cache model class for representing CommerceProductDefinitionOptionRel in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRel
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelCacheModel implements CacheModel<CommerceProductDefinitionOptionRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefinitionOptionRelCacheModel)) {
			return false;
		}

		CommerceProductDefinitionOptionRelCacheModel commerceProductDefinitionOptionRelCacheModel =
			(CommerceProductDefinitionOptionRelCacheModel)obj;

		if (commerceProductDefinitionOptionRelId == commerceProductDefinitionOptionRelCacheModel.commerceProductDefinitionOptionRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceProductDefinitionOptionRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{commerceProductDefinitionOptionRelId=");
		sb.append(commerceProductDefinitionOptionRelId);
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
		sb.append(", commerceProductOptionId=");
		sb.append(commerceProductOptionId);
		sb.append(", commerceProductDefinitionId=");
		sb.append(commerceProductDefinitionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", DDMFormFieldTypeName=");
		sb.append(DDMFormFieldTypeName);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceProductDefinitionOptionRel toEntityModel() {
		CommerceProductDefinitionOptionRelImpl commerceProductDefinitionOptionRelImpl =
			new CommerceProductDefinitionOptionRelImpl();

		commerceProductDefinitionOptionRelImpl.setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionRelId);
		commerceProductDefinitionOptionRelImpl.setGroupId(groupId);
		commerceProductDefinitionOptionRelImpl.setCompanyId(companyId);
		commerceProductDefinitionOptionRelImpl.setUserId(userId);

		if (userName == null) {
			commerceProductDefinitionOptionRelImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionOptionRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceProductDefinitionOptionRelImpl.setCreateDate(null);
		}
		else {
			commerceProductDefinitionOptionRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceProductDefinitionOptionRelImpl.setModifiedDate(null);
		}
		else {
			commerceProductDefinitionOptionRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceProductDefinitionOptionRelImpl.setCommerceProductOptionId(commerceProductOptionId);
		commerceProductDefinitionOptionRelImpl.setCommerceProductDefinitionId(commerceProductDefinitionId);

		if (name == null) {
			commerceProductDefinitionOptionRelImpl.setName(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionOptionRelImpl.setName(name);
		}

		if (description == null) {
			commerceProductDefinitionOptionRelImpl.setDescription(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionOptionRelImpl.setDescription(description);
		}

		if (DDMFormFieldTypeName == null) {
			commerceProductDefinitionOptionRelImpl.setDDMFormFieldTypeName(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionOptionRelImpl.setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}

		if (priority == null) {
			commerceProductDefinitionOptionRelImpl.setPriority(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionOptionRelImpl.setPriority(priority);
		}

		commerceProductDefinitionOptionRelImpl.resetOriginalValues();

		return commerceProductDefinitionOptionRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceProductDefinitionOptionRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceProductOptionId = objectInput.readLong();

		commerceProductDefinitionId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		DDMFormFieldTypeName = objectInput.readUTF();
		priority = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceProductDefinitionOptionRelId);

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

		objectOutput.writeLong(commerceProductOptionId);

		objectOutput.writeLong(commerceProductDefinitionId);

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

		if (priority == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(priority);
		}
	}

	public long commerceProductDefinitionOptionRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceProductOptionId;
	public long commerceProductDefinitionId;
	public String name;
	public String description;
	public String DDMFormFieldTypeName;
	public String priority;
}