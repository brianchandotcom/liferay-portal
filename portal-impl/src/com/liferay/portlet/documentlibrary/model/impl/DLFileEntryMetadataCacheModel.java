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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLFileEntryMetadata in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadata
 * @generated
 */
@ProviderType
public class DLFileEntryMetadataCacheModel implements CacheModel<DLFileEntryMetadata>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileEntryMetadataCacheModel)) {
			return false;
		}

		DLFileEntryMetadataCacheModel dlFileEntryMetadataCacheModel = (DLFileEntryMetadataCacheModel)obj;

		if (fileEntryMetadataId == dlFileEntryMetadataCacheModel.fileEntryMetadataId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, fileEntryMetadataId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", fileEntryMetadataId=");
		sb.append(fileEntryMetadataId);
		sb.append(", DDMStorageId=");
		sb.append(DDMStorageId);
		sb.append(", DDMStructureId=");
		sb.append(DDMStructureId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", fileVersionId=");
		sb.append(fileVersionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLFileEntryMetadata toEntityModel() {
		DLFileEntryMetadataImpl dlFileEntryMetadataImpl = new DLFileEntryMetadataImpl();

		if (uuid == null) {
			dlFileEntryMetadataImpl.setUuid(StringPool.BLANK);
		}
		else {
			dlFileEntryMetadataImpl.setUuid(uuid);
		}

		dlFileEntryMetadataImpl.setFileEntryMetadataId(fileEntryMetadataId);
		dlFileEntryMetadataImpl.setDDMStorageId(DDMStorageId);
		dlFileEntryMetadataImpl.setDDMStructureId(DDMStructureId);
		dlFileEntryMetadataImpl.setFileEntryId(fileEntryId);
		dlFileEntryMetadataImpl.setFileVersionId(fileVersionId);
		dlFileEntryMetadataImpl.setCompanyId(companyId);

		dlFileEntryMetadataImpl.resetOriginalValues();

		return dlFileEntryMetadataImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		fileEntryMetadataId = objectInput.readLong();
		DDMStorageId = objectInput.readLong();
		DDMStructureId = objectInput.readLong();
		fileEntryId = objectInput.readLong();
		fileVersionId = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(fileEntryMetadataId);
		objectOutput.writeLong(DDMStorageId);
		objectOutput.writeLong(DDMStructureId);
		objectOutput.writeLong(fileEntryId);
		objectOutput.writeLong(fileVersionId);
		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long fileEntryMetadataId;
	public long DDMStorageId;
	public long DDMStructureId;
	public long fileEntryId;
	public long fileVersionId;
	public long companyId;
}