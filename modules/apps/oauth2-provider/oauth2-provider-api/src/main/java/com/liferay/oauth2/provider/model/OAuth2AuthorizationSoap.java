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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.oauth2.provider.service.http.OAuth2AuthorizationServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class OAuth2AuthorizationSoap implements Serializable {

	public static OAuth2AuthorizationSoap toSoapModel(
		OAuth2Authorization model) {

		OAuth2AuthorizationSoap soapModel = new OAuth2AuthorizationSoap();

		soapModel.setOAuth2AuthorizationId(model.getOAuth2AuthorizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setOAuth2ApplicationId(model.getOAuth2ApplicationId());
		soapModel.setOAuth2ApplicationScopeAliasesId(
			model.getOAuth2ApplicationScopeAliasesId());
		soapModel.setAccessTokenContent(model.getAccessTokenContent());
		soapModel.setAccessTokenContentHash(model.getAccessTokenContentHash());
		soapModel.setAccessTokenCreateDate(model.getAccessTokenCreateDate());
		soapModel.setAccessTokenExpirationDate(
			model.getAccessTokenExpirationDate());
		soapModel.setRemoteHostInfo(model.getRemoteHostInfo());
		soapModel.setRemoteIPInfo(model.getRemoteIPInfo());
		soapModel.setRefreshTokenContent(model.getRefreshTokenContent());
		soapModel.setRefreshTokenContentHash(
			model.getRefreshTokenContentHash());
		soapModel.setRefreshTokenCreateDate(model.getRefreshTokenCreateDate());
		soapModel.setRefreshTokenExpirationDate(
			model.getRefreshTokenExpirationDate());

		return soapModel;
	}

	public static OAuth2AuthorizationSoap[] toSoapModels(
		OAuth2Authorization[] models) {

		OAuth2AuthorizationSoap[] soapModels =
			new OAuth2AuthorizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OAuth2AuthorizationSoap[][] toSoapModels(
		OAuth2Authorization[][] models) {

		OAuth2AuthorizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new OAuth2AuthorizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OAuth2AuthorizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OAuth2AuthorizationSoap[] toSoapModels(
		List<OAuth2Authorization> models) {

		List<OAuth2AuthorizationSoap> soapModels =
			new ArrayList<OAuth2AuthorizationSoap>(models.size());

		for (OAuth2Authorization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new OAuth2AuthorizationSoap[soapModels.size()]);
	}

	public OAuth2AuthorizationSoap() {
	}

	public long getPrimaryKey() {
		return _oAuth2AuthorizationId;
	}

	public void setPrimaryKey(long pk) {
		setOAuth2AuthorizationId(pk);
	}

	public long getOAuth2AuthorizationId() {
		return _oAuth2AuthorizationId;
	}

	public void setOAuth2AuthorizationId(long oAuth2AuthorizationId) {
		_oAuth2AuthorizationId = oAuth2AuthorizationId;
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

	public long getOAuth2ApplicationScopeAliasesId() {
		return _oAuth2ApplicationScopeAliasesId;
	}

	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {

		_oAuth2ApplicationScopeAliasesId = oAuth2ApplicationScopeAliasesId;
	}

	public String getAccessTokenContent() {
		return _accessTokenContent;
	}

	public void setAccessTokenContent(String accessTokenContent) {
		_accessTokenContent = accessTokenContent;
	}

	public long getAccessTokenContentHash() {
		return _accessTokenContentHash;
	}

	public void setAccessTokenContentHash(long accessTokenContentHash) {
		_accessTokenContentHash = accessTokenContentHash;
	}

	public Date getAccessTokenCreateDate() {
		return _accessTokenCreateDate;
	}

	public void setAccessTokenCreateDate(Date accessTokenCreateDate) {
		_accessTokenCreateDate = accessTokenCreateDate;
	}

	public Date getAccessTokenExpirationDate() {
		return _accessTokenExpirationDate;
	}

	public void setAccessTokenExpirationDate(Date accessTokenExpirationDate) {
		_accessTokenExpirationDate = accessTokenExpirationDate;
	}

	public String getRemoteHostInfo() {
		return _remoteHostInfo;
	}

	public void setRemoteHostInfo(String remoteHostInfo) {
		_remoteHostInfo = remoteHostInfo;
	}

	public String getRemoteIPInfo() {
		return _remoteIPInfo;
	}

	public void setRemoteIPInfo(String remoteIPInfo) {
		_remoteIPInfo = remoteIPInfo;
	}

	public String getRefreshTokenContent() {
		return _refreshTokenContent;
	}

	public void setRefreshTokenContent(String refreshTokenContent) {
		_refreshTokenContent = refreshTokenContent;
	}

	public long getRefreshTokenContentHash() {
		return _refreshTokenContentHash;
	}

	public void setRefreshTokenContentHash(long refreshTokenContentHash) {
		_refreshTokenContentHash = refreshTokenContentHash;
	}

	public Date getRefreshTokenCreateDate() {
		return _refreshTokenCreateDate;
	}

	public void setRefreshTokenCreateDate(Date refreshTokenCreateDate) {
		_refreshTokenCreateDate = refreshTokenCreateDate;
	}

	public Date getRefreshTokenExpirationDate() {
		return _refreshTokenExpirationDate;
	}

	public void setRefreshTokenExpirationDate(Date refreshTokenExpirationDate) {
		_refreshTokenExpirationDate = refreshTokenExpirationDate;
	}

	private long _oAuth2AuthorizationId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _oAuth2ApplicationId;
	private long _oAuth2ApplicationScopeAliasesId;
	private String _accessTokenContent;
	private long _accessTokenContentHash;
	private Date _accessTokenCreateDate;
	private Date _accessTokenExpirationDate;
	private String _remoteHostInfo;
	private String _remoteIPInfo;
	private String _refreshTokenContent;
	private long _refreshTokenContentHash;
	private Date _refreshTokenCreateDate;
	private Date _refreshTokenExpirationDate;

}