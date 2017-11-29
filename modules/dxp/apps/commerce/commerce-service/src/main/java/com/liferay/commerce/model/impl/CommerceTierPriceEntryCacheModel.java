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

import com.liferay.commerce.model.CommerceTierPriceEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceTierPriceEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntry
 * @generated
 */
@ProviderType
public class CommerceTierPriceEntryCacheModel implements CacheModel<CommerceTierPriceEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTierPriceEntryCacheModel)) {
			return false;
		}

		CommerceTierPriceEntryCacheModel commerceTierPriceEntryCacheModel = (CommerceTierPriceEntryCacheModel)obj;

		if (CommerceTierPriceEntryId == commerceTierPriceEntryCacheModel.CommerceTierPriceEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CommerceTierPriceEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CommerceTierPriceEntryId=");
		sb.append(CommerceTierPriceEntryId);
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
		sb.append(", commercePriceEntryId=");
		sb.append(commercePriceEntryId);
		sb.append(", price=");
		sb.append(price);
		sb.append(", minQuantity=");
		sb.append(minQuantity);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTierPriceEntry toEntityModel() {
		CommerceTierPriceEntryImpl commerceTierPriceEntryImpl = new CommerceTierPriceEntryImpl();

		if (uuid == null) {
			commerceTierPriceEntryImpl.setUuid("");
		}
		else {
			commerceTierPriceEntryImpl.setUuid(uuid);
		}

		commerceTierPriceEntryImpl.setCommerceTierPriceEntryId(CommerceTierPriceEntryId);
		commerceTierPriceEntryImpl.setGroupId(groupId);
		commerceTierPriceEntryImpl.setCompanyId(companyId);
		commerceTierPriceEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceTierPriceEntryImpl.setUserName("");
		}
		else {
			commerceTierPriceEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setCreateDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setModifiedDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceTierPriceEntryImpl.setCommercePriceEntryId(commercePriceEntryId);
		commerceTierPriceEntryImpl.setPrice(price);
		commerceTierPriceEntryImpl.setMinQuantity(minQuantity);

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setLastPublishDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		commerceTierPriceEntryImpl.resetOriginalValues();

		return commerceTierPriceEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CommerceTierPriceEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceEntryId = objectInput.readLong();

		price = objectInput.readDouble();

		minQuantity = objectInput.readInt();
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

		objectOutput.writeLong(CommerceTierPriceEntryId);

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

		objectOutput.writeLong(commercePriceEntryId);

		objectOutput.writeDouble(price);

		objectOutput.writeInt(minQuantity);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long CommerceTierPriceEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceEntryId;
	public double price;
	public int minQuantity;
	public long lastPublishDate;
}