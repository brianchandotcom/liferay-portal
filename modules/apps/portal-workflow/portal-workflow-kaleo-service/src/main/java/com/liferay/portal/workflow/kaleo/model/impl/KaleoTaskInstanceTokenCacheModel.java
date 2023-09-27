/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTaskInstanceToken in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTaskInstanceTokenCacheModel
	implements CacheModel<KaleoTaskInstanceToken>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoTaskInstanceTokenCacheModel)) {
			return false;
		}

		KaleoTaskInstanceTokenCacheModel kaleoTaskInstanceTokenCacheModel =
			(KaleoTaskInstanceTokenCacheModel)object;

		if ((kaleoTaskInstanceTokenId ==
				kaleoTaskInstanceTokenCacheModel.kaleoTaskInstanceTokenId) &&
			(mvccVersion == kaleoTaskInstanceTokenCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoTaskInstanceTokenId);

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
		StringBundler sb = new StringBundler(45);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);
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
		sb.append(", kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", kaleoInstanceId=");
		sb.append(kaleoInstanceId);
		sb.append(", kaleoInstanceTokenId=");
		sb.append(kaleoInstanceTokenId);
		sb.append(", kaleoTaskId=");
		sb.append(kaleoTaskId);
		sb.append(", kaleoTaskName=");
		sb.append(kaleoTaskName);
		sb.append(", className=");
		sb.append(className);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", completionUserId=");
		sb.append(completionUserId);
		sb.append(", completed=");
		sb.append(completed);
		sb.append(", completionDate=");
		sb.append(completionDate);
		sb.append(", dueDate=");
		sb.append(dueDate);
		sb.append(", workflowContext=");
		sb.append(workflowContext);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoTaskInstanceToken toEntityModel() {
		KaleoTaskInstanceTokenImpl kaleoTaskInstanceTokenImpl =
			new KaleoTaskInstanceTokenImpl();

		kaleoTaskInstanceTokenImpl.setMvccVersion(mvccVersion);
		kaleoTaskInstanceTokenImpl.setCtCollectionId(ctCollectionId);
		kaleoTaskInstanceTokenImpl.setKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
		kaleoTaskInstanceTokenImpl.setGroupId(groupId);
		kaleoTaskInstanceTokenImpl.setCompanyId(companyId);
		kaleoTaskInstanceTokenImpl.setUserId(userId);

		if (userName == null) {
			kaleoTaskInstanceTokenImpl.setUserName("");
		}
		else {
			kaleoTaskInstanceTokenImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTaskInstanceTokenImpl.setCreateDate(null);
		}
		else {
			kaleoTaskInstanceTokenImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTaskInstanceTokenImpl.setModifiedDate(null);
		}
		else {
			kaleoTaskInstanceTokenImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoTaskInstanceTokenImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoTaskInstanceTokenImpl.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
		kaleoTaskInstanceTokenImpl.setKaleoInstanceId(kaleoInstanceId);
		kaleoTaskInstanceTokenImpl.setKaleoInstanceTokenId(
			kaleoInstanceTokenId);
		kaleoTaskInstanceTokenImpl.setKaleoTaskId(kaleoTaskId);

		if (kaleoTaskName == null) {
			kaleoTaskInstanceTokenImpl.setKaleoTaskName("");
		}
		else {
			kaleoTaskInstanceTokenImpl.setKaleoTaskName(kaleoTaskName);
		}

		if (className == null) {
			kaleoTaskInstanceTokenImpl.setClassName("");
		}
		else {
			kaleoTaskInstanceTokenImpl.setClassName(className);
		}

		kaleoTaskInstanceTokenImpl.setClassPK(classPK);
		kaleoTaskInstanceTokenImpl.setCompletionUserId(completionUserId);
		kaleoTaskInstanceTokenImpl.setCompleted(completed);

		if (completionDate == Long.MIN_VALUE) {
			kaleoTaskInstanceTokenImpl.setCompletionDate(null);
		}
		else {
			kaleoTaskInstanceTokenImpl.setCompletionDate(
				new Date(completionDate));
		}

		if (dueDate == Long.MIN_VALUE) {
			kaleoTaskInstanceTokenImpl.setDueDate(null);
		}
		else {
			kaleoTaskInstanceTokenImpl.setDueDate(new Date(dueDate));
		}

		if (workflowContext == null) {
			kaleoTaskInstanceTokenImpl.setWorkflowContext("");
		}
		else {
			kaleoTaskInstanceTokenImpl.setWorkflowContext(workflowContext);
		}

		kaleoTaskInstanceTokenImpl.resetOriginalValues();

		return kaleoTaskInstanceTokenImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoTaskInstanceTokenId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		kaleoInstanceId = objectInput.readLong();

		kaleoInstanceTokenId = objectInput.readLong();

		kaleoTaskId = objectInput.readLong();
		kaleoTaskName = objectInput.readUTF();
		className = objectInput.readUTF();

		classPK = objectInput.readLong();

		completionUserId = objectInput.readLong();

		completed = objectInput.readBoolean();
		completionDate = objectInput.readLong();
		dueDate = objectInput.readLong();
		workflowContext = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(kaleoTaskInstanceTokenId);

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

		objectOutput.writeLong(kaleoDefinitionId);

		objectOutput.writeLong(kaleoDefinitionVersionId);

		objectOutput.writeLong(kaleoInstanceId);

		objectOutput.writeLong(kaleoInstanceTokenId);

		objectOutput.writeLong(kaleoTaskId);

		if (kaleoTaskName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoTaskName);
		}

		if (className == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(className);
		}

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(completionUserId);

		objectOutput.writeBoolean(completed);
		objectOutput.writeLong(completionDate);
		objectOutput.writeLong(dueDate);

		if (workflowContext == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(workflowContext);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoTaskInstanceTokenId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionId;
	public long kaleoDefinitionVersionId;
	public long kaleoInstanceId;
	public long kaleoInstanceTokenId;
	public long kaleoTaskId;
	public String kaleoTaskName;
	public String className;
	public long classPK;
	public long completionUserId;
	public boolean completed;
	public long completionDate;
	public long dueDate;
	public String workflowContext;

}