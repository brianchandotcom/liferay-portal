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

import com.liferay.batch.engine.model.BatchTask;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing BatchTask in entity cache.
 *
 * @author Shuyang Zhou
 * @generated
 */
@ProviderType
public class BatchTaskCacheModel
	implements CacheModel<BatchTask>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BatchTaskCacheModel)) {
			return false;
		}

		BatchTaskCacheModel batchTaskCacheModel = (BatchTaskCacheModel)obj;

		if ((batchTaskId == batchTaskCacheModel.batchTaskId) &&
			(mvccVersion == batchTaskCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchTaskId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", batchTaskId=");
		sb.append(batchTaskId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", className=");
		sb.append(className);
		sb.append(", version=");
		sb.append(version);
		sb.append(", contentType=");
		sb.append(contentType);
		sb.append(", operation=");
		sb.append(operation);
		sb.append(", batchSize=");
		sb.append(batchSize);
		sb.append(", startTime=");
		sb.append(startTime);
		sb.append(", endTime=");
		sb.append(endTime);
		sb.append(", status=");
		sb.append(status);
		sb.append(", errorMessage=");
		sb.append(errorMessage);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchTask toEntityModel() {
		BatchTaskImpl batchTaskImpl = new BatchTaskImpl();

		batchTaskImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			batchTaskImpl.setUuid("");
		}
		else {
			batchTaskImpl.setUuid(uuid);
		}

		batchTaskImpl.setBatchTaskId(batchTaskId);
		batchTaskImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			batchTaskImpl.setCreateDate(null);
		}
		else {
			batchTaskImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchTaskImpl.setModifiedDate(null);
		}
		else {
			batchTaskImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (className == null) {
			batchTaskImpl.setClassName("");
		}
		else {
			batchTaskImpl.setClassName(className);
		}

		if (version == null) {
			batchTaskImpl.setVersion("");
		}
		else {
			batchTaskImpl.setVersion(version);
		}

		if (contentType == null) {
			batchTaskImpl.setContentType("");
		}
		else {
			batchTaskImpl.setContentType(contentType);
		}

		if (operation == null) {
			batchTaskImpl.setOperation("");
		}
		else {
			batchTaskImpl.setOperation(operation);
		}

		batchTaskImpl.setBatchSize(batchSize);

		if (startTime == Long.MIN_VALUE) {
			batchTaskImpl.setStartTime(null);
		}
		else {
			batchTaskImpl.setStartTime(new Date(startTime));
		}

		if (endTime == Long.MIN_VALUE) {
			batchTaskImpl.setEndTime(null);
		}
		else {
			batchTaskImpl.setEndTime(new Date(endTime));
		}

		if (status == null) {
			batchTaskImpl.setStatus("");
		}
		else {
			batchTaskImpl.setStatus(status);
		}

		if (errorMessage == null) {
			batchTaskImpl.setErrorMessage("");
		}
		else {
			batchTaskImpl.setErrorMessage(errorMessage);
		}

		batchTaskImpl.resetOriginalValues();

		return batchTaskImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		batchTaskId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		className = objectInput.readUTF();
		version = objectInput.readUTF();
		contentType = objectInput.readUTF();
		operation = objectInput.readUTF();

		batchSize = objectInput.readLong();
		startTime = objectInput.readLong();
		endTime = objectInput.readLong();
		status = objectInput.readUTF();
		errorMessage = objectInput.readUTF();
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

		objectOutput.writeLong(batchTaskId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (className == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(className);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		if (contentType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(contentType);
		}

		if (operation == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(operation);
		}

		objectOutput.writeLong(batchSize);
		objectOutput.writeLong(startTime);
		objectOutput.writeLong(endTime);

		if (status == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(status);
		}

		if (errorMessage == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(errorMessage);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long batchTaskId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public String className;
	public String version;
	public String contentType;
	public String operation;
	public long batchSize;
	public long startTime;
	public long endTime;
	public String status;
	public String errorMessage;

}