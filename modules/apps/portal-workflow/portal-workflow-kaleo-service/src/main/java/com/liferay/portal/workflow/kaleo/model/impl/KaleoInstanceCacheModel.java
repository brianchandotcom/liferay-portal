/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoInstanceCacheModel)) {
			return false;
		}

		KaleoInstanceCacheModel kaleoInstanceCacheModel =
			(KaleoInstanceCacheModel)object;

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
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
		sb.append(", kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", kaleoDefinitionName=");
		sb.append(kaleoDefinitionName);
		sb.append(", kaleoDefinitionVersion=");
		sb.append(kaleoDefinitionVersion);
		sb.append(", rootKaleoInstanceTokenId=");
		sb.append(rootKaleoInstanceTokenId);
		sb.append(", active=");
		sb.append(active);
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
		kaleoInstanceImpl.setCtCollectionId(ctCollectionId);
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

		kaleoInstanceImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoInstanceImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (kaleoDefinitionName == null) {
			kaleoInstanceImpl.setKaleoDefinitionName("");
		}
		else {
			kaleoInstanceImpl.setKaleoDefinitionName(kaleoDefinitionName);
		}

		kaleoInstanceImpl.setKaleoDefinitionVersion(kaleoDefinitionVersion);
		kaleoInstanceImpl.setRootKaleoInstanceTokenId(rootKaleoInstanceTokenId);
		kaleoInstanceImpl.setActive(active);

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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoInstanceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();
		kaleoDefinitionName = objectInput.readUTF();

		kaleoDefinitionVersion = objectInput.readInt();

		rootKaleoInstanceTokenId = objectInput.readLong();

		active = objectInput.readBoolean();
		className = objectInput.readUTF();

		classPK = objectInput.readLong();

		completed = objectInput.readBoolean();
		completionDate = objectInput.readLong();
		workflowContext = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

		objectOutput.writeLong(kaleoDefinitionId);

		objectOutput.writeLong(kaleoDefinitionVersionId);

		if (kaleoDefinitionName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoDefinitionName);
		}

		objectOutput.writeInt(kaleoDefinitionVersion);

		objectOutput.writeLong(rootKaleoInstanceTokenId);

		objectOutput.writeBoolean(active);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(workflowContext);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionId;
	public long kaleoDefinitionVersionId;
	public String kaleoDefinitionName;
	public int kaleoDefinitionVersion;
	public long rootKaleoInstanceTokenId;
	public boolean active;
	public String className;
	public long classPK;
	public boolean completed;
	public long completionDate;
	public String workflowContext;

}