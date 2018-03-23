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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link OAuth2Authorization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2Authorization
 * @generated
 */
@ProviderType
public class OAuth2AuthorizationWrapper implements OAuth2Authorization,
	ModelWrapper<OAuth2Authorization> {
	public OAuth2AuthorizationWrapper(OAuth2Authorization oAuth2Authorization) {
		_oAuth2Authorization = oAuth2Authorization;
	}

	@Override
	public Class<?> getModelClass() {
		return OAuth2Authorization.class;
	}

	@Override
	public String getModelClassName() {
		return OAuth2Authorization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuth2AuthorizationId", getOAuth2AuthorizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("oAuth2ApplicationId", getOAuth2ApplicationId());
		attributes.put("oAuth2ApplicationScopeAliasesId",
			getOAuth2ApplicationScopeAliasesId());
		attributes.put("accessTokenContent", getAccessTokenContent());
		attributes.put("accessTokenCreateDate", getAccessTokenCreateDate());
		attributes.put("accessTokenExpirationDate",
			getAccessTokenExpirationDate());
		attributes.put("remoteIPInfo", getRemoteIPInfo());
		attributes.put("refreshTokenContent", getRefreshTokenContent());
		attributes.put("refreshTokenCreateDate", getRefreshTokenCreateDate());
		attributes.put("refreshTokenExpirationDate",
			getRefreshTokenExpirationDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuth2AuthorizationId = (Long)attributes.get(
				"oAuth2AuthorizationId");

		if (oAuth2AuthorizationId != null) {
			setOAuth2AuthorizationId(oAuth2AuthorizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long oAuth2ApplicationId = (Long)attributes.get("oAuth2ApplicationId");

		if (oAuth2ApplicationId != null) {
			setOAuth2ApplicationId(oAuth2ApplicationId);
		}

		Long oAuth2ApplicationScopeAliasesId = (Long)attributes.get(
				"oAuth2ApplicationScopeAliasesId");

		if (oAuth2ApplicationScopeAliasesId != null) {
			setOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);
		}

		String accessTokenContent = (String)attributes.get("accessTokenContent");

		if (accessTokenContent != null) {
			setAccessTokenContent(accessTokenContent);
		}

		Date accessTokenCreateDate = (Date)attributes.get(
				"accessTokenCreateDate");

		if (accessTokenCreateDate != null) {
			setAccessTokenCreateDate(accessTokenCreateDate);
		}

		Date accessTokenExpirationDate = (Date)attributes.get(
				"accessTokenExpirationDate");

		if (accessTokenExpirationDate != null) {
			setAccessTokenExpirationDate(accessTokenExpirationDate);
		}

		String remoteIPInfo = (String)attributes.get("remoteIPInfo");

		if (remoteIPInfo != null) {
			setRemoteIPInfo(remoteIPInfo);
		}

		String refreshTokenContent = (String)attributes.get(
				"refreshTokenContent");

		if (refreshTokenContent != null) {
			setRefreshTokenContent(refreshTokenContent);
		}

		Date refreshTokenCreateDate = (Date)attributes.get(
				"refreshTokenCreateDate");

		if (refreshTokenCreateDate != null) {
			setRefreshTokenCreateDate(refreshTokenCreateDate);
		}

		Date refreshTokenExpirationDate = (Date)attributes.get(
				"refreshTokenExpirationDate");

		if (refreshTokenExpirationDate != null) {
			setRefreshTokenExpirationDate(refreshTokenExpirationDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new OAuth2AuthorizationWrapper((OAuth2Authorization)_oAuth2Authorization.clone());
	}

	@Override
	public int compareTo(OAuth2Authorization oAuth2Authorization) {
		return _oAuth2Authorization.compareTo(oAuth2Authorization);
	}

	/**
	* Returns the access token content of this o auth2 authorization.
	*
	* @return the access token content of this o auth2 authorization
	*/
	@Override
	public java.lang.String getAccessTokenContent() {
		return _oAuth2Authorization.getAccessTokenContent();
	}

	/**
	* Returns the access token create date of this o auth2 authorization.
	*
	* @return the access token create date of this o auth2 authorization
	*/
	@Override
	public Date getAccessTokenCreateDate() {
		return _oAuth2Authorization.getAccessTokenCreateDate();
	}

	/**
	* Returns the access token expiration date of this o auth2 authorization.
	*
	* @return the access token expiration date of this o auth2 authorization
	*/
	@Override
	public Date getAccessTokenExpirationDate() {
		return _oAuth2Authorization.getAccessTokenExpirationDate();
	}

	/**
	* Returns the company ID of this o auth2 authorization.
	*
	* @return the company ID of this o auth2 authorization
	*/
	@Override
	public long getCompanyId() {
		return _oAuth2Authorization.getCompanyId();
	}

	/**
	* Returns the create date of this o auth2 authorization.
	*
	* @return the create date of this o auth2 authorization
	*/
	@Override
	public Date getCreateDate() {
		return _oAuth2Authorization.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _oAuth2Authorization.getExpandoBridge();
	}

	/**
	* Returns the o auth2 application ID of this o auth2 authorization.
	*
	* @return the o auth2 application ID of this o auth2 authorization
	*/
	@Override
	public long getOAuth2ApplicationId() {
		return _oAuth2Authorization.getOAuth2ApplicationId();
	}

	/**
	* Returns the o auth2 application scope aliases ID of this o auth2 authorization.
	*
	* @return the o auth2 application scope aliases ID of this o auth2 authorization
	*/
	@Override
	public long getOAuth2ApplicationScopeAliasesId() {
		return _oAuth2Authorization.getOAuth2ApplicationScopeAliasesId();
	}

	/**
	* Returns the o auth2 authorization ID of this o auth2 authorization.
	*
	* @return the o auth2 authorization ID of this o auth2 authorization
	*/
	@Override
	public long getOAuth2AuthorizationId() {
		return _oAuth2Authorization.getOAuth2AuthorizationId();
	}

	/**
	* Returns the primary key of this o auth2 authorization.
	*
	* @return the primary key of this o auth2 authorization
	*/
	@Override
	public long getPrimaryKey() {
		return _oAuth2Authorization.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuth2Authorization.getPrimaryKeyObj();
	}

	/**
	* Returns the refresh token content of this o auth2 authorization.
	*
	* @return the refresh token content of this o auth2 authorization
	*/
	@Override
	public java.lang.String getRefreshTokenContent() {
		return _oAuth2Authorization.getRefreshTokenContent();
	}

	/**
	* Returns the refresh token create date of this o auth2 authorization.
	*
	* @return the refresh token create date of this o auth2 authorization
	*/
	@Override
	public Date getRefreshTokenCreateDate() {
		return _oAuth2Authorization.getRefreshTokenCreateDate();
	}

	/**
	* Returns the refresh token expiration date of this o auth2 authorization.
	*
	* @return the refresh token expiration date of this o auth2 authorization
	*/
	@Override
	public Date getRefreshTokenExpirationDate() {
		return _oAuth2Authorization.getRefreshTokenExpirationDate();
	}

	/**
	* Returns the remote ip info of this o auth2 authorization.
	*
	* @return the remote ip info of this o auth2 authorization
	*/
	@Override
	public java.lang.String getRemoteIPInfo() {
		return _oAuth2Authorization.getRemoteIPInfo();
	}

	/**
	* Returns the user ID of this o auth2 authorization.
	*
	* @return the user ID of this o auth2 authorization
	*/
	@Override
	public long getUserId() {
		return _oAuth2Authorization.getUserId();
	}

	/**
	* Returns the user name of this o auth2 authorization.
	*
	* @return the user name of this o auth2 authorization
	*/
	@Override
	public java.lang.String getUserName() {
		return _oAuth2Authorization.getUserName();
	}

	/**
	* Returns the user uuid of this o auth2 authorization.
	*
	* @return the user uuid of this o auth2 authorization
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _oAuth2Authorization.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _oAuth2Authorization.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _oAuth2Authorization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _oAuth2Authorization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _oAuth2Authorization.isNew();
	}

	@Override
	public void persist() {
		_oAuth2Authorization.persist();
	}

	/**
	* Sets the access token content of this o auth2 authorization.
	*
	* @param accessTokenContent the access token content of this o auth2 authorization
	*/
	@Override
	public void setAccessTokenContent(java.lang.String accessTokenContent) {
		_oAuth2Authorization.setAccessTokenContent(accessTokenContent);
	}

	/**
	* Sets the access token create date of this o auth2 authorization.
	*
	* @param accessTokenCreateDate the access token create date of this o auth2 authorization
	*/
	@Override
	public void setAccessTokenCreateDate(Date accessTokenCreateDate) {
		_oAuth2Authorization.setAccessTokenCreateDate(accessTokenCreateDate);
	}

	/**
	* Sets the access token expiration date of this o auth2 authorization.
	*
	* @param accessTokenExpirationDate the access token expiration date of this o auth2 authorization
	*/
	@Override
	public void setAccessTokenExpirationDate(Date accessTokenExpirationDate) {
		_oAuth2Authorization.setAccessTokenExpirationDate(accessTokenExpirationDate);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_oAuth2Authorization.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this o auth2 authorization.
	*
	* @param companyId the company ID of this o auth2 authorization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_oAuth2Authorization.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this o auth2 authorization.
	*
	* @param createDate the create date of this o auth2 authorization
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_oAuth2Authorization.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_oAuth2Authorization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_oAuth2Authorization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_oAuth2Authorization.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_oAuth2Authorization.setNew(n);
	}

	/**
	* Sets the o auth2 application ID of this o auth2 authorization.
	*
	* @param oAuth2ApplicationId the o auth2 application ID of this o auth2 authorization
	*/
	@Override
	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2Authorization.setOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	* Sets the o auth2 application scope aliases ID of this o auth2 authorization.
	*
	* @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID of this o auth2 authorization
	*/
	@Override
	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {
		_oAuth2Authorization.setOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);
	}

	/**
	* Sets the o auth2 authorization ID of this o auth2 authorization.
	*
	* @param oAuth2AuthorizationId the o auth2 authorization ID of this o auth2 authorization
	*/
	@Override
	public void setOAuth2AuthorizationId(long oAuth2AuthorizationId) {
		_oAuth2Authorization.setOAuth2AuthorizationId(oAuth2AuthorizationId);
	}

	/**
	* Sets the primary key of this o auth2 authorization.
	*
	* @param primaryKey the primary key of this o auth2 authorization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_oAuth2Authorization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_oAuth2Authorization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the refresh token content of this o auth2 authorization.
	*
	* @param refreshTokenContent the refresh token content of this o auth2 authorization
	*/
	@Override
	public void setRefreshTokenContent(java.lang.String refreshTokenContent) {
		_oAuth2Authorization.setRefreshTokenContent(refreshTokenContent);
	}

	/**
	* Sets the refresh token create date of this o auth2 authorization.
	*
	* @param refreshTokenCreateDate the refresh token create date of this o auth2 authorization
	*/
	@Override
	public void setRefreshTokenCreateDate(Date refreshTokenCreateDate) {
		_oAuth2Authorization.setRefreshTokenCreateDate(refreshTokenCreateDate);
	}

	/**
	* Sets the refresh token expiration date of this o auth2 authorization.
	*
	* @param refreshTokenExpirationDate the refresh token expiration date of this o auth2 authorization
	*/
	@Override
	public void setRefreshTokenExpirationDate(Date refreshTokenExpirationDate) {
		_oAuth2Authorization.setRefreshTokenExpirationDate(refreshTokenExpirationDate);
	}

	/**
	* Sets the remote ip info of this o auth2 authorization.
	*
	* @param remoteIPInfo the remote ip info of this o auth2 authorization
	*/
	@Override
	public void setRemoteIPInfo(java.lang.String remoteIPInfo) {
		_oAuth2Authorization.setRemoteIPInfo(remoteIPInfo);
	}

	/**
	* Sets the user ID of this o auth2 authorization.
	*
	* @param userId the user ID of this o auth2 authorization
	*/
	@Override
	public void setUserId(long userId) {
		_oAuth2Authorization.setUserId(userId);
	}

	/**
	* Sets the user name of this o auth2 authorization.
	*
	* @param userName the user name of this o auth2 authorization
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_oAuth2Authorization.setUserName(userName);
	}

	/**
	* Sets the user uuid of this o auth2 authorization.
	*
	* @param userUuid the user uuid of this o auth2 authorization
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_oAuth2Authorization.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<OAuth2Authorization> toCacheModel() {
		return _oAuth2Authorization.toCacheModel();
	}

	@Override
	public OAuth2Authorization toEscapedModel() {
		return new OAuth2AuthorizationWrapper(_oAuth2Authorization.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _oAuth2Authorization.toString();
	}

	@Override
	public OAuth2Authorization toUnescapedModel() {
		return new OAuth2AuthorizationWrapper(_oAuth2Authorization.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _oAuth2Authorization.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2AuthorizationWrapper)) {
			return false;
		}

		OAuth2AuthorizationWrapper oAuth2AuthorizationWrapper = (OAuth2AuthorizationWrapper)obj;

		if (Objects.equals(_oAuth2Authorization,
					oAuth2AuthorizationWrapper._oAuth2Authorization)) {
			return true;
		}

		return false;
	}

	@Override
	public OAuth2Authorization getWrappedModel() {
		return _oAuth2Authorization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _oAuth2Authorization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _oAuth2Authorization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_oAuth2Authorization.resetOriginalValues();
	}

	private final OAuth2Authorization _oAuth2Authorization;
}