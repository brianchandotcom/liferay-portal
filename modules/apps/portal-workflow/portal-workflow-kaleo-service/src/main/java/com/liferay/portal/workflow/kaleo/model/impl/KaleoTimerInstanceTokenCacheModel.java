/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTimerInstanceToken in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTimerInstanceTokenCacheModel
	implements CacheModel<KaleoTimerInstanceToken>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoTimerInstanceTokenCacheModel)) {
			return false;
		}

		KaleoTimerInstanceTokenCacheModel kaleoTimerInstanceTokenCacheModel =
			(KaleoTimerInstanceTokenCacheModel)object;

		if ((kaleoTimerInstanceTokenId ==
				kaleoTimerInstanceTokenCacheModel.kaleoTimerInstanceTokenId) &&
			(mvccVersion == kaleoTimerInstanceTokenCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoTimerInstanceTokenId);

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
		StringBundler sb = new StringBundler(47);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", kaleoTimerInstanceTokenId=");
		sb.append(kaleoTimerInstanceTokenId);
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
		sb.append(", kaleoClassName=");
		sb.append(kaleoClassName);
		sb.append(", kaleoClassPK=");
		sb.append(kaleoClassPK);
		sb.append(", kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", kaleoInstanceId=");
		sb.append(kaleoInstanceId);
		sb.append(", kaleoInstanceTokenId=");
		sb.append(kaleoInstanceTokenId);
		sb.append(", kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);
		sb.append(", kaleoTimerId=");
		sb.append(kaleoTimerId);
		sb.append(", kaleoTimerName=");
		sb.append(kaleoTimerName);
		sb.append(", blocking=");
		sb.append(blocking);
		sb.append(", completionUserId=");
		sb.append(completionUserId);
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
	public KaleoTimerInstanceToken toEntityModel() {
		KaleoTimerInstanceTokenImpl kaleoTimerInstanceTokenImpl =
			new KaleoTimerInstanceTokenImpl();

		kaleoTimerInstanceTokenImpl.setMvccVersion(mvccVersion);
		kaleoTimerInstanceTokenImpl.setCtCollectionId(ctCollectionId);
		kaleoTimerInstanceTokenImpl.setKaleoTimerInstanceTokenId(
			kaleoTimerInstanceTokenId);
		kaleoTimerInstanceTokenImpl.setGroupId(groupId);
		kaleoTimerInstanceTokenImpl.setCompanyId(companyId);
		kaleoTimerInstanceTokenImpl.setUserId(userId);

		if (userName == null) {
			kaleoTimerInstanceTokenImpl.setUserName("");
		}
		else {
			kaleoTimerInstanceTokenImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTimerInstanceTokenImpl.setCreateDate(null);
		}
		else {
			kaleoTimerInstanceTokenImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTimerInstanceTokenImpl.setModifiedDate(null);
		}
		else {
			kaleoTimerInstanceTokenImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (kaleoClassName == null) {
			kaleoTimerInstanceTokenImpl.setKaleoClassName("");
		}
		else {
			kaleoTimerInstanceTokenImpl.setKaleoClassName(kaleoClassName);
		}

		kaleoTimerInstanceTokenImpl.setKaleoClassPK(kaleoClassPK);
		kaleoTimerInstanceTokenImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoTimerInstanceTokenImpl.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
		kaleoTimerInstanceTokenImpl.setKaleoInstanceId(kaleoInstanceId);
		kaleoTimerInstanceTokenImpl.setKaleoInstanceTokenId(
			kaleoInstanceTokenId);
		kaleoTimerInstanceTokenImpl.setKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
		kaleoTimerInstanceTokenImpl.setKaleoTimerId(kaleoTimerId);

		if (kaleoTimerName == null) {
			kaleoTimerInstanceTokenImpl.setKaleoTimerName("");
		}
		else {
			kaleoTimerInstanceTokenImpl.setKaleoTimerName(kaleoTimerName);
		}

		kaleoTimerInstanceTokenImpl.setBlocking(blocking);
		kaleoTimerInstanceTokenImpl.setCompletionUserId(completionUserId);
		kaleoTimerInstanceTokenImpl.setCompleted(completed);

		if (completionDate == Long.MIN_VALUE) {
			kaleoTimerInstanceTokenImpl.setCompletionDate(null);
		}
		else {
			kaleoTimerInstanceTokenImpl.setCompletionDate(
				new Date(completionDate));
		}

		if (workflowContext == null) {
			kaleoTimerInstanceTokenImpl.setWorkflowContext("");
		}
		else {
			kaleoTimerInstanceTokenImpl.setWorkflowContext(workflowContext);
		}

		kaleoTimerInstanceTokenImpl.resetOriginalValues();

		return kaleoTimerInstanceTokenImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoTimerInstanceTokenId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		kaleoClassName = objectInput.readUTF();

		kaleoClassPK = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		kaleoInstanceId = objectInput.readLong();

		kaleoInstanceTokenId = objectInput.readLong();

		kaleoTaskInstanceTokenId = objectInput.readLong();

		kaleoTimerId = objectInput.readLong();
		kaleoTimerName = objectInput.readUTF();

		blocking = objectInput.readBoolean();

		completionUserId = objectInput.readLong();

		completed = objectInput.readBoolean();
		completionDate = objectInput.readLong();
		workflowContext = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(kaleoTimerInstanceTokenId);

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

		if (kaleoClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoClassName);
		}

		objectOutput.writeLong(kaleoClassPK);

		objectOutput.writeLong(kaleoDefinitionId);

		objectOutput.writeLong(kaleoDefinitionVersionId);

		objectOutput.writeLong(kaleoInstanceId);

		objectOutput.writeLong(kaleoInstanceTokenId);

		objectOutput.writeLong(kaleoTaskInstanceTokenId);

		objectOutput.writeLong(kaleoTimerId);

		if (kaleoTimerName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoTimerName);
		}

		objectOutput.writeBoolean(blocking);

		objectOutput.writeLong(completionUserId);

		objectOutput.writeBoolean(completed);
		objectOutput.writeLong(completionDate);

		if (workflowContext == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(workflowContext);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoTimerInstanceTokenId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String kaleoClassName;
	public long kaleoClassPK;
	public long kaleoDefinitionId;
	public long kaleoDefinitionVersionId;
	public long kaleoInstanceId;
	public long kaleoInstanceTokenId;
	public long kaleoTaskInstanceTokenId;
	public long kaleoTimerId;
	public String kaleoTimerName;
	public boolean blocking;
	public long completionUserId;
	public boolean completed;
	public long completionDate;
	public String workflowContext;

}