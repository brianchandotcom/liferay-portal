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

package com.liferay.portal.security.sso.openid.connect.persistence.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Arthur Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class OpenIdConnectSessionSoap implements Serializable {

	public static OpenIdConnectSessionSoap toSoapModel(
		OpenIdConnectSession model) {

		OpenIdConnectSessionSoap soapModel = new OpenIdConnectSessionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setOpenIdConnectSessionId(model.getOpenIdConnectSessionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAccessToken(model.getAccessToken());
		soapModel.setIdToken(model.getIdToken());
		soapModel.setProviderName(model.getProviderName());
		soapModel.setRefreshToken(model.getRefreshToken());

		return soapModel;
	}

	public static OpenIdConnectSessionSoap[] toSoapModels(
		OpenIdConnectSession[] models) {

		OpenIdConnectSessionSoap[] soapModels =
			new OpenIdConnectSessionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OpenIdConnectSessionSoap[][] toSoapModels(
		OpenIdConnectSession[][] models) {

		OpenIdConnectSessionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new OpenIdConnectSessionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OpenIdConnectSessionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OpenIdConnectSessionSoap[] toSoapModels(
		List<OpenIdConnectSession> models) {

		List<OpenIdConnectSessionSoap> soapModels =
			new ArrayList<OpenIdConnectSessionSoap>(models.size());

		for (OpenIdConnectSession model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new OpenIdConnectSessionSoap[soapModels.size()]);
	}

	public OpenIdConnectSessionSoap() {
	}

	public long getPrimaryKey() {
		return _openIdConnectSessionId;
	}

	public void setPrimaryKey(long pk) {
		setOpenIdConnectSessionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getOpenIdConnectSessionId() {
		return _openIdConnectSessionId;
	}

	public void setOpenIdConnectSessionId(long openIdConnectSessionId) {
		_openIdConnectSessionId = openIdConnectSessionId;
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

	public String getAccessToken() {
		return _accessToken;
	}

	public void setAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	public String getIdToken() {
		return _idToken;
	}

	public void setIdToken(String idToken) {
		_idToken = idToken;
	}

	public String getProviderName() {
		return _providerName;
	}

	public void setProviderName(String providerName) {
		_providerName = providerName;
	}

	public String getRefreshToken() {
		return _refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		_refreshToken = refreshToken;
	}

	private long _mvccVersion;
	private long _openIdConnectSessionId;
	private long _companyId;
	private Date _modifiedDate;
	private String _accessToken;
	private String _idToken;
	private String _providerName;
	private String _refreshToken;

}