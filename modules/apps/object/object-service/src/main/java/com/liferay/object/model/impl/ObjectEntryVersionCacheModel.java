/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectEntryVersion;
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
 * The cache model class for representing ObjectEntryVersion in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectEntryVersionCacheModel
	implements CacheModel<ObjectEntryVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectEntryVersionCacheModel)) {
			return false;
		}

		ObjectEntryVersionCacheModel objectEntryVersionCacheModel =
			(ObjectEntryVersionCacheModel)object;

		if ((objectEntryVersionId ==
				objectEntryVersionCacheModel.objectEntryVersionId) &&
			(mvccVersion == objectEntryVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectEntryVersionId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectEntryVersionId=");
		sb.append(objectEntryVersionId);
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
		sb.append(", objectDefinitionId=");
		sb.append(objectDefinitionId);
		sb.append(", objectEntryId=");
		sb.append(objectEntryId);
		sb.append(", content=");
		sb.append(content);
		sb.append(", version=");
		sb.append(version);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectEntryVersion toEntityModel() {
		ObjectEntryVersionImpl objectEntryVersionImpl =
			new ObjectEntryVersionImpl();

		objectEntryVersionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectEntryVersionImpl.setUuid("");
		}
		else {
			objectEntryVersionImpl.setUuid(uuid);
		}

		objectEntryVersionImpl.setObjectEntryVersionId(objectEntryVersionId);
		objectEntryVersionImpl.setCompanyId(companyId);
		objectEntryVersionImpl.setUserId(userId);

		if (userName == null) {
			objectEntryVersionImpl.setUserName("");
		}
		else {
			objectEntryVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectEntryVersionImpl.setCreateDate(null);
		}
		else {
			objectEntryVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectEntryVersionImpl.setModifiedDate(null);
		}
		else {
			objectEntryVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectEntryVersionImpl.setObjectDefinitionId(objectDefinitionId);
		objectEntryVersionImpl.setObjectEntryId(objectEntryId);

		if (content == null) {
			objectEntryVersionImpl.setContent("");
		}
		else {
			objectEntryVersionImpl.setContent(content);
		}

		objectEntryVersionImpl.setVersion(version);
		objectEntryVersionImpl.setStatus(status);

		objectEntryVersionImpl.resetOriginalValues();

		return objectEntryVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectEntryVersionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectDefinitionId = objectInput.readLong();

		objectEntryId = objectInput.readLong();
		content = (String)objectInput.readObject();

		version = objectInput.readInt();

		status = objectInput.readInt();
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

		objectOutput.writeLong(objectEntryVersionId);

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

		objectOutput.writeLong(objectDefinitionId);

		objectOutput.writeLong(objectEntryId);

		if (content == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(content);
		}

		objectOutput.writeInt(version);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public String uuid;
	public long objectEntryVersionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectDefinitionId;
	public long objectEntryId;
	public String content;
	public int version;
	public int status;

}