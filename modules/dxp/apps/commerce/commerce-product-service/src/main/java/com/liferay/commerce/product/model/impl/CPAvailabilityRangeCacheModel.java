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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPAvailabilityRange;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPAvailabilityRange in entity cache.
 *
 * @author Marco Leo
 * @see CPAvailabilityRange
 * @generated
 */
@ProviderType
public class CPAvailabilityRangeCacheModel implements CacheModel<CPAvailabilityRange>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPAvailabilityRangeCacheModel)) {
			return false;
		}

		CPAvailabilityRangeCacheModel cpAvailabilityRangeCacheModel = (CPAvailabilityRangeCacheModel)obj;

		if (CPAvailabilityRangeId == cpAvailabilityRangeCacheModel.CPAvailabilityRangeId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPAvailabilityRangeId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPAvailabilityRangeId=");
		sb.append(CPAvailabilityRangeId);
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
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPAvailabilityRange toEntityModel() {
		CPAvailabilityRangeImpl cpAvailabilityRangeImpl = new CPAvailabilityRangeImpl();

		if (uuid == null) {
			cpAvailabilityRangeImpl.setUuid(StringPool.BLANK);
		}
		else {
			cpAvailabilityRangeImpl.setUuid(uuid);
		}

		cpAvailabilityRangeImpl.setCPAvailabilityRangeId(CPAvailabilityRangeId);
		cpAvailabilityRangeImpl.setGroupId(groupId);
		cpAvailabilityRangeImpl.setCompanyId(companyId);
		cpAvailabilityRangeImpl.setUserId(userId);

		if (userName == null) {
			cpAvailabilityRangeImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpAvailabilityRangeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpAvailabilityRangeImpl.setCreateDate(null);
		}
		else {
			cpAvailabilityRangeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpAvailabilityRangeImpl.setModifiedDate(null);
		}
		else {
			cpAvailabilityRangeImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpAvailabilityRangeImpl.setCPDefinitionId(CPDefinitionId);

		if (title == null) {
			cpAvailabilityRangeImpl.setTitle(StringPool.BLANK);
		}
		else {
			cpAvailabilityRangeImpl.setTitle(title);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			cpAvailabilityRangeImpl.setLastPublishDate(null);
		}
		else {
			cpAvailabilityRangeImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		cpAvailabilityRangeImpl.resetOriginalValues();

		return cpAvailabilityRangeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPAvailabilityRangeId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();
		title = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPAvailabilityRangeId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(CPDefinitionId);

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long CPAvailabilityRangeId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public String title;
	public long lastPublishDate;
}