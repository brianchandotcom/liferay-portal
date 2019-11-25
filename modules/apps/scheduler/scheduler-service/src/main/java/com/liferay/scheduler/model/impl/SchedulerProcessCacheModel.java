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

package com.liferay.scheduler.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.scheduler.model.SchedulerProcess;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SchedulerProcess in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class SchedulerProcessCacheModel
	implements CacheModel<SchedulerProcess>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SchedulerProcessCacheModel)) {
			return false;
		}

		SchedulerProcessCacheModel schedulerProcessCacheModel =
			(SchedulerProcessCacheModel)obj;

		if ((schedulerProcessId ==
				schedulerProcessCacheModel.schedulerProcessId) &&
			(mvccVersion == schedulerProcessCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, schedulerProcessId);

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
		sb.append(", schedulerProcessId=");
		sb.append(schedulerProcessId);
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
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", system=");
		sb.append(system);
		sb.append(", active=");
		sb.append(active);
		sb.append(", cronExpression=");
		sb.append(cronExpression);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SchedulerProcess toEntityModel() {
		SchedulerProcessImpl schedulerProcessImpl = new SchedulerProcessImpl();

		schedulerProcessImpl.setMvccVersion(mvccVersion);
		schedulerProcessImpl.setSchedulerProcessId(schedulerProcessId);
		schedulerProcessImpl.setCompanyId(companyId);
		schedulerProcessImpl.setUserId(userId);

		if (userName == null) {
			schedulerProcessImpl.setUserName("");
		}
		else {
			schedulerProcessImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			schedulerProcessImpl.setCreateDate(null);
		}
		else {
			schedulerProcessImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			schedulerProcessImpl.setModifiedDate(null);
		}
		else {
			schedulerProcessImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			schedulerProcessImpl.setName("");
		}
		else {
			schedulerProcessImpl.setName(name);
		}

		if (type == null) {
			schedulerProcessImpl.setType("");
		}
		else {
			schedulerProcessImpl.setType(type);
		}

		if (typeSettings == null) {
			schedulerProcessImpl.setTypeSettings("");
		}
		else {
			schedulerProcessImpl.setTypeSettings(typeSettings);
		}

		schedulerProcessImpl.setSystem(system);
		schedulerProcessImpl.setActive(active);

		if (cronExpression == null) {
			schedulerProcessImpl.setCronExpression("");
		}
		else {
			schedulerProcessImpl.setCronExpression(cronExpression);
		}

		if (startDate == Long.MIN_VALUE) {
			schedulerProcessImpl.setStartDate(null);
		}
		else {
			schedulerProcessImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			schedulerProcessImpl.setEndDate(null);
		}
		else {
			schedulerProcessImpl.setEndDate(new Date(endDate));
		}

		schedulerProcessImpl.resetOriginalValues();

		return schedulerProcessImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		schedulerProcessId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
		typeSettings = objectInput.readUTF();

		system = objectInput.readBoolean();

		active = objectInput.readBoolean();
		cronExpression = objectInput.readUTF();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(schedulerProcessId);

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

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeBoolean(system);

		objectOutput.writeBoolean(active);

		if (cronExpression == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cronExpression);
		}

		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);
	}

	public long mvccVersion;
	public long schedulerProcessId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String type;
	public String typeSettings;
	public boolean system;
	public boolean active;
	public String cronExpression;
	public long startDate;
	public long endDate;

}