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

package com.liferay.portal.workflow.kaleo.forms.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoProcess in entity cache.
 *
 * @author Marcellus Tavares
 * @see KaleoProcess
 * @generated
 */
@ProviderType
public class KaleoProcessCacheModel implements CacheModel<KaleoProcess>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoProcessCacheModel)) {
			return false;
		}

		KaleoProcessCacheModel kaleoProcessCacheModel = (KaleoProcessCacheModel)obj;

		if (kaleoProcessId == kaleoProcessCacheModel.kaleoProcessId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, kaleoProcessId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", kaleoProcessId=");
		sb.append(kaleoProcessId);
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
		sb.append(", DDLRecordSetId=");
		sb.append(DDLRecordSetId);
		sb.append(", DDMTemplateId=");
		sb.append(DDMTemplateId);
		sb.append(", workflowDefinitionName=");
		sb.append(workflowDefinitionName);
		sb.append(", workflowDefinitionVersion=");
		sb.append(workflowDefinitionVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoProcess toEntityModel() {
		KaleoProcessImpl kaleoProcessImpl = new KaleoProcessImpl();

		if (uuid == null) {
			kaleoProcessImpl.setUuid("");
		}
		else {
			kaleoProcessImpl.setUuid(uuid);
		}

		kaleoProcessImpl.setKaleoProcessId(kaleoProcessId);
		kaleoProcessImpl.setGroupId(groupId);
		kaleoProcessImpl.setCompanyId(companyId);
		kaleoProcessImpl.setUserId(userId);

		if (userName == null) {
			kaleoProcessImpl.setUserName("");
		}
		else {
			kaleoProcessImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoProcessImpl.setCreateDate(null);
		}
		else {
			kaleoProcessImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoProcessImpl.setModifiedDate(null);
		}
		else {
			kaleoProcessImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoProcessImpl.setDDLRecordSetId(DDLRecordSetId);
		kaleoProcessImpl.setDDMTemplateId(DDMTemplateId);

		if (workflowDefinitionName == null) {
			kaleoProcessImpl.setWorkflowDefinitionName("");
		}
		else {
			kaleoProcessImpl.setWorkflowDefinitionName(workflowDefinitionName);
		}

		kaleoProcessImpl.setWorkflowDefinitionVersion(workflowDefinitionVersion);

		kaleoProcessImpl.resetOriginalValues();

		return kaleoProcessImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		kaleoProcessId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		DDLRecordSetId = objectInput.readLong();

		DDMTemplateId = objectInput.readLong();
		workflowDefinitionName = objectInput.readUTF();

		workflowDefinitionVersion = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(kaleoProcessId);

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

		objectOutput.writeLong(DDLRecordSetId);

		objectOutput.writeLong(DDMTemplateId);

		if (workflowDefinitionName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(workflowDefinitionName);
		}

		objectOutput.writeInt(workflowDefinitionVersion);
	}

	public String uuid;
	public long kaleoProcessId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long DDLRecordSetId;
	public long DDMTemplateId;
	public String workflowDefinitionName;
	public int workflowDefinitionVersion;
}