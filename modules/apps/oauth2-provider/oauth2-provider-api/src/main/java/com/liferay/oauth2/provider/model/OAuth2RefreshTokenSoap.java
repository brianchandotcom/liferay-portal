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

package com.liferay.oauth2.provider.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.oauth2.provider.service.http.OAuth2RefreshTokenServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth2.provider.service.http.OAuth2RefreshTokenServiceSoap
 * @generated
 */
@ProviderType
public class OAuth2RefreshTokenSoap implements Serializable {
	public static OAuth2RefreshTokenSoap toSoapModel(OAuth2RefreshToken model) {
		OAuth2RefreshTokenSoap soapModel = new OAuth2RefreshTokenSoap();

		soapModel.setOAuth2RefreshTokenId(model.getOAuth2RefreshTokenId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setRemoteIPInfo(model.getRemoteIPInfo());
		soapModel.setOAuth2RefreshTokenContent(model.getOAuth2RefreshTokenContent());
		soapModel.setOAuth2ApplicationId(model.getOAuth2ApplicationId());
		soapModel.setScopes(model.getScopes());

		return soapModel;
	}

	public static OAuth2RefreshTokenSoap[] toSoapModels(
		OAuth2RefreshToken[] models) {
		OAuth2RefreshTokenSoap[] soapModels = new OAuth2RefreshTokenSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OAuth2RefreshTokenSoap[][] toSoapModels(
		OAuth2RefreshToken[][] models) {
		OAuth2RefreshTokenSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new OAuth2RefreshTokenSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OAuth2RefreshTokenSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OAuth2RefreshTokenSoap[] toSoapModels(
		List<OAuth2RefreshToken> models) {
		List<OAuth2RefreshTokenSoap> soapModels = new ArrayList<OAuth2RefreshTokenSoap>(models.size());

		for (OAuth2RefreshToken model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OAuth2RefreshTokenSoap[soapModels.size()]);
	}

	public OAuth2RefreshTokenSoap() {
	}

	public long getPrimaryKey() {
		return _oAuth2RefreshTokenId;
	}

	public void setPrimaryKey(long pk) {
		setOAuth2RefreshTokenId(pk);
	}

	public long getOAuth2RefreshTokenId() {
		return _oAuth2RefreshTokenId;
	}

	public void setOAuth2RefreshTokenId(long oAuth2RefreshTokenId) {
		_oAuth2RefreshTokenId = oAuth2RefreshTokenId;
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

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public String getRemoteIPInfo() {
		return _remoteIPInfo;
	}

	public void setRemoteIPInfo(String remoteIPInfo) {
		_remoteIPInfo = remoteIPInfo;
	}

	public String getOAuth2RefreshTokenContent() {
		return _oAuth2RefreshTokenContent;
	}

	public void setOAuth2RefreshTokenContent(String oAuth2RefreshTokenContent) {
		_oAuth2RefreshTokenContent = oAuth2RefreshTokenContent;
	}

	public long getOAuth2ApplicationId() {
		return _oAuth2ApplicationId;
	}

	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2ApplicationId = oAuth2ApplicationId;
	}

	public String getScopes() {
		return _scopes;
	}

	public void setScopes(String scopes) {
		_scopes = scopes;
	}

	private long _oAuth2RefreshTokenId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _expirationDate;
	private String _remoteIPInfo;
	private String _oAuth2RefreshTokenContent;
	private long _oAuth2ApplicationId;
	private String _scopes;
}