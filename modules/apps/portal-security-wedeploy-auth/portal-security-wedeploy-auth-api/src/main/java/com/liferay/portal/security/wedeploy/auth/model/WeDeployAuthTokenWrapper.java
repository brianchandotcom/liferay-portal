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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WeDeployAuthToken}.
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthToken
 * @generated
 */
public class WeDeployAuthTokenWrapper
	extends BaseModelWrapper<WeDeployAuthToken>
	implements ModelWrapper<WeDeployAuthToken>, WeDeployAuthToken {

	public WeDeployAuthTokenWrapper(WeDeployAuthToken weDeployAuthToken) {
		super(weDeployAuthToken);
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

	/**
	 * Returns the client ID of this we deploy auth token.
	 *
	 * @return the client ID of this we deploy auth token
	 */
	@Override
	public String getClientId() {
		return model.getClientId();
	}

	/**
	 * Returns the company ID of this we deploy auth token.
	 *
	 * @return the company ID of this we deploy auth token
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this we deploy auth token.
	 *
	 * @return the create date of this we deploy auth token
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this we deploy auth token.
	 *
	 * @return the modified date of this we deploy auth token
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this we deploy auth token.
	 *
	 * @return the primary key of this we deploy auth token
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the token of this we deploy auth token.
	 *
	 * @return the token of this we deploy auth token
	 */
	@Override
	public String getToken() {
		return model.getToken();
	}

	/**
	 * Returns the type of this we deploy auth token.
	 *
	 * @return the type of this we deploy auth token
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this we deploy auth token.
	 *
	 * @return the user ID of this we deploy auth token
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this we deploy auth token.
	 *
	 * @return the user name of this we deploy auth token
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this we deploy auth token.
	 *
	 * @return the user uuid of this we deploy auth token
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the we deploy auth token ID of this we deploy auth token.
	 *
	 * @return the we deploy auth token ID of this we deploy auth token
	 */
	@Override
	public long getWeDeployAuthTokenId() {
		return model.getWeDeployAuthTokenId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a we deploy auth token model instance should use the <code>WeDeployAuthToken</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the client ID of this we deploy auth token.
	 *
	 * @param clientId the client ID of this we deploy auth token
	 */
	@Override
	public void setClientId(String clientId) {
		model.setClientId(clientId);
	}

	/**
	 * Sets the company ID of this we deploy auth token.
	 *
	 * @param companyId the company ID of this we deploy auth token
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this we deploy auth token.
	 *
	 * @param createDate the create date of this we deploy auth token
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this we deploy auth token.
	 *
	 * @param modifiedDate the modified date of this we deploy auth token
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this we deploy auth token.
	 *
	 * @param primaryKey the primary key of this we deploy auth token
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the token of this we deploy auth token.
	 *
	 * @param token the token of this we deploy auth token
	 */
	@Override
	public void setToken(String token) {
		model.setToken(token);
	}

	/**
	 * Sets the type of this we deploy auth token.
	 *
	 * @param type the type of this we deploy auth token
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this we deploy auth token.
	 *
	 * @param userId the user ID of this we deploy auth token
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this we deploy auth token.
	 *
	 * @param userName the user name of this we deploy auth token
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this we deploy auth token.
	 *
	 * @param userUuid the user uuid of this we deploy auth token
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the we deploy auth token ID of this we deploy auth token.
	 *
	 * @param weDeployAuthTokenId the we deploy auth token ID of this we deploy auth token
	 */
	@Override
	public void setWeDeployAuthTokenId(long weDeployAuthTokenId) {
		model.setWeDeployAuthTokenId(weDeployAuthTokenId);
	}

	@Override
	protected WeDeployAuthTokenWrapper wrap(
		WeDeployAuthToken weDeployAuthToken) {

		return new WeDeployAuthTokenWrapper(weDeployAuthToken);
	}

}