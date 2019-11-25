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
import com.liferay.scheduler.model.SchedulerProcessLog;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SchedulerProcessLog in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class SchedulerProcessLogCacheModel
	implements CacheModel<SchedulerProcessLog>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SchedulerProcessLogCacheModel)) {
			return false;
		}

		SchedulerProcessLogCacheModel schedulerProcessLogCacheModel =
			(SchedulerProcessLogCacheModel)obj;

		if ((schedulerProcessLogId ==
				schedulerProcessLogCacheModel.schedulerProcessLogId) &&
			(mvccVersion == schedulerProcessLogCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, schedulerProcessLogId);

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
		sb.append(", schedulerProcessLogId=");
		sb.append(schedulerProcessLogId);
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
		sb.append(", schedulerProcessId=");
		sb.append(schedulerProcessId);
		sb.append(", error=");
		sb.append(error);
		sb.append(", output=");
		sb.append(output);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SchedulerProcessLog toEntityModel() {
		SchedulerProcessLogImpl schedulerProcessLogImpl =
			new SchedulerProcessLogImpl();

		schedulerProcessLogImpl.setMvccVersion(mvccVersion);
		schedulerProcessLogImpl.setSchedulerProcessLogId(schedulerProcessLogId);
		schedulerProcessLogImpl.setCompanyId(companyId);
		schedulerProcessLogImpl.setUserId(userId);

		if (userName == null) {
			schedulerProcessLogImpl.setUserName("");
		}
		else {
			schedulerProcessLogImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			schedulerProcessLogImpl.setCreateDate(null);
		}
		else {
			schedulerProcessLogImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			schedulerProcessLogImpl.setModifiedDate(null);
		}
		else {
			schedulerProcessLogImpl.setModifiedDate(new Date(modifiedDate));
		}

		schedulerProcessLogImpl.setSchedulerProcessId(schedulerProcessId);

		if (error == null) {
			schedulerProcessLogImpl.setError("");
		}
		else {
			schedulerProcessLogImpl.setError(error);
		}

		if (output == null) {
			schedulerProcessLogImpl.setOutput("");
		}
		else {
			schedulerProcessLogImpl.setOutput(output);
		}

		if (startDate == Long.MIN_VALUE) {
			schedulerProcessLogImpl.setStartDate(null);
		}
		else {
			schedulerProcessLogImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			schedulerProcessLogImpl.setEndDate(null);
		}
		else {
			schedulerProcessLogImpl.setEndDate(new Date(endDate));
		}

		schedulerProcessLogImpl.setStatus(status);

		schedulerProcessLogImpl.resetOriginalValues();

		return schedulerProcessLogImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		schedulerProcessLogId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		schedulerProcessId = objectInput.readLong();
		error = objectInput.readUTF();
		output = objectInput.readUTF();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(schedulerProcessLogId);

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

		objectOutput.writeLong(schedulerProcessId);

		if (error == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(error);
		}

		if (output == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(output);
		}

		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long schedulerProcessLogId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long schedulerProcessId;
	public String error;
	public String output;
	public long startDate;
	public long endDate;
	public int status;

}