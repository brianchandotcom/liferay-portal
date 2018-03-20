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
 * This class is used by SOAP remote services, specifically {@link com.liferay.oauth2.provider.service.http.OAuth2AccessTokenServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth2.provider.service.http.OAuth2AccessTokenServiceSoap
 * @generated
 */
@ProviderType
public class OAuth2AccessTokenSoap implements Serializable {
	public static OAuth2AccessTokenSoap toSoapModel(OAuth2AccessToken model) {
		OAuth2AccessTokenSoap soapModel = new OAuth2AccessTokenSoap();

		soapModel.setOAuth2AccessTokenId(model.getOAuth2AccessTokenId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setOAuth2ApplicationId(model.getOAuth2ApplicationId());
		soapModel.setOAuth2RefreshTokenId(model.getOAuth2RefreshTokenId());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setRemoteIPInfo(model.getRemoteIPInfo());
		soapModel.setScopeAliases(model.getScopeAliases());
		soapModel.setTokenContent(model.getTokenContent());
		soapModel.setTokenType(model.getTokenType());

		return soapModel;
	}

	public static OAuth2AccessTokenSoap[] toSoapModels(
		OAuth2AccessToken[] models) {
		OAuth2AccessTokenSoap[] soapModels = new OAuth2AccessTokenSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OAuth2AccessTokenSoap[][] toSoapModels(
		OAuth2AccessToken[][] models) {
		OAuth2AccessTokenSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new OAuth2AccessTokenSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OAuth2AccessTokenSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OAuth2AccessTokenSoap[] toSoapModels(
		List<OAuth2AccessToken> models) {
		List<OAuth2AccessTokenSoap> soapModels = new ArrayList<OAuth2AccessTokenSoap>(models.size());

		for (OAuth2AccessToken model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OAuth2AccessTokenSoap[soapModels.size()]);
	}

	public OAuth2AccessTokenSoap() {
	}

	public long getPrimaryKey() {
		return _oAuth2AccessTokenId;
	}

	public void setPrimaryKey(long pk) {
		setOAuth2AccessTokenId(pk);
	}

	public long getOAuth2AccessTokenId() {
		return _oAuth2AccessTokenId;
	}

	public void setOAuth2AccessTokenId(long oAuth2AccessTokenId) {
		_oAuth2AccessTokenId = oAuth2AccessTokenId;
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

	public long getOAuth2ApplicationId() {
		return _oAuth2ApplicationId;
	}

	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2ApplicationId = oAuth2ApplicationId;
	}

	public long getOAuth2RefreshTokenId() {
		return _oAuth2RefreshTokenId;
	}

	public void setOAuth2RefreshTokenId(long oAuth2RefreshTokenId) {
		_oAuth2RefreshTokenId = oAuth2RefreshTokenId;
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

	public String getScopeAliases() {
		return _scopeAliases;
	}

	public void setScopeAliases(String scopeAliases) {
		_scopeAliases = scopeAliases;
	}

	public String getTokenContent() {
		return _tokenContent;
	}

	public void setTokenContent(String tokenContent) {
		_tokenContent = tokenContent;
	}

	public String getTokenType() {
		return _tokenType;
	}

	public void setTokenType(String tokenType) {
		_tokenType = tokenType;
	}

	private long _oAuth2AccessTokenId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _oAuth2ApplicationId;
	private long _oAuth2RefreshTokenId;
	private Date _expirationDate;
	private String _remoteIPInfo;
	private String _scopeAliases;
	private String _tokenContent;
	private String _tokenType;
}