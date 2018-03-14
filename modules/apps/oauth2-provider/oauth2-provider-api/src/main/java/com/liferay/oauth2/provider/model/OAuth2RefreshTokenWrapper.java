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
 * This class is a wrapper for {@link OAuth2RefreshToken}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2RefreshToken
 * @generated
 */
@ProviderType
public class OAuth2RefreshTokenWrapper implements OAuth2RefreshToken,
	ModelWrapper<OAuth2RefreshToken> {
	public OAuth2RefreshTokenWrapper(OAuth2RefreshToken oAuth2RefreshToken) {
		_oAuth2RefreshToken = oAuth2RefreshToken;
	}

	@Override
	public Class<?> getModelClass() {
		return OAuth2RefreshToken.class;
	}

	@Override
	public String getModelClassName() {
		return OAuth2RefreshToken.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuth2RefreshTokenId", getOAuth2RefreshTokenId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("remoteIPInfo", getRemoteIPInfo());
		attributes.put("oAuth2RefreshTokenContent",
			getOAuth2RefreshTokenContent());
		attributes.put("oAuth2ApplicationId", getOAuth2ApplicationId());
		attributes.put("scopes", getScopes());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuth2RefreshTokenId = (Long)attributes.get("oAuth2RefreshTokenId");

		if (oAuth2RefreshTokenId != null) {
			setOAuth2RefreshTokenId(oAuth2RefreshTokenId);
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

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String remoteIPInfo = (String)attributes.get("remoteIPInfo");

		if (remoteIPInfo != null) {
			setRemoteIPInfo(remoteIPInfo);
		}

		String oAuth2RefreshTokenContent = (String)attributes.get(
				"oAuth2RefreshTokenContent");

		if (oAuth2RefreshTokenContent != null) {
			setOAuth2RefreshTokenContent(oAuth2RefreshTokenContent);
		}

		Long oAuth2ApplicationId = (Long)attributes.get("oAuth2ApplicationId");

		if (oAuth2ApplicationId != null) {
			setOAuth2ApplicationId(oAuth2ApplicationId);
		}

		String scopes = (String)attributes.get("scopes");

		if (scopes != null) {
			setScopes(scopes);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new OAuth2RefreshTokenWrapper((OAuth2RefreshToken)_oAuth2RefreshToken.clone());
	}

	@Override
	public int compareTo(OAuth2RefreshToken oAuth2RefreshToken) {
		return _oAuth2RefreshToken.compareTo(oAuth2RefreshToken);
	}

	/**
	* Returns the company ID of this o auth2 refresh token.
	*
	* @return the company ID of this o auth2 refresh token
	*/
	@Override
	public long getCompanyId() {
		return _oAuth2RefreshToken.getCompanyId();
	}

	/**
	* Returns the create date of this o auth2 refresh token.
	*
	* @return the create date of this o auth2 refresh token
	*/
	@Override
	public Date getCreateDate() {
		return _oAuth2RefreshToken.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _oAuth2RefreshToken.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this o auth2 refresh token.
	*
	* @return the expiration date of this o auth2 refresh token
	*/
	@Override
	public Date getExpirationDate() {
		return _oAuth2RefreshToken.getExpirationDate();
	}

	/**
	* Returns the o auth2 application ID of this o auth2 refresh token.
	*
	* @return the o auth2 application ID of this o auth2 refresh token
	*/
	@Override
	public long getOAuth2ApplicationId() {
		return _oAuth2RefreshToken.getOAuth2ApplicationId();
	}

	/**
	* Returns the o auth2 refresh token content of this o auth2 refresh token.
	*
	* @return the o auth2 refresh token content of this o auth2 refresh token
	*/
	@Override
	public java.lang.String getOAuth2RefreshTokenContent() {
		return _oAuth2RefreshToken.getOAuth2RefreshTokenContent();
	}

	/**
	* Returns the o auth2 refresh token ID of this o auth2 refresh token.
	*
	* @return the o auth2 refresh token ID of this o auth2 refresh token
	*/
	@Override
	public long getOAuth2RefreshTokenId() {
		return _oAuth2RefreshToken.getOAuth2RefreshTokenId();
	}

	/**
	* Returns the primary key of this o auth2 refresh token.
	*
	* @return the primary key of this o auth2 refresh token
	*/
	@Override
	public long getPrimaryKey() {
		return _oAuth2RefreshToken.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuth2RefreshToken.getPrimaryKeyObj();
	}

	/**
	* Returns the remote ip info of this o auth2 refresh token.
	*
	* @return the remote ip info of this o auth2 refresh token
	*/
	@Override
	public java.lang.String getRemoteIPInfo() {
		return _oAuth2RefreshToken.getRemoteIPInfo();
	}

	/**
	* Returns the scopes of this o auth2 refresh token.
	*
	* @return the scopes of this o auth2 refresh token
	*/
	@Override
	public java.lang.String getScopes() {
		return _oAuth2RefreshToken.getScopes();
	}

	@Override
	public java.util.List<java.lang.String> getScopesList() {
		return _oAuth2RefreshToken.getScopesList();
	}

	/**
	* Returns the user ID of this o auth2 refresh token.
	*
	* @return the user ID of this o auth2 refresh token
	*/
	@Override
	public long getUserId() {
		return _oAuth2RefreshToken.getUserId();
	}

	/**
	* Returns the user name of this o auth2 refresh token.
	*
	* @return the user name of this o auth2 refresh token
	*/
	@Override
	public java.lang.String getUserName() {
		return _oAuth2RefreshToken.getUserName();
	}

	/**
	* Returns the user uuid of this o auth2 refresh token.
	*
	* @return the user uuid of this o auth2 refresh token
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _oAuth2RefreshToken.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _oAuth2RefreshToken.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _oAuth2RefreshToken.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _oAuth2RefreshToken.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _oAuth2RefreshToken.isNew();
	}

	@Override
	public void persist() {
		_oAuth2RefreshToken.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_oAuth2RefreshToken.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this o auth2 refresh token.
	*
	* @param companyId the company ID of this o auth2 refresh token
	*/
	@Override
	public void setCompanyId(long companyId) {
		_oAuth2RefreshToken.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this o auth2 refresh token.
	*
	* @param createDate the create date of this o auth2 refresh token
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_oAuth2RefreshToken.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_oAuth2RefreshToken.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_oAuth2RefreshToken.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_oAuth2RefreshToken.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this o auth2 refresh token.
	*
	* @param expirationDate the expiration date of this o auth2 refresh token
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_oAuth2RefreshToken.setExpirationDate(expirationDate);
	}

	@Override
	public void setNew(boolean n) {
		_oAuth2RefreshToken.setNew(n);
	}

	/**
	* Sets the o auth2 application ID of this o auth2 refresh token.
	*
	* @param oAuth2ApplicationId the o auth2 application ID of this o auth2 refresh token
	*/
	@Override
	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2RefreshToken.setOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	* Sets the o auth2 refresh token content of this o auth2 refresh token.
	*
	* @param oAuth2RefreshTokenContent the o auth2 refresh token content of this o auth2 refresh token
	*/
	@Override
	public void setOAuth2RefreshTokenContent(
		java.lang.String oAuth2RefreshTokenContent) {
		_oAuth2RefreshToken.setOAuth2RefreshTokenContent(oAuth2RefreshTokenContent);
	}

	/**
	* Sets the o auth2 refresh token ID of this o auth2 refresh token.
	*
	* @param oAuth2RefreshTokenId the o auth2 refresh token ID of this o auth2 refresh token
	*/
	@Override
	public void setOAuth2RefreshTokenId(long oAuth2RefreshTokenId) {
		_oAuth2RefreshToken.setOAuth2RefreshTokenId(oAuth2RefreshTokenId);
	}

	/**
	* Sets the primary key of this o auth2 refresh token.
	*
	* @param primaryKey the primary key of this o auth2 refresh token
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_oAuth2RefreshToken.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_oAuth2RefreshToken.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the remote ip info of this o auth2 refresh token.
	*
	* @param remoteIPInfo the remote ip info of this o auth2 refresh token
	*/
	@Override
	public void setRemoteIPInfo(java.lang.String remoteIPInfo) {
		_oAuth2RefreshToken.setRemoteIPInfo(remoteIPInfo);
	}

	/**
	* Sets the scopes of this o auth2 refresh token.
	*
	* @param scopes the scopes of this o auth2 refresh token
	*/
	@Override
	public void setScopes(java.lang.String scopes) {
		_oAuth2RefreshToken.setScopes(scopes);
	}

	@Override
	public void setScopesList(java.util.List<java.lang.String> scopesList) {
		_oAuth2RefreshToken.setScopesList(scopesList);
	}

	/**
	* Sets the user ID of this o auth2 refresh token.
	*
	* @param userId the user ID of this o auth2 refresh token
	*/
	@Override
	public void setUserId(long userId) {
		_oAuth2RefreshToken.setUserId(userId);
	}

	/**
	* Sets the user name of this o auth2 refresh token.
	*
	* @param userName the user name of this o auth2 refresh token
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_oAuth2RefreshToken.setUserName(userName);
	}

	/**
	* Sets the user uuid of this o auth2 refresh token.
	*
	* @param userUuid the user uuid of this o auth2 refresh token
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_oAuth2RefreshToken.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<OAuth2RefreshToken> toCacheModel() {
		return _oAuth2RefreshToken.toCacheModel();
	}

	@Override
	public OAuth2RefreshToken toEscapedModel() {
		return new OAuth2RefreshTokenWrapper(_oAuth2RefreshToken.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _oAuth2RefreshToken.toString();
	}

	@Override
	public OAuth2RefreshToken toUnescapedModel() {
		return new OAuth2RefreshTokenWrapper(_oAuth2RefreshToken.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _oAuth2RefreshToken.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2RefreshTokenWrapper)) {
			return false;
		}

		OAuth2RefreshTokenWrapper oAuth2RefreshTokenWrapper = (OAuth2RefreshTokenWrapper)obj;

		if (Objects.equals(_oAuth2RefreshToken,
					oAuth2RefreshTokenWrapper._oAuth2RefreshToken)) {
			return true;
		}

		return false;
	}

	@Override
	public OAuth2RefreshToken getWrappedModel() {
		return _oAuth2RefreshToken;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _oAuth2RefreshToken.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _oAuth2RefreshToken.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_oAuth2RefreshToken.resetOriginalValues();
	}

	private final OAuth2RefreshToken _oAuth2RefreshToken;
}