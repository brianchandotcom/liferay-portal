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

import com.liferay.commerce.model.CommerceTirePriceEntry;

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
 * The cache model class for representing CommerceTirePriceEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntry
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntryCacheModel implements CacheModel<CommerceTirePriceEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTirePriceEntryCacheModel)) {
			return false;
		}

		CommerceTirePriceEntryCacheModel commerceTirePriceEntryCacheModel = (CommerceTirePriceEntryCacheModel)obj;

		if (CommerceTirePriceEntryId == commerceTirePriceEntryCacheModel.CommerceTirePriceEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CommerceTirePriceEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CommerceTirePriceEntryId=");
		sb.append(CommerceTirePriceEntryId);
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
	public CommerceTirePriceEntry toEntityModel() {
		CommerceTirePriceEntryImpl commerceTirePriceEntryImpl = new CommerceTirePriceEntryImpl();

		if (uuid == null) {
			commerceTirePriceEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			commerceTirePriceEntryImpl.setUuid(uuid);
		}

		commerceTirePriceEntryImpl.setCommerceTirePriceEntryId(CommerceTirePriceEntryId);
		commerceTirePriceEntryImpl.setGroupId(groupId);
		commerceTirePriceEntryImpl.setCompanyId(companyId);
		commerceTirePriceEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceTirePriceEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceTirePriceEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTirePriceEntryImpl.setCreateDate(null);
		}
		else {
			commerceTirePriceEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTirePriceEntryImpl.setModifiedDate(null);
		}
		else {
			commerceTirePriceEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceTirePriceEntryImpl.setCommercePriceEntryId(commercePriceEntryId);
		commerceTirePriceEntryImpl.setPrice(price);
		commerceTirePriceEntryImpl.setMinQuantity(minQuantity);

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceTirePriceEntryImpl.setLastPublishDate(null);
		}
		else {
			commerceTirePriceEntryImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		commerceTirePriceEntryImpl.resetOriginalValues();

		return commerceTirePriceEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CommerceTirePriceEntryId = objectInput.readLong();

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
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CommerceTirePriceEntryId);

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

		objectOutput.writeLong(commercePriceEntryId);

		objectOutput.writeDouble(price);

		objectOutput.writeInt(minQuantity);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long CommerceTirePriceEntryId;
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