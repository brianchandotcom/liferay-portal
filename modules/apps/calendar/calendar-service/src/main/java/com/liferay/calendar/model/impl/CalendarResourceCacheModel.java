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

import com.liferay.calendar.model.CalendarResource;
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
 * The cache model class for representing CalendarResource in entity cache.
 *
 * @author Eduardo Lundgren
 * @generated
 */
public class CalendarResourceCacheModel
	implements CacheModel<CalendarResource>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CalendarResourceCacheModel)) {
			return false;
		}

		CalendarResourceCacheModel calendarResourceCacheModel =
			(CalendarResourceCacheModel)obj;

		if ((calendarResourceId ==
				calendarResourceCacheModel.calendarResourceId) &&
			(mvccVersion == calendarResourceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, calendarResourceId);

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
		sb.append(", calendarResourceId=");
		sb.append(calendarResourceId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", classUuid=");
		sb.append(classUuid);
		sb.append(", code=");
		sb.append(code);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", active=");
		sb.append(active);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CalendarResource toEntityModel() {
		CalendarResourceImpl calendarResourceImpl = new CalendarResourceImpl();

		calendarResourceImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			calendarResourceImpl.setUuid("");
		}
		else {
			calendarResourceImpl.setUuid(uuid);
		}

		calendarResourceImpl.setCalendarResourceId(calendarResourceId);
		calendarResourceImpl.setGroupId(groupId);
		calendarResourceImpl.setCompanyId(companyId);
		calendarResourceImpl.setUserId(userId);

		if (userName == null) {
			calendarResourceImpl.setUserName("");
		}
		else {
			calendarResourceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			calendarResourceImpl.setCreateDate(null);
		}
		else {
			calendarResourceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			calendarResourceImpl.setModifiedDate(null);
		}
		else {
			calendarResourceImpl.setModifiedDate(new Date(modifiedDate));
		}

		calendarResourceImpl.setClassNameId(classNameId);
		calendarResourceImpl.setClassPK(classPK);

		if (classUuid == null) {
			calendarResourceImpl.setClassUuid("");
		}
		else {
			calendarResourceImpl.setClassUuid(classUuid);
		}

		if (code == null) {
			calendarResourceImpl.setCode("");
		}
		else {
			calendarResourceImpl.setCode(code);
		}

		if (name == null) {
			calendarResourceImpl.setName("");
		}
		else {
			calendarResourceImpl.setName(name);
		}

		if (description == null) {
			calendarResourceImpl.setDescription("");
		}
		else {
			calendarResourceImpl.setDescription(description);
		}

		calendarResourceImpl.setActive(active);

		if (lastPublishDate == Long.MIN_VALUE) {
			calendarResourceImpl.setLastPublishDate(null);
		}
		else {
			calendarResourceImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		calendarResourceImpl.resetOriginalValues();

		return calendarResourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		calendarResourceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		classUuid = objectInput.readUTF();
		code = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		active = objectInput.readBoolean();
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

		objectOutput.writeLong(calendarResourceId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (classUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(classUuid);
		}

		if (code == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(code);
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
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long calendarResourceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String classUuid;
	public String code;
	public String name;
	public String description;
	public boolean active;
	public long lastPublishDate;

}