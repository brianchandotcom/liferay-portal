/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectFilter;
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
 * The cache model class for representing ObjectFilter in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectFilterCacheModel
	implements CacheModel<ObjectFilter>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectFilterCacheModel)) {
			return false;
		}

		ObjectFilterCacheModel objectFilterCacheModel =
			(ObjectFilterCacheModel)object;

		if ((objectFilterId == objectFilterCacheModel.objectFilterId) &&
			(mvccVersion == objectFilterCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectFilterId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectFilterId=");
		sb.append(objectFilterId);
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
		sb.append(", objectFieldId=");
		sb.append(objectFieldId);
		sb.append(", filterBy=");
		sb.append(filterBy);
		sb.append(", filterType=");
		sb.append(filterType);
		sb.append(", json=");
		sb.append(json);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectFilter toEntityModel() {
		ObjectFilterImpl objectFilterImpl = new ObjectFilterImpl();

		objectFilterImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectFilterImpl.setUuid("");
		}
		else {
			objectFilterImpl.setUuid(uuid);
		}

		objectFilterImpl.setObjectFilterId(objectFilterId);
		objectFilterImpl.setCompanyId(companyId);
		objectFilterImpl.setUserId(userId);

		if (userName == null) {
			objectFilterImpl.setUserName("");
		}
		else {
			objectFilterImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectFilterImpl.setCreateDate(null);
		}
		else {
			objectFilterImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectFilterImpl.setModifiedDate(null);
		}
		else {
			objectFilterImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectFilterImpl.setObjectFieldId(objectFieldId);

		if (filterBy == null) {
			objectFilterImpl.setFilterBy("");
		}
		else {
			objectFilterImpl.setFilterBy(filterBy);
		}

		if (filterType == null) {
			objectFilterImpl.setFilterType("");
		}
		else {
			objectFilterImpl.setFilterType(filterType);
		}

		if (json == null) {
			objectFilterImpl.setJSON("");
		}
		else {
			objectFilterImpl.setJSON(json);
		}

		objectFilterImpl.resetOriginalValues();

		return objectFilterImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectFilterId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectFieldId = objectInput.readLong();
		filterBy = objectInput.readUTF();
		filterType = objectInput.readUTF();
		json = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(objectFilterId);

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

		objectOutput.writeLong(objectFieldId);

		if (filterBy == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(filterBy);
		}

		if (filterType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(filterType);
		}

		if (json == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(json);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long objectFilterId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectFieldId;
	public String filterBy;
	public String filterType;
	public String json;

}