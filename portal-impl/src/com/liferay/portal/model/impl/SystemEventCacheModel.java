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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.SystemEvent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SystemEvent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SystemEventCacheModel
	implements CacheModel<SystemEvent>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SystemEventCacheModel)) {
			return false;
		}

		SystemEventCacheModel systemEventCacheModel =
			(SystemEventCacheModel)obj;

		if ((systemEventId == systemEventCacheModel.systemEventId) &&
			(mvccVersion == systemEventCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, systemEventId);

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
		sb.append(", systemEventId=");
		sb.append(systemEventId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", classUuid=");
		sb.append(classUuid);
		sb.append(", referrerClassNameId=");
		sb.append(referrerClassNameId);
		sb.append(", parentSystemEventId=");
		sb.append(parentSystemEventId);
		sb.append(", systemEventSetKey=");
		sb.append(systemEventSetKey);
		sb.append(", type=");
		sb.append(type);
		sb.append(", extraData=");
		sb.append(extraData);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SystemEvent toEntityModel() {
		SystemEventImpl systemEventImpl = new SystemEventImpl();

		systemEventImpl.setMvccVersion(mvccVersion);
		systemEventImpl.setSystemEventId(systemEventId);
		systemEventImpl.setGroupId(groupId);
		systemEventImpl.setCompanyId(companyId);
		systemEventImpl.setUserId(userId);

		if (userName == null) {
			systemEventImpl.setUserName("");
		}
		else {
			systemEventImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			systemEventImpl.setCreateDate(null);
		}
		else {
			systemEventImpl.setCreateDate(new Date(createDate));
		}

		systemEventImpl.setClassNameId(classNameId);
		systemEventImpl.setClassPK(classPK);

		if (classUuid == null) {
			systemEventImpl.setClassUuid("");
		}
		else {
			systemEventImpl.setClassUuid(classUuid);
		}

		systemEventImpl.setReferrerClassNameId(referrerClassNameId);
		systemEventImpl.setParentSystemEventId(parentSystemEventId);
		systemEventImpl.setSystemEventSetKey(systemEventSetKey);
		systemEventImpl.setType(type);

		if (extraData == null) {
			systemEventImpl.setExtraData("");
		}
		else {
			systemEventImpl.setExtraData(extraData);
		}

		systemEventImpl.resetOriginalValues();

		return systemEventImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		systemEventId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		classUuid = objectInput.readUTF();

		referrerClassNameId = objectInput.readLong();

		parentSystemEventId = objectInput.readLong();

		systemEventSetKey = objectInput.readLong();

		type = objectInput.readInt();
		extraData = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(systemEventId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (classUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(classUuid);
		}

		objectOutput.writeLong(referrerClassNameId);

		objectOutput.writeLong(parentSystemEventId);

		objectOutput.writeLong(systemEventSetKey);

		objectOutput.writeInt(type);

		if (extraData == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(extraData);
		}
	}

	public long mvccVersion;
	public long systemEventId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long classNameId;
	public long classPK;
	public String classUuid;
	public long referrerClassNameId;
	public long parentSystemEventId;
	public long systemEventSetKey;
	public int type;
	public String extraData;

}