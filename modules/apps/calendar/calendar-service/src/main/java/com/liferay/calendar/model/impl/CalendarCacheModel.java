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

package com.liferay.calendar.model.impl;

import com.liferay.calendar.model.Calendar;
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
 * The cache model class for representing Calendar in entity cache.
 *
 * @author Eduardo Lundgren
 * @generated
 */
public class CalendarCacheModel
	implements CacheModel<Calendar>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CalendarCacheModel)) {
			return false;
		}

		CalendarCacheModel calendarCacheModel = (CalendarCacheModel)obj;

		if ((calendarId == calendarCacheModel.calendarId) &&
			(mvccVersion == calendarCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, calendarId);

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", calendarId=");
		sb.append(calendarId);
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
		sb.append(", calendarResourceId=");
		sb.append(calendarResourceId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", timeZoneId=");
		sb.append(timeZoneId);
		sb.append(", color=");
		sb.append(color);
		sb.append(", defaultCalendar=");
		sb.append(defaultCalendar);
		sb.append(", enableComments=");
		sb.append(enableComments);
		sb.append(", enableRatings=");
		sb.append(enableRatings);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Calendar toEntityModel() {
		CalendarImpl calendarImpl = new CalendarImpl();

		calendarImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			calendarImpl.setUuid("");
		}
		else {
			calendarImpl.setUuid(uuid);
		}

		calendarImpl.setCalendarId(calendarId);
		calendarImpl.setGroupId(groupId);
		calendarImpl.setCompanyId(companyId);
		calendarImpl.setUserId(userId);

		if (userName == null) {
			calendarImpl.setUserName("");
		}
		else {
			calendarImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			calendarImpl.setCreateDate(null);
		}
		else {
			calendarImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			calendarImpl.setModifiedDate(null);
		}
		else {
			calendarImpl.setModifiedDate(new Date(modifiedDate));
		}

		calendarImpl.setCalendarResourceId(calendarResourceId);

		if (name == null) {
			calendarImpl.setName("");
		}
		else {
			calendarImpl.setName(name);
		}

		if (description == null) {
			calendarImpl.setDescription("");
		}
		else {
			calendarImpl.setDescription(description);
		}

		if (timeZoneId == null) {
			calendarImpl.setTimeZoneId("");
		}
		else {
			calendarImpl.setTimeZoneId(timeZoneId);
		}

		calendarImpl.setColor(color);
		calendarImpl.setDefaultCalendar(defaultCalendar);
		calendarImpl.setEnableComments(enableComments);
		calendarImpl.setEnableRatings(enableRatings);

		if (lastPublishDate == Long.MIN_VALUE) {
			calendarImpl.setLastPublishDate(null);
		}
		else {
			calendarImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		calendarImpl.resetOriginalValues();

		return calendarImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		calendarId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		calendarResourceId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		timeZoneId = objectInput.readUTF();

		color = objectInput.readInt();

		defaultCalendar = objectInput.readBoolean();

		enableComments = objectInput.readBoolean();

		enableRatings = objectInput.readBoolean();
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

		objectOutput.writeLong(calendarId);

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

		objectOutput.writeLong(calendarResourceId);

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

		if (timeZoneId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(timeZoneId);
		}

		objectOutput.writeInt(color);

		objectOutput.writeBoolean(defaultCalendar);

		objectOutput.writeBoolean(enableComments);

		objectOutput.writeBoolean(enableRatings);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long calendarId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long calendarResourceId;
	public String name;
	public String description;
	public String timeZoneId;
	public int color;
	public boolean defaultCalendar;
	public boolean enableComments;
	public boolean enableRatings;
	public long lastPublishDate;

}