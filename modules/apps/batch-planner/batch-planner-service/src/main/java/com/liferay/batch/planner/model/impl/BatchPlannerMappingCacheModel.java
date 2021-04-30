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
		sb.append(", externalName=");
		sb.append(externalName);
		sb.append(", externalType=");
		sb.append(externalType);
		sb.append(", internalName=");
		sb.append(internalName);
		sb.append(", internalType=");
		sb.append(internalType);
		sb.append(", transformationExpression=");
		sb.append(transformationExpression);
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
		batchPlannerMappingImpl.setExternalName(externalName);
		batchPlannerMappingImpl.setExternalType(externalType);
		batchPlannerMappingImpl.setInternalName(internalName);
		batchPlannerMappingImpl.setInternalType(internalType);
		batchPlannerMappingImpl.setTransformationExpression(
			transformationExpression);

		batchPlannerMappingImpl.resetOriginalValues();

		return batchPlannerMappingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		batchPlannerMappingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		batchPlannerPlanId = objectInput.readLong();
		externalName = objectInput.readUTF();
		externalType = objectInput.readUTF();
		internalName = objectInput.readUTF();
		internalType = objectInput.readUTF();
		transformationExpression = objectInput.readUTF();
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

		if (externalName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalName);
		}

		if (externalType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalType);
		}

		if (internalName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(internalName);
		}

		if (internalType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(internalType);
		}

		if (transformationExpression == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(transformationExpression);
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
	public String externalName;
	public String externalType;
	public String internalName;
	public String internalType;
	public String transformationExpression;

}