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
 * This class is a wrapper for {@link WeDeployAuthApp}.
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthApp
 * @generated
 */
public class WeDeployAuthAppWrapper
	extends BaseModelWrapper<WeDeployAuthApp>
	implements ModelWrapper<WeDeployAuthApp>, WeDeployAuthApp {

	public WeDeployAuthAppWrapper(WeDeployAuthApp weDeployAuthApp) {
		super(weDeployAuthApp);
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
		attributes.put("redirectURI", getRedirectURI());
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

		String redirectURI = (String)attributes.get("redirectURI");

		if (redirectURI != null) {
			setRedirectURI(redirectURI);
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

	/**
	 * Returns the client ID of this we deploy auth app.
	 *
	 * @return the client ID of this we deploy auth app
	 */
	@Override
	public String getClientId() {
		return model.getClientId();
	}

	/**
	 * Returns the client secret of this we deploy auth app.
	 *
	 * @return the client secret of this we deploy auth app
	 */
	@Override
	public String getClientSecret() {
		return model.getClientSecret();
	}

	/**
	 * Returns the company ID of this we deploy auth app.
	 *
	 * @return the company ID of this we deploy auth app
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this we deploy auth app.
	 *
	 * @return the create date of this we deploy auth app
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this we deploy auth app.
	 *
	 * @return the modified date of this we deploy auth app
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this we deploy auth app.
	 *
	 * @return the name of this we deploy auth app
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this we deploy auth app.
	 *
	 * @return the primary key of this we deploy auth app
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the redirect uri of this we deploy auth app.
	 *
	 * @return the redirect uri of this we deploy auth app
	 */
	@Override
	public String getRedirectURI() {
		return model.getRedirectURI();
	}

	/**
	 * Returns the user ID of this we deploy auth app.
	 *
	 * @return the user ID of this we deploy auth app
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this we deploy auth app.
	 *
	 * @return the user name of this we deploy auth app
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this we deploy auth app.
	 *
	 * @return the user uuid of this we deploy auth app
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the we deploy auth app ID of this we deploy auth app.
	 *
	 * @return the we deploy auth app ID of this we deploy auth app
	 */
	@Override
	public long getWeDeployAuthAppId() {
		return model.getWeDeployAuthAppId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a we deploy auth app model instance should use the <code>WeDeployAuthApp</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the client ID of this we deploy auth app.
	 *
	 * @param clientId the client ID of this we deploy auth app
	 */
	@Override
	public void setClientId(String clientId) {
		model.setClientId(clientId);
	}

	/**
	 * Sets the client secret of this we deploy auth app.
	 *
	 * @param clientSecret the client secret of this we deploy auth app
	 */
	@Override
	public void setClientSecret(String clientSecret) {
		model.setClientSecret(clientSecret);
	}

	/**
	 * Sets the company ID of this we deploy auth app.
	 *
	 * @param companyId the company ID of this we deploy auth app
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this we deploy auth app.
	 *
	 * @param createDate the create date of this we deploy auth app
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this we deploy auth app.
	 *
	 * @param modifiedDate the modified date of this we deploy auth app
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this we deploy auth app.
	 *
	 * @param name the name of this we deploy auth app
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this we deploy auth app.
	 *
	 * @param primaryKey the primary key of this we deploy auth app
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the redirect uri of this we deploy auth app.
	 *
	 * @param redirectURI the redirect uri of this we deploy auth app
	 */
	@Override
	public void setRedirectURI(String redirectURI) {
		model.setRedirectURI(redirectURI);
	}

	/**
	 * Sets the user ID of this we deploy auth app.
	 *
	 * @param userId the user ID of this we deploy auth app
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this we deploy auth app.
	 *
	 * @param userName the user name of this we deploy auth app
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this we deploy auth app.
	 *
	 * @param userUuid the user uuid of this we deploy auth app
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the we deploy auth app ID of this we deploy auth app.
	 *
	 * @param weDeployAuthAppId the we deploy auth app ID of this we deploy auth app
	 */
	@Override
	public void setWeDeployAuthAppId(long weDeployAuthAppId) {
		model.setWeDeployAuthAppId(weDeployAuthAppId);
	}

	@Override
	protected WeDeployAuthAppWrapper wrap(WeDeployAuthApp weDeployAuthApp) {
		return new WeDeployAuthAppWrapper(weDeployAuthApp);
	}

}