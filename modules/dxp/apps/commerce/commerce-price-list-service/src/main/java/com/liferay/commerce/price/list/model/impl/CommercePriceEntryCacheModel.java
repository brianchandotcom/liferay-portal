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

package com.liferay.commerce.price.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.model.CommercePriceEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommercePriceEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntry
 * @generated
 */
@ProviderType
public class CommercePriceEntryCacheModel implements CacheModel<CommercePriceEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceEntryCacheModel)) {
			return false;
		}

		CommercePriceEntryCacheModel commercePriceEntryCacheModel = (CommercePriceEntryCacheModel)obj;

		if (commercePriceEntryId == commercePriceEntryCacheModel.commercePriceEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commercePriceEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commercePriceEntryId=");
		sb.append(commercePriceEntryId);
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
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
		sb.append(", price=");
		sb.append(price);
		sb.append(", promoPrice=");
		sb.append(promoPrice);
		sb.append(", hasTierPrice=");
		sb.append(hasTierPrice);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePriceEntry toEntityModel() {
		CommercePriceEntryImpl commercePriceEntryImpl = new CommercePriceEntryImpl();

		if (uuid == null) {
			commercePriceEntryImpl.setUuid("");
		}
		else {
			commercePriceEntryImpl.setUuid(uuid);
		}

		commercePriceEntryImpl.setCommercePriceEntryId(commercePriceEntryId);
		commercePriceEntryImpl.setGroupId(groupId);
		commercePriceEntryImpl.setCompanyId(companyId);
		commercePriceEntryImpl.setUserId(userId);

		if (userName == null) {
			commercePriceEntryImpl.setUserName("");
		}
		else {
			commercePriceEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setCreateDate(null);
		}
		else {
			commercePriceEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setModifiedDate(null);
		}
		else {
			commercePriceEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commercePriceEntryImpl.setCPInstanceId(CPInstanceId);
		commercePriceEntryImpl.setCommercePriceListId(commercePriceListId);
		commercePriceEntryImpl.setPrice(price);
		commercePriceEntryImpl.setPromoPrice(promoPrice);
		commercePriceEntryImpl.setHasTierPrice(hasTierPrice);

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setLastPublishDate(null);
		}
		else {
			commercePriceEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commercePriceEntryImpl.resetOriginalValues();

		return commercePriceEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();

		commercePriceEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPInstanceId = objectInput.readLong();

		commercePriceListId = objectInput.readLong();
		price = (BigDecimal)objectInput.readObject();
		promoPrice = (BigDecimal)objectInput.readObject();

		hasTierPrice = objectInput.readBoolean();
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

		objectOutput.writeLong(commercePriceEntryId);

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

		objectOutput.writeLong(CPInstanceId);

		objectOutput.writeLong(commercePriceListId);
		objectOutput.writeObject(price);
		objectOutput.writeObject(promoPrice);

		objectOutput.writeBoolean(hasTierPrice);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long commercePriceEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPInstanceId;
	public long commercePriceListId;
	public BigDecimal price;
	public BigDecimal promoPrice;
	public boolean hasTierPrice;
	public long lastPublishDate;
}