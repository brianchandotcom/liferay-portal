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

import com.liferay.commerce.price.list.model.CommercePriceList;
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
 * The cache model class for representing CommercePriceList in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListCacheModel
	implements CacheModel<CommercePriceList>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceListCacheModel)) {
			return false;
		}

		CommercePriceListCacheModel commercePriceListCacheModel =
			(CommercePriceListCacheModel)object;

		if ((commercePriceListId ==
				commercePriceListCacheModel.commercePriceListId) &&
			(mvccVersion == commercePriceListCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePriceListId);

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
		StringBundler sb = new StringBundler(51);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
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
		sb.append(", parentCommercePriceListId=");
		sb.append(parentCommercePriceListId);
		sb.append(", catalogBasePriceList=");
		sb.append(catalogBasePriceList);
		sb.append(", netPrice=");
		sb.append(netPrice);
		sb.append(", type=");
		sb.append(type);
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
		CommercePriceListImpl commercePriceListImpl =
			new CommercePriceListImpl();

		commercePriceListImpl.setMvccVersion(mvccVersion);
		commercePriceListImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commercePriceListImpl.setUuid("");
		}
		else {
			commercePriceListImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commercePriceListImpl.setExternalReferenceCode("");
		}
		else {
			commercePriceListImpl.setExternalReferenceCode(
				externalReferenceCode);
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
		commercePriceListImpl.setParentCommercePriceListId(
			parentCommercePriceListId);
		commercePriceListImpl.setCatalogBasePriceList(catalogBasePriceList);
		commercePriceListImpl.setNetPrice(netPrice);

		if (type == null) {
			commercePriceListImpl.setType("");
		}
		else {
			commercePriceListImpl.setType(type);
		}

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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commercePriceListId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceCurrencyId = objectInput.readLong();

		parentCommercePriceListId = objectInput.readLong();

		catalogBasePriceList = objectInput.readBoolean();

		netPrice = objectInput.readBoolean();
		type = objectInput.readUTF();
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

		objectOutput.writeLong(parentCommercePriceListId);

		objectOutput.writeBoolean(catalogBasePriceList);

		objectOutput.writeBoolean(netPrice);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

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

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long commercePriceListId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceCurrencyId;
	public long parentCommercePriceListId;
	public boolean catalogBasePriceList;
	public boolean netPrice;
	public String type;
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