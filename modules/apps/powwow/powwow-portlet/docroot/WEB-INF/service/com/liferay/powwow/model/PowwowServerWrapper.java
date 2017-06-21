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
 * This class is a wrapper for {@link PowwowServer}.
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowServer
 * @generated
 */
@ProviderType
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

	@Override
	public PowwowServer toEscapedModel() {
		return new PowwowServerWrapper(_powwowServer.toEscapedModel());
	}

	@Override
	public PowwowServer toUnescapedModel() {
		return new PowwowServerWrapper(_powwowServer.toUnescapedModel());
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

	@Override
	public boolean isCachedModel() {
		return _powwowServer.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _powwowServer.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _powwowServer.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _powwowServer.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<PowwowServer> toCacheModel() {
		return _powwowServer.toCacheModel();
	}

	@Override
	public int compareTo(PowwowServer powwowServer) {
		return _powwowServer.compareTo(powwowServer);
	}

	@Override
	public int hashCode() {
		return _powwowServer.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _powwowServer.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new PowwowServerWrapper((PowwowServer)_powwowServer.clone());
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
	* Returns the name of this powwow server.
	*
	* @return the name of this powwow server
	*/
	@Override
	public java.lang.String getName() {
		return _powwowServer.getName();
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
	* Returns the secret of this powwow server.
	*
	* @return the secret of this powwow server
	*/
	@Override
	public java.lang.String getSecret() {
		return _powwowServer.getSecret();
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
	* Returns the user name of this powwow server.
	*
	* @return the user name of this powwow server
	*/
	@Override
	public java.lang.String getUserName() {
		return _powwowServer.getUserName();
	}

	/**
	* Returns the user uuid of this powwow server.
	*
	* @return the user uuid of this powwow server
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _powwowServer.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _powwowServer.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _powwowServer.toXmlString();
	}

	/**
	* Returns the create date of this powwow server.
	*
	* @return the create date of this powwow server
	*/
	@Override
	public Date getCreateDate() {
		return _powwowServer.getCreateDate();
	}

	/**
	* Returns the modified date of this powwow server.
	*
	* @return the modified date of this powwow server
	*/
	@Override
	public Date getModifiedDate() {
		return _powwowServer.getModifiedDate();
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
	* Returns the powwow server ID of this powwow server.
	*
	* @return the powwow server ID of this powwow server
	*/
	@Override
	public long getPowwowServerId() {
		return _powwowServer.getPowwowServerId();
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
	* Returns the user ID of this powwow server.
	*
	* @return the user ID of this powwow server
	*/
	@Override
	public long getUserId() {
		return _powwowServer.getUserId();
	}

	@Override
	public void persist() {
		_powwowServer.persist();
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

	/**
	* Sets the api key of this powwow server.
	*
	* @param apiKey the api key of this powwow server
	*/
	@Override
	public void setApiKey(java.lang.String apiKey) {
		_powwowServer.setApiKey(apiKey);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_powwowServer.setCachedModel(cachedModel);
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
	* Sets the create date of this powwow server.
	*
	* @param createDate the create date of this powwow server
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_powwowServer.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_powwowServer.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_powwowServer.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_powwowServer.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this powwow server.
	*
	* @param modifiedDate the modified date of this powwow server
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_powwowServer.setModifiedDate(modifiedDate);
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

	@Override
	public void setNew(boolean n) {
		_powwowServer.setNew(n);
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
	* Sets the primary key of this powwow server.
	*
	* @param primaryKey the primary key of this powwow server
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_powwowServer.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_powwowServer.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the secret of this powwow server.
	*
	* @param secret the secret of this powwow server
	*/
	@Override
	public void setSecret(java.lang.String secret) {
		_powwowServer.setSecret(secret);
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
	* Sets the user ID of this powwow server.
	*
	* @param userId the user ID of this powwow server
	*/
	@Override
	public void setUserId(long userId) {
		_powwowServer.setUserId(userId);
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
	* Sets the user uuid of this powwow server.
	*
	* @param userUuid the user uuid of this powwow server
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_powwowServer.setUserUuid(userUuid);
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

		if (Objects.equals(_powwowServer, powwowServerWrapper._powwowServer)) {
			return true;
		}

		return false;
	}

	@Override
	public PowwowServer getWrappedModel() {
		return _powwowServer;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _powwowServer.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _powwowServer.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_powwowServer.resetOriginalValues();
	}

	private final PowwowServer _powwowServer;
}