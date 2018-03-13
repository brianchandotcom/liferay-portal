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

import com.liferay.commerce.model.CommerceTaxCategory;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceTaxCategory in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategory
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryCacheModel implements CacheModel<CommerceTaxCategory>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxCategoryCacheModel)) {
			return false;
		}

		CommerceTaxCategoryCacheModel commerceTaxCategoryCacheModel = (CommerceTaxCategoryCacheModel)obj;

		if (commerceTaxCategoryId == commerceTaxCategoryCacheModel.commerceTaxCategoryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceTaxCategoryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{commerceTaxCategoryId=");
		sb.append(commerceTaxCategoryId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTaxCategory toEntityModel() {
		CommerceTaxCategoryImpl commerceTaxCategoryImpl = new CommerceTaxCategoryImpl();

		commerceTaxCategoryImpl.setCommerceTaxCategoryId(commerceTaxCategoryId);
		commerceTaxCategoryImpl.setGroupId(groupId);
		commerceTaxCategoryImpl.setCompanyId(companyId);
		commerceTaxCategoryImpl.setUserId(userId);

		if (userName == null) {
			commerceTaxCategoryImpl.setUserName("");
		}
		else {
			commerceTaxCategoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTaxCategoryImpl.setCreateDate(null);
		}
		else {
			commerceTaxCategoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTaxCategoryImpl.setModifiedDate(null);
		}
		else {
			commerceTaxCategoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceTaxCategoryImpl.setName("");
		}
		else {
			commerceTaxCategoryImpl.setName(name);
		}

		if (description == null) {
			commerceTaxCategoryImpl.setDescription("");
		}
		else {
			commerceTaxCategoryImpl.setDescription(description);
		}

		commerceTaxCategoryImpl.resetOriginalValues();

		return commerceTaxCategoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceTaxCategoryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceTaxCategoryId);

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

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}
	}

	public long commerceTaxCategoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
}