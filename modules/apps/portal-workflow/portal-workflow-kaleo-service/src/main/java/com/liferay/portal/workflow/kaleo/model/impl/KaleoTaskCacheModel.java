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

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTask in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTaskCacheModel
	implements CacheModel<KaleoTask>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoTaskCacheModel)) {
			return false;
		}

		KaleoTaskCacheModel kaleoTaskCacheModel = (KaleoTaskCacheModel)obj;

		if ((kaleoTaskId == kaleoTaskCacheModel.kaleoTaskId) &&
			(mvccVersion == kaleoTaskCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoTaskId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", kaleoTaskId=");
		sb.append(kaleoTaskId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", kaleoNodeId=");
		sb.append(kaleoNodeId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoTask toEntityModel() {
		KaleoTaskImpl kaleoTaskImpl = new KaleoTaskImpl();

		kaleoTaskImpl.setMvccVersion(mvccVersion);
		kaleoTaskImpl.setKaleoTaskId(kaleoTaskId);
		kaleoTaskImpl.setGroupId(groupId);
		kaleoTaskImpl.setCompanyId(companyId);
		kaleoTaskImpl.setUserId(userId);

		if (userName == null) {
			kaleoTaskImpl.setUserName("");
		}
		else {
			kaleoTaskImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTaskImpl.setCreateDate(null);
		}
		else {
			kaleoTaskImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTaskImpl.setModifiedDate(null);
		}
		else {
			kaleoTaskImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoTaskImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoTaskImpl.setKaleoNodeId(kaleoNodeId);

		if (name == null) {
			kaleoTaskImpl.setName("");
		}
		else {
			kaleoTaskImpl.setName(name);
		}

		if (description == null) {
			kaleoTaskImpl.setDescription("");
		}
		else {
			kaleoTaskImpl.setDescription(description);
		}

		kaleoTaskImpl.resetOriginalValues();

		return kaleoTaskImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		kaleoTaskId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		kaleoNodeId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(kaleoTaskId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(kaleoDefinitionVersionId);

		objectOutput.writeLong(kaleoNodeId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}
	}

	public long mvccVersion;
	public long kaleoTaskId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionVersionId;
	public long kaleoNodeId;
	public String name;
	public String description;

}