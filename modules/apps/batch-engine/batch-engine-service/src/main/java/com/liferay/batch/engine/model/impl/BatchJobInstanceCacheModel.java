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

import com.liferay.batch.engine.model.BatchJobInstance;
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
 * The cache model class for representing BatchJobInstance in entity cache.
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class BatchJobInstanceCacheModel
	implements CacheModel<BatchJobInstance>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BatchJobInstanceCacheModel)) {
			return false;
		}

		BatchJobInstanceCacheModel batchJobInstanceCacheModel =
			(BatchJobInstanceCacheModel)obj;

		if (batchJobInstanceId ==
				batchJobInstanceCacheModel.batchJobInstanceId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, batchJobInstanceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", batchJobInstanceId=");
		sb.append(batchJobInstanceId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", jobName=");
		sb.append(jobName);
		sb.append(", jobKey=");
		sb.append(jobKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchJobInstance toEntityModel() {
		BatchJobInstanceImpl batchJobInstanceImpl = new BatchJobInstanceImpl();

		if (uuid == null) {
			batchJobInstanceImpl.setUuid("");
		}
		else {
			batchJobInstanceImpl.setUuid(uuid);
		}

		batchJobInstanceImpl.setBatchJobInstanceId(batchJobInstanceId);
		batchJobInstanceImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			batchJobInstanceImpl.setCreateDate(null);
		}
		else {
			batchJobInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchJobInstanceImpl.setModifiedDate(null);
		}
		else {
			batchJobInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (jobName == null) {
			batchJobInstanceImpl.setJobName("");
		}
		else {
			batchJobInstanceImpl.setJobName(jobName);
		}

		if (jobKey == null) {
			batchJobInstanceImpl.setJobKey("");
		}
		else {
			batchJobInstanceImpl.setJobKey(jobKey);
		}

		batchJobInstanceImpl.resetOriginalValues();

		return batchJobInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		batchJobInstanceId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		jobName = objectInput.readUTF();
		jobKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(batchJobInstanceId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (jobName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(jobName);
		}

		if (jobKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(jobKey);
		}
	}

	public String uuid;
	public long batchJobInstanceId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public String jobName;
	public String jobKey;

}