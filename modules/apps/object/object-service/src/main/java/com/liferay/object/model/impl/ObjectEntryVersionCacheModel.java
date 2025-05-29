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
		StringBundler sb = new StringBundler(39);

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
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", reviewDate=");
		sb.append(reviewDate);
		sb.append(", version=");
		sb.append(version);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
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

		if (displayDate == Long.MIN_VALUE) {
			objectEntryVersionImpl.setDisplayDate(null);
		}
		else {
			objectEntryVersionImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			objectEntryVersionImpl.setExpirationDate(null);
		}
		else {
			objectEntryVersionImpl.setExpirationDate(new Date(expirationDate));
		}

		if (reviewDate == Long.MIN_VALUE) {
			objectEntryVersionImpl.setReviewDate(null);
		}
		else {
			objectEntryVersionImpl.setReviewDate(new Date(reviewDate));
		}

		objectEntryVersionImpl.setVersion(version);
		objectEntryVersionImpl.setStatus(status);
		objectEntryVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			objectEntryVersionImpl.setStatusByUserName("");
		}
		else {
			objectEntryVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			objectEntryVersionImpl.setStatusDate(null);
		}
		else {
			objectEntryVersionImpl.setStatusDate(new Date(statusDate));
		}

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
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		reviewDate = objectInput.readLong();

		version = objectInput.readInt();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(reviewDate);

		objectOutput.writeInt(version);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
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
	public long displayDate;
	public long expirationDate;
	public long reviewDate;
	public int version;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}