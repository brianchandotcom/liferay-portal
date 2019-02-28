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
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WorkflowMetricsSLACalendar in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLACalendarCacheModel
	implements CacheModel<WorkflowMetricsSLACalendar>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WorkflowMetricsSLACalendarCacheModel)) {
			return false;
		}

		WorkflowMetricsSLACalendarCacheModel
			workflowMetricsSLACalendarCacheModel =
				(WorkflowMetricsSLACalendarCacheModel)obj;

		if ((workflowMetricsSLACalendarId ==
				workflowMetricsSLACalendarCacheModel.
					workflowMetricsSLACalendarId) &&
			(mvccVersion == workflowMetricsSLACalendarCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, workflowMetricsSLACalendarId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", workflowMetricsSLACalendarId=");
		sb.append(workflowMetricsSLACalendarId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WorkflowMetricsSLACalendar toEntityModel() {
		WorkflowMetricsSLACalendarImpl workflowMetricsSLACalendarImpl =
			new WorkflowMetricsSLACalendarImpl();

		workflowMetricsSLACalendarImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			workflowMetricsSLACalendarImpl.setUuid("");
		}
		else {
			workflowMetricsSLACalendarImpl.setUuid(uuid);
		}

		workflowMetricsSLACalendarImpl.setWorkflowMetricsSLACalendarId(
			workflowMetricsSLACalendarId);
		workflowMetricsSLACalendarImpl.setGroupId(groupId);
		workflowMetricsSLACalendarImpl.setCompanyId(companyId);
		workflowMetricsSLACalendarImpl.setUserId(userId);

		if (userName == null) {
			workflowMetricsSLACalendarImpl.setUserName("");
		}
		else {
			workflowMetricsSLACalendarImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			workflowMetricsSLACalendarImpl.setCreateDate(null);
		}
		else {
			workflowMetricsSLACalendarImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			workflowMetricsSLACalendarImpl.setModifiedDate(null);
		}
		else {
			workflowMetricsSLACalendarImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		workflowMetricsSLACalendarImpl.resetOriginalValues();

		return workflowMetricsSLACalendarImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		workflowMetricsSLACalendarId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
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

		objectOutput.writeLong(workflowMetricsSLACalendarId);

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
	}

	public long mvccVersion;
	public String uuid;
	public long workflowMetricsSLACalendarId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;

}