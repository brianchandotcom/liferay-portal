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

package com.liferay.meris.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.meris.model.MerisSegment;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MerisSegment in entity cache.
 *
 * @author Eduardo Garcia
 * @see MerisSegment
 * @generated
 */
@ProviderType
public class MerisSegmentCacheModel implements CacheModel<MerisSegment>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MerisSegmentCacheModel)) {
			return false;
		}

		MerisSegmentCacheModel merisSegmentCacheModel = (MerisSegmentCacheModel)obj;

		if (merisSegmentId == merisSegmentCacheModel.merisSegmentId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, merisSegmentId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{merisSegmentId=");
		sb.append(merisSegmentId);
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
	public MerisSegment toEntityModel() {
		MerisSegmentImpl merisSegmentImpl = new MerisSegmentImpl();

		merisSegmentImpl.setMerisSegmentId(merisSegmentId);
		merisSegmentImpl.setGroupId(groupId);
		merisSegmentImpl.setCompanyId(companyId);
		merisSegmentImpl.setUserId(userId);

		if (userName == null) {
			merisSegmentImpl.setUserName("");
		}
		else {
			merisSegmentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			merisSegmentImpl.setCreateDate(null);
		}
		else {
			merisSegmentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			merisSegmentImpl.setModifiedDate(null);
		}
		else {
			merisSegmentImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			merisSegmentImpl.setName("");
		}
		else {
			merisSegmentImpl.setName(name);
		}

		if (description == null) {
			merisSegmentImpl.setDescription("");
		}
		else {
			merisSegmentImpl.setDescription(description);
		}

		if (key == null) {
			merisSegmentImpl.setKey("");
		}
		else {
			merisSegmentImpl.setKey(key);
		}

		merisSegmentImpl.setActive(active);

		if (type == null) {
			merisSegmentImpl.setType("");
		}
		else {
			merisSegmentImpl.setType(type);
		}

		if (criteria == null) {
			merisSegmentImpl.setCriteria("");
		}
		else {
			merisSegmentImpl.setCriteria(criteria);
		}

		merisSegmentImpl.resetOriginalValues();

		return merisSegmentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		merisSegmentId = objectInput.readLong();

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
		objectOutput.writeLong(merisSegmentId);

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

	public long merisSegmentId;
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