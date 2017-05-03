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
 * This class is a wrapper for {@link WeDeployAuthToken}.
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthToken
 * @generated
 */
@ProviderType
public class WeDeployAuthTokenWrapper implements WeDeployAuthToken,
	ModelWrapper<WeDeployAuthToken> {
	public WeDeployAuthTokenWrapper(WeDeployAuthToken weDeployAuthToken) {
		_weDeployAuthToken = weDeployAuthToken;
	}

	@Override
	public Class<?> getModelClass() {
		return WeDeployAuthToken.class;
	}

	@Override
	public String getModelClassName() {
		return WeDeployAuthToken.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("weDeployAuthTokenId", getWeDeployAuthTokenId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("clientId", getClientId());
		attributes.put("token", getToken());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long weDeployAuthTokenId = (Long)attributes.get("weDeployAuthTokenId");

		if (weDeployAuthTokenId != null) {
			setWeDeployAuthTokenId(weDeployAuthTokenId);
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

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String clientId = (String)attributes.get("clientId");

		if (clientId != null) {
			setClientId(clientId);
		}

		String token = (String)attributes.get("token");

		if (token != null) {
			setToken(token);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public WeDeployAuthToken toEscapedModel() {
		return new WeDeployAuthTokenWrapper(_weDeployAuthToken.toEscapedModel());
	}

	@Override
	public WeDeployAuthToken toUnescapedModel() {
		return new WeDeployAuthTokenWrapper(_weDeployAuthToken.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _weDeployAuthToken.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _weDeployAuthToken.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _weDeployAuthToken.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _weDeployAuthToken.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<WeDeployAuthToken> toCacheModel() {
		return _weDeployAuthToken.toCacheModel();
	}

	@Override
	public int compareTo(WeDeployAuthToken weDeployAuthToken) {
		return _weDeployAuthToken.compareTo(weDeployAuthToken);
	}

	/**
	* Returns the type of this we deploy auth token.
	*
	* @return the type of this we deploy auth token
	*/
	@Override
	public int getType() {
		return _weDeployAuthToken.getType();
	}

	@Override
	public int hashCode() {
		return _weDeployAuthToken.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _weDeployAuthToken.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new WeDeployAuthTokenWrapper((WeDeployAuthToken)_weDeployAuthToken.clone());
	}

	/**
	* Returns the client ID of this we deploy auth token.
	*
	* @return the client ID of this we deploy auth token
	*/
	@Override
	public java.lang.String getClientId() {
		return _weDeployAuthToken.getClientId();
	}

	/**
	* Returns the token of this we deploy auth token.
	*
	* @return the token of this we deploy auth token
	*/
	@Override
	public java.lang.String getToken() {
		return _weDeployAuthToken.getToken();
	}

	/**
	* Returns the user name of this we deploy auth token.
	*
	* @return the user name of this we deploy auth token
	*/
	@Override
	public java.lang.String getUserName() {
		return _weDeployAuthToken.getUserName();
	}

	/**
	* Returns the user uuid of this we deploy auth token.
	*
	* @return the user uuid of this we deploy auth token
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _weDeployAuthToken.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _weDeployAuthToken.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _weDeployAuthToken.toXmlString();
	}

	/**
	* Returns the create date of this we deploy auth token.
	*
	* @return the create date of this we deploy auth token
	*/
	@Override
	public Date getCreateDate() {
		return _weDeployAuthToken.getCreateDate();
	}

	/**
	* Returns the modified date of this we deploy auth token.
	*
	* @return the modified date of this we deploy auth token
	*/
	@Override
	public Date getModifiedDate() {
		return _weDeployAuthToken.getModifiedDate();
	}

	/**
	* Returns the company ID of this we deploy auth token.
	*
	* @return the company ID of this we deploy auth token
	*/
	@Override
	public long getCompanyId() {
		return _weDeployAuthToken.getCompanyId();
	}

	/**
	* Returns the primary key of this we deploy auth token.
	*
	* @return the primary key of this we deploy auth token
	*/
	@Override
	public long getPrimaryKey() {
		return _weDeployAuthToken.getPrimaryKey();
	}

	/**
	* Returns the user ID of this we deploy auth token.
	*
	* @return the user ID of this we deploy auth token
	*/
	@Override
	public long getUserId() {
		return _weDeployAuthToken.getUserId();
	}

	/**
	* Returns the we deploy auth token ID of this we deploy auth token.
	*
	* @return the we deploy auth token ID of this we deploy auth token
	*/
	@Override
	public long getWeDeployAuthTokenId() {
		return _weDeployAuthToken.getWeDeployAuthTokenId();
	}

	@Override
	public void persist() {
		_weDeployAuthToken.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_weDeployAuthToken.setCachedModel(cachedModel);
	}

	/**
	* Sets the client ID of this we deploy auth token.
	*
	* @param clientId the client ID of this we deploy auth token
	*/
	@Override
	public void setClientId(java.lang.String clientId) {
		_weDeployAuthToken.setClientId(clientId);
	}

	/**
	* Sets the company ID of this we deploy auth token.
	*
	* @param companyId the company ID of this we deploy auth token
	*/
	@Override
	public void setCompanyId(long companyId) {
		_weDeployAuthToken.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this we deploy auth token.
	*
	* @param createDate the create date of this we deploy auth token
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_weDeployAuthToken.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_weDeployAuthToken.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_weDeployAuthToken.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_weDeployAuthToken.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this we deploy auth token.
	*
	* @param modifiedDate the modified date of this we deploy auth token
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_weDeployAuthToken.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_weDeployAuthToken.setNew(n);
	}

	/**
	* Sets the primary key of this we deploy auth token.
	*
	* @param primaryKey the primary key of this we deploy auth token
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_weDeployAuthToken.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_weDeployAuthToken.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the token of this we deploy auth token.
	*
	* @param token the token of this we deploy auth token
	*/
	@Override
	public void setToken(java.lang.String token) {
		_weDeployAuthToken.setToken(token);
	}

	/**
	* Sets the type of this we deploy auth token.
	*
	* @param type the type of this we deploy auth token
	*/
	@Override
	public void setType(int type) {
		_weDeployAuthToken.setType(type);
	}

	/**
	* Sets the user ID of this we deploy auth token.
	*
	* @param userId the user ID of this we deploy auth token
	*/
	@Override
	public void setUserId(long userId) {
		_weDeployAuthToken.setUserId(userId);
	}

	/**
	* Sets the user name of this we deploy auth token.
	*
	* @param userName the user name of this we deploy auth token
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_weDeployAuthToken.setUserName(userName);
	}

	/**
	* Sets the user uuid of this we deploy auth token.
	*
	* @param userUuid the user uuid of this we deploy auth token
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_weDeployAuthToken.setUserUuid(userUuid);
	}

	/**
	* Sets the we deploy auth token ID of this we deploy auth token.
	*
	* @param weDeployAuthTokenId the we deploy auth token ID of this we deploy auth token
	*/
	@Override
	public void setWeDeployAuthTokenId(long weDeployAuthTokenId) {
		_weDeployAuthToken.setWeDeployAuthTokenId(weDeployAuthTokenId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WeDeployAuthTokenWrapper)) {
			return false;
		}

		WeDeployAuthTokenWrapper weDeployAuthTokenWrapper = (WeDeployAuthTokenWrapper)obj;

		if (Objects.equals(_weDeployAuthToken,
					weDeployAuthTokenWrapper._weDeployAuthToken)) {
			return true;
		}

		return false;
	}

	@Override
	public WeDeployAuthToken getWrappedModel() {
		return _weDeployAuthToken;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _weDeployAuthToken.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _weDeployAuthToken.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_weDeployAuthToken.resetOriginalValues();
	}

	private final WeDeployAuthToken _weDeployAuthToken;
}