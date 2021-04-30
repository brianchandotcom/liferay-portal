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

import com.liferay.batch.planner.model.BatchPlannerPlan;
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
 * The cache model class for representing BatchPlannerPlan in entity cache.
 *
 * @author Igor Beslic
 * @generated
 */
public class BatchPlannerPlanCacheModel
	implements CacheModel<BatchPlannerPlan>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchPlannerPlanCacheModel)) {
			return false;
		}

		BatchPlannerPlanCacheModel batchPlannerPlanCacheModel =
			(BatchPlannerPlanCacheModel)object;

		if ((batchPlannerPlanId ==
				batchPlannerPlanCacheModel.batchPlannerPlanId) &&
			(mvccVersion == batchPlannerPlanCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchPlannerPlanId);

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
		sb.append(", batchPlannerPlanId=");
		sb.append(batchPlannerPlanId);
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
		sb.append(", active=");
		sb.append(active);
		sb.append(", externalContentType=");
		sb.append(externalContentType);
		sb.append(", internalClassName=");
		sb.append(internalClassName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", channel=");
		sb.append(channel);
		sb.append(", export=");
		sb.append(export);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchPlannerPlan toEntityModel() {
		BatchPlannerPlanImpl batchPlannerPlanImpl = new BatchPlannerPlanImpl();

		batchPlannerPlanImpl.setMvccVersion(mvccVersion);
		batchPlannerPlanImpl.setBatchPlannerPlanId(batchPlannerPlanId);
		batchPlannerPlanImpl.setCompanyId(companyId);
		batchPlannerPlanImpl.setUserId(userId);

		if (userName == null) {
			batchPlannerPlanImpl.setUserName("");
		}
		else {
			batchPlannerPlanImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			batchPlannerPlanImpl.setCreateDate(null);
		}
		else {
			batchPlannerPlanImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchPlannerPlanImpl.setModifiedDate(null);
		}
		else {
			batchPlannerPlanImpl.setModifiedDate(new Date(modifiedDate));
		}

		batchPlannerPlanImpl.setActive(active);
		batchPlannerPlanImpl.setExternalContentType(externalContentType);
		batchPlannerPlanImpl.setInternalClassName(internalClassName);
		batchPlannerPlanImpl.setName(name);
		batchPlannerPlanImpl.setChannel(channel);
		batchPlannerPlanImpl.setExport(export);

		batchPlannerPlanImpl.resetOriginalValues();

		return batchPlannerPlanImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		batchPlannerPlanId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		externalContentType = objectInput.readUTF();
		internalClassName = objectInput.readUTF();
		name = objectInput.readUTF();
		channel = objectInput.readUTF();

		export = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(batchPlannerPlanId);

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

		objectOutput.writeBoolean(active);

		if (externalContentType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalContentType);
		}

		if (internalClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(internalClassName);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (channel == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(channel);
		}

		objectOutput.writeBoolean(export);
	}

	public long mvccVersion;
	public long batchPlannerPlanId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public String externalContentType;
	public String internalClassName;
	public String name;
	public String channel;
	public boolean export;

}