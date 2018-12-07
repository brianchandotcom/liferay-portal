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

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ChangeTrackingCollectionSoap implements Serializable {
	public static ChangeTrackingCollectionSoap toSoapModel(
		ChangeTrackingCollection model) {
		ChangeTrackingCollectionSoap soapModel = new ChangeTrackingCollectionSoap();

		soapModel.setChangeTrackingCollectionId(model.getChangeTrackingCollectionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static ChangeTrackingCollectionSoap[] toSoapModels(
		ChangeTrackingCollection[] models) {
		ChangeTrackingCollectionSoap[] soapModels = new ChangeTrackingCollectionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ChangeTrackingCollectionSoap[][] toSoapModels(
		ChangeTrackingCollection[][] models) {
		ChangeTrackingCollectionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ChangeTrackingCollectionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ChangeTrackingCollectionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ChangeTrackingCollectionSoap[] toSoapModels(
		List<ChangeTrackingCollection> models) {
		List<ChangeTrackingCollectionSoap> soapModels = new ArrayList<ChangeTrackingCollectionSoap>(models.size());

		for (ChangeTrackingCollection model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ChangeTrackingCollectionSoap[soapModels.size()]);
	}

	public ChangeTrackingCollectionSoap() {
	}

	public long getPrimaryKey() {
		return _changeTrackingCollectionId;
	}

	public void setPrimaryKey(long pk) {
		setChangeTrackingCollectionId(pk);
	}

	public long getChangeTrackingCollectionId() {
		return _changeTrackingCollectionId;
	}

	public void setChangeTrackingCollectionId(long changeTrackingCollectionId) {
		_changeTrackingCollectionId = changeTrackingCollectionId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private long _changeTrackingCollectionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
}