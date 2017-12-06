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

import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommercePriceListQualificationTypeRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRel
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelCacheModel
	implements CacheModel<CommercePriceListQualificationTypeRel>,
		Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceListQualificationTypeRelCacheModel)) {
			return false;
		}

		CommercePriceListQualificationTypeRelCacheModel commercePriceListQualificationTypeRelCacheModel =
			(CommercePriceListQualificationTypeRelCacheModel)obj;

		if (commercePriceListQualificationTypeRelId == commercePriceListQualificationTypeRelCacheModel.commercePriceListQualificationTypeRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commercePriceListQualificationTypeRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commercePriceListQualificationTypeRelId=");
		sb.append(commercePriceListQualificationTypeRelId);
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
		sb.append(", commercePriceListQualificationType=");
		sb.append(commercePriceListQualificationType);
		sb.append(", order=");
		sb.append(order);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePriceListQualificationTypeRel toEntityModel() {
		CommercePriceListQualificationTypeRelImpl commercePriceListQualificationTypeRelImpl =
			new CommercePriceListQualificationTypeRelImpl();

		if (uuid == null) {
			commercePriceListQualificationTypeRelImpl.setUuid("");
		}
		else {
			commercePriceListQualificationTypeRelImpl.setUuid(uuid);
		}

		commercePriceListQualificationTypeRelImpl.setCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId);
		commercePriceListQualificationTypeRelImpl.setGroupId(groupId);
		commercePriceListQualificationTypeRelImpl.setCompanyId(companyId);
		commercePriceListQualificationTypeRelImpl.setUserId(userId);

		if (userName == null) {
			commercePriceListQualificationTypeRelImpl.setUserName("");
		}
		else {
			commercePriceListQualificationTypeRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceListQualificationTypeRelImpl.setCreateDate(null);
		}
		else {
			commercePriceListQualificationTypeRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceListQualificationTypeRelImpl.setModifiedDate(null);
		}
		else {
			commercePriceListQualificationTypeRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commercePriceListQualificationTypeRelImpl.setCommercePriceListId(commercePriceListId);

		if (commercePriceListQualificationType == null) {
			commercePriceListQualificationTypeRelImpl.setCommercePriceListQualificationType(
				"");
		}
		else {
			commercePriceListQualificationTypeRelImpl.setCommercePriceListQualificationType(commercePriceListQualificationType);
		}

		commercePriceListQualificationTypeRelImpl.setOrder(order);

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceListQualificationTypeRelImpl.setLastPublishDate(null);
		}
		else {
			commercePriceListQualificationTypeRelImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		commercePriceListQualificationTypeRelImpl.resetOriginalValues();

		return commercePriceListQualificationTypeRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commercePriceListQualificationTypeRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceListId = objectInput.readLong();
		commercePriceListQualificationType = objectInput.readUTF();

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

		objectOutput.writeLong(commercePriceListQualificationTypeRelId);

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

		if (commercePriceListQualificationType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(commercePriceListQualificationType);
		}

		objectOutput.writeInt(order);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long commercePriceListQualificationTypeRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceListId;
	public String commercePriceListQualificationType;
	public int order;
	public long lastPublishDate;
}