/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTaskForm in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTaskFormCacheModel
	implements CacheModel<KaleoTaskForm>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoTaskFormCacheModel)) {
			return false;
		}

		KaleoTaskFormCacheModel kaleoTaskFormCacheModel =
			(KaleoTaskFormCacheModel)object;

		if ((kaleoTaskFormId == kaleoTaskFormCacheModel.kaleoTaskFormId) &&
			(mvccVersion == kaleoTaskFormCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoTaskFormId);

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
		sb.append(", kaleoTaskFormId=");
		sb.append(kaleoTaskFormId);
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
		sb.append(", kaleoNodeId=");
		sb.append(kaleoNodeId);
		sb.append(", kaleoTaskId=");
		sb.append(kaleoTaskId);
		sb.append(", kaleoTaskName=");
		sb.append(kaleoTaskName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", formCompanyId=");
		sb.append(formCompanyId);
		sb.append(", formDefinition=");
		sb.append(formDefinition);
		sb.append(", formGroupId=");
		sb.append(formGroupId);
		sb.append(", formId=");
		sb.append(formId);
		sb.append(", formUuid=");
		sb.append(formUuid);
		sb.append(", metadata=");
		sb.append(metadata);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoTaskForm toEntityModel() {
		KaleoTaskFormImpl kaleoTaskFormImpl = new KaleoTaskFormImpl();

		kaleoTaskFormImpl.setMvccVersion(mvccVersion);
		kaleoTaskFormImpl.setCtCollectionId(ctCollectionId);
		kaleoTaskFormImpl.setKaleoTaskFormId(kaleoTaskFormId);
		kaleoTaskFormImpl.setGroupId(groupId);
		kaleoTaskFormImpl.setCompanyId(companyId);
		kaleoTaskFormImpl.setUserId(userId);

		if (userName == null) {
			kaleoTaskFormImpl.setUserName("");
		}
		else {
			kaleoTaskFormImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTaskFormImpl.setCreateDate(null);
		}
		else {
			kaleoTaskFormImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTaskFormImpl.setModifiedDate(null);
		}
		else {
			kaleoTaskFormImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoTaskFormImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoTaskFormImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoTaskFormImpl.setKaleoNodeId(kaleoNodeId);
		kaleoTaskFormImpl.setKaleoTaskId(kaleoTaskId);

		if (kaleoTaskName == null) {
			kaleoTaskFormImpl.setKaleoTaskName("");
		}
		else {
			kaleoTaskFormImpl.setKaleoTaskName(kaleoTaskName);
		}

		if (name == null) {
			kaleoTaskFormImpl.setName("");
		}
		else {
			kaleoTaskFormImpl.setName(name);
		}

		if (description == null) {
			kaleoTaskFormImpl.setDescription("");
		}
		else {
			kaleoTaskFormImpl.setDescription(description);
		}

		kaleoTaskFormImpl.setFormCompanyId(formCompanyId);

		if (formDefinition == null) {
			kaleoTaskFormImpl.setFormDefinition("");
		}
		else {
			kaleoTaskFormImpl.setFormDefinition(formDefinition);
		}

		kaleoTaskFormImpl.setFormGroupId(formGroupId);
		kaleoTaskFormImpl.setFormId(formId);

		if (formUuid == null) {
			kaleoTaskFormImpl.setFormUuid("");
		}
		else {
			kaleoTaskFormImpl.setFormUuid(formUuid);
		}

		if (metadata == null) {
			kaleoTaskFormImpl.setMetadata("");
		}
		else {
			kaleoTaskFormImpl.setMetadata(metadata);
		}

		kaleoTaskFormImpl.setPriority(priority);

		kaleoTaskFormImpl.resetOriginalValues();

		return kaleoTaskFormImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoTaskFormId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		kaleoNodeId = objectInput.readLong();

		kaleoTaskId = objectInput.readLong();
		kaleoTaskName = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		formCompanyId = objectInput.readLong();
		formDefinition = objectInput.readUTF();

		formGroupId = objectInput.readLong();

		formId = objectInput.readLong();
		formUuid = objectInput.readUTF();
		metadata = objectInput.readUTF();

		priority = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(kaleoTaskFormId);

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

		objectOutput.writeLong(kaleoNodeId);

		objectOutput.writeLong(kaleoTaskId);

		if (kaleoTaskName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoTaskName);
		}

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

		objectOutput.writeLong(formCompanyId);

		if (formDefinition == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(formDefinition);
		}

		objectOutput.writeLong(formGroupId);

		objectOutput.writeLong(formId);

		if (formUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(formUuid);
		}

		if (metadata == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(metadata);
		}

		objectOutput.writeInt(priority);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoTaskFormId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionId;
	public long kaleoDefinitionVersionId;
	public long kaleoNodeId;
	public long kaleoTaskId;
	public String kaleoTaskName;
	public String name;
	public String description;
	public long formCompanyId;
	public String formDefinition;
	public long formGroupId;
	public long formId;
	public String formUuid;
	public String metadata;
	public int priority;

}