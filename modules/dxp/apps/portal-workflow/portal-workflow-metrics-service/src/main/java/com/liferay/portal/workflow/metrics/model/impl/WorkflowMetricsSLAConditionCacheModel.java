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
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WorkflowMetricsSLACondition in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLAConditionCacheModel
	implements CacheModel<WorkflowMetricsSLACondition>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WorkflowMetricsSLAConditionCacheModel)) {
			return false;
		}

		WorkflowMetricsSLAConditionCacheModel
			workflowMetricsSLAConditionCacheModel =
				(WorkflowMetricsSLAConditionCacheModel)obj;

		if ((workflowMetricsSLAConditionId ==
				workflowMetricsSLAConditionCacheModel.
					workflowMetricsSLAConditionId) &&
			(mvccVersion ==
				workflowMetricsSLAConditionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, workflowMetricsSLAConditionId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", workflowMetricsSLAConditionId=");
		sb.append(workflowMetricsSLAConditionId);
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
		sb.append(", workflowMetricsSLADefinitionId=");
		sb.append(workflowMetricsSLADefinitionId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WorkflowMetricsSLACondition toEntityModel() {
		WorkflowMetricsSLAConditionImpl workflowMetricsSLAConditionImpl =
			new WorkflowMetricsSLAConditionImpl();

		workflowMetricsSLAConditionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			workflowMetricsSLAConditionImpl.setUuid("");
		}
		else {
			workflowMetricsSLAConditionImpl.setUuid(uuid);
		}

		workflowMetricsSLAConditionImpl.setWorkflowMetricsSLAConditionId(
			workflowMetricsSLAConditionId);
		workflowMetricsSLAConditionImpl.setGroupId(groupId);
		workflowMetricsSLAConditionImpl.setCompanyId(companyId);
		workflowMetricsSLAConditionImpl.setUserId(userId);

		if (userName == null) {
			workflowMetricsSLAConditionImpl.setUserName("");
		}
		else {
			workflowMetricsSLAConditionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			workflowMetricsSLAConditionImpl.setCreateDate(null);
		}
		else {
			workflowMetricsSLAConditionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			workflowMetricsSLAConditionImpl.setModifiedDate(null);
		}
		else {
			workflowMetricsSLAConditionImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		workflowMetricsSLAConditionImpl.setWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinitionId);

		workflowMetricsSLAConditionImpl.resetOriginalValues();

		return workflowMetricsSLAConditionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		workflowMetricsSLAConditionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		workflowMetricsSLADefinitionId = objectInput.readLong();
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

		objectOutput.writeLong(workflowMetricsSLAConditionId);

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

		objectOutput.writeLong(workflowMetricsSLADefinitionId);
	}

	public long mvccVersion;
	public String uuid;
	public long workflowMetricsSLAConditionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long workflowMetricsSLADefinitionId;

}