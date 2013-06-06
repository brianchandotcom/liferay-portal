/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryMetadataSoap implements Serializable {
	public static DLFileEntryMetadataSoap toSoapModel(DLFileEntryMetadata model) {
		DLFileEntryMetadataSoap soapModel = new DLFileEntryMetadataSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setFileEntryMetadataId(model.getFileEntryMetadataId());
		soapModel.setDDMStorageId(model.getDDMStorageId());
		soapModel.setDDMStructureId(model.getDDMStructureId());
		soapModel.setFileEntryTypeId(model.getFileEntryTypeId());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setFileVersionId(model.getFileVersionId());

		return soapModel;
	}

	public static DLFileEntryMetadataSoap[] toSoapModels(
		DLFileEntryMetadata[] models) {
		DLFileEntryMetadataSoap[] soapModels = new DLFileEntryMetadataSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLFileEntryMetadataSoap[][] toSoapModels(
		DLFileEntryMetadata[][] models) {
		DLFileEntryMetadataSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DLFileEntryMetadataSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLFileEntryMetadataSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLFileEntryMetadataSoap[] toSoapModels(
		List<DLFileEntryMetadata> models) {
		List<DLFileEntryMetadataSoap> soapModels = new ArrayList<DLFileEntryMetadataSoap>(models.size());

		for (DLFileEntryMetadata model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLFileEntryMetadataSoap[soapModels.size()]);
	}

	public DLFileEntryMetadataSoap() {
	}

	public long getPrimaryKey() {
		return _fileEntryMetadataId;
	}

	public void setPrimaryKey(long pk) {
		setFileEntryMetadataId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getFileEntryMetadataId() {
		return _fileEntryMetadataId;
	}

	public void setFileEntryMetadataId(long fileEntryMetadataId) {
		_fileEntryMetadataId = fileEntryMetadataId;
	}

	public long getDDMStorageId() {
		return _DDMStorageId;
	}

	public void setDDMStorageId(long DDMStorageId) {
		_DDMStorageId = DDMStorageId;
	}

	public long getDDMStructureId() {
		return _DDMStructureId;
	}

	public void setDDMStructureId(long DDMStructureId) {
		_DDMStructureId = DDMStructureId;
	}

	public long getFileEntryTypeId() {
		return _fileEntryTypeId;
	}

	public void setFileEntryTypeId(long fileEntryTypeId) {
		_fileEntryTypeId = fileEntryTypeId;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public long getFileVersionId() {
		return _fileVersionId;
	}

	public void setFileVersionId(long fileVersionId) {
		_fileVersionId = fileVersionId;
	}

	private String _uuid;
	private long _fileEntryMetadataId;
	private long _DDMStorageId;
	private long _DDMStructureId;
	private long _fileEntryTypeId;
	private long _fileEntryId;
	private long _fileVersionId;
}