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

package com.liferay.commerce.user.segment.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceUserSegmentCriterion in entity cache.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterion
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionCacheModel implements CacheModel<CommerceUserSegmentCriterion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceUserSegmentCriterionCacheModel)) {
			return false;
		}

		CommerceUserSegmentCriterionCacheModel commerceUserSegmentCriterionCacheModel =
			(CommerceUserSegmentCriterionCacheModel)obj;

		if (commerceUserSegmentCriterionId == commerceUserSegmentCriterionCacheModel.commerceUserSegmentCriterionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceUserSegmentCriterionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{commerceUserSegmentCriterionId=");
		sb.append(commerceUserSegmentCriterionId);
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
		sb.append(", commerceUserSegmentEntryId=");
		sb.append(commerceUserSegmentEntryId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceUserSegmentCriterion toEntityModel() {
		CommerceUserSegmentCriterionImpl commerceUserSegmentCriterionImpl = new CommerceUserSegmentCriterionImpl();

		commerceUserSegmentCriterionImpl.setCommerceUserSegmentCriterionId(commerceUserSegmentCriterionId);
		commerceUserSegmentCriterionImpl.setGroupId(groupId);
		commerceUserSegmentCriterionImpl.setCompanyId(companyId);
		commerceUserSegmentCriterionImpl.setUserId(userId);

		if (userName == null) {
			commerceUserSegmentCriterionImpl.setUserName("");
		}
		else {
			commerceUserSegmentCriterionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceUserSegmentCriterionImpl.setCreateDate(null);
		}
		else {
			commerceUserSegmentCriterionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceUserSegmentCriterionImpl.setModifiedDate(null);
		}
		else {
			commerceUserSegmentCriterionImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceUserSegmentCriterionImpl.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		if (type == null) {
			commerceUserSegmentCriterionImpl.setType("");
		}
		else {
			commerceUserSegmentCriterionImpl.setType(type);
		}

		if (typeSettings == null) {
			commerceUserSegmentCriterionImpl.setTypeSettings("");
		}
		else {
			commerceUserSegmentCriterionImpl.setTypeSettings(typeSettings);
		}

		commerceUserSegmentCriterionImpl.setPriority(priority);

		commerceUserSegmentCriterionImpl.resetOriginalValues();

		return commerceUserSegmentCriterionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceUserSegmentCriterionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceUserSegmentEntryId = objectInput.readLong();
		type = objectInput.readUTF();
		typeSettings = objectInput.readUTF();

		priority = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceUserSegmentCriterionId);

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

		objectOutput.writeLong(commerceUserSegmentEntryId);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeDouble(priority);
	}

	public long commerceUserSegmentCriterionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceUserSegmentEntryId;
	public String type;
	public String typeSettings;
	public double priority;
}