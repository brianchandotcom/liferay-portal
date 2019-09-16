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

package com.liferay.segments.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.segments.model.SegmentsEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SegmentsEntry in entity cache.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsEntryCacheModel
	implements CacheModel<SegmentsEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SegmentsEntryCacheModel)) {
			return false;
		}

		SegmentsEntryCacheModel segmentsEntryCacheModel =
			(SegmentsEntryCacheModel)obj;

		if ((segmentsEntryId == segmentsEntryCacheModel.segmentsEntryId) &&
			(mvccVersion == segmentsEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, segmentsEntryId);

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
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", segmentsEntryId=");
		sb.append(segmentsEntryId);
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
		sb.append(", segmentsEntryKey=");
		sb.append(segmentsEntryKey);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", active=");
		sb.append(active);
		sb.append(", criteria=");
		sb.append(criteria);
		sb.append(", source=");
		sb.append(source);
		sb.append(", type=");
		sb.append(type);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SegmentsEntry toEntityModel() {
		SegmentsEntryImpl segmentsEntryImpl = new SegmentsEntryImpl();

		segmentsEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			segmentsEntryImpl.setUuid("");
		}
		else {
			segmentsEntryImpl.setUuid(uuid);
		}

		segmentsEntryImpl.setSegmentsEntryId(segmentsEntryId);
		segmentsEntryImpl.setGroupId(groupId);
		segmentsEntryImpl.setCompanyId(companyId);
		segmentsEntryImpl.setUserId(userId);

		if (userName == null) {
			segmentsEntryImpl.setUserName("");
		}
		else {
			segmentsEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			segmentsEntryImpl.setCreateDate(null);
		}
		else {
			segmentsEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			segmentsEntryImpl.setModifiedDate(null);
		}
		else {
			segmentsEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (segmentsEntryKey == null) {
			segmentsEntryImpl.setSegmentsEntryKey("");
		}
		else {
			segmentsEntryImpl.setSegmentsEntryKey(segmentsEntryKey);
		}

		if (name == null) {
			segmentsEntryImpl.setName("");
		}
		else {
			segmentsEntryImpl.setName(name);
		}

		if (description == null) {
			segmentsEntryImpl.setDescription("");
		}
		else {
			segmentsEntryImpl.setDescription(description);
		}

		segmentsEntryImpl.setActive(active);

		if (criteria == null) {
			segmentsEntryImpl.setCriteria("");
		}
		else {
			segmentsEntryImpl.setCriteria(criteria);
		}

		if (source == null) {
			segmentsEntryImpl.setSource("");
		}
		else {
			segmentsEntryImpl.setSource(source);
		}

		if (type == null) {
			segmentsEntryImpl.setType("");
		}
		else {
			segmentsEntryImpl.setType(type);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			segmentsEntryImpl.setLastPublishDate(null);
		}
		else {
			segmentsEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		segmentsEntryImpl.resetOriginalValues();

		return segmentsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		segmentsEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		segmentsEntryKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		active = objectInput.readBoolean();
		criteria = objectInput.readUTF();
		source = objectInput.readUTF();
		type = objectInput.readUTF();
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

		objectOutput.writeLong(segmentsEntryId);

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

		if (segmentsEntryKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(segmentsEntryKey);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeBoolean(active);

		if (criteria == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(criteria);
		}

		if (source == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(source);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long segmentsEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String segmentsEntryKey;
	public String name;
	public String description;
	public boolean active;
	public String criteria;
	public String source;
	public String type;
	public long lastPublishDate;

}