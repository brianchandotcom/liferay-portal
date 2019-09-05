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

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DEDataListView in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DEDataListViewCacheModel
	implements CacheModel<DEDataListView>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataListViewCacheModel)) {
			return false;
		}

		DEDataListViewCacheModel deDataListViewCacheModel =
			(DEDataListViewCacheModel)obj;

		if (deDataListViewId == deDataListViewCacheModel.deDataListViewId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, deDataListViewId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deDataListViewId=");
		sb.append(deDataListViewId);
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
		sb.append(", appliedFilters=");
		sb.append(appliedFilters);
		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);
		sb.append(", fieldNames=");
		sb.append(fieldNames);
		sb.append(", name=");
		sb.append(name);
		sb.append(", sortField=");
		sb.append(sortField);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DEDataListView toEntityModel() {
		DEDataListViewImpl deDataListViewImpl = new DEDataListViewImpl();

		if (uuid == null) {
			deDataListViewImpl.setUuid("");
		}
		else {
			deDataListViewImpl.setUuid(uuid);
		}

		deDataListViewImpl.setDeDataListViewId(deDataListViewId);
		deDataListViewImpl.setGroupId(groupId);
		deDataListViewImpl.setCompanyId(companyId);
		deDataListViewImpl.setUserId(userId);

		if (userName == null) {
			deDataListViewImpl.setUserName("");
		}
		else {
			deDataListViewImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			deDataListViewImpl.setCreateDate(null);
		}
		else {
			deDataListViewImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			deDataListViewImpl.setModifiedDate(null);
		}
		else {
			deDataListViewImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (appliedFilters == null) {
			deDataListViewImpl.setAppliedFilters("");
		}
		else {
			deDataListViewImpl.setAppliedFilters(appliedFilters);
		}

		deDataListViewImpl.setDdmStructureId(ddmStructureId);

		if (fieldNames == null) {
			deDataListViewImpl.setFieldNames("");
		}
		else {
			deDataListViewImpl.setFieldNames(fieldNames);
		}

		if (name == null) {
			deDataListViewImpl.setName("");
		}
		else {
			deDataListViewImpl.setName(name);
		}

		if (sortField == null) {
			deDataListViewImpl.setSortField("");
		}
		else {
			deDataListViewImpl.setSortField(sortField);
		}

		deDataListViewImpl.resetOriginalValues();

		return deDataListViewImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		deDataListViewId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		appliedFilters = objectInput.readUTF();

		ddmStructureId = objectInput.readLong();
		fieldNames = objectInput.readUTF();
		name = objectInput.readUTF();
		sortField = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(deDataListViewId);

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

		if (appliedFilters == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(appliedFilters);
		}

		objectOutput.writeLong(ddmStructureId);

		if (fieldNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fieldNames);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (sortField == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sortField);
		}
	}

	public String uuid;
	public long deDataListViewId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String appliedFilters;
	public long ddmStructureId;
	public String fieldNames;
	public String name;
	public String sortField;

}