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

package com.liferay.document.library.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DLFileEntryPreviewSoap implements Serializable {
	public static DLFileEntryPreviewSoap toSoapModel(DLFileEntryPreview model) {
		DLFileEntryPreviewSoap soapModel = new DLFileEntryPreviewSoap();

		soapModel.setFileEntryPreviewId(model.getFileEntryPreviewId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setFileVersionId(model.getFileVersionId());
		soapModel.setPreviewType(model.getPreviewType());

		return soapModel;
	}

	public static DLFileEntryPreviewSoap[] toSoapModels(
		DLFileEntryPreview[] models) {
		DLFileEntryPreviewSoap[] soapModels = new DLFileEntryPreviewSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLFileEntryPreviewSoap[][] toSoapModels(
		DLFileEntryPreview[][] models) {
		DLFileEntryPreviewSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DLFileEntryPreviewSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLFileEntryPreviewSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLFileEntryPreviewSoap[] toSoapModels(
		List<DLFileEntryPreview> models) {
		List<DLFileEntryPreviewSoap> soapModels = new ArrayList<DLFileEntryPreviewSoap>(models.size());

		for (DLFileEntryPreview model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLFileEntryPreviewSoap[soapModels.size()]);
	}

	public DLFileEntryPreviewSoap() {
	}

	public long getPrimaryKey() {
		return _fileEntryPreviewId;
	}

	public void setPrimaryKey(long pk) {
		setFileEntryPreviewId(pk);
	}

	public long getFileEntryPreviewId() {
		return _fileEntryPreviewId;
	}

	public void setFileEntryPreviewId(long fileEntryPreviewId) {
		_fileEntryPreviewId = fileEntryPreviewId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public int getPreviewType() {
		return _previewType;
	}

	public void setPreviewType(int previewType) {
		_previewType = previewType;
	}

	private long _fileEntryPreviewId;
	private long _groupId;
	private long _fileEntryId;
	private long _fileVersionId;
	private int _previewType;
}