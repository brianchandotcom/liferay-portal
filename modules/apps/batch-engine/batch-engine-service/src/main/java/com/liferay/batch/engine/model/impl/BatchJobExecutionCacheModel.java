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

package com.liferay.batch.engine.model.impl;

import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing BatchJobExecution in entity cache.
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class BatchJobExecutionCacheModel
	implements CacheModel<BatchJobExecution>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BatchJobExecutionCacheModel)) {
			return false;
		}

		BatchJobExecutionCacheModel batchJobExecutionCacheModel =
			(BatchJobExecutionCacheModel)obj;

		if (batchJobExecutionId ==
				batchJobExecutionCacheModel.batchJobExecutionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, batchJobExecutionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", batchJobExecutionId=");
		sb.append(batchJobExecutionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", batchJobInstanceId=");
		sb.append(batchJobInstanceId);
		sb.append(", status=");
		sb.append(status);
		sb.append(", startTime=");
		sb.append(startTime);
		sb.append(", endTime=");
		sb.append(endTime);
		sb.append(", jobSettings=");
		sb.append(jobSettings);
		sb.append(", error=");
		sb.append(error);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchJobExecution toEntityModel() {
		BatchJobExecutionImpl batchJobExecutionImpl =
			new BatchJobExecutionImpl();

		if (uuid == null) {
			batchJobExecutionImpl.setUuid("");
		}
		else {
			batchJobExecutionImpl.setUuid(uuid);
		}

		batchJobExecutionImpl.setBatchJobExecutionId(batchJobExecutionId);
		batchJobExecutionImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			batchJobExecutionImpl.setCreateDate(null);
		}
		else {
			batchJobExecutionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchJobExecutionImpl.setModifiedDate(null);
		}
		else {
			batchJobExecutionImpl.setModifiedDate(new Date(modifiedDate));
		}

		batchJobExecutionImpl.setBatchJobInstanceId(batchJobInstanceId);

		if (status == null) {
			batchJobExecutionImpl.setStatus("");
		}
		else {
			batchJobExecutionImpl.setStatus(status);
		}

		if (startTime == Long.MIN_VALUE) {
			batchJobExecutionImpl.setStartTime(null);
		}
		else {
			batchJobExecutionImpl.setStartTime(new Date(startTime));
		}

		if (endTime == Long.MIN_VALUE) {
			batchJobExecutionImpl.setEndTime(null);
		}
		else {
			batchJobExecutionImpl.setEndTime(new Date(endTime));
		}

		if (jobSettings == null) {
			batchJobExecutionImpl.setJobSettings("");
		}
		else {
			batchJobExecutionImpl.setJobSettings(jobSettings);
		}

		if (error == null) {
			batchJobExecutionImpl.setError("");
		}
		else {
			batchJobExecutionImpl.setError(error);
		}

		batchJobExecutionImpl.resetOriginalValues();

		return batchJobExecutionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		batchJobExecutionId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		batchJobInstanceId = objectInput.readLong();
		status = objectInput.readUTF();
		startTime = objectInput.readLong();
		endTime = objectInput.readLong();
		jobSettings = objectInput.readUTF();
		error = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(batchJobExecutionId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(batchJobInstanceId);

		if (status == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(status);
		}

		objectOutput.writeLong(startTime);
		objectOutput.writeLong(endTime);

		if (jobSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(jobSettings);
		}

		if (error == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(error);
		}
	}

	public String uuid;
	public long batchJobExecutionId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long batchJobInstanceId;
	public String status;
	public long startTime;
	public long endTime;
	public String jobSettings;
	public String error;

}