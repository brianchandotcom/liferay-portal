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

package com.liferay.portal.security.wedeploy.auth.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.security.wedeploy.auth.service.http.WeDeployAuthAppServiceSoap}.
 *
 * @author Supritha Sundaram
 * @generated
 */
public class WeDeployAuthAppSoap implements Serializable {

	public static WeDeployAuthAppSoap toSoapModel(WeDeployAuthApp model) {
		WeDeployAuthAppSoap soapModel = new WeDeployAuthAppSoap();

		soapModel.setWeDeployAuthAppId(model.getWeDeployAuthAppId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setRedirectURI(model.getRedirectURI());
		soapModel.setClientId(model.getClientId());
		soapModel.setClientSecret(model.getClientSecret());

		return soapModel;
	}

	public static WeDeployAuthAppSoap[] toSoapModels(WeDeployAuthApp[] models) {
		WeDeployAuthAppSoap[] soapModels =
			new WeDeployAuthAppSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WeDeployAuthAppSoap[][] toSoapModels(
		WeDeployAuthApp[][] models) {

		WeDeployAuthAppSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new WeDeployAuthAppSoap[models.length][models[0].length];
		}
		else {
			soapModels = new WeDeployAuthAppSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WeDeployAuthAppSoap[] toSoapModels(
		List<WeDeployAuthApp> models) {

		List<WeDeployAuthAppSoap> soapModels =
			new ArrayList<WeDeployAuthAppSoap>(models.size());

		for (WeDeployAuthApp model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WeDeployAuthAppSoap[soapModels.size()]);
	}

	public WeDeployAuthAppSoap() {
	}

	public long getPrimaryKey() {
		return _weDeployAuthAppId;
	}

	public void setPrimaryKey(long pk) {
		setWeDeployAuthAppId(pk);
	}

	public long getWeDeployAuthAppId() {
		return _weDeployAuthAppId;
	}

	public void setWeDeployAuthAppId(long weDeployAuthAppId) {
		_weDeployAuthAppId = weDeployAuthAppId;
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

	public String getRedirectURI() {
		return _redirectURI;
	}

	public void setRedirectURI(String redirectURI) {
		_redirectURI = redirectURI;
	}

	public String getClientId() {
		return _clientId;
	}

	public void setClientId(String clientId) {
		_clientId = clientId;
	}

	public String getClientSecret() {
		return _clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		_clientSecret = clientSecret;
	}

	private long _weDeployAuthAppId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _redirectURI;
	private String _clientId;
	private String _clientSecret;

}