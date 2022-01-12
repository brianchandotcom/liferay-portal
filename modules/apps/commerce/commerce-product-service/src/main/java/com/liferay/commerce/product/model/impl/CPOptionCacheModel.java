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

import com.liferay.commerce.product.model.CPOption;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPOption in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPOptionCacheModel
	implements CacheModel<CPOption>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPOptionCacheModel)) {
			return false;
		}

		CPOptionCacheModel cpOptionCacheModel = (CPOptionCacheModel)object;

		if ((CPOptionId == cpOptionCacheModel.CPOptionId) &&
			(mvccVersion == cpOptionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPOptionId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", CPOptionId=");
		sb.append(CPOptionId);
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

		cpOptionImpl.setMvccVersion(mvccVersion);
		cpOptionImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpOptionImpl.setUuid("");
		}
		else {
			cpOptionImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			cpOptionImpl.setExternalReferenceCode("");
		}
		else {
			cpOptionImpl.setExternalReferenceCode(externalReferenceCode);
		}

		cpOptionImpl.setCPOptionId(CPOptionId);
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

		if (name == null) {
			cpOptionImpl.setName("");
		}
		else {
			cpOptionImpl.setName(name);
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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		CPOptionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		DDMFormFieldTypeName = objectInput.readUTF();

		facetable = objectInput.readBoolean();

		required = objectInput.readBoolean();

		skuContributor = objectInput.readBoolean();
		key = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(CPOptionId);

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

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long CPOptionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String DDMFormFieldTypeName;
	public boolean facetable;
	public boolean required;
	public boolean skuContributor;
	public String key;
	public long lastPublishDate;

}