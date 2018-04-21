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

import com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommercePriceListUserSegmentEntryRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRel
 * @generated
 */
@ProviderType
public class CommercePriceListUserSegmentEntryRelCacheModel
	implements CacheModel<CommercePriceListUserSegmentEntryRel>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceListUserSegmentEntryRelCacheModel)) {
			return false;
		}

		CommercePriceListUserSegmentEntryRelCacheModel commercePriceListUserSegmentEntryRelCacheModel =
			(CommercePriceListUserSegmentEntryRelCacheModel)obj;

		if (commercePriceListUserSegmentEntryRelId == commercePriceListUserSegmentEntryRelCacheModel.commercePriceListUserSegmentEntryRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commercePriceListUserSegmentEntryRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commercePriceListUserSegmentEntryRelId=");
		sb.append(commercePriceListUserSegmentEntryRelId);
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
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
		sb.append(", commerceUserSegmentEntryId=");
		sb.append(commerceUserSegmentEntryId);
		sb.append(", order=");
		sb.append(order);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePriceListUserSegmentEntryRel toEntityModel() {
		CommercePriceListUserSegmentEntryRelImpl commercePriceListUserSegmentEntryRelImpl =
			new CommercePriceListUserSegmentEntryRelImpl();

		if (uuid == null) {
			commercePriceListUserSegmentEntryRelImpl.setUuid("");
		}
		else {
			commercePriceListUserSegmentEntryRelImpl.setUuid(uuid);
		}

		commercePriceListUserSegmentEntryRelImpl.setCommercePriceListUserSegmentEntryRelId(commercePriceListUserSegmentEntryRelId);
		commercePriceListUserSegmentEntryRelImpl.setGroupId(groupId);
		commercePriceListUserSegmentEntryRelImpl.setCompanyId(companyId);
		commercePriceListUserSegmentEntryRelImpl.setUserId(userId);

		if (userName == null) {
			commercePriceListUserSegmentEntryRelImpl.setUserName("");
		}
		else {
			commercePriceListUserSegmentEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceListUserSegmentEntryRelImpl.setCreateDate(null);
		}
		else {
			commercePriceListUserSegmentEntryRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceListUserSegmentEntryRelImpl.setModifiedDate(null);
		}
		else {
			commercePriceListUserSegmentEntryRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commercePriceListUserSegmentEntryRelImpl.setCommercePriceListId(commercePriceListId);
		commercePriceListUserSegmentEntryRelImpl.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
		commercePriceListUserSegmentEntryRelImpl.setOrder(order);

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceListUserSegmentEntryRelImpl.setLastPublishDate(null);
		}
		else {
			commercePriceListUserSegmentEntryRelImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		commercePriceListUserSegmentEntryRelImpl.resetOriginalValues();

		return commercePriceListUserSegmentEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commercePriceListUserSegmentEntryRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceListId = objectInput.readLong();

		commerceUserSegmentEntryId = objectInput.readLong();

		order = objectInput.readInt();
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

		objectOutput.writeLong(commercePriceListUserSegmentEntryRelId);

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

		objectOutput.writeLong(commercePriceListId);

		objectOutput.writeLong(commerceUserSegmentEntryId);

		objectOutput.writeInt(order);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long commercePriceListUserSegmentEntryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceListId;
	public long commerceUserSegmentEntryId;
	public int order;
	public long lastPublishDate;
}