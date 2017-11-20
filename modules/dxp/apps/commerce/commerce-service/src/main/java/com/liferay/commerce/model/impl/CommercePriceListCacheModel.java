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

import com.liferay.commerce.model.CommercePriceList;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommercePriceList in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceList
 * @generated
 */
@ProviderType
public class CommercePriceListCacheModel implements CacheModel<CommercePriceList>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceListCacheModel)) {
			return false;
		}

		CommercePriceListCacheModel commercePriceListCacheModel = (CommercePriceListCacheModel)obj;

		if (commercePriceListId == commercePriceListCacheModel.commercePriceListId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commercePriceListId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
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
		sb.append(", commerceCurrencyId=");
		sb.append(commerceCurrencyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
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
	public CommercePriceList toEntityModel() {
		CommercePriceListImpl commercePriceListImpl = new CommercePriceListImpl();

		if (uuid == null) {
			commercePriceListImpl.setUuid("");
		}
		else {
			commercePriceListImpl.setUuid(uuid);
		}

		commercePriceListImpl.setCommercePriceListId(commercePriceListId);
		commercePriceListImpl.setGroupId(groupId);
		commercePriceListImpl.setCompanyId(companyId);
		commercePriceListImpl.setUserId(userId);

		if (userName == null) {
			commercePriceListImpl.setUserName("");
		}
		else {
			commercePriceListImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceListImpl.setCreateDate(null);
		}
		else {
			commercePriceListImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceListImpl.setModifiedDate(null);
		}
		else {
			commercePriceListImpl.setModifiedDate(new Date(modifiedDate));
		}

		commercePriceListImpl.setCommerceCurrencyId(commerceCurrencyId);

		if (name == null) {
			commercePriceListImpl.setName("");
		}
		else {
			commercePriceListImpl.setName(name);
		}

		commercePriceListImpl.setPriority(priority);

		if (displayDate == Long.MIN_VALUE) {
			commercePriceListImpl.setDisplayDate(null);
		}
		else {
			commercePriceListImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commercePriceListImpl.setExpirationDate(null);
		}
		else {
			commercePriceListImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceListImpl.setLastPublishDate(null);
		}
		else {
			commercePriceListImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commercePriceListImpl.setStatus(status);
		commercePriceListImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commercePriceListImpl.setStatusByUserName("");
		}
		else {
			commercePriceListImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commercePriceListImpl.setStatusDate(null);
		}
		else {
			commercePriceListImpl.setStatusDate(new Date(statusDate));
		}

		commercePriceListImpl.resetOriginalValues();

		return commercePriceListImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commercePriceListId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceCurrencyId = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readDouble();
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
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commercePriceListId);

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

		objectOutput.writeLong(commerceCurrencyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeDouble(priority);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public String uuid;
	public long commercePriceListId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceCurrencyId;
	public String name;
	public double priority;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}