/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WorkflowMetricsSLADefinitionVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WorkflowMetricsSLADefinitionVersionCacheModel
	implements CacheModel<WorkflowMetricsSLADefinitionVersion>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				WorkflowMetricsSLADefinitionVersionCacheModel)) {

			return false;
		}

		WorkflowMetricsSLADefinitionVersionCacheModel
			workflowMetricsSLADefinitionVersionCacheModel =
				(WorkflowMetricsSLADefinitionVersionCacheModel)object;

		if ((workflowMetricsSLADefinitionVersionId ==
				workflowMetricsSLADefinitionVersionCacheModel.
					workflowMetricsSLADefinitionVersionId) &&
			(mvccVersion ==
				workflowMetricsSLADefinitionVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, workflowMetricsSLADefinitionVersionId);

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
		StringBundler sb = new StringBundler(51);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", workflowMetricsSLADefinitionVersionId=");
		sb.append(workflowMetricsSLADefinitionVersionId);
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
		sb.append(", active=");
		sb.append(active);
		sb.append(", calendarKey=");
		sb.append(calendarKey);
		sb.append(", description=");
		sb.append(description);
		sb.append(", duration=");
		sb.append(duration);
		sb.append(", name=");
		sb.append(name);
		sb.append(", pauseNodeKeys=");
		sb.append(pauseNodeKeys);
		sb.append(", processId=");
		sb.append(processId);
		sb.append(", processVersion=");
		sb.append(processVersion);
		sb.append(", startNodeKeys=");
		sb.append(startNodeKeys);
		sb.append(", stopNodeKeys=");
		sb.append(stopNodeKeys);
		sb.append(", version=");
		sb.append(version);
		sb.append(", workflowMetricsSLADefinitionId=");
		sb.append(workflowMetricsSLADefinitionId);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WorkflowMetricsSLADefinitionVersion toEntityModel() {
		WorkflowMetricsSLADefinitionVersionImpl
			workflowMetricsSLADefinitionVersionImpl =
				new WorkflowMetricsSLADefinitionVersionImpl();

		workflowMetricsSLADefinitionVersionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			workflowMetricsSLADefinitionVersionImpl.setUuid("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setUuid(uuid);
		}

		workflowMetricsSLADefinitionVersionImpl.
			setWorkflowMetricsSLADefinitionVersionId(
				workflowMetricsSLADefinitionVersionId);
		workflowMetricsSLADefinitionVersionImpl.setGroupId(groupId);
		workflowMetricsSLADefinitionVersionImpl.setCompanyId(companyId);
		workflowMetricsSLADefinitionVersionImpl.setUserId(userId);

		if (userName == null) {
			workflowMetricsSLADefinitionVersionImpl.setUserName("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			workflowMetricsSLADefinitionVersionImpl.setCreateDate(null);
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			workflowMetricsSLADefinitionVersionImpl.setModifiedDate(null);
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		workflowMetricsSLADefinitionVersionImpl.setActive(active);

		if (calendarKey == null) {
			workflowMetricsSLADefinitionVersionImpl.setCalendarKey("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setCalendarKey(calendarKey);
		}

		if (description == null) {
			workflowMetricsSLADefinitionVersionImpl.setDescription("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setDescription(description);
		}

		workflowMetricsSLADefinitionVersionImpl.setDuration(duration);

		if (name == null) {
			workflowMetricsSLADefinitionVersionImpl.setName("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setName(name);
		}

		if (pauseNodeKeys == null) {
			workflowMetricsSLADefinitionVersionImpl.setPauseNodeKeys("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setPauseNodeKeys(
				pauseNodeKeys);
		}

		workflowMetricsSLADefinitionVersionImpl.setProcessId(processId);

		if (processVersion == null) {
			workflowMetricsSLADefinitionVersionImpl.setProcessVersion("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setProcessVersion(
				processVersion);
		}

		if (startNodeKeys == null) {
			workflowMetricsSLADefinitionVersionImpl.setStartNodeKeys("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setStartNodeKeys(
				startNodeKeys);
		}

		if (stopNodeKeys == null) {
			workflowMetricsSLADefinitionVersionImpl.setStopNodeKeys("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setStopNodeKeys(
				stopNodeKeys);
		}

		if (version == null) {
			workflowMetricsSLADefinitionVersionImpl.setVersion("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setVersion(version);
		}

		workflowMetricsSLADefinitionVersionImpl.
			setWorkflowMetricsSLADefinitionId(workflowMetricsSLADefinitionId);
		workflowMetricsSLADefinitionVersionImpl.setStatus(status);
		workflowMetricsSLADefinitionVersionImpl.setStatusByUserId(
			statusByUserId);

		if (statusByUserName == null) {
			workflowMetricsSLADefinitionVersionImpl.setStatusByUserName("");
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setStatusByUserName(
				statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			workflowMetricsSLADefinitionVersionImpl.setStatusDate(null);
		}
		else {
			workflowMetricsSLADefinitionVersionImpl.setStatusDate(
				new Date(statusDate));
		}

		workflowMetricsSLADefinitionVersionImpl.resetOriginalValues();

		return workflowMetricsSLADefinitionVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		workflowMetricsSLADefinitionVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		calendarKey = objectInput.readUTF();
		description = (String)objectInput.readObject();

		duration = objectInput.readLong();
		name = objectInput.readUTF();
		pauseNodeKeys = objectInput.readUTF();

		processId = objectInput.readLong();
		processVersion = objectInput.readUTF();
		startNodeKeys = objectInput.readUTF();
		stopNodeKeys = objectInput.readUTF();
		version = objectInput.readUTF();

		workflowMetricsSLADefinitionId = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(workflowMetricsSLADefinitionVersionId);

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

		objectOutput.writeBoolean(active);

		if (calendarKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(calendarKey);
		}

		if (description == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(description);
		}

		objectOutput.writeLong(duration);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (pauseNodeKeys == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(pauseNodeKeys);
		}

		objectOutput.writeLong(processId);

		if (processVersion == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(processVersion);
		}

		if (startNodeKeys == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(startNodeKeys);
		}

		if (stopNodeKeys == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(stopNodeKeys);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(workflowMetricsSLADefinitionId);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public String uuid;
	public long workflowMetricsSLADefinitionVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public String calendarKey;
	public String description;
	public long duration;
	public String name;
	public String pauseNodeKeys;
	public long processId;
	public String processVersion;
	public String startNodeKeys;
	public String stopNodeKeys;
	public String version;
	public long workflowMetricsSLADefinitionId;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}