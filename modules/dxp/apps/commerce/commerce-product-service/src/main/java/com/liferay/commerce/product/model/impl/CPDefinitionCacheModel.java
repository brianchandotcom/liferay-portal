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

import com.liferay.commerce.product.model.CPDefinition;

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
 * The cache model class for representing CPDefinition in entity cache.
 *
 * @author Marco Leo
 * @see CPDefinition
 * @generated
 */
@ProviderType
public class CPDefinitionCacheModel implements CacheModel<CPDefinition>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionCacheModel)) {
			return false;
		}

		CPDefinitionCacheModel cpDefinitionCacheModel = (CPDefinitionCacheModel)obj;

		if (CPDefinitionId == cpDefinitionCacheModel.CPDefinitionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(41);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
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
		sb.append(", productTypeName=");
		sb.append(productTypeName);
		sb.append(", availableIndividually=");
		sb.append(availableIndividually);
		sb.append(", DDMStructureKey=");
		sb.append(DDMStructureKey);
		sb.append(", baseSKU=");
		sb.append(baseSKU);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", defaultLanguageId=");
		sb.append(defaultLanguageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinition toEntityModel() {
		CPDefinitionImpl cpDefinitionImpl = new CPDefinitionImpl();

		if (uuid == null) {
			cpDefinitionImpl.setUuid(StringPool.BLANK);
		}
		else {
			cpDefinitionImpl.setUuid(uuid);
		}

		cpDefinitionImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionImpl.setGroupId(groupId);
		cpDefinitionImpl.setCompanyId(companyId);
		cpDefinitionImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpDefinitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setCreateDate(null);
		}
		else {
			cpDefinitionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (productTypeName == null) {
			cpDefinitionImpl.setProductTypeName(StringPool.BLANK);
		}
		else {
			cpDefinitionImpl.setProductTypeName(productTypeName);
		}

		cpDefinitionImpl.setAvailableIndividually(availableIndividually);

		if (DDMStructureKey == null) {
			cpDefinitionImpl.setDDMStructureKey(StringPool.BLANK);
		}
		else {
			cpDefinitionImpl.setDDMStructureKey(DDMStructureKey);
		}

		if (baseSKU == null) {
			cpDefinitionImpl.setBaseSKU(StringPool.BLANK);
		}
		else {
			cpDefinitionImpl.setBaseSKU(baseSKU);
		}

		if (displayDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setDisplayDate(null);
		}
		else {
			cpDefinitionImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setExpirationDate(null);
		}
		else {
			cpDefinitionImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setLastPublishDate(null);
		}
		else {
			cpDefinitionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		cpDefinitionImpl.setStatus(status);
		cpDefinitionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			cpDefinitionImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			cpDefinitionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			cpDefinitionImpl.setStatusDate(null);
		}
		else {
			cpDefinitionImpl.setStatusDate(new Date(statusDate));
		}

		if (defaultLanguageId == null) {
			cpDefinitionImpl.setDefaultLanguageId(StringPool.BLANK);
		}
		else {
			cpDefinitionImpl.setDefaultLanguageId(defaultLanguageId);
		}

		cpDefinitionImpl.resetOriginalValues();

		return cpDefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		productTypeName = objectInput.readUTF();

		availableIndividually = objectInput.readBoolean();
		DDMStructureKey = objectInput.readUTF();
		baseSKU = objectInput.readUTF();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		defaultLanguageId = objectInput.readUTF();
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

		objectOutput.writeLong(CPDefinitionId);

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

		if (productTypeName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(productTypeName);
		}

		objectOutput.writeBoolean(availableIndividually);

		if (DDMStructureKey == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(DDMStructureKey);
		}

		if (baseSKU == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(baseSKU);
		}

		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);

		if (defaultLanguageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
		}
	}

	public String uuid;
	public long CPDefinitionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String productTypeName;
	public boolean availableIndividually;
	public String DDMStructureKey;
	public String baseSKU;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public String defaultLanguageId;
}