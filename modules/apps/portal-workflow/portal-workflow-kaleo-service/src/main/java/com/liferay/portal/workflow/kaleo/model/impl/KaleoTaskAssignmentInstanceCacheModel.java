/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTaskAssignmentInstance in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTaskAssignmentInstanceCacheModel
	implements CacheModel<KaleoTaskAssignmentInstance>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoTaskAssignmentInstanceCacheModel)) {
			return false;
		}

		KaleoTaskAssignmentInstanceCacheModel
			kaleoTaskAssignmentInstanceCacheModel =
				(KaleoTaskAssignmentInstanceCacheModel)object;

		if ((kaleoTaskAssignmentInstanceId ==
				kaleoTaskAssignmentInstanceCacheModel.
					kaleoTaskAssignmentInstanceId) &&
			(mvccVersion ==
				kaleoTaskAssignmentInstanceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoTaskAssignmentInstanceId);

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", kaleoTaskAssignmentInstanceId=");
		sb.append(kaleoTaskAssignmentInstanceId);
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
		sb.append(", kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);
		sb.append(", kaleoTaskId=");
		sb.append(kaleoTaskId);
		sb.append(", kaleoTaskName=");
		sb.append(kaleoTaskName);
		sb.append(", assigneeClassName=");
		sb.append(assigneeClassName);
		sb.append(", assigneeClassPK=");
		sb.append(assigneeClassPK);
		sb.append(", completed=");
		sb.append(completed);
		sb.append(", completionDate=");
		sb.append(completionDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoTaskAssignmentInstance toEntityModel() {
		KaleoTaskAssignmentInstanceImpl kaleoTaskAssignmentInstanceImpl =
			new KaleoTaskAssignmentInstanceImpl();

		kaleoTaskAssignmentInstanceImpl.setMvccVersion(mvccVersion);
		kaleoTaskAssignmentInstanceImpl.setCtCollectionId(ctCollectionId);
		kaleoTaskAssignmentInstanceImpl.setKaleoTaskAssignmentInstanceId(
			kaleoTaskAssignmentInstanceId);
		kaleoTaskAssignmentInstanceImpl.setGroupId(groupId);
		kaleoTaskAssignmentInstanceImpl.setCompanyId(companyId);
		kaleoTaskAssignmentInstanceImpl.setUserId(userId);

		if (userName == null) {
			kaleoTaskAssignmentInstanceImpl.setUserName("");
		}
		else {
			kaleoTaskAssignmentInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTaskAssignmentInstanceImpl.setCreateDate(null);
		}
		else {
			kaleoTaskAssignmentInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTaskAssignmentInstanceImpl.setModifiedDate(null);
		}
		else {
			kaleoTaskAssignmentInstanceImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		kaleoTaskAssignmentInstanceImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoTaskAssignmentInstanceImpl.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
		kaleoTaskAssignmentInstanceImpl.setKaleoInstanceId(kaleoInstanceId);
		kaleoTaskAssignmentInstanceImpl.setKaleoInstanceTokenId(
			kaleoInstanceTokenId);
		kaleoTaskAssignmentInstanceImpl.setKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
		kaleoTaskAssignmentInstanceImpl.setKaleoTaskId(kaleoTaskId);

		if (kaleoTaskName == null) {
			kaleoTaskAssignmentInstanceImpl.setKaleoTaskName("");
		}
		else {
			kaleoTaskAssignmentInstanceImpl.setKaleoTaskName(kaleoTaskName);
		}

		if (assigneeClassName == null) {
			kaleoTaskAssignmentInstanceImpl.setAssigneeClassName("");
		}
		else {
			kaleoTaskAssignmentInstanceImpl.setAssigneeClassName(
				assigneeClassName);
		}

		kaleoTaskAssignmentInstanceImpl.setAssigneeClassPK(assigneeClassPK);
		kaleoTaskAssignmentInstanceImpl.setCompleted(completed);

		if (completionDate == Long.MIN_VALUE) {
			kaleoTaskAssignmentInstanceImpl.setCompletionDate(null);
		}
		else {
			kaleoTaskAssignmentInstanceImpl.setCompletionDate(
				new Date(completionDate));
		}

		kaleoTaskAssignmentInstanceImpl.resetOriginalValues();

		return kaleoTaskAssignmentInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoTaskAssignmentInstanceId = objectInput.readLong();

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

		kaleoTaskInstanceTokenId = objectInput.readLong();

		kaleoTaskId = objectInput.readLong();
		kaleoTaskName = objectInput.readUTF();
		assigneeClassName = objectInput.readUTF();

		assigneeClassPK = objectInput.readLong();

		completed = objectInput.readBoolean();
		completionDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(kaleoTaskAssignmentInstanceId);

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

		objectOutput.writeLong(kaleoTaskInstanceTokenId);

		objectOutput.writeLong(kaleoTaskId);

		if (kaleoTaskName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoTaskName);
		}

		if (assigneeClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(assigneeClassName);
		}

		objectOutput.writeLong(assigneeClassPK);

		objectOutput.writeBoolean(completed);
		objectOutput.writeLong(completionDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoTaskAssignmentInstanceId;
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
	public long kaleoTaskInstanceTokenId;
	public long kaleoTaskId;
	public String kaleoTaskName;
	public String assigneeClassName;
	public long assigneeClassPK;
	public boolean completed;
	public long completionDate;

}