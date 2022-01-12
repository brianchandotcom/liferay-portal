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

import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
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
 * The cache model class for representing CommercePriceListOrderTypeRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListOrderTypeRelCacheModel
	implements CacheModel<CommercePriceListOrderTypeRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceListOrderTypeRelCacheModel)) {
			return false;
		}

		CommercePriceListOrderTypeRelCacheModel
			commercePriceListOrderTypeRelCacheModel =
				(CommercePriceListOrderTypeRelCacheModel)object;

		if ((commercePriceListOrderTypeRelId ==
				commercePriceListOrderTypeRelCacheModel.
					commercePriceListOrderTypeRelId) &&
			(mvccVersion ==
				commercePriceListOrderTypeRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePriceListOrderTypeRelId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", commercePriceListOrderTypeRelId=");
		sb.append(commercePriceListOrderTypeRelId);
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
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
		sb.append(", commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePriceListOrderTypeRel toEntityModel() {
		CommercePriceListOrderTypeRelImpl commercePriceListOrderTypeRelImpl =
			new CommercePriceListOrderTypeRelImpl();

		commercePriceListOrderTypeRelImpl.setMvccVersion(mvccVersion);
		commercePriceListOrderTypeRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commercePriceListOrderTypeRelImpl.setUuid("");
		}
		else {
			commercePriceListOrderTypeRelImpl.setUuid(uuid);
		}

		commercePriceListOrderTypeRelImpl.setCommercePriceListOrderTypeRelId(
			commercePriceListOrderTypeRelId);
		commercePriceListOrderTypeRelImpl.setCompanyId(companyId);
		commercePriceListOrderTypeRelImpl.setUserId(userId);

		if (userName == null) {
			commercePriceListOrderTypeRelImpl.setUserName("");
		}
		else {
			commercePriceListOrderTypeRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceListOrderTypeRelImpl.setCreateDate(null);
		}
		else {
			commercePriceListOrderTypeRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceListOrderTypeRelImpl.setModifiedDate(null);
		}
		else {
			commercePriceListOrderTypeRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commercePriceListOrderTypeRelImpl.setCommercePriceListId(
			commercePriceListId);
		commercePriceListOrderTypeRelImpl.setCommerceOrderTypeId(
			commerceOrderTypeId);
		commercePriceListOrderTypeRelImpl.setPriority(priority);

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceListOrderTypeRelImpl.setLastPublishDate(null);
		}
		else {
			commercePriceListOrderTypeRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commercePriceListOrderTypeRelImpl.resetOriginalValues();

		return commercePriceListOrderTypeRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		commercePriceListOrderTypeRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceListId = objectInput.readLong();

		commerceOrderTypeId = objectInput.readLong();

		priority = objectInput.readInt();
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

		objectOutput.writeLong(commercePriceListOrderTypeRelId);

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

		objectOutput.writeLong(commercePriceListId);

		objectOutput.writeLong(commerceOrderTypeId);

		objectOutput.writeInt(priority);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long commercePriceListOrderTypeRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceListId;
	public long commerceOrderTypeId;
	public int priority;
	public long lastPublishDate;

}