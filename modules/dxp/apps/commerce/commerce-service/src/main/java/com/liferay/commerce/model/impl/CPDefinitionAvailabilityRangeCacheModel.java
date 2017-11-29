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

import com.liferay.commerce.model.CPDefinitionAvailabilityRange;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionAvailabilityRange in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRange
 * @generated
 */
@ProviderType
public class CPDefinitionAvailabilityRangeCacheModel implements CacheModel<CPDefinitionAvailabilityRange>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionAvailabilityRangeCacheModel)) {
			return false;
		}

		CPDefinitionAvailabilityRangeCacheModel cpDefinitionAvailabilityRangeCacheModel =
			(CPDefinitionAvailabilityRangeCacheModel)obj;

		if (CPDefinitionAvailabilityRangeId == cpDefinitionAvailabilityRangeCacheModel.CPDefinitionAvailabilityRangeId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionAvailabilityRangeId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionAvailabilityRangeId=");
		sb.append(CPDefinitionAvailabilityRangeId);
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
	public CPDefinitionAvailabilityRange toEntityModel() {
		CPDefinitionAvailabilityRangeImpl cpDefinitionAvailabilityRangeImpl = new CPDefinitionAvailabilityRangeImpl();

		if (uuid == null) {
			cpDefinitionAvailabilityRangeImpl.setUuid("");
		}
		else {
			cpDefinitionAvailabilityRangeImpl.setUuid(uuid);
		}

		cpDefinitionAvailabilityRangeImpl.setCPDefinitionAvailabilityRangeId(CPDefinitionAvailabilityRangeId);
		cpDefinitionAvailabilityRangeImpl.setGroupId(groupId);
		cpDefinitionAvailabilityRangeImpl.setCompanyId(companyId);
		cpDefinitionAvailabilityRangeImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionAvailabilityRangeImpl.setUserName("");
		}
		else {
			cpDefinitionAvailabilityRangeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionAvailabilityRangeImpl.setCreateDate(null);
		}
		else {
			cpDefinitionAvailabilityRangeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionAvailabilityRangeImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionAvailabilityRangeImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		cpDefinitionAvailabilityRangeImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionAvailabilityRangeImpl.setCommerceAvailabilityRangeId(commerceAvailabilityRangeId);

		if (lastPublishDate == Long.MIN_VALUE) {
			cpDefinitionAvailabilityRangeImpl.setLastPublishDate(null);
		}
		else {
			cpDefinitionAvailabilityRangeImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		cpDefinitionAvailabilityRangeImpl.resetOriginalValues();

		return cpDefinitionAvailabilityRangeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionAvailabilityRangeId = objectInput.readLong();

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
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPDefinitionAvailabilityRangeId);

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

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(commerceAvailabilityRangeId);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long CPDefinitionAvailabilityRangeId;
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