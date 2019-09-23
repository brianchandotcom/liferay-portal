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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OAuth2Authorization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2Authorization
 * @generated
 */
public class OAuth2AuthorizationWrapper
	extends BaseModelWrapper<OAuth2Authorization>
	implements ModelWrapper<OAuth2Authorization>, OAuth2Authorization {

	public OAuth2AuthorizationWrapper(OAuth2Authorization oAuth2Authorization) {
		super(oAuth2Authorization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuth2AuthorizationId", getOAuth2AuthorizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("oAuth2ApplicationId", getOAuth2ApplicationId());
		attributes.put(
			"oAuth2ApplicationScopeAliasesId",
			getOAuth2ApplicationScopeAliasesId());
		attributes.put("accessTokenContent", getAccessTokenContent());
		attributes.put("accessTokenContentHash", getAccessTokenContentHash());
		attributes.put("accessTokenCreateDate", getAccessTokenCreateDate());
		attributes.put(
			"accessTokenExpirationDate", getAccessTokenExpirationDate());
		attributes.put("remoteHostInfo", getRemoteHostInfo());
		attributes.put("remoteIPInfo", getRemoteIPInfo());
		attributes.put("refreshTokenContent", getRefreshTokenContent());
		attributes.put("refreshTokenContentHash", getRefreshTokenContentHash());
		attributes.put("refreshTokenCreateDate", getRefreshTokenCreateDate());
		attributes.put(
			"refreshTokenExpirationDate", getRefreshTokenExpirationDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuth2AuthorizationId = (Long)attributes.get(
			"oAuth2AuthorizationId");

		if (oAuth2AuthorizationId != null) {
			setOAuth2AuthorizationId(oAuth2AuthorizationId);
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

		Long oAuth2ApplicationId = (Long)attributes.get("oAuth2ApplicationId");

		if (oAuth2ApplicationId != null) {
			setOAuth2ApplicationId(oAuth2ApplicationId);
		}

		Long oAuth2ApplicationScopeAliasesId = (Long)attributes.get(
			"oAuth2ApplicationScopeAliasesId");

		if (oAuth2ApplicationScopeAliasesId != null) {
			setOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);
		}

		String accessTokenContent = (String)attributes.get(
			"accessTokenContent");

		if (accessTokenContent != null) {
			setAccessTokenContent(accessTokenContent);
		}

		Long accessTokenContentHash = (Long)attributes.get(
			"accessTokenContentHash");

		if (accessTokenContentHash != null) {
			setAccessTokenContentHash(accessTokenContentHash);
		}

		Date accessTokenCreateDate = (Date)attributes.get(
			"accessTokenCreateDate");

		if (accessTokenCreateDate != null) {
			setAccessTokenCreateDate(accessTokenCreateDate);
		}

		Date accessTokenExpirationDate = (Date)attributes.get(
			"accessTokenExpirationDate");

		if (accessTokenExpirationDate != null) {
			setAccessTokenExpirationDate(accessTokenExpirationDate);
		}

		String remoteHostInfo = (String)attributes.get("remoteHostInfo");

		if (remoteHostInfo != null) {
			setRemoteHostInfo(remoteHostInfo);
		}

		String remoteIPInfo = (String)attributes.get("remoteIPInfo");

		if (remoteIPInfo != null) {
			setRemoteIPInfo(remoteIPInfo);
		}

		String refreshTokenContent = (String)attributes.get(
			"refreshTokenContent");

		if (refreshTokenContent != null) {
			setRefreshTokenContent(refreshTokenContent);
		}

		Long refreshTokenContentHash = (Long)attributes.get(
			"refreshTokenContentHash");

		if (refreshTokenContentHash != null) {
			setRefreshTokenContentHash(refreshTokenContentHash);
		}

		Date refreshTokenCreateDate = (Date)attributes.get(
			"refreshTokenCreateDate");

		if (refreshTokenCreateDate != null) {
			setRefreshTokenCreateDate(refreshTokenCreateDate);
		}

		Date refreshTokenExpirationDate = (Date)attributes.get(
			"refreshTokenExpirationDate");

		if (refreshTokenExpirationDate != null) {
			setRefreshTokenExpirationDate(refreshTokenExpirationDate);
		}
	}

	/**
	 * Returns the access token content of this o auth2 authorization.
	 *
	 * @return the access token content of this o auth2 authorization
	 */
	@Override
	public String getAccessTokenContent() {
		return model.getAccessTokenContent();
	}

	/**
	 * Returns the access token content hash of this o auth2 authorization.
	 *
	 * @return the access token content hash of this o auth2 authorization
	 */
	@Override
	public long getAccessTokenContentHash() {
		return model.getAccessTokenContentHash();
	}

	/**
	 * Returns the access token create date of this o auth2 authorization.
	 *
	 * @return the access token create date of this o auth2 authorization
	 */
	@Override
	public Date getAccessTokenCreateDate() {
		return model.getAccessTokenCreateDate();
	}

	/**
	 * Returns the access token expiration date of this o auth2 authorization.
	 *
	 * @return the access token expiration date of this o auth2 authorization
	 */
	@Override
	public Date getAccessTokenExpirationDate() {
		return model.getAccessTokenExpirationDate();
	}

	/**
	 * Returns the company ID of this o auth2 authorization.
	 *
	 * @return the company ID of this o auth2 authorization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this o auth2 authorization.
	 *
	 * @return the create date of this o auth2 authorization
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the o auth2 application ID of this o auth2 authorization.
	 *
	 * @return the o auth2 application ID of this o auth2 authorization
	 */
	@Override
	public long getOAuth2ApplicationId() {
		return model.getOAuth2ApplicationId();
	}

	/**
	 * Returns the o auth2 application scope aliases ID of this o auth2 authorization.
	 *
	 * @return the o auth2 application scope aliases ID of this o auth2 authorization
	 */
	@Override
	public long getOAuth2ApplicationScopeAliasesId() {
		return model.getOAuth2ApplicationScopeAliasesId();
	}

	/**
	 * Returns the o auth2 authorization ID of this o auth2 authorization.
	 *
	 * @return the o auth2 authorization ID of this o auth2 authorization
	 */
	@Override
	public long getOAuth2AuthorizationId() {
		return model.getOAuth2AuthorizationId();
	}

	/**
	 * Returns the primary key of this o auth2 authorization.
	 *
	 * @return the primary key of this o auth2 authorization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the refresh token content of this o auth2 authorization.
	 *
	 * @return the refresh token content of this o auth2 authorization
	 */
	@Override
	public String getRefreshTokenContent() {
		return model.getRefreshTokenContent();
	}

	/**
	 * Returns the refresh token content hash of this o auth2 authorization.
	 *
	 * @return the refresh token content hash of this o auth2 authorization
	 */
	@Override
	public long getRefreshTokenContentHash() {
		return model.getRefreshTokenContentHash();
	}

	/**
	 * Returns the refresh token create date of this o auth2 authorization.
	 *
	 * @return the refresh token create date of this o auth2 authorization
	 */
	@Override
	public Date getRefreshTokenCreateDate() {
		return model.getRefreshTokenCreateDate();
	}

	/**
	 * Returns the refresh token expiration date of this o auth2 authorization.
	 *
	 * @return the refresh token expiration date of this o auth2 authorization
	 */
	@Override
	public Date getRefreshTokenExpirationDate() {
		return model.getRefreshTokenExpirationDate();
	}

	/**
	 * Returns the remote host info of this o auth2 authorization.
	 *
	 * @return the remote host info of this o auth2 authorization
	 */
	@Override
	public String getRemoteHostInfo() {
		return model.getRemoteHostInfo();
	}

	/**
	 * Returns the remote ip info of this o auth2 authorization.
	 *
	 * @return the remote ip info of this o auth2 authorization
	 */
	@Override
	public String getRemoteIPInfo() {
		return model.getRemoteIPInfo();
	}

	/**
	 * Returns the user ID of this o auth2 authorization.
	 *
	 * @return the user ID of this o auth2 authorization
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this o auth2 authorization.
	 *
	 * @return the user name of this o auth2 authorization
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this o auth2 authorization.
	 *
	 * @return the user uuid of this o auth2 authorization
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a o auth2 authorization model instance should use the <code>OAuth2Authorization</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the access token content of this o auth2 authorization.
	 *
	 * @param accessTokenContent the access token content of this o auth2 authorization
	 */
	@Override
	public void setAccessTokenContent(String accessTokenContent) {
		model.setAccessTokenContent(accessTokenContent);
	}

	/**
	 * Sets the access token content hash of this o auth2 authorization.
	 *
	 * @param accessTokenContentHash the access token content hash of this o auth2 authorization
	 */
	@Override
	public void setAccessTokenContentHash(long accessTokenContentHash) {
		model.setAccessTokenContentHash(accessTokenContentHash);
	}

	/**
	 * Sets the access token create date of this o auth2 authorization.
	 *
	 * @param accessTokenCreateDate the access token create date of this o auth2 authorization
	 */
	@Override
	public void setAccessTokenCreateDate(Date accessTokenCreateDate) {
		model.setAccessTokenCreateDate(accessTokenCreateDate);
	}

	/**
	 * Sets the access token expiration date of this o auth2 authorization.
	 *
	 * @param accessTokenExpirationDate the access token expiration date of this o auth2 authorization
	 */
	@Override
	public void setAccessTokenExpirationDate(Date accessTokenExpirationDate) {
		model.setAccessTokenExpirationDate(accessTokenExpirationDate);
	}

	/**
	 * Sets the company ID of this o auth2 authorization.
	 *
	 * @param companyId the company ID of this o auth2 authorization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this o auth2 authorization.
	 *
	 * @param createDate the create date of this o auth2 authorization
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the o auth2 application ID of this o auth2 authorization.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID of this o auth2 authorization
	 */
	@Override
	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		model.setOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	 * Sets the o auth2 application scope aliases ID of this o auth2 authorization.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID of this o auth2 authorization
	 */
	@Override
	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {

		model.setOAuth2ApplicationScopeAliasesId(
			oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Sets the o auth2 authorization ID of this o auth2 authorization.
	 *
	 * @param oAuth2AuthorizationId the o auth2 authorization ID of this o auth2 authorization
	 */
	@Override
	public void setOAuth2AuthorizationId(long oAuth2AuthorizationId) {
		model.setOAuth2AuthorizationId(oAuth2AuthorizationId);
	}

	/**
	 * Sets the primary key of this o auth2 authorization.
	 *
	 * @param primaryKey the primary key of this o auth2 authorization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the refresh token content of this o auth2 authorization.
	 *
	 * @param refreshTokenContent the refresh token content of this o auth2 authorization
	 */
	@Override
	public void setRefreshTokenContent(String refreshTokenContent) {
		model.setRefreshTokenContent(refreshTokenContent);
	}

	/**
	 * Sets the refresh token content hash of this o auth2 authorization.
	 *
	 * @param refreshTokenContentHash the refresh token content hash of this o auth2 authorization
	 */
	@Override
	public void setRefreshTokenContentHash(long refreshTokenContentHash) {
		model.setRefreshTokenContentHash(refreshTokenContentHash);
	}

	/**
	 * Sets the refresh token create date of this o auth2 authorization.
	 *
	 * @param refreshTokenCreateDate the refresh token create date of this o auth2 authorization
	 */
	@Override
	public void setRefreshTokenCreateDate(Date refreshTokenCreateDate) {
		model.setRefreshTokenCreateDate(refreshTokenCreateDate);
	}

	/**
	 * Sets the refresh token expiration date of this o auth2 authorization.
	 *
	 * @param refreshTokenExpirationDate the refresh token expiration date of this o auth2 authorization
	 */
	@Override
	public void setRefreshTokenExpirationDate(Date refreshTokenExpirationDate) {
		model.setRefreshTokenExpirationDate(refreshTokenExpirationDate);
	}

	/**
	 * Sets the remote host info of this o auth2 authorization.
	 *
	 * @param remoteHostInfo the remote host info of this o auth2 authorization
	 */
	@Override
	public void setRemoteHostInfo(String remoteHostInfo) {
		model.setRemoteHostInfo(remoteHostInfo);
	}

	/**
	 * Sets the remote ip info of this o auth2 authorization.
	 *
	 * @param remoteIPInfo the remote ip info of this o auth2 authorization
	 */
	@Override
	public void setRemoteIPInfo(String remoteIPInfo) {
		model.setRemoteIPInfo(remoteIPInfo);
	}

	/**
	 * Sets the user ID of this o auth2 authorization.
	 *
	 * @param userId the user ID of this o auth2 authorization
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this o auth2 authorization.
	 *
	 * @param userName the user name of this o auth2 authorization
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this o auth2 authorization.
	 *
	 * @param userUuid the user uuid of this o auth2 authorization
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected OAuth2AuthorizationWrapper wrap(
		OAuth2Authorization oAuth2Authorization) {

		return new OAuth2AuthorizationWrapper(oAuth2Authorization);
	}

}