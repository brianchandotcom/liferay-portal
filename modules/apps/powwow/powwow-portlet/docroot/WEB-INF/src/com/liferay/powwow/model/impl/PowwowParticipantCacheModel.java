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

package com.liferay.powwow.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.powwow.model.PowwowParticipant;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PowwowParticipant in entity cache.
 *
 * @author Shinn Lok
 * @see PowwowParticipant
 * @generated
 */
@ProviderType
public class PowwowParticipantCacheModel implements CacheModel<PowwowParticipant>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PowwowParticipantCacheModel)) {
			return false;
		}

		PowwowParticipantCacheModel powwowParticipantCacheModel = (PowwowParticipantCacheModel)obj;

		if (powwowParticipantId == powwowParticipantCacheModel.powwowParticipantId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, powwowParticipantId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{powwowParticipantId=");
		sb.append(powwowParticipantId);
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
		sb.append(", powwowMeetingId=");
		sb.append(powwowMeetingId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", participantUserId=");
		sb.append(participantUserId);
		sb.append(", emailAddress=");
		sb.append(emailAddress);
		sb.append(", type=");
		sb.append(type);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PowwowParticipant toEntityModel() {
		PowwowParticipantImpl powwowParticipantImpl = new PowwowParticipantImpl();

		powwowParticipantImpl.setPowwowParticipantId(powwowParticipantId);
		powwowParticipantImpl.setGroupId(groupId);
		powwowParticipantImpl.setCompanyId(companyId);
		powwowParticipantImpl.setUserId(userId);

		if (userName == null) {
			powwowParticipantImpl.setUserName(StringPool.BLANK);
		}
		else {
			powwowParticipantImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			powwowParticipantImpl.setCreateDate(null);
		}
		else {
			powwowParticipantImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			powwowParticipantImpl.setModifiedDate(null);
		}
		else {
			powwowParticipantImpl.setModifiedDate(new Date(modifiedDate));
		}

		powwowParticipantImpl.setPowwowMeetingId(powwowMeetingId);

		if (name == null) {
			powwowParticipantImpl.setName(StringPool.BLANK);
		}
		else {
			powwowParticipantImpl.setName(name);
		}

		powwowParticipantImpl.setParticipantUserId(participantUserId);

		if (emailAddress == null) {
			powwowParticipantImpl.setEmailAddress(StringPool.BLANK);
		}
		else {
			powwowParticipantImpl.setEmailAddress(emailAddress);
		}

		powwowParticipantImpl.setType(type);
		powwowParticipantImpl.setStatus(status);

		powwowParticipantImpl.resetOriginalValues();

		return powwowParticipantImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		powwowParticipantId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		powwowMeetingId = objectInput.readLong();
		name = objectInput.readUTF();

		participantUserId = objectInput.readLong();
		emailAddress = objectInput.readUTF();

		type = objectInput.readInt();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(powwowParticipantId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(powwowMeetingId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(participantUserId);

		if (emailAddress == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(emailAddress);
		}

		objectOutput.writeInt(type);

		objectOutput.writeInt(status);
	}

	public long powwowParticipantId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long powwowMeetingId;
	public String name;
	public long participantUserId;
	public String emailAddress;
	public int type;
	public int status;
}