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

import org.osgi.annotation.versioning.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FileVersionPreviewSoap implements Serializable {
	public static FileVersionPreviewSoap toSoapModel(FileVersionPreview model) {
		FileVersionPreviewSoap soapModel = new FileVersionPreviewSoap();

		soapModel.setFileVersionPreviewId(model.getFileVersionPreviewId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setFileVersionId(model.getFileVersionId());
		soapModel.setPreviewStatus(model.getPreviewStatus());

		return soapModel;
	}

	public static FileVersionPreviewSoap[] toSoapModels(
		FileVersionPreview[] models) {
		FileVersionPreviewSoap[] soapModels = new FileVersionPreviewSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FileVersionPreviewSoap[][] toSoapModels(
		FileVersionPreview[][] models) {
		FileVersionPreviewSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FileVersionPreviewSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FileVersionPreviewSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FileVersionPreviewSoap[] toSoapModels(
		List<FileVersionPreview> models) {
		List<FileVersionPreviewSoap> soapModels = new ArrayList<FileVersionPreviewSoap>(models.size());

		for (FileVersionPreview model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FileVersionPreviewSoap[soapModels.size()]);
	}

	public FileVersionPreviewSoap() {
	}

	public long getPrimaryKey() {
		return _fileVersionPreviewId;
	}

	public void setPrimaryKey(long pk) {
		setFileVersionPreviewId(pk);
	}

	public long getFileVersionPreviewId() {
		return _fileVersionPreviewId;
	}

	public void setFileVersionPreviewId(long fileVersionPreviewId) {
		_fileVersionPreviewId = fileVersionPreviewId;
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

	public int getPreviewStatus() {
		return _previewStatus;
	}

	public void setPreviewStatus(int previewStatus) {
		_previewStatus = previewStatus;
	}

	private long _fileVersionPreviewId;
	private long _groupId;
	private long _fileEntryId;
	private long _fileVersionId;
	private int _previewStatus;
}