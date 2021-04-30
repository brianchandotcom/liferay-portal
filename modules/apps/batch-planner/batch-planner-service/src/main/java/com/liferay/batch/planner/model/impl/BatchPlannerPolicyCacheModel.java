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

import com.liferay.batch.planner.model.BatchPlannerPolicy;
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
 * The cache model class for representing BatchPlannerPolicy in entity cache.
 *
 * @author Igor Beslic
 * @generated
 */
public class BatchPlannerPolicyCacheModel
	implements CacheModel<BatchPlannerPolicy>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchPlannerPolicyCacheModel)) {
			return false;
		}

		BatchPlannerPolicyCacheModel batchPlannerPolicyCacheModel =
			(BatchPlannerPolicyCacheModel)object;

		if ((batchPlannerPolicyId ==
				batchPlannerPolicyCacheModel.batchPlannerPolicyId) &&
			(mvccVersion == batchPlannerPolicyCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchPlannerPolicyId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", batchPlannerPolicyId=");
		sb.append(batchPlannerPolicyId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchPlannerPolicy toEntityModel() {
		BatchPlannerPolicyImpl batchPlannerPolicyImpl =
			new BatchPlannerPolicyImpl();

		batchPlannerPolicyImpl.setMvccVersion(mvccVersion);
		batchPlannerPolicyImpl.setBatchPlannerPolicyId(batchPlannerPolicyId);
		batchPlannerPolicyImpl.setCompanyId(companyId);
		batchPlannerPolicyImpl.setUserId(userId);

		if (userName == null) {
			batchPlannerPolicyImpl.setUserName("");
		}
		else {
			batchPlannerPolicyImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			batchPlannerPolicyImpl.setCreateDate(null);
		}
		else {
			batchPlannerPolicyImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchPlannerPolicyImpl.setModifiedDate(null);
		}
		else {
			batchPlannerPolicyImpl.setModifiedDate(new Date(modifiedDate));
		}

		batchPlannerPolicyImpl.setBatchPlannerPlanId(batchPlannerPlanId);
		batchPlannerPolicyImpl.setName(name);
		batchPlannerPolicyImpl.setValue(value);

		batchPlannerPolicyImpl.resetOriginalValues();

		return batchPlannerPolicyImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		batchPlannerPolicyId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		batchPlannerPlanId = objectInput.readLong();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(batchPlannerPolicyId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long mvccVersion;
	public long batchPlannerPolicyId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long batchPlannerPlanId;
	public String name;
	public String value;

}