/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectStateFlow;
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
 * The cache model class for representing ObjectStateFlow in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectStateFlowCacheModel
	implements CacheModel<ObjectStateFlow>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectStateFlowCacheModel)) {
			return false;
		}

		ObjectStateFlowCacheModel objectStateFlowCacheModel =
			(ObjectStateFlowCacheModel)object;

		if ((objectStateFlowId ==
				objectStateFlowCacheModel.objectStateFlowId) &&
			(mvccVersion == objectStateFlowCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectStateFlowId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectStateFlowId=");
		sb.append(objectStateFlowId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectStateFlow toEntityModel() {
		ObjectStateFlowImpl objectStateFlowImpl = new ObjectStateFlowImpl();

		objectStateFlowImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectStateFlowImpl.setUuid("");
		}
		else {
			objectStateFlowImpl.setUuid(uuid);
		}

		objectStateFlowImpl.setObjectStateFlowId(objectStateFlowId);
		objectStateFlowImpl.setCompanyId(companyId);
		objectStateFlowImpl.setUserId(userId);

		if (userName == null) {
			objectStateFlowImpl.setUserName("");
		}
		else {
			objectStateFlowImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectStateFlowImpl.setCreateDate(null);
		}
		else {
			objectStateFlowImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectStateFlowImpl.setModifiedDate(null);
		}
		else {
			objectStateFlowImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectStateFlowImpl.setObjectFieldId(objectFieldId);

		objectStateFlowImpl.resetOriginalValues();

		return objectStateFlowImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectStateFlowId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectFieldId = objectInput.readLong();
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

		objectOutput.writeLong(objectStateFlowId);

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
	}

	public long mvccVersion;
	public String uuid;
	public long objectStateFlowId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectFieldId;

}