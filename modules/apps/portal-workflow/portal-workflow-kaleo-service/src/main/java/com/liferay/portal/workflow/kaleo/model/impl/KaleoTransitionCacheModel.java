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
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTransition in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTransitionCacheModel
	implements CacheModel<KaleoTransition>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoTransitionCacheModel)) {
			return false;
		}

		KaleoTransitionCacheModel kaleoTransitionCacheModel =
			(KaleoTransitionCacheModel)obj;

		if ((kaleoTransitionId ==
				kaleoTransitionCacheModel.kaleoTransitionId) &&
			(mvccVersion == kaleoTransitionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoTransitionId);

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
		sb.append(", kaleoTransitionId=");
		sb.append(kaleoTransitionId);
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
		sb.append(", sourceKaleoNodeId=");
		sb.append(sourceKaleoNodeId);
		sb.append(", sourceKaleoNodeName=");
		sb.append(sourceKaleoNodeName);
		sb.append(", targetKaleoNodeId=");
		sb.append(targetKaleoNodeId);
		sb.append(", targetKaleoNodeName=");
		sb.append(targetKaleoNodeName);
		sb.append(", defaultTransition=");
		sb.append(defaultTransition);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoTransition toEntityModel() {
		KaleoTransitionImpl kaleoTransitionImpl = new KaleoTransitionImpl();

		kaleoTransitionImpl.setMvccVersion(mvccVersion);
		kaleoTransitionImpl.setKaleoTransitionId(kaleoTransitionId);
		kaleoTransitionImpl.setGroupId(groupId);
		kaleoTransitionImpl.setCompanyId(companyId);
		kaleoTransitionImpl.setUserId(userId);

		if (userName == null) {
			kaleoTransitionImpl.setUserName("");
		}
		else {
			kaleoTransitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTransitionImpl.setCreateDate(null);
		}
		else {
			kaleoTransitionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTransitionImpl.setModifiedDate(null);
		}
		else {
			kaleoTransitionImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoTransitionImpl.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
		kaleoTransitionImpl.setKaleoNodeId(kaleoNodeId);

		if (name == null) {
			kaleoTransitionImpl.setName("");
		}
		else {
			kaleoTransitionImpl.setName(name);
		}

		if (description == null) {
			kaleoTransitionImpl.setDescription("");
		}
		else {
			kaleoTransitionImpl.setDescription(description);
		}

		kaleoTransitionImpl.setSourceKaleoNodeId(sourceKaleoNodeId);

		if (sourceKaleoNodeName == null) {
			kaleoTransitionImpl.setSourceKaleoNodeName("");
		}
		else {
			kaleoTransitionImpl.setSourceKaleoNodeName(sourceKaleoNodeName);
		}

		kaleoTransitionImpl.setTargetKaleoNodeId(targetKaleoNodeId);

		if (targetKaleoNodeName == null) {
			kaleoTransitionImpl.setTargetKaleoNodeName("");
		}
		else {
			kaleoTransitionImpl.setTargetKaleoNodeName(targetKaleoNodeName);
		}

		kaleoTransitionImpl.setDefaultTransition(defaultTransition);

		kaleoTransitionImpl.resetOriginalValues();

		return kaleoTransitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		kaleoTransitionId = objectInput.readLong();

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

		sourceKaleoNodeId = objectInput.readLong();
		sourceKaleoNodeName = objectInput.readUTF();

		targetKaleoNodeId = objectInput.readLong();
		targetKaleoNodeName = objectInput.readUTF();

		defaultTransition = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(kaleoTransitionId);

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

		objectOutput.writeLong(sourceKaleoNodeId);

		if (sourceKaleoNodeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sourceKaleoNodeName);
		}

		objectOutput.writeLong(targetKaleoNodeId);

		if (targetKaleoNodeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(targetKaleoNodeName);
		}

		objectOutput.writeBoolean(defaultTransition);
	}

	public long mvccVersion;
	public long kaleoTransitionId;
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
	public long sourceKaleoNodeId;
	public String sourceKaleoNodeName;
	public long targetKaleoNodeId;
	public String targetKaleoNodeName;
	public boolean defaultTransition;

}