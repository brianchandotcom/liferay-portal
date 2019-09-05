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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLFileEntryMetadata in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryMetadataCacheModel
	implements CacheModel<DLFileEntryMetadata>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileEntryMetadataCacheModel)) {
			return false;
		}

		DLFileEntryMetadataCacheModel dlFileEntryMetadataCacheModel =
			(DLFileEntryMetadataCacheModel)obj;

		if ((fileEntryMetadataId ==
				dlFileEntryMetadataCacheModel.fileEntryMetadataId) &&
			(mvccVersion == dlFileEntryMetadataCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, fileEntryMetadataId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", fileEntryMetadataId=");
		sb.append(fileEntryMetadataId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", DDMStorageId=");
		sb.append(DDMStorageId);
		sb.append(", DDMStructureId=");
		sb.append(DDMStructureId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", fileVersionId=");
		sb.append(fileVersionId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLFileEntryMetadata toEntityModel() {
		DLFileEntryMetadataImpl dlFileEntryMetadataImpl =
			new DLFileEntryMetadataImpl();

		dlFileEntryMetadataImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			dlFileEntryMetadataImpl.setUuid("");
		}
		else {
			dlFileEntryMetadataImpl.setUuid(uuid);
		}

		dlFileEntryMetadataImpl.setFileEntryMetadataId(fileEntryMetadataId);
		dlFileEntryMetadataImpl.setCompanyId(companyId);
		dlFileEntryMetadataImpl.setDDMStorageId(DDMStorageId);
		dlFileEntryMetadataImpl.setDDMStructureId(DDMStructureId);
		dlFileEntryMetadataImpl.setFileEntryId(fileEntryId);
		dlFileEntryMetadataImpl.setFileVersionId(fileVersionId);

		dlFileEntryMetadataImpl.resetOriginalValues();

		return dlFileEntryMetadataImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		fileEntryMetadataId = objectInput.readLong();

		companyId = objectInput.readLong();

		DDMStorageId = objectInput.readLong();

		DDMStructureId = objectInput.readLong();

		fileEntryId = objectInput.readLong();

		fileVersionId = objectInput.readLong();
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

		objectOutput.writeLong(fileEntryMetadataId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(DDMStorageId);

		objectOutput.writeLong(DDMStructureId);

		objectOutput.writeLong(fileEntryId);

		objectOutput.writeLong(fileVersionId);
	}

	public long mvccVersion;
	public String uuid;
	public long fileEntryMetadataId;
	public long companyId;
	public long DDMStorageId;
	public long DDMStructureId;
	public long fileEntryId;
	public long fileVersionId;

}