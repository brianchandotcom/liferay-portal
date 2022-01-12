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

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
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
 * The cache model class for representing CommercePriceListChannelRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListChannelRelCacheModel
	implements CacheModel<CommercePriceListChannelRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceListChannelRelCacheModel)) {
			return false;
		}

		CommercePriceListChannelRelCacheModel
			commercePriceListChannelRelCacheModel =
				(CommercePriceListChannelRelCacheModel)object;

		if ((CommercePriceListChannelRelId ==
				commercePriceListChannelRelCacheModel.
					CommercePriceListChannelRelId) &&
			(mvccVersion ==
				commercePriceListChannelRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CommercePriceListChannelRelId);

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
		sb.append(", CommercePriceListChannelRelId=");
		sb.append(CommercePriceListChannelRelId);
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
		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);
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
	public CommercePriceListChannelRel toEntityModel() {
		CommercePriceListChannelRelImpl commercePriceListChannelRelImpl =
			new CommercePriceListChannelRelImpl();

		commercePriceListChannelRelImpl.setMvccVersion(mvccVersion);
		commercePriceListChannelRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commercePriceListChannelRelImpl.setUuid("");
		}
		else {
			commercePriceListChannelRelImpl.setUuid(uuid);
		}

		commercePriceListChannelRelImpl.setCommercePriceListChannelRelId(
			CommercePriceListChannelRelId);
		commercePriceListChannelRelImpl.setCompanyId(companyId);
		commercePriceListChannelRelImpl.setUserId(userId);

		if (userName == null) {
			commercePriceListChannelRelImpl.setUserName("");
		}
		else {
			commercePriceListChannelRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceListChannelRelImpl.setCreateDate(null);
		}
		else {
			commercePriceListChannelRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceListChannelRelImpl.setModifiedDate(null);
		}
		else {
			commercePriceListChannelRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commercePriceListChannelRelImpl.setCommerceChannelId(commerceChannelId);
		commercePriceListChannelRelImpl.setCommercePriceListId(
			commercePriceListId);
		commercePriceListChannelRelImpl.setOrder(order);

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceListChannelRelImpl.setLastPublishDate(null);
		}
		else {
			commercePriceListChannelRelImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commercePriceListChannelRelImpl.resetOriginalValues();

		return commercePriceListChannelRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CommercePriceListChannelRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceChannelId = objectInput.readLong();

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

		objectOutput.writeLong(CommercePriceListChannelRelId);

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

		objectOutput.writeLong(commerceChannelId);

		objectOutput.writeLong(commercePriceListId);

		objectOutput.writeInt(order);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long CommercePriceListChannelRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceChannelId;
	public long commercePriceListId;
	public int order;
	public long lastPublishDate;

}