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

package com.liferay.change.tracking.engine.model;

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
public class ChangeCollectionSoap implements Serializable {
	public static ChangeCollectionSoap toSoapModel(ChangeCollection model) {
		ChangeCollectionSoap soapModel = new ChangeCollectionSoap();

		soapModel.setChangeCollectionId(model.getChangeCollectionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());

		return soapModel;
	}

	public static ChangeCollectionSoap[] toSoapModels(ChangeCollection[] models) {
		ChangeCollectionSoap[] soapModels = new ChangeCollectionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ChangeCollectionSoap[][] toSoapModels(
		ChangeCollection[][] models) {
		ChangeCollectionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ChangeCollectionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ChangeCollectionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ChangeCollectionSoap[] toSoapModels(
		List<ChangeCollection> models) {
		List<ChangeCollectionSoap> soapModels = new ArrayList<ChangeCollectionSoap>(models.size());

		for (ChangeCollection model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ChangeCollectionSoap[soapModels.size()]);
	}

	public ChangeCollectionSoap() {
	}

	public long getPrimaryKey() {
		return _changeCollectionId;
	}

	public void setPrimaryKey(long pk) {
		setChangeCollectionId(pk);
	}

	public long getChangeCollectionId() {
		return _changeCollectionId;
	}

	public void setChangeCollectionId(long changeCollectionId) {
		_changeCollectionId = changeCollectionId;
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

	private long _changeCollectionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
}