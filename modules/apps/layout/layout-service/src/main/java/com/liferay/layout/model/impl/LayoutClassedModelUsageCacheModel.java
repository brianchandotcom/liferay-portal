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

package com.liferay.layout.model.impl;

import com.liferay.layout.model.LayoutClassedModelUsage;
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
 * The cache model class for representing LayoutClassedModelUsage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutClassedModelUsageCacheModel
	implements CacheModel<LayoutClassedModelUsage>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutClassedModelUsageCacheModel)) {
			return false;
		}

		LayoutClassedModelUsageCacheModel layoutClassedModelUsageCacheModel =
			(LayoutClassedModelUsageCacheModel)obj;

		if ((layoutClassedModelUsageId ==
				layoutClassedModelUsageCacheModel.layoutClassedModelUsageId) &&
			(mvccVersion == layoutClassedModelUsageCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutClassedModelUsageId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", layoutClassedModelUsageId=");
		sb.append(layoutClassedModelUsageId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
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
	public LayoutClassedModelUsage toEntityModel() {
		LayoutClassedModelUsageImpl layoutClassedModelUsageImpl =
			new LayoutClassedModelUsageImpl();

		layoutClassedModelUsageImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			layoutClassedModelUsageImpl.setUuid("");
		}
		else {
			layoutClassedModelUsageImpl.setUuid(uuid);
		}

		layoutClassedModelUsageImpl.setLayoutClassedModelUsageId(
			layoutClassedModelUsageId);
		layoutClassedModelUsageImpl.setGroupId(groupId);
		layoutClassedModelUsageImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			layoutClassedModelUsageImpl.setCreateDate(null);
		}
		else {
			layoutClassedModelUsageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutClassedModelUsageImpl.setModifiedDate(null);
		}
		else {
			layoutClassedModelUsageImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutClassedModelUsageImpl.setClassNameId(classNameId);
		layoutClassedModelUsageImpl.setClassPK(classPK);

		if (containerKey == null) {
			layoutClassedModelUsageImpl.setContainerKey("");
		}
		else {
			layoutClassedModelUsageImpl.setContainerKey(containerKey);
		}

		layoutClassedModelUsageImpl.setContainerType(containerType);
		layoutClassedModelUsageImpl.setPlid(plid);
		layoutClassedModelUsageImpl.setType(type);

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutClassedModelUsageImpl.setLastPublishDate(null);
		}
		else {
			layoutClassedModelUsageImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		layoutClassedModelUsageImpl.resetOriginalValues();

		return layoutClassedModelUsageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		layoutClassedModelUsageId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
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

		objectOutput.writeLong(layoutClassedModelUsageId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
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
	public long layoutClassedModelUsageId;
	public long groupId;
	public long companyId;
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