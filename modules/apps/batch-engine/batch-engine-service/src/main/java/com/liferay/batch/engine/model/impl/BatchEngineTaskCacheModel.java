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

import com.liferay.batch.engine.model.BatchEngineTask;
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
 * The cache model class for representing BatchEngineTask in entity cache.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class BatchEngineTaskCacheModel
	implements CacheModel<BatchEngineTask>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BatchEngineTaskCacheModel)) {
			return false;
		}

		BatchEngineTaskCacheModel batchEngineTaskCacheModel =
			(BatchEngineTaskCacheModel)obj;

		if ((batchEngineTaskId ==
				batchEngineTaskCacheModel.batchEngineTaskId) &&
			(mvccVersion == batchEngineTaskCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchEngineTaskId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", batchEngineTaskId=");
		sb.append(batchEngineTaskId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", batchSize=");
		sb.append(batchSize);
		sb.append(", className=");
		sb.append(className);
		sb.append(", contentType=");
		sb.append(contentType);
		sb.append(", endTime=");
		sb.append(endTime);
		sb.append(", errorMessage=");
		sb.append(errorMessage);
		sb.append(", executeStatus=");
		sb.append(executeStatus);
		sb.append(", operation=");
		sb.append(operation);
		sb.append(", startTime=");
		sb.append(startTime);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchEngineTask toEntityModel() {
		BatchEngineTaskImpl batchEngineTaskImpl = new BatchEngineTaskImpl();

		batchEngineTaskImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			batchEngineTaskImpl.setUuid("");
		}
		else {
			batchEngineTaskImpl.setUuid(uuid);
		}

		batchEngineTaskImpl.setBatchEngineTaskId(batchEngineTaskId);
		batchEngineTaskImpl.setCompanyId(companyId);
		batchEngineTaskImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			batchEngineTaskImpl.setCreateDate(null);
		}
		else {
			batchEngineTaskImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchEngineTaskImpl.setModifiedDate(null);
		}
		else {
			batchEngineTaskImpl.setModifiedDate(new Date(modifiedDate));
		}

		batchEngineTaskImpl.setBatchSize(batchSize);

		if (className == null) {
			batchEngineTaskImpl.setClassName("");
		}
		else {
			batchEngineTaskImpl.setClassName(className);
		}

		if (contentType == null) {
			batchEngineTaskImpl.setContentType("");
		}
		else {
			batchEngineTaskImpl.setContentType(contentType);
		}

		if (endTime == Long.MIN_VALUE) {
			batchEngineTaskImpl.setEndTime(null);
		}
		else {
			batchEngineTaskImpl.setEndTime(new Date(endTime));
		}

		if (errorMessage == null) {
			batchEngineTaskImpl.setErrorMessage("");
		}
		else {
			batchEngineTaskImpl.setErrorMessage(errorMessage);
		}

		if (executeStatus == null) {
			batchEngineTaskImpl.setExecuteStatus("");
		}
		else {
			batchEngineTaskImpl.setExecuteStatus(executeStatus);
		}

		if (operation == null) {
			batchEngineTaskImpl.setOperation("");
		}
		else {
			batchEngineTaskImpl.setOperation(operation);
		}

		if (startTime == Long.MIN_VALUE) {
			batchEngineTaskImpl.setStartTime(null);
		}
		else {
			batchEngineTaskImpl.setStartTime(new Date(startTime));
		}

		if (version == null) {
			batchEngineTaskImpl.setVersion("");
		}
		else {
			batchEngineTaskImpl.setVersion(version);
		}

		batchEngineTaskImpl.resetOriginalValues();

		return batchEngineTaskImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		batchEngineTaskId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		batchSize = objectInput.readLong();
		className = objectInput.readUTF();
		contentType = objectInput.readUTF();
		endTime = objectInput.readLong();
		errorMessage = objectInput.readUTF();
		executeStatus = objectInput.readUTF();
		operation = objectInput.readUTF();
		startTime = objectInput.readLong();
		version = objectInput.readUTF();
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

		objectOutput.writeLong(batchEngineTaskId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(batchSize);

		if (className == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(className);
		}

		if (contentType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(contentType);
		}

		objectOutput.writeLong(endTime);

		if (errorMessage == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(errorMessage);
		}

		if (executeStatus == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(executeStatus);
		}

		if (operation == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(operation);
		}

		objectOutput.writeLong(startTime);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long batchEngineTaskId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long batchSize;
	public String className;
	public String contentType;
	public long endTime;
	public String errorMessage;
	public String executeStatus;
	public String operation;
	public long startTime;
	public String version;

}