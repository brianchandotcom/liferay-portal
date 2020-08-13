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

package com.liferay.dynamic.data.mapping.model;

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
public class DDMFormInstanceReportSoap implements Serializable {

	public static DDMFormInstanceReportSoap toSoapModel(
		DDMFormInstanceReport model) {

		DDMFormInstanceReportSoap soapModel = new DDMFormInstanceReportSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setFormInstanceReportId(model.getFormInstanceReportId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFormInstanceId(model.getFormInstanceId());
		soapModel.setData(model.getData());

		return soapModel;
	}

	public static DDMFormInstanceReportSoap[] toSoapModels(
		DDMFormInstanceReport[] models) {

		DDMFormInstanceReportSoap[] soapModels =
			new DDMFormInstanceReportSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceReportSoap[][] toSoapModels(
		DDMFormInstanceReport[][] models) {

		DDMFormInstanceReportSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDMFormInstanceReportSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMFormInstanceReportSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMFormInstanceReportSoap[] toSoapModels(
		List<DDMFormInstanceReport> models) {

		List<DDMFormInstanceReportSoap> soapModels =
			new ArrayList<DDMFormInstanceReportSoap>(models.size());

		for (DDMFormInstanceReport model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DDMFormInstanceReportSoap[soapModels.size()]);
	}

	public DDMFormInstanceReportSoap() {
	}

	public long getPrimaryKey() {
		return _formInstanceReportId;
	}

	public void setPrimaryKey(long pk) {
		setFormInstanceReportId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getFormInstanceReportId() {
		return _formInstanceReportId;
	}

	public void setFormInstanceReportId(long formInstanceReportId) {
		_formInstanceReportId = formInstanceReportId;
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

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getFormInstanceId() {
		return _formInstanceId;
	}

	public void setFormInstanceId(long formInstanceId) {
		_formInstanceId = formInstanceId;
	}

	public String getData() {
		return _data;
	}

	public void setData(String data) {
		_data = data;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _formInstanceReportId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _formInstanceId;
	private String _data;

}