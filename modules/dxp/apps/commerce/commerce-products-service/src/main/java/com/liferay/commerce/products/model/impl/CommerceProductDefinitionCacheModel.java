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

package com.liferay.commerce.products.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.products.model.CommerceProductDefinition;

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
 * The cache model class for representing CommerceProductDefinition in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductDefinition
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionCacheModel implements CacheModel<CommerceProductDefinition>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefinitionCacheModel)) {
			return false;
		}

		CommerceProductDefinitionCacheModel commerceProductDefinitionCacheModel = (CommerceProductDefinitionCacheModel)obj;

		if (commerceProductDefinitionId == commerceProductDefinitionCacheModel.commerceProductDefinitionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceProductDefinitionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(45);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceProductDefinitionId=");
		sb.append(commerceProductDefinitionId);
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
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", description=");
		sb.append(description);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceProductDefinition toEntityModel() {
		CommerceProductDefinitionImpl commerceProductDefinitionImpl = new CommerceProductDefinitionImpl();

		if (uuid == null) {
			commerceProductDefinitionImpl.setUuid(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setUuid(uuid);
		}

		commerceProductDefinitionImpl.setCommerceProductDefinitionId(commerceProductDefinitionId);
		commerceProductDefinitionImpl.setGroupId(groupId);
		commerceProductDefinitionImpl.setCompanyId(companyId);
		commerceProductDefinitionImpl.setUserId(userId);

		if (userName == null) {
			commerceProductDefinitionImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceProductDefinitionImpl.setCreateDate(null);
		}
		else {
			commerceProductDefinitionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceProductDefinitionImpl.setModifiedDate(null);
		}
		else {
			commerceProductDefinitionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			commerceProductDefinitionImpl.setTitle(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setTitle(title);
		}

		if (urlTitle == null) {
			commerceProductDefinitionImpl.setUrlTitle(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setUrlTitle(urlTitle);
		}

		if (description == null) {
			commerceProductDefinitionImpl.setDescription(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setDescription(description);
		}

		if (productTypeName == null) {
			commerceProductDefinitionImpl.setProductTypeName(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setProductTypeName(productTypeName);
		}

		commerceProductDefinitionImpl.setAvailableIndividually(availableIndividually);

		if (DDMStructureKey == null) {
			commerceProductDefinitionImpl.setDDMStructureKey(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setDDMStructureKey(DDMStructureKey);
		}

		if (baseSKU == null) {
			commerceProductDefinitionImpl.setBaseSKU(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setBaseSKU(baseSKU);
		}

		if (displayDate == Long.MIN_VALUE) {
			commerceProductDefinitionImpl.setDisplayDate(null);
		}
		else {
			commerceProductDefinitionImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceProductDefinitionImpl.setExpirationDate(null);
		}
		else {
			commerceProductDefinitionImpl.setExpirationDate(new Date(
					expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceProductDefinitionImpl.setLastPublishDate(null);
		}
		else {
			commerceProductDefinitionImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		commerceProductDefinitionImpl.setStatus(status);
		commerceProductDefinitionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceProductDefinitionImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			commerceProductDefinitionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceProductDefinitionImpl.setStatusDate(null);
		}
		else {
			commerceProductDefinitionImpl.setStatusDate(new Date(statusDate));
		}

		commerceProductDefinitionImpl.resetOriginalValues();

		return commerceProductDefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceProductDefinitionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();
		urlTitle = objectInput.readUTF();
		description = objectInput.readUTF();
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

		objectOutput.writeLong(commerceProductDefinitionId);

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

		if (urlTitle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(urlTitle);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

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
	}

	public String uuid;
	public long commerceProductDefinitionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String urlTitle;
	public String description;
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
}