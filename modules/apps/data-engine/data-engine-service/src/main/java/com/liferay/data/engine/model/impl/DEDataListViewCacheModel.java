/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.model.impl;

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

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
	implements CacheModel<DEDataListView>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DEDataListViewCacheModel)) {
			return false;
		}

		DEDataListViewCacheModel deDataListViewCacheModel =
			(DEDataListViewCacheModel)object;

		if ((deDataListViewId == deDataListViewCacheModel.deDataListViewId) &&
			(mvccVersion == deDataListViewCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, deDataListViewId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
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

		deDataListViewImpl.setMvccVersion(mvccVersion);
		deDataListViewImpl.setCtCollectionId(ctCollectionId);

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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		deDataListViewId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		appliedFilters = (String)objectInput.readObject();

		ddmStructureId = objectInput.readLong();
		fieldNames = (String)objectInput.readObject();
		name = objectInput.readUTF();
		sortField = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(appliedFilters);
		}

		objectOutput.writeLong(ddmStructureId);

		if (fieldNames == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(fieldNames);
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

	public long mvccVersion;
	public long ctCollectionId;
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