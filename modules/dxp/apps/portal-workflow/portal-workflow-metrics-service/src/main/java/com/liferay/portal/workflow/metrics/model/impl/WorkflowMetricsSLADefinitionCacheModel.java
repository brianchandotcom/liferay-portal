/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WorkflowMetricsSLADefinition in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLADefinitionCacheModel
	implements CacheModel<WorkflowMetricsSLADefinition>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WorkflowMetricsSLADefinitionCacheModel)) {
			return false;
		}

		WorkflowMetricsSLADefinitionCacheModel
			workflowMetricsSLADefinitionCacheModel =
				(WorkflowMetricsSLADefinitionCacheModel)obj;

		if ((workflowMetricsSLADefinitionId ==
				workflowMetricsSLADefinitionCacheModel.
					workflowMetricsSLADefinitionId) &&
			(mvccVersion ==
				workflowMetricsSLADefinitionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, workflowMetricsSLADefinitionId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", workflowMetricsSLADefinitionId=");
		sb.append(workflowMetricsSLADefinitionId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", duration=");
		sb.append(duration);
		sb.append(", processId=");
		sb.append(processId);
		sb.append(", pauseNodeNames=");
		sb.append(pauseNodeNames);
		sb.append(", startNodeNames=");
		sb.append(startNodeNames);
		sb.append(", stopNodeNames=");
		sb.append(stopNodeNames);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WorkflowMetricsSLADefinition toEntityModel() {
		WorkflowMetricsSLADefinitionImpl workflowMetricsSLADefinitionImpl =
			new WorkflowMetricsSLADefinitionImpl();

		workflowMetricsSLADefinitionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			workflowMetricsSLADefinitionImpl.setUuid("");
		}
		else {
			workflowMetricsSLADefinitionImpl.setUuid(uuid);
		}

		workflowMetricsSLADefinitionImpl.setWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId);
		workflowMetricsSLADefinitionImpl.setGroupId(groupId);
		workflowMetricsSLADefinitionImpl.setCompanyId(companyId);
		workflowMetricsSLADefinitionImpl.setUserId(userId);

		if (userName == null) {
			workflowMetricsSLADefinitionImpl.setUserName("");
		}
		else {
			workflowMetricsSLADefinitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			workflowMetricsSLADefinitionImpl.setCreateDate(null);
		}
		else {
			workflowMetricsSLADefinitionImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			workflowMetricsSLADefinitionImpl.setModifiedDate(null);
		}
		else {
			workflowMetricsSLADefinitionImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (name == null) {
			workflowMetricsSLADefinitionImpl.setName("");
		}
		else {
			workflowMetricsSLADefinitionImpl.setName(name);
		}

		if (description == null) {
			workflowMetricsSLADefinitionImpl.setDescription("");
		}
		else {
			workflowMetricsSLADefinitionImpl.setDescription(description);
		}

		workflowMetricsSLADefinitionImpl.setDuration(duration);
		workflowMetricsSLADefinitionImpl.setProcessId(processId);

		if (pauseNodeNames == null) {
			workflowMetricsSLADefinitionImpl.setPauseNodeNames("");
		}
		else {
			workflowMetricsSLADefinitionImpl.setPauseNodeNames(pauseNodeNames);
		}

		if (startNodeNames == null) {
			workflowMetricsSLADefinitionImpl.setStartNodeNames("");
		}
		else {
			workflowMetricsSLADefinitionImpl.setStartNodeNames(startNodeNames);
		}

		if (stopNodeNames == null) {
			workflowMetricsSLADefinitionImpl.setStopNodeNames("");
		}
		else {
			workflowMetricsSLADefinitionImpl.setStopNodeNames(stopNodeNames);
		}

		workflowMetricsSLADefinitionImpl.resetOriginalValues();

		return workflowMetricsSLADefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		workflowMetricsSLADefinitionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		duration = objectInput.readLong();

		processId = objectInput.readLong();
		pauseNodeNames = objectInput.readUTF();
		startNodeNames = objectInput.readUTF();
		stopNodeNames = objectInput.readUTF();
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

		objectOutput.writeLong(workflowMetricsSLADefinitionId);

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

		objectOutput.writeLong(duration);

		objectOutput.writeLong(processId);

		if (pauseNodeNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(pauseNodeNames);
		}

		if (startNodeNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(startNodeNames);
		}

		if (stopNodeNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(stopNodeNames);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long workflowMetricsSLADefinitionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public long duration;
	public long processId;
	public String pauseNodeNames;
	public String startNodeNames;
	public String stopNodeNames;

}