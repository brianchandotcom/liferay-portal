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

import com.liferay.commerce.product.model.CPTaxCategory;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPTaxCategory in entity cache.
 *
 * @author Marco Leo
 * @see CPTaxCategory
 * @generated
 */
@ProviderType
public class CPTaxCategoryCacheModel implements CacheModel<CPTaxCategory>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPTaxCategoryCacheModel)) {
			return false;
		}

		CPTaxCategoryCacheModel cpTaxCategoryCacheModel = (CPTaxCategoryCacheModel)obj;

		if (CPTaxCategoryId == cpTaxCategoryCacheModel.CPTaxCategoryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPTaxCategoryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);
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
	public CPTaxCategory toEntityModel() {
		CPTaxCategoryImpl cpTaxCategoryImpl = new CPTaxCategoryImpl();

		cpTaxCategoryImpl.setCPTaxCategoryId(CPTaxCategoryId);
		cpTaxCategoryImpl.setGroupId(groupId);
		cpTaxCategoryImpl.setCompanyId(companyId);
		cpTaxCategoryImpl.setUserId(userId);

		if (userName == null) {
			cpTaxCategoryImpl.setUserName("");
		}
		else {
			cpTaxCategoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpTaxCategoryImpl.setCreateDate(null);
		}
		else {
			cpTaxCategoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpTaxCategoryImpl.setModifiedDate(null);
		}
		else {
			cpTaxCategoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			cpTaxCategoryImpl.setName("");
		}
		else {
			cpTaxCategoryImpl.setName(name);
		}

		if (description == null) {
			cpTaxCategoryImpl.setDescription("");
		}
		else {
			cpTaxCategoryImpl.setDescription(description);
		}

		cpTaxCategoryImpl.resetOriginalValues();

		return cpTaxCategoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPTaxCategoryId = objectInput.readLong();

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
		objectOutput.writeLong(CPTaxCategoryId);

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

	public long CPTaxCategoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
}