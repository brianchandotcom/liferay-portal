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
 * This class is a wrapper for {@link OAuth2AccessToken}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AccessToken
 * @generated
 */
@ProviderType
public class OAuth2AccessTokenWrapper implements OAuth2AccessToken,
	ModelWrapper<OAuth2AccessToken> {
	public OAuth2AccessTokenWrapper(OAuth2AccessToken oAuth2AccessToken) {
		_oAuth2AccessToken = oAuth2AccessToken;
	}

	@Override
	public Class<?> getModelClass() {
		return OAuth2AccessToken.class;
	}

	@Override
	public String getModelClassName() {
		return OAuth2AccessToken.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuth2AccessTokenId", getOAuth2AccessTokenId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("oAuth2ApplicationId", getOAuth2ApplicationId());
		attributes.put("oAuth2RefreshTokenId", getOAuth2RefreshTokenId());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("remoteIPInfo", getRemoteIPInfo());
		attributes.put("scopeAliases", getScopeAliases());
		attributes.put("tokenContent", getTokenContent());
		attributes.put("tokenType", getTokenType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuth2AccessTokenId = (Long)attributes.get("oAuth2AccessTokenId");

		if (oAuth2AccessTokenId != null) {
			setOAuth2AccessTokenId(oAuth2AccessTokenId);
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

		Long oAuth2RefreshTokenId = (Long)attributes.get("oAuth2RefreshTokenId");

		if (oAuth2RefreshTokenId != null) {
			setOAuth2RefreshTokenId(oAuth2RefreshTokenId);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String remoteIPInfo = (String)attributes.get("remoteIPInfo");

		if (remoteIPInfo != null) {
			setRemoteIPInfo(remoteIPInfo);
		}

		String scopeAliases = (String)attributes.get("scopeAliases");

		if (scopeAliases != null) {
			setScopeAliases(scopeAliases);
		}

		String tokenContent = (String)attributes.get("tokenContent");

		if (tokenContent != null) {
			setTokenContent(tokenContent);
		}

		String tokenType = (String)attributes.get("tokenType");

		if (tokenType != null) {
			setTokenType(tokenType);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new OAuth2AccessTokenWrapper((OAuth2AccessToken)_oAuth2AccessToken.clone());
	}

	@Override
	public int compareTo(OAuth2AccessToken oAuth2AccessToken) {
		return _oAuth2AccessToken.compareTo(oAuth2AccessToken);
	}

	/**
	* Returns the company ID of this o auth2 access token.
	*
	* @return the company ID of this o auth2 access token
	*/
	@Override
	public long getCompanyId() {
		return _oAuth2AccessToken.getCompanyId();
	}

	/**
	* Returns the create date of this o auth2 access token.
	*
	* @return the create date of this o auth2 access token
	*/
	@Override
	public Date getCreateDate() {
		return _oAuth2AccessToken.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _oAuth2AccessToken.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this o auth2 access token.
	*
	* @return the expiration date of this o auth2 access token
	*/
	@Override
	public Date getExpirationDate() {
		return _oAuth2AccessToken.getExpirationDate();
	}

	/**
	* Returns the o auth2 access token ID of this o auth2 access token.
	*
	* @return the o auth2 access token ID of this o auth2 access token
	*/
	@Override
	public long getOAuth2AccessTokenId() {
		return _oAuth2AccessToken.getOAuth2AccessTokenId();
	}

	/**
	* Returns the o auth2 application ID of this o auth2 access token.
	*
	* @return the o auth2 application ID of this o auth2 access token
	*/
	@Override
	public long getOAuth2ApplicationId() {
		return _oAuth2AccessToken.getOAuth2ApplicationId();
	}

	/**
	* Returns the o auth2 refresh token ID of this o auth2 access token.
	*
	* @return the o auth2 refresh token ID of this o auth2 access token
	*/
	@Override
	public long getOAuth2RefreshTokenId() {
		return _oAuth2AccessToken.getOAuth2RefreshTokenId();
	}

	/**
	* Returns the primary key of this o auth2 access token.
	*
	* @return the primary key of this o auth2 access token
	*/
	@Override
	public long getPrimaryKey() {
		return _oAuth2AccessToken.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuth2AccessToken.getPrimaryKeyObj();
	}

	/**
	* Returns the remote ip info of this o auth2 access token.
	*
	* @return the remote ip info of this o auth2 access token
	*/
	@Override
	public java.lang.String getRemoteIPInfo() {
		return _oAuth2AccessToken.getRemoteIPInfo();
	}

	/**
	* Returns the scope aliases of this o auth2 access token.
	*
	* @return the scope aliases of this o auth2 access token
	*/
	@Override
	public java.lang.String getScopeAliases() {
		return _oAuth2AccessToken.getScopeAliases();
	}

	/**
	* Returns the token content of this o auth2 access token.
	*
	* @return the token content of this o auth2 access token
	*/
	@Override
	public java.lang.String getTokenContent() {
		return _oAuth2AccessToken.getTokenContent();
	}

	/**
	* Returns the token type of this o auth2 access token.
	*
	* @return the token type of this o auth2 access token
	*/
	@Override
	public java.lang.String getTokenType() {
		return _oAuth2AccessToken.getTokenType();
	}

	/**
	* Returns the user ID of this o auth2 access token.
	*
	* @return the user ID of this o auth2 access token
	*/
	@Override
	public long getUserId() {
		return _oAuth2AccessToken.getUserId();
	}

	/**
	* Returns the user name of this o auth2 access token.
	*
	* @return the user name of this o auth2 access token
	*/
	@Override
	public java.lang.String getUserName() {
		return _oAuth2AccessToken.getUserName();
	}

	/**
	* Returns the user uuid of this o auth2 access token.
	*
	* @return the user uuid of this o auth2 access token
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _oAuth2AccessToken.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _oAuth2AccessToken.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _oAuth2AccessToken.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _oAuth2AccessToken.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _oAuth2AccessToken.isNew();
	}

	@Override
	public void persist() {
		_oAuth2AccessToken.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_oAuth2AccessToken.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this o auth2 access token.
	*
	* @param companyId the company ID of this o auth2 access token
	*/
	@Override
	public void setCompanyId(long companyId) {
		_oAuth2AccessToken.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this o auth2 access token.
	*
	* @param createDate the create date of this o auth2 access token
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_oAuth2AccessToken.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_oAuth2AccessToken.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_oAuth2AccessToken.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_oAuth2AccessToken.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this o auth2 access token.
	*
	* @param expirationDate the expiration date of this o auth2 access token
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_oAuth2AccessToken.setExpirationDate(expirationDate);
	}

	@Override
	public void setNew(boolean n) {
		_oAuth2AccessToken.setNew(n);
	}

	/**
	* Sets the o auth2 access token ID of this o auth2 access token.
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID of this o auth2 access token
	*/
	@Override
	public void setOAuth2AccessTokenId(long oAuth2AccessTokenId) {
		_oAuth2AccessToken.setOAuth2AccessTokenId(oAuth2AccessTokenId);
	}

	/**
	* Sets the o auth2 application ID of this o auth2 access token.
	*
	* @param oAuth2ApplicationId the o auth2 application ID of this o auth2 access token
	*/
	@Override
	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2AccessToken.setOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	* Sets the o auth2 refresh token ID of this o auth2 access token.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID of this o auth2 access token
	*/
	@Override
	public void setOAuth2RefreshTokenId(long oAuth2RefreshTokenId) {
		_oAuth2AccessToken.setOAuth2RefreshTokenId(oAuth2RefreshTokenId);
	}

	/**
	* Sets the primary key of this o auth2 access token.
	*
	* @param primaryKey the primary key of this o auth2 access token
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_oAuth2AccessToken.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_oAuth2AccessToken.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the remote ip info of this o auth2 access token.
	*
	* @param remoteIPInfo the remote ip info of this o auth2 access token
	*/
	@Override
	public void setRemoteIPInfo(java.lang.String remoteIPInfo) {
		_oAuth2AccessToken.setRemoteIPInfo(remoteIPInfo);
	}

	/**
	* Sets the scope aliases of this o auth2 access token.
	*
	* @param scopeAliases the scope aliases of this o auth2 access token
	*/
	@Override
	public void setScopeAliases(java.lang.String scopeAliases) {
		_oAuth2AccessToken.setScopeAliases(scopeAliases);
	}

	/**
	* Sets the token content of this o auth2 access token.
	*
	* @param tokenContent the token content of this o auth2 access token
	*/
	@Override
	public void setTokenContent(java.lang.String tokenContent) {
		_oAuth2AccessToken.setTokenContent(tokenContent);
	}

	/**
	* Sets the token type of this o auth2 access token.
	*
	* @param tokenType the token type of this o auth2 access token
	*/
	@Override
	public void setTokenType(java.lang.String tokenType) {
		_oAuth2AccessToken.setTokenType(tokenType);
	}

	/**
	* Sets the user ID of this o auth2 access token.
	*
	* @param userId the user ID of this o auth2 access token
	*/
	@Override
	public void setUserId(long userId) {
		_oAuth2AccessToken.setUserId(userId);
	}

	/**
	* Sets the user name of this o auth2 access token.
	*
	* @param userName the user name of this o auth2 access token
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_oAuth2AccessToken.setUserName(userName);
	}

	/**
	* Sets the user uuid of this o auth2 access token.
	*
	* @param userUuid the user uuid of this o auth2 access token
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_oAuth2AccessToken.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<OAuth2AccessToken> toCacheModel() {
		return _oAuth2AccessToken.toCacheModel();
	}

	@Override
	public OAuth2AccessToken toEscapedModel() {
		return new OAuth2AccessTokenWrapper(_oAuth2AccessToken.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _oAuth2AccessToken.toString();
	}

	@Override
	public OAuth2AccessToken toUnescapedModel() {
		return new OAuth2AccessTokenWrapper(_oAuth2AccessToken.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _oAuth2AccessToken.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2AccessTokenWrapper)) {
			return false;
		}

		OAuth2AccessTokenWrapper oAuth2AccessTokenWrapper = (OAuth2AccessTokenWrapper)obj;

		if (Objects.equals(_oAuth2AccessToken,
					oAuth2AccessTokenWrapper._oAuth2AccessToken)) {
			return true;
		}

		return false;
	}

	@Override
	public OAuth2AccessToken getWrappedModel() {
		return _oAuth2AccessToken;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _oAuth2AccessToken.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _oAuth2AccessToken.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_oAuth2AccessToken.resetOriginalValues();
	}

	private final OAuth2AccessToken _oAuth2AccessToken;
}