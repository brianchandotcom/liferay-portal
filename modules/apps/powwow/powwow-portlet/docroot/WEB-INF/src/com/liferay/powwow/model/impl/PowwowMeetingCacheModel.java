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

import com.liferay.powwow.model.PowwowMeeting;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PowwowMeeting in entity cache.
 *
 * @author Shinn Lok
 * @see PowwowMeeting
 * @generated
 */
@ProviderType
public class PowwowMeetingCacheModel implements CacheModel<PowwowMeeting>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PowwowMeetingCacheModel)) {
			return false;
		}

		PowwowMeetingCacheModel powwowMeetingCacheModel = (PowwowMeetingCacheModel)obj;

		if (powwowMeetingId == powwowMeetingCacheModel.powwowMeetingId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, powwowMeetingId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{powwowMeetingId=");
		sb.append(powwowMeetingId);
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
		sb.append(", powwowServerId=");
		sb.append(powwowServerId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", providerType=");
		sb.append(providerType);
		sb.append(", providerTypeMetadata=");
		sb.append(providerTypeMetadata);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", calendarBookingId=");
		sb.append(calendarBookingId);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PowwowMeeting toEntityModel() {
		PowwowMeetingImpl powwowMeetingImpl = new PowwowMeetingImpl();

		powwowMeetingImpl.setPowwowMeetingId(powwowMeetingId);
		powwowMeetingImpl.setGroupId(groupId);
		powwowMeetingImpl.setCompanyId(companyId);
		powwowMeetingImpl.setUserId(userId);

		if (userName == null) {
			powwowMeetingImpl.setUserName(StringPool.BLANK);
		}
		else {
			powwowMeetingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			powwowMeetingImpl.setCreateDate(null);
		}
		else {
			powwowMeetingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			powwowMeetingImpl.setModifiedDate(null);
		}
		else {
			powwowMeetingImpl.setModifiedDate(new Date(modifiedDate));
		}

		powwowMeetingImpl.setPowwowServerId(powwowServerId);

		if (name == null) {
			powwowMeetingImpl.setName(StringPool.BLANK);
		}
		else {
			powwowMeetingImpl.setName(name);
		}

		if (description == null) {
			powwowMeetingImpl.setDescription(StringPool.BLANK);
		}
		else {
			powwowMeetingImpl.setDescription(description);
		}

		if (providerType == null) {
			powwowMeetingImpl.setProviderType(StringPool.BLANK);
		}
		else {
			powwowMeetingImpl.setProviderType(providerType);
		}

		if (providerTypeMetadata == null) {
			powwowMeetingImpl.setProviderTypeMetadata(StringPool.BLANK);
		}
		else {
			powwowMeetingImpl.setProviderTypeMetadata(providerTypeMetadata);
		}

		if (languageId == null) {
			powwowMeetingImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			powwowMeetingImpl.setLanguageId(languageId);
		}

		powwowMeetingImpl.setCalendarBookingId(calendarBookingId);
		powwowMeetingImpl.setStatus(status);

		powwowMeetingImpl.resetOriginalValues();

		return powwowMeetingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		powwowMeetingId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		powwowServerId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		providerType = objectInput.readUTF();
		providerTypeMetadata = objectInput.readUTF();
		languageId = objectInput.readUTF();

		calendarBookingId = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(powwowMeetingId);

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

		objectOutput.writeLong(powwowServerId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (providerType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(providerType);
		}

		if (providerTypeMetadata == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(providerTypeMetadata);
		}

		if (languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		objectOutput.writeLong(calendarBookingId);

		objectOutput.writeInt(status);
	}

	public long powwowMeetingId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long powwowServerId;
	public String name;
	public String description;
	public String providerType;
	public String providerTypeMetadata;
	public String languageId;
	public long calendarBookingId;
	public int status;
}