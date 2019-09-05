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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing WorkflowDefinitionLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WorkflowDefinitionLinkCacheModel
	implements CacheModel<WorkflowDefinitionLink>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WorkflowDefinitionLinkCacheModel)) {
			return false;
		}

		WorkflowDefinitionLinkCacheModel workflowDefinitionLinkCacheModel =
			(WorkflowDefinitionLinkCacheModel)obj;

		if ((workflowDefinitionLinkId ==
				workflowDefinitionLinkCacheModel.workflowDefinitionLinkId) &&
			(mvccVersion == workflowDefinitionLinkCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, workflowDefinitionLinkId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", workflowDefinitionLinkId=");
		sb.append(workflowDefinitionLinkId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", typePK=");
		sb.append(typePK);
		sb.append(", workflowDefinitionName=");
		sb.append(workflowDefinitionName);
		sb.append(", workflowDefinitionVersion=");
		sb.append(workflowDefinitionVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WorkflowDefinitionLink toEntityModel() {
		WorkflowDefinitionLinkImpl workflowDefinitionLinkImpl =
			new WorkflowDefinitionLinkImpl();

		workflowDefinitionLinkImpl.setMvccVersion(mvccVersion);
		workflowDefinitionLinkImpl.setWorkflowDefinitionLinkId(
			workflowDefinitionLinkId);
		workflowDefinitionLinkImpl.setGroupId(groupId);
		workflowDefinitionLinkImpl.setCompanyId(companyId);
		workflowDefinitionLinkImpl.setUserId(userId);

		if (userName == null) {
			workflowDefinitionLinkImpl.setUserName("");
		}
		else {
			workflowDefinitionLinkImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			workflowDefinitionLinkImpl.setCreateDate(null);
		}
		else {
			workflowDefinitionLinkImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			workflowDefinitionLinkImpl.setModifiedDate(null);
		}
		else {
			workflowDefinitionLinkImpl.setModifiedDate(new Date(modifiedDate));
		}

		workflowDefinitionLinkImpl.setClassNameId(classNameId);
		workflowDefinitionLinkImpl.setClassPK(classPK);
		workflowDefinitionLinkImpl.setTypePK(typePK);

		if (workflowDefinitionName == null) {
			workflowDefinitionLinkImpl.setWorkflowDefinitionName("");
		}
		else {
			workflowDefinitionLinkImpl.setWorkflowDefinitionName(
				workflowDefinitionName);
		}

		workflowDefinitionLinkImpl.setWorkflowDefinitionVersion(
			workflowDefinitionVersion);

		workflowDefinitionLinkImpl.resetOriginalValues();

		return workflowDefinitionLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		workflowDefinitionLinkId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		typePK = objectInput.readLong();
		workflowDefinitionName = objectInput.readUTF();

		workflowDefinitionVersion = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(workflowDefinitionLinkId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(typePK);

		if (workflowDefinitionName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(workflowDefinitionName);
		}

		objectOutput.writeInt(workflowDefinitionVersion);
	}

	public long mvccVersion;
	public long workflowDefinitionLinkId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long typePK;
	public String workflowDefinitionName;
	public int workflowDefinitionVersion;

}