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
		StringBundler sb = new StringBundler(33);

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
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", DDMFormFieldTypeName=");
		sb.append(DDMFormFieldTypeName);
		sb.append(", facetable=");
		sb.append(facetable);
		sb.append(", required=");
		sb.append(required);
		sb.append(", skuContributor=");
		sb.append(skuContributor);
		sb.append(", key=");
		sb.append(key);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPOption toEntityModel() {
		CPOptionImpl cpOptionImpl = new CPOptionImpl();

		if (uuid == null) {
			cpOptionImpl.setUuid("");
		}
		else {
			cpOptionImpl.setUuid(uuid);
		}

		cpOptionImpl.setCPOptionId(CPOptionId);
		cpOptionImpl.setGroupId(groupId);
		cpOptionImpl.setCompanyId(companyId);
		cpOptionImpl.setUserId(userId);

		if (userName == null) {
			cpOptionImpl.setUserName("");
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

		if (title == null) {
			cpOptionImpl.setTitle("");
		}
		else {
			cpOptionImpl.setTitle(title);
		}

		if (description == null) {
			cpOptionImpl.setDescription("");
		}
		else {
			cpOptionImpl.setDescription(description);
		}

		if (DDMFormFieldTypeName == null) {
			cpOptionImpl.setDDMFormFieldTypeName("");
		}
		else {
			cpOptionImpl.setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}

		cpOptionImpl.setFacetable(facetable);
		cpOptionImpl.setRequired(required);
		cpOptionImpl.setSkuContributor(skuContributor);

		if (key == null) {
			cpOptionImpl.setKey("");
		}
		else {
			cpOptionImpl.setKey(key);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			cpOptionImpl.setLastPublishDate(null);
		}
		else {
			cpOptionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

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
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		DDMFormFieldTypeName = objectInput.readUTF();

		facetable = objectInput.readBoolean();

		required = objectInput.readBoolean();

		skuContributor = objectInput.readBoolean();
		key = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(CPOptionId);

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

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (DDMFormFieldTypeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(DDMFormFieldTypeName);
		}

		objectOutput.writeBoolean(facetable);

		objectOutput.writeBoolean(required);

		objectOutput.writeBoolean(skuContributor);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long CPOptionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String description;
	public String DDMFormFieldTypeName;
	public boolean facetable;
	public boolean required;
	public boolean skuContributor;
	public String key;
	public long lastPublishDate;
}