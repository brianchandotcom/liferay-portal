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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;

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
 * The cache model class for representing CPDefinitionOptionRel in entity cache.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRel
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelCacheModel implements CacheModel<CPDefinitionOptionRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionOptionRelCacheModel)) {
			return false;
		}

		CPDefinitionOptionRelCacheModel cpDefinitionOptionRelCacheModel = (CPDefinitionOptionRelCacheModel)obj;

		if (CPDefinitionOptionRelId == cpDefinitionOptionRelCacheModel.CPDefinitionOptionRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionOptionRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionOptionRelId=");
		sb.append(CPDefinitionOptionRelId);
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
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", CPOptionId=");
		sb.append(CPOptionId);
		sb.append(", DDMFormFieldTypeName=");
		sb.append(DDMFormFieldTypeName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", facetable=");
		sb.append(facetable);
		sb.append(", required=");
		sb.append(required);
		sb.append(", skuContributor=");
		sb.append(skuContributor);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionOptionRel toEntityModel() {
		CPDefinitionOptionRelImpl cpDefinitionOptionRelImpl = new CPDefinitionOptionRelImpl();

		if (uuid == null) {
			cpDefinitionOptionRelImpl.setUuid(StringPool.BLANK);
		}
		else {
			cpDefinitionOptionRelImpl.setUuid(uuid);
		}

		cpDefinitionOptionRelImpl.setCPDefinitionOptionRelId(CPDefinitionOptionRelId);
		cpDefinitionOptionRelImpl.setGroupId(groupId);
		cpDefinitionOptionRelImpl.setCompanyId(companyId);
		cpDefinitionOptionRelImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionOptionRelImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpDefinitionOptionRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionOptionRelImpl.setCreateDate(null);
		}
		else {
			cpDefinitionOptionRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionOptionRelImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionOptionRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			cpDefinitionOptionRelImpl.setTitle(StringPool.BLANK);
		}
		else {
			cpDefinitionOptionRelImpl.setTitle(title);
		}

		if (description == null) {
			cpDefinitionOptionRelImpl.setDescription(StringPool.BLANK);
		}
		else {
			cpDefinitionOptionRelImpl.setDescription(description);
		}

		cpDefinitionOptionRelImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionOptionRelImpl.setCPOptionId(CPOptionId);

		if (DDMFormFieldTypeName == null) {
			cpDefinitionOptionRelImpl.setDDMFormFieldTypeName(StringPool.BLANK);
		}
		else {
			cpDefinitionOptionRelImpl.setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}

		if (name == null) {
			cpDefinitionOptionRelImpl.setName(StringPool.BLANK);
		}
		else {
			cpDefinitionOptionRelImpl.setName(name);
		}

		cpDefinitionOptionRelImpl.setPriority(priority);
		cpDefinitionOptionRelImpl.setFacetable(facetable);
		cpDefinitionOptionRelImpl.setRequired(required);
		cpDefinitionOptionRelImpl.setSkuContributor(skuContributor);

		cpDefinitionOptionRelImpl.resetOriginalValues();

		return cpDefinitionOptionRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionOptionRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();
		description = objectInput.readUTF();

		CPDefinitionId = objectInput.readLong();

		CPOptionId = objectInput.readLong();
		DDMFormFieldTypeName = objectInput.readUTF();
		name = objectInput.readUTF();

		priority = objectInput.readInt();

		facetable = objectInput.readBoolean();

		required = objectInput.readBoolean();

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

		objectOutput.writeLong(CPDefinitionOptionRelId);

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

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(CPOptionId);

		if (DDMFormFieldTypeName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(DDMFormFieldTypeName);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(priority);

		objectOutput.writeBoolean(facetable);

		objectOutput.writeBoolean(required);

		objectOutput.writeBoolean(skuContributor);
	}

	public String uuid;
	public long CPDefinitionOptionRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String description;
	public long CPDefinitionId;
	public long CPOptionId;
	public String DDMFormFieldTypeName;
	public String name;
	public int priority;
	public boolean facetable;
	public boolean required;
	public boolean skuContributor;
}