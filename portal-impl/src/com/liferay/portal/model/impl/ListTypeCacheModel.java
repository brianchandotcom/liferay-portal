/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ListType in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ListTypeCacheModel
	implements CacheModel<ListType>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ListTypeCacheModel)) {
			return false;
		}

		ListTypeCacheModel listTypeCacheModel = (ListTypeCacheModel)object;

		if ((listTypeId == listTypeCacheModel.listTypeId) &&
			(mvccVersion == listTypeCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, listTypeId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", listTypeId=");
		sb.append(listTypeId);
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
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ListType toEntityModel() {
		ListTypeImpl listTypeImpl = new ListTypeImpl();

		listTypeImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			listTypeImpl.setUuid("");
		}
		else {
			listTypeImpl.setUuid(uuid);
		}

		listTypeImpl.setListTypeId(listTypeId);
		listTypeImpl.setCompanyId(companyId);
		listTypeImpl.setUserId(userId);

		if (userName == null) {
			listTypeImpl.setUserName("");
		}
		else {
			listTypeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			listTypeImpl.setCreateDate(null);
		}
		else {
			listTypeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			listTypeImpl.setModifiedDate(null);
		}
		else {
			listTypeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			listTypeImpl.setName("");
		}
		else {
			listTypeImpl.setName(name);
		}

		if (type == null) {
			listTypeImpl.setType("");
		}
		else {
			listTypeImpl.setType(type);
		}

		listTypeImpl.resetOriginalValues();

		return listTypeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		listTypeId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
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

		objectOutput.writeLong(listTypeId);

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

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long listTypeId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String type;

}