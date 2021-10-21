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
		StringBundler sb = new StringBundler(31);

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
		sb.append(", export=");
		sb.append(export);
		sb.append(", externalType=");
		sb.append(externalType);
		sb.append(", externalURL=");
		sb.append(externalURL);
		sb.append(", internalClassName=");
		sb.append(internalClassName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", taskItemDelegateName=");
		sb.append(taskItemDelegateName);
		sb.append(", template=");
		sb.append(template);
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
		batchPlannerPlanImpl.setExport(export);

		if (externalType == null) {
			batchPlannerPlanImpl.setExternalType("");
		}
		else {
			batchPlannerPlanImpl.setExternalType(externalType);
		}

		if (externalURL == null) {
			batchPlannerPlanImpl.setExternalURL("");
		}
		else {
			batchPlannerPlanImpl.setExternalURL(externalURL);
		}

		if (internalClassName == null) {
			batchPlannerPlanImpl.setInternalClassName("");
		}
		else {
			batchPlannerPlanImpl.setInternalClassName(internalClassName);
		}

		if (name == null) {
			batchPlannerPlanImpl.setName("");
		}
		else {
			batchPlannerPlanImpl.setName(name);
		}

		if (taskItemDelegateName == null) {
			batchPlannerPlanImpl.setTaskItemDelegateName("");
		}
		else {
			batchPlannerPlanImpl.setTaskItemDelegateName(taskItemDelegateName);
		}

		batchPlannerPlanImpl.setTemplate(template);

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

		export = objectInput.readBoolean();
		externalType = objectInput.readUTF();
		externalURL = objectInput.readUTF();
		internalClassName = objectInput.readUTF();
		name = objectInput.readUTF();
		taskItemDelegateName = objectInput.readUTF();

		template = objectInput.readBoolean();
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

		objectOutput.writeBoolean(export);

		if (externalType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalType);
		}

		if (externalURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalURL);
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

		if (taskItemDelegateName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(taskItemDelegateName);
		}

		objectOutput.writeBoolean(template);
	}

	public long mvccVersion;
	public long batchPlannerPlanId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public boolean export;
	public String externalType;
	public String externalURL;
	public String internalClassName;
	public String name;
	public String taskItemDelegateName;
	public boolean template;

}