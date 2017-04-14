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
 * This class is a wrapper for {@link WeDeployAuthApp}.
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthApp
 * @generated
 */
@ProviderType
public class WeDeployAuthAppWrapper implements WeDeployAuthApp,
	ModelWrapper<WeDeployAuthApp> {
	public WeDeployAuthAppWrapper(WeDeployAuthApp weDeployAuthApp) {
		_weDeployAuthApp = weDeployAuthApp;
	}

	@Override
	public Class<?> getModelClass() {
		return WeDeployAuthApp.class;
	}

	@Override
	public String getModelClassName() {
		return WeDeployAuthApp.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("weDeployAuthAppId", getWeDeployAuthAppId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("clientId", getClientId());
		attributes.put("clientSecret", getClientSecret());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long weDeployAuthAppId = (Long)attributes.get("weDeployAuthAppId");

		if (weDeployAuthAppId != null) {
			setWeDeployAuthAppId(weDeployAuthAppId);
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

		String clientId = (String)attributes.get("clientId");

		if (clientId != null) {
			setClientId(clientId);
		}

		String clientSecret = (String)attributes.get("clientSecret");

		if (clientSecret != null) {
			setClientSecret(clientSecret);
		}
	}

	@Override
	public WeDeployAuthApp toEscapedModel() {
		return new WeDeployAuthAppWrapper(_weDeployAuthApp.toEscapedModel());
	}

	@Override
	public WeDeployAuthApp toUnescapedModel() {
		return new WeDeployAuthAppWrapper(_weDeployAuthApp.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _weDeployAuthApp.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _weDeployAuthApp.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _weDeployAuthApp.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _weDeployAuthApp.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<WeDeployAuthApp> toCacheModel() {
		return _weDeployAuthApp.toCacheModel();
	}

	@Override
	public int compareTo(WeDeployAuthApp weDeployAuthApp) {
		return _weDeployAuthApp.compareTo(weDeployAuthApp);
	}

	@Override
	public int hashCode() {
		return _weDeployAuthApp.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _weDeployAuthApp.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new WeDeployAuthAppWrapper((WeDeployAuthApp)_weDeployAuthApp.clone());
	}

	/**
	* Returns the client ID of this we deploy auth app.
	*
	* @return the client ID of this we deploy auth app
	*/
	@Override
	public java.lang.String getClientId() {
		return _weDeployAuthApp.getClientId();
	}

	/**
	* Returns the client secret of this we deploy auth app.
	*
	* @return the client secret of this we deploy auth app
	*/
	@Override
	public java.lang.String getClientSecret() {
		return _weDeployAuthApp.getClientSecret();
	}

	/**
	* Returns the name of this we deploy auth app.
	*
	* @return the name of this we deploy auth app
	*/
	@Override
	public java.lang.String getName() {
		return _weDeployAuthApp.getName();
	}

	/**
	* Returns the user name of this we deploy auth app.
	*
	* @return the user name of this we deploy auth app
	*/
	@Override
	public java.lang.String getUserName() {
		return _weDeployAuthApp.getUserName();
	}

	/**
	* Returns the user uuid of this we deploy auth app.
	*
	* @return the user uuid of this we deploy auth app
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _weDeployAuthApp.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _weDeployAuthApp.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _weDeployAuthApp.toXmlString();
	}

	/**
	* Returns the create date of this we deploy auth app.
	*
	* @return the create date of this we deploy auth app
	*/
	@Override
	public Date getCreateDate() {
		return _weDeployAuthApp.getCreateDate();
	}

	/**
	* Returns the modified date of this we deploy auth app.
	*
	* @return the modified date of this we deploy auth app
	*/
	@Override
	public Date getModifiedDate() {
		return _weDeployAuthApp.getModifiedDate();
	}

	/**
	* Returns the company ID of this we deploy auth app.
	*
	* @return the company ID of this we deploy auth app
	*/
	@Override
	public long getCompanyId() {
		return _weDeployAuthApp.getCompanyId();
	}

	/**
	* Returns the primary key of this we deploy auth app.
	*
	* @return the primary key of this we deploy auth app
	*/
	@Override
	public long getPrimaryKey() {
		return _weDeployAuthApp.getPrimaryKey();
	}

	/**
	* Returns the user ID of this we deploy auth app.
	*
	* @return the user ID of this we deploy auth app
	*/
	@Override
	public long getUserId() {
		return _weDeployAuthApp.getUserId();
	}

	/**
	* Returns the we deploy auth app ID of this we deploy auth app.
	*
	* @return the we deploy auth app ID of this we deploy auth app
	*/
	@Override
	public long getWeDeployAuthAppId() {
		return _weDeployAuthApp.getWeDeployAuthAppId();
	}

	@Override
	public void persist() {
		_weDeployAuthApp.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_weDeployAuthApp.setCachedModel(cachedModel);
	}

	/**
	* Sets the client ID of this we deploy auth app.
	*
	* @param clientId the client ID of this we deploy auth app
	*/
	@Override
	public void setClientId(java.lang.String clientId) {
		_weDeployAuthApp.setClientId(clientId);
	}

	/**
	* Sets the client secret of this we deploy auth app.
	*
	* @param clientSecret the client secret of this we deploy auth app
	*/
	@Override
	public void setClientSecret(java.lang.String clientSecret) {
		_weDeployAuthApp.setClientSecret(clientSecret);
	}

	/**
	* Sets the company ID of this we deploy auth app.
	*
	* @param companyId the company ID of this we deploy auth app
	*/
	@Override
	public void setCompanyId(long companyId) {
		_weDeployAuthApp.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this we deploy auth app.
	*
	* @param createDate the create date of this we deploy auth app
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_weDeployAuthApp.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_weDeployAuthApp.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_weDeployAuthApp.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_weDeployAuthApp.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this we deploy auth app.
	*
	* @param modifiedDate the modified date of this we deploy auth app
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_weDeployAuthApp.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this we deploy auth app.
	*
	* @param name the name of this we deploy auth app
	*/
	@Override
	public void setName(java.lang.String name) {
		_weDeployAuthApp.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_weDeployAuthApp.setNew(n);
	}

	/**
	* Sets the primary key of this we deploy auth app.
	*
	* @param primaryKey the primary key of this we deploy auth app
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_weDeployAuthApp.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_weDeployAuthApp.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this we deploy auth app.
	*
	* @param userId the user ID of this we deploy auth app
	*/
	@Override
	public void setUserId(long userId) {
		_weDeployAuthApp.setUserId(userId);
	}

	/**
	* Sets the user name of this we deploy auth app.
	*
	* @param userName the user name of this we deploy auth app
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_weDeployAuthApp.setUserName(userName);
	}

	/**
	* Sets the user uuid of this we deploy auth app.
	*
	* @param userUuid the user uuid of this we deploy auth app
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_weDeployAuthApp.setUserUuid(userUuid);
	}

	/**
	* Sets the we deploy auth app ID of this we deploy auth app.
	*
	* @param weDeployAuthAppId the we deploy auth app ID of this we deploy auth app
	*/
	@Override
	public void setWeDeployAuthAppId(long weDeployAuthAppId) {
		_weDeployAuthApp.setWeDeployAuthAppId(weDeployAuthAppId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WeDeployAuthAppWrapper)) {
			return false;
		}

		WeDeployAuthAppWrapper weDeployAuthAppWrapper = (WeDeployAuthAppWrapper)obj;

		if (Objects.equals(_weDeployAuthApp,
					weDeployAuthAppWrapper._weDeployAuthApp)) {
			return true;
		}

		return false;
	}

	@Override
	public WeDeployAuthApp getWrappedModel() {
		return _weDeployAuthApp;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _weDeployAuthApp.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _weDeployAuthApp.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_weDeployAuthApp.resetOriginalValues();
	}

	private final WeDeployAuthApp _weDeployAuthApp;
}