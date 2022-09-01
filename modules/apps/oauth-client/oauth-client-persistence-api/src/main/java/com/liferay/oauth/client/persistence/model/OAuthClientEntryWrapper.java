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

package com.liferay.oauth.client.persistence.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OAuthClientEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientEntry
 * @generated
 */
public class OAuthClientEntryWrapper
	extends BaseModelWrapper<OAuthClientEntry>
	implements ModelWrapper<OAuthClientEntry>, OAuthClientEntry {

	public OAuthClientEntryWrapper(OAuthClientEntry oAuthClientEntry) {
		super(oAuthClientEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("oAuthClientEntryId", getOAuthClientEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"authRequestParametersJSON", getAuthRequestParametersJSON());
		attributes.put("authServerWellKnownURI", getAuthServerWellKnownURI());
		attributes.put("clientId", getClientId());
		attributes.put("infoJSON", getInfoJSON());
		attributes.put("oidcUserInfoMapperJSON", getOIDCUserInfoMapperJSON());
		attributes.put(
			"tokenRequestParametersJSON", getTokenRequestParametersJSON());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long oAuthClientEntryId = (Long)attributes.get("oAuthClientEntryId");

		if (oAuthClientEntryId != null) {
			setOAuthClientEntryId(oAuthClientEntryId);
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

		String authRequestParametersJSON = (String)attributes.get(
			"authRequestParametersJSON");

		if (authRequestParametersJSON != null) {
			setAuthRequestParametersJSON(authRequestParametersJSON);
		}

		String authServerWellKnownURI = (String)attributes.get(
			"authServerWellKnownURI");

		if (authServerWellKnownURI != null) {
			setAuthServerWellKnownURI(authServerWellKnownURI);
		}

		String clientId = (String)attributes.get("clientId");

		if (clientId != null) {
			setClientId(clientId);
		}

		String infoJSON = (String)attributes.get("infoJSON");

		if (infoJSON != null) {
			setInfoJSON(infoJSON);
		}

		String oidcUserInfoMapperJSON = (String)attributes.get(
			"oidcUserInfoMapperJSON");

		if (oidcUserInfoMapperJSON != null) {
			setOIDCUserInfoMapperJSON(oidcUserInfoMapperJSON);
		}

		String tokenRequestParametersJSON = (String)attributes.get(
			"tokenRequestParametersJSON");

		if (tokenRequestParametersJSON != null) {
			setTokenRequestParametersJSON(tokenRequestParametersJSON);
		}
	}

	@Override
	public OAuthClientEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the auth request parameters json of this o auth client entry.
	 *
	 * @return the auth request parameters json of this o auth client entry
	 */
	@Override
	public String getAuthRequestParametersJSON() {
		return model.getAuthRequestParametersJSON();
	}

	/**
	 * Returns the auth server well known uri of this o auth client entry.
	 *
	 * @return the auth server well known uri of this o auth client entry
	 */
	@Override
	public String getAuthServerWellKnownURI() {
		return model.getAuthServerWellKnownURI();
	}

	/**
	 * Returns the client ID of this o auth client entry.
	 *
	 * @return the client ID of this o auth client entry
	 */
	@Override
	public String getClientId() {
		return model.getClientId();
	}

	/**
	 * Returns the company ID of this o auth client entry.
	 *
	 * @return the company ID of this o auth client entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this o auth client entry.
	 *
	 * @return the create date of this o auth client entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the info json of this o auth client entry.
	 *
	 * @return the info json of this o auth client entry
	 */
	@Override
	public String getInfoJSON() {
		return model.getInfoJSON();
	}

	/**
	 * Returns the modified date of this o auth client entry.
	 *
	 * @return the modified date of this o auth client entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this o auth client entry.
	 *
	 * @return the mvcc version of this o auth client entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the o auth client entry ID of this o auth client entry.
	 *
	 * @return the o auth client entry ID of this o auth client entry
	 */
	@Override
	public long getOAuthClientEntryId() {
		return model.getOAuthClientEntryId();
	}

	/**
	 * Returns the oidc user info mapper json of this o auth client entry.
	 *
	 * @return the oidc user info mapper json of this o auth client entry
	 */
	@Override
	public String getOIDCUserInfoMapperJSON() {
		return model.getOIDCUserInfoMapperJSON();
	}

	/**
	 * Returns the primary key of this o auth client entry.
	 *
	 * @return the primary key of this o auth client entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the token request parameters json of this o auth client entry.
	 *
	 * @return the token request parameters json of this o auth client entry
	 */
	@Override
	public String getTokenRequestParametersJSON() {
		return model.getTokenRequestParametersJSON();
	}

	/**
	 * Returns the user ID of this o auth client entry.
	 *
	 * @return the user ID of this o auth client entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this o auth client entry.
	 *
	 * @return the user name of this o auth client entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this o auth client entry.
	 *
	 * @return the user uuid of this o auth client entry
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
	 * Sets the auth request parameters json of this o auth client entry.
	 *
	 * @param authRequestParametersJSON the auth request parameters json of this o auth client entry
	 */
	@Override
	public void setAuthRequestParametersJSON(String authRequestParametersJSON) {
		model.setAuthRequestParametersJSON(authRequestParametersJSON);
	}

	/**
	 * Sets the auth server well known uri of this o auth client entry.
	 *
	 * @param authServerWellKnownURI the auth server well known uri of this o auth client entry
	 */
	@Override
	public void setAuthServerWellKnownURI(String authServerWellKnownURI) {
		model.setAuthServerWellKnownURI(authServerWellKnownURI);
	}

	/**
	 * Sets the client ID of this o auth client entry.
	 *
	 * @param clientId the client ID of this o auth client entry
	 */
	@Override
	public void setClientId(String clientId) {
		model.setClientId(clientId);
	}

	/**
	 * Sets the company ID of this o auth client entry.
	 *
	 * @param companyId the company ID of this o auth client entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this o auth client entry.
	 *
	 * @param createDate the create date of this o auth client entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the info json of this o auth client entry.
	 *
	 * @param infoJSON the info json of this o auth client entry
	 */
	@Override
	public void setInfoJSON(String infoJSON) {
		model.setInfoJSON(infoJSON);
	}

	/**
	 * Sets the modified date of this o auth client entry.
	 *
	 * @param modifiedDate the modified date of this o auth client entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this o auth client entry.
	 *
	 * @param mvccVersion the mvcc version of this o auth client entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the o auth client entry ID of this o auth client entry.
	 *
	 * @param oAuthClientEntryId the o auth client entry ID of this o auth client entry
	 */
	@Override
	public void setOAuthClientEntryId(long oAuthClientEntryId) {
		model.setOAuthClientEntryId(oAuthClientEntryId);
	}

	/**
	 * Sets the oidc user info mapper json of this o auth client entry.
	 *
	 * @param oidcUserInfoMapperJSON the oidc user info mapper json of this o auth client entry
	 */
	@Override
	public void setOIDCUserInfoMapperJSON(String oidcUserInfoMapperJSON) {
		model.setOIDCUserInfoMapperJSON(oidcUserInfoMapperJSON);
	}

	/**
	 * Sets the primary key of this o auth client entry.
	 *
	 * @param primaryKey the primary key of this o auth client entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the token request parameters json of this o auth client entry.
	 *
	 * @param tokenRequestParametersJSON the token request parameters json of this o auth client entry
	 */
	@Override
	public void setTokenRequestParametersJSON(
		String tokenRequestParametersJSON) {

		model.setTokenRequestParametersJSON(tokenRequestParametersJSON);
	}

	/**
	 * Sets the user ID of this o auth client entry.
	 *
	 * @param userId the user ID of this o auth client entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this o auth client entry.
	 *
	 * @param userName the user name of this o auth client entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this o auth client entry.
	 *
	 * @param userUuid the user uuid of this o auth client entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected OAuthClientEntryWrapper wrap(OAuthClientEntry oAuthClientEntry) {
		return new OAuthClientEntryWrapper(oAuthClientEntry);
	}

}