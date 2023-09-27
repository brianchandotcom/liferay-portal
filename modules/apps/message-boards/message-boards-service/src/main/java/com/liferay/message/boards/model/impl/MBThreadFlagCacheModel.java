/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBThreadFlag;
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
 * The cache model class for representing MBThreadFlag in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBThreadFlagCacheModel
	implements CacheModel<MBThreadFlag>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MBThreadFlagCacheModel)) {
			return false;
		}

		MBThreadFlagCacheModel mbThreadFlagCacheModel =
			(MBThreadFlagCacheModel)object;

		if ((threadFlagId == mbThreadFlagCacheModel.threadFlagId) &&
			(mvccVersion == mbThreadFlagCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, threadFlagId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", threadFlagId=");
		sb.append(threadFlagId);
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
		sb.append(", threadId=");
		sb.append(threadId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MBThreadFlag toEntityModel() {
		MBThreadFlagImpl mbThreadFlagImpl = new MBThreadFlagImpl();

		mbThreadFlagImpl.setMvccVersion(mvccVersion);
		mbThreadFlagImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			mbThreadFlagImpl.setUuid("");
		}
		else {
			mbThreadFlagImpl.setUuid(uuid);
		}

		mbThreadFlagImpl.setThreadFlagId(threadFlagId);
		mbThreadFlagImpl.setGroupId(groupId);
		mbThreadFlagImpl.setCompanyId(companyId);
		mbThreadFlagImpl.setUserId(userId);

		if (userName == null) {
			mbThreadFlagImpl.setUserName("");
		}
		else {
			mbThreadFlagImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbThreadFlagImpl.setCreateDate(null);
		}
		else {
			mbThreadFlagImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbThreadFlagImpl.setModifiedDate(null);
		}
		else {
			mbThreadFlagImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbThreadFlagImpl.setThreadId(threadId);

		if (lastPublishDate == Long.MIN_VALUE) {
			mbThreadFlagImpl.setLastPublishDate(null);
		}
		else {
			mbThreadFlagImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		mbThreadFlagImpl.resetOriginalValues();

		return mbThreadFlagImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		threadFlagId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		threadId = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(threadFlagId);

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

		objectOutput.writeLong(threadId);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long threadFlagId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long threadId;
	public long lastPublishDate;

}