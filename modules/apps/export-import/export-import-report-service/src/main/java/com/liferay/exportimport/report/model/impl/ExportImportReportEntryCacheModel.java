/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.model.impl;

import com.liferay.exportimport.report.model.ExportImportReportEntry;
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
 * The cache model class for representing ExportImportReportEntry in entity cache.
 *
 * @author Carlos Correa
 * @generated
 */
public class ExportImportReportEntryCacheModel
	implements CacheModel<ExportImportReportEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExportImportReportEntryCacheModel)) {
			return false;
		}

		ExportImportReportEntryCacheModel exportImportReportEntryCacheModel =
			(ExportImportReportEntryCacheModel)object;

		if ((exportImportReportEntryId ==
				exportImportReportEntryCacheModel.exportImportReportEntryId) &&
			(mvccVersion == exportImportReportEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, exportImportReportEntryId);

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
		sb.append(", exportImportReportEntryId=");
		sb.append(exportImportReportEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classExternalReferenceCode=");
		sb.append(classExternalReferenceCode);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", error=");
		sb.append(error);
		sb.append(", errorStacktrace=");
		sb.append(errorStacktrace);
		sb.append(", exportImportConfigurationId=");
		sb.append(exportImportConfigurationId);
		sb.append(", resolved=");
		sb.append(resolved);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExportImportReportEntry toEntityModel() {
		ExportImportReportEntryImpl exportImportReportEntryImpl =
			new ExportImportReportEntryImpl();

		exportImportReportEntryImpl.setMvccVersion(mvccVersion);
		exportImportReportEntryImpl.setExportImportReportEntryId(
			exportImportReportEntryId);
		exportImportReportEntryImpl.setGroupId(groupId);
		exportImportReportEntryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			exportImportReportEntryImpl.setCreateDate(null);
		}
		else {
			exportImportReportEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			exportImportReportEntryImpl.setModifiedDate(null);
		}
		else {
			exportImportReportEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (classExternalReferenceCode == null) {
			exportImportReportEntryImpl.setClassExternalReferenceCode("");
		}
		else {
			exportImportReportEntryImpl.setClassExternalReferenceCode(
				classExternalReferenceCode);
		}

		exportImportReportEntryImpl.setClassNameId(classNameId);

		if (error == null) {
			exportImportReportEntryImpl.setError("");
		}
		else {
			exportImportReportEntryImpl.setError(error);
		}

		if (errorStacktrace == null) {
			exportImportReportEntryImpl.setErrorStacktrace("");
		}
		else {
			exportImportReportEntryImpl.setErrorStacktrace(errorStacktrace);
		}

		exportImportReportEntryImpl.setExportImportConfigurationId(
			exportImportConfigurationId);
		exportImportReportEntryImpl.setResolved(resolved);
		exportImportReportEntryImpl.setType(type);

		exportImportReportEntryImpl.resetOriginalValues();

		return exportImportReportEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		exportImportReportEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		classExternalReferenceCode = objectInput.readUTF();

		classNameId = objectInput.readLong();
		error = objectInput.readUTF();
		errorStacktrace = objectInput.readUTF();

		exportImportConfigurationId = objectInput.readLong();

		resolved = objectInput.readBoolean();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(exportImportReportEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (classExternalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(classExternalReferenceCode);
		}

		objectOutput.writeLong(classNameId);

		if (error == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(error);
		}

		if (errorStacktrace == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(errorStacktrace);
		}

		objectOutput.writeLong(exportImportConfigurationId);

		objectOutput.writeBoolean(resolved);

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public long exportImportReportEntryId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public String classExternalReferenceCode;
	public long classNameId;
	public String error;
	public String errorStacktrace;
	public long exportImportConfigurationId;
	public boolean resolved;
	public int type;

}