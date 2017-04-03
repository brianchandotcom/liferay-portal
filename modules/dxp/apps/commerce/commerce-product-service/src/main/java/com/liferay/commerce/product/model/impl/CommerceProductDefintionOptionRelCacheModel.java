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

import com.liferay.commerce.product.model.CommerceProductDefintionOptionRel;

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
 * The cache model class for representing CommerceProductDefintionOptionRel in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionRel
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionRelCacheModel implements CacheModel<CommerceProductDefintionOptionRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefintionOptionRelCacheModel)) {
			return false;
		}

		CommerceProductDefintionOptionRelCacheModel commerceProductDefintionOptionRelCacheModel =
			(CommerceProductDefintionOptionRelCacheModel)obj;

		if (commerceProductDefintionOptionRelId == commerceProductDefintionOptionRelCacheModel.commerceProductDefintionOptionRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceProductDefintionOptionRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{commerceProductDefintionOptionRelId=");
		sb.append(commerceProductDefintionOptionRelId);
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
	public CommerceProductDefintionOptionRel toEntityModel() {
		CommerceProductDefintionOptionRelImpl commerceProductDefintionOptionRelImpl =
			new CommerceProductDefintionOptionRelImpl();

		commerceProductDefintionOptionRelImpl.setCommerceProductDefintionOptionRelId(commerceProductDefintionOptionRelId);
		commerceProductDefintionOptionRelImpl.setGroupId(groupId);
		commerceProductDefintionOptionRelImpl.setCompanyId(companyId);
		commerceProductDefintionOptionRelImpl.setUserId(userId);

		if (userName == null) {
			commerceProductDefintionOptionRelImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceProductDefintionOptionRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceProductDefintionOptionRelImpl.setCreateDate(null);
		}
		else {
			commerceProductDefintionOptionRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceProductDefintionOptionRelImpl.setModifiedDate(null);
		}
		else {
			commerceProductDefintionOptionRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceProductDefintionOptionRelImpl.setCommerceProductOptionId(commerceProductOptionId);
		commerceProductDefintionOptionRelImpl.setCommerceProductDefinitionId(commerceProductDefinitionId);

		if (name == null) {
			commerceProductDefintionOptionRelImpl.setName(StringPool.BLANK);
		}
		else {
			commerceProductDefintionOptionRelImpl.setName(name);
		}

		if (description == null) {
			commerceProductDefintionOptionRelImpl.setDescription(StringPool.BLANK);
		}
		else {
			commerceProductDefintionOptionRelImpl.setDescription(description);
		}

		if (DDMFormFieldTypeName == null) {
			commerceProductDefintionOptionRelImpl.setDDMFormFieldTypeName(StringPool.BLANK);
		}
		else {
			commerceProductDefintionOptionRelImpl.setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}

		if (priority == null) {
			commerceProductDefintionOptionRelImpl.setPriority(StringPool.BLANK);
		}
		else {
			commerceProductDefintionOptionRelImpl.setPriority(priority);
		}

		commerceProductDefintionOptionRelImpl.resetOriginalValues();

		return commerceProductDefintionOptionRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceProductDefintionOptionRelId = objectInput.readLong();

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
		objectOutput.writeLong(commerceProductDefintionOptionRelId);

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

	public long commerceProductDefintionOptionRelId;
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