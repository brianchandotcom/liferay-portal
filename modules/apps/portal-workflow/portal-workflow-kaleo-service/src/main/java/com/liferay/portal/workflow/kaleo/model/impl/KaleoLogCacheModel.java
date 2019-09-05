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
import com.liferay.portal.workflow.kaleo.model.KaleoLog;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoLog in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoLogCacheModel
	implements CacheModel<KaleoLog>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoLogCacheModel)) {
			return false;
		}

		KaleoLogCacheModel kaleoLogCacheModel = (KaleoLogCacheModel)obj;

		if ((kaleoLogId == kaleoLogCacheModel.kaleoLogId) &&
			(mvccVersion == kaleoLogCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoLogId);

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
		StringBundler sb = new StringBundler(63);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", kaleoLogId=");
		sb.append(kaleoLogId);
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
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", kaleoInstanceId=");
		sb.append(kaleoInstanceId);
		sb.append(", kaleoInstanceTokenId=");
		sb.append(kaleoInstanceTokenId);
		sb.append(", kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);
		sb.append(", kaleoNodeName=");
		sb.append(kaleoNodeName);
		sb.append(", terminalKaleoNode=");
		sb.append(terminalKaleoNode);
		sb.append(", kaleoActionId=");
		sb.append(kaleoActionId);
		sb.append(", kaleoActionName=");
		sb.append(kaleoActionName);
		sb.append(", kaleoActionDescription=");
		sb.append(kaleoActionDescription);
		sb.append(", previousKaleoNodeId=");
		sb.append(previousKaleoNodeId);
		sb.append(", previousKaleoNodeName=");
		sb.append(previousKaleoNodeName);
		sb.append(", previousAssigneeClassName=");
		sb.append(previousAssigneeClassName);
		sb.append(", previousAssigneeClassPK=");
		sb.append(previousAssigneeClassPK);
		sb.append(", currentAssigneeClassName=");
		sb.append(currentAssigneeClassName);
		sb.append(", currentAssigneeClassPK=");
		sb.append(currentAssigneeClassPK);
		sb.append(", type=");
		sb.append(type);
		sb.append(", comment=");
		sb.append(comment);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", duration=");
		sb.append(duration);
		sb.append(", workflowContext=");
		sb.append(workflowContext);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoLog toEntityModel() {
		KaleoLogImpl kaleoLogImpl = new KaleoLogImpl();

		kaleoLogImpl.setMvccVersion(mvccVersion);
		kaleoLogImpl.setKaleoLogId(kaleoLogId);
		kaleoLogImpl.setGroupId(groupId);
		kaleoLogImpl.setCompanyId(companyId);
		kaleoLogImpl.setUserId(userId);

		if (userName == null) {
			kaleoLogImpl.setUserName("");
		}
		else {
			kaleoLogImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoLogImpl.setCreateDate(null);
		}
		else {
			kaleoLogImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoLogImpl.setModifiedDate(null);
		}
		else {
			kaleoLogImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (kaleoClassName == null) {
			kaleoLogImpl.setKaleoClassName("");
		}
		else {
			kaleoLogImpl.setKaleoClassName(kaleoClassName);
		}

		kaleoLogImpl.setKaleoClassPK(kaleoClassPK);
		kaleoLogImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoLogImpl.setKaleoInstanceId(kaleoInstanceId);
		kaleoLogImpl.setKaleoInstanceTokenId(kaleoInstanceTokenId);
		kaleoLogImpl.setKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);

		if (kaleoNodeName == null) {
			kaleoLogImpl.setKaleoNodeName("");
		}
		else {
			kaleoLogImpl.setKaleoNodeName(kaleoNodeName);
		}

		kaleoLogImpl.setTerminalKaleoNode(terminalKaleoNode);
		kaleoLogImpl.setKaleoActionId(kaleoActionId);

		if (kaleoActionName == null) {
			kaleoLogImpl.setKaleoActionName("");
		}
		else {
			kaleoLogImpl.setKaleoActionName(kaleoActionName);
		}

		if (kaleoActionDescription == null) {
			kaleoLogImpl.setKaleoActionDescription("");
		}
		else {
			kaleoLogImpl.setKaleoActionDescription(kaleoActionDescription);
		}

		kaleoLogImpl.setPreviousKaleoNodeId(previousKaleoNodeId);

		if (previousKaleoNodeName == null) {
			kaleoLogImpl.setPreviousKaleoNodeName("");
		}
		else {
			kaleoLogImpl.setPreviousKaleoNodeName(previousKaleoNodeName);
		}

		if (previousAssigneeClassName == null) {
			kaleoLogImpl.setPreviousAssigneeClassName("");
		}
		else {
			kaleoLogImpl.setPreviousAssigneeClassName(
				previousAssigneeClassName);
		}

		kaleoLogImpl.setPreviousAssigneeClassPK(previousAssigneeClassPK);

		if (currentAssigneeClassName == null) {
			kaleoLogImpl.setCurrentAssigneeClassName("");
		}
		else {
			kaleoLogImpl.setCurrentAssigneeClassName(currentAssigneeClassName);
		}

		kaleoLogImpl.setCurrentAssigneeClassPK(currentAssigneeClassPK);

		if (type == null) {
			kaleoLogImpl.setType("");
		}
		else {
			kaleoLogImpl.setType(type);
		}

		if (comment == null) {
			kaleoLogImpl.setComment("");
		}
		else {
			kaleoLogImpl.setComment(comment);
		}

		if (startDate == Long.MIN_VALUE) {
			kaleoLogImpl.setStartDate(null);
		}
		else {
			kaleoLogImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			kaleoLogImpl.setEndDate(null);
		}
		else {
			kaleoLogImpl.setEndDate(new Date(endDate));
		}

		kaleoLogImpl.setDuration(duration);

		if (workflowContext == null) {
			kaleoLogImpl.setWorkflowContext("");
		}
		else {
			kaleoLogImpl.setWorkflowContext(workflowContext);
		}

		kaleoLogImpl.resetOriginalValues();

		return kaleoLogImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		kaleoLogId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		kaleoClassName = objectInput.readUTF();

		kaleoClassPK = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		kaleoInstanceId = objectInput.readLong();

		kaleoInstanceTokenId = objectInput.readLong();

		kaleoTaskInstanceTokenId = objectInput.readLong();
		kaleoNodeName = objectInput.readUTF();

		terminalKaleoNode = objectInput.readBoolean();

		kaleoActionId = objectInput.readLong();
		kaleoActionName = objectInput.readUTF();
		kaleoActionDescription = objectInput.readUTF();

		previousKaleoNodeId = objectInput.readLong();
		previousKaleoNodeName = objectInput.readUTF();
		previousAssigneeClassName = objectInput.readUTF();

		previousAssigneeClassPK = objectInput.readLong();
		currentAssigneeClassName = objectInput.readUTF();

		currentAssigneeClassPK = objectInput.readLong();
		type = objectInput.readUTF();
		comment = objectInput.readUTF();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();

		duration = objectInput.readLong();
		workflowContext = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(kaleoLogId);

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

		objectOutput.writeLong(kaleoDefinitionVersionId);

		objectOutput.writeLong(kaleoInstanceId);

		objectOutput.writeLong(kaleoInstanceTokenId);

		objectOutput.writeLong(kaleoTaskInstanceTokenId);

		if (kaleoNodeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoNodeName);
		}

		objectOutput.writeBoolean(terminalKaleoNode);

		objectOutput.writeLong(kaleoActionId);

		if (kaleoActionName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoActionName);
		}

		if (kaleoActionDescription == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoActionDescription);
		}

		objectOutput.writeLong(previousKaleoNodeId);

		if (previousKaleoNodeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(previousKaleoNodeName);
		}

		if (previousAssigneeClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(previousAssigneeClassName);
		}

		objectOutput.writeLong(previousAssigneeClassPK);

		if (currentAssigneeClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(currentAssigneeClassName);
		}

		objectOutput.writeLong(currentAssigneeClassPK);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (comment == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(comment);
		}

		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);

		objectOutput.writeLong(duration);

		if (workflowContext == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(workflowContext);
		}
	}

	public long mvccVersion;
	public long kaleoLogId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String kaleoClassName;
	public long kaleoClassPK;
	public long kaleoDefinitionVersionId;
	public long kaleoInstanceId;
	public long kaleoInstanceTokenId;
	public long kaleoTaskInstanceTokenId;
	public String kaleoNodeName;
	public boolean terminalKaleoNode;
	public long kaleoActionId;
	public String kaleoActionName;
	public String kaleoActionDescription;
	public long previousKaleoNodeId;
	public String previousKaleoNodeName;
	public String previousAssigneeClassName;
	public long previousAssigneeClassPK;
	public String currentAssigneeClassName;
	public long currentAssigneeClassPK;
	public String type;
	public String comment;
	public long startDate;
	public long endDate;
	public long duration;
	public String workflowContext;

}