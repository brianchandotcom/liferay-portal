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

package com.liferay.adaptive.media.image.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AMImageEntrySoap implements Serializable {

	public static AMImageEntrySoap toSoapModel(AMImageEntry model) {
		AMImageEntrySoap soapModel = new AMImageEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setAmImageEntryId(model.getAmImageEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setConfigurationUuid(model.getConfigurationUuid());
		soapModel.setFileVersionId(model.getFileVersionId());
		soapModel.setMimeType(model.getMimeType());
		soapModel.setHeight(model.getHeight());
		soapModel.setWidth(model.getWidth());
		soapModel.setSize(model.getSize());

		return soapModel;
	}

	public static AMImageEntrySoap[] toSoapModels(AMImageEntry[] models) {
		AMImageEntrySoap[] soapModels = new AMImageEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AMImageEntrySoap[][] toSoapModels(AMImageEntry[][] models) {
		AMImageEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AMImageEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AMImageEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AMImageEntrySoap[] toSoapModels(List<AMImageEntry> models) {
		List<AMImageEntrySoap> soapModels = new ArrayList<AMImageEntrySoap>(
			models.size());

		for (AMImageEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AMImageEntrySoap[soapModels.size()]);
	}

	public AMImageEntrySoap() {
	}

	public long getPrimaryKey() {
		return _amImageEntryId;
	}

	public void setPrimaryKey(long pk) {
		setAmImageEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getAmImageEntryId() {
		return _amImageEntryId;
	}

	public void setAmImageEntryId(long amImageEntryId) {
		_amImageEntryId = amImageEntryId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public String getConfigurationUuid() {
		return _configurationUuid;
	}

	public void setConfigurationUuid(String configurationUuid) {
		_configurationUuid = configurationUuid;
	}

	public long getFileVersionId() {
		return _fileVersionId;
	}

	public void setFileVersionId(long fileVersionId) {
		_fileVersionId = fileVersionId;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public void setMimeType(String mimeType) {
		_mimeType = mimeType;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		_height = height;
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		_width = width;
	}

	public long getSize() {
		return _size;
	}

	public void setSize(long size) {
		_size = size;
	}

	private String _uuid;
	private long _amImageEntryId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private String _configurationUuid;
	private long _fileVersionId;
	private String _mimeType;
	private int _height;
	private int _width;
	private long _size;

}