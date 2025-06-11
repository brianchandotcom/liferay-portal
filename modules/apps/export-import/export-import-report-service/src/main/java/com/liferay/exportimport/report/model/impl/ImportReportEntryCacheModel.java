/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.model.impl;

import com.liferay.exportimport.report.model.ImportReportEntry;
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
 * The cache model class for representing ImportReportEntry in entity cache.
 *
 * @author Carlos Correa
 * @generated
 */
public class ImportReportEntryCacheModel
	implements CacheModel<ImportReportEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ImportReportEntryCacheModel)) {
			return false;
		}

		ImportReportEntryCacheModel importReportEntryCacheModel =
			(ImportReportEntryCacheModel)object;

		if ((importReportEntryId ==
				importReportEntryCacheModel.importReportEntryId) &&
			(mvccVersion == importReportEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, importReportEntryId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", importReportEntryId=");
		sb.append(importReportEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", errorStacktrace=");
		sb.append(errorStacktrace);
		sb.append(", resolved=");
		sb.append(resolved);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ImportReportEntry toEntityModel() {
		ImportReportEntryImpl importReportEntryImpl =
			new ImportReportEntryImpl();

		importReportEntryImpl.setMvccVersion(mvccVersion);
		importReportEntryImpl.setImportReportEntryId(importReportEntryId);
		importReportEntryImpl.setGroupId(groupId);
		importReportEntryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			importReportEntryImpl.setCreateDate(null);
		}
		else {
			importReportEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			importReportEntryImpl.setModifiedDate(null);
		}
		else {
			importReportEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		importReportEntryImpl.setClassNameId(classNameId);
		importReportEntryImpl.setClassPK(classPK);
		importReportEntryImpl.setEntityClassNameId(entityClassNameId);

		if (entityExternalReferenceCode == null) {
			importReportEntryImpl.setEntityExternalReferenceCode("");
		}
		else {
			importReportEntryImpl.setEntityExternalReferenceCode(
				entityExternalReferenceCode);
		}

		if (error == null) {
			importReportEntryImpl.setError("");
		}
		else {
			importReportEntryImpl.setError(error);
		}

		if (errorStacktrace == null) {
			importReportEntryImpl.setErrorStacktrace("");
		}
		else {
			importReportEntryImpl.setErrorStacktrace(errorStacktrace);
		}

		importReportEntryImpl.setResolved(resolved);
		importReportEntryImpl.setType(type);

		importReportEntryImpl.resetOriginalValues();

		return importReportEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		importReportEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		entityClassNameId = objectInput.readLong();
		entityExternalReferenceCode = objectInput.readUTF();
		error = (String)objectInput.readObject();
		errorStacktrace = objectInput.readUTF();

		resolved = objectInput.readBoolean();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(importReportEntryId);

		objectOutput.writeLong(groupId);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(error);
		}

		if (errorStacktrace == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(errorStacktrace);
		}

		objectOutput.writeBoolean(resolved);

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public long importReportEntryId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long entityClassNameId;
	public String entityExternalReferenceCode;
	public String error;
	public String errorStacktrace;
	public boolean resolved;
	public int type;

}