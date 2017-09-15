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

import com.liferay.commerce.model.CAvailabilityRangeEntry;

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
 * The cache model class for representing CAvailabilityRangeEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CAvailabilityRangeEntry
 * @generated
 */
@ProviderType
public class CAvailabilityRangeEntryCacheModel implements CacheModel<CAvailabilityRangeEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CAvailabilityRangeEntryCacheModel)) {
			return false;
		}

		CAvailabilityRangeEntryCacheModel cAvailabilityRangeEntryCacheModel = (CAvailabilityRangeEntryCacheModel)obj;

		if (CAvailabilityRangeEntryId == cAvailabilityRangeEntryCacheModel.CAvailabilityRangeEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CAvailabilityRangeEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CAvailabilityRangeEntryId=");
		sb.append(CAvailabilityRangeEntryId);
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
		sb.append(", commerceAvailabilityRangeId=");
		sb.append(commerceAvailabilityRangeId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CAvailabilityRangeEntry toEntityModel() {
		CAvailabilityRangeEntryImpl cAvailabilityRangeEntryImpl = new CAvailabilityRangeEntryImpl();

		if (uuid == null) {
			cAvailabilityRangeEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			cAvailabilityRangeEntryImpl.setUuid(uuid);
		}

		cAvailabilityRangeEntryImpl.setCAvailabilityRangeEntryId(CAvailabilityRangeEntryId);
		cAvailabilityRangeEntryImpl.setGroupId(groupId);
		cAvailabilityRangeEntryImpl.setCompanyId(companyId);
		cAvailabilityRangeEntryImpl.setUserId(userId);

		if (userName == null) {
			cAvailabilityRangeEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			cAvailabilityRangeEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cAvailabilityRangeEntryImpl.setCreateDate(null);
		}
		else {
			cAvailabilityRangeEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cAvailabilityRangeEntryImpl.setModifiedDate(null);
		}
		else {
			cAvailabilityRangeEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		cAvailabilityRangeEntryImpl.setCPDefinitionId(CPDefinitionId);
		cAvailabilityRangeEntryImpl.setCommerceAvailabilityRangeId(commerceAvailabilityRangeId);

		if (lastPublishDate == Long.MIN_VALUE) {
			cAvailabilityRangeEntryImpl.setLastPublishDate(null);
		}
		else {
			cAvailabilityRangeEntryImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		cAvailabilityRangeEntryImpl.resetOriginalValues();

		return cAvailabilityRangeEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CAvailabilityRangeEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		commerceAvailabilityRangeId = objectInput.readLong();
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

		objectOutput.writeLong(CAvailabilityRangeEntryId);

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

		objectOutput.writeLong(commerceAvailabilityRangeId);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long CAvailabilityRangeEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long commerceAvailabilityRangeId;
	public long lastPublishDate;
}