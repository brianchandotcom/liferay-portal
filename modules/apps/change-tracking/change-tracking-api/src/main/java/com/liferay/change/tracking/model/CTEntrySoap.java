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

package com.liferay.change.tracking.model;

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
public class CTEntrySoap implements Serializable {

	public static CTEntrySoap toSoapModel(CTEntry model) {
		CTEntrySoap soapModel = new CTEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtEntryId(model.getCtEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setModelClassNameId(model.getModelClassNameId());
		soapModel.setModelClassPK(model.getModelClassPK());
		soapModel.setModelMvccVersion(model.getModelMvccVersion());
		soapModel.setChangeType(model.getChangeType());

		return soapModel;
	}

	public static CTEntrySoap[] toSoapModels(CTEntry[] models) {
		CTEntrySoap[] soapModels = new CTEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTEntrySoap[][] toSoapModels(CTEntry[][] models) {
		CTEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CTEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTEntrySoap[] toSoapModels(List<CTEntry> models) {
		List<CTEntrySoap> soapModels = new ArrayList<CTEntrySoap>(
			models.size());

		for (CTEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CTEntrySoap[soapModels.size()]);
	}

	public CTEntrySoap() {
	}

	public long getPrimaryKey() {
		return _ctEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCtEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtEntryId() {
		return _ctEntryId;
	}

	public void setCtEntryId(long ctEntryId) {
		_ctEntryId = ctEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
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

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getModelClassNameId() {
		return _modelClassNameId;
	}

	public void setModelClassNameId(long modelClassNameId) {
		_modelClassNameId = modelClassNameId;
	}

	public long getModelClassPK() {
		return _modelClassPK;
	}

	public void setModelClassPK(long modelClassPK) {
		_modelClassPK = modelClassPK;
	}

	public long getModelMvccVersion() {
		return _modelMvccVersion;
	}

	public void setModelMvccVersion(long modelMvccVersion) {
		_modelMvccVersion = modelMvccVersion;
	}

	public int getChangeType() {
		return _changeType;
	}

	public void setChangeType(int changeType) {
		_changeType = changeType;
	}

	private long _mvccVersion;
	private long _ctEntryId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _ctCollectionId;
	private long _modelClassNameId;
	private long _modelClassPK;
	private long _modelMvccVersion;
	private int _changeType;

}