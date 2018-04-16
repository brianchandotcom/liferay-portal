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

import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceUserSegmentEntry in entity cache.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntry
 * @generated
 */
@ProviderType
public class CommerceUserSegmentEntryCacheModel implements CacheModel<CommerceUserSegmentEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceUserSegmentEntryCacheModel)) {
			return false;
		}

		CommerceUserSegmentEntryCacheModel commerceUserSegmentEntryCacheModel = (CommerceUserSegmentEntryCacheModel)obj;

		if (commerceUserSegmentEntryId == commerceUserSegmentEntryCacheModel.commerceUserSegmentEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceUserSegmentEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceUserSegmentEntryId=");
		sb.append(commerceUserSegmentEntryId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceUserSegmentEntry toEntityModel() {
		CommerceUserSegmentEntryImpl commerceUserSegmentEntryImpl = new CommerceUserSegmentEntryImpl();

		commerceUserSegmentEntryImpl.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
		commerceUserSegmentEntryImpl.setGroupId(groupId);
		commerceUserSegmentEntryImpl.setCompanyId(companyId);
		commerceUserSegmentEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceUserSegmentEntryImpl.setUserName("");
		}
		else {
			commerceUserSegmentEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceUserSegmentEntryImpl.setCreateDate(null);
		}
		else {
			commerceUserSegmentEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceUserSegmentEntryImpl.setModifiedDate(null);
		}
		else {
			commerceUserSegmentEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceUserSegmentEntryImpl.setName("");
		}
		else {
			commerceUserSegmentEntryImpl.setName(name);
		}

		commerceUserSegmentEntryImpl.setPriority(priority);
		commerceUserSegmentEntryImpl.setActive(active);

		commerceUserSegmentEntryImpl.resetOriginalValues();

		return commerceUserSegmentEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceUserSegmentEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readDouble();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceUserSegmentEntryId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(active);
	}

	public long commerceUserSegmentEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public double priority;
	public boolean active;
}