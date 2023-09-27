/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectLayoutRow;
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
 * The cache model class for representing ObjectLayoutRow in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectLayoutRowCacheModel
	implements CacheModel<ObjectLayoutRow>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutRowCacheModel)) {
			return false;
		}

		ObjectLayoutRowCacheModel objectLayoutRowCacheModel =
			(ObjectLayoutRowCacheModel)object;

		if ((objectLayoutRowId ==
				objectLayoutRowCacheModel.objectLayoutRowId) &&
			(mvccVersion == objectLayoutRowCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectLayoutRowId);

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
		sb.append(", objectLayoutRowId=");
		sb.append(objectLayoutRowId);
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
		sb.append(", objectLayoutBoxId=");
		sb.append(objectLayoutBoxId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectLayoutRow toEntityModel() {
		ObjectLayoutRowImpl objectLayoutRowImpl = new ObjectLayoutRowImpl();

		objectLayoutRowImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectLayoutRowImpl.setUuid("");
		}
		else {
			objectLayoutRowImpl.setUuid(uuid);
		}

		objectLayoutRowImpl.setObjectLayoutRowId(objectLayoutRowId);
		objectLayoutRowImpl.setCompanyId(companyId);
		objectLayoutRowImpl.setUserId(userId);

		if (userName == null) {
			objectLayoutRowImpl.setUserName("");
		}
		else {
			objectLayoutRowImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectLayoutRowImpl.setCreateDate(null);
		}
		else {
			objectLayoutRowImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectLayoutRowImpl.setModifiedDate(null);
		}
		else {
			objectLayoutRowImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectLayoutRowImpl.setObjectLayoutBoxId(objectLayoutBoxId);
		objectLayoutRowImpl.setPriority(priority);

		objectLayoutRowImpl.resetOriginalValues();

		return objectLayoutRowImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectLayoutRowId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectLayoutBoxId = objectInput.readLong();

		priority = objectInput.readInt();
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

		objectOutput.writeLong(objectLayoutRowId);

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

		objectOutput.writeLong(objectLayoutBoxId);

		objectOutput.writeInt(priority);
	}

	public long mvccVersion;
	public String uuid;
	public long objectLayoutRowId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectLayoutBoxId;
	public int priority;

}