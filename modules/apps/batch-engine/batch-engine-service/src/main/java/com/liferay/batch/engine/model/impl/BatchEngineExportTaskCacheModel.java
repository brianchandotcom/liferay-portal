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

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * The cache model class for representing BatchEngineExportTask in entity cache.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class BatchEngineExportTaskCacheModel
	implements CacheModel<BatchEngineExportTask>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BatchEngineExportTaskCacheModel)) {
			return false;
		}

		BatchEngineExportTaskCacheModel batchEngineExportTaskCacheModel =
			(BatchEngineExportTaskCacheModel)obj;

		if ((batchEngineExportTaskId ==
				batchEngineExportTaskCacheModel.batchEngineExportTaskId) &&
			(mvccVersion == batchEngineExportTaskCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchEngineExportTaskId);

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
		sb.append(", batchEngineExportTaskId=");
		sb.append(batchEngineExportTaskId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", callbackURL=");
		sb.append(callbackURL);
		sb.append(", className=");
		sb.append(className);
		sb.append(", contentType=");
		sb.append(contentType);
		sb.append(", endTime=");
		sb.append(endTime);
		sb.append(", errorMessage=");
		sb.append(errorMessage);
		sb.append(", fieldNames=");
		sb.append(fieldNames);
		sb.append(", executeStatus=");
		sb.append(executeStatus);
		sb.append(", parameters=");
		sb.append(parameters);
		sb.append(", startTime=");
		sb.append(startTime);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchEngineExportTask toEntityModel() {
		BatchEngineExportTaskImpl batchEngineExportTaskImpl =
			new BatchEngineExportTaskImpl();

		batchEngineExportTaskImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			batchEngineExportTaskImpl.setUuid("");
		}
		else {
			batchEngineExportTaskImpl.setUuid(uuid);
		}

		batchEngineExportTaskImpl.setBatchEngineExportTaskId(
			batchEngineExportTaskId);
		batchEngineExportTaskImpl.setCompanyId(companyId);
		batchEngineExportTaskImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			batchEngineExportTaskImpl.setCreateDate(null);
		}
		else {
			batchEngineExportTaskImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchEngineExportTaskImpl.setModifiedDate(null);
		}
		else {
			batchEngineExportTaskImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (callbackURL == null) {
			batchEngineExportTaskImpl.setCallbackURL("");
		}
		else {
			batchEngineExportTaskImpl.setCallbackURL(callbackURL);
		}

		if (className == null) {
			batchEngineExportTaskImpl.setClassName("");
		}
		else {
			batchEngineExportTaskImpl.setClassName(className);
		}

		if (contentType == null) {
			batchEngineExportTaskImpl.setContentType("");
		}
		else {
			batchEngineExportTaskImpl.setContentType(contentType);
		}

		if (endTime == Long.MIN_VALUE) {
			batchEngineExportTaskImpl.setEndTime(null);
		}
		else {
			batchEngineExportTaskImpl.setEndTime(new Date(endTime));
		}

		if (errorMessage == null) {
			batchEngineExportTaskImpl.setErrorMessage("");
		}
		else {
			batchEngineExportTaskImpl.setErrorMessage(errorMessage);
		}

		if (fieldNames == null) {
			batchEngineExportTaskImpl.setFieldNames("");
		}
		else {
			batchEngineExportTaskImpl.setFieldNames(fieldNames);
		}

		if (executeStatus == null) {
			batchEngineExportTaskImpl.setExecuteStatus("");
		}
		else {
			batchEngineExportTaskImpl.setExecuteStatus(executeStatus);
		}

		batchEngineExportTaskImpl.setParameters(parameters);

		if (startTime == Long.MIN_VALUE) {
			batchEngineExportTaskImpl.setStartTime(null);
		}
		else {
			batchEngineExportTaskImpl.setStartTime(new Date(startTime));
		}

		if (version == null) {
			batchEngineExportTaskImpl.setVersion("");
		}
		else {
			batchEngineExportTaskImpl.setVersion(version);
		}

		batchEngineExportTaskImpl.resetOriginalValues();

		return batchEngineExportTaskImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		batchEngineExportTaskId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		callbackURL = objectInput.readUTF();
		className = objectInput.readUTF();
		contentType = objectInput.readUTF();
		endTime = objectInput.readLong();
		errorMessage = objectInput.readUTF();
		fieldNames = objectInput.readUTF();
		executeStatus = objectInput.readUTF();
		parameters = (Map<String, Serializable>)objectInput.readObject();
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

		objectOutput.writeLong(batchEngineExportTaskId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (callbackURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(callbackURL);
		}

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

		if (fieldNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fieldNames);
		}

		if (executeStatus == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(executeStatus);
		}

		objectOutput.writeObject(parameters);
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
	public long batchEngineExportTaskId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String callbackURL;
	public String className;
	public String contentType;
	public long endTime;
	public String errorMessage;
	public String fieldNames;
	public String executeStatus;
	public Map<String, Serializable> parameters;
	public long startTime;
	public String version;

}