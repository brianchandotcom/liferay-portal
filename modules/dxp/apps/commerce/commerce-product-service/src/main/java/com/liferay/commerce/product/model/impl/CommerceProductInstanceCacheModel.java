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

import com.liferay.commerce.product.model.CommerceProductInstance;

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
 * The cache model class for representing CommerceProductInstance in entity cache.
 *
 * @author Marco Leo
 * @see CommerceProductInstance
 * @generated
 */
@ProviderType
public class CommerceProductInstanceCacheModel implements CacheModel<CommerceProductInstance>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductInstanceCacheModel)) {
			return false;
		}

		CommerceProductInstanceCacheModel commerceProductInstanceCacheModel = (CommerceProductInstanceCacheModel)obj;

		if (commerceProductInstanceId == commerceProductInstanceCacheModel.commerceProductInstanceId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceProductInstanceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(39);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceProductInstanceId=");
		sb.append(commerceProductInstanceId);
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
		sb.append(", commerceProductDefinitionId=");
		sb.append(commerceProductDefinitionId);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", LSIN=");
		sb.append(LSIN);
		sb.append(", DDMContent=");
		sb.append(DDMContent);
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
	public CommerceProductInstance toEntityModel() {
		CommerceProductInstanceImpl commerceProductInstanceImpl = new CommerceProductInstanceImpl();

		if (uuid == null) {
			commerceProductInstanceImpl.setUuid(StringPool.BLANK);
		}
		else {
			commerceProductInstanceImpl.setUuid(uuid);
		}

		commerceProductInstanceImpl.setCommerceProductInstanceId(commerceProductInstanceId);
		commerceProductInstanceImpl.setGroupId(groupId);
		commerceProductInstanceImpl.setCompanyId(companyId);
		commerceProductInstanceImpl.setUserId(userId);

		if (userName == null) {
			commerceProductInstanceImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceProductInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceProductInstanceImpl.setCreateDate(null);
		}
		else {
			commerceProductInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceProductInstanceImpl.setModifiedDate(null);
		}
		else {
			commerceProductInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceProductInstanceImpl.setCommerceProductDefinitionId(commerceProductDefinitionId);

		if (sku == null) {
			commerceProductInstanceImpl.setSku(StringPool.BLANK);
		}
		else {
			commerceProductInstanceImpl.setSku(sku);
		}

		if (LSIN == null) {
			commerceProductInstanceImpl.setLSIN(StringPool.BLANK);
		}
		else {
			commerceProductInstanceImpl.setLSIN(LSIN);
		}

		if (DDMContent == null) {
			commerceProductInstanceImpl.setDDMContent(StringPool.BLANK);
		}
		else {
			commerceProductInstanceImpl.setDDMContent(DDMContent);
		}

		if (displayDate == Long.MIN_VALUE) {
			commerceProductInstanceImpl.setDisplayDate(null);
		}
		else {
			commerceProductInstanceImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceProductInstanceImpl.setExpirationDate(null);
		}
		else {
			commerceProductInstanceImpl.setExpirationDate(new Date(
					expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceProductInstanceImpl.setLastPublishDate(null);
		}
		else {
			commerceProductInstanceImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		commerceProductInstanceImpl.setStatus(status);
		commerceProductInstanceImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceProductInstanceImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			commerceProductInstanceImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceProductInstanceImpl.setStatusDate(null);
		}
		else {
			commerceProductInstanceImpl.setStatusDate(new Date(statusDate));
		}

		commerceProductInstanceImpl.resetOriginalValues();

		return commerceProductInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceProductInstanceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceProductDefinitionId = objectInput.readLong();
		sku = objectInput.readUTF();
		LSIN = objectInput.readUTF();
		DDMContent = objectInput.readUTF();
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

		objectOutput.writeLong(commerceProductInstanceId);

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

		objectOutput.writeLong(commerceProductDefinitionId);

		if (sku == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(sku);
		}

		if (LSIN == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(LSIN);
		}

		if (DDMContent == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(DDMContent);
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
	public long commerceProductInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceProductDefinitionId;
	public String sku;
	public String LSIN;
	public String DDMContent;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}