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

import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
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
 * The cache model class for representing CommercePriceListDiscountRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListDiscountRelCacheModel
	implements CacheModel<CommercePriceListDiscountRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceListDiscountRelCacheModel)) {
			return false;
		}

		CommercePriceListDiscountRelCacheModel
			commercePriceListDiscountRelCacheModel =
				(CommercePriceListDiscountRelCacheModel)object;

		if ((commercePriceListDiscountRelId ==
				commercePriceListDiscountRelCacheModel.
					commercePriceListDiscountRelId) &&
			(mvccVersion ==
				commercePriceListDiscountRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePriceListDiscountRelId);

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
		sb.append(", commercePriceListDiscountRelId=");
		sb.append(commercePriceListDiscountRelId);
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
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
		sb.append(", order=");
		sb.append(order);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePriceListDiscountRel toEntityModel() {
		CommercePriceListDiscountRelImpl commercePriceListDiscountRelImpl =
			new CommercePriceListDiscountRelImpl();

		commercePriceListDiscountRelImpl.setMvccVersion(mvccVersion);
		commercePriceListDiscountRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commercePriceListDiscountRelImpl.setUuid("");
		}
		else {
			commercePriceListDiscountRelImpl.setUuid(uuid);
		}

		commercePriceListDiscountRelImpl.setCommercePriceListDiscountRelId(
			commercePriceListDiscountRelId);
		commercePriceListDiscountRelImpl.setCompanyId(companyId);
		commercePriceListDiscountRelImpl.setUserId(userId);

		if (userName == null) {
			commercePriceListDiscountRelImpl.setUserName("");
		}
		else {
			commercePriceListDiscountRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceListDiscountRelImpl.setCreateDate(null);
		}
		else {
			commercePriceListDiscountRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceListDiscountRelImpl.setModifiedDate(null);
		}
		else {
			commercePriceListDiscountRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commercePriceListDiscountRelImpl.setCommerceDiscountId(
			commerceDiscountId);
		commercePriceListDiscountRelImpl.setCommercePriceListId(
			commercePriceListId);
		commercePriceListDiscountRelImpl.setOrder(order);

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceListDiscountRelImpl.setLastPublishDate(null);
		}
		else {
			commercePriceListDiscountRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commercePriceListDiscountRelImpl.resetOriginalValues();

		return commercePriceListDiscountRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		commercePriceListDiscountRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();

		commercePriceListId = objectInput.readLong();

		order = objectInput.readInt();
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

		objectOutput.writeLong(commercePriceListDiscountRelId);

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

		objectOutput.writeLong(commerceDiscountId);

		objectOutput.writeLong(commercePriceListId);

		objectOutput.writeInt(order);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long commercePriceListDiscountRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceDiscountId;
	public long commercePriceListId;
	public int order;
	public long lastPublishDate;

}