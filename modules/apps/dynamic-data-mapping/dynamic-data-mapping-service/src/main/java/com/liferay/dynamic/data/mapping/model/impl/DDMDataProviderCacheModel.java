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

package com.liferay.dynamic.data.mapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMDataProvider;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DDMDataProvider in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProvider
 * @generated
 */
@ProviderType
public class DDMDataProviderCacheModel implements CacheModel<DDMDataProvider>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMDataProviderCacheModel)) {
			return false;
		}

		DDMDataProviderCacheModel ddmDataProviderCacheModel = (DDMDataProviderCacheModel)obj;

		if (dataProviderId == ddmDataProviderCacheModel.dataProviderId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, dataProviderId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", dataProviderId=");
		sb.append(dataProviderId);
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
		sb.append(", description=");
		sb.append(description);
		sb.append(", definition=");
		sb.append(definition);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMDataProvider toEntityModel() {
		DDMDataProviderImpl ddmDataProviderImpl = new DDMDataProviderImpl();

		if (uuid == null) {
			ddmDataProviderImpl.setUuid(StringPool.BLANK);
		}
		else {
			ddmDataProviderImpl.setUuid(uuid);
		}

		ddmDataProviderImpl.setDataProviderId(dataProviderId);
		ddmDataProviderImpl.setGroupId(groupId);
		ddmDataProviderImpl.setCompanyId(companyId);
		ddmDataProviderImpl.setUserId(userId);

		if (userName == null) {
			ddmDataProviderImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddmDataProviderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmDataProviderImpl.setCreateDate(null);
		}
		else {
			ddmDataProviderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmDataProviderImpl.setModifiedDate(null);
		}
		else {
			ddmDataProviderImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			ddmDataProviderImpl.setName(StringPool.BLANK);
		}
		else {
			ddmDataProviderImpl.setName(name);
		}

		if (description == null) {
			ddmDataProviderImpl.setDescription(StringPool.BLANK);
		}
		else {
			ddmDataProviderImpl.setDescription(description);
		}

		if (definition == null) {
			ddmDataProviderImpl.setDefinition(StringPool.BLANK);
		}
		else {
			ddmDataProviderImpl.setDefinition(definition);
		}

		ddmDataProviderImpl.resetOriginalValues();

		return ddmDataProviderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		dataProviderId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		definition = objectInput.readUTF();
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

		objectOutput.writeLong(dataProviderId);
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

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (definition == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(definition);
		}
	}

	public String uuid;
	public long dataProviderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String definition;
}