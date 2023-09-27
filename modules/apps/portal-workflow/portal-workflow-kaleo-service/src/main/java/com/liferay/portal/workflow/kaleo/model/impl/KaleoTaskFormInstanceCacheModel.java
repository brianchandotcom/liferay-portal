/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoTaskFormInstance in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTaskFormInstanceCacheModel
	implements CacheModel<KaleoTaskFormInstance>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoTaskFormInstanceCacheModel)) {
			return false;
		}

		KaleoTaskFormInstanceCacheModel kaleoTaskFormInstanceCacheModel =
			(KaleoTaskFormInstanceCacheModel)object;

		if ((kaleoTaskFormInstanceId ==
				kaleoTaskFormInstanceCacheModel.kaleoTaskFormInstanceId) &&
			(mvccVersion == kaleoTaskFormInstanceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoTaskFormInstanceId);

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
		sb.append(", kaleoTaskFormInstanceId=");
		sb.append(kaleoTaskFormInstanceId);
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
		sb.append(", kaleoTaskId=");
		sb.append(kaleoTaskId);
		sb.append(", kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);
		sb.append(", kaleoTaskFormId=");
		sb.append(kaleoTaskFormId);
		sb.append(", formValues=");
		sb.append(formValues);
		sb.append(", formValueEntryGroupId=");
		sb.append(formValueEntryGroupId);
		sb.append(", formValueEntryId=");
		sb.append(formValueEntryId);
		sb.append(", formValueEntryUuid=");
		sb.append(formValueEntryUuid);
		sb.append(", metadata=");
		sb.append(metadata);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoTaskFormInstance toEntityModel() {
		KaleoTaskFormInstanceImpl kaleoTaskFormInstanceImpl =
			new KaleoTaskFormInstanceImpl();

		kaleoTaskFormInstanceImpl.setMvccVersion(mvccVersion);
		kaleoTaskFormInstanceImpl.setCtCollectionId(ctCollectionId);
		kaleoTaskFormInstanceImpl.setKaleoTaskFormInstanceId(
			kaleoTaskFormInstanceId);
		kaleoTaskFormInstanceImpl.setGroupId(groupId);
		kaleoTaskFormInstanceImpl.setCompanyId(companyId);
		kaleoTaskFormInstanceImpl.setUserId(userId);

		if (userName == null) {
			kaleoTaskFormInstanceImpl.setUserName("");
		}
		else {
			kaleoTaskFormInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoTaskFormInstanceImpl.setCreateDate(null);
		}
		else {
			kaleoTaskFormInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoTaskFormInstanceImpl.setModifiedDate(null);
		}
		else {
			kaleoTaskFormInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		kaleoTaskFormInstanceImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoTaskFormInstanceImpl.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
		kaleoTaskFormInstanceImpl.setKaleoInstanceId(kaleoInstanceId);
		kaleoTaskFormInstanceImpl.setKaleoTaskId(kaleoTaskId);
		kaleoTaskFormInstanceImpl.setKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId);
		kaleoTaskFormInstanceImpl.setKaleoTaskFormId(kaleoTaskFormId);

		if (formValues == null) {
			kaleoTaskFormInstanceImpl.setFormValues("");
		}
		else {
			kaleoTaskFormInstanceImpl.setFormValues(formValues);
		}

		kaleoTaskFormInstanceImpl.setFormValueEntryGroupId(
			formValueEntryGroupId);
		kaleoTaskFormInstanceImpl.setFormValueEntryId(formValueEntryId);

		if (formValueEntryUuid == null) {
			kaleoTaskFormInstanceImpl.setFormValueEntryUuid("");
		}
		else {
			kaleoTaskFormInstanceImpl.setFormValueEntryUuid(formValueEntryUuid);
		}

		if (metadata == null) {
			kaleoTaskFormInstanceImpl.setMetadata("");
		}
		else {
			kaleoTaskFormInstanceImpl.setMetadata(metadata);
		}

		kaleoTaskFormInstanceImpl.resetOriginalValues();

		return kaleoTaskFormInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoTaskFormInstanceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		kaleoInstanceId = objectInput.readLong();

		kaleoTaskId = objectInput.readLong();

		kaleoTaskInstanceTokenId = objectInput.readLong();

		kaleoTaskFormId = objectInput.readLong();
		formValues = objectInput.readUTF();

		formValueEntryGroupId = objectInput.readLong();

		formValueEntryId = objectInput.readLong();
		formValueEntryUuid = objectInput.readUTF();
		metadata = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(kaleoTaskFormInstanceId);

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

		objectOutput.writeLong(kaleoTaskId);

		objectOutput.writeLong(kaleoTaskInstanceTokenId);

		objectOutput.writeLong(kaleoTaskFormId);

		if (formValues == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(formValues);
		}

		objectOutput.writeLong(formValueEntryGroupId);

		objectOutput.writeLong(formValueEntryId);

		if (formValueEntryUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(formValueEntryUuid);
		}

		if (metadata == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(metadata);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoTaskFormInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionId;
	public long kaleoDefinitionVersionId;
	public long kaleoInstanceId;
	public long kaleoTaskId;
	public long kaleoTaskInstanceTokenId;
	public long kaleoTaskFormId;
	public String formValues;
	public long formValueEntryGroupId;
	public long formValueEntryId;
	public String formValueEntryUuid;
	public String metadata;

}