/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		StringBundler sb = new StringBundler(37);

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
		sb.append(", size=");
		sb.append(size);
		sb.append(", taskItemDelegateName=");
		sb.append(taskItemDelegateName);
		sb.append(", total=");
		sb.append(total);
		sb.append(", template=");
		sb.append(template);
		sb.append(", status=");
		sb.append(status);
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

		batchPlannerPlanImpl.setSize(size);

		if (taskItemDelegateName == null) {
			batchPlannerPlanImpl.setTaskItemDelegateName("");
		}
		else {
			batchPlannerPlanImpl.setTaskItemDelegateName(taskItemDelegateName);
		}

		batchPlannerPlanImpl.setTotal(total);
		batchPlannerPlanImpl.setTemplate(template);
		batchPlannerPlanImpl.setStatus(status);

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

		size = objectInput.readInt();
		taskItemDelegateName = objectInput.readUTF();

		total = objectInput.readInt();

		template = objectInput.readBoolean();

		status = objectInput.readInt();
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

		objectOutput.writeInt(size);

		if (taskItemDelegateName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(taskItemDelegateName);
		}

		objectOutput.writeInt(total);

		objectOutput.writeBoolean(template);

		objectOutput.writeInt(status);
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
	public int size;
	public String taskItemDelegateName;
	public int total;
	public boolean template;
	public int status;

}