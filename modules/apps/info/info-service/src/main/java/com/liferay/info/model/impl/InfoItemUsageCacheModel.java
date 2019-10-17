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

package com.liferay.info.model.impl;

import com.liferay.info.model.InfoItemUsage;
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
 * The cache model class for representing InfoItemUsage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class InfoItemUsageCacheModel
	implements CacheModel<InfoItemUsage>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof InfoItemUsageCacheModel)) {
			return false;
		}

		InfoItemUsageCacheModel infoItemUsageCacheModel =
			(InfoItemUsageCacheModel)obj;

		if ((infoItemUsageId == infoItemUsageCacheModel.infoItemUsageId) &&
			(mvccVersion == infoItemUsageCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, infoItemUsageId);

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
		sb.append(", infoItemUsageId=");
		sb.append(infoItemUsageId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", containerKey=");
		sb.append(containerKey);
		sb.append(", containerType=");
		sb.append(containerType);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", type=");
		sb.append(type);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public InfoItemUsage toEntityModel() {
		InfoItemUsageImpl infoItemUsageImpl = new InfoItemUsageImpl();

		infoItemUsageImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			infoItemUsageImpl.setUuid("");
		}
		else {
			infoItemUsageImpl.setUuid(uuid);
		}

		infoItemUsageImpl.setInfoItemUsageId(infoItemUsageId);
		infoItemUsageImpl.setGroupId(groupId);

		if (createDate == Long.MIN_VALUE) {
			infoItemUsageImpl.setCreateDate(null);
		}
		else {
			infoItemUsageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			infoItemUsageImpl.setModifiedDate(null);
		}
		else {
			infoItemUsageImpl.setModifiedDate(new Date(modifiedDate));
		}

		infoItemUsageImpl.setClassNameId(classNameId);
		infoItemUsageImpl.setClassPK(classPK);

		if (containerKey == null) {
			infoItemUsageImpl.setContainerKey("");
		}
		else {
			infoItemUsageImpl.setContainerKey(containerKey);
		}

		infoItemUsageImpl.setContainerType(containerType);
		infoItemUsageImpl.setPlid(plid);
		infoItemUsageImpl.setType(type);

		if (lastPublishDate == Long.MIN_VALUE) {
			infoItemUsageImpl.setLastPublishDate(null);
		}
		else {
			infoItemUsageImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		infoItemUsageImpl.resetOriginalValues();

		return infoItemUsageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		infoItemUsageId = objectInput.readLong();

		groupId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		containerKey = objectInput.readUTF();

		containerType = objectInput.readLong();

		plid = objectInput.readLong();

		type = objectInput.readInt();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(infoItemUsageId);

		objectOutput.writeLong(groupId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (containerKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(containerKey);
		}

		objectOutput.writeLong(containerType);

		objectOutput.writeLong(plid);

		objectOutput.writeInt(type);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long infoItemUsageId;
	public long groupId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String containerKey;
	public long containerType;
	public long plid;
	public int type;
	public long lastPublishDate;

}