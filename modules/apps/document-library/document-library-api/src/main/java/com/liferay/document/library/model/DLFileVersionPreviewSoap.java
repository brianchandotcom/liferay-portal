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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DLFileVersionPreviewSoap implements Serializable {

	public static DLFileVersionPreviewSoap toSoapModel(
		DLFileVersionPreview model) {

		DLFileVersionPreviewSoap soapModel = new DLFileVersionPreviewSoap();

		soapModel.setDlFileVersionPreviewId(model.getDlFileVersionPreviewId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setFileVersionId(model.getFileVersionId());
		soapModel.setPreviewStatus(model.getPreviewStatus());

		return soapModel;
	}

	public static DLFileVersionPreviewSoap[] toSoapModels(
		DLFileVersionPreview[] models) {

		DLFileVersionPreviewSoap[] soapModels =
			new DLFileVersionPreviewSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLFileVersionPreviewSoap[][] toSoapModels(
		DLFileVersionPreview[][] models) {

		DLFileVersionPreviewSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DLFileVersionPreviewSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLFileVersionPreviewSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLFileVersionPreviewSoap[] toSoapModels(
		List<DLFileVersionPreview> models) {

		List<DLFileVersionPreviewSoap> soapModels =
			new ArrayList<DLFileVersionPreviewSoap>(models.size());

		for (DLFileVersionPreview model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DLFileVersionPreviewSoap[soapModels.size()]);
	}

	public DLFileVersionPreviewSoap() {
	}

	public long getPrimaryKey() {
		return _dlFileVersionPreviewId;
	}

	public void setPrimaryKey(long pk) {
		setDlFileVersionPreviewId(pk);
	}

	public long getDlFileVersionPreviewId() {
		return _dlFileVersionPreviewId;
	}

	public void setDlFileVersionPreviewId(long dlFileVersionPreviewId) {
		_dlFileVersionPreviewId = dlFileVersionPreviewId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	private long _dlFileVersionPreviewId;
	private long _groupId;
	private long _companyId;
	private long _fileEntryId;
	private long _fileVersionId;
	private int _previewStatus;

}