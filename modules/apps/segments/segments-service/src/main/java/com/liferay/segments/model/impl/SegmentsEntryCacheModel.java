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

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;

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
 * @see SegmentsEntry
 * @generated
 */
@ProviderType
public class SegmentsEntryCacheModel implements CacheModel<SegmentsEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SegmentsEntryCacheModel)) {
			return false;
		}

		SegmentsEntryCacheModel segmentsEntryCacheModel = (SegmentsEntryCacheModel)obj;

		if (segmentsEntryId == segmentsEntryCacheModel.segmentsEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, segmentsEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{segmentsEntryId=");
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", key=");
		sb.append(key);
		sb.append(", active=");
		sb.append(active);
		sb.append(", type=");
		sb.append(type);
		sb.append(", criteria=");
		sb.append(criteria);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SegmentsEntry toEntityModel() {
		SegmentsEntryImpl segmentsEntryImpl = new SegmentsEntryImpl();

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

		if (key == null) {
			segmentsEntryImpl.setKey("");
		}
		else {
			segmentsEntryImpl.setKey(key);
		}

		segmentsEntryImpl.setActive(active);

		if (type == null) {
			segmentsEntryImpl.setType("");
		}
		else {
			segmentsEntryImpl.setType(type);
		}

		if (criteria == null) {
			segmentsEntryImpl.setCriteria("");
		}
		else {
			segmentsEntryImpl.setCriteria(criteria);
		}

		segmentsEntryImpl.resetOriginalValues();

		return segmentsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		segmentsEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		key = objectInput.readUTF();

		active = objectInput.readBoolean();
		type = objectInput.readUTF();
		criteria = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
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

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		objectOutput.writeBoolean(active);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (criteria == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(criteria);
		}
	}

	public long segmentsEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String key;
	public boolean active;
	public String type;
	public String criteria;
}