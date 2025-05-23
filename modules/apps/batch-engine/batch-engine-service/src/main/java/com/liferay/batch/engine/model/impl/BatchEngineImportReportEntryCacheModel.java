/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model.impl;

import com.liferay.batch.engine.model.BatchEngineImportReportEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BatchEngineImportReportEntry in entity cache.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class BatchEngineImportReportEntryCacheModel
	implements CacheModel<BatchEngineImportReportEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchEngineImportReportEntryCacheModel)) {
			return false;
		}

		BatchEngineImportReportEntryCacheModel
			batchEngineImportReportEntryCacheModel =
				(BatchEngineImportReportEntryCacheModel)object;

		if ((batchEngineImportReportEntryId ==
				batchEngineImportReportEntryCacheModel.
					batchEngineImportReportEntryId) &&
			(mvccVersion ==
				batchEngineImportReportEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchEngineImportReportEntryId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", batchEngineImportReportEntryId=");
		sb.append(batchEngineImportReportEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", entityClassNameId=");
		sb.append(entityClassNameId);
		sb.append(", entityExternalReferenceCode=");
		sb.append(entityExternalReferenceCode);
		sb.append(", error=");
		sb.append(error);
		sb.append(", resolved=");
		sb.append(resolved);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchEngineImportReportEntry toEntityModel() {
		BatchEngineImportReportEntryImpl batchEngineImportReportEntryImpl =
			new BatchEngineImportReportEntryImpl();

		batchEngineImportReportEntryImpl.setMvccVersion(mvccVersion);
		batchEngineImportReportEntryImpl.setBatchEngineImportReportEntryId(
			batchEngineImportReportEntryId);
		batchEngineImportReportEntryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			batchEngineImportReportEntryImpl.setCreateDate(null);
		}
		else {
			batchEngineImportReportEntryImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchEngineImportReportEntryImpl.setModifiedDate(null);
		}
		else {
			batchEngineImportReportEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		batchEngineImportReportEntryImpl.setClassNameId(classNameId);
		batchEngineImportReportEntryImpl.setClassPK(classPK);
		batchEngineImportReportEntryImpl.setEntityClassNameId(
			entityClassNameId);

		if (entityExternalReferenceCode == null) {
			batchEngineImportReportEntryImpl.setEntityExternalReferenceCode("");
		}
		else {
			batchEngineImportReportEntryImpl.setEntityExternalReferenceCode(
				entityExternalReferenceCode);
		}

		if (error == null) {
			batchEngineImportReportEntryImpl.setError("");
		}
		else {
			batchEngineImportReportEntryImpl.setError(error);
		}

		batchEngineImportReportEntryImpl.setResolved(resolved);
		batchEngineImportReportEntryImpl.setType(type);

		batchEngineImportReportEntryImpl.resetOriginalValues();

		return batchEngineImportReportEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		batchEngineImportReportEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		entityClassNameId = objectInput.readLong();
		entityExternalReferenceCode = objectInput.readUTF();
		error = objectInput.readUTF();

		resolved = objectInput.readBoolean();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(batchEngineImportReportEntryId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(entityClassNameId);

		if (entityExternalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(entityExternalReferenceCode);
		}

		if (error == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(error);
		}

		objectOutput.writeBoolean(resolved);

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public long batchEngineImportReportEntryId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long entityClassNameId;
	public String entityExternalReferenceCode;
	public String error;
	public boolean resolved;
	public int type;

}