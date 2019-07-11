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

package com.liferay.data.engine.model.impl;

import com.liferay.data.engine.model.DEListView;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing DEListView in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DEListViewCacheModel
	implements CacheModel<DEListView>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEListViewCacheModel)) {
			return false;
		}

		DEListViewCacheModel deListViewCacheModel = (DEListViewCacheModel)obj;

		if (deListViewId == deListViewCacheModel.deListViewId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, deListViewId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deListViewId=");
		sb.append(deListViewId);
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
		sb.append(", DDMStructureId=");
		sb.append(DDMStructureId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", deRecordQueryId=");
		sb.append(deRecordQueryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DEListView toEntityModel() {
		DEListViewImpl deListViewImpl = new DEListViewImpl();

		if (uuid == null) {
			deListViewImpl.setUuid("");
		}
		else {
			deListViewImpl.setUuid(uuid);
		}

		deListViewImpl.setDeListViewId(deListViewId);
		deListViewImpl.setGroupId(groupId);
		deListViewImpl.setCompanyId(companyId);
		deListViewImpl.setUserId(userId);

		if (userName == null) {
			deListViewImpl.setUserName("");
		}
		else {
			deListViewImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			deListViewImpl.setCreateDate(null);
		}
		else {
			deListViewImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			deListViewImpl.setModifiedDate(null);
		}
		else {
			deListViewImpl.setModifiedDate(new Date(modifiedDate));
		}

		deListViewImpl.setDDMStructureId(DDMStructureId);

		if (name == null) {
			deListViewImpl.setName("");
		}
		else {
			deListViewImpl.setName(name);
		}

		deListViewImpl.setDeRecordQueryId(deRecordQueryId);

		deListViewImpl.resetOriginalValues();

		return deListViewImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		deListViewId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		DDMStructureId = objectInput.readLong();
		name = objectInput.readUTF();

		deRecordQueryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(deListViewId);

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

		objectOutput.writeLong(DDMStructureId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(deRecordQueryId);
	}

	public String uuid;
	public long deListViewId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long DDMStructureId;
	public String name;
	public long deRecordQueryId;

}