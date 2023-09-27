/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectState;
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
 * The cache model class for representing ObjectState in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectStateCacheModel
	implements CacheModel<ObjectState>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectStateCacheModel)) {
			return false;
		}

		ObjectStateCacheModel objectStateCacheModel =
			(ObjectStateCacheModel)object;

		if ((objectStateId == objectStateCacheModel.objectStateId) &&
			(mvccVersion == objectStateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectStateId);

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
		sb.append(", objectStateId=");
		sb.append(objectStateId);
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
		sb.append(", listTypeEntryId=");
		sb.append(listTypeEntryId);
		sb.append(", objectStateFlowId=");
		sb.append(objectStateFlowId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectState toEntityModel() {
		ObjectStateImpl objectStateImpl = new ObjectStateImpl();

		objectStateImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectStateImpl.setUuid("");
		}
		else {
			objectStateImpl.setUuid(uuid);
		}

		objectStateImpl.setObjectStateId(objectStateId);
		objectStateImpl.setCompanyId(companyId);
		objectStateImpl.setUserId(userId);

		if (userName == null) {
			objectStateImpl.setUserName("");
		}
		else {
			objectStateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectStateImpl.setCreateDate(null);
		}
		else {
			objectStateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectStateImpl.setModifiedDate(null);
		}
		else {
			objectStateImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectStateImpl.setListTypeEntryId(listTypeEntryId);
		objectStateImpl.setObjectStateFlowId(objectStateFlowId);

		objectStateImpl.resetOriginalValues();

		return objectStateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectStateId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		listTypeEntryId = objectInput.readLong();

		objectStateFlowId = objectInput.readLong();
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

		objectOutput.writeLong(objectStateId);

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

		objectOutput.writeLong(listTypeEntryId);

		objectOutput.writeLong(objectStateFlowId);
	}

	public long mvccVersion;
	public String uuid;
	public long objectStateId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long listTypeEntryId;
	public long objectStateFlowId;

}