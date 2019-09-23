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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class PowwowServerWrapper
	extends BaseModelWrapper<PowwowServer>
	implements ModelWrapper<PowwowServer>, PowwowServer {

	public PowwowServerWrapper(PowwowServer powwowServer) {
		super(powwowServer);
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
		attributes.put("active", isActive());

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
	 * Returns the active of this powwow server.
	 *
	 * @return the active of this powwow server
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the api key of this powwow server.
	 *
	 * @return the api key of this powwow server
	 */
	@Override
	public String getApiKey() {
		return model.getApiKey();
	}

	/**
	 * Returns the company ID of this powwow server.
	 *
	 * @return the company ID of this powwow server
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this powwow server.
	 *
	 * @return the create date of this powwow server
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this powwow server.
	 *
	 * @return the modified date of this powwow server
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this powwow server.
	 *
	 * @return the name of this powwow server
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the powwow server ID of this powwow server.
	 *
	 * @return the powwow server ID of this powwow server
	 */
	@Override
	public long getPowwowServerId() {
		return model.getPowwowServerId();
	}

	/**
	 * Returns the primary key of this powwow server.
	 *
	 * @return the primary key of this powwow server
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the provider type of this powwow server.
	 *
	 * @return the provider type of this powwow server
	 */
	@Override
	public String getProviderType() {
		return model.getProviderType();
	}

	/**
	 * Returns the secret of this powwow server.
	 *
	 * @return the secret of this powwow server
	 */
	@Override
	public String getSecret() {
		return model.getSecret();
	}

	/**
	 * Returns the url of this powwow server.
	 *
	 * @return the url of this powwow server
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the user ID of this powwow server.
	 *
	 * @return the user ID of this powwow server
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this powwow server.
	 *
	 * @return the user name of this powwow server
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this powwow server.
	 *
	 * @return the user uuid of this powwow server
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this powwow server is active.
	 *
	 * @return <code>true</code> if this powwow server is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a powwow server model instance should use the <code>PowwowServer</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this powwow server is active.
	 *
	 * @param active the active of this powwow server
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the api key of this powwow server.
	 *
	 * @param apiKey the api key of this powwow server
	 */
	@Override
	public void setApiKey(String apiKey) {
		model.setApiKey(apiKey);
	}

	/**
	 * Sets the company ID of this powwow server.
	 *
	 * @param companyId the company ID of this powwow server
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this powwow server.
	 *
	 * @param createDate the create date of this powwow server
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this powwow server.
	 *
	 * @param modifiedDate the modified date of this powwow server
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this powwow server.
	 *
	 * @param name the name of this powwow server
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the powwow server ID of this powwow server.
	 *
	 * @param powwowServerId the powwow server ID of this powwow server
	 */
	@Override
	public void setPowwowServerId(long powwowServerId) {
		model.setPowwowServerId(powwowServerId);
	}

	/**
	 * Sets the primary key of this powwow server.
	 *
	 * @param primaryKey the primary key of this powwow server
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the provider type of this powwow server.
	 *
	 * @param providerType the provider type of this powwow server
	 */
	@Override
	public void setProviderType(String providerType) {
		model.setProviderType(providerType);
	}

	/**
	 * Sets the secret of this powwow server.
	 *
	 * @param secret the secret of this powwow server
	 */
	@Override
	public void setSecret(String secret) {
		model.setSecret(secret);
	}

	/**
	 * Sets the url of this powwow server.
	 *
	 * @param url the url of this powwow server
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the user ID of this powwow server.
	 *
	 * @param userId the user ID of this powwow server
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this powwow server.
	 *
	 * @param userName the user name of this powwow server
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this powwow server.
	 *
	 * @param userUuid the user uuid of this powwow server
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected PowwowServerWrapper wrap(PowwowServer powwowServer) {
		return new PowwowServerWrapper(powwowServer);
	}

}