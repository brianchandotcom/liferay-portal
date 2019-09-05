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
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoInstance in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoInstanceCacheModel
	implements CacheModel<KaleoInstance>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoInstanceCacheModel)) {
			return false;
		}

		KaleoInstanceCacheModel kaleoInstanceCacheModel =
			(KaleoInstanceCacheModel)obj;

		if ((kaleoInstanceId == kaleoInstanceCacheModel.kaleoInstanceId) &&
			(mvccVersion == kaleoInstanceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoInstanceId);

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
		sb.append(", kaleoInstanceId=");
		sb.append(kaleoInstanceId);
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
		sb.append(", kaleoDefinitionName=");
		sb.append(kaleoDefinitionName);
		sb.append(", kaleoDefinitionVersion=");
		sb.append(kaleoDefinitionVersion);
		sb.append(", rootKaleoInstanceTokenId=");
		sb.append(rootKaleoInstanceTokenId);
		sb.append(", className=");
		sb.append(className);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", completed=");
		sb.append(completed);
		sb.append(", completionDate=");
		sb.append(completionDate);
		sb.append(", workflowContext=");
		sb.append(workflowContext);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoInstance toEntityModel() {
		KaleoInstanceImpl kaleoInstanceImpl = new KaleoInstanceImpl();

		kaleoInstanceImpl.setMvccVersion(mvccVersion);
		kaleoInstanceImpl.setKaleoInstanceId(kaleoInstanceId);
		kaleoInstanceImpl.setGroupId(groupId);
		kaleoInstanceImpl.setCompanyId(companyId);
		kaleoInstanceImpl.setUserId(userId);

		if (userName == null) {
			kaleoInstanceImpl.setUserName("");
		}
		else {
			kaleoInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoInstanceImpl.setCreateDate(null);
		}
		else {
			kaleoInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoInstanceImpl.setModifiedDate(null);
		}
		else {
			kaleoInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoInstanceImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (kaleoDefinitionName == null) {
			kaleoInstanceImpl.setKaleoDefinitionName("");
		}
		else {
			kaleoInstanceImpl.setKaleoDefinitionName(kaleoDefinitionName);
		}

		kaleoInstanceImpl.setKaleoDefinitionVersion(kaleoDefinitionVersion);
		kaleoInstanceImpl.setRootKaleoInstanceTokenId(rootKaleoInstanceTokenId);

		if (className == null) {
			kaleoInstanceImpl.setClassName("");
		}
		else {
			kaleoInstanceImpl.setClassName(className);
		}

		kaleoInstanceImpl.setClassPK(classPK);
		kaleoInstanceImpl.setCompleted(completed);

		if (completionDate == Long.MIN_VALUE) {
			kaleoInstanceImpl.setCompletionDate(null);
		}
		else {
			kaleoInstanceImpl.setCompletionDate(new Date(completionDate));
		}

		if (workflowContext == null) {
			kaleoInstanceImpl.setWorkflowContext("");
		}
		else {
			kaleoInstanceImpl.setWorkflowContext(workflowContext);
		}

		kaleoInstanceImpl.resetOriginalValues();

		return kaleoInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		kaleoInstanceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();
		kaleoDefinitionName = objectInput.readUTF();

		kaleoDefinitionVersion = objectInput.readInt();

		rootKaleoInstanceTokenId = objectInput.readLong();
		className = objectInput.readUTF();

		classPK = objectInput.readLong();

		completed = objectInput.readBoolean();
		completionDate = objectInput.readLong();
		workflowContext = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(kaleoInstanceId);

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

		if (kaleoDefinitionName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoDefinitionName);
		}

		objectOutput.writeInt(kaleoDefinitionVersion);

		objectOutput.writeLong(rootKaleoInstanceTokenId);

		if (className == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(className);
		}

		objectOutput.writeLong(classPK);

		objectOutput.writeBoolean(completed);
		objectOutput.writeLong(completionDate);

		if (workflowContext == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(workflowContext);
		}
	}

	public long mvccVersion;
	public long kaleoInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionVersionId;
	public String kaleoDefinitionName;
	public int kaleoDefinitionVersion;
	public long rootKaleoInstanceTokenId;
	public String className;
	public long classPK;
	public boolean completed;
	public long completionDate;
	public String workflowContext;

}