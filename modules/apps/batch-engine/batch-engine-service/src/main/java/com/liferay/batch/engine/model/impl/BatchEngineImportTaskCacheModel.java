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

import com.liferay.batch.engine.model.BatchEngineImportTask;
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
 * The cache model class for representing BatchEngineImportTask in entity cache.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class BatchEngineImportTaskCacheModel
	implements CacheModel<BatchEngineImportTask>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BatchEngineImportTaskCacheModel)) {
			return false;
		}

		BatchEngineImportTaskCacheModel batchEngineImportTaskCacheModel =
			(BatchEngineImportTaskCacheModel)obj;

		if ((batchEngineImportTaskId ==
				batchEngineImportTaskCacheModel.batchEngineImportTaskId) &&
			(mvccVersion == batchEngineImportTaskCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchEngineImportTaskId);

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
		sb.append(", batchEngineImportTaskId=");
		sb.append(batchEngineImportTaskId);
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
		sb.append(", executeStatus=");
		sb.append(executeStatus);
		sb.append(", fieldNameMapping=");
		sb.append(fieldNameMapping);
		sb.append(", operation=");
		sb.append(operation);
		sb.append(", parameters=");
		sb.append(parameters);
		sb.append(", startTime=");
		sb.append(startTime);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchEngineImportTask toEntityModel() {
		BatchEngineImportTaskImpl batchEngineImportTaskImpl =
			new BatchEngineImportTaskImpl();

		batchEngineImportTaskImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			batchEngineImportTaskImpl.setUuid("");
		}
		else {
			batchEngineImportTaskImpl.setUuid(uuid);
		}

		batchEngineImportTaskImpl.setBatchEngineImportTaskId(
			batchEngineImportTaskId);
		batchEngineImportTaskImpl.setCompanyId(companyId);
		batchEngineImportTaskImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			batchEngineImportTaskImpl.setCreateDate(null);
		}
		else {
			batchEngineImportTaskImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchEngineImportTaskImpl.setModifiedDate(null);
		}
		else {
			batchEngineImportTaskImpl.setModifiedDate(new Date(modifiedDate));
		}

		batchEngineImportTaskImpl.setBatchSize(batchSize);

		if (callbackURL == null) {
			batchEngineImportTaskImpl.setCallbackURL("");
		}
		else {
			batchEngineImportTaskImpl.setCallbackURL(callbackURL);
		}

		if (className == null) {
			batchEngineImportTaskImpl.setClassName("");
		}
		else {
			batchEngineImportTaskImpl.setClassName(className);
		}

		if (contentType == null) {
			batchEngineImportTaskImpl.setContentType("");
		}
		else {
			batchEngineImportTaskImpl.setContentType(contentType);
		}

		if (endTime == Long.MIN_VALUE) {
			batchEngineImportTaskImpl.setEndTime(null);
		}
		else {
			batchEngineImportTaskImpl.setEndTime(new Date(endTime));
		}

		if (errorMessage == null) {
			batchEngineImportTaskImpl.setErrorMessage("");
		}
		else {
			batchEngineImportTaskImpl.setErrorMessage(errorMessage);
		}

		if (executeStatus == null) {
			batchEngineImportTaskImpl.setExecuteStatus("");
		}
		else {
			batchEngineImportTaskImpl.setExecuteStatus(executeStatus);
		}

		batchEngineImportTaskImpl.setFieldNameMapping(fieldNameMapping);

		if (operation == null) {
			batchEngineImportTaskImpl.setOperation("");
		}
		else {
			batchEngineImportTaskImpl.setOperation(operation);
		}

		batchEngineImportTaskImpl.setParameters(parameters);

		if (startTime == Long.MIN_VALUE) {
			batchEngineImportTaskImpl.setStartTime(null);
		}
		else {
			batchEngineImportTaskImpl.setStartTime(new Date(startTime));
		}

		batchEngineImportTaskImpl.resetOriginalValues();

		return batchEngineImportTaskImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		batchEngineImportTaskId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		batchSize = objectInput.readLong();
		callbackURL = objectInput.readUTF();
		className = objectInput.readUTF();
		contentType = objectInput.readUTF();
		endTime = objectInput.readLong();
		errorMessage = objectInput.readUTF();
		executeStatus = objectInput.readUTF();
		fieldNameMapping = (Map<String, Serializable>)objectInput.readObject();
		operation = objectInput.readUTF();
		parameters = (Map<String, Serializable>)objectInput.readObject();
		startTime = objectInput.readLong();
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

		objectOutput.writeLong(batchEngineImportTaskId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(batchSize);

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

		if (executeStatus == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(executeStatus);
		}

		objectOutput.writeObject(fieldNameMapping);

		if (operation == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(operation);
		}

		objectOutput.writeObject(parameters);
		objectOutput.writeLong(startTime);
	}

	public long mvccVersion;
	public String uuid;
	public long batchEngineImportTaskId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long batchSize;
	public String callbackURL;
	public String className;
	public String contentType;
	public long endTime;
	public String errorMessage;
	public String executeStatus;
	public Map<String, Serializable> fieldNameMapping;
	public String operation;
	public Map<String, Serializable> parameters;
	public long startTime;

}