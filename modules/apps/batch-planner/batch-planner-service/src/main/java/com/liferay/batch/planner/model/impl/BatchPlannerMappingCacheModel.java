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

package com.liferay.batch.planner.model.impl;

import com.liferay.batch.planner.model.BatchPlannerMapping;
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
 * The cache model class for representing BatchPlannerMapping in entity cache.
 *
 * @author Igor Beslic
 * @generated
 */
public class BatchPlannerMappingCacheModel
	implements CacheModel<BatchPlannerMapping>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchPlannerMappingCacheModel)) {
			return false;
		}

		BatchPlannerMappingCacheModel batchPlannerMappingCacheModel =
			(BatchPlannerMappingCacheModel)object;

		if ((batchPlannerMappingId ==
				batchPlannerMappingCacheModel.batchPlannerMappingId) &&
			(mvccVersion == batchPlannerMappingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchPlannerMappingId);

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
		sb.append(", batchPlannerMappingId=");
		sb.append(batchPlannerMappingId);
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
		sb.append(", batchPlannerPlanId=");
		sb.append(batchPlannerPlanId);
		sb.append(", externalFieldName=");
		sb.append(externalFieldName);
		sb.append(", externalFieldType=");
		sb.append(externalFieldType);
		sb.append(", internalFieldName=");
		sb.append(internalFieldName);
		sb.append(", internalFieldType=");
		sb.append(internalFieldType);
		sb.append(", script=");
		sb.append(script);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchPlannerMapping toEntityModel() {
		BatchPlannerMappingImpl batchPlannerMappingImpl =
			new BatchPlannerMappingImpl();

		batchPlannerMappingImpl.setMvccVersion(mvccVersion);
		batchPlannerMappingImpl.setBatchPlannerMappingId(batchPlannerMappingId);
		batchPlannerMappingImpl.setCompanyId(companyId);
		batchPlannerMappingImpl.setUserId(userId);

		if (userName == null) {
			batchPlannerMappingImpl.setUserName("");
		}
		else {
			batchPlannerMappingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			batchPlannerMappingImpl.setCreateDate(null);
		}
		else {
			batchPlannerMappingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchPlannerMappingImpl.setModifiedDate(null);
		}
		else {
			batchPlannerMappingImpl.setModifiedDate(new Date(modifiedDate));
		}

		batchPlannerMappingImpl.setBatchPlannerPlanId(batchPlannerPlanId);

		if (externalFieldName == null) {
			batchPlannerMappingImpl.setExternalFieldName("");
		}
		else {
			batchPlannerMappingImpl.setExternalFieldName(externalFieldName);
		}

		if (externalFieldType == null) {
			batchPlannerMappingImpl.setExternalFieldType("");
		}
		else {
			batchPlannerMappingImpl.setExternalFieldType(externalFieldType);
		}

		if (internalFieldName == null) {
			batchPlannerMappingImpl.setInternalFieldName("");
		}
		else {
			batchPlannerMappingImpl.setInternalFieldName(internalFieldName);
		}

		if (internalFieldType == null) {
			batchPlannerMappingImpl.setInternalFieldType("");
		}
		else {
			batchPlannerMappingImpl.setInternalFieldType(internalFieldType);
		}

		if (script == null) {
			batchPlannerMappingImpl.setScript("");
		}
		else {
			batchPlannerMappingImpl.setScript(script);
		}

		batchPlannerMappingImpl.resetOriginalValues();

		return batchPlannerMappingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		batchPlannerMappingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		batchPlannerPlanId = objectInput.readLong();
		externalFieldName = objectInput.readUTF();
		externalFieldType = objectInput.readUTF();
		internalFieldName = objectInput.readUTF();
		internalFieldType = objectInput.readUTF();
		script = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(batchPlannerMappingId);

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

		objectOutput.writeLong(batchPlannerPlanId);

		if (externalFieldName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalFieldName);
		}

		if (externalFieldType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalFieldType);
		}

		if (internalFieldName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(internalFieldName);
		}

		if (internalFieldType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(internalFieldType);
		}

		if (script == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(script);
		}
	}

	public long mvccVersion;
	public long batchPlannerMappingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long batchPlannerPlanId;
	public String externalFieldName;
	public String externalFieldType;
	public String internalFieldName;
	public String internalFieldType;
	public String script;

}