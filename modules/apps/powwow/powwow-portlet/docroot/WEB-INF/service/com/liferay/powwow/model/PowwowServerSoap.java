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

package com.liferay.powwow.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Shinn Lok
 * @generated
 */
public class PowwowServerSoap implements Serializable {

	public static PowwowServerSoap toSoapModel(PowwowServer model) {
		PowwowServerSoap soapModel = new PowwowServerSoap();

		soapModel.setPowwowServerId(model.getPowwowServerId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setProviderType(model.getProviderType());
		soapModel.setUrl(model.getUrl());
		soapModel.setApiKey(model.getApiKey());
		soapModel.setSecret(model.getSecret());
		soapModel.setActive(model.isActive());

		return soapModel;
	}

	public static PowwowServerSoap[] toSoapModels(PowwowServer[] models) {
		PowwowServerSoap[] soapModels = new PowwowServerSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PowwowServerSoap[][] toSoapModels(PowwowServer[][] models) {
		PowwowServerSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PowwowServerSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PowwowServerSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PowwowServerSoap[] toSoapModels(List<PowwowServer> models) {
		List<PowwowServerSoap> soapModels = new ArrayList<PowwowServerSoap>(
			models.size());

		for (PowwowServer model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PowwowServerSoap[soapModels.size()]);
	}

	public PowwowServerSoap() {
	}

	public long getPrimaryKey() {
		return _powwowServerId;
	}

	public void setPrimaryKey(long pk) {
		setPowwowServerId(pk);
	}

	public long getPowwowServerId() {
		return _powwowServerId;
	}

	public void setPowwowServerId(long powwowServerId) {
		_powwowServerId = powwowServerId;
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

	public String getProviderType() {
		return _providerType;
	}

	public void setProviderType(String providerType) {
		_providerType = providerType;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public String getApiKey() {
		return _apiKey;
	}

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	public String getSecret() {
		return _secret;
	}

	public void setSecret(String secret) {
		_secret = secret;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _powwowServerId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _providerType;
	private String _url;
	private String _apiKey;
	private String _secret;
	private boolean _active;

}