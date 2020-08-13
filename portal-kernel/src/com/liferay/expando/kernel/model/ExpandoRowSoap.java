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

package com.liferay.expando.kernel.model;

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
public class ExpandoRowSoap implements Serializable {

	public static ExpandoRowSoap toSoapModel(ExpandoRow model) {
		ExpandoRowSoap soapModel = new ExpandoRowSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setRowId(model.getRowId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTableId(model.getTableId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static ExpandoRowSoap[] toSoapModels(ExpandoRow[] models) {
		ExpandoRowSoap[] soapModels = new ExpandoRowSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ExpandoRowSoap[][] toSoapModels(ExpandoRow[][] models) {
		ExpandoRowSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ExpandoRowSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ExpandoRowSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ExpandoRowSoap[] toSoapModels(List<ExpandoRow> models) {
		List<ExpandoRowSoap> soapModels = new ArrayList<ExpandoRowSoap>(
			models.size());

		for (ExpandoRow model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExpandoRowSoap[soapModels.size()]);
	}

	public ExpandoRowSoap() {
	}

	public long getPrimaryKey() {
		return _rowId;
	}

	public void setPrimaryKey(long pk) {
		setRowId(pk);
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

	public long getRowId() {
		return _rowId;
	}

	public void setRowId(long rowId) {
		_rowId = rowId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		_tableId = tableId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _rowId;
	private long _companyId;
	private Date _modifiedDate;
	private long _tableId;
	private long _classPK;

}