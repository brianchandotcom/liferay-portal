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

package com.liferay.portal.security.sso.openid.connect.persistence.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OpenIdConnectSession}.
 * </p>
 *
 * @author Arthur Chan
 * @see OpenIdConnectSession
 * @generated
 */
public class OpenIdConnectSessionWrapper
	extends BaseModelWrapper<OpenIdConnectSession>
	implements ModelWrapper<OpenIdConnectSession>, OpenIdConnectSession {

	public OpenIdConnectSessionWrapper(
		OpenIdConnectSession openIdConnectSession) {

		super(openIdConnectSession);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("openIdConnectSessionId", getOpenIdConnectSessionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("userId", getUserId());
		attributes.put("accessToken", getAccessToken());
		attributes.put("configurationPid", getConfigurationPid());
		attributes.put("idToken", getIdToken());
		attributes.put("providerName", getProviderName());
		attributes.put("refreshToken", getRefreshToken());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long openIdConnectSessionId = (Long)attributes.get(
			"openIdConnectSessionId");

		if (openIdConnectSessionId != null) {
			setOpenIdConnectSessionId(openIdConnectSessionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String accessToken = (String)attributes.get("accessToken");

		if (accessToken != null) {
			setAccessToken(accessToken);
		}

		String configurationPid = (String)attributes.get("configurationPid");

		if (configurationPid != null) {
			setConfigurationPid(configurationPid);
		}

		String idToken = (String)attributes.get("idToken");

		if (idToken != null) {
			setIdToken(idToken);
		}

		String providerName = (String)attributes.get("providerName");

		if (providerName != null) {
			setProviderName(providerName);
		}

		String refreshToken = (String)attributes.get("refreshToken");

		if (refreshToken != null) {
			setRefreshToken(refreshToken);
		}
	}

	@Override
	public OpenIdConnectSession cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the access token of this open ID connect session.
	 *
	 * @return the access token of this open ID connect session
	 */
	@Override
	public String getAccessToken() {
		return model.getAccessToken();
	}

	/**
	 * Returns the company ID of this open ID connect session.
	 *
	 * @return the company ID of this open ID connect session
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the configuration pid of this open ID connect session.
	 *
	 * @return the configuration pid of this open ID connect session
	 */
	@Override
	public String getConfigurationPid() {
		return model.getConfigurationPid();
	}

	/**
	 * Returns the id token of this open ID connect session.
	 *
	 * @return the id token of this open ID connect session
	 */
	@Override
	public String getIdToken() {
		return model.getIdToken();
	}

	/**
	 * Returns the modified date of this open ID connect session.
	 *
	 * @return the modified date of this open ID connect session
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this open ID connect session.
	 *
	 * @return the mvcc version of this open ID connect session
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the open ID connect session ID of this open ID connect session.
	 *
	 * @return the open ID connect session ID of this open ID connect session
	 */
	@Override
	public long getOpenIdConnectSessionId() {
		return model.getOpenIdConnectSessionId();
	}

	/**
	 * Returns the primary key of this open ID connect session.
	 *
	 * @return the primary key of this open ID connect session
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the provider name of this open ID connect session.
	 *
	 * @return the provider name of this open ID connect session
	 */
	@Override
	public String getProviderName() {
		return model.getProviderName();
	}

	/**
	 * Returns the refresh token of this open ID connect session.
	 *
	 * @return the refresh token of this open ID connect session
	 */
	@Override
	public String getRefreshToken() {
		return model.getRefreshToken();
	}

	/**
	 * Returns the user ID of this open ID connect session.
	 *
	 * @return the user ID of this open ID connect session
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this open ID connect session.
	 *
	 * @return the user uuid of this open ID connect session
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the access token of this open ID connect session.
	 *
	 * @param accessToken the access token of this open ID connect session
	 */
	@Override
	public void setAccessToken(String accessToken) {
		model.setAccessToken(accessToken);
	}

	/**
	 * Sets the company ID of this open ID connect session.
	 *
	 * @param companyId the company ID of this open ID connect session
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the configuration pid of this open ID connect session.
	 *
	 * @param configurationPid the configuration pid of this open ID connect session
	 */
	@Override
	public void setConfigurationPid(String configurationPid) {
		model.setConfigurationPid(configurationPid);
	}

	/**
	 * Sets the id token of this open ID connect session.
	 *
	 * @param idToken the id token of this open ID connect session
	 */
	@Override
	public void setIdToken(String idToken) {
		model.setIdToken(idToken);
	}

	/**
	 * Sets the modified date of this open ID connect session.
	 *
	 * @param modifiedDate the modified date of this open ID connect session
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this open ID connect session.
	 *
	 * @param mvccVersion the mvcc version of this open ID connect session
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the open ID connect session ID of this open ID connect session.
	 *
	 * @param openIdConnectSessionId the open ID connect session ID of this open ID connect session
	 */
	@Override
	public void setOpenIdConnectSessionId(long openIdConnectSessionId) {
		model.setOpenIdConnectSessionId(openIdConnectSessionId);
	}

	/**
	 * Sets the primary key of this open ID connect session.
	 *
	 * @param primaryKey the primary key of this open ID connect session
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the provider name of this open ID connect session.
	 *
	 * @param providerName the provider name of this open ID connect session
	 */
	@Override
	public void setProviderName(String providerName) {
		model.setProviderName(providerName);
	}

	/**
	 * Sets the refresh token of this open ID connect session.
	 *
	 * @param refreshToken the refresh token of this open ID connect session
	 */
	@Override
	public void setRefreshToken(String refreshToken) {
		model.setRefreshToken(refreshToken);
	}

	/**
	 * Sets the user ID of this open ID connect session.
	 *
	 * @param userId the user ID of this open ID connect session
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this open ID connect session.
	 *
	 * @param userUuid the user uuid of this open ID connect session
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected OpenIdConnectSessionWrapper wrap(
		OpenIdConnectSession openIdConnectSession) {

		return new OpenIdConnectSessionWrapper(openIdConnectSession);
	}

}