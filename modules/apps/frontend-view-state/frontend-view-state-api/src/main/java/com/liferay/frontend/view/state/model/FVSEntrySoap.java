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

package com.liferay.frontend.view.state.model;

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
public class FVSEntrySoap implements Serializable {

	public static FVSEntrySoap toSoapModel(FVSEntry model) {
		FVSEntrySoap soapModel = new FVSEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setFvsEntryId(model.getFvsEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setViewState(model.getViewState());

		return soapModel;
	}

	public static FVSEntrySoap[] toSoapModels(FVSEntry[] models) {
		FVSEntrySoap[] soapModels = new FVSEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FVSEntrySoap[][] toSoapModels(FVSEntry[][] models) {
		FVSEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FVSEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new FVSEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FVSEntrySoap[] toSoapModels(List<FVSEntry> models) {
		List<FVSEntrySoap> soapModels = new ArrayList<FVSEntrySoap>(
			models.size());

		for (FVSEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FVSEntrySoap[soapModels.size()]);
	}

	public FVSEntrySoap() {
	}

	public long getPrimaryKey() {
		return _fvsEntryId;
	}

	public void setPrimaryKey(long pk) {
		setFvsEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getFvsEntryId() {
		return _fvsEntryId;
	}

	public void setFvsEntryId(long fvsEntryId) {
		_fvsEntryId = fvsEntryId;
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

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
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

	public String getViewState() {
		return _viewState;
	}

	public void setViewState(String viewState) {
		_viewState = viewState;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _fvsEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _viewState;

}