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

import com.liferay.commerce.model.CommerceAvailabilityRange;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceAvailabilityRange in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRange
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeCacheModel implements CacheModel<CommerceAvailabilityRange>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceAvailabilityRangeCacheModel)) {
			return false;
		}

		CommerceAvailabilityRangeCacheModel commerceAvailabilityRangeCacheModel = (CommerceAvailabilityRangeCacheModel)obj;

		if (commerceAvailabilityRangeId == commerceAvailabilityRangeCacheModel.commerceAvailabilityRangeId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceAvailabilityRangeId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceAvailabilityRangeId=");
		sb.append(commerceAvailabilityRangeId);
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
		sb.append(", title=");
		sb.append(title);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceAvailabilityRange toEntityModel() {
		CommerceAvailabilityRangeImpl commerceAvailabilityRangeImpl = new CommerceAvailabilityRangeImpl();

		if (uuid == null) {
			commerceAvailabilityRangeImpl.setUuid("");
		}
		else {
			commerceAvailabilityRangeImpl.setUuid(uuid);
		}

		commerceAvailabilityRangeImpl.setCommerceAvailabilityRangeId(commerceAvailabilityRangeId);
		commerceAvailabilityRangeImpl.setGroupId(groupId);
		commerceAvailabilityRangeImpl.setCompanyId(companyId);
		commerceAvailabilityRangeImpl.setUserId(userId);

		if (userName == null) {
			commerceAvailabilityRangeImpl.setUserName("");
		}
		else {
			commerceAvailabilityRangeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceAvailabilityRangeImpl.setCreateDate(null);
		}
		else {
			commerceAvailabilityRangeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceAvailabilityRangeImpl.setModifiedDate(null);
		}
		else {
			commerceAvailabilityRangeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			commerceAvailabilityRangeImpl.setTitle("");
		}
		else {
			commerceAvailabilityRangeImpl.setTitle(title);
		}

		commerceAvailabilityRangeImpl.setPriority(priority);

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceAvailabilityRangeImpl.setLastPublishDate(null);
		}
		else {
			commerceAvailabilityRangeImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		commerceAvailabilityRangeImpl.resetOriginalValues();

		return commerceAvailabilityRangeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceAvailabilityRangeId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();

		priority = objectInput.readDouble();
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

		objectOutput.writeLong(commerceAvailabilityRangeId);

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

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeDouble(priority);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long commerceAvailabilityRangeId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public double priority;
	public long lastPublishDate;
}