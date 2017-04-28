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

import com.liferay.commerce.product.model.CPOption;

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
 * The cache model class for representing CPOption in entity cache.
 *
 * @author Marco Leo
 * @see CPOption
 * @generated
 */
@ProviderType
public class CPOptionCacheModel implements CacheModel<CPOption>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPOptionCacheModel)) {
			return false;
		}

		CPOptionCacheModel cpOptionCacheModel = (CPOptionCacheModel)obj;

		if (CPOptionId == cpOptionCacheModel.CPOptionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPOptionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPOptionId=");
		sb.append(CPOptionId);
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
		sb.append(", facetable=");
		sb.append(facetable);
		sb.append(", skuContributor=");
		sb.append(skuContributor);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPOption toEntityModel() {
		CPOptionImpl cpOptionImpl = new CPOptionImpl();

		if (uuid == null) {
			cpOptionImpl.setUuid(StringPool.BLANK);
		}
		else {
			cpOptionImpl.setUuid(uuid);
		}

		cpOptionImpl.setCPOptionId(CPOptionId);
		cpOptionImpl.setGroupId(groupId);
		cpOptionImpl.setCompanyId(companyId);
		cpOptionImpl.setUserId(userId);

		if (userName == null) {
			cpOptionImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpOptionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpOptionImpl.setCreateDate(null);
		}
		else {
			cpOptionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpOptionImpl.setModifiedDate(null);
		}
		else {
			cpOptionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			cpOptionImpl.setName(StringPool.BLANK);
		}
		else {
			cpOptionImpl.setName(name);
		}

		if (description == null) {
			cpOptionImpl.setDescription(StringPool.BLANK);
		}
		else {
			cpOptionImpl.setDescription(description);
		}

		if (DDMFormFieldTypeName == null) {
			cpOptionImpl.setDDMFormFieldTypeName(StringPool.BLANK);
		}
		else {
			cpOptionImpl.setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}

		cpOptionImpl.setFacetable(facetable);
		cpOptionImpl.setSkuContributor(skuContributor);

		cpOptionImpl.resetOriginalValues();

		return cpOptionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPOptionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		DDMFormFieldTypeName = objectInput.readUTF();

		facetable = objectInput.readBoolean();

		skuContributor = objectInput.readBoolean();
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

		objectOutput.writeLong(CPOptionId);

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

		objectOutput.writeBoolean(facetable);

		objectOutput.writeBoolean(skuContributor);
	}

	public String uuid;
	public long CPOptionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String DDMFormFieldTypeName;
	public boolean facetable;
	public boolean skuContributor;
}