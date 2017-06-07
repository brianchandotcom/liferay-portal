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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PowwowServer}.
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowServer
 * @generated
 */
public class PowwowServerWrapper implements PowwowServer,
	ModelWrapper<PowwowServer> {
	public PowwowServerWrapper(PowwowServer powwowServer) {
		_powwowServer = powwowServer;
	}

	@Override
	public Class<?> getModelClass() {
		return PowwowServer.class;
	}

	@Override
	public String getModelClassName() {
		return PowwowServer.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("powwowServerId", getPowwowServerId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("providerType", getProviderType());
		attributes.put("url", getUrl());
		attributes.put("apiKey", getApiKey());
		attributes.put("secret", getSecret());
		attributes.put("active", getActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long powwowServerId = (Long)attributes.get("powwowServerId");

		if (powwowServerId != null) {
			setPowwowServerId(powwowServerId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String providerType = (String)attributes.get("providerType");

		if (providerType != null) {
			setProviderType(providerType);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String apiKey = (String)attributes.get("apiKey");

		if (apiKey != null) {
			setApiKey(apiKey);
		}

		String secret = (String)attributes.get("secret");

		if (secret != null) {
			setSecret(secret);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	* Returns the primary key of this powwow server.
	*
	* @return the primary key of this powwow server
	*/
	@Override
	public long getPrimaryKey() {
		return _powwowServer.getPrimaryKey();
	}

	/**
	* Sets the primary key of this powwow server.
	*
	* @param primaryKey the primary key of this powwow server
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_powwowServer.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the powwow server ID of this powwow server.
	*
	* @return the powwow server ID of this powwow server
	*/
	@Override
	public long getPowwowServerId() {
		return _powwowServer.getPowwowServerId();
	}

	/**
	* Sets the powwow server ID of this powwow server.
	*
	* @param powwowServerId the powwow server ID of this powwow server
	*/
	@Override
	public void setPowwowServerId(long powwowServerId) {
		_powwowServer.setPowwowServerId(powwowServerId);
	}

	/**
	* Returns the company ID of this powwow server.
	*
	* @return the company ID of this powwow server
	*/
	@Override
	public long getCompanyId() {
		return _powwowServer.getCompanyId();
	}

	/**
	* Sets the company ID of this powwow server.
	*
	* @param companyId the company ID of this powwow server
	*/
	@Override
	public void setCompanyId(long companyId) {
		_powwowServer.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this powwow server.
	*
	* @return the user ID of this powwow server
	*/
	@Override
	public long getUserId() {
		return _powwowServer.getUserId();
	}

	/**
	* Sets the user ID of this powwow server.
	*
	* @param userId the user ID of this powwow server
	*/
	@Override
	public void setUserId(long userId) {
		_powwowServer.setUserId(userId);
	}

	/**
	* Returns the user uuid of this powwow server.
	*
	* @return the user uuid of this powwow server
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowServer.getUserUuid();
	}

	/**
	* Sets the user uuid of this powwow server.
	*
	* @param userUuid the user uuid of this powwow server
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_powwowServer.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this powwow server.
	*
	* @return the user name of this powwow server
	*/
	@Override
	public java.lang.String getUserName() {
		return _powwowServer.getUserName();
	}

	/**
	* Sets the user name of this powwow server.
	*
	* @param userName the user name of this powwow server
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_powwowServer.setUserName(userName);
	}

	/**
	* Returns the create date of this powwow server.
	*
	* @return the create date of this powwow server
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _powwowServer.getCreateDate();
	}

	/**
	* Sets the create date of this powwow server.
	*
	* @param createDate the create date of this powwow server
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_powwowServer.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this powwow server.
	*
	* @return the modified date of this powwow server
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _powwowServer.getModifiedDate();
	}

	/**
	* Sets the modified date of this powwow server.
	*
	* @param modifiedDate the modified date of this powwow server
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_powwowServer.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this powwow server.
	*
	* @return the name of this powwow server
	*/
	@Override
	public java.lang.String getName() {
		return _powwowServer.getName();
	}

	/**
	* Sets the name of this powwow server.
	*
	* @param name the name of this powwow server
	*/
	@Override
	public void setName(java.lang.String name) {
		_powwowServer.setName(name);
	}

	/**
	* Returns the provider type of this powwow server.
	*
	* @return the provider type of this powwow server
	*/
	@Override
	public java.lang.String getProviderType() {
		return _powwowServer.getProviderType();
	}

	/**
	* Sets the provider type of this powwow server.
	*
	* @param providerType the provider type of this powwow server
	*/
	@Override
	public void setProviderType(java.lang.String providerType) {
		_powwowServer.setProviderType(providerType);
	}

	/**
	* Returns the url of this powwow server.
	*
	* @return the url of this powwow server
	*/
	@Override
	public java.lang.String getUrl() {
		return _powwowServer.getUrl();
	}

	/**
	* Sets the url of this powwow server.
	*
	* @param url the url of this powwow server
	*/
	@Override
	public void setUrl(java.lang.String url) {
		_powwowServer.setUrl(url);
	}

	/**
	* Returns the api key of this powwow server.
	*
	* @return the api key of this powwow server
	*/
	@Override
	public java.lang.String getApiKey() {
		return _powwowServer.getApiKey();
	}

	/**
	* Sets the api key of this powwow server.
	*
	* @param apiKey the api key of this powwow server
	*/
	@Override
	public void setApiKey(java.lang.String apiKey) {
		_powwowServer.setApiKey(apiKey);
	}

	/**
	* Returns the secret of this powwow server.
	*
	* @return the secret of this powwow server
	*/
	@Override
	public java.lang.String getSecret() {
		return _powwowServer.getSecret();
	}

	/**
	* Sets the secret of this powwow server.
	*
	* @param secret the secret of this powwow server
	*/
	@Override
	public void setSecret(java.lang.String secret) {
		_powwowServer.setSecret(secret);
	}

	/**
	* Returns the active of this powwow server.
	*
	* @return the active of this powwow server
	*/
	@Override
	public boolean getActive() {
		return _powwowServer.getActive();
	}

	/**
	* Returns <code>true</code> if this powwow server is active.
	*
	* @return <code>true</code> if this powwow server is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _powwowServer.isActive();
	}

	/**
	* Sets whether this powwow server is active.
	*
	* @param active the active of this powwow server
	*/
	@Override
	public void setActive(boolean active) {
		_powwowServer.setActive(active);
	}

	@Override
	public boolean isNew() {
		return _powwowServer.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_powwowServer.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _powwowServer.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_powwowServer.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _powwowServer.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _powwowServer.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_powwowServer.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoBridge getExpandoBridge() {
		return _powwowServer.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_powwowServer.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.expando.kernel.model.ExpandoBridge expandoBridge) {
		_powwowServer.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		_powwowServer.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PowwowServerWrapper((PowwowServer)_powwowServer.clone());
	}

	@Override
	public int compareTo(com.liferay.powwow.model.PowwowServer powwowServer) {
		return _powwowServer.compareTo(powwowServer);
	}

	@Override
	public int hashCode() {
		return _powwowServer.hashCode();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<com.liferay.powwow.model.PowwowServer> toCacheModel() {
		return _powwowServer.toCacheModel();
	}

	@Override
	public com.liferay.powwow.model.PowwowServer toEscapedModel() {
		return new PowwowServerWrapper(_powwowServer.toEscapedModel());
	}

	@Override
	public com.liferay.powwow.model.PowwowServer toUnescapedModel() {
		return new PowwowServerWrapper(_powwowServer.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _powwowServer.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _powwowServer.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_powwowServer.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PowwowServerWrapper)) {
			return false;
		}

		PowwowServerWrapper powwowServerWrapper = (PowwowServerWrapper)obj;

		if (Validator.equals(_powwowServer, powwowServerWrapper._powwowServer)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public PowwowServer getWrappedPowwowServer() {
		return _powwowServer;
	}

	@Override
	public PowwowServer getWrappedModel() {
		return _powwowServer;
	}

	@Override
	public void resetOriginalValues() {
		_powwowServer.resetOriginalValues();
	}

	private PowwowServer _powwowServer;
}